<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="../../../common.jsp"%>
    <title>申请合同审价</title>
    <script type="text/javascript">
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
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              location.href = '${pageContext.request.contextPath}/appraisalContract/select.html?page=' + e.curr;
            }
          }
        });
      });

      function add() {
        window.location.href = "${pageContext.request.contextPath}/appraisalContract/add.html";
      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }
<<<<<<< Updated upstream

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

      function appraisal() {
        var appraisal = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();

        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });

        if(id.length == 1) {
          if($.trim(appraisal) == "待审价") {
            window.location.href = "${pageContext.request.contextPath}/appraisalContract/selectContractInfo.html?id=" + id;
          } else {
            layer.alert("请选择待审价的合同", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });
          }
        } else if(id.length > 1) {
          layer.alert("请选择一个待审价的合同", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择待审价的合同", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        }

      }
=======
      
<<<<<<< Updated upstream
      function shenqing(){
    	  var appraisal = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();
    	  appraisal = $.trim(appraisal);
    	  alert(appraisal);
      }
      
      function transformational(){
    	  window.location.href="${pageContext.request.contextPath}/purchaseContract/createStraightContract.html";
      }
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">申请合同审价</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>申请审价合同列表</h2>
      </div>

      <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath}/appraisalContract/serch.html" method="post" class="mb0">
          <ul class="demand_list">
            <li>
              <label class="fl">合同名称：</label><span><input type="text" name="name" value="${name }" class=""/></span>
            </li>
            <li>
              <label class="fl">合同编号：</label>
              <span><input type="text"  name="code" value="${code }" class=""/>
          </li>
          <li>
            <label class="fl">供应商名称：</label><span><input type="text" name="supplierName" value="${supplierName }" class=""/></span>
            </li>
            <input type="hidden" name="like" value="0">
            <button type="submit" class="btn">查询</button>
            <button type="button" class="btn" onclick="resetQuery()">重置</button>
          </ul>
          <div class="clear"></div>
        </form>
      </div>

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows add" type="button" onclick="appraisal()">申请审价</button>
        <button class="btn btn-windows add" type="button" onclick="add()">添加合同</button>
        <button class="btn" type="button" onclick="transformational()">生成合同</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-striped table-hover">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">序号</th>
              <th class="info">合同名称</th>
              <th class="info">合同编号</th>
              <th class="info">合同金额(万元)</th>
              <th class="info">供应商名称</th>
              <th class="info">操作</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="contract" varStatus="vs">
            <tr>
              <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${contract.id }" /></td>
              <td class="tc"><input type="hidden" value="${contract.id }" />${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tc">${contract.name }</td>
              <td class="tc">${contract.code }</td>
              <td class="tc">${contract.money }</td>
              <td class="tc">${contract.supplierName }</td>
              <td class="tc">
                <c:if test="${contract.appraisal=='0' }">
                  待审价
                </c:if>
                <c:if test="${contract.appraisal=='1' || contract.appraisal=='3' }">
                  审价中
                </c:if>
                <c:if test="${contract.appraisal=='2' }">
                  已审价
                </c:if>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>

      <div id="pagediv" align="right"></div>
    </div>

  </body>

</html>