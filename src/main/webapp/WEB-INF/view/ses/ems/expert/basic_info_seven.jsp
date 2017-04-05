<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

<head>
    <%@ include file="/reg_head.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>评审专家注册</title>
    <script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
    <script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
    <%
        //表单标示
        String tokenValue = new Date().getTime() + UUID.randomUUID().toString() + "";
        session.setAttribute("tokenSession", tokenValue);
    %>
    <script type="text/javascript">
        var isIs;
        function submitformExpert() {
            getChildren();
            tempSave();
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/zanCun.do",
                data: $("#formExpert").serialize(),
                type: "post",
                async: true,
                success: function (result) {
                    $("#id").val(result.id);
                    //layer.msg("已暂存",{offset: ['300px', '750px']});
                }
            });
        }
        //无提示暂存
        function submitForm2() {

            $.ajax({
                url: "${pageContext.request.contextPath}/expert/zanCun.do",
                data: $("#formExpert").serialize(),
                type: "post",
                async: false,
                success: function (result) {
                    $("#id").val(result.id);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/expert/getAllCategory.do",
                        data: {
                            "expertId": $("#id").val()
                        },
                        async: false,
                        dataType: "json",
                        success: function (response) {
                            updateStepNumber("six");
                           /* if (!$.isEmptyObject(response)) {

                                /!*updateStepNumber("six");
                            } else {
                                updateStepNumber("three");*!/
                             //
                            }*/
                        }
                    });
                    window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
                }
            });
        }

		// 不知道是干嘛用的，崔梦凯加的
        function mmm() {
            var hh;
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAttachment2.do",
                data: {
                    "sysId": $("#sysId").val(),
                    "from": "test",
                    "isReferenceLftter": 3
                },
                cache: false,
                async: false,
                success: function (data) {
                    if (data) {
                        layer.msg(data);
                        hh = false
                    } else {
                        hh = true
                    }
                },
                dataType: "json"
            });
            return hh;
        }


        //function validations=
        function fun() {
            //此处是对选中专家进行校验
            if (isIs) {
            	
//             	var checklists = document.getElementsByName("chkItem_1");
// 				if (checklists[1].checked) {
// 					if (!$("#professional").val()) {
// 	                    layer.msg("请填写执业资格职称 !");
// 	                    return false;
// 	                }
// 	                if (!$("#timeProfessional").val()) {
// 	                    layer.msg("请填写获取专家证书的时间 !");
// 	                    return false;
// 	                }
// 				}
                var asx = mmm();

                /*if (!$("#professTechTitles").val()) {
                    layer.msg("请填写执业资格职称 !");
                    return false;
                }
                if (!$("#timeToWork").val()) {
                    layer.msg("请填写获取专家证书的时间 !");
                    return false;
                }*/
                if ((typeof asx) == "undefined") {
                    asx = true;
                }
                if (asx && isIs ) {
                    if (!validateType()) {
                        return;
                    } else{
                        submitForm2();
                    }

                }

            }else{
                if (!validateType()) {
                    return;
                } else{
                    submitForm2();
                }

            }
            //暂存无提示
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
        //获取选中子节点id
        function getChildren() {
            var ids = new Array();
            var isType1 = 0;
            var isType2 = 0;
            $("#isType").val("");  
            var checklist1 = document.getElementsByName("chkItem_1");
            for (var i = 0; i < checklist1.length; i++) {
                var vals = checklist1[i].value;
                if (checklist1[i].checked) {
                	isType1 = 1;
                    ids.push(vals);
                }
            }
            var checklist2 = document.getElementsByName("chkItem_2");
            for (var i = 0; i < checklist2.length; i++) {
                var vals = checklist2[i].value;
                if (checklist2[i].checked) {
                	 isType2 = 1;
                    ids.push(vals);
                }
            }
            
            if(isType1 == 1 && isType2 == 1){
            	$("#isType").val(1);	
            }
            
            $("#expertsTypeId").val(ids);
        }

        function pre() {
            updateStepNumber("one");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }

        function pre1() {
            updateStepNumber("one");
            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
        }
        //判断专家类型
        function validateType() {
            getChildren();
            var categoryId = $("#expertsTypeId").val();
            var isType = $("#isType").val();
            if (categoryId == "") {
                layer.msg("请选择专家类别 !");
                return false;
            }else if (isType != null && isType == 1) {
            	  layer.msg("不能同时选择经济和技术类型! ");
                return false;
            }
            
            return true;
        }
        $(function () {

            var bja = [];

            $("input").bind("blur", submitformExpert);
            var typeIds = "${expert.expertsTypeId}";
            var ids = typeIds.split(",");
            //回显
            var checklist1 = document.getElementsByName("chkItem_1");
            for (var i = 0; i < checklist1.length; i++) {
                var vals = checklist1[i].value;
                for (var j = 0; j < ids.length; j++) {
                    if (ids[j] == vals) {
                        bja.push(vals);
                        checklist1[i].checked = true;
                    }
                }
            }
            var checklist2 = document.getElementsByName("chkItem_2");
            for (var i = 0; i < checklist2.length; i++) {
                var vals = checklist2[i].value;
                for (var j = 0; j < ids.length; j++) {
                    if (ids[j] == vals) {
                        checklist2[i].checked = true;

                    }
                }
            }


        	$("input[name='chkItem_1']:checked").each(function() {
        		var val=$(this).parent().text();
            	if(val.trim()=="工程技术"){	
            	 if ($(this).prop("checked")) {
                     init_web_upload();
                       $("#tab_div").attr("class", "container");
                 }  
            	}
    		});
        	
        	$("input[name='chkItem_2']:checked").each(function() {
        		var val=$(this).parent().text();
        		
            	if(val.trim()=="工程经济"){	
            	
            	 if ($(this).prop("checked")) {
                     init_web_upload();
                       $("#tab_div").attr("class", "container");
                 } 
            	}
    		});
        	
        	
//绑定工程技术的切换事件
         /*    isIs = bja.some(function (item, index, array) {
                return item == "3EC64C63FE15422EA100F58A4A872F4A"
            }); */
       /*      if (isIs) {
                init_web_upload();
                $("#tab_div").attr("class", "container");
                
            } else {
              $("#tab_div").attr("class", "container opacity_0");
            }
            $(checklist1[1]).change(function () {
                if ($(this).prop("checked")) {
                    isIs = true;
                    init_web_upload();
                      $("#tab_div").attr("class", "container");
                } else {
                    isIs = false;
                       $("#tab_div").attr("class", "container opacity_0");
                }
            }) */
        });

        function errorMsg(auditField) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
                data: {
                    "expertId": $("#id").val(),
                    "auditField": auditField
                },
                dataType: "json",
                success: function (response) {
                    layer.msg("不通过理由:" + response.auditReason);
                }
            });
        }

        function zc() {
            layer.msg("已暂存");
        }
        function checks(obj){
        	var flag=true;
        	$("input[name='chkItem_1']").each(function() {
        		var val=$(this).parent().text();
            	if(val.trim()=="工程技术"){	
            	 if ($(this).prop("checked")) {
                     init_web_upload();
                       $("#tab_div").attr("class", "container");
                 }else {
                	 flag=false;
                        $("#tab_div").attr("class", "container opacity_0");
                 }
            	}
    		});
        	
        	if(flag==false){
        		 $("input[name='chkItem_2']").each(function() {
             		var val=$(this).parent().text();
             		
                 	if(val.trim()=="工程经济"){	
                 	 if ($(this).prop("checked")) {
                          init_web_upload();
                            $("#tab_div").attr("class", "container");
                      } else {
                             $("#tab_div").attr("class", "container opacity_0");
                      }
                 	}
         		}); 
        	}
        	 
        	
        }
        
        function addPractice(){
        	var detailRow = $("#production_div").find("li");
			var index = (detailRow.length+1)/4-1;
			var id=$("#id").val();
			$.ajax({
				url: "${pageContext.request.contextPath}/expert/practice.do",
				type: "post",
				data:{"index":index,"expertId":id},
				success: function(data) {
					$("#addUl").append(data);
					init_web_upload();
				}
			});
			
        }
        
        function delPractice(obj){
        	var detailRow = $("#tab_div").find("li");
			var index = detailRow.length;
			/* if(index<4){
				 layer.msg("不通过理由:" + response.auditReason);
			}else{ */
				var id=$(obj).next().val();
	        	$.ajax({
					url: "${pageContext.request.contextPath}/expert/deleteprofessional.do",
					type: "post",
					data:{"id":id},
					success: function(data) {
						$(obj).parent().parent().prev().prev().prev().remove();
			        	$(obj).parent().parent().prev().prev().remove();
			        	$(obj).parent().parent().prev().remove();
			        	$(obj).parent().parent().remove();
					}
				});
			//}
        }
        function tempSave(){
        	 $.ajax({
                 url: "${pageContext.request.contextPath}/expert/addprofessional.do",
                 data: $("#formExpert").serialize(),
                 type: "post",
                 async: true,
                 success: function (result) {
                     //layer.msg("已暂存",{offset: ['300px', '750px']});
                 }
             });
        }
    </script>
