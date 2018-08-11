package com.lavaira.chimera;


/**
 * Define the animation types here. If there are more types, please add it here.
 * Author: Lajesh Dineshkumar
 * Email: lajeshds2007@gmail.com
 * Created: 7/17/2017
 * Modified: 7/17/2017
 */
public enum AnimType {
    // Defines the animation xml for SLIDE_DOWN animation
    SLIDE_DOWN (R.anim.slide_down),

    // Defines the animation xml for BOUNCE_IN animation
    BOUNCE_IN(R.anim.bounce_in),

    BOUNCE_IN_DOWN(R.anim.bounce_in_down),

    // Defines the animation xml for BOUNCE_IN animation
    FADE_IN(R.anim.fade_in),

    // Defines the animation xml for BOUNCE_IN animation
    FADE_IN_DOWN(R.anim.fade_in_down),

    // Defines the animation xml for BOUNCE_IN animation
    FADE_IN_UP(R.anim.fade_in_up),

    // Defines the animation xml for BOUNCE_IN animation
    FADE_IN_LEFT(R.anim.fade_in_left),

    // Defines the animation xml for BOUNCE_IN animation
    FADE_IN_RIGHT(R.anim.fade_in_right),

    // Defines the animation xml for OVERSHOOT animation
    OVERSHOOT(R.anim.overshoot),

    // Defines the animation xml for ROTATE animation
    ROTATE_IN(R.anim.rotate_in),

    // Defines the animation xml for ALPHA animation
    ANIM_ALPHA(R.anim.anim_alpha),

    // Define the animation xml for FLIP animation
    ANIM_FLIP(R.anim.flip_in),

    // Define the animation xml for ZOOM animation
    ANIM_ZOOM(R.anim.zoom_in);


    // keep the animation id set
    private int animId;

    AnimType(int animId) {
        this.animId = animId;
    }

    /**
     * This method return the animation id
     * @return
     */
    public int getAnimId() {
        return this.animId;
    }


}
