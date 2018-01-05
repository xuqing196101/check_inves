<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container" id="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
        <li><a href="javascript:void(0);">保障作业系统</a></li>
        <li><a href="javascript:void(0);">采购计划管理</a></li>
        <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/statistic/taskList.html');">采购任务统计</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <div class="container">
    <div class="headline-v2 fl">
     <h2>任务明细</h2>
    </div>
    <div class="col-md-12 col-xs-12 col-sm-12 mt5 content require_ul_list" id="content">
       <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr>
              <th class="info seq">序号</th>
              <th class="info department">需求部门</th>
              <th class="info goodsname">产品名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准<br>（技术参数）</th>
              <th class="info item">计量</br>单位</th>
              <th class="info purchasecount">采购</br>数量</th>
              <th class="info price">单价<br>（元）</th>
              <th class="info budget">预算金额<br>（万元）</th>
              <th class="info deliverdate">交货<br>期限</th>
              <th class="info purchasetype">采购方式</th>
              <th class="info organization">采购机构</th>
              <th class="info purchasename">供应商名称</th>
              <th class="info memo">备注</th>
              <th class="info extrafile">附件</th>
            </tr>
          </thead>
					<tbody>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr>
              <td><div class="seq">${obj.seq}</div></td>
              <td>
              	<div class="department">
                	<c:if test="${obj.isParent eq 'true'}">
              			${obj.department}
              		</c:if>
              	</div>
              </td>
              <td title="${obj.goodsName}">
                <div class="goodsname">
                  <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
               	</div>
              </td >
              <td title="${obj.stand}">
                <div class="stand">
                  <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                </div>
              </td >
              <td title="${obj.qualitStand}">
                <div class="qualitstand">
                  <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                </div>
              </td >
              <td title="${obj.item}">
                <div class="item">
                 <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                 <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                </div>
              </td >
              <td>
               	<div class="purchasecount">${obj.purchaseCount}</div>
              </td>
              <td>
               	<div class="price">
               	<fmt:formatNumber type='number'   pattern='#,##0.00' value='${obj.price }' />
               	 </div>
              </td>
              <td>
              <div class="budget">
              <fmt:formatNumber type='number'   pattern='#,##0.00' value='${obj.budgets }' />
               	</div>
              </td>
              <td>
               	<div class="deliverdate">${obj.deliverDate }</div>
              </td>
              <td class="tc">
                <div class="purchasetype">
                	<c:if test="${obj.isParent ne 'true'}">
                		${obj.purchaseType}
                 	</c:if>
                </div>
              </td>
              <td class="tl">
                <div class="organization">
                 	<c:if test="${obj.isParent ne 'true'}">
                 		${obj.organization}
                  </c:if>
                </div>
              </td>  
              <td title="${obj.supplier}" class="tl">
              	<div class="purchasename">
               		<c:if test="${obj.isParent ne 'true' }">
                		<c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                		<c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
                	</c:if>
               	</div>
              </td >
              <td title="${obj.memo}">
                <div class="memo">
                 	<c:if test="${fn:length(obj.memo) > 8}">${fn:substring(obj.memo,0,7)}...</c:if>
                 	<c:if test="${fn:length(obj.memo) <= 8}">${obj.memo}</c:if>
                </div>
              </td>
              <td class="p0">
								<div class="extrafile">
							  	<u:show showId="pShow${vs.index}"  delete="false" businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
					  		</div> 
			 				</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
  		</div>
  		<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
    		<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
  		</div>
  	</div>
</body>

<!-- <script type="text/javascript">
	$(function () {
		$("#table tr").each(function() {
		debugger;
			var purchaseCount = $(this).find("td:eq(6)").find("input").val();
			var price = $(this).find("td:eq(7)").find("input").val();
			var sum = (purchaseCount - 0) * (price - 0) / 10000;
			sum = sum.toFixed(2);
			$(this).find("td:eq(8)").children(":last").text(sum);
		});
	});
	
	function sum(obj) { //数量
     var purchaseCount = $(obj).val() - 0; //数量
     var price2 = $(obj).parent().next().children(":last").prev(); //价钱
     var price = $(price2).val() - 0;
     var sum = purchaseCount * price / 10000;
     var budget = $(obj).parent().next().next().children(":last").prev();
     sum = sum.toFixed(2);
     $(budget).val(sum);
     var id = $(obj).next().val(); //parentId
     aa(id);
  }
  
  function aa(id) { // id是指当前的父级parentid
        var budget = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(9)").children(":last").val(); //parentId
          var same = $(this).find("td:eq(9)").children(":last").prev().val() - 0; //价格
          if(id == cid) {
            budget = budget + same; //查出所有的子节点的值
          }
        });
        budget = budget.toFixed(2);

        $("#table tr").each(function() {
          var pid = $(this).find("td:eq(9)").children(":first").val(); //上级id

          if(id == pid) {
            $(this).find("td:eq(9)").children(":first").next().val(budget);
            var spid = $(this).find("td:eq(9)").children(":last").val();
            calc(spid);
          }
        });
        var did = $("table tr:eq(1)").find("td:eq(9)").children(":first").val();
        var total = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(9)").children(":last").val();
          var same = $(this).find("td:eq(9)").children(":last").prev().val() - 0;
          if(did == cid) {
            total = total + same;
          }
        });
        $("table tr:eq(1)").find("td:eq(9)").children(":first").next().val(total);
      }
</script> -->
</html>
