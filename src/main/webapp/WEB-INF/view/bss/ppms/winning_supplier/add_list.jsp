<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>录入标的</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <%@ include file="/WEB-INF/view/common.jsp"%>

  </head>
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

    var addFlag = 0;

    //明细的复选框只能选一个
    $(function() {
      $(".ck").click(function() {
        $(".ck").toggle(
          function() {
            $(".ck").each(function() {
              $(this).attr("checked", false);
            });
            $(this).prop("checked", true);
          },
          function() {
            $(".ck").each(function() {
              $(this).attr("checked", false);
            });
            $(this).prop("checked", false);
          }
        );
      });
    });

    var tempStrForAdd = 3;
    //新增行
    function add(btns) {
      var checkboxStatus = false,
        detailId, appendObj;
      var tableObj = $("#forAppendTr");
      //tableObj.show();
      var appendStr = '<tr class="tc">' +
        '<td><input type="checkbox" name="appendCK"/></td>' +
        '<td><input type="text" class="tc w50" name="serialNumber"></td>' +
        '<td><input type="text" class="tc w50" name="goodsName"></td>' +
        '<td><input type="text" class="tc w50" name="stand"></td>' +
        '<td><input type="text" class="tc w50" name="trademark"></td>' +
        '<td><input type="text" class="tc w50" name="qualitStand"></td>' +
        '<td><input type="text" class="tc w50" name="item"></td>' +
        '<td><input type="text" class="tc w50" name="purchaseCount" onkeyup="this.value=this.value.replace(/\\D/g,' + "''" + ')"></td>' +
        '<td><input type="text" class="tc w50" name="unitPrice" onblur="pMoney(this)"></td>' +
        '<td class="tc w50"><input type="text" name="money" value="" readonly="readonly"></td>'+
        '</tr>';
      tableObj.append(appendStr);
    }
    //删除选中新增标的
    function del(obj) {
      //删除选中行
      $("input[name='appendCK']:checked").each(function() {
        $(this).parent().parent().remove();
      });
    }

    //保存
    function saveOrUpdate(btns) {
      var btnVal = $(btns).html();
      if(btnVal == "保存" && tempStrForAdd == 3) {
        var sid = "${supplierId}";
        var subjectList = [];
        var validateFlag = "pass";
        //‘正规’标的信息循环放入数组
        $("input[name='ck']").each(function(index, element) {
          /****/
          if($(this).parent().parent().find(":input[name='goodsName']").val() == null || $(this).parent().parent().find(":input[name='goodsName']").val() == "") {
            validateFlag = "goodsName";
          }
          if($(this).parent().parent().find(":input[name='unitPrice']").val() == null || $(this).parent().parent().find(":input[name='unitPrice']").val() == "") {
            validateFlag = "unitPrice";
          }

          var data = {
            detailId: $(this).attr("title"),
            serialNumber: $(this).parent().parent().find(":input[name='serialNumber']").val(),
            supplierId: sid,
            packageId: "${packageId}",
            goodsName: $(this).parent().parent().find(":input[name='goodsName']").val(),
            stand: $(this).parent().parent().find(":input[name='stand']").val(),
            qualitStand: $(this).parent().parent().find(":input[name='qualitStand']").val(),
            item: $(this).parent().parent().find(":input[name='item']").val(),
            trademark: $(this).parent().parent().find(":input[name='trademark']").val(),
            purchaseCount: $(this).parent().parent().find(":input[name='purchaseCount']").val(),
            unitPrice: $(this).parent().parent().find(":input[name='unitPrice']").val()
          };
          subjectList.push(data);
        });
        //附赠标的信息循环放入同一个数组，此标的只关联供应商
        $("input[name='appendCK']").each(function(index, element) {
          if($(this).parent().parent().find(":input[name='goodsName']").val() == null || $(this).parent().parent().find(":input[name='goodsName']").val() == "") {
            validateFlag = "goodsName";
          }
          var unitPrices = $(this).parent().parent().find(":input[name='unitPrice']").val();
          unitPrices = $.trim(unitPrices);
          if(unitPrices == null || unitPrices == "") {
            validateFlag = "unitPrice";
          }

          var data = {
            serialNumber: $(this).parent().parent().find(":input[name='serialNumber']").val(),
            supplierId: sid,
            packageId: "${packageId}",
            goodsName: $(this).parent().parent().find(":input[name='goodsName']").val(),
            stand: $(this).parent().parent().find(":input[name='stand']").val(),
            qualitStand: $(this).parent().parent().find(":input[name='qualitStand']").val(),
            item: $(this).parent().parent().find(":input[name='item']").val(),
            trademark: $(this).parent().parent().find(":input[name='trademark']").val(),
            purchaseCount: $(this).parent().parent().find(":input[name='purchaseCount']").val(),
            unitPrice: $(this).parent().parent().find(":input[name='unitPrice']").val()
          };
          subjectList.push(data);
        });
        if(validateFlag == "pass") {
          layer.confirm("保存后不可以修改", {
            title: '提示',
            shade: 0.01
          }, function(index) {
            layer.close(index);
            $.ajax({
              url: "${pageContext.request.contextPath}/theSubject/batchInsert.do",
              data: JSON.stringify(subjectList),
              type: "post",
              contentType: "application/json",
              success: function(obj) {
                layer.alert(
                  '添加成功', {
                    btn: ['确定']
                  },
                  function() {
                    window.location.href = "${pageContext.request.contextPath}/winningSupplier/viewPackageSupplier.html?packageId=" + "${packageId}" + "&flowDefineId=${flowDefineId}&projectId=${projectId}&supplierIds=${supplierIds}";
                  }
                );
              },
              error: function(obj) {
                layer.alert("添加失败");
              }
            });
          });
        } else {
          layer.alert("单价不可以为空");
        }
      } else if(btnVal == "修改") {

      } else if(btnVal == "保存" && tempStrForAdd == 0) {
        layer.alert("请先添加一条标的");
      }

    }
    function pMoney(obj){
    	var reg = /^\d+\.?\d*$/;
    	if(!reg.exec($(obj).val())) {
    		$(obj).val("");
    	}else{
    		var cont=$(obj).parent().prev().find("input").val();
    		var price=$(obj).val();
    		$(obj).parent().next().find("input").val((cont*price/10000).toFixed(4));
    	}
    }
  </script>

  <body>
    <h2 class="list_title mb0 clear">标的录入</h2>
    <div style="margin-top: 10px;">
      <button class="btn btn-windows add " onclick="add(this);" type="button">新增</button>
      <button class="btn btn-windows add " onclick="del(this);" type="button">删除</button>
    </div>
    <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
      <table class="table table-bordered table-condensed lockout" id="forAppendTr">
        <tr class="tc">
          <th class="w30">
            <input type="checkbox" id="checkAll" disabled="disabled" onclick="selectAll()" />
          </th>
          <th class="tc w50"><input type="hidden" name="cks" />编号</th>
          <th>物资名称</th>
          <th>规格型号</th>
          <th>品牌商标</th>
          <th>质量技术标准</th>
          <th>计量单位</th>
          <th>采购数量</th>
          <th>单价（元）</th>
          <th>金额（万元）</th>
        </tr>
        <c:forEach items="${detailList}" var="detail" varStatus="p">
          <tr class="tc ">
            <td>
              <input type="checkbox" id="checkAll" disabled="disabled" />
            </td>
            <td class="tc w50" title="${detail.serialNumber }">
              <input type="hidden" name="ck" class="ck" title="${detail.id }" />
              <input type="text" name="serialNumber" class="tc w50" value="${p.index+1}"></td>
            <td class="tc w50" title="${detail.goodsName}"><input type="hidden" name="detailId" value="${detail.id }"> <input type="hidden" name="detailId" value="${detail.id }"> <input type="hidden" name="detailId" value="${detail.id }"> <input type="text" name="goodsName" value="${detail.goodsName }"></td>
            <td class="tc w50" title=" ${detail.stand }"><input type="text" name="stand" value=" ${detail.stand }"></td>
            <td class="tc w50" title=""><input type="text" name="trademark" value=""></td>
            <td class="tc w50" title="${detail.brand }"><input type="text" name="qualitStand" value="${detail.brand }"></td>
            <td class="tc w50"><input type="text" name="item" readonly="readonly" value="${detail.item }"></td>
            <td id="purchaseCount" class="tc w50"><input type="text" name="purchaseCount" value="<fmt:formatNumber type='number' value='${detail.purchaseCount*pass.priceRatio/100}' pattern='0.00' maxFractionDigits='2'/>" readonly="readonly"></td>
            <td class="tc w50">
              <input type="text" name='unitPrice' value="${detail.budget}"  onblur="pMoney(this)">
            </td>
            <td class="tc w50">
              <input type="text" name='money' value="" readonly="readonly">
            </td>
          </tr>
        </c:forEach>
      </table>
      <table class="table table-bordered table-condensed table_input table_input" id="appendTable" style="display: none;">
        <tr class="tc">
          <th>
            <input type="checkbox" disabled="disabled" name="ck111s" />
          </th>
          <th >编号</th>
          <th >物资名称</th>
          <th >规格型号</th>
          <th width="">品牌商标</th>
          <th width="">质量技术标准</th>
          <th>计量单位</th>
          <th>采购数量</th>
          <th>单价（元）</th>
        </tr>
      </table>
    </div>
    <div style="text-align: center;">
      <button class="btn btn-windows add " onclick="saveOrUpdate(this);" type="button">保存</button>
      <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
    </div>
  </body>

</html>