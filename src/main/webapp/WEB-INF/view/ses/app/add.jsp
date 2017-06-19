<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
  <title>添加App版本信息页面</title>
  <script type="text/javascript">
    $(function(){
    	$(".webuploader-pick").html("上传安装包");
    	$("#extensionId").val("apk");
    });
  </script>
</head>
<body>
<!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li>
        <li><a href="javascript:void(0)">支撑环境</a></li>
        <li><a href="javascript:void(0)">App管理</a></li>
        <li class="active"><a href="javascript:void(0)">添加App版本信息</a></li>
      </ul>
    <div class="clear"></div>
  </div>
  </div>
<!-- 新增页面开始-->
  <div class="container container_box">
    <form action="${pageContext.request.contextPath }/appInfo/add.html?businessId=${businessId}" method="post" class="mb0">
      <h2 class="list_title">添加App版本信息</h2>
      <ul class="ul_list">
        <!-- 版本号 -->
        <li class="col-md-3 col-sm-3 col-xs-3 pl15">
          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>版本号</span>
          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            <input class="" name="version" type="text" value="${appInfo.version }">
            <span class="add-on">i</span>
            <span class="input-tip">不能为空 例如1.0.0</span>
            <div class="cue">${error_version }</div>
          </div>
        </li>
        <!-- 上传附件 -->
        <li class="col-md-6 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>版本信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
            <u:upload id="post_attach_up" businessId="${businessId }" sysKey="${sysKey }" typeId="" multiple="true" auto="true" />
            <u:show showId="post_attach_show" businessId="${businessId }" sysKey="${sysKey }" typeId=""/>
            <div class="cue" id = "shangchuan">${errorShangchuan }</div>
          </div>
        </li>
        <!-- 备注 -->
        <%-- <li class="col-md-12 col-sm-6 col-xs-12 pl15">
          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>备注</span>
          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过1000个字" name="remark">${appInfo.remark }</textarea>
          <div class="star_red">${error_version }</div>
          </div>
        </li> --%>
      </ul>
      <div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
        <button type="submit" class="btn btn-windows save">添加</button>
        <button type="button" class="btn btn-windows back" onclick="window.location.href = '${pageContext.request.contextPath }/appInfo/list.html'">返回</button>
      </div>
    </form>
  </div>
</body>
</html>