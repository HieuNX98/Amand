package com.amand.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClouldConfig {
	@Bean
	public Cloudinary cloudinary() {
		 Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
	                "cloud_name", "dgdzqjjfz", // insert here you cloud name
	                "api_key", "559899996414345", // insert here your api code
	                "api_secret", "QjyLylKlKMh_CU2S-rcrE--U3yw", // insert here your api secret
		 			"secure", true
				 ));
		 return cloudinary;
	}
}
