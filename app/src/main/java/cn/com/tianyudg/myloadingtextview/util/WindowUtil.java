package cn.com.tianyudg.myloadingtextview.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by ZZB on 2017/6/8.
 */

public class WindowUtil {


    /**
     *
     * @param view
     * @param width
     * @param height
     * @param anchorView
     * @return
     */
    public static PopupWindow showPopupWindow(View view, int width, int height, View anchorView) {
        final PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(view);
        popupWindow.setFocusable(false);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                点击外面消失
//                LogUtils.e("event.getAction()="+event.getAction());
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    LogUtils.e("MotionEvent.ACTION_OUTSIDE="+MotionEvent.ACTION_OUTSIDE);
//                    closePopWin(popupWindow);
//                    return true;
//                }
//                return false;
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.CENTER);

        } else {
            popupWindow.showAsDropDown(anchorView, 0, 0);
        }

        return popupWindow;
    }

    public static PopupWindow showPopupWindow(View view, View anchorView) {

        return showPopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, anchorView);
    }

    public static boolean closePopWin(PopupWindow pw) {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
            return true;
        }
        return false;

    }


    public static AlertDialog showCustomAlertDialog(Context context, @LayoutRes int layoutResId) {
        return showCustomAlertDialog(context, layoutResId, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public static AlertDialog showCustomAlertDialog(Context context, @LayoutRes int layoutResId, int width, int height) {
        View view = LayoutInflater.from(context).inflate(layoutResId,null,false);
        return showCustomAlertDialog(context, view, width, height);
    }

    public static AlertDialog showCustomAlertDialog(Context context, @LayoutRes int layoutResId, double factor) {
        View view = LayoutInflater.from(context).inflate(layoutResId,null,false);
        return showCustomAlertDialog(context, view, factor);
    }

    public static AlertDialog showCustomAlertDialog(Context context, View view, int width, int height) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        alertDialog.show();
        setDialogWindowAttr(alertDialog, width, height);
        return alertDialog;
    }

    public static AlertDialog showCustomAlertDialog(Context context, View view, double factor) {
        return showCustomAlertDialog(context, view, factor, factor);
    }

    public static AlertDialog showCustomAlertDialog(Context context, View view, double widthFactor, double heightFactor) {

        return showCustomAlertDialog(context, view, (int) (getScreenWidth(context) * widthFactor), (int) (getScreenHeight(context) * heightFactor));
    }

    /**
     * 关闭对话框
     * @param ag
     * @return
     */
    public static boolean closeDialog(Dialog ag) {
        if (ag != null && ag.isShowing()) {
            ag.dismiss();
            return true;
        }

        return false;
    }

    /**
     * 展示对话框
     * @param ag
     * @return
     */
    public static boolean showDialog(Dialog ag) {
        if (ag != null && !ag.isShowing()) {
            ag.show();
            return true;
        }

        return false;
    }


    /**
     * 设置一个对话框大小
     * @param context
     * @param dlg
     * @param factor
     */
    public static void setDialogWindowAttr(Context context, Dialog dlg, double factor) {
        int width = ((int) (getScreenWidth(context) * factor));
        int height = ((int) (getScreenHeight(context) * factor));
        setDialogWindowAttr(dlg, width, height);
    }


    public static void setDialogWindowAttr(Context context, Dialog dlg, double widthFactor, double heightFactor) {
        int width = ((int) (getScreenWidth(context) * widthFactor));
        int height = ((int) (getScreenHeight(context) * heightFactor));
        setDialogWindowAttr(dlg, width, height);
    }


    /**
     *   设置DialogFragment 大小
     * @param context 上下文
     * @param dlg  对话框
     * @param factor 比例因子
     */
    public static void setDialogFragmentWindowLayoutParams(Context context, Dialog dlg, double factor) {
        int width = ((int) (getScreenWidth(context) * factor));
        int height = ((int) (getScreenHeight(context) * factor));
        dlg.getWindow().setLayout(width, height);
    }



    /**
     * 设置Dialogd的大小，在dialog.show()之后调用
     */
    public static void setDialogWindowAttr(Dialog dlg, int width, int height) {
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = width;//宽高可设置具体大小,单位pixel
        lp.height = height;
        dlg.getWindow().setAttributes(lp);
    }


    /**
     * 获取屏幕宽度像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度像素
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


}
