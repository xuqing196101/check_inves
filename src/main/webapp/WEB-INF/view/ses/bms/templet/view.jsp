<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->

</head>

<body>

	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">论坛管理</a>
				</li>
				<li class="active"><a href="#">版块管理</a>
				</li>
				<li class="active"><a href="#">版块详情</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container container_box">
			<div>
				<h2 class="count_flow">
					<i>1</i>模板详情
				</h2>
				<ul class="ul_list mb20">
					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">模板名称</span>
						<div class="input-append">
							<input class="span5" type="text" value='${templet.name}'
								readonly="readonly">
							<span class="add-on">i</span>
						</div></li>

					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">模板类型</span>
						<div class="input-append">
							<input class="span5" type="text" value='${templet.temType}'
								readonly="readonly">
							<span class="add-on">i</span>
						</div></li>
					<li class="col-md-12 p0">
					<span class="col-md-12 p0">模板内容</span>
						<div class="col-md-9 mt5 p0">
							<script id="editor" name="content" type="text/plain" ></script>
							<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
						</div></li>
				</ul>
				<!-- 底部按钮 -->
				<div class="col-md-12 tc">
						<button class="btn btn-windows back" onclick="history.go(-1)"
							type="button">返回</button>
				</div>
			</div>

	</div>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor');
		var content = '${templet.content}';
		ue.ready(function() {
			ue.setContent(content);
	  		ue.setDisabled([]);
		});
	</script>
</body>
</html>
