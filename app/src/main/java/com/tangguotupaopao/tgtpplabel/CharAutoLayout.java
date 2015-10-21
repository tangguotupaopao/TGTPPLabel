package com.tangguotupaopao.tgtpplabel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by gelin on 15/10/20.
 */
public class CharAutoLayout {
    private float mWidth;
    private float mHeight;

    private float mSize;
    private float mPadding_Left = 0.05f;
    private float mPadding_Right = 0.05f;
    private float mPadding_Top = 0.05f;
    private float mPadding_Bottom = 0.05f;
    private int mBackgroundResId = -1;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;

    public CharAutoLayout(Context context, Bitmap bitmap){
        mContext = context;
        mBitmap = bitmap;
        createCanvas();
    }

    public CharAutoLayout(Context context, int width, int height){
        mContext = context;
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createCanvas();
    }

    private void createCanvas(){
        mCanvas = new Canvas(mBitmap);

    }

    public void draw(Paint paint, String str){
        calWH(mCanvas);
        mPaint = paint;
        drawBackground();
        mSize = paint.getTextSize()+1;
        Paint.FontMetrics font;
        int col,row;
        do {
            mSize--;
            mPaint.setTextSize(mSize);
            font = mPaint.getFontMetrics();
            col = (int) (mWidth / mSize);
            row = (int)((mHeight+font.leading)/(font.descent-font.ascent+font.leading));
        }while(col*row<str.length());
        mPaint.setTextSize(mSize);
        row = str.length() / col;
        int rm = str.length()%col;
        if(rm>0){
            row++;
        }
        float lineHeight = font.descent-font.ascent;
        float h = row*lineHeight+(row-1)*font.leading;
        float w = col*mSize;
        float startX = (mWidth-w)/2+mCanvas.getWidth()*mPadding_Left;
        float startY = (mHeight-h)/2-font.ascent+mCanvas.getHeight()*mPadding_Top;
        Log.i("TAG", "info "+startY+" "+mHeight+" "+mCanvas.getHeight()+" "+h+" "+font.ascent);
        for(int i=0; i<row-1; i++){
            mCanvas.drawText(str.substring(i*col,(i+1)*col),startX,startY,mPaint);
            startY += font.descent+font.leading-font.ascent;
        }

        if(rm>0){
            startX = (int)(mWidth-rm*mSize)/2+mCanvas.getWidth()*mPadding_Left;
        }
        mCanvas.drawText(str.substring((row-1)*col),startX,startY, mPaint);
    }

    public boolean saveBitmap(String path){
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            return f.exists();
        }
    }

    private void drawBackground(){
        if(mBackgroundResId==-1){
            mCanvas.drawColor(Color.WHITE);
        }
        else{
            Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), mBackgroundResId);
            b = Bitmap.createScaledBitmap(b, mCanvas.getWidth(), mCanvas.getHeight(), false);
            mCanvas.drawBitmap(b,0,0,mPaint);
            b.recycle();
        }
    }

    private void calWH(Canvas canvas){
        mWidth = canvas.getWidth()*(1-mPadding_Left-mPadding_Right);
        mHeight = canvas.getHeight()*(1-mPadding_Top-mPadding_Bottom);
    }

    public void setBackgroundResId(int id){
        mBackgroundResId = id;
    }

    public void setPaddingLeft(float rate){
        mPadding_Left = rate;
    }

    public void setPaddingRight(float rate){
        mPadding_Right = rate;
    }

    public void setPaddingTop(float rate){
        mPadding_Top = rate;
    }

    public void setPaddingBottom(float rate){
        mPadding_Bottom = rate;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    public Canvas getCanvas(){
        return mCanvas;
    }
}
