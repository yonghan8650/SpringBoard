package com.itwillbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	//글쓰기(GET) : /board/register
	@GetMapping(value="/register")
	public void registerGET() throws Exception {
		logger.debug(" /board/register -> registerGET() 호출 ");
		logger.debug(" /board/register.jsp 뷰페이지 연결 ");
		
	}
	
}
