<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>采购档案审核</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			//通过
			function pass() {
				var id = $("#archiveId").val();
				window.location.href = "${pageContext.request.contextPath }/purchaseArchive/passArchive.html?id=" + id;
			}

			//退回
			function retreat() {
				layer.open({
					type: 1,
					title: '信息',
					skin: 'layui-layer-rim',
					offset: ['30%', '40%'],
					shadeClose: true,
					area: ['580px', '310px'],
					content: $("#backReason")
				});
				$(".layui-layer-shade").remove();
			}

			//确定
			function sure() {
				var id = $("#archiveId").val();
				var reason = $("#reason").val();
				window.location.href = "${pageContext.request.contextPath }/purchaseArchive/backReason.html?id=" + id + "&reason=" + reason;
			}

			//取消
			function cancel() {
				layer.closeAll();
			}
		</script>

	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购档案管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container container_box">
			<h2 class="list_title">采购档案审核</h2>
	
			<input type="hidden" value="${archive.id }" id="archiveId" />

			<ul class="list-unstyled ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">档案名称：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.name }" readonly="readonly" />
					</div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">档案编号：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.code }" readonly="readonly" />
				    </div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合同编号：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.contractCode }" readonly="readonly" />
					</div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">项目编号：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.projectCode }" readonly="readonly" />
					</div>
				</li>

				<%--<li class="col-md-12 p0">
	  			<span class="fl">计划文号：</span>
	  			<input type="text" value="${archive.planCode }" readonly="readonly"/>
	    	</li>
	    	
	    	<li class="col-md-12 p0">
	  			<span class="fl">计划下达时间：</span>
	  			<input type="text" value="${archive.planCode }" readonly="readonly"/>
	    	</li>
	    	
	    	--%>
				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">预算年度：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.year }" readonly="readonly" />
				    </div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购机构：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
				    	<input type="text" value="${archive.purchaseDep }" readonly="readonly" />
				    </div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购方式：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
				    	<input type="text" value="${archive.purchaseType }" readonly="readonly" />
				    </div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">产品名称：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					    <input type="text" value="${archive.productName }" readonly="readonly" />
					</div>
				</li>

				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
					   <input type="text" value="${archive.supplierName }" readonly="readonly" />
					</div>
				</li>
			</ul>

			<!-- 按钮 -->
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
				<button class="btn" type="button" onclick="pass()">通过</button>
				<button class="btn" type="button" onclick="retreat()">退回</button>
				<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</div>
		</div>

		<div class="layui-layer-wrap col-md-12 col-xs-12 col-sm-12 dnone mt20" id="backReason">
			<span class="col-md-12 col-xs-12 col-sm-12">退回理由：</span>
			<div class="col-md-12 col-xs-12 col-sm-12">
				<textarea class="col-md-12 col-xs-12 col-sm-12 h80 p0" name="reason" id="reason"></textarea>
			</div>
			<div class="col-md-12 col-xs-12 col-sm-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="sure()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div>
		</div>
	</body>

</html>