<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<title>供应商报价页面</title>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<%@ include file="/WEB-INF/view/bss/ob/common/obShowProductCommon.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	
	<script type="text/javascript">
		var t = '${beginQuotoTime}';
		var ts = '${beginQuotoTimeSecond}';
		var afterTime;
		var downTimer;
		if(t != ''){
			// 第一次报价
			afterTime = t;
		}
		if(ts != ''){
			// 第二次报价
			afterTime = ts;
		}

		function getRTime(){
			if(t > 0 || ts > 0){
				afterTime = afterTime - 1000;
				if(afterTime > 0) {
					var d = Math.floor(afterTime/1000/60/60/24);
					var h = Math.floor(afterTime/1000/60/60%24);
					var m = Math.floor(afterTime/1000/60%60);
					var s = Math.floor(afterTime/1000%60);
					if(t != ''){
						$("#quotoCountDown").text(d + "天" + h + "时" + m + "分" + s + "秒");
					}
					if(ts != ''){
						$("#quotoCountDownSecond").text(d + "天" + h + "时" + m + "分" + s + "秒");
					}
				} else {
					if(t != ''){
						$("#quotoCountDown").text("报价时间已结束");
						clearInterval(downTimer);
					}
					if(ts != ''){
						$("#quotoCountDownSecond").text("二次报价时间已结束");
						clearInterval(downTimer);
					}
				}
			}else{
				if(t != ''){
					$("#quotoCountDown").text("报价时间已结束");
					clearInterval(downTimer);
				}
				if(ts != ''){
					$("#quotoCountDownSecond").text("二次报价时间已结束");
					clearInterval(downTimer);
				}
			}
		}
		var ids = [];
		$(function(){
			var idsStr = '${productIds}';
			ids = idsStr.split(",");
			// 报价倒计时
			downTimer = setInterval(getRTime, 1000);
		})
		
		function totalPrice(obj,id,limitedPriceId){
			// 获取采购数量
			var count = $(obj).attr("data-count");
			// 获取报价金额
			var unitPrice = $(obj).val();
			
			var limitedPrice = $("#"+limitedPriceId).html();
			if(parseFloat(limitedPrice) != 0 && parseFloat(unitPrice) > parseFloat(limitedPrice)){
				layer.msg("对不起，报价不能大于限价");
				$(obj).val("");
				$("#"+id).html("");
				//$("#totalPrice").html("");
				calTotalPrice();
				return;
			}
			// 判断单价输入是否为空
			if(unitPrice != ''){
				if(! /^-?\d+$/.test(unitPrice) && ! /^-?\d+\.?\d{0,2}$/.test(unitPrice)){
					layer.msg("请您输入报价(整数或保留两位小数)");
                    $(obj).val("");
					$("#"+id).html("");
					calTotalPrice();
					return;
				}
				// 限制输入价格最低为0.5元
				/*if(unitPrice < 0.05){
					layer.msg("报价最低0.05元");
					$("#"+id).html("");
					return;
				}*/
				var x = parseInt(count) * unitPrice;
				//var unitPriceFloat = toDecimal(x);
				// 判断用户输入的（数量*单价）是否等于计算出的单个商品的总价
				/* if(x/10000 != (x/10000).toFixed(4)){
					layer.msg("您的输入有误，请重新输入");
					$(obj).val("");
					$("#"+id).html("");
					calTotalPrice();
					return ;
				}  */
				// 判断输入长度
				if(unitPrice.length > 20){
					$(obj).val("");
					$("#"+id).html("");
					calTotalPrice();
					layer.msg("您输入报价长度过长");
					return;
				}
				
				$("#"+id).text((x/10000).toFixed(4));
				calTotalPrice();
				
			}else{
				$("#"+id).html("");
				$("#totalPrice").html("");
				calTotalPrice();
			}
			
		}
		
		// 字符串转浮点数
		function toDecimal(signalTotalPrice) { 
	        var f = Math.round(signalTotalPrice*100)/100;
	        var s = f.toString();
	        var rs = s.indexOf('.');
	        if (rs < 0) {
	            rs = s.length;
	            s += '.';
	        } 
	        while (s.length <= rs + 2) {
	            s += '0';
	        }
	        return s;
		 } 
		
		// 计算总价
		function calTotalPrice(){
			// 总价定义
			var total = 0;
			for(var i = 0;i < ids.length; i++) {
				var id = ids[i];
				var signalPrice = $("#"+id).html();
				// 判断是否为空
				if(signalPrice != ''){
					if(total == 0){
						total = parseFloat(signalPrice);
					}else{
						total = parseFloat(total) + parseFloat(signalPrice);
					}
				}
			}
			if(total == 0){
				$("#totalPrice").html("");
			}else{
				$("#totalPrice").html(total.toFixed(4));
			}
		}
		
		// 产品信息表单提交
		function confirm(){
			for(var i = 0;i < ids.length; i++) {
				var id = ids[i];
				var signalPrice = parseInt($("#"+id).html());
				if(isNaN(signalPrice)){
					$("#"+id).html("");
					layer.msg("请您输入报价(整数或保留两位小数)");
					return;
				}
			}
			
			// 获取供应商报价总金额--提交前的提示信息里面需要的数据
			var quotoTotalPrice = $("#totalPrice").html();

			if(quotoTotalPrice == ''){
				layer.msg("对不起！您的输入有误");
				return;
			}

			layer.confirm("本次竞价项目您的报价总金额为 <b><span style='color:red;font-size:16px'>"+quotoTotalPrice+" 万元</span></b>，是否确认提交？", {
			    btn: ['确定','取消'], //按钮
			}, function(index){
				$("#showQuotoTotalPrice").val(quotoTotalPrice);
			    layer.close(index);
			    var inde = layer.load(0, {
					shade : [ 0.1, '#fff' ],
					offset : [ '45%', '53%' ]
				});
			    $.post("${pageContext.request.contextPath}/supplierQuote/saveQuoteInfo.do", $("#productForm").serialize(), function(data) {
					if (data.status == 200) {
						layer.close(inde);
						layer.confirm("操作成功，请等待确认结果！本次竞价项目您的报价总金额为 <b><span style='color:red;font-size:16px'>"+data.data+" 万元</span></b>,请在报价截止时间后，查看本次中标结果！",{
							btn:['确定']
						},function(){
								window.location.href="${pageContext.request.contextPath}/supplierQuote/list.html";
							}
						) 
					}
					if(data.status == 500){
						layer.close(inde);
						layer.confirm(data.msg,{
							btn:['确定']
						},function(){
								window.location.href="${pageContext.request.contextPath}/supplierQuote/list.html";
							}
						) 
					}
				});
			});
		}
		
		// 返回
		function forWord(){
			layer.confirm('您确认要退出吗？', {
			    btn: ['确认','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(index){
			    layer.close(index);
			    window.history.go(-1);        // 返回+刷新  
			});
		}
		
		
		// 查看文件
		function findFile(filePath){
			$.ajax({
				url: "${pageContext.request.contextPath }/open_bidding/downloadFile.do",
				type: "POST",
				data: {
					filePath: filePath
				},
				success: function(data) {
				}
			});
		}
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">供应商报价</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="star_red">提示：报价一旦提交将不能修改，请谨慎提交！</div>
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
			<%@ include file="/WEB-INF/view/bss/ob/supplier/biddingInfoCommon.jsp" %>
  </div> 
   <div>
	 <h2 class="count_flow"><i>2</i>竞价规则详情</h2>
	   <%@ include file="/WEB-INF/view/bss/ob/biddingRules/ruleCommon.jsp" %>
  </div>
  <div class="clear" ></div>
  <c:if test="${ not empty oBResultsInfo }">
  	<div>
	    <h2 class="count_flow"><i>3</i>第一轮产品报价信息</h2>
	  		<%@ include file="/WEB-INF/view/bss/ob/supplier/findQuotoIssueInfoCommon.jsp" %>
	  </div>
  </c:if>
  <form id="productForm" name="" method="post">
  	<input type="hidden" name="titleId" value="${ obProject.id }">
  	 <input type="hidden" id="showQuotoTotalPrice" name="showQuotoTotalPrice" value="">
	  <div>
	  	<c:if test="${ quotoFlag eq 'firstQuoto'}">
		    <h2 class="count_flow"><i>3</i>产品信息 <font style="margin-left: 15px">报价时间倒计时：</font>
		    <span style="color: red" id="quotoCountDown"></span></h2>
	    </c:if>
	    <c:if test="${ quotoFlag eq 'secondQuoto'}">
		    <h2 class="count_flow"><i>3</i>产品信息 <font style="margin-left: 15px">二次报价时间倒计时：</font>
		    <span style="color: red" id="quotoCountDownSecond"></span></h2>
	    </c:if>
		<div class="tab-content padding-left-20 padding-right-20">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="info">序号</th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info" width="5px">报价（元）</th>
			  <th class="info">总价（万元）</th>
			  <th class="info">备注信息</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"><span name="quotoTotalPrice" id="totalPrice"></span></td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
				<tr>
				  <input type="hidden" name="quotoFlag" value="${ quotoFlag }">
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].productId" value="${ productInfo.obProduct.id }">
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].resultsNumber" value="${ productInfo.purchaseCount }">
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].limitPrice" value="${ productInfo.limitedPrice }">
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].remark" value="${ productInfo.remark }">
				  <td class="tc">${ vs.index + 1 }</td>
				  <td class="tc" id="t_${productInfo.id}" onmouseout="closePrompt()" onmouseover="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')" title="${ productInfo.obProduct.name }">
				  	<c:if test="${fn:length(productInfo.obProduct.name) > 30 }">${fn:substring(productInfo.obProduct.name, 0, 30)}...</c:if>
					<c:if test="${fn:length(productInfo.obProduct.name) <= 30 }">${productInfo.obProduct.name }</c:if>			  
				  </td>
				  <td class="tc" id="${ vs.index }">${ productInfo.limitedPrice }</td>
				  <td class="tc">${ productInfo.purchaseCount }</td>
				  <td class="tc" width="3px"><input id="" data-count="${ productInfo.purchaseCount }" name="obResultsInfoExt[${ vs.index }].myOfferMoney" maxlength="20" onkeyup="totalPrice(this,'${productInfo.obProduct.id}','${ vs.index }')" type="text" class="w230 mb0 border0" /></td>
				  <td class="tc" id="${ productInfo.obProduct.id }"></td>
				  <td class="tc" title="${ productInfo.remark }">
				  	<c:if test="${fn:length(productInfo.remark) > 30 }">${fn:substring(productInfo.remark, 0, 30)}...</c:if>
					<c:if test="${fn:length(productInfo.remark) <= 30 }">${productInfo.remark }</c:if>	
				  </td>
				</tr>
			</c:forEach>
		</table>
	  </div>
	  </div>	 
  </form>
	<div class="col-md-12 clear tc mt10">
   		<button class="btn btn-windows save" onclick="confirm()">提交</button>
   		<button class="btn btn-windows back" type="button" onclick="forWord()">返回</button>
   	</div>
 </div>
</body>
</html>