<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../common.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=request.getContextPath()%>public/laypage-v1.3/laypage/laypage.js"></script>
    <title>供应商列表展示</title>
    
<script type="text/javascript">

$(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${list.total}",
		    startRow:"${list.startRow}",
		    endRow:"${list.endRow}",//"${list.endRow}"
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '<%=request.getContextPath()%>offer/list.html?page='+e.curr;
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

function add(){
	var id=[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==1){
		window.location.href="<%=basePath%>offer/selectProduct.html?contractId="+id;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择需要报价的合同",{offset: ['222px', '390px'], shade:0.01});
	}
}

</script>    
    
  </head>
  
  <body>
    
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">供应商报价</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
    
    <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
    <div class="container">
     <div class="p10_25">
     <h2 class="padding-10 border1">
       <form action="<%=request.getContextPath() %>offer/search.html" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">合同名称：</label><span><input type="text" name="name" value="${name }" class=""/></span>
	      </li>
    	  <li class="fl">
	    	<label class="fl">合同编号：</label><span><input type="text"  name="code" value="${code }" class=""/></span>
	      </li>
    	  <li class="fl">
	    	<label class="fl">供应商名称：</label><span><input type="text" name="supplierName" value="${supplierName }" class=""/></span>
	      </li> 
	    	<button type="submit" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>
   </div>
</div>
	<div class="container">	
		<div class="col-md-8 mt10 ml5">
	   		<button class="btn" type="button" onclick="add()">报价</button>
		</div>
	</div>
	
	<div class="container margin-top-5">
	<div class="content padding-left-25 padding-right-25 padding-top-5">
		  <table class="table table-bordered table-condensed">
		  	<thead>
	  			<tr>
	  				<th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	  				<th class="info">序号</th>
	  				<th class="info">合同名称</th>
	  				<th class="info">合同编号</th>
	  				<th class="info">合同金额(万元)</th>
	  				<th class="info">供应商名称</th>
	  				<th class="info">合同状态</th>
	  				<th class="info">签订状态</th>
	  			</tr>
	  		</thead>
	  		<c:forEach items="${list.list}" var="contract" varStatus="vs">
	  			<tr class="pointer">
	  				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${contract.id }" /></td>
	  				<td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
	  				<td class="tc">${contract.name }</td>
	  				<td class="tc">${contract.code }</td>
	  				<td class="tc">${contract.money }</td>
	  				<td class="tc">${contract.supplierName }</td>
	  				<td class="tc">
	  				<c:if test="${contract.contract.given=='1' }">
	  					已签订
	  				</c:if>
	  				</td>
	  				<td class="tc">
	  				<c:if test="${contract.appraisal=='1' }">
	  					审价中
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
