<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>近三年销售合同主要页及相应合同的银行收款进帐单</title>

    <script type="text/javascript">
      $(function() {
        layer.close(index);
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${result.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
              });
              $("#pageNum").val(e.curr);
              var pageNum = $("#pageNum").val();
              var supplierId = $("#supplierId").val();
              var type = "${supplierTypeId}";
              var path = "${pageContext.request.contextPath}/supplierAttachAudit/ajaxContract.html?supplierId=" + supplierId + "&supplierTypeId=" + type + "&pageNum=" + pageNum;
              if(type == "PRODUCT") {
                $("#tab-1").load(path);
              } else if(type == "SALES") {
                $("#tab-2").load(path);
              } else if(type == "PROJECT") {
                $("#tab-3").load(path);
              } else if(type == "SERVICE") {
                $("#tab-4").load(path);
              }
            }
          }
        });
      });
    </script>
  </head>

  <body>
    <c:if test="${supplierTypeId eq 'PRODUCT'}">
      <c:set var="fileShow" value="pShow" />
      <input type="hidden" id="pro_val" value="生产">
    </c:if>
    <c:if test="${supplierTypeId eq 'SALES'}">
      <c:set var="fileShow" value="saleShow" />
      <input type="hidden" id="sal_val" value="销售">
    </c:if>
    <c:if test="${supplierTypeId eq 'PROJECT'}">
      <c:set var="fileShow" value="projectShow" />
      <input type="hidden" id="eng_val" value="工程">
    </c:if>
    <c:if test="${supplierTypeId eq 'SERVICE'}">
      <c:set var="fileShow" value="serShow" />
      <input type="hidden" id="ser_val" value="服务">
    </c:if>

    <form id="formSearch" action="${pageContext.request.contextPath}/supplierAttachAudit/ajaxContract.html">
      <input type="hidden" name="pageNum" id="pageNum">
      <input type="hidden" name="supplierId" id="supplierId" value="${supplierId}">
      <input type="hidden" name="supplierTypeId" id="supplierTypeId" value="${supplierTypeId}">
      <table class="table table-bordered m_table_fixed_border">
        <tr>
          <td class="" rowspan="2">产品名称或小类</td>
          <td colspan="3" class="tc info">销售合同(体现甲乙双方盖章及标的相关页)</td>
          <td colspan="3" class="tc info">证明合同有效履行的相应银行收款进账单</td>
        </tr>
        <tr>
          <c:forEach items="${years}" var="year">
            <td class="tc info">${year}</td>
          </c:forEach>
          <c:forEach items="${years}" var="year">
            <td class="tc info">${year}</td>
          </c:forEach>
        </tr>
        <c:forEach items="${contract}" var="obj" varStatus="vs">
          <tr>
            <td>
              ${obj.fourthNode}
              <c:if test="${obj.fourthNode == null || obj.fourthNode ==''}">
                ${obj.thirdNode}
              </c:if>
              <c:if test="${obj.thirdNode == null || obj.thirdNode ==''}">
                ${obj.secondNode}
              </c:if>
              <c:if test="${obj.secondNode == null || obj.secondNode ==''}">
                ${obj.firstNode}
              </c:if>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.oneContract))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-1}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
              </div>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.twoContract))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-2}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
              </div>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.threeContract))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-3}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
              </div>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.oneBil))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-4}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
              </div>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.twoBil))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-5}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
              </div>
            </td>
            <td class="" <c:if test="${fn:contains(fileModifyField,obj.itemsId.concat(obj.threeBil))}">style="border: 1px solid #FF8C00;"</c:if>>
              <div class="tc w50">
                <u:show delete="false" showId="${fileShow}${(vs.index + 1)*6-6}" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
              </div>
            </td>
          </tr>
        </c:forEach>
      </table>
    </form>
    <div id="pagediv" align="right" class="mb50"></div>
  </body>

</html>