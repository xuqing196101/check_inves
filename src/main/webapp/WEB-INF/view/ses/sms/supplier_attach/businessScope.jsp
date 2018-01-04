<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <title>省级地域的合同主要页</title>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
  </head>

  <body>
    <div class="container">
      <div class="content table_box">
	      <c:forEach items="${areas}" var="area" varStatus="st">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15 h70">
		       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" onclick="auditFile(this,'mat_eng_page','${area.name}');">${area.name}：</span>
		       <u:show showId="area_show_${st.index+1}" delete="false" businessId="${supplierId}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
		     </li>
	     </c:forEach>
     </div>
    </div>
  </body>

</html>