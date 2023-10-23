package com.util;



public enum ApiRoute {

    AUTH_LOGIN("/login"),
    AUTH_REGISTER("api/auth/register"),

    USER_CREATE("api/users/create"),
    USER_READ("api/users/read/{id}"),
    USER_UPDATE("api/users/update/{id}"),
    USER_DELETE("api/users/delete/{id}"),

    MERCHANT_CREATE("api/merchants/create"),
    MERCHANT_READ("api/merchants/read/{id}"),
    MERCHANT_UPDATE("api/merchants/update/{id}"),
    MERCHANT_DELETE("api/merchants/delete/{id}");

    private final String route;
    private final static String baseUrlV1 = "/api/v1/";
    public final static String AUTH_URL = baseUrlV1.concat("auth");
    private final static String USER_URL = baseUrlV1.concat("users");
    private final static String MERCHANT_URL = baseUrlV1.concat("merchant");

    ApiRoute(String route) {
        this.route = route;
    }

    public String getRoute() {return route;}

}
