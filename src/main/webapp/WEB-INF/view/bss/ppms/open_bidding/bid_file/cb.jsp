<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="u" uri="/tld/upload"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
function update(obj, supplierId, packageId, projectId, quoteId){
	var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
	var reg = /^\d+\.?\d*$/;
	var flag = false;
	var x,y;  
    oRect = obj.getBoundingClientRect();  
    x=oRect.left-350;  
    y=oRect.top;
	if(!reg.exec(total)) {
		$(obj).parent().parent().find("td").eq("2").find("input").val('');
		layer.msg("金额必填且为数字,请正确填写",{offset: [y, x]});
		return;
	}
	var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
	var isTurnUp = $(obj).parent().parent().find("td").eq("4").find("option:selected").text();
	if (isTurnUp == '未到场') {
		isTurnUp = 1;
	} else {
		isTurnUp = 2;
	}
	$.ajax({
		url:"${pageContext.request.contextPath}/open_bidding/save.html?total=" + total +
		 "&supplierId="+ supplierId+ "&deliveryTime="+ deliveryTime+ "&isTurnUp="+ isTurnUp + "&packageId="+ packageId + "&projectId="+ projectId+ "&quoteId="+ quoteId,
		success:function(data){
			layer.msg("暂存成功",{offset: [y, x], shade:0.01});
		}
	});
}

function ycDiv(obj, index){
	if ($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
		$(obj).removeClass("jbxx");
		$(obj).addClass("zhxx");
	} else {
		if ($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
			$(obj).removeClass("zhxx");
			$(obj).addClass("jbxx");
		}
	}
	
	var divObj = new Array();
	divObj = $(".p0" + index);
	for (var i =0; i < divObj.length; i++) {
    	if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
    		$(divObj[i]).removeClass("hide");
    	} else {
    		if ($(divObj[i]).hasClass("p0"+index)) {
    			$(divObj[i]).addClass("hide");
    		}
    	}
	};
}
</script>
</head>
<body>
<!-- 表格开始-->  
<div class="clear">
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
			 </h2>
        </div>
        <div class="p0${index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">总价(万元)</th>
					<th class="info">交货期限</th>
					<th class="info">是否到场</th>
					<th class="info">上传投标文件</th>
					<th class="info">操作</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<td class="tc"><input class="w60" value="${treemapValue.total}"  maxlength="16" /></td>
					<td class="tc"><input class="w90" value="<fmt:formatDate value="${treemapValue.deliveryTime }" pattern="YYYY-MM-dd" />"  readonly="readonly" onClick="WdatePicker()" /></td>
					<td class="tc">
						<select>
							<option value="1" <c:if test="${treemapValue.isTurnUp ==1 }">selected = "selected"</c:if>>未到场</option>
							<option value="1" <c:if test="${treemapValue.isTurnUp ==2 }">selected = "selected"</c:if>>已到场</option>
						</select>
					</td>
					<td class="tc">
						<c:if test="${fn:length(treemap.value) > 1}">
							<u:upload id="bf${vs.index}${vsKey.index}" groups="${treemapValue.groupsUpload}" businessId="${treemapValue.suppliers.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
							<u:show showId="bs${vs.index}${vsKey.index}" groups="${treemapValue.groupShow}" businessId="${treemapValue.suppliers.id}" sysKey="${sysKey}" typeId="${typeId}" />
						</c:if>
						<c:if test="${fn:length(treemap.value) == 1}">
							<u:upload id="bf${vs.index}"  businessId="${treemapValue.suppliers.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
							<u:show showId="bs${vs.index}"  businessId="${treemapValue.suppliers.id}" sysKey="${sysKey}" typeId="${typeId}" />
						</c:if>
					</td>
					<td class="tc"><span class="btn btn-windows edit" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')">更新</span></td>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
</div>
</body>
</html>
