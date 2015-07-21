package com.meitu.scrawl.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.meitu.scrawl.R;
import com.meitu.scrawl.widgets.glsurfaceviews.TextureGLSurfaceView;


public class TextureScrawlActivity extends Activity {

    private TextureGLSurfaceView mGLSurfaview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaview = new TextureGLSurfaceView(this);
        setContentView(mGLSurfaview);
    }
}
