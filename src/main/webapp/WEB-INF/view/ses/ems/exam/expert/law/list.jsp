<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>法律类专家题库</title>
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
			//alert("${list.pages}");
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = '<%=basePath%>expertExam/searchLawExpPool.do?page='+e.curr;
			        }
			    }
			});
		})	
	
		//删除题库中的题目
		function deleteById(){
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请选择删除内容",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			for(var i=0;i < info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=path%>/expertExam/deleteById.html?ids="+ids,
			       	success:function(data){
			       		layer.msg('删除成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		window.location.href="<%=path%>/expertExam/searchLawExpPool.html";
				       	}, 1000);
			       	}
		       	});
			});
		}
		
		//增加题库
		function addLaw(){
			window.location.href = "<%=path%>/expertExam/addLaw.html";
		}
		
		//修改题库
		function editLaw(){
			var count = 0;
			var info = document.getElementsByName("info");
			var str = "";
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count > 1){
				layer.alert("只能选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else if(count == 0){
				layer.alert("请先选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else{
				for(var i = 0;i<info.length;i++){
					if(info[i].checked == true){
						str = info[i].value;
					}
				}
				window.location.href = "<%=path%>/expertExam/editLaw.html?id="+str;
			}
		}
		
		//查看功能
		function view(obj){
			window.location.href = "<%=path%>/expertExam/viewLaw.html?id="+obj;
		}
		
		//下载模板
		function download(){
			window.location.href = "<%=path%>/expertExam/loadExpertTemplet.html";
		}
		
		//导入法律类题目
		function poiExcel(){
			$.ajaxFileUpload({
			    url: "<%=path %>/expertExam/importLaw.html",  
			    secureuri: false,
			    fileElementId: "excelFile",
			    dataType: "json",
			    success: function(data) {  
			    	layer.msg('导入成功',{offset: ['222px', '390px']});
			    	window.setTimeout(function(){
			       		window.location.href="<%=path%>/expertExam/searchLawExpPool.html";
			       	}, 1000);
			    }  
			}); 
		}
	</script>
  </head>
  
  <body>
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">专家考试</a></li><li class="active"><a href="#">题库管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>法律类题库列表</h2>
	   </div>
   </div>
	<!-- 按钮开始-->
   <div class="container">
   		<div class="col-md-12">
		    <button class="btn btn-windows add" type="button" onclick="addLaw()">新增题目</button>
		    <input type="file" name="file" id="excelFile" style="display:inline"/>
		    <button class="btn btn-windows add" type="button" onclick="poiExcel()">Excel导入</button>
			<button class="btn btn-windows edit" type="button" onclick="editLaw()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="deleteById()">删除</button>
			<button class="btn btn-windows pl13" type="button" onclick="download()">专家题库模板下载</button>
		</div>
    </div>
                       
    <div class="container margin-top-5" id="div_print">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
   		<table class="table table-bordered table-condensed">
    
		<thead>
			<tr>
				<th class="info w50">选择</th>
			    <th class="info" width="50">题型</th>
				<th class="info" width="100">题干</th>
				<th class="info">选项</th>
			    <th class="info">答案</th>
				<th class="info">分值</th>
				<th class="info">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list.list }" var="l">
				<tr>
					<td class="tc pointer"><input type="checkbox" name="info" value="${l.id }"/></td>
					<td class="tc pointer" onclick="view('${l.id }')">${l.examQuestionType.name }</td>
					<td class="tc pointer" onclick="view('${l.id }')">${l.topic }</td>
					<td class="tc pointer" onclick="view('${l.id }')">${l.items }</td>
					<td class="tc pointer" onclick="view('${l.id }')">${l.answer}</td>
					<td class="tc pointer" onclick="view('${l.id }')">${l.point }</td>
					<td class="tc pointer" onclick="view('${l.id }')"><fmt:formatDate value="${l.createdAt}" pattern="yyyy年MM月dd日   HH:mm:ss"/> </td
				</tr>
			</c:forEach>
		</tbody>
	</table>
     </div>
   	<div id="pageDiv" align="right"></div>
   </div>
  </body>
</html>
