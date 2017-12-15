<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>专家黑名单</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
  $(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${result.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${result.total}",
      startRow : "${result.startRow}",
      endRow : "${result.endRow}",
      groups : "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${result.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#form1").submit();
        }
      }
    });
  });
</script>
<script type="text/javascript">
  /** 全选全不选 */
  function selectAll() {
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    if (checkAll.checked) {
      for (var i = 0; i < checklist.length; i++) {
        checklist[i].checked = true;
      }
    } else {
      for (var j = 0; j < checklist.length; j++) {
        checklist[j].checked = false;
      }
    }
  }

  /** 单选 */
  function check() {
    var count = 0;
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    for (var i = 0; i < checklist.length; i++) {
      if (checklist[i].checked == false) {
        checkAll.checked = false;
        break;
      }
      for (var j = 0; j < checklist.length; j++) {
        if (checklist[j].checked == true) {
          checkAll.checked = true;
          count++;
        }
      }
    }
  }

  /**添加页面*/
  function add() {
	  var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
    window.location.href = "${pageContext.request.contextPath}/expertBlacklist/addBlacklist.html";
  }

  //更新
  function update() {
	  var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
    var id = [];
    var e_status;
    $('input[name="chkItem"]:checked').each(function(){
      id.push($(this).val());
      e_status = $(this).parent("td").parent("tr").find("td").eq(7).text().trim();
    });
    if (id.length == 1) {
      if (e_status != "手动移除") {
        window.location.href = "${pageContext.request.contextPath}/expertBlacklist/editBlacklist.html?id="
            + id;
      } else {
        layer.msg("不能修改手动移除!", {
          offset : '300px',
        });
        return;
      }
    } else if (id.length > 1) {
      layer.alert("只能选择一个", {
        offset : '200px'
      });
    } else {
      layer.alert("请选择需要修改的信息", {
        offset : '200px'
      });
    }
  }

  //移除
  function updateStatus() {
	  var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
    var ids = [];
    var type;
    $('input[name="chkItem"]:checked').each(function() {
      ids.push($(this).val());
      type = $(this).parents("tr").find("td").eq(7).text();
      type = $.trim(type);

    });
    if (type == "过期") {
      layer.msg("过期的不能手动移除 !", {
        offset : '300px',
      });
      return;
    } else if (type == "手动移除") {
      layer.msg("不能重复手动移除 !", {
        offset : '300px',
      });
      return;
    }

    if (ids.length > 0) {
      layer.confirm('您确定要移除黑名单吗?',
        {
          title : '提示！',
          offset : [ '200px' ]
        },
          function(index){
            layer.close(index);
            $.ajax({
              url : "${pageContext.request.contextPath}/expertBlacklist/updateStatus.html",
              data : "ids=" + ids + "&status=2",
              success : function() {
                layer.msg("移除成功", {
                  offset : '200px'
                });
                window.setTimeout(function() {
                  location.href = "${pageContext.request.contextPath}/expertBlacklist/blacklist.html";
                }, 1000);
              },
              error : function(message) {
                layer.msg("移除失败", {
                  offset : '200px'
                });
              }
            });
          });
    } else {
      layer.alert("请选择需要移除黑名单的信息！", {
        offset : '200px'
      });
    }
  }

  function log() {
	  var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
    window.location.href = "${pageContext.request.contextPath}/expertBlacklist/expertBlackListLog.html";
  }

  //重置因在IE8里面下拉框removeAttr("selected")、attr("selected", false)失效,此方法暂时不用
  function resetForm() {
    //$("#relName").attr("value","");
    $("#relName").val("");
    //还原select下拉列表只需要这一句，注：这几个方法在IE8失效
    $("#punishDate option:selected").removeAttr("selected");
    $("#punishType option:selected").removeAttr("selected");
    $("#punishDate option:selected").attr("selected", false);
    $("#punishType option:selected").attr("selected", false);
  }

  function resetAll() {
    $("#relName").val("");
    $("#punishDate").val("");
    $("#punishType").val("");
  }
