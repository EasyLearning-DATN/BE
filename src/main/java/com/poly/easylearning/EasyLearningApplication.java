package com.poly.easylearning;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.repo.IImageRepo;
import com.poly.easylearning.service.IImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@RequiredArgsConstructor
public class EasyLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyLearningApplication.class, args);
	}

	// init temp data
	private final IImageRepo imageRepo;

	@Bean
	public CommandLineRunner initializeData() {
		String url = "https://res-console.cloudinary" +
				".com/dvjmszx5r/media_explorer_thumbnails/f25d1d566a8d7efb83996a0d5e799808/detailed";
		return args -> {
			imageRepo.findByPublicId(DefaultValueConstants.IMAGE_USER_DEFAULT)
					.orElse(imageRepo.save(
							Image.builder()
									.publicId(DefaultValueConstants.IMAGE_USER_DEFAULT)
									.url(url)
									.build()
					));
		};
	}
}
