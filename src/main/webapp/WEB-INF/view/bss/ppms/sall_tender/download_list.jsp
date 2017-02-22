<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">
    	$(function() {
	  		//获取查看或操作权限
	       	var isOperate = $('#isOperate', window.parent.document).val();
	       	if(isOperate == 0) {
				$(":button").each(function(){ 
					$(this).hide();
	            }); 
			}
	    })
      function download() {
      	var id = [];
        $('input[name="chkItem_supplier"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
	       	var projectId = $("#projectId").val();
	       	window.location.href = "${pageContext.request.contextPath}/saleTender/downloads.html?projectId="+projectId+"&id="+id;
        } else if(id.length > 1) {
          layer.msg("只能选择一个供应商", {
            offset: '180px',
            shade: 0.01
          });
        } else {
          layer.msg("请选择一个供应商", {
            offset: '180px',
            shade: 0.01
          });
        }
      }

    </script>
  </head>

  <body>
    <input type="hidden" id="projectId" value="${projectId}" />
    <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
    	<button class="btn btn-windows input" onclick="download();" type="button">下载标书</button>
  	</div>
    <table class="table table-bordered table-condensed table-hover table-striped">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info">供应商名称</th>
            <th class="info">参与包</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${sds}" var="obj" varStatus="vs">
            <tr>
              <td class="tc opinter w50"><input  type="radio" name="chkItem_supplier" value="${obj.id}" /></td>
              <td class="tc opinter " title="${obj.suppliers.supplierName}">
	              <c:choose>
		             <c:when test="${fn:length(obj.suppliers.supplierName) > 12}">
		               ${fn:substring(obj.suppliers.supplierName, 0, 10)}......
		             </c:when>
		             <c:otherwise>
		               ${obj.suppliers.supplierName}
		             </c:otherwise>
	           	  </c:choose>
              </td>
              <td class="tc" title="${obj.packageNames}"> 
			  	  <c:choose>
		             <c:when test="${fn:length(obj.packageNames) > 20}">
		               ${fn:substring(obj.packageNames, 0, 20)}......
		             </c:when>
		             <c:otherwise>
		               ${obj.packageNames}
		             </c:otherwise>
	           	  </c:choose>
			  </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
  </body>
</html>