<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购合同管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	  <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	  <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var contractType = $("#contractType").val();
		            location.href = "${pageContext.request.contextPath}/performance/selectAll.html?page="+e.curr+"&contractType="+contractType;
		        }
		    }
		});
	  
	  var conty = "${contractType}";
	  if(conty!=null && conty!=''){
	  	$("#contractType").val(conty);
	  }
  });
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 }
	}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/performance/view.html?id="+id;
  	}
    
  	function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/downloadUser/deleteDownloadUser.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
  	
  	function updateEcetion(){
  		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条履约修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				window.location.href="${pageContext.request.contextPath}/performance/createUpdateEx.html?ids="+ids;
			}
		}else{
			layer.alert("请选择要修改的履约",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	
  //鼠标移动显示全部内容
	function out(content){
		layer.msg(content, {
			    skin: 'demo-class',
				shade:false,
				area: ['600px'],
				time : 0    //默认消息框不关闭
		});//去掉msg图标
  	}
  
	function statements(){
		var ids =[];
		var purchaseType = "";
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			purchaseType = $(this).prev().text();
			alert(purchaseType);
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条合同结算",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(purchaseType=="单一来源"){
					alert(111);
				}else{
					alert(222);
				}
			}
		}else{
			layer.alert("请选择一条合同结算",{offset: ['222px', '390px'], shade:0.01});
		}
	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">履约情况管理</a></li><li><a href="#">履约情况列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
      <h2>履约情况列表
	  </h2>
   </div> 
<!-- 项目戳开始 -->
  <form action="${pageContext.request.contextPath}/performance/selectAll.html">
     <h2 class="search_detail">
    	<ul class="demand_list">
          <li class="fl"><label class="fl">合同类型：</label><span>
          <select name="contractType" id="contractType">
          	<option></option>
          	<option value="0">正常采购合同</option>
          	<option value="1">以厂代储合同</option>
          	<option value="2">合同储备合同</option>
          </select>
          </span></li>
	    	<input class="btn" type="submit" value="查询"/>
	    	<input class="btn" type="reset" value="重置"/> 	
    	</ul>
    	  <div class="clear"></div>
     </h2>
    </form>	
    <div class="col-md-12 pl20 mt10">
		<button class="btn" onclick="updateEcetion()">修改履约情况</button>
		<button class="btn btn-windows delete" onclick="delEcetion()">删除</button>
		<button class="btn" onclick="statements()">最终结算价格</button>
	</div>
   <div class="content table_box">
   	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info w50">序号</th>
			    <th class="info">合同编号</th>
			    <th class="info">合同名称</th>
				<th class="info">交货进度</th>
				<th class="info">资金支付百分比</th>
				<th class="info">合同草稿签订时间</th>
				<th class="info">正式合同签订时间</th>
				<th class="info">交付期</th>
				<th class="info">合同执行状态</th>
				<th class="info">质量检验</th>
			</tr>
		</thead>
		<c:forEach items="${performanceList}" var="performance" varStatus="vs">
			<tr>
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${performance.id}" /></td>
				<td onclick="view('${performance.id}')" class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<c:set value="${performance.contract.code}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>3}">
					<td onclick="view('${performance.id}')" onmouseover="out('${performance.contract.code}')" class="tc pointer ">${fn:substring(code,0,3)}...</td>
				</c:if>
				<c:if test="${length<=3}">
					<td onclick="view('${performance.id}')" onmouseover="out('${performance.contract.code}')" class="tc pointer ">${code}</td>
				</c:if>
				<c:set value="${performance.contract.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>4}">
					<td onclick="view('${performance.id}')" onmouseover="out('${performance.contract.name}')" class="tc pointer ">${fn:substring(name,0,4)}...</td>
				</c:if>
				<c:if test="${length<=4}">
					<td onclick="view('${performance.id}')" onmouseover="out('${performance.contract.name}')" class="tc pointer ">${name}</td>
				</c:if>		
				<td onclick="view('${performance.id}')" class="tc pointer">${performance.deliverySchedule}</td>
				<td onclick="view('${performance.id}')" class="tc pointer">${performance.fundsPaid}</td>
				<td onclick="view('${performance.id}')" class="tc pointer"><fmt:formatDate value='${performance.draftSignedAt}' pattern="yyyy年MM月dd日   HH:mm:ss" /></td>
				<td onclick="view('${performance.id}')" class="tc pointer"><fmt:formatDate value='${performance.formalSignedAt}' pattern="yyyy年MM月dd日   HH:mm:ss" /></td>
				<td onclick="view('${performance.id}')" class="tc pointer"><fmt:formatDate value='${performance.delivery}' pattern="yyyy年MM月dd日   HH:mm:ss" /></td>
				<td onclick="view('${performance.id}')" class="tc pointer">
					<c:if test="${performance.completedStatus=='0'}">合同执行中</c:if>
					<c:if test="${performance.completedStatus=='1'}">合同终止</c:if>
					<c:if test="${performance.completedStatus=='2'}">合同变更</c:if>
					<c:if test="${performance.completedStatus=='3'}">合同完成</c:if>
				</td>
				<td onclick="view('${performance.id}')" class="tc pointer">${performance.checkMass}</td>
			</tr>
		</c:forEach>
	</table>
     </div>
   <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
