package com.oz.ozHouse.client.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oz.ozHouse.client.repository.MemberRepository;
import com.oz.ozHouse.client.service.MemberService.IdExistException;
import com.oz.ozHouse.domain.Member;
import com.oz.ozHouse.dto.client.member.MemberPasswdUpdateDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageServiceImpl implements MypageService{
	
	// private final ModelMapper modelMapper;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 코드 수정 : 가독성
	@Override
	public boolean passUpdate(MemberPasswdUpdateDTO dto) {
		if (!passwordEncoder.matches(dto.getMemberPasswd(), 
						memberRepository.findByMemberId(dto.getMemberId()).getMemberId())) {
			// passwd 저장
			memberRepository.updateMemberPasswdByMemberId(passwordEncoder.encode(dto.getNewMemberPasswd()), dto.getMemberId());
			return false;
		}
		
		
		return true;
	}

}
