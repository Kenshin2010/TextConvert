package com.manroid.convert.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Manroid
 */

public class BaseTextView extends android.support.v7.widget.AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
        setup(context);

    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);

    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context) {
        AssetManager assetManager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Roboto-Light.ttf");
        setTypeface(typeface);
    }
}
