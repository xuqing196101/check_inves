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

			function clearSearch() {
        $("#name").attr("value", "");
        $("#projectNumber").attr("value", "");
        $("#status option:selected").removeAttr("selected");
      }
      
      function view(code,status){
      	window.location.href = "${pageContext.request.contextPath}/packageAdvice/audit.html?code=" + code + "&status=" + status;
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
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/packageAdvice/list.html')">转竞谈审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>审核列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
    <form action="${pageContext.request.contextPath}/packageAdvice/list.html" id="form1" method="post" class="mb0">
      <ul class="demand_list">
      <li>
        <label class="fl">项目名称： </label>
        <span>
          <input type="hidden" name="page" id="page">
          <input type="text" name="project.name" id="name" value="${packageAdvice.project.name }" />
        </span>
      </li>
      <li>
        <label class="fl">项目编号：</label> 
        <span>
         <input type="text" name="project.projectNumber" id="projectNumber" value="${packageAdvice.project.projectNumber }" />
        </span>
      </li>
      <li>
        <label class="fl">状态：</label>
            <span class="">
              <select name="status" id="status">
                <option selected="selected" value="">全部</option>
                <option value="1" <c:if test="${packageAdvice.status == 1}">selected="selected"</c:if>>待审核</option>
                <option value="2" <c:if test="${packageAdvice.status == 2}">selected="selected"</c:if>>审核中</option>
                <option value="3" <c:if test="${packageAdvice.status == 3}">selected="selected"</c:if>>审核通过</option>
                <option value="4" <c:if test="${packageAdvice.status == 4}">selected="selected"</c:if>>审核不通过</option>
              </select>
            </span>
      </li>
      <button class="btn fl mt1" type="submit">查询</button>
        <button type="reset" class="btn fl mt1" onclick="clearSearch();">重置</button>
    </ul>
    <div class="clear"></div>
    </form>
    </h2>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th width="25%">项目名称</th>
              <th width="15%">项目编号</th>
              <th width="15%">包名</th>
              <th width="10%">采购方式</th>
              <th width="15%">转竞谈时间</th>
              <th width="5%">状态</th>
              <th>项目负责人</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl" onclick="view('${obj.code}','${obj.status}')">${obj.project.name}</td>
                <td class="tl" onclick="view('${obj.code}','${obj.status}')">${obj.project.projectNumber}</td>
                <td class="tl" onclick="view('${obj.code}','${obj.status}')">${obj.packageName}</td>
                <td class="tc " onclick="view('${obj.code}','${obj.status}')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id eq obj.project.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc" onclick="view('${obj.code}','${obj.status}')">
                  <fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tc">
                  <c:if test="${obj.status == 1}">待审核</c:if>
                  <c:if test="${obj.status == 2}">审核中</c:if>
                  <c:if test="${obj.status == 3}">审核通过</c:if>
                  <c:if test="${obj.status == 4}">审核不通过</c:if>
                </td>
                <td class="tl" onclick="view('${obj.code}','${obj.status}')">${obj.project.principal}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>