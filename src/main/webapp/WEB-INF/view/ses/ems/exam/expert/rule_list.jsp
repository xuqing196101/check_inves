<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家考试规则列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = "${pageContext.request.contextPath }/expertExam/createRule.do?page="+e.curr;
			        }
			    }
			});
		})
		
		//全选方法
		function selectAll(){
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			if(selectAll.checked){
				for(var i = 0;i<info.length;i++){
					info[i].checked = true;
				}
			}else{
				for(var i = 0;i<info.length;i++){
					info[i].checked = false;
				}
			}
		}
		
		//新增
		function add(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/expertExam/judgeAdd.html",
				success:function(data){
	       			if(data==0){
	       				window.location.href = "${pageContext.request.contextPath }/expertExam/createRule.html";
	       			}else if(data==1){
	       				layer.alert("专家考试规则一年只能设置一次，请仔细检查",{offset: ['30%','40%']});
						$(".layui-layer-shade").remove();
	       			}
	       		}
	       	});
		}
		
		//修改
		function edit(){
			var count = 0;
			var info = document.getElementsByName("info");
			var str = "";
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count > 1){
				layer.alert("只能选择一项",{offset: ['30%','40%']});
				$(".layui-layer-shade").remove();
				return;
			}else if(count == 0){
				layer.alert("请先选择一项",{offset: ['30%','40%']});
				$(".layui-layer-shade").remove();
				return;
			}else{
				for(var i = 0;i<info.length;i++){
					if(info[i].checked == true){
						str = info[i].value;
					}
				}
				window.location.href = "${pageContext.request.contextPath }/expertExam/editRule.html?id="+str;
			}
		}
		
		//检查全选
		function check(){
			var count = 0;
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == false){
					selectAll.checked = false;
					break;
				}
			}
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == info.length){
				selectAll.checked = true;
			}
		}
	</script>

  </head>
  
  <body>
    <!--面包屑导航开始-->
   	<div class="margin-top-10 breadcrumbs ">
      	<div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考试规则管理</a></li>
		   </ul>
		<div class="clear"></div>
	  	</div>
   	</div>
   	<div class="container">
	  	<div class="headline-v2">
	   		<h2>专家考试规则列表</h2>
	   	</div>
   		
   		<!-- 表格开始-->
   		<div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
		    <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		</div>
		
		<div class="content table_box">
   		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr class="info">
					<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
					<th class="w50">序号</th>
				    <th>考试开始日期</th>
					<th>考试截止日期</th>
					<th>考卷年度</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list }" var="rule" varStatus="vs">
					<tr class="pointer">
						<td class="tc"><input type="checkbox" name="info" value="${rule.id }" onclick="check()"/></td>
						<td class="tc" onclick="view('${rule.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td class="tc" onclick="view('${rule.id }')"><fmt:formatDate value="${rule.startTime }" pattern="yyyy-MM-dd HH:mm"/></td>
						<td class="tc" onclick="view('${rule.id }')"><fmt:formatDate value="${rule.offTime }" pattern="yyyy-MM-dd HH:mm"/></td>
						<td class="tc" onclick="view('${rule.id }')">${rule.year }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
     </div>
     <div id="pageDiv" align="right"></div>
   </div>
  </body>
</html>
