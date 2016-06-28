package net.intellectsoft.authorization.data;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private volatile String mToken;

    public TokenInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mToken != null) {
            request = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", mToken)
                    .build();
        }
        return chain.proceed(request);
    }

    public void setToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            mToken = "Bearer " + token;
        } else {
            mToken = null;
        }
    }

}