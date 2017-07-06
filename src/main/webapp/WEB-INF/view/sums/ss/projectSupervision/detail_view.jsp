<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/webuploadFT/layui/layui.js"></script>
    <script type="text/javascript">
      $(function() {
        $(".progress-bar").each(function() {
          var progress = $(this).prev().val();
          progress = progress + "%";
          $(this).width(progress);
        });
      });
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/planSupervision/overview.html?id=" + id;
      }

      function bigImg(obj, x) {
        $(obj).append("<span>" + x + "%" + "</span>");
      }

      function normalImg(obj, x) {
        $(obj).children("span").remove();
      }

     /*  $(function() {
        layui.use('flow', function() {
          var flow = layui.flow;
          flow.load({
            elem: '#tbody_id' //指定列表容器
              ,
            done: function(page, next) { //到达临界点（默认滚动触发），触发下一页
              var lis = [];
              //以jQuery的Ajax请求为例，请求下一页数据
              $.ajax({
                url: "${pageContext.request.contextPath}/projectSupervision/paixu.do?id=${planId}&projectId=${projectId}&page=" + page,
                type: "get",
                dataType: "json",
                success: function(res) {
                  layui.each(res.data, function(index, item) {
                    var department = "";
                    var progress = "";
                    if(item.price == null) {
                      department = "<div class='department'>" + item.department + "</div>";
                    } else if(item.price != 0) {
                      progress = "<div class='progress-new'><input type='hidden' value='" + item.progressBar + "'/><div id='progress' class='progress-bar' style='background:#2c9fa6;width:" + item.progressBar + "%" + "'" +
                        "onmouseover='bigImg(this,\"" + item.progressBar + "\")' onmouseout='normalImg(this,\"" + item.progressBar + "\")'></div></div>";
                    }
                    var code = "";
                    if(item.oneAdvice == "DYLY") {
                      code = item.supplier;
                    }
                    if(item.purchaseCount == 0) {
                      item.purchaseCount = "";
                    }
                    if(item.price == 0) {
                      item.price = "";
                    }
                    var html = "<tr class='pointer'><td class='tc w50'>" + item.seq + "</td><td>" +
                      department + "</td><td class='tl pl20'>" + item.goodsName + "</td><td class='tl pl20'>" + item.stand + "</td><td class='tl pl20'>" +
                      item.qualitStand + "</td><td class='tl pl20'>" + item.item + "</td><td class='tl pl20'>" + item.purchaseCount + "</td><td class='tr pr20'>" + item.price + "</td><td class='tr pr20'>" +
                      item.budget + "</td><td class='tl pl20'>" + item.deliverDate + "</td><td class='tl pl20'>" + item.purchaseType + "</td><td class='tl pl20'>" +
                      code + "</td><td class='tl pl20'>" + item.status + "</td><td class='tc' onclick='view(\"" + item.id + "\")'>" + progress + "</td></tr>";
                    lis.push(html);
                  });
                  next(lis.join(''), page < res.pages);
                },
              });
            }
          });
        });
      }); */
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-tni op-10 breadcrumbs ">
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
            <a href="javascript:void(0)">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <div>
        <c:if test="${'0' != type}">
          <h2 class="count_flow"><i>1</i>基本信息</h2>
          <ul class="ul_list">
            <table class="table table-bordered">
              <tbody>
                <tr>
                  <td class="bggrey">采购管理部门：</td>
                  <td>${collectPlan.purchaseId}</td>
                  <td class="bggrey">计划名称：</td>
                  <td>${collectPlan.fileName}</td>
                  <td class="bggrey">计划文号：</td>
                  <td>${collectPlan.taskId}</td>
                </tr>
                <tr>
                  <td class="bggrey">计划下达时间：</td>
                  <td>
                    <fmt:formatDate type='date' value='${collectPlan.orderAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                  </td>
                  <td class="bggrey">联系人：</td>
                  <td>${collectPlan.userId}</td>
                  <td class="bggrey"></td>
                  <td></td>
                </tr>
              </tbody>
            </table>
          </ul>
      </div>
      </c:if>
      <div class="padding-top-10 clear" id="clear">
        <h2 class="count_flow">
              <c:choose>
            <c:when test="${'0' eq type}"><i>1</i>需求明细</c:when>
            <c:otherwise><i>2</i>采购明细</c:otherwise>
          </c:choose>
             </h2>
        <ul class="ul_list">
          <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="content">
            <table id="table" class="table table-bordered table-condensed lockout">
              <thead>
                <tr class="info">
                  <th class="w50">序号</th>
                  <th class="info department">需求部门</th>
                  <th class="info ">物资类别及名称</th>
                  <th class="info ">规格型号</th>
                  <th class="info ">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info ">采购<br/>数量</th>
                  <th class="info">单价<br/>（元）</th>
                  <th class="info">预算<br/>金额<br/>（万元）</th>
                  <th class="info">交货期限</th>
                  <th class="info">采购方式</th>
                  <th class="info">供应商名称</th>
                  <th class="info">状态</th>
                  <th class="info">进度</th>
                </tr>
              </thead>
              <tbody id="tbody_id">
                <c:forEach items="${list}" var="obj" varStatus="vs">
                  <tr class="pointer">
                    <td class="tc w50">${obj.seq}</td>
                    <td><c:if test="${obj.price eq null}">
                    <div class="department">${obj.department}</div>
                    </c:if></td>
                    <td title="${obj.goodsName}">
                     	<div class="goodsname">
                     		 ${obj.goodsName}
                     	 </div>
                    </td>
                    <td title="${obj.stand}">
                    	<div class="stand">
                     	 	${obj.stand}
                        </div>
                    </td>
                    <td title="${obj.qualitStand}">
                    	<div class="qualitstand">
                     		 ${obj.qualitStand}
                        </div>
                    </td>
                    <td title="${obj.item}" class="tl">
                    	<div class="item">
                     		 ${obj.item}
                     	</div>
                    </td>
                    <td class="tl">
                    	<div class="purchasecount">
                    	${obj.purchaseCount}
                    	</div>
                    </td>
                    <td class="tr">
                    	<div class="price">
                 		   ${obj.price}
                 		</div>
                 	</td>
                    <td class="tr">
                    	<div class="budget">
                    		${obj.budget}
                    	</div>
                    </td>
                    <td class="tl">
                    	<div class="deliverdate">${obj.deliverDate}</div>
                    </td>
                    <td class="tl">
                    	<div class="purchasetype">${obj.purchaseType}</div>
                    </td>
                    <td title="${obj.supplier}" class="tl">
                    	<div class="purchasename">
                      	<c:if test="${code eq 'DYLY'}">
                       	 ${obj.supplier}
                      	</c:if>
                      	</div>
                    </td>
                    <td class="tl">
                    	<div class="w50 tc">${obj.status}</div>
                    </td>
                    <td class="tc" onclick="view('${obj.id}')">
                      <c:if test="${obj.price != null}">
                        <div class="progress-new w100">
                          <input type="hidden" value="${obj.progressBar}" />
                          <div id="progress" class="progress-bar" style="background:#2c9fa6;" onmouseover="bigImg(this,'${obj.progressBar}')" onmouseout="normalImg(this,'${obj.progressBar}')">
                          </div>
                        </div>
                        <div id="p" class="easyui-progressbar" data-options="value:${obj.progressBar}" style="width:80px;"></div>
                      </c:if>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </ul>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>