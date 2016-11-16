<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
</head>
<script type="text/javascript">
	function cheClick() {
		var roleIds = "";
		var roleNames = "";
		$('input[name="chkItem"]:checked').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			roleIds += arr[0] + ",";
			roleNames += arr[1] + ",";
		});
		$("#roleId").val(roleIds.substr(0, roleIds.length - 1));
		$("#roleName").val(roleNames.substr(0, roleNames.length - 1));
	}
</script>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑系统</a></li>
				<li><a href="#">后台管理</a></li>
				<li class="active"><a href="#">用户管理</a></li>
				<li class="active"><a href="#">增加用户</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 修改订列表开始-->
	<div class="container">
		<div>
			<div class="headline-v2">
				<h2>专家抽取表</h2>
			</div>
			<div>
				<table style="width: 70%"
					class="table table-bordered table-condensed">
					<tr>
						<td  class="bggrey" width="100px">项目名称:</td>
						<td colspan="7" width="150px" id="tName">${ExpExtractRecord.projectName}</td>
					</tr>
					<tr>
						<td  class="bggrey">抽取时间:</td>
						<td colspan="3" align="center"><fmt:formatDate
								value="${ExpExtractRecord.extractionTime}"
								pattern="yyyy年MM月dd日   " /></td>
						<td class="bggrey" >抽取地点:</td>
						<td colspan="3" align="center">${fn:replace(ExpExtractRecord.extractionSites,',','')}</td>
					</tr>
					<tr>
						<td align="center" class="bggrey" height="300px;">抽取条件<br>抽取数量
						</td>
						<td colspan="7"  height="300px;">
							<div class="margin-left-100">
								<c:forEach items="${conditionList}" var="con" varStatus="vs">
									<span style="font-size: 16px;">第${(vs.index+1)}次抽取，抽取条件如下：</span>
									<br />
                                                                                                          专家来源【${con.expertsFrom}】 专家所在地区【${con.address}】 <br />
									<ol>
										<c:forEach items="${con.conTypes }" var="contypes">
											<li>{专家类型 <c:choose>
													<c:when test="${contypes.expertsTypeId==1}">
                                                                                                                                   【技术】
                                          </c:when>
													<c:when test="${contypes.expertsTypeId==2}">
                                                                                                                                           【商务】
                                              </c:when>
													<c:when test="${contypes.expertsTypeId==3}">
                                                                                                                                   【法律】
                                         </c:when>
												</c:choose> ， 采购类别【 ${contypes.categoryName}
												】，专家数量【${contypes.expertsCount}】 }
											</li>
										</c:forEach>
									</ol>

								</c:forEach>

							</div>
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center" class="bggrey">抽取记录</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">专家名称</td>
						<td align="center">联系人</td>
						<td align="center">手机号</td>
						<td align="center">传真</td>
						<td align="center">抽取次数</td>
						<td align="center">能否参加</td>
						<td align="center">不参加理由</td>
					</tr>
					<c:forEach items="${ProjectExtract}" var="pe" varStatus="vse">
						<c:forEach items="${pe}" var="ext" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${ext.expert.relName}</td>
								<td align="center">${ext.expert.relName}</td>
								<td align="center">${ext.expert.relName}</td>
								<td align="center">${ext.expert.relName}</td>
								<td align="center">${vse.index+1}</td>
								<td align="center"><c:if test="${ext.operatingType==1 }">
                                                                                         参加
                            </c:if>
                            <c:if test="${ext.operatingType==2 }">
                                                                                         待定
                            </c:if>
                             <c:if test="${ext.operatingType==3 }">
                                                                                     不参加                                                           
                            </c:if></td>
								<td align="center">${ext.reason}</td>
							</tr>
						</c:forEach>
					</c:forEach>
					<tr>
						<td colspan="7" align="center" class="bggrey">抽取人员</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">姓名</td>
						  <td align="center">手机号</td>
						<td align="center">单位</td>
						<td align="center">职务</td>
<!-- 						<td align="center">军衔</td> -->
						<td colspan="2" align="center">签字</td>
					</tr>
					<tr>
						<td align="center">1</td>
						<td align="center">${ExpExtractRecord.perpleUser.relName}</td>
						<td align="center">${ExpExtractRecord.perpleUser.mobile}</td>
						  <td align="center">${ExpExtractRecord.perpleUser.org.name}</td>
                        <td align="center">${ExpExtractRecord.perpleUser.duties}</td>
<!-- 						<td align="center">军23衔</td> -->
						<td colspan="2" align="center"></td>
					</tr>
					<tr>
						<td colspan="7" align="center" class="bggrey">监督人员</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">姓名</td>
						<td align="center">手机号</td>
						<td align="center">单位</td>
						<td align="center">职务</td>
<!-- 						<td align="center">军衔</td> -->
						<td colspan="2" align="center">签字</td>
					</tr>
					<c:forEach items="${listUser}" var="tuser" varStatus="vs">
						<tr>
							<td align="center">${vs.index+1 }</td>
							<td align="center">${tuser.relName}</td>
                             <td align="center">${tuser.mobile}</td>
                            <td align="center">${tuser.org.name}</td>
                            <td align="center">${tuser.duties}</td>
<!--                            <td align="center">军23衔</td> -->
                            <td colspan="2" align="center"></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="fl padding-10">
				<button class="btn btn-windows git" onclick="history.go(-1)"
					type="button">返回</button>
			</div>
		</div>
	</div>
</body>
</html>
