<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		 <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
		
		<script type="text/javascript">
		
		
		  $(function(){
				$("td[name='userNone']").attr("style","display:none");
				$("th[name='userNone']").attr("style","display:none");
			  
		  });
		  
		  
		  
			//跳转到增加页面
			function add() {
				window.location.href = "${pageContext.request.contextPath}/purchaser/add.html";
			}

			//鼠标移动显示全部内容
			var index;

			function chakan() {
				index = layer.open({
					type: 1, //page层
					area: ['600px', '500px'],
					title: '编制说明',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: $('#content'),
				});
			}

			function closeLayer() {
				layer.close(index);
			}

			/**function uploadExcel() {
				layer.open({
					type: 2, //page层
					area: ['600px', '500px'],
					title: '文件上传',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: '${pageContext.request.contextPath}/purchaser/fileUpload.html',
				});
			}*/

			//上传excel文件
	/* 		function upload() {
				$.ajaxFileUpload({
					url: '${pageContext.request.contextPath}/purchaser/upload.do',
					secureuri: false,
					fileElementId: 'fileName',
					dataType: "text",
					success: function(data) {
						if(data == "ERROR") {
							layer.msg("文件名错误");
						} else if(data == "exception") {
							layer.msg("格式错误");
						} else {
							layer.msg("上传成功");
							window.location.href = "${pageContext.request.contextPath}/purchaser/list.html";
						}
					},
					error: function(data, status, e) {
						layer.msg("上传失败");
					}
				});
			} */

			function loadTrInfo(obj) {

			}

			function adds() {
				var name = $("#jhmc").val();
				var no = $("#jhbh").val();
				var type = $("#wtype").val();
				if(name == "") {
					layer.tips("计划名称不允许为空", "#jhmc");
				} else if(no == "") {
					layer.tips("计划编号不允许为空", "#jhbh");
				} else if(type == "") {
					layer.tips("物资类别不允许为空", "#wtype");
				} else {
					$("#fjhmc").val(name);
					$("#fjhbh").val(no);
					$("#ptype").val(type);

					$("#add_form").submit();
				}
			}

			function hide() {
				$("#add_div").hide();
			}

			//动态添加
			function aadd() {
				var value = $("#xqbm").val();
				var detailRow = document.getElementsByName("detailRow");
				var id = null;
				$.ajax({
					url: "${pageContext.request.contextPath}/purchaser/getId.html",
					type: "post",
					success: function(data) {
						id = data;
						var tr = $("input[name=dyadds]").parent().parent().prev();
						// var tr=$(obj).parent().parent();
						$(tr).children(":first").children(":first").val(data);
						var s = detailRow.length;
						var count=1;
						// var trs = $(obj).parent().parent();
						if(detailRow.length==0){
							count=count+s;
							$("#detailZeroRow").html("<tr name='detailRow' class='tc p0'>   <td> " +
									"<input class='m0 ' required='required' type='text' name='list[" + 0 + "].seq' /></td>" +
									"<td class=''  name='department'><input class='m0'  type='text' name='list[" + 0 + "].department'  value=''/></td>" +
									"<td class='' ><input class='m0 ' type='text' name='list[" + 0 + "].goodsName' onkeyup='listName(this)'/></td>" +
									"<td class=''  ><input class='m0' type='text' name='list[" + 0 + "].stand' /></td>" +
									"<td class='' ><input class='m0 ' type='text' name='list[" + 0 + "].qualitStand' /></td>" +
									"<td class='' ><input class='m0 ' type='text' name='list[" + 0 + "].item' /> </td>" +
									"<td class=''  name='purchaseQuantity'><input class='m0' type='text' name='list[" + 0 + "].purchaseCount' onkeyup='checkNum(this,1)'/></td>" +
									"<td class='' name='unitPrice'><input class='m0' type='text' name='list[" + 0 + "].price' onkeyup='checkNum(this,2)'/></td>" +
									"<td class='' ><input type='text' class='m0'  name='list[" + 0 + "].budget' readonly='readonly' /></td>" +
									"<td class='' ><input type='text' class='m0' name='list[" + 0 + "].deliverDate' /></td>" +
									"<td class=''><select name='list[" + 0 + "].purchaseType' class='pt' id='pType["+0+"]'> <option value='' >请选择</option>" +


									" <c:forEach items='${list2 }' var='obj'> <option value='${obj.id }'>${obj.name }</option></c:forEach>  </select></td>" +
									"<td class='' ><input type='text' name='list[" + 0 + "].supplier' /></td>" +
									"<td class='' ><input type='text' name='list[" + 0 + "].isFreeTax' /></td>" +
									"<td class='' ><input type='text' name='list[" + 0 + "].goodsUse' /></td>" +
									"<td class=''  ><input type='text' name='list[" + 0 + "].useUnit' /></td>" +
									"<td class=''  ><input type='text' name='list[" + 0 + "].memo' /></td>" +
								/* 	"<td><input type='text' name='list[" + 0 + "].status' value='暂存' readonly='readonly' /></td>" + */
									"<td class=''  ><button type='button' class='btn' onclick='delRowIndex(this)'>删除</button></td>" +
									"<tr/>");
						}else{
						$(detailRow[detailRow.length-1]).after("<tr name='detailRow' class='tc'>  <td>"+count+"</td>  <td>  " +
							"<input  class='m0'  required='required' type='text' name='list[" + s + "].seq' /></td>" +
							"<td class=''  name='department'><input   class='m0'  type='text' name='list[" + s + "].department' readonly='readonly' value=''/></td>" +
							"<td  class='' ><input class='m0 ' type='text' name='list[" + s + "].goodsName' onkeyup='listName(this)'/></td>" +
							"<td  class='' ><input class='m0 ' type='text' name='list[" + s + "].stand' /></td>" +
							"<td  class='' ><input class='m0 w100p' type='text' name='list[" + s + "].qualitStand' /></td>" +
							"<td class='' ><input  class='m0'  type='text' name='list[" + s + "].item' /> </td>" +
							"<td class='' name='purchaseQuantity'><input class='m0'   type='text' name='list[" + s + "].purchaseCount' onkeyup='checkNum(this,1)'/></td>" +
							"<td class='' name='unitPrice'><input class='m0'   type='text' name='list[" + s + "].price' onkeyup='checkNum(this,2)'/></td>" +
							"<td class='' ><input type='text' class='m0'  name='list[" + s + "].budget' readonly='readonly' /></td>" +
							"<td class='' ><input lass='m0' type='text' name='list[" + s + "].deliverDate' /></td>" +
							"<td  class='' ><select class='pt m0' name='list[" + s + "].purchaseType' class='pt' id='pType["+s+"]'> <option value='' >请选择</option>" +
							" <c:forEach items='${list2 }' var='obj'> <option value='${obj.id }'>${obj.name }</option></c:forEach>  </select></td>" +
							"<td class='' ><input class='pt ' type='text' name='list[" + s + "].supplier' /></td>" +
							"<td  class=''><input class='pt' type='text' name='list[" + s + "].isFreeTax' /></td>" +
							"<td name='userNone' class='' ><input class='pt'  type='text' name='list[" + s + "].goodsUse' /></td>" +
							"<td name='userNone' class='' ><input class='pt'   type='text' name='list[" + s + "].useUnit' /></td>" +
							"<td class='' ><input class='pt '  type='text' name='list[" + s + "].memo' /></td>" +
							/* "<td><input type='text' name='list[" + s + "].status' value='暂存' readonly='readonly' /></td>" + */
							"<td class='' ><button  type='button' class='btn' onclick='delRowIndex(this)'>删除</button></td>" +
							"<tr/>");
						}
					}
				});
			}

			
			//保存
			function incr() {
			
				var orgType="${orgType}";
				var name = $("#jhmc").val();
				var no = $("#jhbh").val();
				var mobile = $("#mobile").val();
				var type = $("#wtype").val();
			 	var refNo = $("#referenceNo").val();
				var bool= details();
				/* var seq=seqs(); */
			 if(orgType!='0'){
				 layer.msg("请用需求部门编制采购计划！"); 
			 }else if($.trim(name) == "") {
					 layer.msg("计划名称不允许为空"); 
				} else if($.trim(mobile) == "") {
					 layer.msg("录入人手机号不允许为空"); 
					//layer.tips("录入人手机号不允许为空", "#mobile");
				} else if($.trim(type) == ""){
					 layer.msg("请选择物资类别"); 
				} 
				/* else if($.trim(refNo) == ""){
					 layer.msg("请 填写计划文号"); 
				} */
				
				else if(bool==true){
					$("#detailJhmc").val(name);
					$("#detailJhbh").val(no);
					$("#detailType").val(type);
					$("#detailMobile").val(mobile);
					$("#detailRefNo").val(refNo);
			  	$.ajax({
						url: "${pageContext.request.contextPath}/purchaser/queryNo.html",
						data:{no:no},
						type: "post",
						success: function(data) {
							if(data!='1'){  
								$("#add_form").submit();
		 			}else{
								layer.tips("计划编号已存在", "#jhbh");
							}
						}
					});  
					
				}
				
				
			}

			function down() {
				window.location.href = "${pageContext.request.contextPath}/purchaser/download.html?filename=模板.xlsx";
			}

			function delets() {
				var tr = $("input[name=delt]").parent().parent();
				$(tr).prev().remove();
			}
			var datas;
			var treeObj;
			
			$(function() {
				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/category/createtree.do",
						dataType: "json",
						type: "post",
					},
					callback: {
						onClick: zTreeOnClick, //点击节点触发的事件
						//beforeRemove: zTreeBeforeRemove,
						//beforeRename: zTreeBeforeRename, 
						//onRemove: zTreeOnRemove,
						//onRename: zTreeOnRename,
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0,
						}
					},
				};
				//控制树的显示和隐藏
				var expertsTypeId = $("#expertsTypeId").val();
				if(expertsTypeId == 1 || expertsTypeId == "1") {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").show();
				} else {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").hide();
				}
				
			});
			
			function typeShow() {
				/* 	 var expertsTypeId = $("#expertsTypeId").val();
					 if(expertsTypeId==1 || expertsTypeId=="1"){ */
				$("#ztree").show();
				layer.open({
					type: 1,
					title: '信息',
					skin: 'layui-layer-rim',
					shadeClose: true,
					offset: ['20%', '20%'],
					area: ['45%', '70%'],
					content: $("#catalogue")
				});
				$(".layui-layer-shade").remove();
				/*  }else{
					 $("#ztree").hide();
				 } */

			}
			var treeid = null;
			/*树点击事件*/
			function zTreeOnClick(event, treeId, treeNode) {
				treeid = treeNode.id;

			}

			function typehide() {
				layer.closeAll();
			}

			function same() {
				$.ajax({
					url: "${pageContext.request.contextPath}/purchaser/getId.html",
					type: "post",

					success: function(data) {

						var tr = $("input[name=dyadds]").parent().parent().prev();;
						var id = $(tr).children(":first").children(":last").val();

						var s = $("#count").val();
						s++;
						$("#count").val(s);
						//  var trs = $(obj).parent().parent();
						$(tr).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list[" + s + "].id' value='" + data + "' />" +
							"<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
							"<td class='tc'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
							"<td class='tc'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
							"<td class='tc'>  <select name='list[" + s + "].purchaseType'> <option value='' >请选择</option>" +
							" <c:forEach items='${list2 }' var='obj'> <option value='${obj.id }'>${obj.name }</option></c:forEach>  </select></td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
							"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +

							+"<tr/>");
					},
					error: function() {

					}
				});
			}

			function news(obj) {
				var s = $("#count").val();
				s++;
				$("#count").val(s);
				var trs = $(obj).parent().parent();
				$(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' name='list[" + s + "].id' />" +
					"<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
					"<td class='tc p0'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
					"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
					"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
					"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
					"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
					"<td class='tc p0'><input class='add' name='dyadds' type='button' onclick='aadd(this)' value='添加子节点'>" +
					"<input class='btn btn-windows add' name='delt' type='button' onclick='same(this)' value='添加同级节点'>" +
					" <input class='btn btn-windows add' name='delt' type='button' onclick='news(this)' value='新加任务'></td>" +
					+"<tr/>");

			}

			//选择采购方式
			function changeType(obj) {
				$(obj).parent().next().find("input").val("");
				var purchaseType = $("#select option:selected").text(); //选中的文本
				if(purchaseType == "单一来源") {
					$(obj).parent().next().find("input").removeAttr("disabled");
				} else {
					$(obj).parent().next().find("input").attr("disabled", "disabled");
				}
			}
			
			//只能输入数字
			function checkNum(obj,num){
				var vals = $(obj).val();
				var reg = /^\d+\.?\d*$/;  
				if(!reg.exec(vals)){
					$(obj).val("");
				}else{
					if(num==1){
						var count = $(obj).val();
						var price = $(obj).parent().next().find("input").val();
						$(obj).parent().next().next().find("input").val(count*price);
					}else if(num==2){
						var count = $(obj).parent().prev().find("input").val();
						var price = $(obj).val();
						$(obj).parent().next().find("input").val(count*price);
					}
				}
				var totalPrice = 0;
				var quantity = document.getElementsByName("purchaseQuantity");
				var unitPrice = document.getElementsByName("unitPrice");
				for(var i=0;i<quantity.length;i++){
					if($(quantity[i]).find("input").val()!=""){
						totalPrice = totalPrice + $(quantity[i]).find("input").val()*($(quantity[i]).next().find("input").val());
					}
				}
				for(var i=0;i<quantity.length;i++){
					if($(quantity[i]).find("input").val()==""){
						$(quantity[i]).next().next().find("input").val(totalPrice);
					}
				}
			}
			
			//需求部门赋值
			function assignDepartment(obj){
				var value = $(obj).val();
				var department = document.getElementsByName("department");
				for(var i=0;i<department.length;i++){
					$(department[i]).find("input").val(value);
				}
			}
			
			//检索名字
			function listName(obj) {
				var name = $(obj).val();
				if(name == "" || name == null) {
					$("#materialName").html("");
					$("#materialName").addClass("dnone");
					return;
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaser/listName.do?name=" + name,
					success: function(data) {
							if(data.length>0){
								var html = "";
								for(var i = 0; i < data.length; i++) {
									html += "<div style='width:178px;height:20px;' class='pointer' onmouseover='changeColor(this)' onclick='getValue(this)'>"+data[i].name+"</div>";
								}
								$("#materialName").html(html);
								$("#materialName").removeClass("dnone");
								$(obj).after($("#materialName"));
							}else{
								$("#materialName").html("");
								$("#materialName").addClass("dnone");
							}
					}
				});
			}
			
			//改变颜色
			function changeColor(obj){
				$(obj).css("background-color","#eee");
			}
			
			//获取值
			function getValue(obj){
				$(obj).parent().parent().find("input").val($(obj).html());
				$(obj).parent().addClass("dnone");
			}
			
			//删除一行
			function delRowIndex(obj){
				$(obj).parent().parent().remove();
				var detailRow = document.getElementsByName("detailRow");
				if(detailRow.length!=0){
					for(var i=0;i<detailRow.length;i++){
						$(detailRow[i]).find("td:eq(0)").find("input:eq(0)").attr("name","list["+i+"].id");
						$(detailRow[i]).find("td:eq(0)").find("input:eq(1)").attr("name","list["+i+"].seq");
						$(detailRow[i]).find("td:eq(1)").find("input").attr("name","list["+i+"].department");
						$(detailRow[i]).find("td:eq(2)").find("input").attr("name","list["+i+"].goodsName");
						$(detailRow[i]).find("td:eq(3)").find("input").attr("name","list["+i+"].stand");
						$(detailRow[i]).find("td:eq(4)").find("input").attr("name","list["+i+"].qualitStand");
						$(detailRow[i]).find("td:eq(5)").find("input").attr("name","list["+i+"].item");
						$(detailRow[i]).find("td:eq(6)").find("input").attr("name","list["+i+"].purchaseCount");
						$(detailRow[i]).find("td:eq(7)").find("input").attr("name","list["+i+"].price");
						$(detailRow[i]).find("td:eq(8)").find("input").attr("name","list["+i+"].budget");
						$(detailRow[i]).find("td:eq(9)").find("input").attr("name","list["+i+"].deliverDate");
						$(detailRow[i]).find("td:eq(10)").find("select").attr("name","list["+i+"].purchaseType");
						$(detailRow[i]).find("td:eq(10)").find("select").attr("id","pType["+i+"]");
						$(detailRow[i]).find("td:eq(11)").find("input").attr("name","list["+i+"].supplier");
						$(detailRow[i]).find("td:eq(12)").find("input").attr("name","list["+i+"].isFreeTax");
						$(detailRow[i]).find("td:eq(13)").find("input").attr("name","list["+i+"].goodsUse");
						$(detailRow[i]).find("td:eq(14)").find("input").attr("name","list["+i+"].useUnit");
						$(detailRow[i]).find("td:eq(15)").find("input").attr("name","list["+i+"].memo");
						$(detailRow[i]).find("td:eq(16)").find("input").attr("name","list["+i+"].status");
					}
					var totalPrice = 0;
					var quantity = document.getElementsByName("purchaseQuantity");
					var unitPrice = document.getElementsByName("unitPrice");
					for(var i=0;i<quantity.length;i++){
						if($(quantity[i]).find("input").val()!=""){
							totalPrice = totalPrice + $(quantity[i]).find("input").val()*($(quantity[i]).next().find("input").val());
						}
					}
					for(var i=0;i<quantity.length;i++){
						if($(quantity[i]).find("input").val()==""){
							$(quantity[i]).next().next().find("input").val(totalPrice);
						}
					}
				}
			}
			var index;
			function uploadExcel() {
				index = layer.open({
					type: 1, //page层
					area: ['400px', '300px'],
					title: '导入需求计划',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: $('#file_div'),
				});
			}
			
		/* 	function gtype(obj){
				var vals=$(obj).val();
				if(vals == 'FC9528B2E74F4CB2A9E74735A8D6E90A'){
					  $("#dnone").show();  
				}else{
				
					 $("#dnone").hide();
				}
				
				$("#detailType").val(vals);
			} */
			
		 function fileup(){
			 
			/*  var name=$("#jhmc").val();
		     var no=$("#jhbh").val(); 
		     var planType=$("#wtype").val();*/
	           $.ajaxFileUpload ( {
	                        url: "${pageContext.request.contextPath}/purchaser/upload.do?",  
	                        secureuri: false,  
	                        fileElementId: 'fileName', 
	                        dataType: 'json',
	                        success: function (data) { 
	                        	var bool=true;
	                           var chars = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
							           
	                            if(data=="1"){
					        	   layer.alert("文件格式错误",{offset: ['222px', '390px'], shade:0.01});
					        	    
					           } for(var i = 0; i < chars.length ; i ++) {
							             if(data.indexOf(chars[i])!=-1){
							            	 bool=false;
							             }
							           }
							     if(bool!=true){
							        	   layer.alert(data,{offset: ['222px', '390px'], shade:0.01});
							        	  //  layer.msg(data);   
							        }
							           else{
							        	   layer.alert("上传成功",{offset: ['222px', '390px'], shade:0.01});
							            //  layer.msg("上传成功");
							              $("#jhmc").val(data[0].planName);
							              $("#detailZeroRow").empty();
							              var count=1;
									           for(var i = 0 ;i<data.length;i++ ){
									             $("#detailZeroRow").append("<tr> <td>"+count+"</td> "
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='hidden' name='list[" + i + "].id' value='"+data[i].id+"' />"
												               +"    <input ityle='border: 0px;' type='text' name='list[" + i + "].seq' value='"+data[i].seq+"'/>"
												               +"    <input style='border: 0px;' value='" + data[i].parentId + "' type='hidden' name='list[" + i + "].parentId' />"
												               +"  </td> "
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].department' value='"+isValueLegal(data[i].department)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].goodsName' value='"+isValueLegal(data[i].goodsName)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].stand' value='"+isValueLegal(data[i].stand)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].qualitStand' value='"+isValueLegal(data[i].qualitStand)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].item' value='"+isValueLegal(data[i].item)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'> <input   type='hidden'   value='"+data[i].id+"'> "
												               +"    <input style='border: 0px;' onblur='sum2(this)' type='text' name='list[" + i + "].purchaseCount' value='"+isValueLegal(data[i].purchaseCount)+"'/>"
												               +"  <input type='hidden'  value='"+data[i].parentId+"' >  </td>"
												               +"  <td class='tc p0'> <input   type='hidden'   value='"+data[i].id+"'>"
												               +"    <input style='border: 0px;'  onblur='sum1(this)'  type='text' name='list[" + i + "].price' value='"+isValueLegal(data[i].price)+"'/>"
												               +"   <input type='hidden'  value='"+data[i].parentId+"' >   </td>"
												               +"  <td class='tc p0'>  <input   type='hidden'   value='"+data[i].id+"'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].budget' value='"+budgets(data[i].budget)+"'/>"
												               +"   <input type='hidden'  value='"+data[i].parentId+"' > </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].deliverDate' value='"+isValueLegal(data[i].deliverDate)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"<select name='list["+i+"].purchaseType' class='pt m0' onchange='changeType(this)' >" 
												               +" <c:forEach items='${list2 }' var='obj'>"
																
												               +"	<option value='${obj.name }' >${obj.name}</option>"
												               +"</c:forEach>"
												               +"	</select>"
															
															
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].supplier' value='"+isValueLegal(data[i].supplier)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].isFreeTax' value='"+isValueLegal(data[i].isFreeTax)+"'/>"
												               +"  </td>"
												               +"  <td name='userNone'  class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].goodsUse' value='"+isValueLegal(data[i].goodsUse)+"'/>"
												               +"  </td>"
												               +"  <td name='userNone' class='tc p0'>"
												               +"    <input style='border: 0px;' type='text' name='list[" + i + "].useUnit' value='"+isValueLegal(data[i].useUnit)+"'/>"
												               +"  </td>"
												               +"  <td class='tc p0'>"
												              +"    <input style='border: 0px;' type='text' name='list[" + i + "].memo' value='"+isValueLegal(data[i].memo)+"'/>"
												               +"  </td> <td class='tc w100 p0'><button type='button' class='btn' onclick='delRowIndex(this)''>删除</button></td>"
												               +"</tr>");
									             count++;
									           }
									         
									           
									   	    $("#table tr").each(function(i){
									    	     var  val1= $(this).find("td:eq(8)").children(":first").next().val();//上级id
									    	     var  val2= $(this).find("td:eq(7)").children(":first").next().val();
									    	    if($.trim(val1)== ""&&$.trim(val2) =="") {
									    	    	$(this).find("td:eq(8)").children(":first").next().attr("disabled","true");
									    	    	$(this).find("td:eq(7)").children(":first").next().attr("disabled","true");
									    		}   
								    		}); 
									   	    
									   	    
									   	    
									           var bool=$("input[name='import']").is(':checked');
												if(bool==true){
													$("td[name='userNone']").attr("style","");
													$("th[name='userNone']").attr("style","");
												}else{
													$("td[name='userNone']").attr("style","display:none");
													$("th[name='userNone']").attr("style","display:none");
												}
												
												
							           layer.close(index);
	                        	// eachData(json);
	                           }
	                        }
	                        /* ,error: function (data, status, e) {
	    						alert(e);
	    					} */
	                    }); 
				
			} 
			
 
			
			//判断值是否合法
			function isValueLegal(value){
				if (value == null || value =="null" || value =="undefined" || value ==undefined){
				   return "";
				}
				return value;
			}
			
	 	function budgets(bud){
				if (bud != null ){
					  bud = bud.toFixed(4);
					}
				return bud;
			}   
