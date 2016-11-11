<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商务类专家题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="${pageContext.request.contextPath }/public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#error").hide();
			$("#topic").val("${topic}");
			var type_options = document.getElementById("questionTypeId").options;
			for(var i=0;i<type_options.length;i++){
				if($(type_options[i]).attr("value")=="${questionTypeId}"){
					type_options[i].selected=true;
				}
			}
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${commerceList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${commerceList.total}",
			    startRow: "${commerceList.startRow}",
			    endRow: "${commerceList.endRow}",
			    groups: "${commerceList.pages}">=5?5:"${commerceList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var topic = "${topic}";
						var questionTypeId = "${questionTypeId}";
			            location.href = "${pageContext.request.contextPath }/expertExam/searchComExpPool.do?topic="+topic+"&questionTypeId="+questionTypeId+"&page="+e.curr;
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
					url:"${pageContext.request.contextPath }/expertExam/deleteById.html?ids="+ids,
			       	success:function(data){
			       		layer.msg('删除成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		window.location.href="${pageContext.request.contextPath }/expertExam/searchComExpPool.html";
				       	}, 1000);
			       	}
		       	});
			});
		}
		
		//增加题库
		function addCommerce(){
			window.location.href = "${pageContext.request.contextPath }/expertExam/addCommerce.html";
		}
		
		//修改题库
		function editCommerce(){
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
				window.location.href = "${pageContext.request.contextPath }/expertExam/editCommerce.html?id="+str;
			}
		}
		
		//查看功能
		function view(obj){
			window.location.href = "${pageContext.request.contextPath }/expertExam/viewCom.html?id="+obj;
		}
		
		//下载模板
		function download(){
			window.location.href = "${pageContext.request.contextPath }/expertExam/loadExpertTemplet.html";
		}
		
		//导入商务类题目
		function poiExcel(){
			$.ajaxFileUpload({
			    url: "${pageContext.request.contextPath }/expertExam/importCom.do",  
			    secureuri: false,
			    fileElementId: "excelFile",
			    type: "POST",
			    dataType: "text",
			    success: function(data) {
			    	if(data.length<=5){
			    		layer.msg('导入成功',{offset: ['222px', '390px']});
				    	window.setTimeout(function(){
				       		window.location.href="${pageContext.request.contextPath }/expertExam/searchComExpPool.html";
				       	}, 1000);
			    	}else{
			    		var array = data.split(";");
			    		var html = "";
			    		for(var i=0;i<array.length-1;i++){
			    			html = html + "<tr class='tc'>";
			            	html = html + "<td>"+(i+1)+"</td>";
			            	if(i==0){
			            		html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
			            	}else{
			            		html = html + "<td>"+array[i].split(",")[0]+"</td>";
			            	}
			            	html = html + "<td>"+array[i].split(",")[1]+"</td>";
			            	html = html + "</tr>";
			    		}
			    		$("#errorResult").html(html);
			    		$("#errorNews").html("Excel表中以下题目的题干已存在");
			    		layer.open({
						 	type: 1, //page层
							area: ['430px', '200px'],
							closeBtn: 1,
							shade:0.01, //遮罩透明度
							moveType: 1, //拖拽风格，0是默认，1是传统拖动
							shift: 1, //0-6的动画形式，-1不开启
							offset: ['120px', '550px'],
							shadeClose: false,
							content : $('#error')
						});
						$(".layui-layer-shade").remove();
			    	}
			    }
			}); 
		}
		
		//重置方法
		function reset(){
			$("#topic").val("");
			var questionTypeId = document.getElementById("questionTypeId").options;
			questionTypeId[0].selected=true;
		}
		
		//按条件查询商务类专家题库
		function query(){
			var topic = $("#topic").val();
			var questionTypeId = $("#questionTypeId").val();
			if((topic==""||topic==null)&&(questionTypeId==""||questionTypeId==null)){
				window.location.href = "${pageContext.request.contextPath }/expertExam/searchComExpPool.do";
			}else{
				window.location.href = "${pageContext.request.contextPath }/expertExam/searchComExpPool.do?topic="+topic+"&questionTypeId="+questionTypeId;
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
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">题库管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>商务类专家题库列表</h2>
	   </div>
   
   		<h2 class="search_detail">
			<ul class="demand_list">
		    	<li>
			    	<label class="fl">名称：</label><span><input type="text" id="topic" class=""/></span>
			    </li>
			    <li>
			    	<label class="fl">题型：</label>
			    	<span>
				    	<select id="questionTypeId" class="w178">
						    <option value="">请选择</option>
						    <option value="1">单选题</option>
						    <option value="2">多选题</option>
				    	</select>
			   		</span>
			     </li>
			    <button type="button" onclick="query()" class="btn">查询</button>
			    <button type="button" onclick="reset()" class="btn">重置</button>
		    </ul>
		    <div class="clear"></div>
	 	</h2>
    
		<!-- 表格开始-->
  		<div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows add" type="button" onclick="addCommerce()">新增</button>
		    <button class="btn btn-windows edit" type="button" onclick="editCommerce()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="deleteById()">删除</button>
		    <button class="btn" type="button" onclick="download()">题目模板下载</button>
		    <button class="btn btn-windows input" type="button" onclick="poiExcel()">导入</button>
		    <input type="file" name="file" id="excelFile" style="display:inline"/>

		</div>
    
                       
    <div class="content table_box">
   		<table class="table table-bordered table-condensed table-hover">
    
		<thead>
			<tr class="info">
				<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
				<th class="w50">序号</th>
			    <th class="w60">题型</th>
				<th>题干</th>
				<th>选项</th>
			    <th>答案</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${commerceList.list }" var="c" varStatus="vs">
				<tr class="pointer">
					<td class="tc"><input type="checkbox" name="info" value="${c.id }" onclick="check()"/></td>
					<td class="tc" onclick="view('${c.id }')">${(vs.index+1)+(commerceList.pageNum-1)*(commerceList.pageSize)}</td>
					<td class="tc" onclick="view('${c.id }')">${c.examQuestionType.name }</td>
					<c:if test="${fn:length(c.topic)>20}">
						<td onclick="view('${c.id }')">${fn:substring(c.topic,0,20)}...</td>
					</c:if>
					<c:if test="${fn:length(c.topic)<=20}">
						<td onclick="view('${c.id }')">${c.topic }</td>
					</c:if>
					<c:if test="${fn:length(c.items)>20}">
						<td onclick="view('${c.id }')">${fn:substring(c.items,0,20)}...</td>
					</c:if>
					<c:if test="${fn:length(c.items)<=20}">
						<td onclick="view('${c.id }')">${c.items }</td>
					</c:if>
					<td class="tc" onclick="view('${c.id }')">${c.answer}</td>
					<td class="tc" onclick="view('${c.id }')"><fmt:formatDate value="${c.createdAt}" pattern="yyyy/MM/dd"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
     </div>
     <div id="pageDiv" align="right"></div>
   </div>
   
   		<div class="content padding-left-25 padding-right-25" id="error">
	  		<div id="errorNews"></div>
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th>序号</th>
						<th>题型</th>
						<th>题干</th>
					</tr>
				</thead>
				<tbody id="errorResult">
				</tbody>
			</table>
		</div>
  </body>
</html>
