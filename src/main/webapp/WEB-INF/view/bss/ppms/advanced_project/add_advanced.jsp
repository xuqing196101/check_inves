<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%
    String tokenValue = new Date().getTime()
          + UUID.randomUUID().toString() + "";
%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head_two.js" ></script> --%>
    <script type="text/javascript">
      function chkItems(ele) {
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        var pId = $(ele).prev().val();
        if(flag) {
          //递归选中父节点
          checkedParent(pId);
          //递归选中子节点
          checkedChild(id);
        } else {
          //递归取消父节点选中
          noCheckedParent(pId);
          //递归取消子节点选中
          noCheckedChild(id);
        }
      }
      
      //递归取消父节点选中
      function noCheckedParent(pId) {
        //判断子节点是否全部没有选中
        var isChecked = 0;
        $("input[name='pId_" + pId + "']").each(function() {
          var v = $(this).val();
          if($(this).next().prop("checked") == true) {
            isChecked = 1;
          }
        });
        if(isChecked == 0) {
          $("input[name='chkItem_" + pId + "']").each(function() {
            $(this).prop("checked", false);
            var pId_v = $(this).prev().val();
            noCheckedParent(pId_v);
          });
        }
      }
      
      //递归取消子节点选中
      function noCheckedChild(id) {
        //所有子节点取消选中
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", false);
          var currId = $(this).next().val();
          noCheckedChild(currId);
        });
      }

      //递归选中父节点
      function checkedParent(pId) {
        $("input[name='chkItem_" + pId + "']").each(function() {
          $(this).prop("checked", true);
          var pId_v = $(this).prev().val();
          checkedParent(pId_v);
        });
      }

      //递归选中子节点
      function checkedChild(id) {
        $("input[name='pId_" + id + "']").each(function() {
          if(!$(this).next().is(':disabled')){
            $(this).next().prop("checked", true);
            var currId = $(this).next().val();
            checkedChild(currId);
          } else {
            $(this).next().prop("checked", false);
          }
        });
      }

      function download() {
      	var id = [];
      	$("input[name^='chkItem_']:checked").each(function() {
      		id.push($(this).val());
        });
        var projectNumber = $("#projectNumber").val();
        var department = $("#department").val();
        var proName = $("#proName").val();
        var userId = $("#userId").val();
        var planNo = $("#planNo").val();
        var orgId = [];
        var nameTr = document.getElementsByName("attr");
        var checkName=[];
        for(var i=0;i<nameTr.length;i++){
	       	if($(nameTr[i]).children(":first").children()[1].checked==true){
        		checkName.push($(nameTr[i]).children()[11]);
	        }
        }
        $(checkName).each(function() {
          orgId.push($(this).children(":last").val());
        });
        //$.unique(orgId);
        var kindName = $("#kindName").val();
        window.location.href = "${pageContext.request.contextPath}/advancedProject/download.html?projectNumber=" + projectNumber + "&department=" + department +
          "&proName=" + proName + "&orgId=" + orgId.unique3() + "&kindName=" + kindName + "&userId=" + userId + "&planNo=" + planNo + "&id=" + id;
      }
      
      Array.prototype.unique3 = function(){
		 		var res = [];
		 		var json = {};
		 		for(var i = 0; i < this.length; i++){
		  		if(!json[this[i]]){
		   			res.push(this[i]);
		   			json[this[i]] = 1;
		  		}
		 		}
		 		return res;
			}

      function upload() {
        var proName = $("#proName").val();
        proName = $.trim(proName);
        var projectNumber = $("#projectNumber").val();
        projectNumber = $.trim(projectNumber);
        var department = $("#department").val();
        var purchaseType = $("#purchaseType").val();
        var planType = $("#planType").val();
        var organization = [];
        $("input[name='organization']").each(function() {
          organization.push($(this).val());
        });
        var ids = [];
        $('input[name^="chkItem"]:checked').each(function() {
          ids.push($(this).val());
        });
        if(proName == "") {
          layer.tips("项目名称不允许为空", "#proName");
          $("#proName").focus();
        } else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#projectNumber");
          $("#projectNumber").focus();
        } else if(ids.length < 1) {
          layer.alert("请勾选明细", {
            shade: 0.01
          });
        } else {
        	var bool = verify();
        	if(bool){
        	   var nameTr=document.getElementsByName("attr");
        	   var checkName=[];
        	   for(var i=0;i<nameTr.length;i++){
	        	   if($(nameTr[i]).children(":first").children()[1].checked==true){
	        	     checkName.push($(nameTr[i]).children()[10]);
	        	   }
        	   }
        	   for(var i=0;i<checkName.length;i++){
        	      if(i != 0){
        	        if($(checkName[i]).text()!=$(checkName[i-1]).text()){
        	           layer.msg("采购方式不一样");
        	           return false;
        	        }
        	      }
        	   }
        	
        		layer.open({
            type: 2, //page层
            area: ['80%', '300px'],
            title: '下达',
            shade: 0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            shadeClose: true,
            content: '${pageContext.request.contextPath}/advancedProject/attachment.html?proName=' + proName +
                    '&projectNumber=' + projectNumber + '&department=' + department + '&purchaseType=' + purchaseType + '&ids=' + ids + '&planType=' + planType + '&organization=' + organization,
            });
        	} else {
        		$("#sps").html("项目编号已存在").css('color', 'red');
        		$("#projectNumber").focus();
        	}
          
          
        }

      }
      
      function verify() {
      	var bool = true;
        var projectNumber = $("#projectNumber").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/advancedProject/verify.html",
          type: "post",
          data: {"projectNumber" : projectNumber},
          dataType: "text",
          async: false,
          success: function(data) {
          		if(data == "false"){
          			$("#sps").html("项目编号已存在").css('color', 'red');
          			bool = false;
          		} else {
          			$("#sps").html("");
          		}
          },
        });
        return bool;
      }

      function goBack() {
        window.location.href = "${pageContext.request.contextPath}/collect/list.html";
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
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">预研项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">新建预研项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <sf:form id="form1" action="${pageContext.request.contextPath}/advancedProject/save.html" method="post" modelAttribute="project">
        <div>
          <h2 class="count_flow"><i>1</i>添加预研基本信息</h2>
          <% session.setAttribute("tokenSession", tokenValue); %>
          <input type="hidden" name="token2" value="<%=tokenValue%>">
          <input type="hidden" id="userId" name="user" value="${user}">
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>预研项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="proName" type="text" class="input_group" name="name" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>预研项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="projectNumber" type="text" class="input_group" name="projectNumber" onblur="verify();"/>
                <span class="add-on">i</span>
                <div class="cue" id="sps">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>需求明细</h2>
          <div class="ul_list">
            <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
              <table id="table" class="table table-bordered table-condensed lockout">
                <thead>
                  <tr class="space_nowrap">
                    <th class="info choose">选择</th>
                    <th class="info seq">序号</th>
                    <th class="info department">需求部门</th>
                    <th class="info goodsname">物资类别<br/>及名称</th>
                    <th class="info stand">规格型号</th>
                    <th class="info qualitstand">质量技术标准</th>
                    <th class="info item">计量<br/>单位</th>
                    <th class="info purchasecount">采购<br/>数量</th>
                    <th class="info price">单价<br/>（元）</th>
                    <th class="info deliverdate">交货<br>期限</th>
                    <th class="info purchasetype">采购方式</th>
                    <th class="info organization">采购机构</th>
                    <th class="info purchasename">供应商名称</th>
                    <!-- <th class="info freetax">是否申请<br/>办理免税</th>
                    <th class="info goodsuse">物资用途<br/>（进口）</th>
                    <th class="info useunit">使用单位<br/>（进口）</th> -->
                    <th class="info memo">备注</th>
                  </tr>
                </thead>
                <tbody id="task_id">
                  <c:forEach items="${lists}" var="obj" varStatus="vs">
                    <tr <c:if test="${obj.price != null}">name="attr"</c:if>>
                      <td class="tc choose">
                        <input type="hidden" name="pId_${obj.parentId}" value="${obj.parentId}" />
                        <input type="checkbox" value="${obj.id}" name="chkItem_${obj.id}" onclick="chkItems(this)" />
                      </td>
                      <td>
                        <div class="seq">
                          ${obj.seq} <input type="hidden" id="planNo" name="planNo" value="${obj.planNo}" />
                        </div>
                      </td>
                      <td>
                        <div class="department">
                          ${obj.department}
                          <input type="hidden" id="department" name="department" value="${obj.department}" />
                          <input type="hidden" id="id" name="id" value="${obj.id}" />
                        </div>
                      </td>
                      <td>
                        <div class="goodsname">${obj.goodsName}</div>
                        <input type="hidden" id="twoAdvice" name="twoAdvice" value="${obj.twoAdvice}" />
                      </td>
                      <td>
                        <div class="stand">${obj.stand}</div>
                      </td>
                      <td>
                        <div class="qualitStand">${obj.qualitStand}</div>
                      </td>
                      <td>
                        <div class="item">${obj.item}</div>
                      </td>
                      <td>
                        <div class="purchasecount">${obj.purchaseCount}</div>
                      </td>
                      <td>
                        <div class="price">${obj.price}</div>
                      </td>
                      <td>
                        <div class="deliverdate">${obj.deliverDate}</div>
                      </td>
                      <td>
                        <div class="purchasetype">
                          <c:forEach items="${kind}" var="kind">
                            <c:if test="${kind.id eq obj.purchaseType}">
                              ${kind.name}
                              <input type="hidden" id="kindName" name="kindName" value="${kind.name}" />
                              <input type="hidden" id="purchaseType" name="purchaseType" value="${obj.purchaseType}" />
                              <input type="hidden" id="planName" name="planName" value="${obj.planName}" />
                            </c:if>
                          </c:forEach>
                        </div>
                      </td>
                      <td>
                        <c:if test="${obj.price ne null}">
                          <div class="organization">${obj.organization}</div>
                          <input type="hidden" name="organization" value="${obj.oneOrganiza}" />
                        </c:if>
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
                        <div class="memo">${obj.memo} <input type="hidden" id="planType" name="planType" value="${obj.planType}" /></div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
          (请先下载预研通知书)
          <button class="btn btn-windows input" type="button" onclick="download()">下载预研通知书</button>
          <button class="btn" onclick="upload()" type="button">下达</button>
          <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
        </div>
      </sf:form>
    </div>
    <script type="text/javascript">
      $(function() {
        $("input[name='twoAdvice']").each(function(){
          var twoAdvice = $(this).val();
          if(twoAdvice == "1"){
            $(this).parents("tr").find("td").eq(0).find("input[type='checkbox']").prop("disabled", true);
          }
        });
      });
    </script>
  </body>
  
</html>