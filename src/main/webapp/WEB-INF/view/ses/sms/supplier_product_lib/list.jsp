<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
<%@ include file="../../../common.jsp"%>
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
	      		location.href = "${pageContext.request.contextPath }/obrule/ruleList.do?name=${name}&&quoteTime=${quoteTime}&&intervalWorkday=${intervalWorkday}&&page=" + e.curr;
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
    	window.location.href="${pageContext.request.contextPath}/product_lib/addSMSProductUI.html";
	}
	
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/pqinfo/view.html?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/pqinfo/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的质检报告",{offset: ['222px', '390px'], shade:0.01});
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
				window.location.href="${pageContext.request.contextPath}/pqinfo/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的质检报告",{offset: ['222px', '390px'], shade:0.01});
		}
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
	   		<h2>产品列表</h2>
	   </div>

   
   <!-- 查询 -->

     <div class="search_detail">
   	 <form action="${pageContext.request.contextPath}/pqinfo/search.html" method="post" enctype="multipart/form-data" class="mb0" >
	 <ul class="demand_list">
	   <li><label class="fl">合同名称：</label><span><input type="text" name="contract.name" id="contractName" class="mb0" /></span></li>
	   <li><label class="fl">合同编号：</label><span><input type="text" name="contract.code" id="contractCode" class="mb0" /></span></li>
	   <li><label class="fl">验收类型：</label>
	   		<span>
	   			<select id="searchType" name =type class="w100" >
					<option value="-请选择-">-请选择-</option>
			  	  	<option value="首件检验">首件检验</option>
			  	 	<option value="生产验收">生产验收</option>
			  	 	<option value="出厂验收">出厂验收</option>
			  	 	<option value="到货验收">到货验收</option>
	  			</select>
	  		</span>
	  </li>
	  <li><label class="fl">质检结论：</label>
	   		<span>
	   			<select id="searchConclusion" name =conclusion class="w80" >
					<option value="-请选择-">-请选择-</option>
			  	  	<option value="合格">合格</option>
			  	 	<option value="不合格">不合格</option>
	  			</select>
	   		</span>
	   </li>
	   	 <button class="btn fl" type="submit">查询</button>
	   	 <button type="reset" class="btn fl">重置</button> 
	 </ul>

	 </form>
	 <div class="clear"></div>
    </div>

   
<!-- 表格开始-->

    <div class="col-md-12 pl20 mt10">
    		<button class="btn btn-windows add" type="button" onclick="add()">录入</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>      
   </div>
   <div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info">合同名称</th>
		  <th class="info">合同编号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">验收类型</th>
		  <th class="info">质检结论</th>
		  <th class="info">查看</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="PqInfo" varStatus="vs">
			<tr>
				
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${PqInfo.id}" /></td>
				
				<td class="tc pointer" onclick="show('${PqInfo.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
				<td class="tl pl20 pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.name}</td>
				
				<td class="tl pl20 pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.code}</td>
			
				<td class="tl pl20 pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.supplier.supplierName}</td>
			
				<td class="tc pointer" onclick="show('${PqInfo.id}')">${PqInfo.type}</td>
				
				<td class="tc pointer" onclick="show('${PqInfo.id}')">${PqInfo.conclusion}</td>
			
				<td class="tc pointer">
				<button type="button" onclick="openViewDIv('${PqInfo.id}','${PqInfo.report}','2','artice_show','this')" class="btn">质检报告</button>
				</td>
   				
			</tr>
		</c:forEach>
        </table>
     </div>
   <div id="pagediv" align="right"></div>
  </div>
  </body>
</html>
