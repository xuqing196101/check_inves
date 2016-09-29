<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>专家考试成绩查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#userName").val("${userName}");
			var type_options = document.getElementById("userType").options;
			for(var i=0;i<type_options.length;i++){
				if($(type_options[i]).attr("value")=="${userType}"){
					type_options[i].selected=true;
				}
			}
			var status_options = document.getElementById("status").options;
			for(var i=0;i<status_options.length;i++){
				if($(status_options[i]).attr("value")=="${status}"){
					status_options[i].selected=true;
				}
			}
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${expertResultList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${expertResultList.total}",
			    startRow: "${expertResultList.startRow}",
			    endRow: "${expertResultList.endRow}",
			    groups: "${expertResultList.pages}">=3?3:"${expertResultList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var userName = "${userName}";
						var userType = "${userType}";
						var status = "${status}";
			            location.href = "<%=path%>/expertExam/result.do?userName="+userName+"&userType="+userType+"&status="+status+"&page="+e.curr;
			        }
			    }
			});		
		})
	
		//专家考试成绩按条件查询
		function queryResult(){
			var userName = $("#userName").val().trim();
			var userType = $("#userType").val();
			var status = $("#status").val();
			if((userName==""||userName==null)&&(userType==""||userType==null)&&(status==""||status==null)){
				window.location.href = "<%=path%>/expertExam/result.do";
				return;
			}else{
				window.location.href = "<%=path%>/expertExam/result.do?userName="+userName+"&userType="+userType+"&status="+status;
			}
			
		}
		
		//重置方法
		function resetResult(){
			$("#userName").val("");
			var userType = document.getElementById("userType").options;
			userType[0].selected=true;
			var status = document.getElementById("status").options;
			status[0].selected=true;
		}
	</script>
  </head>
  
  <body>
  	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">成绩管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
  
  	<div class="container">
  		<div class="border1 col-md-12 ml30">
	  		姓名:<input type="text" id="userName" class="mt10"/>
	  		专家类型:
	  		<select id="userType">
	  			<option value="">请选择</option>
	  			<option value="1">技术</option>
	  			<option value="3">商务</option>
	  			<option value="2">法律</option>
	  		</select>
	  		考试状态:
	  		<select id="status">
	  			<option value="">请选择</option>
	  			<option value="及格">及格</option>
	  			<option value="不及格">不及格</option>
	  		</select>
	  		<button class="btn" type="button" onclick="queryResult()">查询</button>
	  		<button class="btn" type="button" onclick="resetResult()">重置</button>
  		</div>
  	</div>
  	
  	<div class="container">
	   <div class="headline-v2">
	   		<h2>专家成绩列表</h2>
	   </div>
   	</div>
  	
  	<div class="container">
  		<div class="content padding-left-25 padding-right-25 padding-top-5">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<th class="info">序号</th>
					<th class="info">专家姓名</th>
					<th class="info">专家类型</th>
					<th class="info">证件类型</th>
					<th class="info">证件号</th>
					<th class="info">考试时间</th>
					<th class="info">得分</th>
					<th class="info">考试状态</th>
				</thead>
				<tbody>
					<c:forEach items="${expertResultList.list }" var="result" varStatus="vs">
						<tr>
							<td class="tc">${(vs.index+1)+(expertResultList.pageNum-1)*(expertResultList.pageSize)}</td>
							<td class="tc">${result.relName }</td>
							<td class="tc">${result.userDuty }</td>
							<td class="tc">${result.idType }</td>
							<td class="tc">${result.idNumber }</td>
							<td class="tc">${result.formatDate }</td>
							<td class="tc">${result.score }</td>
							<td class="tc">${result.status }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  </body>
</html>
