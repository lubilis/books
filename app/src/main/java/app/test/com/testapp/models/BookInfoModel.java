package app.test.com.testapp.models;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * This data model contains information about a book
 *
 * @author omar.brugna
 */
public class BookInfoModel {

    private String title;
    private ArrayList<String> authors;
    private String publishedDate;
    private BookImageLinksModel imageLinks;

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
}
