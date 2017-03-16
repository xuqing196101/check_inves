<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>确认结果页面</title>
<script type="text/javascript">
	//倒计时方法
	function getRTime(){
		var getOverTime = $("#confirmOverTime").text();
		var EndTime= new Date("${confirmInfoVo.confirmOvertime}"); //截止时间
		var NowTime = new Date();
		var t =EndTime.getTime() - NowTime.getTime();
		if(t > 0) {
			var d = Math.floor(t/1000/60/60/24);
			var h = Math.floor(t/1000/60/60%24);
			var m = Math.floor(t/1000/60%60);
			var s = Math.floor(t/1000%60);
			$("#confirmCountDown").text(d + "天" + h + "时" + m + "分" + s + "秒");
		} else {
			$("#confirmCountDown").text("第一轮确认倒计时已经结束");
			clearInterval(downTimer);
		}
	}
	function getRTime2(){
		var getOverTime = $("#confirmOverTime").text();
		var EndTime= new Date("${confirmInfoVo.confirmOvertime}"); //截止时间
		var sysNowTime = new Date("${sysNowTime}");
		var clientNowTime = new Date();
		var t = EndTime.getTime() - clientNowTime.getTime();
		if(t > 0) {
			var d = Math.floor(t/1000/60/60/24);
			var h = Math.floor(t/1000/60/60%24);
			var m = Math.floor(t/1000/60%60);
			var s = Math.floor(t/1000%60);
			$("#confirmCountDown2").text(d + "天" + h + "时" + m + "分" + s + "秒");
		} else {
			$("#confirmCountDown2").text("第二轮确认倒计时已经结束");
			clearInterval(downTimer2);
		}
	}
	//占比下调,返回对应占比后数量@param 值
	function getDownRatioVal(passVal,beforeRatio,afterRatio) {
		var br = parseInt(beforeRatio)/100;
		var ar = parseInt(afterRatio)/100;
		return Math.ceil((parseInt(passVal) / br) * ar);
	}
	$(function() {
		//定时器调用
		var downTimer = setInterval(getRTime,1000);
		var downTimer2 = setInterval(getRTime2,1000);
		
		var currentVal = $("input[name='confirmRatioFirst']").val();
		var changeRatioCounts = [];
		$("[title='theProductCount']").each(function(index,element) {
			changeRatioCounts.push($(this).text());
		});
		var productPrices = [];
		$("[title='theProductPrice']").each(function() {
			productPrices.push($(this).text());
		});
		$("input[name='confirmRatioFirst']").keyup(function(event) {
			var currentPressKey = event.keyCode;//当前输入的字符
			var afterInputVal = $(this).val();
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
						var afterCount = getDownRatioVal(changeRatioCounts[index],currentVal,afterInputVal);
						$(this).text((afterCount * productPrices[index]).toFixed(2));
						$("[title='theProductCount']").each(function(indexPc,element) {
							if(index == indexPc) {
								$(this).text(afterCount);
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
		});
		
		
		
	});
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
					oBProjectResult.resultCount = $(this).find("input[name='productNum']").val();
					oBProjectResult.offerPrice = $(this).find("input[name='productQuotePrice']").val();
					oBProjectResult.status = 1;
					projectResultList.push(oBProjectResult);
				});
				
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.html",
					data : JSON.stringify(projectResultList),
					dataType : "json",
					contentType:"application/json",
					success : function(obj) {
						alert("suc");
					},
					error : function(obj) {
						alert("error");
					}
				});
			} else if(currentConfirmStatus == "1") {
				var projectResultList = [];
				var oBProjectResult = {};
				
				$("[title='theProductId2']").each(function(index,element) {
					oBProjectResult.projectId = "${projectId}";
					oBProjectResult.supplierId = "${supplierId}";
					oBProjectResult.proportion = $("input[name='confirmRatioFirst2']").val();
					oBProjectResult.productId = $(this).find("input[name='productId2']").val();
					oBProjectResult.resultCount = $(this).find("input[name='productNum2']").val();
					oBProjectResult.offerPrice = $(this).find("input[name='productQuotePrice2']").val();
					oBProjectResult.status = 2;
					projectResultList.push(oBProjectResult);
				});
				
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/supplierQuote/uptConfirmAccept.html",
					data : JSON.stringify(projectResultList),
					dataType : "json",
					contentType:"application/json",
					success : function(obj) {
						alert("suc");
					},
					error : function(obj) {
						alert("error");
					}
				});
			}
			
		});
		//url saveConfirmQuoteInfo
	}
	function cancelAccept() {
		var projectResult = {};
		var closeLayerIndex = 0;
		layer.confirm('您确定要放弃吗?', {title:'提示',offset: ['222px','500px'],shade:0.01}, function(index){
			//window.location.href="${pageContext.request.contextPath}/performance/deletePerfor.html?id="+ids;
			layer.close(index);
			$.ajax({
				url:"${pageContext.request.contextPath}/supplierQuote/uptConfirmDrop.html",
				type:"post",
				dataType:"text",
				data:{
					"projectId" : "${supplierId}"
				},
				success:function(data){
					window.history.go(-1);
				},
				error : function(data) {
					alert("放弃失败");
				}
			});
		});
		//url saveConfirmQuoteInfo
	}
	
	$(function() {
		var allCount = 0;
		$("[title='theProductTotalPrice']").each(function(index,element) {
			allCount += parseInt($(this).text());
		});
		$("[title='allProductTotalPrice']").text(allCount.toFixed(2));
	});
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">确认结果</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div> 
    
    <!-- 表格开始    projectResult -->
    <div class="container container_box">
    
    <h2>竞价结果 - 查询管理
    	<span style="font-weight: lighter;font-size: 18px;padding-left: 22px;">
    		竞价标题：${confirmInfoVo.quoteName }<input type="hidden" id="quoteName" value="${confirmInfoVo.quoteName }"/>
    	</span>
    </h2>
    <h2 class="count_flow">排名：${confirmInfoVo.ranking }<input type="hidden" id="quoteName" value="${confirmInfoVo.ranking }"/>
    	<span style="margin-left: 22px;">状态：</span>
    		<c:if test="${confirmInfoVo.ranking < 7}">
    		中标
    		</c:if>
    		<c:if test="${confirmInfoVo.ranking >= 7}">
    		中标
    		</c:if>
    	<span style="margin-left: 22px;margin-right: 12px;">中标比例  :</span>
    		<input id="" name="" readonly="readonly" value="50" type="text" class="w5 mb0 ">%
    </h2>
    <h2 class="count_flow">确认结束时间：
    	<span id="confirmOverTime">
    	<fmt:formatDate value="${confirmInfoVo.confirmOvertime }" pattern="yyyy-MM-dd HH:ss:mm"/>
    	<input type="hidden" id="quoteName" value="${confirmInfoVo.confirmOvertime }"/>
    	</span>
    </h2>
    <c:if test="${confirmStatus=='0'}">
     <div>
     <div class="clear total f22">
     	<span class="fl block">基本数量---第一轮确认：</span>
     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<input id="" name="confirmRatioFirst" value="${confirmInfoVo.bidRatio }" type="text" class="w5 mb0 ">%
     		<span style="padding-left: 22px;">确认倒计时：<span id="confirmCountDown"></span></span>
     	</h2>
     </div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice"></td>
		</tr>
		<c:forEach items="${confirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc" title="theProductId">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value="${bidproduct.id }"/>
		  	<input type="hidden" name="productName" value="${bidproduct.productName }"/>
		  	<input type="hidden" name="productNum" value="${bidproduct.productNum }"/>
		  	<input type="hidden" name="productQuotePrice" value="${bidproduct.myOfferMoney }"/>
		  	<input type="hidden" name="productResultCount" value=""/>
		  </td>
		  <td class="tc" title="theProductName">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount">
		  	<fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/>
		  </td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice">${bidproduct.dealMoney }</td>
		  <td class="tc" title="theProductTotalPrice">${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 * bidproduct.dealMoney }</td>
		</tr>
		</c:forEach>
		<!-- 
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc" title="theProductCount">20</td>
		  <td class="tc">200</td>
		  <td class="tc" title="theProductPrice">200</td>
		  <td class="tc" title="theProductTotalPrice">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc" title="theProductCount">10</td>
		  <td class="tc">300</td>
		  <td class="tc" title="theProductPrice">300</td>
		  <td class="tc" title="theProductTotalPrice">3000</td>
		</tr>
		 -->
	</table>
  </div>
  </div>
  </c:if>
  
  <c:if test="${confirmInfoVo.bidStatus=='-1' || confirmInfoVo.bidStatus==null || confirmInfoVo.bidStatus=='2'}">
  <div>
     <div class="clear total f22"><span class="fl block">基本数量---第二轮确认：</span>
     	<h2 class="count_flow">
     		<span style="margin-left: 22px;margin-right: 12px;">确认成交</span>
     		<input id="" name="confirmRatioSecond" value="20" type="text" class="w5 mb0 ">%
     			<span style="padding-left: 22px;">第二轮确认倒计时：</span>
     			<span id="confirmCountDown2">未开始</span>
     	</h2>
     </div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice2">12000</td>
		</tr>
		<c:forEach items="${confirmInfoVo.bidProductList }" var="bidproduct" varStatus="vs">
		<tr>
		  <td class="tc">
		  	${vs.index + 1 }
		  	<input type="hidden" name="productId" value=""/>
		  	<input type="hidden" name="productName" value=""/>
		  	<input type="hidden" name="productNum" value=""/>
		  	<input type="hidden" name="productQuotePrice" value=""/>
		  	<input type="hidden" name="productResultCount" value=""/>
		  </td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc" title="theProductCount2"><fmt:formatNumber type="number" value="${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 }"/></td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" title="theProductPrice2">${bidproduct.dealMoney }</td>
		  <td class="tc" title="theProductTotalPrice2">${(bidproduct.productNum * confirmInfoVo.bidRatio - bidproduct.productNum * confirmInfoVo.bidRatio % 100) / 100 * bidproduct.dealMoney }</td>
		</tr>
		</c:forEach>
		<!-- 
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc" title="theProductCount2">20</td>
		  <td class="tc">200</td>
		  <td class="tc" title="theProductPrice2">200</td>
		  <td class="tc" title="theProductTotalPrice2">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc" title="theProductCount2">10</td>
		  <td class="tc">300</td>
		  <td class="tc" title="theProductPrice2">300</td>
		  <td class="tc" title="theProductTotalPrice2">3000</td>
		</tr>
		 -->
	</table>
  </div>
  </div>
  	</c:if>
  
  
  <div class="star_red" style="display: none;">规则1、第一轮确认如果都按比例成交，则没有第二轮确认，如果不是按比例成交，则有第二轮确认，第一轮正在确认的时候不显示<br/>第二轮数据，只有所有供应商第一轮确认完毕后，才有第二轮确认。<br/>
						规则2、自动生成成交比例（可修改），修改后成交数量和总价发生变化，未中标的的显示未中标，不显示成交比例，未中标的只能<br/>看到已中标明细，未中标的只有返回按钮。
  </div>
  <div class="col-md-12 clear tc mt10">
  <button class="btn" onclick="confirmAccept()">接受</button>
  <input type="hidden" value="${confirmStatus }" id="currentConfirmStatus"/>
  <button class="btn" onclick="cancelAccept()">放弃</button>
  </div>
  </div>
</body>
</html>