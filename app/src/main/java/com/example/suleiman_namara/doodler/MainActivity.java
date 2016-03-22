package com.example.suleiman_namara.doodler;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.LinkAddress;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import static android.support.v4.view.ViewCompat.setLayerType;

public class MainActivity extends Activity {
    DoodleView mdoodle, doodleid;
    LinearLayout mlayout;
    Button clearbutton,redobutton,undobutton,colorbutton;
    CustomDailog colorpick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SeekBar mseek = (SeekBar)findViewById(R.id.seekBar);
         clearbutton = (Button)findViewById(R.id.clearbutton);
         redobutton = (Button)findViewById(R.id.redobutton);
         undobutton = (Button)findViewById(R.id.undobutton);
        colorbutton = (Button)findViewById(R.id.colorbutton);
         doodleid = (DoodleView)findViewById(R.id.doodleview);
        colorbutton.setBackgroundColor(Color.BLACK);
        
        //Initiate Doodle class
        mdoodle = new DoodleView(MainActivity.this);
        //TO save Canvas drawing as Bitmap

        final View content = doodleid;
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        //Attach to main Layout.
        mlayout =(LinearLayout)findViewById(R.id.Linear);



        mseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int mprogress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mprogress = progress;
                doodleid.mSetStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Brush Size:" + mprogress, Toast.LENGTH_SHORT).show();
            }
        });

        //Color picker
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                colorpick = new CustomDailog(MainActivity.this,doodleid,colorbutton);
                colorpick.setTitle(R.string.color_picker);
                colorpick.show();

            }
        });





        undobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleid.Undo();
            }
        });
        //Clear the DoodleView
        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the doodleview based on its id
                doodleid.clearCanvas();

            }
        });

        //Save the DoodleView
        redobutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doodleid.Redo();
            }
        });
    }
}
