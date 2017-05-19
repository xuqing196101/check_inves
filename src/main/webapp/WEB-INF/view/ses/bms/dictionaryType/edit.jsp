<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	function goback(){
		window.location.href = '${pageContext.request.contextPath}/dictionaryType/list.html'
	}
</script>
</head>
<body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="${pageContext.request.contextPath}" target="_parent"> 首页</a>
                </li>
                <li><a href="javascript:void(0)">支撑系统</a>
                </li>
                <li><a href="javascript:void(0)">数据字典</a>
                </li>
                <li class="active"><a href="javascript:void(0)">修改数据字典类型</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>

    <!-- 新增模板开始-->
    <div class="container container_box">
        <form action="${pageContext.request.contextPath}/dictionaryType/update.do"
            method="post">
            <div>
			   <h2 class="list_title">修改数据字典类型</h2>
                <ul class="ul_list">
                    <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>类型编号</span>
                        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input name="code" type="text"
                                value="${dictionaryType.code}">
                            <span class="add-on">i</span>
                            <div id="contractCodeErr" class="cue">${ERR_code}</div>
                        </div>
                    </li>

                    <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>类型名称</span>
                        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input name="name" type="text"
                                value="${dictionaryType.name}">
                            <span class="add-on">i</span>
                            <div id="contractCodeErr" class="cue">${ERR_name}</div>
                        </div>
                    </li>

                </ul>

                <div class="col-md-12 col-sm-12 col-xs-12 tc">
                    <button class="btn btn-windows save" type="submit">保存</button>
                    <button class="btn btn-windows back" onclick="goback()"
                        type="button">返回</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
