package com.oz.ozHouse.domain;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AttributeOverride(name = "regDate", column = @Column(name = "inAppliDate"))
public class Inbrand extends BaseEntity{

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inNum;
	private int merNum;
	private String inCompany;
	
	@Embedded
    @AttributeOverrides({
    		@AttributeOverride(name = "merComnum1", column = @Column(name = "inComnum1")),
    		@AttributeOverride(name = "merComnum2", column = @Column(name = "inComnum2")),
    		@AttributeOverride(name = "merComnum3", column = @Column(name = "inComnum3"))
    })
	private String inComnum;
	
	private String inHomepage;
	private String inManname;
	
	@Embedded
    @AttributeOverrides({
    		@AttributeOverride(name = "memberHp1", column = @Column(name = "inManhp1")),
    		@AttributeOverride(name = "memberHp2", column = @Column(name = "inManhp2")),
    		@AttributeOverride(name = "memberHp3", column = @Column(name = "inManhp3"))
    })
	private String inManhp;
	
	private String inManemail;
	private String inCategory;
	private String inOthershop;
	private String inSaleFile;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yy/MM/dd")
	private Date inCancelDate;
	
}
