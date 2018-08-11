package com.lavaira.chimera;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The animator class which is responsible for performing most of the view animations.
 * By default the library supports the default animations like FADE, SLIDE, SCALE, etc.
 * Even the consumers can create there on objectanimators from outside and animate the views/viewbgroups.
 *
 * Using this library either the users can animate the viewgroups or even independant views.
 *
 * Author: Lajesh Dineshkumar
 * Email: lajeshds2007@gmail.com
 * Created: 8/10/17
 * Modified: 8/10/17
 */
public class ChimeraAnimator {
    private List<View> viewList = new ArrayList<>();
    private final int startOffset;
    private final int duration;
    private final int delay;
    private final int direction;
    private final Context context;
    private boolean viewGroupAsOne;
    private boolean setAplha;
    private final int animId;
    private AnimationListener animationCallBack;
    private ObjectAnimator defaultObjectAnimator;
    private final AnimType anim;



    private ChimeraAnimator(Builder builder) {
        this.startOffset = builder.startOffset;
        this.duration = builder.duration;
        this.delay = builder.delay;
        this.direction = builder.direction;
        this.context = builder.context;
        this.animId = builder.animId;
        this.viewGroupAsOne = builder.viewGroupAsOne;
        this.animationCallBack = builder.animationListener;
        this.defaultObjectAnimator = builder.objectAnimator;
        this.setAplha = builder.setAlpha;
        this.anim = builder.anim;
        if (builder.vg != null)
            fetchChildLayouts(builder.vg);
        else
            fetchView(builder.view);

        arrangeLayouts(viewList);
        setAnimation();
    }


    /**
     * Prepare the layout here for animations.
     * This class defines the duration, offset values, flow direction, etc.
     */
    public static class Builder {
        private static final int DEFAULT_OFFSET = 100;
        private static final int DEFAULT_DURATION = 500;

        private ViewGroup vg;
        private View view;
        private int startOffset = DEFAULT_OFFSET;
        private int duration = DEFAULT_DURATION;
        private int delay = DEFAULT_DURATION;
        private int direction = AnimDirection.FORWARD;
        private Context context;
        private int animId;
        private boolean viewGroupAsOne;
        private boolean setAlpha;
        private AnimType anim;
        private AnimationListener animationListener;
        private ObjectAnimator objectAnimator;


        Builder(ViewGroup vg) {
            this.vg = vg;
        }

        Builder(View view) {
            this.view = view;
        }

        public Builder offset(int offset) {
            this.startOffset = offset;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder flow(@AnimDirection int direction) {
            this.direction = direction;
            return this;
        }

        public Builder anim(Context context, int animId) {
            this.context = context;
            this.animId = animId;
            return this;
        }

        public Builder anim(Context context, AnimType anim) {
            this.context = context;
            this.anim = anim;
            return this;
        }

        public Builder setListener(AnimationListener callback) {
            this.animationListener = callback;
            return this;
        }


        public ChimeraAnimator start() {
            return new ChimeraAnimator(this);
        }

        public ObjectAnimator getObjectAnimator() {
            return objectAnimator;
        }

        public Builder setObjectAnimator(ObjectAnimator objectAnimator) {
            this.objectAnimator = objectAnimator;
            return this;
        }

        public boolean isViewGroupAsOne() {
            return viewGroupAsOne;
        }

        public Builder setViewGroupAsOne(boolean viewGroupAsOne) {
            this.viewGroupAsOne = viewGroupAsOne;
            return this;
        }

        public Builder setAlpha(boolean setAlpha) {
            this.setAlpha = setAlpha;
            return this;
        }
    }

    /**
     * Sets the root layout here. Suppose if you want to animate all the controls
     * inside your viewgroup, then set that viewgroup as the origin
     *
     * @param vg
     * @return
     */
    public static Builder origin(ViewGroup vg) {
        return new Builder(vg);
    }

    public static Builder origin(View view) {
        return new Builder(view);
    }


