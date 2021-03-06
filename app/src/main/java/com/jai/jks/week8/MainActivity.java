package com.jai.jks.week8;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class MainActivity extends AppCompatActivity  {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private ImageView mImageView;
    private Bitmap mback, mbill;
    private EditText mText;
    private String mString;


    private static final int REQUEST_WRITE_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mText = (EditText) findViewById(R.id.editText);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
        }}
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Error: Grant Permission First", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }




    public void btn(View v){

        int [] i = {
                R.drawable.bill_1, R.drawable.bill_2,
                R.drawable.bill_3, R.drawable.bill_4,
                R.drawable.bill_5, R.drawable.bill_6,
                R.drawable.bill_7, R.drawable.bill_8,
                R.drawable.bill_9, R.drawable.bill_10,
                R.drawable.bill_11, R.drawable.bill_12,
                R.drawable.bill_13, R.drawable.bill_14,
                R.drawable.bill_15, R.drawable.bill_16,
                R.drawable.bill_17, R.drawable.bill_18,
                R.drawable.bill_19, R.drawable.bill_20,

        };
        Random ran = new Random();
        int n = ran.nextInt(i.length);

        Toast.makeText(getApplicationContext(), "Tap again to change Bill", Toast.LENGTH_LONG).show();
        mString = mText.getText().toString();
        mback = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        //BitmapDrawable bd = (BitmapDrawable) getApplicationContext().getResources().getDrawable(i[n]);
        mbill = BitmapFactory.decodeResource(getResources(), i[n]);
        Bitmap resizedBill = Bitmap.createScaledBitmap(mbill,550,550, false);
        Bitmap resizedBack = Bitmap.createScaledBitmap(mback, 800, 800, false);

        mBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mCanvas.drawBitmap(resizedBack, 0, 0, null);

        //random file from array should point here for version 1.2
        mCanvas.drawBitmap(resizedBill, 300, 100, null);


        TextPaint mTextPaint=new TextPaint();
        DynamicLayout mTextLayout = new DynamicLayout(mString, mTextPaint, 115, Layout.Alignment.ALIGN_NORMAL, 1.0f, 28.0f, true);
        mTextPaint.setTextSize(45);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        Typeface plain = Typeface.createFromAsset(getAssets(), "fonts/dead_end.ttf");

        mTextPaint.setTypeface(plain);
        mTextPaint.setStyle(TextPaint.Style.FILL);

        mTextPaint.setColor(0xff191919);

        mCanvas.save();
        // calculate x and y position where your text will be placed

        int textX = 60;
        int textY = 95;

        mCanvas.translate(textX, textY);
        mTextLayout.draw(mCanvas);
        mCanvas.restore();


        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(mBitmap);


    }

    public void savnshare(View v){
        if (mBitmap == null){
            return;
        }
            File path = new File(Environment.getExternalStorageDirectory() + "/Bill");
            path.mkdirs();
            Random rand = new Random();
            int n = rand.nextInt(200);
            String filename = "bill_"+n+".png";
            File file = new File(path, filename);
            FileOutputStream stream;
            try{
                stream = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.close();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"errr try again...",Toast.LENGTH_SHORT).show();
            }
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent.createChooser(intent, "Share via...");
            startActivity(intent);


    }}



