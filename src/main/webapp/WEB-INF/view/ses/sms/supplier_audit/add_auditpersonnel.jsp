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
					/* "<td class='tc'><input type='checkbox' name='supplierSignatureList["+i+"].id'/></td>" + */
					"<td class='tc'><input type='text' name='supplierSignatureList["+i+"].name' value=''> </td>" +
					"<td class='tc'><input type='text' name='supplierSignatureList["+i+"].company' value=''> </td>" +
					"<td class='tc'><input type='text' name='supplierSignatureList["+i+"].job' value=''></td>"  +
					"<td onClick='del(this)'><button>删除</button></td>" +
					"</tr>");
			}
			function add(){
				var batchNo = $("input[name='batchNo']").val();
				if(batchNo == ""){
					layer.msg("审核批次不能为空！", {
						offset: '300px'
					});
				}else{
					var msg = "";
					var flag = true;
					$("#info").find("input[type='text']").each(function(index, element) {
						if(element.value == "") {
							msg = "信息不能为空!";
							flag = false;
						}
					});
	
					if(!flag){
						layer.msg(msg, {
							offset: '300px'
						});
					}
				}
			
			
				// var index = parent.layer.getFrameIndex(window.name);
				if(flag){
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/saveSignature.do",
						type: "post",
						data:$("#add_form").serialize(),
						//dataType:"json",
						success: function(result) {
							  var el = document.createElement("a");
	              document.body.appendChild(el);
	              el.href = "${pageContext.request.contextPath}/supplierAudit/supplierAll.do?sign=3&status=5";  
	              el.target = '_parent'; //指定在新窗口打开
	              el.click();
	              document.body.removeChild(el);   
							// window.location.href = "${pageContext.request.contextPath}/expertAudit/list.do";
							 // parent.layer.close(index); 
							}
						});
					}
			}
			
			//删除
			function del(tr){
			  $(tr).parent().remove();     
			}
		</script>
		
	</head>

	<body>
		<!-- 我的订单页面开始-->
		<div class="container">
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				
				<!-- <button class="btn btn-windows delete" type="button" onclick="deleteStockholder()">删除</button> -->
			</div>
			<form action="${pageContext.request.contextPath}/supplierAudit/saveSignature.do" method="post" id="add_form"  class="registerform">
			   <button class="btn btn-windows add" type="button" onclick="openTr()">新增</button>
			   <ul class="demand_list">
						<li>
							<label class="fl">批次：</label>
							<input type="text" name="batchNo" value="">
							<input type="hidden" name="ids" value="${ids }">
						</li>
					</ul>
			
					<div class="content table_box" id="info">
						<table class="table table-bordered table-condensed table-hover hand left_table table_input">
							<tr >
								<!-- <th class="info"><input type="checkbox"  /> -->
								<th class="info" width="30%"><div class="star_red">*</div>姓名 </th>
								<th class="info" width="35%"><div class="star_red">*</div>单位</th>
								<th class="info" width="35%"><div class="star_red">*</div>技术职称（职务）</th>
							</tr>
							<tr id="list_tbody_id">
								<!-- <td><input type="checkbox" name="list[0].id"/></td> -->
								<td width="30%" class=""><input name="supplierSignatureList[0].name" value="" type="text"></td>
								<td width="30%" class=""><input name="supplierSignatureList[0].company" value="" type="text"></td>
								<td width="30%" class=""><input name="supplierSignatureList[0].job" value="" type="text"></td>
							</tr>
						</table>
					</div>
					<div class="tc">
					<input class="btn btn-windows save" value="保存 "  type="button" onclick="add()"/>
				</div>
			</form>
		</div>
	</body>

</html>