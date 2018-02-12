package com.radiogroup.baselibrary.slidingtab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radiogroup.baselibrary.R;

import java.util.Locale;

public class PagerSlidingTabStrip extends HorizontalScrollView {
    // @formatter:off
    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    private int currentPosition = 0;
    private float currentPositionOffset = 0.0f ;
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    public OnPageChangeListener delegatePageListener;
    private int dividerColor = 0x1A000000;
    private int dividerPadding = 2;
    private Paint dividerPaint;
    private int dividerWidth = 1;
    private LinearLayout.LayoutParams expandedTabLayoutParams;
    private int indicatorColor = 0xFF666666;
    private int indicatorHeight = 8;
    private int indicatorPadding;
    private boolean isShowDivider = true;
    private int lastScrollX = 0;
    private Locale locale;
    private final PageListener pageListener  = new PageListener();
    private MyViewPager pager;
    private Paint rectPaint;
    private int scrollOffset = 52;
    private boolean shouldExpand = false;
    private int tabCount;
    private int tabPadding = 24;
    private int tabSelectTextSize;
    private int tabTextColor = 0xFF666666;
    private int tabTextSize = 12;
    private Typeface tabTypeface;
    private int tabTypefaceStyle = Typeface.BOLD;
    private LinearLayout tabsContainer;
    private boolean textAllCaps  = true;
    private int underlineColor = 0x1A000000;
    private int underlineHeight = 2;

    public interface TextWithIconTabProvider {
        TextWithIconTabInfo getTextWithIconTabInfo(int i);
    }

    public interface IconTabProvider {
        int getPageIconResId(int i);
    }

