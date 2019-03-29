package com.andy.androinfo.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class DemoGLSurfaceView extends GLSurfaceView {

    private DemoRender render;

    public DemoGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        render = new DemoRender();
        setRenderer(render);
    }

    public DemoGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
