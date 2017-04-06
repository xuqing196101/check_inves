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
				url: "${pageContext.request.contextPath }/product/productType.do",
				type: "POST",
				data: {productId:id},
				success: function(data) {
				if(data){
       	  layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+data.qualityTechnicalStandard, 
       	    '#'+selectID, {tips: [2, '#78BA32'],time:-1});
				}else{
				 inder=layer.tips("", 
       	    '#'+selectID, {tips: [2, '#78BA32']});
				}
		      },error:function(){
		       layer.tips("错误！", 
       	    '#'+selectID, {tips: [2, '#78BA32']});
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
    <ul class="ul_list">
		<%@ include file="/WEB-INF/view/bss/ob/biddingSpectacular/biddingInfoCommon.jsp" %>
     </ul>
  </div> 
  <div class="clear" ></div>
  <form id="productForm" name="" method="post" >
  	<input type="hidden" name="titleId" value="${ obProject.id }">
	  <div>
	    <h2 class="count_flow"><i>2</i>产品信息</h2>
	    <ul class="ul_list">
			<div class="content table_box">
		    	<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
				  <th class="w30 info"><input alt="" type="checkbox"></th>
				  <th class="info">序号</th>
				  <th class="info" width="25%">定型产品名称</th>
				  <th class="info">限价（元）</th>
				  <th class="info">采购数量</th>
				  <th class="info">总价（元）</th>
				  <th class="info" width="30%">备注信息</th>
				</tr>
				</thead>
				<tr>
				  <td class="tc" colspan="2">合计</td>
				  <td class="tc" colspan="5">${ totalCountPriceBigDecimal }</td>
				</tr>
				<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
					<tr>
					  <td class="tc"><input type="checkbox" alt=""></td>
					  <td class="tc">${ vs.index+1 }</td>
					  <td class="tc" id="t_${productInfo.id}" onmousemove="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')">${ productInfo.obProduct.name }</td >
					  <td class="tc">${ productInfo.limitedPrice }</td>
					  <td class="tc">${ productInfo.purchaseCount }</td>
					  <td class="tc">${ productInfo.totalMoneyStr }</td>
					  <td class="tc">${ productInfo.remark }</td>
					</tr>
				</c:forEach>
			</table>
		  </div>
		 </ul>
	  </div>	 
  </form>
 </div>
</body>
</html>