package app.test.com.testapp.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

import app.test.com.testapp.R;

/**
 * Utility class that provides some useful methods to make animations
 *
 * @author omar.brugna
 */
public class Animations {

    public static final float ALPHA_ENABLED = 1.0f;
    public static final float ALPHA_DISABLED = 0.5f;
    public static final float ALPHA_PRESSED = 0.5f;

    private static final String ANIM_ROTATION_Y = "rotationY";
    private static final String ANIM_ALPHA = "alpha";
    private static final String ANIM_SCALE_X = "scaleX";
    private static final String ANIM_SCALE_Y = "scaleY";

    private void startAnimation(Context context, ValueAnimator valueAnimator, Integer animationLength) {

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });

        valueAnimator.setDuration(animationLength != null ? animationLength : context.getResources().getInteger(R.integer.default_animation_duration_long));
        valueAnimator.start();
    }

    private void startAnimation(Context context, AnimatorSet animatorSet, Integer animationLength) {

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.setDuration(animationLength != null ? animationLength : context.getResources().getInteger(R.integer.default_animation_duration_long));
        animatorSet.start();
    }

    public void fadeIn(Context context, final View view, final AnimationListener listener) {
        fadeIn(context, view, listener, null);
    }

    /**
     * Vertical scale in animation for View
     *
     * @param context  an instance of {@link Context}
     * @param view     the {@link View} to fade in
     * @param listener an instance of {@link AnimationListener}
     */
    public void fadeIn(Context context, final View view,
                       final AnimationListener listener,
                       Integer animationLength) {

        view.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(view, ANIM_ALPHA, 0.0f, 1.0f);
        startAnimation(context, valueAnimator, animationLength);
    }

    public void fadeOut(Context context, final View view, final int visibility,
                        boolean startWithDelay, final AnimationListener listener) {
        fadeOut(context, view, visibility, startWithDelay, listener, null);
    }

    /**
     * @param context        an instance of {@link Context}
     * @param view           the {@link View} to scale out
     * @param visibility     visibility value to set after animation
     * @param startWithDelay startWithDelay if true, animation will start with delay
     * @param listener       an instance of {@link AnimationListener}
     */
    private void fadeOut(Context context, final View view, final int visibility,
                         boolean startWithDelay, final AnimationListener listener,
                         Integer animationLength) {

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(view, ANIM_ALPHA,
                1.0f, 0.0f);

        if (startWithDelay)
            valueAnimator.setStartDelay(context.getResources().getInteger(R.integer.default_animation_offset_long));

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null)
                    listener.onAnimationStart(Animations.this);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(visibility);
                if (listener != null)
                    listener.onAnimationEnd(Animations.this);
            }
        });

        startAnimation(context, valueAnimator, animationLength);
    }

    /**
     * Horizontal rotation in animation for View
     *
     * @param context  an instance of {@link Context}
     * @param view     the {@link View} to fade in
     * @param listener an instance of {@link AnimationListener}
     */
    public void rotateX(Context context, final View view,
                        final AnimationListener listener,
                        Integer animationLength) {

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(view, ANIM_ROTATION_Y, 0.0f, 360f);

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!(view.getVisibility() == View.VISIBLE))
                    view.setVisibility(View.VISIBLE);
                if (listener != null)
                    listener.onAnimationStart(Animations.this);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null)
                    listener.onAnimationEnd(Animations.this);
            }
        });

        startAnimation(context, valueAnimator, animationLength);
    }

    /**
     * Scale in animation for View
     *
     * @param context  an instance of {@link Context}
     * @param view     the {@link View} to scale in
     * @param listener an instance of {@link AnimationListener}
     */
    public void zoom(Context context, final View view, @Nullable final AnimationListener listener, Integer animationLength, boolean zoomIn) {

        ValueAnimator scaleDownX = ObjectAnimator.ofFloat(view, ANIM_SCALE_Y, zoomIn ? 0.95f : 1.0f);
        ValueAnimator scaleDownY = ObjectAnimator.ofFloat(view, ANIM_SCALE_X, zoomIn ? 0.95f : 1.0f);
        ValueAnimator alpha = zoomIn ? ObjectAnimator.ofFloat(view, ANIM_ALPHA, 1.0f, ALPHA_PRESSED) : ObjectAnimator.ofFloat(view, ANIM_ALPHA, ALPHA_PRESSED, 1.0f);

        ViewHelper.setPivotY(view, view.getHeight() / 2);
        ViewHelper.setPivotX(view, view.getWidth() / 2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleDownX).with(scaleDownY).with(alpha);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null)
                    listener.onAnimationStart(Animations.this);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null)
                    listener.onAnimationEnd(Animations.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        startAnimation(context, animatorSet, animationLength);
    }

    interface AnimationListener {

        void onAnimationStart(Animations animation);

        void onAnimationEnd(Animations animation);
    }
}
