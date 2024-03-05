package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
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
	
	//리스트(GET) : /board/list
	@GetMapping(value="/list")	
	public void ListGET(Model model,HttpSession session) throws Exception{
		logger.debug(" /board/list -> ListGET() 호출 ");		
		logger.debug(" /board/list.jsp 연결");
		// 서비스 -> DAO 게시판 글 목록 가져오기
		List<BoardVO> boardList = bService.getList();
		logger.debug(" list.size : " + boardList.size());
		// 연결된 뷰페이지에 정보 전달(Model)
		model.addAttribute("boardList",boardList);
		
		
		session.setAttribute("viewUpdateStatus", 1);
	}
	
		//본문읽기(GET) //RequestParam
		@GetMapping(value="/read")
		public void readGet(Criteria cri, @RequestParam("bno") int bno, Model model, 
				HttpSession session) throws Exception {
			//ModelAttribute : 파라메터 저장 + 영역저장  (1:N관계)
			//RequestParam : 파라메터 저장 (1:1관계)
			
			logger.debug(" /board/read -> readGET() 호출 ");
			
			//전달 정보 저장
			logger.debug("bno : " + bno);
			
			int status = (Integer)session.getAttribute("viewUpdateStatus");
			
			if(status == 1) {
				// 서비스 -> DAO 게시판 글 조회수 1증가
				bService.updateViewcnt(bno);
				// 조회수 상태 0 : 조회수 증가X   , 1 : 조회수 증가 O
				session.setAttribute("viewUpdateStatus", 0);
			}
			// 서비스 -> DAO 게시판 글정보 조회 동작
			BoardVO vo = bService.read(bno);
			// 해당정보를 저장 -> 연결된 뷰페이지로 전달
			model.addAttribute("vo", vo);
			
			model.addAttribute("cri", cri); //뷰페이지로 페이징 처리 정보 전달
			//model.addAttribute(bService.read(bno));
			// 뷰페이지로 이동(/board/read.jsp)
		}
		
		//본문 수정(GET) : /board/modify?bno=000
		@GetMapping(value="/modify")
		public void modifyGET(@RequestParam("bno") int bno, Model model) throws Exception{
			logger.debug(" /board/modify -> modifyDET() 호출 ");
			
			// 전달받은 정보 (bno) 저장
			logger.debug("bno : " + bno);
			// 서비스 -> DAO 특정 글 정보 조회 동작
			// BoardVO vo = bService.read(bno);
			// 연결된 뷰페이지에 전달(Model)
			model.addAttribute(bService.read(bno));
			// 연결된 뷰페이지 (/board/modify.jsp);
		}
		
		//본문 수정(POST) : /board/modify
		@PostMapping(value="/modify")
		public String modifyPOST(BoardVO vo) throws Exception{
			logger.debug("/board/modify -> modifyPOST() 호출 ");
			
			// 한글처리 인코딩 (필터)
			// 전달 정보 저장(bno, title, writer, content)
			logger.debug("vo : " + vo);
			// 서비스 -> DAO 게시판 글 정보 수정
			bService.modify(vo);
			// 수정완료후에 list페이지로 이동(redirect)
			
			return "redirect:/board/list";
		}
		
		//본문 삭제(POST) : /board/remove	+(post)bno=000	
		@PostMapping(value="/remove")
		public String deletePOST(@RequestParam("bno") int bno) throws Exception {
			logger.debug(" /board/remove -> removePOST() 호출 ");
			
			// 전달 정보 저장
			logger.debug("bno : " + bno);
			
			// 서비스 -> DAO 게시판 글 삭제
			bService.remove(bno);
			
			// 삭제후 list 페이지로 이동
			return "redirect:/board/list";
		}
		
		//리스트Cri(GET) :  http://localhost:8088/board/listCri		//기본값 : page=1&pageSize=10
		//리스트Cri(GET) 2페이지 :  http://localhost:8088/board/listCri?page=2&pageSize=20
		@GetMapping(value="/listCri")	
		public void ListCriGET(Model model,HttpSession session, Criteria cri) throws Exception{
			logger.debug(" /board/listCri -> ListCriGET() 호출 ");		
			logger.debug(" /board/listCri.jsp 연결");
			
			// 페이징 처리 객체
			// Criteria cri = new Criteria();
			// cri.setPageSize(20);
			// 서비스 -> DAO 게시판 글 목록 가져오기
			// List<BoardVO> boardList = bService.getList(); all
			List<BoardVO> boardList = bService.getListCri(cri); //페이징
			logger.debug(" list.size : " + boardList.size());
			// 연결된 뷰페이지에 정보 전달(Model)
			model.addAttribute("boardList",boardList);
			
			model.addAttribute("cri", cri);
			
			
			session.setAttribute("viewUpdateStatus", 1);
		}
		
}
// http://localhost:8088/board/register