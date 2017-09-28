<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet"/>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/iss/ps/cache/cachemanage.js"></script>
</head>
<body>

<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
            <li><a>信息服务</a></li>
            <li><a>门户管理</a></li>
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/cacheManage/cachemanage.html')">缓存管理</a>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<div class="container">
    <div class="headline-v2">
        <h2>缓存列表</h2>
    </div>
    <input type="hidden" id="depid" name="depid">
    <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows delete" type="button" onclick="clearSignalCache()">清空缓存</button>
        <button class="btn btn-windows delete" type="button" onclick="clearAllCache()">全部清空缓存</button>
    </div>
    <div class="content table_box">
        <table id="dataTable" class="table table-bordered table-condensed table-hover table-striped">
            <thead>
            <tr>
                <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()"/></th>
                <th class="tnone"></th>
                <th class="info w50">序号</th>
                <th class="info" width="40%">缓存名称</th>
                <th class="info" width="30%">缓存类型</th>
                <th class="info">有效时长</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div id="pagediv" align="right"></div>
</div>
</body>
</html>