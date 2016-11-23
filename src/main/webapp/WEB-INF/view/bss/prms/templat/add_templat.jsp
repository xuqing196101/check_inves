<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	var index;
	function cancel() {
		layer.close(index);
	}
	function openWindow() {
		index = layer.open({
			type : 1, //page层
			area : [ '700px', '300px' ],
			title : '新增模板',
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			offset : [ '220px', '250px' ],
			shadeClose : true,
			content : $('#package')
		//数组第二项即吸附元素选择器或者DOM $('#openWindow')
		});
	}
	function submit1() {

		var name = $("#name").val();
		if (!name) {
			layer.tips("请填写名称", "#name");
			return;
		}
		var id = [];
		$('input[name="kind"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 0) {
			layer.tips("请选择类型", "#kind");
			return;
		}

		/* var creater = $("#creater").val();
		if (!creater) {
			layer.tips("请填写名称", "#creater");
			return;
		} */
		var id2 = [];
		$('input[name="isOpen"]:checked').each(function() {
			id2.push($(this).val());
		});
		if (id2.length == 0) {
			layer.tips("请选择一个", "#isOpen");
			return;
		}
		var id3 = [];
		$('input[name="isUse"]:checked').each(function() {
			id3.push($(this).val());
		});
		if (id3.length == 0) {
			layer.tips("请选择一个", "#isUse");
			return;
		}
		$("#form1").submit();
	}
</script>
</head>
<body>
	<div class="container container_box">
		<!-- 新增窗口 -->
		<form
			action="${pageContext.request.contextPath}/auditTemplat/add.html"
			method="post" id="form1">
			<div class="headline-v2">
                 <h2>新增模板</h2>
               </div>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">初审项模板名称</span>
					<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="text" id="name" maxlength="30" name="name">
						<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">初审项模板类型</span>
					<div class="select_check">
						<input type="radio" name="kind" value="商务">商务 
						<input type="radio" name="kind" id="kind" value="技术">技术 
						<input name="creater" readonly="readonly" id="creater" maxlength="10" type="hidden" value="${sessionScope.loginUser.relName}">
					</div>
				</li>
				<li class="col-md-3 margin-0 padding-0 "><span class="">是否公开</span>
                    <div class="select_check">
                        <input name="isOpen" maxlength="10" type="radio" value="0">公开
                        <input name="isOpen" id="isOpen" maxlength="10" type="radio" value="1">私有
                    </div>
                </li>
                <li class="col-md-3 margin-0 padding-0 "><span class="">是否可用</span>
                    <div class="select_check">
                        <input name="isUse" maxlength="10" type="radio" value="0">可用
                        <input name="isUse" id="isUse" maxlength="10" type="radio" value="1">不可用
                        <input type="hidden" name="userId" value="${sessionScope.loginUser.id }">
                    </div>
                </li>
			</ul>
			<div class="col-md-12 tc">
			    <input type="button" onclick="submit1();" value="保存" class="btn btn-windows save" />
                <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
            </div>
		</form>
	</div>
</body>
</html>