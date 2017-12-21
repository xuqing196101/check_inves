<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<link href="${pageContext.request.contextPath}/public/webupload/css/webuploader.css" media="screen" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" media="screen" rel="stylesheet" type="text/css">
		<!-- 文件显示 -->
		<link href="${pageContext.request.contextPath}/public/webupload/css/viewer.css" media="screen" rel="stylesheet" type="text/css">
		
		<!-- 文件上传 -->
		<script src="${pageContext.request.contextPath}/public/webupload/js/webuploader.js"></script>
		<script src="${pageContext.request.contextPath}/public/webuploadSBW/upload.js"></script>
		<!-- 文件显示 -->
		<script src="${pageContext.request.contextPath}/public/webupload/js/viewer.js"></script>
		<script src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
		
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title></title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<script type="text/javascript">
			$(function() {
				/* debugger;
				var sel=document.getElementsByName("isturnUp");
				for(var i=0;i<sel.length;i++){
					var arr = $(sel[i]).parents("tr").find("td:last").find(".upload_tag");
    			if (sel[i] == '0') {
    				
    			}
    			for (var k = 0; k < arr.length; k++) {
    					$(arr[k]).css({
    						top: '50%',
    						marginTop: '-13px'
    					});
    				}
				} */
				//获取查看或操作权限
				var isOperate = $('#isOperate', window.parent.document).val();
				if(isOperate == 0) {
					//只具有查看权限，隐藏操作按钮
					$(":button").each(function() {
						$(this).hide();
					});
				}
			});
		
			var aa;
			function showPackageType(supplierId,index) {
			aa = index;
				var setting = {
					check: {
						enable: true,
						chkboxType: {
							"Y": "",
							"N": ""
						}
					},
					view: {
						dblClickExpand: false
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId"
						}
					},
					callback: {
						beforeClick: beforeClick,
						onCheck: onCheck
					}
				};

				$.ajax({
					type: "GET",
					async: false,
					url: "${pageContext.request.contextPath}/open_bidding/getpackage.do?projectId=${projectId}&supplierId="+supplierId,
					dataType: "json",
					success: function(zNodes) {
						tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
						tree.expandAll(true); //全部展开
					}
				});
				var cityObj = $("#packageName"+index);
				var cityOffset = $("#packageName"+index).offset();
				$("#packageContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownPackageType);
			}
			
			function onBodyDownPackageType(event) {
				if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
					hidePackageType();
				}
			}

			function hidePackageType() {
				$("#packageContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownPackageType);

			}
			
			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treePackageType");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					rid += nodes[i].id + ",";
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#packageName" + aa);
				cityObj.attr("value", v);
				cityObj.attr("title", v);
				$("#packageId" + aa).val(rid);
				if($("#packageId" + aa).val()){
					$("#isturnUp" + aa).val("已到场");
					var arr = new Array();
					arr = $("#isturnUp" + aa).parents("tr").find("td:last").find(".upload_tag");
					for (var i = 0; i < arr.length; i++) {
						$(arr[i]).css({
							top: '50%',
							marginTop: '-13px'
						});
					}
				} else {
					$("#isturnUp" + aa).val("");
					var arrs = new Array();
					arrs = $("#isturnUp" + aa).parents("tr").find("td:last").find(".upload_tag");
					for (var i = 0; i < arrs.length; i++) {
					$(arrs[i]).css({
						top: '-100%'
					});
				}
				}
			}
			
			var jsonStr = [];
			function updateSaleTender() {
				layer.confirm('提交后不可变更?', {title: '提示',shade: 0.01
				}, function(index) {
					layer.close(index);
					var allTable = document.getElementsByTagName("table");
					for(var j = 1; j < allTable[0].rows.length; j++) {
						var isTurnUp = $(allTable[0].rows).eq(j).find("td").eq("3").find("input").val();
						var supplierId = $(allTable[0].rows).eq(j).find("td").eq("1").find("input").val();
						if (isTurnUp == null || isTurnUp == '') {
							isTurnUp = 1;
						} else {
							isTurnUp = 0;
						}
						var json = {"supplierId" : supplierId, "isTurnUp" : isTurnUp};
						jsonStr.push(json);
					}
				 	var projectId = "${projectId}";
				 	$("#jsonString").val(JSON.stringify(jsonStr));
				 	$.ajax({
	        	type: "POST",
	        	url: "${pageContext.request.contextPath}/open_bidding/isTurnUp.html?projectId=" + projectId,
	        	data: {isTurnUp:JSON.stringify(jsonStr)},
	        	dataType: "json",
	        	async:false,
	       	 	success: function (message) {
	       	 		$.ajax({
								url: "${pageContext.request.contextPath}/open_bidding/checkSupplierNumber.html",
								data: {
									"projectId": projectId
								},
								type: "post",
								dataType: "json",
								async:false,
								success: function(data2) {
									if(data2.rules != null){
										var split = data2.rules.split(";");
										var html="";
										$('#openDiv_packages', window.parent.document).empty();
										for(var i=0;i<split.length;i++){
											var split2=split[i].split(",");
											html+='<label class="mr10 hand m_inline"><input type="checkbox" value="'+split2[0]+'" name="packagesId" /> '+split2[1]+'</label>';
										}
										$("#openDiv_packages", window.parent.document).append(html);
										indexLayer = parent.layer.open({
										  	    shift: 1, //0-6的动画形式，-1不开启
										  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
										  	    title: ['提示','border-bottom:1px solid #e5e5e5'],
										  	    shade:0.01, //遮罩透明度
											  		type : 1,
											  		area : [ '30%' ], //宽高
											  		cancel : function () {
											  			$.ajax({
															url: "${pageContext.request.contextPath}/open_bidding/isTurnUp.html?type=delete&projectId=" + projectId,
															type: "post",
															dataType: "json",
															async:false,
															success: function(data2) {
																window.location.reload();
															}
											  			});
										              },
											  		content : $('#openDivPackages', window.parent.document),
										});
									}else{
										if(data2.status == "failed"){
											$("#jzxtp", window.parent.document).hide();
										}
										
									}
								},
								error: function() {
									layer.msg("提交失败", {
										offset: '100px'
									});
								}
							});
	        		window.location.reload();
	        	},
   		  	});
				});
			}
			
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<!-- 我的页面开始-->
		<div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
		</div>
		<div class="">
			<!-- 表格开始-->
				<input type="hidden" id="jsonString" value="">
				<table class="table table-bordered table-condensed table-hover table-striped table_input">
					<thead>
						<tr>
							<th class="w50">序号</th>
							<th>供应商名称</th>
							<th class="w200">关联的包名</th>
							<th class="w80">是否到场</th>
							<th class="w280">投标文件</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplier}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc">${vs.index+1}</td>
								<td class="tl">
									${supplier.supplierName}
									<input type="hidden" value="${supplier.id}"/>
								</td>
								<td>
									<c:if test="${empty supplier.isturnUp}">
										<input class="w100p" readonly id="packageName${vs.index}" value="" placeholder="请选择包" onclick="showPackageType('${supplier.id}','${vs.index}');" type="text">
										<input readonly id="packageId${vs.index}" name="packageId" type="hidden">
									</c:if>
									<c:if test="${not empty supplier.isturnUp and supplier.isturnUp == 0}">
										${supplier.packageName}
									</c:if>
								</td>
								<td class="tc p0">
									<c:if test="${empty supplier.isturnUp}">
										<input readonly class="w100p" id="isturnUp${vs.index}" type="text" value=""/>
									</c:if>
									<c:if test="${not empty supplier.isturnUp and supplier.isturnUp == 0}">已到场</c:if>
									<c:if test="${not empty supplier.isturnUp and supplier.isturnUp == 1}">未到场</c:if>
									<input type="hidden" value="${supplier.isturnUp}" name="isturnUp"/>
								</td>
								<td class="pr over_hideen">
									<c:if test="${flag == false}">
									<div id="upload_tag_${vs.index+1}" class="upload_tag" style="position: absolute; top: -100px; left: 5x; width: 100%;">
									<c:if test="${fn:length(listSupplier) > 1}">
										<u:upload id="${supplier.groupsUpload}" exts="txt,rar,zip,doc,docx,pdf" multiple="true" groups="${supplier.groupsUploadId}" buttonName="上传附件" businessId="${supplier.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
										<u:show showId="${supplier.groupShow}" groups="${supplier.groupShowId}" businessId="${supplier.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
							  	</c:if>
							  	<c:if test="${fn:length(listSupplier) == 1}">
										<u:upload id="${supplier.groupsUpload}" exts="txt,rar,zip,doc,docx,pdf" businessId="${supplier.proSupFile}" buttonName="上传附件" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
										<u:show showId="${supplier.groupShow}" businessId="${supplier.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
								  </c:if>
						    </div>
						    </c:if>
						    <c:if test="${flag == true}">
						    	<a class="mt3 color7171C6" href="javascript:download('${supplier.bidFileId}', '${sysKey}')">${supplier.bidFileName}</a>
						    </c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${flag == false}">
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" value="提交" type="button" onclick="updateSaleTender()">
				</div>
			</c:if>
	</body>

</html>