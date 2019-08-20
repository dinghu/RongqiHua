package com.rongqi.hua.rongqihua.uitls;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by dinghu on 2019/8/20.
 */
public class CommonUtils {
    public static boolean checkInput(EditText editText) {
        String input = editText.getText().toString();
        if (TextUtils.isEmpty(input)) {
            editText.requestFocus();
            editText.setError(TextUtils.isEmpty(editText.getHint()) ? "请输入内容" : editText.getHint());
            return false;
        }

        return true;
    }
}
