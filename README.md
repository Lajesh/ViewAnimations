
# Android View Animation Using Object Animators 

# Prerequisites

1. MIN SDK version should be greater than or equal to 21
2. Use Java-8 language features. So JAVA 8 Source compatability is required.

# Usage

For animating a single view

```ChimeraAnimator.origin(viewid).anim(this, AnimType.FADE_IN_LEFT).duration(500).start();```

For animating a viewgroup

```ChimeraAnimator.origin(viewgroupid).anim(MainActivity.this, AnimType.OVERSHOOT).duration(500).start();```



