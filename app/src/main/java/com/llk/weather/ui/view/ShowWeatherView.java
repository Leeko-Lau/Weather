package com.llk.weather.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by King on 2016/6/14.
 */
public class ShowWeatherView extends View {

    public int XPoint = 170;    //原点的X坐标
    public int YPoint = 380;     //原点的Y坐标
    public int XScale = 55;     //X的刻度长度
    public int YScale = 40;     //Y的刻度长度
    public int XLength = 380;        //X轴的长度
    public int YLength = 340;        //Y轴的长度
    public String[] XLabel;    //X的刻度
    public String[] YLabel;    //Y的刻度
    public String[] hight;      //高温数据
    public String[] low;        //低温数据

    public ShowWeatherView(Context context) {
        super(context);
    }

    public ShowWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShowWeatherView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setInfo(String[] XLabel, String[] YLabel, String[] hight, String[] low) {
        this.XLabel = XLabel;
        this.YLabel = YLabel;
        this.hight = hight;
        this.low = low;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.GRAY);//颜色

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);//去锯齿
        paint1.setColor(Color.GRAY);
        paint1.setTextSize(12);  //设置轴文字大小

        Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);//去锯齿
        paint2.setColor(Color.BLUE);
        paint2.setTextSize(12);  //设置轴文字大小

        Paint paint3 = new Paint();
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setAntiAlias(true);//去锯齿
        paint3.setColor(Color.RED);
        paint3.setTextSize(12);  //设置轴文字大小

        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);   //轴线
        for (int i = 0; i * YScale < YLength; i++) {
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint);  //刻度
            try {
                canvas.drawText(YLabel[i], XPoint - 22, YPoint - i * YScale, paint1);  //文字
            } catch (Exception e) {
            }
        }
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength + 6, paint);  //箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength + 6, paint);

        //设置X轴
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint);   //轴线
        for (int i = 0; i * XScale < XLength; i++) {
            canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale, YPoint - 5, paint);  //刻度
            try {
                canvas.drawText(XLabel[i], XPoint + i * XScale, YPoint + 20, paint1);  //文字
                //数据值
                if (i > 0 && i < hight.length + 1) {
                    canvas.drawLine(XPoint + (i - 1) * XScale, getY(hight[i - 1]),
                            XPoint + i * XScale, getY(hight[i]), paint2);
                    canvas.drawLine(XPoint + (i - 1) * XScale, getY(low[i - 1]),
                            XPoint + i * XScale, getY(low[i]), paint3);

                }
                canvas.drawCircle(XPoint + i * XScale, getY(hight[i]), 2, paint2);
                canvas.drawText(hight[i], XPoint + i * XScale - 2, getY(hight[i]) - 8, paint2);

                canvas.drawCircle(XPoint + i * XScale, getY(low[i]), 2, paint3);
                canvas.drawText(low[i], XPoint + i * XScale - 2, getY(low[i]) - 8, paint2);
            } catch (Exception e) {
            }
        }
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint - 3, paint);
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint + 3, paint);
        paint.setTextSize(16);
    }

    private int getY(String valueY) {
        int y;
        y = Integer.parseInt(valueY);
        return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
    }
}
