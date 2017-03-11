<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
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
		
		function totalPrice(obj,id){
			// 获取采购数量
			var count = $(obj).attr("data-count");
			// 获取报价金额
			var unitPrice = $(obj).val();
			
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
				$("#"+id).text(x);
				calTotalPrice();
				
			}else{
				$("#"+id).html("");
				$("#totalPrice").html("");
				calTotalPrice();
			}
			
		}
		
		// 计算总价
		function calTotalPrice(){
			// 总价定义
			var total = 0;
			for(var i = 0;i < ids.length; i++) {
				var id = ids[i];
				var signalPrice = parseInt($("#"+id).html());
				if(!isNaN(signalPrice)){
					total = total + signalPrice;
				}
			}
			if(total == 0){
				$("#totalPrice").html("");
			}else{
				$("#totalPrice").html(total);
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
			$("#productForm").attr("action","${pageContext.request.contextPath}/obrule/ruleList.html");
			$("#productForm").submit();
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
		<table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="tc">竞价标题</td>
				    <td class="tc">${ obProject.name }</td>
				    <td class="tc">交货截止时间</td>
				    <td class="tc"><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="HH:ss:mm"/></td>
				  </tr>
				  <tr>
				    <td class="tc">交货地点</td>
				    <td class="tc">${ obProject.deliveryAddress }</td>
				    <td class="tc">成交供应商数</td>
				    <td class="tc">${ obProject.tradedSupplierCount }</td>
				  </tr>
				  <tr>
				    <td class="tc">运杂费</td>
				    <td class="tc">
				    	<c:if test="${ !empty obProject.transportFees }">
					    	${ obProject.transportFees }元
				    	</c:if>
				    </td>
				    <td class="tc"></td>
				    <td class="tc"></td>
				  </tr>
				  <tr>
				    <td class="tc">需求单位</td>
				    <td class="tc">${ obProject.demandUnit }</td>
				    <td class="tc">联系人：${ obProject.contactName }</td>
				    <td class="tc">联系电话：${ obProject.contactTel }</td>
				  </tr>
				  <tr>
				    <td class="tc">采购机构</td>
				    <td class="tc">${ orgName }</td>
				    <td class="tc">采购联系人：${ obProject.orgContactName }</td>
				    <td class="tc">联系电话：${ obProject.orgContactTel }</td>
				  </tr>
				  <tr>
				    <td class="tc">竞价开始时间</td>
				    <td class="tc"><fmt:formatDate value="${ obProject.startTime }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
				    <td class="tc">竞价结束时间</td>
				    <td class="tc"><fmt:formatDate value="${ obProject.endTime }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
				  </tr>
				  <tr>
				    <td class="tc">竞价内容</td>
				    <td class="tc" colspan="3" style="height:130px">${ obProject.content }</td>
				  </tr>
				  <tr>
				    <td class="tc">竞价文件</td>
				    <c:if test="${ !empty obProject.attachmentId }">
					    <td class="tc">
					    	${ obProject.attachmentId }.pdf
					    	<td class="tc"><button class="btn" onclick="findpdfFile('${obProject.attachmentId}')">查看</button></td>
					    </td>
				    </c:if>
				    <c:if test="${ empty obProject.attachmentId }">
				    	<td class="tc">无</td>
				    </c:if>
				  </tr>
				 </tbody>
			 </table>
  </div> 
  <div class="clear" ></div>
  <form id="productForm" name="" method="post">
  	<input type="hidden" name="titleId" value="${ obProject.id }">
	  <div>
	    <h2 class="count_flow"><i>2</i>产品信息</h2>
		<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="w30 info"><input alt="" type="checkbox"></th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info">报价</th>
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
			<c:forEach items="${ oBProductInfoList }" var="productInfo">
				<tr>
				  <td class="tc"><input type="checkbox" alt=""></td>
				  <td class="tc">${ productInfo.obProduct.name }</td>
				  <td class="tc">${ productInfo.limitedPrice }</td>
				  <td class="tc">${ productInfo.purchaseCount }</td>
				  <td><input id="" data-count="${ productInfo.purchaseCount }" name="" onkeyup="totalPrice(this,'${productInfo.obProduct.id}')" type="text" class="w230 mb0 border0" /></td>
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
   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   	</div>
 </div>
</body>
</html>