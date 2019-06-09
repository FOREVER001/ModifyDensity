package client.com.modifydensity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 *  px=density*dp
 *  density=dpi/160;
 *  ===>px=(dpi/160)*dp
 *
 * */
public class Density {
    //参考屏幕宽度为360dp
    private static final float STANDARD_WIDTH=320;//参考设备的宽，单位是dp 320 / 2 = 160
    private static float appDensity;//表示屏幕密度
    private static float appScaleDensity;//字体缩放比例，默认为appDensity;

    public static void setDensity(final Application application, Activity activity){
        //获取屏幕的宽度
        DisplayMetrics displayMetrics=application.getResources().getDisplayMetrics();

        if(appDensity==0){
            //初始化赋值操作
            appDensity=displayMetrics.density;
            appScaleDensity=displayMetrics.scaledDensity;
            //添加字体变化监听回调
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体发生更改，重新对scaleDensity进行赋值
                    if (newConfig != null && newConfig.fontScale > 0){
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        //计算目标值density, scaleDensity, densityDpi

        float targetDensity=displayMetrics.widthPixels/STANDARD_WIDTH;
        Log.e("==targetDensity===",targetDensity+"");
        int targetDesityDpi= (int) (targetDensity*160);
        float targetScaleDensity=targetDensity*(appScaleDensity/appDensity);

        //替换Activity的density, scaleDensity, densityDpi
        DisplayMetrics activityDisPlayMetrics = activity.getResources().getDisplayMetrics();
        activityDisPlayMetrics.density=targetDensity;
        activityDisPlayMetrics.densityDpi=targetDesityDpi;
        activityDisPlayMetrics.scaledDensity=targetScaleDensity;
    }
}
