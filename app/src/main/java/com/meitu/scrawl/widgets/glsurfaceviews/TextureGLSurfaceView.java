package com.meitu.scrawl.widgets.glsurfaceviews;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.meitu.scrawl.domain.TextureRenderer;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class TextureGLSurfaceView extends GLSurfaceView {

    private Context mContext;
    private TextureRenderer mRenderer;

    public TextureGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextureGLSurfaceView(Context context) {
        super(context);
        initialRunnableContext(context);
    }

    private void initialRunnableContext(Context context) {
        this.mContext = context;
        mRenderer = new TextureRenderer(mContext);
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }
}