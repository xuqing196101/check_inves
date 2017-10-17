<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
  <!--<![endif]-->

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function back() {
        $("#tab-3").load("${pageContext.request.contextPath}/adPackageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
      }
      var count1 = "${count1}";
      var jsonStr = [];

      function update(obj, supplierId, packageId, projectId, quoteId, flowDefineId) {
        var reg = /^\d+\.?\d*$/;
        var flag = false;
        var x, y;
        oRect = obj.getBoundingClientRect();
        x = oRect.left;
        y = oRect.top;
        var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
        var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
        var auditReason = $(obj).parent().parent().find("td").eq("5").find("input").val();
        var isGiveUp = $(obj).parent().parent().find("td").eq("4").find("select").val();
        var date = '${date}';
        var json = {
          "total": total,
          "supplierId": supplierId,
          "deliveryTime": deliveryTime,
          "packageId": packageId,
          "projectId": projectId,
          "quoteId": quoteId,
          "date": date,
          "auditReason": auditReason,
          "flowDefineId": flowDefineId,
          "isGiveUp": isGiveUp
        };
        jsonStr.push(json);
        console.log(jsonStr);
      }

      var error = 0;

      function eachTable(obj) {
        //根据保存按钮显示提示信息
        var x, y;
        oRect = obj.getBoundingClientRect();
        x = oRect.left - 150;
        y = oRect.top - 150;
        var allTable = document.getElementsByTagName("table");
        var priceStr = "";
        var count = 0;
        var i = 0;
        if(count1 > 0) {
          i = count1;
        }
        for(i; i < allTable.length; i++) {
          for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
            var total = $(allTable[i].rows).eq(j).find("td").eq("2").find("input").val();
            var time = $(allTable[i].rows).eq(j).find("td").eq("3").find("input").val();
            var auditReason = $(allTable[i].rows).eq(j).find("td").eq("5").find("input").val();
            var isGiveUp = $(allTable[i].rows).eq(j).find("td").eq("4").find("select").val();
            var reg = /^\d+\.?\d*$/;
            if(isGiveUp == "2" && auditReason == "") {
              layer.msg("必须填写放弃原因", {
                offset: ['25%', '25%']
              });
              return;
            }
            if(isGiveUp == "" || isGiveUp == undefined) {
              if(total == "" || time == "") {
                count++;
                layer.msg("表单未填写完整,总价和交货时间必须填写,请检查表单");
                return;
              }
              if(!reg.exec(total)) {
                count++;
                layer.msg("表单未填写完整,总价和交货时间必须填写,请检查表单");
                return;
              }
            }
          };
        }
        if(count == 0) {
          for(var i = 0; i < allTable.length; i++) {
            for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
              var inputObj = $(allTable[i].rows).eq(j).find("td").eq("0").find("input");
              $(inputObj).click();
            };
          }
          $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/Adopen_bidding/save.html",
            data: {
              quoteList: JSON.stringify(jsonStr)
            },
            dataType: "json",
            success: function(message) {
              if('${packId}' == null || '${packId}' == "") {
                window.location.href = "${pageContext.request.contextPath}/Adopen_bidding/changtotal.html?projectId=${projectId}";
              } else {
                $("#tab-3").load("${pageContext.request.contextPath}/adPackageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
              }
            }
          });
        }
      }

      function ycDiv(obj, index) {
        if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
          $(obj).removeClass("spread");
          $(obj).addClass("shrink");
        } else {
          if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
            $(obj).removeClass("shrink");
            $(obj).addClass("spread");
          }
        }

        var divObj = new Array();
        divObj = $(".p0" + index);
        for(var i = 0; i < divObj.length; i++) {
          if($(divObj[i]).hasClass("p0" + index) && $(divObj[i]).hasClass("hide")) {
            $(divObj[i]).removeClass("hide");
          } else {
            if($(divObj[i]).hasClass("p0" + index)) {
              $(divObj[i]).addClass("hide");
            }
          }
        };
      }

      function download(id, key) {
        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('method', 'post');
        form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
        $('body').append(form);
        form.submit();
      }
      $(function() {
        for(var i = 1; i < 20; i++) {
          $(".p0" + i).addClass("hide");
        };
      });

      function show(ob) {
        var index = ob.selectedIndex;
        var i = ob.options[index].value;
        var val = $(ob).parent().next().find('input').val();
        if(i == 1){
					var aa = $(ob).parent().prev().prev().find('input[type=hidden]').val();
					$(ob).parent().prev().prev().find('input[type=text]').val(aa);
				} else if (i == 2){
					layer.prompt({title: '放弃原因', formType: 2, value : val ,shade: 0}, function(pass, index){
						  layer.close(index);
						  $(ob).parent().next().find('input').val(pass);
						});
				} else {
					$(ob).parent().prev().prev().find('input[type=text]').val("");
				}
      }
      
      function checkTotal(obj){
				var total = $(obj).val();
				total = total.trim();
				if(total){
					if(total == '0'){
						layer.msg("总价不合法,请重新输入");
					}
				} else {
					layer.msg("总价不能为空");
				}
			}
    </script>
  </head>

  <body>
    <div id="showDiv" class="clear">
      <c:if test="${not empty count}">
        <h2 class="tc">第${count + 1}轮报价</h2>
      </c:if>
      <c:forEach items="${treeMap}" var="treemap" varStatus="vsKey">
        <c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
          <div class="col-md-12 col-sm-12 col-xs-12 p0">
            <c:if test="${vsKey.index ==0 }">
              <h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
          <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
          </h2>
            </c:if>
            <c:if test="${vsKey.index != 0 }">
              <h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
          <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
          </h2>
            </c:if>
          </div>
            <div class="p0 ${vsKey.index} w100p clear">
              <table class="table table-bordered table-condensed mt5 left_table table_input">
                <thead>
                  <tr>
                    <th class="info w50">序号</th>
                    <th class="info w200">供应商名称</th>
                    <th class="info w100">总价(万元)</th>
                    <th class="info w100">交货期限</th>
                    <c:if test="${not empty count}">
                      <th class="info w100">状态</th>
                      <th class="info w100" style="display: none;">放弃原因</th>
                    </c:if>
                  </tr>
                </thead>
                <c:set value="${treemap.value}" var="judgeTreemap"></c:set>
                <c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
                  <tr>
                    <td class="tc w50">${vs.index+1 }
                      <input type="hidden" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${projectId}','${treemapValue.quoteId}','${flowDefineId}')" />
                    </td>
                    <td class="tl">${treemapValue.suppliers.supplierName}</td>
                    <td class="tc">
                    	<input name="total" onblur="checkTotal(this)" maxlength="16" type="text" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
											<c:forEach items="${selectQuoteList}" var="obj">
												<c:if test="${obj.supplierId eq treemapValue.suppliers.id}">
													<input type="hidden" value="${obj.total}"/>
												</c:if>
											</c:forEach>
                    </td>
                    <td class="tc"><input type="text" /></td>
                    <c:if test="${not empty count}">
                      <td class="tc">
                        <select onchange="show(this)">
                          <option value="">请选择</option>
                          <option value="1">放弃本轮报价</option>
                          <option value="2">放弃报价</option>
                        </select>
                      </td>
                      <td class="tc" style="display: none;"><input /></td>
                    </c:if>
                  </tr>
                </c:forEach>
              </table>
            </div>
        </c:forEach>
      </c:forEach>
      <div class="col-md-12 col-sm-12 col-xs-12 tc">
        <c:if test="${not empty judgeTreemap}">
          <c:if test="${not empty count}">
            <input class="btn btn-windows save" value="报价完成" type="button" onclick="eachTable(this)">
            <input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
          </c:if>
          <c:if test="${empty count}">
            <input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
          </c:if>
        </c:if>
        <c:if test="${empty judgeTreemap}">
          <input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
        </c:if>

      </div>
    </div>
  </body>

</html>