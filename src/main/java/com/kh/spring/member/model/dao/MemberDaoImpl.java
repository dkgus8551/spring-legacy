package com.kh.spring.member.model.dao;

import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Override
	public Member loginUser(String userId) {
		
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
