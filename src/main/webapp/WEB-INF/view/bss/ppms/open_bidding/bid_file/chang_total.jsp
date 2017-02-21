<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function back() {
		$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
	}
	var count1 = '${count1}';
	var jsonStr = [];
	function update(obj, supplierId, packageId, projectId, quoteId){
		var reg = /^\d+\.?\d*$/;
		var flag = false;
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left;  
	    y=oRect.top;
	    var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
		var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
		var auditReason = $(obj).parent().parent().find("td").eq("5").find("input").val();
		var isGiveUp = $(obj).parent().parent().find("td").eq("4").find("select").val();
		var date = '${date}';
		var json = {"total":total, "supplierId":supplierId, "deliveryTime":deliveryTime, "packageId":packageId, "projectId":projectId, "quoteId":quoteId, "date":date, "auditReason":auditReason, "isGiveUp":isGiveUp};
		jsonStr.push(json);
		console.log(jsonStr); 
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
		var i = 0;
		if (count1 > 1) {
			i = count1;
		}
		for(i; i < allTable.length; i++) {
			for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
				var total = $(allTable[i].rows).eq(j).find("td").eq("2").find("input").val();
				var time = $(allTable[i].rows).eq(j).find("td").eq("3").find("input").val();
				var auditReason = $(allTable[i].rows).eq(j).find("td").eq("5").find("input").val();
				var isGiveUp = $(allTable[i].rows).eq(j).find("td").eq("4").find("select").val();
				var reg = /^\d+\.?\d*$/;
			    if (isGiveUp == "2" && auditReason == "") {
					layer.msg("必须填写放弃原因",{offset: ['25%', '25%']});
					return;
				}
				if (isGiveUp == "" || isGiveUp == undefined) {
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
				}
			};
		}
		if (count == 0) {
			for(var i = 0; i < allTable.length; i++) {
				for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
					var inputObj = $(allTable[i].rows).eq(j).find("td").eq("0").find("input");
					$(inputObj).click();	
				};
			}
			//alert(JSON.stringify(jsonStr));
			  $.ajax({
		        type: "POST",
		        url: "${pageContext.request.contextPath}/open_bidding/save.html",
		        data: {quoteList:JSON.stringify(jsonStr)},
		        dataType: "json",
		        success: function (message) {
		        		if ('${packId}' == null || '${packId}' == "") {
			        		window.location.href="${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId=${projectId}";
			        	} else {
			        		//window.location.href="${pageContext.request.contextPath}/packageExpert/auditManage.html?projectId=${projectId}" + "&flowDefineId=${flowDefineId}";
			        		$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
			        	}
		        },
		        error: function (message) {
		        }
		    });
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
<c:if test="${not empty count}">
<h2 class="tc">第${count + 1}轮报价</h2>
</c:if>
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
		<table class="table table-bordered table-condensed mt5 left_table table_input">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info w200">供应商名称</th>
					<th class="info w100">总价(万元)</th>
					<th class="info w100">交货期限</th>
					<c:if test="${not empty count}">
					<th class="info w100">状态</th>
					<th class="info w100">放弃原因</th>
					</c:if>
			    </tr>
			</thead>
	    <c:set value="${treemap.value}" var="judgeTreemap"></c:set>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<tr>
				    <td class="tc w50">${vs.index+1 }
			    		<input type="hidden" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')" />
				    </td>
				    <td class="tl">${treemapValue.suppliers.supplierName}</td>
					<td class="tc"><input   maxlength="16" type="text"/></td>
					<td class="tc"><input type="text"/></td>
					<c:if test="${not empty count}">
					<td class="tc">
							<select>
								<option value="">请选择</option>
								<option value="2">放弃报价</option>
							</select>
					</td>
					<td class="tc"><input /></td>
					</c:if>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
		<div class="col-md-12 tc">
		    <c:if test="${not empty judgeTreemap}">
		    	<c:if test="${not empty count}">
		    	<input class="btn btn-windows save" value="结束报价" type="button" onclick="eachTable(this)">
		    	<input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
		    	</c:if>
		    	<c:if test="${empty count}">
		    	<input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
		    	</c:if>
		    </c:if>
		    <c:if test="${empty judgeTreemap}">
		    	<input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
		    </c:if>
				
		</div>
</div>
</body>
</html>
