<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>确认结果页面</title>
<script type="text/javascript">
	//倒计时方法
	var loadPageTime = new Date();
	function getRTime(){
		var getOverTime = $("#confirmOverTime").text();
		var EndTime= new Date("${confirmInfoVo.confirmOvertime}"); //截止时间
		var sysNowTime = new Date("${sysCurrentTime}");//后台传过来的系统的当前时间
		var clientNowTime = new Date();
		var t = (EndTime.getTime() - sysNowTime.getTime()) - (clientNowTime.getTime() - loadPageTime.getTime());
		if(t > 0) {
			var d = Math.floor(t/1000/60/60/24);
			var h = Math.floor(t/1000/60/60%24);
			var m = Math.floor(t/1000/60%60);
			var s = Math.floor(t/1000%60);
			$("#confirmCountDown").text(d + "天" + h + "时" + m + "分" + s + "秒");
		} else {
			$("#confirmCountDown").text("第一轮确认倒计时已经结束");
			close('${confirmStatus }');
			clearInterval(downTimer);
		}
	}
	function getRTime2(){
		var getOverTime = $("#confirmOverTime").text();
		var EndTime= new Date("${confirmInfoVo.confirmOvertime}"); //截止时间
		var sysNowTime = new Date("${sysCurrentTime}");//后台传过来的系统的当前时间
		var clientNowTime = new Date();//这个客户端的当前时间，暂时不用
		//var t = EndTime.getTime() - sysNowTime.getTime();
		var t = (EndTime.getTime() - sysNowTime.getTime()) - (clientNowTime.getTime() - loadPageTime.getTime());
		if(t > 0) {
			var d = Math.floor(t/1000/60/60/24);
			var h = Math.floor(t/1000/60/60%24);
			var m = Math.floor(t/1000/60%60);
			var s = Math.floor(t/1000%60);
			$("#confirmCountDown2").text(d + "天" + h + "时" + m + "分" + s + "秒");
		} else {
			$("#confirmCountDown2").text("第二轮确认倒计时已经结束");
			close('${confirmStatus }');
			clearInterval(downTimer2);
		}
	}
	//占比下调,返回对应占比后数量@param 值
	function getDownRatioVal(passVal,beforeRatio,afterRatio) {
		var br = parseDouble(beforeRatio)/100;
		var ar = parseDouble(afterRatio)/100;
		return Math.ceil((parseDouble(passVal) / br) * ar);
	}
	//定义当前标题的全局(无论第一、二轮)变量
	
	var confirmStarttime = "${confirmInfoVo.confirmStarttime }";
	var confirmOvertime = "${confirmInfoVo.confirmOvertime }";
	var secondOvertime = "${confirmInfoVo.secondOvertime }";
	var sysCurrentTime = "${sysCurrentTime }";
	
	$(function() {
		//定义一个变量接收后台传过来的状态值
		var passStatus = "${confirmStatus}";
		//定时器调用
		if(passStatus == "-1") {
			var downTimer = setInterval(getRTime,1000);
		} else if(passStatus == "1") {
			$("#confirmCountDown12").text("第一轮确认已经结束");
			var downTimer2 = setInterval(getRTime2,1000);
		}
		
		//第一轮占比的初始值
		var currentVal = $("input[name='confirmRatioFirst']").val();
		//第二轮占比的初始值
		var currentSecondVal = $("input[name='confirmRatioSecond']").val();
		//下面加上"2"的占比表示第二轮的数据变量
		var changeRatioCounts = [];
		$("[title='theProductCount']").each(function(index,element) {
			changeRatioCounts.push($(this).text());
		});
		var changeRatioCounts2 = [];
		$("[title='theProductCount2']").each(function(index,element) {
			changeRatioCounts2.push($(this).text().trim());
		});
		var productPrices = [];
		$("[title='theProductPrice']").each(function() {
			productPrices.push($(this).text());
		});
		var productPrices2 = [];
		$("[title='theProductPrice2']").each(function() {
			productPrices2.push($(this).text());
		});
		//先把不前的各个产品的数量存到全局的一个数组里
		var eachProductCount = [];
		$("[title='theProductCount']").each(function(index,element) {
			eachProductCount.push($(this).text().trim());
		});
		$("input[name='confirmRatioFirst']").keyup(function(event) {
			var currentPressKey = event.keyCode;//当前输入的字符
			var afterInputVal = $(this).val();
			if(passStatus == "-1") {
				if(currentPressKey >= 48 && currentPressKey <= 57 || currentPressKey == 8) {
					if(parseInt(afterInputVal) > parseInt(currentVal)) {
						$(this).val(currentVal);
						layer.alert("占比只能下调");
					} else if(parseInt(afterInputVal) == 0) {
						$(this).val(currentVal);
						layer.alert("占比不能为0");
					} else {
						var allCount = 0;
						$("[title='theProductTotalPrice']").each(function(index,element) {
// 						alert(changeRatioCounts[index]+"  f "+afterInputVal);
							//var afterCount = getDownRatioVal(changeRatioCounts[index],currentVal,afterInputVal);
							var afterCount = getDownRatioVal(changeRatioCounts[index],100,afterInputVal);
							$(this).text((afterCount * productPrices[index]).toFixed(2));
							$("[title='theProductCount']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									//alert(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultCount']").text(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productNum']").text();
								}
							});
							allCount += afterCount * productPrices[index];
						});
						$("[title='allProductTotalPrice']").text(allCount.toFixed(2));
					}
				} else if(currentPressKey == 13 || currentPressKey == 18) {
					//删除键和回车，放行
				} else {
					$(this).val(currentVal);
					layer.alert("请输入合法数字");
				}
			} else {
				$(this).val(currentVal);
				layer.alert("已经在第二轮,不能修改第一轮的数据");
			}
		});
		
		$("input[name='confirmRatioSecond']").keyup(function(event) {
			var currentPressKey = event.keyCode;//当前输入的字符
			var afterInputVal = $(this).val();
			if(passStatus == "1") {
				if(currentPressKey >= 48 && currentPressKey <= 57 || currentPressKey == 8) {
					if(parseInt(afterInputVal) > parseInt(currentSecondVal)) {
						$(this).val(currentSecondVal);
						layer.alert("占比只能下调");
					} else if(parseInt(afterInputVal) == 0) {
						$(this).val(currentSecondVal);
						layer.alert("占比不能为0");
					} else {
						var allCount = 0;
						//第二轮占比改动，调动下面的数据
						$("[title='theProductTotalPrice2']").each(function(index,element) {
							var afterCount = getDownRatioVal(changeRatioCounts2[index],currentSecondVal,afterInputVal);
							$(this).text((afterCount * productPrices2[index]).toFixed(2));
							$("[title='theProductCount2']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									$(this).parent().find("input[name='productNum']").text();
								}
							});
							allCount += afterCount * productPrices2[index];
						});
						$("[title='allProductTotalPrice2']").text(allCount.toFixed(2));
					}
				} else if(currentPressKey == 13 || currentPressKey == 18) {
					//删除键和回车，放行
				} else {
					$(this).val(currentSecondVal);
					layer.alert("请输入合法数字");
				}
			} else {
				$(this).val(currentSecondVal);
				layer.alert("已经在第一轮,不能修改第二轮的数据");
			}
		});
	});
	
	
	
	//这个暂时不用
	var formatDateTime = function (date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		m = m < 10 ? ('0' + m) : m;
		var d = date.getDate();
		d = d < 10 ? ('0' + d) : d;
		var h = date.getHours();
		var minute = date.getMinutes();
		minute = minute < 10 ? ('0' + minute) : minute;
		var second = date.getSeconds();
		return y + '-' + m + '-' + d+' '+h+':'+minute + ':' + second;
	}
	//CST（后台传过来的）转GMT（js用的）
	function getTaskTime(strDate) {   
	    if(null==strDate || ""==strDate){  
	        return "";  
	    }
	    var dateStr=strDate.trim().split(" ");  
	    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";  
	    var date = new Date(Date.parse(strGMT));  
	    var y = date.getFullYear();  
	    var m = date.getMonth() + 1;    
	    m = m < 10 ? ('0' + m) : m;  
	    var d = date.getDate();    
	    d = d < 10 ? ('0' + d) : d;  
	    var h = date.getHours();  
	    var minute = date.getMinutes();
	    minute = minute < 10 ? ('0' + minute) : minute;
	    var second = date.getSeconds();  
	    second = second < 10 ? ('0' + second) : second;
	      
	    return y+"-"+m+"-"+d+" "+h+":"+minute+":"+second;
	}
	//后台传过来的时间调用此方法转换
	confirmStarttime = getTaskTime(confirmStarttime);
	confirmOvertime = getTaskTime(confirmOvertime);
	secondOvertime = getTaskTime(secondOvertime);
	//定义当前标题的供应商正处的状态
	//var currentStatus 
	
	//点击接受按钮调用的方法
	function confirmAccept() {
	    
		layer.confirm('您确定接受吗?', {title:'提示',offset: ['222px','500px'],shade:0.01}, function(index){
			layer.close(index);
			var currentConfirmStatus = $("#currentConfirmStatus").val();
			  if(currentConfirmStatus == "-1") {
				var projectResultList = [];
				var oBProjectResult = {};
				$("[title='theProductId']").each(function(index,element) {
					oBProjectResult.projectId = "${projectId}";
					oBProjectResult.supplierId = "${supplierId}";
					oBProjectResult.proportion = $("input[name='confirmRatioFirst']").val();
					oBProjectResult.productId = $(this).find("input[name='productId']").val();
					oBProjectResult.resultCount = $(this).find("input[name='productResultCount']").val();
					oBProjectResult.offerPrice = $(this).find("input[name='productQuotePrice']").val();
					oBProjectResult.status = 1;
					projectResultList.push(oBProjectResult);
				});
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.html?acceptNum=${confirmStatus}&confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					data : JSON.stringify(projectResultList),
					contentType:"application/json",
					success : function(obj) {//第一轮接受
						location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					},
					error : function(obj) {
						layer.alert("第一轮接受失败");
					}
				});
			} else if(currentConfirmStatus == "1") {
				var projectResultList = [];
				var oBProjectResult = {};
				
				$("[title='theProductId2']").each(function(index,element) {
					oBProjectResult.projectId = "${projectId}";
					oBProjectResult.supplierId = "${supplierId}";
					oBProjectResult.proportion = $("input[name='confirmRatioFirst2']").val();
					oBProjectResult.productId = $(this).find("input[name='productId']").val();
					oBProjectResult.resultCount = $(this).find("input[name='productResultCount']").val();
					oBProjectResult.offerPrice = $(this).find("input[name='productQuotePrice']").val();
					oBProjectResult.status = 2;
					projectResultList.push(oBProjectResult);
				});
				
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.html?acceptNum=${confirmStatus}&confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime,
					data : JSON.stringify(projectResultList),
					dataType : "json",
					contentType:"application/json",
					success : function(obj) {//第二轮接受
						location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					},
					error : function(obj) {
						layer.alert("第二轮接受失败");
					}
				});
			}
			
		});
			 
		//url saveConfirmQuoteInfo
	}
	//点击放弃按钮调用此方法
	function cancelAccept(confirmStatus) {
		var projectResult = {};
		var closeLayerIndex = 0;
		if(confirmStatus == "-1") {
			layer.confirm('您确定要放弃吗?', {title:'提示',offset: ['222px','500px'],shade:0.01}, function(index){
				//window.location.href="${pageContext.request.contextPath}/performance/deletePerfor.html?id="+ids;
				layer.close(index);
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.html",
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${projectId}",
						"confirmStatus" : confirmStatus
					},
					success:function(data){
						window.history.go(-1);
					},
					error : function(data) {
						alert("放弃失败");
					}
				});
			});
		} else if(confirmStatus == "1") {
			layer.confirm('您确定要放弃吗?', {title:'提示',offset: ['222px','500px'],shade:0.01}, function(index){
				//window.location.href="${pageContext.request.contextPath}/performance/deletePerfor.html?id="+ids;
				layer.close(index);
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.html",
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${projectId}",
						"confirmStatus" : confirmStatus
					},
					success:function(data){
						window.history.go(-1);
					},
					error : function(data) {
						alert("放弃失败");
					}
				});
			});
		}
		
	}
	//点击放弃按钮调用此方法
	function close(confirmStatus) {
		var projectResult = {};
		var closeLayerIndex = 0;
		if(confirmStatus == "-1") {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.html",
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${projectId}",
						"confirmStatus" : confirmStatus
					},
					success:function(data){
						window.history.go(-1);
					},
					error : function(data) {
					}
				});
		} else if(confirmStatus == "1") {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.html",
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${projectId}",
						"confirmStatus" : confirmStatus
					},
					success:function(data){
						window.history.go(-1);
					},
					error : function(data) {
					}
				});
		}
		
	}
	
	
	$(function() {
		//页面加载进来计算合计金额
		var allCount = 0;
		var allCount2 = 0;
		$("[title='theProductTotalPrice']").each(function(index,element) {
			allCount += parseInt($(this).text());
		});
		$("[title='theProductTotalPrice2']").each(function(index,element) {
			allCount2 += parseInt($(this).text());
		});
		$("[title='allProductTotalPrice']").text(allCount.toFixed(2));
		$("[title='allProductTotalPrice2']").text(allCount2.toFixed(2));
	});
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">我的竞价项目</a></li>
		   <li><a href="javascript:void(0)">确认结果</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 表格开始    projectResult -->
    <div class="container container_box">
    <h2 class="count_flow"><i>1</i><span class="count_flow">基本信息</span></h2>
    <ul class="ul_list" >
    	<li class="col-md-4 col-sm-6 col-xs-12 pl15" >
    	<h2 class="count_flow">
 	      <span class="font_sblck">竞价标题：</span>
 	      <span class="margin-left-10 font_sblck">${confirmInfoVo.quoteName }</span>
		    		<input type="hidden" name="quoteName" id="quoteName" value="${confirmInfoVo.quoteName }"/>
		    		<input type="hidden" name="confirmStarttime" id="confirmStarttime" value="${confirmInfoVo.confirmStarttime }"/>
		    		<!-- <input type="hidden" name="confirmOvertime" value="${confirmInfoVo.confirmOvertime }"/>这个放到第一轮确认时间那里 -->
		</h2>    		<!-- <input type="hidden" name="secondOvertime" id="secondOvertime" value="${confirmInfoVo.secondOvertime }"/> 这个放到第二轮确认时间那里-->
      </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15" style="width: 50%;">
      <h2 class="count_flow">
 	      <span class="font_sblck">确认结束时间：</span>
 	      <span class="margin-left-10 font_sblck" id="confirmOverTime">
		    	<fmt:formatDate value="${confirmInfoVo.confirmOvertime }" pattern="yyyy-MM-dd HH:ss:mm"/>
		    	<!-- 第一轮确认结束的时间点 -->
		    	<input type="hidden" name="confirmOvertime" value="${confirmInfoVo.confirmOvertime }"/>
		    	<!-- 第二轮确认结束的时间点 -->
		    	<input type="hidden" name="secondOvertime" value="${confirmInfoVo.secondOvertime }"/>
		    	</span>
		    	</h2>
      </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15">
      <h2 class="count_flow">
      <span class="font_sblck">名次：</span>  
      <span class="margin-left-10 font_sblck">第${confirmInfoVo.ranking }名</span>  
      </h2>
       </li>
       <li class="col-md-4 col-sm-6 col-xs-12 pl15"> 
       <h2 class="count_flow">
		   <input type="hidden" id="ranking" value="${confirmInfoVo.ranking }"/>
		    	<span class="font_sblck">状态：</span>
		    	<span class="margin-left-10 font_sblck">
		    		<c:if test="${confirmInfoVo.ranking < 7}">
		    		中标
		    		</c:if>
		    		<c:if test="${confirmInfoVo.ranking >= 7}">
		    		未中标
		    		</c:if>
		    		</span>
		    		</h2>
		    		</li>
		    		<li  class="col-md-4 col-sm-6 col-xs-12 pl15">
		    		<h2 class="count_flow">
		    	<span class="font_sblck">中标比例  :</span>
		    	 <span class="margin-left-10 font_sblck">${confirmInfoVo.bidRatio }%
		    		</span>
		    		</h2>
		    		</li>
    </ul>
    <!--<c:if test="${confirmStatus=='-1'}"></c:if>-->
   
     <h2 class="count_flow"><i>2</i>第一轮确认</h2>
    <ul class="ul_list" >
    <c:if test="${confirmStatus=='-1'}">
     <li class="col-md-4 col-sm-6 col-xs-12 pl15" >
     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<input class="input_group" id="" name="confirmRatioFirst" value="${confirmInfoVo.bidRatio }" type="text">%
     	</h2>
     </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15" >
      <h2 class="count_flow">
      <span style="margin-left: 22px;margin-right: 12px;">确认倒计时：</span>
      <span id="confirmCountDown"></span> 
      </h2>
      </li>
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"  width="10%">序号</th>
		  <th class="tc" width="20%">产品名称</th>
		  <th class="tc">数量</th>
		  <th class="tc">自报单价（元）</th>
		  <th class="tc">成交单价（元）</th>
		  <th class="tc">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice"></td>
		</tr>
		<c:forEach items="${confirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc"  width="5%" title="theProductId">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productName }"/>
		  	<input type="hidden" name="productNum" value="${bidproduct.productNum }"/>
		  	<input type="hidden" name="productQuotePrice" value="${bidproduct.myOfferMoney }"/>
		  	<input type="hidden" name="productResultCount" value="${bidproduct.productNum }"/>
		  </td>
		  <td class="tc" title="theProductName">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount">
		  	<fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/>
		  </td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice">${bidproduct.dealPrice }</td>
		  <!-- 
		  <td class="tc" title="theProductTotalPrice">${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 * bidproduct.dealMoney }</td>
		   -->
		  <td class="tc" title="theProductTotalPrice">${bidproduct.dealMoney }</td>
		</tr>
		</c:forEach>
	</table>
  
  </c:if>
  <c:if test="${confirmStatus=='1'}">
