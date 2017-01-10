<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>专家评分详情</title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>
<jsp:include page="../../common.jsp"></jsp:include>
<script type="text/javascript">
	function backUp(){
		$("#tab-6").load("${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId=${packageId}&projectId=${projectId}");
	}
</script>
  <!-- 锁表头js -->
<script type="text/javascript">
    function FixTable(TableID, FixColumnNumber, width, height) {
    if ($("#" + TableID + "_tableLayout").length != 0) {
        $("#" + TableID + "_tableLayout").before($("#" + TableID));
        $("#" + TableID + "_tableLayout").empty();
    }
    else {
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
    }
    $('<div id="' + TableID + '_tableFix"></div>'
    + '<div id="' + TableID + '_tableHead"></div>'
    + '<div id="' + TableID + '_tableColumn"></div>'
    + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
    var oldtable = $("#" + TableID);
    var tableFixClone = oldtable.clone(true);
    tableFixClone.attr("id", TableID + "_tableFixClone");
    $("#" + TableID + "_tableFix").append(tableFixClone);
    var tableHeadClone = oldtable.clone(true);
    tableHeadClone.attr("id", TableID + "_tableHeadClone");
    $("#" + TableID + "_tableHead").append(tableHeadClone);
    var tableColumnClone = oldtable.clone(true);
    tableColumnClone.attr("id", TableID + "_tableColumnClone");
    $("#" + TableID + "_tableColumn").append(tableColumnClone);
    $("#" + TableID + "_tableData").append(oldtable);
    $("#" + TableID + "_tableLayout table").each(function () {
        $(this).css("margin", "0");
    });
    var HeadHeight = $("#" + TableID + "_tableHead thead").height();
    HeadHeight += 2;
    $("#" + TableID + "_tableHead").css("height", HeadHeight);
    $("#" + TableID + "_tableFix").css("height", HeadHeight);
    var ColumnsWidth = 0;
    var ColumnsNumber = 0;
    $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {
        ColumnsWidth += $(this).outerWidth(true);
        ColumnsNumber++;
    });
    ColumnsWidth += 2;
    if ($.browser.msie) {
        switch ($.browser.version) {
            case "7.0":
                if (ColumnsNumber >= 3) ColumnsWidth--;
                break;
            case "8.0":
                if (ColumnsNumber >= 2) ColumnsWidth--;
                break;
        }
    }
    $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
    $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
    $("#" + TableID + "_tableData").scroll(function () {
        $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
    });
    $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "#F7F7F7" });
    $("#" + TableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });
    if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
        $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
        $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
    }
    if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 17);
    }
    $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());
    $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());
}
$(document).ready(function () {
		var boxwidth = $("#content").width();
            FixTable("table", 1, boxwidth, 460);
        });
        
</script>
</head>
<body>
  <div class="container">
  <div class="">
    <h2>${expert.relName }</h2>
  </div>
  <div align="right">
	<button class="btn" onclick="window.print();" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
  <div class="content mt0">
	
             <div class="content" id="content">
	        <table id="table" style="border-bottom-color: #dddddd; border-top-color: #dddddd; color: #333333; border-right-color: #dddddd; width:1600px; font-size: medium; border-left-color: #dddddd; max-width:10000px"
  border="1" cellspacing="0" cellpadding="0" class="table table-bordered table-condensed table_input left_table lockout">
			  <tr>
			      <th colspan="4"></th>
			      <c:forEach items="${supplierList}" var="supplier">
				      <th colspan="2" class="tc">${supplier.suppliers.supplierName}</th>
				    </c:forEach>
			  </tr>
			  <tr>
		   	  	  <th class="tc">评审项目</th>
		   	      <th class="tc">评审指标</th>
		   	      <th class="tc">指标模型</th>
		   	      <th class="tc">标准分值</th>
		   	      <c:forEach items="${supplierList}" var="supplier">
		   		        <th class="tc w100">评审得分</th>
	   		  	  	</c:forEach>
			  </tr>
			    <c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			    	      <td class="tc" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
			    	      <td class="tc"><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
			 	  		  <td class="tc">
			 	    	    <c:if test="${score.typeName == 0}">模型一A</c:if>
			 	            <c:if test="${score.typeName == 1}">模型二</c:if>
				 	        <c:if test="${score.typeName == 2}">模型三</c:if>
				 	        <c:if test="${score.typeName == 3}">模型四 A</c:if>
				 	        <c:if test="${score.typeName == 4}">模型五</c:if>
				 	        <c:if test="${score.typeName == 5}">模型六</c:if>
				 	        <c:if test="${score.typeName == 6}">模型七</c:if>
				 	        <c:if test="${score.typeName == 7}">模型八</c:if>
				 	        <c:if test="${score.typeName == 8}">模型一B</c:if>
				 	        <c:if test="${score.typeName == 9}">模型四B</c:if>
				 	      </td>
				 	      <td class="tc">${score.standardScore}</td>
				 	      <c:forEach items="${supplierList}" var="supplier">
					 	    <td class="tc">
					 	      <input type="hidden" name="supplierId"  value="${supplier.suppliers.id}"/>
					 	      <input type="hidden" name="expertScore" readonly="readonly"
					 	      	<c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">value="${sco.score}"</c:if>
					 	        </c:forEach>
					 	      />
					 	      <span><c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><font color="red" class="f18">${sco.score}</font></c:if>
					 	        </c:forEach></span>
					 	    </td>
				 	      </c:forEach>
				 	    </tr>
				 	  </c:if>
				 	</c:forEach>
				 </c:forEach>
				 </table>
				 </div></div>
  </div>
</div>
</body>
</html>
