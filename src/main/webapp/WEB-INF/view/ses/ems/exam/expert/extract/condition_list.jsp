<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <title>专家抽取</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
    <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertExtract/extract.js"></script> --%>
  </head>
  <script type="text/javascript">
      $(function() {
        //获取查看或操作权限
        var isOperate = $('#isOperate', window.parent.document).val();
        if(isOperate == 0) {
          //只具有查看权限，隐藏操作按钮
          $(":button").each(function() {
            $(this).hide();
          });
        }
      });
      function showPackageType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClick,
            onCheck: onCheck
          }
        };

        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId=${project.id}",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#packageName");
        var cityOffset = $("#packageName").offset();
        $("#packageContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownPackageType);
      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#packageName");
        cityObj.attr("value", v);
        cityObj.attr("title", v);
        $("#packageId").val(rid);
      }
      
      function onBodyDownPackageType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
          hidePackageType();
        }
      }
      
      function hidePackageType() {
        $("#packageContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownPackageType);
      }

      function extractVerify(projectId) {
        var packageName = $("#packageName").val();
        if(packageName){
          var packageId = $("#packageId").val();
          window.open("${pageContext.request.contextPath}/extractExpert/toExpertExtract.html?projectId=" + projectId + "&packageId=" + packageId + "&projectInto=relPro" + "&packageName=" + packageName);
        } else {
          layer.msg("请选择包!");
        }
        
      }
    </script>

  <body>
    <!-- 项目戳开始 -->
    <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
      <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
    </div>
    <div class="container_box col-md-12 col-sm-12 col-xs-12">
      <form id="form">
        <h2 class="count_flow"><i>1</i>项目信息</h2>
        <ul class="ul_list">
          <li class="col-md-3 col-sm-4 col-xs-12 pl15">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red0">*</span> 项目名称:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input id="name" name="name" value="${project.name}" type="text">
              <span class="add-on">i</span>
              <div class="cue" id="projectNameError"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red1">*</span> 项目编号:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input id="projectNumber" name="projectNumber" value="${project.projectNumber}" type="text">
              <span class="add-on">i</span>
              <div class="cue" id="projectNumberError"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red2">*</span>采购方式:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input id="purchaseType" name="purchaseType" value="${project.purchaseType}" type="text">
              <span class="add-on">i</span>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red3">*</span> 开标日期:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="col-md-12 col-sm-12 col-xs-6 p0" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" id="tenderTimeId" readonly="readonly" name="bidDate" value="<fmt:formatDate value='${project.bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
              <div class="cue" id="tenderTimeError"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red3">*</span> 包名:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class=" " readonly id="packageName" value="" placeholder="请选择包" onclick="showPackageType();" type="text">
              <input readonly id="packageId" name="packageId" type="hidden">
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <div class="col-md-12 tc mt20">
              <button class="btn" onclick="extractVerify('${project.id}');" type="button">抽取</button>
              <!-- <button class="btn" onclick="finish();" type="button">完成抽取</button>
                <button class="btn" onclick="temporary();" type="button">暂存</button> -->
            </div>
          </li>
        </ul>
      </form>
    </div>
    <!-- 项目信息结束 -->
    <!-- 条件开始 -->
    <div class="padding-top-10 clear">
      <div class="container_box col-md-12 col-sm-12 col-xs-12">
        <h2 class="count_flow"><i>2</i>抽取结果</h2>
        <ul class="ul_list">
          <!-- Begin Content -->
          <table id="table" class="table table-bordered table-condensed">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info" width="15%">包名</th>
                <th class="info" width="15%">专家名称</th>
                <th class="info" width="15%">类型</th>
                <th class="info" width="18%">联系人电话</th>
              </tr>
            </thead>
            <tbody id="supplierList">
              <c:if test="${extRelates ne null}">
                <c:forEach items="${extRelates}" var="list" varStatus="vs">
                  <tr>
                    <td class="tc w50">${vs.index+1}</td>
                    <td>${list.packageName}</td>
                    <td>${list.expert.relName}</td>
                    <td>${list.expert.expertsTypeId}</td>
                    <td>${list.expert.telephone}</td>
                  </tr>
                </c:forEach>
              </c:if>
            </tbody>
          </table>
        </ul>
      </div>
    </div>
  </body>

</html>