/* 			function  jud() {
		  var data="5行A列有错误信息";
				var flag=true;
				var chars = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
			     for(var i = 0; i < chars.length ; i ++) {
			    	 if(data.indexOf(chars[id])!=-1){
			    		 flag= false;
			    	 }
			     }
			     if(data.indexOf("文本格式")!=-1){
			    	 flag= false; 
			     }
			     
			     alert(flag);
			     return flag;
			} */
			
			
			
			function imports(obj){
				var bool=$(obj).is(':checked');
				if(bool==true){
					$("td[name='userNone']").attr("style","");
					$("th[name='userNone']").attr("style","");
			
				}else{
					$("td[name='userNone']").attr("style","display:none");
					$("th[name='userNone']").attr("style","display:none");
				}
				
			}
			
/* 			function checkBud(){
				var budget=0;
			    $("#table tr").each(function(){
			    	     var  val= $(this).find("td:eq(7)").children(":first").next().val();//上级id
			    	  if($.trim(val) != "") {
			    			var same=$(this).find("td:eq(8)").children(":first").next().val()-0;;
			    			budget=budget+same;
			    	
			    		}   
		    		}); 
			    budget = budget.toFixed(2); 
			    var total= $("#table").find("tr:eq(1)").find("td:eq(8)").children(":first").next().val()-0;
			    total=total.toFixed(2); 
			   if(total==budget){
				  return true;
			  }else{
				  return false;
			  } 
			} */
			
			function details(){
				var bool=true;
			    $("#table tr").each(function(i){
		    	     var  val1= $(this).find("td:eq(8)").children(":first").next().val();//上级id
		    	     var  val2= $(this).find("td:eq(7)").children(":first").next().val();
		    	  if($.trim(val1) != ""&&$.trim(val2) ) {
		    		  var budget=(val1-0)*(val2-0)/10000;
		    		  var same=$(this).find("td:eq(9)").children(":first").next().val()-0;
		    		   budget = budget.toFixed(4); 
		    		   same = same.toFixed(4); 
			    		if(budget!=same){
			    			 layer.msg("第"+i+"行，金额计算错误，请重新计算！");
			    			 bool=false;
			    		} 
		    	
		    		}   
	    		}); 
			    return bool;
			    
			}
			
		  	 function sum2(obj){  //数量
			        var purchaseCount = $(obj).val()-0;//数量
			        var price2 = $(obj).parent().next().children(":last").prev();//价钱
			        var price = $(price2).val()-0;
			        var sum = purchaseCount*price/10000;
			        var budget = $(obj).parent().next().next().children(":last").prev();
			        $(budget).val(sum);
			      	var id=$(obj).next().val(); //parentId
			      	aa(id);
			    } 
			    
			       function sum1(obj){
			        var purchaseCount = $(obj).val()-0; //价钱
			         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
			      	 var sum = purchaseCount*price2/10000;
			         $(obj).parent().next().children(":last").prev().val(sum);
				     	var id=$(obj).next().val(); //parentId
				     	aa(id);
			    }
			
			       function aa(id){// id是指当前的父级parentid
			    	   var budget=0;
			    	   $("#table tr").each(function(){
			 	    		var cid= $(this).find("td:eq(9)").children(":last").val(); //parentId
			 	    		var same= $(this).find("td:eq(9)").children(":last").prev().val()-0; //价格
				 	       if(id==cid){
				 	    	 
				 	    	  budget=budget+same; //查出所有的子节点的值
				 	       }
			    	   });
			    	   budget = budget.toFixed(4); 
			     
			    	    $("#table tr").each(function(){
				    	  var  pid= $(this).find("td:eq(9)").children(":first").val();//上级id
				    		
				    		if(id==pid){
				    			$(this).find("td:eq(9)").children(":first").next().val(budget);
				    			 var spid= $(this).find("td:eq(9)").children(":last").val();
				    		 calc(spid);
				    		}  
			    		}); 
			    	  var did=$("table tr:eq(1)").find("td:eq(9)").children(":first").val();
			    	    var total=0;
			    	    $("#table tr").each(function(){
			 	    		var cid= $(this).find("td:eq(9)").children(":last").val();
			 	    		var same= $(this).find("td:eq(9)").children(":last").prev().val()-0;
			 	    		 if(did==cid){
			 	    			total=total+same;
			 	    		 }
			    	   }); 
			    	    $("table tr:eq(1)").find("td:eq(9)").children(":first").next().val(total);
			       }   
			       
		        function calc(id){
		        	var bud=0;
			 	   	    $("#table tr").each(function(){
			 	   	           var pid= $(this).find("td:eq(9)").children(":last").val() ;
				 	   	       if(id==pid){
				 	   	         	var currBud=$(this).find("td:eq(9)").children(":first").next().val()-0;
				 	   	            bud=bud+currBud;
				 	   	            bud = bud.toFixed(4);
				 	   	            
				 	   	              var spid= $(this).find("td:eq(9)").children(":last").val();
				 	   	              aa(spid);
				 	   	      }
			     		}); 
			 	    	   
			 	     }   
		        
		        
		      /*   function seqs(){
		        	var bool=true;
				    $("#table tr").each(function(i){
				    	var  val1= $(this).find("td:eq(1)").children(":first").val();//上级id
				    	  if($.trim(val1) == "") {
				    		  layer.msg("第"+i+"行，序号没有填写！");
				    		  bool=false;
				    	  }
				    });
				    return bool;
		        } */
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购需求管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div>
				<h2 class="count_flow"><i>1</i>计划主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>计划名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="name" id="jhmc" value="${planName }">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>计划编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="${planNo }" id="jhbh">
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划文号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="" id="referenceNo">
							<span class="add-on">i</span>
						</div>
					</li>
								
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>类别</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="planType" id="wtype" onchange="gtype(this)">
								<c:forEach items="${list }" var="obj">
									<option value="${obj.id }">${obj.name }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					
				  
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>录入人手机号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" id="mobile" value="${user.mobile }"> 
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"  id="dnone" >
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <input type="checkbox" name="import" onchange="imports(this)" value="进口" class="mr5"/>进口
            </div>
          </li>
          
            <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划附件</span>
                      <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                        <u:show showId="detailshow"  businessId="${fileId}" sysKey="2" typeId="${typeId}" />
          </li>
          
          
          
       
                         
                         
                         
				</ul>

			</div>
			<div class="padding-top-10 clear">
				<h2 class="count_flow"><i>2</i>计划明细</h2>
				<div class="ul_list">
					<div class="col-md-12 p115 mt10">
						<button class="btn btn-windows add" onclick="aadd()">添加</button>
						<%--
	<button  class="btn btn-windows add" onclick="same()">添加同级</button>
	--%><button class="btn btn-windows input" onclick="down()">下载模板</button>
						<button class="btn btn-windows input" onclick="uploadExcel();">导入</button>
						<button class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button>
						<button class="btn padding-left-10 padding-right-10 btn_back" onclick="chakan()">查看编制说明</button>
					</div>
					<div class="col-md-12 col-xs-12 col-sm-12 mt5 over_scroll" id="add_div">

						<form id="add_form" action="${pageContext.request.contextPath}/purchaser/adddetail.html" method="post">
							<table id="table" class="table table-bordered table-condensed table_input">
								<thead>
									<tr class="info space_nowrap">
									    <th class="">行号</th>
										<th class="">序号</th>
										<th class="">需求部门</th>
										<th class="">物种名称</th>
										<th class="">规格型号</th>
										<th class="">质量技术标准（技术参数）</th>
										<th class="">计量单位</th>
										<th class="">采购数量</th>
										<th class="">单价（元）</th>
										<th class="">预算金额（万元）</th>
										<th class="">交货期限</th>
										<th class="">采购方式建议</th>
										<th class="">供应商名称</th>
										<th class="">是否申请办理免税</th>
										<th name="userNone" class="">物资用途（仅进口）</th>
										<th name="userNone" class="">使用单位（仅进口）</th>
										<th class="">备注</th>
										<!-- <th  style="width:300px;">附件</th> -->
									<!-- 	<th class="w100">状态</th> -->
										<th class="">操作</th>
									</tr>
								</thead>
								<tbody id="detailZeroRow">
								<c:if test="${plist==null }">
									<tr name="detailRow">
									<td class="tc">1</td>
										<td class=" p0">
											<input type="hidden" name="list[0].id" id="purid" value="" class="m0">
											<input type="text" name="list[0].seq" required="required" value="一" class="m0  ">
										</td>
										<td class=" p0" name="department">
										
								  		<input type="hidden" name="list[0].department" value="${orgId }" >
								  		<input type="text"  readonly="readonly" value="${orgName}" class="m0 " >
								  		  
										<%-- 	<select name="list[0].department" class="pt" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${requires }" var="obj">
													<option value="${obj.id }">${obj.name }</option>
												</c:forEach>
											</select> --%>
											
											
										</td>
										<td class=" p0">
											<input type="text" name="list[0].goodsName" onkeyup="listName(this)" onblur="lossValue()" class="m0"/>
										</td>
										<td class="tc  p0"><input type="text" name="list[0].stand" class="m0 "></td>
										<td class="tc  p0"><input type="text" name="list[0].qualitStand" class="m0 w100p"></td>
										<td class="tc p0"><input type="text" name="list[0].item" class="m0 "></td>
										<td class="tc  p0" name="purchaseQuantity"><input type="text" name="list[0].purchaseCount" onkeyup="checkNum(this,1)" class="m0 "></td>
										<td class="tc  p0" name="unitPrice"><input type="text" name="list[0].price" onkeyup="checkNum(this,2)" class="m0 "></td>
										<td class="tc  p0"><input type="text" name="list[0].budget" class="m0" ></td>
										<td class=" p0"><input type="text" name="list[0].deliverDate" class="m0"></td>
										<td class=" p0">
											<select name="list[0].purchaseType" class="pt m0" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="obj">
												
													<option value="${obj.id }">${obj.name }</option>
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0"><input type="text" name="list[0].supplier" class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[0].isFreeTax" class="m0"></td>
										<td name="userNone" class="tc  p0"><input type="text" name="list[0].goodsUse" class="m0"></td>
										<td name="userNone" class="tc  p0"><input type="text" name="list[0].useUnit" class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[0].memo" class="m0"></td>
										<!-- <td class="tc w100 p0"></td> -->
								<!--  <td class="tc w100 p0"></td> -->  
										<td class="tc p0"><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
									</tr>
								</c:if>
				 
								<c:if test="${plist!=null }" >
								<c:forEach items="${plist}" var="objs" varStatus="vs">
									<tr name="detailRow">
										<td class="tc  p0">
											<input type="hidden" name="list[${vs.index }].id" id="purid" value="${objs.id}">
											<input type="hidden" name="list[${vs.index }].parentId" id="purid" value="${objs.parentId}">
											<input type="text" name="list[${vs.index }].seq" value="${objs.seq}" class="m0">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${vs.index }].department"   value="${obj.department}"> --%>
											<input type="hidden" name="list[${vs.index }].department" value="${orgId }" >
								  			<input type="text"  readonly="readonly" value="${orgName}" >
											
										
										</td>
										<td class=" p0">
											<input type="text" name="list[${vs.index }].goodsName" onkeyup="listName(this)" onblur="lossValue()" value="${objs.goodsName}" />
										</td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].stand" value="${objs.stand}"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].qualitStand" value="${objs.qualitStand}" class=""></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].item" value="${objs.item}" ></td>
										<td class="tc p0" name="purchaseQuantity"><input type="text" name="list[${vs.index }].purchaseCount" onkeyup="checkNum(this,1)" value="${objs.purchaseCount}" ></td>
										<td class="tc  p0" name="unitPrice"><input type="text" name="list[${vs.index }].price" onkeyup="checkNum(this,2)" value="${objs.price}" ></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].budget"   value="${objs.budget}" ></td>
										<td class=" p0"><input type="text" name="list[${vs.index }].deliverDate" value="${objs.deliverDate}" ></td>
										<td class="p0">
											<select name="list[${vs.index }].purchaseType" class="pt" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
													<c:if test="${objd.id ==objs.purchaseType }">
														<option value="${objd.id }" selected="selected">${objd.name }</option>
														</c:if>
														<c:if test="${objd.id !=objs.purchaseType }">
														<option value="${objd.id }">${objd.name }</option>
														</c:if>
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].supplier"  class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].isFreeTax" class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].goodsUse" class="m0"></td>
										<td class="tc p0"><input type="text" name="list[${vs.index }].useUnit" class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].memo" value="${obj.memo}" class="m0" ></td>
									<%-- 	<td style="width:300px;" class="p0">
											   <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
													<u:upload id="pUp${vs.index}" groups="${sbUp}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${attchid}" auto="true" />
													<u:show showId="pShow${vs.index}" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey}" typeId="${attchid}" />
											   </div>											
										</td> --%>
										
										
