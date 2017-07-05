<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

<script type="text/javascript">
  $(function() {
		laypage({
		 	cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages: "${list.pages}", //总页数
			skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip: true, //是否开启跳页
			total: "${list.total}",
			startRow: "${list.startRow}",
			endRow: "${list.endRow}",
			groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			    var page = location.search.match(/page=(\d+)/);
			    return page ? page[1] : 1;
			}(), 
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					$("input[name='page']").val(e.curr);
					searchAfterSaleSerlist(0);
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
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/after_sale_ser/show.html?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/after_sale_ser/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的售后服务",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/after_sale_ser/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的售后服务",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="${pageContext.request.contextPath}/after_sale_ser/add.html";
    }
    function showPic(url,name){
    	var pic = $("#"+url.toString());
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '516px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: pic
			});
	};
  </script>

</head>

<body>
	<div class="wrapper">
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
            <a href="javascript:void(0);"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0);">售后服务采购管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0);">售后服务登记</a>
          </li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
        <h2>售后服务登记列表</h2>
      </div>
      <c:if test="${'1' ne type}">
      <h2 class="search_detail ">
      <form id="form" action="${pageContext.request.contextPath}/after_sale_ser/list.html" method="post" class="mb0" > 
      <ul class="demand_list">
        <li class="fl">
        <label class="fl">产品名称：</label><span><input class="span2" name="product.name" id="productName"  type="text"></span>
        </li>
        <li class="fl">
        <label class="fl">合同名称：</label><span><input class="span2" name="contract.name" id="contractName"  type="text"></span>
        </li>
        <li class="fl">
        <label class="fl">合同编号：</label><span><input class="span2" name="contract.code" id="contractCode" type="text"></span>
        </li>
	   	 <button class="btn fl" type="submit">查询</button>
	   	 <button type="reset" class="btn fl">重置</button> 
	 </ul>
        <div class="clear"></div>
       </form>
     </h2>
     </c:if>
      <div class="content table_box">
		  <div class="col-md-12 pl20 mt10">
		  <c:choose>
          <c:when test="${'1' eq type}">
            <button class="btn btn-windows back" type="button" onclick="window.history.go(-1)">返回</button>
          </c:when>
          <c:otherwise>
            <button class="btn btn-windows add" type="button" onclick="add()">登记</button>
            <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
            <button class="btn btn-windows delete" type="button" onclick="del();">删除</button> 
          </c:otherwise>
        </c:choose>
   </div>

		 <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">合同编号</th>
              				<th class="info">产品名称</th>
              				<th class="info">技术参数</th>
             				<th class="info">合同金额（万元）</th>
						</tr>
					</thead>
					<tbody id="black_tbody_id">
						<c:forEach items="${list.list}" var="AfterSaleSer" varStatus="vs">
						<tr>
				
							<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${AfterSaleSer.id}" /></td>
				
							<td class="tc pointer" onclick="show('${AfterSaleSer.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
							<td class="tl pl20 pointer" onclick="show('${AfterSaleSer.id}')">${AfterSaleSer.contractCode}</td>
				
							<td class="tl pl20 pointer" onclick="show('${AfterSaleSer.id}')">${AfterSaleSer.requiredId}</td>
			
							<td class="tl pl20 pointer" onclick="show('${AfterSaleSer.id}')">${AfterSaleSer.technicalParameters}</td>
			
							<td class="tc pointer" onclick="show('${AfterSaleSer.id}')">${AfterSaleSer.money}</td>
				
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
	
	<form id="edit_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html" method="post">
		<input name="supplierBlacklistId" type="hidden" />
	</form>
</body>
</html>