    private class PageListener implements OnPageChangeListener {
        private PageListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (PagerSlidingTabStrip.this.tabsContainer.getChildCount() > position) {
                PagerSlidingTabStrip.this.currentPosition = position;
                PagerSlidingTabStrip.this.currentPositionOffset = positionOffset;
                PagerSlidingTabStrip.this.scrollToChild(position, (int) (((float) PagerSlidingTabStrip.this.tabsContainer.getChildAt(position).getWidth()) * positionOffset));
                PagerSlidingTabStrip.this.invalidate();
                if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                    PagerSlidingTabStrip.this.delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.pager.getCurrentItem(), 0);
            }
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        public void onPageSelected(int position) {
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageSelected(position);
            }
            PagerSlidingTabStrip.this.updateTabStyles();
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPosition = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPosition);
        }
    }

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.tabSelectTextSize = dp2px(context, 18f);
        setFillViewport(true);
        setWillNotDraw(false);
        this.tabsContainer = new LinearLayout(context);
        this.tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        this.tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(this.tabsContainer);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.scrollOffset, dm);
        this.indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.indicatorHeight, dm);
        this.underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.underlineHeight, dm);
        this.dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.dividerPadding, dm);
        this.tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.tabPadding, dm);
        this.dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.dividerWidth, dm);
        this.tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, (float) this.tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        this.tabTextSize = a.getDimensionPixelSize(0, this.tabTextSize);
        this.tabTextColor = a.getColor(1, this.tabTextColor);

        a.recycle();

        // get custom attrs
        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, this.indicatorColor);
        underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, this.underlineColor);
        dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
        shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

        a.recycle();


        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Style.FILL);

        this.dividerPaint = new Paint();
        this.dividerPaint.setAntiAlias(true);
        this.dividerPaint.setStrokeWidth((float) this.dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);


        if (this.locale == null) {
            this.locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(MyViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pager.setOnPageChangeListener(this.pageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.pager.getAdapter().getCount();
        boolean isIconTab = this.pager.getAdapter() instanceof IconTabProvider;
        boolean isTextWithIconTab = this.pager.getAdapter() instanceof TextWithIconTabProvider;
        for (int i = 0; i < this.tabCount; i++) {
            if (isIconTab) {
                addIconTab(i, ((IconTabProvider) this.pager.getAdapter()).getPageIconResId(i));
            } else if (isTextWithIconTab) {
                addTextWithIconTab(i, ((TextWithIconTabProvider) this.pager.getAdapter()).getTextWithIconTabInfo(i));
            } else {
                addTextTab(i, this.pager.getAdapter().getPageTitle(i).toString());
            }
        }
        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressLint({"NewApi"})
            public void onGlobalLayout() {
                if (VERSION.SDK_INT < 16) {
                    PagerSlidingTabStrip.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    PagerSlidingTabStrip.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                PagerSlidingTabStrip.this.currentPosition = PagerSlidingTabStrip.this.pager.getCurrentItem();
                PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.currentPosition, 0);
            }
        });
    }

    private void addTextTab(int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setPadding(this.tabPadding, 0, this.tabPadding, 0);
        addTab(position, tab);
    }

    private void addIconTab(int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        tab.setPadding(this.tabPadding, 0, this.tabPadding, 0);
        addTab(position, tab);
    }

    private void addTextWithIconTab(int position, TextWithIconTabInfo tabInfo) {
        View tab = LayoutInflater.from(getContext()).inflate(R.layout.item_pager_sliding_text_with_icon, null);
        TextView tabIconTv = (TextView) tab.findViewById(R.id.tabIconTv);
        ((TextView) tab.findViewById(R.id.tabTitleTv)).setText(tabInfo.getTitle());
        if (tabInfo.isShowIcon()) {
            tabIconTv.setVisibility(View.VISIBLE);
            tabIconTv.setTag(tabInfo.getTitle());
        } else {
            tabIconTv.setVisibility(View.GONE);
        }
        addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PagerSlidingTabStrip.this.pager.setCurrentItem(position);
            }
        });
        this.tabsContainer.addView(tab, position, this.shouldExpand ? this.expandedTabLayoutParams : this.defaultTabLayoutParams);
    }

    private void updateTabStyles() {
        boolean isTextWithIconTab = false;
        if (this.pager != null) {
            isTextWithIconTab = this.pager.getAdapter() instanceof TextWithIconTabProvider;
        }
        int i = 0;
        while (i < this.tabCount) {
            View v;
            if (isTextWithIconTab) {
                v = this.tabsContainer.getChildAt(i).findViewById(R.id.tabTitleTv);
            } else {
                v = this.tabsContainer.getChildAt(i);
            }
            if (v != null && (v instanceof TextView)) {
                TextView tab = (TextView) v;
                tab.setTypeface(this.tabTypeface, this.tabTypefaceStyle);
                if (this.pager == null || this.pager.getCurrentItem() != i) {
                    tab.setTextColor(this.tabTextColor);
                    tab.setTextSize(0, (float) this.tabTextSize);
                } else {
                    tab.setTextColor(Color.BLACK);
                    tab.setTextSize(0, (float) this.tabSelectTextSize);
                }
                if (this.textAllCaps) {
                    if (VERSION.SDK_INT  >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(this.locale));
                    }
                }
            }
            i++;
        }
    }

    public void scrollToChild(int position, int offset) {
        if (this.tabCount != 0) {
            int newScrollX = this.tabsContainer.getChildAt(position).getLeft() + offset;
            if (position > 0 || offset > 0) {
                newScrollX -= this.scrollOffset;
            }
            if (newScrollX == 0 || newScrollX != this.lastScrollX) {
                this.lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.tabCount != 0) {
            int height = getHeight();
            this.rectPaint.setColor(this.indicatorColor);
            View currentTab = this.tabsContainer.getChildAt(this.currentPosition);
            if (currentTab != null) {
                float lineLeft = (float) currentTab.getLeft();
                float lineRight = (float) currentTab.getRight();
                if (this.currentPositionOffset > 0.0f && this.currentPosition < this.tabCount - 1) {
                    View nextTab = this.tabsContainer.getChildAt(this.currentPosition + 1);
                    lineLeft = (this.currentPositionOffset * ((float) nextTab.getLeft())) + ((1.0f - this.currentPositionOffset) * lineLeft);
                    lineRight = (this.currentPositionOffset * ((float) nextTab.getRight())) + ((1.0f - this.currentPositionOffset) * lineRight);
                }
                canvas.drawRect(lineLeft + ((float) this.indicatorPadding), (float) (height - this.indicatorHeight), lineRight - ((float) this.indicatorPadding), (float) height, this.rectPaint);
                this.rectPaint.setColor(this.underlineColor);
                canvas.drawRect(0.0f, (float) (height - this.underlineHeight), (float) this.tabsContainer.getWidth(), (float) height, this.rectPaint);
                if (this.isShowDivider) {
                    this.dividerPaint.setColor(this.dividerColor);
                    for (int i = 0; i < this.tabCount - 1; i++) {
                        View tab = this.tabsContainer.getChildAt(i);
                        canvas.drawLine((float) tab.getRight(), (float) this.dividerPadding, (float) tab.getRight(), (float) (height - this.dividerPadding), this.dividerPaint);
                    }
                }
            }
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public void setIndicatorPadding(int indicatorPaddingPx) {
        this.indicatorPadding = indicatorPaddingPx;
        invalidate();
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return this.dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return this.underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return this.dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return this.shouldExpand;
    }

    public boolean isTextAllCaps() {
        return this.textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return this.tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return this.tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return this.tabPadding;
    }

    public void setIsShowDivider(boolean isShowDivider) {
        this.isShowDivider = isShowDivider;
        invalidate();
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.currentPosition = savedState.currentPosition;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPosition = this.currentPosition;
        return savedState;
    }

    public void hideForumTabIcon(String forumCode) {
        View view = null;
        if (this.tabsContainer != null) {
            view = this.tabsContainer.findViewWithTag(forumCode);
        }
        if (view != null) {
            view.setVisibility(View.GONE);
            invalidate();
        }
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }


}
