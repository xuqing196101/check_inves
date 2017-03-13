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
              $("#add_form").submit();
            }
          }
        });
      });
  
  
      function resetQuery() {
        $("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购计划监督</a>
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
      <form id="add_form" action="${pageContext.request.contextPath }/planSupervision/list.html" class="mb0" method="post" >
        <input type="hidden" name="page" id="page">
        <ul class="demand_list">
          <li>
            <label class="fl">采购计划名称：</label>
            <span><input type="text" name="fileName" value="${collectPlan.fileName}"/></span>
          </li>
               
          <li>
            <label class="fl">采购金额：</label>
            <span><input type="text" name="budget" value="${collectPlan.budget}"/> </span>
          </li>
          
          <li>
            <label class="fl">状态：</label>
            <span>
              <select name="status">
                <option selected="selected" value="">请选择</option>
                <option value="1" <c:if test="${collectPlan.status=='1'}"> selected</c:if> >审核轮次设置</option>
		            <option value="3" <c:if test="${collectPlan.status=='3'}"> selected</c:if> > 第一轮审核</option>
		            <option value="4" <c:if test="${collectPlan.status=='4'}"> selected</c:if> > 第二轮审核人员设置</option>
		            <option value="5" <c:if test="${collectPlan.status=='5'}"> selected</c:if> > 第二轮审核</option>
		            <option value="6" <c:if test="${collectPlan.status=='6'}"> selected</c:if> > 第三轮审核人员设置</option>
		            <option value="7" <c:if test="${collectPlan.status=='7'}"> selected</c:if> > 第三轮审核</option>
		            <option value="8" <c:if test="${collectPlan.status=='8'}"> selected</c:if> > 审核结束</option>
		            <option value="12" <c:if test="${collectPlan.status=='12'}"> selected</c:if> > 未下达</option>
		            <option value="2" <c:if test="${collectPlan.status=='2'}"> selected</c:if> > 已下达</option>
              </select>
            </span>
          </li>       
        </ul>
        <input class="btn fl mt1" type="submit" value="查询" /> 
        <input class="btn fl mt1" type="button" value="重置" onclick="resetQuery()"  /> 
      </form>
    </h2>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">采购计划名称</th>
              <th class="info">预算总金额（万元）</th>
              <th class="info">编制人</th>
              <th class="info">编制时间</th>
              <th class="info">状态</th>
              <th class="info">执行情况</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tl pl20" width="35%" onclick="view('${obj.id}')">${obj.fileName }</td>
              <td class="tr pr20 w140" onclick="view('${obj.id}')">
                <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
              </td>
              <td class="tr pr20 w140" onclick="view('${obj.id}')">${obj.userId}</td>
              <td class="tc " onclick="view('${obj.id}')">
                <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tl pl20" onclick="view('${obj.id}')">
                <c:if test="${obj.status == 1}">审核轮次设置</c:if>
                <c:if test="${obj.status == 2}">已下达</c:if>
                <c:if test="${obj.status == 3}">第一轮审核</c:if>
                <c:if test="${obj.status == 4}">第二轮审核人员设置</c:if>
                <c:if test="${obj.status == 5}">第二轮审核</c:if>
                <c:if test="${obj.status == 6}">第三轮审核人员设置</c:if>
                <c:if test="${obj.status == 7}">第三轮审核</c:if>
                <c:if test="${obj.status == 12}">未下达</c:if>
              </td>
              <td class="tl pl20" onclick="view('${obj.id}')">
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>

  </body>

</html>