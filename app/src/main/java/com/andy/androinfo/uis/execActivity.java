package com.andy.androinfo.uis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.andy.androinfo.R;
import com.andy.androinfo.utils.ShellUtils;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;


public class execActivity extends AppCompatActivity {

    private EditText cmd_text;
    private Button exec_btn;
    private TextView exec_reulst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exec);
        cmd_text = (EditText) findViewById(R.id.andy_exec_content);
        exec_btn = (Button) findViewById(R.id.andy_exec);
        exec_reulst = (TextView) findViewById(R.id.andy_exe_result);
        exec_reulst.setMovementMethod(ScrollingMovementMethod.getInstance());

        exec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exec(cmd_text.getText().toString().trim());
            }
        });
    }

    private void exec(String cmd){
        if (TextUtils.isEmpty(cmd)) {
            exec_reulst.setText("cmd is null");
            return;
        }
        try {

        }catch (Exception e) {
            e.printStackTrace();
        }

        String result = ShellUtils.do_exec(cmd);
        exec_reulst.setText(result);

    }
}
