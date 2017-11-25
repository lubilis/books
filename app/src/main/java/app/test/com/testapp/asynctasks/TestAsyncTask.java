package app.test.com.testapp.asynctasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import app.test.com.testapp.models.BookModel;
import app.test.com.testapp.models.ErrorModel;
import app.test.com.testapp.models.ResponseModel;
import app.test.com.testapp.network.TestConnection;
import app.test.com.testapp.utils.Logger;

/**
 * Default Test {@link AsyncTask)
 *
 * @author omar.brugna
 */
public class TestAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final TestAsyncTaskListener mListener;
    private final String mUrl;
    private String mResponse = null;
    private ArrayList<BookModel> mModels;

    public TestAsyncTask(@NonNull TestAsyncTaskListener listener, @NonNull String url) {
        mListener = listener;
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        mListener.onStartConnection();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        TestConnection connection = new TestConnection(mUrl);
        try {
            mResponse = connection.initConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("Connection error" + Logger.DIVIDER + e.toString());
        }
        return mResponse != null;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            if (deserializeResponse())
                mListener.onSuccessConnection(mModels);
            else {
                Logger.e("Response model (or response model result) is null");
                mListener.onErrorConnection(deserializeError());
            }
        } else
            mListener.onErrorConnection(null);

        mListener.onFinishConnection();
    }

    private boolean deserializeResponse() {
        try {
            ResponseModel responseModel = (new Gson()).fromJson(mResponse, new TypeToken<ResponseModel>() {
            }.getType());
            mModels = responseModel.getBooks();
            return mModels != null;
        } catch (JsonSyntaxException | IllegalStateException e) {
            Logger.e("Error parsing json response");
        }
        return false;
    }

    /**
     * Try to retrieve api error message
     *
     * @return api error message if parsed, null otherwise
     */
    @Nullable
    private String deserializeError() {
        try {
            ErrorModel error = (new Gson()).fromJson(mResponse, new TypeToken<ErrorModel>() {
            }.getType());
            return error.getMessage();
        } catch (Exception e) {
            Logger.e("Error parsing json response");
        }
        return null;
    }

    /**
     * Listener to receive callback from the {@link TestAsyncTask}
     */
    public interface TestAsyncTaskListener {

        void onStartConnection();

        void onFinishConnection();

        void onSuccessConnection(@NonNull ArrayList<BookModel> models);

        void onErrorConnection(@Nullable String errorMessage);
    }
}
