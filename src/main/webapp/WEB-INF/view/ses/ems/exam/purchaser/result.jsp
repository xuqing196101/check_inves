<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人成绩查询</title>
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
		//查询方法
		function query(){
			var relName = $("#relName").val();
			var status = $("#status").val();
			var code = $("#code").val();
			if((relName==""||relName==null)&&(status==""||status==null)&&(code==""||code==null)){
				layer.alert("请输入内容",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else{
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=basePath%>purchaserExam/selectPurchaserByTerm.do?relName="+relName+"&status="+status+"&code="+code,
		       		success:function(data){
		       			if(data){
		       				var html = "";
			            	for(var i=0;i<data.length;i++){
			            	  html = html + "<tr>";
			            	  html = html + "<td class='tc'>"+(i+1)+"</td>";
			            	  html = html + "<td class='tc'>"+data[i].relName +"</td>";
			            	  html = html + "<td class='tc'>"+data[i].code +"</td>";
			            	  html = html + "<td class='tc'>"+data[i].formatDate+"</td>";
			            	  html = html + "<td class='tc'>"+data[i].score+"</td>";
			            	  html = html + "<td class='tc'>"+data[i].status+"</td>";
			            	  html = html + "</tr>";
			            	}
			            	$("#purchaserResult").html(html);
		       			}
		       		}
		       	});
			}
		}
		
		//重置方法
		function reset(){
			$("#relName").val("");
			$("#code").val("");
			var status = document.getElementById("status").options;
			status[0].selected=true;
		}
	</script>
	
  </head>
  
  <body>
    <div class="container">
    	<div class="border1 col-md-8">
	    	姓名:<input type="text" id="relName" name="relName" class="mt10"/>
	    	试卷编号:<input type="text" id="code" name="code" class="mt10"/>
	    	考试状态:<select name="status" id="status">
	    		<option value="">请选择</option>
	    		<option value="及格">及格</option>
	    		<option value="不及格">不及格</option>
	    	</select>
	    	<button class="btn" type="button" onclick="query()">查询</button>
	  		<button class="btn" type="button" onclick="reset()">重置</button>
    	</div>
    </div>
    <div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w100">序号</th>
						<th class="info w100">采购人姓名</th>
						<th class="info w100">试卷编号</th>
					    <th class="info w100">考试时间</th>
						<th class="info w100">得分</th>
						<th class="info w100">考试状态</th>
					</tr>
				</thead>
				<tbody id="purchaserResult">
				
				</tbody>
			</table>
		</div>
  	</div>
  </body>
</html>
