package com.meitu.scrawl.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.meitu.scrawl.R;
import com.meitu.scrawl.utils.ConfigParameters.*;
import com.meitu.scrawl.utils.ImageUtils;
import com.meitu.scrawl.utils.TextureUtils;
import com.meitu.scrawl.widgets.brush.Brush;
import com.meitu.scrawl.widgets.brush.TextureDistrict;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class TextureRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private int[] textureId;
    private int[] frameBufferId;
    private Bitmap mBitmap;

    private Brush mBrush;
    private TextureDistrict bgDistrict;//背景图


    public TextureRenderer(Context context) {
        this.mContext = context;
        initialParameters(context);
    }

    private void initialParameters(Context context) {
        textureId = new int[2];
        frameBufferId = new int[1];
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_pomelo_width);
        mBitmap = ImageUtils.reverseBitmap(mBitmap, 1);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(TextureGLSurfaceViewConfig.COLOR_R, TextureGLSurfaceViewConfig.COLOR_G, TextureGLSurfaceViewConfig.COLOR_B, TextureGLSurfaceViewConfig.COLOR_A);
        textureId[0] = TextureUtils.generateTexture(mBitmap);
        frameBufferId[0] = TextureUtils.generateFrameBuffer(mBitmap, textureId[0]);
//        int redColor = mContext.getResources().getColor(R.color.red);
//        textureId[0] = TextureUtils.generateColorTexture(mBitmap,redColor);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
//        GLES20.glViewport(100, 110, 500, 500);
        GLES20.glViewport(0, 0, width, height);
        RectF domain = ImageUtils.getImageDomain(mBitmap, width, height);
        bgDistrict = new TextureDistrict(mContext, textureId[0]);
        bgDistrict.setImageDomain(domain);
        mBrush = new Brush(mContext);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBufferId[0]);
        mBrush.draw();
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        offlineScreenRenderer();
    }

    private void offlineScreenRenderer() {
        bgDistrict.draw();
    }
}
