<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
        var index = 0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");
        $("#package").addClass("spread");
        
        $(".progress-bar").each(function() {
          var progress = $(this).prev().val();
          $(this).width(progress);
        });
      });

      function ycDiv(obj, index) {
        if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
          $(obj).removeClass("shrink");
          $(obj).addClass("spread");
        } else {
          if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
            $(obj).removeClass("spread");
            $(obj).addClass("shrink");
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
            };
          };
        };
      }
      
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/planSupervision/overview.html?id=" + id;
      }
      
      function bigImg(obj, x) {
        $(obj).append("<span>" + x + "%" + "</span>");
      }

      function normalImg(obj, x) {
        $(obj).children("span").remove();
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购计划监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="tab-content">
        <div class="tab-pane fade in active" id="dep_tab-0">
          <h2 class="count_flow jbxx">基本信息</h2>
          <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <td width="10%" class="info">项目名称：</td>
                  <td width="25%">${project.name}</td>
                  <td width="10%" class="info">项目编号：</td>
                  <td width="25%">${project.projectNumber}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">项目状态：</td>
                  <td width="25%">${project.status}</td>
                  <td width="10%" class="info">创建人：</td>
                  <td width="25%">${project.appointMan}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">创建日期：</td>
                  <td width="25%">
                    <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                  <td width="10%" class="info"></td>
                  <td width="25%"></td>
                </tr>
              </tbody>
            </table>
            <h2 class="count_flow jbxx">项目明细</h2>
        <!-- 包明细 -->
        <c:if test="${packages != null}">
          <c:forEach items="${packages}" var="list" varStatus="vs">
            <c:set value="${vs.index}" var="index"></c:set>
            <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">
              <span class="f14 blue">${packages[index].name}</span>
            </h2>
            <div class="p0${index} hide">
              <table id="table" class="table table-bordered table-condensed lockout">
                <thead>
                  <tr class="info">
                    <th class="w50">序号</th>
                    <th class="info department">需求部门</th>
                    <th class="info" width="10%">物资类别及名称</th>
                    <th class="info">规格型号</th>
                    <th class="info" width="15%">质量技术标准<br/>(技术参数)</th>
                    <th class="info item">计量<br/>单位</th>
                    <th class="info">采购<br/>数量</th>
                    <th class="info w80">单价<br/>（元）</th>
                    <th class="info w120">预算<br/>金额<br/>（万元）</th>
                    <th class="info" width="10%">交货期限</th>
                    <th class="info" width="8%">采购方式</th>
                    <c:if test="${code eq 'DYLY'}">
                      <th class="info" width="10%">供应商名称</th>
                    </c:if>
                    <th class="info" width="8%">状态</th>
                    <th class="info" width="8%">进度</th>
                  </tr>
                </thead>
                <tbody id="tbody_id">
                  <c:forEach items="${list.projectDetails}" var="obj" varStatus="vs">
                    <tr class="pointer">
                      <td class="tc w50">${(vs.index+1)}</td>
                      <td><div class="department">${obj.department}</div></td>
                      <td title="${obj.goodsName}" class="tl pl20">
                        ${obj.goodsName}
                      </td>
                      <td title="${obj.stand}" class="tl pl20">
                        ${obj.stand}
                      </td>
                      <td title="${obj.qualitStand}" class="tl pl20">
                        ${obj.qualitStand}
                      </td>
                      <td title="${obj.item}" class="tl pl20">
                        ${obj.item}
                      </td>
                      <td class="tl pl20">${obj.purchaseCount}</td>
                      <td class="tr pr20">${obj.price}</td>
                      <td class="tr pr20">${obj.budget}</td>
                      <td class="tl pl20">${obj.deliverDate}</td>
                      <td class="tl pl20">${obj.purchaseType}</td>
                      <c:if test="${code eq 'DYLY'}">
                        <td title="${obj.supplier}" class="tl pl20">
                          ${obj.supplier}
                        </td>
                      </c:if>
                      <td class="tl pl20">${obj.status}</td>
                      <td class="tc" onclick="view('${obj.id}')">
                        <div class="progress-new">
                          <input type="hidden" value="${obj.progressBar}" />
                          <div id="progress" class="progress-bar" style="background:#2c9fa6;" onmouseover="bigImg(this,'${obj.progressBar}')" onmouseout="normalImg(this,'${obj.progressBar}')">
                          </div>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:forEach>
        </c:if>
        <!-- 项目明细 -->
        <c:if test="${details != null}">
          <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="content">
            <table id="table" class="table table-bordered table-condensed lockout">
            <thead>
              <tr class="info">
                <th class="w50">序号</th>
                <th class="info department">需求部门</th>
                <th class="info goodsname">物资类别<br/>及名称</th>
                <th class="info stand">规格型号</th>
                <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                <th class="info item">计量<br/>单位</th>
                <th class="info purchasecount">采购<br/>数量</th>
                <th class="info w150">单位<br/>（元）</th>
                <th class="info w150">预算金额<br/>（万元）</th>
                <th class="info deliverdate">交货<br/>期限</th>
                <th class="info purchasetype">采购方式</th>
                <c:if test="${code eq 'DYLY'}">
                <th class="info purchasename">供应商名称</th>
                </c:if>
                <th class="info" width="8%">状态</th>
                <th class="info" width="8%">进度</th>
              </tr>
            </thead>
            <tbody id="tbody_id">
              <c:forEach items="${details}" var="obj" varStatus="vs">
                <tr class="pointer">
                      <td class="tc w50">${obj.serialNumber}</td>
                      <td><c:if test="${obj.price eq null}">
	                    <div class="department">${obj.department}</div>
	                    </c:if></td>
                      <td title="${obj.goodsName}" class="tl pl20">
                        ${obj.goodsName}
                      </td>
                      <td title="${obj.stand}" class="tl pl20">
                        ${obj.stand}
                      </td>
                      <td title="${obj.qualitStand}" class="tl pl20">
                        ${obj.qualitStand}
                      </td>
                      <td title="${obj.item}" class="tl pl20">
                        ${obj.item}
                      </td>
                      <td class="tl pl20">${obj.purchaseCount}</td>
                      <td class="tr pr20">${obj.price}</td>
                      <td class="tr pr20">${obj.budget}</td>
                      <td class="tl pl20">${obj.deliverDate}</td>
                      <td class="tl pl20">${obj.purchaseType}</td>
                      <c:if test="${code eq 'DYLY'}">
                        <td title="${obj.supplier}" class="tl pl20">
                          ${obj.supplier}
                        </td>
                      </c:if>
                      <td class="tl pl20">${obj.status}</td>
                      <td class="tc" onclick="view('${obj.id}')">
                        <c:if test="${obj.price != null}">
                        <div class="progress-new">
                          <input type="hidden" value="${obj.progressBar}" />
                          <div id="progress" class="progress-bar" style="background:#2c9fa6;" onmouseover="bigImg(this,'${obj.progressBar}')" onmouseout="normalImg(this,'${obj.progressBar}')">
                          </div>
                        </div>
                        </c:if>
                      </td>
                    </tr>
              </c:forEach>
            </tbody>
          </table>
          </div>
        </c:if>
        </div>
      </div>
     <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>