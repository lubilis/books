package app.test.com.testapp.models;

import android.text.TextUtils;

/**
 * This data model contains information about a book
 *
 * @author omar.brugna
 */
public class BookModel {

    private String id;
    private String selfLink;
    private BookInfoModel volumeInfo;

    /**
     * Check if model is valid according to some fields
     *
     * @return true if model is valid
     */
    public boolean isValid() {
        return !TextUtils.isEmpty(id) &&
                !TextUtils.isEmpty(selfLink) &&
                volumeInfo != null && volumeInfo.isValid();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public BookInfoModel getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(BookInfoModel volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
