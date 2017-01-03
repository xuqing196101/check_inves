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
	    x=oRect.left;  
	    y=oRect.top;
	    var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
		if(!reg.exec(total)) {
			$(obj).parent().parent().find("td").eq("2").find("input").val('');
			layer.msg("金额必填且为数字,请正确填写",{offset: [y, x]});
			return;
		}
		var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
		deliveryTime = encodeURI(deliveryTime);
		deliveryTime = encodeURI(deliveryTime);
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
				//layer.msg("暂存成功",{offset: [y, x], shade:0.01});
				window.location.reload();
			}
		});
	}
	
	var error = 0;
	function eachTable(obj) {
	    //根据保存按钮显示提示信息
	 	var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left - 150;  
	    y=oRect.top - 150;  
		var allTable = document.getElementsByTagName("table");
		var priceStr = "";
		var count = 0;
		for(var i = 1; i < allTable.length; i++) {
			for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
				var total = $(allTable[i].rows).eq(j).find("td").eq("2").find("input").val();
				var time = $(allTable[i].rows).eq(j).find("td").eq("3").find("input").val();
				var reg = /^\d+\.?\d*$/;
				if (total == "" || time == "") {
					count ++;
						layer.msg("表单未填写完整,单价和交货时间必须正确填写,请检查表单");
						return ;
				}
				if(!reg.exec(total)) {
						count ++;
						layer.msg("表单未填写完整,单价和交货时间必须正确填写,请检查表单");
						return ;
			   }
			};
		}
		if (count == 0) {
			for(var i = 1; i < allTable.length; i++) {
				for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
					var inputObj = $(allTable[i].rows).eq(j).find("td").eq("0").find("input");
					$(inputObj).click();	
				};
			}
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
	$(function(){
		for (var i = 1; i < 20; i++) {
			$(".p0" + i).addClass("hide");
		};
	});
</script>
</head>
<body>
<div id="showDiv" class="clear">
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 	<c:if test="${vsKey.index ==0 }">
				 	<h2  onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
				 	<span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
				 	</h2>
			 	</c:if>
			 	<c:if test="${vsKey.index != 0 }">
				 	<h2  onclick="ycDiv(this,'${vsKey.index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
				 	<span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
				 	</h2>
			 	</c:if>
        </div>
        <div class="p0${vsKey.index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">总价(万元)</th>
					<th class="info">交货期限</th>
					<th class="info">是否到场</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<tr>
				    <td class="tc w50">${vs.index+1 }
				    	<c:if test="${empty treemapValue.total}">
			    			<input type="hidden" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')" />
			    		</c:if>
				    </td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<c:if test="${not empty treemapValue.total}">
				    	<td class="tc">${treemapValue.total}</td>
				    	<td class="tc">${treemapValue.deliveryTime }</td>
						<td class="tc">
								<c:if test="${treemapValue.isTurnUp ==2 }">已到场</c:if>
								<c:if test="${treemapValue.isTurnUp ==1 }">未到场</c:if>
						</td>
					</c:if>
					
					<c:if test="${empty treemapValue.total}">
						<td class="tc"><input class="w60"  maxlength="16" /></td>
						<td class="tc"><input class="w90"/></td>
						<td class="tc">
							<select>
								<option>已到场</option>
								<option>未到场</option>
							</select>
						</td>
					</c:if>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
		<div class="col-md-12 tc">
		   <c:if test="${flag == false }">
			<input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
		   </c:if>
		</div>
</div>
</body>
</html>
