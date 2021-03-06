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

      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }

      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
          }
        }
      }

      function edit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
        status = $.trim(status);
        if(id.length == 1) {
          if(status != "已下达") {
            window.location.href = "${pageContext.request.contextPath}/adjust/pledit.html?id=" + id;
          } else {
            layer.alert("已下达", {
              offset: ['322px', '790px'],
              shade: 0.01
            });
          }

        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要修改的版块", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function view(id) {
        window.location.href = "${pageContext.request.contextPath }/look/view.html?id=" + id;
      }

      function resetQuery() {
        $("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }

      function search() {
        if($("#budget").val() != "" && $("#budget").val().trim() != "") {
          var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,4})?$/;
          if(!exp.test($("#budget").val())) {
            layer.alert("请输入正确预算金额");
            return false;
          }
        }
        $("#add_form").submit();
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
            <a href="javascript:void(0);">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购计划管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/adjust/edit.html');">采购计划修改</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2 fl">
        <h2>采购计划列表 </h2>
      </div>
      <h2 class="search_detail">
      <form id="add_form" action="${pageContext.request.contextPath }/adjust/edit.html" class="mb0" method="post" >
      <input type="hidden" name="page" id="page">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购计划名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="fileName" value="${inf.fileName }" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">预算金额：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" id="budget" name="budget" value="${inf.budget}" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <input class="btn mb0 h32" type="button" onclick="search()" value="查询"> 
              <input class="btn mb0 mr0 h32" type="button" value="重置" onclick="resetQuery()">
            </div>
          </div>
        </div>
      </div>
      </div>
      </form>
    </h2>

      <div class="col-md-12 pl20 mt10">
        <c:if test="${auth eq 'show'}">
          <button class="btn padding-left-10 padding-right-10 btn_back" onclick="edit()">修改</button>
        </c:if>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
              <th class="info w50">序号</th>
              <th class="info">采购计划名称</th>
              <th class="info">预算总金额（万元）</th>
              <th class="info">汇总时间</th>
              <th class="info">状态</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr class="pointer">
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()" alt=""></td>
              <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>

              <td class="tl" title="${obj.fileName}" onclick="view('${obj.id}')">
                <c:if test="${fn:length (obj.fileName) > 50}">${fn:substring(obj.fileName,0,49)}...</c:if>
                <c:if test="${fn:length(obj.fileName) <= 50}">${obj.fileName}</c:if>
              </td>

              <td class="tr w140" onclick="view('${obj.id}')">
                <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
              </td>
              <td class="tc w120" onclick="view('${obj.id}')">
                <fmt:formatDate value="${obj.createdAt }" />
              </td>
              <td class="tl" onclick="view('${obj.id}')">
                <input type="hidden" value="${obj.status}" />
                <c:if test="${obj.status=='1'&&obj.auditTurn != 2&&obj.auditTurn != 1&&obj.auditTurn != 3 }">审核轮次设置</c:if>
                <c:if test="${obj.status=='1'&&(obj.auditTurn == 2||obj.auditTurn == 1||obj.auditTurn == 3) }">第一轮审核人员设置</c:if>
                <c:if test="${(obj.status == 2 || obj.status == 12  ) && obj.auditTurn == null}">已直接下达</c:if>
                <%--    <c:if test="${obj.status == 2}">
                    已直接下达
                </c:if> --%>
                <c:if test="${(obj.status == 2 || obj.status == 12  )  && obj.auditTurn == 1}">审核结束</c:if>
                <c:if test="${obj.status==3 }">第一轮审核</c:if>
                <c:if test="${obj.status == 12 && obj.auditTurn == 2}">审核结束</c:if>
                <c:if test="${obj.status == 2 && obj.auditTurn == 2}">审核结束</c:if>
                <c:if test="${obj.status == 2 && obj.auditTurn == 3}">审核结束</c:if>
                <c:if test="${obj.status == 12 && obj.auditTurn == 3}">审核结束</c:if>
                <%--    <c:if test="${obj.status == 2 && obj.auditTurn == null}">
                    第三轮审核
                </c:if> --%>
                <c:if test="${obj.status==4 }">第二轮审核人员设置</c:if>
                <c:if test="${obj.status==5 }">第二轮审核</c:if>
                <c:if test="${obj.status=='6' }">第三轮审核人员设置</c:if>
                <c:if test="${obj.status=='7' }">第三轮审核</c:if>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>

  </body>

</html>