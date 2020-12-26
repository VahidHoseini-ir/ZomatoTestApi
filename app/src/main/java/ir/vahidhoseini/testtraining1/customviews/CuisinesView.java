package ir.vahidhoseini.testtraining1.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.vahidhoseini.testtraining1.R;

public class CuisinesView extends androidx.appcompat.widget.AppCompatTextView {

    public CuisinesView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CuisinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    private void init(Context context) {
        setClickable(true);
        setFocusable(true);
        setPadding(8, 0, 8, 0);
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(context.getDrawable(outValue.resourceId));
        }

        context.getTheme().resolveAttribute(android.R.attr.colorPrimary, outValue, true);
        setTextColor(outValue.data);
    }


}
