package com.poly.easylearning.constant;

public class SystemConstant {
	/* Status code */
	public static final Integer STATUS_CODE_SUCCESS = 200;
	public static final Integer STATUS_CODE_BAD_REQUEST = 400;
	public static final Integer STATUS_CODE_NOT_FOUND = 404;
	public static final Integer STATUS_CODE_INTERNAL = 500;
	public static final Integer STATUS_UNAUTHORIZED = 401;
	public static final Integer STATUS_FORBIDDEN = 403;
	public static final String ACTIVE = "ACTIVE";
	public static final String IN_ACTIVE = "IN_ACTIVE";

	/* API path */
	public static final String API_ADMIN = "/admin";
	public static final String API_PUBLIC = "/public";
	public static final String API_MEMBER = "/member";
	public static final String VERSION_1 = "/v1";
	public static final String API_USER = "/user";
	public static final String API_USERINFO = "/info";
	public static final String API_AVATAR = "/avatar";
	public static final String LOCK_USER = "/lock";
	public static final String API_FORGOT_PASSWORD = "/forgot-pass";
	public static final String API_LOGOUT = "/logout";
	public static final String API_SIGNUP = "/sign-up";
	public static final String API_AUTHENTICATION = "/authenticate";
	public static final String API_LESSON = "/lesson";
	public static final String API_PACKAGE_UPGRADE = "/package-upgrade";

	public static final String API_QUESTION_TYPE = "/question-type";

	public static final String PATH_ID = "/{id}";
	public static final String ID = "id";

	/* Default Data */
	public static final String DEF_AVATAR = "https://i.imgur.com/6VBx3io.png";



	/* Security */
	public static final String BEARER = "Bearer ";
	public static final String AUTHORIZATION = "Authorization";
}
