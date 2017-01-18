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
			layer.confirm('提交后不可变更?', {title: '提示',offset: ['30%', '30%'],shade: 0.01
				}, function(index) {
					layer.close(index);
					var allTable = document.getElementsByTagName("table");
					for(var j = 1; j < allTable[0].rows.length; j++) {
						var isTurnUp = $(allTable[0].rows).eq(j).find("td:last").find("select").find("option:checked").text();
						var supplierId = $(allTable[0].rows).eq(j).find("td:last").find("select").val();
						//alert(isTurnUp + "-" + supplierId);
						if (isTurnUp == '未到场') {
							isTurnUp = 1;
						} else {
							isTurnUp = 0;
						}
						var json = {"supplierId" : supplierId, "isTurnUp" : isTurnUp};
						jsonStr.push(json);
						console.log(jsonStr);
					}
					 var projectId = $("#projectId").val();
					$.ajax({
				        type: "POST",
				        url: "${pageContext.request.contextPath}/open_bidding/isTurnUp.html?projectId=" + projectId,
				        data: {isTurnUp:JSON.stringify(jsonStr)},
				        dataType: "json",
				        success: function (message) {
				        	window.location.reload();
				        },
		    		  });
		   			
		   			//window.location.herf = "${pageContext.request.contextPath}/open_bidding/selectSupplierByProject.html?project=" + projectId;
				});
		}
		
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<!-- 我的页面开始-->
		<div class="">
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				 <u:upload id="flUpload" businessId="1234567890-1234567890-1234567890" multiple="true" buttonName="批量上传"  groups="${supplierList[0].groupsUploadId}" auto="true" typeId="${typeId}" sysKey="${sysKey}"/> 
				 <u:show showId="flshow" groups="${supplierList[0].groupShowId}" businessId="1234567890-1234567890-1234567890" sysKey="${sysKey}" typeId="${typeId}" />
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="w50 info">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">关联的包名</th>
							<th class="info">投标文件</th>
							<th class="info">是否到场</th>
						</tr>
					</thead>
					<c:forEach items="${supplierList }" var="list" varStatus="vs">
						<tr>
							<td class="tc">${vs.index+1}</td>
							<td class="tl">${list.supplierName}</td>
							<td class="tl">${list.packageName }</td>
							<td>
							    <c:if test="${flag == false}">
									<c:if test="${fn:length(supplierList) > 1}">
										<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx" multiple="true" groups="${list.groupsUploadId}" buttonName="上传附件" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
										<u:show showId="${list.groupShow}" groups="${list.groupShowId}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
								  	</c:if>
								  	<c:if test="${fn:length(supplierList) == 1}">
										<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx" businessId="${list.proSupFile}" buttonName="上传附件" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
										<u:show showId="${list.groupShow}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
								  	</c:if>
							  	</c:if>
							  	 <c:if test="${flag == true}">
									<a class="mt3 color7171C6" href="javascript:download('${list.bidFileId}', '${sysKey}')">${list.bidFileName}</a>							
						  		 </c:if>
							</td>
							<td class="tc">
								<c:if test="${empty list.isturnUp}">
									<select>
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
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="col-md-12 tc">
				<input type="hidden" id="projectId" value="${projectId}" />
				<c:if test="${flag == false}">
				<input class="btn btn-windows save" value="提交" type="button" onclick="updateSaleTender()">
				</c:if>
			</div>
		</div>
	</body>

</html>