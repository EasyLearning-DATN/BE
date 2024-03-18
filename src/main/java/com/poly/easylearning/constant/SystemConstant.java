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

//	/* API path */
	public static final String API_ADMIN = "/admin";
	public static final String API_PUBLIC = "/public";
	public static final String API_MEMBER = "/member";
	public static final String VERSION_1 = "/v1";
	public static final String API_USER = "/user";
	public static final String API_ROLE = "/role";
	public static final String API_USERINFO = "/info";
	public static final String API_AVATAR = "/avatar";
	public static final String API_PASSWORD = "/password";
	public static final String LOCK_USER = "/lock";
	public static final String API_REPORT = "/report";
	public static final String API_CHANGE_STATUS = "/change-status";
	public static final String API_GET_TOKEN_FORGOT_PASSWORD = "/get-token-forgot-pass";
	public static final String API_FORGOT_PASSWORD = "/forgot-pass";
	public static final String API_LOGOUT = "/logout";
	public static final String API_SIGNUP = "/sign-up";
	public static final String API_AUTHENTICATION = "/authenticate";
	public static final String API_COMMENT = "/comment";
	public static final String API_LESSON = "/lesson";
	public static final String API_FILE_UPLOAD = "/file-upload";
	public static final String API_PACKAGE_UPGRADE = "/package-upgrade";
	public static final String API_VALID_TOKEN = "/valid-token";

	public static final String API_QUESTION_TYPE = "/question-type";
	public static final String API_QUESTION = "/question";
	public static final String API_CREATE_LIST = "/list";
	public static final String API_ANSWER = "/answer";
	public static final String API_REACTION = "/reaction";
	public static final String API_TEST = "/test";
	public static final String API_TEST_REPORT = "/test-report";
	public static final String API_VIEW_RESULT_TYPE = "/view-result-type";

	public static final String PATH_ID = "/{id}";
	public static final String ID = "id";

	/* Api Thanh toán */
	public static final String API_PAYMENT = "/payment";
	public static final String API_MOMO = "/momo";
//	Xử lý thanh toán thành công
	public static final String API_PAYMENT_SUCCESS = "/payment-success";

	/* Default Data */
	public static final String DEF_AVATAR = "https://i.imgur.com/6VBx3io.png";



	/* Security */
	public static final String BEARER = "Bearer ";
	public static final String AUTHORIZATION = "Authorization";

	//Param
	public static final String CURRENT_PAGE = "currentPage";
	public static final String LIMIT_PAGE = "limitPage";
	public static final String NAME = "name";
}
