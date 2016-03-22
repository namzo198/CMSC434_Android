package com.example.suleiman_namara.doodler;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;

import static android.support.v4.view.ViewCompat.setLayerType;

/**
 * Created by Suleiman_Namara on 3/22/2016.
 */
public class CustomDailog extends Dialog implements android.view.View.OnClickListener{

    Button accept,decline,mcolorButton;
    LobsterPicker lobsterPicker;
    LobsterOpacitySlider Opacity;
    int mcolor,mOpacity;
    DoodleView d;

    public CustomDailog(Context context,DoodleView d,Button colorbutton) {
        super(context);
        this.d = d;
        this.mcolorButton = colorbutton;
    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.color_picker);

    lobsterPicker = (LobsterPicker) findViewById(R.id.lobsterpicker);
    Opacity = (LobsterOpacitySlider) findViewById(R.id.opacityslider);
    accept = (Button)findViewById(R.id.btn_yes);
    decline = (Button)findViewById(R.id.btn_no);

   mcolor = lobsterPicker.getColor();
   lobsterPicker.addDecorator(Opacity);
    lobsterPicker.setColorHistoryEnabled(true);


    lobsterPicker.addOnColorListener(new OnColorListener() {
        @Override
        public void onColorChanged(@ColorInt int color) {
            mcolor = color;
        }

        @Override
        public void onColorSelected(@ColorInt int color) {
            mcolor = color;
        }
    });

    accept.setOnClickListener(this);
    decline.setOnClickListener(this);


}


    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btn_yes:
                d.mSetColor(mcolor);
                mcolorButton.setBackgroundColor(mcolor);
            case R.id.btn_no:
                dismiss();
            default:
                    break;
        }

        dismiss();

    }

    public int mgetColor() {
       // return mcolor;
       return lobsterPicker.getColor();
    }
}
