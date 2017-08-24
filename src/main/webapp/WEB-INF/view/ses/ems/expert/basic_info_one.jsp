<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
		<%-- <jsp:include page="/WEB-INF/view/common/webupload.jsp"/> --%>
		<title>评审专家注册</title>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
		<%
        //表单标示
        String tokenValue = new Date().getTime() + UUID.randomUUID().toString() + "";
        session.setAttribute("tokenSession", tokenValue);

    %>
		<%@ include file="/WEB-INF/view/common/validate.jsp"%>
		<script type="text/javascript">
			$().ready(function() {
				$("#formExpert").validForm();
			});

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
				$("textarea").bind("blur", submitformExpert);
			});
			//校验军官证号
            function checkCardNumber(){
                var idNumber = $("#idNumber").val();
                if(!idNumber) {
                    layer.msg("请填写证件号码 !");
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
                                layer.msg("该证件号码已被占用!");
                                isok = 1;
                            }
                        }
                        })
                        }
            }
			function submitformExpert() {
				var idCard = $("#idCardNumber").val().trim();
				// 身份证唯一性验证
				if(idCard != "") {
					//15位和18位身份证号码的正则表达式
				    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
				    //如果通过该验证，说明身份证格式正确，但准确性还需计算
				    if (regIdCard.test(idCard)) {
				        if (idCard.length == 18) {
				            var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
				            var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
				            var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
				            for (var i = 0; i < 17; i++) {
				                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
				            }

				            var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
				            var idCardLast = idCard.substring(17);//得到最后一位身份证号码

				            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
				            if (idCardMod == 2) {
				                if (idCardLast == "X" || idCardLast == "x") {
				                    //return "success";
				                } else {
				                	 layer.msg('身份证号码错误！', {
                                         offset: '300px'
                                     });
                                    return false;
				                }
				            } else {
				                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
				                if (idCardLast == idCardY[idCardMod]) {
				                    //return "success";
				                } else {
				                    layer.msg('身份证号码错误！', {
		                                 offset: '300px'
		                             });
		                            return false;
				                }
				            }
				        } else if (idCard.length > 0 && idCard.length < 18) {
				            layer.msg('请输入正确的身份证号！', {
	                             offset: '300px'
	                         });
	                        return false;
				        } else if (idCard.length == 0) {
				           // return "success";
				        } else {
				            //15
				            //return "success";
				        }
				    } else if (idCard.trim().length==0){
				       // return "success";
				    }else {
				    	 layer.msg('身份证格式不正确!', {
                             offset: '300px'
                         });
				        return false;
				    }
                    //校验专家表身份证
                    $.ajax({
                        url: '${pageContext.request.contextPath}/expert/validateIdCardNumber.do',
                        type: "post",
                        async: false,
                        data: {
                            "idCardNumber": idCard,
                            "expertId": $("#id").val()
                        },
                        success: function(obj) {
                            if(obj == '1') {
                            	 layer.msg('该身份证号码已经被注册，请重新填写！', {
                                     offset: '300px'
                                 });
                                return false;
                            }
                            
                            /* else if(obj == 'disabled_180'){
                                layer.msg('该身份证号码在180天内禁止再次注册，请重新填写！', {
                                    offset: '300px'
                                });
                                return false;
                            } */
                            
                            else{
                                validateIdCard();
                            }
                        }
                    });

				} else {
					validateIdCard();
				}

			}

			function validateIdCard() {
				//父地区
				var add = document.getElementById("addr"); //selectid
				var addiIdex = add.selectedIndex; // 选中索引

				var addValue1 = add.options[addiIdex].text;
				//子地区
				var add2 = document.getElementById("add"); //selectid

				var addiIdex2 = add2.selectedIndex; // 选中索引

				var addValue2 = add2.options[addiIdex2].text;

				$("#range").val(addValue1 + "," + addValue2);
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
				updateStepNumber("seven");
				getChildren();
				$("input[type='text'],select,textarea").attr('disabled',false);
				var fromList=$("#formExpert").serialize()
				if('${expert.status}'==3){
					$("input[type='text'],select,textarea").attr('disabled',true);
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: fromList,
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
				$("input[type='text'],select,textarea").attr('disabled',false);
                var fromList=$("#formExpert").serialize()
                if('${expert.status}'==3){
                    $("input[type='text'],select,textarea").attr('disabled',true);
                }
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: fromList,
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
				$("input[type='text'],select,textarea").attr('disabled',false);
                var fromList=$("#formExpert").serialize()
                if('${expert.status}'==3){
                    $("input[type='text'],select,textarea").attr('disabled',true);
                }
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: fromList,
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
				$("input[type='text'],select,textarea").attr('disabled',false);
                var fromList=$("#formExpert").serialize()
                if('${expert.status}'==3){
                    $("input[type='text'],select,textarea").attr('disabled',true);
                }
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: fromList,
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
				$("input[type='text'],select,textarea").attr('disabled',false);
                var fromList=$("#formExpert").serialize()
                if('${expert.status}'==3){
                    $("input[type='text'],select,textarea").attr('disabled',true);
                }
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: fromList,
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
			// 专家完善注册信息页面
			function supplierRegist3() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm3();
				}
			}
			// 专家完善注册信息页面
			function supplierRegist4() {
				if(!validateformExpert()) {
					return;
				} else {
					//暂存无提示
					submitForm4();
				}
			}
			// 专家完善注册信息页面
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
				//缴纳社会保险证明

				var obj_c = document.getElementById("coverNote"); //selectid
				var index_c = obj_c.selectedIndex; // 选中索引
				var text_c = obj_c.options[index_c].text;
				$("#tSex_c").text(text_c);

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

			// 点击下一步事件 yong
			function fun() {
				var idCardNumber = $("#idCardNumber").val().trim();
				// 身份证唯一性验证
				if(idCardNumber != "") {
					/*$.ajax({
						url: '${pageContext.request.contextPath}/user/ajaxIdNumber.do',
						type: "post",
						dataType: "json",
						data: {
							"idNumber": $("#idCardNumber").val().trim(),
							"id": $("#userId").val().trim()
						},
						success: function(obj) {
							if(obj.success == false) {
								layer.msg("居民身份证号码已被占用!");
								return false;
							} else {
								supplierRegist();
								editTable();
							}

						}
					});*/
                    //校验专家表身份证
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
                                layer.msg("居民身份证号码已被占用!");
                                return false;
                            }else if(obj == 'disabled_180'){
                                layer.msg('该身份证号码在180天内禁止再次注册，请重新填写！', {
                                    offset: '300px'
                                });
                                return false;
                            }else{
                                supplierRegist();
                                editTable();
                            }
                        }
                    });
				} else {
					supplierRegist();
					editTable();
				}

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
					layer.msg("请输入姓名 !");
					return false;
				}
				var gender = $("#gender").val().trim();
				if(!gender) {
					layer.msg("请选择性别 !");
					return false;
				}

				var politicsStatus = $("#politicsStatus").val();
				if(!politicsStatus) {
					layer.msg("请填写政治面貌 !");
					return false;
				}
				var birthday = $("#birthday").val();
				if(!birthday) {
					layer.msg("请填写出生日期 !");
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
								layer.msg("年龄70周岁以下的才能进行注册!");
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
					layer.msg("请填写民族 !");
					return false;
				}
				var healthState = $("#healthState").val().trim();
				if(!healthState) {
					layer.msg("请填写健康状态!");
					return false;
				}
				var mobile = $("#mobile").val();
				if(!mobile) {
					layer.msg("请填写手机号!");
					return false;
				}
				if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
					layer.msg("手机号格式不正确!");
                    return false;
				}
				var checkPhone=0;
				$.ajax({
					url: '${pageContext.request.contextPath}/expert/checkPhone.do',
					type: "post",
					async: false,
					data: {
						"phone": mobile,
						"id": $("#id").val()
					},
					success: function(obj) {
						if(obj == '1') {
							layer.msg("该手机号码已被使用!");
							checkPhone=1;
						}
					}
				});
				if(checkPhone==1){
					 return false;
				}
				var idCardNumber = $("#idCardNumber").val().trim();
				if(!idCardNumber) {
					layer.msg("请填写居民身份证号码 !");
					return false;
				}
				if(from == "ARMY") {
					var idType = $("#idType").val();
					if(!idType) {
						layer.msg("请选择军队人员身份证件类型 !");
						return false;
					}
					var idNumber = $("#idNumber").val();
					if(!idNumber) {
						layer.msg("请填写证件号码 !");
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
									layer.msg("该证件号码已被占用!");
									isok = 1;
								}else if(obj == 'disabled_180'){
                  layer.msg('该身份证号码在180天内禁止再次注册，请重新填写！', {
                      offset: '300px'
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

				
				var telephone = $("#telephone").val().trim();
				if(!telephone) {
					layer.msg("请填写固定电话!");
					return false;
				}
				if(telephone != "" && telephone.length > 20) {
					layer.msg("固定电话格式有误!");
					return false;
				}
				var email = $("#email").val().trim();
				if(!email) {
					layer.msg("请填写个人邮箱!");
					return false;
				}
				var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
				if(email != "" && !emailReg.test(email)) {
					layer.msg("个人邮箱格式有误 !");
					return false;
				}
				var workUnit = $("#workUnit").val();
				if(!workUnit) {
					layer.msg("请填写所在单位 !");
					return false;
				}

				var id_areaSelect = $("#add").val();
				if(!id_areaSelect) {
					layer.msg("请选择区域 !");
					return false;
				}

				var unitAddress = $("#unitAddress").val();
				if(!unitAddress) {
					layer.msg("请填写单位地址!");
					return false;
				}
				var postCode = $("#postCode").val();
				if(!postCode) {
					layer.msg("请填写单位邮编!");
					return false;
				}
				if(postCode.length != 6) {
					layer.msg("请正确填写邮编!");
					return false;
				}

				var major = $("#major").val();
				if(!major) {
					layer.msg("请填写从事专业!");
					return false;
				}

				var timeStartWork = $("#timeStartWork").val();
				if(!timeStartWork) {
					layer.msg("请填写从事专业起始年月!");
					return false;
				}
				
				var teachTitle = $("#teachTitle").val();
				
				if(teachTitle==0){
				}else if(teachTitle == 1){
					var professTechTitles = $("#professTechTitles").val();
					if(!professTechTitles) {
						layer.msg("请填写专业技术职称!");
						return false;
					}
					var makeTechDate = $("#makeTechDate").val();
					if(!makeTechDate) {
						layer.msg("请填写取得技术职称时间!");
						return false;
					}
				}else{
					teachTitle=0;
				}

				var hightEducation = $("#hightEducation").val();
				if(!hightEducation) {
					layer.msg("请选择最高学历!");
					return false;
				}
				//如果是地方则为必填

				var jobExperiences = $("#jobExperiences").val();
				if(!jobExperiences) {
					layer.msg("请填写主要工作经历!");
					return false;
				}

				if(from == "LOCAL") {
					//毕业证书  学位证书

					var graduateSchool = $("#graduateSchool").val();
					if(!graduateSchool) {
						layer.msg("请填写毕业院校及专业 !");
						return false;
					}
					
					//校验地方专家最高学位非空
					/* var degree = $("#degree").val();
					if(degree == null || degree == 0) {
						layer.msg("请选择最高学位!");
						return false;
					} */

				}

				/* if (telephone != "") {
					//var reg = /^(\d{3,4}-{0,1})?\d{7,8}$/;
					var reg = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
					if (!reg.test(telephone)) {
						//layer.msg("固定电话格式有误！正确格式为：010-12345678或010-12345678-123456，分机号1~6位");
						return false;
					}
			 	}
				var fax = $("#fax").val();
				//var faxReg = /^(\d{3,4}-{0,1})?\d{7,8}$/;
				var faxReg = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
				if (fax != "" && !faxReg.test(fax)) {
					layer.msg("传真电话格式有误！正确格式为：010-12345678或010-12345678-123456，分机号1~6位");
					return false;
			 	} */
				var postCode = $("#postCode").val();
				if(idNumber != "" && isNaN(postCode)) {
					layer.msg("邮编格式只能输入数字 !");
					return false;
				}

				if(idCardNumber != "") {
					var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(X|x)$)/
					if(!reg.test(idCardNumber)) {
						layer.msg("居民身份证号码格式有误 !");
						return false;
					}
					if(reg.test(idCardNumber) && idCardNumber.length == 18) {
						// 分别获取到身份证号码中的年月日并转换为数字格式
						var year = parseInt(idCardNumber.substring(6, 10));
						var month = parseInt(idCardNumber.substring(10, 12));
						var day = parseInt(idCardNumber.substring(12, 14));
						// 月份判断
						if(month < 1 || month > 12) {
							layer.msg("居民身份证号码格式有误 !");
							return false;
						}
						// 根据大小月判断日(大月)
						if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
							if(day < 1 || day > 31) {
								layer.msg("居民身份证号码格式有误 !");
								return false;
							}
						}
						// 根据大小月判断日(大月)
						if(month == 4 || month == 6 || month == 9 || month == 11) {
							if(day < 1 || day > 30) {
								layer.msg("居民身份证号码格式有误 !");
								return false;
							}
						}
						// 根据大小月判断日(二月)
						if(month == 2) {
							// 闰年
							if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
								if(day < 1 || day > 29) {
									layer.msg("居民身份证号码格式有误 !");
									return false;
								}
							}
							// 平年
							if(year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
								if(day < 1 || day > 28) {
									layer.msg("居民身份证号码格式有误 !");
									return false;
								}
							}
						}
					}
				}
				// 身份证唯一性验证
				/*if(idCardNumber != "") {
					var isok = 0;
					$.ajax({
						url: '${pageContext.request.contextPath}/expert/validateIdNumber.do',
						type: "post",
						async: false,
						data: {
							"idCardNumber": idCardNumber,
							"expertId": $("#id").val()
						},
						success: function(obj) {
							if(obj == '1') {
								layer.msg("该身份证号已被占用!");
								isok = 1;
							}else if(obj == 'disabled_180'){
                                layer.msg('该身份证号码在180天内禁止再次注册，请重新填写！', {
                                    offset: '300px'
                                });
                                isok = 1;
                            }
						}
					});
				}
				if(isok == 1) {
					return false;
				}*/

				if(from == "LOCAL") {
					var coverNote = $("#coverNote").val();
					if(!coverNote) {
						layer.msg("请选择缴纳社会保险证明 !");
						return false;
					}
				}

				var sysId = $("#sysId").val();
				var coverNote = $("#coverNote option:selected") .val();
				//var teachTitle = $("#teachTitle").val();
				//图片上传
				var flag = true;
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/findAttachment2.do",
					data: {
						"sysId": sysId,
						"from": from,
            "coverNote": coverNote || 1,
            "teachTitle": teachTitle,
						"isReferenceLftter": $("#isReferenceLftter").val()
					},
					cache: false,
					async: false,
					success: function(data) {
						if(data) {
							layer.msg(data);
							flag = false;
						} else {
							flag = true;
						}
					},
					dataType: "json",
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
					type: "post",
					success: function(response) {
						layer.msg("不通过理由：" + response.auditReason);
					}
				});
			}

			function zc() {
				layer.msg("已暂存");
			}
			//非空判断
			function notNull(name) {
				var len = document.getElementById(name).value.length;
				if(len == null || len == 0) {
					document.getElementById("err_msg_" + name).innerHTML = "不能为空";
				} else {
					document.getElementById("err_msg_" + name).innerHTML = "";
				}
			}
			function teachTitleSelected(opt,index){
				if(index==1){
					opt.attr("selected","selected");
					$("#teachTitle2").attr("selected","");
				}else if(index==2){
					opt.attr("selected","selected");
                    $("#teachTitle1").attr("selected","");
				}
			}
			function checkMobile(){
				var mobile = $("#mobile").val();
				if(!mobile) {
					layer.msg("请填写手机号!");
					return false;
				}
				if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
					layer.msg("手机号格式不正确!");
                    return false;
				}
				$.ajax({
					url: '${pageContext.request.contextPath}/expert/checkPhone.do',
					type: "post",
					async: false,
					data: {
						"phone": mobile,
						"id": $("#id").val()
					},
					success: function(obj) {
						if(obj == '1') {
							layer.msg("该手机号码已被使用!");
							return false;
						}
					}
				});
			}
		</script>
	</head>

	<body>
		<form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
			<input type="hidden" id="userId" name="userId" value="${user.id}" />
			<input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}" />
			<input type="hidden" name="id" id="id" value="${expert.id}" />
			<input type="hidden" name="zancun" id="zancun" value="" />
			<input type="hidden" name="step"  value="1" />
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
			<input type="hidden" name="range" value="${errorMap.range}" id="range" />
			
			<input type="hidden" value="${errorMap.att1}" id="error_att1">
			<input type="hidden" value="${errorMap.att2}" id="error_att2">
			<input type="hidden" value="${errorMap.att3}" id="error_att3">
			<input type="hidden" value="${errorMap.att4}" id="error_att4">
			
			<input type="hidden" id="categoryId" name="categoryId" value="" />
			<input type="hidden" id="expertsTypeId" name="expertsTypeId" value="" />
			<input type="hidden" name="token2" value="<%=tokenValue%>" />
			<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
				<h2 class="step_flow">
            <span id="sp1" class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
            <span id="sp7" class="new_step fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
            <span id="ty6" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
            <span id="sp3" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
            <span id="sp4" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
            <span id="sp5" class="new_step fl new_step_last"><i class="">6</i> <span class="step_desc_01">提交审核</span> </span>
            <div class="clear"></div>
        </h2>
        <c:if test="${ipAddressType==0 }">
            <div class="w100p f16 mt40"><span class="red">提示：上传的彩色扫描件需要去除标签水印。</span></div>
        </c:if>
				<div class="container container_box">
					<h2 class="count_flow"><i>1</i>专家个人信息</h2>
					<ul class="ul_list m_ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('relName')" id="relName" name="relName" maxlength="50" value="${expert.relName}" type="text" <c:if test="${fn:contains(errorField,'专家姓名')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专家姓名')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
								<div class="cue" id="err_msg_relName"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 近期免冠彩色证件照</span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'近期免冠彩色证件照')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('近期免冠彩色证件照')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'近期免冠彩色证件照')}">
										<u:show showId="show50" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8,show8,show50" businessId="${sysId}" sysKey="${expertKey}" typeId="50" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert50" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8,expert8,expert50"  businessId="${sysId}" sysKey="${expertKey}" maxcount="1" typeId="50" auto="true" />
										<u:show showId="show50"  groups="show1,show2,show3,show4,show5,show6,show7,show8,show8,show50" businessId="${sysId}" sysKey="${expertKey}" typeId="50" />
									</c:otherwise>
								</c:choose>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 性别</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="gender" id="gender" <c:if test="${fn:contains(errorField,'性别')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('性别')"
									</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${sexList}" var="sex" varStatus="vs">
										<option <c:if test="${expert.gender eq sex.id}">selected="selected"</c:if>
											value="${sex.id}">${sex.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 出生日期</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'出生日期')}"> style="border: 1px solid #ef0000;" onmouseover="errorMsg('出生日期')"</c:if>
								readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd' />" name="birthday" onblur="notNull('birthday')" id="birthday" type="text" onclick="WdatePicker({onpicking:function() {document.getElementById('err_msg_birthday').innerHTML = '';},dateFmt:'yyyy-MM-dd',startDate:'1970-01-01',maxDate:'%y-%M-%d'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，年龄不大于70周岁</span>
								<div class="cue" id="err_msg_birthday"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 政治面貌</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="politicsStatus" id="politicsStatus" <c:if test="${fn:contains(errorField,'政治面貌')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('政治面貌')"
									</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${zzList}" var="zz" varStatus="vs">
										<option <c:if test="${expert.politicsStatus eq zz.id}">selected="selected"</c:if>
											value="${zz.id}">${zz.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 民族</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('nation')" maxlength="10" value="${expert.nation}" name="nation" id="nation" type="text" <c:if test="${fn:contains(errorField,'民族')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('民族')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如： 汉族</span>
								<div class="cue" id="err_msg_nation"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 健康状态</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('healthState')" maxlength="10" value="${expert.healthState}" name="healthState" id="healthState" type="text" <c:if test="${fn:contains(errorField,'健康状态')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('健康状态')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如：良好</span>
								<div class="cue" id="err_msg_healthState"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 手机</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="15" value="${user.mobile}" onblur="checkMobile()" name="mobile" id="mobile" type="text" <c:if test="${fn:contains(errorField,'手机')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('手机')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">11位数字</span>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                    	class="red">*</i> 居民身份证号码</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="submitformExpert()" required maxlength="18" value="${expert.idCardNumber}" name="idCardNumber" id="idCardNumber" type="text" <c:if test="${fn:contains(errorField,'居民身份证号码')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('居民身份证号码')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，长度为18位</span>
								<div class="cue">${err_legalCard }</div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 身份证扫描件（正反面在一张上）</span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'身份证复印件（正反面）')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('身份证复印件（正反面）')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'身份证复印件（正反面）')}">
										<u:show showId="show3" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="3" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" maxcount="1" typeId="3" auto="true" />
										<u:show showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7,show8,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="3" />
									</c:otherwise>
								</c:choose>
								<!--typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }" -->
							</div>
						</li>
						<%--如果是民--%>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 是否缴纳社会保险</span>
								<div class="select_common col-md-12 col-xs-12 col-sm-12 p0" >
									<select name="coverNote" id="coverNote"  <c:if test="${fn:contains(errorField,'是否缴纳社会保险')}">style="border: 1px solid red;" onmouseover="errorMsg('是否缴纳社会保险')"</c:if> >
						<option <c:if test="${expert.coverNote eq '2'}">selected="selected"</c:if> value="2">否
						</option>
						<option <c:if test="${expert.coverNote eq '1'}">selected="selected"</c:if> value="1">是
						</option>
						</select>
						</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 
                            <span id="sbzm">
                            	<c:if test="${expert.coverNote eq '1'}">缴纳社会保险证明</c:if>
                            	<c:if test="${expert.coverNote eq '2'}">退休证书或退休证明</c:if>
                            </span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0 converNoteDiv" <c:if test="${fn:contains(errorField,'缴纳社会保险证明') or fn:contains(errorField,'退休证书或退休证明')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('缴纳社会保险证明 ')"</c:if>  <c:if test="${fn:contains(errorField,'退休证书或退休证明')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('退休证书或退休证明')"</c:if>>
								<%--图片的大小   图片的类型  --%>
								<c:if test="${expert.coverNote eq '1'}">
									<c:choose>
										<c:when test="${expert.status == 3 and !fn:contains(errorField,'缴纳社会保险证明')}">
											<u:show showId="show1" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="1" />
										</c:when>
										<c:otherwise>
											<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert1" maxcount="1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" businessId="${sysId}" sysKey="${expertKey}" typeId="1" auto="true" />
	                  	<u:show showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="1" />
										</c:otherwise>
									</c:choose>
                </c:if>
                <c:if test="${expert.coverNote eq '2'}">
                	<c:choose>
										<c:when test="${expert.status == 3 and !fn:contains(errorField,'退休证书或退休证明')}">
											<u:show showId="show1_2" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="2" />
										</c:when>
										<c:otherwise>
											<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert1_2" maxcount="1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" businessId="${sysId}" sysKey="${expertKey}" typeId="2" auto="true" />
                    	<u:show showId="show1_2"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="2" />
										</c:otherwise>
									</c:choose>
                </c:if>
							</div>
						</li>
						</c:if>

						<%--如果用户是军--%>
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件类型</span>
								<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
									<select name="idType" id="idType" <c:if test="${fn:contains(errorField,'军队人员身份证件类型')}"> style="border: 1px solid #ef0000;" onmouseover="errorMsg('军队人员身份证件类型')"
						</c:if>>
						<option selected="selected" value="">-请选择-</option>
						<c:forEach items="${idTypeList}" var="idType" varStatus="vs">
							<option <c:if test="${expert.idType eq idType.id}">selected="selected"</c:if>
								value="${idType.id}">${idType.name}</option>
						</c:forEach>
						</select>
						</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 证件号码</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="checkCardNumber()" maxlength="30" value="${expert.idNumber}" name="idNumber" id="idNumber" type="text" <c:if test="${fn:contains(errorField,'证件号码')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('证件号码')"
								</c:if>/>
								<c:if test="${fn:contains(errorField,'证件号码')}">
									<!-- <span class="add-on"
                                      style="color: red; border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span> -->
									<span class="add-on">i</span>
								</c:if>
								<c:if test="${!fn:contains(errorField,'证件号码')}">
									<span class="add-on">i</span>
									<span class="input-tip">不能为空，且不得重复使用</span>
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件</span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'军队人员身份证件')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('军队人员身份证件')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'军队人员身份证件')}">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert12" businessId="${sysId}" sysKey="${expertKey}" typeId="12" maxcount="12" auto="true" />
										<u:show showId="show12" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="12" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert12" businessId="${sysId}" sysKey="${expertKey}" typeId="12" maxcount="12" auto="true" />
										<u:show showId="show12"  businessId="${sysId}" sysKey="${expertKey}" typeId="12" />
									</c:otherwise>
								</c:choose>
							</div>

							<%-- <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                                           exts="${properties['file.picture.type']}"
                                           id="bank_up"
                                           maxcount="1"
                                           groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up"
                                           businessId="${currSupplier.id}"
                                           sysKey="${sysKey}"
                                           typeId="12"
                                           auto="true" />
                                 <u:show showId="bank_show"
                                         groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change"
                                         businessId="${currSupplier.id}"
                                         sysKey="${sysKey}"
                                         typeId="12" />
                                 <div class="cue"> ${err_supplierBank } </div>
                             </div>--%>

						</li>
						</c:if>

						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 固定电话</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('telephone')" maxlength="50" value="${expert.telephone}" name="telephone" id="telephone" type="text" <c:if test="${fn:contains(errorField,'固定电话')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('固定电话')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如: 010-12345678</span>
								<div class="cue" id="err_msg_telephone"></div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input value="${expert.fax}" name="fax" id="fax" type="text" maxlength="50" <c:if test="${fn:contains(errorField,'传真电话')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('传真电话')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如: 010-12345678</span>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 个人邮箱</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('email')" maxlength="30" value="${expert.email}" name="email" id="email" type="text" <c:if test="${fn:contains(errorField,'个人邮箱')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('个人邮箱')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空，如：123@163.com</span>
								<div class="cue" id="err_msg_email"></div>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>2</i>专业信息（包括学历和专业）</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 所在单位</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('workUnit')" maxlength="20" value="${expert.workUnit}" name="workUnit" id="workUnit" type="text" <c:if test="${fn:contains(errorField,'所在单位')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('所在单位')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
								<div class="cue" id="err_msg_workUnit"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 省</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select id="addr" onchange="func123()" <c:if test="${fn:contains(errorField,'地区')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('地区')"
									</c:if>>
									<option value="">-请选择-</option>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 市</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="address" id="add" <c:if test="${fn:contains(errorField,'地区')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('地区')"
									</c:if>>
									<option value="">-请选择-</option>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 单位地址</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('unitAddress')" maxlength="30" value="${expert.unitAddress}" name="unitAddress" id="unitAddress" type="text" placeholder="街道名称，门牌号" <c:if test="${fn:contains(errorField,'单位地址')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('单位地址')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
								<div class="cue" id="err_msg_unitAddress"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i
                        class="red">*</i>单位邮编</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="6" isZipCode="true" value="${expert.postCode}" name="postCode" id="postCode" type="text" <c:if test="${fn:contains(errorField,'单位邮编')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('单位邮编')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">长度为6位</span>
								<div class="cue"> ${err_msg_postCode } </div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input maxlength="50" value="${expert.atDuty}" name="atDuty" id="appendedInput" type="text" <c:if test="${fn:contains(errorField,'现任职务')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('现任职务')"
								</c:if>/>
								<span class="add-on">i</span>
								<span class="input-tip">如：项目经理</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 从事专业</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input onblur="notNull('major')" maxlength="50" value="${expert.major}" name="major" id="major" type="text" <c:if test="${fn:contains(errorField,'从事专业,')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('从事专业')"
								</c:if> />
								<span class="add-on">i</span>
								<span class="input-tip">不能为空</span>
								<div class="cue" id="err_msg_major"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 从事专业起始年月</span>
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'专业起始年月')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专业起始年月')"
								</c:if>
								value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM' />" readonly="readonly" onblur="notNull('timeStartWork')" name="timeStartWork" id="timeStartWork" type="text" onclick="WdatePicker({onpicking:function() {document.getElementById('err_msg_timeStartWork').innerHTML = '';},lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">如：2017-03</span>
								<div class="cue" id="err_msg_timeStartWork"></div>
							</div>
						</li>
						
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
		                    <li class="col-md-3 col-sm-6 col-xs-12"><span
		                            class="col-md-12 col-xs-12 col-sm-12 padding-left-5" ><i class="red">*</i>有无专业技术职称</span>
		                        <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
		                            <select name="teachTitle" id="teachTitle"  <c:if test="${fn:contains(errorField,'有无专业技术职称')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('有无专业技术职称')"</c:if>>
		                              <!--   <option value="">-请选择-</option> -->
                                    <option <c:if test="${expert.teachTitle == 1}">selected="selected"</c:if> onclick="teachTitleSelected(this,1)" id="teachTitle1" value="1">有</option>
                                    <option <c:if test="${expert.teachTitle == 2}">selected="selected"</c:if> onclick="teachTitleSelected(this,2)" id="teachTitle2" value="2">无</option>
		                            </select>
		                        </div>
		                    </li>
		                </c:if>
                
                
					<li class="col-md-3 col-sm-6 col-xs-12" id="profession_title">
						<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>专业技术职称</span>
							<!--/执业资格  -->
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'专业技术职称')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专业技术职称')"
								</c:if>
								maxlength="20" value="${expert.professTechTitles}" name="professTechTitles" onblur="notNull('professTechTitles')" id="professTechTitles" type="text"/>
								<span class="add-on">i</span> <span class="input-tip">不能为空</span>
								<div class="cue" id="err_msg_professTechTitles"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12" id="profession_pic"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 专业技术职称证书</span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'专业技术职称证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('专业技术职称证书')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'专业技术职称证书')}">
										<u:show showId="show4" delete="false" groups="show9,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="4" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert4" maxcount="1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="4" auto="true" />
										<u:show showId="show4"  groups="show9,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="4" />
									</c:otherwise>
								</c:choose>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12" id="profession_date"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 取得技术职称时间</span>
							<!--/职业资格时间  -->
							<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'取得技术职称时间')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('取得技术职称时间')"
								</c:if>
								value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM' />" readonly="readonly" onblur="notNull('makeTechDate')" name="makeTechDate" id="makeTechDate" type="text" onclick="WdatePicker({onpicking:function() {document.getElementById('err_msg_makeTechDate').innerHTML = '';},lang:'zh-cn',dateFmt:'yyyy-MM'})"/> <span class="add-on">i</span> <span class="input-tip">如：2017-03</span>
								<div class="cue" id="err_msg_makeTechDate"></div>
							</div>
						</li>
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red"></i> 毕业院校及专业</span>
						</c:if>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 毕业院校及专业</span>
						</c:if>
						<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
							<input <c:if test="${expert.expertsFrom eq 'LOCAL'}">onblur="notNull('graduateSchool')"</c:if> maxlength="50" value="${expert.graduateSchool}" name="graduateSchool" id="graduateSchool" type="text" <c:if test="${fn:contains(errorField,'毕业院校及专业')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('毕业院校及专业')"
							</c:if>/>
							<span class="add-on">i</span>
							<c:if test="${expert.expertsFrom eq 'ARMY'}">
								<span class="input-tip">如：北京大学计算机专业</span>
							</c:if>
							<c:if test="${expert.expertsFrom eq 'LOCAL'}">
								<span class="input-tip">不能为空，如：北京大学计算机专业</span>
							</c:if>
							<div class="cue" id="err_msg_graduateSchool"></div>
						</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 最高学历</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<select name="hightEducation" id="hightEducation" <c:if test="${fn:contains(errorField,'最高学历')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('最高学历')"
									</c:if>>
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${xlList}" var="xl" varStatus="vs">
										<option <c:if test="${expert.hightEducation eq xl.id}">selected="selected"</c:if>
											value="${xl.id}">${xl.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>

						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red"></i> 毕业证书</span>
						</c:if>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 毕业证书</span>
						</c:if>
						<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'毕业证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('毕业证书')"
							</c:if>>
							<c:choose>
								<c:when test="${expert.status == 3 and !fn:contains(errorField,'毕业证书')}">
									<u:show showId="show5" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="5" />
								</c:when>
								<c:otherwise>
									<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" maxcount="1" typeId="5" auto="true" />
									<u:show showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="5" />
								</c:otherwise>
							</c:choose>
						</div>
						</li>
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red"></i> 最高学位</span>
						</c:if>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red"></i> 最高学位</span>
						</c:if>
						<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
							<select name="degree" id="degree" <c:if test="${fn:contains(errorField,'最高学位')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('最高学位')"
								</c:if>>
								<option selected="selected" value="0">-请选择-</option>
								<c:forEach items="${xwList}" var="xw" varStatus="vs">
									<option <c:if test="${expert.degree eq xw.id}">selected="selected"</c:if>
										value="${xw.id}">${xw.name}</option>
								</c:forEach>
							</select>
						</div>
						</li>
						<c:if test="${expert.expertsFrom eq 'ARMY'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red"></i> 学位证书</span>
						</c:if>
						<c:if test="${expert.expertsFrom eq 'LOCAL'}">
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red"></i> 学位证书</span>
						</c:if>
						<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'学位证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('学位证书')"
							</c:if>>
							<c:choose>
								<c:when test="${expert.status == 3 and !fn:contains(errorField,'学位证书')}">
									<u:show showId="show6" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="6" />
								</c:when>
								<c:otherwise>
									<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert6" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" maxcount="1" typeId="6" auto="true" />
									<u:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="6" />
								</c:otherwise>
							</c:choose>
						</div>
						</li>
						<%-- <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 执业资格证书</span>
                     <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'执业资格证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('执业资格证书')"</c:if>>
                         <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true" />
                         <u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" />
                     </div>
                 </li>--%>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间</span>
							<div class="input-append input_group  col-sm-12 col-xs-12 col-md-12 p0">
								<input <c:if test="${fn:contains(errorField,'参加工作时间')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('参加工作时间')"
								</c:if> readonly="readonly" value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM' />" name="timeToWork" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
								<span class="add-on">i</span>
								<span class="input-tip">如：2017-03</span>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>3</i>推荐信</h2>
					<ul class="ul_list">

						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red"></i>相关机关事业部门推荐信</span>
							<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
								<span class="input-tip">相关机关事业部门推荐信</span>
								<select  name="isReferenceLftter" id="isReferenceLftter" <c:if test="${fn:contains(errorField,'相关机关事业部门推荐信')}">style="border: 1px solid red;" onmouseover="errorMsg('相关机关事业部门推荐信')"
									</c:if>>
									<option <c:if test="${expert.isReferenceLftter eq '2'}">selected="selected"</c:if>
										value="2">无
									</option>
									<option <c:if test="${expert.isReferenceLftter eq '1'}">selected="selected"</c:if>
										value="1">有
									</option>
								</select>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12" id="tjx" style="display:none"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i
                        class="red">*</i> 推荐信</span>
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'推荐信')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('推荐信')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'推荐信')}">
										<u:show showId="show8" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="8" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert8" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" maxcount="1" typeId="8" auto="true" />
										<u:show showId="show8"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="8" />
									</c:otherwise>
								</c:choose>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>4</i><font color=red>*</font> 主要工作经历</h2>
					<ul class="ul_list">
					<div class="padding-top-10 clear">
						<%-- <h2 class="count_flow"><i>1</i><font color=red>*</font> 主要工作经历</h2>--%>

						<li>
							<textarea <c:if test="${fn:contains(errorField,'主要工作经历')}">onmouseover="errorMsg('主要工作经历')"</c:if>
								rows="10" name="jobExperiences" id="jobExperiences" maxlength="1000"
								style='height: 150px; width: 100%; resize: none; 
								<c:if test="${fn:contains(errorField,'主要工作经历')}">border: 1px solid #ef0000;</c:if>'
								placeholder="包括时间、工作单位、职务、工作内容等"
								onkeyup="checkCharLimit('jobExperiences','limit_char_jobExperiences',1000);">${expert.jobExperiences}</textarea>
						</li>
						<h2 class="count_flow fr">（还可输入 <span id="limit_char_jobExperiences">1000</span> 个字）</h2>

					</div>
				</ul>

					<h2 class="count_flow"><i>5 </i><font color=red></font>获奖证书(限国家科技进步三等或军队科技进步二等以上奖项)</h2>
					<ul class="ul_list">
						<!-- 专业学术成果 -->
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 获奖证书</span></li>
						<div class="padding-top-10 clear">
							<div class="input-append h30  col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'获奖证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('获奖证书')"
								</c:if>>
								<c:choose>
									<c:when test="${expert.status == 3 and !fn:contains(errorField,'获奖证书')}">
										<u:show showId="show7" delete="false" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="7" />
									</c:when>
									<c:otherwise>
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="7" auto="true" />
										<u:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="7" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</ul>

					<h2 class="count_flow"><i>6</i><font color=red></font>专业学术成果</h2>
					<ul class="ul_list">
						<!-- 专业学术成果 -->
						<div class="padding-top-10 clear">
						<%--<h2 class="count_flow"><i>2</i><font color=red></font> 专业学术成果</h2>--%>

						<li>
							<textarea <c:if test="${fn:contains(errorField,'专业学术成果')}">onmouseover="errorMsg('专业学术成果')"</c:if>
								rows="10" name="academicAchievement" id="academicAchievement" maxlength="1000"
								style='height: 150px; width: 100%; resize: none; 
								<c:if test="${fn:contains(errorField,'专业学术成果')}">border: 1px solid #ef0000;</c:if>'
								placeholder=""
								onkeyup="checkCharLimit('academicAchievement','limit_char_academicAchievement',1000);">${expert.academicAchievement}</textarea>
						</li>
						<h2 class="count_flow fr">（还可输入 <span id="limit_char_academicAchievement">1000</span> 个字）</h2>

					</div>
				</ul>

					<h2 class="count_flow"><i>7</i><font color=red></font>参加军队地方采购评审情况</h2>
					<ul class="ul_list">
					<div class="padding-top-10 clear">
						<%-- <h2 class="count_flow"><i>4</i><font color=red></font> 参加军队地方采购评审情况</h2>--%>

						<li>
							<textarea <c:if test="${fn:contains(errorField,'参加军队地方采购评审情况')}">onmouseover="errorMsg('参加军队地方采购评审情况')"</c:if>
								rows="10" name="reviewSituation" id="reviewSituation" maxlength="1000"
								style='height: 150px; width: 100%; resize: none; 
								<c:if test="${fn:contains(errorField,'参加军队地方采购评审情况')}">border: 1px solid #ef0000;</c:if>'
								placeholder=""
								onkeyup="checkCharLimit('reviewSituation','limit_char_reviewSituation',1000);">${expert.reviewSituation}</textarea>
						</li>
						<h2 class="count_flow fr">（还可输入 <span id="limit_char_reviewSituation">1000</span> 个字）</h2>

					</div>
				</ul>

					<h2 class="count_flow"><i>8</i><font color=red></font>需要申请回避的情况</h2>
					<ul class="ul_list">
					<div class="padding-top-10 clear">
						<%-- <h2 class="count_flow"><i>5</i><font color=red></font> 需要申请回避的情况</h2>--%>

						<li>
							<textarea <c:if test="${fn:contains(errorField,'需要申请回避的情况')}">onmouseover="errorMsg('需要申请回避的情况')"</c:if>
								rows="10" name="avoidanceSituation" id="avoidanceSituation" maxlength="1000"
								style='height: 150px; width: 100%; resize: none; 
								<c:if test="${fn:contains(errorField,'需要申请回避的情况')}">border: 1px solid #ef0000;</c:if>'
								placeholder="近3年内,存在劳动关系的供应商,或者担任过供应商的董事、监事,或者是供应商的控股股东（实际控制人）；与供应商法定代表人或者主要负责人有夫妻、直系血亲、三代以内旁系血亲或者近姻亲关系；发生过法律纠纷的供应商；其它需要回避的情况。"
								onkeyup="checkCharLimit('avoidanceSituation','limit_char_avoidanceSituation',1000);">${expert.avoidanceSituation}</textarea>
						</li>
						<h2 class="count_flow fr">（还可输入 <span id="limit_char_avoidanceSituation">1000</span> 个字）</h2>

					</div>
				</ul>

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
	<script type="text/javascript">
		(function() {
			var teachTitle="${expert.teachTitle}";
			if(teachTitle=="1"){
				$("#profession_pic").show();
				 $("#profession_title").show();
				 $("#profession_date").show();
			}else if(teachTitle=="2"){
				$("#profession_pic").hide();
				 $("#profession_title").hide();
				 $("#profession_date").hide();
			}
			
			$("#coverNote").change(function() {
                if($(this).val() == "1") {
					$("#sbzm").text("缴纳社会保险证明");

                    addressUploadAjax('show1','show1_2','${sysId}','${expertKey}',1,1);
				} else {
					$("#sbzm").text("退休证书或退休证明");

                    addressUploadAjax('show1','show1_2','${sysId}','${expertKey}',2,1);
				}
			});
			/**动态加载上传附件控件*/
            function addressUploadAjax(uploadId,showId,businessId,sysKey,typeId, maxcount){
                $.ajax({
                    url: '${pageContext.request.contextPath}/expert/attachmentControlShow.do',
                    type: "post",
                    async: false,
                    data: {
                        "uploadId": uploadId,
                        "showId": showId,
                        "businessId": businessId,
                        "sysKey": sysKey,
                        "typeId": typeId,
                        "maxcount": maxcount
                    },
                    success: function(data) {
                        $(".converNoteDiv").empty();
                        $(".converNoteDiv").html(data);
                    },
                    complete: function () {
                        init_web_upload();
                    }
                });
            }
			
			$("#teachTitle").change(function() {
				if($(this).val() == "1") {
					 $("#profession_pic").show();
					 $("#profession_title").show();
					 $("#profession_date").show();
				} else {
					$("#profession_pic").hide();
					 $("#profession_title").hide();
					 $("#profession_date").hide();
				}
			});
			
			
			
			
			if($("#isReferenceLftter").val() == "1") {
				$("#tjx").show();
				init_web_upload();
			}

			$("#isReferenceLftter").change(function() {
				if($(this).val() == "1") {
					$("#tjx").show();
					init_web_upload();
					wtj = 5;
				} else {
					$("#tjx").hide();
					wtj = 4;
				}
			});
		})();
		
		// 核对字符长度
		function checkCharLimit(inputId,countId,limit){
			var inputVal = $("#"+inputId).val();
			var inputLen = inputVal ? inputVal.length : 0;
			$("#"+countId).text(limit - inputLen);
		}
		
		checkCharLimit('jobExperiences','limit_char_jobExperiences',1000);
		checkCharLimit('academicAchievement','limit_char_academicAchievement',1000);
		checkCharLimit('reviewSituation','limit_char_reviewSituation',1000);
		checkCharLimit('avoidanceSituation','limit_char_avoidanceSituation',1000);
		
		// 如果专家状态是退回修改，控制表单域的编辑与不可编辑
		var expertSt = '${expert.status}';
		if(expertSt == '3'){
			$("input[type='text'],select,textarea").attr('disabled',true);
			$("input[type='text'],select,textarea").each(function(){
				// 或者$(this).attr("style").indexOf("border: 1px solid #ef0000;") > 0
				// 或者$(this).css("border") == '1px solid rgb(239, 0, 0)'
				if(this.style.border == '1px solid rgb(239, 0, 0)'||this.style.border == '1px solid red'){
					$(this).attr('disabled',false);
				}
				if(this.value==''||this.value=='0'){
					$(this).attr('disabled',false);
				}
			});
		}
	</script>

</html>