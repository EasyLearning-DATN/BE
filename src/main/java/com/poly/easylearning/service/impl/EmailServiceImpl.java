package com.poly.easylearning.service.impl;

import com.poly.easylearning.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements IEmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${app.config.domain}")
	private String domain;

	@Value("${app.config.path.valid_token}")
	private String pathValidToken;

	@Override
	public void send(String to, String email, String subject) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper =
					new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(fromEmail, "EasyLearning.com");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.error("failed to send email", e);
			throw new IllegalStateException("failed to send email");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String buildEmailWelcome(String email) {
		return null;
	}

	@Override
	public String buildEmailForgotPassword(String token) {
		return "<div style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">\n" +
				"    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#f4f4f4\">\n" +
				"        <tr>\n" +
				"            <td align=\"center\">\n" +
				"                <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #ffffff; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); margin: 20px auto;\">\n" +
				"                    <tr>\n" +
				"                        <td align=\"center\" style=\"padding: 20px;\">\n" +
				"                            <h1 style=\"color: #333;\">Chào mừng bạn đến với EasyLearning!</h1>\n" +
				"                            <p style=\"color: #666; line-height: 1.6;\">Cảm ơn bạn đã đăng ký để nhận các cập nhật và thông tin mới nhất từ chúng tôi.</p>\n" +
				"                            <p style=\"color: #666; line-height: 1.6;\"> Vui lòng ấn vào link này để xác thực:  <a href=\"" + domain + pathValidToken + token + "\" style=\"color: #007BFF; text-decoration: none;\">Link</a>" +
				"                            <p style=\"color: #666; line-height: 1.6;\">Nếu bạn có bất kỳ câu hỏi hoặc góp ý nào, đừng ngần ngại liên hệ với chúng tôi qua email <a href=\"youremail@gmail.com\" " +
				" style=\"color: #007BFF; text-decoration: none;\">"+ fromEmail + ".</a></p>\n" +
				"                            <p style=\"color: #666; line-height: 1.6;\">Cảm ơn bạn một lần nữa và chúng tôi rất mong được kết nối với bạn!</p>\n" +
				"                            <a href=\"[URL Của Trang Chính]\" style=\"display: inline-block; background-color: #007BFF; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 4px; cursor: pointer;\">Trang Chính</a>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"    </table>\n" +
				"</div>";
	}
}
