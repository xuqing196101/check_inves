<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/oms/css/consume.css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>

<script type="text/javascript">
	function save(){
		var index = parent.layer.getFrameIndex(window.name);
		if(check()){
			$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/intelligentScore/operatorNode.do",
			    dataType:'json',
			    data : $('#formID').serialize(),
			    success: function(data) {
			    	//$("#total_score").text(data.message);
			    	//$("#remain_score").text(data.message);
			    	parent.window.location.reload();
			    	parent.layer.close(index); //执行关闭
			    }
			});
		}
	}
	function check(){
		var name = $("#name").val();
		var maxScore = $("#maxScore").val();
		var remain_score = $("#remain_score").val();
		if(name==null ||name==undefined || name==""){
			layer.msg("打分项名称不可以为空");
			return false;
		}
		if(maxScore==null ||maxScore==undefined || maxScore==""){
			layer.msg("最高分不可以为空");
			return false;
		}
		if(maxScore!=null && maxScore!=undefined && maxScore!=""){
			if(Number(remain_score)-Number(maxScore)<0){
				layer.msg("最高分不能超过"+remain_score+"分");
				return false;
			}
			
		}else{
			layer.msg("最高分不可以为空");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container">
		<form action="" method="post"  id="formID">
			<input type="hidden" name="method" value="${method }">
			<input type="hidden" name="pid" value="${pid }">
			<%-- <input type="hidden" name="bidMethodId" value="${bidMethodId }"> --%>
			<input type="hidden" name="packageId" value="${packageId }">
			<input type="hidden" name="projectId" value="${projectId }">
			<input type="hidden" id="remain_score" value="${remainScore }">
			<input type="hidden" id="id" value="${markTerm.id }">
			<div>
				<ul class="list-unstyled list-flow p10_20 mt10">
					<li class="col-md-6 p0"><span class="">打分项名称：</span>
						<div class="input-append">
							<input class="span2 w180" name="name" type="text" id="name" value="${markTerm.name }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6 p0"><span class="">分值：</span>
						<div class="input-append">
							<input class="span2 w180" name="maxScore" type="text" id="maxScore"> <span
								class="add-on">i</span>
						</div></li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="mt40 tc mb50">
					<input type="button" class="btn  padding-right-20 btn_back margin-5" value="保存" onclick="save();"></input>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
