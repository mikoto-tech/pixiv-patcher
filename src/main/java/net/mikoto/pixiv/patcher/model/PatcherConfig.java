package net.mikoto.pixiv.patcher.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author mikoto
 * {@code @time} 2022/9/25
 * Create for pixiv-patcher
 */
@Configuration
@ConfigurationProperties(prefix = "mikoto.pixiv.patcher")
public class PatcherConfig {
    private int threadCount;
    private int beginningArtworkId;
    private int targetArtworkId;
    private int cacheSize;
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private TimeUnit timeUnit;
    private String nameFormat;
    private String key;
    private Source usingSource;
    private Storage[] usingStorage;
    private DatabaseType databaseType;
    private Local local;

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getBeginningArtworkId() {
        return beginningArtworkId;
    }

    public void setBeginningArtworkId(int beginningArtworkId) {
        this.beginningArtworkId = beginningArtworkId;
    }

    public int getTargetArtworkId() {
        return targetArtworkId;
    }

    public void setTargetArtworkId(int targetArtworkId) {
        this.targetArtworkId = targetArtworkId;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getNameFormat() {
        return nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Source getUsingSource() {
        return usingSource;
    }

    public void setUsingSource(Source usingSource) {
        this.usingSource = usingSource;
    }

    public Storage[] getUsingStorage() {
        return usingStorage;
    }

    public void setUsingStorage(Storage[] usingStorage) {
        this.usingStorage = usingStorage;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public static class Local {
        private String path;
        private boolean isSaveImage;
        private ImageType[] imageTypes;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSaveImage() {
            return isSaveImage;
        }

        public void setSaveImage(boolean saveImage) {
            isSaveImage = saveImage;
        }

        public ImageType[] getImageTypes() {
            return imageTypes;
        }

        public void setImageTypes(ImageType[] imageTypes) {
            this.imageTypes = imageTypes;
        }
    }
}
