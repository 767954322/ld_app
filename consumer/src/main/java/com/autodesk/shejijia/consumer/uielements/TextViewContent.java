package com.autodesk.shejijia.consumer.uielements;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * @author   Malidong .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          TextViewContent.java .
 * @brief        .
 */
public class TextViewContent extends EditText {
    private int cursorPos;
    private String inputAfterText;

    private boolean resetText;

    private Context mContext;

    public TextViewContent(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public TextViewContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public TextViewContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    }

    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    inputAfterText = s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!resetText) {
                    if (before == 0) {
                        CharSequence input = s.subSequence(cursorPos, cursorPos
                                + count);
                        if (containsEmoji(input.toString())) {
                            resetText = true;
//                            MyToast.show(mContext, UIUtils.getString(R.string.no_support_emojo));
                            setText(inputAfterText);
                            CharSequence text = getText();
                            if (text instanceof Spannable) {
                                Spannable spanText = (Spannable) text;
                                Selection.setSelection(spanText,
                                        spanText.length());
                            }
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        int b = codePoint;
        Log.e("Test", Integer.toHexString(b));
        return (codePoint == 0x0)
                || (codePoint == 0x9)
                || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


}