</head>

<body>
<form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
    <input type="hidden" name="userId" value="${user.id}"/>
    <input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}"/>
    <input type="hidden" name="id" id="id" value="${expert.id}"/>
    <input type="hidden" name="zancun" id="zancun" value=""/>
    <input type="hidden" name="sysId" id="sysId" value="${sysId}"/>
    <input type="hidden" value="${errorMap.realName}" id="error1">
    <input type="hidden" value="${errorMap.nation}" id="error2">
    <input type="hidden" value="${errorMap.gender}" id="error3">
    <input type="hidden" value="${errorMap.idType}" id="error4">
    <input type="hidden" value="${errorMap.idNumber}" id="error5">
    <input type="hidden" value="${errorMap.address}" id="error6">
    <input type="hidden" value="${errorMap.hightEducation}" id="error7">
    <input type="hidden" value="${errorMap.graduateSchool}" id="error8">
    <input type="hidden" value="${errorMap.major}" id="error9">
    <input type="hidden" value="${errorMap.unitAddress}" id="error11">
    <input type="hidden" value="${errorMap.telephone}" id="error12">
    <input type="hidden" value="${errorMap.mobile}" id="error13">
    <input type="hidden" value="${errorMap.healthState}" id="error14">
    <input type="hidden" value="${errorMap.mobile2}" id="error15">
    <input type="hidden" value="${errorMap.idNumber2}" id="error16">
    <input type="hidden" id="categoryId" name="categoryId" value=""/>
    <input type="hidden" id="expertsTypeId" name="expertsTypeId" value=""/>
      <!--   是否只选择了一种类型 -->
    <input type="hidden" id="isType" name="isType" value=""/>
    <input type="hidden" name="token2" value="<%=tokenValue%>"/>
    <div id="reg_box_id_3" class="container clear margin-top-30 job-content">
        <h2 class="padding-20 mt40">
            <span id="sp1" class="new_step current fl" onclick="pre1()"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
            <span id="sp7" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类别</span> </span>
            <span id="ty6" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
            <span id="sp3" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
            <span id="sp4" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
            <span id="sp5" class="new_step fl"><i class="">6</i><span class="step_desc_01">提交审核</span> </span>
            <div class="clear"></div>
        </h2>
        <div class="container container_box">
            <h2 class="count_flow">专家类别</h2>
            <!-- 专家专业信息 -->
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl10">
                    <div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
                        <c:forEach items="${spList}" var="sp">
                            <span  <c:if test="${fn:contains(errorField,sp.id)}">style="color: #ef0000;"  onmouseover="errorMsg('${sp.id}')"</c:if>  class="margin-left-30">
                            <input  type="checkbox"  onclick="checks(this)" name="chkItem_1" value="${sp.id}"/>${sp.name}技术 </span>
                        </c:forEach>
                        <c:forEach items="${jjList}" var="jj">
                            <span <c:if test="${fn:contains(errorField,jj.id)}">style="color: #ef0000;"  onmouseover="errorMsg('${jj.id}')"</c:if> class="margin-left-30">
                            <input onclick="checks(this)"  type="checkbox" name="chkItem_2" value="${jj.id}"/>${jj.name} </span>
                        </c:forEach>
                    </div>
                </li>
            </ul>


	<div class="container opacity_0" id="tab_div">
		<div class="tab-pane fades active in" id="production_div">
	 
		<ul class="list-unstyled f14" id="addUl">
		
		<c:forEach items="${expert.titles }" var="t"  varStatus="vs" >
		<li class="col-md-3 col-sm-6 col-xs-12 pl15">
			<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格职称</span> <!--/执业资格  -->
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input  <c:if test="${fn:contains(errorField,'执业资格职称')}">style="border: 1px solid #ef0000;"
                                onmouseover="errorMsg('执业资格职称')"</c:if>
                                maxlength="20" value="${t.qualifcationTitle}"
                                name="titles[${vs.index }].qualifcationTitle"  type="text"/>
                        <span class="add-on">i</span> <span class="input-tip"></span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 执业资格</span>
                    <div
                            class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0"
                            <c:if test="${fn:contains(errorField,'执业资格')}">style="border: 1px solid #ef0000;"
                            onmouseover="errorMsg('执业资格')"</c:if>>
                        <u:upload
                                singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                                exts="${properties['file.picture.type']}" id="expert_${vs.index}" maxcount="1"
                                groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8"
                                multiple="true" businessId="${t.id}" sysKey="${expertKey}"
                                typeId="9" auto="true"/>
                        <u:show showId="expert_${vs.index}"     businessId="${t.id}" sysKey="${expertKey}" typeId="9"/>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得执业资格时间</span>
                    <!--/职业资格时间  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input
                                <c:if test="${fn:contains(errorField,'取得执业资格时间')}">style="border: 1px solid #ef0000;"
                                onmouseover="errorMsg('取得执业资格时间')"</c:if>
                                value="<fmt:formatDate type='date' value="${t.titleTime}" dateStyle='default' pattern='yyyy-MM' />"
                                readonly="readonly" name="titles[${vs.index }].titleTime"    type="text"
                                onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> <span
                            class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li>
                
                <li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
						<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
							<input type="button" onclick="addPractice()" class="btn list_btn" value="十" />
							<input type="button" onclick="delPractice(this)" class="btn list_btn" value="一" />
								<input type="hidden" name="titles[${vs.index }].id" value="${t.id}" />
								<input type="hidden" name="titles[${vs.index }].expertId" value="${t.expertId}" />
						</div>
			  </li>
			</c:forEach>
			</ul>	
		 						
		</div>
	</div>
 
	
	
	
            <ul style="display:none" id="zyzg">
                <%-- <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i>专家技术资格</span> <!--/执业资格  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input
                                <c:if test="${fn:contains(errorField,'专家技术资格')}">style="border: 1px solid #ef0000;"
                                onmouseover="errorMsg('专家技术资格')"</c:if>
                                maxlength="20" value="${expert.professTechTitles}"
                                name="professTechTitles" id="professTechTitles" type="text"/>
                        <span class="add-on">i</span> <span class="input-tip">不能为空</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red"></i> 专家技术资格证书</span>
                    <div
                            class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0"
                            <c:if test="${fn:contains(errorField,'专家技术资格证书')}">style="border: 1px solid #ef0000;"
                            onmouseover="errorMsg('专家技术资格证书')"</c:if>>
                        <u:upload
                                singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                                exts="${properties['file.picture.type']}" id="expert4" maxcount="1"
                                groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8"
                                multiple="true" businessId="${sysId}" sysKey="${expertKey}"
                                typeId="4" auto="true"/>
                        <u:show showId="show4"
                                groups="show9,show2,show3,show4,show5,show6,show7,show8"
                                businessId="${sysId}" sysKey="${expertKey}"
                                typeId="4"/>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i>证书获取时间</span>
                    <!--/职业资格时间  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input
                                <c:if test="${fn:contains(errorField,'证书获取时间')}">style="border: 1px solid #ef0000;"
                                onmouseover="errorMsg('证书获取时间')"</c:if>
                                value="<fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle='default' pattern='yyyy-MM' />"
                                readonly="readonly" name="timeToWork" id="timeToWork"
                                type="text"
                                onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> <span
                            class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li> --%>


            </ul>
			
            <div class="btmfix">
                <div style="margin-top: 15px;text-align: center;">
                    <button class="btn" id="nextBind_" type="button" onclick='pre()'>上一步</button>
                    <button class="btn" onclick='zc()' type="button">暂存</button>
                    <button class="btn" id="nextBind" type="button" onclick='fun()'>下一步</button>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>

</html>