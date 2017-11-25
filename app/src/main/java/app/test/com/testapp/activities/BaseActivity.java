package app.test.com.testapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.test.com.testapp.R;
import app.test.com.testapp.utils.DeviceUtils;

/**
 * Abstract base {@link AppCompatActivity} class
 *
 * @author omar.brugna
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Bundle activityBundle;
    private ViewGroup mRootView;
    private FrameLayout mProgressDialog;
    private boolean isProgressDialogVisible;
    private TextView mActionBarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            activityBundle = savedInstanceState;
        else
            activityBundle = getIntent().getExtras();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        // get root view
        mRootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        ViewGroup contentView = (ViewGroup) findViewById(R.id.root_layout);

        Toolbar actionBar = mRootView.findViewById(R.id.toolbar);
        if (actionBar != null) {
            mActionBarTitle = actionBar.findViewById(R.id.toolbarTitle);

            setSupportActionBar(actionBar);
            if (getSupportActionBar() != null) {
                // invalidate default, use custom
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayUseLogoEnabled(false);

                if (this instanceof BookActivity)
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        // suppose each activity that extends BaseActivity should need progress dialog
        // inflate progressDialog
        mProgressDialog = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.progress_dialog, mRootView, false);
        contentView.addView(mProgressDialog);
        mProgressDialog.setVisibility(ViewGroup.GONE);

        // invalidate touch on below views
        mProgressDialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    protected void setActionBarTitle(String title) {
        if (mActionBarTitle != null)
            mActionBarTitle.setText(title);
    }

    /**
     * Override to customize
     *
     * @param isNetworkAvailable true if network is available
     */
    protected void onConnectivityChanged(boolean isNetworkAvailable) {

    }

    /*******************
     * UTILITY METHODS *
     ******************/

    /**
     * @return the RootView
     */
    public ViewGroup getRootView() {
        return mRootView;
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*******************
     * PROGRESS DIALOG *
     ******************/

    /**
     * Display ProgressDialog
     */
    protected void showProgressDialog() {
        mProgressDialog.setVisibility(ViewGroup.VISIBLE);
        isProgressDialogVisible = true;
    }

    /**
     * Hide ProgressDialog
     */
    protected void hideProgressDialog() {
        if (isProgressDialogVisible) {
            mProgressDialog.setVisibility(ViewGroup.GONE);
            isProgressDialogVisible = false;
        }
    }

    class ConnectivityChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            onConnectivityChanged(DeviceUtils.isNetworkAvailable(BaseActivity.this));
        }
    }
}
