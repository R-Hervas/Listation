package com.multimed.listation.support;

public interface MultiToolbarActivity {

    /**
     * This MultiToolbarActivity has the default toolbar with the title of the app
     */
    int DEFAULT_TOOLBAR = 0;

    /**
     * This MultiToolbarActivity has a custom toolbar as an alternative to defaul
     */
    int CUSTOM_TOOLBAR = 1;

    /**
     * Changes the activity toolbar between default and custom
     * @param toolbar - An int with one of two values {@value DEFAULT_TOOLBAR} OR {@value CUSTOM_TOOLBAR}
     *                which defines the toolbar
     */
    void changeToolbar(int toolbar);

    void setupBtnEdit();

    void setupBtnDelete();

    /**
     * Permits to other classes to change the visibility of the edit button from the {@value CUSTOM_TOOLBAR}
     * @param visibility - An int with two posible values View.VISIBLE or View.INVISIBLE
     */
    void setBtnEditVisibility(int visibility);
}
