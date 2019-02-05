package com.manroid.convert.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Manroid
 * full support unicode
 */

public class BaseEditText extends android.support.v7.widget.AppCompatEditText {
    public BaseEditText(Context context) {
        super(context);
        setup(context);

    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);

    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context) {
        AssetManager assetManager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/DejaVuSerif.ttf");
        setTypeface(typeface);
    }
}
