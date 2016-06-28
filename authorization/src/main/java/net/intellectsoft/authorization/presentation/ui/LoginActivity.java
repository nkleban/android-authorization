package net.intellectsoft.authorization.presentation.ui;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.intellectsoft.authorization.R;
import net.intellectsoft.authorization.presentation.presenter.LoginPresenter;
import net.intellectsoft.core.mvp.presentation.ui.BaseAppCompatActivity;

public abstract class LoginActivity extends BaseAppCompatActivity implements LoginMvpView {

    protected EditText emailEdt;
    protected EditText passwordEdt;
    protected Button loginBtn;

    protected LoginPresenter presenter;

    protected TextView.OnEditorActionListener editorListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onLoginClick(loginBtn);
        }
        return false;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        bindViews();
        setListeners();
    }

    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    protected void bindViews() {
        emailEdt = (EditText) findViewById(R.id.edt_email);
        passwordEdt = (EditText) findViewById(R.id.edt_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
    }

    protected void setListeners() {
        loginBtn.setOnClickListener(this::onLoginClick);
        passwordEdt.setOnEditorActionListener(editorListener);
    }

    protected void onLoginClick(View v) {
        String email = emailEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString();
        presenter.login(email, password);
    }

}
