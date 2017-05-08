<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<jsp:include page="/index_head.jsp"></jsp:include>
	<script type="text/javascript">
		/* 分页 */
		$(function() {
			laypage({
				cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
				pages : "${listSuppliers.pages}", //总页数
				skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				skip : true, //是否开启跳页
				total : "${listSuppliers.total}",
				startRow : "${listSuppliers.startRow}",
				endRow : "${listSuppliers.endRow}",
				groups : "${listSuppliers.pages}" >= 5 ? 5 : "${listSuppliers.pages}", //连续显示分页数
				curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
					var page = location.search.match(/page=(\d+)/);
					return page ? page[1] : 1;
				}(),
				jump : function(e, first) { //触发分页后的回调
					if (!first) { //一定要加此判断，否则初始时会无限刷新
						//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
						//alert(e.curr);
						$("input[name='page']").val(e.curr);
						searchSupplierLevel(0);
					}
				}
			});
			autoSelected("level_select_id", "${level}");
		});
		
		function searchSupplierLevel(sign) {
			if (sign) {
				$("input[name='page']").val(1);
			}
			$("#search_form_id").submit();
		}
		
		//重置按钮事件
		function resetForm() {
	        $("#supplierName").val("");
	        $("#level_select_id").val("");
		}
		
		function autoSelected(id, v) {
			if (v) {
				$("#" + id).find("option").each(function() {
					var value = $(this).val();
					if(value == v) {
						$(this).prop("selected", true);
					} else {
						$(this).prop("selected", false);
					}
				});
			}
		}
	</script>
</head>

<body>
  	<!--面包屑导航开始-->
   	<div class="margin-top-10 breadcrumbs">
      <div class="container">
		   	<ul class="breadcrumb margin-left-0">
		   		<li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li><li><a href="javascript:;">供应商诚信列表</a></li>
		   	</ul>
				<div class="clear"></div>
	  	</div>
   	</div>
	
	<div class="container job-content ">
		<div class="search_box col-md-12 col-sm-12 col-xs-12">
		<form id="search_form_id" class="mb0" action="${pageContext.request.contextPath}/supplier_level/indexList.html" method="post">
			<input name="page" type="hidden" />
				<ul class="demand_list">
					<li>
						<label class="fl">供应商名称：</label>
						<span><input type="text" id="supplierName" name="supplierName" value="${supplierName}"/></span>
					</li>
					<li>
						<label class="fl">等级：</label>
						<span>
							<select id="level_select_id" class="w150" name="level">
								<option selected="selected" value="">全部</option>
								<option value="1">一星级</option>
								<option value="2">二星级</option>
								<option value="3">三星级</option>
								<option value="4">四星级</option>
								<option value="5">五星级</option>
							</select>
						</span>
					</li>
					<button type="button" onclick="searchSupplierLevel(1)" class="btn btn-u-light-grey">查询</button>
					<button onclick="resetForm()" class="btn btn-u-light-grey">重置</button>
				</ul>
				<div class="clear"></div>
			</form>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
			<h2 class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
				<div class="col-md-3 col-xs-4 col-sm-4 tc f16">供应商名称</div>
				<div class="col-md-3 col-xs-4 col-sm-4 tc f16">企业等级</div>
				<div class="col-md-3 col-xs-4 col-sm-4 tc f16">分数</div>
				<div class="col-md-3 col-xs-4 col-sm-4 tc f16">企业性质</div>
			</h2>
			<c:choose>
				<c:when test="${!empty listSuppliers.list}">
					<ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="status">
							<li>
								<div class="col-md-3 col-xs-4 col-sm-4 tc">
									<span class="f18 mr5 fl">·</span>${ supplier.supplierName }</a>
								</div>
								<span class="col-md-3 col-xs-4 col-sm-4 tc">
									${supplier.level}
								</span>
								<div class="col-md-3 col-xs-4 col-sm-4 tc">
									${supplier.score}
								</div>
								<div class="col-md-3 col-xs-4 col-sm-4 tc">
									<c:forEach items="${data }" var="dic">
										<c:if test="${supplier.businessType==dic.id}">
											${dic.name }									
										</c:if>
									</c:forEach>
									</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
           			暂无数据
           		</c:otherwise>
			</c:choose>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
	
	<!--底部代码开始-->
	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
