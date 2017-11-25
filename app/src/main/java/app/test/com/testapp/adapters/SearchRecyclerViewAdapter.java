package app.test.com.testapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.test.com.testapp.R;
import app.test.com.testapp.activities.SearchActivity;
import app.test.com.testapp.models.BookModel;
import app.test.com.testapp.widget.CircularImageView;

/**
 * Adapter for {@link RecyclerView}
 *
 * @author omar.brugna
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SearchActivity mActivity;

    private final ArrayList<BookModel> mItems;

    public SearchRecyclerViewAdapter(SearchActivity activity) {
        super();
        mActivity = activity;
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.book_item, viewGroup, false);
        return new BookViewHolder(mActivity, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BookViewHolder) holder).bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(ArrayList<BookModel> items) {
        for (BookModel item : items)
            if (item != null && item.isValid())
                addItem(item);
    }

    private void addItem(@NonNull BookModel model) {
        addItem(mItems.size(), model);
    }

    private void addItem(int index, @NonNull BookModel model) {
        mItems.add(index, model);
        notifyItemInserted(index);
    }

    public void removeAllItems() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public interface BookListener {

        void onBookClicked(BookModel model);
    }

    private static class BookViewHolder extends RecyclerView.ViewHolder {

        private final BookListener mListener;
        private final View mContainer;
        private final CircularImageView mImage;
        private final TextView mTitle;
        private final TextView mBookPublishedDate;
        private final TextView mBookId;

        BookViewHolder(BookListener listener, View itemView) {
            super(itemView);
            mListener = listener;
            mContainer = itemView;
            mImage = itemView.findViewById(R.id.bookThumb);
            mTitle = itemView.findViewById(R.id.bookTitle);
            mBookPublishedDate = itemView.findViewById(R.id.bookPublishedDate);
            mBookId = itemView.findViewById(R.id.bookId);
        }

        private void bind(final BookModel model) {

            mImage.displayImage(model.getVolumeInfo().getImageLinks().getSmallThumbnailFallback(), true, false);
            mTitle.setText(model.getVolumeInfo().getTitle());
            mBookPublishedDate.setText(String.valueOf(model.getVolumeInfo().getPublishedDate()));
            mBookId.setText(String.valueOf(model.getId()));

            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onBookClicked(model);
                }
            });
        }
    }
}

