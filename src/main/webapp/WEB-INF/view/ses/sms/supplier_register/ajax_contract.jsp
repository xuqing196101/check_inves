<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">

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
  <div class="col-xs-12 col-sm-12 col-md-12 p0 over_scroll">
    <table class="table table-bordered space_nowrap">
      <tr>
        <td class="info tc"> 品目名称</td>
        <td colspan="3" class="info tc">合同上传</td>
        <td colspan="3" class="info tc">收款进账单</td>
      </tr>
      <tr>
        <td class="info tc"> 末级节点</td>
        <c:forEach items="${years}" var="year">
          <td class="info tc w300">${year}</td>
        </c:forEach>
        <c:forEach items="${years}" var="year">
          <td class="info  w300 tc">${year}</td>
        </c:forEach>
      </tr>
      <c:forEach items="${contract}" var="obj" varStatus="vs">
      <tr>
        <td>${obj.name }</td>
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
</div>
</body>
</html>
