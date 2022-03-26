package com.nativedevps.support.custom_views;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class LinkTextView extends AppCompatTextView {
    public LinkTextView(Context context) {
        this(context, null);
    }

    public LinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        super.setText(content, type);
    }
}