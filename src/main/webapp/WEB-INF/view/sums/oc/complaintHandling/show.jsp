<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉处理详细页面</title>
<script type="text/javascript">

//$("#sendPhoneNum").css("display", "none"); 

$(function(){ 
	<c:if test="${complaint.type=='0'}">
	$("#idcard").css("display", "none"); 	
    </c:if>
    <c:if test="${complaint.type=='1'}">
    $("#idcard").css("display", ""); 
</c:if>

}); 
    
	function openBli() {
		layer
				.open({

					btn : [ '确定', '取消' ],
					btnAlign : 'c',
					type : 1,
					title : '请注明缺失内容',
					skin : 'layui-layer-rim',
					shadeClose : true,
					area : [ '580px', '230px' ],
					content : $("#openBli"),
					yes : function() {
						<!--按钮确定的回调-->						
						var id = $("#ComplaintId").val();<!--获取审核数据idmsg无法立项的信息-->
						var msg = $("#bremarks").val();						
						window.location.href = "${pageContext.request.contextPath}/onlineComplaints/update.do?Id="
								+ id+"&Msg="+msg+"&State=2";
					},
					btn2 : function(index) {
						<!--按钮取消的回调-->
						layer.close(index);

					}
				});
	}

	function openLi() {
		layer
				.open({
					type : 1,
					btn : [ '确定', '取消' ],
					btnAlign : 'l',
					title : '请输入处理结果',
					skin : 'layui-layer-rim',
					shadeClose : true,
					area : [ '580px', '230px' ],
					content : $("#openLi"),
					yes : function() {
						<!--按钮确定的回调-->
						var text = $("#remarks").val();
						var id = $("#ComplaintId").val();
						window.location.href = "${pageContext.request.contextPath}/onlineComplaints/update.do?Id="
								+ id + "&Msg=" +text+"&State=1";
					},
					btn2 : function() {
						<!--按钮取消的回调-->
						layer.closeAll();

					}
				});
	}
	/* 查看图片 */
	function openViewDIvs(id,typeId){
		var params={"businessId":id,"typeId":typeId,"key":2};
		$.ajax({
			url: globalPath + '/file/displayFile.do',
			data: params,
			async: false,
			dataType: 'json',
			success:function(datas){
				var html ="<ul>";
				for(var i = 0;i < datas.length;i++){
					var url='${pageContext.request.contextPath }/file/viewFile.html?id='+datas[i].id+'&key=2';
					html+='<li><div class="col-md-2 padding-0 fl"><div class="fl suolue"><a href="javascript:upPicture();" class="thumbnail mb0 suolue">'
					+'<img data-original="'+url+'"  src="'+url+'" height="120px"/></a></div></div></li>';
				}
				html += "</ul>";
				var height = document.documentElement.clientHeight;
				var index = layer.open({
					  type: 1,
					  title: '图片查看',
					  skin: 'layui-layer-pic',
					  shadeClose: true,
					  area: [$(document).width() +'px',height + "px"],
					  offset:['0px','0px'],
					  content: html
					});
			}
		});

	}
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理详细页</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>


	<div class="container container_box">
		<form action="update" method="post" class="mb0">
			<h2 class="list_title">投诉处理详细</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12  pl15"><span
					class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉人类型</span>
					<div
						class="input-append input_group col-md-12 mb20 col-sm-12 col-xs-12 p0">
						<c:if test="${complaint.type=='0'}">
							<input readOnly="readOnly" class="" onchange="hid()" name="PerSonName" type="text"
								value="单位">
						</c:if>
						<c:if test="${complaint.type=='1'}">
							<input readOnly="readOnly" class="" onchange="hid()" name="PerSonName" type="text"
								value="个人">
						</c:if>
						<input readOnly="readOnly" type="hidden" id="ComplaintId" value="${ComplaintId}">
					</div></li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span
					class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉人名称（姓名）</span>
					<div
						class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input  readOnly="readOnly" class="" name="PerSonName" type="text"
							value="${complaint.name }">
					</div></li>

				<li class="col-md-3 col-sm-6 col-xs-12"><span
					class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉对象</span>
					<div
						class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input readOnly="readOnly" class="" name="PerSonName" type="text"
							value="${complaint.complaintObject }">
					</div></li>
				<li class="col-md-12 col-sm-12 col-xs-12"><span
					class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉事项</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
						<textarea readOnly="readOnly" class="w100p h130" >${complaint.complaintMatter }</textarea>
					</div></li>
			  <li class="col-md-3 col-sm-6 col-xs-12 mt15"><span
					class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉文件附件</span>
					<div
						class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<td class="tc"><button type="button" onclick="openViewDIvs('${complaint.id }',47);" class="btn" >查看</button></td>
					</div></li>  
			<li class="col-md-3 col-sm-6 col-xs-12 mt15 " id="idcard"><span
			class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">身份证照片附件</span>
			<div
				class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<td class="tc"><button type="button" onclick="openViewDIvs('${complaint.id }',48);" class="btn" >查看</button></td>
			</div></li> 
			</ul>
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
				<button class="btn" type="button" onclick="openLi();">立项</button>
				<button class="btn" type="button" onclick="openBli();">无法立项</button>
			</div>
			<!--无法立项弹出框-->
			<div id="openBli" class="dnone layui-layer-wrap">
				<div class="drop_window">
					<ul class="list-unstyled">
						<li class=" col-md-12 col-sm-12 col-xs-12 pl15"><label
							class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</label> <textarea
								id="bremarks" name="bremarks" class="w100p h80 p0" rows="3"
								cols="1"></textarea></li>
					</ul>
				</div>
			</div>
			<!--立项弹出框-->
			<div id="openLi" class="dnone layui-layer-wrap">
				<div class="drop_window">
					<ul class="list-unstyled">
						<li class=" col-md-12 col-sm-12 col-xs-12 pl15"><label
							class="col-md-12 col-sm-12 col-xs-12 padding-left-5">立项结果</label>
							<textarea id="remarks" name="remarks" class="w100p h80 p0"
								rows="3" cols="1"></textarea></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
</body>
</html>