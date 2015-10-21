package com.tangguotupaopao.tgtpplabel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CharAutoLayout cal = new CharAutoLayout(this,614,210);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(40);
        cal.setBackgroundResId(R.drawable.label);
        cal.setPaddingTop(0.35f);
        cal.setPaddingBottom(0.1f);
        cal.setPaddingLeft(0.06f);
        cal.setPaddingRight(0.06f);
        cal.draw(p, "山不在高");
        boolean res = cal.saveBitmap(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.png");
        Log.i("TAG", "path " + Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.png"+" "+res);
        ImageView i = (ImageView)findViewById(R.id.imageView);
        i.setImageBitmap(cal.getBitmap());
    }
}
