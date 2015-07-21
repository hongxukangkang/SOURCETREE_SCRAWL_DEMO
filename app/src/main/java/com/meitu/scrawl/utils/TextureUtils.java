package com.meitu.scrawl.utils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class TextureUtils {

    private static final String TAG = TextureUtils.class.getSimpleName();

    public static int generateTexture(Bitmap bitmap) {
        int buffer[] = new int[1];
        if (bitmap == null) {
            return buffer[0];
        } else {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            IntBuffer ib = getBitmapBuffer(bitmap);
            GLES20.glGenTextures(1, buffer, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, buffer[0]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
//            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            Log.i(TAG, "buffer[0]:" + buffer[0]);
            return buffer[0];
        }
    }

    public static int generateColorTexture(Bitmap bitmap, int colorValue) {
        int buffer[] = new int[1];
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        IntBuffer ib = getBitmapBuffer(bitmap);
        ByteBuffer bb = ByteBuffer.allocateDirect(width * height * 4);
        IntBuffer ib = bb.asIntBuffer();
        for (int i = 0; i < width * height; i++) {
            ib.put(colorValue);
        }
        GLES20.glGenTextures(1, buffer, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, buffer[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
//            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        Log.i(TAG, "buffer[0]:" + buffer[0]);
        return buffer[0];
    }

    public static int generateFrameBuffer(Bitmap bitmap, int texureId) {
        int frameBuffer[] = new int[1];
        int renderBuffer[] = new int[1];
        int textureWidth = bitmap.getWidth();
        int textureHeight = bitmap.getHeight();
        GLES20.glGenRenderbuffers(1, renderBuffer, 0);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, renderBuffer[0]);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT, textureWidth, textureHeight);

        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0]);
        //指定FBO与对应的纹理ID绑定到一起
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texureId, 0);
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, renderBuffer[0]);
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.i(TAG, "CHECK true");
        } else {
            Log.i(TAG, "CHECK false");
            return 0;
        }
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        return frameBuffer[0];
    }

    private static IntBuffer getBitmapBuffer(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pixels[] = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        IntBuffer result = ByteBuffer.allocateDirect(pixels.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(pixels).position(0);
        return result;
    }

    public static int generateBrushTexture(Bitmap bitmap) {
        int buffer[] = new int[1];
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        IntBuffer ib = getBitmapBuffer(bitmap);
        GLES20.glGenTextures(1, buffer, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, buffer[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        return buffer[0];
    }
}