package app.test.com.testapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.test.com.testapp.R;
import app.test.com.testapp.adapters.SearchRecyclerViewAdapter;
import app.test.com.testapp.asynctasks.TestAsyncTask;
import app.test.com.testapp.models.BookModel;
import app.test.com.testapp.utils.VersionUtils;

/**
 * Activity to perform a search and show a list of books
 *
 * @author omar.brugna
 */
public class SearchActivity extends BaseActivity implements TestAsyncTask.TestAsyncTaskListener, SearchRecyclerViewAdapter.BookListener {

    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private SearchRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        prepare();
    }

    private void prepare() {

        mEditText = (EditText) findViewById(R.id.searchEditText);
        mRecyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);

        mAdapter = new SearchRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mAdapter.getItemCount() > 0) {
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mAdapter.removeAllItems();
                }
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchBooks();
                return true;
            }
        });
    }

    private void searchBooks() {

        // start connection to retrieve books list
        String endpoint = String.format(getResources().getString(R.string.google_books_api), mEditText.getText().toString().trim());
        new TestAsyncTask(this, endpoint).execute();
    }

    @Override
    protected void onConnectivityChanged(boolean isNetworkAvailable) {

        //noinspection StatementWithEmptyBody
        if (isNetworkAvailable) {
            // network is now available, here we could do something like restart
            // connection if it was fired when network was not available and failed..
        }
    }

    // connection delegate methods

    @Override
    public void onStartConnection() {
        hideKeyboard();
        showProgressDialog();
    }

    @Override
    public void onFinishConnection() {
        hideProgressDialog();
    }

    @Override
    public void onSuccessConnection(@NonNull ArrayList<BookModel> models) {
        if (models.size() > 0) {
            mAdapter.removeAllItems();
            mAdapter.addItems(models);

            if (mAdapter.getItemCount() > 0)
                mRecyclerView.setNestedScrollingEnabled(true);

        } else {
            // no books found
            String message = String.format(getResources().getString(R.string.books_not_found), mEditText.getText().toString().trim());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onErrorConnection(@Nullable String errorMessage) {
        if (TextUtils.isEmpty(errorMessage))
            errorMessage = String.format(
                    getResources().getString(R.string.network_error_or_books_not_found),
                    mEditText.getText().toString().trim()
            );

        Snackbar snackbar = Snackbar.make(getRootView(), errorMessage, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(VersionUtils.getColor(this, R.color.colorAccent));
        snackbar.show();
    }

    // BookListener delegate methods

    @Override
    public void onBookClicked(BookModel model) {
        // TODO currently showing something for test..
        String message = String.format(getResources().getString(R.string.book), model.getVolumeInfo().getTitle());
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
