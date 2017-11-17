<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>股东信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/essential.js"></script>
		<style type="text/css">
			td {
			  cursor:pointer;
			}
			.icon_edit,.icon_sc{
       	padding: 5px;
      }
		</style>
  </head>

  <body>
	  <!--面包屑导航开始-->
	  <div class="margin-top-10 breadcrumbs ">
          <div class="container">
              <ul class="breadcrumb margin-left-0">
                  <li>
                      <a> 首页</a>
                  </li>
                  <li>
                      <a  href="javascript:void(0)">支撑环境</a>
                  </li>
                  <li>
                      <a  href="javascript:void(0)">供应商管理</a>
                  </li>
                  <c:if test="${sign == 1}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
                      </li>
                  </c:if>
                  <c:if test="${sign == 2}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
                      </li>
                  </c:if>
                  <c:if test="${sign == 3}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
                      </li>
                  </c:if>
              </ul>
          </div>
      </div>
    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
          	<jsp:param value="three" name="currentStep"/>
           	<jsp:param value="${supplierId }" name="supplierId"/>
           	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
           	<jsp:param value="${sign }" name="sign"/>
          </jsp:include>
        <form id="form_id" action="" method="post" >
            <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
            <input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
            <input type="hidden" name="sign" value="${sign}">
        </form>
        <ul class="ul_list count_flow">
        	<h5>出资人（股东）信息 （说明：出资人（股东）多于10人的，可以列出出资金额前十位的信息，但所列的出资比例应高于50%）</h5>
          <table class="table table-bordered table-condensed table-hover m_table_fixed_border">
            <thead>
		          <tr>
		            <th class="info w50">序号</th>
		            <th class="info" width="10%">出资人性质</th>
		            <th class="info">出资人名称或姓名</th>
		            <th class="info">证件类型</th>
		            <th class="info">统一社会信用代码或身份证号码</th>
		            <th class="info">出资金额或股份(万元/份)</th>
		            <th class="info">比例(%)</th>
		            <th class="info w50">操作</th>
		          </tr>
            </thead>
	            <c:forEach items="${shareholder}" var="s" varStatus="vs">
	              <tr>
		              <td class="tc">${vs.index + 1}</td>
		              <td class="tc" id="nature_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_nature'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'shareholder_page','nature','${s.id}','4');"</c:if>>
		              	<c:if test="${s.nature eq '1'}">法人</c:if>
		              	<c:if test="${s.nature eq '2'}">自然人</c:if>
		              </td>
		              <td class="tl" id="name_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'shareholder_page','name','${s.id}','4');"</c:if> >${s.name}</td>
		              <td class="tl" id="nature_${s.id }" >
                    <c:if test="${s.nature==1}">统一社会信用代码</c:if>
                    <c:if test="${s.nature==2}">居民二代身份证</c:if>
                  </td>
		              <td class="tc" id="identity_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_identity'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'shareholder_page','identity','${s.id}','4');"</c:if>>${s.identity}</td>
		              <td class="tc" id="shares_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_shares'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'shareholder_page','shares','${s.id}','4');"</c:if>>${s.shares}</td>
		              <td class="tc" id="proportion_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_proportion'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'shareholder_page','proportion','${s.id}','4');"</c:if>>${s.proportion}</td>
		              <td class="tc w50">
		                <%-- <c:if test="${!fn:contains(unableField,s.id)}">
		                	<p onclick="reason('${s.id}','${s.name}');" id="${s.id}_hidden" class="editItem">
		                		<c:if test="${!fn:contains(auditField,s.id)}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                      </c:if>
	                      <c:if test="${fn:contains(auditField,s.id)}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                      </c:if>
		                	</p>
		              	</c:if>
		              	<c:if test="${fn:contains(unableField,s.id)}">
                      <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                    </c:if> --%>
                    <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                    <c:set var="iconCls" value="icon_edit" />
                    <c:if test="${!fn:contains(unableField,s.id) && fn:contains(auditField,s.id)}">
                    	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                    </c:if>
                    <c:if test="${fn:contains(unableField,s.id)}">
                      <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                      <c:set var="iconCls" value="icon_sc" />
                    </c:if>
                    <img src="${iconUrl}" class="${iconCls}"
                    onclick="auditList(this,'basic_page','${s.id}','股东信息','${s.name}');" />
		              </td>
	              </tr>
	            </c:forEach>
            </table>
          </ul>
        <div class="col-sm-12 col-xs-12 col-md-12 add_regist tc">
          
          <a class="btn"  type="button" onclick="toStep('two');">上一步</a>
          <c:if test="${isStatusToAudit}">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempAudit();">暂存</a>
          </c:if>
          <%--<a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>--%>
          <a class="btn"  type="button" onclick="toStep('four');">下一步</a>
	      </div>
        </div>
      </div>
    </div>
  </body>
</html>
