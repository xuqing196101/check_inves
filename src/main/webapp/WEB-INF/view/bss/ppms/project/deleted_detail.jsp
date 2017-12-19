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
	
			$(function() {
				var index = 0;
				var divObj = $("#table_1_" + index);
				$(divObj).removeClass("hide");
				$("#package").removeClass("shrink");
				$("#package").addClass("spread");
				
				//获取查看或操作权限
        var isOperate = $('#isOperate', window.parent.document).val();
        if(isOperate == 0) { 
          //只具有查看权限，隐藏操作按钮
          $(":button").each(function() {
            $(this).hide();
          });
         }
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
        if($("#table_1_" + index).hasClass("hide")) {
          $("#table_1_" + index).removeClass("hide");
        } else {
          $("#table_1_" + index).addClass("hide");
        }
      } 
      
      function deleted(){
      	var id = [];
      	$("input[name='chkItem']:checked").each(function() {
      		id.push($(this).val());
      	});
      	$.ajax({
      		url: "${pageContext.request.contextPath}/project/deletedDetail.html?id=" + id,
          type: "post",
          dataType: "text",
          async: false,
          success: function(result) {
            if(result == "ok") {
            	 layer.msg("删除成功");
            	 window.location.reload()
            } else {
            	 layer.msg("删除失败");
            }
          }
      	});
      }
    </script>
  </head>

  <body>
  <div class="col-md-12 p0">
  	<h2 class="list_title">拟制符合性审查项</h2>
	  <ul class="flow_step">
	    <li>
		  	<a href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
		   	<i></i>
			</li>
	    <li>
		   	<a href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
		   	<i></i>
		 	</li>
		 	<li class="active">
		  	<a href="${pageContext.request.contextPath}/project/packDetail.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、删除明细</a>
		   	<i></i>
		 	</li>
		 	<li>
		   	<a href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、评审项预览</a>
		   	<i></i>
		 	</li>
		 	<li>
		   	<a href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}">05、采购文件</a>
		   	<i></i>
		 	</li>
		 	<li>
		   <a href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${projectId}&flowDefineId=${flowDefineId}">06、编报说明</a>
		   <i></i>
		 	</li>
		 	<li>
		   <a href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">07、审核意见</a>
		 	</li>
	 	</ul>
	</div>
    <!-- 录入采购计划开始-->
      <!-- 项目戳开始 --> 
      <div class="content table_box over_auto" id="content">
        <c:if test="${list ne null}">
          <c:forEach items="${list}" var="pack" varStatus="p">
          	<div class="col-md-12 col-sm-6 col-xs-12 p0">
            	<span onclick="ycDiv(this,'${p.index}')" id="package" class="count_flow hand shrink"></span>
              <span class="f16 b">包名：</span>
              <span class="f14 blue">${pack.name}</span>
            </div>
            <div class="clear"></div>
            <div id="table_1_${p.index}" class="hide">
            <button class="btn" type="button" onclick="deleted()">删除</button>
            <table  class="table table-bordered table-condensed table-hover table-striped left_table lockout  mt10 mb0">
              <thead>
                <tr class="space_nowrap">
                	<th class="w30">
		                <input type="checkbox" id="checkAll" onclick="selectAll()" />
		              </th>
                  <th class="info seq">序号</th>
                  <th class="info department">需求部门</th>
                  <th class="info goodsname">产品名称</th>
                  <th class="info stand">规格型号</th>
                  <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info purchasecount">采购<br/>数量</th>
                  <th class="info deliverdate">交货期限</th>
                  <th class="info purchasetype">采购方式</th>
                  <th class="info purchasename">供应商名称</th>
                  <th class="info memo">备注</th>
                </tr>
              </thead>
              <c:forEach items="${pack.projectDetails}" var="obj" varStatus="vs">
                <tr style="cursor: pointer;">
                	<td class="tc w30">
	                  <input type="checkbox" value="${obj.id}" name="chkItem" onclick="check()">
	                </td>
                  <td><div class="seq">${obj.serialNumber}</div></td>
                  <td>
                   <div class="department">${obj.department}</div>
                  </td>
                  <td><div class="goodsname" title="${obj.goodsName}">
                  	<c:if test="${fn:length (obj.goodsName) > 20}">${fn:substring(obj.goodsName,0,19)}...</c:if>
                   	<c:if test="${fn:length(obj.goodsName) <= 20}">${obj.goodsName}</c:if>
                  </td>
                  <td class=""><div class="stand" title="${obj.stand}">
                  	<c:if test="${fn:length (obj.stand) > 20}">${fn:substring(obj.stand,0,19)}...</c:if>
                   	<c:if test="${fn:length(obj.stand) <= 20}">${obj.stand}</c:if>
                  </td>
                  <td class="tc"><div class="qualitstand" title="${obj.qualitStand}">
                  	<c:if test="${fn:length (obj.qualitStand) > 20}">${fn:substring(obj.qualitStand,0,19)}...</c:if>
                   	<c:if test="${fn:length(obj.qualitStand) <= 20}">${obj.qualitStand}</c:if>
                  </td>
                  <td class="tc"><div class="item">${obj.item}</td>
                  <td class="tc"><div class="purchasecount">${obj.purchaseCount}</div></td>
                  <td class=""><div class="deliverdate">${obj.deliverDate}</div></td>
                  <td class="tc">
                   <div class="purchasetype">
                     <c:forEach items="${kind}" var="kind">
                       <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
                     </c:forEach>
                   </div>
                  </td>
                  <td><div class="purchasename">${obj.supplier}</div></td>
                  <td><div class="memo">${obj.memo}</div></td>
                </tr>
              </c:forEach>
            </table>
            </div>
          </c:forEach>
        </c:if>
      </div>
  </body>

</html>