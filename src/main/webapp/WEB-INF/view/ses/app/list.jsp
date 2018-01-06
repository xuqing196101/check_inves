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
        	var version = $("#version").val();
            location.href = "${pageContext.request.contextPath }/appInfo/list.html?page=" + e.curr + "&&version="+version;
          }
        }
      });
    });
  
  /* 回退 */
  function fallback(){
    var authType = "${authType}";
    if(authType != '4'){
		layer.msg("只有资源服务中心才能操作");
		return;
	}
    var type = "";
    layer.open({
        type: 1,
        title: "选择类型",
        shadeClose: true,
        shade: 0.01,
        area: ['350px', '180px'],
        content: $("#selectType"),
        btnAlign : 'c',
        btn: '确定', 
        yes: function (index, layero) {
        	type = $('input[name="selType"]:checked').val();
		    if(typeof (type) == "undefined" || type == ""){
		    	layer.close(index);
		    	layer.msg("请选择一种类型进行版本回退");
		    	return;
		    }
		    $.ajax({
		        url: "${pageContext.request.contextPath }/appInfo/fallback.do",
		        type: "post",
		        data: {
		        	"type" : type,
		        },
		        success: function(data) {
		          if(data == 'success'){
		            window.location.href = "${pageContext.request.contextPath }/appInfo/list.html";
		          }else{
		          	layer.msg("回退失败");
		          }
		          layer.close(index);
		        },
		        error: function() {
		      	  layer.msg("回退失败");
		      	  layer.close(index);
		        }
		     });
        }
    });
  }

  //重置
  function resetQuery() {
    $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    window.location.href = "${pageContext.request.contextPath }/appInfo/list.html";
  }
  
  //查看详情
  function view(version,type){
    var authType = "${authType}";
    if(authType != '4'){
      layer.msg("只有资源服务中心才能操作");
      return;
    }
    window.location.href = "${pageContext.request.contextPath }/appInfo/view.html?version="+version+"&type="+type;
  }
  
  //新增
  function add(){
    var authType = "${authType}";
    if(authType != '4'){
      layer.msg("只有资源服务中心才能操作");
      return;
    }
    window.location.href = "${pageContext.request.contextPath }/appInfo/toAdd.html";
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
          <input type="text" id="version" class="" name = "version" value="${appInfo.version }"/>
        </li>
        <li>
          <label class="fl">类型：</label>
          <select class="w178" name="type">
              <option value="" <c:if test="${appInfo.type == '' }">selected="selected"</c:if> >全部</option>
              <option value="1" <c:if test="${appInfo.type == '1' }">selected="selected"</c:if> >公告App</option>
              <option value="2" <c:if test="${appInfo.type == '2' }">selected="selected"</c:if> >网上商城App</option>
          </select>
        </li>

        <input class="btn fl mt1" type="submit" value="查询" /> 
        <input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>  
      </ul>
      <div class="clear"></div>
      </form>
    </div>
    <!-- 表格开始 -->
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
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
           <th width="">类型</th>
         </tr>
        </thead>
        <tbody>
          <c:forEach items="${info.list }" var="appInfo" varStatus="vs">
            <tr class="tc">
              <%-- <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${appInfo.version }" /></td> --%>
              <td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
              <td class="tc" onclick = "view('${appInfo.version}','${appInfo.type }')">${appInfo.version }</td>
              <td class="tc">
                <fmt:formatDate value="${appInfo.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc">
                <c:choose>
                	<c:when test="${appInfo.type != null && appInfo.type == '2' }">网上商城App</c:when>
                	<c:otherwise>公告App</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </tbody>
    </table>
  </div>
  <div id="pagediv" align="right"></div>
  <div id="selectType" class="p20 tc hide">
  	<input type = "radio" name = "selType" id="selType_1" value = "1"><label for="selType_1" class="hand m_inline ml5">公告App</label>
  	<input type = "radio" name = "selType" id="selType_2" value = "2" class="ml20"><label for="selType_2" class="hand m_inline ml5">网上商城App</label>
  </div>
  </div>
</body>
</html>