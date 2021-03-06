<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="up" uri="/tld/upload" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <title>复查信息</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
      <%@ include file="/WEB-INF/view/ses/ems/expertQuery/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertQuery/merge_jump.js"></script>
    <script type="text/javascript">
      //上一步
      function lastStep() {
        var action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <%-- <jsp:include page="navigation.jsp" flush="ture" /> --%>
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
            <a href="javascript:void(0)">专家管理</a>
          </li>
          <li>
            <c:if test="${sign == 1}">
              <a href="javascript:jumppage('${pageContext.request.contextPath}/expert/findAllExpert.html')">全部专家查询</a>
            </c:if>
            <c:if test="${sign == 2}">
              <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">入库专家查询</a>
            </c:if>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <ul class="nav nav-tabs bgwhite">
            <li class="">
              <a aria-expanded="false" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
            </li>
            <li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
            </li>
            <li class="">
              <a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">参评类别</a>
            </li>
            <li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
            </li>
            <li class="">
              <a aria-expanded="ture" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('auditInfo');">采购机构初审意见</a>
            </li>
            <li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('review');">专家复审意见</a>
            </li>
            <li class="active">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('reviewCheck');">采购机构复查意见</a>
            </li>
          </ul>
          
          <div class="clear"></div>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
	          <table class="table table-bordered table-condensed table-hover">
	            <thead>
	              <tr>
	                <th class="info w50">序号</th>
	                <th class="info">审批类型</th>
	                <th class="info">审批字段</th>
	                <th class="info">审批内容</th>
	                <th class="info">不合格理由</th>
	              </tr>
	            </thead>
	            <c:forEach items="${auditList }" var="audit" varStatus="vs">
	              <input id="auditId" value="${audit.id}" type="hidden">
	              <tr>
	                <td class="">${vs.index + 1}</td>
	                <td class="">
	                  <c:if test="${audit.suggestType eq 'one'}">基本信息</c:if>
	                  <%-- <c:if test="${audit.suggestType eq 'two'}">经历经验</c:if> --%>
	                  <c:if test="${audit.suggestType eq 'seven'}">专家类别</c:if>
	                  <c:if test="${audit.suggestType eq 'six'}">产品类别</c:if>
	                  <c:if test="${audit.suggestType eq 'five'}">承诺书和申请表</c:if>
	                </td>
	                <td class="">${audit.auditField }</td>
	                <td class="hand" title="${audit.auditContent}">
	                  <c:if test="${fn:length (audit.auditContent) > 20}">${fn:substring(audit.auditContent,0,20)}...</c:if>
	                  <c:if test="${fn:length (audit.auditContent) <= 20}">${audit.auditContent}</c:if>
	                </td>
	                <td class="hand" title="${audit.auditReason}">
	                  <c:if test="${fn:length (audit.auditReason) > 20}">${fn:substring(audit.auditReason,0,20)}...</c:if>
	                  <c:if test="${fn:length (audit.auditReason) <= 20}">${audit.auditReason}</c:if>
	                </td>
	              </tr>
	            </c:forEach>
	          </table>
	        </ul>
	        
          <div class="clear"></div>
            <h2 class="count_flow"><i>2</i>意见信息</h2>
            <ul class="ul_list">
              <li>
                <div class="select_check" id="selectOptionId">
                    <input type="radio" disabled="disabled" value="7" <c:if test="${auditOpinion.flagAudit eq '7'}">checked</c:if>>复查合格
                    <input type="radio"  disabled="disabled" value="8" <c:if test="${auditOpinion.flagAudit eq '8'}">checked</c:if>>复查不合格
                </div>
              </li>
              <li>
                <div id="check_opinion"></div>
              </li>
              <div><span type="text" name="cate_result" id="cate_result"></span></div>
              <li class="mt10">
                <textarea id="opinion" disabled="disabled" class="col-md-12 col-xs-12 col-sm-12 h80">${ auditOpinion.opinion }</textarea>
              </li>
            </ul>
	         <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
            <%-- <c:if test="${ empty reqType }"> --%>
              <c:if test="${sign == 1}">
                <a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回列表</a>
              </c:if>
              <c:if test="${sign == 2}">
                <a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回列表</a>
              </c:if>
            <%-- </c:if>
            <c:if test="${not empty reqType }">
                <a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/readOnlyList.html?address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}">返回列表</a>
            </c:if> --%>
          </div>
        </div>
      </div>
    </div>
    <form id="form_id" action="" method="post">
      <input name="expertId" value="${expertId}" type="hidden">
      <input name="sign" value="${sign}" type="hidden">
      <input name="status" value="${status}" type="hidden">
    </form>
  </body>

</html>