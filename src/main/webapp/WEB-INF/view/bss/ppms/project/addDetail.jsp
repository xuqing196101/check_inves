<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/backend/js/table.js" ></script>
    <script type="text/javascript">
      //勾选明细
      function check(ele) {
        var flag = $(ele).prop("checked");
        var purchaseType = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).children().val();
        purchaseType = $.trim(purchaseType);
        var goodUse = $("input[name='chkItem']:checked").parents("tr").find("td").eq(13).text();
        goodUse = $.trim(goodUse);
        if(!goodUse) {
          goodUse = null;
        }
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/checkDeail.html",
          data: "id=" + id,
          type: "post",
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='chkItem']").each(function() {
                var v1 = result[i].id;
                var v2 = $(this).val();
                if(v1 == v2) {
                  $(this).prop("checked", flag);
                }
              });
            }
          },
          error: function() {
            layer.msg("失败", {
              offset: ['222px', '390px']
            });
          }
        });
      }


      function save() {
        var checkIds = [];
        $('input[name="chkItem"]:checked').each(function() {
          checkIds.push($(this).val());
        });
        var id = $('input[name="chkItem"]:checked').val();
        id = $.trim(id);
        if(id == "") {
          layer.alert("请勾选明细", "#tb_id");
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/project/purchaseType.html",
            data: "id=" + checkIds,
            type: "post",
            dataType: "json",
            success: function(result) {
              if(result == "1") {
                if(checkIds.length > 0) {
                  var checked;
                  var unCheckedBoxs = [];
                  $('input[name="chkItem"]:not(:checked)').each(function() {
                    unCheckedBoxs.push($(this).val());
                  });
                  if(unCheckedBoxs < 1) {
                    checked = 0;
                  } else {
                    checked = 1;
                  }
                  $("#uncheckId").val(checked);
                  var purchaseTypes = $("#purchaseTypes").val();
                  $("#purchaseType").val(purchaseTypes);
                  $("#detail_id").val(checkIds);
                  $("#save_form_id").submit();
                }
              } else {
                layer.alert("采购方式不相同", {
                  shade: 0.01
                });

              }
            },
            error: function() {
              layer.msg("失败", {
                offset: ['222px', '390px']
              });
            }
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
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0)">项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">选择明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 录入采购计划开始-->
    <div class="container">

      <!-- 项目戳开始 -->
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows save" type="button" onclick="save()">确定</button>
        <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
      </div>
      <div class="content table_box ">
        <table id="table" class="table table-bordered table-condensed table-hover table_wrap left_table">
          <thead>
            <tr class="info">
              <th class="w30">选择</th>
              <th class="w50">序号</th>
              <th>需求部门</th>
              <th>物资名称</th>
              <th>规格型号</th>
              <th>质量技术标准</th>
              <th>计量单位</th>
              <th>采购数量</th>
              <th>交货期限</th>
              <th>采购方式</th>
              <th>供应商名称</th>
              <th>是否申请办理免税</th>
              <th>物资用途（进口）</th>
              <th>使用单位（进口）</th>
              <th>备注</th>
            </tr>
          </thead>
          <tbody id="tb_id">
            <c:forEach items="${lists}" var="obj" varStatus="vs">
              <c:if test="${obj.projectStatus eq '0'}">
                <tr class="pointer">
                  <td class="tc w30">
                    <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this)" alt="">
                  </td>
                  <td class="tc w50"> ${obj.seq}
                    <input type="hidden" id="seq" name="listDetail[${vs.index }].seq" value="${obj.seq }">
                    <input type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
                  </td>
                  <td class="tc">
                      ${obj.department}
                    <input type="hidden" name="listDetail[${vs.index }].department" value="${obj.department }">
                  </td>
                  <td class="tc">${obj.goodsName}
                    <input type="hidden" name="listDetail[${vs.index }].goodsName" value="${obj.goodsName }">
                  </td>
                  <td class="tc">
                  <c:if test="${obj.stand!='合计'}">
                   ${obj.stand}
                  </c:if>
                 
                    <input type="hidden" name="listDetail[${vs.index }].stand" value="${obj.stand }">
                  </td>
                  <td class="tc">${obj.qualitStand}
                    <input type="hidden" name="listDetail[${vs.index }].qualitStand" value="${obj.qualitStand }">
                  </td>
                  <td class="tc">${obj.item}
                    <input type="hidden" name="listDetail[${vs.index }].item" value="${obj.item }">
                  </td>
                  <td class="tc">${obj.purchaseCount}
                    <input type="hidden" name="listDetail[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                  </td>
                  <td class="tc">${obj.deliverDate}
                    <input type="hidden" name="listDetail[${vs.index }].deliverDate" value="${obj.deliverDate }">
                  </td>
                  <td class="tc">
                    <input type="hidden" id="purchaseTypes" value="${obj.purchaseType }">
                    <c:choose>
                            <c:when test="${obj.detailStatus==0 }">

                            </c:when>
                            <c:otherwise>
                              <c:forEach items="${kind}" var="kind">
					                      <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
					                    </c:forEach>
                        </c:otherwise>
                        </c:choose>
                    
                    <input type="hidden" name="listDetail[${vs.index }].purchaseType" value="${obj.purchaseType }">
                  </td>
                  <td class="tc">${obj.supplier}
                    <input type="hidden" name="listDetail[${vs.index }].supplier" value="${obj.supplier }">
                  </td>
                  <td class="tc">${obj.isFreeTax}
                    <input type="hidden" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
                  </td>
                  <td class="tc">${obj.goodsUse}
                    <input type="hidden" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }">
                  </td>
                  <td class="tc">${obj.useUnit}
                    <input type="hidden" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }">
                  </td>
                  <td class="tc">${obj.memo}
                    <input type="hidden" name="listDetail[${vs.index }].memo" value="${obj.memo }">
                    <input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
                    <input type="hidden" name="listDetail[${vs.index }].detailStatus" value="${obj.detailStatus}">
                    <input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType}">
                  </td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <form id="save_form_id" action="${pageContext.request.contextPath}/project/save.html" method="post">

      <c:forEach items="${lists}" var="obj" varStatus="vs">
        <input type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
        <input type="hidden" name="listDetail[${vs.index }].memo" value="${obj.memo }">
        <input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
        <input type="hidden" name="listDetail[${vs.index }].detailStatus" value="${obj.detailStatus}">
        <input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType}">
        <input type="hidden" name="listDetail[${vs.index }].purchaseType" value="${obj.purchaseType}">
      </c:forEach>
      <input id="detail_id" name="checkIds" type="hidden" />
      <input name="name" type="hidden" value="${name}" />
      <input name="purchaseType" id="purchaseType" type="hidden" />
      <input name="projectNumber" value="${projectNumber}" type="hidden" />
      <input name="projectId" type="hidden" value="${projectId }" />
      <input name="id" type="hidden" value="${id}" />
      <input id="uncheckId" name="uncheckId" type="hidden" />
      <input id="uncheckId" name="orgId" type="hidden" value="${orgId }" />

    </form>

  </body>

</html>