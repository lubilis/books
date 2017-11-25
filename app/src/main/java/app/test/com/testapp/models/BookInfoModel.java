package app.test.com.testapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * This data model contains information about a book
 *
 * @author omar.brugna
 */
public class BookInfoModel implements Parcelable {

    public static final Creator<BookInfoModel> CREATOR = new Creator<BookInfoModel>() {
        @Override
        public BookInfoModel createFromParcel(Parcel in) {
            return new BookInfoModel(in);
        }

        @Override
        public BookInfoModel[] newArray(int size) {
            return new BookInfoModel[size];
        }
    };
    private String title;
    private ArrayList<String> authors;
    private String publishedDate;
    private BookImageLinksModel imageLinks;

    protected BookInfoModel(Parcel in) {
        title = in.readString();
        authors = in.createStringArrayList();
        publishedDate = in.readString();
        imageLinks = in.readParcelable(BookImageLinksModel.class.getClassLoader());
    }

    /**
     * Check if model is valid according to some fields
     *
     * @return true if model is valid
     */
    public boolean isValid() {
        return !TextUtils.isEmpty(title) &&
                imageLinks != null && imageLinks.isValid();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    @NonNull
    public String getAuthorsList() {
        StringBuilder result = new StringBuilder();
        if (authors != null) {
            for (String author : authors) {
                if (!TextUtils.isEmpty(result))
                    result.append(", ");
                result.append(author);
            }
        }
        return result.toString();
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public BookImageLinksModel getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(BookImageLinksModel imageLinks) {
        this.imageLinks = imageLinks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(authors);
        dest.writeString(publishedDate);
        dest.writeParcelable(imageLinks, flags);
    }
}
