package com.andy.androinfo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.androinfo.R;
import com.andy.androinfo.utils.FileUtil;
import com.andy.androinfo.utils.ShellUtils;

import java.util.Set;

/**
 * Created by Administrator on 2018/5/14.
 */

public class BinderFrament extends AndyBaseFragment {

    private static final String TAG = BinderFrament.class.getSimpleName();

    private Context context;
    public static BinderFrament instance(String content) {
        BinderFrament fragment = new BinderFrament();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void initPrepare() {
    }

    @Override
    protected void onInvisible() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }
    }

    private Button home, cat;
    private EditText editText_command, editText_path;
    private TextView textView_exec_res;

    private void backHome(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);

        String action = intent.getAction();
        Set<String> categories = intent.getCategories();

        Process.killProcess(Process.myPid());
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_binder, null);

        editText_command = (EditText) view.findViewById(R.id.binder_command);
        editText_path = (EditText) view.findViewById(R.id.binder_path);
        textView_exec_res = (TextView) view.findViewById(R.id.binder_exec_res);

        home = (Button) view.findViewById(R.id.Binder_home);
        cat = (Button) view.findViewById(R.id.Binder_cat);

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_exec_res.setText("");

                final String command = editText_path.getText().toString();
                if (TextUtils.isEmpty(command) || command.trim().length() <= 0) {
                    Toast.makeText(context, "命令格式不正确", Toast.LENGTH_SHORT).show();
                    editText_path.setText("");
                    return;
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtil.readFile(command);
                    }
                }).start();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView_exec_res.setText("");

                String command = editText_command.getText().toString();
                if (TextUtils.isEmpty(command) || command.trim().length() <= 0) {
                    Toast.makeText(context, "命令格式不正确", Toast.LENGTH_SHORT).show();
                    editText_command.setText("");
                    return;
                }

                ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command, false);
                String res = "";
                if (commandResult.successMsg != null) {
                    res += commandResult.successMsg;
                    res += "\n";
                }

                if (commandResult.errorMsg != null) {
                    res += commandResult.errorMsg;
                    res += "\n";
                }
                textView_exec_res.setText(res);

            }
        });



        return view;
    }
}
