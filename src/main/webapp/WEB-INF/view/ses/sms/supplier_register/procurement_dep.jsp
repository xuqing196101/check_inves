<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/front.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">

	$(function() {
		var procurementDepId = "${currSupplier.procurementDepId}";
		$(":radio").each(function() {
			var value = $(this).val();
			if (value == procurementDepId) {
				$(this).prop("checked", true);
			}
		});
		
		if ("${currSupplier.status}" == 7) {
			$(":radio").each(function() {
				$(this).prop("disabled", true);
			});
		}
		
		loadRootArea();
	});
	
	
	/** 加载地区根节点 */
	function loadRootArea() {
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_root_area.do",
			type : "post",
			dataType : "json",
			success : function(result) {
				var html = "";
				html += "<option value=''>请选择</option>";
				for(var i = 0; i < result.length; i++) {
					html += "<option id='" + result[i].id + "' value='" + result[i].id + "'>" +  result[i].name + "</option>";
				}
				$("#root_area_select_id").append(html);
			},
		});
	}
	
	/** 保存基本信息 */
	function saveProcurementDep(flag) {
		var size = $(":radio:checked").size();
		if(flag==='prev'){
			  $("input[name='flag']").val(flag);
			$("#procurement_dep_form_id").submit();
		}else{
			if (!size) {
				layer.msg("请选择一个初审采购机构", {
					offset : '300px',
				});
				return;
			}
			var procurementDepId = $("input[name='radio']:checked").val();
	 
			$("input[name='procurementDepId']").val(procurementDepId);
			  $("input[name='flag']").val(flag);
			  $("#procurement_dep_form_id").submit();
		}
		
		

	}
</script>

</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/index_head.jsp"%>
	</c:if>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">1</i>
 -->						<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> <span class="new_step fl"><i class="">6</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="procurement_dep_form_id" action="${pageContext.request.contextPath}/supplier/perfect_dep.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" />
							<input name="procurementDepId" type="hidden" />
							<input name="jsp" type="hidden" />
							<input name="flag" type="hidden" />
						</form>
						<div class="tab-content padding-top-20">
							<!-- 物资生产型 -->
							<div class="tab-pane fade active in height-300" id="tab-1">
								<div class="margin-bottom-0  categories">
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0"><span class=""> 选择您所在的城市：</span>
											<form action="${pageContext.request.contextPath}/supplier/search_org.html" method="post">
												<div class="input-append">
													<select class="w200 fz13" id="root_area_select_id" name="id"></select>
													<input type="submit" class="btn padding-left-20 padding-right-20 btn_back mt1 ml10" value="查询" />
												</div>
											</form>
										</li>
									</ul><br />
									<h2 class="f16 jbxx mt40">
										选择采购机构
									</h2>
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info">选择</th>
												<th class="info">序号</th>
												<th class="info">采购机构名称</th>
												<th class="info">机构代称</th>
												<th class="info">所在城市</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${listOrgnizations1}" var="org1" varStatus="vs">
												<tr>
												
													<td class="tc"><input type="radio" value="${org1.id}" name="radio" <c:if test="${org1.provinceId==currSupplier.procurementDepId}"> checked='checked' </c:if> /></td>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc">${org1.name}</td>
													<td class="tc">${org1.shortName}</td>
													<td class="tc">${org1.provinceName}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	  <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   			<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProcurementDep('prev')">上一步</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProcurementDep('store')">暂存</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProcurementDep('next')">下一步</button>
	  	  </div>
	  </div>
	  
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
