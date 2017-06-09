<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          skip: true, //是否开启跳页
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#param_form").submit();
            }
          }
        });
      });

      function resetQuery() {
        $("#fileName").attr("value", "");
      }

      function view(id) {
        window.location.href = "${pageContext.request.contextPath }/look/view.html?id=" + id;
      }
      
      function back() {
        window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzePurchasePlan.html"
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
            <a href="javascript:void(0)">采购计划列表</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2 fl">
        <h2>采购计划列表</h2>
      </div>

      <h2 class="search_detail">
        <form id="param_form" action="${pageContext.request.contextPath}/taskassgin/viewPlan.html" method="post" >
          <input type="hidden" name="page" id="page">
          <ul class="demand_list">
            <li>
              <label class="fl"> 计划名称：</label>
              <span>
                <input type="text" name="fileName" id="fileName" value="${collectPlan.fileName }"/>  
                <input type="hidden" name="orgId" value="${orgId}">
                <input type="hidden" name="date" value="${date}">
                <input type="hidden" name="orgnizationId" value="${orgnizationId}">
              </span>
            </li>
          </ul>
          <input class="btn fl" type="submit" value="查询" />
          <input type="button" onclick="resetQuery()" class="btn fl" value="重置"/>
          <div class="clear"></div>
        </form>
      </h2>

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info" width="50%">采购计划名称</th>
              <c:if test="${orgId ne null}">
              <th class="info" width="10%">采购管理部门</th>
              </c:if>
              <c:if test="${orgnizationId ne null}">
              <th class="info" width="10%">采购机构</th>
              </c:if>
              <th class="info" width="15%">预算总金额（万元）</th>
              <th class="info" width="15%">
                <c:if test="${date ne null}">下达时间</c:if>
                <c:if test="${date eq null}">汇总时间</c:if>
              </th>
              <th class="info">状态</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
              <td class="tl" onclick="view('${obj.id}')">${obj.fileName }</td>
              <c:if test="${orgId ne null}">
              <td class="tl" onclick="view('${obj.id}')">${obj.taskId }</td>
              </c:if>
              <c:if test="${orgnizationId ne null}">
              <td class="tl" onclick="view('${obj.id}')">${obj.taskId }</td>
              </c:if>
              <td class="tr" onclick="view('${obj.id}')">
                <fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}" />
              </td>
              <td class="tc" onclick="view('${obj.id}')">
                <fmt:formatDate value="${obj.createdAt }" />
              </td>
              <td class="tc" onclick="view('${obj.id}')">
                <c:if test="${obj.status=='2'}">已下达</c:if>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>
  </body>

</html>