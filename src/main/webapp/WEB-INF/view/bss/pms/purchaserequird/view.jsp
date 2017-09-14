<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

    <script type="text/javascript">
      $(function() {
        // 附件一排显示
        $('#content .uploadFiles').each(function() {
          $(this).addClass('w50 p0');
        });
      });
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
            <a href="javascript:void(0);">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购计划管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/purchaser/list.html');">采购需求编报</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <h2 class="count_flow"><i>1</i>需求主信息</h2>
      <ul class="ul_list">
        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求名称</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="text" class="input_group" disabled="disabled" name="name" id="jhmc" value="${list[0].planName}">
            <span class="add-on">i</span>
          </div>
        </li>
        <li class="col-md-3 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="text" class="input_group" disabled="disabled" value="${list[0].referenceNo}">
            <span class="add-on">i</span>
          </div>
        </li>

        <li class="col-md-3 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">类别</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <c:forEach items="${types}" var="tp">
              <c:if test="${tp.id eq list[0].planType }">
                <input type="text" class="input_group" disabled="disabled" value="${tp.name}">
              </c:if>
            </c:forEach>
            <span class="add-on">i</span>
          </div>
        </li>

        <li class="col-md-3 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="text" class="input_group" disabled="disabled" id="mobile" value="${list[0].recorderMobile }">
            <span class="add-on">i</span>
          </div>
        </li>
        
        <c:if test="${list[0].planType eq goods}">
          <li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" id="dnone">
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
              <input type="checkbox" id="import" <c:if test="${list[0].enterPort==1}">checked="checked"</c:if> disabled="disabled"/>进口
            </div>
          </li>
        </c:if>
        <li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span>
          <u:show showId="detailshow" delete="false" businessId="${fileId}" sysKey="2" typeId="${detailId}" />
        </li>
      </ul>

      <h2 class="count_flow"><i>2</i>需求明细</h2>
      <div class="content require_ul_list" id="content">
        <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr class="space_nowrap">
              <th class="info seq">序号</th>
              <th class="info department">需求部门</th>
              <th class="info goodsname">物资类别<br/>及名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准</th>
              <th class="info item">计量<br/>单位</th>
              <th class="info purchasecount">采购<br/>数量</th>
              <th class="info price">单价<br/>（元）</th>
              <th class="info budget">预算金额<br>（万元）</th>
              <th class="info deliverdate">交货期限</th>
              <th class="info purchasetype">采购方式</th>
              <c:if test="${org_advice!=2}">
                <th class="info organization">采购机构<br/>建议</th>
              </c:if>
              <th class="info purchasename">供应商名称</th>
              <c:if test="${list[0].planType eq goods && list[0].enterPort==1}">
                <th class="info freetax">是否申请<br>办理免税</th>
                <th class="goodsuse ">物资用途（仅进口）</th>
                <th class="useunit">使用单位（仅进口）</th>
              </c:if>
              <th class="info memo">备注</th>
              <th class="info extrafile">附件</th>
            </tr>
          </thead>

          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr>
              <td>
                <div class="seq">${obj.seq}</div>
              </td>
              <td>
                <div class="department">
                  <c:if test="${obj.purchaseCount eq null}">
                    ${obj.department}
                  </c:if>
                </div>
              </td>
              <td title="${obj.goodsName}" class="tl">
                <div class="goodsname">
                  <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
                </div>
              </td>
              <td title="${obj.stand}" class="tl">
                <div class="stand">
                  <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                </div>
              </td>
              <td title="${obj.qualitStand}" class="tl">
                <div class="qualitstand">
                  <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                </div>
              </td>
              <td title="${obj.item}">
                <div class="item">
                  <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                </div>
              </td>
              <td>
                <div class="purchasecount">
                  <fmt:formatNumber>${obj.purchaseCount }</fmt:formatNumber>
                </div>
              </td>
              <td>
                <div class="price">
                  <fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.price }" />
                </div>
              </td>
              <td>
                <div class="budget">
                  <fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}" />
                </div>
              </td>
              <td>
                <div class="deliverdate">${obj.deliverDate }</div>
              </td>
              <td>
                <div class="purchasetype">
                  <c:if test="${obj.purchaseCount ne null}">
                    <c:forEach items="${kind}" var="kind">
                      <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
                    </c:forEach>
                  </c:if>
                </div>
              </td>
              <c:if test="${org_advice!=2 }">
                <td>
                  <div class="organization">
                    <c:if test="${obj.purchaseCount ne null}">
                      <c:forEach items="${requires}" var="ss">
                        <c:if test="${ss.orgId eq obj.organization}">${ss.shortName}</c:if>
                      </c:forEach>
                    </c:if>
                  </div>
                </td>
              </c:if>
              <td title="${obj.supplier}">
                <div class="purchasename">
                  <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
                </div>
              </td>

              <c:if test="${list[0].planType eq goods && list[0].enterPort==1}">
                <td title="${obj.isFreeTax}">
                  <div class="freetax">
                    <c:if test="${fn:length (obj.isFreeTax) > 8}">${fn:substring(obj.isFreeTax,0,7)}...</c:if>
                    <c:if test="${fn:length(obj.isFreeTax) <= 8}">${obj.isFreeTax}</c:if>
                  </div>
                </td>
                <td title="${obj.goodsUse}">${obj.goodsUse}</td>
                <td title="${obj.useUnit}">${obj.useUnit }</td>
              </c:if>
              <td title="${obj.memo}">
                <div class="memo">
                  <c:if test="${fn:length (obj.memo) > 8}">${fn:substring(obj.memo,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.memo) <= 8}">${obj.memo}</c:if>
                </div>
              </td>
              <td>
                <u:show showId="pShow${vs.index}" delete="false" businessId="${obj.id}" sysKey="2" typeId="270FA42F7A214E25B62CD80D1045D158" />
              </td>
            </tr>

          </c:forEach>
        </table>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
      </div>

    </div>

  </body>

</html>