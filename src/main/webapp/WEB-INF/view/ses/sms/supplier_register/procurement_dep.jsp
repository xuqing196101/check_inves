<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
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
		
		// loadRootArea();
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
			var procurementDepId = $(":radio:checked").val();
			$("input[name='procurementDepId']").val(procurementDepId);
			  $("input[name='flag']").val(flag);
			  $("#procurement_dep_form_id").submit();
		}
		
		

	}
	
	

	function loadChildren(obj) {
		var id = $(obj).val();
		if (id) {
			$.ajax({
				url : globalPath + "/area/find_area_by_parent_id.do",
				type : "post",
				dataType : "json",
				data : {
					id : id
				},
				success : function(result) {
					var html = "<option > 请选择</option>";
					for ( var i = 0; i < result.length; i++) {
						html += "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
					}
					$("#children_area_select_id").empty();
					$("#children_area_select_id").append(html);

					// 自动选中
				},
			});
		}
	}

	
	
		function showJiGou(obj){
			$("#purchase_orgs").empty();
			$("#purchase_orgs2").empty();
/* 		$("#thead").empty();
		//采购机构
		var sup = $("#purchaseDepId").val();
		var purDepId="";
		var expertId="${expert.id}";
		if(expertId){
			$.ajax({
				url:'${pageContext.request.contextPath}/expert/getPurDepIdByExpertId.do',
				data:{"expertId":expertId},
				cache: false,
				async: false,
				success:function(data){
					purDepId=data;
				}
			});
		}else{
			purDepId=sup;
		} */
		var shengId = $("#root_area_select_id").val();
		var shiId = $(obj).val();
		var orgId = $("#orgId").val();
		var purDepId = "${currSupplier.procurementDepId}";
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/showJiGou.do',
			data:{"pId":shengId,"zId":shiId},
			//type:"post",
			dataType:"json",
			cache: false,
	        async: false,
			success:function(obj){
				$.each(obj,function(i,result){
					i=i+1;
					var name=result.name;
					var princinpal=result.princinpal;
					var detailAddr=result.detailAddr;
					var mobile = result.mobile;
					if(name==null)name="";
					if(princinpal==null)princinpal="";
					if(detailAddr==null)detailAddr="";
					if(mobile==null)mobile="";
					var flag;
					if (result.flag == '1') {
						flag = "purchase_orgs";
					} else {
						flag = "purchase_orgs2";
					}
					if(purDepId==result.id){
						$("#"+flag).append(
								"<tr align='center' ><td><input checked='checked' type='radio' name='procurementDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}else{
						$("#"+flag).append(
								"<tr align='center' ><td><input type='radio' name='procurementDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}
				});
			}
		});
	}
	
	
		
		function prev(){
			 $("#flag").val("5");
			 $("#items_info_form_id").submit();
		}
		
		///暂存
		function temporarySave(){
			
			var procurementDepId = $("input[name='radio']:checked").val();
			$("input[name='procurementDepId']").val(procurementDepId);
			
			
			$("input[name='flag']").val("1");
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
				type : "post",
				data : $("#procurement_dep_form_id").serializeArray(),
				contextType: "application/x-www-form-urlencoded",
				success:function(msg){
				 
			 	if (msg == 'ok'){
						layer.msg('暂存成功');
					} 
				  if (msg == 'failed'){
						layer.msg('暂存失败');
					}  
				}
			});
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
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">1</i>
 -->						<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">资质文件维护</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">品目合同上传</span> </span> <span class="new_step current  fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_02">申请表承诺书上传</span> 
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
							<input  name="org" id="orgId" value="${orgnization.id  }" type="hidden" />
							<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
							<input name="jsp" type="hidden" />
							<input name="flag"  type="hidden" />
						</form>
						<div class="tab-content padding-top-20">
							<!-- 物资生产型 -->
							<div class="tab-pane fade active in height-300" id="tab-1">
								<div class="margin-bottom-0  categories">
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0"><span class=""> 选择您所在的城市：</span>
											<form action="${pageContext.request.contextPath}/supplier/search_org.html" method="post">
												<div class="select_common">
													<select class="w100 fz13" id="root_area_select_id" name=pid" onchange="loadChildren(this)">
														<option value="">请选择</option>
														<c:forEach  items="${privnce }" var="prin">
													         <c:if test="${prin.id==orgnization.provinceId }">
													          <option value="${prin.id }" selected="selected" >${prin.name }</option>
													         </c:if>
												           <c:if test="${prin.id!=orgnization.provinceId }">
													          <option value="${prin.id }"  >${prin.name }</option>
													         </c:if>
												         </c:forEach>
													</select>
													
													<select class="w100 fz13" id="children_area_select_id" name="cid" onchange="showJiGou(this)">
														 <c:forEach  items="${city }" var="city">
													         <c:if test="${city.id==orgnization.cityId }">
													          <option value="${city.id }" selected="selected" >${city.name }</option>
													         </c:if>
												           <c:if test="${city.id!=orgnization.cityId }">
													          <option value="${city.id }"  >${city.name }</option>
													         </c:if>
												         </c:forEach>
													</select>
<!-- 													<input type="submit" class="btn padding-left-20 padding-right-20 btn_back mt1 ml10" value="查询" />
 -->												</div>
											<!-- 	 <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
												 
												 <select id="children_area_select_id" name="address" > -->
				         
				 <%--           <c:forEach  items="${city }" var="city">
					         <c:if test="${city.id==currSupplier.address }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.address }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach> --%>
				         
				         
								      <!--    </select> -->
								         
								         <!-- </div> -->
				         
											</form>
										</li>
									</ul><!-- <br /> -->
									<h2 class="f16 ">
										推荐采购机构
									</h2>
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info w30"><input type="radio" disabled="disabled"></th>
												<th class="info w50">序号</th>
												<th class="info">采购机构</th>
												<th class="info">联系人</th>
												<th class="info">联系地址</th>
												<th class="info">联系电话</th>
											</tr>
										</thead>
										<tbody id="purchase_orgs"></tbody>
									</table>
									<h2 class="f16 ">
										 其他采购机构
									</h2>
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info w30"><input type="radio" disabled="disabled"></th>
											    <th class="info w50">序号</th>
											    <th class="info">采购机构</th>
											    <th class="info">联系人</th>
											    <th class="info">联系地址</th>
											    <th class="info">联系电话</th>
											</tr>
										</thead>
										<tbody id="purchase_orgs2">
											<c:forEach items="${allPurList}" var="org1" varStatus="vs">
												<tr>
													<td class="tc"><input type="radio" value="${org1.id}" name="procurementDepId" <c:if test="${org1.provinceId==currSupplier.procurementDepId}"> checked='checked' </c:if> /></td>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc">${org1.name}</td>
													<td class="tc">${org1.princinpal}</td>
													<td class="tc">${org1.detailAddr}</td>
													<td class="tc">${org1.mobile}</td>
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
	  	  	   			<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="prev()">上一步</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporarySave()">暂存</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProcurementDep('next')">下一步</button>
	  	  </div>
	  </div>
	  
	  <form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/contract.html" method="post">
		<input name="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="1" id="flag" type="hidden" /> 
	</form>
	
	
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
