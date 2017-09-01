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
        	var expertType=false;
        	$("input[type='checkbox']:checked").each(function(){
        		  if($(this).next().val()=="true"){
        			 expertType=true;
        		 };  
        	});
        	if(expertType){
        		layer.msg("请取消被退回的专家类型 !");
        		return;
        	}
           	var flag=false;
        	var bool=false;
        	var val=$("#mySelect").val();
        	var s=true;
        	var s2=true;
            var _expertId = $("#id").val();
            var _expertsTypeId = "";
       	 $("input[name='chkItem_2']").each(function() {
       		var val=$(this).parent().text();
         	if(val.trim()=="工程经济"){	
         	 if ($(this).prop("checked")) {
         		  flag=true;
         			// $("#pro_div").show();
                 _expertsTypeId += $(this).val()+",";
         	    }else{
         	    	flag=false;
         	   	// $("#pro_div").hide();
         	    }
         	 }
       	 });
        	
        $("input[name='chkItem_1']").each(function() {
            var val=$(this).parent().text();
            if(val.trim()=="工程技术"){
                if ($(this).prop("checked")) {
                    bool=true;
                    _expertsTypeId += $(this).val()+",";
                }else{
                    bool=false;
                }
            }
         });
    	 
    	 if(flag==true&&val==1){
    		 $("#pro_div").find("input[type='text']").each(
                function(index, element) {
                    if (element.value.trim() == "") {
                        // flag = false;
                         s=false;
                    }
 						 
 			 }); 
    	 }
    	 if(bool==true&&val==1){
    		 $("#server_div").find("input[type='text']").each(
                function(index, element) {
                    if (element.value.trim() == "") {
                        // bool = false;
                        /*  layer.msg("请完善执业资格信息 !");
                         return; */
                         s2=false;
                    }
 						 
 			 }); 
    	 }
   	   if(s==false||s2==false){
    		  layer.msg("请完善执业资格信息 !");
    		 return;
       }
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
                    //校验执照图片是否上传
                    $.ajax({
                        url: "${pageContext.request.contextPath}/expert/isUpload.do",
                        data: {
                            "expertId": _expertId,
                            "typeId":_expertsTypeId
                        },
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            if(data=="1"){
                                submitForm2();
                            }else{
                                layer.msg("请完善执业资格信息 !");
                                return;
                            }
                        }
                    });
                    /* var boo=isVal();
                    if(boo==true){ */

                    /* }else{
                         layer.msg("请完善执业资格信息!");
                    } */

                }

            }

        }else{
            if (!validateType()) {
                return;
            } else{
                //校验执照图片是否上传
                $.ajax({
                    url: "${pageContext.request.contextPath}/expert/isUpload.do",
                    data: {
                        "expertId": _expertId,
                        "typeId":_expertsTypeId
                    },
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if(data=="1"){
                            submitForm2();
                        }else{
                            layer.msg("请完善执业资格信息 !");
                            return;
                        }
                    }
                });
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

        	var isTitle="${expert.isTitle}";
        	var flag=false;
        	var bool=false;
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
            		 flag=true;
                     init_web_upload();
            
                     $("#zhiyezige").show();
                  	// $("#server_div").attr("class", "tab-pane fades active in");
                  	// $("#server_div").show();
                  	$("input[name='chkItem_2']").each(function() {
                		var val=$(this).parent().text();
                    	if(val.trim()=="工程经济"){	
                    		// $(this).prop("disabled",true);
                    	}
            		});
                  	
                 }  
            	}
    		});
        	
        	$("input[name='chkItem_2']:checked").each(function() {
        		var val=$(this).parent().text();
        		
            	if(val.trim()=="工程经济"){	
            	
            	 if ($(this).prop("checked")) {
            		 bool=true;
                     init_web_upload();
                     $("#zhiyezige").show();
                       // $("#pro_div").attr("class", "tab-pane fades active in");
                    	// $("#pro_div").show();
                       $("input[name='chkItem_1']").each(function() {
                   		var val=$(this).parent().text();
                       	if(val.trim()=="工程技术"){	
                       		//$(this).prop("disabled",true);
                       	}
               		});
                       
                 } 
            	}
    		});
       	 if(isTitle==1&&flag==true){
    		 $("#server_div").show();
    	 }
    
    	 if(isTitle==1&&bool==true){
    		 $("#pro_div").show();
    	 }
    	 // 选中专家类别
       checkZjlb();	
        	
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

        function errorFileMsg(auditFieldName,id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
                data: {
                    "expertId": $("#id").val(),
                    "auditFieldName": auditFieldName,
                    "auditFieldId": id
                },
                dataType: "json",
                success: function (response) {
                    layer.msg("不通过理由:" + response.auditReason);
                }
            });
        }
				
				function errorMsg(auditFieldId) {
            $.ajax({
                url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
                data: {
                    "expertId": $("#id").val(),
                    "auditFieldId": auditFieldId,
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
        	 var expertId=$("#id").val();
        	$("input[name='chkItem_1']").each(function() {
        		var val=$(this).parent().text();
            	if(val.trim()=="工程技术"){	
            	 if ($(this).prop("checked")) {
            		 flagValue="工程技术";
                     init_web_upload();
                     $("#zhiyezige").show();
                   //    $("#server_div").show();
            		$("input[name='chkItem_2']").each(function() {
                		var val=$(this).parent().text();
                    	if(val.trim()=="工程经济"){	
                    		$(this).prop("disabled",true);
                    	}
            		});
            		
                 }else {
                	 $("#zhiyezige").hide();
                	 cacel(expertId,$(this).val());
                	 $("#mySelect option:first").prop("selected", 'selected');
                	 flag=false;
                		 $("#server_div").hide();
                		$("input[name='chkItem_2']").each(function() {
                    		var val=$(this).parent().text();
                        	if(val.trim()=="工程经济"){	
                        		$(this).prop("disabled",false);
                        	}
                		});
                 }
            	}
    		});
        	
        	
        	 if(flag==false){
        		 $("input[name='chkItem_2']").each(function() {
             		var val=$(this).parent().text();
                 	if(val.trim()=="工程经济"){	
                 	 if ($(this).prop("checked")) {
                 		 $("#zhiyezige").show();
                 		  flagValue="工程经济";
                          init_web_upload();
                      	// $("#pro_div").show();
                      	$("input[name='chkItem_1']").each(function() {
                    		var val=$(this).parent().text();
                        	if(val.trim()=="工程技术"){	
                        		
                        		// $(this).prop("disabled",true);
                        	}
                		});
                      	
                      } else {
                    	  $("#zhiyezige").hide();
                    	  cacel(expertId,$(this).val());
                    	  $("#mySelect option:first").prop("selected", 'selected');
                    	  
                    	 	$("#pro_div").hide();
                    		$("input[name='chkItem_1']").each(function() {
                        		var val=$(this).parent().text();
                            	if(val.trim()=="工程技术"){	
                            		$(this).prop("disabled",false);
                            	}
                    		});
                    		
                      }
                 	}
         		}); 
        	 }
        	 checkZjlb();
        }
        
       	// 选中专家类别
				function checkZjlb() {
					$("input[name='chkItem_1']").each(function() {
						if ($(this).prop("checked")) {
							$("input[name='chkItem_2']").prop("disabled", true);
						}
					});
					$("input[name='chkItem_2']").each(function() {
						if ($(this).prop("checked")) {
							$("input[name='chkItem_1']").prop("disabled", true);
						}
					});
					var notCheckedCount = 0;// 没有选中的checkbox
					var allCheckboxCount = 0;// 所有的checkbox
					$("input[name='chkItem_1'],input[name='chkItem_2']").each(function() {
						allCheckboxCount++;
						if (!$(this).prop("checked")) {
							notCheckedCount++;
						}
					});
					if (notCheckedCount == allCheckboxCount) {
						$("input[name='chkItem_1'],input[name='chkItem_2']").prop(
								"disabled", false);
					}
				}
        
        function  isZhiye(obj){
        	submitformExpert();
        	var val=$(obj).val();
        	var flag=false;
        	var bool=false;
       	 $("input[name='chkItem_2']").each(function() {
       		var val=$(this).parent().text();
         	if(val.trim()=="工程经济"){	
         	 if ($(this).prop("checked")) {
         		  flag=true;
         			// $("#pro_div").show();
         	    }else{
         	    	flag=false;
         	   	// $("#pro_div").hide();
         	    }
         	 }
       	 });
        	
    	 $("input[name='chkItem_1']").each(function() {
        		var val=$(this).parent().text();
          	if(val.trim()=="工程技术"){	
          	 if ($(this).prop("checked")) {
          		 bool=true;
          		// $("#server_div").show();
          	    }else{
          	    	bool=false;
          	   // 	$("#server_div").hide();
          	    }
          	 }
        	 });
    
    	 if(val==1&&flag==true&&bool==true){
    		 init_web_upload_in("#server_div");
    		 $("#server_div").show();
    	 }else{
    		 if(val==1&&flag==true){
        		 init_web_upload_in("#pro_div");
        		 $("#pro_div").show();
        	 }
        	 if(val==2){
        		 $("#pro_div").hide();
        			$("#server_div").hide();
        	 }
        	 if(val==1&&bool==true){
        		 init_web_upload_in("#server_div");
        		 $("#server_div").show();
        	 }
    	 }
    	 
    	
     
    	 
        }
        
        
        function addPractice(val){
        	// var detailRow = $("#server_div").find("li");
       	
			  var index = 1;
			var proIndex =$("#proIndex").val();
			 
			var ecoIndex =$("#ecoIndex").val();
			 
			
			 if(val=="2"){
				 
				  index =ecoIndex;
			}else{
				index =proIndex;;
			}  
			
			var id=$("#id").val();
			$.ajax({
				url: "${pageContext.request.contextPath}/expert/practice.do",
				type: "post",
				data:{"index":index,"expertId":id,"type":val},
				success: function(data) {
					if(val==2){
						ecoIndex++;
						$("#ecoIndex").val(ecoIndex);
						$("#jingji_ul").append(data);
						
					}else{
						proIndex++;
						$("#proIndex").val(proIndex);
						$("#addUl").append(data);
					}
					init_web_upload();
				}
			});
			
        }
        
        function delPractice(obj){
        	var bool1 = false;// 工程技术是否选择
        	var bool2 = false;// 工程经济是否选择
        	var zyzgCount1 = 0;// 工程技术执业资格数量统计
        	var zyzgCount2 = 0;// 工程经济执业资格数量统计
        	zyzgCount1 = $("#server_div input[name$='qualifcationTitle']").length;
        	zyzgCount2 = $("#pro_div input[name$='qualifcationTitle']").length;
        	
    	 		$("input[name='chkItem_1']").each(function() {
        		var val=$(this).parent().text();
          	if(val.trim()=="工程技术"){	
          		if ($(this).prop("checked")) {
          			bool1 = true;
          	  } 
          	}
       	 	});
       	 	
       	 	$("input[name='chkItem_2']").each(function() {
       			var val=$(this).parent().text();
	         	if(val.trim()=="工程经济"){
	         		if ($(this).prop("checked")) {
	         		  bool2 = true;
	       	    }
	       	 	}
       	 	});
    	 
					if(zyzgCount1 == 1 && bool1 == true) {
						layer.msg("工程技术-执业资格信息至少保留一个!", {
							offset: '300px'
						});
					}  
					
					else if(zyzgCount2 == 1 && bool2 == true) {
						layer.msg("工程经济-执业资格信息至少保留一个!", {
							offset: '300px'
						});
					}
					
					else{
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
			 		}
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
        
        function cacel(expterId,expertTypeId){
        	 $.ajax({
                 url: "${pageContext.request.contextPath}/expert/deleteExperType.do",
                 data: {"expertId":expterId,"expertTypeId":expertTypeId},
                 type: "post",
                 async: true,
                 success: function (result) {
                     //layer.msg("已暂存",{offset: ['300px', '750px']});
                 }
             });
        }
 
        
        
        function uploaTitleFile(){
        	 var expertId=$("#id").val();
        	 
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
        <h2 class="step_flow">
            <span id="sp1" class="new_step current fl" onclick="pre1()"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
            <span id="sp7" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类别</span> </span>
            <span id="ty6" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
            <span id="sp3" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
            <span id="sp4" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
            <span id="sp5" class="new_step fl new_step_last"><i class="">6</i><span class="step_desc_01">提交审核</span> </span>
            <div class="clear"></div>
        </h2>
        <div class="container container_box">
            <h2 class="list_title">专家类别</h2>
            <!-- 专家专业信息 -->
            <ul class="ul_list">
                <li class="col-md-12 col-sm-12 col-xs-12 pl10">
                    <div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
                        <c:forEach items="${spList}" var="sp">
                            <span  <c:if test="${fn:contains(typeErrorField,sp.id)}">style="color: #ef0000;"  onmouseover="errorMsg('${sp.id}')"</c:if>  class="margin-left-30">
                            <input  type="checkbox"  onclick="checks(this)" name="chkItem_1" value="${sp.id}"/>${sp.name}技术 
                            <input type="hidden" value="${fn:contains(typeErrorField,sp.id)}">
                            </span>
                        </c:forEach>
                        <c:forEach items="${jjList}" var="jj">
                            <span <c:if test="${fn:contains(typeErrorField,jj.id)}">style="color: #ef0000;"  onmouseover="errorMsg('${jj.id}')"</c:if> class="margin-left-30">
                            <input onclick="checks(this)"  type="checkbox" name="chkItem_2" value="${jj.id}"/>${jj.name} 
                            <input type="hidden" value="${fn:contains(typeErrorField,sp.id)}">
                            </span>
                        </c:forEach>
                    </div>
                </li>
            </ul>

 			<div class="ul_list clear">
 				<ul class="list-unstyled">
						<li  id="zhiyezige" style="display: none;" class="col-md-3 col-sm-6 col-xs-12 pl10">
						<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>有无执业资格</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<select class="w100p" name="isTitle" id="mySelect" onchange="isZhiye(this)" <c:if test="${fn:contains(typeErrorField,'isTitle')}">style="border: 1px solid red;"  onmouseover="errorMsg('isTitle')"</c:if>>
									<option  value="2" <c:if test="${expert.isTitle==2}">selected="selected"</c:if> >无 </option>
									<option  value="1" <c:if test="${expert.isTitle==1}">selected="selected"</c:if> >有 </option>
								</select>
							</div>
						</li>
				</ul>
				
				<div style="display: none;" id="server_div" class="clear">
					<ul class="list-unstyled f14" id="addUl">
						<c:forEach items="${proList }" var="t"  varStatus="vs" >
							<li class="col-md-3 col-sm-6 col-xs-12 pl10">
								<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>执业资格职称</span> <!--/执业资格  -->
   			        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
            				<input  <c:if test="${fn:contains(engErrorField,t.id.concat('_qualifcationTitle'))}">style="border: 1px solid #ef0000;" onmouseover="errorFileMsg('qualifcationTitle','${t.id }')"</c:if>
                     maxlength="20" value="${t.qualifcationTitle}"
                     name="titles[${vs.index }].qualifcationTitle"  type="text"/>
          				 	<span class="add-on">i</span> <span class="input-tip">不能为空</span>
           			</div>
     				  </li>
							<li class="col-md-3 col-sm-6 col-xs-12">
	     				  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>执业资格</span>
	       				<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(engErrorField,t.id.concat('_tieleFile'))}">style="border: 1px solid #ef0000;" onmouseover="errorFileMsg('tieleFile','${t.id }')"</c:if>>
	         		    <c:choose>
										<c:when test="${expert.status == 3 and !fn:contains(engErrorField,t.id.concat('_tieleFile'))}">
											<u:show showId="pro_${vs.index}" delete="false" businessId="${t.id}" sysKey="${expertKey}" typeId="9"/>
										</c:when>
										<c:otherwise>
											<u:upload
                        singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                        exts="${properties['file.picture.type']}" id="pro_${vs.index}"
                        groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8"
                        multiple="true" businessId="${t.id}" sysKey="${expertKey}"
                        typeId="9" auto="true" maxcount="20"/>
           	   				<u:show showId="pro_${vs.index}" businessId="${t.id}" sysKey="${expertKey}" typeId="9"/>
										</c:otherwise>
									</c:choose>
               	</div>
               </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>取得执业资格时间</span>
                    <!--/职业资格时间  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input
                                <c:if test="${fn:contains(engErrorField,t.id.concat('_titleTime'))}">style="border: 1px solid #ef0000;"
                                onmouseover="errorFileMsg('titleTime','${t.id }')"</c:if>
                                value="<fmt:formatDate type='date' value="${t.titleTime}" dateStyle='default' pattern='yyyy-MM' />"
                                readonly="readonly" name="titles[${vs.index }].titleTime"    type="text"
                                onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> <span
                            class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li>
                
        <li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
						<c:if test="${expert.status != 3}">
							<input type="button" onclick="addPractice(1)" class="btn list_btn" value="十" />
						</c:if>
						<input type="button" onclick="delPractice(this)" class="btn list_btn" value="一" />
						<input type="hidden" name="titles[${vs.index }].id" value="${t.id}" />
						<input type="hidden" name="titles[${vs.index }].expertId" value="${t.expertId}" />
					</div>
			  </li>
			</c:forEach>
			</ul>	
		 </div>	
		 			
	 <div style="display: none;" id="pro_div">
		<ul class="list-unstyled f14 clear" id="jingji_ul">
		<c:forEach items="${ecoList}" var="t"  varStatus="vs" >
		<li class="col-md-3 col-sm-6 col-xs-12 pl10">
			<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>执业资格职称</span> <!--/执业资格  -->
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input  <c:if test="${fn:contains(engErrorField,t.id.concat('_qualifcationTitle'))}">style="border: 1px solid #ef0000;" onmouseover="errorFileMsg('qualifcationTitle','${t.id }')"</c:if>
                                maxlength="20" value="${t.qualifcationTitle}"
                                name="ecoList[${vs.index }].qualifcationTitle"  type="text"/>
                        <span class="add-on">i</span> <span class="input-tip">不能为空</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>执业资格</span>
                    <div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(engErrorField,t.id.concat('_tieleFile'))}">style="border: 1px solid #ef0000;" onmouseover="errorFileMsg('tieleFile','${t.id }')"</c:if>>
                      <c:choose>
												<c:when test="${expert.status == 3 and !fn:contains(engErrorField,t.id.concat('_tieleFile'))}">
													<u:show showId="pro_${vs.index}" delete="false" businessId="${t.id}" sysKey="${expertKey}" typeId="9"/>
												</c:when>
												<c:otherwise>
													<u:upload
                            singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                            exts="${properties['file.picture.type']}" id="eco_${vs.index}"
                            groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8"
                            multiple="true" businessId="${t.id}" sysKey="${expertKey}"
                            typeId="9" auto="true" maxcount="20"/>
                        	<u:show showId="eco_${vs.index}" businessId="${t.id}" sysKey="${expertKey}" typeId="9"/>
												</c:otherwise>
											</c:choose>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>取得执业资格时间</span>
                    <!--/职业资格时间  -->
                    <div  class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input
                                <c:if test="${fn:contains(engErrorField,t.id.concat('_titleTime'))}">style="border: 1px solid #ef0000;"
                                onmouseover="errorFileMsg('titleTime','${t.id }')"</c:if>
                                value="<fmt:formatDate type='date' value="${t.titleTime}" dateStyle='default' pattern='yyyy-MM' />"
                                readonly="readonly" name="ecoList[${vs.index }].titleTime"    type="text"
                                onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> <span
                            class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li>
                
                <li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
						<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
							<c:if test="${expert.status != 3}">
								<input type="button" onclick="addPractice(2)" class="btn list_btn" value="十" />
							    <input type="button" onclick="delPractice(this)" class="btn list_btn" value="一" />
							</c:if>
							
							<input type="hidden" name="ecoList[${vs.index }].id" value="${t.id}" />
							<input type="hidden" name="ecoList[${vs.index }].expertId" value="${t.expertId}" />
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
                                <c:if test="${fn:contains(typeErrorField,'专家技术资格')}">style="border: 1px solid #ef0000;"
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
                            <c:if test="${fn:contains(typeErrorField,'专家技术资格证书')}">style="border: 1px solid #ef0000;"
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
                                <c:if test="${fn:contains(typeErrorField,'证书获取时间')}">style="border: 1px solid #ef0000;"
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
                <div class="mt20 tc w100p fl">
                    <button class="btn" id="nextBind_" type="button" onclick='pre()'>上一步</button>
                    <button class="btn" onclick='zc()' type="button">暂存</button>
                    <button class="btn" id="nextBind" type="button" onclick='fun()'>下一步</button>
                </div>
            </div>
        </div>
    </div>
</form>
<input type="hidden" id="proIndex" value="1">
<input type="hidden" id="ecoIndex" value="1">
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
<script type="text/javascript">
		// 如果专家状态是退回修改，控制表单域的编辑与不可编辑
		var expertSt = '${expert.status}';
		if(expertSt == '3'){
			$("input[type='text'],select,textarea").attr('disabled',true);
			$("input[type='text'],select,textarea").each(function(){
				// 或者$(this).attr("style").indexOf("border: 1px solid #ef0000;") > 0
				// 或者$(this).css("border") == '1px solid rgb(239, 0, 0)'
				if(this.style.border == '1px solid rgb(239, 0, 0)'){
					$(this).attr('disabled',false);
				}
			});
			// 控制5大类别的编辑性
			$("input[type='checkbox'][name='chkItem_1']").attr('disabled',true);
			$("input[type='checkbox'][name='chkItem_2']").attr('disabled',true);
			$("input[type='checkbox'][name='chkItem_1']").each(function(){
				/* if($(this).parent().css("color") == 'rgb(239, 0, 0)'){
					$(this).attr('disabled',false);
				} */
				// 或者
				var typeErrorField = '${typeErrorField}';
				if(typeErrorField.indexOf($(this).val()) >= 0){
					$(this).attr('disabled',false);
					var thisText = $(this).parent().text().trim();
					if(thisText == "工程技术"){
						//控制有无执业资格下拉的可选与否
						//$("#mySelect").attr('disabled',false);
					}
				}
			});
			$("input[type='checkbox'][name='chkItem_2']").each(function(){
				/* if($(this).parent().css("color") == 'rgb(239, 0, 0)'){
					$(this).attr('disabled',false);
				} */
				// 或者
				var typeErrorField = '${typeErrorField}';
				if(typeErrorField.indexOf($(this).val()) >= 0){
					$(this).attr('disabled',false);
					var thisText = $(this).parent().text().trim();
					if(thisText == "工程经济"){
						//控制有无执业资格下拉的可选与否
						//$("#mySelect").attr('disabled',false);
					}
				}
			});
		}
</script>
</html>