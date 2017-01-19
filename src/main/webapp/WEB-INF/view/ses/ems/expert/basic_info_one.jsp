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
			function func123() {
				var parentId = $("#addr").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_parent_id.do",
					data: {
						"id": parentId
					},
					dataType: "json",
					async: false,
					success: function(response) {
						$("#add").empty();
						$("#add").append("<option value=''>-请选择-</option>");
						$.each(response, function(i, result) {
							$("#add").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					}
				});
			}
			var parentId;
			var addressId = "${expert.address}";
			window.onload = function() {
				//地区回显和数据显示
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_id.do",
					data: {
						"id": addressId
					},
					async: false,
					dataType: "json",
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(addressId == result.id) {
								parentId = result.parentId;
								$("#add").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#add").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}
						});
					}
				});
				//地区
				$.ajax({
					url: "${pageContext.request.contextPath}/area/listByOne.do",
					async: false,
					dataType: "json",
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(parentId == result.id) {
								$("#addr").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#addr").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}
						});
					}
				});
			}
			//实时保存
			$(function() {
				$("input").bind("blur", submitformExpert);
				$("select").bind("change", submitformExpert);
			});

			function submitformExpert() {
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						//layer.msg("已暂存",{offset: ['300px', '750px']});
					}
				});
			}
			//无提示暂存
			function submitForm2() {
				updateStepNumber("two");
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: false,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			//无提示暂存
			function submitForm22() {
				updateStepNumber("two");
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			//无提示暂存
			function submitForm3() {
				updateStepNumber("three");
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			//无提示暂存
			function submitForm4() {
				updateStepNumber("four");
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			//无提示暂存
			function submitForm5() {
				updateStepNumber("five");
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			/** 专家完善注册信息页面 */
			function supplierRegist() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm2();
				}
			}
			/** 专家完善注册信息页面 */
			function supplierRegist2() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm22();
				}
			}
			/** 专家完善注册信息页面 */
			function supplierRegist3() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm3();
				}
			}
			/** 专家完善注册信息页面 */
			function supplierRegist4() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm4();
				}
			}
			/** 专家完善注册信息页面 */
			function supplierRegist5() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm5();
				}
			}

			//回显基本信息到表中
			function editTable() {
				var name = $("#relName").val();
				$("#tName").text(name);
				//性别
				var obj = document.getElementById("gender"); //selectid

				var index = obj.selectedIndex; // 选中索引

				var text = obj.options[index].text;
				$("#tSex").text(text);
				var birthday = $("#birthday").val();
				$("#tBirthday").text(birthday);
				//政治面貌
				var obj3 = document.getElementById("politicsStatus"); //selectid

				var index3 = obj3.selectedIndex; // 选中索引

				var tFace = obj3.options[index3].text;
				$("#tFace").text(tFace);
				var professTechTitles = $("#professTechTitles").val();
				$("#tHey").text(professTechTitles);
				var idNumber = $("#idNumber").val();
				$("#tNumber").text(idNumber);
				//最高学历
				var obj2 = document.getElementById("hightEducation"); //selectid

				var index2 = obj2.selectedIndex; // 选中索引

				var text2 = obj2.options[index2].text;

				$("#tHight").text(text2);
				var degree = $("#degree").val();
				$("#tWei").text(degree);
				var mobile = $("#mobile").val();
				$("#tMobile").text(mobile);
				var telephone = $("#telephone").val();
				$("#tTelephone").text(telephone);
				var workUnit = $("#workUnit").val();
				$("#tWorkUnit").text(workUnit);
				var graduateSchool = $("#graduateSchool").val();
				$("#tGraduateSchool").text(graduateSchool);
				var unitAddress = $("#unitAddress").val();
				$("#tUnitAddress").text(unitAddress);
				var postCode = $("#postCode").val();
				$("#tPostCode").text(postCode);
				var timeStartWork = $("#timeStartWork").val();
				$("#tTimeStartWork").text(timeStartWork);
				//父地区
				var add = document.getElementById("addr"); //selectid

				var addiIdex = add.selectedIndex; // 选中索引

				var addValue1 = add.options[addiIdex].text;
				//子地区
				var add2 = document.getElementById("add"); //selectid

				var addiIdex2 = add2.selectedIndex; // 选中索引

				var addValue2 = add2.options[addiIdex2].text;

				$("#Taddress").text(addValue1 + "," + addValue2);
			}

			// 点击下一步事件
			function fun() {
				supplierRegist();
				editTable();
			}
			// 点击2事件
			function fun2() {
				supplierRegist2();
				editTable();
			}
			// 点击3事件
			function fun3() {
				supplierRegist3();
				editTable();
			}
			// 点击4事件
			function fun4() {
				supplierRegist4();
				editTable();
			}
			// 点击5事件
			function fun5() {
				supplierRegist5();
				editTable();
			}

			//校验基本信息 不能为空的字段
			function validateformExpert() {
				var from = "${expert.expertsFrom}";
				var relName = $("#relName").val();
				if(!relName) {
					layer.msg("请输入姓名 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var gender = $("#gender").val();
				if(!gender) {
					layer.msg("请选择性别 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var birthday = $("#birthday").val();
				if(!birthday) {
					layer.msg("请填写出生日期 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var isAge = true;
				if(birthday != "") {
					$.ajax({
						url: "${pageContext.request.contextPath}/expert/validateAge.do",
						async: false,
						data: {
							"birthday": birthday
						},
						success: function(response) {
							if(response == "1") {
								layer.msg("年龄70周岁以下的才能进行注册!", {
									offset: ['300px', '750px']
								});
								isAge = false;
							} else {
								isAge = true;
							}
						}
					});
				}
				if(isAge == false) {
					return false;
				}
				var nation = $("#nation").val();
				if(!nation) {
					layer.msg("请填写民族 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var graduateSchool = $("#graduateSchool").val();
				if(!graduateSchool) {
					layer.msg("请填写毕业院校及专业 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var hightEducation = $("#hightEducation").val();
				if(!hightEducation) {
					layer.msg("请选择最高学历!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var degree = $("#degree").val();
				if(!degree) {
					layer.msg("请选择最高学位!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var major = $("#major").val();
				if(!major) {
					layer.msg("请填写从事专业!", {
						offset: ['300px', '750px']
					});
					return false;
				}

				var unitAddress = $("#unitAddress").val();
				if(!unitAddress) {
					layer.msg("请填写单位地址!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var telephone = $("#telephone").val();
				if(!telephone) {
					layer.msg("请填写固定电话!", {
						offset: ['300px', '750px']
					});
					return false;
				}

				if(telephone != "") {
					var reg = /^(\d{3,4}-{0,1})?\d{7,8}$/
					if(!reg.test(telephone)) {
						layer.msg("固定电话格式有误!", {
							offset: ['300px', '750px']
						});
						return false;
					}
				}

				var mobile = $("#mobile").val();
				if(!mobile) {
					layer.msg("请填写手机号!", {
						offset: ['300px', '750px']
					});
					return false;
				}

				var healthState = $("#healthState").val();
				if(!healthState) {
					layer.msg("请填写健康状态!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var email = $("#email").val();
				if(!email) {
					layer.msg("请填写个人邮箱!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
				if(email != "" && !emailReg.test(email)) {
					layer.msg("个人邮箱格式有误 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var fax = $("#fax").val();
				var faxReg = /^(\d{3,4}-{0,1})?\d{7,8}$/
				if(fax != "" && !faxReg.test(fax)) {
					layer.msg("传真电话格式有误 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var postCode = $("#postCode").val();
				if(idNumber != "" && isNaN(postCode)) {
					layer.msg("邮编格式只能输入数字 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(from == "ARMY") {
					var idType = $("#idType").val();
					if(!idType) {
						layer.msg("请选择军队人员身份证件类型 !", {
							offset: ['300px', '750px']
						});
						return false;
					}
					var idNumber = $("#idNumber").val();
					if(!idNumber) {
						layer.msg("请填写军队人员身份证件号码 !", {
							offset: ['300px', '750px']
						});
						return false;
					}
					// 军队人员身份证件号码唯一性验证
					if(idNumber != "") {
						var isok = 0;
						$.ajax({
							url: '${pageContext.request.contextPath}/expert/validateIdNumber.do',
							type: "post",
							async: false,
							data: {
								"idNumber": idNumber,
								"expertId": $("#id").val()
							},
							success: function(obj) {
								if(obj == '1') {
									layer.msg("该军队人员身份证件号码已被占用!", {
										offset: ['300px', '750px']
									});
									isok = 1;
								}
							}
						});
					}
					if(isok == 1) {
						return false;
					}
				}
				var professTechTitles = $("#professTechTitles").val();
				if(!professTechTitles) {
					layer.msg("请填写专家技术职称/职业资格!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var idCardNumber = $("#idCardNumber").val();
				if(!idCardNumber) {
					layer.msg("请填写居民身份证号码 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(idCardNumber != "") {
					var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(X|x)$)/
					if(!reg.test(idCardNumber)) {
						layer.msg("居民身份证号码格式有误 !", {
							offset: ['300px', '750px']
						});
						return false;
					}
					if(reg.test(idCardNumber) && idCardNumber.length == 18) {
						// 分别获取到身份证号码中的年月日并转换为数字格式
						var year = parseInt(idCardNumber.substring(6, 10));
						var month = parseInt(idCardNumber.substring(10, 12));
						var day = parseInt(idCardNumber.substring(12, 14));
						// 月份判断
						if(month < 1 || month > 12) {
							layer.msg("居民身份证号码格式有误 !", {
								offset: ['300px', '750px']
							});
							return false;
						}
						// 根据大小月判断日(大月)
						if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
							if(day < 1 || day > 31) {
								layer.msg("居民身份证号码格式有误 !", {
									offset: ['300px', '750px']
								});
								return false;
							}
						}
						// 根据大小月判断日(大月)
						if(month == 4 || month == 6 || month == 9 || month == 11) {
							if(day < 1 || day > 30) {
								layer.msg("居民身份证号码格式有误 !", {
									offset: ['300px', '750px']
								});
								return false;
							}
						}
						// 根据大小月判断日(二月)
						if(month == 2) {
							// 闰年
							if(year % 4 == 0 && year % 100 != 0 && year % 400 == 0) {
								if(day < 1 || day > 29) {
									layer.msg("居民身份证号码格式有误 !", {
										offset: ['300px', '750px']
									});
									return false;
								}
							}
							// 平年
							if(year % 4 != 0 || year % 400 != 0 || (year % 100 == 0 && year % 400 != 0)) {
								if(day < 1 || day > 28) {
									layer.msg("居民身份证号码格式有误 !", {
										offset: ['300px', '750px']
									});
									return false;
								}
							}
						}
					}
				}
				// 身份证唯一性验证
				if(idCardNumber != "") {
					var isok = 0;
					$.ajax({
						url: '${pageContext.request.contextPath}/expert/validateIdCardNumber.do',
						type: "post",
						async: false,
						data: {
							"idCardNumber": idCardNumber,
							"expertId": $("#id").val()
						},
						success: function(obj) {
							if(obj == '1') {
								layer.msg("该身份证号已被占用!", {
									offset: ['300px', '750px']
								});
								isok = 1;
							}
						}
					});
				}
				if(isok == 1) {
					return false;
				}
				var workUnit = $("#workUnit").val();
				if(!workUnit) {
					layer.msg("请填写所在单位 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(from == "LOCAL") {
					var coverNote = $("#coverNote").val();
					if(!coverNote) {
						layer.msg("请填写缴纳社会保险证明 !", {
							offset: ['300px', '750px']
						});
						return false;
					}
				}

				var id_areaSelect = $("#add").val();
				if(!id_areaSelect) {
					layer.msg("请选择区域 !", {
						offset: ['300px', '750px']
					});
					return false;
				}
				var sysId = $("#sysId").val();
				var flag;
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/findAttachment.do",
					data: {
						"sysId": sysId
					},
					cache: false,
					async: false,
					success: function(data) {
						if(data.length < 5) {
							layer.msg("还有未上传!", {
								offset: ['300px', '750px']
							});
							flag = false;
						} else {
							flag = true;
						}
					},
					dataType: "json"
				});
				return flag;
			}

			function tab3(typeId, depId) {
				if(typeId != "") {
					if(depId != "") {
						fun3();
					}
				}
			}

			function tab2(typeId) {
				if(typeId != "") {
					fun2();
				}
			}

			function tab4(typeId, depId) {
				if(typeId != "") {
					if(depId != "") {
						fun4();
					}
				}
			}

			function tab5(typeId, depId) {
				if(typeId != "") {
					if(depId != "") {
						fun5();
					}
				}
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
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					if(checklist1[i].checked) {
						ids.push(vals);
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					if(checklist2[i].checked) {
						ids.push(vals);
					}
				}
				$("#expertsTypeId").val(ids);
			}
			$(function() {
				var typeIds = "${expert.expertsTypeId}";
				var ids = typeIds.split(",");
				//回显
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist1[i].checked = true;
						}
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist2[i].checked = true;
						}
					}
				}
			});

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
							offset: '200px'
						});
					}
				});
			}

			function zc() {
				layer.msg("已暂存", {
					offset: ['300px', '750px']
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
			<input type="hidden" value="${errorMap.unitAddress}" id="error11">
			<input type="hidden" value="${errorMap.telephone}" id="error12">
			<input type="hidden" value="${errorMap.mobile}" id="error13">
			<input type="hidden" value="${errorMap.healthState}" id="error14">
			<input type="hidden" value="${errorMap.mobile2}" id="error15">
			<input type="hidden" value="${errorMap.idNumber2}" id="error16">
			<input type="hidden" id="categoryId" name="categoryId" value="" />
			<input type="hidden" id="expertsTypeId" name="expertsTypeId" value="" />
			<input type="hidden" name="token2" value="<%=tokenValue%>" />
			<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
				<h2 class="padding-20 mt40">
	    			<span id="sp1" class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
	    			<span id="sp2" class="new_step fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">经历经验</span> </span>
	    			<span id="sp7" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">专家类别</span> </span> 
	    			<span id="ty6" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">产品目录</span> </span>
	    			<span id="sp3" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
	    			<span id="sp4" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">文件下载</span> </span> 
	    			<span id="sp5" class="new_step fl"><i class="">7</i> <span class="step_desc_02">文件上传</span> </span> 
	    			<div class="clear"></div>
	  			</h2>
				<div class="container container_box">
					<h2 class="count_flow"><i>1</i>专家个人信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input id="relName" name="relName" value="${expert.relName}" type="text" <c:if test="${fn:contains(errorField,'专家姓名')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专家姓名')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 性别</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="gender" id="gender" <c:if test="${fn:contains(errorField,'性别')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('性别')"</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${sexList}" var="sex" varStatus="vs">
										<option <c:if test="${expert.gender eq sex.id}">selected="selected"</c:if> value="${sex.id}">${sex.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 出生日期</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'出生日期')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('出生日期')"</c:if> readonly="readonly" value="
								<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd' />" name="birthday" id="birthday" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',startDate:'1970-01-01'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，年龄不得大于70周岁</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 民族</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="10" value="${expert.nation}" name="nation" id="nation" type="text" <c:if test="${fn:contains(errorField,'民族')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('民族')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如： 汉族</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 省</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select id="addr" onchange="func123()" <c:if test="${fn:contains(errorField,'省')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('省')"</c:if>>
									<option value="">-请选择-</option>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 市</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="address" id="add" <c:if test="${fn:contains(errorField,'市')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('市')"</c:if>>
									<option value="">-请选择-</option>
								</select>
							</div>
						</li>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 缴纳社会保险证明</span>
								<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
									<input maxlength="30" value="${expert.coverNote}" name="coverNote" id="coverNote" type="text" <c:if test="${fn:contains(errorField,'缴纳社会保险证明')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('缴纳社会保险证明')"</c:if>/>
						<span class="add-on">i</span>
						</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 社保证明</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'社保证明')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('社保证明')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true" />
								<u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" />
							</div>
						</li>
						</c:if>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证号码</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="30" value="${expert.idCardNumber}" name="idCardNumber" id="idCardNumber" type="text" <c:if test="${fn:contains(errorField,'居民身份证号码')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('居民身份证号码')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，长度为15位或者18位</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'居民身份证')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('居民身份证')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert8" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDCARDNUMBER_TYPEID}" auto="true" />
								<u:show showId="show8" groups="show1,show2,show3,show4,show5,show6,show7,show8,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDCARDNUMBER_TYPEID}" />
							</div>
						</li>
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件类型</span>
								<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
									<select name="idType" id="idType" <c:if test="${fn:contains(errorField,'军队人员身份证件类型')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('军队人员身份证件类型')"</c:if>>
						<option selected="selected" value="">-请选择-</option>
						<c:forEach items="${idTypeList}" var="idType" varStatus="vs">
							<option <c:if test="${expert.idType eq idType.id}">selected="selected"</c:if> value="${idType.id}">${idType.name}</option>
						</c:forEach>
						</select>
						</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件号码</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="30" value="${expert.idNumber}" name="idNumber" id="idNumber" type="text" <c:if test="${fn:contains(errorField,'军队人员身份证件号码')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('军队人员身份证件号码')"</c:if>/>
								<c:if test="${fn:contains(errorField,'军队人员身份证件号码')}">
									<span class="add-on" style="color: red; border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span>
								</c:if>
								<c:if test="${!fn:contains(errorField,'军队人员身份证件号码')}">
									<span class="add-on">i</span>
									<span class="input-tip">不能为空，且不得重复使用</span>
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'军队人员身份证件')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('军队人员身份证件')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" auto="true" />
								<u:show showId="show1" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" />
							</div>
						</li>
						</c:if>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 健康状态</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="10" value="${expert.healthState}" name="healthState" id="healthState" type="text" <c:if test="${fn:contains(errorField,'健康状态')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('健康状态')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如：良好</span>

							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 手机</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="15" value="${user.mobile}" readonly="readonly" name="mobile" id="mobile" type="text" <c:if test="${fn:contains(errorField,'手机')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('手机')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">手机号码暂不支持修改</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 固定电话</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="15" value="${expert.telephone}" name="telephone" id="telephone" type="text" <c:if test="${fn:contains(errorField,'固定电话')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('固定电话')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如: XXX - XXXXXXX</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 个人邮箱</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="30" value="${expert.email}" name="email" id="email" type="text" <c:if test="${fn:contains(errorField,'个人邮箱')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('个人邮箱')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如：XXXX@XX.com</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 政治面貌</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="politicsStatus" id="politicsStatus" <c:if test="${fn:contains(errorField,'政治面貌')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('政治面貌')"</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${zzList}" var="zz" varStatus="vs">
										<option <c:if test="${expert.politicsStatus eq zz.id}">selected="selected"</c:if> value="${zz.id}">${zz.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input value="${expert.fax}" name="fax" id="fax" type="text" <c:if test="${fn:contains(errorField,'传真电话')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('传真电话')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如: XXX - XXXXXXX</span>
							</div>
						</li>
					</ul>
					<!-- 专家学历信息 -->
					<h2 class="count_flow"><i>2</i>专家学历信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 毕业院校及专业</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="40" value="${expert.graduateSchool}" name="graduateSchool" id="graduateSchool" type="text" <c:if test="${fn:contains(errorField,'毕业院校及专业')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('毕业院校及专业')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如：XXX大学XXX专业</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 毕业证书</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'毕业证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('毕业证书')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true" />
								<u:show showId="show2" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" />
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 最高学历</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="hightEducation" id="hightEducation" <c:if test="${fn:contains(errorField,'最高学历')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('最高学历')"</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${xlList}" var="xl" varStatus="vs">
										<option <c:if test="${expert.hightEducation eq xl.id}">selected="selected"</c:if> value="${xl.id}">${xl.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 最高学位</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="degree" id="degree" <c:if test="${fn:contains(errorField,'最高学位')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('最高学位')"</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${xwList}" var="xw" varStatus="vs">
										<option <c:if test="${expert.degree eq xw.id}">selected="selected"</c:if> value="${xw.id}">${xw.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 学位证书</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'学位证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('学位证书')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true" />
								<u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" />
							</div>
						</li>
					</ul>
					<!-- 专家专业信息 -->
					<h2 class="count_flow"><i>3</i>专家专业信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 所在单位</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="40" value="${expert.workUnit}" name="workUnit" id="workUnit" type="text" <c:if test="${fn:contains(errorField,'所在单位')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('所在单位')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 单位地址</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="40" value="${expert.unitAddress}" name="unitAddress" id="unitAddress" type="text" <c:if test="${fn:contains(errorField,'单位地址')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('单位地址')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="6" value="${expert.postCode}" name="postCode" id="postCode" type="text" <c:if test="${fn:contains(errorField,'单位邮编')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('单位邮编')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">长度为6位</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="10" value="${expert.atDuty}" name="atDuty" id="appendedInput" type="text" <c:if test="${fn:contains(errorField,'现任职务')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('现任职务')"</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如：项目经理</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 从事专业</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="20" value="${expert.major}" name="major" id="major" type="text" <c:if test="${fn:contains(errorField,'从事专业,')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('从事专业')"</c:if> />
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'从事专业起始年度')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('从事专业起始年度')"</c:if> value="
								<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM' />" readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">如：XXXX-XX</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>专家技术职称/执业资格</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'专家技术职称/执业资格')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专家技术职称/执业资格')"</c:if> maxlength="20" value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text"/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 技术职称/执业资格证书</span>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'技术职称/执业资格证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('技术职称/执业资格证书')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true" />
								<u:show showId="show3" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" />
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 取得技术职称时间</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'取得技术职称时间')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('取得技术职称时间')"</c:if> value="
								<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM' />" readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">如：XXXX-XX</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'取得技术职称时间')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('取得技术职称时间')"</c:if> readonly="readonly" value="
								<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM' />" name="timeToWork" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">如：XXXX-XX</span>
							</div>
						</li>
					</ul>
					<!-- 专家专业信息 -->
					<div class="btmfix">
						<div style="margin-top: 15px;text-align: center;">
							<button class="btn" type="button" onclick='zc()'>暂存</button>
							<button class="btn" id="nextBind" type="button" onclick='fun()'>下一步</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
	</body>

</html>