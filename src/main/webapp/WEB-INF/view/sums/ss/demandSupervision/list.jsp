<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <title>需求监管列表</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${list.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#add_form").submit();
            }
          }
        });
      });

      //重置
      /* function resetResult() {
        $("#planName").val("");
        $("#planNo").val("");
        $("#createdAt").val("");
        //$("#planCode").val("");
        var status = document.getElementById("status").options;
        status[0].selected = true;
      } */
      //查看
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/supervision/demandSupervisionView.html?id=" + id;
          jumppage("${pageContext.request.contextPath}/supervision/demandSupervisionView.html?id=" + id);
      }

      function resetQuery() {
        /* $("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected"); */
        $("#planName").attr("value", "");
        $("#referenceNo").attr("value", "");
        $("#createdAt").attr("value", "");
      }
    </script>
  </head>

  <body>
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
              <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0);">业务监管</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购业务监督</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/supervision/demandSupervisionList.html');">采购需求监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>采购需求列表</h2>
      </div>
      <div class="search_detail">
      <form id="add_form" action="${pageContext.request.contextPath }/supervision/demandSupervisionList.html" class="mb0" method="post" >
        <input type="hidden" name="page" id="page">
        <ul class="demand_list">
          <li>
            <label class="fl">采购需求名称：</label>
            <span><input type="text" name="planName" id="planName" value="${purchaseRequired.planName }" /></span>
           </li>
           <li>
             <label class="fl">采购需求文号：</label>
             <span><input  type="text" name="referenceNo" id="referenceNo" value="${purchaseRequired.referenceNo }" /> </span>
           </li>
           <li>
             <label class="fl">需求填报日期：</label>
             <span><input style="width: 120px;" id="createdAt" class="span2 Wdate w220"  value='<fmt:formatDate value="${purchaseRequired.createdAt }"/>' name="createdAt" type="text" onclick='WdatePicker()'> </span>
           </li>
           <li>
             <label class="fl">状态：</label>
             <select  name="status" id="status">
               <option selected="selected" value="total">全部</option>
               <option value="1" <c:if test="${'1'==purchaseRequired.status}">selected="selected"</c:if>>未提交</option>
               <option value="4" <c:if test="${'4'==purchaseRequired.status}">selected="selected"</c:if>>受理退回</option>
               <option value="5" <c:if test="${'5'==purchaseRequired.status}">selected="selected"</c:if>>已提交 </option>
             </select>
           </li>
         </ul>
         <div class="col-md-12 clear tc mt10">
           <input class="btn" type="submit" value="查询" /> 
           <input class="btn" type="reset" value="重置" onclick="resetQuery()" /> 
         </div>
         <div class="clear"></div>
       </form>
     </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th width="25%">采购需求名称</th>
              <th width="15%">采购需求文号</th>
              <th width="15%">需求单位</th>
              <th width="13%">预算总金额（万元）</th>
              <th class="info" width="12%">编制时间</th>
              <th>填报人</th>
              <!-- <th>状态</th> -->
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list.list}" var="items" varStatus="status">
              <tr class="tc">
                <td class="tc w50">${(status.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                <td class="tl">
                  <a href="javascript:void(0)" onclick="view('${items.id}');">${items.planName}</a>
                </td>
                <td class="tl">${items.referenceNo}</td>
                <td class="tl">${items.department}</td>
                <td class="tr">${items.budget}</td>
                <td class="tc"><fmt:formatDate value="${items.createdAt }" pattern="yyyy-MM-dd"/></td>
                <td class="tl">${items.userName}</td>
                <%-- <td>
                  <c:if test="${items.status=='1' }">未提交</c:if>
                  <c:if test="${items.status=='4' }">受理退回</c:if>
                  <c:if test="${items.status =='2' || items.status =='3' || items.status=='5' }">已提交</c:if>
                </td> --%>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>