package com.meitu.scrawl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class ImageUtils {
    /**
     * 图片反转
     *
     * @param bm
     * @param flag:0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
        }
        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return null;
    }

    public static RectF getImageDomain(Bitmap mBitmap, int width, int height) {
        if (mBitmap == null) {
            return null;
        }
        RectF rect = new RectF();

        float glHalfWidth = 1.0f;
        float glHalfHeight = 1.0f;

        int mCurrentBitmapWidth = mBitmap.getWidth();
        int mCurrentBitmapHeight = mBitmap.getHeight();

        float imageAspect = mCurrentBitmapWidth / (float) mCurrentBitmapHeight;
        float renderBufferAspect = width / (float) height;

        if (imageAspect > renderBufferAspect) {
            glHalfWidth = Math.min(1.0f, mCurrentBitmapWidth / (float) width);
            glHalfHeight = glHalfWidth * renderBufferAspect / imageAspect;
        } else {
            glHalfHeight = Math.min(1.0f, mCurrentBitmapHeight / (float) height);
            glHalfWidth = imageAspect / renderBufferAspect;
        }
        rect.set(-glHalfWidth, glHalfHeight, glHalfWidth, -glHalfHeight);
        return rect;
    }
}