</script>
</head>
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
        <li><a href="javascript:void(0)">支撑系统</a></li>
        <li><a href="javascript:void(0);">专家管理</a></li>
        <li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/expertBlacklist/blacklist.html')">专家黑名单</a></li>
      </ul>
    </div>
  </div>
  <!-- 搜索 -->
  <div class="container">
    <div class="headline-v2">
      <h2>专家黑名单列表</h2>
    </div>

    <div class="search_detail">
    <form action="${pageContext.request.contextPath}/expertBlacklist/blacklist.html" method="post" id="form1" class="mb0">
    <input type="hidden" name="page" id="page">
    <div class="m_row_5">
    <div class="row">
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家姓名：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" id="relName" name="relName" value="${relName }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">处罚时限：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="punishDate" id="punishDate" class="w100p h32 f14">
              <option value="">-请选择-</option>
              <option <c:if test="${punishDate =='3个月' }">selected</c:if> value="3个月">3个月</option>
              <option <c:if test="${punishDate =='6个月' }">selected</c:if> value="6个月">6个月</option>
              <option <c:if test="${punishDate =='一年' }">selected</c:if> value="一年">一年</option>
              <option <c:if test="${punishDate =='两年' }">selected</c:if> value="两年">两年</option>
              <option <c:if test="${punishDate =='三年' }">selected</c:if> value="三年">三年</option>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">处罚类型：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="punishType" id="punishType" class="w100p h32 f14">
              <option value=''>-请选择-</option>
              <option <c:if test="${punisType =='1' }">selected</c:if> value="1">警告</option>
              <option <c:if test="${punisType =='2' }">selected</c:if> value="2">严重警告</option>
              <option <c:if test="${punisType =='3' }">selected</c:if> value="3">取消资格</option>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-12 f0">
            <input type="submit" class="btn mb0 h32" value="查询" />
            <button onclick="resetAll()" class="btn mb0 mr0 h32">重置</button>
          </div>
        </div>
      </div>
    </div>
    </div>
    </form>
    </div>
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows add" type="button" onclick="add();">新增</button>
      <button class="btn btn-windows edit" type="button" onclick="update();">修改</button>
      <button class="btn btn-windows delete" type="button" onclick="updateStatus();">移除</button>
      <button class="btn" type="button" onclick="log();">历史记录</button>
    </div>
    <!-- 表格开始-->
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover">
        <thead>
          <tr>
            <th class="info w30"><input type="checkbox" onclick="selectAll();" id="checkAll"></th>
            <th class="info w50">序号</th>
            <th class="info" width="15%">姓名</th>
            <th class="info" width="10%">入库时间</th>
            <th class="info" width="10%">处罚日期</th>
            <th class="info" width="8%">处罚时限</th>
            <th class="info" width="10%">处罚方式</th>
            <th class="info">状态</th>
            <th class="info" width="30%">处罚理由</th>
          </tr>
        </thead>
        <c:forEach items="${expertList }" var="e" varStatus="vs">
          <tr>
            <td class="tc w30"><input type="checkbox" value="${e.id }" name="chkItem" onclick="check()" id="${e.id}"></td>
            <td class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
            <td class="tl">${e.relName }</td>
            <td class="tc"><fmt:formatDate type='date' value='${e.storageTime }' dateStyle="default" pattern="yyyy-MM-dd" /></td>
            <td class="tc"><fmt:formatDate type='date' value='${e.dateOfPunishment }' dateStyle="default" pattern="yyyy-MM-dd" /></td>
            <td class="tc">${e.punishDate }</td>
            <td class="tl">
              <c:if test="${e.punishType == 1}">警告</c:if>
              <c:if test="${e.punishType == 2}">严重警告</c:if>
              <c:if test="${e.punishType == 3}">取消资格</c:if>
            </td>
            <td class="tl">
              <c:if test="${e.status == 0}">处罚中</c:if>
              <c:if test="${e.status == 1}">过期</c:if>
              <c:if test="${e.status == 2}">手动移除</c:if>
          </td>
            <td class="tl" title="${e.reason }">
              <c:if test="${fn:length(e.reason) > 20 }">${fn:substring(e.reason, 0, 20)}...</c:if>
              <c:if test="${fn:length(e.reason) <= 20 }">${e.reason }</c:if></td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <div id="pagediv" align="right"></div>
  </div>
</body>
</html>
