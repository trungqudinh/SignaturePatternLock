package hcmus.hcb.signaturepatternlock;

import android.content.Context;

import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DrawView extends View
{
    class Point
    {
        private float x, y;
        private long time;
        private double degree;

        public Point(float x, float y)
        {
            this.x = x;
            this.y = y;
        }

        public float getX()
        {
            return x;
        }

        public void setX(float x)
        {
            this.x = x;
        }

        public float getY()
        {
            return y;
        }

        public void setY(float y)
        {
            this.y = y;
        }

        public long getTime()
        {
            return time;
        }

        public void setTime(long time)
        {
            this.time = time;
        }

        public double getDegree()
        {
            return degree;
        }

        public void setDegree(double degree)
        {
            this.degree = degree;
        }

        @Override
        public String toString()
        {
            return x + "-" + y + "-" + degree;
        }
    }

    private enum AngularMeasurementUnit
    {
        Radian,
        Degree
    }

    private long startTime;
    private Path drawPath;
    private Paint drawPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float lastX, lastY;
    private Point[] lastPoints = {null, null, null, null};
    public ArrayList<Point> markedPoints;
    public ArrayList<Point> points;

    public Bitmap getCanvasBitmap()
    {
        return canvasBitmap;
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //startTime = Long.MAX_VALUE;
        //endTime = 0;
        setupDrawing();
    }
    public DrawView(Context context)
    {
        super(context);
        //startTime = Long.MAX_VALUE;
        //endTime = 0;
        //setupDrawing();
        setupDrawing();
        Log.d("DrawView", "Ong noi may ne !!!!!");
    }
    private void setupDrawing()
    {
        Log.d("DrawView", "setting up drawing");
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(0xFF000000);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        Log.d("DrawView", "Instantiate marked points");
        markedPoints = new ArrayList<>();

        if (markedPoints == null)
        {
            Log.d("DrawView", "oh shit, marked point is NULL");
        }
        points = new ArrayList<>();
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

    private void onTouchActionDown(Point currentPoint)
    {
        if (startTime == 0)
        {
            startTime = System.currentTimeMillis();
            currentPoint.setTime(0);
        }
        else
        {
            currentPoint.setTime(System.currentTimeMillis() - startTime);
        }
        drawPath.reset();
        drawPath.moveTo(currentPoint.getX(), currentPoint.getY());
        currentPoint.setDegree(0);
        markedPoints.add(currentPoint);
        lastPoints[2] = lastPoints[3] = lastPoints[1] = currentPoint;
        lastX = currentPoint.getX();
        lastY = currentPoint.getY();
        invalidate();
    }

    private void onTouchActionMove(Point currentPoint)
    {
        currentPoint.setTime(System.currentTimeMillis() - startTime);
        Point ptemp = lastPoints[2];
        lastPoints[2] = lastPoints[3];
        lastPoints[3] = currentPoint;
        drawPath.quadTo(lastX, lastY, (currentPoint.getX() + lastX) / 2, (currentPoint.getY() + lastY) / 2);
        lastX = currentPoint.getX();
        lastY = currentPoint.getY();

        double angleDegree = getDegree(AngularMeasurementUnit.Degree, lastPoints[1], lastPoints[2], lastPoints[3]);
        // get point which has the angle < cachingDegree Degree
        int filterDegree = 160;
        if (angleDegree < filterDegree)
        {
            lastPoints[2].setDegree(angleDegree);
            lastPoints[2].setTime(System.currentTimeMillis() - startTime);
            if (lastPoints[2].getTime() < 3000L)
            {
                markedPoints.add(lastPoints[2]);
            }
            lastPoints[1] = ptemp;
        }
        invalidate();
    }

    private void onTouchActionUp(Point currentPoint)
    {
        currentPoint.setTime(System.currentTimeMillis() - startTime);
        currentPoint.setDegree(0);
        markedPoints.add(currentPoint);
        drawPath.lineTo(lastX, lastY);
        drawCanvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Point currentPoint = new Point(event.getX(), event.getY());
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                onTouchActionDown(currentPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchActionMove(currentPoint);
                break;
            case MotionEvent.ACTION_UP:
                onTouchActionUp(currentPoint);
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
        startTime = 0;
        markedPoints = new ArrayList<>();
        points = new ArrayList<>();
        invalidate();
    }

    public List<List<Integer>> getBinnaryImage(int backgroundValue, int foregroundValue, double scalingPercent)
    {
        List<List<Integer>> binaryImage = new ArrayList<>();
        int newWidth = (int) (canvasBitmap.getWidth() * scalingPercent);
        int newHeight = (int) (canvasBitmap.getHeight() * scalingPercent);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(canvasBitmap, newWidth, newHeight, true);

        for (int i = 1; i < resizedBitmap.getHeight(); i++)
        {
            List<Integer> line = new ArrayList<>();
            for (int j = 1; j < resizedBitmap.getWidth(); j++)
            {
                if (resizedBitmap.getPixel(j, i) == -1)
                    line.add(backgroundValue);
                else
                    line.add(foregroundValue);
            }
            binaryImage.add(line);
        }
        return binaryImage;
    }

    public List<List<Integer>> getBinnaryImage(double scalingPercent)
    {
        return getBinnaryImage(0, 1, scalingPercent);
    }

    public List<List<Integer>> getBinnaryImage()
    {
        return getBinnaryImage(1);
    }

    public void writeData()
    {

        Log.i("WRITING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>=====================");
        String filename = "signatureData.txt";
        String fileContents = "ahihi";
        FileOutputStream outputStream;
        Context ctx = getContext();
        try
        {
            outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("WRITING", "=====================<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    // get degree at P2
    public double getDegree(AngularMeasurementUnit unit, Point p1, Point p2, Point p3)
    {
        double resultAsRadian = 0;
        if ((p1.x == p2.x && p2.x == p3.x) || (p1.y == p2.y && p2.y == p3.y))
        {
            resultAsRadian = Math.PI;
        }
        else
        {
            double a = (p3.x - p2.x) * (p3.x - p2.x) + (p3.y - p2.y)
                * (p3.y - p2.y);
            double b = (p3.x - p1.x) * (p3.x - p1.x) + (p3.y - p1.y)
                * (p3.y - p1.y);
            double c = (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y)
                * (p2.y - p1.y);
            resultAsRadian = Math.acos((a + c - b) / (2 * Math.sqrt(c) * Math.sqrt(a)));
            //temp * 180 / Math.PI < cachingDegree
        }
        if (unit == AngularMeasurementUnit.Degree)
        {
            return resultAsRadian * 180 / Math.PI;
        }
        else
        {
            return resultAsRadian;
        }
    }

    public ArrayList<Double> nomalizeInput(ArrayList<Point> input, long timeOffset)
    {
        ArrayList<Double> list = new ArrayList<>();
        ArrayList<Point> newCatched = new ArrayList<>();
        int index = 0;
        long time = 0;
        while (time < 3000L && list.size() < (3000L / timeOffset))
        {
            if (index < input.size())
            {
                while (time < input.get(index).getTime() - timeOffset)
                {
                    list.add(0.0);
                    time += timeOffset;
                    Log.d("MyTime", "\nDegree = " + list.get(list.size() - 1)
                        + " Time = " + time + " - "
                        + input.get(index - 1).getTime());
                }
                newCatched.add(input.get(index));
                list.add(input.get(index++).getDegree());
                time += timeOffset;
                Log.d("MyTime", "\nDegree = " + list.get(list.size() - 1)
                    + " Time = " + time + " - " + input.get(index - 1).getTime());
                while ((index < input.size() - 1)
                    && time > input.get(index).getTime())
                    index++;
            }
            else
            {
                list.add(0.0);
                time += timeOffset;
                Log.d("MyTime", "\nDegree = " + list.get(list.size() - 1)
                    + " Time = " + time + " - " + input.get(index - 1).time);
            }
        }
        this.markedPoints = newCatched;
        return list;
    }
}