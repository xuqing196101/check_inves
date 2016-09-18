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
	<script type="text/javascript">
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
				alert("只能选择一项");
				return;
			}else if(count == 0){
				alert("请先选择一项");
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
				alert("请选择删除内容");
				return;
			}
			for(var i=0;i<info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/deleteById.do?ids="+ids,
		       	success:function(data){
			       	window.setTimeout(function(){
			       		window.location.href="<%=path%>/purchaserExam/purchaserExam.html";
			       	}, 1000);
		       	},
		       	error: function(){
					alert("删除失败");
				}
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
		            	  html = html + "<td><input type='checkbox' name='info' value='"+data[i].id+"'/></td>";
		            	  html = html + "<td>"+data[i].examQuestionType.name+"</td>";
		            	  html = html + "<td>"+data[i].topic+"</td>";
		            	  html = html + "<td>"+data[i].items+"</td>";
		            	  html = html + "<td>"+data[i].answer+"</td>";
		            	  html = html + "<td>"+data[i].point+"</td>";
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
			    	alert("导入成功");
			    }  
			}); 
		}
	</script>

  </head>
  
  <body>
    <div>
    	名称:<input type="text" id="queName"/>
    	题型:<select id="queType">
    		<option value="">请选择</option>
    		<option value="1">单选题</option>
    		<option value="2">多选题</option>
    		<option value="3">判断题</option>
    	</select>
    	<input type="button" value="查询" onclick="query()"/>
    	<input type="button" value="重置"/>
    </div>
    <div>
    	<input type="button" value="新增题目" onclick="add()"/>
    	<input type="button" value="修改" onclick="edit()"/>
    	<input type="button" value="删除" onclick="deleteById()"/>
    	<input type="file" name="file" id="excelFile"/>
    	<input type="button" value="导入" onclick="poiExcel()" />
    </div>
    <table>
    	<thead>
    		<th><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
    		<th>题型</th>
    		<th>题干</th>
    		<th>选项</th>
    		<th>答案</th>
    		<th>分值</th>
    	</thead>
    	<tbody id="resultList">
    		
    	</tbody>
    </table>
  </body>
</html>
