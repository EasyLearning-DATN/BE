package com.poly.easylearning.service;

import java.math.BigDecimal;

public interface IEmailService {

	void send(String to, String email, String subject);

	String buildEmailWelcome(String email);

	String buildEmailForgotPassword(String password);

}
