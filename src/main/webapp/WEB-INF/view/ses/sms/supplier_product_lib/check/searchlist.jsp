<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />  
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" type="text/css" />
  </head>

  <script type="text/javascript">
  $(function(){
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
	      		location.href = "${pageContext.request.contextPath }/product_lib/findAllCheckProduct.html?name=${name}&&status=${status}&&page=" + e.curr;
	        }
	      }
	    });
	    
	  });
  
  
  // 加载供应商
  $(function(){
		$.ajax({
			url: "${pageContext.request.contextPath }/product_lib/findAllSupplier.do",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
					$.each(data, function(i, supplier) {
						if(supplier.supplierName != null){
							$("#supplierId").append("<option value=" + supplier.id + ">" + supplier.supplierName + "</option>");
						}
					});
				} 
				$("#supplierId").select2();
				// 设置被选中的值
				$("#supplierId").select2("val","${createrId}");
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
	
	// 查看基本信息
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/product_lib/findSignalProductInfo.html?id="+id;
  	}
	
	
	//!--搜索-->
	function query(){
		$("#queryForm").attr("action","${pageContext.request.contextPath}/product_lib/findAllCheckProduct.html");
		$("#queryForm").submit();
	}
	
	//重置按钮事件  
    function resetAll(){
        $("#name").val("");  
        $("#status").val("");  
        $("#supplierId").val("");  
    }
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">产品库管理</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
   			<h2>供应商商品查询</h2>
	   </div>

   
   <!-- 查询 -->

     <div class="search_detail">
	 	<form action="" name="queryForm" id="queryForm" method="post">
			 <ul class="demand_list">
			   <li><label class="fl">名称：</label><span><input type="text" value="${ name }" name="name" id="name" class="mb0" /></span></li>
			   <li>
			   	<label class="fl">审核状态：</label>
		    	  <select style="width: 150px" id="status" name="status" class="w178">
		    	    <option value="">--请选择--</option>
		    	    <option value="1" <c:if test="${'1' eq status}">selected</c:if>>待审核</option>
		    	    <option value="2" <c:if test="${'2' eq status}">selected</c:if>>审核未通过</option>
		    	    <option value="3" <c:if test="${'3' eq status}">selected</c:if>>审核通过</option>
		   	  	  </select>
			   </li>
			   <li>
			   		<label class="fl">供应商名称：</label>
					<select style="width: 200px" id="supplierId" name="createrId">
			  			<option value="">--请选择--</option>
					</select>
				</li>
			   <button type="button" onclick="query()" class="btn fl mt1">查询</button>
	    	   <button onclick="resetAll()" class="btn fl ml5 mt1">重置</button> 
			 </ul>
		 </form>
	 <div class="clear"></div>
    </div>

   
   <!-- 表格开始-->
   <div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info" width="25%">名称</th>
		  <th class="info" width="10%">价格（元）</th>
		  <th class="info" width="15%">型号</th>
		  <th class="info" width="15%">商品品目</th>
		  <th class="info" width="15%">供应商</th>
		  <th class="info">审核状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="p" varStatus="vs">
			<tr>
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${p.id}:${p.status}" /></td>
				<td class="tc pointer" onclick="show('${p.id},${p.status}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<td class="tl pointer" onclick="show('${p.id},${p.status}')">${p.name}</td>
				<td class="tr pointer" onclick="show('${p.id},${p.status}')">
					<fmt:formatNumber value='${p.price}' pattern='#,##,###.00'/>
				</td>
				<td class="tl pointer" onclick="show('${p.id},${p.status}')">${p.typeNum}</td>
				<td class="tl pointer" onclick="show('${p.id},${p.status}')">${p.category.name}</td>
				<td class="tl pointer" onclick="show('${p.id},${p.status}')">${p.supplier.supplierName}</td>
				<td class="tc pointer" onclick="show('${p.id},${p.status}')">
					<c:if test="${ p.status == 1 }">
						待审核
					</c:if>
					<c:if test="${ p.status == 2 }">
						审核未通过
					</c:if>
					<c:if test="${ p.status == 3 }">
						审核通过
					</c:if>
				</td>
			</tr>
		</c:forEach>
        </table>
     </div>
   <div id="pagediv" align="right"></div>
  </div>
  </body>
</html>
