<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价列表页面</title>
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
	      		location.href = "${pageContext.request.contextPath }/supplierQuote/list.do?name=${ name }&&createTime=${ createTimeStr }&&page=" + e.curr;
	        }
	      }
	    });
	    
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
	
	//搜索
	function query(){
		$("#queryForm").attr("action","${pageContext.request.contextPath}/supplierQuote/list.html");
		$("#queryForm").submit();
	}
	
	var sysDate;
	// 页面加载完毕倒计时
	$(function(){
		// 获取当前页面各个时间段信息
		var timeArray = [];
		var timeListObject = ${timeListObject};
		// 遍历获取当前页面时间段
		$.each(timeListObject,function(index,ele){
			// 获取报价开始时间
			timeArray.push(ele.quotoTimeDate/1000);
			// 获取报价结束时间
			timeArray.push(ele.endQuotoTimeDate/1000);
			// 获取第一轮确认时间
			timeArray.push(ele.confirmTime/1000);
			// 获取第二轮确认时间
			timeArray.push(ele.secondConfirmTime/1000);
		})
		$("#aaa").val(timeArray);
		// 获取系统当前时间
		$.ajax({
			url:"${pageContext.request.contextPath}/supplierQuote/getSysTime.do",
			type: "POST",
			success:function(data){
				sysDate = data;
			}
		});
		// 定时执行
		setInterval(function() {
			sysDate = sysDate + 1000;
			var sysDateInt = parseInt(sysDate / 1000);
			var times = $.inArray(sysDateInt, timeArray);
			if(times >= 0){
				reloadPage();
			}
			$("#bbb").val(sysDateInt);
       	},1000);
		
		function reloadPage(){
			// 刷新页面
			window.location.reload();
		}

	});
	
	// 开始报价
	function beginQuote() {
	       var id = [];
		   $('input[name="chkItem"]:checked').each(function() {
		   		id.push($(this).val());
		   });
		   if(id.length == 1) {
			   var valueArr = id[0].split(',');
			   var status = valueArr[1];
			   var remark = valueArr[2];
			   var quotoEndTime = valueArr[3];
			   // 得到报价截止的毫秒数
			   var quotoEndTimeMill = (new Date(quotoEndTime)).getTime();
			   // 竞价结束
			   if(status == '3'){
				   layer.alert("竞价已结束 ！");
				   return;
			   }
			   // 该项目已流拍
			   if(status == '4'){
				   layer.alert("对不起，项目已流拍 ！");
				   return;
			   }
			   if(status == '1'){
				   layer.alert("对不起，报价时间还未开始，请您等待 ！");
				   return;
			   }
			   
			   if(status == '2'){
				   if(remark == '1'){
					   layer.alert("已报价，请等待确认结果 ！");
					   return;
				   }
				   if(remark == '2'){
					   layer.alert("对不起，您未在规定的时间内完成报价！");
					   return;
				   }
			   }
			   
			   if(status == '5' && remark == '1'){
				   layer.alert("报价已结束，请您确认结果 ！");
				   return;
			   }
			   // 报价时间已结束
			   if(status != '2'){
				   layer.alert("报价已结束 ！");
				   return;
			   }
			   // 开始报价
			   if(status == '2'){
				   window.location.href="${pageContext.request.contextPath}/supplierQuote/beginQuoteInfo.html?id="+valueArr[0]+"&&quotoEndTimeMill="+quotoEndTimeMill;
			   }
	       } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
		   } else {
	          layer.alert("请选择开始报价的模块", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
	        }
	    }
	
	// 确认结果
	function confirmResult() {
	       var id = [];
		   $('input[name="chkItem"]:checked').each(function() {
		   		id.push($(this).val());
		   });
		   if(id.length == 1) {
			   var valueArr = id[0].split(',');
			   var status = valueArr[1];
			   var remark = valueArr[2];
			   // 报价时间还未开始
			   if(status == '1'){
				   layer.alert("对不起，报价时间还未开始，请您等待 ！");
				   return;
			   }
			   
			   // 确认结果前做报价判断
			   if(status == 2){
				   if(remark == '0'){
					   layer.alert("对不起，您还未参与报价！");
					   return;
				   }
				   if(remark == '1'){
					   layer.alert("确认时间未到，请等待确认结果 ！");
					   return;
				   }
			   }
			   // 竞价结束
			   if(status == '3'){
				   layer.alert("竞价已结束 ！");
				   return;
			   }
			   // 该项目已流拍
			   if(status ==  '4'){
				   layer.alert("对不起，项目已流拍 ！");
				   return;
			   }
			   
			   // 第一轮确认时间未到
			   if((status != '5' && remark == '1')){
				   layer.alert("对不起，确认时间未到不能确认结果 ！");
				   return;
			   }
			   
			   //-------------------------第一 轮----------------//
			   // 第一轮确认后，第二轮未开始
			   if((status == '5' && remark == '5') || (status == '5' && remark == '4')){
				   layer.alert("第一轮已确认 ！");
				   return;
			   }
			   // 第二轮开始确认结果，改比接受或者全接受状态都有可能进入第二轮
			   if(remark == '3'){
				   layer.alert("对不起，第一轮已放弃不能进入第二轮确认 ！");
				   return;
			   }
			   // 第二轮确认时间未到
			   if((status != '6' && remark == '4') || (status != '6' && remark == '5')){
				   layer.alert("对不起，第二轮确认时间未到不能确认结果 ！");
				   return;
			   }
			   
			   //-------------------------第二轮----------------//
			   // 第一轮确认后，第二轮未开始
			   if((status == '6' && remark == '42') || (status == '6' && remark == '52')){
				   layer.alert("第二轮已确认，请您等待竞价结束 ！");
				   return;
			   }
			   
			   
			   // 如果供应商未报价则显示
			   if(status == '5' && remark == '0'){
				   layer.alert("对不起，您未报价不能确认结果 ！");
				   return;
			   }
			   //未中标状态
			   if(remark == '666'){
				   layer.alert("对不起，本次竞价项目您未中标 ！");
				   return;
			   }
			   
			   // 确认结果
			   if((status == '5' && remark == '1') || (status == '6' && remark == '5') || (status == '6' && remark == '4')){
			   	   $.ajax({
			   		   type:'POST',
			   		   url:'${pageContext.request.contextPath}/supplierQuote/checkConfirmResult.do',
			   		   data:{projectId:valueArr[0]},
			   		   dataType:'json',
			   		   success:function(data){
			   			   if(data.status == 1 || data.status == 2){
			   					confirmResult();
			   			   }
			   			   if(data.status == 3){
			   					layer.alert(data.msg);
			   			   }
			   			   if(data.status == 4){
			   					layer.alert(data.msg);
			   			   }
			   			   if(data.status == 5){
			   					layer.alert(data.msg);
			   			   }
			   			   if(data.status == 0){
			   					layer.alert(data.msg);
			   			   }
			   			   if(data.status == 6){
			   					layer.alert(data.msg);
			   			   }
			   		   }
			   	   });
			   }
			   
			   // 确认结果
			   function confirmResult(){
				   window.location.href = "${pageContext.request.contextPath}/supplierQuote/confirmResult.html?projectId="+valueArr[0];
			   }
			   //-------------------------放弃情况提示----------------//
			   // 第一轮确认时：点击放弃按钮
			  /*  if(status == '5' && remark == '3'){
				   layer.alert("您已放弃第一轮确认结果 ！");
				   return;
			   }
			   if(remark == '3'){
				   layer.alert("您已放弃第一轮确认结果 ！");
				   return;
			   }
			   if(status == '6' && remark == '32'){
				   layer.alert("您已放弃第二轮确认结果 ！");
				   return;
			   }
			   if(remark == '32'){
				   layer.alert("您已放弃第二轮确认结果 ！");
				   return;
			   } */
			   //-------------------------------------------------//
			   
	       } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
		   } else {
	          layer.alert("请选择开始报价的模块", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
	        }
	    }
		
		//  1.竞价未开始、已流拍状态查看的是竞价信息页面
		//	2.竞价已结束状态查看的是竞价结果页面
		//	3.已报价、报价结束、第一轮结果待确认查看的是竞价信息+报价信息页面
		//	4.第一轮结果已确认、第二轮结果待确认查看的是第一轮结果确认页面
		//	5.第二轮结果已确认查看的是第二轮结果确认页面】
		function findIssueInfo(pId,pStatus,pRemark) {
			// 1.竞价未开始、已流拍状态
			if(pStatus == 1 || pRemark == '0' || pRemark == '2'){
				window.location.href="${pageContext.request.contextPath}/ob_project/findBiddingIssueInfo.html?flag=1&id="+pId;  
		    }
			
			// 2.待确认状态查看的信息
			if(pStatus == 2 && pRemark == '1'){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?id="+pId; 
			}
			if(pStatus == 5 && pRemark == '1'){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?id="+pId; 
			}
			
			// 3.第一轮放弃查看的是报价信息页面
			if(pStatus == 5 && pRemark == '3'){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/queryBiddingResult.html?id="+pId;
			}
			
			// 4.竞价结束和流拍
			if(pStatus == 3 || pStatus == 4 ){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/queryBiddingResult.html?projectId="+pId;
			}
			
			// 5.第一轮结果已确认、第二轮结果待确认查看的是第一轮结果确认页面  --第一轮放弃是查看的第一轮确认的结果
			if((pStatus == 5 && pRemark == '4') || (pStatus == 5 && pRemark == '5') || (pStatus == 5 && pRemark == '32') || (pStatus == 6 && pRemark == '5') || (pStatus == 6 && pRemark == '32')){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/queryBiddingResult.html?projectId="+pId;
			}
			
			// 6.第二轮结果已确认查看的是第二轮结果确认页面
			if((pStatus == 6 && pRemark == '42') || (pStatus == 6 && pRemark == '52')){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/queryBiddingResult.html?projectId="+pId;
			}
	    }
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">供应商报价列表</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价信息列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form id="queryForm" action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价标题：</label>
			<input name="name" value="${ name }" type="text" id="topic" class=""/>
	      </li>
	      </li>
    	  <li>
	    	<label class="fl">发布时间：</label>
			<input name="createTime" value="${ createTimeStr }"  class="Wdate" type="text" id="d17" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',firstDayOfWeek:1})"/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows apply" type="submit" onclick="beginQuote()">开始报价</button>
		<button class="btn btn-windows git" type="submit" onclick="confirmResult()">确认结果</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">竞价标题</th>
		  <th class="info">竞价项目编号</th>
		  <th class="info">报价开始时间</th>
		  <th class="info">报价截止时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${ info.list }" var="obProject" varStatus="vs">
			<tr>
			  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obProject.obProjectList[0].id},${ obProject.obProjectList[0].status },${obProject.remark},<fmt:formatDate value='${ obProject.obProjectList[0].quoteEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"/></td>
			  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td>
			  	<a href="javascript:;" onclick="findIssueInfo('${obProject.obProjectList[0].id}',${ obProject.obProjectList[0].status },'${obProject.remark}')">${ obProject.obProjectList[0].name }</a>
			  </td class="tl">
			  <td class="tc">${ obProject.obProjectList[0].projectNumber }</td>
			  <td class="tc"><fmt:formatDate value="${ obProject.obProjectList[0].startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc"><fmt:formatDate value="${ obProject.obProjectList[0].quoteEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc" id=${ obProject.obProjectList[0].id }>
			  	<c:if test="${ obProject.obProjectList[0].status == 1 }">
			  		竞价未开始
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 2 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0'}">
							报价中
						</c:when>
						<c:when test="${obProject.remark == '1'}">
							已报价待确认
						</c:when>
					</c:choose>
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 3 }">
			  		竞价结束
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 4 }">
				  	流拍
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 5 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0'}">
							未报价
						</c:when>
						<c:when test="${obProject.remark == '1'}">
							结果待确认(第一轮)
						</c:when>
						<c:when test="${obProject.remark == '3'}">
							放弃确认(第一轮)
						</c:when>
						<c:when test="${obProject.remark == '4'}">
							第一轮已确认
						</c:when>
						<c:when test="${obProject.remark == '5'}">
							第一轮已确认
						</c:when>
					</c:choose>
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 6}">
			  		<c:choose>
						<c:when test="${obProject.remark == '0'}">
							未报价
						</c:when>
						<c:when test="${obProject.remark == '42'}">
							第二轮已确认
						</c:when>
						<c:when test="${obProject.remark == '52'}">
							第二轮已确认
						</c:when>
						<c:when test="${obProject.remark == '32'}">
							放弃确认(第二轮)
						</c:when>
						<%-- <c:when test="${obProject.remark == '4'}">
							待结束
						</c:when> --%>
						<c:otherwise>
							结果待确认(第二轮)
						</c:otherwise>
					</c:choose>
			  	</c:if>
			  	<c:if test="${ obProject.remark == '666' }">
				  	未中标
			  	</c:if>
			  </td>
			</tr>
		</c:forEach>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
   
   <input id="aaa" type="text" />
   <input id="bbb" type="text" />
</body> 
</html>