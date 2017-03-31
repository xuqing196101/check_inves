<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>供应商列表页面</title>
<script type="text/javascript">
/* 分页 */
$(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${info.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${info.total}",
      startRow : "${info.startRow}",
      endRow : "${info.endRow}",
      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${info.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
    	if(!first){ //一定要加此判断，否则初始时会无限刷新
      		location.href = "${pageContext.request.contextPath}/obSupplier/supplier.do?page=" + e.curr;
        }
      }
    });
  });

//重置
function resetQuery() {
	var prodid = $("#prodid").val();
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	window.location.href = "${pageContext.request.contextPath}/obSupplier/supplier.html?prodid="+prodid;
}

/** 全选全不选 */
function selectAll(){
	 var checklist = document.getElementsByName ("chkItem");
	 var checkAll = document.getElementById("checkAll");
	   if(checkAll.checked){
		   for(var i=0;i<checklist.length;i++)
		   {
		      checklist[i].checked = true;
		   } 
		 }else{
		  for(var j=0;j<checklist.length;j++)
		  {
		     checklist[j].checked = false;
		  }
	 	}
	}

/** 单选 */
function check(){
	 var count=0;
	 var checklist = document.getElementsByName ("chkItem");
	 var checkAll = document.getElementById("checkAll");
	 for(var i=0;i<checklist.length;i++){
		   if(checklist[i].checked == false){
			   checkAll.checked = false;
			   break;
		   }
		   for(var j=0;j<checklist.length;j++){
				 if(checklist[j].checked == true){
					   checkAll.checked = true;
					   count++;
				   }
			 }
	   }
}

