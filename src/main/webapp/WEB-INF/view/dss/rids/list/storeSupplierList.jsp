<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>

	<head>
        <%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listSupplier.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listSupplier.total}",
					startRow: "${listSupplier.startRow}",
					endRow: "${listSupplier.endRow}",
					groups: "${listSupplier.pages}" >= 5 ? 5 : "${listSupplier.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${listSupplier.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
						}
					}
				});
			});

			function back() {
				window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzeSuppliers.html?judge=5";
			}

			function chongzhi() {
				$("#supplierName").val('');
				$("#startAuditDate").val('');
				$("#endAuditDate").val('');
				$("#contactName").val('');
				$("#category").val('');
				$("#supplierType").val('');
				$("#categoryIds").val('');
				$("#mobile").val('');
				$("#isProvisional").val('');
				$("#status option:selected").removeAttr("selected");
				$("#address option:selected").removeAttr("selected");
				$("#businessNature option:selected").removeAttr("selected");
				
				$("#form1").submit();
			}
			
			//回显下拉框
			$(function() {
				var optionStatus = $("#status").find("option");
				for(var i = 1; i < optionStatus.length; i++) {
					if("${supplier.status}" == $(optionStatus[i]).val()) {
						optionStatus[i].selected = true;
					}
				}
			});
		</script>
	</head>
	<!--面包屑导航开始-->
	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?judge=5&sign=2')">入库供应商列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<input type="text" id="key" value="" class="empty" /><br/>
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
		</div>
		<div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商信息</h2>
			</div>
			<h2 class="search_detail">
  			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/readOnlyList.html" method="post">
		    	<input type="hidden" name="page" id="page">
		      <input type="hidden" name="judge" value="5">
		      <input type="hidden" name="orgId" value="${ supplier.orgId }">
		      <input type="hidden" name="reqType" value="${ reqType }">
		      <input type="hidden" name="supplierTypeIds" value="${ supplierAnalyzeVo.supplierTypeIds }">
		      <c:if test="${sign != 2 }">
		      	<input type="hidden" name="address" value="${ supplierAnalyzeVo.address }">
		      </c:if>
		      <input type="hidden" name="sign" value="${sign }">
		      <ul class="demand_list">
		      	<li>
            	<label class="fl">供应商名称：</label><span><input id="supplierName" class="w220" name="supplierName" value="${supplier.supplierName }" type="text"></span>
            </li>
            <%-- <li>
		          <label class="fl">用户名：</label><span><input class="w220" id="loginName" name="loginName" value="${supplier.loginName }" type="text"></span>
		        </li> --%>
            <li>
              <label class="fl">联系人：</label><span><input id="contactName" class="w220" name="contactName" value="${supplier.contactName }" type="text"></span>
            </li>
            <%-- <li>
							<label class="fl">手机号：</label>
							<input id="mobile" class="w220" name="mobile" value="${supplier.mobile }" type="text">
						</li> --%>
						<li>
            	<label class="fl">企业性质：</label>
	            <select name="businessNature" id="businessNature" class="w220">
	              <option value=''>全部</option>
	              <c:forEach items="${businessNature}" var="list">
	              	<option <c:if test="${supplier.businessNature eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
	              </c:forEach>
	            </select>
	          </li>  
		         <li>
							<label class="fl">供应商状态：</label>
							<select id="status" name="status" class="w220">
								<option value=''>全部</option>
								<option value="1">审核通过</option>
								<option value="4">待复核</option>
								<option value="5">复核通过</option>
								<option value="6">复核未通过</option>
								<option value="7">待考察</option>
								<option value="8">考察合格</option>
								<option value="9">考察不合格</option>
							</select>
						 </li>
         		 <li>
	          	<label class="fl">审核日期：</label><span><input id="startAuditDate" name="startAuditDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startAuditDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startAuditDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startAuditDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endAuditDate" name="endAuditDate" value='<fmt:formatDate value="${supplier.endAuditDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endAuditDate\')}'})"/>
	            </span>
	          </li>
	          <c:if test ="${sign == 2 }">
              <li>
                <label class="fl">地区：</label>
                <select name="address" id="address" class="w220">
                  <option value=''>全部</option>
                  <c:forEach items="${privnce}" var="list">
                    <option <c:if test="${supplier.address eq list.name }">selected</c:if> value="${list.name }">${list.name }</option>
                  </c:forEach>
                </select>
              </li>
            </c:if>
		       </ul>
		       <div class="col-md-12 clear tc mt10">
	           <button type="button" onclick="submit()" class="btn">查询</button>
	           <button type="button" class="btn" onclick="chongzhi()">重置</button>
	           <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
           </div>
           <div class="clear"></div>
		     </form>
		   </h2>
			<div class="content table_box">
				<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info" width="15%">供应商名称</th>
							<!-- <th class="info">用户名</th> -->
							<th class="info" width="10%">联系人</th>
							<th class="info" width="10%">手机号</th>
							<th class="info" width="10%">审核日期</th>
							<th class="info" width="9%">地区</th>
							<th class="info" width="13%">供应商类型</th>
							<th class="info" width="10%">企业性质</th>
							<th class="info" width="10%">采购机构</th>
							<th class="info">供应商状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
							<tr>
								<td class="tc">${(vs.count)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
								<td>
									<a href="${pageContext.request.contextPath}/supplierQuery/essential.html?reqType=analyze&address=${ supplierAnalyzeVo.address }&businessNature=${ supplierAnalyzeVo.businessNature }&orgId=${ supplierAnalyzeVo.orgId }&supplierTypeIds=${ supplierAnalyzeVo.supplierTypeIds }&judge=5&supplierId=${list.id}&sign=${sign}">${list.supplierName }</a>
								</td>
								<%-- <td class="">${list.loginName }</td> --%>
								<td class="">${list.contactName }</td>
								<td class="tc">${list.mobile }</td>
								<td class="tc">
									<fmt:formatDate value="${list.auditDate }" pattern="yyyy-MM-dd" />
								</td>
								<td class="">${list.name }</td>							
								<td class="">${list.supplierType }</td>
								<td class="tc">${list.businessNature}</td>
								<td class="tl">${list.orgName}</td>
								<td class="tc">
									<%-- <c:if test="${list.status==5 and list.isProvisional == 1 }"><span class="label rounded-2x label-dark">临时</span></c:if> --%>
									<c:if test="${list.status==1 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
									<c:if test="${list.status==4 }"><span class="label rounded-2x label-dark">待复核</span></c:if>
									<c:if test="${list.status==5 and list.isProvisional == 0 }"><span class="label rounded-2x label-u">复核通过</span></c:if>
									<c:if test="${list.status==6 }"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
									<c:if test="${list.status==7 }"><span class="label rounded-2x label-dark">待考察</span></c:if>
									<c:if test="${list.status==8 }"><span class="label rounded-2x label-u">考察合格</span></c:if>
									<c:if test="${list.status==9 }"><span class="label rounded-2x label-dark">考察不合格</span></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>