<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
      function sum2(obj) { //数量
        var val = $(obj).val();
        var pid = $(obj).prev().val();
        var defVal = obj.defaultValue;
        if(val != defVal) {
          var td = $(obj).parent().parent().children(":last");
          $(td).children(":last").prev().prev().prev().val(pid);
        } else {
          $(obj).parent().parent().children(":last").children(":last").prev().prev().prev().val("");
        }
        var purchaseCount = $(obj).val() - 0; //数量
        var price2 = $(obj).parent().next().children(":last").prev(); //价钱
        var price = $(price2).val() - 0;
        var sum = purchaseCount * price / 10000;
        var budget = $(obj).parent().next().next().children(":last").prev();
        $(budget).val(sum);
        var id = $(obj).next().val(); //parentId
        aa(id);
      }

      function sum1(obj) {
        var val = $(obj).val();
        var pid = $(obj).prev().val();
        var defVal = obj.defaultValue;
        if(val != defVal) {
          var td = $(obj).parent().parent().children(":last");
          $(td).children(":last").prev().prev().val(pid);
        } else {
          $(obj).parent().parent().children(":last").children(":last").prev().prev().val("");
        }

        var purchaseCount = $(obj).val() - 0; //价钱
        var price2 = $(obj).parent().prev().children(":last").prev().val() - 0; //数量
        var sum = purchaseCount * price2 / 10000;
        $(obj).parent().next().children(":last").prev().val(sum);
        var id = $(obj).next().val(); //parentId
        aa(id);
      }

      function aa(id) { // id是指当前的父级parentid
        var budget = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(8)").children(":last").val(); //parentId
          var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0; //价格
          if(id == cid) {

            budget = budget + same; //查出所有的子节点的值
          }
        });
        budget = budget.toFixed(2);

        $("#table tr").each(function() {
          var pid = $(this).find("td:eq(8)").children(":first").val(); //上级id

          if(id == pid) {
            $(this).find("td:eq(8)").children(":first").next().val(budget);
            var spid = $(this).find("td:eq(8)").children(":last").val();
            calc(spid);
          }
        });
        var did = $("table tr:eq(1)").find("td:eq(8)").children(":first").val();
        var total = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(8)").children(":last").val();
          var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
          if(did == cid) {
            total = total + same;
          }
        });
        $("table tr:eq(1)").find("td:eq(8)").children(":first").next().val(total);
      }

      function calc(id) {
        var bud = 0;
        $("#table tr").each(function() {
          var pid = $(this).find("td:eq(8)").children(":last").val();
          if(id == pid) {
            var currBud = $(this).find("td:eq(8)").children(":first").next().val() - 0;
            bud = bud + currBud;
            bud = bud.toFixed(2);

            var spid = $(this).find("td:eq(8)").children(":last").val();
            aa(spid);

          }
        });

      }

      function sel(obj) {
        var defVal;
        var org = $(obj).val();
        var price = $(obj).parent().prev().prev().prev().prev().val();
        if(price == "") {
          var id = $(obj).prev().val();
          $.ajax({
            url: "${pageContext.request.contextPath}/accept/detail.html",
            data: "id=" + id,
            type: "post",
            dataType: "json",
            success: function(result) {
              for(var i = 0; i < result.length; i++) {
                var v1 = result[i].id;
                $("#table tr").each(function() {
                  var opt = $(this).find("td:eq(10)").children(":first").val();
                  if(v1 == opt) {
                    var td = $(this).find("td:eq(10)");
                    var options = $(td).find("option");
                    $(options).each(function(i) {
                      if(options[i].defaultSelected) {
                        defVal = options[i].value;
                      }

                      var opt = $(this).val();
                      if(org == opt) {
                        $(this).prop("selected", true);
                        if(defVal != org) {
                          var eid = $(this).parent().prev().val();
                          $(this).parent().parent().parent().children(":last").children(":last").prev().val(eid);
                        } else {
                          $(this).parent().parent().parent().children(":last").children(":last").prev().val("");
                        }

                      } else {
                        $(this).prop("selected", false);
                        // $(this).removeAttr("selected");
                      }
                    });
                  }
                });
              }
            }
          });

        }
      }

      function org(obj) {
        var defVal;
        var org = $(obj).val();
        var price = $(obj).parent().prev().prev().prev().prev().val();
        if(price == "") {
          var id = $(obj).prev().val();
          $.ajax({
            url: "${pageContext.request.contextPath}/accept/detail.html",
            data: "id=" + id,
            type: "post",
            dataType: "json",
            success: function(result) {
              for(var i = 0; i < result.length; i++) {
                var v1 = result[i].id;
                $("#table tr").each(function() {
                  var opt = $(this).find("td:eq(11)").children(":first").val();
                  if(v1 == opt) {
                    var td = $(this).find("td:eq(11)");
                    var options = $(td).find("option");
                    $(options).each(function(i) {
                      if(options[i].defaultSelected) {
                        defVal = options[i].value;
                      }
                      var opt = $(this).val();
                      if(org == opt) {
                        $(this).prop("selected", true);
                        if(defVal != org) {
                          var eid = $(this).parent().prev().val();
                          $(this).parent().parent().parent().children(":last").children(":last").val(eid);
                        } else {
                          $(this).parent().parent().parent().children(":last").children(":last").val("");
                        }
                        // $(this).attr("selected", "selected");  
                      } else {
                        $(this).prop("selected", false);
                        //$(this).removeAttr("selected");
                      }
                    });
                  }
                });
              }
            }
          });

        }
      }

      /*         function FixTable(TableID, FixColumnNumber, width, height) {
                  if ($("#" + TableID + "_tableLayout").length != 0) {
                      $("#" + TableID + "_tableLayout").before($("#" + TableID));
                      $("#" + TableID + "_tableLayout").empty();
                  }
                  else {
                      $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
                  }
                  $('<div id="' + TableID + '_tableFix"></div>'
                  + '<div id="' + TableID + '_tableHead"></div>'
                  + '<div id="' + TableID + '_tableColumn"></div>'
                  + '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");
                  var oldtable = $("#" + TableID);
                  var tableFixClone = oldtable.clone(true);
                  tableFixClone.attr("id", TableID + "_tableFixClone");
                  $("#" + TableID + "_tableFix").append(tableFixClone);
                  var tableHeadClone = oldtable.clone(true);
                  tableHeadClone.attr("id", TableID + "_tableHeadClone");
                  $("#" + TableID + "_tableHead").append(tableHeadClone);
                  var tableColumnClone = oldtable.clone(true);
                  tableColumnClone.attr("id", TableID + "_tableColumnClone");
                  $("#" + TableID + "_tableColumn").append(tableColumnClone);
                  $("#" + TableID + "_tableData").append(oldtable);
                  $("#" + TableID + "_tableLayout table").each(function () {
                      $(this).css("margin", "0");
                  });
                  var HeadHeight = $("#" + TableID + "_tableHead thead").height();
                  HeadHeight += 2;
                  $("#" + TableID + "_tableHead").css("height", HeadHeight);
                  $("#" + TableID + "_tableFix").css("height", HeadHeight);
                  var ColumnsWidth = 0;
                  var ColumnsNumber = 0;
                  $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {
                      ColumnsWidth += $(this).outerWidth(true);
                      ColumnsNumber++;
                  });
                  ColumnsWidth += 2;
                  if ($.browser.msie) {
                      switch ($.browser.version) {
                          case "7.0":
                              if (ColumnsNumber >= 3) ColumnsWidth--;
                              break;
                          case "8.0":
                              if (ColumnsNumber >= 2) ColumnsWidth--;
                              break;
                      }
                  }
                  $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
                  $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
                  $("#" + TableID + "_tableData").scroll(function () {
                      $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
                      $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
                  });
                  $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "#f7f7f7" });
                  $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "#f7f7f7" });
                  $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "#f7f7f7" });
                  $("#" + TableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });
                  if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
                      $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
                      $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
                  }
                  if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
                      $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());
                      $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableColumn table").height() + 17);
                  }
                  $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());
                  $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());
                  $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());
                  $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());
              }
              $(document).ready(function () {
                  var boxwidth = $("#container").width();
                  var table_box = $("#table").width(boxwidth);
                          FixTable("table", 1, boxwidth, 460);
                      }); */

      function unique(array) {
        var n = []; //结果数组
        //从第二项开始遍历
        for(var i = 1; i < array.length; i++) {
          //如果当前数组的第i项在当前数组中第一次出现的位置不是i，
          //那么表示第i项是重复的，忽略掉。否则存入结果数组
          if(array.indexOf(array[i]) == i) n.push(array[i]);
        }
        return n;
      }

      function cancelProject(obj) {
        var val = $(obj).val();
        var id = $(obj).prev().val();
        var defVal = obj.defaultValue;
        if(val != defVal) {
          $(obj).next().val(id);
        } else {
          $(obj).next().val("");
        }
      }

      function sub() {
        $("#table").find("#adjust").submit();

      }
    </script>

    <!-- textarea 自适应高度js1 -->
    <script type="text/javascript">
      var autoTextarea = function(elem, extra, maxHeight) {
        extra = extra || 0;
        var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
          isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
          addEvent = function(type, callback) {
            elem.addEventListener ?
              elem.addEventListener(type, callback, false) :
              elem.attachEvent('on' + type, callback);
          },
          getStyle = elem.currentStyle ? function(name) {
            var val = elem.currentStyle[name];

            if(name === 'height' && val.search(/px/i) !== 1) {
              var rect = elem.getBoundingClientRect();
              return rect.bottom - rect.top -
                parseFloat(getStyle('paddingTop')) -
                parseFloat(getStyle('paddingBottom')) + 'px';
            };

            return val;
          } : function(name) {
            return getComputedStyle(elem, null)[name];
          },
          minHeight = parseFloat(getStyle('height'));

        elem.style.resize = 'none';

        var change = function() {
          var scrollTop, height,
            padding = 0,
            style = elem.style;

          if(elem._length === elem.value.length) return;
          elem._length = elem.value.length;

          if(!isFirefox && !isOpera) {
            padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
          };
          scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

          elem.style.height = minHeight + 'px';
          if(elem.scrollHeight > minHeight) {
            if(maxHeight && elem.scrollHeight > maxHeight) {
              height = maxHeight - padding;
              style.overflowY = 'auto';
            } else {
              height = elem.scrollHeight - padding;
              style.overflowY = 'hidden';
            };
            style.height = height + extra + 'px';
            scrollTop += parseInt(style.height) - elem.currHeight;
            document.body.scrollTop = scrollTop;
            document.documentElement.scrollTop = scrollTop;
            elem.currHeight = parseInt(style.height);
          };
        };

        addEvent('propertychange', change);
        addEvent('input', change);
        addEvent('focus', change);
        change();
      };
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="#"> 首页</a>
          </li>
          <li>
            <a href="#">保障作业系统</a>
          </li>
          <li>
            <a href="#">采购计划管理</a>
          </li>
          <li class="active">
            <a href="#">采购需求管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container" id="container">
      <div class="headline-v2 fl">
        <h2>采购任务明细</h2>
      </div>

      <div class="content table_box">

        <!-- 前半部分 -->
        <div class="content" id="content">
          <table id="table" class="table table-bordered table-condensed table_input lockout">
            <thead>
              <tr>
                <th class="info seq">序号</th>
                <th class="info department">需求部门</th>
                <th class="info goodsname">物资类别<br>及名称</th>
                <th class="info stand">规格型号</th>
                <th class="info qualitstand">质量技术标准</th>
                <th class="info item">计量<br>单位</th>
                <th class="info purchasecount">采购<br>数量</th>
                <th class="info price">单位<br>（元）</th>
                <th class="info budget">预算金额<br>（万元）</th>
                <th class="info deliverdate">交货<br>期限</th>
                <th class="info purchasetype">采购方式</th>
                <th class="info organization">采购机构</th>
                <th class="info purchasename">供应商名称</th>
                <th class="info freetax">是否申请<br>办理免税</th>
                <th class="info memo">备注</th>
              </tr>
            </thead>
            <form id="adjust" action="${pageContext.request.contextPath}/adjust/update.html" method="post">
              <c:forEach items="${list }" var="obj" varStatus="vs">
                <tr>
                  <td>
                    <input readonly="readonly" type="text" name="listDetail[${vs.index }].seq" value="${obj.seq }" class="seq">
                    <input type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
                  </td>
                  <td>
                    <textarea readonly="readonly" class="target department">${obj.department}</textarea>
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <textarea name="listDetail[${vs.index }].goodsName" class="target goodsname">${obj.goodsName }</textarea>
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <textarea name="listDetail[${vs.index }].stand" onblur="cancelProject(this)" class="target stand">${obj.stand }</textarea>
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <input class="qualitStand" type="text" name="listDetail[${vs.index }].qualitStand" onblur="cancelProject(this)" value="${obj.qualitStand }">
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <input class="item" type="text" name="listDetail[${vs.index }].item" onblur="cancelProject(this)" value="${obj.item }">
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td class="tc">
                    <c:if test="${obj.price!=null}">
                      <input type="hidden" name="ss" value="${obj.id }">
                      <input onblur="sum2(this)" class="purchasecount" type="text" name="listDetail[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.price==null}">
                      <input readonly="readonly" class="purchasecount" type="text">
                    </c:if>
                  </td>
                  <td class="tl">
                    <c:if test="${obj.price!=null}">
                      <input type="hidden" name="ss" value="${obj.id }">
                      <input onblur="sum1(this)" type="text" name="listDetail[${vs.index }].price" value="${obj.price }" class="price">
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </c:if>
                    <c:if test="${obj.price==null}">
                      <input readonly="readonly" type="text" class="price">
                    </c:if>

                  </td>
                  <td class="tr">
                    <input type="hidden" name="ss" value="${obj.id}">
                    <input type="text" name="listDetail[${vs.index }].budget" class="budget" value="${obj.budget }">
                    <input type="hidden" name="ss" value="${obj.parentId }">
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id}">
                    <input type="text" name="listDetail[${vs.index }].deliverDate" class="deliverdate" value="${obj.deliverDate }">
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id}">
                    <select name="listDetail[${vs.index }].purchaseType" class="purchasetype" onchange="sel(this)" id="select">
                      <option value="">请选择</option>
                      <c:forEach items="${types }" var="mt">
                        <option value="${mt.id }" <c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
                      </c:forEach>
                    </select>
                    <input type="hidden" name="ss" value="${obj.parentId}">
                  </td>
                  <td class="tc">
                    <%--<input type="hidden" name="listDetail[${vs.index }].organization" value="${obj.organization }">--%>
                    <input type="hidden" name="ss" value="${obj.id}">
                    <select class="org organization" onchange="org(this)" name="listDetail[${vs.index }].organization">
                      <option value="">请选择</option>
                      <c:forEach items="${orgs }" var="ss">
                        <c:if test="${obj.organization==ss.orgId }">
                          <option value="${ss.orgId }" selected="selected">${ss.name}</option>
                        </c:if>
                        <c:if test="${obj.organization!=ss.orgId }">
                          <option value="${ss.orgId }">${ss.name}</option>
                        </c:if>
                      </c:forEach>
                      <input type="hidden" name="ss" value="${obj.parentId}">
                    </select>
                  </td>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <input class="purchasename" type="text" name="listDetail[${vs.index }].supplier" onblur="cancelProject(this)" value="${obj.supplier }">
                    <input type="hidden" name="details" value="" />
                  </td>
                  <td><input class="freetax" readonly="readonly" type="text" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
                  <%--            <td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
                  <td class="tl pl20"><input style="border: 0px;" readonly="readonly" type="text" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }"></td> --%>
                  <td>
                    <input type="hidden" name="ss" value="${obj.id }">
                    <input class="memo" type="text" name="listDetail[${vs.index }].memo" onblur="cancelProject(this)" value="${obj.memo }">
                    <input type="hidden" name="details" value="" />
                    <input type="hidden" class="count" name="details" value="" />
                    <input type="hidden" class="price" name="details" value="" />
                    <input type="hidden" class="purchaseType" name="details" value="" />
                    <input type="hidden" class="org" name="details" value="" />
                    <%--      <input type="hidden" name="listDetail[${vs.index }].planName" value="${obj.planName }">
                <input type="hidden" name="listDetail[${vs.index }].planNo" value="${obj.planNo }">
                <input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType }">
                <input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
                <input type="hidden" name="listDetail[${vs.index }].historyStatus" value="${obj.historyStatus }">
                <input type="hidden" name="listDetail[${vs.index }].goodsType" value="${obj.goodsType }">
                <input type="hidden" name="listDetail[${vs.index }].auditDate" value="${obj.auditDate }">
                <input type="hidden" name="listDetail[${vs.index }].isMaster" value="${obj.isMaster }">
                <input type="hidden" name="listDetail[${vs.index }].isDelete" value="${obj.isDelete }">
                <input type="hidden" name="listDetail[${vs.index }].status" value="${obj.status }">
                <input type="hidden" name="listDetail[${vs.index }].threePurchaseType" value="${obj.threePurchaseType }">
                <input type="hidden" name="listDetail[${vs.index }].threeOrganiza" value="${obj.threeOrganiza }">
                <input type="hidden" name="listDetail[${vs.index }].threeAdvice" value="${obj.threeAdvice }">
                <input type="hidden" name="listDetail[${vs.index }].createAt" value="${obj.createdAt }">
                <input type="hidden" name="listDetail[${vs.index }].isCollect" value="${obj.isCollect }"> --%>
                  </td>
                </tr>
              </c:forEach>

            </form>
          </table>
        </div>

      </div>
      <div class="" style="margin-left:520px;">
        <div style="text-algin:center;">
          <u:upload id="detail" multiple="true" buttonName="上传图片" businessId="${id}" sysKey="2" typeId="${fileId}" auto="true" />
          <u:show showId="detailshow" businessId="${id}" sysKey="2" typeId="${fileId}" />
          <input style="margin-left:20px;" class="btn btn-windows edit" type="button" value="保存" onclick="sub()">
          <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
        </div>
      </div>

    </div>

  </body>

</html>