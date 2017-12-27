<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

<script type="text/javascript">
		//受理退回
 		function returns(){
		 var value=$("#reson").val();
		 value = $.trim(value);
		 if(value != null && value != ""){
			 $("#treson").val(value);
			 $("#status").val(4);
			 $("#table").find("#acc_form").submit();
		 }else{
				layer.tips("退回理由不允许为空","#reson");
		 }
	 }
	 
	 //受理通过
	 function save(){
	 	 var list = $("#table tr:gt(0)");
	 	 for(var i = 0; i < list.length; i++) {
	 		 if($(list[i]).attr("attr") != 'true'){
	 			 var aa = $(list[i]).find("td:eq(11)").children(":first").next().val();
	 			 if($.trim(aa) == "") {
	 				 layer.msg("第"+(i + 1)+"行，请选择采购机构！");
	 				 return;
	 			 }
	 		 }
	 	 }
	 	 $("#status").val(3);
	 	 $("#table").find("#acc_form").submit();
	 }
	 
	 //修改采购方式
	 function purchaseType(obj){
	 	var purchaseTypes = $(obj).find("option:selected").val();
	 	$.trim(purchaseTypes);
	 	 var attr = $(obj).parent().parent().attr("attr");
	 	 if (attr == "true" && purchaseTypes != "") {
	 	 	 var id = $(obj).prev().val();
	 	 	 var nextAll=$(obj).parent().parent().nextAll();
	 	 	 for(var i = 0; i < nextAll.length; i++) {
	 	 	 	 var parentId = $(nextAll[i]).find("td:eq(10)").children(":last").val();
	 	 	 	 if (id == parentId) {
		 	 	 	 var aa = $($(nextAll[i])).find("td:eq(10)").children(":first").next().val($(obj).val());
		 	 	 	 purchaseType(aa);
	 	 	 	 }
	 	 	 }
	 	 } else {
	 	 	 //如果采购方式是单一来源可以添加供应商
	 	   if (purchaseTypes == "${dyly}") {
	 	   		$(obj).parent().next().next().find("input").removeAttr("readonly");
	 	   } else {
	 	   		$(obj).parent().next().next().find("input").val("");
	 	   		$(obj).parent().next().next().find("input").attr("readonly", "readonly");
	 	   }
	 	 }
	 }
	 
	 //修改采购机构
	 function org(obj){
	 	var orgName = $(obj).find("option:selected").val();
	 	$.trim(orgName);
	 	 var attr = $(obj).parent().parent().attr("attr");
	 	 if (attr == "true" && orgName != "") {
	 	 	 var id = $(obj).prev().val();
	 	 	 var nextAll=$(obj).parent().parent().nextAll();
	 	 	 for(var i = 0; i < nextAll.length; i++) {
	 	 	 	 var parentId = $(nextAll[i]).find("td:eq(11)").children(":last").prev().val();
	 	 	 	 if (id == parentId) {
		 	 	 	 var aa = $($(nextAll[i])).find("td:eq(11)").children(":first").next().val($(obj).val());
		 	 	 	 org(aa);
	 	 	 	 }
	 	 	 }
	 	 }
	 }
</script>

