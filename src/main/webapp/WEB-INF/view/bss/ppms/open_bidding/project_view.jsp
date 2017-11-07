<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
		function view(packId,projectId){
			window.open("${pageContext.request.contextPath}/intelligentScore/viewPackageModel.html?packId="+packId+"&projectId="+projectId);
		}
		 
		</script>
	</head>

	<body>
		<div class="col-md-12 p0">
			<h2 class="list_title">评审项预览</h2>
			<ul class="flow_step">
				<li>
					<a href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
					<i></i>
				</li>

				<li>
					<a href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、报批说明、审批单</a>
					<i></i>
				</li>
				<li class="active">
					<a href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、评审项预览</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}">
						05、采购文件
					</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">06、审核意见</a>
				</li>
			</ul>
		</div>
        <div class="clear mt10">
        </div>
		<table class="table table-bordered table-condensed table-hover table-striped mb0 mt20">
			<thead>
				<tr>
					<th class="w50">序号</th>
					<th>包名</th>
					<th>状态</th>
					<th class="w120">操作</th>
				</tr>
			</thead>
			<tbody>
			    <c:forEach items="${packs}" var="pack" varStatus="v">
			    <tr>
					<td class="tc w30">${v.index+1 }</td>
					<td class="tc">${pack.name}
					</td>
					<td class="tc">
					    <c:if test="${pack.isEditFirst==1 }">
					    已维护检查数据
					    </c:if> 
					</td>
					<td class="tc">
						<button class="btn" type="button" onclick="view('${pack.id}','${pack.projectId }')">查看</button>
					</td>
				</tr>
			    </c:forEach>
			</tbody>
		</table>
	</body>

</html>