package com.kh.spring.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // Component-scan에 의해 bean 객체 등록
@SessionAttributes({"loginUser"}) // Model에 들어가는 데이터 중 Session에 보관할 데이터를 설정하는 주석
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
	
	@RequestMapping(value="/member/login", method=RequestMethod.GET)
	public String loginMember() {
		return "member/login"; // forwarding할 jsp 페이지의 경로
	}
	
	// 스프링의 argument Resolver
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public String login(HttpServletRequest request) {
//		System.out.println(request.getParameter("userId"));
//		
//		return "main";
//	}
	
	// @RequestParam 어노테이션
	// servlet의 request,getParameter("키")로 뽑은 역할을 대신 수행해주는 어노테이션
	// 클라이언트가 요청한 값ㅇ르 대신 변환하여 바인딩해주는 역할은 argumentResolver가 수행
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public String login(@RequestParam(value="userId", defaultValue="mkm") String userId) {
//		System.out.println(userId);
//		return "main";
//	}
	// @RequestParam 생략
	// 매개변수의 이름과 일치하는 request의 파라미터 값을 추출하여 바인딩
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public String login(String userId, String userPwd) {
//		System.out.println(userId+" :: "+userPwd);
//		return "main";
//	}
	
	/*
	 * 커맨드 객체 방식
	 *  메소드의 매개변수로 vo클래스 타입을 지정하는 경우,
	 *  요청 전달값의 name 속성과 일치하는 vo클래스 필드의 Setter 함수를 호출하여 바인딩
	 *  
	 *  매개변수의 클래스와 일치하는 클래스의 기본생성자 호출
	 *  파라미터의 key값과 클래스의 필드 명이 일치하는 경우 setter 함수 호출
	 * */
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public String login(Member m) {
//		System.out.println(m.getUserId()+" :: "+m.getUserPwd());
//		return "main";
//	}
	
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public ModelAndView login(Member m, ModelAndView mv, Model model) {
//		/*
//		 * 로그인 비즈니스 작업 처리 완료 후, "응답 데이터"를 담아 응답 페이지로 redirect(재요청)
//		 * 
//		 * model은 내부에 데이터 추가 시 addAttribute() 함수를 사용하여 데이터를 추가
//		 * modelAndView는 데이터 추가 시 addObject()를 사용하며
//		 * view 설정 시 setViewName()을 사용
//		 * 
//		 * 응답 데이터를 담을 수 있는 객체
//		 * 1) Model
//		 *  - forwarding할 응답 뷰페이지로 전달하고자 하는 데이터를 맵 형식으로 담을 수 있는 객체
//		 *  - 기본 requestScope에, 설정을 통해 session Scope에도 데이터를 담을 수 있음
//		 *  - 클래스 선언부에 @SessionAttributes를 추가하면 데이터가 session Scope로 저장
//		 * 
//		 * 2) ModelAndView
//		 *  - ModelAndView에서 Model은 데이터를 담을 수 있는 맵 형태의 객체
//		 *  - View는 이동할 페이지에 대한 정보를 담고 있는 객체
//		 *  - 기본 requestScope에 데이터를 보관
//		 * */
//		
//		model.addAttribute("errorMsg", "오류 발생");
//		
//		mv.addObject("errorMsg", "오류 발생");
//		mv.setViewName("common/errorPage");
//		
//		return mv;
//	}
	
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
	@PostMapping("/member/login")
	public ModelAndView login(
			@ModelAttribute Member m,
			ModelAndView mv,
			Model model,
			HttpSession session, // 로그인 성공 시, 사용자 정보를 보관할 객체
			RedirectAttributes ra
			) {
		
		// 로그인 요청 처리
		Member loginUser = mService.loginMember(m);
		// 로그인 성공 시 회원정보, 실패 시 null값이 전달
		
		if(loginUser != null) {
			// 사용자 인증 정보(loginUser)를 session에 보관
//			session.setAttribute("loginUser", loginUser);
//			session.setAttribute("alertMsg", "로그인 성공!");
			model.addAttribute("loginUser", loginUser);
			ra.addFlashAttribute("alertMsg", "로그인 성공!");
			/*
			 * RedirectAttributes의 flashAttribbute는 데이터를 sessionScope에 담고
			 * Redirect가 완료되면, sessionScope에 있던 데이터를 requestScope로 변경
			 * */
		} else {
//			session.setAttribute("alertMsg", "로그인 실패!");
			ra.addFlashAttribute("alertMsg", "로그인 실패!");
		}
		
		mv.setViewName("redirect:/"); // 메인 페이지로 리다이렉트
		
		return mv;
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session, SessionStatus status) {
		// 로그아웃 방법
		// 1. session 내부의 인증 정보를 무효화
		session.invalidate(); // 세션 내부의 모든 데이터를 삭제
		status.setComplete(); // model로 session scope에 이관된 데이터를 비우는 메소드
		
		return "redirect:/";
	}
	
	@GetMapping("/member/insert")
	public String enrollForm() {
		
		return "member/memberEnrollForm";
	}
	
	@PostMapping("member/insert")
	public String insertMember(Member m,
			Model model,
			RedirectAttributes ra
			) {
		int result = mService.insertMember(m);
		
		String viewName = "";
		
		if(result>0) {
			ra.addFlashAttribute("alertMsg", "회원가입 성공 ^^");
			viewName = "redirect:/";
		}else {
			model.addAttribute("errorMsg","회원가입 실패 ㅠㅠ");
			viewName = "common/errorPage";
		}
		
		return viewName;
	}
	
	@GetMapping("/member/myPage")
	public String myPage() {
		return "member/myPage";
	}
	
	@PostMapping("/member/update")
	public String updateMember(
			Member m,
			RedirectAttributes ra,
			Model model
			) {
		int result = mService.updateMember(m);
		String url = "";
		
//		throw new RuntimeException("예외 발생");
		
		if(result>0) {
			// 업데이트 성공 시
			ra.addFlashAttribute("alertMsg", "내 정보 수정 성공");
			url="redirect:/";
		}else {
			model.addAttribute("errorMsg","회원정보 수정 실패");
			url="common/errorPage";
		}
		return url;
	}
	
	/*
	 * 스프링 예외 처리 방법
	 * 1. try-catch로 메소드별 예외 처리 -> 1순위로 적용
	 * 2. 하나의 컨트롤러에서 발생하는 예외들을 모아서 처리하는 방법 -> 2순위로 적용
	 * 	  컨트롤러에 예외 처리 메소드를 1개 추가한 후, @ExceptionHandler 추가
	 * 3. 어플리케이션 전역에서 발생하는 예외를 모아서 처리하는 방법 -> 3순위
	 *    새로운 클래스 작성 후, 클래스에 @ControllerAdvice를 추가
	 * */
	
//	@ExceptionHandler
//	public String exceptionHandler(Exception e, Model model) {
//		e.printStackTrace();
//		
//		model.addAttribute("errorMsg","서비스 이용 중 문제가 발생했습니다.");
//		return "common/errorPage";
//	}
	
	// 비동기 요청
	@ResponseBody // 반환되는 값이 값 그 자체임을 의미하는 주석
	@GetMapping("/member/idCheck")
	public String idCheck(String userId) {
		int result = mService.idCheck(userId); // 아이디 존재 시 1, 없다면 0
		
		// 컨트롤러에서 반환되는 값은 기본적으로 forward 경로 혹은 redirect를 위한 경로로 해석함
		// 즉, 반환되는 문자열 값은 "특정 경로"로서 인식을 하는데
		// 경로가 아닌 값 자체를 반환하기 위해서는 @ResponseBody가 필요함
		return result+""; // 1,0 -> /WEB-INF/views/1.jsp
	}
	
//	@PostMapping("/member/selectOne")
//	@ResponseBody
//	public HashMap<String, Object> selectOne(String userId){
//		HashMap<String, Object> map = mService.selectOne(userId);
//		
//		// jackson-databind를 활용하여, vo클래스, ArrayList, map 데이터를 자동으로 JSON 형태로 바인딩
//		return map;
//	}
	
	@PostMapping("/member/selectOne")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> selectOne(String userId){
		HashMap<String, Object> map = mService.selectOne(userId);
		
		// jackson-databind를 활용하여, vo클래스, ArrayList, map 데이터를 자동으로 JSON 형태로 바인딩
//		return map;
		
		ResponseEntity res = null;
		if(map!=null) {
			res = ResponseEntity
					.ok() // 응답상태 200
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.body(map);
		} else {
			res=ResponseEntity
					.notFound() // 응답상태404
					.build();
		}
		return res;
	}
}















