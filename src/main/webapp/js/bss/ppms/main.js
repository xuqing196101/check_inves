

$(function() {
	$("#menu a").click(function() {
		$('#menu li').each(function(index) {
			$(this).removeClass('active'); // 删除其他兄弟元素的样式
		});
		$(this).parent().addClass('active'); // 添加当前元素的样式
	});
	/*上面一个的问题就是 $(this).parent().addClass('active')这个a标签不是循环出来的 是自定义的它的id是as */
	$(document).ready(function() {
		$("#menu li").click(function() {
			$(this).addClass("active").siblings().removeClass("active");
		});
	});
	var projectId = $("#projectId").val();
	var types = $("#type").val();
	$.ajax({
		url: globalPath+"/open_bidding/getNextFd.do?flowDefineId=0&projectId=" + projectId,
		contentType: "application/json;charset=UTF-8",
		dataType: "json", //返回格式为json
		type: "POST", //请求方式           
		success: function(data) {
			if(data.success) {
				//当前环节经办人
				$("#currHuanjieId").val(data.currFlowDefineId);
				$("#currPrincipal").empty();
				if(data.users != null && data.users != '') {
					$.each(data.users, function(i, user) {
						$("#currPrincipal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
					});
				} else {
					$("#currPrincipal").append("<option  value=" + data.currOperatorId + ">" + data.operateName + "</option>");
				}
				$("#currPrincipal").select2();
				$("#currPrincipal").select2("val", data.currOperatorId);
				$("#currPrincipalId").val(data.currOperatorId);
				if(types){
					$("#isOperate").val(0);
				} else {
					$("#isOperate").val(data.isOperate);
				}
				
				//禁止变更经办人操作
				if(data.isOperate == 0) {
					$("#submitdiv").attr("disabled", true);
					$("#principal").attr("disabled", true);
					$("#currPrincipal").attr("disabled", true);
				} else {
					if(types){
						$("#submitdiv").attr("disabled", true);
						$("#principal").attr("disabled", true);
						$("#currPrincipal").attr("disabled", true);
					} else {
						$("#submitdiv").attr("disabled", false);
						$("#principal").attr("disabled", false);
						$("#currPrincipal").attr("disabled", false);
					}
					
					//环节结束
					if(data.isFes == 1) {
						$("#submitdiv").attr("disabled", true);
						$("#principal").attr("disabled", true);
						$("#currPrincipal").attr("disabled", true);
					} else {
						if(types){
							$("#submitdiv").attr("disabled", true);
							$("#principal").attr("disabled", true);
							$("#currPrincipal").attr("disabled", true);
						} else {
							$("#submitdiv").attr("disabled", false);
							$("#principal").attr("disabled", false);
							$("#currPrincipal").attr("disabled", false);
						}
						
					}
				}
				if(!data.isEnd) {
					$("#nextHaunjie").show();
					$("#updateOperateId").show();
					$("#huanjie").html(data.flowDefineName);
					$("#huanjieId").val(data.flowDefineId);
					$("#principal").empty();
					if(data.users != null && data.users != '') {
						$.each(data.users, function(i, user) {
							$("#principal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
						});
					} else {
						$("#principal").append("<option  value=" + data.operatorId + ">" + data.nextOperatorName + "</option>");
					}

					$("#principal").select2();
					$("#principal").select2("val", data.operatorId);
					$("#principalId").val(data.operatorId);
				}
				if(data.isEnd) {
					$("#nextHaunjie").hide();
					$("#updateOperateId").hide();
				}
				if(data.isZJT){
					$("#dnoneZJT").removeClass("dnone");
				}
			}
		}
	});
	$("#onmouse").addClass("btmfixs");
});

function back() {
	var type = $("#type").val();
	if(type == '1') {
		location.href = globalPath+'/project/projectByAll.html';
	} else {
		location.href = globalPath+'/project/list.html';
	}
}

function jumpLoad(url, projectId, flowDefineId) {
	var types = $("#type").val();
	$.ajax({
		url: globalPath+"/open_bidding/getNextKb.do?flowDefineId=" + flowDefineId + "&projectId=" + projectId,
		contentType: "application/json;charset=UTF-8",
		dataType: "json", //返回格式为json
        async : false,
		type: "POST", //请求方式           
		success: function(data) {
			if(data.next == '1') {
				layer.alert(data.name + "环节未结束");
			} else if (data.next == '3') {
				layer.alert("请到基本信息填写最少供应商");
			} else {
				$.ajax({
					url: globalPath+"/open_bidding/getNextFd.do?flowDefineId=" + flowDefineId + "&projectId=" + projectId,
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式           
					success: function(data) {
						if(data.success) {
							//当前环节经办人
							$("#currHuanjieId").val(data.currFlowDefineId);
							$("#currPrincipal").empty();
							if(data.users != null && data.users != '') {
								$.each(data.users, function(i, user) {
									$("#currPrincipal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
								});
							} else {
								$("#currPrincipal").append("<option  value=" + data.currOperatorId + ">" + data.operateName + "</option>");
							}
							$("#currPrincipal").select2();
							$("#currPrincipal").select2("val", data.currOperatorId);
							$("#currPrincipalId").val(data.currOperatorId);
							if(types){
								$("#isOperate").val(0);
							} else {
								$("#isOperate").val(data.isOperate);
							}
							
							//禁止变更经办人操作
							if(data.isOperate == 0) {
								$("#submitdiv").attr("disabled", true);
								$("#principal").attr("disabled", true);
								$("#currPrincipal").attr("disabled", true);
							} else {
								if(types){
									$("#submitdiv").attr("disabled", true);
									$("#principal").attr("disabled", true);
									$("#currPrincipal").attr("disabled", true);
								} else {
									$("#submitdiv").attr("disabled", false);
									$("#principal").attr("disabled", false);
									$("#currPrincipal").attr("disabled", false);
								}
								
								//环节结束
								if(data.isFes == 1) {
									$("#submitdiv").attr("disabled", true);
									$("#principal").attr("disabled", true);
									$("#currPrincipal").attr("disabled", true);
								} else {
									if(types){
										$("#submitdiv").attr("disabled", true);
										$("#principal").attr("disabled", true);
										$("#currPrincipal").attr("disabled", true);
									} else {
										$("#submitdiv").attr("disabled", false);
										$("#principal").attr("disabled", false);
										$("#currPrincipal").attr("disabled", false);
									}
								}
							}
							if(!data.isEnd) {
								$("#nextHaunjie").show();
								$("#updateOperateId").show();
								$("#huanjie").html(data.flowDefineName);
								$("#huanjieId").val(data.flowDefineId);
								$("#principal").empty();
								if(data.users != null && data.users != '') {
									$.each(data.users, function(i, user) {
										$("#principal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
									});
								} else {
									$("#principal").append("<option  value=" + data.operatorId + ">" + data.nextOperatorName + "</option>");
								}
								$("#principal").select2();
								$("#principal").select2("val", data.operatorId);
								$("#principalId").val(data.operatorId);
							}
							if(data.isEnd) {
								$("#nextHaunjie").hide();
								$("#updateOperateId").hide();
							}
						}
					}
				});
				var urls = globalPath+"/" + url + "?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
				$("#as").attr("href", urls);
				var el = document.getElementById('as');
				el.click(); //触发打开事件
				// $("#open_bidding_main").load(urls);
			}
		}
	});

}

//提交下一环节经办人
function updateOperator() {
	$.ajax({
		type: "POST",
		url: globalPath+"/open_bidding/updateOperator.html",
		dataType: "json", //返回格式为json
		data: $('#updateLinkId').serialize(),
		success: function(data) {
			if(data.success) {
				layer.msg("提交下一环节经办人成功", {
					offset: '100px'
				});
			}
		},
		error: function(data) {
			layer.msg("请稍后再试", {
				offset: '100px'
			});
		}
	});
}

//变更当前环节经办人
function updateCurrOperator() {
	var currUpdateoperator = $("#currPrincipal").select2("data").text;
	layer.confirm('您确认变更当前环节经办人为 【' + currUpdateoperator + '】吗?', {
		btn: ['确定', '取消']
	}, function() {
		var projectId = $("#projectId").val();
		var currFlowDefineId = $("#currHuanjieId").val();
		var currUpdateUserId = $("#currPrincipal").val();
		$.ajax({
			type: "POST",
			url: globalPath+"/open_bidding/updateCurrOperator.html",
			dataType: "json", //返回格式为json
			data: {
				"currFlowDefineId": currFlowDefineId,
				"currUpdateUserId": currUpdateUserId,
				"projectId": projectId
			},
			success: function(data) {
				if(data.success) {
					layer.msg("变更当前环节经办人成功", {
						offset: '100px'
					});
					$("#currPrincipalId").val(currUpdateUserId);
					jumpLoad(data.url, projectId, currFlowDefineId);
				}
			},
			error: function(data) {
				layer.msg("请稍后再试", {
					offset: '100px'
				});
			}
		});
	}, function() {
		$("#currPrincipal").select2();
		$("#currPrincipal").select2("val", $("#currPrincipalId").val());
	});
}

function jumpChild(url) {
	$("#open_bidding_main").load(url + "#TANGER_OCX");
}

//页面初始加载将要执行的页面
function initLoad() {
	var url = $("#initurl").val();
	$("#open_bidding_main").load(globalPath+"/" + url);
}

function tips(step) {
	if(step != 1) {
		layer.msg("请先执行前面步骤", {
			offset: ['220px'],
		});
	}
}

//变更下一环节经办人
function submitCurrOperator() {
	var nextUpdateOperator = $("#principal").select2("data").text;
	layer.confirm('您确认要变更下一环节经办人为【' + nextUpdateOperator + '】吗?', {
		btn: ['确定', '取消']
	}, function() {
		var projectId = $("#projectId").val();
		var nextFlowDefineId = $("#huanjieId").val();
		var nextUpdateUserId = $("#principal").val();
		$.ajax({
			type: "POST",
			url: globalPath+"/open_bidding/updateCurrOperator.html",
			dataType: "json", //返回格式为json
			data: {
				"currFlowDefineId": nextFlowDefineId,
				"currUpdateUserId": nextUpdateUserId,
				"projectId": projectId
			},
			success: function(data) {
				if(data.success) {
					$("#principalId").val(nextUpdateUserId);
					layer.msg(data.flowDefineName + "经办人设置成功", {
						offset: '100px'
					});
				}
			},
			error: function(data) {
				layer.msg("请稍后再试", {
					offset: '100px'
				});
			}
		});
	}, function() {
		$("#principal").select2();
		$("#principal").select2("val", $("#principalId").val());
	});
}

function bigImg(x) {
	$(x).removeClass("btmfixs");
	$(x).addClass("btmfix");

}

function normalImg(x) {
	$(x).removeClass("btmfix");
	$(x).addClass("btmfixs");
}
var indexLayer;
//提交当前环节
function submitcurr() {
	var projectId = $("#projectId").val();
	var currFlowDefineId = $("#currHuanjieId").val();
	var currUpdateUserId = $("#currPrincipal").val();
	var layerobj=layer.confirm('您确定已经完成当前环节操作吗?', {
		title: '提示',
		offset: '222px',
		shade: 0.01
	}, function(index) {
		layer.close(layerobj);
		//校验当前环节是否完成
		$.ajax({
			url: globalPath+"/open_bidding/isSubmit.html",
			data: {
				"currFlowDefineId": currFlowDefineId,
				"projectId": projectId
			},
			type: "post",
			dataType: "json", //返回格式为json
			success: function(data) {
				if(data.flowType=="GYSQD"){
					$.ajax({
						url: globalPath+"/open_bidding/checkSupplierNumber.html",
						data: {
							"projectId": projectId
						},
						type: "post",
						dataType: "json",
						success: function(data2) {
							if(data2.rules != null){
								var split=data2.rules.split(";");
								var html="";
								$("#openDiv_packages").empty();
								for(var i=0;i<split.length;i++){
									var split2=split[i].split(",");
									html+='<div class=" mt10 fl ml10"><input type="checkbox" value="'+split2[0]+'" name="packagesId" />'+split2[1]+'</div>';
								}
								$("#openDiv_packages").append(html);
								fflog=false;
								/*indexLayer =  layer.open({
								  	    shift: 1, //0-6的动画形式，-1不开启
								  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
								  	    title: ['提示','border-bottom:1px solid #e5e5e5'],
								  	    shade:0.01, //遮罩透明度
									  		type : 1,
									  		area : [ '30%', '200px'  ], //宽高
									  		content : $('#openDivPackages'),
								});*/
								submitFlw(data,currFlowDefineId,projectId);
							}else{
								if(data2.status == "failed"){
									$("#jzxtp").hide();
									submitFlw(data,currFlowDefineId,projectId);
								} else {
									submitFlw(data,currFlowDefineId,projectId);
								}
								
							}
						},
						error: function() {
							layer.msg("提交失败", {
								offset: '100px'
							});
						}
					});
				}/*else if (data.flowType =="NZCGGG" || data.flowType == "NZZBGS"){
					var noticeType = null;
					if(data.flowType =="NZCGGG"){
						noticeType = "purchase";
					} else {
						noticeType = "win";
					}
					//查询公告是否完成
					var bool = ifNotice(projectId, noticeType);
					if(!bool){
						document.getElementById("open_bidding_iframe").contentWindow.publish();
						var categoryId = $("#open_bidding_iframe").contents().find("#cId").val();
						if(categoryId){
							submitFlw(data,currFlowDefineId,projectId);
						}
					} else {
						submitFlw(data,currFlowDefineId,projectId);
					}
				}*/ else {
					submitFlw(data,currFlowDefineId,projectId);
				}
			},
			error: function() {
				layer.msg("提交失败", {
					offset: '100px'
				});
			}
		});
	});
}

/*function ifNotice(projectId, noticeType){
	var bool = true;
	$.ajax({
		url: globalPath+"/open_bidding/ifNotice.html",
		data: {
			"projectId": projectId,
			"noticeType" : noticeType
		},
		type: "post",
		async: false,
		dataType: "text",
		success: function(data) {
			if(data != "ok"){
				bool = false;
			}
		},
		error: function() {
			layer.msg("提交失败", {
				offset: '100px'
			});
		}
	});
	return bool;
}*/

function submitFlw(data,currFlowDefineId,projectId){
	if(data.success) {
		//提交当前环节
		$.ajax({
			url: globalPath+"/open_bidding/submitHuanjie.html",
			data: {
				"currFlowDefineId": currFlowDefineId,
				"projectId": projectId
			},
			type: "post",
			dataType: "json",
			success: function(data2) {
				if(data2.success) {
					jumpLoad(data2.url, projectId, currFlowDefineId);
					$("#"+currFlowDefineId+"_exe").removeClass("executed");
					$("#"+currFlowDefineId+"_exe").addClass("executed");
					layer.msg("提交成功", {
						offset: '100px'
					});
					
				}
			},
			error: function() {
				layer.msg("提交失败", {
					offset: '100px'
				});
			}
		});
	} else {
		if(data.flowType == "XMFB") {
			//如果是项目分包环节
			layer.confirm(data.msg, {
				shade: 0.01,
				btn: ['确定', '取消']
			}, function() {
				$.ajax({
					url: globalPath+"/project/savePackage.html",
					data: {
						"projectId": projectId
					},
					type: "post",
					dataType: "json",
					success: function(data) {
						if(data == "1") {
							$.ajax({
								url: globalPath+"/open_bidding/submitHuanjie.html",
								data: {
									"currFlowDefineId": currFlowDefineId,
									"projectId": projectId
								},
								type: "post",
								dataType: "json",
								success: function(data2) {
									if(data2.success) {
										layer.msg("提交成功", {
											offset: '100px'
										});
										jumpLoad(data2.url, projectId, currFlowDefineId);
									}
								},
								error: function() {
									layer.msg("提交失败", {
										offset: '100px'
									});
								}
							});
						}
					},
					error: function() {
						layer.msg("提交失败", {
							offset: '100px'
						});
					}
				});
			}, function() {
				/*var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);*/
			});
		} else if(data.flowTypes == "KBCB" || data.flowTypes == "XMXX"||data.flowTypes == "NZCGWJ"){
			layer.alert(data.msgs, {
				offset: '100px'
			});
		}
	}
}
function closelayer(){
	layer.close(indexLayer);
}

// 左侧导航收缩
function tree_toggle() {
	if ($('.btn_toggle').hasClass('open')) {
		$('.btn_toggle').removeClass('open');
		$('.btn_toggle').animate({
			left: '0'
		});
		$('#show_tree_div').animate({
			left: '-180'
		});
	} else {
		$('.btn_toggle').addClass('open');
		$('.btn_toggle').animate({
			left: '180'
		});
		$('#show_tree_div').animate({
			left: '0'
		});
	}
}
// 自适应屏幕高度
function fixed_treeHeight() {
	var win_height = $(window).height();
	var tree_height = $('#show_tree_div .btn_list').height() + parseInt($('#show_tree_div').css('top'));
	
	if (tree_height > win_height) {
		$('#show_tree_div').css({
			height: win_height - parseInt($('#show_tree_div').css('top')) - 10
		});
	} else {
		$('#show_tree_div').css({
			height: 'auto'
		});
	}
}
// 品目大小变化重新计算高度
$(window).resize(function () {
	fixed_treeHeight();
});
// 显示当前环节
$(function () {
	var tree_place = $('#show_tree_div #menu li').eq(0).find('a').html();
	$('#tree_place').html(tree_place);
	if(tree_place=="项目信息"){
		$("#updateLinkId").hide();
		$("#headline-v2").hide();
	}
	$('#show_tree_div #menu li').bind('click', function () {
		tree_place = $(this).find('a').html();
		$('#tree_place').html(tree_place);
		if(tree_place!="项目信息"){
			$("#updateLinkId").show();
			$("#headline-v2").show();
		}
		if(tree_place=="项目信息"){
			$("#updateLinkId").hide();
			$("#headline-v2").hide();
		}
	});
	
	fixed_treeHeight();
});