<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/" target="_self">

    <title>专家列表</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
    /**查詢按钮*/
    $(".queryBtn").click(function() {
        $.ajaxSetup({
          cache: false
        });
        var packageId = $("input[name='packageId']").val();
        var projectId = $("input[name='projectId']").val();
        var path = "${pageContext.request.contextPath}/adPackageExpert/gotoCiteExpertView.html?packageId=" + packageId + "&projectId=" + projectId + "&expertName=" + $("#expertName").val() + "&expertMobile=" + $("#expertMobile").val();
        $("#tab-1").load(path);
      })
      /**重置按钮*/
    $(".resetBtn").click(function() {
        var packageId = $("input[name='packageId']").val();
        var projectId = $("input[name='projectId']").val();
        var path = "${pageContext.request.contextPath}/adPackageExpert/gotoCiteExpertView.html?packageId=" + packageId + "&projectId=" + projectId
        $("#tab-1").load(path);
      })
      /**点击全选按钮*/
    $("#checkAll").click(function() {
        var value = "";
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
            var hiddenValue = $("#hiddenValue").val();
            if(hiddenValue != null && hiddenValue != '') {
              var newStr = hiddenValue.replace(checklist[i].value + ",", "");
              $("#hiddenValue").val(newStr);
            }
            value = value + checklist[i].value + ",";
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
            var hiddenValue = $("#hiddenValue").val();
            var newStr = hiddenValue.replace(checklist[j].value + ",", "");
            $("#hiddenValue").val(newStr);
          }
        }
        $("#hiddenValue").val($("#hiddenValue").val() + value);
      })
      /**初始化选中值,避免分页取消勾选*/
    function _selected(value) {
      var count = 0;
      $("#hiddenValue").val(value);
      var strs = new Array(); //定义一数组
      strs = value.split(","); //字符分割
      var checkAll = document.getElementById("checkAll");
      var checklist = document.getElementsByName("chkItem");
      for(var j = 0; j < checklist.length; j++) {
        for(var k = 0; k < strs.length; k++) {
          if(checklist[j].value == strs[k]) {
            checklist[j].checked = true;
            count++;
          }
        }
      }
      if(count != 0 && checklist.length == count) {
        checkAll.checked = true;
      }
    }
    /**单选一个checkbox*/
    $("input[name='chkItem']").click(function() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            var hiddenValue = $("#hiddenValue").val();
            // != null 去除重复
            if(hiddenValue != null && hiddenValue != '') {
              var newStr = hiddenValue.replace(checklist[i].value + ",", "");
              $("#hiddenValue").val(newStr);
            }
          }
          var value = "";
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              var hiddenValue = $("#hiddenValue").val();
              // != null 去除重复
              if(hiddenValue != null && hiddenValue != '') {
                var newStr = hiddenValue.replace(checklist[j].value + ",", "");
                $("#hiddenValue").val(newStr);
              }
              value = value + checklist[j].value + ",";
            }
          }
          $("#hiddenValue").val($("#hiddenValue").val() + value);
        }
        var len = $("input[name='chkItem']:checked").length;
        if(len != 0 && (len == 0 && checklist.length == 0)) {
          checkAll.checked = true;
        }
      })
      /**返回按钮*/
    $(".footerDiv > .back").click(function() {
        var projectId = $("input[name='projectId']").val();
        $.ajaxSetup({
          cache: false
        });
        var path = '${pageContext.request.contextPath}/adPackageExpert/auditManage.html?projectId=' + projectId;
        $("#tab-1").load(path);
      })
      /**保存按钮*/
    $(".footerDiv > .save").click(function() {
      var packageId = $("input[name='packageId']").val();
      var projectId = $("input[name='projectId']").val();
      var idstr = $("#hiddenValue").val();
      if(idstr == '') {
        layer.alert("请选择专家", {
          offset: '50px',
          shade: 0.01
        });
      } else {
        $.ajax({
          url: '${pageContext.request.contextPath}/expert/saveCiteExpert.do',
          type: 'POST',
          data: {
            'packageId': packageId,
            'expertIds': idstr
          },
          dataType: 'json',
          success: function(data) {
            if(data.isSuccess) {
              if(data.messageCode == 20) {
                layer.alert("引用临时专家保存成功", {
                  offset: '50px',
                  shade: 0.01,
                  time: 500
                });
                var path = "${pageContext.request.contextPath}/adPackageExpert/auditManage.html?projectId=" + projectId
                $("#tab-1").load(path);
              } else {
                layer.alert("服务器异常", {
                  offset: '50px',
                  shade: 0.01
                });
              }
            } else {
              layer.alert("服务器异常", {
                offset: '50px',
                shade: 0.01
              });
            }
          },
          error: function() {

          }
        });
      }
    })
  </script>

  <body>
    <!--面包屑导航开始-->
    <div class="search_detail ml0">
      <ul class="demand_list">
        <li class="fl"><label class="fl">专家姓名：</label><span><input
                type="text" id="expertName" class="" value="${expertName}"  name="expertName"/></span></li>
        <li class="fl"><label class="fl">联系电话：</label><span><input
                type="text" id="expertMobile" class="" value="${expertMobile}"  name="expertMobile"/></span></li>
      </ul>
      <input type="submit" class="btn fl queryBtn" value="查询" />
      <input type="reset" class="btn fl resetBtn" value="重置">
      <div class="clear"></div>
    </div>
    <input type="hidden" id="hiddenValue" value="" />
    <input type="hidden" name="packageId" value="${packageId }" />
    <input type="hidden" name="projectId" value="${projectId }" />
    <input type="hidden" id="kind" value="${kind }" />

    <table class="table table-bordered table-condensed mt5">
      <thead>
        <tr>
          <th class="info w30"><input id="checkAll" type="checkbox" /></th>
          <th class="info" width="20%">专家姓名</th>
          <th class="info" width="15%">专家类别</th>
          <th class="info" width="23%">证件号</th>
          <th class="info" width="20%">现任职务</th>
          <th class="info">联系电话</th>
        </tr>
      </thead>
      <c:forEach items="${list.list}" var="ext" varStatus="vs">
        <tr>
          <td class="tc opinter"><input type="checkbox" name="chkItem" value="${ext.id}" /></td>
          <td class="tl opinter">${ext.relName }</td>
          <td class="tl opinter">
            <c:forEach var="expertType" items="${ddList}">
              <c:if test="${ext.expertsTypeId eq expertType.id}">
                ${expertType.name}
                <input type="hidden" name="packageExperts.reviewTypeId" value="${expertType.id}">
              </c:if>
            </c:forEach>
          </td>
          <td class="tc opinter">${ext.idCardNumber}</td>
          <td class="tl opinter">${ext.atDuty}</td>
          <td class="tc opinter">${ext.mobile}</td>
        </tr>
      </c:forEach>
    </table>
    <div id="pagediv" align="right"></div>
    <div class="col-md-12 tc mt5 footerDiv">
      <button class="btn btn-windows save">保存</button>
      <button class="btn btn-windows back">返回</button>
    </div>

  </body>

</html>
<script type="text/javascript">
  $(function() {
    /**分页组件*/
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages: "${list.pages}", //总页数
      skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip: true, //是否开启跳页
      total: "${list.total}",
      startRow: "${list.startRow}",
      endRow: "${list.endRow}",
      groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
      curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

        return "${list.pageNum}";
      }(),
      jump: function(e, first) { //触发分页后的回调
        if(!first) { //一定要加此判断，否则初始时会无限刷新
          $.ajaxSetup({
            cache: false
          });
          var packageId = "${packageId }";
          var projectId = "${projectId }";
          var path = "${pageContext.request.contextPath}/adPackageExpert/gotoCiteExpertView.html?packageId=" + packageId + "&projectId=" + projectId +
            "&expertName=" + $("#expertName").val() + "&expertMobile=" + $("#expertMobile").val() + "&page=" + e.curr + "&ix=${ix}" + "&selectValue=" + $("#hiddenValue").val();
          $("#tab-1").load(path);

        }
      }
    });
    var selectValue = "${selectValue}";
    if(selectValue != null && selectValue != '') {
      _selected(selectValue);
    }
  });
</script>