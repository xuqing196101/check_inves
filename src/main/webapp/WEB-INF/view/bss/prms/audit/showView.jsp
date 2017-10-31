<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
  </head>
  <script type="text/javascript">
    function FixTable(TableID, FixColumnNumber, width, height) {
      if($("#" + TableID + "_tableLayout").length != 0) {
        $("#" + TableID + "_tableLayout").before($("#" + TableID));
        $("#" + TableID + "_tableLayout").empty();
      } else {
        $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
      }
      $('<div id="' + TableID + '_tableFix"></div>' +
        '<div id="' + TableID + '_tableHead"></div>' +
        '<div id="' + TableID + '_tableColumn"></div>' +
        '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
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
      $("#" + TableID + "_tableLayout table").each(function() {
        $(this).css("margin", "0");
      });
      var HeadHeight = $("#" + TableID + "_tableHead thead").height();
      HeadHeight += 2;
      $("#" + TableID + "_tableHead").css("height", HeadHeight);
      $("#" + TableID + "_tableFix").css("height", HeadHeight);
      var ColumnsWidth = 0;
      var ColumnsNumber = 0;
      $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function() {
        ColumnsWidth += $(this).outerWidth(true);
        ColumnsNumber++;
      });
      ColumnsWidth += 2;
      if($.browser.msie) {
        switch($.browser.version) {
          case "7.0":
            if(ColumnsNumber >= 3) ColumnsWidth--;
            break;
          case "8.0":
            if(ColumnsNumber >= 2) ColumnsWidth--;
            break;
        }
      }
      $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
      $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
      $("#" + TableID + "_tableData").scroll(function() {
        $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
        $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
      });
      $("#" + TableID + "_tableFix").css({
        "overflow": "hidden",
        "position": "relative",
        "z-index": "50",
        "background-color": "#F7F7F7"
      });
      $("#" + TableID + "_tableHead").css({
        "overflow": "hidden",
        "width": width - 17,
        "position": "relative",
        "z-index": "45",
        "background-color": "#F7F7F7"
      });
      $("#" + TableID + "_tableColumn").css({
        "overflow": "hidden",
        "height": height - 17,
        "position": "relative",
        "z-index": "40",
        "background-color": "#F7F7F7"
      });
      $("#" + TableID + "_tableData").css({
        "overflow": "scroll",
        "width": width,
        "height": height,
        "position": "relative",
        "z-index": "35"
      });
      if($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
        $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
        $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
      }
      if($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
        $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());
        $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 17);
      }
      $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());
      $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());
      $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());
      $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());
    }
    $(document).ready(function() {
      var boxwidth = $("#content").width();
      FixTable("table", 3, boxwidth, 460);
    });

    $(function() {
      var markTermList = "${markTermList}";
      if(markTermList){
        var index = 2;
	      var divObj = $(".p0" + index);
	      $(divObj).addClass("hide");
	      $("#clear").removeClass("spread");
	      $("#clear").addClass("shrink");
      }
    });

    function ycDiv(obj, index) {
      if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
        $(obj).removeClass("shrink");
        $(obj).addClass("spread");
      } else {
        if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
          $(obj).removeClass("spread");
          $(obj).addClass("shrink");
        }
      }

      var divObj = new Array();
      divObj = $(".p0" + index);
      for(var i = 0; i < divObj.length; i++) {
        if($(divObj[i]).hasClass("p0" + index) && $(divObj[i]).hasClass("hide")) {
          $(divObj[i]).removeClass("hide");
        } else {
          if($(divObj[i]).hasClass("p0" + index)) {
            $(divObj[i]).addClass("hide");
          };
        };
      }
      
      setTimeout(function () {
        var boxwidth = $("#content").width();
        FixTable("table", 3, boxwidth, 460);
      }, 200);
    }
    
    function viewUpload(obj){
      layer.open({
			  content: obj,
			});
    }
  </script>

  <body>

    <div class="container clear">
      <div class="mt10 tc">
        <h2>${project.name}--${pack.name}</h2>
      </div>
      <h2 onclick="ycDiv(this,'${1}')" class="count_flow spread hand" id="package">资格性和符合性评审</h2>
      <div class="p0${1}">
        <form action="" method="post">
          <div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
            <table class="table table-bordered table-condensed table-hover p0 space_nowrap mb0" id="table2">
              <thead>
                <th class="info space_nowrap">资格性和符合性检查项</th>
                <c:set var="suppliers" value="0" />
                <c:forEach items="${extension.supplierList}" var="supplier" varStatus="vs">
                  <c:if test="${fn:contains(supplier.packages,extension.packageId)}">
                    <c:set var="suppliers" value="${suppliers+1}" />
                    <th class="info w120">
                      ${supplier.suppliers.supplierName }
                    </th>
                  </c:if>
                </c:forEach>
              </thead>
              <c:forEach items="${dds}" var="d">
                <tr>
                  <td class="info tc f22" colspan="${suppliers+1}"><b>${d.name}</b></td>
                </tr>
                <c:forEach items="${extension.firstAuditList }" var="first" varStatus="vs">
                  <c:if test="${first.kind == d.id}">
                    <tr>
                      <td>
                        <a href="javascript:void(0);" title="${first.content}">${first.name}</a>
                      </td>
                      <c:forEach items="${extension.supplierList }" var="supplier" varStatus="v">
                        <c:if test="${fn:contains(supplier.packages,extension.packageId)}">
                          <td class="tc">
                            <c:forEach items="${reviewFirstAuditList }" var="r">
                              <c:if test="${isSubmit == 0 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId}">暂无</c:if>
                              <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==0 }">合格</c:if>
                              <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==1 }">
                                <div class="red">不合格</div>
                                <button class="btn" onclick="viewUpload('${r.rejectReason}');" type="button">理由</button>
                              </c:if>
                            </c:forEach>
                          </td>
                        </c:if>
                      </c:forEach>
                    </tr>
                  </c:if>
                </c:forEach>
              </c:forEach>
            </table>
          </div>
        </form>
      </div>
      <div class="clear"></div>
      <c:if test="${markTermList ne null}">
      <h2 onclick="ycDiv(this,'${2}')" class="count_flow spread hand" id="clear">经济技术评审</h2>
      <div class="p0${2}">
        <div class="content" id="content">
          <table id="table" class="table table-bordered table-condensed lockout" style="width:1600px; font-size: medium; max-width:10000px">
            <tr>
              <th class="tc w120" rowspan="2">评审项目</th>
              <th class="tc w260" rowspan="2">评审指标</th>
              <th class="tc w120" rowspan="2">标准分值</th>
              <c:forEach items="${supplierList}" var="supplier">
                <th class="tc">${supplier.suppliers.supplierName}</th>
              </c:forEach>
            </tr>
            <tr>
              <c:forEach items="${supplierList}" var="supplier">
                <th class="t">得分</th>
              </c:forEach>
            </tr>
            <c:forEach items="${markTermList}" var="markTerm">
             <c:if test="${markTerm.checkedPrice!=1 }">
              <c:forEach items="${scoreModelList}" var="score" varStatus="vs">
                <c:if test="${score.markTerm.pid eq markTerm.id}">
                  <tr>
                    <!-- 所属模型 -->
                    <c:set var="model" value="" />
                    <c:if test="${score.typeName == 0}">
                      <c:set var="model" value="模型一A" /></c:if>
                    <c:if test="${score.typeName == 1}">
                      <c:set var="model" value="模型二" /></c:if>
                    <c:if test="${score.typeName == 2}">
                      <c:set var="model" value="模型三" /></c:if>
                    <c:if test="${score.typeName == 3}">
                      <c:set var="model" value="模型四 A" /></c:if>
                    <c:if test="${score.typeName == 4}">
                      <c:set var="model" value="模型五" /></c:if>
                    <c:if test="${score.typeName == 5}">
                      <c:set var="model" value="模型六" /></c:if>
                    <c:if test="${score.typeName == 6}">
                      <c:set var="model" value="模型七" /></c:if>
                    <c:if test="${score.typeName == 7}">
                      <c:set var="model" value="模型八" /></c:if>
                    <c:if test="${score.typeName == 8}">
                      <c:set var="model" value="模型一B" /></c:if>
                    <c:if test="${score.typeName == 9}">
                      <c:set var="model" value="模型四B" /></c:if>
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
                    <input type="hidden" name="supplierId" value="${supplier.supplierId}" />
                    <input type="hidden" name="expertScore" readonly="readonly" <c:forEach items="${scores}" var="sco">
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
            </c:if>
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
        </div>
      </div>
      </c:if>
    </div>
    <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
      <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
    </div>
  </body>

</html>