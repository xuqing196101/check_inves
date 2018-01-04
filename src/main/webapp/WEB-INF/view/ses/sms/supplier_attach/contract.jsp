<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>近三年销售合同主要页及相应合同的银行收款进帐单</title>
    <script type="text/javascript">
      $(function() {
        var product = $("#a_id_1").text();
        var sales = $("#a_id_2").text();
        var project = $("#a_id_3").text();
        var service = $("#a_id_4").text();
        //加载默认的页签
        if(product == "物资-生产型合同信息") {
          loadPageOne('tab-1', 'supplierAttachAudit/ajaxContract.html', 'PRODUCT');
          return;
        }
        if(sales == "物资-销售型合同信息") {
          loadPageTwo('tab-2', 'supplierAttachAudit/ajaxContract.html', 'SALES');
          return;
        }
        if(project == "工程合同信息") {
          loadPageThree('tab-3', 'supplierAttachAudit/ajaxContract.html', 'PROJECT');
          return;
        }
        if(service == "服务合同信息") {
          loadPageFour('tab-4', 'supplierAttachAudit/ajaxContract.html', 'SERVICE');
          return;
        }
      });
    </script>
    <script type="text/javascript">
      function loadPageOne(id, url, supplierTypeId) {
        index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var supplierId = $("#supplierId").val();
        var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
        $("#tab-4").html("");
        $("#tab-2").html("");
        $("#tab-3").html("");
        $("#" + id).load(path);
      }

      function loadPageTwo(id, url, supplierTypeId) {
        index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var supplierId = $("#supplierId").val();
        var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
        $("#tab-1").html("");
        $("#tab-4").html("");
        $("#tab-3").html("");
        $("#" + id).load(path);
      }

      function loadPageThree(id, url, supplierTypeId) {
        index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var supplierId = $("#supplierId").val();
        var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
        $("#tab-1").html("");
        $("#tab-2").html("");
        $("#tab-4").html("");
        $("#" + id).load(path);
      }

      function loadPageFour(id, url, supplierTypeId) {
        index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        var supplierId = $("#supplierId").val();
        var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
        $("#tab-1").html("");
        $("#tab-2").html("");
        $("#tab-3").html("");
        $("#" + id).load(path);
      }
    </script>
  </head>

  <body>
    <div class="container">
      <div class="content table_box">
        <ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
          <c:set value="0" var="liCount" />
          <c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
            <c:set value="${liCount+1}" var="liCount" />
            <li id="li_id_1" class="active" onclick="loadPageOne('tab-1','supplierAttachAudit/ajaxContract.html','PRODUCT')">
              <a aria-expanded="true" href="#tab-1" data-toggle="tab" id="a_id_1">物资-生产型合同信息</a>
            </li>
          </c:if>
          <c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
            <li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadPageTwo('tab-2','supplierAttachAudit/ajaxContract.html','SALES')">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" id="a_id_2">物资-销售型合同信息</a>
            </li>
            <c:set value="${liCount+1}" var="liCount" />
          </c:if>
          <c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
            <li id="li_id_4" class="<c:if test=" ${liCount==0} ">active</c:if>" onclick="loadPageFour('tab-4','supplierAttachAudit/ajaxContract.html','SERVICE')">
              <a aria-expanded="false" href="#tab-4" data-toggle="tab" id="a_id_4">服务合同信息</a>
            </li>
            <c:set value="${liCount+1}" var="liCount" />
          </c:if>
        </ul>

        <div class="count_flow">
          <div class="tab-content padding-top-20" id="tab_content_div_id">
            <c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
              <!-- 物资生产型 -->
              <div class="tab-pane active in fade active in height-300" id="tab-1">
              </div>
            </c:if>
            <c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
              <!-- 物资销售型 -->
              <div class="tab-pane active in fade height-300 " id="tab-2">
              </div>
            </c:if>
            <c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
              <!-- 服务 -->
              <div class="tab-pane active in fade height-200 " id="tab-4">
              </div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
    <form id="form_id" action="" method="post">
      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
    </form>
  </body>

</html>