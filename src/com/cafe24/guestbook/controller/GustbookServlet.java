package com.cafe24.guestbook.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.guestbook.dao.GuestbookDao;
import com.cafe24.guestbook.vo.GuestbookVo;
import com.cafe24.mvc.util.WebUtil;

@WebServlet("/gb")
public class GustbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "utf-8" );
		
		String actionName = request.getParameter( "a" );
		
		if( "deleteform".equals( actionName ) ) {
			WebUtil.forward(request, response, "/WEB-INF/views/deleteform.jsp" );
		} else if( "delete".equals( actionName ) ) {
			String no = request.getParameter( "no" );
			String password = request.getParameter( "password" );
			
			GuestbookVo vo = new GuestbookVo();
			vo.setNo( Long.parseLong( no ) );
			vo.setPassword( password );
			
			new GuestbookDao().delete( vo );
	
			WebUtil.redirect(request, response, "/guestbook2/gb" );
		} else if( "add".equals( actionName ) ) {
			String name = request.getParameter( "name" );
			String password = request.getParameter( "pass" );
			String content = request.getParameter( "content" );
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName( name );
			vo.setPassword( password );
			vo.setContent( content );
			
			new GuestbookDao().insert( vo );
			
			WebUtil.redirect(request, response, "/guestbook2/gb" );
		} else {
			/* default 요청 처리 (list) */
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();
			
			request.setAttribute( "list", list );

			WebUtil.forward(request, response, "/WEB-INF/views/index.jsp" );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
