package app.test.com.testapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This data model contains information about a google books api response json
 *
 * @author omar.brugna
 */
public class ResponseModel {

    private String kind;
    private Integer totalItems;

    @SerializedName("items")
    private ArrayList<BookModel> books;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<BookModel> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookModel> books) {
        this.books = books;
    }
}
