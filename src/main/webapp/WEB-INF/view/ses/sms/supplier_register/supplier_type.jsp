<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	var zTreeObj;
	var zNodes;
	$(function() {
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do",
				otherParam : {supplierId : "${currSupplier.id}"},
				dataType : "json",
				type : "post",
			},
			check : {
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
		};
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});

	function checkedTree(obj) {
		var nodes = zTreeObj.getCheckedNodes(true);
		var ids = "";
		for ( var i = 0; i < nodes.length; i++) {
			if (i != 0) {
				ids += ",";
			}
			ids += $(nodes[i]).attr("id");
		}
		$("input[name='sign']").val(obj);
		$("input[name='supplierTypeIds']").val(ids);
		$("#supplier_type_form_id").submit();
	}
</script>
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/index_head.jsp"%>
    </c:if>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<%@include file="supplierNav.jsp" %>
		<!--详情开始-->
		<div class="sevice_list col-md-12 container">
		  <h2>产品服务/分类</h2>
		  <div class="col-md-12 p0">
		      <div class="col-md-5 title"><span class="star_red fl">*</span>产品服务/分类：</div>
			  <div class="col-md-7 service_list">
			    <span><input type="checkbox"/>服务类</span>
				<span><input type="checkbox"/>工程类</span>
			  </div>
		    </div>
		  </div>
		
		
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="padding-top-20">
							<div class="margin-bottom-0 tc">
								<!-- <div class="w150 lr0_tbauto">
									<ul id="treeDemo" class="ztree"></ul>
								</div> -->
								<div class="mt40 tc mb50">
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="checkedTree(3'basic_info')">上一步</button>
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="checkedTree(2'supplier_type')">暂存</button>
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="checkedTree(1'professional_info')">下一步</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
	
	<form id="supplier_type_form_id" action="${pageContext.request.contextPath}/supplier_type_relate/perfect_type.html" method="post">
		<input name="id" type="hidden" value="${currSupplier.id}" />
		<input name="sgin" type="hidden" />
		<input name="supplierTypeIds" type="hidden" />
	</form>
	
	<!-- footer -->
	<%-- <c:if test="${currSupplier.status != 7}">
		<jsp:include page="../../../../../index_bottom.jsp" />
	</c:if> --%> 
</body>
</html>
