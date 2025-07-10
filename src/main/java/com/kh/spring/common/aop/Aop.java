package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
/*
 * Aspect
 *  - 공통 관심사를 모듈화한 클래스(로깅, 트랜잭션, 보안 검사)
 *  - 이 클래스 안에는 실제로 수행할 공통 로직(Advice)과
 *    그 로직을 적용할 지점(Pointcut)을 정의해야 함
 * */
public class Aop {
	/*
	 * JoinPoint
	 *  - 클라이언트가 호출 가능한 모든 메소드 실행 지점
	 *  - AOP가 적용될 수 있는 후보지들
	 * 
	 * Pointcut
	 *  - JoinPoint 중에서 실제 Advice가 실행될 지점
	 *  
	 * @Pointcut("excution([접근제한자] [반환형] 패키지명.클래스명.메소드명([매개변수])")
	 *  - *: 모든 값
	 *  - ..: 하위 패키지 포함, 매개 변수에 사용 시 0개 이상을 의미
	 * */
	
	@Pointcut("execution(* com.kh.spring.board..*Impl.*(..))")
	public void testPointcut() {}
	
	@Before("testPointcut()")
	public void start() {
		log.debug("=============service start=============");
	}
	
	@After("testPointcut()")
	public void end() {
		log.debug("=============service end=============");
	}
}