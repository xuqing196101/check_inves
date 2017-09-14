<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
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
	  		//获取查看或操作权限
	       	var isOperate = $('#isOperate', window.parent.document).val();
	       	if(isOperate == 0) {
				$(":button").each(function(){ 
					$(this).hide();
	            }); 
	            $("div[id^='upload_tag']").each(function(i){  
  					$(this).hide();
				});	
	            $("#batch_upload").hide();
	            
			}
	    })
		function download(id, key) {
			var form = $("<form>");
			form.attr('style', 'display:none');
			form.attr('method', 'post');
			form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
			$('body').append(form);
			form.submit();
		}
		var jsonStr = [];
		function updateSaleTender() {
			var allTable = document.getElementsByTagName("table");
			for(var j = 1; j < allTable[0].rows.length; j++) {
				var isTurnUp = $(allTable[0].rows).eq(j).find("td").eq("3").find("select").find("option:checked").text();
				var supplierId = $(allTable[0].rows).eq(j).find("td").eq("3").find("select").val();
				if (isTurnUp == '请选择') {
					layer.msg("必须选择是否到场",{offset: ['25%', '25%']});
					return;
				}
			}
			layer.confirm('提交后不可变更?', {title: '提示',offset: ['30%', '30%'],shade: 0.01
				}, function(index) {
					layer.close(index);
					var allTable = document.getElementsByTagName("table");
					for(var j = 1; j < allTable[0].rows.length; j++) {
						var isTurnUp = $(allTable[0].rows).eq(j).find("td").eq("3").find("select").find("option:checked").text();
						var supplierId = $(allTable[0].rows).eq(j).find("td").eq("3").find("select").val();
						if (isTurnUp == '未到场') {
							isTurnUp = 1;
						} else if (isTurnUp == '请选择') {
							isTurnUp = 2;
						} else {
							isTurnUp = 0;
						}
						var json = {"supplierId" : supplierId, "isTurnUp" : isTurnUp};
						jsonStr.push(json);
						//console.log(jsonStr);
					}
					 var projectId = $("#projectId").val();
					 var ends = end(projectId);
					 if(ends){
					 	$.ajax({
				        type: "POST",
				        url: "${pageContext.request.contextPath}/open_bidding/isTurnUp.html?projectId=" + projectId,
				        data: {isTurnUp:JSON.stringify(jsonStr)},
				        dataType: "json",
				        success: function (message) {
				        	if (message == true) {
				        		window.location.reload();
				        	} else {
				        		layer.msg("必须上传投标文件",{offset: ['25%', '25%']});
				        		return;
				        	}
				        },
		    		  });
					 } else {
					 		layer.msg("发售标书环节未结束");
					 }
				});
		}
		
		function end(id){
			var bool = true;
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/open_bidding/end.html?projectId=" + id,
				dataType: "text",
				success: function (message) {
					if(message != "ok"){
						bool = false;
					}
				},
			});
			return bool;
		}
		
		$(function(){
			var allTable = document.getElementsByTagName("table");
			for(var j = 1; j < allTable[0].rows.length; j++) {
				$(allTable[0].rows).eq(j).find("td:first").text(j);;
			}
		});
		
		function yincUpload(obj) {
			var textVal = $(obj).find("option:selected").text();
			var arr = new Array();
			arr = $(obj).parents("tr").find("td:last").find("div");
			
			if (textVal == '已到场') {
				for (var i = 0; i < arr.length; i++) {
					$(arr[i]).removeClass("hide");
				}
			} else {
				for (var i = 0; i < arr.length; i++) {
					$(arr[i]).addClass("hide");
				}
			}
		}
		
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<!-- 我的页面开始-->
		<div class="">
			<!-- 表格开始-->
			<%-- <div class="col-md-12 pl20 mt10" id="batch_upload">
			<c:if test="${flag == false}">
				 <u:upload id="flUpload" exts="txt,rar,zip,doc,docx,pdf" businessId="1234567890-1234567890-1234567890" multiple="true" buttonName="批量上传"  groups="${supplierList[0].groupsUploadId}" auto="true" typeId="${typeId}" sysKey="${sysKey}"/> 
				 <u:show showId="flshow" groups="${supplierList[0].groupShowId}" businessId="1234567890-1234567890-1234567890" sysKey="${sysKey}" typeId="${typeId}" />
			</c:if>
			</div> --%>

				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="w50 info">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">关联的包名</th>
							<th class="info">是否到场</th>
							<th class="info">投标文件</th>
						</tr>
					</thead>
					<c:forEach items="${supplierList}" var="list" varStatus="vs">
						<c:if test="${not empty list.packageName}">
							<tr>
							<td class="tc">${vs.index+1}</td>
							<td class="tl w300">${list.supplierName}</td>
							<td class="tl">${list.packageName }</td>
							<td class="tc">
								<c:if test="${empty list.isturnUp}">
									<select onchange="yincUpload(this)">
										<option value="">请选择</option>
										<option value="${list.id}">已到场</option>
										<option value="${list.id}">未到场</option>
									</select>
								</c:if>
								
								<c:if test="${not empty list.isturnUp and list.isturnUp == 0}">
									已到场
								</c:if>
								<c:if test="${not empty list.isturnUp and list.isturnUp == 1}">
									未到场
								</c:if>
							</td>
							<td>
							    <c:if test="${flag == false}">
							    	<div id="upload_tag_${vs.index+1}">
										<c:if test="${fn:length(supplierList) > 1}">
											<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx,pdf" multiple="true" groups="${list.groupsUploadId}" buttonName="上传附件" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
											<u:show showId="${list.groupShow}" groups="${list.groupShowId}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
									  	</c:if>
									  	<c:if test="${fn:length(supplierList) == 1}">
											<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx,pdf" businessId="${list.proSupFile}" buttonName="上传附件" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
											<u:show showId="${list.groupShow}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
									  	</c:if>
							    	</div>
							  	</c:if>
							  	 <c:if test="${flag == true}">
									<a class="mt3 color7171C6" href="javascript:download('${list.bidFileId}', '${sysKey}')">${list.bidFileName}</a>							
						  		 </c:if>
							</td>
						</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="col-md-12 tc">
				<input type="hidden" id="projectId" value="${projectId}" />
				<c:if test="${flag == false}">
				<input class="btn btn-windows save" value="提交" type="button" onclick="updateSaleTender()">
				</c:if>
			</div>
	</body>

</html>