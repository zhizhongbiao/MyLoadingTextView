package cn.com.tianyudg.myloadingtextview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Auther   : ZZB
 * Date     : 2017/6/26
 * Desc     :
 */

public class LoadingTextView extends android.support.v7.widget.AppCompatTextView implements Runnable {


    private static final String TAG = "LoadingTextView";
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_HIGH_LIGHT_TEXT_COLOR = Color.RED;
    private static final float DEFAULT_HIGH_LIGHT_TEXT_SIZE = 60;
    private static final float DEFAULT_TEXT_SIZE = 40;
    private static final int DEFAULT_TEXT_MARGIN = 2;
    private static final int DEFAULT_UP_OFF_SIDE = 10;
    private static final int DEFAULT_INVALIDATE_INTERVAL = 180;
    private Paint paint;
    private String loadingText = "";
    private int selectedPos = 0;
    private int highLightTextColor;
    private float highLightTextSize;
    private float textSize;
    private int textColor;
    private int textMargin;
    private int upOffSide;
    private int invalidateInterval;
    private boolean isStopLoading;
    private int mHeight;
    private int mWidth;
    private int mWidthCenter;
    private int mHeightCenter;
    private Rect rect = new Rect();
    private ArrayList<Integer> widths = new ArrayList<>();
    private ArrayList<Integer> heights = new ArrayList<>();
    private int loadingTextWidth;
    private int loadingTextHeight;
    private int loadingTextLength;


    /**
     * getter and setter
     *
     * @return the loading text
     */
    public String getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public int getHighLightTextColor() {
        return highLightTextColor;
    }

    public void setHighLightTextColor(int highLightTextColor) {
        this.highLightTextColor = highLightTextColor;
    }

    public float getHighLightTextSize() {
        return highLightTextSize;
    }

    public void setHighLightTextSize(float highLightTextSize) {
        this.highLightTextSize = highLightTextSize;
    }

    @Override
    public float getTextSize() {
        return textSize;
    }

    @Override
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextMargin() {
        return textMargin;
    }

    public void setTextMargin(int textMargin) {
        this.textMargin = textMargin;
    }

    public int getUpOffSide() {
        return upOffSide;
    }

    public void setUpOffSide(int upOffSide) {
        this.upOffSide = upOffSide;
    }

    public int getInvalidateInterval() {
        return invalidateInterval;
    }

    public void setInvalidateInterval(int invalidateInterval) {
        this.invalidateInterval = invalidateInterval;
    }

    public boolean isStopLoading() {
        return isStopLoading;
    }

    public void setStopLoading(boolean stopLoading) {
        isStopLoading = stopLoading;
    }


    private void setPaintParams(float textSize, int textColor) {
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        invalidate();
    }


    /**
     * constructor
     *
     * @param context the context
     */
    public LoadingTextView(Context context) {
        this(context, null, 0);

    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public LoadingTextView(Context context, String loadingText) {
        this(context, null, 0);
        if (TextUtils.isEmpty(loadingText))
        {
            loadingText="努力加载中···";
        }
        this.loadingText = loadingText;
        textColor = DEFAULT_TEXT_COLOR;
        textSize = DEFAULT_TEXT_SIZE;
        textMargin = DEFAULT_TEXT_MARGIN;
        upOffSide = DEFAULT_UP_OFF_SIDE;
        invalidateInterval = DEFAULT_INVALIDATE_INTERVAL;
        highLightTextColor = DEFAULT_HIGH_LIGHT_TEXT_COLOR;
        highLightTextSize = DEFAULT_HIGH_LIGHT_TEXT_SIZE;
        initPaint();
        initText(loadingText);

        postDelayed(this, invalidateInterval);
    }

    private void initText(String loadingText) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setGravity(Gravity.CENTER);
        loadingTextLength = this.loadingText.length();
        for (int i = 0; i < loadingTextLength; i++) {
            String letter = loadingText.charAt(i) + "";
            paint.getTextBounds(letter, 0, letter.length(), rect);
            int letterHeight = rect.height();
            int letterWidth = rect.width();
            if (i > 0) {
                letterWidth = (letterHeight + textMargin) > widths.get(i - 1) ?
                        letterHeight + textMargin : widths.get(i - 1);
            }
            loadingTextWidth = loadingTextWidth + letterWidth;
            loadingTextHeight = loadingTextWidth < letterHeight ? letterHeight : loadingTextHeight;
//            widths.add(i, letterWidth + textMargin);
            widths.add(i, letterWidth);
            heights.add(i, letterHeight);
            rect.setEmpty();
        }
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        setPaintParams(textSize, textColor);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        getLayoutParameters();
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void getLayoutParameters() {
        mWidth = getWidth();
        mHeight = getHeight();
        mWidthCenter = mWidth / 2;
        mHeightCenter = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(loadingText))
        {
            Log.e(TAG, "onDraw: loadingText 为空" );
            return;
        }


        int tempWidth = mWidthCenter - ((loadingTextWidth + textMargin * loadingTextLength) / 2);
        for (int i = 0; i < loadingText.length(); i++) {
            int width = 0;
            for (int j = 0; j <= i; j++) {
                if (i == j) {
                    width = width + widths.get(j)/2;
                } else {
                    width = width + widths.get(j);
                }
            }

            int tempHeght;
            if (i == selectedPos) {
                setPaintParams(highLightTextSize, highLightTextColor);
                tempHeght = mHeightCenter - upOffSide;
            } else {
                setPaintParams(textSize, textColor);
                tempHeght = mHeightCenter;
            }

//            Log.e(TAG, "onDraw: mHeightCenter/mWidthCenter=" + mHeightCenter + "/" + mWidthCenter);
            canvas.drawText(loadingText.charAt(i) + "", tempWidth + width
                    , tempHeght
                    , paint);

        }


    }




    @Override
    public void run() {
        if (!isStopLoading) {

            if (selectedPos > loadingTextLength - 1) {
                selectedPos = -1;
            }
//            Log.e("LoadingTextView", "run: selectedPos=" + selectedPos);
            selectedPos++;
            invalidate();
            postDelayed(this, invalidateInterval);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStopLoading = true;
        removeCallbacks(this);
    }
}
