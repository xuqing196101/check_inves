<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>专家抽取项目列表</title>
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
  
  /* 下载抽取记录表 */
  function download(){
    var id = [];
        $('input[name="chkItem"]:checked').each(function () {
            id.push($(this).val());
        });
        if (id.length != 1) {
            layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
        } else {
        	/* var flag = true;
        	$.ajax({
                url : "${pageContext.request.contextPath}/extractExpertRecord/selReviewTime.do",
                data : {
                    "id" : id
                },
                dataType : "json",
                async : false,
                type : "POST",
                success : function(data) {
                   if(data == "yes"){
                	   flag = true;
                   }else{
                	   flag = false;
                   }
                }
            }); */
        	//if(flag){
				conditionId = $("#conditionId").val();
				window.location.href = "${pageContext.request.contextPath}/extractExpertRecord/printRecord.html?id=" + id + "&&conditionId=";
        	//}else{
        	//	layer.alert("评审时间未满足半个小时", {offset: ['222px', '390px'], shade: 0.01});
        	//}
        }
  }
  
  /* 重置 */
  function form_reset(){
    $("#form1").find("input").val("");
    var SelectArr = $("#form1").find("select");
    for (var i = 0; i < SelectArr.length; i++) {
      SelectArr[i].options[0].selected = true; 
    }
    $("#page").val("1");
  }
</script>
</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
        <li><a href="javascript:void(0);">支撑环境系统</a></li>
        <li><a href="javascript:void(0);">专家管理</a></li>
        <li class="active"><a href="javascript:void(0);">专家抽取查询</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <!-- 项目列表开始-->
  <div class="container">
    <div class="headline-v2"><h2>抽取记录列表</h2></div>
    <!-- 项目戳开始 -->
    <h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/extractExpertRecord/getRecordList.html" id="form1" method="post" class="mb0">
        <input type="hidden" name="page" id="page" value="1">
        <ul class="demand_list">
          <li>
            <label>项目名称：</label>
            <input type="text" name="projectName" id="projectName" value="${project.projectName }" />
          </li>
          <li>
            <label class="fl">项目编号：</label>
            <input type="text" name="code" id="code" value="${project.code }" />
          </li>
          <li>
            <label class="fl">采购方式：</label>
            <select class="w178" name="purchaseWay">
              <option value="" <c:if test="${project.purchaseWay == '' }">selected="selected"</c:if> >全部</option>
              <c:forEach items="${purchaseWayList}" var="map">
                <option value="${map.id}" <c:if test="${project.purchaseWay == map.id }">selected="selected"</c:if> >${map.name}</option>
              </c:forEach>
            </select>
          </li>
          <li>
            <label class="fl">起始时间：</label>
            <input id="startTime" name="startTime" type="text" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
          </li>
          <li>
            <label class="fl">结束时间：</label>
            <input id="endTime" name="endTime" type="text" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
          </li>
          <button class="btn fl mt1" type="submit">查询</button>
          <button type="button" class="btn fl mt1" onclick="form_reset()">重置</button>
        </ul>
        <div class="clear"></div>
      </form>
    </h2>
    <div class="col-md-12 pl20 mt10">
      <button class="btn" onclick="download();">下载抽取记录表</button>
    </div>
    <div class="container table_box">
      <table class="table table-striped table-bordered table-hover">
        <thead>
          <tr>
            <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
            <th class="info w50">序号</th>
            <th class="info">项目名称</th>
            <th class="info">项目编号</th>
            <th class="info">包名（标段）</th>
            <th class="info">项目类型</th>
            <th class="info">采购方式</th>
            <th class="info">抽取方式</th>
            <th class="info">评审地点</th>
            <th class="info">抽取人</th>
            <th class="info">抽取时间</th>
            <th class="info">抽取状态</th>
          </tr>
        </thead>
        <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <tr>
            <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obj.id }" /></td>
            <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
            <td class="">${obj.projectName }</td>
            <td class="w90">${obj.code }</td>
            <td class="w100">${obj.packageName }</td>
            <td class="tc w90">${obj.projectType }</td>
            <td class="tc w100">${obj.purchaseWay }</td>
            <td class="tc w90">
              <c:if test="${obj.isAuto == 1 }">自动抽取</c:if>
              <c:if test="${obj.isAuto == 0 }">人工抽取</c:if>
            </td>
            <td class="tc w120" title="${obj.reviewAddress }">
              <c:if test="${fn:length(obj.reviewAddress) > 7 }">${fn:substring(obj.reviewAddress, 0, 7)}...</c:if>
              <c:if test="${fn:length(obj.reviewAddress) <= 7 }">${obj.reviewAddress }</c:if>
            </td>
            <td class="tc w90" title="${obj.extractPerson }">
              <c:if test="${fn:length(obj.extractPerson) > 4 }">${fn:substring(obj.extractPerson, 0, 4)}...</c:if>
              <c:if test="${fn:length(obj.extractPerson) <= 4 }">${obj.extractPerson }</c:if>
            </td>
            <td class="tc w150"><!-- reviewTime -->
              <fmt:formatDate value="${obj.createdAt }" pattern="yyyy/MM/dd HH:mm:ss" />
            </td>
            <td class="tc w90">
              <c:if test="${obj.status == '0' }">未开始</c:if>
              <c:if test="${obj.status == '1' }">抽取中</c:if>
              <c:if test="${obj.status == '2' }">抽取结束</c:if>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <div id="pagediv" align="right"></div>
  </div>
</body>
</html>