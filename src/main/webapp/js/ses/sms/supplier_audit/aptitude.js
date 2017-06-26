//默认不显示叉
$(function() {
	findDate();
	$("td").each(function() {
		$(this).find("p").hide();
	});
});
/** 分页* */
function listPage(pages, total, startRow, endRow, pageNum) {
	laypage({
		cont : $("#pagediv"), // 容器。值支持id名、原生dom对象，jquery对象,
		pages : pages, // 总页数
		skin : '#2c9fA6', // 加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip : true, // 是否开启跳页
		total : total,
		startRow : startRow,
		endRow : endRow,
		groups : pages >= 3 ? 3 : pages, // 连续显示分页数
		curr : function() { // 通过url获取当前页，也可以同上（pages）方式获取
			return pageNum;
		}(),
		jump : function(e, first) { // 触发分页后的回调
			if (!first) { // 一定要加此判断，否则初始时会无限刷新
				$("#page").val(e.curr);
				findDate();
			}
		}
	});
}
//获取 数据
function findDate() {
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '45%', '53%' ]
		});
		$.ajax({
			type : "POST",
			url : globalPath + "/supplierAudit/overAptitude.do",
			data : $("#form_id").serializeArray(),
			success : function(obj) {
				if (obj) {
					listPage(obj.data.pages, obj.data.total, obj.data.startRow,
							obj.data.endRow, obj.data.pageNum);
					showData(obj.data);
				} else {
					layer.msg(obj.msg);
				}
				layer.close(index);
			},
			error : function(data) {
				layer.msg("请求异常!");
				layer.close(index);
			},
		});
}
//封装填充 数据
function showData(obj) {
	$("#tab_content_2 tbody").empty();
	$(obj.list).each(
			function(index, item) {
				$("#tab_content_2 tbody").append("<tr>"+
								"<td class=\"tc info\">" + ((index+1)+(obj.pageNum-1)*(obj.pageSize))+ "</td>"+
	                            "<td class=\"tc info\">"+isNull(item.rootNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.firstNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.secondNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.thirdNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.fourthNode)+"</td>"+
	                            "<td class=\"tc info\">"+isShow(item.fileCount,"qualifications",item.rootNode,item.rootNodeID,item.firstNodeID,item.secondNodeID,item.thirdNodeID,item.fourthNodeID)+"</td>"+
	                            "<td class=\"tc info\">"+isShow(item.contractCount,"contract",item.rootNode,item.rootNodeID,item.firstNodeID,item.secondNodeID,item.thirdNodeID,item.fourthNodeID)+"</td></tr>"
				);
			});
}
//判断显示相关内容 合同
function onContractShow(rootNode,rootNodeID,firstNodeID,secondNodeID,thirdNodeID,fourthNodeID){
	showFrame(rootNode+"-销售合同信息");
}
//判断显示相关内容 资质
function onQualificationsShow(rootNode,rootNodeID,firstNodeID,secondNodeID,thirdNodeID,fourthNodeID){
	showFrame(rootNode+"-资质文件信息");
}
//是否有内容显示
function isShow(obj,type,rootNode,rootNodeID,firstNodeID,secondNodeID,thirdNodeID,fourthNodeID){
	var rut="";
	//合同
	if(type=='contract'){
		if(obj>0){
			rut= "<a href=\"javascript:void(0);\" onclick=\"onContractShow('"+rootNode+"','"+rootNodeID+"','"+firstNodeID+"','"+secondNodeID+"','"+thirdNodeID+"','"+fourthNodeID+"')\">查看</a>";
		}else {
			rut= "";
		}
		//资质
	}else if(type=='qualifications'){
		if(obj>0){
			rut="<a href=\"javascript:void(0);\" onclick=\"onQualificationsShow('"+rootNode+"','"+rootNodeID+"','"+firstNodeID+"','"+secondNodeID+"','"+thirdNodeID+"','"+fourthNodeID+"')\">查看</a>";
		}else{
			rut= "";
		};
	};
	return rut;
}
//弹出框
function showFrame(obj){
	 index = layer.open({
		type: 1, //page层
		area: ['800px', '430px'],
		title: obj,//标题
		closeBtn: 1,
		shade: 0.01, //遮罩透明度
		moveType: 1, //拖拽风格，0是默认，1是传统拖动
		shift: 1, //0-6的动画形式，-1不开启
		offset: ['40px', '280px'],
		content: $('#show_div'),
		});
	}
//是否为空
function isNull(obj){
	if(obj){
		return obj;
	}else{
		return "";
	}
}
// 下一步
function nextStep() {
	var action = globalPath + "/supplierAudit/contract.html";
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}

