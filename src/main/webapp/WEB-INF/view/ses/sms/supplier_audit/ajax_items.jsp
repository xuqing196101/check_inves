<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>

  <head>
    <script type="text/javascript">
      $(function() {
        //默认不显示叉
        $("td").each(function() {
          $(this).find("p").hide();
        });

        // 加载完成后关闭layer.load()加载层
        layer.close(loading);
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
              loading = layer.load(1);
              var pageNum = e.curr;
              var supplierId = "${supplierId}";
              var type = "${supplierTypeRelateId}";
              var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=" + type + "&pageNum=" + pageNum;
              $("#tbody_category").load(path);
            }
          }
        });
      });
    </script>
    <script type="text/javascript">
      //重置窗口
      /* function openInfo() {
          layer.open({
            type: 1,
            title: '资质文件和销售合同信息',
            area: ['1200px', '500px'],
            closeBtn: 1,
            shade: 0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '0px',
            shadeClose: false,
            content: $("#openDiv"),
          });
      } */
    </script>
  </head>

  <body>
    <table class="table table-bordered table-hover m_table_fixed_border">
      <tr>
        <td class="info tc w50">序号</td>
        <td class="info tc w100">类别</td>
        <td class="info tc">大类</td>
        <td class="info tc">中类</td>
        <td class="info tc">小类</td>
        <td class="info tc">品种名称</td>
        <td class="info tc">资质文件及销售和同</td>
      </tr>
      <c:forEach items="${itemsList}" var="item" varStatus="vs">
        <tr>
          <td class="tc">${result.pageSize * (result.pageNum - 1) + vs.index + 1}</td>
          <td class="tc">${item.rootNode}</td>
          <td class="tl pl20">${item.firstNode}</td>
          <td class="tl pl20">${item.secondNode}</td>
          <td class="tl pl20">${item.thirdNode}</td>
          <td class="tl pl20">${item.fourthNode}</td>
          <%-- <td class="tc w50 hand">
          <a onclick="reason('${item.firstNode}','${item.secondNode}','${item.thirdNode}','${item.fourthNode}','${item.itemsId}');"  id="${item.itemsId}_hidden" class="editItem"><c:if test="${!fn:contains(passedField,item.itemsId)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if><c:if test="${fn:contains(passedField,item.itemsId)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png' class="hidden"></c:if></a>
          <p id="${item.itemsId}_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
          
          <c:if test="${fn:contains(passedField,item.itemsId)}">
            <img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
          </c:if>
        </td> --%>
          <%-- <td >
          <u:upload id="up${vs.index + 1 }" businessId="${item.itemsId}" buttonName="上传考察报告" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPresentation}" auto="true" /> 
          <u:show showId="show${vs.index + 1 }" businessId="${item.itemsId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPresentation}" />
        </td> --%>
            ${item.itemsId}
          <td class="tc">
            <a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierAudit/qualificationsAndContract.html?secondNode='+${item.secondNode}+)">查看</a>
          </td>
        </tr>
      </c:forEach>
    </table>
    
    
    <!-- <div id="openDiv" class="dnone layui-layer-wrap" >
        <div class="drop_window">
          <h2 class="count_flow"><i>1</i>资质文件维护</h2>
          <ul class="list-unstyled">
            
          </ul>
          
          <h2 class="count_flow"><i>2</i>销售合同</h2>
          <ul class="list-unstyled">
            
          </ul>
    </div> -->
  </body>

</html>