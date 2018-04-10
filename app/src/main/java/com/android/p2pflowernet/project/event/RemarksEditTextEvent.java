package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/3/5/005.
 */

public class RemarksEditTextEvent {

    private final String editText;

    public RemarksEditTextEvent(String editText) {

        this.editText = editText;
    }

    public String getEditText() {
        return editText;
    }

}
