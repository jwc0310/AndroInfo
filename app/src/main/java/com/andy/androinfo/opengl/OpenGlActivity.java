package com.andy.androinfo.opengl;

import android.os.Bundle;
import com.andy.androinfo.uis.AndyBaseActivity;

public class OpenGlActivity extends AndyBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoGLSurfaceView glView = new DemoGLSurfaceView(this);
        setContentView(glView);
    }

}
