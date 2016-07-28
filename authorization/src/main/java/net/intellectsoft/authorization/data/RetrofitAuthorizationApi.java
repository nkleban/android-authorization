package net.intellectsoft.authorization.data;

import com.google.gson.Gson;

import net.intellectsoft.authorization.domain.AuthorizationApi;
import net.intellectsoft.authorization.domain.entity.LoginResponse;
import net.intellectsoft.core.mvp.domain.exception.NetworkException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetrofitAuthorizationApi implements AuthorizationApi {

    protected final String baseUrl;
    protected final AuthorizationService authService;
    protected final TokenInterceptor tokenInterceptor;
    protected final Gson gson;

    public RetrofitAuthorizationApi(String baseUrl, Gson gson, TokenInterceptor tokenInterceptor) {
        this.baseUrl = baseUrl;
        this.gson = gson;
        this.tokenInterceptor = tokenInterceptor;

        OkHttpClient.Builder builder = createOkHttpBuilder();
        builder.addInterceptor(tokenInterceptor);
        OkHttpClient client = builder.build();

        Retrofit retrofit = createRetrofitBuilder(client).build();
        authService = retrofit.create(AuthorizationService.class);
    }

    protected OkHttpClient.Builder createOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true);
        return builder;
    }

    protected Retrofit.Builder createRetrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public void setToken(String token) {
        tokenInterceptor.setToken(token);
    }

    @Override
    public Observable<LoginResponse> login(String email, String password) {
        return authService.login(email, password)
                .doOnError(this::wrapError);
    }

    @Override
    public Observable<Void> logout() {
        return authService.logout()
                .doOnError(this::wrapError);
    }

    @Override
    public Observable<Void> registration(String email, String password, String firstName, String lastName) {
        return authService.registration(email, password, firstName, lastName)
                .doOnError(this::wrapError);
    }

    @Override
    public Observable<Void> restorePassword(String email) {
        return authService.restorePassword(email)
                .doOnError(this::wrapError);
    }

    private Throwable wrapError(Throwable t) {
        Throwable wrappedError;
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            wrappedError = new NetworkException(httpException, httpException.code());
        } else if (t instanceof IOException) {
            wrappedError = new NetworkException(t, NetworkException.NETWORK_ERROR);
        } else {
            wrappedError = new NetworkException(t);
        }
        return wrappedError;
    }

}
