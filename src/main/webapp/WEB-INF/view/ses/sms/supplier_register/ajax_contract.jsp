<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<link href="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/skin/laypage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function() {
	laypage({
		cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${contract.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${contract.total}",
		startRow: "${contract.startRow + 1}",
		endRow: "${contract.endRow + 1}",
		groups: "${contract.pages}" >= 3 ? 3 : "${contract.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${contract.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				$("#pageNum").val(e.curr);
				var pageNum = $("#pageNum").val();
				var supplierId = $("#supplierId").val();
				var type = "${supplierTypeId}";
				var path = "${pageContext.request.contextPath}/supplier/ajaxContract.html?supplierId=" + supplierId + "&supplierTypeId=" + type + "&pageNumber=" + pageNum;
				if (type == "PRODUCT") {
					$("tab-1").load(path);
				} else if (type == "SALES") {
					$("tab-2").load(path);
				} else if (type == "PROJECT") {
					$("tab-3").load(path);
				} else if (type == "SERVICE") {
					$("tab-4").load(path);
				}
			}
		}
	});
});
</script>
</head>
<body>
	<c:if test="${supplierTypeId eq 'PRODUCT'}">
	  <c:set var="fileUp" value="pUp"/>
	  <c:set var="fileShow" value="pShow"/>
	</c:if>
	<c:if test="${supplierTypeId eq 'SALES'}">
	  <c:set var="fileUp" value="saleUp"/>
	  <c:set var="fileShow" value="saleShow"/>
	</c:if>
	<c:if test="${supplierTypeId eq 'PROJECT'}">
	  <c:set var="fileUp" value="projectUp"/>
	  <c:set var="fileShow" value="projectShow"/>
	</c:if>
	<c:if test="${supplierTypeId eq 'SERVICE'}">
	  <c:set var="fileUp" value="serUp"/>
	  <c:set var="fileShow" value="serShow"/>
	</c:if>
  <div class="col-xs-12 col-sm-12 col-md-12 p0 over_scroll mb20">
    <form id="formSearch" action="${pageContext.request.contextPath}/supplier/ajaxContract.html">
      <input type="hidden" name="pageNum" id="pageNum">
      <input type="hidden" name="supplierId" id="supplierId" value="${supplierId}">
      <input type="hidden" name="supplierTypeId" id="supplierTypeId" value="${supplierTypeId}">
      <table class="table table-bordered">
        <tr>
          <td class="info tc w258"> 品目名称</td>
          <td colspan="3" class="info tc">合同上传</td>
          <td colspan="3" class="info tc">收款进账单</td>
        </tr>
        <tr>
          <td class="info tc"> 末级节点</td>
          <c:forEach items="${years}" var="year">
            <td class="info tc">${year}</td>
          </c:forEach>
          <c:forEach items="${years}" var="year">
            <td class="info tc">${year}</td>
          </c:forEach>
        </tr>
      <c:forEach items="${contract.list}" var="obj" varStatus="vs">
        <tr>
          <td class="tl pl20">${obj.name }</td>
          <td>
	        <u:upload id="${fileUp}${(vs.index + 1)*6-1}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneContract}" auto="true" />
	        <u:show showId="${fileShow}${(vs.index + 1)*6-1}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
		  </td>
		  <td>
		    <u:upload id="${fileUp}${(vs.index + 1)*6-2}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoContract}" auto="true" />
		    <u:show showId="${fileShow}${(vs.index + 1)*6-2}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
	      </td>
		  <td>
			<u:upload id="${fileUp}${(vs.index + 1)*6-3}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeContract}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-3}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
		  </td>
		  <td> 
			<u:upload id="${fileUp}${(vs.index + 1)*6-4}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-4}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
		  </td>
		  <td>
			<u:upload id="${fileUp}${(vs.index + 1)*6-5}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-5}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
		  </td>
		  <td>
			<u:upload id="${fileUp}${(vs.index + 1)*6-6}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-6}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
		  </td>
	    </tr>
      </c:forEach>
    </table> 
  </form>
</div>
<div id="pagediv" align="right"></div>
</body>
</html>
