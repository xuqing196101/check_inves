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
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_attach/attach_audit.js"></script>
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
            <jsp:param value="ten" name="currentStep"/>
            <jsp:param value="${supplierId}" name="supplierId"/>
            <jsp:param value="${supplierStatus}" name="supplierStatus"/>
            <jsp:param value="3" name="sign"/>
           </jsp:include>

					<h2 class="count_flow"><i>1</i>上传实地考察记录表</h2>
					<ul class="ul_list">
						<div style="display: inline-block;">
							<u:upload id="inves1" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ supplierDictionaryData.supplierImageData }" buttonName="上传实地考察记录表" auto="true" exts="png,jpeg,jpg,bmp,git" multiple="true"/>
							<u:show showId="inves1" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ supplierDictionaryData.supplierImageData }"/>
						</div>
						<div style="display: inline-block;margin-left: 30px;">
							<u:upload id="inves2" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ supplierDictionaryData.supplierSurveyRecords }" buttonName="上传考察影像资料" auto="true" exts="png,jpeg,jpg,bmp,git" multiple="true"/>
							<u:show showId="inves2" businessId="${supplierId}" sysKey="${ sysKey }" typeId="${ supplierDictionaryData.supplierSurveyRecords }"/>
						</div>
					</ul>
					<div class="clear"></div>
          <h2 class="count_flow"><i>2</i>供应商实地考察</h2>
          <ul class="ul_list">
          	<!-- <span class="col-md-12 col-sm-12 col-xs-12">查验资质原件情况</span>
						<span class="col-md-12 col-sm-12 col-xs-12 red">说明：所有项目均为必须考察项，必须选择是否一致；不一致项目必须填写理由。</span> -->
						<li>查验资质原件情况</li>
						<li class="red">说明：所有项目均为必须考察项，必须选择是否一致；不一致项目必须填写理由。</li>
          	<li>
							<table class="table table-bordered table-condensed mt5 table_wrap table_input m_table_fixed_border">
	              <thead>
	                <tr class="h40">
	                  <th class="info w50">序号</th>
	                  <th class="info w400">项目</th>
	                  <th class="info w80">扫描件</th>
	                  <th class="info w100">原件与扫描件是否一致</th>
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
	                        <button class="btn" type="button" onclick="opr(this, '${item.id}', 1, 1)">一致</button>
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 2, 2)">不一致</button>
	                      </c:if>
	                      <c:if test="${item.isAccord==2}">
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 1, 2)">一致</button>
	                        <button class="btn bgred" type="button" onclick="opr(this, '${item.id}', 2)">不一致</button>
	                      </c:if>
	                      <c:if test="${item.isAccord==0}">
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 1, 2)">一致</button>
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 2, 2)">不一致</button>
	                      </c:if>
	                    </td>
	                    <td><input type="text" class="w100p mb0" id="${item.id}_suggest_${vs.index+1}" value="${item.suggest}" maxlength="300" onblur="saveAuditSuggest('${item.id}', 2 , ${vs.index+1})"/></td>
	                  </tr>
	                </c:forEach>
	              </tbody>
	            </table>
						</li>
						<li>申报产品类别产品提供能力情况</li>
						<li>
							<table class="table table-bordered table-condensed mt5 table_wrap table_input m_table_fixed_border">
	              <thead>
	                <tr class="h40">
	                  <th class="info w50">序号</th>
	                  <th class="info w400">产品类别</th>
	                  <th class="info w100">是否具备产品提供能力</th>
	                  <th class="info">理由</th>
	                </tr>
	              </thead>
	              <tbody id="tbody_cates">
	                <c:forEach items="${cateList}" var="item" varStatus="vs">
	                  <tr class="h40">
	                    <td class="tc">${item.sn}</td>
	                    <td class="tc">${item.categoryName}</td>
	                    <td class="tc">
	                      <input type="hidden" value="0" id="isSupplied_${item.id}" />
	                      <c:if test="${item.isSupplied==1}">
	                        <button class="btn" type="button" onclick="opr(this, '${item.id}', 1, 1)">是</button>
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 2, 2)">否</button>
	                      </c:if>
	                      <c:if test="${item.isSupplied==2}">
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 1, 2)">是</button>
	                        <button class="btn bgred" type="button" onclick="opr(this, '${item.id}', 2)">否</button>
	                      </c:if>
	                      <c:if test="${item.isSupplied==0}">
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 1, 2)">是</button>
	                        <button class="btn bgdd black_link" type="button" onclick="opr(this, '${item.id}', 2, 2)">否</button>
	                      </c:if>
	                    </td>
	                    <td><input type="text" class="w100p mb0" id="${item.id}_suggest" value="${item.suggest}" maxlength="300" onblur="tempSaveCateAudit('${item.id}')"/></td>
	                  </tr>
	                </c:forEach>
	              </tbody>
	            </table>
						</li>
						<li class="mt10"><span>主要生产场所情况</span></li>
						<li class="mt10">
							<input type="hidden" value="${other.id}" id="invesOtherId" />
							<textarea id="productionPlaceInfo" name="productionPlaceInfo" class="col-md-12 col-xs-12 col-sm-12 h80" onchange="tempSaveInvesOther(this)" maxlength="300">${other.productionPlaceInfo}</textarea>
						</li>
						<li class="mt10"><span class="mt10">查看主要设施设备情况</span></li>
						<li class="mt10">
							<textarea id="facilitiesInfo" name="facilitiesInfo" class="col-md-12 col-xs-12 col-sm-12 h80" onchange="tempSaveInvesOther(this)" maxlength="300">${other.facilitiesInfo}</textarea>
						</li>
          </ul>
          <div class="clear"></div>
          <h2 class="count_flow"><i>3</i>考察意见</h2>
          <ul class="ul_list">
            <li>
              <div class="select_check">
					      <input type="radio" value="1" name="selectOption" id="qualified" onclick="tempSaveAuditOpinion()">考察合格
					      <input type="radio" value="0" name="selectOption" id="unqualified" onclick="tempSaveAuditOpinion()">考察不合格
				      </div>
            </li>
            <li>
              <div id="cate_result"></div>
            </li>
						<li class="mt10">
	             <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" onblur="tempSaveAuditOpinion()" maxlength="300">${auditOpinion}</textarea>
	          </li>
          </ul>
          <div class="clear"></div>
          <h2 class="count_flow"><i>4</i>考察组成员</h2>
          <ul class="ul_list">
            <li>
            	<a class="btn padding-left-20 padding-right-20 btn_back" onclick="addSignature();">新增成员</a>
            	<form action="${pageContext.request.contextPath}/supplierInves/addSignature.html">
            		<input type="hidden" value="${supplierId}" name="supplierId" />
            		<table class="table table-bordered table-condensed mt5 table_wrap table_input m_table_fixed_border">
		              <thead>
		                <tr class="h40">
		                  <th class="info w50">序号</th>
		                  <th class="info w200">姓名</th>
		                  <th class="info">单位</th>
		                  <th class="info w300">职位（职务）</th>
		                  <th class="info w50">操作</th>
		                </tr>
		              </thead>
		              <tbody id="tbody_sign">
		              	<c:set var="signNumber" value="0" />
		                <c:forEach items="${signList}" var="item" varStatus="vs">
		                  <tr class="h40" id="tr_${vs.index}" data-id="${item.id}">
		                    <td class="tc">${vs.index+1}<input type="hidden" name="signs[${vs.index}].id" value="${item.id}" /></td>
		                    <td class="tc"><input type="text" class="w100p mb0" name="signs[${vs.index}].name" value="${item.name}" onchange="tempSaveSignature(this)" maxlength="10"/></td>
		                    <td class="tc"><input type="text" class="w100p mb0" name="signs[${vs.index}].company" value="${item.company}" onchange="tempSaveSignature(this)" maxlength="50"/></td>
		                    <td class="tc"><input type="text" class="w100p mb0" name="signs[${vs.index}].job" value="${item.job}" onchange="tempSaveSignature(this)" maxlength="50"/></td>
		                    <td class="tc"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="delSignature('${item.id}')"></td>
		                  </tr>
		                  <c:set var="signNumber" value="${signNumber+1}" />
		                </c:forEach>
		                <input type="hidden" id="signNumber" value="${signNumber}" />
		              </tbody>
		            </table>
            	</form>
            </li>
            <li>
            	<table class="table table-bordered table-condensed mt5 table_wrap table_input m_table_fixed_border">
            		<tr class="h40">
                  <td class="info tc w250">拍照录像人员</td>
                  <td class="tc">
                  	<input type="text" id="photographer" name="photographer" class="w100p mb0" value="${other.photographer}" onchange="tempSaveInvesOther(this)" maxlength="50"/>
                  </td>
                 </tr>
            	</table>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc" id="reviewEnd">
    	<a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="toStep('six');">上一步</a>
     	<!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporary(1);">暂存</a> -->
     	<a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="invesEnd();">考察结束</a>
    </div>
    
    <input type="hidden" value="${supplierId}" name="supplierId" id="supplierId" />
  </body>

</html>