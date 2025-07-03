package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

@Controller // Component-scan에 의해 bean 객체 등록
public class MemberController {
	
	@Autowired
	private MemberService mService; // = new MemberServiceImpl();
	
	/*
	 * Spring의 DI(Dependency Injection)
	 *  - 의존성 주입
	 *  - 어플리케이션을 구성하는 객체를 개발자가 직접 생성하는 게 아닌,
	 *    스프링이 생성한 객체를 주입 받아서 사용하는 방식
	 *  - new 연산자를 직접 사용하지 않고, 자료형 선언만 한 후 @Autowired 어노테이션을 통해 주입 받음
	 * */
	
}
