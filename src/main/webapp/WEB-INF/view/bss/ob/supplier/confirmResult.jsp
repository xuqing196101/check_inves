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
		var EndTime= new Date("${result.confirmOvertime}"); //截止时间
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
		var EndTime= new Date("${result.secondOvertime}"); //截止时间
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
	//占比下调,返回对应占比后数量@param 值 向上取整
	function getDownRatioVal(passVal,beforeRatio,afterRatio) {
	/* 	var br = parseInt(beforeRatio)/100;
		var ar = parseInt(afterRatio)/100;
		return Math.ceil((parseInt(passVal) / br) * ar); */
		return Math.ceil(parseFloat(passVal) * parseFloat(afterRatio) / parseFloat(beforeRatio)); 
	}
	//占比下调, 值 向下取整
	function getfloor(passVal,beforeRatio,afterRatio) {
	/* 	var br = parseInt(beforeRatio)/100;
		var ar = parseInt(afterRatio)/100;
		return Math.ceil((parseInt(passVal) / br) * ar); */
		return Math.floor(parseFloat(passVal) * parseFloat(afterRatio) / parseFloat(beforeRatio)); 
	}
	
	//定义当前标题的全局(无论第一、二轮)变量
	
 	var confirmStarttime = "${result.confirmStarttime }";
	var confirmOvertime = "${result.confirmOvertime }";
	var secondOvertime = "${result.secondOvertime }";
	var sysCurrentTime = "${sysCurrentTime }"; 
	var downTimer;
	var downTimer2;
	$(function() {
		//定义一个变量接收后台传过来的状态值
		 var passStatus ="${confirmStatus}"; //"${confirmStatus}";
		//定时器调用
		 if(passStatus == "1") {
			 downTimer = setInterval(getRTime,1000);
		} else if(passStatus == "2") {
			$("#confirmCountDown12").text("第一轮确认已经结束");
			 downTimer2 = setInterval(getRTime2,1000);
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
			if(passStatus == "1") {
				if(currentPressKey >= 48 && currentPressKey <= 57 || currentPressKey == 8) {
					 if(parseInt(afterInputVal) > parseInt(currentVal)) {
						$(this).val(currentVal);
						layer.alert("占比只能下调");
					} else  
					if(parseInt(afterInputVal) == 0) {
						$(this).val(currentVal);
						firstInit(currentVal,changeRatioCounts,productPrices,eachProductCount);
						layer.alert("占比不能为0");
					} else if(!afterInputVal){
					   $(this).val(currentVal);
					   firstInit(currentVal,changeRatioCounts,productPrices,eachProductCount);
						layer.alert("占比不能为空");
					}else{
						var allCount = 0;
						$("[title='theProductTotalPrice']").each(function(index,element) {
							var afterCount = getDownRatioVal(changeRatioCounts[index],currentVal,afterInputVal);
							
							$(this).text((afterCount * productPrices[index]).toFixed(2));
							$("[title='theProductCount']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									//alert(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultsCount']").val(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultsNumber']").val(afterCount); 
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
			if(passStatus == "2") {
				if(currentPressKey >= 48 && currentPressKey <= 57 || currentPressKey == 8) {
					if(parseInt(afterInputVal) > parseInt(currentSecondVal)) {
						$(this).val(currentSecondVal);
						layer.alert("占比只能下调");
					} else if(parseInt(afterInputVal) == 0) {
						$(this).val(currentSecondVal);
						secoundInit(currentSecondVal, changeRatioCounts2, productPrices2, eachProductCount);
						layer.alert("占比不能为0");
					 } else if(!afterInputVal){
					 secoundInit(currentSecondVal, changeRatioCounts2, productPrices2, eachProductCount);
					   $(this).val(currentVal);
						layer.alert("占比不能为空");
					} else {
						var allCount = 0;
						//第二轮占比改动，调动下面的数据
						$("[title='theProductTotalPrice2']").each(function(index,element) {
							var afterCount = getfloor(changeRatioCounts2[index],currentSecondVal,afterInputVal);
							$(this).text((afterCount * productPrices2[index]).toFixed(2));
							$("[title='theProductCount2']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									$(this).parent().find("input[name='productResultsCount']").val(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultsNumber']").val(afterCount); 
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
		
		
		//页面加载进来计算合计金额
		 var allCount = 0;
		var allCount2 = 0;
		var First;
		var second=0;
		if(passStatus==1){
		   First=$("[name='confirmRatioFirst']").val();
		}else if(passStatus==2){
		  First=$("#confirmRatioFirst").text();
		}
		if(First){
		//初始化 第一轮
		firstInit(First,changeRatioCounts,productPrices,eachProductCount);
			}
			if(passStatus==2){
			//初始化 第二轮
			secoundInit(currentSecondVal, changeRatioCounts2, productPrices2, eachProductCount);
			}	
		$("[title='theProductTotalPrice']").each(function(index,element) {
			allCount += parseFloat($(this).text());
		});
		$("[title='theProductTotalPrice2']").each(function(index,element) {
			allCount2 += parseInt($(this).text());
		});
		$("[title='allProductTotalPrice']").text(allCount.toFixed(2));
		$("[title='allProductTotalPrice2']").text(allCount2.toFixed(2)); 
	});
	//第二轮初始化
	function secoundInit(currentSecondVal,changeRatioCounts2,productPrices2,eachProductCount){
	//第二轮占比改动，调动下面的数据
						$("[title='theProductTotalPrice2']").each(function(index,element) {
							var afterCount = getfloor(changeRatioCounts2[index],100,currentSecondVal);
							$(this).text((afterCount * productPrices2[index]).toFixed(2));
							$("[title='theProductCount2']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									$(this).parent().find("input[name='productResultsCount']").val(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultsNumber']").val(afterCount); 
								}
							});
							allCount += afterCount * productPrices2[index];
						});
						$("[title='allProductTotalPrice2']").text(allCount.toFixed(2));
			}
	//第一轮初始化
	function firstInit(first,changeRatioCounts,productPrices,eachProductCount){
	  var allCount=0;
		$("[title='theProductTotalPrice']").each(function(index,element) {
							var afterCount = getDownRatioVal(changeRatioCounts[index],100,first);
							$(this).text((afterCount * productPrices[index]).toFixed(2));
							$("[title='theProductCount']").each(function(indexPc,element) {
								if(index == indexPc) {
									$(this).text(afterCount);
									$(this).parent().find("input[name='productResultsCount']").val(parseInt(eachProductCount[indexPc]) - parseInt(afterCount));
									$(this).parent().find("input[name='productResultsNumber']").val(afterCount); 
								}
							});
							allCount += afterCount * productPrices[index];
						});
						$("[title='allProductTotalPrice']").text(allCount.toFixed(2));
	}
	
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
			var inde = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
			var currentConfirmStatus = $("#currentConfirmStatus").val();
			  if(parseInt(currentConfirmStatus) == 1) {
				var projectResultList = [];
				$("[title='theProductId']").each(function(index,element) {
				 var oBProjectResult=new Object();
					oBProjectResult.projectId = "${result.projectId}";
					oBProjectResult.supplierId = "${result.supplierId}";
					oBProjectResult.ranking = "${result.ranking}";
					oBProjectResult.projectResultId="${result.resultId}";
					oBProjectResult.proportion = $("input[name='confirmRatioFirst']").val();
					oBProjectResult.productId = $(this).find("input[name='productName']").val();
					oBProjectResult.resultNumber = $(this).find("input[name='productResultsNumber']").val();
					oBProjectResult.myOfferMoney = $(this).find("input[name='productMyOfferMoney']").val();
					oBProjectResult.dealMoney = $(this).find("input[name='productDealMoney']").val();
					oBProjectResult.status = 1;
					projectResultList.push(oBProjectResult);
				});
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.do?acceptNum=${confirmStatus}&confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					data : JSON.stringify(projectResultList),
					contentType:"application/json",
					success : function(obj) {//第一轮接受
						layer.alert(obj.msg);
						layer.close(inde);
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					},
					error : function(obj) {
					layer.close(inde);
						layer.alert("第一轮接受失败");
					}
				});
			} else if(parseInt(currentConfirmStatus) == 2) {
				var projectResultList = [];
				$("[title='theProductId2']").each(function(index,element) {
					var oBProjectResult=new Object();
					oBProjectResult.projectId = "${result.projectId}";
					oBProjectResult.supplierId = "${result.supplierId}";
					oBProjectResult.ranking = "${result.ranking}";
					oBProjectResult.proportion = $("input[name='confirmRatioSecond']").val();
					oBProjectResult.productId = $(this).find("input[name='productName']").val();
					oBProjectResult.resultNumber = $(this).find("input[name='productResultsNumber']").val();
					oBProjectResult.myOfferMoney = $(this).find("input[name='productMyOfferMoney']").val();
					oBProjectResult.dealMoney = $(this).find("input[name='productDealMoney']").val();
					oBProjectResult.status = 2;
					projectResultList.push(oBProjectResult);
				});
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.do?acceptNum=${confirmStatus}&confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					data : JSON.stringify(projectResultList),
					contentType:"application/json",
					success : function(obj) {//第二轮接受
					layer.alert(obj.msg);
						layer.close(inde);
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					},
					error : function(obj) {
					layer.close(inde);
						layer.alert("第二轮接受失败");
					}
				});
			}
			
		});
	}
	//点击放弃按钮调用此方法
	function cancelAccept(confirmStatus) {
		var projectResult = {};
		var closeLayerIndex = 0;
			layer.confirm('您确定要放弃吗?', {title:'提示',offset: ['222px','500px'],shade:0.01}, function(index){
				layer.close(index);
				var inde = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
		if(confirmStatus == 1) {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.do?confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${result.projectId}",
						"confirmStatus" : confirmStatus,
						"supplierId":"${result.supplierId}",
						"projectResultId":"${result.resultId}"
					},
					success:function(data){
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					layer.close(inde);
					},
					error : function(data) {
						layer.alert("第一轮放弃失败");
					layer.close(inde);
					}
				});
		} else if(confirmStatus == 2) {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.do?confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${result.projectId}",
						"confirmStatus" : confirmStatus,
						"supplierId":"${result.supplierId}",
						"projectResultId":"${result.resultId}"
					},
					success:function(data){
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					layer.close(inde);
					},
					error : function(data) {
						layer.alert("第二轮放弃失败");
					layer.close(inde);
					}
				});
				}
			});
		
	}
	//点击放弃按钮调用此方法
	function close(confirmStatus) {
			var projectResult = {};
		var closeLayerIndex = 0;
		var inde = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
		if(confirmStatus == 1) {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.do?confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${result.projectId}",
						"confirmStatus" : confirmStatus,
						"supplierId":"${result.supplierId}",
						"projectResultId":"${result.resultId}"
					},
					success:function(data){
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					layer.close(inde);
					},
					error : function(data) {
					layer.close(inde);
						layer.alert("第一轮放弃失败");
					}
				});
		} else if(confirmStatus == 2) {
				$.ajax({
					url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.do?confirmStarttime="+confirmStarttime+"&confirmOvertime="+confirmOvertime+"&secondOvertime="+secondOvertime, 
					type:"post",
					dataType:"text",
					data:{
						"projectId" : "${result.projectId}",
						"confirmStatus" : confirmStatus,
						"supplierId":"${result.supplierId}",
						"projectResultId":"${result.resultId}"
					},
					success:function(data){
						window.location.href = "${pageContext.request.contextPath}/supplierQuote/list.html";
					layer.close(inde);
					},
					error : function(data) {
					layer.close(inde);
						layer.alert("第二轮放弃失败");
					}
				});
				}
	   }
	
	/* 显示规则 */
	function showRule(){
		var sh = $("#rule").css("display");
		if(sh == "block"){
			$("#serr").css("right","0px");
			$("#rule").css("display","none");
		}else{
			$("#serr").css("right","210px");
			$("#rule").css("display","block");
		}
	}
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
 	      <span class="margin-left-10 font_sblck">${result.quoteName }</span>
 	      <input type="hidden" name="projectResultId" id="projectResultId" value="${result.resultId }"/>
		    		<input type="hidden" name="quoteName" id="quoteName" value="${result.quoteName }"/>
		    		<input type="hidden" name="confirmStarttime" id="confirmStarttime" value=" confirmInfoVo.confirmStarttime }"/>
		    		<!-- <input type="hidden" name="confirmOvertime" value="${confirmInfoVo.confirmOvertime }"/>这个放到第一轮确认时间那里 -->
		</h2>    		<!-- <input type="hidden" name="secondOvertime" id="secondOvertime" value="${confirmInfoVo.secondOvertime }"/> 这个放到第二轮确认时间那里-->
      </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15" style="width: 50%;">
      <h2 class="count_flow">
 	      <span class="font_sblck">确认结束时间：</span>
 	      <span class="margin-left-10 font_sblck" id="confirmOverTime">
 	      <c:if test="${confirmStatus==1 }">
 	       <fmt:formatDate value='${result.confirmOvertime}' pattern='yyyy-MM-dd HH:mm:ss'/>
 	      </c:if>
 	      <c:if test="${confirmStatus==2 }">
 	      <fmt:formatDate value='${result.secondOvertime}' pattern='yyyy-MM-dd HH:mm:ss'/>
 	      </c:if>
		    	<%-- <fmt:formatDate value="" pattern="yyyy-MM-dd HH:ss:mm"/> --%>
		    	<!-- 第一轮确认结束的时间点 -->
		    	<input type="hidden" name="confirmOvertime" value="${result.confirmOvertime}"/>
		    	<!-- 第二轮确认结束的时间点 -->
		    	<input type="hidden" name="secondOvertime" value=" ${result.secondOvertime }"/>
		    	</span>
		    	</h2>
      </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15">
      <h2 class="count_flow">
      <span class="font_sblck">名次：</span>  
      <span class="margin-left-10 font_sblck">第${result.ranking }名</span>  
      </h2>
       </li>
       <li class="col-md-4 col-sm-6 col-xs-12 pl15"> 
       <h2 class="count_flow">
		   <input type="hidden" id="ranking" value="${result.bidStatus }"/>
		    	<span class="font_sblck">状态：</span>
		    	<span class="margin-left-10 font_sblck">
		    		<c:if test="${result.bidStatus < 7}">
		    		中标
		    		</c:if>
		    		<c:if test="${result.bidStatus >= 7}">
		    		未中标
		    		</c:if>
		    		</span>
		    		</h2>
		    		</li>
		    		<li  class="col-md-4 col-sm-6 col-xs-12 pl15">
		    		<h2 class="count_flow">
		    	<span class="font_sblck">中标比例  :</span>
		    	 <span class="margin-left-10 font_sblck">${result.firstRatio }%
		    		</span>
		    		</h2>
		    		</li>
    </ul>
    <!--<c:if test="${confirmStatus=='-1'}"></c:if>-->
   
     <h2 class="count_flow"><i>2</i>第一轮确认</h2>
    <ul class="ul_list" >
    <%-- <c:if test="-1=-1"> --%><!--  confirmStatus=='-1'} -->
     <li class="col-md-4 col-sm-6 col-xs-12 pl15" style="width: 50%">
     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<c:if test="${confirmStatus=='1'}">
     		<input class="input_group" id="" name="confirmRatioFirst" value="${result.firstRatio }" type="text">%
     		</c:if>
     		<c:if test="${confirmStatus=='2'}">
     		<span id="confirmRatioFirst" style="margin-left: 12px;margin-right: 12px;">${result.firstRatio }</span>%
     		</c:if>
     	</h2>
     </li>
      <li class="col-md-4 col-sm-6 col-xs-12 pl15" style="width: 50%">
      <h2 class="count_flow">
      <span style="margin-left: 22px;margin-right: 12px;">第一轮确认倒计时：</span>
      <c:if test="${confirmStatus==1}">
      <span style="color: red" id="confirmCountDown"></span> 
      </c:if>
      <c:if test="${confirmStatus==2}">
                    第一轮确认倒计时已经结束
      </c:if>
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
		<c:forEach items="${result.OBResultsInfo }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc"  width="5%" title="theProductId">
		  	${vs.index + 1 }
		  	<input type="hidden" name="ResultsNumber" value="${bidproduct.resultsNumber }"/>
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productId }"/>
		  	<input type="hidden" name="productResultsCount" value=""/>
		  	<input type="hidden" name="productResultsNumber" value="${bidproduct.resultsNumber }"/>
		  	<input type="hidden" name="productMyOfferMoney" value="${bidproduct.myOfferMoney }"/>
		  	<input type="hidden" name="productDealMoney" value="${bidproduct.dealMoney }"/>
		  </td>
		  <td class="tc" title="theProductName">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount">
		  ${bidproduct.resultsNumber}
		  	<%-- <fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/> --%>
		  </td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice">${bidproduct.dealMoney} </td>
		 <!--   
		  <td class="tc" title="theProductTotalPrice">$ { (bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 * bidproduct.dealMoney }</td>
		   -->
		  <td class="tc" title="theProductTotalPrice">${bidproduct.dealMoney*bidproduct.resultsNumber }</td>
		</tr>
		</c:forEach>
	</table>
  
 <%--  </c:if> --%>
    </ul>
  <!-- 
  <c:if test="${confirmInfoVo.bidStatus=='-1' || confirmInfoVo.bidStatus==null || confirmInfoVo.bidStatus=='2'}">
  </c:if>
   -->
    <h2 class="count_flow"><i>3</i>第二轮确认</h2>
   <ul class="ul_list">
  	<%-- <c:if test=" confirmStatus=='1'}"> --%>
  	<li class="col-md-3 col-sm-6 col-xs-12 pl15" style="width: 50%">
     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<c:if test="${confirmStatus=='1'}">
     		<span id="confirmRatioSecond" style="margin-left: 12px;margin-right: 12px;">0</span>%
     		</c:if>
     		<c:if test="${confirmStatus=='2'}">
     		<input class="input_group" id="" name="confirmRatioSecond" value="${result.secondRatio }" type="text">%
     		</c:if>
     		
     	</h2>
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 pl15" style="width: 50%">
    	<h2 class="count_flow">
     			<span style="margin-left: 22px;margin-right: 12px;">第二轮确认倒计时：</span>
     			<c:if test="${confirmStatus==1}">
     			未开始
      			</c:if>
     			 <c:if test="${confirmStatus==2}">
      			<span style="color: red" id="confirmCountDown2"></span> 
      			</c:if>
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
		<c:forEach items="${result.OBResultsInfo }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc" title="theProductId2" width="5%">
		  	 ${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value=" ${bidproduct.productId }"/>
		  	<input type="hidden" name="productResultsNumber" value=" ${bidproduct.resultsNumber }"/>
		  	<input type="hidden" name="productMyOfferMoney" value=" ${bidproduct.myOfferMoney }"/>
		  	<input type="hidden" name="productDealMoney" value=" ${bidproduct.dealMoney }"/>
		  </td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount2">
		  ${bidproduct.resultsNumber}
		  <%-- <fmt:formatNumber type="number" value=" $ {bidproduct.productNum - ((bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100) }"/> --%>
		  </td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice2">${bidproduct.dealMoney}</td>
		  <td class="tc" title="theProductTotalPrice2">${bidproduct.dealMoney*bidproduct.resultsNumber } <!--  (bidproduct.productNum - ((bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100)) * bidproduct.dealPrice } --></td>
		</tr>
		</c:forEach>
	</table>
  <%-- 	</c:if> --%>
    </ul>
  
  <div class="col-md-12 clear tc mt10">
  <button class="btn" onclick="confirmAccept('${confirmStatus }')">接受</button>
  <input type="hidden" value=" ${confirmStatus }" id="currentConfirmStatus"/>
  <button class="btn" onclick="cancelAccept('${confirmStatus }')">放弃</button>
  </div>
  
  <div class="rule_search" id = "serr" onclick = "showRule()">
  	<a href="javaScript:void(0);">查看规则</a>     
 	<div class="rule_desc" id = "rule" style="display: none;"> 
  		<h4 class="red">规则说明：</h4>
  		<p>1、按所有供应商报价的总价，按基准法计算成交价；</p>
  		<p>2、所有成交供应商的产品成交单价都按第一名执行：</p>
  		<p>3、确认分为两轮确认，第一轮确认基本成交数量（所有供应商同步进行），第二轮确认追加成交数量，从第一名开始，
  		如果第一名不要的部分，给第二名，依次排序。不成交数量打印出来显示。</p>
  	</div>
  </div>
  
  </div>
</body>
</html>