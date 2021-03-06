<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>

  <head>
    <%@ include file="../../../common.jsp" %>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

  </head>

  <script type="text/javascript">
    $(function() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: "${info.pages}", //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: "${info.total}",
        startRow: "${info.startRow}",
        endRow: "${info.endRow}",
        groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
        curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
          return "${info.pageNum}";
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#page").val(e.curr);
            $("#form1").submit();
          }
        }
      });
    });

    //重置
    function clearSearch() {
      $("#supplierName").attr("value", "");
    }
  </script>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品质量管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/pqinfo/getAll.html')">产品质量结果列表</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>质量结果查询</h2>
      </div>

      <!-- 查询 -->
      <div class="search_detail">
      <form id="form1" action="${pageContext.request.contextPath}/pqinfo/getAllSupplierPqInfo.html" method="post" enctype="multipart/form-data" class="mb0">
      <input type="hidden" name="page" id="page">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="supplierName" id="supplierName" class="w100p h32 f14 mb0" value="${supplierName}">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button class="btn mb0 h32" type="submit">查询</button>
              <button type="reset" class="btn mb0 mr0 h32" onclick="clearSearch();">重置</button>
            </div>
          </div>
        </div>
      </div>
      </div>
      </form>
      </div>

      <!-- 表格开始-->

      <div class="content table_box">
        <table class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">供应商名称</th>
              <th class="info" width="15%">质检合格次数</th>
              <th class="info" width="15%">质检不合格次数</th>
              <th class="info" width="15%">质检合格百分比(%)</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr>
              <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>

              <td class="tl pl20">${obj.supplierName}</td>

              <td class="tc">${obj.successedCount}</td>

              <td class="tc">${obj.failedCount}</td>

              <td class="tc">${obj.successedAvg}</td>

            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>