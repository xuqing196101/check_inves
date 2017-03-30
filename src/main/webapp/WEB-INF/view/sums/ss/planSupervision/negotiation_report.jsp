<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">

    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>

    <script type="text/javascript">
      $(function() {
        var index = 0 ;
         var divObj = $(".p0" + index);
              $(divObj).removeClass("hide");
              $("#package").removeClass("shrink");        
              $("#package").addClass("spread");
        
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
          url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId=${projectId}",
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
      
      function onBodyDownPackageType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
          hidePackageType();
        }
      }
      
      function hidePackageType() {
        $("#packageContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownPackageType);

      }
      
	    
	    /** 导出  **/
	    function educe(){
	       var packageName = $("#packageName").val();
	       if(packageName){
	         var id=$("#packageId").val();
	         var projectId = $("#projectId").val();
	         var reviewTime = $("#reviewTime"+id).val();
	         var reviewSite = $("#reviewSite"+id).val();
	         var finalOffer = $("#finalOffer"+id).val();
	         var talks = $("#talks"+id).val();
	         var supperName = $("#supperName"+id).val();
	         window.location.href = "${pageContext.request.contextPath}/open_bidding/educes.html?projectId="
	         +projectId+"&reviewTime="+reviewTime+"&reviewSite="+reviewSite+"&finalOffer="+finalOffer+"&talks="+talks+"&supperName="+supperName+"&packageId="+id;
	       }else{
	         layer.alert("请选择包!");
	       }
	       
	    }
	    
	    function ycDiv(obj, index) {
              if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
                    $(obj).removeClass("shrink");
                    $(obj).addClass("spread");
                  } else {
                    if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                      $(obj).removeClass("spread");
                      $(obj).addClass("shrink");
                    }
                  }
                  
                  var divObj = new Array();
                  divObj = $(".p0" + index);
                  for (var i =0; i < divObj.length; i++) {
                      if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                        $(divObj[i]).removeClass("hide");
                      } else {
                        if ($(divObj[i]).hasClass("p0"+index)) {
                          $(divObj[i]).addClass("hide");
                        };
                      };
                  };
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
    </script>
  </head>

  <body>
    <div class="container">
      <div class="tab-content mt10">
        <div class="tab-v2">
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
                <div>
                  <h2 class="count_flow shrink hand" id="package">包名:<span class="f14 blue">${packages.name}</span></h2>
                </div>
                <table class="table table-bordered left_table">
                  <tbody>
                    <tr>
                      <td class="bggrey" colspan="2">项目编号:</td>
                      <td class="p0"><input name="projectNumber" class="m0" disabled="disabled" id="projectNumber" value="${project.projectNumber}" type="text" class="m0" /><input type="hidden" name="id" id="id" value="${project.id}" /></td>
                      <td class="bggrey" >项目名称:</td>
                      <td class="p0"><input name="name" class="m0" id="name" disabled="disabled" value="${project.name}" type="text" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="2">谈判时间:</td>
                      <td class="p0"><input readonly="readonly" disabled="disabled"  value="<fmt:formatDate type='date' value='${list.negotiationReport.reviewTime }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="reviewTime" id="reviewTime${list.id}" type="text" /></td>
                      <td class="bggrey">谈判地点:</td>
                      <td class="p0"><input name="reviewSite" disabled="disabled"  value="${packages.negotiationReport.reviewSite}" type="text" class="m0" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="2">成交供应商:</td>
                      <td class="p0" ><input name="nuter" disabled="disabled"  type="text" class="m0" value="${packages.supplierList[0].supplier.supplierName}"/></td>
                      <td class="bggrey">报价:</td>
                      <td class="p0"><input name="finalOffer" disabled="disabled" value="${packages.supplierList[0].totalPrice}" type="text" class="m0" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="5">
                        <p align="center" class="f22">谈判情况</p>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="6"><textarea class="col-md-12 col-sm-12 col-xs-12" name="talks" disabled="disabled" style="height:330px" title="不超过800个字">${packages.negotiationReport.talks}</textarea></td>
                    </tr>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">谈判小组成员</th>
                      <th class="info">专家姓名</th>
                      <th class="info">工作单位</th>
                      <th class="info">职务</th>
                    </tr>
                    <c:set var="count" value="0"/>
                    <c:forEach items="${expertSigneds}" var="packageExpert" varStatus="vs">
                    <c:if test="${packages.id == packageExpert.packageId}">
                      <c:set var="count" value="${count+1}"/>
                      <tr>
                        <td class='tc'>${count}</td>
                        <td class='tc'>
                          <c:if test="${packageExpert.isGroupLeader == 1}">组长</c:if>
                          <c:if test="${packageExpert.isGroupLeader == 0}">成员</c:if>
                        </td>
                        <td class='tc'>${packageExpert.expert.relName}</td>
                        <td class='tc'>${packageExpert.expert.workUnit}</td>
                        <td class='tc'>${packageExpert.expert.professTechTitles}</td>
                      </tr>
                      </c:if>
                    </c:forEach> 
                  </tbody>
                </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>