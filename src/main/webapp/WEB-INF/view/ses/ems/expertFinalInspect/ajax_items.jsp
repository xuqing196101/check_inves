<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<script type="text/javascript">
	$(function() {
		//默认不显示叉
		$("td").each(function() {
			$(this).find("p").hide();
		});

 		layer.close(loading);
 	
	});
</script>
</head>
<body>
  <table class="table table-bordered table-hover m_table_fixed_border">
    <tr>
      <td class="info tc w50">序号</td>
      <td class="info tc w100">类别</td>
      <td class="info tc">大类</td>
      <td class="info tc">中类</td>
      <td class="info tc">小类</td>
    </tr>
    <c:forEach items="${itemsList}" var="item" varStatus="vs">
      <tr>
	      <td class="tc">${result.pageSize * (result.pageNum - 1) + vs.index + 1}</td>
		    <td class="tc" name="itemtd${item.itemsId}"  >${item.rootNode}</td>
		    <td class="tl pl20" name="itemtd${item.itemsId}"  >${item.firstNode}</td>
		    <td class="tl pl20" name="itemtd${item.itemsId}"  >${item.secondNode}</td>
		    <td class="tl pl20" name="itemtd${item.itemsId}"  >${item.thirdNode}</td>
		    <input type="hidden" name="del${item.itemsId}" value=""/>
      </tr>
    </c:forEach>
  </table> 
</body>
</html>
