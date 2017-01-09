<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>添加物资生产品目信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	
	function saveOrBack(sign) {
		var action = "${pageContext.request.contextPath}/supplier_items_pro/";
		if (sign) {
			action += "save_or_update_items_pro.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#items_pro_form_id").attr("action", action);
		$("#items_pro_form_id").submit();
	}
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="items_pro_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matProId" value="${matProId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-200" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 品目类别：</span>
												<div class="input-append">
													<input class="span3" type="text" name="itemsId" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 大类名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bigKindName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 中类名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="normalKindName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 小类名称 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="smallKingName"/>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 品种名称 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="kindName"/>
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(1)">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(0)">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>