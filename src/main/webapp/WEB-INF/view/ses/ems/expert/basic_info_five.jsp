<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

<head>
    <%@ include file="/reg_head.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>评审专家注册</title>
    <script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
    <script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
    <%
        //表单标示
        String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+"";
        session.setAttribute("tokenSession", tokenValue);
    %>
    <script type="text/javascript">
    	//提交后显示出已选采购机构的
    	function queryXinXi(){
    		$.ajax({
				url: "${pageContext.request.contextPath}/expert/validateAuditTime.do",
				data: {"userId" : "${userId}"},
				dataType: "json",
				async: false,
				success: function(response){
					//console.info(response)
					//询问框
					layer.confirm("您选择的是" + response.purchaseDep.shortName + "，联系人：" + response.purchaseDep.experContact + ",电话：" + response.purchaseDep.experPhone + "，地址：" + response.purchaseDep.experAddress +"，邮编："+response.purchaseDep.experPostcode+ "。", {
					//layer.confirm("您选择的是" + response.purchaseDep.shortName + "，联系人：" + response.purchaseDep.contact + ",电话：" + response.purchaseDep.contactMobile + "，地址：" + response.purchaseDep.contactAddress + "。", {
						btn : [ '确定' ],
						shade: false ,//不显示遮罩
						closeBtn: 0
              //按钮
            }, function() {
              window.location.href = '${pageContext.request.contextPath}/';
            });
					}
				});
    	}
        //提交
        function addSubmitForm() {
			
           /*  if(!validateHeTong()) {
                return;
            } else { */
                //$("#formExpert").attr("action","${pageContext.request.contextPath}/expert/add1.html?gitFlag=1");
                //$("#formExpert").submit();
                $.ajax({
                    url: "${pageContext.request.contextPath}/expert/add1.do?gitFlag=1",
                    async: false,
                    data: $('#formExpert').serialize(),
                    success: function(data) {
                        var flag = data.split(",");
                        if(data == "0") {
                        	queryXinXi();
//                             layer.confirm('您已成功提交,请等待审核结果!', {
//                                 btn: ['确定'],
//                                 shade: false //不显示遮罩
                                //按钮
//                             }, function() {
//                                 window.location.href = '${pageContext.request.contextPath}/';
//                             });
                        }else if(flag[0] == "expert_logout"){
                            layer.confirm("您未在 "+flag[1]+" 天内提交审核,注册信息已失效", {
                                btn: ['确定'],
                                shade: false //不显示遮罩
                                //按钮
                            }, function() {
                                window.location.href = '${pageContext.request.contextPath}/';
                            });
                        } else if(data == "2"){
                        	layer.alert("附件未上传，请上传附件");
                        
                        }
                        else {
                            layer.confirm('您已提交,请勿重复操作!', {
                            	closeBtn: 0,
                                btn: ['确定'],
                                shade: false //不显示遮罩
                                //按钮
                            }, function() {
                                window.location.href = '${pageContext.request.contextPath}/';
                            });
                        }
                    }
                });
           // }
        }
        //判断申请表  合同书
        function validateHeTong() {
            var flag = true;
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAttachment2.do",
                data: {
                    "sysId": $("#sysId").val(),
                    "from": "false",
                    "isReferenceLftter":"5"
                },
                cache: false,
                async: false,
                success: function (data) {
                    if (data) {
                        layer.msg(data);
                        flag = false;
                    } else {
                        flag = true;
                    }
                },
                dataType: "json"
            });

            return flag;
        }

        function tab1(name, i, position) {
            updateStepNumber("one");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function tab2(name, i, position) {
            updateStepNumber("two");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function tab7(name, i, position) {
            updateStepNumber("seven");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function tab3(name, i, position) {
            updateStepNumber("three");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function tab6(name, i, position) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/getAllCategory.do",
                data: {
                    "expertId": $("#id").val()
                },
                async: false,
                dataType: "json",
                success: function(response) {
                    if(!$.isEmptyObject(response)) {
                        updateStepNumber("six");
                        window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
                    }
                }
            });
        }

        function tab4(name, i, position) {
            updateStepNumber("four");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function updateStepNumber(stepNumber) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/updateStepNumber.do",
                data: {
                    "expertId": $("#id").val(),
                    "stepNumber": stepNumber
                },
                async: false,
            });
        }

        function errorMsg(auditField) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
                data: {
                    "expertId": $("#id").val(),
                    "auditField": auditField
                },
                dataType: "json",
                success: function(response) {
                    layer.msg("不通过理由:" + response.auditReason, {
                        offset: ['400px', '730px']
                    });
                }
            });
        }
    </script>
</head>

