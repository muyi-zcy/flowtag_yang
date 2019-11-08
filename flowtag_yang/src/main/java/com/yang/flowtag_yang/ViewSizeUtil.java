package com.yang.flowtag_yang;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * 获取屏幕手机信息
 */

public class ViewSizeUtil {

    public static Rect getWindowRootViewRect(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame;
    }

    public static Rect getViewRectInParent(View view, ViewGroup parent) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        parent.offsetDescendantRectToMyCoords(view, rect);
        return rect;
    }

    /**
     * 获取屏幕尺寸
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context) {
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wmManager.getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();
        display.getMetrics(metric);
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    /**
     * @param 计算 dp值 （将px值转换为dip或dp值，保证尺寸大小不变）
     * @return
     */
    public static int getCustomDimen(Context context, float dimen) {
        float density = ViewSizeUtil.getScreenSize(context).x / ViewSizeUtil.getDensity(context);
        //计算公式
        return (int) ((ViewSizeUtil.getDensity(context) * density * dimen / 360f) + 0.5f);
    }

    /**
     * 显示屏幕密度
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wmManager.getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();
        display.getMetrics(metric);
        return metric.density;
    }

    /**
     * 显示屏幕绝对高度  单位 像素
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context) {
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wmManager.getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();
        display.getMetrics(metric);
        return metric.densityDpi;
    }


    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }


}
