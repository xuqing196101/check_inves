<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
  </head>

  <body>
    <div class="container">
      <!-- 列表 -->
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th>序号</th>
              <th>生产或经营地址</th>
              <th>房产证明或租赁协议</th>
            </tr>
          </thead>
          <c:forEach items="${supplierAddress}" var="address" varStatus="vs">
            <tr>
              <td>${vs.index+1}</td>
              <td>${address.parentName}${address.subAddressName}${address.detailAddress}</td>
              <td>
                <u:show delete="false" showId="house_show_${vs.index+1}" businessId="${address.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}"/>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </body>

</html>