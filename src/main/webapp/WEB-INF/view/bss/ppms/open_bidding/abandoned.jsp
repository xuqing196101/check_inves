<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
      }

      function showPackageType(id) {
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
          url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId=" + id,
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

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function hidePackageType() {
        $("#packageContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownPackageType);
      }

      function select() {
        var packageName = $("#packageName").val();
        if(packageName) {
          layer.open({
            type: 1, //page层
            area: ['400px', '200px'],
            shade: 0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            shadeClose: true,
            content: $("#file")
          });
        } else {
          layer.alert("请选择包");
        }
      }

      function save() {
        var id = [];
        $('input[name="packageId"]').each(function() {
          id.push($(this).val());
        });
        var projectId = $("#projectId").val();
        var flowDefineId = $("#status").val();
        layer.confirm('您确定要废标吗?', {
            title: '提示',
            offset: ['30%', '40%'],
            shade: 0.01
          },
          function(index) {
            layer.close(index);
            $.ajax({
              url: "${pageContext.request.contextPath}/project/abandoned.html?id="+id+"&projectId="+projectId+"&flowDefineId="+flowDefineId,
              type: "post",
              dateType: "text",
              success: function(data) {
                if(data == "\"SCCUESS\"") {
                  layer.msg("废标成功", {
                    time: 2000,
                  });
                  window.setTimeout(function() {
                    window.location.href = "${pageContext.request.contextPath}/project/list.html";
                  }, 1000);

                }

                window.setTimeout(function() {
                  location.reload();
                }, 1000);
              },
              error: function() {
                layer.msg("废标失败", {
                  offset: ['30%', '40%'],
                });
              }
            });
          });
      }
    </script>
  </head>

  <body>
    <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
      <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
    </div>
    <input class=" " readonly id="packageName" value="" placeholder="请选择包" onclick="showPackageType('${project.id}');" type="text">
    <input readonly id="packageId" name="packageId" type="hidden">
    <input type="hidden" id="projectId" value="${project.id}">
    <button class="btn" type="button" onclick="select()">确定</button>

    <div id="file" class="dnone">
      <label class="fl">请选择流程：</label>
      <span class="">
        <select name="status" id="status">
          <option selected="selected" value="">请选择</option>
            <c:forEach items="${list}" var="aa" >
              <option value="${aa.id}">${aa.name}</option>
            </c:forEach>
        </select>
      </span>
      <button class="btn" type="button" onclick="save()">确定</button>
    </div>
  </body>

</html>