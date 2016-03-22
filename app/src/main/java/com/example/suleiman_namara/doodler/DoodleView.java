package com.example.suleiman_namara.doodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Suleiman_Namara on 3/4/2016.
 */
public class DoodleView extends View {

    private Paint mpaintDoodle = new Paint();
    private Path mpath;
    private LinkedHashMap<Path,Float> array = new LinkedHashMap<Path,Float>();
    private LinkedHashMap<Path,Integer> colorarray = new LinkedHashMap<Path,Integer>();
    private ArrayList<Path>paths = new ArrayList<Path>();
   private ArrayList<Path>undo = new ArrayList<Path>();
    private ArrayList<Float>_size = new ArrayList<Float>();
    private ArrayList<Integer>_color = new ArrayList<Integer>();
    private  float msize;
    private int mcolor;
    public Bitmap bitmap;

    public Canvas mCanvas;
    private boolean delete = false;
    //constructor
    public DoodleView(Context context) {
        super(context);
        init(null, 0);
        msize = 0;
        mpath = new Path();
    }

    //2/3 View constructors
    public DoodleView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        mpath = new  Path();

    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
        init(attrs, defStyle);
        mpath = new Path();
    }

    private void init (AttributeSet attrs, int defStyle){
       // mpaintDoodle.setColor(Color.RED);
        mcolor = Color.BLACK;
        mpaintDoodle.setAntiAlias(true);
        mpaintDoodle.setStyle(Paint.Style.STROKE);
        mpath = new Path();
    }


    public void mSetStrokeWidth(float size){
        msize = size;
    }

    public void mSetColor(int color){
        mcolor = color;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       for(Path _paths: array.keySet())
       {   mpaintDoodle.setColor(colorarray.get(_paths));
           mpaintDoodle.setStrokeWidth(array.get(_paths));
           canvas.drawPath(_paths,mpaintDoodle);
       }
        mpaintDoodle.setColor(mcolor);
        mpaintDoodle.setStrokeWidth(msize);

        canvas.drawPath(mpath, mpaintDoodle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //undo.clear();
                mpath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                undo.clear();
                mpath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                array.put(mpath, msize);
                colorarray.put(mpath,mcolor);
                mpath = new Path();
                break;
        }
        invalidate();
        return true;
    }

    //clear button function
    public void clearCanvas() {
        array.clear();
        invalidate();

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void Undo() {
        if(array.size()>0) {
            List<Path> list = new ArrayList<Path>(array.keySet());

            _size.add(array.get(list.get(list.size()-1)));
            array.remove(list.get(list.size() - 1));
            undo.add(list.get(list.size()-1));
            invalidate();

        }else {
            Toast.makeText(getContext(),"NOTHING ELSE TO UNDO",Toast.LENGTH_SHORT).show();
        }
    }

    public void Redo() {
        if(undo.size() > 0) {

            array.put(undo.get(undo.size() - 1),_size.get(_size.size() - 1));
            undo.remove(undo.size()-1);
            _size.remove(_size.size()-1);
            invalidate();

        }else {
            Toast.makeText(getContext(),"NOTHING ELSE TO REDO",Toast.LENGTH_SHORT).show();
        }
    }

}
