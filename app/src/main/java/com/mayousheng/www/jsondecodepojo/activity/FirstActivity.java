package com.mayousheng.www.jsondecodepojo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.User;
import com.mayousheng.www.jsondecodepojo.utils.CommonRequestUtils;
import com.mayousheng.www.jsondecodepojo.utils.Settings;
import com.mayousheng.www.jsondecodepojo.utils.UserUtils;

/**
 * Created by makai on 2017/12/6.
 */

public class FirstActivity extends BaseActivity implements View.OnClickListener {
    @ViewDesc(viewId = R.id.user_name)
    public EditText userName;
    @ViewDesc(viewId = R.id.user_pass)
    public EditText userPass;
    @ViewDesc(viewId = R.id.login)
    public Button login;
    @ViewDesc(viewId = R.id.register)
    public Button register;
    @ViewDesc(viewId = R.id.loading)
    public RelativeLayout loading;

    @Override
    protected int getLayout() {
        return R.layout.activity_first;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String session = Settings.getStringSetting(this, StaticParam.USER_SESSION);
        if (session != null && !session.isEmpty()) {
            toMain();
        } else {
            login.setOnClickListener(this);
            register.setOnClickListener(this);
            loading.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        loading(true);
        hideKeyboard();
        Editable userNameStr = userName.getText();
        Editable userPassStr = userPass.getText();
        if (userNameStr == null || userPassStr == null) {
            Toast.makeText(this, "用户名或者密码不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userNameStr.length() < 6 || userNameStr.length() > 99) {
            Toast.makeText(this, "用户名长度必须保持在6-100之间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassStr.length() < 6 || userPassStr.length() > 99) {
            Toast.makeText(this, "密码长度必须保持在6-100之间", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(userNameStr.toString(), userNameStr.toString(), userPassStr.toString()
                , StaticParam.REMBER_ME, StaticParam.TYPE_ANDROID);
        switch (view.getId()) {
            case R.id.login:
                login(user);
                break;
            case R.id.register:
                register(user);
                break;
            case R.id.loading:
                loading(false);
                break;
        }
    }


    private void register(final User user) {
        UserUtils.register(user, new CommonRequestUtils.Back() {
            @Override
            public void succeed() {
                login(user);
            }

            @Override
            public void field(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        loading(false);
                    }
                });
            }
        });
    }

    private void login(User user) {
        UserUtils.login(user, new CommonRequestUtils.SessionBack() {
            @Override
            public void succeed(String sessionId) {
                if (sessionId != null) {
                    Settings.saveSetting(getApplicationContext(), StaticParam.USER_SESSION, sessionId);
                }
                loading(false);
                toMain();
            }

            @Override
            public void field(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        loading(false);
                    }
                });
            }
        });
    }

    private void loading(final boolean isLoading) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(login.getWindowToken(), 0); //强制隐藏键盘
    }

    private void toMain() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        loading(false);
        super.onDestroy();
    }
}
