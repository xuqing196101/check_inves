<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
  <!--<![endif]-->

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function ycDiv(obj, index) {
        if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
          $(obj).removeClass("spread");
          $(obj).addClass("shrink");
        } else {
          if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
            $(obj).removeClass("shrink");
            $(obj).addClass("spread");
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
            }
          }
        };
      }

      $(function() {
        for(var i = 1; i < 20; i++) {
          $(".p0" + i).addClass("hide");
        };
      });

      function openMax() {
        window.open("${pageContext.request.contextPath}/open_bidding/viewChangtotal.html?projectId=${projectId}");
      };
    </script>
  </head>

  <body>
    <div id="showDiv" class="col-md-12 col-xs-12 col-sm-12 mt20">
      <c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
        <c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
          <div>
            <c:if test="${vsKey.index ==0 }">
              <h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
          <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
          </h2>
            </c:if>
            <c:if test="${vsKey.index != 0 }">
              <h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
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
                  <th class="info w100">总价(万元)</th>
                  <th class="info w100">交货期限</th>
                </tr>
              </thead>
              <c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
                <tr>
                  <td class="tc w50">${vs.index+1 }</td>
                  <td class="tl">${treemapValue.suppliers.supplierName}</td>
                  <td class="tr">${treemapValue.total}</td>
                  <td class="tc">${treemapValue.deliveryTime }</td>
                </tr>
              </c:forEach>
            </table>
          </div>
        </c:forEach>
      </c:forEach>
    </div>
  </body>

</html>