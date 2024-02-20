package com.oz.ozHouse.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.oz.ozHouse.domain.Chatt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본생성자 자동으로 생성
public class ChattDTO {
	private int msgNum;
	private int proNum;
	private String recipient;
	private String sender;
	private String msg;
	private String readStatus;
	private String file;
	private String inTime;
	
	public ChattDTO toDTO(Chatt chatt) {
		
		return ChattDTO.builder()
				.msgNum(chatt.getMsgNum())
				.proNum(chatt.getOzMarketPro().getProNum())
//				.proNum(chatt.getProNum())
				.recipient(chatt.getRecipient())
				.sender(chatt.getSender())
				.msg(chatt.getMsg())
				.readStatus(chatt.getReadStatus())
				.file(chatt.getFile())
				.inTime(chatt.getInTime())
				.build();
	}
	
	public ChattDTO(HttpServletRequest req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
		
//		this.msgNum = Integer.parseInt(req.getParameter("msgNum"));
		this.proNum = Integer.parseInt(req.getParameter("proNum"));
		this.recipient = req.getParameter("recipient");
		this.sender = req.getParameter("sender");
		this.msg = req.getParameter("msg");
		this.readStatus = "f";
//		this.readStatus = req.getParameter("readStatus");
		this.file = req.getParameter("file");
		this.inTime = LocalDate.now().format(formatter);
	}
	
	public void setFile(String file) {
		this.file = file;
	}
}