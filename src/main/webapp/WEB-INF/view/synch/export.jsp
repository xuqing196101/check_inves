<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
  <style>
    .dataType {
      width: 200px;
      float: left;
      padding-right: 10px;
      min-width: 50px;
    }
  </style>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <script type="text/javascript">
  	function yzExport(){
  		var authType = "${authType}";
  		if(authType != '4'){
  			layer.msg("只有资源服务中心才能操作");
  			return;
  		} 
  		synchExport()
  	}
  </script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/synch/export.js"></script>
</head>
<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
  <div class="container">
    <ul class="breadcrumb margin-left-0">
      <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
      <li><a>支撑环境</a></li>
      <li><a>数据同步</a></li>
      <li class="active"><a onclick="jumppage('${pageContext.request.contextPath}/synchExport/initExport.html')">数据导出</a></li>
    </ul>
    <div class="clear"></div>
  </div>
</div>

<div class="container container_box">
  <input type="hidden" id="operType" name="operType" value="${operType}"/>
  <div>
    <h2 class="count_flow"><i>1</i>导出设置</h2>
    <ul class="ul_list">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>开始时间</span>
        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
          <input class="input_group Wdate mb0 w220" id="startTime" name="startTime" type="text" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        </div>
      </li>

      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>结束时间</span>
        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
          <input class="input_group Wdate mb0 w220" id="endTime" name="endTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        </div>
      </li>

      <li class="col-md-12 col-sm-12 col-xs-12 pl15">
        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>同步类型</span>
        <div>
          <c:forEach items="${dataTypeList}" var="type">
            <div class="dataType">
              <input type="checkbox" name="dataType" value="${type.code}"/> ${type.name}
            </div>
          </c:forEach>
          <c:if test="${authType ==  '4'}">
	          <div class="dataType">
	            <input type="checkbox" name="dataType" value="inner_out"/> 供应商退回修改导出外网
	          </div>
	          <div class="dataType">
	            <input type="checkbox" name="dataType" value="temp_out"/> 临时供应商导出外网
	          </div>
	          <div class="dataType">
	            <input type="checkbox" name="dataType" value="expert_out"/> 专家退回修改导出外网
	          </div>
	          <div class="dataType">
	            <input type="checkbox" name="dataType" value="img_out"/> 供应商，专家图片导出
	          </div>
          </c:if>
        </div>
      </li>

      <div class="clear mt10 tc">
        <button class="btn" onclick="yzExport();">导出</button>
      </div>
    </ul>
  </div>

  <div class="padding-top-10 clear" id="relaDeptId">
    <h2 class="count_flow"><i>2</i>导出日志</h2>
    <ul class="ul_list">
      <div class="search_detail ml0">
        <ul class="demand_list">
          <li>
            <label class="fl">类型：</label>
            <select name="searchType" id="searchType">
              <option value="">请选择</option>
              <c:forEach items="${dataTypeList}" var="dataType">
                <option value="${dataType.id}">${dataType.name}</option>
              </c:forEach>
            </select>
          </li>

          <li>
            <label>导出时间：</label>
            <input type="text" class="Wdate" id="searchStartTime" name="searchStartTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            <span>至</span>
            <input type="text" class="Wdate" id="searchEndTime" name="searchEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
          </li>

          <button type="button" onclick="query();" class="btn fl mt1">查询</button>
          <button type="reset" class="btn fl mt1" onclick="reset();">重置</button>
        </ul>
        <div class="clear"></div>
      </div>

      <div class="content pt10">
        <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable">
          <thead>
          <tr>
            <th class="info w50">序号</th>
            <th class="info w150">类型</th>
            <th class="info w200">导出时间</th>
            <th class="info">描述</th>
          </tr>
          </thead>
          <tbody></tbody>
        </table>
      </div>
    </ul>
    <div id="pagediv" align="right"></div>
  </div>
</div>
</body>

</html>