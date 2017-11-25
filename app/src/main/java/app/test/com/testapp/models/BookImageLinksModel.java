package app.test.com.testapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * This data model contains information about a book thumbnail links
 *
 * @author omar.brugna
 */
public class BookImageLinksModel implements Parcelable {

    public static final Creator<BookImageLinksModel> CREATOR = new Creator<BookImageLinksModel>() {
        @Override
        public BookImageLinksModel createFromParcel(Parcel in) {
            return new BookImageLinksModel(in);
        }

        @Override
        public BookImageLinksModel[] newArray(int size) {
            return new BookImageLinksModel[size];
        }
    };
    private String smallThumbnail;
    private String thumbnail;

    protected BookImageLinksModel(Parcel in) {
        smallThumbnail = in.readString();
        thumbnail = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(smallThumbnail);
        dest.writeString(thumbnail);
    }
}
