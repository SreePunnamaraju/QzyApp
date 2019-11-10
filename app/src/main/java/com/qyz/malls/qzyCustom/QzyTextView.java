package com.qyz.malls.qzyCustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qyz.malls.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QzyTextView extends AppCompatTextView {

    public QzyTextView(Context context) {
        this(context, null);
    }
    public QzyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public QzyTextView(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  System.out.println("Ritesh here type 12345 fkhjg fdsjkhdhdg");
        int defaultFont = 1;
        if (attrs != null) {
                TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QzyTextView, 0, 0);
            int  type = a.getInt(R.styleable.QzyTextView_Font, defaultFont);
            //   System.out.println("Ritesh here type "+type);
            switch (type)
            {
                case 1:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Regular.ttf"));
                    break;
                case 2:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-SemiBoldItalic.ttf"));
                    break;
                case 3:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-SemiBold.ttf"));
                    break;
                case 4:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-LightItalic.ttf"));
                    break;
                case 5:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Light.ttf"));
                    break;
                case 6:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Italic.ttf"));
                    break;
                case 7:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-ExtraBoldItalic.ttf"));
                    break;
                case 8:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-BoldItalic.ttf"));
                    break;
                case 9:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Bold.ttf"));
                    break;
                case 10:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Black.ttf"));
                    break;
                case 11:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Bold.ttf"));
                    break;
                case 12:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                    break;
                case 13:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Black.ttf"));
                    break;
                case 14:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-ExtraBold.ttf"));
                    break;
                default:
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Regular.ttf"));
                    break;
            }
        }

    }
}