<!--    <h2 class="count_flow"><i>2</i>第一轮确认</h2>
 -->  <li class="col-md-3 col-sm-6 col-xs-12 pl15" >
     	<h2 class="count_flow" >
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<span id="confirmRatioSpan12" style="margin-left: 12px;margin-right: 12px;">${confirmInfoVo.bidRatio }</span>%
     	</h2>
     	</li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15" >
      <h2 class="count_flow">
     			<span style="padding-left: 22px;">第一轮确认倒计时：</span>
     			<span id="confirmCountDown12">未开始</span>
      </h2>
      </li>
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <td class="w30 info"  width="10%">序号</td>
		  <td class="tc" width="20%">产品名称</td>
		  <td class="tc" >数量</td>
		  <td class="tc" >自报单价（元）</td>
		  <td class="tc" >成交单价（元）</td>
		  <td class="tc" >成交总价（元）</td>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td  class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice12"></td>
		</tr>
		<c:forEach items="${confirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc" title="theProductId2">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productName }"/>
		  	<input type="hidden" name="productNum" value="${bidproduct.productNum }"/>
		  	<input type="hidden" name="productQuotePrice" value="${bidproduct.dealMoney }"/>
		  	<input type="hidden" name="productResultCount" value=""/>
		  </td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount12"><fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/></td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice12">${bidproduct.dealPrice }</td>
		  <td class="tc" title="theProductTotalPrice12">${bidproduct.dealMoney }</td>
		</tr>
		</c:forEach>
	</table>
  </c:if>
    </ul>
  <!-- 
  <c:if test="${confirmInfoVo.bidStatus=='-1' || confirmInfoVo.bidStatus==null || confirmInfoVo.bidStatus=='2'}">
  </c:if>
   -->
    <h2 class="count_flow"><i>3</i>第二轮确认</h2>
   <ul class="ul_list">
   <c:if test="${confirmStatus=='-1'}">
   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
     	<h2 class="count_flow" >
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<span style="margin-left: 22px;">0</span>%
     	</h2>
     	</li>
     	
     	 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
     	<h2 class="count_flow" >
     			<span style="padding-left: 22px;">第二轮确认倒计时：</span>
     			<span id="confirmCountDown21">未开始</span>
     	</h2>
     	</li>
    	<table class="table table-bordered table-condensed table-hover table-striped">
    	<thead>
		<tr>
		  <td class="w30 info"  width="10%">序号</td>
		  <td class="tc" width="20%">产品名称</td>
		  <td  class="tc" >数量</td>
		  <td  class="tc" >自报单价（元）</td>
		  <td  class="tc" >成交单价（元）</td>
		  <td  class="tc" >成交总价（元）</td>
		</tr>
		</thead>
		<tr>
		  <td class="tc" ></td>
		  <td class="tc"  colspan="4">合计</td>
		  <td class="tc"  title="allProductTotalPrice2"></td>
		</tr>
		<c:forEach items="${confirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc" width="5%" title="theProductId2">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productName }"/>
		  	<input type="hidden" name="productNum" value="${bidproduct.productNum }"/>
		  	<input type="hidden" name="productQuotePrice" value="${bidproduct.dealMoney }"/>
		  	<input type="hidden" name="productResultCount" value=""/>
		  </td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount21"><fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/></td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice21">${bidproduct.dealPrice }</td>
		  <td class="tc" title="theProductTotalPrice21">${bidproduct.dealMoney }</td>
		</tr>
		</c:forEach>
	</table>
  	</c:if>
  	<c:if test="${confirmStatus=='1'}">
  	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
