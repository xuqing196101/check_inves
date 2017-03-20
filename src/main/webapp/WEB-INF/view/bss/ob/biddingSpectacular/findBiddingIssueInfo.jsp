<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<%@ include file="/WEB-INF/view/common.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<title>竞价信息查看页面</title>
	
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
   <div class="container container_box">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div> 
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="tc" class="info"><b>竞价标题</b></td>
				    <td class="tc">${ obProject.name }</td>
				    <td class="tc"><b>交货截止时间</b></td>
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
				    <td class="tc"><b>合格供应商数</b></td>
				    <td class="tc">
					    <c:if test="${!empty obProject.qualifiedSupplier }">
					    	${ obProject.qualifiedSupplier }
					    </c:if>
					    <c:if test="${empty obProject.qualifiedSupplier }">
					    	0
					    </c:if>
				    </td>
				  </tr>
				  <tr>
				    <td class="tc" class="info"><b>需求单位</b></td>
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
				     		${ file.name }&nbsp;&nbsp;<button class="btn" onclick="download('${file.id}','2','','')"> 查看 </button>
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
	    <h2 class="count_flow"><i>2</i>产品信息</h2>
		<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="w30 info"><input alt="" type="checkbox"></th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info">总价（元）</th>
			  <th class="info">备注信息</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"><input type="checkbox" alt=""></td>
			  <td class="tc" colspan="3">合计</td>
			  <td class="tc">${ totalCountPriceBigDecimal }</td>
			</tr>
			<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
				<tr>
				  <td class="tc"><input type="checkbox" alt=""></td>
				  <td class="tc">${ productInfo.obProduct.name }</td>
				  <td class="tc">${ productInfo.limitedPrice }</td>
				  <td class="tc">${ productInfo.purchaseCount }</td>
				  <td class="tc">${ productInfo.totalMoney }</td>
				  <td class="tc">${ productInfo.obProduct.remark }</td>
				</tr>
			</c:forEach>
		</table>
	  </div>
	  </div>	 
  </form>
 </div>
</body>
</html>