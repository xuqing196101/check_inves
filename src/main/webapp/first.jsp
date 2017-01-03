<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    // 	response.sendRedirect("index/selectIndexNews.html");
    ServletContext sc = getServletContext();
    RequestDispatcher rd = null;
    rd = sc.getRequestDispatcher("/index/selectIndexNews.html");
    rd.forward(request, response);
%>