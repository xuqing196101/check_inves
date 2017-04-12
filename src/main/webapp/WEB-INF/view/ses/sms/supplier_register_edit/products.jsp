<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<script type="text/javascript">

	$(function() {
		if ("${currSupplier.status}" == 7) {
			showReason();
		}
	});
	
	/** 保存基本信息 */
	function saveProducts(flag) {
		$("input[name='flag']").val(flag);
		$("#products_form_id").submit();

	}
	
	
	function addProductsMsg(itemId) {
		var supplierId = $("input[name='id']").val();
		layer.open({
			type : 2,
			title : '添加产品信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '600px', '500px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_products/add_products.html?itemId=' + itemId + '&supplierId=' + supplierId, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
	function checkAll(ele, id) {
		var flag = $(ele).prop("checked");
		$("#" + id).find("input:checkbox").each(function() {
			$(this).prop("checked", flag);
		});

	}
	
	function downloadFile(fileName) {
		$("input[name='fileName']").val(fileName);
		$("#download_form_id").submit();
	}
	
	var tr;
	function addParam(obj,id) {
/* 		var checkbox = $("#" + id).find("input:checkbox:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var productsId = checkbox.val();
		var categoryId = checkbox.parents("tr").find("td").eq(1).attr("id"); */
		tr=$(obj).parent().next().children(":last").children();
		var supplierId=$("#supplier").val();
		layer.open({
			type : 2,
			title : '添加技术参数',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '600px', '350px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/categoryparam/category_param.html?categoryId='+id+'&&supplierId='+supplierId, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
	function deletePro(id) {
		var checkbox = $("#" + id).find("input:checkbox:checked");
		var size = checkbox.size();
		if (size == 0) {
			layer.msg("请至少勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var ids = "";
		checkbox.each(function() {
			if (ids) {
				ids += ",";
			}
			ids += $(this).val();
		});
		var supplierId = $("input[name='id']").val();
		window.location.href = "${pageContext.request.contextPath}/supplier_products/delete.html?supplierId=" + supplierId + "&proIds=" + ids;
	}
</script>
<script type="text/javascript">
	function showReason() {
		var supplierId = "${currSupplier.id}";
		var left = document.body.clientWidth - 500;
		var top = window.screen.availHeight / 2 - 150;
		layer.open({
			type : 2,
			title : '审核反馈',
			closeBtn : 0, //不显示关闭按钮
			skin : 'layui-layer-lan', //加上边框
			area : [ '500px', '300px' ], //宽高
			offset : [top, left],
			shade : 0,
			maxmin : true,
			shift : 2,
			content : '${pageContext.request.contextPath}/supplierAudit/showReasonsList.html?&auditType=products_page' + '&jsp=dialog_products_reason' + '&supplierId=' + supplierId, //url
		});
	}
	
	function downloadFile(obj){
		var id=$(obj).parent().children(":last").val();
	 	var key=1;
	    var form = $("<form>");   
	        form.attr('style', 'display:none');   
	        form.attr('method', 'post');
	        form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
	        $('body').append(form); 
	        form.submit();
	}
	
	
</script>
</head>

<body>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
	<!-- 					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i> -->
						<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">产品信息</span> </span> <span class="new_step fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> <span class="new_step fl"><i class="">6</i> 
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
						<form id="products_form_id" action="${pageContext.request.contextPath}/supplier_products/perfect_products.html" method="post">
						 
							<input name="jsp" type="hidden" />
						    <input name="flag" type="hidden" />
							<div class="tab-content padding-top-20">
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0  categories">
									<!--这是所有品目  -->
										<c:forEach items="${currSupplier.listSupplierItems}" var="category" varStatus="cs">
											 <h2 class="f16 jbxx mt40">${category.categoryName} </h2>
											 <div class="overflow_h">
											 <button type="button" class="btn padding-left-20 padding-right-20 btn_back fr" onclick="addParam(this,'${category.categoryId}')">添加产品信息</button>
											</div>
											<table class="table table-bordered table-condensed mt5">
											
												<thead>
													 <tr>
													 <!--这是所有的品目参数  -->
													 <c:forEach items="${currSupplier.categoryParam}" var="item" varStatus="vs"> 
										  	  				  <c:if test="${category.categoryId==item.cateId }">
															    <th class="info">${item.paramName}</th>
															 </c:if>
													  </c:forEach> 
													 </tr>
												</thead>
											 	 <tr>
											 	 	<td style="display:none"></td>
													 <!--这是所有的品目参数值  -->
													  <c:forEach items="${currSupplier.categoryParam}" var="cate" varStatus="vs"> 
														  <c:forEach items="${currSupplier.paramVleu}" var="obj"  > 
										  	  				  <c:if test="${category.categoryId==cate.cateId and obj.categoryParamId==cate.id }"> 
															    <td  align="center" >${obj.paramValue}</td>
													 	      </c:if>
										 			     </c:forEach> 
										 			   </c:forEach>  
												 </tr>  
										  </table>
									</c:forEach>
									</div>
								</div>
							</div>
						<!-- 	<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('items')">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('products')">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('procurement_dep')">下一步</button>
							</div> -->
							<input type="hidden" name="id" value="${currSupplier.id}">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   			<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('prev')">上一步</button>
						<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('store')">暂存</button>
						<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts('next')">下一步</button>
	  	  </div>
	  </div>
	
	<input type="hidden" value="${currSupplier.id}" id="supplier">
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