<body>
<form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
    <input type="hidden" name="userId" value="${user.id}" />
    <input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}" />
    <input type="hidden" name="id" id="id" value="${expert.id}" />
    <input type="hidden" name="zancun" id="zancun" value="" />
    <input type="hidden" name="sysId" id="sysId" value="${sysId}" />
    <input type="hidden" value="${errorMap.realName}" id="error1">
    <input type="hidden" value="${errorMap.nation}" id="error2">
    <input type="hidden" value="${errorMap.gender}" id="error3">
    <input type="hidden" value="${errorMap.idType}" id="error4">
    <input type="hidden" value="${errorMap.idNumber}" id="error5">
    <input type="hidden" value="${errorMap.address}" id="error6">
    <input type="hidden" value="${errorMap.hightEducation}" id="error7">
    <input type="hidden" value="${errorMap.graduateSchool}" id="error8">
    <input type="hidden" value="${errorMap.major}" id="error9">
    <input type="hidden" value="${errorMap.expertsFrom}" id="error10">
    <input type="hidden" value="${errorMap.unitAddress}" id="error11">
    <input type="hidden" value="${errorMap.telephone}" id="error12">
    <input type="hidden" value="${errorMap.mobile}" id="error13">
    <input type="hidden" value="${errorMap.healthState}" id="error14">
    <input type="hidden" value="${errorMap.mobile2}" id="error15">
    <input type="hidden" value="${errorMap.idNumber2}" id="error16">
    <input type="hidden" id="categoryId" name="categoryId" value="" />
    <input type="hidden" name="token2" value="<%=tokenValue%>" />
    <div id="reg_box_id_7" class="container clear margin-top-30">
      <div class="col-md-12 col-xs-12 col-sm-12 p0 mb10">
        <h2 class="step_flow">
            <span id="sc1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
            <span id="sp7" class="new_step current fl" onclick='tab7()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类别</span> </span>
            <span id="ty6" class="new_step current fl" onclick='tab6()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
            <span id="sc3" class="new_step current fl" onclick='tab3()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
            <span id="sc4" class="new_step current fl" onclick='tab4()'><i class="">5</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
            <span id="sc5" class="new_step current fl new_step_last"><i class="">6</i> <span class="step_desc_01">提交审核</span> </span>
            <div class="clear"></div>
        </h2>
      </div>
        <div class="tab-content padding-top-20 col-md-12 col-sm-12 col-xs-12 over_auto clear">
            <div class="headline-v2">
                <h2>上传专家申请表、承诺书(将第五步下载的申请表、承诺书签字盖章后,扫描为彩色图片上传。)</h2>
            </div>
            <table class="table table-bordered">
                <tr>
                	<td class="bggrey" width="17%"><i class="red">*</i>军队评审专家承诺书：</td>
                    <td <c:if test="${fn:contains(errorField,'军队评审专家承诺书')}">style="border: 1px solid red;" onmouseover="errorMsg('军队评审专家承诺书')"</c:if>>
                      <div class="w200 fl">
                      	<c:choose>
													<c:when test="${expert.status == 3 and !fn:contains(errorField,'军队评审专家承诺书')}">
														<u:show showId="show7" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="14" />
													</c:when>
													<c:otherwise>
														<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" id="expert14" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" exts="jpg,jpeg,gif,png,bmp" businessId="${sysId}" multiple="true" sysKey="${expertKey}" typeId="14" maxcount="1"   auto="true" />
                        		<u:show showId="show7" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="14" />
													</c:otherwise>
												</c:choose>
                      </div>
                    </td>
                    <td class="bggrey" width="19%"><i class="red">*</i>军队评审专家入库申请表：</td>
                    <td <c:if test="${fn:contains(errorField,'军队评审专家入库申请表')}">style="border: 1px solid red;" onmouseover="errorMsg('军队评审专家入库申请表')"</c:if>>
                       <div class="w200 fl">
                       	<c:choose>
													<c:when test="${expert.status == 3 and !fn:contains(errorField,'军队评审专家入库申请表')}">
														<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}"  id="expert13" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" exts="jpg,jpeg,gif,png,bmp"  businessId="${sysId}" multiple="true"  sysKey="${expertKey}" typeId="13" maxcount="100" auto="true" />
                                <u:show showId="show6" groups="show1,show2,show3,show4,show5,show6,show7,show8"  businessId="${sysId}" sysKey="${expertKey}" typeId="13" />
                                
													</c:when>
													<c:otherwise>
														<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}"  id="expert13" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" exts="jpg,jpeg,gif,png,bmp"  businessId="${sysId}" multiple="true"  sysKey="${expertKey}" typeId="13" maxcount="100" auto="true" />
                        		<u:show showId="show6" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="13" />
													</c:otherwise>
												</c:choose>
                       </div>
                    </td>
                </tr>
            </table>
            <div class="btmfix">
                <div style="margin-top: 15px;text-align: center;">
                    <button class="btn" type="button" onclick="tab4()">上一步</button>
                    <input class="btn" type="button" onclick="addSubmitForm()" value="提交审核" />
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>

</html>