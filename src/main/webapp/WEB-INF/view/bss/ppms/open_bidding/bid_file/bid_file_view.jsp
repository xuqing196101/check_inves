<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'bid_file.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
        
      function confirmOk(obj, id, flowDefineId){
      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId="+id+"&flowDefineId="+flowDefineId,
	 				dataType: 'json',  
	 	       		success:function(result){
	                   	layer.msg(result.msg,{offset: '222px'});
	                   	$("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
	                    $("#queren").remove();
	                },
	                error: function(result){
	                    layer.msg("确认失败",{offset: '222px'});
	                }
	 	       	});
	 		});
      }
      
      function jump(url){
       	$("#open_bidding_main").load(url);
	   }
  </script>
  </head>
  <body>
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${projectId}&flowDefineId=${flowDefineId }" >01、符合性</a>
		   <i></i>
		 </li>
		 <li>
		   <a href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${projectId}&flowDefineId=${flowDefineId }">02、符合性关联</a>
		   <i></i>							  
		 </li>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${projectId}&flowDefineId=${flowDefineId }">03、评标细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${projectId}&flowDefineId=${flowDefineId }" >
		     <c:if test="${type eq 'gkzb' }">04、招标文件</c:if>
		     <c:if test="${type eq 'jzxtp' }"> 04、竞谈文件</c:if>
		     <c:if test="${type eq 'dyly' }"> 04、单一来源文件</c:if>
		   </a>
		   <i></i>
		 </li>
		 <li>
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a></c:if>
		    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
		 </li>
	   </ul>
	 </div>
	 <div class="content">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr>
				  <th class="w50 info">序号</th>
				  <th class="info">初审项名称</th>
		          <th class="info">要求类型</th>
		          <th class="info">创建人</th>
				</tr>
			</thead>
			<c:forEach items="${list }" var="l" varStatus="vs">
			    <tr>
			    	<td class="tc w50">${vs.index+1 }</td>
			        <td >${l.name }</td>
			        <td class="tc">${l.kind }</td>
			        <td class="tc">${l.creater }</td>
			    </tr>
		    </c:forEach>
		</table>
   	</div>
  </body>
</html>
