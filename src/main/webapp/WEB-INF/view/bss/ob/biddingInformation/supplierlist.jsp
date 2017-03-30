<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
    	 if(!first) { //一定要加此判断，否则初始时会无限刷新
			$("#page").val(e.curr);
    	 	var obProjectId = $("#obProjectId").val();
    	 	var result = $("#result").val();
    	 	var status = $("#st option:selected").val();
    	 	window.location.href = "${pageContext.request.contextPath}/ob_project/supplierList.html?page=" + e.curr +"&&obProjectId="+obProjectId+"&&result="+result+"&&status="+status;
		}
      }
    });
  });

//重置
function resetQuery() {
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
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
</script>
</head>
<body>
    
<!-- 供应商列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/ob_project/supplierList.html" method="post" class="mb0" id = "form1">
    	<input id = "obProjectId" name = "obProjectId" value = "${obProjectId }" style="display: none;">
    	<input id="result" name="result" value="${result }" type="hidden">
    	<ul class="demand_list">
    	<li>
	    	<label class="fl">供应商名称：</label>
			<input type="text" id="" class="" name = "name" value="${name }"/>
	     </li>
		<li>
			<label class="fl">供应商证书状态：</label>
			<select class="w178" name = "status" id = "st">
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
		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	</div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">产品目录（末节点）</th>
		  <th class="info">证书有效期至</th>
		  <th class="info">资质证书内容</th>
		  <th class="info">是否过期</th>
		</tr>
		</thead>
		<c:forEach items="#{info.list}" var="supplier" varStatus="vs">
			<tr>
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${supplier.id }" /></td>
				<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tl">${supplier.supplier.supplierName }</td>
				<td class="tc" title = "${supplier.pointsName }">${supplier.smallPoints.name }</td>
				<td class="tc">
					<fmt:formatDate value="${supplier.certValidPeriod }" pattern="yyyy-MM-dd" /> 
				</td>
				<td class="tc"><button type="button" onclick="openViewDIvs('${supplier.id }');" class="btn">查看</button></td>
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
</body>
</html>