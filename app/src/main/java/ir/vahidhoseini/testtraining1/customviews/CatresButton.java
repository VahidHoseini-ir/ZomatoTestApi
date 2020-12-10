package ir.vahidhoseini.testtraining1.customviews;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ir.vahidhoseini.testtraining1.R;

public class CatresButton extends androidx.appcompat.widget.AppCompatCheckBox {

    TransitionDrawable transition;
    int transitionTime = 300;
    public MutableLiveData<CatresButton> listener;

    public CatresButton(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public CatresButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        listener = new MutableLiveData<>();
        CatresButton.this.setBackground(getContext().getDrawable(R.drawable.caters_button_transition));
        setGravity(Gravity.CENTER);
        TransitionDrawable transition = (TransitionDrawable) this.getBackground();
        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.postValue(CatresButton.this);
                if (b) {
                    transition.startTransition(transitionTime);

                } else {
                    transition.reverseTransition(transitionTime);
                }
            }
        });

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = 8;
        setLayoutParams(layoutParams);


    }

    public LiveData<CatresButton> getCatresValue() {
        return listener;
    }
}
