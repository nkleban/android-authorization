package net.intellectsoft.authorization.presentation.presenter;

import net.intellectsoft.authorization.domain.AuthorizationUnit;
import net.intellectsoft.authorization.domain.entity.LoginResponse;
import net.intellectsoft.authorization.presentation.ui.LoginMvpView;
import net.intellectsoft.core.mvp.presentation.BasePresenter;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    protected AuthorizationUnit authUnit;

    @Inject
    public LoginPresenter(AuthorizationUnit authUnit) {
        this.authUnit = authUnit;
    }

    public void login(String email, String password) {
        mvpView.showLoading();
        authUnit.login(email, password)
                .subscribe(this::onNext,this::onError, this::onCompleted);
    }

    protected void onNext(LoginResponse loginResponse) {}

    protected void onError(Throwable throwable) {
        mvpView.hideLoading();
        mvpView.showError(throwable);
    }

    protected void onCompleted() {
        mvpView.hideLoading();
        mvpView.onSuccessfulLogin();
    }

}
