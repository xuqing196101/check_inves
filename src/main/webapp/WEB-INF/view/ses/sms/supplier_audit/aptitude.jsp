<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>

	<head>
		<%@ include file="/WEB-INF/view/front.jsp" %>
		<script type="text/javascript">
			function saveItems(flag) {
				getCategoryId();
				$("#flag").val(flag);
				$("#items_info_form_id").submit();
			}

			function next() {
				$("#items_info_form_id").submit();
				/*  var id="${currSupplier.id}";
				 window.location.href="${pageContext.request.contextPath}/supplier/contract.html?supplierId="+id; */
			}

			function prev() {

				$("#items_info_form_id").submit();
			}
		</script>

	</head>

	<body>
		<c:if test="${currSupplier.status != 7}">
			<%@ include file="/reg_head.jsp"%>
		</c:if>
		<div class="wrapper">

			<!-- 项目戳开始 -->
			<c:if test="${currSupplier.status != 7}">
				<div class="container clear margin-top-30">
					<h2 class="padding-20 mt40 ml30">
				       	<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"> <i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step  fl"><i class=""> 6</i>
						<div class="line"></div> <span class="step_desc_02">品目合同上传</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
				</div>
			</c:if>
			<c:set value="0" var="length"></c:set>>
			<!--基本信息-->
			<div class="container content height-300">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">

							<table class="table table-bordered">

								<tr>
									<td class="info"> 品目名称</td>
									<td>需要上传的文件</td>
								</tr>
								<c:forEach items="${cateList }" var="obj">
									<tr>
										<td class="info">${obj.categoryName }</td>

										${length+ fn:length(obj.list)}

										<td class="info">
											<c:forEach items="${obj.list }" var="qua" varStatus="vs">

												${qua.name }
												<div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
													<u:upload id="pUp${len-(vs.index+1)}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="1" auto="true" />
													<u:show showId="pShow${len-(vs.index+1)}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
												</div>
											</c:forEach>
										</td>

									</tr>
								</c:forEach>

							</table>

						</div>
					</div>
				</div>
			</div>
		</div>

	</body>

</html>