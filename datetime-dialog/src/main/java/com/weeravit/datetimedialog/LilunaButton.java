package com.weeravit.datetimedialog;

import android.content.Context;
import android.util.AttributeSet;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by appsolute4 on 8/12/2016 AD.
 */
public class LilunaButton extends FancyButton {

    public static final String CUSTOM_TEXT_FONT = "Kanit-Light.ttf";

    public LilunaButton(Context context) {
        super(context);
        initInstances();
    }

    public LilunaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInstances();
    }

    private void initInstances() {
        setCustomTextFont(CUSTOM_TEXT_FONT);
    }

}
