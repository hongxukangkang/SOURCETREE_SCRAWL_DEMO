package com.meitu.scrawl.widgets.brush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.util.Log;

import com.meitu.scrawl.R;
import com.meitu.scrawl.utils.BufferUtils;
import com.meitu.scrawl.utils.ConfigParameters.*;
import com.meitu.scrawl.utils.DemoData;
import com.meitu.scrawl.utils.ImageUtils;
import com.meitu.scrawl.utils.ShaderUtils;

import java.nio.FloatBuffer;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class TextureDistrict {

    private static final String TAG = TextureDistrict.class.getSimpleName();
    private Context mContext;

    private float[] textureCord = {
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            0.0f, 1.0f
    };

    private float[] vertices = {};

    private int samplerHandle;
    private int positionHandle;
    private int textureCordHandle;
    private Bitmap mBitmap;

    public TextureDistrict(Context context, int textureId) {
        this.mContext = context;
        this.mTextureId = textureId;
        verticesBuffer = BufferUtils.getBufferFromFloatArray(DemoData.vertices);
        textureCordBuffer = BufferUtils.getBufferFromFloatArray(textureCord);
        try {
            mProgramId = ShaderUtils.generateShaderProgram(context, "brush_vertices_shader.sl", "brush_fragment_shader.sl");
            samplerHandle = GLES20.glGetUniformLocation(mProgramId, ShaderConfig.UNIFORM_SAMPLER);
            positionHandle = GLES20.glGetAttribLocation(mProgramId, ShaderConfig.ATTRIBUTE_POSITION);
            textureCordHandle = GLES20.glGetAttribLocation(mProgramId, ShaderConfig.ATTRIBUTE_TEXTURECORD);
            Log.i(TAG, "mProgramId:" + mProgramId);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "generate program exception...");
        }
    }

    private int mTextureId;
    private int mProgramId;
    private RectF mImgDomain;
    private FloatBuffer verticesBuffer;
    private FloatBuffer textureCordBuffer;

    public void setImageDomain(RectF domain) {
        this.mImgDomain = domain;
        updateVerticesFloatBuffer();
    }

    private void updateVerticesFloatBuffer() {

        vertices = new float[8];

        vertices[0] = mImgDomain.right;
        vertices[1] = mImgDomain.bottom;

        vertices[2] = mImgDomain.right;
        vertices[3] = mImgDomain.top;

        vertices[4] = mImgDomain.left;
        vertices[5] = mImgDomain.bottom;

        vertices[6] = mImgDomain.left;
        vertices[7] = mImgDomain.top;

        verticesBuffer = BufferUtils.getBufferFromFloatArray(vertices);
    }

    public void draw() {
        GLES20.glUseProgram(mProgramId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE0, mTextureId);
        GLES20.glEnableVertexAttribArray(textureCordHandle);
        GLES20.glVertexAttribPointer(textureCordHandle, 2, GLES20.GL_FLOAT, false, 0, textureCordBuffer);

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, verticesBuffer);

        GLES20.glUniform1f(GLES20.GL_TEXTURE0, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE0, 0);
    }
}