// 上一步
function lastStep() {
	/* $("#form_id").attr("action", lastUrl); */
	var action = globalPath + "/supplierAudit/supplierType.html";
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}

/*
 * function reason(auditFieldName, auditContent, dex) { var supplierId =
 * $("#supplierId").val(); var index = layer.prompt({ title: '请填写不通过的理由：',
 * formType: 2, offset: '100px' }, function(text) { $.ajax({ url:
 * globalPath + "/supplierAudit/auditReasons.html", type:
 * "post", data: { "auditType": "aptitude_page", "auditFieldName":
 * auditFieldName, "auditContent": auditContent + "附件信息", "suggest": text,
 * "supplierId": supplierId, "auditField": auditContent }, dataType: "json",
 * success: function(result) { result = eval("(" + result + ")"); if(result.msg ==
 * "fail") { layer.msg('该条信息已审核过！', { shift: 6, //动画类型 offset: '100px' }); } }
 * });
 * 
 * $("#" + dex + "_hidden").hide(); $("#" + dex + "_show").show();
 * layer.close(index); }); }
 */

function reason(auditField, auditFieldName, auditContent) {
	var supplierId = $("#supplierId").val();
	var index = layer
			.prompt(
					{
						title : '请填写不通过的理由：',
						formType : 2,
						offset : '100px',
						maxlength : '100',
					},
					function(text) {
						var text = trim(text);
						if (text != null && text != "") {
							$
									.ajax({
										url : globalPath + "/supplierAudit/auditReasons.html",
										type : "post",
										data : "&auditFieldName="
												+ auditFieldName + "&suggest="
												+ text + "&supplierId="
												+ supplierId
												+ "&auditType=aptitude_page"
												+ "&auditContent="
												+ auditContent + "&auditField="
												+ auditField,
										dataType : "json",
										success : function(result) {
											result = eval("(" + result + ")");
											if (result.msg == "fail") {
												layer.msg('该条信息已审核过！', {
													shift : 6, // 动画类型
													offset : '100px',
												});
											};
										},
									});
							$("#" + auditField + "").show(); // 显示叉
							layer.close(index);
						} else {
							layer.msg('不能为空！', {
								offset : '100px',
							});
						}
						;
					});
}

function reasonProject(auditField, auditFieldName, auditContent) {
	var supplierId = $("#supplierId").val();
	var index = layer
			.prompt(
					{
						title : '请填写不通过的理由：',
						formType : 2,
						offset : '100px',
						maxlength : '100',
					},
					function(text) {
						var text = trim(text);
						if (text != null && text != "") {
							$
									.ajax({
										url : globalPath + "/supplierAudit/auditReasons.html",
										type : "post",
										data : "&auditFieldName="
												+ auditFieldName + "&suggest="
												+ text + "&supplierId="
												+ supplierId
												+ "&auditType=aptitude_page"
												+ "&auditContent="
												+ auditContent + "&auditField="
												+ auditField,
										dataType : "json",
										success : function(result) {
											result = eval("(" + result + ")");
											if (result.msg == "fail") {
												layer.msg('该条信息已审核过！', {
													shift : 6, // 动画类型
													offset : '100px',
												});
											}
											;
										}
									});
							$("#" + auditField + "_show").show();
							$("#" + auditField + "_hidden").hide();
							layer.close(index);
						} else {
							layer.msg('不能为空！', {
								offset : '100px'
							});
						}
						;
					});
}

// 删除左右两端的空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 暂存
function zhancun() {
	var supplierId = $("#id").val();
	$.ajax({
				url : globalPath + "/supplierAudit/temporaryAudit.do",
				dataType : "json",
				data : {
					supplierId : supplierId
				},
				success : function(result) {
					layer.msg(result, {
						offset : [ '100px' ]
					});
				},
				error : function() {
					layer.msg("暂存失败", {
						offset : [ '100px' ]
					});
				}
			});
}
function jump(str) {
	var action;
	if (str == "essential") {
		action = globalPath + "/supplierAudit/essential.html";
	}
	if (str == "financial") {
		action = globalPath + "/supplierAudit/financial.html";
	}
	if (str == "items") {
		action = globalPath + "/supplierAudit/items.html";
	}
	if (str == "aptitude") {
		action = globalPath + "/supplierAudit/goPageAptitude.html";
	}
	if (str == "contract") {
		action = globalPath + "/supplierAudit/contract.html";
	}
	if (str == "applicationForm") {
		action = globalPath + "/supplierAudit/applicationForm.html";
	}
	if (str == "reasonsList") {
		action = globalPath + "/supplierAudit/reasonsList.html";
	}
	if (str == "supplierType") {
		action = globalPath + "/supplierAudit/supplierType.html";
	}
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}