package com.oz.ozHouse.domain;

import java.time.format.DateTimeFormatter;

import com.oz.ozHouse.dto.ChattDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Chatt")
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chatt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int msgNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proNum")
	private OzMarketPro ozMarketPro; 
	
//	private int proNum;
	
	private String recipient;
	
	private String sender;
	
	private String msg;
	
	private String readStatus;
	
	private String file;
	
	private String inTime;
	
	public Chatt(ChattDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

        this.msgNum = dto.getMsgNum();
//        this.proNum = dto.getProNum();
        this.recipient = dto.getRecipient();
        this.sender = dto.getSender();
        this.msg = dto.getMsg();
        this.readStatus = dto.getReadStatus();
        this.file = dto.getFile();
        this.inTime = dto.getInTime().formatted(formatter);
	}
}