package net.mikoto.pixiv.dao;

import net.mikoto.dao.BaseDao;
import net.mikoto.pixiv.Main;
import net.mikoto.pixiv.log.Log;
import net.mikoto.pixiv.pojo.PixivData;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static net.mikoto.pixiv.util.CharUtil.stringToUnicode;


/**
 * @author mikoto
 * @date 2021/12/11 15:22
 */
public class PixivDataDao extends BaseDao {
    /**
     * 常量
     */
    private static final int GRAND_0 = 100000;
    private static final int GRAND_1 = 50000;
    private static final int GRAND_2 = 40000;
    private static final int GRAND_3 = 30000;
    private static final int GRAND_4 = 25000;
    private static final int GRAND_5 = 20000;
    private static final int GRAND_6 = 15000;
    private static final int GRAND_7 = 10000;
    private static final int GRAND_8 = 5000;
    private static final int GRAND_9 = 1000;
    private static final int GRAND_10 = 0;

    /**
     * 单例
     */
    private static final PixivDataDao INSTANCE = new PixivDataDao(Main.PROPERTIES.getProperty("URL"), Main.PROPERTIES.getProperty("USERNAME"), Main.PROPERTIES.getProperty("PASSWORD"));
    private Log logger;

    private PixivDataDao(String url, String userName, String userPassword) {
        super(url, userName, userPassword);
    }

    public static PixivDataDao getInstance() {
        return INSTANCE;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * Get pixiv data from database.
     *
     * @param sql Sql statement
     * @return A pixiv data object.
     * @throws SQLException A sql error.
     */
    public PixivData getPixivData(String sql) throws SQLException {
        PixivData outputPixivData = new PixivData();
        ResultSet resultSet = executeQuery(sql);

        while (resultSet.next()) {
            outputPixivData.setArtworkId(resultSet.getInt("pk_artwork_id"));
            outputPixivData.setArtworkTitle(resultSet.getString("artwork_title"));
            outputPixivData.setAuthorId(resultSet.getInt("author_id"));
            outputPixivData.setAuthorName(resultSet.getString("author_name"));
            outputPixivData.setDescription(resultSet.getString("description"));
            outputPixivData.setPageCount(resultSet.getInt("page_count"));
            outputPixivData.setBookmarkCount(resultSet.getInt("bookmark_count"));
            outputPixivData.setLikeCount(resultSet.getInt("like_count"));
            outputPixivData.setViewCount(resultSet.getInt("view_count"));
            outputPixivData.setGrading(resultSet.getInt("grading"));
            outputPixivData.setCrawlDate(resultSet.getDate("crawl_date").toString());
            outputPixivData.setCreateDate(resultSet.getDate("create_date").toString());
            outputPixivData.setUpdateDate(resultSet.getDate("update_date").toString());
            outputPixivData.setTags(resultSet.getString("tags").split(";"));
            Map<String, String> illustUrls = new HashMap<>(5);
            illustUrls.put("mini", resultSet.getString("illust_url_mini"));
            illustUrls.put("thumb", resultSet.getString("illust_url_thumb"));
            illustUrls.put("small", resultSet.getString("illust_url_small"));
            illustUrls.put("regular", resultSet.getString("illust_url_regular"));
            illustUrls.put("original", resultSet.getString("illust_url_original"));
            outputPixivData.setIllustUrls(illustUrls);
        }

        closeResource();

        logger.log("Query data(id:" + outputPixivData.getArtworkId() + ")");

        return outputPixivData;
    }

    /**
     * Insert pixiv data
     *
     * @param pixivData Pixiv data.
     * @throws SQLException ERROR.
     */
    public synchronized void insertPixivData(@NotNull PixivData pixivData) throws SQLException {
        Connection connection = getConnection();

        String sql = "select count(*) as result from " + getPixivDataTable(pixivData.getBookmarkCount()) + " where pk_artwork_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt("result");
        if (result != 0) {
            sql = "DELETE FROM " + getPixivDataTable(pixivData.getBookmarkCount()) + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pixivData.getArtworkId());
            preparedStatement.executeUpdate();
        }

        sql = "INSERT INTO pixiv_data." + getPixivDataTable(pixivData.getBookmarkCount()) + " (pk_artwork_id, artwork_title, author_id, author_name, description, tags, illust_url_mini, illust_url_thumb, illust_url_small, illust_url_regular, illust_url_original, page_count, bookmark_count, like_count, view_count, grading, update_date, create_date, crawl_date)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        preparedStatement.setString(2, pixivData.getArtworkTitle());
        preparedStatement.setInt(3, pixivData.getAuthorId());
        preparedStatement.setString(4, stringToUnicode(pixivData.getAuthorName()));
        preparedStatement.setString(5, stringToUnicode(pixivData.getDescription()));
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < pixivData.getTags().length; i++) {
            tags.append(pixivData.getTags()[i]);
            if (i != tags.length() - 1) {
                tags.append(";");
            }
        }
        preparedStatement.setString(6, tags.toString());
        preparedStatement.setString(7, pixivData.getIllustUrls().get("mini"));
        preparedStatement.setString(8, pixivData.getIllustUrls().get("thumb"));
        preparedStatement.setString(9, pixivData.getIllustUrls().get("small"));
        preparedStatement.setString(10, pixivData.getIllustUrls().get("regular"));
        preparedStatement.setString(11, pixivData.getIllustUrls().get("original"));
        preparedStatement.setInt(12, pixivData.getPageCount());
        preparedStatement.setInt(13, pixivData.getBookmarkCount());
        preparedStatement.setInt(14, pixivData.getLikeCount());
        preparedStatement.setInt(15, pixivData.getViewCount());
        preparedStatement.setInt(16, pixivData.getGrading());
        preparedStatement.setString(17, pixivData.getUpdateDate());
        preparedStatement.setString(18, pixivData.getCreateDate());
        preparedStatement.setString(19, pixivData.getCrawlDate());
        preparedStatement.executeUpdate();
        logger.log("update data(id:" + pixivData.getArtworkId() + ")");

        closeResource();
    }

    /**
     * Get table.
     *
     * @param bookmarkCount Bookmark count
     * @return A table name
     */
    private String getPixivDataTable(int bookmarkCount) {
        String table = "bookmark_";
        if (bookmarkCount > GRAND_0) {
            table += String.valueOf(GRAND_0);
        } else if (bookmarkCount > GRAND_1) {
            table += GRAND_1 + "_" + GRAND_0;
        } else if (bookmarkCount > GRAND_2) {
            table += GRAND_2 + "_" + GRAND_1;
        } else if (bookmarkCount > GRAND_3) {
            table += GRAND_3 + "_" + GRAND_2;
        } else if (bookmarkCount > GRAND_4) {
            table += GRAND_4 + "_" + GRAND_3;
        } else if (bookmarkCount > GRAND_5) {
            table += GRAND_5 + "_" + GRAND_4;
        } else if (bookmarkCount > GRAND_6) {
            table += GRAND_6 + "_" + GRAND_5;
        } else if (bookmarkCount > GRAND_7) {
            table += GRAND_7 + "_" + GRAND_6;
        } else if (bookmarkCount > GRAND_8) {
            table += GRAND_8 + "_" + GRAND_7;
        } else if (bookmarkCount > GRAND_9) {
            table += GRAND_9 + "_" + GRAND_8;
        } else if (bookmarkCount >= GRAND_10) {
            table += GRAND_10 + "_" + GRAND_9;
        }
        return table;
    }
}
