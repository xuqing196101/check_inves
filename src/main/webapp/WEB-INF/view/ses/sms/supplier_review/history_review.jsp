<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    
    <script type="text/javascript">
      //返回列表
      function renturnList(){
    	  $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/list.html");
        $("#submitform").submit();
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商管理</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商复核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
            <jsp:param value="eleven" name="currentStep"/>
            <jsp:param value="${supplierId}" name="supplierId"/>
            <jsp:param value="${supplierStatus}" name="supplierStatus"/>
            <jsp:param value="${sign}" name="sign"/>
           </jsp:include>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info w250">项目</th>
                  <!-- <th class="info w60">扫描件</th> -->
                  <th class="info w150">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
              <tbody id="tbody_items">
                <c:forEach items="${itemList}" var="item" varStatus="vs">
                  <tr class="h40">
                    <td class="tc">${vs.index+1}</td>
                    <td class="tc">${item.attachName}</td>
                    <%-- <td class="tc">
                      <c:if test="${!empty item.businessId && !empty item.typeId}">
                        <u:show showId="inves_${vs.index+1}" businessId="${item.businessId}" sysKey="${sysKey}" typeId="${item.typeId}" delete="false"/>
                      </c:if>
                    </td> --%>
                    <td class="tc">
                      <input type="hidden" value="" id="isAccord_${item.id}" />
                      <c:if test="${item.isAccord==1}">
                        <button class="btn" type="button">一致</button>
                        <button class="btn bgdd black_link" type="button">不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==2}">
                        <button class="btn bgdd black_link" type="button">一致</button>
                        <button class="btn bgred" type="button">不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==0}">
                        <button class="btn bgdd black_link" type="button">一致</button>
                        <button class="btn bgdd black_link" type="button">不一致</button>
                      </c:if>
                    </td>
                    <td><input type="text" class="w100p mb0" disabled value="${item.suggest}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </ul>
          
          <div class="clear"></div>
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div>${opinion}</div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
      <a class="btn" type="button" onclick="toStep('six');">上一步</a>
      <a class="btn" type="button" onclick="toStep('nine');">转至复核</a>
    </div>
    
    <input id="supplierId" value="${supplierId}" type="hidden">
    
    <form action="" id="submitform">
      <input value="" name="supplierId" type="hidden"/>
    </form>
  </body>
</html>