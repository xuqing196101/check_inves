<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>原、辅材料工艺定额消耗明细表</title>

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
          window.location.href = "${pageContext.request.contextPath }/accessoriesCon/edit.do?id=" + id + "&proId=" + proId;
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
        window.location.href = "${pageContext.request.contextPath }/accessoriesCon/add.html?proId=" + proId;
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
            window.location.href = "${pageContext.request.contextPath }/accessoriesCon/delete.html?proId=" + proId + "&ids=" + ids;
          });
        } else {
          layer.alert("请选择要删除的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function onStep() {
        var productId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath }/offer/selectProductInfo.do?productId=" + productId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        var total = $("#total").val();
        if(total == "NaN") {
          total = 0;
        }
        window.location.href = "${pageContext.request.contextPath }/outproductCon/select.do?proId=" + proId + "&total=" + total;
      }

      $(document).ready(function() {
        var totalRow = 0;
        var totalRow2 = 0;
        $('#table1 tr:not(:last)').each(function() {
          $(this).find('td:eq(10)').each(function() {
            totalRow += parseFloat($(this).text());
          });
          $(this).find('td:eq(15)').each(function() {
            totalRow2 += parseFloat($(this).text());
          });
        });
        $('#total').val(totalRow);
        $('#total2').val(totalRow2);

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
            <a href="javascript:void(0)">产品报价</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>原、辅材料工艺定额消耗明细表</h2>
      </div>

      <div class="col-md-8 mt10 pl20 ml5">
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
              <th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="2" class="info w50">序号</th>
              <th rowspan="2" class="info">材料性质</th>
              <th rowspan="2" class="info">材料名称</th>
              <th rowspan="2" class="info">规格型号</th>
              <th rowspan="2" class="info">图纸位置号(代号)</th>
              <th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
              <th colspan="5" class="info">消耗定额审核核准数（含税金额）</th>
              <th rowspan="2" class="info">供货单位</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">单位</th>
              <th class="info">单件重</th>
              <th class="info">重量小计</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
              <th class="info">单位</th>
              <th class="info">单件重</th>
              <th class="info">重量小计</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list}" var="acc" varStatus="vs">
              <tr>
                <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${acc.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc">
                  <c:if test="${acc.productNature=='0' }">
                    主要材料
                  </c:if>
                  <c:if test="${acc.productNature=='1' }">
                    辅助材料
                  </c:if>
                </td>
                <td>${acc.stuffName }</td>
                <td>${acc.norm }</td>
                <td>${acc.paperCode }</td>
                <td class="tc">${acc.workAmout }</td>
                <td class="tc">${acc.workWeight }</td>
                <td class="tc">${acc.workWeightTotal }</td>
                <td class="tc">${acc.workPrice }</td>
                <td class="tc">${acc.workMoney }</td>
                <td class="tc">${acc.consumeAmout }</td>
                <td class="tc">${acc.consumeWeight }</td>
                <td class="tc">${acc.consumeWeightTotal }</td>
                <td class="tc">${acc.consumePrice }</td>
                <td class="tc">${acc.consumeMoney }</td>
                <td>${acc.supplyUnit }</td>
                <td>${acc.remark }</td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td colspan="6" class="tc">总计：</td>
              <td colspan="4"></td>
              <td class="tc"><input type="text" id="total" class="border0 tc w50 m0" readonly="readonly"></td>
              <td colspan="4"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
              <td colspan="2"></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="col-md-12 col-xs-12 col-sm-12 mt20 tc">
        <button class="btn" type="button" onclick="onStep()">上一步</button>
        <button class="btn " type="button" onclick="nextStep()">下一步</button>
      </div>

    </div>

  </body>

</html>