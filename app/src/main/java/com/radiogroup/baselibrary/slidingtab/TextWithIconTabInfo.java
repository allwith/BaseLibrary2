package com.radiogroup.baselibrary.slidingtab;

public class TextWithIconTabInfo {
    private boolean isShowIcon;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setShowIcon(boolean showIcon) {
        this.isShowIcon = showIcon;
    }

    public boolean isShowIcon() {
        return this.isShowIcon;
    }
}
