<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
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
	
	$(function(){
		for (var i = 1; i < 20; i++) {
			$(".p0" + i).addClass("hide");
		};
		
		var ix = "${ix}";
		if(ix != "null"){
			$("h2").each(function() {
				$(this).removeClass("count_flow");
				$(this).addClass("counts_flow");
			});
			$(".f14").each(function() {
				$(this).removeClass("f14");
				$(this).addClass("f25");
			});
			$(".star_red").each(function() {
				$(this).addClass("f25");
			});
			
			$("th").each(function() {
				$(this).addClass("f25");
			});
			$("td").each(function() {
				$(this).addClass("f25");
			});
			$("#jscb").addClass("f16");
		}
	});
	
	 function openMax(){
	 	window.open("${pageContext.request.contextPath}/open_bidding/viewChangtotal.html?projectId=${projectId}");
	 }; 
	 
	 function printCon(projectId, objId){
	 	 var packageId = $("#packId"+objId).val();
	 	 window.location.href="${pageContext.request.contextPath}/open_bidding/changTotalWord.html?projectId="+projectId+"&packId="+packageId;
	 }
</script>
</head>
<body>
<c:if test="${listLength != 1}">
<div class="tr mt10"><button class="btn" onclick="openMax()">全屏</button></div>
</c:if>
<div id="showDiv" class="clear">
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div class="col-md-12 col-sm-12 col-xs-12 p0">
			 	<c:if test="${vsKey.index ==0 }">
				 	<h2  onclick="ycDiv(this,'${vsKey.index}')"
				 	 <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ' ||
				 	  mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP' ||
				 	   mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHZ' ||
				 	   mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHBTG'}">
				 	class="count_flow hand fl spread" </c:if> class="count_flow spread hand fl">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
					<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
					<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
					<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHBTG'}"><span class="star_red">[该包转竞谈审核不通过]</span></c:if>
					</span>
				 	<span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
				 	</h2>
				 	<div class="fl mt20 ml10">
					 	<button class="btn" onclick="printCon('${projectId}','${vsKey.index}')" <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')]=='YZZ'}"> disabled="disabled"</c:if>>投标报价一览表</button>
				 	</div>
			 	</c:if>
			 	<c:if test="${vsKey.index != 0 }">
				 		<h2  onclick="ycDiv(this,'${vsKey.index}')"
				 	 <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ' ||
				 	  mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP' ||
				 	   mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHZ' ||
				 	   mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHBTG'}">
				 	class="count_flow hand fl shrink" </c:if> class="count_flow shrink fl hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
				 	 <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
				 	 <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
				 	 <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJTSHBTG'}"><span class="star_red">[该包转竞谈审核不通过]</span></c:if>
				 	 </span>
				 	<span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
				 	</h2>
				 	<div class="fl mt20 ml10">
					 	<button class="btn" onclick="printCon('${projectId}','${vsKey.index}')"  <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')]=='YZZ'}"> disabled="disabled"</c:if>>投标报价一览表</button>
				 	</div>
			 	</c:if>
        </div>
         <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'YZZ'
          && mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'ZJZXTP'
          && mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'ZJTSHZ'
          && mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'ZJTSHBTG'}">
        <div class="p0${vsKey.index} clear w100p">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info w120">总价(万元)</th>
					<th class="info  w160">交货期限</th>
				<!-- 	<th class="info w100">状态</th>
					<th class="info w100">放弃原因</th> -->
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<input type="hidden" id="packId${vsKey.index}" value="${treemapValue.packages}"/>
				<tr>
				    <td class="tc w50 f14">${vs.index+1 }</td>
				    <td class="tl f14">${treemapValue.suppliers.supplierName}</td>
				    <td class="tr f14">${treemapValue.total}</td>
				    <td class="tc f14">${treemapValue.deliveryTime }</td>
			<%-- 	    <td class="tc">${treemapValue.isRemoved}</td>
					<td class="tc">${treemapValue.removedReason}</td> --%>
			    </tr>
		</c:forEach>
		</table>
		</div>
		</c:if>
	</c:forEach>
</c:forEach>
</div>
</body>
</html>
