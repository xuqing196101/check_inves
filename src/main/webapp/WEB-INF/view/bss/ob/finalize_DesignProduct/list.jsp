<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>定型产品列表页面</title>
	<jsp:include page="../../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
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
	      		location.href = "${pageContext.request.contextPath }/product/list.do?page=" + e.curr;
	        }
	      }
	    });
	  });
	
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
		var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		var ids = id.toString();
		if(id.length > 0) {
			for(var i=0;i<id.length;i++){
				if($("#"+id[i]).html().replace(/\s+/g,"") == "已发布"){
					layer.alert("不能删除已发布的产品", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
					return false;
				}
			}
			layer.confirm('您确定要删除吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/product/delete.html",
					type: "post",
					data: {
						oBProductids: ids
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath }/product/list.html";
					},
					error: function() {

					}
				});
			});
		} else {
			layer.alert("请选择要删除的版块", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	
	/* 发布 */
	function fb() {
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			if($("#"+id).html().replace(/\s+/g,"") == "暂存" || $("#"+id).html().replace(/\s+/g,"") == "已撤回" || $("#"+id).html().replace(/\s+/g,"") == "未发布"){
			layer.confirm('您确定要发布吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/product/fab.html",
					type: "post",
					data: {
						id:id[0]
					},
					success: function(data) {
						if(data == 'success'){
							window.location.href = "${pageContext.request.contextPath }/product/list.html";
						}else{
							if(data == 'error'){
								layer.alert("产品代码和产品名称不能重复", {
									offset: ['222px', '390px'],
									shade: 0.01
								});
							}else if(data == 'error1'){
								layer.alert("产品代码不能重复", {
									offset: ['222px', '390px'],
									shade: 0.01
								});
							}else if(data == 'error2'){
								layer.alert("产品名称不能重复", {
									offset: ['222px', '390px'],
									shade: 0.01
								});
							}else{
							layer.alert("只有资源服务中心才能操作", {
									offset: ['222px', '390px'],
									shade: 0.01
								});
							}
						}
					},
					error: function() {
						
					}
				});
			});
		}else{
			layer.alert("不能重复发布产品", {
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
			layer.alert("请选择需要发布的产品", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	
	/* 撤回发布 */
	function chfb() {
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			if($("#"+id).html().replace(/\s+/g,"") == "已发布" ){
			layer.confirm('您确定要撤回吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/product/chfab.html",
					type: "post",
					data: {
						id:id[0]
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath }/product/list.html";
					},
					error: function() {
						
					}
				});
			});
		}else{
			layer.alert("只能撤回已发布的产品", {
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
			layer.alert("请选择需要撤回的产品", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	
	/* 修改 */
	function edit() {
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			if($("#"+id).html().replace(/\s+/g,"") != "已发布" ){
				window.location.href = "${pageContext.request.contextPath }/product/tiaozhuan.html?proid=" + id + "&&type=1";
			}else{
				layer.alert("不能修改已发布的产品", {
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
			layer.alert("请选择需要修改的产品", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	//重置
	function resetQuery() {
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
		window.location.href = "${pageContext.request.contextPath}/product/list.html";
	}
	
	// 弹出导入框
	var index;
	function upload(){
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
	 index = layer.open({
		type: 1, //page层
		area: ['400px', '300px'],
		title: '导入定型产品',
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
    	 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
    	window.location.href ="${pageContext.request.contextPath}/product/download.html";
    }
	
	//导入excl 
	function fileUpload(){
	 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
	 $.ajaxFileUpload ({
	               url: "${pageContext.request.contextPath}/product/upload.do?",  
	               secureuri: false,  
	               fileElementId: 'fileName', 
	               dataType: 'json',
	               success: function (data) { 
	               var bool=true;
	               var chars = ['A','B','C','D','E','F'];
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
					       			window.location.href = "${pageContext.request.contextPath}/product/list.html";
								});
					         
	                 }
	             }
	         }); 
	     }
	
	/* 下载目录 */
	function downCategory(){
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		window.location.href ="${pageContext.request.contextPath}/product/downloadCategory.html";
	}
	
	function fbdxcp(){
		 var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
	window.location.href = "${pageContext.request.contextPath }/product/tiaozhuan.html?type=2";
	}
	
	</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 定型产品列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>定型产品列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/product/list.html" method="post" class="mb0" id = "form1">
    	<ul class="demand_list">
    	<li>
	    	<label class="fl">产品名称：</label>
			<input type="text" id="topic" class="" name = "name" value="${productExample.name }"/>
	      </li>
    	  <li>
	    	<label class="fl">产品代码：</label>
			<input type="text" id="topic" class="" name = "code" value="${productExample.code }"/>
	      </li>
    	  <li>
	    	<label class="fl">产品状态：</label>
	    	  <select class="w178" name="status">
	    	    <option value="0">全部</option>
	    	    <option value="1" <c:if test="${'1'==productExample.status}">selected="selected"</c:if>>暂存</option>
	    	    <option value="2" <c:if test="${'2'==productExample.status}">selected="selected"</c:if>>已发布</option>
	    	    <option value="4" <c:if test="${'4'==productExample.status}">selected="selected"</c:if>>未发布</option>
	    	    <option value="3" <c:if test="${'3'==productExample.status}">selected="selected"</c:if>>已撤回</option>
	    	  </select>
	      </li>
	    	<input class="btn fl mt1" type="submit" value="查询" /> 
	    	<input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick="fbdxcp()">添加定型产品</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
		<button class="btn btn-windows apply" type="button" onclick="fb()">发布</button>
		<button class="btn btn-windows withdraw" type="button" onclick="chfb()">撤回发布</button>
		<button class="btn btn-windows btn btn-windows input" type="button" onclick="down()">下载批量导入模板</button>
		<button class="btn btn-windows btn btn-windows output" type="button" onclick="upload()">批量导入</button>
		<button class="btn btn-windows btn btn-windows input" type="button" onclick="downCategory()">下载产品目录</button>
	</div>   
	<div class="content table_box">
	
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="33%">产品名称</th>
		  <th class="info" width="17%">产品代码</th>
		  <th class="info" width="20%">产品目录（末节点）</th>
		  <th class="info" width="10%">产品状态</th>
		  <th class="info">合格供应商数量</th>
		</tr>
		</thead>
		<c:forEach items="${info.list }" var="product" varStatus="vs">
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${product.id }" /></td>
		  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
		  <td title="${product.name }">
			  <a href="${pageContext.request.contextPath }/product/view.html?productId=${product.id }">
			  	<c:if test="${fn:length(product.name) > 25 }">${fn:substring(product.name, 0, 25)}...</c:if>
				<c:if test="${fn:length(product.name) <= 25 }">${product.name }</c:if>
			  </a>
		  </td>
		  <td>${product.code}</td>
		  <td class="tl" title = "${product.pointsName }">${product.smallPoints.name }</td>
		  <td class="tc" id = "${product.id }">
		  	<c:if test="${product.status == 1}">暂存</c:if>
		  	<c:if test="${product.status == 2}">已发布</c:if>
		  	<c:if test="${product.status == 3}">已撤回</c:if>
		  	<c:if test="${product.status == 4}">未发布</c:if>
		  </td>
		  <td class="tc">
		  	<a href = "${pageContext.request.contextPath}/product/supplier.html?smallPointsId=${product.smallPointsId }">
		  		<c:forEach items="${numlist }" var="num">
		  			<c:if test="${num.smallPointsId == product.smallPointsId }">${num.nCount }</c:if>
		  		</c:forEach>
		 	</a>
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