<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
    <title>申请合同分配</title>
    
<script type="text/javascript">

$(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${list.total}",
		    startRow:"${list.startRow}",
		    endRow:"${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '${pageContext.request.contextPath}/appraisalContract/selectDistribution.html?page='+e.curr;
		        }
		    }
		});
});

function distribution(id){
	layer.open({
		  type: 2, //page层
		  area: ['450px', '350px'],
		  title: '分配任务',
		  closeBtn: 1,
		  shade:0.01, //遮罩透明度
		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  shift: 1, //0-6的动画形式，-1不开启
		  offset: ['80px', '400px'],
		  shadeClose: false,
		  content: "${pageContext.request.contextPath}/appraisalContract/distributionUser.html?sbid="+id,
		  end:function(){
			  location.reload();
		  }
		});
}

function resetQuery(){
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
}
</script>    
    
  </head>
  
  <body>
    
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">申请合同审价</a></li></ul>
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
       <form id="form1" action="${pageContext.request.contextPath}/appraisalContract/serch.html" method="post" class="mb0">
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
	    	<input type="hidden" name="like" value="1">
	    	<button type="submit" class="btn">查询</button>
	    	<button type="button" class="btn" onclick="resetQuery()">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>
   </div>
</div>
	<%--<div class="container">	
		<div class="col-md-8 mt10 ml5">
	   		<button class="btn btn-windows ht_add" type="button" onclick="add()">添加合同</button>
		</div>
	</div>
	
	--%><div class="container margin-top-5">
	<div class="content padding-left-25 padding-right-25 padding-top-5">
		  <table class="table table-bordered table-striped table-hover">
		  	<thead>
	  			<tr>
	  				<th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	  				<th class="info">序号</th>
	  				<th class="info">合同名称</th>
	  				<th class="info">合同编号</th>
	  				<th class="info">合同金额(万元)</th>
	  				<th class="info">供应商名称</th>
	  				<th class="info">审价状态</th>
	  				<th class="info">操作</th>
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
	  				<c:if test="${contract.appraisal=='1' }">
	  					审价中
	  				</c:if>
	  				<c:if test="${contract.appraisal=='2' }">
	  					已审价
	  				</c:if>
	  				</td>
	  				<td class="tc">
	  				<c:if test="${contract.distribution=='0' }">
	  					<input type="button" value="分配任务" onclick="distribution('${contract.id }')" class="btn">
	  				</c:if>
	  				<c:if test="${contract.distribution=='1' }">
	  					已分配
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
