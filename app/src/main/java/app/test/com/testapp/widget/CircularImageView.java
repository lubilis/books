package app.test.com.testapp.widget;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.IOException;

import app.test.com.testapp.R;
import app.test.com.testapp.utils.Animations;
import app.test.com.testapp.utils.Logger;
import app.test.com.testapp.utils.VersionUtils;

/**
 * Custom circular ImageView
 *
 * @author omar.brugna
 */
public class CircularImageView extends com.pkmmte.view.CircularImageView {

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshBitmapShader() {
        try {
            super.refreshBitmapShader();
        } catch (Exception ignored) {

        }
    }

    public void clearImage() {
        try {
            // prevent to showing previous content in recycled views,
            // this code is in a try catch since {@link CircularImageView} cause a NullPointerException
            setImageDrawable(null);
        } catch (NullPointerException ignored) {

        }
    }

    public void displayImage(final String url, final boolean enableAnimation, boolean clearCache) {
        clearImage();

        if (clearCache) {
            MemoryCacheUtils.removeFromCache(url, ImageLoader.getInstance().getMemoryCache());
            ImageLoader.getInstance().getDiskCache().remove(url);
            ImageLoader.getInstance().getMemoryCache().remove(url);
        }

        ImageLoader.getInstance().displayImage(url, this, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // here we could show something like a loader..
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                // here we need to hide loader if exists..

                try {
                    //noinspection ThrowableResultOfMethodCallIgnored
                    if (failReason != null && failReason.getCause() != null && failReason.getCause().toString().contains("NotFound")) {
                        // avoid reloading every time, save in cache a default image
                        ImageLoader.getInstance().getDiskCache().save(url, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                    }
                } catch (IOException e) {
                    Logger.e("Failed saving local profile image in cache");
                }
            }

            @SuppressLint("NewApi")
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // here we need to hide loader if exists..

                if (enableAnimation) {
                    if (VersionUtils.hasLollipop() && isAttachedToWindow()) {
                        int startX = getWidth() / 2;
                        int startY = getHeight() / 2;
                        int finalRadius = Math.max(startX, startY);

                        Animator anim = ViewAnimationUtils.createCircularReveal(CircularImageView.this, startX, startY, 0, finalRadius);
                        anim.setDuration(getResources().getInteger(R.integer.default_animation_duration_short));
                        anim.start();
                    } else
                        (new Animations()).fadeIn(getContext(), CircularImageView.this, null,
                                getResources().getInteger(R.integer.default_animation_duration_short));
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // here we need to hide loader if exists..
            }
        });
    }
}
