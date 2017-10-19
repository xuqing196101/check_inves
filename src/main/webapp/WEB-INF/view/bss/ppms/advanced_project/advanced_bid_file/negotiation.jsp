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
        var index = 0;
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
          url: "${pageContext.request.contextPath}/Adopen_bidding/getpackage.do?projectId=${projectId}",
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

      /** 保存  **/
      function save(id) {
        var net = $("#negotiationRecord" + id).val();
        if(net){
          layer.confirm('您确定要保存吗?', {
          title: '提示',
          shade: 0.01
	        }, function(index) {
	          layer.close(index);
	          var projectId = $("#projectId").val();
	          var createdAt = $("#createdAt" + id).val();
	          var nuter = $("#nuter" + id).val();
	          var negId = $("#negId").val();
	          var uuId = $("#uuId").val();
	          var flowDefineId = $("#flowDefineId").val();
	          $.ajax({
	            url: "${pageContext.request.contextPath}/open_bidding/saveNet.html?projectId=" + projectId + "&createdAt=" + createdAt + "&nuter=" + nuter + "&net=" + net + "&negId=" + negId + "&uuId=" + uuId + "&packageId=" + id,
	            type: "post",
	            dataType: "json",
	            success: function(result) {
	              if(result == "1") {
	                layer.msg("保存成功", {
	                  time: 2000,
	                });
	                window.setTimeout(function() {
	                  window.location.href = "${pageContext.request.contextPath}/Adopen_bidding/negotiation.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
	                }, 1000);
	
	              }
	              if(result == "2") {
	                layer.msg("修改成功");
	              }
	            }
	          });
	        });
        } else {
          layer.msg("内容不能为空");
        }
        
      }

      /** 导出  **/
      function educe() {
        var packageName = $("#packageName").val();
        if(packageName) {
          var id = $("#packageId").val();
          var projectId = $("#projectId").val();
          var createdAt = $("#createdAt" + id).val();
          var nuter = $("#nuter" + id).val();
          var net = $("#negotiationRecord" + id).val();
          window.location.href = "${pageContext.request.contextPath}/Adopen_bidding/educe.html?projectId=" + projectId + "&createdAt=" + createdAt + "&nuter=" + nuter + "&net=" + net;
        } else {
          layer.alert("请选择包！");
        }

      }

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
    <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
      <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
    </div>
      <div class="tab-content mt10">
        <div class="tab-v2">
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
              <input type="hidden" id="flowDefineId" value="${flowDefineId}">
              <input class=" " readonly id="packageName" value="" placeholder="请选择包" onclick="showPackageType();" type="text">
              <input readonly id="packageId" name="packageId" type="hidden">
              <button class="btn btn-windows input" type="button" onclick="educe()">导出</button>
              <c:forEach items="${listResultExpert }" var="list" varStatus="vs">
                <c:set value="${vs.index}" var="index"></c:set>
                <div>
                  <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">包名:<span class="f14 blue">${listResultExpert[index].name }</span></h2>
                </div>
                <div class="p0${index} hide">
                  <table class="table table-bordered left_table">
                    <tbody>
                      <tr>
                        <td class="bggrey" colspan="2">项目编号:<input type="hidden" id="projectId" value="${project.id}" /></td>
                        <td class="p0"><input name="projectNumber" class="m0" id="projectNumber" value="${project.projectNumber}" type="text" class="m0" /><input type="hidden" name="id" id="id" value="${project.id}" /></td>
                        <td class="bggrey">项目名称:</td>
                        <td class="p0"><input name="name" class="m0" id="name" value="${project.name}" type="text" /><input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}" /></td>
                      </tr>
                      <tr>
                        <td class="bggrey" colspan="2">时间:<input type="hidden" id="uuId" value="${uuId}" /></td>
                        <td class="p0"><input readonly="readonly" value="<fmt:formatDate type='date' value='${list.negotiation.createdAt }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="createdAt" id="createdAt${list.id}" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" /></td>
                        <td class="bggrey">地点:</td>
                        <td class="p0"><input name="bidAddress" id="bidAddress" value="${project.bidAddress}" type="text" class="m0" /></td>
                      </tr>
                      <tr>
                        <td class="bggrey" colspan="2">记录人:</td>
                        <td class="p0" colspan="3"><input name="nuter" id="nuter${list.id}" value="${list.negotiation.nuter}" type="text" class="m0" /></td>
                        <u:upload id="uu${vs.index}" auto="true" businessId="${list.id}" typeId="${dataId}" sysKey="2" />
                        <u:show showId="ss${vs.index}" businessId="${list.id}" sysKey="2" typeId="${dataId}" />
                      </tr>
                      <tr>
                        <td class="bggrey" colspan="5">
                          <p align="center" class="f22">谈判小组成员</p>
                        </td>
                      </tr>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">专家姓名</th>
                        <th class="info">工作单位</th>
                        <th class="info">职务</th>
                        <th class="info">备注</th>
                      </tr>
                      <c:forEach items="${list.listProjectExtract}" var="listyes" varStatus="vs">
                        <tr>
                          <td class='tc'>${vs.index+1}</td>
                          <td class='tc'>${listyes.expert.relName}</td>
                          <td class='tc'>${listyes.expert.workUnit}</td>
                          <td class='tc'>${listyes.expert.professTechTitles}</td>
                          <td class='tc'>${listyes.expert.remarks}</td>
                        </tr>
                      </c:forEach>
                      <tr>
                        <td class="bggrey" colspan="5">
                          <p align="center" class="f22">记录内容</p>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="6"><textarea class="col-md-12 col-sm-12 col-xs-12" name="negotiationRecord" id="negotiationRecord${list.id}" style="height:330px" title="不超过800个字">${list.negotiation.negotiationRecord}</textarea></td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="col-md-12 tc mt20">
                    <button class="btn btn-windows git" type="button" onclick="save('${list.id}');">保存</button>
                  </div>
                </div>
              </c:forEach>
            </div>
        </div>
      </div>
    </div>
  </body>

</html>