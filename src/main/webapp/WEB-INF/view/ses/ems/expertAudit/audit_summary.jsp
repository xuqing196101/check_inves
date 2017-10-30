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
    	  $("#reverse_of_eight").attr("class","active");
        $("#reverse_of_eight").removeAttr("onclick");
    	  
      })
     
      //下一步
      function nextStep() {
        var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }

      //上一步
      function lastStep() {
        var action = "${pageContext.request.contextPath}/expertAudit/preliminaryInfo.html";
        $("#form_id").attr("action", action);
        $("#form_id").submit();
      }
      
      /** 全选全不选 */
      function selectAll() {
          var checklist = document.getElementsByName("chkItem");
          var checkAll = document.getElementById("checkAll");
          if (checkAll.checked) {
              for (var i = 0; i < checklist.length; i++) {
                  checklist[i].checked = true;
              }
          } else {
              for (var j = 0; j < checklist.length; j++) {
                  checklist[j].checked = false;
              }
          }
      }
      
      //移除
      function dele() {
        var expertId = $("input[name='expertId']").val();
        var ids = [];
        $('input[name="chkItem"]:checked').each(function () {
            ids.push($(this).val());
        });
        if (ids.length > 0) {
            layer.confirm('您确定要移除吗?', {title: '提示！', offset: ['200px']}, function (index) {
                layer.close(index);
                $.ajax({
                    url: "${pageContext.request.contextPath}/expertAudit/deleteByIds.html",
                    data: "ids=" + ids,
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        result = eval("(" + result + ")");
                        if (result.msg == "yes") {
                            layer.msg("删除成功!", {offset: '100px'});
                            window.setTimeout(function () {
                                var action = "${pageContext.request.contextPath}/expertAudit/auditSummary.html";
                                $("#form_id").attr("action", action);
                                $("#form_id").submit();
                            }, 1000);
                        }
                        ;
                    },
                    error: function () {
                        layer.msg("删除失败", {offset: '100px'});
                    }
                });
            });
        } else {
            layer.alert("请选择需要移除的信息！", {offset: '100px'});
        }
        ;
      }
      
    //暂存
      function zancun() {
        var expertId = $("#expertId").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/expertAudit/temporaryAudit.do",
          dataType: "json",
          data: {expertId: expertId},
          success: function (result) {
              layer.msg(result, {offset: ['100px']});
          }, error: function () {
              layer.msg("暂存失败", {offset: ['100px']});
          }
        });
      }
    
    
      function showDiv(){
        var s=$('input[name="chkItem"]:checked').eq(0).parent("td").parent("tr").find("td").eq(7).text();
        var show=true;
        if($('input[name="chkItem"]:checked').size()<=0){
          layer.alert("请选择需要修改状态的信息！", {offset: '100px'});
          return;
        }
        $('input[name="chkItem"]:checked').each(function () {
         var str=$(this).parent("td").parent("tr").find("td").eq(7).text();
         if(s!=str){
           layer.alert("请选择相同状态的审核记录！", {offset: '100px'});
           show=false;
          }
        });
        if("已修改"==s||"撤销退回"==s||"撤销不通过"==s){
          layer.alert(s+"的审核记录不能修改状态！", {offset: '100px'});
          return;
        }
        if(show){
          $("input[name='updateStatusRadio']").attr("disabled","disabled");
          $("input[name='updateStatusRadio']").removeAttr('checked');
          $("input[type='checkbox']").attr("disabled","disabled");
          if("退回修改"==s||"未修改"==s){
           $("#revokeReturn").attr("disabled",false);
          }
          if("审核不通过"==s){
           $("#revokeNotpass").attr("disabled",false);
          }
          if(""==s){
           $("input[name='updateStatusRadio']").attr("disabled",false);
          }
         $("#updateStatus").css('display','inline');
        }
      }
      function updateStatus(status) {
    	  var expertId = $("input[name='expertId']").val();
        var ids = "";
        $('input[name="chkItem"]:checked').each(function () {
          ids+=$(this).val()+",";
        });
        if (ids.length > 0) {
		      layer.confirm('您确定要修改吗?', {title: '提示！', offset: ['200px']}, function (index) {
		    	layer.close(index);
          $.ajax({
            url: "${pageContext.request.contextPath}/expertAudit/updateAuditStatus.html",
            data: {'ids' : ids.substring(0, ids.length),
              'status':status
            },
            type: "post",
            dataType: "json",
            success: function (result) {
              result = eval("(" + result + ")");
              if (result.msg == "yes") {
                layer.msg("修改成功!", {offset: '100px'});
                window.setTimeout(function () {
                  var action = "${pageContext.request.contextPath}/expertAudit/auditSummary.html";
                  $("#form_id").attr("action", action);
                  $("#form_id").submit();
                }, 1000);
              };
            },
            error: function () {
                layer.msg("修改失败", {offset: '100px'});
            }
          });
        });
      }
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
          <c:if test="${sign eq '2'}">
            <h2 class="count_flow"><i>1</i>复审汇总信息</h2>
          </c:if>
          <c:if test="${sign ne '2'}">
            <h2 class="count_flow"><i>1</i>审核汇总信息</h2>
          </c:if>
          <ul class="ul_list count_flow">
             <c:if test="${status == -2 || status == 0 || (sign ==1 && expert.status ==9) || (sign ==3 && status ==6) || status ==4}">
              <!-- <button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">撤销</button> -->
              <button class="btn btn-windows edit" type="button" onclick="showDiv()" style=" border-bottom-width: -;margin-bottom: 7px;">改状态</button>  
            </c:if> 
		         <div id="updateStatus" style="display: none">
		          <input type="radio" id="upd" onclick="updateStatus(1)" name="updateStatusRadio" >退回修改
		          <input type="radio" id="yupd" onclick="updateStatus(2)" name="updateStatusRadio" >已修改
		          <input type="radio" id="nupd" onclick="updateStatus(3)" name="updateStatusRadio" >未修改
		          <input type="radio" id="revokeReturn" onclick="updateStatus(4)" name="updateStatusRadio" >撤销退回
		          <input type="radio" id="revokeNotpass" onclick="updateStatus(5)" name="updateStatusRadio">撤销不通过
		         </div>
            <table class="table table-bordered table-condensed table-hover">
              <thead>
              <tr>
                <th class="info w30"><input type="checkbox" onclick="selectAll();" id="checkAll"></th>
                <th class="info w50">序号</th>
                <th class="info w80">审批类型</th>
                <th class="info w80">审批字段</th>
                <th class="info w200">审批内容</th>
                <th class="info">审核理由</th>
                <!-- <th class="info w150">审核时间</th> -->
                <th class="info w100">状态</th>
              </tr>
              </thead>
              <c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
              <input id="auditId" value="${reasons.id}" type="hidden">
              <tr>
                <td class="tc"><input type="checkbox" value="${reasons.id }" name="chkItem" id="${reasons.id}"></td>
                <td class="text-center">${vs.index + 1}</td>
                <td class="text-center">
                  <c:if test="${reasons.suggestType eq 'one'}">基本信息</c:if>
                      <%-- <c:if test="${reasons.suggestType eq 'two'}">经历经验</c:if> --%>
                  <c:if test="${reasons.suggestType eq 'seven'}">专家类别</c:if>
                  <c:if test="${reasons.suggestType eq 'six'}">参评类别</c:if>
                  <c:if test="${reasons.suggestType eq 'five'}">承诺书和申请表</c:if>
                </td>
                <td class="text-center">${reasons.auditField }</td>
                <td class="hand" title="${reasons.auditContent}">
                  <c:if test="${fn:length (reasons.auditContent) > 30}">${fn:substring(reasons.auditContent,0,30)}...</c:if>
                  <c:if test="${fn:length (reasons.auditContent) <= 30}">${reasons.auditContent}</c:if>
                </td>
                <td class="hand" title="${reasons.auditReason}">
	                 <c:if test="${fn:length (reasons.auditReason) > 20}">${fn:substring(reasons.auditReason,0,20)}...</c:if>
	                 <c:if test="${fn:length (reasons.auditReason) <= 20}">${reasons.auditReason}</c:if>
                </td>
                <!-- 审核时间 auditAt-->
                <%-- <td class="tc">
                  <fmt:formatDate value="${reasons.auditAt }" pattern="yyyy-MM-dd HH:mm"/>
                </td> --%>
                 <!-- 状态 -->
                 <c:if test="${reasons.auditStatus eq '1'}"><td class="tc">退回修改</td></c:if>
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
          
          <div class="col-md-12 col-sm-12 col-xs-12  add_regist tc">
	          <a class="btn" type="button" onclick="lastStep();">上一步</a>
	          <c:if test="${status == -2 || status == 0 || (sign ==1 && expert.status ==9) || (sign ==3 && status ==6) || status ==4}">
	            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zancun();">暂存</a>
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
      <input name="batchId" value="${batchId}" type="hidden">
    </form>

  </body>

</html>