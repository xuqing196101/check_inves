<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>

    <title>专项费用明细</title>

    <script type="text/javascript">
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
        var proId = $("#proId").val();
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath}/specialCost/edit.do?id=" + id + "&proId=" + proId;
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择修改的内容", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function add() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/specialCost/add.html?proId=" + proId;
      }

      function del() {
        var proId = $("#proId").val();
        var ids = [];
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
        });
        if(ids.length > 0) {
          layer.confirm('您确定要删除吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            window.location.href = "${pageContext.request.contextPath}/specialCost/delete.html?proId=" + proId + "&ids=" + ids;
          });
        } else {
          layer.alert("请选择要删除的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outsourcingCon/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        var total = $("#total").val();
        window.location.href = "${pageContext.request.contextPath}/burningPower/select.do?proId=" + proId + "&total=" + total;
      }

      $(document).ready(function() {
        var totalRow = 0;
        $('#table1 tr:not(:last)').each(function() {
          $(this).find('td:eq(9)').each(function() {
            totalRow += parseFloat($(this).text());
          });
        });
        $('#total').val(totalRow);
      });
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
            <a href="javascript:void(0)">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">专项费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>专项费用明细</h2>
      </div>

      <div class="col-md-8 mt10 ml10">
        <button class="btn btn-windows add" type="button" onclick="add()">添加</button>
        <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
        <button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
      </div>

    </div>

    <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

    <div class="container margin-top-5">
      <div class="container padding-left-25 padding-right-25">
        <table id="table1" class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">序号</th>
              <th class="info">项目名称</th>
              <th class="info">项目明细</th>
              <th class="info">名称</th>
              <th class="info">规格型号</th>
              <th class="info">计量单位</th>
              <th class="info">数量(消耗使用)</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
              <th class="info">分摊数量</th>
              <th class="info">单位产品分摊额</th>
              <th class="info">备 注</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list}" var="sc" varStatus="vs">
              <tr>
                <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${sc.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc">${sc.projectName }</td>
                <td class="tc">${sc.productDetal }</td>
                <td class="tc">${sc.name }</td>
                <td class="tc">${sc.norm }</td>
                <td class="tc">${sc.measuringUnit }</td>
                <td class="tc">${sc.amount }</td>
                <td class="tc">${sc.price }</td>
                <td class="tc">${sc.money }</td>
                <td class="tc">${sc.proportionAmout }</td>
                <td class="tc">${sc.proportionPrice }</td>
                <td>${sc.remark }</td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td class="tc" colspan="7">总计：</td>
              <td colspan="2"></td>
              <td class="tc"><input type="text" id="total" class="border0 tc w50 mb0" readonly="readonly"></td>
              <td colspan="2"></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="col-md-12 col-xs-12 col-sm-12 mt20 tc">
        <button class="btn" type="button" onclick="onStep()">上一步</button>
        <button class="btn" type="button" onclick="nextStep()">下一步</button>
      </div>

    </div>

  </body>

</html>