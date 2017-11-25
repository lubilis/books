package app.test.com.testapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import app.test.com.testapp.R;
import app.test.com.testapp.models.BookModel;
import app.test.com.testapp.widget.CircularImageView;

/**
 * Activity that shows the details of a book
 *
 * @author omar.brugna
 */
public class BookActivity extends BaseActivity {

    public static final String BOOK_EXTRA = "book_extra";

    private BookModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mModel = activityBundle.getParcelable(BOOK_EXTRA);
        assert mModel != null;

        setActionBarTitle(mModel.getVolumeInfo().getTitle());

        prepare();
    }

    private void prepare() {

        CircularImageView bookImageView = (CircularImageView) findViewById(R.id.bookImage);
        TextView title = (TextView) findViewById(R.id.bookTitle);
        TextView authors = (TextView) findViewById(R.id.bookAuthors);
        TextView publishedDate = (TextView) findViewById(R.id.bookPublishedDate);
        TextView bookId = (TextView) findViewById(R.id.bookId);

        bookImageView.displayImage(mModel.getVolumeInfo().getImageLinks().getThumbnail(), true, false);
        title.setText(mModel.getVolumeInfo().getTitle());
        authors.setText(mModel.getVolumeInfo().getAuthorsList());
        publishedDate.setText(String.valueOf(mModel.getVolumeInfo().getPublishedDate()));
        bookId.setText(String.valueOf(mModel.getId()));
    }
}
