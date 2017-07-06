<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
  <title>查看App版本信息页面</title>
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
        <li class="active"><a href="javascript:void(0)">App版本信息</a></li>
      </ul>
    <div class="clear"></div>
  </div>
  </div>
<!-- 新增页面开始-->
  <div class="container container_box">
      <h2 class="list_title">App版本信息</h2>
      <ul class="ul_list">
        <!-- 版本号 -->
        <li class="col-md-3 col-sm-3 col-xs-3 pl15">
          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">版本号</span>
          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            <input class="" name="version" type="text" value="${appInfo.version }" readonly="readonly" >
            <span class="add-on">i</span>
          </div>
        </li>
        <!-- 上传附件 -->
        <li class="col-md-6 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">版本信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
            <u:upload id="post_attach_up" businessId="${businessId }" sysKey="${sysKey }" typeId="" multiple="true" auto="true" />
            <u:show showId="post_attach_show" businessId="${businessId }" sysKey="${sysKey }" typeId=""/>
          </div>
        </li>
      </ul>
      <div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
        <button type="button" class="btn btn-windows back" onclick="window.location.href = '${pageContext.request.contextPath }/appInfo/list.html'">返回</button>
      </div>
  </div>
</body>
</html>