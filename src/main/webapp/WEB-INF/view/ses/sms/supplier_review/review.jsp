<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_attach/attach_audit.js"></script>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_review/review.js"></script>
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
            <jsp:param value="${reviewStatus}" name="reviewStatus"/>
           </jsp:include>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <p class="red">说明：项目中红色字体为必须复核项，必须选择是否一致；不一致的项目必须填写理由。</p>
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="tc w40">序号</th>
                  <th>项目</th>
                  <th class="tc">扫描件</th>
                  <th class="w150">原件与扫描件是否一致</th>
                  <th>理由</th>
                </tr>
              </thead>
              <tbody id="tbody_items">
                <c:forEach items="${itemList}" var="item" varStatus="vs">
                  <tr class="h40">
                    <td class="tc">${vs.index+1}</td>
                    <td class="w250 <c:if test="${item.attachCode eq 'SUPPLIER_BUSINESS_CERT'}">red</c:if>
                    <c:if test="${item.attachCode eq 'SUPPLIER_BEARCHCERT'}">red</c:if>
                    <c:if test="${item.attachCode eq 'SUPPLIER_FINANCE'}">red</c:if>
                    <c:if test="${item.attachCode eq 'SUPPLIER_ISO9001'}">red</c:if>
                    <c:if test="${item.attachCode eq 'SUPPLIER_CON_ACH'}">red</c:if>
                    <c:if test="${item.attachCode eq 'SUPPLIER_CERT_ENG'}">red</c:if>">
                    ${item.attachName}</td>
                    
                    <td class="tc w70">
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
                        <button class="btn" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgdd black_link" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2, 1)"</c:if>>不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==2}">
                        <button class="btn bgdd black_link" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgred" type="button" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2, 1)"</c:if>>不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==0}">
                        <button class="btn bgdd black_link" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgdd black_link" name="isAccord" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2, 1)"</c:if>>不一致</button>
                      </c:if>
                    </td>
                    <td><input type="text" class="w100p mb0" id="${item.id}_suggest_${vs.index+1}" value="${item.suggest}" maxlength="300" onblur="saveAuditSuggest('${item.id}', 1 , ${vs.index+1})"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </ul>
          <div class="clear"></div>
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div class="select_check">
					      <input type="radio" value="1" name="selectOption" id="qualified" onclick="temporary(2)">复核合格
					      <input type="radio" value="0" name="selectOption" id="unqualified" onclick="temporary(2)">复核不合格
				      </div>
            </li>
            <li>
              <div id="cate_result"></div>
            </li>
						<li class="mt10">
	             <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" onblur="temporary(2)">${auditOpinion}</textarea>
	          </li>
          </ul>
          
          <div class="clear"></div>
          <h2 class="count_flow"><i>3</i>下载供应商复核表</h2>
          <ul class="ul_list hand">
            <a class="btn btn-windows input" id="downloadTable" <c:if test="${status == 1}">onclick='downloadTable()'</c:if> href="javascript:void(0)">下载复核表</a>
          </ul>
          
          <div class="clear"></div>
          <div id="checkList" class="hidden">
            <h2 class="count_flow"><i>4</i>上传供应商复核表</h2>
	          <ul class="ul_list hand">
	            <li class="col-md-6 col-sm-6 col-xs-6">
                <div>
	                <span class="fl"><span class="red">*</span>上传复核表：</span>
	                <u:upload id="pic_review" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReview}" buttonName="上传彩色扫描件" auto="true" multiple="true"/>
	                <u:show showId="pic_review" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReview}"/>
                </div>
              </li>
	          </ul>
          </div>
        </div>
      </div>
    </div>
    
    <c:if test="${status == 1}">
      <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc" id="reviewEnd">
        <c:if test="${reviewStatus != 1}">
	        <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="toStep('six');">上一步</a>
	      </c:if>
	      <c:if test="${reviewStatus == 1}">
	        <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="toStep('eleven');">上一步</a>
	      </c:if>
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporary(1);">暂存</a>
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="reviewEnd();">复核结束</a>
      </div>
    </c:if>
    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc hidden" id="review">
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="restartReview();">重新复核</a>
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="renturnList();">返回</a>
    </div>
    
    <input id="supplierId" value="${supplierId}" type="hidden">
    <input id="flagAduit" value="${flagAduit}" type="hidden">
    <input id="status" value="${status}" type="hidden">
    <input id="noPass" value="${noPass}" type="hidden">
    
    <form action="" id="submitform">
      <input value="" name="supplierId" type="hidden"/>
    </form>
  </body>
</html>