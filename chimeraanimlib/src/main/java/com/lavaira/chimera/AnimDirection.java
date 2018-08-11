package com.lavaira.chimera;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This interface keeps the possible animation directions.
 * Author: Lajesh Dineshkumar
 * Email: lajeshds2007@gmail.com
 * Created: 7/17/2017
 * Modified: 7/17/2017
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({AnimDirection.FORWARD, AnimDirection.BACKWARD, AnimDirection.RANDOM})
@interface AnimDirection {
     int FORWARD = 0;
     int BACKWARD = 1;
     int RANDOM = 2;
}
