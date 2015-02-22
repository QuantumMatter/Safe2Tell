package com.nhscoding.safe2tell;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by davidkopala on 2/13/15.
 */
public class CustomCard extends View {

    String mText = "Please Add Content";
    int mTextColor = 0;
    float mTextSize = 0.0f;
    float textPosX;
    float textPosY;
    Paint mTextPaint;

    String mTitleText = "Please Add A Title";
    float mTitleSize = 0.0f;
    float titlePosX;
    float titlePosY;
    Paint mTitlePaint;

    int logoType = -1;
    Drawable mLogoDrawable;
    String mLogoString;

    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;

    String[] textArray;

    int width = 0;
    int height = 0;

    Paint mLinePaint;

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Set Background Color of Card
        setBackgroundColor(Color.parseColor("#D2D1BB"));

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomCardView,
                0, 0
        );

        try {
            //Get attributes from the XML file
            mText = a.getString(R.styleable.CustomCardView_txt);
            mTextColor = a.getInt(R.styleable.CustomCardView_txtColor, 0xff000000);
            mTextSize = a.getDimension(R.styleable.CustomCardView_txtSize, 0.0f);

            mTitleText = a.getString(R.styleable.CustomCardView_titleTxt);
            mTitleSize = a.getDimension(R.styleable.CustomCardView_titleSize, 0.0f);

            logoType = a.getInt(R.styleable.CustomCardView_logoType, -1);
            mLogoString = a.getString(R.styleable.CustomCardView_logoLocation);
            mLogoDrawable = a.getDrawable(R.styleable.CustomCardView_logoDrawable);
        } finally {
            a.recycle();
        }
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw Content Text
        for (int i = 0; i < textArray.length; i++) {
            String text = textArray[i];
            float posY = i * (mTextSize + 5) + textPosY;
            if (text != null) {
                canvas.drawText(text, textPosX, posY, mTextPaint);
                Log.i("Context Text", text);
                Log.i("Content Text Size", String.valueOf(mTextPaint.getTextSize()));
            }
        }

        String width = String.valueOf(getWidth());
        //canvas.drawText(width, titlePosX, titlePosY, mTitlePaint);
        //Draw Title
        canvas.drawText(mTitleText, titlePosX, titlePosY, mTitlePaint);
        Log.i("Title Text", mTitleText);
        Log.i("Title Text Size", String.valueOf(mTitlePaint.getTextSize()));
        //canvas.drawText(mText, textPosX, textPosY, mTextPaint);
        //Underline The Title
        canvas.drawLine(titlePosX, titlePosY + 10, getWidth() - 50, titlePosY + 10, mLinePaint);
    }

    public CustomCard(Context context) {
        super(context);
        init();
    }

    public void setTitle(String text) {
        mTitleText = text;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setText(String text) {
        mText = text;
        invalidate();
        requestLayout();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float size) {
        mTextSize = size;
    }

    public float getTitleSize() {
        return mTitleSize;
    }

    public void setTitleSize(float size) {
        mTitleSize = size;
    }

    public String getText() {
        return mText;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        invalidate();
        requestLayout();
    }

    public void replace(CustomCard card) {
        mText = card.mText;
        mTextColor = card.mTextColor;
        mTextSize = card.mTextSize;
        textPosX = card.textPosX;
        textPosY = card.textPosY;
        mTextPaint = card.mTextPaint;

        mTitleText = card.mTitleText;
        mTitleSize = card.mTitleSize;
        titlePosX = card.titlePosX;
        titlePosY = card.titlePosY;
        mTitlePaint = card.mTitlePaint;

        logoType = card.logoType;
        mLogoDrawable = card.mLogoDrawable;
        mLogoString = card.mLogoString;

        paddingLeft = card.paddingLeft;
        paddingRight = card.paddingRight;
        paddingTop = card.getPaddingTop();
        paddingBottom = card.paddingBottom;

        textArray = card.textArray;

        width = card.width;
        height = card.height;

        mLinePaint = card.mLinePaint;

        init();

        invalidate();
        requestLayout();
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = 0;
        width = MeasureSpec.getSize(widthMeasureSpec);

        int adjWidth = width - 60;

        //Dynamically Setting the height to fit all content
        String tempText = "";
        char[] text = mText.toCharArray();
        textArray = new String[(int) (mTextPaint.measureText(mText) / adjWidth) + 2];
        int position = -1;

        try {
            for (int i = 0; i < text.length; i++) {
                char aText = text[i];
                tempText += aText;
                int room = (int) (adjWidth - mTextPaint.measureText(tempText));
                int size = (int) mTextPaint.measureText("_______");
                if ((room < size) && (aText == ' ')) {
                    position++;
                    //Add text to array for drawing
                    textArray[position] = tempText;
                    tempText = "";
                    height += mTextSize + 10;
                } else {
                    if((i + 1) == text.length) {
                        position++;
                        //Add text to array for drawing
                        textArray[position] = tempText;
                        tempText = "";
                        height += mTextSize + 10;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        width = width - (getPaddingLeft() + getPaddingRight());
        height = (int) ((height) + (mTitleSize) + 40 + getPaddingBottom() + getPaddingTop());

        //Set the calculated dimensions
        //This is where the actual height and width come from
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {

        //Get padding
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();

        //Set Title Position
        titlePosX = paddingLeft + 30.0f;
        titlePosY = paddingTop + mTitleSize + 10.0f;

        //Set Text Position
        textPosX = paddingLeft + 30.0f;
        textPosY = titlePosY + mTextSize + 30.0f;

        //Set Text Properties
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        //Set Title Properties
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setTextSize(mTitleSize);

        //Set Line Properties
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(getResources().getColor(R.color.material_blue_grey_800));
    }
}