<%-- 										<td class="tc w100 "><input type="hidden"  name="list[${vs.index }].status" value="暂存" class="m0" > 暂存</td>
 --%>										<td class="tc "><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
									</tr>
									</c:forEach>
								</c:if>
	
							</tbody>
						</table>
							
							<input type="hidden" name="planName" id="detailJhmc">
							<input type="hidden" name="planNo" id="detailJhbh">
							<input type="hidden" name="planType" id="detailType">
							<input type="hidden" name="recorderMobile" id="detailMobile">
							<input type="hidden" name="planDepName" id="detailXqbm"/>
						    <input type="hidden" name="referenceNo" id="detailRefNo"/>
						 
						</form>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 mt20">
				<input class="btn btn-windows save" style="margin-left: 500px;" type="button" onclick="incr()" value="保存">
				<button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
			</div>

			<div id="content" class="dnone">
				<p align="center">编制说明
					<p style="margin-left: 20px;">1、请严格按照序号顺序为：一、（一）、1（1）、a、（a）的顺序填写序号，括号为中文括号</p>

					<p style="margin-left: 20px;">2、任务明细最多为六级,请勿多于六级</p>

					<p style="margin-left: 20px;">3、请勿空行填写</p>

					<p style="margin-left: 20px;">4、需求单位名称不能为空</p>

					<p style="margin-left: 20px;">5、请按表式填写计划明细。用户可以编辑行，但不能增加或删除列。</p>

					<p style="margin-left: 20px;">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)
					</p>

					<p style="margin-left: 20px;">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

					<p style="margin-left: 20px;">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

					<p style="margin-left: 20px;">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。</p>

					<p style="margin-left: 20px;">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
					<button class="btn padding-left-10 padding-right-10 btn_back" style="margin-left: 230px;" onclick="closeLayer()">确定</button>

			</div>

			<input type="hidden" id="count" value="0">
			<div id="catalogue" class="dnone">
				<div id="ztree" class="ztree"></div>
			</div>

			<form id="" action="${pageContext.request.contextPath}/purchaser/ztree.html" method="post">
				<input type="hidden" name="planName" id="fjhmc">
				<input type="hidden" name="planNo" id="fjhbh">
				<input type="hidden" name="type" value="" id="ptype">
			</form>
		</div>
		
		<div id="materialName" class="dnone" style="width:178px;max-height:400px;overflow:scroll;border:1px solid grey;">
				
		</div>
		
		
	<div  class=" clear margin-top-30" id="file_div"  style="display:none;" >
<%--     	<form id="up_form" action="${pageContext.request.contextPath}/purchaser/upload.do" method="post" enctype="multipart/form-data">
 --%>    	  <div class="col-md-12 col-sm-12 col-xs-12">
 				<input type="file" id="fileName" class="input_group" name="file" >
 			  </div>
 			  <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
    		    <input type="button" class="btn input" onclick="fileup()"   value="导入" />
    		  </div>
    		<!-- 	 <input type="hidden"  name="planName" id="detailJhmcf">
							<input type="hidden" name="planNo" id="detailJhbhf">
							<input type="hidden" name="planType" id="detailTypfef">
							<input type="hidden" name="recorderMobile" id="detailMobilef">
							<input type="hidden" name="planDepName" id="detailXqbmf"/> -->
							
							
    <!-- 	</form> -->
    </div>
    
    
	</body>
</html>