<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家列表</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
/*    $(function(){
      laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}">=3?3:"${result.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
          return "${result.pageNum}";
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
                $("#form1").submit();
              }
          }
      });
    }); */
    
  $(function() {
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages: "${result.pages}", //总页数
      skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip: true, //是否开启跳页
      total: "${result.total}",
      startRow: "${result.startRow}",
      endRow: "${result.endRow}",
      groups: "${result.pages}">=5?5:"${result.pages}", //连续显示分页数
      curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
        var page = location.search.match(/page=(\d+)/);
          return page ? page[1] : 1;
      }(), 
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("input[name='page']").val(e.curr);
          searchExpert(0);
        }
      }
    });
    
     $("input[name='id']").click(function(index) {
      var id = $(this).val();
      var relName = $(this).parents("tr").find("td").eq(2).text();
       $("input[name='expertId']").val(id);
      $("#relName_name_input_id").val(relName);
    });
  });
  
  //选择的专家
  function checkExpert() {
    var size = $(":radio:checked").size();
    if (!size) {
      layer.msg("请勾选一条记录 !", {
        offset : '150px',
      });
      return;
    }
  /*   var i= $("input[name='expertId']").val(expertId);
    alert(i); */
    $("#check_form_id").submit();   
  }
  //查询
  function searchExpert(sign) {
    if (sign) {
      $("input[name='page']").val(1);
    }
    $("#search_form_id").submit();
  }
  
  //重置搜索
  function resetForm() {
    $("input[name='relName']").val("");
  }
</script>
</head>
<body>
  <div class="wrapper">
    <form action="${pageContext.request.contextPath}/expertBlacklist/expert_list.html"  id="search_form_id" method="get" class="registerform"> 
    <input type="hidden" name="page" id="page">
    <ul class="demand_list">
      <li class="fl">
        <label class="fl mt5">姓名：</label>
          <span><input name="relName" type="text" value="${relName }"/></span>
      </li>
      <li class="fl mt1">
        <input type="button" onclick="searchExpert(1)" class="btn" value="查询" />
        <input onclick="resetForm()" class="btn" type="button" value="重置" />
      </li>
    </ul>
    </form>
<!-- 表格开始-->
  <div class="container">
    <div class="col-md-8">
      </div>
        </div>
          <div class="container margin-top-5">
            <div class="content padding-left-25 padding-right-25 padding-top-5">
              <table class="table table-bordered table-condensed">
                <thead>
                  <tr>
							      <th class="info w50">选择</th>
							      <th class="info w50">序号</th>
							      <th class="info">专家姓名</th>
                  </tr>
                </thead>
                <c:forEach items="${expertAll }" var="e" varStatus="vs">
	                <tr>
							      <td class="tc w30"><input name="id" type="radio" value="${e.id}"></td>
							      <td  class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
							      <td  class="tc">${e.relName}</td>
	                </tr>
                </c:forEach>
              </table>
            <div id="pagediv" align="right"></div>
          </div>
          <div class="tc mb10">
            <a class="btn btn-windows save" onclick="checkExpert()">选择</a>
            <a target="_parent" class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertBlacklist/addBlacklist.html">返回</a>
          </div>
        </div>
      <form target="_parent" id="check_form_id" action="${pageContext.request.contextPath}/expertBlacklist/addBlacklist.html" method="post">
        <input type="hidden" name="expertId" />
        <input id="relName_name_input_id" type="hidden" name="relName" />
      </form>
    </div>
</body>
</html>
