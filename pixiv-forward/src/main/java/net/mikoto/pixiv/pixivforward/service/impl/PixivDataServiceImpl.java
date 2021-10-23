package net.mikoto.pixiv.pixivforward.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.pixivforward.model.PixivData;
import net.mikoto.pixiv.pixivforward.service.PixivDataService;
import net.mikoto.pixiv.pixivforward.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mikoto
 * Created at 2:45:41, 2021/10/3
 * Project: pixiv-forward
 */
public class PixivDataServiceImpl implements PixivDataService {
    private final PixivData pixivData = new PixivData();

    /**
     * Get a pixiv data by the artwork id.
     *
     * @param artworkId The id of this artwork.
     * @return An artwork object.
     * @throws IOException IOException.
     */
    @Override
    public PixivData getPixivDataById(int artworkId) throws IOException {
        // 获取原始Json
        JSONObject rawJson = JSON.parseObject(HttpUtil.httpGet("https://www" +
                ".pixiv.net/ajax/illust/" + artworkId));

        // 解析Json到PixivData对象
        JSONObject jsonBody = rawJson.getJSONObject("body");

        // 处理基础数据
        this.pixivData.setArtworkId(Integer.parseInt(jsonBody.getString(
                "illustId")));
        this.pixivData.setArtworkTitle(jsonBody.getString("illustTitle"));
        this.pixivData.setAuthorId(Integer.parseInt(jsonBody.getString(
                "userId")));
        this.pixivData.setAuthorName(jsonBody.getString("userName"));
        this.pixivData.setDescription(jsonBody.getString("description"));
        this.pixivData.setPageCount(jsonBody.getInteger("pageCount"));
        this.pixivData.setBookmarkCount(jsonBody.getInteger(
                "bookmarkCount"));
        this.pixivData.setLikeCount(jsonBody.getInteger("likeCount"));
        this.pixivData.setViewCount(jsonBody.getInteger("viewCount"));
        this.pixivData.setCreateDate(jsonBody.getString("createDate"));
        this.pixivData.setUpdateDate(jsonBody.getString("uploadDate"));

        // 处理链接
        Map<String, String> urls = new HashMap<>(5);
        urls.put("mini", jsonBody.getJSONObject("urls").getString("mini"));
        urls.put("thumb", jsonBody.getJSONObject("urls").getString("thumb"));
        urls.put("small", jsonBody.getJSONObject("urls").getString("small"));
        urls.put("regular", jsonBody.getJSONObject("urls").getString("regular"));
        urls.put("original", jsonBody.getJSONObject("urls").getString(
                "original"));
        this.pixivData.setIllustUrls(urls);

        // 根据标签判定年龄分级
        Set<String> tags = new HashSet<>();
        int grading = 0;
        for (int i = 0; i <
                jsonBody.getJSONObject("tags").getJSONArray("tags").size(); i++) {
            String tag =
                    jsonBody.getJSONObject("tags").getJSONArray("tags").getJSONObject(i).getString("tag");
            if ("R-18".equals(tag)) {
                grading = 1;
            } else if ("R-18G".equals(tag)) {
                grading = 2;
            }

            tags.add(tag);
        }
        this.pixivData.setGrading(grading);
        this.pixivData.setTags(tags.toArray(new String[0]));

        return this.pixivData;
    }
}
