<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
  </script>
  <body>
	    <h2 class="list_title">评审进度</h2>
  		<input type="hidden" id="projectId" value="${projectId}">
	   	<table class="table table-bordered table-condensed table-hover table-striped space_nowrap">
			<thead>
			<tr>
			  <!-- <th class="w50 info">序号</th> -->
			  <th class="info">包名</th>
			  <th class="info">总进度</th>
			  <th class="info">符合性审查进度</th>
			  <th class="info">经济技术审查进度</th>
			</tr>
			</thead>
			<c:forEach items="${reviewProgressList}" var="rp" varStatus="vs">
		       <tr>
		        <%-- <td class="tc w30">${vs.count} </td> --%>
		        <td class="tc">${rp.packageName}</td>
		        <td class="tc ">
				  <div class="col-md-12 padding-0">
				  	  <span class="fl padding-5">
				  	  	<c:if test="${rp.auditStatus == '0'}">未评审</c:if>
				  	  	<c:if test="${rp.auditStatus == '1'}">符合性审查审中</c:if>
				  	  	<c:if test="${rp.auditStatus == '2'}">符合性审完成</c:if>
				  	  	<c:if test="${rp.auditStatus == '3'}">经济技术审查中</c:if>
				  	  	<c:if test="${rp.auditStatus == '4'}">评审完成</c:if>
				  	  </span>
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${rp.totalProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${rp.totalProgress*100}%</span>
				  </div>
			    </td>
			    <td class="tc">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${rp.firstAuditProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${rp.firstAuditProgress*100}%</span>
				  </div>
			    </td>
			    <td class="tc">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${rp.scoreProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${rp.scoreProgress*100}%</span>
				  </div>
			    </td>
		      </tr>
			</c:forEach>
		</table>
  </body>
</html>
