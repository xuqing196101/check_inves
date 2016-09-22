<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>历史考卷管理</title>
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
		//新增考卷
		function addPaper(){
			window.location.href="<%=path %>/purchaserExam/addPaper.html";
		}
		
		$(function(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/loadPaper.do",
	       		success:function(data){
	       			if(data){
	       				var html = "";
	       				for(var i=0;i<data.length;i++){
							html = html+"<tr>";
							html = html+"<td class='tc'><input name='info' type='checkbox' value='"+data[i].id+"'/></td>";
							html = html+"<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+(i+1)+"</td>";
							html = html+"<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].name+"</td>";
							html = html+"<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].year+"</td>";
							html = html+"<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].startTrueDate+"</td>";
							html = html+"<td class='tc pointer' onclick='view(\""+data[i].id+"\")'>"+data[i].status+"</td>";
							html = html+"</tr>";
						}
	       				$("#paperResult").html(html);
					}
				}
			});
		})

		//编辑考卷
		function editPaper() {
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请先选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			if(count > 1){
				layer.alert("只能修改一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
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
				url:"<%=path%>/purchaserExam/editSelectedPaper.html?id="+ids,
		       	success:function(data){
			    	if(data==1){
			    		layer.msg('当前考卷正在考试期间',{offset: ['222px', '390px']});
			    	}else if(data==2){
			    		window.location.href="<%=path%>/purchaserExam/editNoTestPaper.html?id="+ids;
			    	}else if(data==3){
			    		layer.msg('当前考卷已经过了考试有效期',{offset: ['222px', '390px']});
			    	}
		       	}
		    });
		}
		
		//查看考卷
		function view(obj){
			window.location.href = "<%=path%>/purchaserExam/viewPaper.html?id="+obj;
		}
		
		//查看参考人员
		function viewReference(){
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
				window.location.href = "<%=path%>/purchaserExam/viewReference.do?id="+str;
			}
		}
	</script>

  </head>
  
  <body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考卷管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>历史考卷列表</h2>
	   </div>
   </div>
   
   
    <div class="container">
   		<div class="col-md-10">
	    	<input type="button" class="btn btn-windows add" value="新建考卷" onclick="addPaper()"/>
	    	<input type="button" class="btn btn-windows edit" value="编辑" onclick="editPaper()"/>
	    	<input type="button" class="btn btn-windows pl13" value="查看参考人员" onclick="viewReference()"/>
    	</div>
    </div>
    
   <div class="container margin-top-5" id="div_print">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
	   		<table class="table table-bordered table-condensed">
		    	<thead>
		    		<th>选择</th>
		    		<th>序号</th>
		    		<th>考卷名称</th>
		    		<th>考卷年度</th>
		    		<th>考试日期</th>
		    		<th>考试状态</th>
		    	</thead>
		    	<tbody id="paperResult">
		    		
		    	</tbody>
		    </table>
   		</div>
    </div>
  </body>
</html>
