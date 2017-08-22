<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="up" uri="/tld/upload" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>审核汇总</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/merge_jump.js"></script>
    <script type="text/javascript">
      $(function(){
    	  $("#reverse_of_seven").attr("class","active");
        $("#reverse_of_seven").removeAttr("onclick");
    	  
      })
     
      //下一步
      function nextStep() {
        var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }

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
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">支撑系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">专家管理</a>
          </li>
          <c:if test="${sign == 1}">
            <li>
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
            </li>
          </c:if>
          <c:if test="${sign == 2}">
            <li>
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=2')">专家复审</a>
            </li>
          </c:if>
          <c:if test="${sign == 3}">
            <li>
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
            </li>
          </c:if>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <div class="content">
        <div class="col-md-12 tab-v2 job-content">
          <%@include file="/WEB-INF/view/ses/ems/expertAudit/common_jump.jsp" %>

          <h2 class="count_flow"><i>1</i>审核汇总信息</h2>
          <ul class="ul_list count_flow">
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
              <c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
                <input id="auditId" value="${reasons.id}" type="hidden">
                <tr>
                  <td class="">${vs.index + 1}</td>
                  <td class="">
                    <c:if test="${reasons.suggestType eq 'one'}">基本信息</c:if>
                    <%-- <c:if test="${reasons.suggestType eq 'two'}">经历经验</c:if> --%>
                    <c:if test="${reasons.suggestType eq 'seven'}">专家类别</c:if>
                    <c:if test="${reasons.suggestType eq 'six'}">产品类别</c:if>
                    <c:if test="${reasons.suggestType eq 'five'}">承诺书和申请表</c:if>
                  </td>
                  <td class="">${reasons.auditField }</td>
                  <td class="hand" title="${reasons.auditContent}">
                    <c:if test="${fn:length (reasons.auditContent) > 20}">${fn:substring(reasons.auditContent,0,20)}...</c:if>
                    <c:if test="${fn:length (reasons.auditContent) <= 20}">${reasons.auditContent}</c:if>
                  </td>
                  <td class="hand" title="${reasons.auditReason}">
                    <c:if test="${fn:length (reasons.auditReason) > 20}">${fn:substring(reasons.auditReason,0,20)}...</c:if>
                    <c:if test="${fn:length (reasons.auditReason) <= 20}">${reasons.auditReason}</c:if>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </ul>
          
          <h2 class="count_flow mt0"><i>2</i>最终意见</h2>
          <ul class="ul_list">
             <li>
               <div class="select_check">
                  <input type="radio"  disabled <c:if test="${auditOpinion.flagAudit eq '15'}">checked</c:if>>初审合格
                  <input type="radio"  disabled <c:if test="${auditOpinion.flagAudit eq '16'}">checked</c:if>>初审不合格
                </div>
              </li>
            <li class="mt10">
               <textarea id="opinion" readonly="readonly" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion.opinion }</textarea>
            </li>
          </ul>
          
          <div class="col-md-12 col-sm-12 col-xs-12  add_regist tc">
	          <a class="btn" type="button" onclick="lastStep();">上一步</a>
	          <c:if test="${status == -2 || status == 0 || status == 9 || (sign ==3 && status ==6) || status ==4}">
	            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
	          </c:if>
	          <a class="btn" type="button" onclick="nextStep();">下一步</a>
	        </div>
        </div>
      </div>
    </div>
    <input value="${expertId}" id="expertId" type="hidden" />
    <form id="form_id" action="" method="post">
      <input name="expertId" value="${expertId}" type="hidden">
      <input name="sign" value="${sign}" type="hidden">
      <input name="status" id="status" value="${status}" type="hidden">
    </form>

  </body>

</html>