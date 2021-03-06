<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      /*分页  */
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

      //查看明细
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/project/particulars.html?id=" + id;
      }

      //重置
      function clearSearch() {
        $("#proName").attr("value", "");
        $("#projectNumber").attr("value", "");
        $("#status option:selected").removeAttr("selected");
      }
      
      function back(){
        window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzePurchaseProject.html"
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">决策支持</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购资源综合展示</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/resAnalyze/list.html')">采购资源展示</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购项目列表</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>采购项目列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/project/selectByProject.html" id="form1" method="post" class="mb0">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="hidden" name="page" id="page">
              <input type="text" name="name" id="proName" value="${projects.name }" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目编号：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="projectNumber" id="projectNumber" value="${projects.projectNumber }" class="w100p h32 f14 mb0">
              <input type="hidden" name="purchaseType" value="${projects.purchaseType}"/>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button class="btn mb0 h32" type="submit">查询</button>
              <button type="reset" class="btn mb0 h32" onclick="clearSearch();">重置</button>
              <button class="btn btn-windows back mb0 mr0 h32" onclick="back()" type="button">返回</button>
            </div>
          </div>
        </div>
      </div>
      </div>
    </form>
    </h2>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th width="25%">项目名称</th>
              <th width="15%">项目编号</th>
              <th width="10%">采购方式</th>
              <th width="17%">创建时间</th>
              <th width="15%">项目状态</th>
              <th>项目负责人</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl" onclick="view('${obj.id}')">${obj.name}</td>
                <td class="tl" onclick="view('${obj.id}')">${obj.projectNumber}</td>
                <td class="tc" onclick="view('${obj.id}')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc" onclick="view('${obj.id}')">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tl">
                  <c:forEach items="${status}" var="status">
                    <c:if test="${status.id == obj.status}">${status.name}
                    </c:if>
                  </c:forEach>
                </td>
                <td class="tl" onclick="view('${obj.id}')">${obj.projectContractor}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>