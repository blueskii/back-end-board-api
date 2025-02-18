package com.mycompany.backendservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	@GetMapping("/board")
	public String getGreeting() {
		log.info("실행");
		return "docs/api-documentation.html";
	}
}
