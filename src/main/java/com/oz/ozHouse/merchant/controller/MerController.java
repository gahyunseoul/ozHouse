package com.oz.ozHouse.merchant.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oz.ozHouse.merchant.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/merchant/home")
public class MerController {
	
	private final NoticeService noticeService;
	
	@GetMapping("")
	public String merchantMain(HttpServletRequest req) {
		req.setAttribute("noticeMainList", noticeService.simpleNotice());
		return "merchant/main/main";
	}
	
	@GetMapping("/notice-title/{noticeNum}")
	public String noticeTitle(HttpServletRequest req, @PathVariable("noticeNum") int noticeNum) {
		req.setAttribute("noticeDetail", noticeService.detailNotice(noticeNum));
		return "merchant/main/notice_detail";
	}
	
	@GetMapping("/notices")
	public String notice(HttpServletRequest req) {
		req.setAttribute("noticeList", noticeService.listNotice());
		return "merchant/main/notice";
	}
	
	@GetMapping("/notices/search")
    public ModelAndView findNotice(@RequestParam("search") String search, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("merchant/main/notice");
        modelAndView.addObject("noticeList", noticeService.findNotice(search));
        return modelAndView;
    }
}
