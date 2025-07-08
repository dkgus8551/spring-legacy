package com.kh.spring.board.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor // 의존성 자동 주입
@RequestMapping("/board") // 공용 주소 설정
@Slf4j
public class BoardController {

	private final BoardService boardService;
	// ServletContext : Application Scope를 가진 서블릿 전역에서 사용 가능한 객체
	private final ServletContext application;
	/*
	 * ResourceLoader
	 *  - 스프링에서 제공하는 자원 로딩 클래스
	 *  - classpath, file 시스템, url 등 다양한 경로의 자원을
	 *    동일한 인터페이스로 로드하는 메소드를 제공
	 * */
	private final ResourceLoader resourceLoader;
	
	// BoardType 전역객체 설정
	//  - 어플리케이션 전역에서 사용할 수 있는 BoardType 객체를 추가
	//  - 서버 가동 중에 1회 수행되어 application에 자동으로 등록
	@PostConstruct
	public void init() {
		// key=value, BOARD_CODE=BAORD_NAME
		// N=일반게시판, P=사진게시판
		Map<String,String> boardTypeMap = boardService.getBoardTypeMap();
		application.setAttribute("boardTypeMap", boardTypeMap);
		log.info("boardTypeMap: {}",boardTypeMap);
	}
	
	// 게시판 목록보기 서비스
	// 일반게시판, 사진게시판, 롤게시판 등 모든 목록보기 페이지를 하나의 메소드에서 경로를 매핑하는 방법
	// 1. GetMapping의 속성값을 "문자열 배열" 형태로 관리
	//@GetMapping({"/list/N","/list/P"}) // 권장 X
	// 2. GetMapping에 동적 경로 변수를 사용
	@GetMapping("/list/{boardCode}")
	//	- {boardCode}는 N, P, C, L, D 등 동적으로 바뀌는 모든 보드 코드 값을 저장할 수 있음
	//	- 선언한 동적 경로 변수는 @PathVariable 어노테이션으로 추출하여 사용할 수 있음
	//	- @PathVariable로 자원경로 추출 시, 추출한 변수는 model 영역에 자동으로 추가됨
	public String selectList(
			@PathVariable("boardCode") String boardCode,
			// currentPage
			//  - 현재 요청한 페이지 번호(페이징 처리에 필요한 변수)
			//  - 기본값은 1, 값을 전달하지 않은 경우 항상 1페이지로 요청하게 처리
			@RequestParam(value="currentPage",defaultValue="1") int currentPage,
			/*
			 *  @RequestParam Map<String,Object>
			 *  - 클라이언트가 요청 시 전달한 파라미터의 key, value를 Map 형태로 만들어서 대입
			 *  - 현재 메소드로 전달할 파라미터의 개수가 정해져 있지 않은 경우,
			 *    일반적인 vo 클래스로 바인딩되지 않는 경우 사용함(ex. 검색 파라미터 등)
			 *  - 반드시 @RequestParam 어노테이션을 추가해야 바인딩해줌
			 * */
			@RequestParam Map<String,Object> paramMap,
			Model model
			) {
		/*
		 * 업무 로직
		 * 1. 페이징 처리
		 * 		1) 현재 요청한 게시판 코드 및 검색 정보와 일치하는 게시글의 총 개수를 조회
		 * 		2) 게시글 개수, 페이지 번호, 기본 파라미터를 추가하여 페이징 정보(pageInfo) 객체 생성
		 * 2. 현재 요청한 게시판 코드와 일치하면서, 현재 페이지에 해당하는 게시글 정보를 조회
		 * 3. 게시글 목록 페이지로 게시글 정보, 페이징 정보, 검색 정보를 담아서 forward
		 * */
		paramMap.put("boardCode", boardCode); // 검색조건 + 게시판 코드
		int listCount = boardService.selectListCount(paramMap);
		int pageLimit = 10;
		int boardLimit = 10;
		
		// 페이지 정보 생성 템플릿을 이용하여 PageInfo 생성
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		List<Board> list = boardService.selectList(pi, paramMap);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		model.addAttribute("param", paramMap);
		
		return "board/boardListView";
	}

}
