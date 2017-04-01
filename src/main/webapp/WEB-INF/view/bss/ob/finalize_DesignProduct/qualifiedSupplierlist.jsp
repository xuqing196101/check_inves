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
      		location.href = "${pageContext.request.contextPath }/product/supplier.do?page=" + e.curr+"&&smallPointsId=${smallPointsId }";
        }
      }
    });
  });


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

//重置
function resetQuery() {
	var prodid = $("#prodid").val();
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	window.location.href = "${pageContext.request.contextPath}/product/supplier.html?smallPointsId="+prodid;
}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li><li class="active"><a href="javascript:void(0)">供应商列表</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 供应商列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/product/supplier.html" method="post" class="mb0" id = "form1">
    	<input id = "prodid" name = "smallPointsId" value = "${smallPointsId }" style="display: none;">
    	<ul class="demand_list">
		<li>
	    	<label class="fl">供应商名称：</label>
			<input type="text" id="" class="" name = "supplierName" value="${supplierName }"/>
	     </li>
	     <li>
			<label class="fl">供应商证书状态：</label>
			<select class="w178" name = "status">
				<option value="0">-请选择-</option>
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
	<button class="btn btn-windows back" type="button" onclick="window.location.href = '${pageContext.request.contextPath}/product/list.html'">返回</button>
	</div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
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
				<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tc" width="38%">${supplier.supplier.supplierName }</td>
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
				<td class="tc">
				<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
					<c:choose>  
         	 			<c:when test="${nowDate-supplier.certValidPeriod.getTime() > 0}">过期</c:when>  
          				<c:when test="${nowDate-supplier.certValidPeriod.getTime() < 0}">未过期</c:when>  
          			</c:choose> 
				</td>
			</tr> 
		</c:forEach>
		</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>