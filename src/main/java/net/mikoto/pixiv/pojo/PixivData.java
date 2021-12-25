package net.mikoto.pixiv.pojo;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * Created at 21:30:24, 2021/9/19
 * Project: PixivRelay
 */
public class PixivData {
    private int artworkId;
    private String artworkTitle;
    private int authorId;
    private String authorName;
    private String description;
    private Map<String, String> illustUrls;
    private int pageCount;
    private int bookmarkCount;
    private int likeCount;
    private int viewCount;
    private int grading;
    private String[] tags;
    private String createDate;
    private String updateDate;
    private String crawlDate;

    /**
     * Load pixiv data from json
     *
     * @param jsonObject Json object
     * @return Pixiv data
     */
    public PixivData loadJson(@NotNull JSONObject jsonObject) {
        JSONObject jsonObjectBody = jsonObject.getJSONObject("body");
        this.artworkId = jsonObjectBody.getInteger("artworkId");
        this.artworkTitle = jsonObjectBody.getString("artworkTitle");
        this.authorId = jsonObjectBody.getInteger("authorId");
        this.authorName = jsonObjectBody.getString("authorName");
        this.description = jsonObjectBody.getString("description");
        Map<String, String> urls = new HashMap<>(5);
        urls.put("small", jsonObjectBody.getJSONObject("illustUrls").getString("small"));
        urls.put("original", jsonObjectBody.getJSONObject("illustUrls").getString("original"));
        urls.put("mini", jsonObjectBody.getJSONObject("illustUrls").getString("mini"));
        urls.put("thumb", jsonObjectBody.getJSONObject("illustUrls").getString("thumb"));
        urls.put("regular", jsonObjectBody.getJSONObject("illustUrls").getString("regular"));
        this.illustUrls = urls;
        this.pageCount = jsonObjectBody.getInteger("pageCount");
        this.bookmarkCount = jsonObjectBody.getInteger("bookmarkCount");
        this.likeCount = jsonObjectBody.getInteger("likeCount");
        this.viewCount = jsonObjectBody.getInteger("viewCount");
        this.grading = jsonObjectBody.getInteger("grading");
        this.tags = new String[jsonObjectBody.getJSONArray("tags").size()];
        for (int i = 0; i < jsonObjectBody.getJSONArray("tags").size(); i++) {
            tags[i] = jsonObjectBody.getJSONArray("tags").getString(i);
        }
        this.createDate = jsonObjectBody.getString("createDate");
        this.updateDate = jsonObjectBody.getString("updateDate");
        this.crawlDate = jsonObject.getString("crawlDate");

        return this;
    }

    /**
     * Change pixiv data to fast json object.
     *
     * @return A fast json object.
     */
    public JSONObject toJsonObject() {
        JSONObject outputJson = new JSONObject();
        JSONObject outputJsonBody = new JSONObject();
        outputJsonBody.put("artworkId", artworkId);
        outputJsonBody.put("artworkTitle", artworkTitle);
        outputJsonBody.put("authorId", authorId);
        outputJsonBody.put("authorName", authorName);
        outputJsonBody.put("description", description);
        outputJsonBody.put("illustUrls", illustUrls);
        outputJsonBody.put("pageCount", pageCount);
        outputJsonBody.put("bookmarkCount", bookmarkCount);
        outputJsonBody.put("likeCount", likeCount);
        outputJsonBody.put("viewCount", viewCount);
        outputJsonBody.put("grading", grading);
        outputJsonBody.put("tags", tags);
        outputJsonBody.put("createDate", createDate);
        outputJsonBody.put("updateDate", updateDate);
        outputJsonBody.put("crawlDate", crawlDate);

        outputJson.put("body", outputJsonBody);

        return outputJson;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public void setArtworkTitle(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getGrading() {
        return grading;
    }

    public void setGrading(int grading) {
        this.grading = grading;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Map<String, String> getIllustUrls() {
        return illustUrls;
    }

    public void setIllustUrls(Map<String, String> illustUrls) {
        this.illustUrls = illustUrls;
    }

    public String getCrawlDate() {
        return crawlDate;
    }

    public void setCrawlDate(String crawlDate) {
        this.crawlDate = crawlDate;
    }
}

