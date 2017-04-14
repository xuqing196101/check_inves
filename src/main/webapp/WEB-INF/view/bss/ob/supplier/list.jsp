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
	      		location.href = "${pageContext.request.contextPath }/supplierQuote/list.do?name=${ name }&&createTime=${ createTimeStr }&&queryStatus=${queryStatus}&&page=" + e.curr;
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
		var projectArr = [];
		var timeListObject = ${timeListObject};
		// 遍历获取当前页面时间段
		$.each(timeListObject,function(index,ele){
			// 获取报价开始时间
			timeArray.push(ele.quotoTimeDate/1000);
			// 获取报价结束时间
			timeArray.push(ele.endQuotoTimeDate/1000);
			timeArray.push(ele.endQuotoTimeDateSecond/1000);
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
				// 刷新页面
				reloadPage();
			}
       	},1000);
		
		function reloadPage(){
			// 刷新页面
			layer.msg("自动刷新页面");
			window.location.reload();
		}

	});
	
	
	// 开始报价和确认结果通用验证
	function statusCommon(status){
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
	}
	
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
			   var quotoEndTimeSecond = valueArr[4];
			   // 得到报价截止的毫秒数
			   var quotoEndTimeMill = (new Date(quotoEndTime)).getTime();
			   var quotoEndTimeMillSecond = (new Date(quotoEndTimeSecond)).getTime();
			  
			   //竞价结束和流拍
			   statusCommon(status);
			   
			   if(status == '1'){
				   layer.alert("对不起，报价时间还未开始，请您等待 ！");
				   return;
			   }
			   
			   if(status == '2'){
				   if(remark == '1'){
					   layer.alert("已报价，请等待确认结果 ！");
					   return;
				   }
			   }
			   if(status == '7'){
				   if(remark == '21'){
					   layer.alert("已报价，请等待确认结果 ！");
					   return;
				   }
				   if(remark == '0'){
					   layer.alert("第一轮未报价，不能参与第二轮报价 ！");
					   return;
				   }
			   }
			   
			   // 报价时间已结束
			   if(status != '2' && status != '7'){
				   layer.alert("报价已结束 ！");
				   return;
			   }
			   // 开始报价第一次报价
			   if(status == '2' && remark == '0'){
				   window.location.href="${pageContext.request.contextPath}/supplierQuote/beginQuoteInfo.html?id="+valueArr[0]+"&&quotoEndTimeMill="+quotoEndTimeMill+"&&status="+status;
			   }
			   // 二次报价
			   // 第二次报价前，两家供应商报价进入第二轮报价时的判断，未报价的不能进入，只能这第一次报价的两家供应商才可以进入
			   if(status == '7' && remark == '20'){
				   $.ajax({
			   		   type:'POST',
			   		   url:'${pageContext.request.contextPath}/supplierQuote/checkQuotoSecond.do',
			   		   data:{id:valueArr[0]},
			   		   dataType:'json',
			   		   success:function(data){
			   			   if(data.status == 500){
			   					layer.alert(data.msg);
			   			   }else{
			   					quotoSecond();
			   			   }
			   		   }
			   	   });
			   }
			   // 进入第二次报价
			   function quotoSecond(){
				   window.location.href="${pageContext.request.contextPath}/supplierQuote/beginQuoteInfo.html?id="+valueArr[0]+"&&quotoEndTimeMillSecond="+quotoEndTimeMillSecond+"&&status="+status;
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
			   
			   //竞价结束和流拍
			   statusCommon(status);
			   
			   if(status != '5' && status != '6'){
				   layer.alert("对不起，确认时间未开始，请您等待 ！");
				   return;
			   }
			   
			   // 未中标提示
			   if((status == '5' && remark == '0') || (status == '5' && remark == '20')
					|| (status == '6' && remark == '0') || (status == '6' && remark == '20')
					   || (status == '6' && remark == '3') || (status == '5' && remark == '666')){
				   layer.alert("对不起，您未中标 ！");
				   return;
			   }
			   
			   // 第一轮已操作
			   if((status == '5' && remark == '4') || (status == '5' && remark == '3')){
				   layer.alert("对不起，不能重复确认 ！");
				   return;
				}
			   
			   // 第二轮已操作
			   if((status == '6' && remark == '42') || (status == '6' && remark == '32')
					   || (status == '6' && remark == '22')){
				   layer.alert("对不起，不能重复确认 ！");
				   return;
				}
			   
			   // 确认结果
			   if((status == '5' && remark == '21') || (status == '5' && remark == '1') || (status == '6' && remark == '4') || (status == '6' && remark == '666')){
			   	   $.ajax({
			   		   type:'POST',
			   		   url:'${pageContext.request.contextPath}/supplierQuote/checkConfirmResult.do',
			   		   data:{projectId:valueArr[0]},
			   		   dataType:'json',
			   		   success:function(data){
			   			   if(data.status == 1 || data.status == 2){
			   					confirmResult();
			   			   }else{
			   					layer.alert(data.msg);
			   			   }
			   		   }
			   	   });
			   }
			   
			   // 确认结果
			   function confirmResult(){
				   window.location.href = "${pageContext.request.contextPath}/supplierQuote/confirmResult.html?projectId="+valueArr[0];
			   }
			   
	       } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
		   } else {
	          layer.alert("请选择确认结果的模块", {
	            offset: ['222px', '255px'],
	            shade: 0.01,
	          });
	        }
	    }
		
		//  1.竞价未开始状态查看的是竞价信息页面
		//	2.竞价已结束状态查看的是竞价结果页面
		//	3.已报价、报价结束、第一轮结果待确认查看的是竞价信息+报价信息页面
		//	4.第一轮结果已确认、第二轮结果待确认查看的是第一轮结果确认页面
		//	5.第二轮结果已确认查看的是第二轮结果确认页面】
		function findIssueInfo(pId,pStatus,pRemark) {
			// 1.竞价未开始
			if(pStatus == 1 || pRemark == '0'){
				window.location.href="${pageContext.request.contextPath}/ob_project/findBiddingIssueInfo.html?flag=1&id="+pId;  
		    }
			
			// 2.待确认状态查看的信息--未中标状态--已报价待确认
			if((pStatus == 2 && pRemark == '1') || (pStatus == 7 && pRemark == '21') || (pStatus == 7 && pRemark == '20') || pRemark == '666'){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?id="+pId; 
			}
			
			if((pStatus == 5 && pRemark == '1') || pStatus == 5 && pRemark == '21' || pStatus == 6 && pRemark == '1'){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?id="+pId; 
			}
			
			// 3.第一轮放弃查看的是报价信息页面
			if((pStatus == 5 && pRemark == '3') || (pStatus == 6 && pRemark == '3')){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?id="+pId; 
			}
			
			// 4.竞价结束和流拍
			if(pStatus == 3 || pStatus == 4 ){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/queryBiddingResult.html?projectId="+pId;
			}
			
			// 5.第一轮结果已确认、第二轮结果待确认查看的是第一轮结果确认页面  --第二轮放弃是查看的第一轮确认的结果
			if((pStatus == 5 && pRemark == '4') || (pStatus == 6 && pRemark == '4') || (pStatus == 6 && pRemark == '32')
					|| (pStatus == 6 && pRemark == '22')){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?flag=firstConfirm&&id="+pId;
			}
			
			// 6.第二轮结果已确认查看的是第二轮结果确认页面
			if((pStatus == 6 && pRemark == '42')){
				window.location.href="${pageContext.request.contextPath}/supplierQuote/findQuotoIssueInfo.html?flag=secondConfirm&&id="+pId;
			}
			
	    }
		
		//重置按钮事件  
	    function resetAll(){
	        $("#name").val("");  
	        $("#d17").val("");  
	        $("#queryStatus").val("");
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
			<input name="name" value="${ name }" type="text" id="name" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">发布时间：</label>
			<input name="createTime" value="${ createTimeStr }"  class="Wdate" type="text" id="d17" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',firstDayOfWeek:1})"/>
	      </li>
	      <li>
	    	<label class="fl">竞价状态：</label>
	    	  <select id="queryStatus" name="queryStatus" class="w178">
	    	    <option value="">--请选择--</option>
	    	    <option value="1:0" <c:if test="${'1:0' eq queryStatus}">selected</c:if>>竞价未开始</option>
	    	    <option value="3:(4,42,22)" <c:if test="${'3:(4,42,22)' eq queryStatus}">selected</c:if>>中标</option>
	    	    <option value="3:(0,20,3,32,666,1,21)" <c:if test="${'3:(0,20,3,32,666,1,21)' eq queryStatus}">selected</c:if>>未中标</option>
	    	  </select>
	      </li>
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button onclick="resetAll()" class="btn">重置</button>  	
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
			  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obProject.obProjectList[0].id},${ obProject.obProjectList[0].status },${obProject.remark},<fmt:formatDate value='${ obProject.obProjectList[0].quoteEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>,<fmt:formatDate value='${ obProject.obProjectList[0].quoteEndTimeSecond }' pattern='yyyy-MM-dd HH:mm:ss'/>"/></td>
			  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td class="tl">
			  	<a href="javascript:;" onclick="findIssueInfo('${obProject.obProjectList[0].id}',${ obProject.obProjectList[0].status },'${obProject.remark}')">${ obProject.obProjectList[0].name }</a>
			  </td>
			  <td class="tc">${ obProject.obProjectList[0].projectNumber }</td>
			  <td class="tc"><fmt:formatDate value="${ obProject.obProjectList[0].startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc"><fmt:formatDate value="${ obProject.obProjectList[0].quoteEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc" id=${ obProject.obProjectList[0].id }>
			  	<!-- 竞价未开始 -->
			  	<c:if test="${ obProject.obProjectList[0].status == 1 && obProject.remark == '0' }">
			  		竞价未开始
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 2 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0'}">
							第一轮报价中
						</c:when>
						<c:when test="${obProject.remark == '1'}">
							第一轮已报价
						</c:when>
					</c:choose>
			  	</c:if>
			  	
			  	<c:if test="${ obProject.obProjectList[0].status == 7 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0'}">
							未报价
						</c:when>
						<c:when test="${obProject.remark == '21'}">
							第二轮已报价
						</c:when>
						<c:when test="${obProject.remark == '20'}">
							第二轮报价中
						</c:when>
					</c:choose>
			  	</c:if>
			  	
			  	<c:if test="${ obProject.remark == '666' && obProject.obProjectList[0].status != 6 && obProject.obProjectList[0].status != 3 && obProject.obProjectList[0].status != 4}">
				  	未中标
			  	</c:if>
			  	
			  	<c:if test="${ obProject.obProjectList[0].status == 5 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0' || obProject.remark == '20'}">
							未报价
						</c:when>
						<c:when test="${obProject.remark == '1' || obProject.remark == '21'}">
							结果待确认(第一轮)
						</c:when>
						<c:when test="${obProject.remark == '3'}">
                           	第一轮已放弃
						</c:when>
						<c:when test="${obProject.remark == '4'}">
							第一轮已确认
						</c:when>
					</c:choose>
			  	</c:if>
			  	<c:if test="${ obProject.obProjectList[0].status == 6 }">
			  		<c:choose>
						<c:when test="${obProject.remark == '0' || obProject.remark == '20'}">
							未报价
						</c:when>
						<c:when test="${obProject.remark == '1'}">
							未中标
						</c:when>
						<c:when test="${obProject.remark == '42'}">
							第二轮已确认
						</c:when>
						<c:when test="${obProject.remark == '32'}">
							第二轮已放弃
						</c:when>
						<c:when test="${obProject.remark == '22'}">
							第二轮已放弃
						</c:when>
						<c:when test="${obProject.remark == '3'}">
							第一轮已放弃
						</c:when>
						<c:when test="${obProject.remark == '4'}">
							结果待确认(第二轮)
						</c:when>
						<c:when test="${obProject.remark == '666'}">
							结果待确认(第二轮)
						</c:when>
					</c:choose>
			  	</c:if>
			  	
			  	<!-- 竞价结束 -->
			  	<c:if test="${ obProject.obProjectList[0].status == 3 }">
                    <c:choose>
	                    <c:when test="${obProject.remark == '0' || obProject.remark == '20' || obProject.remark == '3' || obProject.remark == '32' || obProject.remark == '666' || obProject.remark == '1' || obProject.remark == '21'}">
                        	未中标
	                    </c:when>
	                    <c:when test="${obProject.remark == '4' || obProject.remark == '42' || obProject.remark == '22'}">
                        	中标
	                    </c:when>
                    </c:choose>
			  	</c:if>
			  	
			  	<!-- 流拍 -->
			  	<c:if test="${ obProject.obProjectList[0].status == 4 }">
				  	已流拍
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