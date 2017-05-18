<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
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
                if(v2 == v1) {
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
        $('input[name="chkItem"]:checked').val();
        var checkIds = [];
        $('input[name="chkItem"]:checked').each(function() {
          checkIds.push($(this).val());
        });
        if(checkIds.length < 1) {
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
      <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto mt20" id="content">
        <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr class="space_nowrap">
              <th class="choose">选择</th>
              <th class="info seq">序号</th>
              <th class="info department">需求部门</th>
              <th class="info goodsname">物资类别<br/>及名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
              <th class="info item">计量<br/>单位</th>
              <th class="info purchasecount">采购<br/>数量</th>
              <th class="info deliverdate">交货<br/>期限</th>
              <th class="info purchasetype">采购方式</th>
              <th class="info purchasename">供应商名称</th>
              <th class="info freetax">是否申请<br/>办理免税</th>
              <th class="info goodsuse">物资用途<br/>（进口）</th>
              <th class="info useunit">使用单位<br/>（进口）</th>
              <th class="memo">备注</th>
            </tr>
          </thead>
          <tbody id="tb_id">
            <c:forEach items="${lists}" var="obj" varStatus="vs">
              <c:if test="${obj.projectStatus eq '0'}">
                <tr class="pointer">
                  <td>
                    <div class="choose">
                      <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this)" alt="">
                    </div>
                  </td>
                  <td>
                     <div class="seq"> ${obj.seq}
                      <input type="hidden" id="seq" name="listDetail[${vs.index }].seq" value="${obj.seq }">
                      <input type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
                     </div>
                  </td>
                  <td>
                    <c:if test="${obj.price eq null}">
                   <div class="department">
                    ${obj.department}
                    <input type="hidden" name="listDetail[${vs.index }].department" value="${obj.department }">
                   </div>
                   </c:if>
                  </td>
                  <td class="tl">
                    <div class="goodsname">
                     ${obj.goodsName}
                      <input type="hidden" name="listDetail[${vs.index }].goodsName" value="${obj.goodsName }">
                    </div>
                  </td>
                  <td class="tl">
                    <div class="stand">
                    <c:if test="${obj.stand!='合计'}">
                      ${obj.stand}
                    </c:if>
                    <input type="hidden" name="listDetail[${vs.index }].stand" value="${obj.stand }">
                   </div>
                  </td>
                  <td>
                   <div class="qualitstand">
                    ${obj.qualitStand}
                    <input type="hidden" name="listDetail[${vs.index }].qualitStand" value="${obj.qualitStand }">
                   </div>
                  </td>
                  <td class="tc">
                   <div class="item">${obj.item}
                    <input type="hidden" name="listDetail[${vs.index }].item" value="${obj.item }">
                   </div>
                  </td>
                  <td class="tc">
                   <div class="purchasecount">${obj.purchaseCount}
                    <input type="hidden" name="listDetail[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                   </div>
                  </td>
                  <td class="tl">
                   <div class="deliverdate">${obj.deliverDate}
                    <input type="hidden" name="listDetail[${vs.index }].deliverDate" value="${obj.deliverDate }">
                   </div>
                  </td>
                  <td class="tc">
                   <div class="purchasetype">
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
                   </div>
                  </td>
                  <td>
                   <div class="purchasename">${obj.supplier}
                    <input type="hidden" name="listDetail[${vs.index }].supplier" value="${obj.supplier }">
                   </div>
                  </td>
                  <td>
                    <div class="freetax">${obj.isFreeTax}
                     <input type="hidden" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
                    </div>
                  </td>
                  <td class="tl">
                    <div class="goodsuse">${obj.goodsUse}
                      <input type="hidden" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }">
                    </div>
                  </td>
                  <td class="tl">
                    <div class="useunit">${obj.useUnit}
                      <input type="hidden" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }">
                    </div>
                  </td>
                  <td class="tl">
                    <div class="memo">${obj.memo}
                      <input type="hidden" name="listDetail[${vs.index }].memo" value="${obj.memo }">
                      <input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
                      <input type="hidden" name="listDetail[${vs.index }].detailStatus" value="${obj.detailStatus}">
                      <input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType}">
                    </div>
                  </td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      
    </div>
 	 <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
          <button class="btn btn-windows save" type="button" onclick="save()">确定</button>
          <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
        </div>
     </div>
    <form id="save_form_id" action="${pageContext.request.contextPath}/project/save.html" method="post">

      <c:forEach items="${lists}" var="obj" varStatus="vs">
        <input type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }">
        <input type="hidden" name="listDetail[${vs.index }].memo" value="${obj.memo }">
        <input type="hidden" name="listDetail[${vs.index }].parentId" value="${obj.parentId }">
        <input type="hidden" name="listDetail[${vs.index }].detailStatus" value="${obj.detailStatus}">
        <input type="hidden" name="listDetail[${vs.index }].planType" value="${obj.planType}">
      </c:forEach>
      <input id="detail_id" name="checkIds" type="hidden" />
      <input name="name" type="hidden" value="${name}" />
      <input name="projectNumber" value="${projectNumber}" type="hidden" />
      <input name="projectId" type="hidden" value="${projectId }" />
      <input name="id" type="hidden" value="${id}" />
      <input id="uncheckId" name="uncheckId" type="hidden" />
      <input id="uncheckId" name="orgId" type="hidden" value="${orgId }" />

    </form>

  </body>

</html>