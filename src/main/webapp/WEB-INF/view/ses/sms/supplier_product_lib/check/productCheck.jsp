<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>产品信息 </title>
	<script type="text/javascript">
		// 提交表单
		function checkSubmit(flag){
			// 判断用户点击(审核通过/审核不通过)
			layer.confirm("是否执行此操作？", {
			    btn: ['确定','取消'], //按钮
			}, function(index){
				layer.close(index);
				$("#flag").val(flag);
				// 表单提交
				$.post("${pageContext.request.contextPath}/product_lib/checkProductInfo.do?", $("#smsProductCheckForm").serialize(), function(data) {
					if (data.status == 200) {
						layer.confirm("操作成功",{
							btn:['确定']
						},function(){
								// 成功后加载商品信息 
								window.location.href="${pageContext.request.contextPath}/product_lib/findAllWaitCheck.html";
							}
						) 
					}
					if(data.status == 500){
						layer.alert(data.msg);
					}
				});
			});
		}
	</script>
</head>
<body>
	
	<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/product_lib/findAllWaitCheck.html">产品库管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">产品审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 新增模板开始-->
    <div class="container container_box">
    	<div>
    		<%@ include file="/WEB-INF/view/ses/sms/supplier_product_lib/productBasicCommon.jsp" %>
  		</div>
  		<div>
  		<form action="" id="smsProductCheckForm" method="post">
  			<c:if test="${ not empty smsProductInfo.smsProductArguments }">
    			<h2 class="count_flow"><i>4</i>审核意见</h2>
    		</c:if>
  			<c:if test="${ empty smsProductInfo.smsProductArguments }">
    			<h2 class="count_flow"><i>3</i>审核意见</h2>
    		</c:if>
    		<input name="productBasicIds" value="${ smsProductBasic.id }" type="hidden">
    		<input id="flag" name="flag" type="hidden" value="">
    		<ul class="ul_list">
				<div class="content table_box">
					 <li class="col-md-12 col-sm-12 col-xs-12">
			              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red"></div></span>
			              <div class="col-md-12 col-sm-12 col-xs-12 p0">
			                <textarea class="h80 col-md-12 col-sm-12 col-xs-12 " name="advice" title="审核意见" placeholder="" ></textarea>
			              </div>
			              <div class="clear red"></div>
			         </li>
			    </div>
		    </ul>
		 </form>
		 <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
			 <button class="btn btn-windows check" type="button" onclick="checkSubmit(3)">审核通过</button>
	         <button class="btn btn-windows check" type="button" onclick="checkSubmit(2)">审核不通过</button>
	         <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	     </div>
  		</div>
    </div>
	
</body>
</html>