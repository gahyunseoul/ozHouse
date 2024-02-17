package com.oz.ozHouse.merchant.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oz.ozHouse.domain.Category;
import com.oz.ozHouse.dto.ApplicationDTO;
import com.oz.ozHouse.dto.CategoryDTO;
import com.oz.ozHouse.dto.InbrandDTO;
import com.oz.ozHouse.dto.MerchantDTO;
import com.oz.ozHouse.merchant.Exception.NotFoundMerNumException;
import com.oz.ozHouse.merchant.config.MerchantLoginBean;
import com.oz.ozHouse.merchant.service.MerInbrandService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/merchant/home/brand")
@RequiredArgsConstructor
public class MerInbrandController {
	private final MerInbrandService inbrandService;
	
	private HttpServletRequest goToMessage(HttpServletRequest req, String url, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return req;
	}
	
	@GetMapping("/applications")
	public String applications(HttpServletRequest req) {
		HttpSession session = req.getSession();
		MerchantLoginBean loginOk = (MerchantLoginBean)session.getAttribute("merLoginMember");
		if (loginOk == null) {
			req = goToMessage(req, "/merchant/login", "로그인 후 이용하시길 바랍니다.");
			return "message";
		}
		int num = loginOk.getMerNum();
		InbrandDTO dto = inbrandService.selectMer(num);
		if(dto != null) {	
			if(!dto.getInCancelDate().equals("0")) {	
				Calendar calNow = Calendar.getInstance();		
				Calendar calEnd= Calendar.getInstance();	     
				SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
				Date date;
				try {
					date = df.parse(dto.getInCancelDate());
					calEnd.setTime(date);
					calEnd.add(Calendar.MONTH, 3);
					if(calNow.before(calEnd)) {		
						req = goToMessage(req, "/merchant/home", "입점신청 취소, 또는 거절일로부터 3개월이 지나지 않아 신청이 불가합니다.");
						return "message";
					}else {								
						req.setAttribute("mer_num", num);
						return "merchant/brand/brand_application";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {	
				req = goToMessage(req,"merchant/home/brand/appliresult/" + num, "현재 입점신청 승인중이거나 승인된 상점입니다.");
				return "message";
			}
		}
		req.setAttribute("mer_num", num);
		return "merchant/brand/brand_application";
	}
	
	@PostMapping(value="/checkbusiness/{merNum}")
	public String brandApplicationOk(HttpServletRequest req, 
			@RequestParam Map<String, String> map, 
			@PathVariable(name="merNum")int merNum) {
		boolean result = false;
		try {
			result = inbrandService.searchComNum(merNum, map);
		}catch(NotFoundMerNumException e) {
			req = goToMessage(req, "/merchant/home", "존재하지 않는 판매자입니다.");
			return "message";
		}
		if(!result) {
			req = goToMessage(req, "/merchant/home/brand/applications", 
					"회원가입시의 사업자등록번호와 일치하지 않습니다.");
			return "message";
		}
		req.setAttribute("category", Category.values());
		req.setAttribute("inbrand_comnum1", map.get("inComnum1"));
		req.setAttribute("inbrand_comnum2", map.get("inComnum2"));
		req.setAttribute("inbrand_comnum3", map.get("inComnum3"));
		req.setAttribute("mer_num", merNum);
		return "merchant/brand/brand_inform";
	}

}