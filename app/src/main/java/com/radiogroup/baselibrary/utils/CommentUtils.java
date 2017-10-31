package com.radiogroup.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.radiogroup.baselibrary.R;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/10/7.
 */

public class CommentUtils {


    /**
     * 设置图文内容
     *
     * @param context
     * @param target
     * @param htmlSource
     * @param linkColor
     * @param textPainFlag
     */
    public static void setTextViewFromHtml(final Context context, TextView target, String htmlSource, final int
            linkColor, final int textPainFlag) {
        target.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence charSequence = Html.fromHtml(htmlSource, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                return drawable;
            }
        }, null);

        SpannableString spanStr = new SpannableString(charSequence);

        URLSpan[] urlspans = spanStr.getSpans(0, spanStr.length(), URLSpan.class);
        for (URLSpan urlspan : urlspans) {
            Log.d("url", "url=" + urlspan.getURL());
            spanStr.setSpan(new URLSpan(urlspan.getURL()) {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setFlags(textPainFlag);
                    ds.setColor(linkColor);
                }

                @Override
                public void onClick(View widget) {
                    try {
                        URI uri = new URI(getURL());
                        if ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme())) {
                        } else if ("tel".equalsIgnoreCase(uri.getScheme())) {
                        } else if ("mailto".equalsIgnoreCase(uri.getScheme())) {
                        } else if ("page".equalsIgnoreCase(uri.getScheme())) {
                            // TODO Activity 跳转
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }, spanStr.getSpanStart(urlspan), spanStr.getSpanEnd(urlspan), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        target.setText(spanStr);
    }


}
