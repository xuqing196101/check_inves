<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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

		/* var name = $("#name").val();
		if (!name) {
			layer.tips("请填写名称", "#name");
			return;
		} */
		/* var id = [];
		$('input[name="kind"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 0) {
			layer.tips("请选择类型", "#kind");
			return;
		} */

		/* var creater = $("#creater").val();
		if (!creater) {
			layer.tips("请填写名称", "#creater");
			return;
		} */
		/* var id2 = [];
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
		} */
		$("#form1").submit();
	}
	
	function goBack(){
		window.location.href = '${pageContext.request.contextPath}/auditTemplat/list.html?page=1';
	}
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">支撑系统</a>
				</li>
				<li><a href="javascript:void(0)">后台管理</a>
				</li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/auditTemplat/toAdd.html')">评审模板管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box">
		<!-- 新增窗口 -->
		<sf:form action="${pageContext.request.contextPath}/auditTemplat/add.html" method="post" id="form1" modelAttribute="firstAuditTemplat">
			<h2 class="list_title">编辑模板</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板名称</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="text" id="name" maxlength="30" name="name" value="${templat.name}">
						<span class="add-on">i</span>
						<div class="cue"><sf:errors path="name"/></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
					<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><div class="star_red">*</div>模板类型</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
                    <select  name="kind">
                        <option value="">请选择</option>
                        <c:forEach items="${kinds}" var="k" varStatus="vs">
                            <option value="${k.id }" <c:if test="${k.id eq templat.kind}">selected</c:if>>
                                ${k.name}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="cue"><sf:errors path="kind"/></div>
                    </div>
				</li>
				<%-- <li class="col-md-3 margin-0 padding-0 "><span class="">是否公开</span>
                    <div class="select_check">
                        <input name="isOpen" maxlength="10" type="radio" checked value="0">公开
                        <input name="isOpen" id="isOpen" maxlength="10" type="radio" value="1">私有
                    </div>
                </li>
                <li class="col-md-3 margin-0 padding-0 "><span class="">是否可用</span>
                    <div class="select_check">
                        <input name="isUse" maxlength="10" type="radio" checked value="0">可用
                        <input name="isUse" id="isUse" maxlength="10" type="radio" value="1">不可用
                        <input type="hidden" name="userId" value="${sessionScope.loginUser.id }">
                    </div>
                </li> --%>
			</ul>
			<div class="col-md-12 tc">
			    <input type="button" onclick="submit1();" value="保存" class="btn btn-windows save" />
                <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
            </div>
		</sf:form>
	</div>
</body>
</html>