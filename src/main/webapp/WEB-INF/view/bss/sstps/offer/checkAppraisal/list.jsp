<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
  <head>
    <%@ include file="../../../../common.jsp"%>
    <title>审价人员审价</title>
    
<script type="text/javascript">

$(function(){
	$("#name").val('${name }');
	$("#code").val('${code }');
	$("#supplierName").val('${supplierName }');
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
		            location.href = '${pageContext.request.contextPath}/offer/checkList.html?page='+e.curr;
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
		window.location.href="${pageContext.request.contextPath}/offer/userSelectProductCheck.html?contractId="+id;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择需要审核的合同",{offset: ['222px', '390px'], shade:0.01});
	}
}

</script>    
    
  </head>
  
  <body>
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 保障作业</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 单一来源审价</a>
			  </li>
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
			  </li>
		  </ul>
		<div class="clear"></div>
	  </div>
   </div>
    
    <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   		<!-- 查询 -->
		<div class="search_detail">
		<form action="${pageContext.request.contextPath}/offer/userSearchCheck.html" method="post" enctype="multipart/form-data" class="mb0">
    <div class="m_row_5">
    <div class="row">
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同名称：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="name" id="name" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同编号：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="code" id="code" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="supplierName" id="supplierName" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-12 f0">
            <button class="btn mb0 h32" type="submit">查询</button>
  					<button type="reset" class="btn mb0 mr0 h32">重置</button>
          </div>
        </div>
      </div>
    </div>
    </div>
		</form>
		</div>
		<!-- 表格开始-->
		<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">复审</button>
				<button class="btn btn-windows edit" type="button">导出</button>
		</div>
		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
					<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()"/></th>
					<th class="info w50">序号</th>
					<th class="info" width="30%">合同名称</th>
					<th class="info" width="17%">合同编号</th>
					<th class="info" width="12%">合同金额(万元)</th>
					<th class="info" width="20%">供应商名称</th>
					<th class="info">签订状态</th>
				</tr>
				</thead>
				<c:forEach items="${list.list}" var="contract" varStatus="vs">

					<tr>
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${contract.id }"/>
						</td>
						<td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td class="tl pointer">${contract.name }</td>
						<td class="tl pointer">${contract.code }</td>
						<td class="tr pointer">${contract.money }</td>
						<td class="tl pointer">${contract.supplierName }</td>
						<td class="tc pointer">
							<c:if test="${contract.appraisal=='1' }">
								审价中
							</c:if>
							<c:if test="${contract.appraisal=='2' }">
								审价完成
							</c:if>
						</td>
					</tr>

				</c:forEach>
			</table>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
  </body>
</html>
