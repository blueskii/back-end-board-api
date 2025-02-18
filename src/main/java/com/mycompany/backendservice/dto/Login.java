package com.mycompany.backendservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Login {
	@NotBlank(message = "mid는 필수 입력 정보입니다.")
	private String mid;
	
	@NotBlank(message = "mpassword는 필수 입력 정보입니다.")
	//@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,10}$", message = "mpassword가 유효하지 않습니다.")
	private String mpassword;
}
