package net.intellectsoft.authorization.domain;

import rx.Observable;

public interface TokenStorage {

    Observable<Void> setToken(String token);

    Observable<String> getToken();

}
