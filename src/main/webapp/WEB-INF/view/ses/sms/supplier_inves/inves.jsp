<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>供应商实地考察</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_inves/inves.js"></script>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_attach/attach_view.js"></script>
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
          	<a href="javascript:void(0)">供应商实地考察</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
            <jsp:param value="nine" name="currentStep"/>
            <jsp:param value="${supplierId}" name="supplierId"/>
            <jsp:param value="${supplierStatus}" name="supplierStatus"/>
            <jsp:param value="3" name="sign"/>
           </jsp:include>

					<h2 class="count_flow"><i>1</i>上传实地考察记录表</h2>
					<ul class="ul_list hand">
					 	<u:upload id="inves" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传实地考察记录表" auto="true" exts="png,jpeg,jpg,bmp,git" multiple="true"/>
						<u:show showId="inves" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ typeId }"/>
						
						<u:upload id="inves" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传考察影像资料" auto="true" exts="png,jpeg,jpg,bmp,git" multiple="true"/>
						<u:show showId="inves" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ typeId }"/>
					</ul>
					<div class="clear"></div>
          <h2 class="count_flow"><i>2</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed mt5 table_wrap table_input m_table_fixed_border">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info">项目</th>
                  <th class="info">扫描件</th>
                  <th class="info">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
              <tbody id="tbody_items">
              	<c:forEach items="${itemList}" var="item" varStatus="vs">
              		<tr class="h40">
		                <td class="tc">${vs.index+1}</td>
		                <td class="tc">${item.attachName}</td>
		                <td class="tc">
		                	<c:if test="${empty item.businessId || empty item.typeId}">
		                		<a href="javascript:;" onclick="viewAttach('${item.viewUrl}','${item.attachName}')">查看</a>
		                	</c:if>
		                	<c:if test="${!empty item.businessId && !empty item.typeId}">
		                		<u:show showId="inves_${vs.index+1}" businessId="${item.businessId}" sysKey="${sysKey}" typeId="${item.typeId}" delete="false"/>
		                	</c:if>
		                </td>
		                <td class="tc">
		                	<input type="hidden" value="" id="isAccord_${item.id}" />
		                	<c:if test="${item.isAccord==1}">
		                		<button class="btn" type="button" onclick="opr('${item.id}')">一致</button>
		                		<button class="btn bgdd black_link" type="button" onclick="opr('${item.id}')">不一致</button>
		                	</c:if>
		                	<c:if test="${item.isAccord==2}">
		                		<button class="btn bgdd black_link" type="button" onclick="opr('${item.id}')">一致</button>
		                		<button class="btn bgred" type="button" onclick="opr('${item.id}')">不一致</button>
		                	</c:if>
		                	<c:if test="${item.isAccord==0}">
		                		<button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 1)">一致</button>
		                		<button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 2)">不一致</button>
		                	</c:if>
		                </td>
		                <td><input type="text" class="w300 border0" value="${item.suggest}" maxlength="300" /></td>
		              </tr>
              	</c:forEach>
              </tbody>
            </table>
          </ul>
        </div>
      </div>
    </div>
  </body>

</html>