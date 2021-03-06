package net.intellectsoft.authorization.data;

import net.intellectsoft.authorization.domain.entity.LoginResponse;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthorizationService {

    @FormUrlEncoded
    @POST(AuthorizationApiConstants.LOGIN_PATH)
    Observable<LoginResponse> login(@Field("email") String email,
                                    @Field("password") String password);

    @DELETE(AuthorizationApiConstants.LOGOUT_PATH)
    Observable<Void> logout();

    @FormUrlEncoded
    @POST(AuthorizationApiConstants.REGISTRATION_PATH)
    Observable<Void> registration(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("first_name") String firstName,
                                  @Field("last_name") String lastName);

    @POST(AuthorizationApiConstants.RESTORE_PASS_PATH)
    Observable<Void> restorePassword(@Field("email") String email);

}
