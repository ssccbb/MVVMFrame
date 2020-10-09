package me.sung.base.bean.config;

import androidx.annotation.IdRes;
import me.sung.base.enums.MenuEnum;

/**
 * Create by sung at 2020/10/9
 *
 * @desc:
 * @notice:
 */
public class ActionBarConfig {
    /** 返回按钮 */
    private boolean displayBackButton = true;
    /** 返回图标 */
    private boolean displayBackIconButton = true;
    /** 返回logo */
    private boolean displayBackLogoButton = false;
    /** 返回文字 */
    private boolean displayBackTextButton = false;
    /** 中心标题 */
    private boolean displayCenterTitle;
    /** 中心副标题 */
    private boolean displaySubTitle;
    /** 标题文字 */
    private String tittle;
    /** 副标题文字 */
    private String subTitle;
    /** 菜单类型 */
    private MenuEnum menuType = MenuEnum.None;
    /** 返回文字 */
    private String backTips;
    /** 图标菜单 */
    private @IdRes int menuIcon;
    /** 文字菜单 */
    private @IdRes int menuText;
    /** 返回图标 */
    private @IdRes int backIcon;
    /** 返回logo */
    private @IdRes int backLogo;
    /** 背景颜色 */
    private @IdRes int backgroundColor;
    /** 返回文字颜色 */
    private @IdRes int backTextColor;
    /** 菜单文字颜色 */
    private @IdRes int menuColor;
    /** 标题颜色 */
    private @IdRes int titleColor;
    /** 副标题颜色 */
    private @IdRes int subTitleColor;

    public static class Builder {
        ActionBarConfig config = new ActionBarConfig();

        public Builder setDisplayBackLogoButton(boolean displayBackLogoButton) {
            config.displayBackLogoButton = displayBackLogoButton;
            return this;
        }

        public Builder setDisplayBackButton(boolean displayBackButton) {
            config.displayBackButton = displayBackButton;
            return this;
        }

        public Builder setDisplayBackIconButton(boolean displayBackIconButton) {
            config.displayBackIconButton = displayBackIconButton;
            return this;
        }

        public Builder setDisplayBackTextButton(boolean displayBackTextButton) {
            config.displayBackTextButton = displayBackTextButton;
            return this;
        }

        public Builder setDisplayCenterTitle(boolean displayCenterTitle) {
            config.displayCenterTitle = displayCenterTitle;
            return this;
        }

        public Builder setDisplaySubTitle(boolean displaySubTitle) {
            config.displaySubTitle = displaySubTitle;
            return this;
        }

        public Builder setTittle(String tittle) {
            config.tittle = tittle;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            config.subTitle = subTitle;
            return this;
        }

        public Builder setMenuType(MenuEnum menuType) {
            config.menuType = menuType;
            return this;
        }

        public Builder setBackTips(String backTips) {
            config.backTips = backTips;
            return this;
        }

        public Builder setMenuIcon(int menuIcon) {
            config.menuIcon = menuIcon;
            return this;
        }

        public Builder setMenuText(int menuText) {
            config.menuText = menuText;
            return this;
        }

        public Builder setBackIcon(int backIcon) {
            config.backIcon = backIcon;
            return this;
        }

        public Builder setBackLogo(int backLogo) {
            config.backLogo = backLogo;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            config.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackTextColor(int backTextColor) {
            config.backTextColor = backTextColor;
            return this;
        }

        public Builder setMenuColor(int menuColor) {
            config.menuColor = menuColor;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            config.titleColor = titleColor;
            return this;
        }

        public Builder setSubTitleColor(int subTitleColor) {
            config.subTitleColor = subTitleColor;
            return this;
        }

        public ActionBarConfig creat(){
            return config;
        }
    }

    public boolean isDisplayBackLogoButton() {
        return displayBackLogoButton;
    }

    public boolean isDisplayBackButton() {
        return displayBackButton;
    }

    public boolean isDisplayBackIconButton() {
        return displayBackIconButton;
    }

    public boolean isDisplayBackTextButton() {
        return displayBackTextButton;
    }

    public boolean isDisplayCenterTitle() {
        return displayCenterTitle;
    }

    public boolean isDisplaySubTitle() {
        return displaySubTitle;
    }

    public String getTittle() {
        return tittle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public MenuEnum getMenuType() {
        return menuType;
    }

    public String getBackTips() {
        return backTips;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public int getMenuText() {
        return menuText;
    }

    public int getBackLogo() {
        return backLogo;
    }

    public int getBackIcon() {
        return backIcon;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackTextColor() {
        return backTextColor;
    }

    public int getMenuColor() {
        return menuColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getSubTitleColor() {
        return subTitleColor;
    }
}
