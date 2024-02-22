package com.oz.ozHouse.merchant.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oz.ozHouse.domain.Category;
import com.oz.ozHouse.domain.Merchant;
import com.oz.ozHouse.dto.MerchantDTO;
import com.oz.ozHouse.merchant.exception.NotFoundMerNumException;
import com.oz.ozHouse.merchant.repository.merchantRepository.MerchantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerMyInformServiceImpl implements MerMyInformService {
	private final MerchantRepository merRepository;
	
	@Override
	public MerchantDTO myInformView(int merNum) throws NotFoundMerNumException {
		Optional<Merchant> merchant = merRepository.findById(merNum);
		MerchantDTO dto = null;
		if(merchant.isPresent()) {
			dto = setMerchantEntity(merchant.get());
		}else {
			throw new NotFoundMerNumException("등록된 판매자 번호가 존재하지 않습니다.");
		}
		return dto;
	}
	
	private MerchantDTO setMerchantEntity(Merchant m) {
		if(m.getInbrand() == null || m.getInbrand().getInCancelDate()!=null) {
			return MerchantDTO.builder()
					.merNum(m.getMerNum())
					.merId(m.getMerId())
					.merPw(m.getMerPw())
					.merCompany(m.getMerCompany())
					.merIsbrand(m.getMerIsbrand())
					.merComnum1(m.getMerComnum().getMerComnum1())
					.merComnum2(m.getMerComnum().getMerComnum2())
					.merComnum3(m.getMerComnum().getMerComnum3())
					.merHp1(m.getMerHp().getPhoneNumber1())
					.merHp2(m.getMerHp().getPhoneNumber2())
					.merHp3(m.getMerHp().getPhoneNumber3())
					.merHomepage("미등록")
					.merManname("담당자 지정 전")
					.merManhp1("담당자 지정 전")
					.merManemail("담당자 지정 전")
					.merOthershop("미등록")
					.merFile("미등록")
					.merJoindate(m.getMerJoindate())
					.merInbranddate(m.getMerInbranddate())
					.merDeletedate(m.getMerDeletedate())
					.merAdress(m.getMerAdress())
					.merRegistration(m.getMerRegistration())
					.merName(m.getMerName())
					.merEmail(m.getMerEmail())
					.merBusinessPost(m.getMerBusinessPost())
					.build();
		}else {
			return MerchantDTO.builder()
					.merNum(m.getMerNum())
					.merId(m.getMerId())
					.merPw(m.getMerPw())
					.merCompany(m.getMerCompany())
					.merIsbrand(m.getMerIsbrand())
					.merComnum1(m.getMerComnum().getMerComnum1())
					.merComnum2(m.getMerComnum().getMerComnum2())
					.merComnum3(m.getMerComnum().getMerComnum3())
					.merHp1(m.getMerHp().getPhoneNumber1())
					.merHp2(m.getMerHp().getPhoneNumber2())
					.merHp3(m.getMerHp().getPhoneNumber3())
					.merHomepage(m.getInbrand().getInbrandInfo().getHomepage())
					.merManname(m.getInbrand().getInbrandInfo().getManagerName())
					.merManhp1(m.getInbrand().getInbrandInfo().getPhoneNum().phoneNumber1)
					.merManhp2(m.getInbrand().getInbrandInfo().getPhoneNum().phoneNumber2)
					.merManhp3(m.getInbrand().getInbrandInfo().getPhoneNum().phoneNumber3)
					.merManemail(m.getInbrand().getInbrandInfo().getManagerEmail())
					.merCategory(m.getInbrand().getInbrandInfo().getCategory())
					.merOthershop(m.getInbrand().getInbrandInfo().getOtherShop())
					.merFile(m.getInbrand().getInbrandInfo().getBrandFile())
					.merJoindate(m.getMerJoindate())
					.merInbranddate(m.getMerInbranddate())
					.merDeletedate(m.getMerDeletedate())
					.merAdress(m.getMerAdress())
					.merRegistration(m.getMerRegistration())
					.merName(m.getMerName())
					.merEmail(m.getMerEmail())
					.merBusinessPost(m.getMerBusinessPost())
					.build();
		}
	}
}