/* 删除 */
function del(){
	var id = [];
	$('input[name="chkItem"]:checked').each(function() {
		id.push($(this).val());
	});
	var ids = id.toString();
	if(id.length > 0) {
		var pan = true;
		for (var i=0;i<id.length;i++){
			var status = $("#"+id[i]+"status").html();
			var aa=status.replace(/\s+/g,"");
			if(aa == "已过期"){
				pan = false;
			}
		}
		if(pan == true){
			layer.confirm('您确定要删除吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/obSupplier/delete.html",
					type: "post",
					data: {
						ids: ids
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath }/obSupplier/supplier.html?prodid=${prodid }";
					},
					error: function() {

					}
				});
			});
		}else {
			layer.alert("只能删除未过期的供应商", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	} else {
		layer.alert("请选择要删除的版块", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	}
}

/* 修改 */
function edit() {
	var id = [];
	$('input[name="chkItem"]:checked').each(function() {
		id.push($(this).val());
	});
	if(id.length == 1) {
		var status = $("#"+id+"status").html();
		var aa=status.replace(/\s+/g,"");
		if(aa == "已过期"){
			window.location.href = "${pageContext.request.contextPath }/obSupplier/toedit.html?status=${status}&&suppid=" + id ;
		}else {
			layer.alert("只能修改已过期的供应商", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	} else if(id.length > 1) {
		layer.alert("只能选择一个", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	} else {
		layer.alert("请选择需要修改的版块", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	}
}

/* 查看图片 */
function openViewDIvs(id){
	
	var params={"businessId":id,"typeId":46,"key":2};
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

/**
 * 附件下载
 * @param id 主键
 * @param key 对应系统的key
 */
function download(bid){
	var key = 2;
	var zipFileName = null;
	var fileName = null;
	$.ajax({
		url: "${pageContext.request.contextPath }/obSupplier/findBybusinessId.html",
		type: "post",
		data: {
			id: bid,
			key:key
		},
		success: function(data) {
			if(data != ""){
				id = data;
				var form = $("<form>");   
			    form.attr('style', 'display:none');   
			    form.attr('method', 'post');
			    form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key + '&zipFileName=' + encodeURI(encodeURI(zipFileName)) + '&fileName=' + encodeURI(encodeURI(fileName)));
			    $('body').append(form); 
			    form.submit();
			}
		},
		error: function() {

		}
	});
	
}

//弹出导入框
var index;
function upload(){
		index = layer.open({
			type: 1, //page层
			area: ['400px', '300px'],
			title: '导入供应商',
			closeBtn: 1,
			shade: 0.01, //遮罩透明度
			moveType: 1, //拖拽风格，0是默认，1是传统拖动
			shift: 1, //0-6的动画形式，-1不开启
			offset: ['80px', '400px'],
			content: $('#file_div'),
			});
}

//下载模板
function down(){
	window.location.href ="${pageContext.request.contextPath}/obSupplier/download.html";
}

//导入excl 
function fileUpload(){
 $.ajaxFileUpload ({
               url: "${pageContext.request.contextPath}/obSupplier/upload.do",
               secureuri: false,  
               fileElementId: 'fileName', 
               dataType: 'json',
               success: function (data) { 
               var bool=true;
               var chars = ['A','B','C','D','E','F','G','H'];
               if(data=="1"){
			     layer.alert("文件格式错误",{offset: ['222px', '390px'], shade:0.01});
				 } 
				 for(var i = 0; i < chars.length ; i ++) {
					 if(data.indexOf(chars[i])!=-1){
					  	 bool=false;
					}
					 }
					if(bool!=true){
					 	   layer.alert(data,{offset: ['222px', '390px'], shade:0.01});
					  }else{
						  layer.confirm('上传成功', {
							    btn: ['确定'], //按钮
							    shade: false //不显示遮罩
							}, function(index){
								window.location.href = "${pageContext.request.contextPath}/obSupplier/supplier.html";
							});
                 }
             }
         }); 
     }
     
     
	/* 下载目录 */
	function downCategory(){
		window.location.href ="${pageContext.request.contextPath}/product/downloadCategory.html";
	}

</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li><li class="active"><a href="javascript:void(0)">质检供应商列表</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 供应商列表页面开始 -->
	<div class="container">
	<div class="headline-v2">
		<h2>质检供应商列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/obSupplier/supplier.html" method="post" class="mb0" id = "form1">
    	<input id = "prodid" name = "prodid" value = "${prodid }" style="display: none;">
    	<ul class="demand_list">
    	<li>
	    	<label class="fl">供应商名称：</label>
			<input type="text" id="" class="" name = "supplierName" value="${supplierName }"/>
	     </li>
	     <li>
	    	<label class="fl">产品目录：</label>
			<input type="text" id="" class="" name = "smallPointsName" value="${smallPointsName }"/>
	     </li>
		<li>
			<label class="fl">供应商证书状态：</label>
			<select class="w178" name = "status">
				<option value="0" <c:if test="${'0'==status}">selected="selected"</c:if>>-请选择-</option>
	    	    <option value="1" <c:if test="${'1'==status}">selected="selected"</c:if>>已过期</option>
	    	    <option value="2" <c:if test="${'2'==status}">selected="selected"</c:if>>未过期</option>
			</select>
		</li>
		<button type="submit" class="btn">查询</button>
		<button type="reset" class="btn" onclick="resetQuery()">重置</button>  	
		</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick = "window.location.href = '${pageContext.request.contextPath }/obSupplier/addSupplieri.html'">添加</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
		<button class="btn btn-windows btn btn-windows input" type="button" onclick="down()">下载EXCEL模板</button>
		<button class="btn btn-windows btn btn-windows output" type="button" onclick="upload()">导入EXCEL</button>
		<button class="btn btn-windows btn btn-windows input" type="button" onclick="downCategory()">下载产品目录</button>
	</div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="38%">供应商名称</th>
		  <th class="info">证书有效期至</th>
		  <th class="info">产品目录（末节点）</th>
		  <th class="info">资质证书内容</th>
		  <th class="info">是否过期</th>
		</tr>
		</thead>
		<c:forEach items="${info.list }" var="supplier" varStatus="vs">
			<tr>
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${supplier.id }" /></td>
				<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tl" width="38%">${supplier.supplier.supplierName }</td>
				<td class="tc">
					<fmt:formatDate value="${supplier.certValidPeriod }" pattern="yyyy-MM-dd" /> 
				</td>
				<td class="tc" title = "${supplier.pointsName }">${supplier.smallPoints.name }</td>
				<td class="tc">
					<ul id="post_attach_show_disFileId" class="uploadFiles">
						<li class="file_view">
						<a href="javascript:openViewDIvs('${supplier.id }');"></a>
						</li>
						<li class="file_load">
							<a href="javascript:download('${supplier.id }');"></a>
						</li>
						<li class="file_delete"></li>
					</ul>
				</td>
				<td class="tc" id = "${supplier.id }status">
				<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
					<c:choose>  
         	 			<c:when test="${nowDate-supplier.certValidPeriod.getTime() > 0}">已过期</c:when>  
          				<c:when test="${nowDate-supplier.certValidPeriod.getTime() < 0}">未过期</c:when>  
          			</c:choose> 
				</td>
			</tr> 
		</c:forEach>
		</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
   <!-- 导入文件 -->
	<div  class=" clear margin-top-30" id="file_div"  style="display:none;" >
		<div class="col-md-12 col-sm-12 col-xs-12">
 		   <input type="file" id="fileName" class="input_group" name="file" >
 		</div>
 		<div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
    	    <input type="button" class="btn input" onclick="fileUpload()" value="导入" />
    	</div>
	</div>
</body>
</html>