package net.intellectsoft.authorization.domain;

import net.intellectsoft.authorization.domain.entity.LoginResponse;
import net.intellectsoft.core.mvp.domain.LogicUnit;

import javax.inject.Inject;

import rx.Observable;

public class AuthorizationUnit extends LogicUnit {

    protected final AuthorizationApi authApi;
    protected final TokenStorage tokenStorage;

    @Inject
    public AuthorizationUnit(AuthorizationApi authApi, TokenStorage tokenStorage) {
        this.authApi = authApi;
        this.tokenStorage = tokenStorage;
    }

    public Observable<LoginResponse> login(String email, String password) {
        return authApi.login(email, password)
                .doOnNext(loginResponse -> tokenStorage.setToken(loginResponse.getToken()))
                .compose(applySchedulers());
    }

    public Observable<Void> logout() {
        return authApi.logout()
                .compose(applySchedulers());
    }

    public Observable<Void> registration(String email, String password, String firstName, String lastName) {
        return authApi.registration(email, password, firstName, lastName)
                .compose(applySchedulers());
    }

    public Observable<Void> restorePassword(String email) {
        return authApi.restorePassword(email)
                .compose(applySchedulers());
    }

    public Observable<Boolean> hasSavedAuthToken() {
        return tokenStorage.getToken()
                .map(token -> (token != null))
                .compose(applySchedulers());
    }

    public Observable<Boolean> restoreSavedAuthToken() {
        return tokenStorage.getToken()
                .map(token -> {
                    authApi.setToken(token);
                    return (token != null);
                })
                .compose(applySchedulers());
    }

}
