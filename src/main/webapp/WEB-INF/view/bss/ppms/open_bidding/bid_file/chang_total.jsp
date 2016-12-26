<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">



<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function update(obj, supplierId, packageId, projectId, quoteId){
		var reg = /^\d+\.?\d*$/;
		var flag = false;
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left-350;  
	    y=oRect.top;
	    var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
		if(!reg.exec(total)) {
			$(obj).parent().parent().find("td").eq("2").find("input").val('');
			layer.msg("金额必填且为数字,请正确填写",{offset: [y, x]});
			return;
		}
		var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
		if (!deliveryTime) {
			layer.msg("交货时间为必填",{offset: [y, x]});
			return;
		}
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
				window.location.reload();
			}
		});
	}
	
	function eachTable(obj) {
	    //根据保存按钮显示提示信息
	 	var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left - 150;  
	    y=oRect.top - 150;  
		var allTable = document.getElementsByTagName("table");
		var priceStr = "";
		var error = 0;
		for(var i = 0; i < allTable.length; i++) {
			for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
				var span = $(obj).parent().parent().find("td:last").find("span");		
				$(span).click();
			};
		}
	}

	function ycDiv(obj, index){
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
			$(obj).removeClass("spread");
			$(obj).addClass("shrink");
		} else {
			if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
				$(obj).removeClass("shrink");
				$(obj).addClass("spread");
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

	function download(id, key) {
		var form = $("<form>");
		form.attr('style', 'display:none');
		form.attr('method', 'post');
		form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
		$('body').append(form);
		form.submit();
	}
</script>
</head>
<body>
<div id="showDiv" class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
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
					<!-- <th class="info">操作</th> -->
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<c:if test="${not empty treemapValue.total}">
				    	<td class="tc">${treemapValue.total}</td>
				    	<td class="tc"><fmt:formatDate value="${treemapValue.deliveryTime }" pattern="YYYY-MM-dd" /></td>
						<td class="tc">
								<c:if test="${treemapValue.isTurnUp ==2 }">已到场</c:if>
								<c:if test="${treemapValue.isTurnUp ==1 }">未到场</c:if>
						</td>
					</c:if>
					
					<c:if test="${empty treemapValue.total}">
						<td class="tc"><input class="w60"  maxlength="16" /></td>
						<td class="tc"><input class="w90" value="<fmt:formatDate value="${treemapValue.deliveryTime }" pattern="YYYY-MM-dd" />"  readonly="readonly" onClick="WdatePicker()" /></td>
						<td class="tc">
							<select>
								<option value="1" <c:if test="${treemapValue.isTurnUp ==2 }">selected = "selected"</c:if>>已到场</option>
								<option value="1" <c:if test="${treemapValue.isTurnUp ==1 }">selected = "selected"</c:if>>未到场</option>
							</select>
						</td>
					</c:if>
					<td>
						  <c:if test="${empty treemapValue.bidFileName && empty treemapValue.total}">
						    <c:if test="${fn:length(treemap.value) > 1}">
								<u:upload id="${treemapValue.groupsUpload}" exts="txt,rar,zip,doc,docx" groups="${treemapValue.groupsUploadId}" buttonName="上传附件" businessId="${treemapValue.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
								<u:show showId="${treemapValue.groupShow}" groups="${treemapValue.groupShowId}" businessId="${treemapValue.id}" sysKey="${sysKey}" typeId="${typeId}" />
						  	</c:if>
						  	<c:if test="${fn:length(treemap.value) == 1}">
								<u:upload id="${treemapValue.groupsUpload}" exts="txt,rar,zip,doc,docx" businessId="${treemapValue.id}" buttonName="上传附件" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
								<u:show showId="${treemapValue.groupShow}" businessId="${treemapValue.id}" sysKey="${sysKey}" typeId="${typeId}" />
						  	</c:if>
						  </c:if>
						  <c:if test="${not empty treemapValue.bidFileName}">
								<a class="mt3 color7171C6" href="javascript:download('${treemapValue.bidFileId}', '${sysKey}')">${treemapValue.bidFileName}</a>							
						  </c:if>
					</td>
					
					<c:if test="${empty treemapValue.total}">
						<%-- <td class="tc hide"><span class="btn btn-windows git" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')">提交</span></td> --%>
			    		<input type="hidden"  onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')" />
			    	</c:if>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
		<div class="col-md-12 tc">
			<input class="btn btn-windows save" value="保存" type="button" onclick="eachTable(this)">
		</div>
</div>
</body>
</html>
