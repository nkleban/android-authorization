package net.intellectsoft.authorization.injection.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.intellectsoft.authorization.data.RetrofitAuthorizationApi;
import net.intellectsoft.authorization.data.TokenInterceptor;
import net.intellectsoft.authorization.domain.AuthorizationApi;

import javax.inject.Singleton;

import dagger.Provides;

public class AuthorizationModule {

    private final String baseUrl;

    public AuthorizationModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    protected Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    protected TokenInterceptor providesTokenInterceptor() {
        return new TokenInterceptor();
    }

    @Provides
    @Singleton
    protected AuthorizationApi provideAuthorizationApi(Gson gson, TokenInterceptor tokenInterceptor) {
        return new RetrofitAuthorizationApi(baseUrl, gson, tokenInterceptor);
    }

}
