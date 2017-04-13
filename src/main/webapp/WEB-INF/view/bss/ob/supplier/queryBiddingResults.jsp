<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file ="/WEB-INF/view/common/webupload.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
	<script type="text/javascript">
		function showPrompt(id,selectID){
	 		  if(id){
	 		  $.ajax({
					async: false,
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
		   <li><a href="javascript:void(0)">竞价结果查询</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
   <div>
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<%@ include file="/WEB-INF/view/bss/ob/supplier/biddingInfoCommon.jsp" %>
  </div>
  <div>
	 <h2 class="count_flow"><i>2</i>产品信息</h2>
	   <%@ include file="/WEB-INF/view/bss/ob/supplier/productIssueInfoCommon.jsp" %>
  </div>
  
	 <h2 class="count_flow"><i>3</i>供应商信息</h2>
	   <%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
  </div>
 </div>
</body>
</html>