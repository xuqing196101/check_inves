<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
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

      //返回
      function cancel() {
        /* var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index); */
        window.location.href = "${pageContext.request.contextPath}/project/list.html";

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
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">查看项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <!-- 项目戳开始 -->
      <div class="headline-v2 fl">
        <h2>查看项目明细</h2>
      </div>
      <div class="col-md-12 col-sm-12 col-xs-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
      </div>
      <div class="content table_box over_auto" id="content">
        <c:if test="${lists != null }">
          <table id="table" class="table table-bordered table-condensed lockout">
            <thead>
              <tr class="space_nowrap">
                <th class="info seq">序号</th>
                <th class="info department">需求部门</th>
                <th class="info goodsname">物资类别及<br/>物资名称</th>
                <th class="info stand">规格型号</th>
                <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                <th class="info item">计量<br/>单位</th>
                <th class="info purchasecount">采购<br/>数量</th>
                <th class="info deliverdate">交货<br/>期限</th>
                <th class="info purchasetype">采购方式</th>
                <th class="info purchasename">供应商名称</th>
                <th class="info memo">备注</th>
              </tr>
            </thead>
            <c:forEach items="${lists}" var="obj" varStatus="vs">
              <tr style="cursor: pointer;">
                <td><div class="seq">${obj.serialNumber}</div></td>
                <td class=""><div class="department">${obj.department}</div></td>
                <td class=""><div class="goodsname">${obj.goodsName}</div></td>
                <td class=""><div class="stand">${obj.stand}</div></td>
                <td class=""><div class="qualitstand">${obj.qualitStand}</div></td>
                <td class="tc"><div class="item">${obj.item}</div></td>
                <td class="tc"><div class="purchasecount">${obj.purchaseCount}</div></td>
                <td class=""><div class="deliverdate">${obj.deliverDate}</div></td>
                <td class="tc">
                  <div class="purchasetype">
                  <c:choose>
                    <c:when test="${obj.detailStatus==0 }">
                    </c:when>
                    <c:otherwise>
                      <c:forEach items="${kind}" var="kind">
                        <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                  </div>
                </td>
                <td class=""><div class="purchasename">${obj.supplier}</div></td>
                <td class=""><div class="memo">${obj.memo}</div></td>
              </tr>
            </c:forEach>
          </table>
        </c:if>
        <c:if test="${packageList != null}">
          <c:forEach items="${packageList}" var="pack" varStatus="p">
            <div class="col-md-6 col-sm-6 col-xs-12 p0">
              <span class="f16 b">包名:</span>
              <span class="f14 blue">${pack.name}</span>
            </div>
            <input type="hidden" value="${pack.id}" />
            <table id="table" class="table table-bordered table-condensed lockout">
              <thead>
                <tr class="space_nowrap">
                  <th class="info seq">序号</th>
                  <th class="info department">需求部门</th>
                  <th class="info goodsname">物资类别及<br/>物资名称</th>
                  <th class="info stand">规格型号</th>
                  <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info purchasecount">采购<br/>数量</th>
                  <th class="info deliverdate">交货期限</th>
                  <th class="info purchasetype">采购方式</th>
                  <th class="info purchasename">供应商名称</th>
                  <%-- <c:if test="${pack.isImport==1}">
                    <th class="info freetax">是否申请<br/>办理免税</th>
                    <th class="info goodsuse">物资用途<br/>（进口）</th>
                    <th class="info useunit">使用单位<br/>（进口）</th>
                  </c:if> --%>
                  <th class="info memo">备注</th>
                </tr>
              </thead>
              <c:forEach items="${pack.projectDetails}" var="obj" varStatus="vs">
                <tr style="cursor: pointer;">
                  <td><div class="seq">${vs.index+1}</div></td>
                  <td>
                   <div class="department">
                    <c:if test="${orgnization.id == obj.department}">
                      ${orgnization.name}
                    </c:if>
                   </div>
                  </td>
                  <td><div class="goodsname">${obj.goodsName}</td>
                  <td class=""><div class="stand">${obj.stand}</td>
                  <td class="tc"><div class="qualitstand">${obj.qualitStand}</td>
                  <td class="tc"><div class="item">${obj.item}</td>
                  <td class="tc"><div class="purchasecount">${obj.purchaseCount}</div></td>
                  <td class=""><div class="deliverdate">${obj.deliverDate}</div></td>
                  <td class="tc">
                   <div class="purchasetype">
                    <c:choose>
                      <c:when test="${obj.detailStatus==0 }">
                      </c:when>
                      <c:otherwise>
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
                    </div>
                  </td>
                  <td><div class="purchasename">${obj.supplier}</div></td>
                 <%--  <c:if test="${pack.isImport==1}">
                    <td><div class="freetax">${obj.isFreeTax}</div></td>
                    <td><div class="goodsuse">${obj.goodsUse}</div></td>
                    <td><div class="useunit">${obj.useUnit}</div></td>
                  </c:if> --%>
                  <td><div class="memo">${obj.memo}</div></td>
                </tr>
              </c:forEach>
            </table>
          </c:forEach>
        </c:if>
      </div>
    </div>
  </body>

</html>