<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js"></script> --%>

    <script type="text/javascript"></script>
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
            <a href="javascript:void(0)">采购任务管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/adjust/list.html');">采购任务调整</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">查看采购任务</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container mt20">
      <div class="tab-content">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li class="active">
              <a href="#dep_tab-0" data-toggle="tab" class="f18">详细信息</a>
            </li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
              <h2 class="count_flow jbxx">基本信息</h2>
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <td class="bggrey">任务名称：</td>
                    <td>${task.name}</td>
                    <td class="bggrey">任务编号：</td>
                    <td>${task.documentNumber}</td>
                    <c:if test="${projectId ne null}">
                      <td class="bggrey">预研通知书：</td>
                      <td><u:show showId="upload_id" groups="upload_ids" businessId="${projectId}" sysKey="2" delete="false" typeId="${advancedAdvice}" /></td>
                    </c:if>
                  </tr>
                </tbody>
              </table>
              <h2 class="count_flow jbxx">任务明细查看</h2>

              <div class="col-md-12 col-sm-12 col-xs-12 mt5 content require_ul_list" id="content">
                <table id="table" class="table table-bordered table-condensed lockout">
                  <thead>
                    <tr class="space_nowrap">
                      <th class="info seq">序号</th>
                      <th class="info department">需求部门</th>
                      <th class="info goodsname">物资类别及<br/>物资名称</th>
                      <th class="info stand">规格型号</th>
                      <th class="info qualitstand">质量技术标准</th>
                      <th class="info item">计量<br>单位</th>
                      <th class="info purchasecount">采购<br>数量</th>
                      <th class="info deliverdate">交货期限</th>
                      <th class="info purchasetype">采购方式</th>
                      <th class="info organization">采购机构</th>
                      <th class="info purchasename">供应商名称</th>
                      <!-- <th class="info freetax">是否申请<br>办理免税</th>
                      <th class="info goodsuse">物资用途<br>（进口）</th>
                      <th class="info useunit">使用单位<br>（进口）</th> -->
                      <th class="info memo">备注</th>
                      <c:if test="${lists != null}">
                        <th class="info purchasetype">明细状态</th>
                      </c:if>
                    </tr>
                  </thead>
                  <c:if test="${lists ne null}">
                    <c:forEach items="${lists}" var="obj" varStatus="vs">
                      <tr class="pointer">
                        <td><div class="seq">${obj.seq}</div></td>
                        <td>
                         <div class="department">
                           ${obj.department}
                         </div>
                        </td>
                        <td>
                          <div class="goodsname">${obj.goodsName}</div>
                        </td>
                        <td>
                          <div class="stand">${obj.stand}</div>
                        </td>
                        <td>
                          <div class="qualitstand">${obj.qualitStand}</div>
                        </td>
                        <td>
                          <div class="item">${obj.item}</div>
                        </td>
                        <td>
                          <div class="purchasecount">${obj.purchaseCount}</div>
                        </td>
                        <td>
                          <div class="deliverdate">${obj.deliverDate}</div>
					              </td>
                        <td>
                          <div class="purchasetype">
                            <c:if test="${obj.isParent!='true' }">
                            <c:forEach items="${kind}" var="kind">
                              <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                            </c:forEach>
                            </c:if>
                          </div>
                        </td>
                        <td>
                           <div class="organization">
                           <c:if test="${obj.isParent!='true' }">
                             <c:forEach items="${list2}" var="list">
                               <c:if test="${obj.organization eq list.id}">${list.shortName}</c:if>
                             </c:forEach>
                             </c:if>
                           </div>
                        </td>
                        <td>
                           <div class="purchasename">
                           <c:if test="${obj.isParent!='true' }">
                           ${obj.supplier}
                           </c:if>
                           </div>
                        </td>
                       <%--  <td>
                           <div class="freetax">${obj.isFreeTax}</div>
                        </td>
                        <td>
                           <div class="goodsuse">${obj.goodsUse}</div>
                        </td>
                        <td>
                           <div class="useunit">${obj.useUnit}</div>
                        </td> --%>
                        <td>
                           <div class="memo">${obj.memo}</div>
                        </td>
                        <td>
                           <div class="purchasetype">
                             <c:if test="${'0' eq obj.projectStatus}">
                                                                                                 未立项
                             </c:if>
                             <c:if test="${'1' eq obj.projectStatus}">
                                                                                                 已立项
                             </c:if>
                           </div>
                        </td>
                      </tr>
                    </c:forEach>
                  </c:if>
                  <c:if test="${list ne null}">
                    <c:forEach items="${list}" var="obj" varStatus="vs">
                      <tr style="cursor: pointer;">
			            <td><div class="seq">${obj.serialNumber}</div></td>
                        <td><div class="department">${obj.department}</div></td>
                        <td><div class="goodsname">${obj.goodsName}</div></td>
                        <td><div class="stand">${obj.stand}</div></td>
                        <td><div class="qualitstand">${obj.qualitStand}</div></td>
                        <td><div class="item">${obj.item}</div></td>
                        <td><div class="purchasecount">${obj.purchaseCount}</div></td>
                        <td><div class="deliverdate">${obj.deliverDate}</div></td>
                        <td>
                              <c:forEach items="${kind}" var="kind">
                                <c:if test="${kind.id == obj.purchaseType}">
                                  <div class="purchasetype">${kind.name}</div>
                                </c:if>
                              </c:forEach>
                        </td>
                        <td>
                          <c:forEach items="${list2}" var="list">
                            <c:if test="${obj.organization eq list.id}">
                              <div class="organization">${list.shortName}</div>
                            </c:if>
                          </c:forEach>
                        </td>
                        <td>
                           <div class="purchasename">${obj.supplier}</div>
                        </td>
                        <%-- <td>
                           <div class="freetax">${obj.isFreeTax}</div>
                        </td>
                        <td>
                           <div class="goodsuse">${obj.goodsUse}</div>
                        </td>
                        <td>
                           <div class="useunit">${obj.useUnit}</div>
                        </td> --%>
                        <td>
                           <div class="memo">${obj.memo}</div>
                        </td>
                      </tr>
                    </c:forEach>
                  </c:if>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-xs-12 col-sm-12 mt10">
          <button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
        </div>
      </div>

    </div>
  </body>

</html>