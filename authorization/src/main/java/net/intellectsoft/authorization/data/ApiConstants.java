package net.intellectsoft.authorization.data;

public interface ApiConstants {

    String BASE_API_PATH = "api/v1/";

    String LOGIN_PATH = BASE_API_PATH + "sessions";
    String LOGOUT_PATH = BASE_API_PATH + "sessions/my";

    String REGISTRATION_PATH = BASE_API_PATH + "users";
    String RESTORE_PASS_PATH = BASE_API_PATH + "";

}
