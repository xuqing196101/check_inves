<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人题库页面</title>
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
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/getAllPurchaserQuestion.do",
	       		success:function(data){
	       			if(data){
	       				var html = "";
	       				for(var i=0;i<data.length;i++){
	       			      html = html + "<tr>";
		            	  html = html + "<td class='tc'><input type='checkbox' name='info' value='"+data[i].id+"'/></td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].examQuestionType.name+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].topic+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].items+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].answer+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].point+"</td>";
		            	  html = html + "</tr>";
						}
	       				$("#resultList").html(html);
					}
				}
			});
		})
	
		//采购人新增题库
		function add(){
			window.location.href = "<%=path%>/purchaserExam/addPurQue.html";
		}
		
		//采购人修改题库
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
				window.location.href = "<%=path%>/purchaserExam/editPurQue.html?id="+str;
			}
		}
		
		//采购人删除题库
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
			for(var i=0;i<info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=path%>/purchaserExam/deleteById.do?ids="+ids,
			       	success:function(data){
			       		layer.msg('删除成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		window.location.href="<%=path%>/purchaserExam/purchaserList.html";
				       	}, 1000);
			       	}
			    });
			});	
		}
		
		//按条件查询采购人题库
		function query(){
			var queName = $("#queName").val();
			var queType = $("#queType").val();
			if((queName==""||queName==null)&&(queType==""||queType==null)){
				$("#resultList").html("");
				return;
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=basePath%>purchaserExam/queryPurchaser.do?queName="+queName+"&queType="+queType,
	       		success:function(data){
	       			if(data){
	       				var html = "";
		            	for(var i=0;i<data.length;i++){
		            	  html = html + "<tr>";
		            	  html = html + "<td class='tc'><input type='checkbox' name='info' value='"+data[i].id+"'/></td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].examQuestionType.name+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].topic+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].items+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].answer+"</td>";
		            	  html = html + "<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].point+"</td>";
		            	  html = html + "</tr>";
		            	}
		            	$("#resultList").html(html);
	       			}
	       		}
	       	});
		}
		
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
		
		//导入Excel
		function poiExcel(){
			$.ajaxFileUpload({
			    url: "<%=path %>/purchaserExam/importExcel.do",  
			    secureuri: false,
			    fileElementId: "excelFile",
			    dataType: "json",
			    success: function(data) {  
			    	layer.msg('导入成功',{offset: ['222px', '390px']});
			    	window.setTimeout(function(){
			       		window.location.href="<%=path%>/purchaserExam/purchaserList.html";
			       	}, 1000);
			    }  
			}); 
		}
		
		//重置方法
		function reset(){
			$("#queName").val("");
			$("#queType").val("");
		}
		
		//查看采购人题库
		function view(obj){
			window.location.href = "<%=path%>/purchaserExam/view.html?id="+obj;
		}
	</script>

  </head>
  
  <body>
    <div class="container">
    	<div class="border1 col-md-5 ml30">
	    	名称:<input type="text" id="queName" class="mt10"/>
	    	题型:<select id="queType">
	    		<option value="">请选择</option>
	    		<option value="1">单选题</option>
	    		<option value="2">多选题</option>
	    		<option value="3">判断题</option>
	    	</select>
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="button" onclick="reset()" class="btn">重置</button>
    	</div>
    </div>
    <div class="container">
    	<div class="col-md-12 mt0">
	    	<input type="button" class="btn btn-windows add" value="新增题目" onclick="add()"/>
	    	<input type="button" class="btn btn-windows edit" value="修改" onclick="edit()"/>
	    	<input type="button" class="btn btn-windows delete" value="删除" onclick="deleteById()"/>
	    	<input type="file" name="file" id="excelFile" style="display:inline;"/>
	    	<input type="button" value="导入" class="btn" onclick="poiExcel()"/>
    	</div>
    </div>
    
    <div class="container margin-top-5">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
   		<table class="table table-bordered table-condensed">
	    	<thead>
	    		<tr>
		    		<th class="info"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
		    		<th class="info">题型</th>
		    		<th class="info">题干</th>
		    		<th class="info">选项</th>
		    		<th class="info">答案</th>
		    		<th class="info">分值</th>
	    		</tr>
	    	</thead>
	    	<tbody id="resultList">
	    		
	    	</tbody>
    	</table>
    	</div>
    </div>
  </body>
</html>
