<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>添加供应商列表页面</title>

	<jsp:include page="../../../ses/bms/page_style/backend_common.jsp"></jsp:include>
	<script type="text/javascript">
	
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
	
	function addSupplier(){
		window.location.href="${pageContext.request.contextPath}/addSupplier/add.html";
	}
	
	function supplierlist(){
		window.location.href="${pageContext.request.contextPath}/addSupplier/supplierlist.html";
	}
	</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">添加供应商</a></li><li class="active"><a href="javascript:void(0)">添加供应商列表</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 添加供应商列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">产品代码：</label>
			<input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">产品状态：</label>
	    	  <select class="w178">
	    	    <option>选项一</option>
	    	    <option>选项二</option>
	    	    <option>选项三</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">产品名称：</label>
			<input type="text" id="topic" class=""/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick="addSupplier()">添加供应商</button>
		<button class="btn" type="button" onclick="supplierlist()">供应商列表</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">产品代码</th>
		  <th class="info">产品名称</th>
		  <th class="info">大类</th>
		  <th class="info">小类</th>
		  <th class="info">合格供应商数量</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">1</td>
		  <td>adada4425535</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		  <td class="tc">计算机</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">3</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">2</td>
		  <td>adada4425535</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		  <td class="tc">计算机</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">3</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">3</td>
		  <td>adada4425535</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		  <td class="tc">计算机</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">3</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">4</td>
		  <td>adada4425535</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		  <td class="tc">计算机</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">3</td>
		</tr>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>