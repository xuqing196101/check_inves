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
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<!-- 我的页面开始-->
		<div class="">
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
			    <button class="btn">批量上传</button>
				<%-- <u:upload id="upload_id" businessId="${projectId}" multiple="true" buttonName="批量上传"  auto="true" typeId="${typeId}" sysKey="${sysKey}"/> --%>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="w50 info">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">关联的包名</th>
							<th class="info">投标文件</th>
						</tr>
					</thead>
					<c:forEach items="${supplierList }" var="list" varStatus="vs">
						<tr>
							<td class="tc">${vs.index+1}</td>
							<td class="tl">${list.supplierName}</td>
							<td class="tc">${list.packageName }</td>
							<td>
							    <c:if test="${empty list.bidFileName}">
								<c:if test="${fn:length(supplierList) > 1}">
									<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx" groups="${list.groupsUploadId}" buttonName="上传附件" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
									<u:show showId="${list.groupShow}" groups="${list.groupShowId}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
							  	</c:if>
							  	<c:if test="${fn:length(supplierList) == 1}">
									<u:upload id="${list.groupsUpload}" exts="txt,rar,zip,doc,docx" businessId="${list.proSupFile}" buttonName="上传附件" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
									<u:show showId="${list.groupShow}" businessId="${list.proSupFile}" sysKey="${sysKey}" typeId="${typeId}" />
							  	</c:if>
							  	</c:if>
							  	 <c:if test="${not empty list.bidFileName}">
									<a class="mt3 color7171C6" href="javascript:download('${list.bidFileId}', '${sysKey}')">${list.bidFileName}</a>							
						  		 </c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</body>

</html>