    /**
     * This method fetches all the child layout and adds it to a list
     *
     * @param viewGroup - container view
     */
    private void fetchChildLayouts(ViewGroup viewGroup) {
        if (!viewGroupAsOne) {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof ViewGroup) {
                    fetchChildLayouts((ViewGroup) view);
                } else {
                    view.setVisibility(View.INVISIBLE);
                    viewList.add(view);
                }
            }
        } else {
            viewList.add(viewGroup);
        }
    }


    /**
     * This method fetches all the child layout and adds it to a list
     *
     * @param view - container view
     */
    private void fetchView(View view) {
        viewList.add(view);
    }


    /**
     * This method arranges the layout elements based on the animation direction being set.
     *
     * @param viewList -list containing all the child views
     * @return modified view list based on the direction
     */
    private List<View> arrangeLayouts(List<View> viewList) {
        switch (direction) {
            case AnimDirection.BACKWARD:
                Collections.reverse(viewList);
                break;
            case AnimDirection.RANDOM:
                Collections.shuffle(viewList);
                break;
            default:
                break;
        }
        return viewList;
    }

    private void setAnimation() {
        int count = viewList.size();
        // Iterate through the views and apply animation to each view
        for (int i = 0; i < count; i++) {

            // Get the view based on index
            final View view = viewList.get(i);

            // Calculate the offset and reset the animation if there is already
            final int offset = i * startOffset;
            resetAnimation(view);

            // Prepares the animator list for each view, first we will be adding fadein/out animation
            List<Animator> animatorList = new ArrayList<>();
            animatorList.add(getStartObjectAnimator(offset, view));

            // Add the animator based on the animation id set from outside
            if (animId != 0) {
                animatorList.add(getResAnimator(context, animId, view));
            } else if(anim != null){
                animatorList.add(getResAnimator(context, anim.getAnimId(), view));
            }
            // Add default animator if set from outside
            if (null != defaultObjectAnimator) {
                animatorList.add(defaultObjectAnimator);
            }
            // Add Alpha animator if set from outside
            if (setAplha) {
                animatorList.add(ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1));
            }

            // Play the animator list all together
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animatorList);
            set.setDuration(duration);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if (null != animationCallBack)
                        animationCallBack.onAnimationStart(animator, view);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (null != animationCallBack)
                        animationCallBack.onAnimationEnd(animator, view);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    if (null != animationCallBack)
                        animationCallBack.onAnimationCancel(animator, view);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    if (null != animationCallBack)
                        animationCallBack.onAnimationRepeat(animator, view);
                }
            });
            if (delay == 0) {
                set.setStartDelay((long) i * startOffset);
            } else if (i == 0) {
                set.setStartDelay(delay);
            } else {
                set.setStartDelay((long) (i * startOffset) + delay);
            }
            set.start();
        }
    }

    /**
     * This method resets all the animation
     *
     * @param view - the view for which we need to reset the animation
     */
    private void resetAnimation(View view) {
        view.setAlpha(1);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setTranslationX(0);
        view.setTranslationY(0);
        view.setRotation(0);
        view.setRotationX(0);
        view.setRotationY(0);
    }

    /**
     * The object animator for fadin/out based on the predefined offset values
     *
     * @param offset - offset value
     * @param view   - The object whose property is to be animated
     * @return the animator
     */
    private ObjectAnimator getStartObjectAnimator(int offset, final View view) {
        ObjectAnimator ob = ObjectAnimator.ofFloat(view, View.ALPHA, 1, 1);
        ob.setDuration(1).setStartDelay(offset);
        ob.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator anim) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator anim) {
                // Nothing to go here
            }

            @Override
            public void onAnimationEnd(Animator anim) {
                // Nothing to go here
            }

            @Override
            public void onAnimationCancel(Animator anim) {
                // Nothing to go here
            }
        });
        return ob;
    }


    /**
     * This method returns the animation xml based on the animation id
     *
     * @param context - application context
     * @param animId  - animation id set by the user
     * @param view    - View to be animated
     * @return Animator
     */
    private Animator getResAnimator(Context context, int animId, View view) {
        Animator animator = AnimatorInflater.loadAnimator(context, animId);
        animator.setTarget(view);
        return animator;
    }

}