<!--      <div class="clear total f22"><span class="fl block">基本数量---第二轮确认：</span>
 -->     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<input id="" name="confirmRatioSecond" value="${secondConfirmInfoVo.bidRatio }" type="text" class="tc w50">%
     	</h2>
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
    	<h2 class="count_flow">
     			<span style="padding-left: 22px;">第二轮确认倒计时：</span>
     			<span id="confirmCountDown2"></span>
     	</h2>
     </li>
    	<table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="w30 info" width="10%">序号</th>
		  <th class="tc" width="20%">产品名称</th>
		  <th class="tc">数量</th>
		  <th class="tc">自报单价（元）</th>
		  <th class="tc">成交单价（元）</th>
		  <th class="tc">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice2"></td>
		</tr>
		<c:forEach items="${secondConfirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc" title="theProductId2">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productName }"/>
		  	<input type="hidden" name="productNum" value="${bidproduct.productNum }"/>
		  	<input type="hidden" name="productQuotePrice" value="${bidproduct.dealMoney }"/>
		  	<input type="hidden" name="productResultCount" value="${bidproduct.productNum }"/>
		  </td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount2"><fmt:formatNumber type="number" value="${bidproduct.productNum - ((bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100) }"/></td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice2">${bidproduct.dealPrice }</td>
		  <td class="tc" title="theProductTotalPrice2">${(bidproduct.productNum - ((bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100)) * bidproduct.dealPrice }</td>
		</tr>
		</c:forEach>
	</table>
  	</c:if>
    </ul>
  
  <div class="star_red" style="display: none;">规则1、第一轮确认如果都按比例成交，则没有第二轮确认，如果不是按比例成交，则有第二轮确认，第一轮正在确认的时候不显示<br/>第二轮数据，只有所有供应商第一轮确认完毕后，才有第二轮确认。<br/>
						规则2、自动生成成交比例（可修改），修改后成交数量和总价发生变化，未中标的的显示未中标，不显示成交比例，未中标的只能<br/>看到已中标明细，未中标的只有返回按钮。
  </div>
  <div class="col-md-12 clear tc mt10">
  <button class="btn" onclick="confirmAccept('${confirmStatus }')">接受</button>
  <input type="hidden" value="${confirmStatus }" id="currentConfirmStatus"/>
  <button class="btn" onclick="cancelAccept('${confirmStatus }')">放弃</button>
  </div>
  </div>
</body>
</html>