</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/accept/list.html');">采购需求受理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box">
				<h2 class="count_flow"><i>1</i>需求主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="disabled" name="name" id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="${list[0].planNo}" disabled="disabled" >
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  disabled="disabled"  value="${list[0].referenceNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类别</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="disabled"  value="${planType}" >
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="disabled" id="mobile" value="${list[0].recorderMobile}"> 
							<span class="add-on">i</span>
						</div>
					</li>
					<c:if test="${list[0].planType eq goods}">
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"  id="dnone" >
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <input type="checkbox" name="" disabled="disabled" <c:if test="${list[0].enterPort==1}">checked="checked"</c:if> value="" />进口
            </div>
         	</li>
         	</c:if>
          <li class="col-md-3 col-sm-6 col-xs-12">
             <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span>
             <u:show showId="detailshow"  delete="false" businessId="${list[0].fileId}" sysKey="2" typeId="${typeId}"/>
          </li>
	   	</ul>
	<div class="padding-top-10 clear">
	 <h2 class="count_flow"><i>2</i>需求明细</h2>
		<div class="content require_ul_list"  id="content">
				<table id="table" class="table table-bordered table-condensed lockout">
					<thead>
						<tr class="space_nowrap">
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">产品名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准</br>（技术参数）</th>
							<th class="info">计量<br>单位</th>
							<th class="info">采购<br>数量</th>
							<th class="info">单价<br>（元）</th>
							<th class="info">预算金额</br>（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式</br>建议</th>
							<th class="info">采购机构<br>建议</th>
							<th class="info">供应商名称</th>
							<th class="info" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>是否申请</br>办理免税</th>
							<th class="info">备注</th>
							<th class="info">附件</th>
						</tr>
					</thead>
					<tbody>
					<form id="acc_form" action="${pageContext.request.contextPath}/accept/update.html" method="post">
						<c:forEach items="${list}" var="obj" varStatus="vs">
							<tr <c:if test="${obj.isParent eq 'true'}">attr='true'</c:if>>
								<td class="tc w50">
							    <div class="w50">${obj.seq} <input type="hidden" value="${obj.id }" name="list[${vs.index }].id"></div>
								</td>
								<td class="tl"> 
							   	<div class="department">${obj.department}
							   		<input type="hidden" name="list[${vs.index}].userId" value="${obj.userId }">
							   	</div>
							  </td>
								<td class="tl">
							  	<div class="goodsname">${obj.goodsName}</div>
								</td>
								<td title="${obj.stand}" class="tl">
							 		<div class="stand">
							   		<c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
							   		<c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
							 		</div>
								</td>
								<td title="${obj.qualitStand}" class="tl">
						  	 	<div class="qualitstand">
							   		<c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
							   		<c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
							 		</div>
								</td>
								<td class="tc">
							    <div class="item">${obj.item}</div>
								</td>
								<td class="tc">
							    <div class="purchasecount">${obj.purchaseCount}</div>
								</td>
								<td class="tr">
							  	<div class="price"><fmt:formatNumber  type="number" pattern="#,##0.00" value="${obj.price }"/></div>
								</td>
								<td class="tr">
							    <div class="budget"><fmt:formatNumber type="number" pattern="#,##0.00"  value="${obj.budget}"/></div>
								</td>
								<td> 
							  	<div class="deliverdate">${obj.deliverDate }</div> 
								</td>
								<td class="p0">
									<input type="hidden" value="${obj.id}">
									<select class="type w120"  onchange="purchaseType(this)" required="required" name="list[${vs.index }].purchaseType" id="select">
									 	<option value="" <c:if test="${obj.isParent eq 'true' }"> selected="selected" </c:if>>请选择</option>
									 	<c:forEach items="${kind}" var="kind" >
			               	<option value="${kind.id}" <c:if test="${kind.id eq obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
			             	</c:forEach>
			            </select>
			            <input type="hidden" value="${obj.parentId}">
								</td>
								<td class="tc p0">
									<input type="hidden" name="ss" value="${obj.id}">
									<select  class="org w200" required="required"  name="list[${vs.index}].organization" onchange="org(this)">
										<option value="">请选择</option>
										<c:forEach items="${org}" var="ss">
											<option value="${ss.id}" <c:if test="${ss.id eq obj.organization}">selected="selected" </c:if> >${ss.shortName}</option>
										</c:forEach>
									</select>
									<input type="hidden" value="${obj.parentId}">
						 			<input type="hidden"  name="history" value="">  
								</td>
								<td class="tl">
									<div class="w80"><input name="list[${vs.index }].supplier" readonly="readonly" value="${obj.supplier}"  /></div>
								</td>
								<td class="tc" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>
							    <div class="w80">${obj.isFreeTax}</div>
								</td>
								<td class="tl">
							    <div class="w160">${obj.memo}</div>
								</td>
								<td class="tl">
									<div class="w100">
										<u:show showId="pShow${vs.index}"  delete="false" businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
									</div> 
								</td>
						  </tr>
						</c:forEach>
						<input type="hidden" name="planNo" value="${planNo}">
				    <input type="hidden" id="status" name="status">
				    <input type="hidden" id="treson" name="reason">
					</form>
					</tbody>
				</table>
			</div>
			</div>
			<div class="col-md-12 col-xs-12 col-sm-12 p0" >
		    <div class="col-md-12 col-xs-12 col-sm-12 p0"> 退回理由：</div>
		    <div class="col-md-12 col-xs-12 col-sm-12 p0">
		    	<textarea id="reson" name="reason" maxlength="800" class="h80 col-md-12 col-xs-12 col-sm-12" title="不超过800个字"></textarea>
        </div>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
				<button class="btn btn-windows save" style="margin-left: 100px;"  onclick="save()" type="button">受理</button> 
				<button class="btn btn-windows back" type="button" onclick="returns();">退回</button> 
				<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</div> 
	</div>
</body>
</html>
