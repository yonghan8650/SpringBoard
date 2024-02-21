package com.itwillbs.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.service.BoardService;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 서비스 객체 주입
	@Inject
	private BoardService bService;
	
	//글쓰기(GET) : /board/register
	@GetMapping(value="/register")
	public void registerGET() throws Exception {
		logger.debug(" /board/register -> registerGET() 호출 ");
		logger.debug(" /board/register.jsp 뷰페이지 연결 ");
		
	}
	//글쓰기(POST) : /board/register	
	@PostMapping(value="/register")
	public String registerPOST(BoardVO vo) throws Exception{
		logger.debug(" /board/register.jsp (submit) -> registerPOST() 호출 ");
		
		// 한글처리(필터) 생략
		// 전달정보( 글 정보 ) 저장
		logger.debug(" 전달정보 : " + vo);
		
		// 서비스 - DAO 글쓰기 동작 호출
		bService.regist(vo);
		logger.debug(" 글쓰기 완료 ");
		// 페이지 이동 (list)
		logger.debug(" list.jsp로 이동 ");
		return "redirect:/board/list";
	}
	
	
}
