package com.meitu.scrawl.widgets.brush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.meitu.scrawl.R;
import com.meitu.scrawl.utils.ShaderUtils;
import com.meitu.scrawl.utils.TextureUtils;

import java.io.IOException;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class Brush {

    private int mTextureId;
    private int mProgramId;
    private Context mContext;
    private Bitmap mBrushWithBitmap;

    public Brush(Context context){
        this.mContext = context;
        this.mTextureId = TextureUtils.generateTexture(mBrushWithBitmap);
        mBrushWithBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brush_bg);
        try {
            this.mProgramId = ShaderUtils.generateShaderProgram(mContext,"brush_vertices_shader.sl","brush_fragment_shader.sl");
            if(mProgramId == 0){
                throw new RuntimeException("initial Brush exception.please try again later or send email to zhaokangk@sina.com");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(){
        
    }
}