<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>My JSP 'list.jsp' starting page</title>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/comCostDis/select.do?proId=" + proId;
      }
      jQuery.fn.rowspan = function(colIdx) {
    	  return this.each(function(){
	    	  var that;
	    	  $('tr', this).each(function(row) {
		    	  $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
		    		  if (that!=null && $(this).html() == $(that).html()) {
				    	  rowspan = $(that).attr("rowSpan");
				    	  if (rowspan == undefined) {
				    	  	$(that).attr("rowSpan",1);
				    	  	rowspan = $(that).attr("rowSpan");
				    	  }
				    	  rowspan = Number(rowspan)+1;
				    	  $(that).attr("rowSpan",rowspan);
				    	  $(this).hide(); 
				    	  } else {
				    	  	that = this;
				    	  }
			    	  });
		    	  });
	    	  });
    	  }
      $(document).ready(function() {
          $("#table1").rowspan(1);
      })
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价（审价）详细情况</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>产品报价（审价）详细情况</h2>
      </div>
    </div>

    <form action="${pageContext.request.contextPath}/auditSummary/update.html" method="post">

      <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }">
      <input type="hidden" name="proId" class="w230 mb0" value="${proId }">
      <input type="hidden" id="apid" name="id" value="${ap.id }" />
      <div class="container margin-top-5">
        <div class="container padding-left-25 padding-right-25">
          <table class="table table-bordered table-condensed">
            <tr>
              <th class="info">产品名称</th>
              <td><input type="text" name="contractProduct.name" class="m0" value="${ap.contractProduct.name }" /></td>
            </tr>
            <tr>
              <th class="info">生产单位</th>
              <td><input type="text" name="produceUnit" class="m0" value="${ap.produceUnit }"></td>
            </tr>
            <tr>
              <th class="info">订货数量</th>
              <td><input type="text" name="orderAcount" class="m0" value="${ap.orderAcount }" /></td>
            </tr>
            <tr>
              <th class="info">计量单位</th>
              <td><input type="text" name="measuringUnit" class="m0" value="${ap.measuringUnit }" /></td>
            </tr>
            <%-- <tr>
              <th class="info">审核人员</th>
              <td><input type="text" name="auditUser" class="m0" value="${ap.auditUser }" /></td>
            </tr> --%>
          </table>
        </div>
      </div>

        <div class="container padding-left-25 padding-right-25">
          <table id="table1" class="table table-bordered table-condensed" >
              <tr>
                <th class="info">序号</th>
                <th class="info" colspan="2" >项目名称</th>
                <th class="info" >单台报价</th>
                <th class="info" >备注</th>
              </tr>
              <c:forEach items="${list}" var="cc" varStatus="vs">
                <tr>
                  <td class="tc">
                    <input type="hidden" name="plcc[${(vs.index)}].id" value="${cc.id }" />${vs.index+1 }
                    <input type="hidden" name="plcc[${(vs.index)}].status" value="${cc.status }" />
                  </td>
                  <td class="tc">${cc.projectName }</td>
                  <td class="tc">${cc.secondProject }</td>
                  <td class="tc"><input type="text" class="m0" name="plcc[${(vs.index)}].singleOffer" onkeyup="if(isNaN(value))execCommand('undo')" value="${cc.singleOffer }" /></td>
                  <td class="tc"><input type="text" class="m0" name="plcc[${(vs.index)}].remark" value="${cc.remark }" /></td>
                </tr>
              </c:forEach>
          </table>
        </div>

        <div class="col-md-12 col-xs-12 col-sm-12 mt20 tc">
          <button class="btn" type="button" onclick="onStep()">上一步</button>
          <button class="btn btn-windows git" type="submit">提交</button>
        </div>

    </form>

  </body>

</html>