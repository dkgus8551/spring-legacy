package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service // Component scan에 의해 bean 객체로 등록될 클래스를 지정
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Override
	public Member loginMember(String userId) {

		return null;
	}

	@Override
	public int insertMember(Member m) {

		return 0;
	}

	@Override
	public int updateMember(Member m) {

		return 0;
	}

	@Override
	public int idCheck(String userId) {

		return 0;
	}

	@Override
	public void updateMemberChagePwd() {

	}

}
