package app.test.com.testapp.models;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * This data model contains information about a book thumbnail links
 *
 * @author omar.brugna
 */
public class BookImageLinksModel {

    private String smallThumbnail;
    private String thumbnail;

    /**
     * Check if model is valid according to some fields
     *
     * @return true if model is valid
     */
    boolean isValid() {
        return !TextUtils.isEmpty(thumbnail);
    }

    @Nullable
    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return string the small thumbnail link if exists, otherwise the standard thumbnail link
     */
    public String getSmallThumbnailFallback() {
        return smallThumbnail != null ? smallThumbnail : thumbnail;
    }
}
