package com.lavaira.animationsample;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lavaira.chimera.AnimType;
import com.lavaira.chimera.ChimeraAnimator;

public class MainActivity extends AppCompatActivity {

    private TextView helloTv;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloTv = (TextView) findViewById(R.id.helloTv);
        layout = (LinearLayout) findViewById(R.id.layoutGroup) ;


        ChimeraAnimator.origin(helloTv).anim(this, AnimType.FADE_IN_LEFT).duration(500).start();
        ChimeraAnimator.origin(layout).anim(MainActivity.this, AnimType.OVERSHOOT).duration(500).start();

    }
}
