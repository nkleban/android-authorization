package net.intellectsoft.authorization.injection.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.intellectsoft.authorization.data.RetrofitAuthApi;
import net.intellectsoft.authorization.data.TokenInterceptor;
import net.intellectsoft.authorization.domain.AuthorizationApi;

import javax.inject.Singleton;

import dagger.Provides;

public class AuthNetworkModule {

    private final String baseUrl;

    public AuthNetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    TokenInterceptor providesTokenInterceptor() {
        return new TokenInterceptor();
    }

    @Provides
    @Singleton
    AuthorizationApi provideAuthorizationApi(Gson gson, TokenInterceptor tokenInterceptor) {
        return new RetrofitAuthApi(baseUrl, gson, tokenInterceptor);
    }

}
