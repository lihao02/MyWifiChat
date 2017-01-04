package com.lh.sky.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lh.sky.activity.R;

/**
 * Created by Sky000 on 2017/1/4.
 */
public class WifiPasswordInputView extends LinearLayout {
    private EditText passwordEditText;
    private Button connButton;
    private Context context;

    public WifiPasswordInputView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public void initView()
    {
        setOrientation(VERTICAL);

        passwordEditText = new EditText(context);
        passwordEditText.setTextSize(18);
        addView(passwordEditText);

        connButton = new Button(context);
        connButton.setTextSize(18);
        connButton.setText(getResources().getString(R.string.startConnect));
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(connButton, layoutParams);
    }

    public EditText getPasswordEditText()
    {
        return passwordEditText;
    }

    public Button getConnButton()
    {
        return connButton;
    }
}
