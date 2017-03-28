<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价结果查询页面</title>
	
	<script type="text/javascript">
		function printResult(){
			window.location.href="${pageContext.request.contextPath}/ob_project/printResult.html?id="+'${titleId}';
		}
		
		/* 分页 */
		/* $(function() {
		    laypage({
		      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		      pages : "${info.pages}", //总页数
		      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		      skip : true, //是否开启跳页
		      total : "${info.total}",
		      startRow : "${info.startRow}",
		      endRow : "${info.endRow}",
		      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
		      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
		        return "${info.pageNum}";
		      }(),
		      jump : function(e, first) { //触发分页后的回调
	        	if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		location.href = "${pageContext.request.contextPath }/ob_project/findBiddingResult.html?id=${info.list[0].projectId}&&page=" + e.curr;
		        }
		      }
		    });
		  }); */
		
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
       <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价看板</a></li><li class="active"><a href="javascript:void(0)">竞价结果查询</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 添加供应商列表页面开始 -->
<div class="container">
	<div class="headline-v2">
     	<h2>竞价标题：${projectName }</h2>
	</div> 
	<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<span><font size="3">供应商确认中标比例为<c:if test="${turnoverRation == null }">0</c:if>%，未中标比例为${100 - turnoverRation }%.</font></span>
		<button class="btn btn-windows print" onclick="printResult()">打印结果</button>
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	</div>
	
	<div>
	  <h2 class="count_flow"><i>1</i>产品信息</h2>
	   <ul class="ul_list">
		<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="w50 info">序号</th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info">总价（元）</th>
			  <th class="info">备注信息</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="3">合计</td>
			  <td class="tc">
			  	<c:if test="${ totalCountPriceBigDecimal != '00' }">
			  		${ totalCountPriceBigDecimal }
			  	</c:if>
			  </td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
				<tr>
				  <td class="tc">${ vs.index + 1 }</td>
				  <td class="tc" id="t_${productInfo.id}" onmousemove="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')">${ productInfo.obProduct.name } </td>
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
	 <h2 class="count_flow"><i>2</i>报价信息</h2>
		<%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
      <!-- <div id="pagediv" align="right"></div> -->
   </div>
</body>
</html>