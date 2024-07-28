package com.example.erpdemo.rest;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/themeleaf")
public class ThemeleafController {

	@GetMapping("/hello")
	public String hello(Model model) {
		ArrayList<String> items = new ArrayList<>();
		items.add("first");
		items.add("second");
		model.addAttribute("name", "hitesh");
		model.addAttribute("user", "hitesh");
		model.addAttribute("items", items);
		return "hello";
	}
}
