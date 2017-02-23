<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
$(function() {
	layer.close(index);
	laypage({
		cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${result.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${result.total}",
		startRow: "${result.startRow}",
		endRow: "${result.endRow}",
		groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			return "${result.pageNum}";
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				$("#pageNum").val(e.curr);
				var pageNum = $("#pageNum").val();
				var supplierId = $("#supplierId").val();
				var type = "${supplierTypeId}";
				var path = "${pageContext.request.contextPath}/supplier/ajaxContract.html?supplierId=" + supplierId + "&supplierTypeId=" + type + "&pageNum=" + pageNum;
				if (type == "PRODUCT") {
					$("#tab-1").load(path);
				} else if (type == "SALES") {
					$("#tab-2").load(path);
				} else if (type == "PROJECT") {
					$("#tab-3").load(path);
				} else if (type == "SERVICE") {
					$("#tab-4").load(path);
				}
			}
		}
	});
});

		//显示不通过的理由
			function errorMsg(auditField, auditType){
				var supplierId = "${supplierId}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/audit.html",
					data: {"supplierId": supplierId, "auditField": auditField, "auditType": auditType},
					dataType: "json",
					success: function(data){
					layer.msg("不通过理由：" + data.suggest , {offset: '200px'});
					}
				});
			}
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
          <td class="info tc w258" rowspan="2"> 产品名称或小类</td>
          <td colspan="3" class="info tc"><c:if test="${supplierTypeId eq 'PROJECT'}">承包合同</c:if><c:if test="${supplierTypeId ne 'PROJECT'}">销售合同</c:if>(体现甲乙双方盖章及标的相关页，最多上传5张)</td>
          <td colspan="3" class="info tc">证明合同有效履行的相应银行收款进账单(最多上传5张)</td>
        </tr>
        <tr>
          <c:forEach items="${years}" var="year">
            <td class="info tc">${year}</td>
          </c:forEach>
          <c:forEach items="${years}" var="year">
            <td class="info tc">${year}</td>
          </c:forEach>
        </tr>
      <c:forEach items="${contract}" var="obj" varStatus="vs">
        <tr <c:if test="${fn:contains(audit,obj.id)}"> onmouseover="errorMsg('${obj.id}','contract_page')"</c:if>>
          <td class="tl pl20" <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>${obj.name }</td>
          <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>
          
	        <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-1}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneContract}" auto="true" />
	        <u:show showId="${fileShow}${(vs.index + 1)*6-1}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
		  </td>
		  <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>
		    <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-2}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoContract}" auto="true" />
		    <u:show showId="${fileShow}${(vs.index + 1)*6-2}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
	      </td>
		  <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>
			<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-3}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeContract}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-3}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
		  </td>
		  <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>> 
			<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-4}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-4}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
		  </td>
		  <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>
			<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-5}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-5}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
		  </td>
		  <td <c:if test="${fn:contains(audit,obj.id)}">style="border: 1px solid red;" </c:if>>
			<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" exts="${properties['file.picture.type']}" id="${fileUp}${(vs.index + 1)*6-6}" multiple="true" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeBil}" auto="true" />
			<u:show showId="${fileShow}${(vs.index + 1)*6-6}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
		  </td>
	    </tr>
      </c:forEach>
    </table> 
  </form>
</div>
<div id="pagediv" align="right" class="mb50"></div>
</body>
</html>
