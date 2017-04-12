<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<script type="text/javascript">
	$(function() {
		// 加载完成后关闭layer.load()加载层
		layer.close(loading);
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
					loading = layer.load(1);
					var pageNum = e.curr;
					var supplierId = "${supplierId}";
					var type = "${supplierTypeRelateId}";
					var path = "${pageContext.request.contextPath}/supplier_item/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=" + type + "&pageNum=" + pageNum;
					$("#tbody_category").load(path);
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
  <h2 class="f16">已选产品类别</h2>
  <table class="table table-bordered table-hover">
    <tr>
      <td class="info tc w50">序号</td>
      <td class="info tc w100">类别</td>
      <td class="info tc">大类</td>
      <td class="info tc">中类</td>
      <td class="info tc">小类</td>
      <td class="info tc">品种名称</td>
    </tr>
    <c:forEach items="${itemsList}" var="item" varStatus="vs">
      <tr <c:if test="${fn:contains(audit,item.itemsId)}"> onmouseover="errorMsg('${item.itemsId}','items_page')"</c:if>>
        <td class="tc" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${result.pageSize * (result.pageNum - 1) + vs.index + 1}</td>
		    <td class="tc" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${item.rootNode}</td>
		    <td class="tl pl20" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${item.firstNode}</td>
		    <td class="tl pl20" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${item.secondNode}</td>
		    <td class="tl pl20" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${item.thirdNode}</td>
		    <td class="tl pl20" <c:if test="${fn:contains(audit,item.itemsId)}">style="border: 1px solid red;" </c:if>>${item.fourthNode}</td>
      </tr>
    </c:forEach>
  </table> 
</body>
</html>
