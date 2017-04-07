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
    <!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

  </head>
  <body>
    <!--面包屑导航开始-->
    <div class="search_detail ml0">
          <ul class="demand_list">
            <li class="fl"><label class="fl">专家姓名：</label><span><input
                type="text" id="expertName" class="" value="${expertName}"  name="expertName"/></span></li>
          </ul>
            <input type="submit" class="btn fl queryBtn" value="查询"/>
            <input type="reset" class="btn fl resetBtn" value="重置">
          <div class="clear"></div>
      </div>
    <input type="hidden" id="hiddenValue"  value="" />
    <input type="hidden" name="packageId" value="${packageId }" />
    <input type="hidden" name="projectId" value="${projectId }" />
    <input type="hidden" id="kind" value="${kind }" />

    <table class="table table-bordered table-condensed mt5">
      <thead>
        <tr>
          <th class="info w30"><input id="checkAll" type="checkbox" /></th>
            <th class="info">专家姓名</th>
            <th class="info">专家类别</th>
            <th class="info">证件号</th>
            <th class="info">现任职务</th>
            <th class="info">联系电话</th>
        </tr>
      </thead>
      <c:forEach items="${list.list}" var="ext" varStatus="vs">
        <tr>
          <td class="tc opinter"><input type="checkbox" name="chkItem" value="${ext.id}" /></td>
          <td class="tc opinter">${ext.relName }</td>
          <td class="tc opinter">
              <c:forEach var="expertType" items="${ddList}">
                  <c:if test="${projectExtract.reviewType eq expertType.id}">
                      ${expertType.name}
                      <input type="hidden" name="packageExperts[${listCount}].reviewTypeId" value="${expertType.id}">
                  </c:if>
              </c:forEach>
          </td>
          <td class="tc opinter">${ext.idCardNumber}</td>
          <td class="tc opinter">${ext.atDuty}</td>
          <td class="tc opinter">${ext.mobile}</td>
        </tr>
      </c:forEach>
    </table>
    <div id="pagediv" align="right"></div>
    <div class="col-md-12 tc mt5 footerDiv">
      <button class="btn btn-windows save" >保存</button>
      <button class="btn btn-windows back" >返回</button>
    </div>
    
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/prms/expertCiteList.js"></script>
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
                    $.ajaxSetup({cache:false});
                    var packageId = "${packageId }";
                    var projectId = "${projectId }";
                    var path="${pageContext.request.contextPath}/expert/gotoCiteExpertView.html?packageId=" + packageId + "&projectId=" + projectId + "&expertName="+$("#expertName").val()+"&page="+e.curr+"&ix=${ix}"+"&selectValue="+$("#hiddenValue").val();
                    $("#tab-1").load(path);

                }
            }
        });
        var selectValue = "${selectValue}";
        if(selectValue != null && selectValue != ''){
            _selected(selectValue);
        }
    });
</script>