<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>短信发送记录列表</title>
<script type="text/javascript">
	/*分页  */
	$(function() {
	  laypage({
	    cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	    pages : "${info.pages}", //总页数
	    skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	    skip : true, //是否开启跳页
	    total : "${info.total}",
	    startRow : "${info.startRow}",
	    endRow : "${info.endRow}",
	    groups : "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
	    curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
	      return "${info.pageNum}";
	    }(),
	    jump : function(e, first) { //触发分页后的回调
	      if (!first) { //一定要加此判断，否则初始时会无限刷新
	        $("#page").val(e.curr);
	        $("#form1").submit();
	      }
	    }
	  });
	});
	
	/* 重置 */
	function form_reset(){
	  $("#form1").find("input").val("");
	  var SelectArr = $("#form1").find("select");
	  for (var i = 0; i < SelectArr.length; i++) {
	    SelectArr[i].options[0].selected = true; 
	  }
	  $("#page").val("1");
	}
	
	/* 查看详情 */
	function view(id){
		window.location.href = "${pageContext.request.contextPath}/smsRecord/view.html?id="+id;
	}
</script>
</head>
<body>
 <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
        <li><a href="javascript:void(0);">支撑环境</a></li>
        <li><a href="javascript:void(0);">系统管理</a></li>
        <li><a href="javascript:void(0);">短信记录查询</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <!-- 项目列表开始-->
  <div class="container">
    <div class="headline-v2"><h2>短信记录查询</h2></div>
    <!-- 项目戳开始 -->
    <h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/smsRecord/list.html" id="form1" method="post" class="mb0">
        <input type="hidden" name="page" id="page" value="1">
        <ul class="demand_list">
          <li>
            <label class="fl">发送环节：</label>
            <select class="w178" name="sendLink">
              <option value="" <c:if test="${smsRecord.sendLink == '' }">selected="selected"</c:if> >全部</option>
              <c:forEach items="${sendLinkList}" var="map">
                <option value="${map.id}" <c:if test="${smsRecord.sendLink == map.id }">selected="selected"</c:if> >${map.name}</option>
              </c:forEach>
            </select>
          </li>
          <li>
            <label>接收人：</label>
            <input type="text" name="recipient" id="recipient" value="${smsRecord.recipient }" />
          </li>
          <li>
            <label class="fl">接收号码：</label>
            <input type="text" name="receiveNumber" id="receiveNumber" value="${smsRecord.receiveNumber }" />
          </li>
          <li>
            <label class="fl">操作人：</label>
            <input type="text" name="operator" id="operator" value="${smsRecord.operator }" />
          </li>
          <li>
            <label class="fl">发送时间：</label>
            <input id="startTime" name="startTime" type="text" value="${smsRecord.startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
            <span class="fl ml5 mr5">-</span><input id="endTime" name="endTime" type="text" value="${smsRecord.endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
          </li>
          <li>
            <label class="fl">状态：</label>
            <select class="w178" name="status">
              <option value="" <c:if test="${smsRecord.status == '' }">selected="selected"</c:if> >全部</option>
              <option value="0" <c:if test="${smsRecord.status == '0' }">selected="selected"</c:if> >发送成功</option>
              <option value="1" <c:if test="${smsRecord.status == '1' }">selected="selected"</c:if> >发送失败</option>
            </select>
          </li>
          <button class="btn fl mt1" type="submit">查询</button>
          <button type="button" class="btn fl mt1" onclick="form_reset()">重置</button>
        </ul>
        <div class="clear"></div>
      </form>
    </h2>
    <div class="table_box">
      <table class="table table-striped table-bordered table-hover">
        <thead>
          <tr>
            <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
            <th class="info w50">序号</th>
            <th class="info">发送环节</th>
            <th class="info">操作人</th>
            <th class="info">发送内容</th>
            <th class="info">接收人</th>
            <th class="info">接收号码</th>
            <th class="info">发送时间</th>
            <th class="info">状态</th>
            <th class="info">失败原因</th>
          </tr>
        </thead>
        <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <tr>
            <td class="tc w30"><input type="checkbox" name="chkItem" /></td>
            <td class="tc w50" onclick="view('${obj.id}')">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
            <td class="tc" onclick="view('${obj.id}')">${obj.sendLink}</td>
            <td class="tc" onclick="view('${obj.id}')">${obj.operator}</td>
            <td class="" onclick="view('${obj.id}')" title="${obj.sendContent }">
              <c:if test="${fn:length(obj.sendContent) > 8 }">${fn:substring(obj.sendContent, 0, 8)}...</c:if>
              <c:if test="${fn:length(obj.sendContent) <= 8 }">${obj.sendContent }</c:if>
            </td>
            <td class="tc" onclick="view('${obj.id}')">${obj.recipient}</td>
            <td class="tc" onclick="view('${obj.id}')">${obj.receiveNumber}</td>
            <td class="tc" onclick="view('${obj.id}')">
              <fmt:formatDate value="${obj.sendTime }" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
            <td class="tc" onclick="view('${obj.id}')">
              <c:if test="${obj.status == 0 }">发送成功</c:if>
              <c:if test="${obj.status == 1 }">发送失败</c:if>
            </td>
            <td class="tc">${obj.failReason}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <div id="pagediv" align="right"></div>
  </div>
</body>
</html>