<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价信息列表页面</title>
<script type="text/javascript">
	$(function() {
	    laypage({
	      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	      pages : "${info.pages}", //总页数
	      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	      skip : true, //是否开启跳页
	      total : "${info.total}",
	      startRow : "${info.startRow}",
	      endRow : "${info.endRow}",
	      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
	      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
	        return "${info.pageNum}";
	      }(),
	      jump : function(e, first) { //触发分页后的回调
	    	if(!first){ //一定要加此判断，否则初始时会无限刷新
	      		location.href = "${pageContext.request.contextPath }/ob_project/biddingInfoList.do?name=${ name }&&startTime=${ startTimeStr }&&endTime=${ endTimeStr }&&page=" + e.curr;
	        }
	      }
	    });
	  });
	
	/** 全选全不选 */
	function selectAll(){
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
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
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
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
	
	//<!--搜索-->
	function query(){
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
		$("#queryForm").attr("action","${pageContext.request.contextPath}/ob_project/biddingInfoList.html");
		$("#queryForm").submit();
	}
	
	//  查看结果
	function findResult() {
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
	       var id = [];
		   $('input[name="chkItem"]:checked').each(function() {
		   		id.push($(this).val());
		   });
		   if(id.length == 1) {
			   var valueArr = id[0].split(',');
			   var status = valueArr[1];
			   if(status != '3' && status != '4'){
				   layer.alert("竞价结束才可以查看结果!");
				   return;
			   }
			   if(status == '3' || status == '4'){
			   	   // 查看结果
				  window.location.href="${pageContext.request.contextPath}/ob_project/selInfo.html?id="+valueArr[0];
			   }
	       } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
		   } else {
	          layer.alert("请选择要查看结果的模块", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
	        }
	    }
	//  查看
	function findIssueInfo() {
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
	       var id = [];
		   $('input[name="chkItem"]:checked').each(function() {
		   		id.push($(this).val());
		   });
		   if(id.length == 1) {
			   var valueArr = id[0].split(',');
			   var status = valueArr[1];
			   if(status == '3'){
				   layer.alert("请点击查看结果按钮!");
				   return;
			   }
			   if(status == '1' || status == '2' || status == '4' || status == '5' || status == '6' || status == '7'){
			   	   // 查看竞价信息
				   window.location.href="${pageContext.request.contextPath}/ob_project/findBiddingIssueInfo.html?id="+valueArr[0];
			   }
	       } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
		   } else {
	          layer.alert("请选择要查看的模块", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
	        }
	    }
	
		//重置按钮事件  
		function resetAll() {
			var authType = '${authType}';
			if (authType != '4') {
				layer.msg("只有资源服务中心才能操作");
				return;
			}
			$("#name").val("");
			$("#startTimeStr").val("");
			$("#endTimeStr").val("");
		}
</script>
</head>
<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
	<div class="container">
		<ul class="breadcrumb margin-left-0">
			<li>
				<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			</li>
			<li><a href="javascript:void(0)">保障作业</a></li>
			<li><a href="javascript:void(0)">网上竞价</a></li>
			<li><a href="javascript:jumppage('${pageContext.request.contextPath}/ob_project/biddingInfoList.html')">竞价看板</a></li>
			<li class="active"><a href="javascript:void(0)">竞价信息列表</a></li>
		</ul>
		<div class="clear"></div>
	</div>
    </div>
    
<!-- 竞价信息列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>竞价信息列表</h2>
	 </div>
		<div class="search_detail">
		<form id="queryForm" action="" method="post" class="mb0">
		<div class="m_row_5">
    <div class="row">
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">竞价标题：</div>
          <div class="col-xs-8 f0 lh0">
						<input id="name" name="name" value="${ name }" type="text" id="topic" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">竞价开始时间：</div>
          <div class="col-xs-8 f0 lh0">
						<input id="startTimeStr" name="startTimeStr" type="text" readonly="readonly" maxlength="19" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					  value="<fmt:formatDate value="${startTimeStr}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">竞价结束时间：</div>
          <div class="col-xs-8 f0 lh0">
						<input id="endTimeStr" name="endTimeStr" type="text" readonly="readonly" maxlength="19" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${endTimeStr}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-12 f0">
						<button type="button" onclick="query()" class="btn mb0 h32">查询</button>
			    	<button onclick="resetAll()" class="btn mb0 mr0 h32">重置</button>
					</div>
        </div>
      </div>
    </div>
    </div>
		</form>
		</div>
    <div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows apply" type="submit" onclick="findIssueInfo()">查看</button>
		<button class="btn btn-windows apply" type="submit" onclick="findResult()">查看结果</button>
	</div>   
	<!-- 表格开始 -->
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="22%">竞价标题</th>
		  <th class="info" width="15%">竞价开始时间</th>
		  <th class="info" width="15%">竞价结束时间</th>
		  <th class="info" width="15%">成交供应商</th>
		  <th class="info" width="15%">合格供应商</th>
		  <th class="info">竞价状态</th>
		  <!-- <th class="info">操作</th> -->
		</tr>
		</thead>
		<c:forEach items="${ info.list }" var="obProject" varStatus="vs">
			<tr>
			  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obProject.id},${obProject.status}" /></td>
			  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td class="tl">${ obProject.name }</td>
			  <td class="tc"><fmt:formatDate value="${ obProject.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc"><fmt:formatDate value="${ obProject.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tl">
				<c:if test="${obProject.closingSupplier==0}">
		   			0
		  		</c:if>
		  		<c:if test="${obProject.closingSupplier==null}">
		   			0
		  		</c:if>
		   		<c:if test="${obProject.closingSupplier!=0}">
		   			<a href="${pageContext.request.contextPath}/ob_project/supplierList.html?obProjectId=${obProject.id}&result=2">${obProject.closingSupplier}</a>
		  		</c:if>
			  </td>
			  <td class="tl">
			  	<c:if test="${obProject.qualifiedSupplier==0}">
			   		0
			  	</c:if>
			  	<c:if test="${obProject.qualifiedSupplier==null}">
			  		0
			  	</c:if>
			   	<c:if test="${obProject.qualifiedSupplier!=0}">
			   		 <a href="javascript:jumppage('${pageContext.request.contextPath}/ob_project/supplierList.html?obProjectId=${obProject.id}')">${obProject.qualifiedSupplier}</a>
			 	</c:if>
			  </td>
			  <td class="tl">
			  	<c:if test="${ obProject.status == 1 }">
			  		已发布 
			  	</c:if>
			  	<c:if test="${ obProject.status == 2 }">
			  		报价中
			  	</c:if>
			  	<c:if test="${ obProject.status == 7 }">
			  		报价中
			  	</c:if>
			  	<c:if test="${ obProject.status == 3 }">
			  		竞价结束
			  	</c:if>
			  	<c:if test="${ obProject.status == 4 }">
			  		已流拍
			  	</c:if>
			  	<c:if test="${ obProject.status == 5 }">
			  		待确认(第一轮)
			  	</c:if>
			  	<c:if test="${ obProject.status == 6 }">
			  		待确认(第二轮)
			  	</c:if>
			  </td>
			</tr> 
		</c:forEach>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>  
</body>
</html>