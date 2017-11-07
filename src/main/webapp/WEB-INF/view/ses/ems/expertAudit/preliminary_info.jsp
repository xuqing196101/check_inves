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
    	  
        check_opinion();
      })
     
      //下一步
      function nextStep() {
    	  var status = $("input[name='status']").val();
        var sign = $("input[name='sign']").val();
    	  if(sign == 2){
    		  var action = "${pageContext.request.contextPath}/expertAudit/auditSummary.html";
    	  }else if(sign == 1 && (status == 10 || status == 5)){
    		  var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
    	  }
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }

      //上一步
      function lastStep() {
    	  var status = $("input[name='status']").val();
        var sign = $("input[name='sign']").val();
    	  if(sign == 1 && (status == 5 || status == 10)){
    		  var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
    	  }else{
    		  var action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
    	  }
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }
      
      
    //查询合格通过的产品类别
      function check_opinion() {
        var status = $(":radio:checked").val();
        var expertId = "${expertId}";
        var opinion = '${auditOpinion.opinion}';
        var itemType;
        var item
        if(status == 15){
        	itemType = "初审合格，通过的是物资服务经济类别。";
        	item = "初审合格，选择了";
        }
        
        if(status == -3){
        	itemType = "复审合格，通过的是物资服务经济类别。";
          item = "复审合格，选择了";
        }
        
        //复审退回
        if(status == '10'){
            $("#check_opinion").html("退回修改。" + opinion);
        }
        
        if(status == '5'){
            $("#check_opinion").html("复审不合格。" + opinion);
        }
        if(status != null && typeof(status) != "undefined" && (status == 15 || status == -3)) {
          $.ajax({
            url: "${pageContext.request.contextPath}/expertAudit/findCategoryCount.do",
            data: {
              "expertId" : expertId,
              "auditFalg" : 1
            },
            type: "post",
            dataType: "json",
            success: function(data) {
            	//初审
                if(data.all == 0 && data.pass == 0){
                  $("#check_opinion").html(itemType);
                }else{
                  $("#check_opinion").html(item + data.all + "个参评类别，通过了" + data.pass + "个参评类别。" + opinion);
                }
             }
          });
        }
      }
    
    
      //复审退回或复审不合格的，初审机构确认
      function preliminaryConfirmation(){
        var action = "${pageContext.request.contextPath}/expertAudit/preliminaryConfirmation.html";
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
              <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAgainAudit/findBatchDetailsList.html?batchId=${batchId}')">专家复审</a>
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
          <c:if test="${sign eq '1'}">
            <h2 class="count_flow"><i>1</i>复审汇总信息</h2>
          </c:if>
          <c:if test="${sign ne '1'}">
            <h2 class="count_flow"><i>1</i>审核汇总信息</h2>
          </c:if>
          <ul class="ul_list count_flow">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info">审批类型</th>
                  <th class="info">审批字段</th>
                  <th class="info">审批内容</th>
                  <th class="info">不合格理由</th>
                  <th class="info w100">状态</th>
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
                  <td class="hand" title="${reasons.catalogCode == null ? reasons.auditContent : reasons.catalogCode}">
                    <c:if test="${fn:length (reasons.auditContent) > 20}">${fn:substring(reasons.auditContent,0,20)}...</c:if>
                    <c:if test="${fn:length (reasons.auditContent) <= 20}">${reasons.auditContent}</c:if>
                  </td>
                  <td class="hand" title="${reasons.auditReason}">
                    <c:if test="${fn:length (reasons.auditReason) > 20}">${fn:substring(reasons.auditReason,0,20)}...</c:if>
                    <c:if test="${fn:length (reasons.auditReason) <= 20}">${reasons.auditReason}</c:if>
                  </td>
                  <!-- 状态 -->
                   <c:if test="${reasons.auditStatus eq '1'}"><td class="tc">有问题</td></c:if>
                   <c:if test="${reasons.suggestType eq 'six' && reasons.auditStatus eq '2'}"><td class="tc">审核不通过</td></c:if>
                   <c:if test="${reasons.suggestType != 'six' && reasons.auditStatus eq '2'}"><td class="tc">已修改</td></c:if>
                   <c:if test="${reasons.auditStatus eq '3'}"><td class="tc">未修改</td></c:if>
                   <c:if test="${reasons.auditStatus eq '4'}"><td class="tc">撤销退回</td></c:if>
                   <c:if test="${reasons.auditStatus eq '5'}"><td class="tc">撤销不通过</td></c:if>
                   <c:if test="${reasons.auditStatus eq '6'}"><td class="tc">审核不通过</td></c:if>
                   <c:if test="${reasons.auditStatus eq null}"><td class="tc"></td></c:if>
                </tr>
              </c:forEach>
            </table>
          </ul>
          
          <h2 class="count_flow mt0"><i>2</i>最终意见</h2>
          <ul class="ul_list">
             <li>
               <div id="check_opinion"></div>
             </li>
            
             <li>
               <div class="select_check">
                 <c:if test="${sign == 1}">
                    <%-- <input type="radio" disabled <c:if test="${auditOpinion.flagAudit eq '-3'}">checked</c:if> value="-3">复审合格 --%>
                    <input class="hidden" type="radio" disabled <c:if test="${auditOpinion.flagAudit eq '5'}">checked</c:if> value="5">
                    <input class="hidden" type="radio" disabled <c:if test="${auditOpinion.flagAudit eq '10'}">checked</c:if> value="10">
                  </c:if>
                  <c:if test="${sign == 2}">
	                  <input class="hidden" type="radio" disabled <c:if test="${auditOpinion.flagAudit eq '15'}">checked</c:if> value="15">
	                  <input class="hidden" type="radio" disabled <c:if test="${auditOpinion.flagAudit eq '16'}">checked</c:if> value="16">
                  </c:if>
                </div>
              </li>
             <%--  <li class="mt10">
              ${auditOpinion.opinion }
               <textarea id="opinion" readonly="readonly" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion.opinion }</textarea>
              </li> --%>
          </ul>
          
          <c:if test="${sign == 2}">
	          <h2 class="count_flow mt0"><i>3</i>批准初审表</h2>
	          <ul class="ul_list">
	            <li class="col-md-6 col-sm-6 col-xs-6">
	              <div>
	                <span class="fl">批准初审表：</span>
	                <u:show showId="pic_checkword" businessId="${expertId}2" sysKey="${ sysKey }" typeId="${typeId }" delete="false"/>
	              </div>
	           </li>
	          </ul>
          </c:if>
          <div class="col-md-12 col-sm-12 col-xs-12  add_regist tc">
	          <a class="btn" type="button" onclick="lastStep();">上一步</a>
	          <c:if test="${expert.status == -2 ||  expert.status == 0 || (sign ==1 && expert.status ==9) || (sign ==3 && expert.status ==6) || expert.status ==4}">
	            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zancun();">暂存</a>
	          </c:if>
	          <c:if test="${sign == 2}">
	            <a class="btn" type="button" onclick="nextStep();">下一步</a>
	          </c:if>
	          
	          <c:if test = "${sign eq '1' && (status eq '10' || (reviewUnqualifiedConfirm != 1 and status eq '5')) && isCheck eq 'no'}" >
              <a class="btn" type="button" onclick="preliminaryConfirmation();">确认</a>
            </c:if>
	        </div>
        </div>
      </div>
    </div>
    <input value="${expertId}" id="expertId" type="hidden" />
    <form id="form_id" action="" method="post">
      <input name="expertId" value="${expertId}" type="hidden">
      <input name="sign" value="${sign}" type="hidden">
      <input name="status" id="status" value="${status}" type="hidden">
      <input name="batchId" value="${batchId}" type="hidden">
      <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
      <input name="isCheck" value="${isCheck}" type="hidden">
    </form>

  </body>

</html>