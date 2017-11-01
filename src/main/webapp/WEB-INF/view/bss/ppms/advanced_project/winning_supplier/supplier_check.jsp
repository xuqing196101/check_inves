<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">
    <%@ include file="/WEB-INF/view/common.jsp"%>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
  </head>
  <script type="text/javascript">

    /** 全选全不选 */
    function selectAll() {
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
          checklist[i].checked = true;
          $($($(checklist[i]).parent().nextAll()[$(checklist[i]).parent().nextAll().length - 1]).children()[0]).attr("readonly", false).attr("style", "width: 32px;");
          var associate = document.getElementsByName("associate" + checklist[i].value);
          for(var k = 0; k < associate.length; k++) {
            associate[k].checked = true;
          }

        }

      } else {
        for(var j = 0; j < checklist.length; j++) {
          checklist[j].checked = false;
          $($($(checklist[j]).parent().nextAll()[$(checklist[j]).parent().nextAll().length - 1]).children()[0]).attr("readonly", true).attr("style", "width: 32px;border:none;");
          var associate = document.getElementsByName("associate" +
            checklist[j].value);
          for(var k = 0; k < associate.length; k++) {
            associate[k].checked = false;
          }
        }
      }

      ratio();
    }

    /** 单选 */
    function check(index, objs) {
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
      if(objs.checked == true) {
        $($($(objs).parent().nextAll()[$(objs).parent().nextAll().length - 1]).children()[0]).attr("readonly", false).attr("style", "width: 32px;");
      } else {
        $($($(objs).parent().nextAll()[$(objs).parent().nextAll().length - 1]).children()[0]).attr("readonly", true).attr("style", "width: 32px;border:none;");
      }
      var associate = document.getElementsByName("associate" + index);
      for(var i = 0; i < associate.length; i++) {
        if($("#rela" + index).prop("checked")) {
          $(associate[i]).prop("checked", "checked");
        } else {
          $(associate[i]).prop("checked", false);
        }

      }

      ratio(index);

    }

    /**移除供应商 */
    function del(btn) {
      var ids = [];
      $('input[name="chkItem"]:checked').each(function() {
        ids.push($(this).val());
      });
      if(ids.length == 1) {
        layer.confirm(
          '您确定要移除吗?', {
            title: '提示',
            shade: 0.01
          },
          function(index) {
            layer.close(index);
            var type = 0;
            layer.open({
              type: 2,
              title: '上传',
              shadeClose: false,
              shade: 0.01,
              area: ['367px', '180px'], //宽高
              content: '${pageContext.request.contextPath}/adWinningSupplier/supplierUpload.html?packageId=${packageId}&flowDefineId=${flowDefineId}&projectId=${projectId}&checkPassId='+ids,
              success: function(layero, index) {
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
              },
              btn: ['保存', '关闭'],
              yes: function() {
                iframeWin.upload();
                type = 1;
              },
              btn2: function() {
                delFileAjax();
              },
              end: function() {
                if(type != 1) {
                  delFileAjax();
                }
              }
            });

          });
      } else if(ids.length > 1) {
        layer.alert("只能选择一个供应商", {
          offset: ['222px', '390px'],
          shade: 0.01
        });
      } else {
        layer.alert("请选择要移除的供应商", {
          offset: ['222px', '390px'],
          shade: 0.01
        });
      }
    }

    /** 计算金额*/
    function ratio(index) {
      var quote = "${quote}";
      var checklist = document.getElementsByName("chkItem");
      for(var j = 0; j < checklist.length; j++) {
        $($("#" + checklist[j].value).find("#priceRatio").children()[0]).val("");
        $("#" + checklist[j].value).find("#wonPrice").text("");
        if(quote == 0) {
          $("#" + checklist[j].value).find("#singQuote").val("");
        } else {
          $("#" + checklist[j].value).find("#singQuote").text("");
          $("#" + checklist[j].value).find("#singQuotehhide").val("");

        }

      }

      var lengths = $("input[name='chkItem']:checked").length;
      if(lengths != 0) {
        var id = [];
        var ratio = [];
        if(lengths == 1) {
          ratio.push("100");
        } else if(lengths == 2) {
          ratio.push("70");
          ratio.push("30");
        } else if(lengths == 3) {
          ratio.push("50");
          ratio.push("30");
          ratio.push("20");
        } else if(lengths == 4) {
          ratio.push("40");
          ratio.push("30");
          ratio.push("20");
          ratio.push("10");
        }
        var i = 0;
        //第一名的报价金额
        var onePrice = [];
        //算出实际成交金额
        $('input[name="chkItem"]:checked').each(
          function() {
            $($("#" + $(this).val()).find("#priceRatio").children()[0]).val(ratio[i]).attr("attr", ratio[i]);
            var price = 0;
            var id = $(this).val();
            var j = 0;
            $('input[name="associate' + id + '"]:checked').each(
              function() {
                //报价id
                var quote = $("#" + id + $(this).val()).find("#Quotedamount").text();
                var count = $("#" + id + $(this).val()).find("#purchaseCount").text();

                //第一名赋值报价
                if(onePrice[j] == null ||
                  onePrice[j] == '') {
                  onePrice[j] = quote;
                } else {
                  if(quote >= onePrice[j]) {
                    quote = onePrice[j];
                  }
                }
                price = parseFloat(price) +toDecimal((ratio[i] / 100) *count * quote);
                j++;
              });
            if(price == 0) {
              price = "";
            }
            var quote = "${quote}";
            if(quote == 0) {
              $("#" + $(this).val()).find("#singQuote").val(price);
            } else {
              $("#" + $(this).val()).find("#singQuote").text(price);
              $("#" + $(this).val()).find("#singQuotehhide").val(price);
            }
            i++;
          });
      }
    }

    //保留两位小数  
    //功能：将浮点数四舍五入，取小数点后2位 
    function toDecimal(x) {
      var f = parseFloat(x);
      if(isNaN(f)) {
        return;
      }
      f = Math.round(x * 100) / 100;
      return f;
    }

    function save() {
      var id = "";
      var wonPrice = [];
      var isNull = 0;
      var priceRatio = [];
      var quote = "${quote}";
      var supplierIds = [];
      var flg = false;
      $('input[name="chkItem"]:checked').each(function() {
        id += $(this).val() + ",";
        supplierIds.push($(this).attr("class"));
        priceRatio.push($($(this).parent().parent().find("[title='priceRatio']").children()[0]).val());
        if($($(this).parent().parent().find("[title='priceRatio']").children()[0]).val() == "" || $($(this).parent().parent().find("[title='priceRatio']").children()[0]).val() == "0") {
          flg = true;
          return false;
        }
        var sq = 0;
        if(quote == 0) {
          sq = $("#" + $(this).val()).find("#singQuote").val();
          if(isNull == 0) {
            if(sq == null || sq == '') {
              isNull = 1;
            }
          }
        } else {
          sq = $("#" + $(this).val()).find("#singQuote").text();
        }
        wonPrice.push(sq);
      });
      if(flg == true) {
        layer.alert("选择占比不能为0!");
        return false;
      }
      id = id.substring(0, id.length - 1);
      if(id.length >= 1) {
        var checklist = document.getElementsByName("chkItem");
        var passId = "";
        var ratio = "";
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == true) {
            passId += $(checklist[i]).parent().parent().attr("id") + ",";
            ratio += $($($(checklist[i]).parent().nextAll()[$(checklist[i]).parent().nextAll().length - 1]).children()[0]).val() + ",";
          }

        }
        passId = passId.substring(0, passId.length - 1);
        ratio = ratio.substring(0, ratio.length - 1);
        $.ajax({
          type: "POST",
          dataType: "text",
          url: "${pageContext.request.contextPath }/adWinningSupplier/changeRatioByCheckpassId.do?ids=" + passId + "&priceRatios=" + ratio,
          success: function(data) {
            if(data == "ERROR") {
              layer.confirm('成交数量将会出现小数，是否确定此占比分配？', {
                btn: ['确认', '取消']
              }, function() {
                layer.confirm(
                  '确定后将跳转到录入标的,是否确定', {
                    title: '提示',
                    offset: ['100px', '300px'],
                    shade: 0.01
                  },
                  function(index) {
                    layer.close(index);
                    window.location.href = "${pageContext.request.contextPath}/adWinningSupplier/packageSupplier.html?supplierIds=" + supplierIds + "&ids=" + id + "&flowDefineId=${flowDefineId}&passquote=${quote}&packageId=${packageId}&projectId=${projectId}&priceRatios=" + priceRatio;
                  }
                );

              });
            } else if (data == "SCCUESS"){
              layer.confirm(
                '确定后将跳转到录入标的,是否确定', {
                  title: '提示',
                  offset: ['100px', '300px'],
                  shade: 0.01
                },
                function(index) {
                  layer.close(index);
                  window.location.href = "${pageContext.request.contextPath}/adWinningSupplier/packageSupplier.html?supplierIds=" + supplierIds + "&ids=" + id + "&flowDefineId=${flowDefineId}&passquote=${quote}&packageId=${packageId}&projectId=${projectId}&priceRatios=" + priceRatio;
                }
              );
            } else {
              layer.msg("失败");
            }
          }
        });

      } else {
        layer.alert("请选择供应商", {
          offset: ['100px', '390px'],
          shade: 0.01
        });
      }
    }

    /**ajax 删除文件 **/
    function delFileAjax() {
      $.ajax({
        type: "POST",
        dataType: "json",
        url: '${pageContext.request.contextPath}/winningSupplier/deleFile.do?packageId=${packageId}',
        success: function(data) {
          var map = data;
          if(map == "SCCUESS") {
            window.location.href = '${pageContext.request.contextPath}/adWinningSupplier/selectSupplier.do?projectId=${projectId}&flowDefineId=${flowDefineId}';
          } else {
            layer.msg("请上传");
          }

        }
      });
    }


    var tempTextValue = 0;

    function priceRatioFocus(obj) {
      tempTextValue = parseInt($(obj).attr("attr"));
    }

    function priceRatioConfirm(obj) {
      var objVal = obj.val();
      var objTitleValue = obj.attr("title");
      var currentStatus = 0;
      var ids = [];
      var priceRatios = [];
      if($($(obj).parent().prevAll()[$(obj).parent().prevAll().length - 1]).children()[0].checked == false) {
        return false;
      }
      for(var i = 0; i < objTitleValue.length; i++) {
        if(objTitleValue.charAt(i) > 0 && objTitleValue.charAt(i) < 10) {
          currentStatus = parseInt(objTitleValue.substr(i, objTitleValue.length));
          break;
        }
      }
      if(objVal >= 0 && objVal <= tempTextValue) {
      } else {
        layer.alert("请输入小于当前数的值");
        $obj.val(tempTextValue);
      }
    }

    function hrefGO() {
      location.href = "${pageContext.request.contextPath}/adWinningSupplier/selectSupplier.do?projectId=${projectId}&flowDefineId=${flowDefineId}";
    }

    function openDetail(packageId) {
      location.href = "${pageContext.request.contextPath}/adWinningSupplier/openDetail.do?packageId=${packageId}&flowDefineId=${flowDefineId}";
    }
  </script>

  <body>

    <h2 class="list_title mb0 clear">确认中标供应商</h2>

    <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
      <button class="btn" onclick="save();" type="button">确定</button>
      <button class="btn" onclick="del(this);" type="button">移除</button>
      <button class="btn" onclick="openDetail('${packageId}');" type="button">查看明细</button>
    </div>
    <div class="content table_box pl0">
      <table class="table table-bordered table-condensed table-hover table-striped">
        <thead>
          <tr class="info">
            <th class="w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
            <th class="w200">供应商名称</th>
            <th style="width: 110px;">&nbsp;总报价&nbsp;（万元）</th>
            <th style="width: 50px;">总得分</th>
            <th style="width: 20px;">排名</th>
            <th class="w50">占比（%）</th>
          </tr>
        </thead>
        <c:forEach items="${supplierCheckPass}" var="checkpass" varStatus="vs">
          <tr id="${checkpass.id}">
            <td class="tc opinter">
              <c:if test="${checkpass.isDeleted == 0 }">
                <input onclick="check('${checkpass.id}',this);" id="rela${checkpass.id}" type="checkbox" name="chkItem" class="${checkpass.supplier.id}" value="${checkpass.id}" />
              </c:if>
            </td>
            <td class="opinter" title="${checkpass.supplier.supplierName }">
              <c:choose>
                <c:when test="${fn:length(checkpass.supplier.supplierName) >10}">
                  ${fn:substring(checkpass.supplier.supplierName , 0, 10)}...
                </c:when>
                <c:otherwise>
                  ${checkpass.supplier.supplierName}
                </c:otherwise>
              </c:choose>
            </td>
            <td class="tc opinter" id="totalPrice">${checkpass.totalPrice}</td>
            <td class="tc opinter">${checkpass.totalScore}</td>
            <td class="tc opinter">${vs.index+1}</td>
            <td class="tc opinter" id="priceRatio" title="priceRatio">
              <input type="text" class="forChangeRatio${(vs.index+1)}" onfocus="priceRatioFocus(this)" onchange="priceRatioConfirm(this)" title="unchanged${(vs.index+1) }" style="width: 32px;border:none;" readonly="readonly" value="${checkpass.priceRatio}" />
            </td>
          </tr>
        </c:forEach>
      </table>
      <div class="col-md-12 tc">
        <button class="btn btn-windows back" onclick="hrefGO();" type="button">返回</button>
      </div>
    </div>
  </body>

</html>