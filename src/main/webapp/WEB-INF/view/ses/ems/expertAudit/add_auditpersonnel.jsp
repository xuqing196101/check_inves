<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
		  var i=0;
			function openTr() {
			i++;
				$("#list_tbody_id").after(
					"<tr>" +
					"<td class='tc'><input type='checkbox' name='expertSignatureList["+i+"].id'/></td>" +
					"<td class='tc'><input type='text' name='expertSignatureList["+i+"].name' value=''> </td>" +
					"<td class='tc'><input type='text' name='expertSignatureList["+i+"].company' value=''> </td>" +
					"<td class='tc'><input type='text' name='expertSignatureList["+i+"].job' value=''></td>"  +
					"</tr>");
			}
			function add(){
				// var index = parent.layer.getFrameIndex(window.name); 
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/saveSignature.do",
					type: "post",
					data:$("#add_form").serialize(),
					//dataType:"json",
					success: function(result) {
						layer.msg("添加成功");
						  var el = document.createElement("a");
	                      document.body.appendChild(el);
	                      el.href = "${pageContext.request.contextPath}/expertAudit/list.do?sign=2&&status=1";  
	                      el.target = '_parent'; //指定在新窗口打开
	                      el.click();
	                      document.body.removeChild(el);
	                      
	                      
						// window.location.href = "${pageContext.request.contextPath}/expertAudit/list.do";
						 // parent.layer.close(index); 
					}
				});
			}
			
			
		</script>
		
	</head>

	<body>
		<!-- 我的订单页面开始-->
		<div class="container">
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="openTr()">新增</button>
				<!-- <button class="btn btn-windows delete" type="button" onclick="deleteStockholder()">删除</button> -->
			</div>
			<form action="${pageContext.request.contextPath}/expertAudit/saveSignature.do" method="post" id="add_form"  class="registerform">
			   <ul class="demand_list">
						<li>
							<label class="fl">审核批次：</label>
							<input type="text" name="batchNo" value="">
							<input type="hidden" name="ids" value="${ids }">
						</li>
						</ul>
			
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover table-striped hand">
					
							<tr >
								<th class="info"><input type="checkbox"  />
								<th class="info">姓名 </th>
								<th class="info">单位</th>
								<th class="info">技术职称（职务）</th>
							</tr>
					
							<tr id="list_tbody_id">
								<td><input type="checkbox" name="list[0].id"/></td>
								<td class=""><input name="expertSignatureList[0].name" value=""></td>
								<td class=""><input name="expertSignatureList[0].company" value=""></td>
								<td class=""><input name="expertSignatureList[0].job" value=""></td>
							</tr>
					</table>
				</div>
				<div class="tc">
				
					<input class="btn btn-windows add" value="保存 "  type="button" onclick="add()"/>
				</div>
			</form>
		</div>
	</body>

</html>