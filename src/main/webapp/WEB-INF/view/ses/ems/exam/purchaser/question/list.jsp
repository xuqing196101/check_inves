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
			$("#topic").val("${topic}");
			var type_options = document.getElementById("questionTypeId").options;
			for(var i=0;i<type_options.length;i++){
				if($(type_options[i]).attr("value")=="${questionTypeId}"){
					type_options[i].selected=true;
				}
			}
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${purchaserQuestionList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${purchaserQuestionList.total}",
			    startRow: "${purchaserQuestionList.startRow}",
			    endRow: "${purchaserQuestionList.endRow}",
			    groups: "${purchaserQuestionList.pages}">=5?5:"${purchaserQuestionList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var topic = "${topic}";
						var questionTypeId = "${questionTypeId}";
			            location.href = "<%=path%>/purchaserExam/purchaserList.do?topic="+topic+"&questionTypeId="+questionTypeId+"&page="+e.curr;
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
				       		window.location.reload();
				       	}, 1000);
			       	}
			    });
			});	
		}
		
		//按条件查询采购人题库
		function query(){
			var topic = $("#topic").val();
			var questionTypeId = $("#questionTypeId").val();
			if((topic==""||topic==null)&&(questionTypeId==""||questionTypeId==null)){
				window.location.href = "<%=basePath%>purchaserExam/purchaserList.do";
			}else{
				window.location.href = "<%=basePath%>purchaserExam/purchaserList.do?topic="+topic+"&questionTypeId="+questionTypeId;
			}
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
		
		//下载模板
		function download(){
			window.location.href = "<%=path%>/purchaserExam/loadPurchaserTemplet.html";
		}
		
		//重置方法
		function reset(){
			$("#topic").val("");
			var questionTypeId = document.getElementById("questionTypeId").options;
			questionTypeId[0].selected=true;
		}
		
		//查看采购人题库
		function view(obj){
			window.location.href = "<%=path%>/purchaserExam/view.html?id="+obj;
		}
	</script>

  </head>
  
  <body>
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">题库管理</a></li>
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
	    	名称:<input type="text" id="topic" class="mt10"/>
	    	题型:<select id="questionTypeId">
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
	   <div class="headline-v2">
	   		<h2>采购人题库列表</h2>
	   </div>
   </div>
    <div class="container">
    	<div class="col-md-12 mt0">
	    	<input type="button" class="btn btn-windows add" value="新增" onclick="add()"/>
	    	<input type="button" class="btn btn-windows edit" value="修改" onclick="edit()"/>
	    	<input type="button" class="btn btn-windows delete" value="删除" onclick="deleteById()"/>
	    	<div class="fr">
	    		<button class="btn" type="button" onclick="download()">题目模板下载</button>
	    		<span class="">
		    	  	<input type="file" name="file" id="excelFile" style="display:inline;"/>
		    	  	<input type="button" value="导入" class="btn btn-windows input" onclick="poiExcel()"/>
	    	  	</span>
	    	</div>
    	</div>
    </div>
    
    <div class="container">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
   		<table class="table table-bordered table-condensed table-hover">
	    	<thead>
		    	<th class="info"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
		    	<th class="info w50">序号</th>
		    	<th class="info w60 tc">题型</th>
		    	<th class="info">题干</th>
		    	<th class="info">选项</th>
		    	<th class="info w50">答案</th>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${purchaserQuestionList.list }" var="purchaser" varStatus="vs">
	    			<tr class="pointer">
	    				<td class="tc"><input type="checkbox" name="info" value="${purchaser.id }"/></td>
	    				<td class="tc w50" onclick="view('${purchaser.id }')">${(vs.index+1)+(purchaserQuestionList.pageNum-1)*(purchaserQuestionList.pageSize)}</td>
	    				<td class="w60 tc" onclick="view('${purchaser.id }')">${purchaser.examQuestionType.name }</td>
	    				<c:if test="${fn:length(purchaser.topic)>26}">
	    					<td onclick="view('${purchaser.id }')">${fn:substring(purchaser.topic,0,26)}...</td>
	    				</c:if>
	    				<c:if test="${fn:length(purchaser.topic)<=26}">
	    					<td onclick="view('${purchaser.id }')">${purchaser.topic }</td>
	    				</c:if>
	    				<c:if test="${fn:length(purchaser.items)>26}">
	    					<td onclick="view('${purchaser.id }')">${fn:substring(purchaser.items,0,26)}...</td>
	    				</c:if>
	    				<c:if test="${fn:length(purchaser.items)<=26}">
	    					<td onclick="view('${purchaser.id }')">${purchaser.items }</td>
	    				</c:if>
	    				<td class="tc w50" onclick="view('${purchaser.id }')">${purchaser.answer }</td>
	    			</tr>
	    		</c:forEach>
	    	</tbody>
    	</table>
    	</div>
    	<div id="pageDiv" align="right"></div>
    </div>
  </body>
</html>
