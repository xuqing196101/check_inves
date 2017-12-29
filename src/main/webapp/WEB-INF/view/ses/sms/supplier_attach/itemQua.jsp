<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>资质文件</title>
  </head>

  <body>
    <div class="container">
      <div class="content table_box">
        <ul class="ul_list">
          <ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
            <c:set value="0" var="liCountPro" />
            <c:set value="0" var="liCountSell" />
            <c:set value="0" var="liCountEng" />
            <c:set value="0" var="liCountSer" />
            <c:if test="${fn:contains(supplierTypeIds, 'PRODUCT') and fn:length(cateList) > 0}">
              <c:set value="${liCountPro+1}" var="liCountPro" />
              <li id="li_id_1" class="active">
                <a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型资质信息</a>
              </li>
            </c:if>
            <c:if test="${fn:contains(supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
              <li id="li_id_2" class='<c:if test="${liCountPro == 0}">active <c:set value="${liCountSell+1}" var="liCountSell"/></c:if>'>
                <a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型资质信息</a>
              </li>
            </c:if>
            <c:if test="${fn:contains(supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
              <li id="li_id_4" class='<c:if test="${liCountEng == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer"/></c:if>'>
                <a aria-expanded="false" href="#tab-4" data-toggle="tab">服务资质信息</a>
              </li>
            </c:if>
          </ul>

          <div class="tab-content padding-top-20" id="tab_content_div_id">

            <!-- 物资生产型 -->
            <c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
              <c:set value="0" var="prolength" />
              <div class="tab-pane fade active in" id="tab-1">
                <table class="table table-bordered">
                  <c:forEach items="${cateList }" var="obj" varStatus="vs">
                    <tr>
                      <td class="tc info">${obj.categoryName } </td>
                      <c:forEach items="${obj.list }" var="quaPro">
                        <td>
                          <c:set value="${prolength+1}" var="prolength"></c:set>
                          <span class="hand" onclick="reason('${quaPro.flag}','${obj.categoryName }','生产-${quaPro.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${quaPro.name}：</span>
                          <u:show showId="pShow${prolength}" groups="${saleShow}" delete="false" businessId="${quaPro.flag}" sysKey="${sysKey }" typeId="${typeId}" />
                          <p id="${quaPro.flag}"></p>
                        </td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </c:if>

            <!-- 物资销售型 -->
            <c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
              <c:set value="0" var="length"> </c:set>
              <div class="tab-pane <c:if test=" ${liCountSell==1 } ">active in</c:if> fade height-300" id="tab-2">
                <table class="table table-bordered">
                  <c:forEach items="${saleQua }" var="sale">
                    <tr>
                      <td class="tc info">${sale.categoryName } </td>
                      <c:forEach items="${sale.list }" var="saua" varStatus="vs">
                        <td>
                          <c:set value="${length+1}" var="length"></c:set>
                          <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${saua.name}：</span>
                          <u:show showId="saleShow${length}" groups="${saleShow}" delete="false" businessId="${saua.flag}" sysKey="${sysKey }" typeId="${typeId}" />
                          <p id="${saua.flag}"></p>
                        </td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </c:if>

            <!-- 服务 -->
            <c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
              <div class="tab-pane <c:if test=" ${liCountSer==1 } ">active in</c:if> fade height-200" id="tab-4">
                <table class="table table-bordered">
                  <c:set value="0" var="slength"> </c:set>
                  <c:forEach items="${serviceQua }" var="server">
                    <tr>
                      <td class="info">${server.categoryName }
                      </td>
                      <c:forEach items="${server.list }" var="ser" varStatus="vs">
                        <td class="tc">
                          <c:set value="${slength+1}" var="slength"></c:set>
                          <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${ser.name}：</span>
                          <u:show showId="serverShow${plength}" delete="false" groups="${saleShow}" businessId="${ser.flag}" sysKey="${sysKey }" typeId="${typeId}" />
                          <p id="${ser.flag}"></p>
                        </td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </c:if>
          </div>
        </ul>
      </div>
    </div>
  </body>

</html>