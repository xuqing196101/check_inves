<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<script type="text/javascript">
	$(function() {
		// 加载完成后关闭layer.load()加载层
		layer.close(loading);
		/* laypage({
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
					var expertId = "${expertId}";
					var typeId = "${typeId}";
					var path = "${pageContext.request.contextPath}/expert/getCategories.html?expertId=" + expertId + "&typeId=" + typeId + "&pageNum=" + pageNum;
					$("#tbody_category").load(path);
				}
			}
		}); */
	});
</script>
</head>
<body>
  <table class="table table-bordered table-hover">
    <tr>
      <td class="info tc w50">序号</td>
      <td class="info tc w100">类别</td>
      <td class="info tc">大类</td>
      <td class="info tc">中类</td>
      <td class="info tc">小类</td>
      <td class="info tc">采购机构初审意见</td>
      <td class="info tc">专家复审意见</td>
      <!-- <td class="info tc">品种名称</td> -->
    </tr>
    <c:forEach items="${itemsList}" var="item" varStatus="vs">
      <tr>
	      <td class="tc">${result.pageSize * (result.pageNum - 1) + vs.index + 1}</td>
		    <td class="tc">${item.rootNode}</td>
		    <td class="tl pl20">${item.firstNode}</td>
		    <td class="">${item.secondNode}</td>
		    <td class="">${item.thirdNode}</td>
		    <td class="w200 hand" title="${item.auditReason}">
		    	<c:if test="${fn:length(item.auditReason) >12}">${fn:substring(item.auditReason,0,12)}...</c:if>
                <c:if test="${fn:length(item.auditReason) <=12}">${item.auditReason}</c:if>
		    </td>
		    <td class="w200 hand" title="${item.reviewAudit}">
		    	<c:if test="${fn:length(item.reviewAudit) >12}">${fn:substring(item.reviewAudit,0,12)}...</c:if>
                <c:if test="${fn:length(item.reviewAudit) <=12}">${item.reviewAudit}</c:if>
            </td>
		   <%--  <td class="tl pl20">${item.fourthNode}</td> --%>
      </tr>
    </c:forEach>
  </table> 
</body>
</html>
