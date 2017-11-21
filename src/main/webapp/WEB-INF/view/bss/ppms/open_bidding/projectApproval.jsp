<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
		 $(function(){
			 if("${msg}"  == "1") {
			    layer.alert("请先上传报批说明",{offset: '50px'});
			}else if("${msg}" == "2"){
				layer.alert("请先上传审批文件",{offset: '50px'});
			}
		 });
		 function bidRegister(id,type) {
		        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
		      }
		</script>
	</head>

	<body>
		<div class="col-md-12 p0">
			<h2 class="list_title">报批说明</h2>
			<ul class="flow_step">
				<li>
					<a href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
					<i></i>
				</li>

				<li class="active">
					<a href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、编报说明</a>
					<i></i>
				</li>
				<li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、评审项预览</a>
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
		<div class="clear"></div>
		<table class="mt20">
			<c:if test="${project.confirmFile == null || project.confirmFile == 0 || project.confirmFile == 2}">
				<tr>
					<td> <span class="star_red">*</span>报批说明：</td>
					<td class="w100"><button class="btn btn-windows input m0 w50p" type="button" onclick="bidRegister('${project.id}','16')">模板下载</button></td>
					<td class="w200">
						<u:upload id="c"  buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp,pdf" multiple="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" auto="true" />
						<u:show showId="f"  businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" />
					</td>
				</tr>
				<%-- <tr class="h50">
					<td><span class="star_red">*</span>审批单：</td>
					<td ><button class="btn btn-windows input m0 w50p" type="button" onclick="bidRegister('${project.id}','17')"> 模板下载</button></td>
					<td class="w200">
						<u:upload id="a" buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp,pdf" multiple="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
						<u:show showId="b"  businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
					</td>
				</tr> --%>
			</c:if>
			<c:if test="${project.confirmFile == 1 || project.confirmFile == 3 || project.confirmFile == 4 ||project.confirmFile == 5 }">
				<tr>
				    
					<td>编报说明：</td>
					<td class="w200">
						<u:show showId="f" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" />
					</td>
				</tr>
				<%-- <tr class="h50">
				    
					<td>审批单：</td>
					<td class="w200">
						<u:show showId="b"  delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
					</td>
				</tr> --%>
			</c:if>
		</table>
	</body>

</html>