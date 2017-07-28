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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/reasonsList.js"></script>
    <script type="text/javascript">
        $(function () {
            //审核按钮状态
            var num = ${num};
            if (num == 0) {
                if('${status}' != -2 && '${status}' != -3){
                    //$("#tuihui").attr("disabled", true);
                    //$("#butongguo").attr("disabled", true);
                    $("#tichu").attr("disabled", true);
                }
            }
            if (num != 0) {
                //$("#tongguo").attr("disabled", true);
            }
        });

        // 审核意见
        function checkOpinion(status, expertId){
        	 var opinion = document.getElementById('opinion').value;
             opinion = trim(opinion);
             var flagTime;
             if(status == -2){
                 flagTime = 1;
             }
             if (opinion != null && opinion != "") {
                 if (opinion.length <= 200) {
                     $.ajax({
                         url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
                         data: {"opinion": opinion, "expertId": expertId,"flagTime":flagTime},
                         success: function () {
                             //$("#status").val(status);
                             //$("#form_shenhe").submit();
                         }
                     });
                 } else {
                     layer.msg("不能超过200字", {offset: '100px'});
                     return true;
                 }
             } else {
                 layer.msg("请填写最终意见", {offset: '100px'});
                 return true;
             }
        }
        
        //提交审核
        function shenhe(status) {
            var expertId = $("input[name='expertId']").val();
            //退回
            if (status == 3) {
                updateStepNumber("one");
            }
            if (status == 2 || status == 3 || status == 5  || status == 8) {
                //询问框
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认', '取消']
                }, function () {
                    /* var index = layer.prompt({
                     title: '请填写最终意见：',
                     formType: 2,
                     offset: '100px',
                     }, function(text) {
                     $.ajax({
                     url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
                     data: {"opinion" : text , "expertId" : expertId},
                     success: function() {
                     //提交审核
                     $("#status").val(status);
                     $("#form_shenhe").submit();
                     }
                     });
                     }); */
                    if(status != 3){
                        if(checkOpinion(status, expertId)){
                            return;
                        }else{
                            $.ajax({
                                url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
                                data: {"expertId" : expertId},
                                success: function() {
                                    //提交审核
                                    $("#status").val(status);
                                    $("#form_shenhe").submit();
                                }
                            });
                        }
                    }else {
                        //提交审核
                        $("#status").val(status);
                        $("#form_shenhe").submit();
                    }


                    //初审不合格
                    /* if (status == 2) {
                        window.location.href = "${pageContext.request.contextPath}/expertAudit/saveAuditNot.html?expertId=" + expertId;
                    } */
                });
            } else {
                if(status == -2){
                    //校验
                    var flag = vartifyAuditCount();
                    if(flag){
                        return;
                    }
                }
                //询问框
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认', '取消']
                }, function (index) {
                	  // 审核意见
                    if(status != -2){
                        if('${opinion}' == ''){
                            if(checkOpinion(status, expertId)){
                                return;
                            }
                        }
                    }
                  	//提交审核
                    if(status == -2){
                    	$("#status").val(status);
                    	$.ajax({
                            url: "${pageContext.request.contextPath}/expertAudit/updateStatusOfPublictity.do",
                            data: $("#form_shenhe").serialize(),
                            success: function (data) {
                            	if(data.status == 200){
                            	    $("#expertStatus").val(data.data);
                            	    $("#tuihui").hide();
                            	    $("#tongguo").hide();
                            	    $("#tempSave").css("display","inline-block");
                            	    $("#nextStep").css("display","inline-block");
                                    $("#checkWord").show();
                                    // 加载审核导航栏-上传批准审核表
                                    $("#reverse_of_five_i").show();
                                    $("#reverse_of_six").show();
                            	}
                            }
                       });
                    	layer.close(index);
                        return;
                    }
                    //提交审核
                    layer.close(index);
                    // 上传审核扫描件
                    /*if(status == -3){
                    	$("#auditOpinion").val($("#auditOpinionFile").val());
                    }*/
                    $("#status").val(status);
                    $("#form_shenhe").submit();
                });
            }
        }

        function trim(str) { //删除左右两端的空格
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }


        function updateStepNumber(stepNumber) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/updateStepNumber.do",
                data: {"expertId": $("#expertId").val(), "stepNumber": stepNumber},
                async: false,
            });
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
                                    var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
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
              <c:if test="${status == 0 || status == -2 || (sign ==2 && status ==1) || status == 6}">
                <button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">移除</button>
              </c:if>  
                <table class="table table-bordered table-condensed table-hover">
                    <thead>
                    <tr>
                        <th class="info w30"><input type="checkbox" onclick="selectAll();" id="checkAll"></th>
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
                            <td class="tc w30"><input type="checkbox" value="${reasons.id }" name="chkItem" id="${reasons.id}"></td>
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
            <c:if test="${sign != 2}">
                <div>
                    <h2 class="count_flow"><i>2</i>最终意见</h2>
                    <ul class="ul_list">
                        <li class="col-md-12 col-sm-12 col-xs-12">
                            <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion.opinion }</textarea>
                            </div>
                        </li>
                    </ul>
                </div>
            </c:if>
            <c:if test="${ sign == 2 }">
                <div>
                    <div id="opinionDiv">
                        <h2 class="count_flow"><i>2</i><span class="red">*</span>复审意见</h2>
                        <ul class="ul_list">
                            <li>
                                <div class="select_check" id="selectOptionId">
	                                <input type="radio"  name="selectOption" value="1">预复审合格
	                                <input type="radio"  name="selectOption" value="0">预复审不合格
                                </div>
                            </li>
                            <div><span type="text" name="cate_result" id="cate_result"></span></div>
                            <li class="col-md-12 col-sm-12 col-xs-12">
                                <div class="col-md-12 col-sm-12 col-xs-12 p0">
		                               <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${ auditOpinion.opinion }</textarea>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- 审核公示扫描件上传 -->
                    <div class="display-none" id="checkWord">
                        <h2 class="count_flow"><i>3</i>专家审批表</h2>
                        <ul class="ul_list">
                                <li class="col-md-6 col-sm-6 col-xs-6">
                                    <span class="fl">下载入库复审表：</span>
                                    <a href="javascript:;" onclick="downloadTable(0)"><img src="${ pageContext.request.contextPath }/public/webupload/css/download.png"/></a>
                                </li>
                               <%-- <li class="col-md-6 col-sm-6 col-xs-6">
                                   <div>
                                     <span class="fl">专家审批表：</span>
                                       <u:show showId="pic_checkword" businessId="${ expert.auditOpinionAttach }" sysKey="${ sysKey }" typeId="${typeId }" delete="false" />
                                   </div>
                                  </li>--%>
                            <%--<c:if test="${ status == -3 }">
                                    <li class="col-md-6 col-sm-6 col-xs-6">
                                        <span class="fl">下载入库复审表：</span>
                                        <a href="javascript:;" onclick="downloadTable(2)"><img src="${ pageContext.request.contextPath }/public/webupload/css/download.png"/></a>
                                    </li>
                                <li class="col-md-6 col-sm-6 col-xs-6">
                                      <div>
                                        <span class="fl">上传彩色扫描件：</span>
                                          <% String uuidcheckword = UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
                                          <input name="check_word_pic" id="auditOpinionFile" type="hidden" value="<%=uuidcheckword%>" />
                                          <u:upload id="pic_checkword" businessId="<%=uuidcheckword %>" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传彩色扫描件" auto="true" exts="png,jpeg,jpg,bmp,git" />
                                          <u:show showId="pic_checkword" businessId="<%=uuidcheckword %>" sysKey="${ sysKey }" typeId="${typeId }" />
                                      </div>
                                    </li>
                            </c:if>--%>
                        </ul>
                    </div>
                    <input type="hidden" value="${auditOpinion.flagAudit}" id="hiddenSelectOptionId" />
                </div>
                <form id="opinionForm" method="post">
                    <input name="id" value="${auditOpinion.id}" type="hidden">
                    <input name="flagTime" value="" id="flagTime" type="hidden"/>
                    <input name="flagAudit" value="" id="flagAudit" type="hidden"/>
                    <input name="expertId" value="${expertId}" type="hidden"/>
                    <input name="opinion" value="" id="opinionId" type="hidden"/>
                    <input name="vertifyFlag" value="" id="vertifyFlag" type="hidden"/>
                    <input name="isDownLoadAttch" id="downloadAttachFile" value="${auditOpinion.isDownLoadAttch}" type="hidden">
                </form>
            </c:if>

            <div class="col-md-12 add_regist tc">
                <input name="opinionBack" id="opinionBack" value="" type="hidden">
                <form id="form_shenhe" action="${pageContext.request.contextPath}/expertAudit/updateStatus.html">
                    <a class="btn" type="button" onclick="lastStep();">上一步</a>
                    <input name="id" value="${expertId}" type="hidden">
                    <input type="hidden" name="status" id="status" value="${status}"/>
                    <input name="auditOpinionAttach" id="auditOpinion" type="hidden" />
                    <c:if test="${status eq '0'}">
                        <input class="btn btn-windows git" type="button" onclick="shenhe(1);" value="初审合格 " id="tongguo">
                        <input class="btn btn-windows reset" type="button" onclick="shenhe(2);" value="初审不合格" id="butongguo">
                        <input class="btn btn-windows reset" type="button" onclick="shenhe(3);" value="退回修改" id="tuihui">
                    </c:if>
                    <c:if test="${status eq '1' && sign eq '2'}">
                        <%--<span class="display-none" id="publicity"><input class="btn btn-windows apply" type="button" onclick="shenhe(-3);" value="公示 "></span>--%>
                        <%--<input class="btn btn-windows edit" type="button" onclick="shenhe(5);" value="复审不合格" id="tichu">--%>
                        <input class="btn btn-windows reset" type="button" onclick="shenhe(3);" value="退回修改" id="tuihui">
                        <input class="btn btn-windows git"  type="button" onclick="shenhe(-2)" value="预复审结束" id="tongguo">
                        <a id="tempSave" class="btn padding-left-20 padding-right-20 btn_back margin-5 display-none" onclick="tempSave();">暂存</a>
                        <a id="nextStep" class="btn display-none" type="button" onclick="nextStep();">下一步</a>
                    </c:if>
                    <c:if test="${status eq '-2' || status == '-3' || status == '4' || status == '5'}">
                        <c:if test="${status == '-2'}">
                          <a id="tempSave" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempSave();">暂存</a>
                        </c:if>
                        <a id="nextStep" class="btn" type="button" onclick="nextStep();">下一步</a>
                    </c:if>
                    <c:if test="${status eq '6'}">
                        <input class="btn btn-windows git" type="button" onclick="shenhe(7);" value="复查合格 " id="tongguo">
                        <input class="btn btn-windows edit" type="button" onclick="shenhe(8);" value="复查不合格" id="tichu">
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
<input value="${expertId}" id="expertId" type="hidden" />
<form id="form_id" action="" method="post">
    <input name="expertId" value="${expertId}" type="hidden">
    <input name="sign" value="${sign}" type="hidden">
    <input name="status" id="expertStatus" value="${status}" type="hidden">
</form>

<form id="form_id_word" method="post">
  <input name="expertId" type="hidden" value="${expertId}"/>
  <input name="sign" type="hidden" value="${sign }"/>
  <input name="opinion" type="hidden"/>
  <input name="tableType" type="hidden" value=""/>
</form>

</body>
</html>