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
          //  var notCategoryNum=${notCategoryNum};
            var qualified=${qualified};
            var status = $("#status").val()
            if (num == 0) {
            	 $("#tuihui").attr("disabled", true);
                if('${status}' != -2 && '${status}' != -3){
                   
                    //$("#butongguo").attr("disabled", true);
                    $("#tichu").attr("disabled", true);
                }
            }
            if (num != 0) {
                //$("#tongguo").attr("disabled", true);
                //if(notCategoryNum == 0){
                	if(qualified){
                		$("#qualified").attr("disabled", false);
                	}else{
                		var mes='${message}';
                		$("#qualified").attr("disabled", true);
                		$("#check_opinion").html("<span class='red'>"+mes+"</span>");
                		$("#cate_result").html("<span class='red'>"+mes+"</span>");
                	}
                /* }else{
                	$("#qualified").attr("disabled", true);
                }*/
                 
            }
            if($("#status").val() == '15' || $("#status").val() == '1' || $("#status").val() == '16' || $("#status").val() == '2'){
            	$("#expdown").css("display","block");
            	/* $("#reverse_of_five_i").css("display","block");
            	$("#reverse_of_six").css("display","block"); */
            }
            
            /**
            *预复审合格状态默认加载通过了xx,不通过xx
            */
           	var expertId = $("input[name='expertId']").val();
           	var checkVal = $("input:radio[name='selectOption']:checked").val();
           	
          	//是否点专家姓名进来查看的
          	var isCheck = '${isCheck}';
           	if(isCheck == "yes"){
           		var opinion = "${auditOpinion.opinion}";
           		seeItemsOpinion(expertId,opinion,status);
           	}else{
           		if(checkVal == '-3'){
               		getCheckOpinionType(expertId);
               	}
               	if(checkVal == '5'){
               		$("#cate_result").html("复审不合格。");
               	}
               	if(checkVal == '10'){
               		$("#cate_result").html("复审退回修改。");
               	}
           	}
           	
            
            //控制《预初审合格》《预初审不合格》
           if(status == '5' || status == '10'){
            	$("#qualified").attr("disabled", true);
            	$("#noQualified").attr("disabled", true);
            }
            check_opinion();
            
            $("#fushengEnd").hide();
        });
        
        
        function showDiv(){
					var s=$('input[name="chkItem"]:checked').eq(0).parent("td").parent("tr").find("td").eq(7).text();
					var status=$("#expertStatus").val();
					if(status==10){
						layer.alert("复审退回修改的专家不能修改审核记录状态！", {offset: '100px'});
						return;
					}
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
					 if("有问题"==s||"未修改"==s){
						$("#revokeStatus").val("4");
						$("#revokeReturn").attr("disabled",false);
					 }
					 if("审核不通过"==s){
						$("#revokeStatus").val("5");
						$("#revokeReturn").attr("disabled",false);
					 }
					 if(""==s){
						$("input[name='updateStatusRadio']").attr("disabled",false);
					 }
				 	$("#updateStatus").css('display','inline');
				 }
			}
			function updateStatus(status) {
				if(status==0){
					status=$("#revokeStatus").val();
				}
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
                    var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
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
        // 审核意见
        function checkOpinion(status, expertId){
        	 var opinion = document.getElementById('opinion').value;
             opinion = trim(opinion);
             var sign = ${sign};
             var flagTime;
             if(sign == 1){
            	 flagTime = 0;
             }
             if(sign == 2){
            	 flagTime = 1;
             }
             // 获取选择radio类型
             var selectOption = $("input[name='selectOption']:checked").val();
            // 审核意见通过。。
            var cate_result = $("#cate_result").html();
             $.ajax({
                 url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
                 data: {"opinion": opinion, "expertId": expertId,"flagTime":flagTime,"flagAudit":selectOption,"cateResult":cate_result},
                 type: "POST",
                 success: function () {
                     $("#status").val(status);
                     $("#form_shenhe").submit();
                 }
             });
        }
        
        //提交审核
        function shenhe(status) {
            var expertId = $("input[name='expertId']").val();
            if(status == null){
            	var status = $(":radio:checked").val().trim();
            	if(status == null){
            		layer.msg("请选择意见", {offset: '100px'});
                return true;
            	}
            }
            
            //退回
            if (status == 3) {
            	updateStepNumber("one");
                //询问框
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认', '取消']
                }, function () {
                	  zancun(3);
                    if (status == 3) {
                    	$("#status").val(status);
                      $("#form_shenhe").submit();
                    }else{
                    	checkOpinion(status, expertId)
                    }
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
                    	alert(status);
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
                                    /* $("#reverse_of_five_i").show();
                                    $("#reverse_of_six").show(); */
                                    $("#fushengEnd").show();
                            	}
                            }
                       });
                    	  layer.close(index);
                    	  //保存信息
                        tempSave();
                        
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
        
	//查询合格通过的产品类别
	function check_opinion() {
		var status = $(":radio:checked").val();
		var expertId = $("input[name='expertId']").val();
		var expertStatus = $("input[name='status']").val();
		if(status != null && typeof(status) != "undefined") {
			$.ajax({
				url: "${pageContext.request.contextPath}/expertAudit/findCategoryCount.do",
				data: {
					"expertId" : expertId,
					"auditFalg" : 1
				},
				type: "post",
				dataType: "json",
				success: function(data) {
					if(status == 15 && expertStatus !=10) {
						if(data.isGoodsServer == 1 && data.pass == 0){
							$("#check_opinion").html("初审合格，通过的是物资服务经济类别。");
						}else{
							$("#check_opinion").html("初审合格，选择了" + data.all + "个参评类别，通过了" + data.pass + "个参评类别。");
						}
					} else if(status == 16 && expertStatus !=10) {
						$("#check_opinion").html("初审不合格。");
						
						//下面是复审退回修改要显示的
					}else if(status == 16 && expertStatus ==10) {
            $("#check_opinion").html("初审不合格。");
          }else if(status == 15 && expertStatus ==10) {
	          if(data.isGoodsServer == 1 && data.pass == 0){
	              $("#check_opinion").html("初审合格，通过的是物资服务经济类别。");
	            }else{
	              $("#check_opinion").html("初审合格，选择了" + data.all + "个参评类别，通过了" + data.pass + "个参评类别。");
	            }
          } 
				}
			});
		}
	}
	
	//预初审结束
	function yuend(status) {
		var status = $(":radio:checked").val();
		var expertId = $("input[name='expertId']").val();
		if(status == null) {
			layer.msg("请选择意见", { offset: '100px' });
			return true;
		}
		layer.confirm('您确认吗？', {
					closeBtn: 0,
					offset: '100px',
					shift: 4,
					btn: ['确认', '取消']
				}, function(index) {
					// 审核意见
					var opinion = document.getElementById('opinion').value;
		             opinion = trim(opinion);
		             var flagTime;
		             if(status == -2){
		                 flagTime = 1;
		             }
		             var sign = $("input[name='sign']").val();
		             var radio = $(":radio:checked").val();
		             if(sign == 1){
		                 flagTime = 0;
		             }
		             if (radio == 15 || (opinion != null && opinion != "")) {
		                 if (opinion.length <= 200) {
		                	 $("#status").val(status);
		                     $.ajax({
		                         url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
		                         data: {"opinion": opinion, "expertId": expertId,"flagTime":flagTime,"flagAudit":radio},
		                         type: "POST",
		                         success: function () {
		                             layer.close(index);
		                             $.ajax({
		                                 url: "${pageContext.request.contextPath}/expertAudit/updateStatusOfPublictity.do",
		                                 data: $("#form_shenhe").serialize(),
		                                 success: function (data) {
		                                 	if(data.status == 200){
			   		                             $("#expdown").css("display","block");
			   		                             $("#tuihui").css("display", "none");
			   		                             $("#yund").css("display", "none");
			   		                             $("#tempSave").css("display", "inline-block");
			   		                             $("#nextStep").css("display", "inline-block");
		                                         // 加载审核导航栏-上传批准审核表
		                                         $("#reverse_of_five_i").show();
		                                         $("#reverse_of_six").show();
		                                         layer.close(index);
		                                 	}
		                                 }
		                            });
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
			});
	}
	
	//预初审下一步
	function yuNext(){
		// 判断附件是否下载
        var downloadAttachFile = $("#isDownLoadAttch").val();
        var status = $("#status").val();
		if(status == '0' || status == '15' || status == '16'){
	        if(downloadAttachFile == null || typeof(downloadAttachFile) == "undefined" || downloadAttachFile == ''){
	            layer.msg("请下载审批表！");
	            return;
	        }
        }
		$("#form_id").attr("action", globalPath + "/expertAudit/uploadApproveFile.html");
        $("#form_id").submit();
	}
	
	//保存下载次数记录
	function downloadCount(){
		var expertId = $("input[name='expertId']").val();
		$.ajax({
            url: "${pageContext.request.contextPath}/expertAudit/downloadCount.do",
            data: {"expertId": expertId},
            type: "POST",
            success: function () {
            	return;
            }
        });
    }
	
	 //暂存
    function zancun(status) {
        var opinion = document.getElementById('opinion').value;
		var expertId = $("input[name='expertId']").val();
		var sign = $("input[name='sign']").val();
        var radio = $(":radio:checked").val();
        var isDownLoadAttch = $("#isDownLoadAttch").val();
        if(sign == 1){
            flagTime = 0;
        }
        if(status == 3){
        	radio = 3;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/expertAudit/auditOpinion.html",
            data: {"opinion": opinion, "expertId": expertId,"flagTime":flagTime,"flagAudit":radio,"isDownLoadAttch":isDownLoadAttch},
            type: "POST",
            success: function () {
	            	//修改专家状态为审核中
	            	$.ajax({
	                 url: "${pageContext.request.contextPath}/expertAudit/temporaryAudit.do",
	                 dataType: "json",
	                 data: {"expertId": expertId, "sign" : sign},
	                 success: function (result) {
	                     layer.msg(result, {offset: ['100px']});
	                 }, error: function () {
	                     layer.msg("暂存失败", {offset: ['100px']});
	                 }
	              });
            }
        });
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
            
            <c:if test="${sign == 1 || sign == 3}">
            <h2 class="count_flow"><i>1</i>审核汇总信息</h2>
            <ul class="ul_list count_flow">
        <c:if test="${isCheck eq 'no' && (status == 0 || status == 9 || status == 15 || status == 16  || status == -2 || (sign ==3 && status ==6) || status == 4)}">
              <!--<button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">撤销</button>-->            	
              <button class="btn btn-windows edit" type="button" onclick="showDiv()" style=" border-bottom-width: -;margin-bottom: 7px;">改状态</button>  
 				</c:if>  
 				<div id="updateStatus" style="display: none">
 					<input type="radio" id="upd" onclick="updateStatus(1)" name="updateStatusRadio" >有问题
 					<input type="radio" id="yupd" onclick="updateStatus(2)" name="updateStatusRadio" >已修改
 					<input type="radio" id="nupd" onclick="updateStatus(3)" name="updateStatusRadio" >未修改
 					<input type="radio" id="revokeReturn" onclick="updateStatus(0)" name="updateStatusRadio" >撤销审核
 					<!-- <input type="radio" id="revokeNotpass" onclick="updateStatus(0)" name="updateStatusRadio">撤销不通过 -->
 					<input type="hidden" id="revokeStatus">
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
                        <th class="info w150">审核时间</th>
                        <th class="info w100">状态</th>
                    </tr>
                    </thead>
                    <c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
                        <input id="auditId" value="${reasons.id}" type="hidden">
                        <tr>
                            <td class="tc"><input type="checkbox" value="${reasons.id }" name="chkItem" id="${reasons.id}" <c:if test="${reasons.suggestType eq 'six' && reasons.auditStatus eq '2'}">disabled="disabled"</c:if> ></td>
                            <td class="text-center">${vs.index + 1}</td>
                            <td class="text-center">
                                <c:if test="${reasons.suggestType eq 'one'}">基本信息</c:if>
                                    <%-- <c:if test="${reasons.suggestType eq 'two'}">经历经验</c:if> --%>
                                <c:if test="${reasons.suggestType eq 'seven'}">专家类别</c:if>
                                <c:if test="${reasons.suggestType eq 'six'}">参评类别</c:if>
                                <c:if test="${reasons.suggestType eq 'five'}">承诺书和申请表</c:if>
                            </td>
                            <td class="text-center">${reasons.auditField }</td>
                            <td class="hand"  title="${reasons.catalogCode == null ? reasons.auditContent : reasons.catalogCode}">
                                <c:if test="${fn:length (reasons.auditContent) > 30}">${fn:substring(reasons.auditContent,0,30)}...</c:if>
                                <c:if test="${fn:length (reasons.auditContent) <= 30}">${reasons.auditContent}</c:if>
                            
                            <td class="hand" title="${reasons.auditReason}">
                                <c:if test="${fn:length (reasons.auditReason) > 20}">${fn:substring(reasons.auditReason,0,20)}...</c:if>
                                <c:if test="${fn:length (reasons.auditReason) <= 20}">${reasons.auditReason}</c:if>
                            </td>
                            <!-- 审核时间 auditAt-->
                            <td class="tc">
                            	<fmt:formatDate value="${reasons.auditAt }" pattern="yyyy-MM-dd HH:mm"/>
                            </td>
                            <!-- 状态 -->
                            <c:if test="${reasons.auditStatus eq '1'}"><td class="tc">有问题</td></c:if>
                            <c:if test="${reasons.suggestType eq 'six' && reasons.auditStatus eq '2'}"><td class="tc">审核不通过</td></c:if>
                            <c:if test="${reasons.suggestType eq 'seven' && reasons.type eq '1' && reasons.auditFieldId != 'isTitle' && reasons.auditStatus eq '2'}"><td class="tc">审核不通过</td></c:if>
                            <c:if test="${reasons.suggestType != 'six' && reasons.auditStatus eq '2' && !(reasons.suggestType eq 'seven' && reasons.type eq '1' && reasons.auditFieldId != 'isTitle' && reasons.auditStatus eq '2') }"><td class="tc">已修改</td></c:if>
                            <c:if test="${reasons.auditStatus eq '3'}"><td class="tc">未修改</td></c:if>
                            <c:if test="${reasons.auditStatus eq '4'}"><td class="tc">撤销退回</td></c:if>
                            <c:if test="${reasons.auditStatus eq '5'}"><td class="tc">撤销不通过</td></c:if>
                            <c:if test="${reasons.auditStatus eq '6'}"><td class="tc">审核不通过</td></c:if>
                            <c:if test="${reasons.auditStatus eq null}"><td class="tc"></td></c:if>
                        </tr>
                    </c:forEach>
                </table>
            </ul>
            </c:if>
            <c:if test="${sign == 1 or sign == 3}">
              <div class="clear"></div>
              <h2 class="count_flow mt0"><i>2</i>最终意见</h2>
              <ul class="ul_list">
                 <c:if test="${sign == 1 }">
                   <li>
                   <div class="select_check">
                      <input type="radio"  id="qualified" <c:if test="${auditOpinion.flagAudit eq '15'}">checked</c:if> name="selectShenhe" value="15" onclick = "check_opinion()" <c:if test="${isCheck eq 'yes'}">disabled="disabled"</c:if>><c:if test="${sign == 1 && status ne '10'}"></c:if>初审合格
                      <input type="radio" id = "noQualified" <c:if test="${auditOpinion.flagAudit eq '16'}">checked</c:if> name="selectShenhe" value="16" onclick = "check_opinion()" <c:if test="${isCheck eq 'yes'}">disabled="disabled"</c:if>><c:if test="${sign == 1 && status ne '10'}"></c:if>初审不合格
                    </div>
                  </li>
                  <li>
                   <div id="check_opinion"></div>
                 </li>
                 </c:if>
                  <li class="mt10">
                     <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" <c:if test="${isCheck eq 'yes'}">disabled="disabled"</c:if>>${auditOpinion.opinion }</textarea>
                  </li>
                </ul>
                <div class="clear"></div>
              <div id = "expdown" class = "display-none">
              <h2 class="count_flow mt0"><i>3</i>专家初审表</h2>
              <ul class="ul_list">
                 <li>
                   <div class="">
                      <a class="btn btn-windows input" onclick='downloadTable(1)' href="javascript:void(0)">下载初审表</a>
                    </div>
                    <input name="isDownLoadAttch" id="isDownLoadAttch" value="${auditOpinion.isDownLoadAttch}" type="hidden">
                    <input name="isDownLoadAttch" id="auditOpinionId" value="${auditOpinion.id}" type="hidden">
                  </li>
                </ul>
                </div>
                
                <!-- 复审退回修改显示 状态为：10 -->
                <c:if test="${sign == 1 && (status eq '10' || (isReviewRevision eq 'yes' && status eq '3'))}">
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
			          
			          
                </div>
              </div>
            </c:if>
            <c:if test="${ sign == 2 }">
              <div class="clear"></div>
              <div id="opinionDiv">
	              <h2 class="count_flow mt0"><i>1</i><span class="red">*</span>专家复审意见</h2>
	              <ul class="ul_list">
		             <c:choose>
		             	<c:when test="${isCheck eq 'yes'}">
		             		<div><span type="text" name="cate_result" id="cate_result"></span></div>
		      		 		<li>
		                 		<div class="select_check" id="selectOptionId">
		                  			<input type="radio" class="hidden" id="qualified" name="selectOption" value="-3">
		                  			<input type="radio" class="hidden" name="selectOption" value="5">
		                  			<input type="radio" class="hidden" name="selectOption" value="10">
		                 		</div>
		             		</li>
		             	</c:when>
		             	<c:otherwise>
		                <li>
		                	<div class="select_check" id="selectOptionId">
			                    <input type="radio" id="qualified" name="selectOption" value="-3">复审合格
			                    <input type="radio"  name="selectOption" value="5">复审不合格
			                    <input type="radio"  name="selectOption" value="10" id="tuihui">复审退回修改
		                    </div>
		                </li>
		                <div><span type="text" name="cate_result" id="cate_result"></span></div>
		                <li class="mt10">
		                    <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion.opinion}</textarea>
		                </li>
		             	</c:otherwise>
		             </c:choose>
              	 </ul>
	          </div>
              <!-- 审核公示扫描件上传 -->
              <%-- <div class="display-none" id="checkWord">
                  <h2 class="count_flow"><i>3</i>专家审批表</h2>
                  <ul class="ul_list">
                          <li class="col-md-6 col-sm-6 col-xs-6">
                              <span class="fl">下载入库复审表：</span>
                              <a href="javascript:;" onclick="downloadTable(0)"><img src="${ pageContext.request.contextPath }/public/webupload/css/download.png"/></a>
                          </li>
                         <li class="col-md-6 col-sm-6 col-xs-6">
                             <div>
                               <span class="fl">专家审批表：</span>
                                 <u:show showId="pic_checkword" businessId="${ expert.auditOpinionAttach }" sysKey="${ sysKey }" typeId="${typeId }" delete="false" />
                             </div>
                            </li>
                      <c:if test="${ status == -3 }">
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
                      </c:if>
                  </ul>
              </div> --%>
              <input type="hidden" value="${auditOpinion.flagAudit}" id="hiddenSelectOptionId" />
              <input type="hidden" value="${passTypeNumber}" id="passTypeNumber" />
              <form id="opinionForm" method="post">
                  <input name="id" value="${auditOpinion.id}" type="hidden">
                  <input name="flagTime" value="" id="flagTime" type="hidden"/>
                  <input name="flagAudit" value="" id="flagAudit" type="hidden"/>
                  <input name="expertId" value="${expertId}" type="hidden"/>
                  <input name="opinion" value="" id="opinionId" type="hidden"/>
                  <input name="vertifyFlag" value="" id="vertifyFlag" type="hidden"/>
                  <input name="isDownLoadAttch" id="downloadAttachFile" value="${auditOpinion.isDownLoadAttch}" type="hidden">
                  <input name="cateResult" id="cateResult" value="" type="hidden">
              </form>
            </c:if>

            <div class="col-md-12 add_regist tc">
                <input name="opinionBack" id="opinionBack" value="" type="hidden">
                <form id="form_shenhe" action="${pageContext.request.contextPath}/expertAudit/updateStatus.html">
                    <a class="btn" type="button" onclick="lastStep();">上一步</a>
                    <input name="id" value="${expertId}" type="hidden">
                    <input type="hidden" name="status" id="status" value="${status}"/>
                    <input name="auditOpinionAttach" id="auditOpinion" type="hidden" />
                    <input name="sign" value="${sign}" type="hidden">
                    <c:if test="${(status eq '0' || (sign eq '1' && status eq '9')) && isCheck eq 'no'}">
                       <!-- <input class="btn btn-windows passed" type="button" onclick="shenhe(1);" value="初审合格 " id="tongguo">
                       <input class="btn btn-windows cancel" type="button" onclick="shenhe(2);" value="初审不合格" id="butongguo"> -->
                       <!-- <input class="btn btn-windows end" type="button" onclick="shenhe();" value="初审结束" id="tuihui"> -->
                       <input class="btn btn-windows reset" type="button" onclick="shenhe(3);" value="退回修改" id="tuihui">
                   	   <input class="btn btn-windows end" type="button" onclick="yuend(15);" value="预初审结束" id="yund">
										  <a id="tempSave" class="btn" onclick="zancun();">暂存</a>
										  <a id="nextStep" class="btn display-none" type="button" onclick="yuNext();">下一步</a>
                    </c:if>
                    <c:if test = "${status == '15' || status == '16'}" >
                      <c:if test="${isCheck eq 'no'}">
                        <a id="tempSave" class="btn" onclick="zancun();">暂存</a>
                      </c:if>
                    	<a id="nextStep" class="btn" type="button" onclick="yuNext();">下一步</a>
                    </c:if>
                    <c:if test = "${status == '1' || status == '2' && sign eq '1'}" >
                    	<a id="nextStep" class="btn" type="button" onclick="yuNext();">下一步</a>
                    </c:if>
                    <c:if test = "${sign eq '1' && (status eq '5' || status eq '10' || (isReviewRevision eq 'yes' && status eq '3'))}" >
                    	<a id="nextStep" class="btn" type="button" onclick="nextStep();">下一步</a>
                    </c:if>
                    <c:if test="${(status eq '4' && sign eq '2' || status eq '-2') && isCheck eq 'no'}">
                       <!-- <input class="btn btn-windows passed" type="button" onclick="shenhe(4);" value="复审合格 " id="tongguo">
                        <input class="btn btn-windows cancel" type="button" onclick="shenhe(5);" value="复审不合格" id="tichu"> -->
                        <!-- <input class="btn btn-windows passed" type="button" onclick="shenhe(3);" value="退回修改" id="tuihui"> -->
                        <input class="btn btn-windows end"  type="button" onclick="preReviewEnd(-2)" value="预复审结束" id="tongguo">
                        <a id="tempSave" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempSave();">暂存</a>
                        <!-- <a id="nextStep" class="btn display-none" type="button" onclick="nextStep();">下一步</a> -->
                    </c:if>
                    <%-- <input class="btn btn-windows end" type="button" onclick="shenhe()" value="复审结束" id="fushengEnd">
                    <c:if test="${status eq '-2' || status == '-3' || status == '4' || status == '5'}">
                        <c:if test="${status == '-2'}">
                          <a id="tempSave" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempSave();">暂存</a>
                           <input class="btn btn-windows end"  type="button" onclick="shenhe()" value="复审结束">
                        </c:if>
                    </c:if> --%>
                    <c:if test="${sign eq '3' and status eq '6'}">
                        <input class="btn btn-windows git" type="button" onclick="shenhe(7);" value="复查合格 " id="tongguo">
                        <input class="btn btn-windows cancel" type="button" onclick="shenhe(8);" value="复查不合格" id="tichu">
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
    <input name="batchId" value="${batchId}" type="hidden">
    <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
    <input name="isCheck" value="${isCheck}" type="hidden">
</form>

<form id="form_id_word" method="post">
  <input name="expertId" type="hidden" value="${expertId}"/>
  <input name="sign" type="hidden" value="${sign }"/>
  <input name="opinion" type="hidden"/>
  <input name="tableType" type="hidden" value=""/>
</form>
<input id="isGoodsServer" type="hidden" value="${isGoodsServer}"/>

  <script>
    // 预复审结束
    function preReviewEnd(status) {
      var expertId = $("input[name='expertId']").val();
      //var batchId = $("input[name='batchId']").val();
      if(status == null){
        var status = $(":radio:checked").val().trim();
        if(status == null){
          layer.msg("请选择意见", {offset: '100px'});
          return true;
        }
      }
      
      $("#status").val(status);
       
      //校验
      var flag = vartifyAuditCount();
      if(flag) {
        return;
      }
       
      $.ajax({
        url: "${pageContext.request.contextPath}/expertAudit/updateStatusOfPublictity.do",
        data: $("#form_shenhe").serialize(),
        success: function (data) {
          if(data.status == 200){
            $("#expertStatus").val(data.data);
          }
        }
      });
      // 获取审核意见
      var opinion = $("#opinion").val();
      // 获取选择radio类型
      var selectOption = $("input[name='selectOption']:checked").val();
      // 将审核意见表单赋值
      $("#opinionId").val(opinion);
      $("#flagTime").val(1);
      $("#flagAudit").val(selectOption);
      // 审核意见通过。。
      var cate_result = $("#cate_result").html();
      $("#cateResult").val(cate_result);
      $.ajax({
        url:globalPath + "/expertAudit/saveAuditOpinion.do",
        type: "POST",
        async :false,
        data:$("#opinionForm").serialize(),
        dataType:"json",
        success:function (data) {
          if(data.status == 200) {
            // location.href = "${pageContext.request.contextPath}/expertAgainAudit/findBatchList.html";
            refresh_parent();
          }
        }
      });
    }
    
    function refresh_parent() {
    	window.opener.index_load(true);
   		window.opener.location.href = changeURLArg(window.opener.location.href, 'expertId', $("input[name='expertId']").val());
   	  window.close();
    }
    
    function changeURLArg(url,arg,arg_val){ 
      var pattern = arg+'=([^&]*)';
      var replaceText = arg+'='+arg_val;
      if(url.match(pattern)) {
        var tmp = '/('+ arg+'=)([^&]*)/gi';
        tmp = url.replace(eval(tmp),replaceText);
        return tmp;
      } else {
        if(url.match('[\?]')) {
          return url+'&'+replaceText;
        } else {
          return url+'?'+replaceText;
        }
      }
      return url+'\n'+arg+'\n'+arg_val;
    }
  </script>
</body>
</html>