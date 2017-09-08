<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>

  <head>
    <jsp:include page="/WEB-INF/view/common.jsp" />
    <title>采购合同管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript">
      $(function() {
        var status = "${isCreate}";
        $("#isCreate").val(status);
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 3 ? 3 : "${list.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${list.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
            }
          }
        });
        $(document).keyup(function(event) {
          if(event.keyCode == 13) {
            query();
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

      function query() {
        var projectName = $("#projectName").val();
        var projectCode = $("#projectCode").val();
        var isCreate = $("#isCreate").val();
        window.location.href = "${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html?name=" + projectName + "&projectNumber=" + projectCode + "&isCreate=" + isCreate;
      }

      function resets() {
        var auth = '${authType}';
        if(auth != '1') {
          layer.msg("只有采购机构可以操作");
          return;
        }
        $("#projectName").val("");
        $("#projectCode").val("");
        $("#isCreate").val("");
      }

      function createContract() {
        var auth = '${authType}';
        if(auth != '1') {
          layer.msg("只有采购机构可以操作");
          return;
        }
        var ids = [];
        var supid = [];
        var supcheckid = [];
        var isCreateContract = [];
        var transactionAmount = [];
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
          supid.push($(this).parent().next().text());
          supcheckid.push($(this).parent().next().next().text());
          isCreateContract.push($(this).parent().next().next().next().text());
          transactionAmount.push($(this).parent().next().next().next().next().text());
        });
        if(ids.length > 0) {
          if(ids.length > 1) {
            layer.msg("只可选择一条项目生成");
          } else {
            if(isCreateContract == 1) {
              layer.msg("已生成过");
            } else if(isCreateContract == 2) {
              layer.msg("暂存文件请在修改暂存中生成");
            } else {
              window.location.href = "${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?supid=" + supid + "&id=" + ids + "&supcheckid=" + supcheckid + "&transactionAmount=" + transactionAmount;
            }
          }
        } else {
          layer.msg("请选择要生成的项目");
        }
      }

      function showDraftContract(id, type, status) {
        if(type == '1') {
          window.location.href = "${pageContext.request.contextPath}/purchaseContract/showDraftContract.html?ids=" + id + "&status=" + status;
        } else {
          alert("还没有生成合同");
        }

      }

      function someCreateContract() {
        var auth = '${authType}';
        if(auth != '1') {
          layer.msg("只有采购机构可以操作");
          return;
        }
        var ids = [];
        var suppliers = [];
        var supcheckid = [];
        var isCreateContract = [];
        var transactionAmount = [];
        var flag = true;
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
          suppliers.push($(this).parent().next().text());
          supcheckid.push($(this).parent().next().next().text());
          isCreateContract.push($(this).parent().next().next().next().text());
          transactionAmount.push($(this).parent().next().next().next().next().text());
        });
        if(ids.length > 0) {
          if(ids.length > 1) {
            for(var i = 0; i < isCreateContract.length; i++) {
              if(isCreateContract[i] == 1) {
                flag = false;
                layer.alert("已生成过", {
                  offset: ['222px', '390px'],
                  shade: 0.01
                });
              }
            }
            if(flag) {
              $.ajax({
                url: "${pageContext.request.contextPath}/purchaseContract/createAllCommonContract.html?ids=" + ids + "&suppliers=" + suppliers,
                type: "POST",
                dataType: "text",
                success: function(data) {
                  var dd = data.replace("\"", "");
                  var ss = dd.split("=");
                  if(ss[0] == "true") {
                    window.location.href = "${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?id=" + ids + "&supid=" + suppliers + "&supcheckid=" + supcheckid + "&transactionAmount=" + transactionAmount;
                  } else if(ss[0] == "false") {
                    layer.alert(ss[1], {
                      offset: ['222px', '390px'],
                      shade: 0.01
                    });
                  }
                }
              });
            }
          } else if(ids.length == 1) {
            layer.alert("请至少选择两条", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          }
        } else if(ids.length == 0) {
          layer.alert("请选择要生成的项目", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
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
            <a href="javascript:void(0);">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购合同管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html');">采购项目列表</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>成交项目列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <div class="search_detail">
        <form action="${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html" id="form1" method="post" class="mb0">
          <input type="hidden" name="page" id="page">
          <ul class="demand_list">
            <li class="fl pl20"><label class="fl">采购项目名称：</label><span><input type="text" name="name" value="${projects.name}" id="projectName" class=""/></span></li>
            <li class="fl pl50"><label class="fl">采购项目编号：</label><span><input type="text" name="projectNumber" value="${projects.projectNumber}" id="projectCode" class=""/></span></li>
            <li class="fl pl20">
              <label class="fl">采购项目状态：</label>
              <span>
                <select id="isCreate"  name="isCreate" class="mb0 mt5 w178">
                  <option value="">请选择</option>
                  <option value="0">未生成</option>
                  <option value="1">已生成</option>
                </select>
              </span>
            </li>
          </ul>
          <button type="button" onclick="query()" class="btn mt1 fl">查询</button>
          <button type="button" onclick="resets()" class="btn mt1 fl">重置</button>
          <div class="clear"></div>
        </form>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn" onclick="createContract()">生成</button>
        <button class="btn" onclick="someCreateContract()">合并生成</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
          <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="tnone"></th>
              <th class="tnone"></th>
              <th class="tnone"></th>
              <th class="info w50">序号</th>
              <th class="info" width="20%">采购项目名称</th>
              <th class="info" width="12%">编号</th>
              <th class="info" width="7%">包名</th>
              <th class="info" width="8%">成交金额</th>
              <th class="info" width="19%">成交供应商</th>
              <th class="info" width="15%">采购机构</th>
              <th class="info">状态</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="pass" varStatus="vs">
            <tr>
              <td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${pass.packages.id}" /></td>
              <td class="tnone">${pass.supplierId}</td>
              <td class="tnone">${pass.id}</td>
              <td class="tnone">${pass.isCreateContract}</td>
              <td class="tnone">${pass.packages.wonPrice}</td>
              <td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <c:set value="${pass.project.name}" var="name"></c:set>
              <c:set value="${fn:length(name)}" var="length"></c:set>
              <c:if test="${length>10}">
                <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')" title="${name}">${fn:substring(name,0,10)}...</td>
              </c:if>
              <c:if test="${length<=10}">
                <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')" title="${name}">${name}</td>
              </c:if>
              <c:set value="${pass.project.projectNumber}" var="code"></c:set>
              <c:set value="${fn:length(code)}" var="length"></c:set>
              <c:if test="${length>10}">
                <td class=" tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')" title="${code}">${fn:substring(code,0,10)}...</td>
              </c:if>
              <c:if test="${length<=10}">
                <td class=" tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')" title="${code}">${code}</td>
              </c:if>
              <td class="tc pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')">${pass.packages.name}</td>
              <td class="tr pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')">${pass.packages.wonPrice }</td>
              <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')">${pass.supplier.supplierName}</td>
              <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')">${pass.purchaseDep}</td>
              <td class="tc" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}','${pass.pc.status}')">
                <c:if test="${pass.isCreateContract==1}">已生成</c:if>
                <c:if test="${pass.isCreateContract!=1}">未生成</c:if>
              </td>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>