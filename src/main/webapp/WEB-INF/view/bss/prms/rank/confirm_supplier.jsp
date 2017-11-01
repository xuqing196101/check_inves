<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).attr("disabled",true);
            }); 
		}
    })
	function removeSupplier(supplierId, packageId){
		var projectId = "${projectId}";
		var removedReason = layer.prompt({
		    title : '请填写移除的理由：', 
		    formType : 2, 
		    offset : '100px',
		    maxlength: 300,
		},function(text){
			//$("tab-8").load("${pageContext.request.contextPath}/packageExpert/removeSaleTender.html?supplierId="+supplierId+"&packageId="+packageId+"&projectId="+projectId);
			$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/removeSaleTender.do",
				data: {"supplierId": supplierId, "packageId": packageId, "projectId": projectId, "removedReason": text},
				success: function (response) {
					$("#"+supplierId+"_"+packageId).text('已移除');
					$("#"+supplierId+"_"+packageId).next().children().attr("disabled","disabled");
					layer.msg("移除成功!",{offset: '100px'});
					var path = "${pageContext.request.contextPath}/packageExpert/confirmSupplier.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
				      $("#tab-8").load(path);
					/* window.location.reload(); */
				},
				error: function () {
					layer.msg("抱歉,移除失败!",{offset: ['100px', '350px']});
					layer.close(removedReason);
				}
			});
		});
	}
	
	function removed(id,name,removedReason){
	  $("#removedReason").val(removedReason);
	  layer.open({
          type: 1, //page层
          area: ['300px', '300px'],
          title: id+name+'移除理由',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: $("#file")
        });
	}
  </script>
  <body>
	    <h2 class="list_title">合格供应商</h2>
   		<input type="hidden" id="projectId" value="${projectId}">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <!-- <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
			  <th class="w50 info">序号</th>
			  <th class="info">包名</th>
			  <th class="info">供应商名称</th>
			  <th class="info">状态</th>
			  <th class="info">操作</th>
			  <th class="info">备注</th>
			</tr>
			</thead>
			<tbody>
			<c:set var="count" value="0"></c:set>
			<c:forEach items="${packages}" var="p">
				<c:forEach items="${supplierList}" var="supp" varStatus="vs">
					<c:if test="${p.id == supp.packages}">
					  <c:set var="count" value="${count+1}"></c:set>
					  <tr>
					    <td class="tc w50">${count}</td>
					    <td class="tc">${supp.packageNames}</td>
					    <td class="tc">${supp.suppliers.supplierName}</td>
					    <td class="tc" id="${supp.suppliers.id}_${supp.packages}">
					    <c:if test="${supp.isFirstPass == 0 && supp.isRemoved eq '0'}">不合格</c:if>
					    <c:if test="${supp.isFirstPass == 1 && supp.isRemoved eq '0'}">合格</c:if>
					    <c:if test="${supp.isFirstPass == null && supp.isRemoved eq '0'}">符合性和资格性审查未结束</c:if>
					    <c:if test="${supp.isRemoved eq '1'}"><a href="javascript:void(0)" onclick="removed('${supp.packageNames}','${supp.suppliers.supplierName}','${supp.removedReason}')">已移除</a></c:if>
					    <c:if test="${supp.isRemoved eq '2'}">已放弃报价</c:if>
					    </td>
					    <td class="tc"><input <c:if test="${supp.isFirstPass != 1 or supp.isRemoved ne '0' or supp.isFinish == 1}">disabled="disabled"</c:if> type="button" value="移除" onclick="removeSupplier('${supp.suppliers.id}','${supp.packages}')" class="btn"></td>
					    <td class="tc w100" title="${supp.removedReason }">
						    <c:if test="${fn:length (supp.removedReason) > 8}">${fn:substring(supp.removedReason,0,7)}...</c:if>
	                        <c:if test="${fn:length(supp.removedReason) <= 8}">${supp.removedReason}</c:if>
					    </td>
					  </tr>
					</c:if>
				</c:forEach>
			</c:forEach>
			</tbody>
		</table>
		<div id="file" class="drop_window dnone">
      <ul class="list-unstyled">
        <li class="col-md-12 col-sm-6 col-xs-12">
          <span class="col-md-12 col-sm-12 col-xs-12 p0">
            <textarea style="height: 200px;width: 250px" rows="3" cols="1" id="removedReason" disabled="disabled"></textarea>
          </span>
        </li>
        <div class="clear"></div>
      </ul>
    </div>
  </body>
</html>
