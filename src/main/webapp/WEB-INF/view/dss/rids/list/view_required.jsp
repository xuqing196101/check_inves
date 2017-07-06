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

      function view(no) {
        window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + no + "&&type=2";
      }

      function resetQuery() {
        $("#param_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }

      function back() {
        window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzePurchaseRequire.html"
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
            <a href="javascript:void(0)">采购需求列表</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>采购需求列表</h2>
      </div>

      <h2 class="search_detail">
            <form id="param_form" action="${pageContext.request.contextPath }/purchaser/viewDetamd.html"  method="post" class="mb0">
            <input type="hidden" name="page" id="page">
            <ul class="demand_list">
              <li>
                <label class="fl">采购需求名称：</label><span>
                <input type="text" name="planName" value="${purchaseRequired.planName }" />
              </span>
              </li>
              <li>
                <label class="fl">采购需求文号：</label><span>
                <input  type="text" name="referenceNo" value="${purchaseRequired.referenceNo }" /> 
                <input type="hidden" name="date" value="<fmt:formatDate value='${purchaseRequired.createdAt }' pattern='yyyy' />" />
                <input type="hidden" name="orgId" value="${orgId}"/>
                <input type="hidden" name="planType" value="${purchaseRequired.planType}"/>
              </span>
              </li>
              <li>
                <label class="fl">状态：</label>
                <select  name="status" id="status">
                  <option selected="selected" value="">全部</option>
                  <option value="1" <c:if test="${'1'==purchaseRequired.status}">selected="selected"</c:if>>未提交</option>
                  <option value="4" <c:if test="${'4'==purchaseRequired.status}">selected="selected"</c:if>>受理退回</option>
                  <option value="5" <c:if test="${'5'==purchaseRequired.status}">selected="selected"</c:if>>已提交 </option>
                </select>
              </li>
            </ul>
            <div class="col-md-12 clear tc mt10">
              <input class="btn" type="submit" value="查询" /> 
              <input class="btn" type="button" value="重置" onclick="resetQuery()" /> 
              <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
            </div>
            <div class="clear"></div>
            </form>
         </h2>

      <div class="clear"></div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info" width="30%">需求名称</th>
              <th class="info" width="18%">采购需求文号</th>
              <c:if test="${orgId ne null}">
              <th class="info" width="10%">采购管理部门</th>
              </c:if>
              <c:if test="${purchaseRequired.planType ne null}">
              <th class="info" width="5%">类型</th>
              </c:if>
              <th class="info" width="10%">金额（万元）</th>
              <th class="info" width="15%">编制时间</th>
              <th class="info">状态</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr class="pointer">
              <td class="tc w50" onclick="view('${obj.uniqueId }')">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
              <td class="tl" onclick="view('${obj.uniqueId }')">
                ${obj.planName }
              </td>
              <td class="tl" onclick="view('${obj.uniqueId }')">
                ${obj.referenceNo }
              </td>
              <c:if test="${orgId ne null}">
	              <td class="tl" onclick="view('${obj.uniqueId }')">
	                ${shortName}
	              </td>
              </c:if>
              <c:if test="${purchaseRequired.planType ne null}">
                <td class="tc" onclick="view('${obj.uniqueId }')">
                  <c:forEach items="${data}" var="aa">
                    <c:if test="${aa.id eq obj.planType}">${aa.name}</c:if>
                  </c:forEach>
                </td>
              </c:if>
              <td class="tr">
                <div onclick="view('${obj.uniqueId }')">
                  <fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}" />
                </div>
              </td>
              <td class="tc" onclick="view('${obj.uniqueId }')">
                <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc" onclick="view('${obj.uniqueId }')">
                <c:if test="${obj.status=='1' }">未提交</c:if>
                <c:if test="${obj.status=='4' }">受理退回</c:if>
                <c:if test="${obj.status =='2' || obj.status =='3' || obj.status=='5' }">已提交</c:if>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>

  </body>

</html>