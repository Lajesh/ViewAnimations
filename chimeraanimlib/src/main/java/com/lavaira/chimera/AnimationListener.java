package com.lavaira.chimera;

import android.animation.Animator;
import android.util.Log;
import android.view.View;

/**
 * Animation listener which can be used if you want to synchronize the multiple animations
 * in a screen.
 * Author: Lajesh Dineshkumar
 * Email: lajeshds2007@gmail.com
 * Created: 8/10/18
 * Modified: 8/10/18
 */
public interface AnimationListener {

    String TAG = AnimationListener.class.getSimpleName();

    default void onAnimationStart(Animator animatorSet, View view){
        Log.v(TAG, animatorSet.getClass().getCanonicalName()+ ":" + view.getId() );
    }
    default void onAnimationEnd(Animator animatorSet, View view){
        Log.v(TAG, animatorSet.getClass().getCanonicalName()+ ":" + view.getId() );
    }
    default void onAnimationCancel(Animator animatorSet, View view){
        Log.v(TAG, animatorSet.getClass().getCanonicalName()+ ":" + view.getId() );
    }
    default void onAnimationRepeat(Animator animatorSet, View view){
        Log.v(TAG, animatorSet.getClass().getCanonicalName()+ ":" + view.getId() );
    }
}
