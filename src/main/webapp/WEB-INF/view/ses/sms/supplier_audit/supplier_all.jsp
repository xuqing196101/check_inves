<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>供应商列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<head>
			<%@ include file="/WEB-INF/view/common.jsp" %>
			<title>供应商列表</title>
			<script type="text/javascript">
				$(function() {
					laypage({
						cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
						pages: "${result.pages}", //总页数
						skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
						skip: true, //是否开启跳页
						total: "${result.total}",
						startRow: "${result.startRow}",
						endRow: "${result.endRow}",
						groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
						curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
							return "${result.pageNum}";
						}(),
						jump: function(e, first) { //触发分页后的回调
							if(!first) { //一定要加此判断，否则初始时会无限刷新
								$("#page").val(e.curr);
								$("#form1").submit();
							}
						}
					});
				});
			
				/** 全选全不选 */
				function selectAll() {
					var checklist = document.getElementsByName("chkItem");
					var checkAll = document.getElementById("checkAll");
					if(checkAll.checked) {
						for(var i = 0; i < checklist.length; i++) {
							checklist[i].checked = true;
						}
					} else {
						for(var j = 0; j < checklist.length; j++) {
							checklist[j].checked = false;
						}
					}
				}

				/** 单选 */
				function check() {
					var count = 0;
					var checklist = document.getElementsByName("chkItem");
					var checkAll = document.getElementById("checkAll");
					for(var i = 0; i < checklist.length; i++) {
						if(checklist[i].checked == false) {
							checkAll.checked = false;
							break;
						}
						for(var j = 0; j < checklist.length; j++) {
							if(checklist[j].checked == true) {
								checkAll.checked = true;
								count++;
							}
						}
					}
				}
				//审核
				function shenhe(id) {
					if(id == null) {
						var size = $(":radio:checked").size();
						if(!size) {
							layer.msg("请选择供应商 !", {
								offset: '300px',
							});
							return;
						}
						var id = $(":radio:checked").val();
					}
					var state = $("#" + id + "").parents("tr").find("td").eq(5).text().trim();
					/* var state = $("#"+id+"").text().trim(); */
					var isExtract = $("#" + id + "_isExtract").text();
					if(state == "审核通过" || state == "审核退回" || state == "审核未通过" || state == "复核通过" || state == "复核未通过" || state == "合格" || state == "不合格") {
						layer.msg("请选择待审核项 !", {
							offset: '100px',
						});
						return;
					}
					//抽取之后的才能复核
					/*   if(state == "未抽取"){
					 	layer.msg("该供应商未抽取 !", {
					     	offset : '100px',
					     });
					     return;
					 } */

					$("input[name='supplierId']").val(id);
					$("#shenhe_form_id").submit();
				}

				//重置搜索栏
				function resetForm() {
					$("input[name='supplierName']").val("");
					//还原select下拉列表只需要这一句
					$("#status option:selected").removeAttr("selected");
					$("#businessType option:selected").removeAttr("selected");
				}
			</script>
		</head>

		<body>
			<!--面包屑导航开始-->
			<div class="margin-top-10 breadcrumbs ">
				<div class="container">
					<ul class="breadcrumb margin-left-0">
						<li>
							<a href="#"> 首页</a>
						</li>
						<li>
							<a href="#">供应商管理</a>
						</li>
						<c:choose>
							<c:when test="${sign == 3}">
								<li>
									<a href="#">供应商实地考察</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="#">供应商审核</a>
								</li>
							</c:otherwise>
						   </c:choose>
						
							<c:if test="${sign == 1}">
							<li class="active">
								<a href="#">供应商审核列表</a>
							</li>
							</c:if>
							<c:if test="${sign == 2}">
							<li class="active">
								<a href="#">供应商复核列表</a>
							</li>
							</c:if>
					</ul>
				</div>
			</div>
			<div class="container">
				<div class="headline-v2">
					<h2>供应商列表</h2>
				</div>
				<!-- 搜索 -->
				<h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/supplierAudit/supplierAll.html"  method="post" id="form1" class="mb0"> 
      <input type="hidden" name="page" id="page">
      <input type="hidden" name="sign" value="${sign}">
      <ul class="demand_list">
      <li class="fl">
	      <label class="fl">供应商名称：</label> 
	      <input class="" name="supplierName" type="text" value="${supplierName }">
      </li>
	      <li class="fl">
		      <label class="fl">状态：</label> 
		      <select name="status" class="w178" id="status">
		        <option value="">全部</option>
		        	<c:if test="${sign eq '1' }">
		        		<option <c:if test="${state == 0 }">selected</c:if> value="0">待审核</option>
		            <option <c:if test="${state == 1 }">selected</c:if> value="1">审核通过 </option>
		            <option <c:if test="${state == 2 }">selected</c:if> value="2">审核退回</option>
		            <option <c:if test="${state == 3 }">selected</c:if> value="3">审核未通过</option>
		        	</c:if>
		        	<c:if test="${sign eq '2' }">
		        		<option <c:if test="${state == 4 }">selected</c:if> value="4">待复核</option>
		            <option <c:if test="${state == 5 }">selected</c:if> value="5">复核通过</option>
		            <option <c:if test="${state == 6 }">selected</c:if> value="6">复核未通过 </option>
		        	</c:if>
		        	<c:if test="${sign eq '3' }">
		        	  <option <c:if test="${state == 5 }">selected</c:if> value="5">待考察</option>
		        		<option <c:if test="${state == 7 }">selected</c:if> value="7">合格</option>
	              <option <c:if test="${state == 8 }">selected</c:if> value="8">不合格</option>
		        	</c:if>
	            
	            
	            
		      </select> 
	       </li>
       <li class="fl">
		      <label class="fl">企业性质：</label> 
		        <select name="businessType" id="businessType" class="w178">
		          <option value="">全部</option>
		          <c:forEach var="type" varStatus="vs" items="${enterpriseTypeList}">
		            <option <c:if test="${businessTypeId eq type.id }">selected</c:if> value="${type.id}">${type.name}</option>
		          </c:forEach>
		       </select> 
		    </li>
		   <%-- <li class="fl">
		      <label class="fl">企业类型：</label> 
		        <select name="supplierType" class="mb0 mt5">
		          <option value="">全部</option>
		          <c:forEach var="type" varStatus="vs" items="${supplierType}">
		            <option value="${type.name}">${type.name}</option>
		          </c:forEach>
		       </select> 
		    </li> --%>
        </ul>
        <input type="submit" class="btn fl" value="查询" />
			  <button onclick="resetForm();" class="btn fl" type="button">重置</button>
      </form>
    </h2>
				<!-- 表格开始-->
				<div class="col-md-12 pl20 mt10">
						<c:if test="${sign == 1 || sign == 2}"><button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button></c:if>
						<c:if test="${sign == 3}"><button class="btn btn-windows check" type="button" onclick="shenhe();">考察</button></c:if>
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover hand">
						<thead>
							<tr>
								<th class="info w50">选择</th>
								<th class="info w50">序号</th>
								<th class="info">供应商名称</th>
								<th class="info">企业类型</th>
								<th class="info">企业性质</th>
								<th class="info w100">状态</th>
							</tr>
						</thead>
						<c:forEach items="${result.list }" var="list" varStatus="page">
							<tr>
								<td class="tc w30"><input name="id" type="radio" value="${list.id}"></td>
								<td class="tc w50" onclick="shenhe('${list.id }');">${page.count}</td>
								<td class="tl" onclick="shenhe('${list.id }');">${list.supplierName }</td>
								<td class="tl" onclick="shenhe('${list.id }');">${list.supplierTypeNames}</td>
								<td class="tc" onclick="shenhe('${list.id }');">
								  <c:forEach items="${enterpriseTypeList}" var="type">
								  	 <c:if test="${list.businessType == type.id}">${type.name}</c:if>
								  </c:forEach>
								</td>
								<td class="tc w100" id="${list.id}" onclick="shenhe('${list.id }');">
									<c:if test="${list.status == 0}"><span class="label rounded-2x label-u">待审核</span></c:if>
									<c:if test="${list.status == 1}"><span class="label rounded-2x label-dark">审核通过</span></c:if>
									<c:if test="${list.status == 2}"><span class="label rounded-2x label-dark">审核退回</span></c:if>
									<c:if test="${list.status == 3}"><span class="label rounded-2x label-dark">审核未通过</span></c:if>
									<c:if test="${list.status == 4}"><span class="label rounded-2x label-u">待复核</span></c:if>
									<c:if test="${list.status == 5 and sign == 2}"><span class="label rounded-2x label-dark">复核通过</span></c:if>
									<c:if test="${list.status == 6}"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
									<c:if test="${list.status == 5 and sign == 3}"><span class="label rounded-2x label-u">待考察</span></c:if>
									<c:if test="${list.status == 7}"><span class="label rounded-2x label-dark">合格</span></c:if>
									<c:if test="${list.status == 8}"><span class="label rounded-2x label-dark">不合格</span></c:if>
								</td>
								<%-- <td class="tc" id="${list.id }_isExtract" >${list.isExtract}</td> --%>
							</tr>
						</c:forEach>
					</table>
					<div id="pagediv" align="right"></div>
				</div>
			</div>
			<form id="shenhe_form_id" action="${pageContext.request.contextPath}/supplierAudit/essential.html" method="post">
				<input name="supplierId" type="hidden" />
				<input type="hidden" name="sign" value="${sign}">
			</form>
		</body>

</html>