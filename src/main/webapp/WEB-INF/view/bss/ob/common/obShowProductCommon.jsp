<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function showPrompt(id, selectID) {
		if (id) {
			$.ajax({
				async : false,
				url : "${pageContext.request.contextPath }/product/productType.do",
				type : "POST",
				data : {
					productId : id
				},
				success : function(data) {
					if (data) {
						var quality = data.qualityTechnicalStandard;
						if (quality == null) {
							quality = "无";
						}
						layer.tips("产品规格型号：" + data.standardModel + "<br/>"
								+ "质量技术标准：" + quality, '#' + selectID, {
							tips : [ 3, '#78BA32' ],
							time : -1,
							area : [ '500px', 'auto' ],
						});
					} else {
						inder = layer.tips("", '#' + id, {
							tips : [ 3, '#78BA32' ]
						});
					}
				},
				error : function() {
					layer.tips("错误！", '#' + selectID, {
						tips : [ 3, '#78BA32' ]
					});
				}
			});
		}
	}
	
	// 关闭
	function closePrompt() {
		layer.closeAll('tips');
	}
</script>