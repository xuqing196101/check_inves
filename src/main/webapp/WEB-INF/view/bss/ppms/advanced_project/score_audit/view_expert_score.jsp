<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>专家评分详情</title>

  <!-- Meta -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  function backUp(){
    $("#tab-6").load("${pageContext.request.contextPath}/adPackageExpert/detailedReview.html?packageId=${packageId}&projectId=${projectId}");
  }
</script>
  <!-- 锁表头js -->
<script type="text/javascript">
    function FixTable(TableID, FixColumnNumber, width, height) {
      var parent_num = $('#' + TableID).parent('div').length;
      var parent_width = $('#' + TableID).parent('div').width();
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
    var HeadHeight = $("#" + TableID + "_tableHead thead").height();
    HeadHeight += 2;
    $("#" + TableID + "_tableHead").css("height", HeadHeight);
    $("#" + TableID + "_tableFix").css("height", HeadHeight);
    var ColumnsWidth = 0;
    var AllColumnsWidth = 0;
    var ColumnsNumber = 0;
    $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {
        ColumnsWidth += $(this).outerWidth(true);
        ColumnsNumber++;
    });
    $("#" + TableID + "_tableColumn tr:last td").each(function () {
        AllColumnsWidth += $(this).outerWidth(true);
    });
    ColumnsWidth += 2;
    console.log(AllColumnsWidth+","+parent_width);
    if (parent_num > 0) {
      if (AllColumnsWidth < parent_width) {
        ColumnsWidth = '100%';
      }
    }
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
            FixTable("table", 3, boxwidth, 460);
        });
function printResult(expertId,projectId,packageId){
     window.location.href="${pageContext.request.contextPath}/adPackageExpert/showViewByExpertIdWord.html?expertId="+expertId+"&projectId="+projectId+"&packageId="+packageId;

} 
</script>
</head>
<body>
  <div class="container">
  <div class="">
    <h2>${expert.relName }</h2>
  </div>
  <div align="right">
  <button class="btn" onclick="printResult('${expertId}','${projectId}','${packageId}');" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
  <div class="content mt0">
  
             <div class="content" id="content">
          <table id="table" class="table table-bordered table-condensed table_input left_table lockout mb0">
        <tr>
               <th class="tc" rowspan="2" width="120">评审项目</th>
              <th class="tc" rowspan="2" width="180">评审指标</th>
              <th class="tc" rowspan="2" width="100">标准分值</th>
              <c:forEach items="${supplierList}" var="supplier">
              <th class="tc">${supplier.supplierName}</th>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach items="${supplierList}" var="supplier">
                  <th class="tc">得分</th>
                </c:forEach>
        </tr>
          <c:forEach items="${markTermList}" var="markTerm">
            <c:forEach items="${scoreModelList}" var="score" varStatus="vs">
              <c:if test="${score.markTerm.pid eq markTerm.id}">
                <tr>
                  <!-- 所属模型 -->
                  <c:set var="model" value=""/>
                  <c:if test="${score.typeName == 0}"><c:set var="model" value="模型一A"/></c:if>
                  <c:if test="${score.typeName == 1}"><c:set var="model" value="模型二"/></c:if>
                <c:if test="${score.typeName == 2}"><c:set var="model" value="模型三"/></c:if>
                <c:if test="${score.typeName == 3}"><c:set var="model" value="模型四 A"/></c:if>
                  <c:if test="${score.typeName == 4}"><c:set var="model" value="模型五"/></c:if>
                  <c:if test="${score.typeName == 5}"><c:set var="model" value="模型六"/></c:if>
                  <c:if test="${score.typeName == 6}"><c:set var="model" value="模型七"/></c:if>
                <c:if test="${score.typeName == 7}"><c:set var="model" value="模型八"/></c:if>
                <c:if test="${score.typeName == 8}"><c:set var="model" value="模型一B"/></c:if>
                <c:if test="${score.typeName == 9}"><c:set var="model" value="模型四B"/></c:if>
                  <td class="tc" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
                  <td class="tc">
                    <a href="javascript:void();" title='所 属 模 型 : ${model}&#10;评 审 指 标 : ${score.name}&#10;评 审 内 容 : ${score.reviewContent}'>
                      <c:if test="${fn:length(score.name) <= 10}">${score.name}</c:if>
                      <c:if test="${fn:length(score.name) > 10}">${fn:substring(score.name, 0, 10)}...</c:if>
                    </a>
                  </td>
                <td class="tc">${score.standardScore}</td>
                <c:forEach items="${supplierList}" var="supplier">
                <td class="tc">
                  <input type="hidden" name="supplierId"  value="${supplier.supplierId}"/>
                  <input type="hidden" name="expertScore" readonly="readonly"
                    <c:forEach items="${scores}" var="sco">
                      <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.supplierId and sco.scoreModelId eq score.id}">value="${sco.score}"</c:if>
                    </c:forEach>
                  />
                  <span><c:forEach items="${scores}" var="sco">
                      <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.supplierId and sco.scoreModelId eq score.id}"><font color="red" class="f18">${sco.score}</font></c:if>
                    </c:forEach></span>
                </td>
                </c:forEach>
              </tr>
            </c:if>
          </c:forEach>
         </c:forEach>
         <tr>
          <td class="tc">合计</td>
          <td class="tc">--</td>
          <td class="tc">--</td>
          <c:forEach items="${supplierList}" var="supplier">
              <td class="tc" >
                <input type="hidden" name="${supplier.supplierId}_total"/>
                <span>
                  <c:set var="sum_score" value="0"/>
                  <c:forEach items="${scores}" var="sco">
                    <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.supplierId}">
                      <c:set var="sum_score" value="${sum_score+sco.score}"/>
                    </c:if>
                  </c:forEach>
                  <font color="red" class="f18">${sum_score}</font>
                  <c:set var="sum_score" value="0"/>
                </span>
              </td>
            </c:forEach>
         </tr>
         </table>
         </div></div>
  </div>
</div>
</body>
</html>
