<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
<title>App版本管理查询列表</title>
<script type="text/javascript">
  /* 分页 */
  $(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages : "${info.pages}", //总页数
        skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip : true, //是否开启跳页
        total : "${info.total}",
        startRow : "${info.startRow}",
        endRow : "${info.endRow}",
        groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
        curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
          return "${info.pageNum}";
        }(),
        jump : function(e, first) { //触发分页后的回调
          if(!first){ //一定要加此判断，否则初始时会无限刷新
            location.href = "${pageContext.request.contextPath }/appInfo/list.html?page=" + e.curr;
          }
        }
      });
    });

/** 全选全不选 */
  function selectAll(){
    var checklist = document.getElementsByName ("chkItem");
    var checkAll = document.getElementById("checkAll");
    if(checkAll.checked){
        for(var i=0;i<checklist.length;i++){
          checklist[i].checked = true;
        } 
    }else{
        for(var j=0;j<checklist.length;j++){
          checklist[j].checked = false;
        }
      }
  }

  /** 单选 */
  function check(){
    var count=0;
    var checklist = document.getElementsByName ("chkItem");
    var checkAll = document.getElementById("checkAll");
    for(var i=0;i<checklist.length;i++){
      if(checklist[i].checked == false){
        checkAll.checked = false;
        break;
      }
      for(var j=0;j<checklist.length;j++){
        if(checklist[j].checked == true){
          checkAll.checked = true;
          count++;
        }
      }
    }
  }

  /* 回退 */
  function fallback(){
    var version = [];
    $('input[name="chkItem"]:checked').each(function() {
      version.push($(this).val());
    });
    if(version.length == 1) {
      layer.confirm('您确定要回退吗?', {
        title: '提示',
        offset: ['222px', '360px'],
        shade: 0.01
      }, function(index) {
        layer.close(index);
        $.ajax({
          url: "${pageContext.request.contextPath }/appInfo/fallback.html",
          type: "post",
          data: {
	        version: version
          },
          success: function() {
             window.location.href = "${pageContext.request.contextPath }/appInfo/list.html";
          },
          error: function() {
          }
       });
      });
    } else if(version.length > 1){
      layer.alert("只能选择一个版本", {
        offset: ['222px', '390px'],
        shade: 0.01
      });
    } else {
      layer.alert("请选择要回退的版本", {
        offset: ['222px', '390px'],
        shade: 0.01
      });
    }
  }

  //重置
  function resetQuery() {
    $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    window.location.href = "${pageContext.request.contextPath }/appInfo/list.html";
  }
</script>
</head>
<body>
<!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">支撑环境</a></li><li><a href="javascript:void(0)">App管理</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <!-- app版本信息列表页面开始 -->
  <div class="container">
    <div class="headline-v2">
      <h2>App版本信息列表</h2>
    </div>
    <div class="search_detail">
      <form action="${pageContext.request.contextPath }/appInfo/list.html" method="post" class="mb0" id = "form1">
      <ul class="demand_list">
        <li>
          <label class="fl">版本号：</label>
          <input type="text" id="" class="" name = "version" value="${appInfo.version }"/>
        </li>
        <input class="btn fl mt1" type="submit" value="查询" /> 
        <input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>  
      </ul>
      <div class="clear"></div>
      </form>
    </div>
    <!-- 表格开始 -->
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows add" type="button" onclick="window.location.href = '${pageContext.request.contextPath }/appInfo/toAdd.html'">新增</button>
      <button class="btn btn-windows delete" type="button" onclick="fallback()">回退</button>
    </div>
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover">
        <thead>
         <tr class="info">
         <!-- <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
           <th class="w50">序号</th>
           <th width="">版本号</th>
           <th width="">更新时间</th>
         </tr>
        </thead>
        <tbody>
          <c:forEach items="${info.list }" var="appInfo" varStatus="vs">
            <tr class="tc">
              <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${appInfo.version }" /></td>
              <td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
              <td class="tl">${appInfo.version }</td>
              <td class="tc">
                <fmt:formatDate value="${appInfo.createAt }" pattern="yyyy-MM-dd" />
              </td>
            </tr>
          </c:forEach>
        </tbody>
    </table>
  </div>
  <div id="pagediv" align="right"></div>
  </div>
</body>
</html>