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
            <jsp:param value="nine" name="currentStep"/>
            <jsp:param value="${supplierId}" name="supplierId"/>
            <jsp:param value="${supplierStatus}" name="supplierStatus"/>
            <jsp:param value="${sign }" name="sign"/>
           </jsp:include>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info">项目</th>
                  <th class="info">扫描件</th>
                  <th class="info">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
            </table>
          </ul>
          
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div>${opinion}</div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc hidden">
      <a class="btn" type="button" onclick="toStep('six');">上一步</a>
      <a class="btn" type="button" onclick="toStep('nine');">下一步</a>
      <a class="btn" type="button" onclick="toStep('nine');">转至复核</a>
    </div>
    
    <input id="supplierId" value="${supplierId}" type="hidden">
    
    <form action="" id="submitform">
      <input value="" name="supplierId" type="hidden"/>
    </form>
  </body>
</html>