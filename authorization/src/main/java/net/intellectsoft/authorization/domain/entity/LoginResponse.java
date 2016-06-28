package net.intellectsoft.authorization.domain.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @Expose @SerializedName("token") String token;

    public String getToken() {
        return token;
    }

}
