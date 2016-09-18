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
							html = html + "<tr>";
							html += "<td><input name='info' type='checkbox' value='"+data[i].id+"'/></td>";
							html = html+"<td>"+(i+1)+"</td>";
							html = html+"<td>"+data[i].name+"</td>";
							html = html+"<td>"+data[i].year+"</td>";
							html = html+"<td>"+data[i].startTrueDate+"</td>";
							var currentTime = new Date();
							if(currentTime.getTime()-data[i].startTime>0){
								html = html+"<td>已考</td>";
							}else{
								html = html+"<td>未考</td>";
							}
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
				alert("请选择一项");
				return;
			}
			if(count > 1){
				alert("只能选择一项");
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
			    		window.location.href="<%=path%>/purchaserExam/editNoTestPaper.html?id="+ids;
			    	}else if(data==0){
			    		alert("当前考卷不可编辑");
			    	}
		       	}
		    });
		}
		
		//查看参考人员
		function viewReference(){
			
		}
		
		//打印表格
		function printTable(){
			window.location.href="<%=path%>/purchaserExam/printTable.html";
		}
		
		
			function dayin() {
				var LODOP = getLodop();
				if (LODOP) {
					LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%",
							document.getElementById("div_print").innerHTML);
					LODOP.PREVIEW();
				}
			}
		 
	</script>

  </head>
  
  <body>
    <div style="width:960px;height:200px;font-size:24px;font-weight:700;line-height:200px;margin:0 auto;">
    	历史考卷列表
    </div>
    <div>
    	<input type="button" value="新建考卷" onclick="addPaper()"/>
    	<input type="button" value="编辑" onclick="editPaper()"/>
    	<input type="button" value="查看参考人员" onclick="viewReference()"/>
    	<input type="button" value="打印表格" onclick="printTable()"/>
    </div>
    <div id="div_print">
	    <table>
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
  </body>
</html>
