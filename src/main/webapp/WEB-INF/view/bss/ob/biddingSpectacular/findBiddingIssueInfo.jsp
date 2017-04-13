<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<title>竞价信息查看页面</title>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<script type="text/javascript">
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
			  // 显示
    function showPrompt(id,selectID){
   		  if(id){
	   		  $.ajax({
	                async: false,
					url: "${pageContext.request.contextPath }/product/productType.do",
					type: "POST",
					data: {productId:id},
					success: function(data) {
					if(data){
						var quality = data.qualityTechnicalStandard;
						if(quality == null){
							quality = "无";
						}
	   	  				layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+quality, 
	   	    			'#'+id, {tips: [1, '#78BA32'],time:-1,area: ['500px', 'auto'],});
					}else{
					 inder=layer.tips("", 
	       	    '#'+selectID, {tips: [1, '#78BA32']});
					}
			      },error:function(){
			       layer.tips("错误！", 
	       	    '#'+selectID, {tips: [1, '#78BA32']});
			      }
	           });
           }
       	}
		  //关闭
	function closePrompt(){
	layer.closeAll('tips');
	}
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">竞价管理</a></li><li><a href="javascript:void(0)">竞价信息查看</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
   <!-- 修改订列表开始-->
   <div class="container container_box" onmouseover="closePrompt()">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div> 
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
    <div class="ul_list">
		<%@ include file="/WEB-INF/view/bss/ob/biddingSpectacular/biddingInfoCommon.jsp" %>
     </div>
  </div> 
  <div class="clear" ></div>
  <%-- <form id="productForm" name="" method="post" >
  	<input type="hidden" name="titleId" value="${ obProject.id }">
	  <div>
	    <h2 class="count_flow"><i>2</i>产品信息</h2>
	    <div class="ul_list">
			<div class="content table_box">
		    	<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
				  <th class="info">序号</th>
				  <th class="info" width="25%">定型产品名称</th>
				  <th class="info">限价（元）</th>
				  <th class="info">采购数量</th>
				  <th class="info">总价（万元）</th>
				  <th class="info" width="30%">备注信息</th>
				</tr>
				</thead>
				<tr>
                  <td></td>
				  <td class="tc" colspan="3">合计</td>
				  <td class="tc"><b>${ totalCountPriceBigDecimal }</b></td>
                  <td></td>
				</tr>
				<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
					<tr>
					  <td class="tc">${ vs.index+1 }</td>
					  <td class="tc" id="t_${productInfo.id}" onmousemove="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')" title="${ productInfo.obProduct.name }">
						<c:if test="${fn:length(productInfo.obProduct.name) > 20 }">${fn:substring(productInfo.obProduct.name, 0, 20)}...</c:if>
						<c:if test="${fn:length(productInfo.obProduct.name) <= 20 }">${productInfo.obProduct.name }</c:if>	
					  </td >
					  <td class="tc">${ productInfo.limitedPrice }</td>
					  <td class="tc">${ productInfo.purchaseCount }</td>
					  <td class="tc">${ productInfo.totalMoney }</td>
					  <td class="tc">${ productInfo.remark }</td>
					</tr>
				</c:forEach>
			</table>
		  </div>
		 </div>
	  </div>	 
  </form> --%>
  <div>
	 <h2 class="count_flow"><i>2</i>产品信息</h2>
	   <%@ include file="/WEB-INF/view/bss/ob/supplier/productIssueInfoCommon.jsp" %>
  </div>
 </div>
</body>
</html>