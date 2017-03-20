<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
	
	<script type="text/javascript">
		var ids = [];
		$(function(){
			var idsStr = '${productIds}';
			ids = idsStr.split(",");
		})
		
		function totalPrice(obj,id,limitedPriceId){
			// 获取采购数量
			var count = $(obj).attr("data-count");
			// 获取报价金额
			var unitPrice = $(obj).val();
			
			var limitedPrice = $("#"+limitedPriceId).html();
			if(parseFloat(unitPrice) > parseFloat(limitedPrice)){
				layer.msg("对不起，报价不能大于限价");
				$("#"+id).html("");
				$("#totalPrice").html("");
				return;
			}
			// 判断单价输入是否为空
			if(unitPrice != ''){
				if(! /^-?\d+$/.test(unitPrice) && ! /^-?\d+\.?\d{0,2}$/.test(unitPrice)){
					layer.msg("请输入整数或两位小数");
					$("#"+id).html("");
					$("#totalPrice").html("");
					calTotalPrice();
					return;
				}
				var x = parseInt(count) * unitPrice;
				var unitPriceFloat = toDecimal(x);
				$("#"+id).text(unitPriceFloat);
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
				var signalPrice = toDecimal($("#"+id).html());
				if(total == 0){
					total = signalPrice;
				}else{
					total = parseFloat(total) + parseFloat(signalPrice);
				}
			}
			if(total == 0){
				$("#totalPrice").html("");
			}else{
				$("#totalPrice").html(toDecimal(total));
			}
		}
		
		// 产品信息表单提交
		function confirm(){
			for(var i = 0;i < ids.length; i++) {
				var id = ids[i];
				var signalPrice = parseInt($("#"+id).html());
				if(isNaN(signalPrice)){
					layer.msg("输入格式错误或者输入信息不全");
					return;
				}
			}
			
			layer.confirm('您确认要提交吗？', {
			    btn: ['确定','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(index){
			    layer.close(index);
				$.post("${pageContext.request.contextPath}/supplierQuote/saveQuoteInfo.do", $("#productForm").serialize(), function(data) {
					if (data.status == 200) {
						layer.confirm(data.data,{
							btn:['确定']
						},function(){
								window.location.href="${pageContext.request.contextPath}/supplierQuote/list.html";
							}
						) 
					}
					if(data.status == 500){
						layer.alert(data.msg);
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
    <h2 class="count_flow"><b>1</b>竞价基本信息</h2>
		<table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="tc"><b>竞价标题</b></td>
				    <td class="tc">${ obProject.name }</td>
				    <td class="tc"><b>交货时间</b></td>
				    <td class="tc"><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
				  </tr>
				  <tr>
				    <td class="tc"><b>交货地点</b></td>
				    <td class="tc">${ obProject.deliveryAddress }</td>
				    <td class="tc"><b>成交供应商数</b></td>
				    <td class="tc">${ obProject.tradedSupplierCount }</td>
				  </tr>
				  <tr>
				    <td class="tc"><b>运杂费</b></td>
				    <td class="tc">
				    	<c:if test="${ !empty obProject.transportFees }">
					    	${ obProject.transportFees }元
				    	</c:if>
				    </td>
				    <td class="tc"></td>
				    <td class="tc"></td>
				  </tr>
				  <tr>
				    <td class="tc"><b>需求单位</b></td>
				    <td class="tc">${ obProject.demandUnit }</td>
				    <td class="tc"><b>联系人：</b>${ obProject.contactName }</td>
				    <td class="tc"><b>联系电话：</b>${ obProject.contactTel }</td>
				  </tr>
				  <tr>
				    <td class="tc"><b>采购机构</b></td>
				    <td class="tc">${ orgName }</td>
				    <td class="tc"><b>采购联系人：</b>${ obProject.orgContactName }</td>
				    <td class="tc"><b>联系电话：</b>${ obProject.orgContactTel }</td>
				  </tr>
				  <tr>
				    <td class="tc"><b>竞价开始时间</b></td>
				    <td class="tc"><fmt:formatDate value="${ obProject.startTime }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
				    <td class="tc"><b>竞价结束时间</b></td>
				    <td class="tc"><fmt:formatDate value="${ obProject.endTime }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
				  </tr>
				  <tr>
				    <td class="tc"><b>竞价内容</b></td>
				    <td colspan="3">${ obProject.content }</td>
				  </tr>
				  <tr>
				    <td class="tc"><b>竞价文件</b></td>
				    <td class="tc">
				    <c:if test="${ !empty uploadFiles  }">
				    	<c:forEach items="${ uploadFiles }" var="file">
				     		${ file.name } <button class="btn" onclick="download('${file.id}','2','','')"> 查看 </button>
				     		<br />
				       	</c:forEach>
				     </c:if>
				     <c:if test="${ empty uploadFiles  }">
				     	无
				     </c:if>
				  </tr>
				 </tbody>
			 </table>
  </div> 
  <div class="clear" ></div>
  <form id="productForm" name="" method="post">
  	<input type="hidden" name="titleId" value="${ obProject.id }">
	  <div>
	    <h2 class="count_flow"><b>2</b>产品信息</h2>
		<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="w30 info"><input alt="" type="checkbox"></th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info" width="10px">报价</th>
			  <th class="info">总价（元）</th>
			  <th class="info">备注信息</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"><input type="checkbox" alt=""></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc" id="totalPrice"></td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
				<tr>
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].productId" value="${ productInfo.obProduct.id }">
				  <input type="hidden" name="obResultsInfoExt[${ vs.index }].resultsNumber" value="${ productInfo.purchaseCount }">
				  <td class="tc"><input type="checkbox" alt=""></td>
				  <td class="tc">${ productInfo.obProduct.name }</td>
				  <td class="tc" id="${ vs.index }">${ productInfo.limitedPrice }</td>
				  <td class="tc">${ productInfo.purchaseCount }</td>
				  <td class="tc" width="3px"><input id="" data-count="${ productInfo.purchaseCount }" name="obResultsInfoExt[${ vs.index }].myOfferMoney" onkeyup="totalPrice(this,'${productInfo.obProduct.id}','${ vs.index }')" type="text" class="w230 mb0 border0" /></td>
				  <td class="tc" id="${ productInfo.obProduct.id }"></td>
				  <td class="tc">${ productInfo.obProduct.remark }</td>
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