package net.intellectsoft.authorization.domain;

import net.intellectsoft.authorization.domain.entity.LoginResponse;

import rx.Observable;

public interface AuthorizationApi {

    void setToken(String token);

    Observable<LoginResponse> login(String email, String password);

    Observable<Void> logout();

    Observable<Void> registration(String email, String password, String firstName, String lastName);

    Observable<Void> restorePassword(String email);

}
