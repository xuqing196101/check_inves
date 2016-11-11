<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">	
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/select2/css/select2-bootstrap.css"  rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
    


<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/browser.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.ba-hashchange.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masterslider.min.js"></script>
<!-- 待删除的 -->
<script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>

<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/public/select2/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js"></script>
<!-- 时间插件 -->
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>


<!-- 文本编辑器 -->  
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>

<!-- echars -->
<script src="${pageContext.request.contextPath}/public/echarts/echarts.js"></script>