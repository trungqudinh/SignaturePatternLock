package hcmus.hcb.signaturepatternlock;

import android.content.Context;

import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DrawView extends View
{

    private long startTime;
    private long endTime;
    private Path drawPath;
    private Paint drawPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        startTime = Long.MAX_VALUE;
        endTime = 0;
        setupDrawing();
    }

    private void setupDrawing()
    {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        for (int i = 1; i < h; i++)
        {
            for (int j = 1; j < w; j++)
            {
                canvasBitmap.setPixel(j, i, 0xFFFFFFFF);
            }
        }
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(canvasBitmap, 0, 0, drawPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                if (System.currentTimeMillis() < startTime) startTime = System.currentTimeMillis();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                if (System.currentTimeMillis() > endTime) endTime = System.currentTimeMillis();
                invalidate();
                break;
            default:
                return false;
        }
        return true;
    }

    public void startNew()
    {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        for (int i = 1; i < canvasBitmap.getHeight(); i++)
        {
            for (int j = 1; j < canvasBitmap.getWidth(); j++)
            {
                canvasBitmap.setPixel(j, i, 0xFFFFFFFF);
            }
        }
        startTime = Long.MAX_VALUE;
        endTime = 0;
        invalidate();
    }

    public List<String> getBinnaryImage(String backgroundCharacter, String foregroundCharacter, float scalingPercent)
    {
        ArrayList<String> binaryImage = new ArrayList<>();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(canvasBitmap,(int)(canvasBitmap.getWidth()*scalingPercent), (int)(canvasBitmap.getHeight()*scalingPercent), true);
        for (int i = 1; i < resizedBitmap.getHeight(); i++)
        {
            String res = "";
            for (int j = 1; j < resizedBitmap.getWidth(); j++)
            {
                if (resizedBitmap.getPixel(j, i) == -1)
                    res += backgroundCharacter;
                else
                    res += foregroundCharacter;
            }
            binaryImage.add(res);
        }
        return binaryImage;
    }

    public List<String> getBinnaryImage(float scalingPercent)
    {
        return getBinnaryImage(" ", "1", scalingPercent);
    }

    public List<String> getBinnaryImage()
    {
        return getBinnaryImage(" ", "1", 1);
    }

    public void writeData()
    {

        Log.i("WRITING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>=====================");
        String filename = "signatureData.txt";
        String fileContents = "ahihi";
        FileOutputStream outputStream;
        Context ctx = getContext();
        try {
            outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("WRITING", "=====================<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}