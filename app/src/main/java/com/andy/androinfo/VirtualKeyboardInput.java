package com.andy.androinfo;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import java.util.Arrays;

public class VirtualKeyboardInput extends EditText
{
    public VirtualKeyboardInput(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public VirtualKeyboardInput(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public VirtualKeyboardInput(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        if (emojiExcludeFilter == null)
        {
            emojiExcludeFilter = new EmojiExcludeFilter();
        }
        setFilters(new InputFilter[]{emojiExcludeFilter});
    }

    @Override
    public void setFilters(InputFilter[] filters)
    {
        if (filters.length != 0 && emojiExcludeFilter != null)
        { //if length == 0 it will here return when init() is called
            boolean add = true;
            for (InputFilter inputFilter : filters)
            {
                if (inputFilter == emojiExcludeFilter)
                {
                    add = false;
                    break;
                }
            }
            if (add) {
                filters = Arrays.copyOf(filters, filters.length + 1);
                filters[filters.length - 1] = emojiExcludeFilter;
            }
        }
        super.setFilters(filters);
    }

    private EmojiExcludeFilter emojiExcludeFilter;

    private class EmojiExcludeFilter implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

    //Override BACK key to hide the virtual keyboard
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
//        {
//            AndroidThunkJava_HideVirtualKeyboardInput();
//            nativeVirtualKeyboardSendKey(KeyEvent.KEYCODE_BACK);
//        }
        return super.dispatchKeyEvent(event);
    }

    //extend associated InputConnection to handle special keys
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        return new VirtualKeyboardInputConnection(super.onCreateInputConnection(outAttrs), true, this);
    }

    private class VirtualKeyboardInputConnection extends InputConnectionWrapper
    {
        VirtualKeyboardInput owner;
        public VirtualKeyboardInputConnection(InputConnection target, boolean mutable, VirtualKeyboardInput editText)
        {
            super(target, mutable);
            owner = editText;
        }

        private void replaceSubstring(String newString)
        {
            StringBuffer text = new StringBuffer(owner.getText().toString());
            int selStart, selEnd;

            int a = owner.getSelectionStart();
            int b = owner.getSelectionEnd();

            selStart = Math.min(a, b);
            selEnd = Math.max(a, b);
            //Log.debug("VK: replaceSubstring selStart=" + selStart + " selEnd="+selEnd + " text="+text);

            if (selStart != selEnd)
            {
                //replace
                text.replace(selStart, selEnd, newString);
            }
            else if(newString.length() > 0)
            {
                //insert
                text.insert(selStart, newString);
            }
            else if(selStart > 0)
            {
                //delete last character
                selStart--;
                text.replace(selStart, selStart + 1, "");
            }

            if(newString.length() == 0)
            {
                //#jira UE-48948 Crash when pressing backspace on empty line
                selStart--;
            }
            //#jira UE-49120 Virtual keyboard number pad "kicks" user back to regular keyboard
            owner.getText().clear();
            owner.append(text.toString());
            owner.setSelection(selStart + 1);
        }


        //implement virtual keyboard's NUMERIC, BACKSPACE and ENTER keys
        @Override
        public boolean sendKeyEvent(KeyEvent event)
        {
            ////Log.debug("VK: sendKeyEvent " + event.getKeyCode() );
            if (event.getAction() == KeyEvent.ACTION_DOWN )
            {
                if(event.getKeyCode() >= KeyEvent.KEYCODE_0 && event.getKeyCode() <= KeyEvent.KEYCODE_9)
                {
                    char numChar = (char)('0' + (event.getKeyCode() - KeyEvent.KEYCODE_0));
                    replaceSubstring(String.valueOf(numChar));
                }
                else if(event.getKeyCode() >= KeyEvent.KEYCODE_NUMPAD_0 && event.getKeyCode() <= KeyEvent.KEYCODE_NUMPAD_9)
                {
                    char numChar = (char)('0' + (event.getKeyCode() - KeyEvent.KEYCODE_NUMPAD_0));
                    replaceSubstring(String.valueOf(numChar));
                }
                else if (event.getKeyCode() == KeyEvent.KEYCODE_DEL)
                {
                    //delete selected text / previous character
                    replaceSubstring("");
                }
                else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {

                    if (0 != (getInputType() & InputType.TYPE_TEXT_FLAG_MULTI_LINE))
                    {
                        //add new line
                        replaceSubstring("\n");
                    }
                    else
                    {
//                        AndroidThunkJava_HideVirtualKeyboardInput();
//                        nativeVirtualKeyboardSendKey(KeyEvent.KEYCODE_ENTER);
                    }
                }
            }
            return true;
        }

        //in latest Android, deleteSurroundingText(1, 0) will be called for BACKSPACE
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength)
        {
            ////Log.debug("VK: deleteSurroundingText");
            if (beforeLength == 1 && afterLength == 0)
            {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }
}