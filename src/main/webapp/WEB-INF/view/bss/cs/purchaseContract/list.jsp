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
        var packId = [];
        var supplierId = [];
        var supcheckId = [];
        var status = [];
        var wonPrice = [];
        var projectId = [];
        $('input[name="chkItem"]:checked').each(function() {
          packId.push($(this).val());
          projectId.push($(this).next().val());
          supplierId.push($(this).parent().next().text());
          supcheckId.push($(this).parent().next().next().text());
          status.push($(this).parent().next().next().next().text());
          wonPrice.push($(this).parent().next().next().next().next().text());
        });
        if(packId.length > 0) {
          if(packId.length > 1) {
            layer.msg("只可选择一条项目生成");
          } else {
            if(status == 1) {
              layer.msg("已生成过");
            } else if(status == 2) {
              layer.msg("暂存文件请在修改暂存中生成");
            } else {
            	window.location.href = "${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?id=" + packId + "&supid=" + supplierId + "&supcheckid=" + supcheckId + "&transactionAmount=" + wonPrice;
              //window.location.href = "${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?supplierId=" + supplierId + "&packId=" + packId + "&supcheckId=" + supcheckId + "&wonPrice=" + wonPrice + "&projectId=" + projectId;
            }
          }
        } else {
          layer.msg("请选择要生成的项目");
        }
      }

      function showDraftContract(id, type) {
        if(type == '1') {
        	if (id) {
        		window.location.href = "${pageContext.request.contextPath}/purchaseContract/showDraftContract.html?ids=" + id;
        	} else {
        		layer.msg("还没有生成合同");
        	}
        } else {
          layer.msg("还没有生成合同");
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
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购项目名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="name" value="${projects.name}" id="projectName" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购项目编号：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="projectNumber" value="${projects.projectNumber}" id="projectCode" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
            <div class="col-xs-8 f0 lh0">
              <select id="isCreate" name="isCreate" class="w100p h32 f14">
                <option value="">请选择</option>
                <option value="0" <c:if test="${'0' eq isCreate}">selected="selected"</c:if>>未生成</option>
                <option value="1" <c:if test="${'1' eq isCreate}">selected="selected"</c:if>>已生成</option>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button type="button" onclick="query()" class="btn mb0 h32">查询</button>
              <button type="button" onclick="resets()" class="btn mb0 mr0 h32">重置</button>
            </div>
          </div>
        </div>
      </div>
      </div>
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
              <th class="info" width="20%">项目名称</th>
              <th class="info" width="12%">项目编号</th>
              <th class="info" width="7%">包名</th>
              <th class="info" width="8%">成交金额</th>
              <th class="info" width="19%">成交供应商</th>
              <th class="info" width="15%">采购机构</th>
              <th class="info">状态</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="pass" varStatus="vs">
            <tr>
              <td class="tc pointer">
              	<input onclick="check()" type="checkbox" name="chkItem" value="${pass.packageId}" />
              	<input type="hidden" value="${pass.projectId}"/>
              </td>
              <td class="tnone">${pass.supplierId}</td>
              <td class="tnone">${pass.id}</td>
              <td class="tnone">${pass.isCreateContract}</td>
              <td class="tnone">${pass.wonPrice}</td>
              <td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <c:set value="${pass.projectName}" var="name"></c:set>
              <c:set value="${fn:length(name)}" var="length"></c:set>
              <c:if test="${length>10}">
                <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')" title="${name}">${fn:substring(name,0,10)}...</td>
              </c:if>
              <c:if test="${length<=10}">
                <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')" title="${name}">${name}</td>
              </c:if>
              <c:set value="${pass.projectNumber}" var="code"></c:set>
              <c:set value="${fn:length(code)}" var="length"></c:set>
              <c:if test="${length>10}">
                <td class=" tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')" title="${code}">${fn:substring(code,0,10)}...</td>
              </c:if>
              <c:if test="${length<=10}">
                <td class=" tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')" title="${code}">${code}</td>
              </c:if>
              <td class="tc pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')">${pass.packName}</td>
              <td class="tr pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')">${pass.wonPrice}</td>
              <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')">${pass.supplier.supplierName}</td>
              <td class="tl pointer" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')">${pass.purchaseDep}</td>
              <td class="tc" onclick="showDraftContract('${pass.contractId}','${pass.isCreateContract}')">
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