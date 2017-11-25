package app.test.com.testapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * This data model contains information about a book
 *
 * @author omar.brugna
 */
public class BookModel implements Parcelable {

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };
    private String id;
    private String selfLink;
    private BookInfoModel volumeInfo;

    protected BookModel(Parcel in) {
        id = in.readString();
        selfLink = in.readString();
        volumeInfo = in.readParcelable(BookInfoModel.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(selfLink);
        dest.writeParcelable(volumeInfo, flags);
    }
}
