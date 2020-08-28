package com.sung.common.bean.config;

import androidx.annotation.ColorInt;

/**
 * Create by sung at 2018/12/6
 *
 * @Description: toolbar配置
 */
public class ToolbarConfig {
    private boolean displayBackAsUpEnable;
    private boolean displayLogoEnable;
    private boolean displayTitleEnable;
    private boolean displaySubTitleEnable;
    private boolean displayCenterTitleEnable;
    private boolean displayElevationEnable;
    private String textTitle;
    private String textSubTitle;
    private @ColorInt
    int colorBackground = -99;
    private @ColorInt
    int colorText = -99;
    private int resLogo = -99;
    private int resBackButton = -99;

    public boolean isDisplayBackAsUpEnable() {
        return displayBackAsUpEnable;
    }

    public boolean isDisplayLogoEnable() {
        return displayLogoEnable;
    }

    public boolean isDisplayTitleEnable() {
        return displayTitleEnable;
    }

    public boolean isDisplaySubTitleEnable() {
        return displaySubTitleEnable;
    }

    public boolean isDisplayCenterTitleEnable() {
        return displayCenterTitleEnable;
    }

    public boolean isDisplayElevationEnable() {
        return displayElevationEnable;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getTextSubTitle() {
        return textSubTitle;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public int getResLogo() {
        return resLogo;
    }

    public int getResBackButton() {
        return resBackButton;
    }

    public int getColorText() {
        return colorText;
    }

    public static class Builder {
        ToolbarConfig config = new ToolbarConfig();

        public Builder setDisplayBackAsUpEnable(boolean displayBackAsUpEnable) {
            config.displayBackAsUpEnable = displayBackAsUpEnable;
            return this;
        }

        public Builder setDisplayLogoEnable(boolean displayLogoEnable) {
            config.displayLogoEnable = displayLogoEnable;
            return this;
        }

        public Builder setDisplayTitleEnable(boolean displayTitleEnable) {
            config.displayTitleEnable = displayTitleEnable;
            return this;
        }

        public Builder setDisplaySubTitleEnable(boolean displaySubTitleEnable) {
            config.displaySubTitleEnable = displaySubTitleEnable;
            return this;
        }

        public Builder setDisplayCenterTitleEnable(boolean displayCenterTitleEnable) {
            config.displayCenterTitleEnable = displayCenterTitleEnable;
            return this;
        }

        public Builder setDisplayElevationEnable(boolean displayElevationEnable) {
            config.displayElevationEnable = displayElevationEnable;
            return this;
        }

        public Builder setTextTitle(String textTitle) {
            config.textTitle = textTitle;
            return this;
        }

        public Builder setTextSubTitle(String textSubTitle) {
            config.textSubTitle = textSubTitle;
            return this;
        }

        public Builder setColorBackground(int colorBackground) {
            config.colorBackground = colorBackground;
            return this;
        }

        public Builder setColorText(int colorText) {
            config.colorText = colorText;
            return this;
        }

        public Builder setResLogo(int resLogo) {
            config.resLogo = resLogo;
            return this;
        }

        public Builder setResBackButton(int resBackButton) {
            config.resBackButton = resBackButton;
            return this;
        }

        public ToolbarConfig creat() {
            return config;
        }
    }
}
