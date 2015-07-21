package com.meitu.scrawl.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.meitu.scrawl.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private Intent gotoIntent;
    private Button btnTextureScrawl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setOnClickListeners();
    }

    private void findViews() {
        mContext = this;
        btnTextureScrawl = (Button) findViewById(R.id.btn_texture);
    }

    private void setOnClickListeners() {
        btnTextureScrawl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (gotoIntent == null) {
            gotoIntent = new Intent();
        }
        switch (view.getId()) {
            case R.id.btn_texture:
                gotoIntent.setClass(mContext, TextureScrawlActivity.class);
                break;
        }
        startActivity(gotoIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; //
            Toast.makeText(this, getString(R.string.system_exit), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; //
                }
            }, 2000); //
        } else {
            finish();
            System.exit(0);
        }
    }
}