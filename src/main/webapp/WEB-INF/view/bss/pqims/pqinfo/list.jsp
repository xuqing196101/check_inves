<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看质检信息</title>
    
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script src="<%=basePath%>public/layer/layer.js"></script>
   <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
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
		        	if(("${pqinfo.contract.name}"!=null && "${pqinfo.contract.name}"!="") ||
		        		("${pqinfo.contract.code}"!=null && "${pqinfo.contract.code}"!="") ||
		        		("${pqinfo.type}"!="" && "${pqinfo.type}"!="-请选择-" && "${pqinfo.type}"!=null) ||
		        		("${pqinfo.conclusion}"!="-请选择-" &&"${pqinfo.conclusion}"!="" &&"${pqinfo.conclusion}"!=null  )){
		        		location.href = '<%=basePath%>pqinfo/search.do?page='+e.curr+'&contract.name='+"${pqinfo.contract.name}"+'&contract.code='+ "${pqinfo.contract.code}"+'&type='+ "${pqinfo.type}"+'&conclusion='+ "${pqinfo.conclusion}";
		        	}else{
		            	location.href = '<%=basePath%>pqinfo/getAll.do?page='+e.curr;
		        	}
		        }
		    }
		});
  })
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
  	function view(id){
  		window.location.href="<%=basePath%>pqinfo/view.html?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>pqinfo/edit.html?id="+id;
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
				window.location.href="<%=basePath%>pqinfo/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的质检报告",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="<%=basePath%>pqinfo/add.html";
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
	$(function(){
		if("${pqinfo.type}"!=null&&"${pqinfo.type}"!=""){
			$("#searchType").val('${pqinfo.type}');			
		}else{
			$("#searchType").val('-请选择-');	
		}
		if("${pqinfo.conclusion}"!=null&&"${pqinfo.conclusion}"!=""){
			$("#searchConclusion").val('${pqinfo.conclusion}');			
		}else{
			$("#searchConclusion").val('-请选择-');	
		}
		$("#contractName").val('${pqinfo.contract.name}');
		$("#contractCode").val('${pqinfo.contract.code}');
	});
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">产品质量结果登记  </a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>质量结果查询</h2>
	   </div>
   </div>
   
   <!-- 查询 -->
   
   <div class="container clear margin-top-0">
   <div class="padding-10 border1 m0_30 tc">
   	<form action="<%=basePath %>pqinfo/search.html" method="post" enctype="multipart/form-data" class="mb0" >
	 <ul class="demand_list">
	   <li class="fl mr15"><label class="fl mt5">合同名称：</label><span><input type="text" name="contract.name" id="contractName" class="mb0" /></span></li>
	   <li class="fl mr15"><label class="fl mt5">合同编号：</label><span><input type="text" name="contract.code" id="contractCode" class="mb0" /></span></li>
	   <li class="fl mr15"><label class="fl mt5">验收类型：</label>
	   		<span>
	   			<select id="searchType" name =type class="w150" >
					<option value="-请选择-">-请选择-</option>
			  	  	<option value="首件检验">首件检验</option>
			  	 	<option value="生产验收">生产验收</option>
			  	 	<option value="出厂验收">出厂验收</option>
			  	 	<option value="到货验收">到货验收</option>
	  			</select>
	  		</span>
	  </li>
	   <li class="fl mr15"><label class="fl mt5">质检结论：</label>
	   		<span>
	   			<select id="searchConclusion" name =conclusion class="w150" >
					<option value="-请选择-">-请选择-</option>
			  	  	<option value="合格">合格</option>
			  	 	<option value="不合格">不合格</option>
	  			</select>
	   		</span>
	   </li>
	   	 <button class="btn fl ml5 mt1" type="submit">查询</button>
	   	 <button type="reset" class="btn ml5 mt1">重置</button> 
	 </ul>

	 <div class="clear"></div>
	 </form>
   </div>
  </div>
   
<!-- 表格开始-->
   <div class="container">
   		<div class="headline-v2 fl">
      		<h2>质检情况列表</h2>
  		</div> 
    </div>
    <div class="container">
   		<div class="col-md-8">
    		<button class="btn btn-windows add" type="button" onclick="add()">登记质检报告</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
		</div>        
   </div>
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
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
				
				<td class="tc opinter"><input onclick="check()" type="checkbox" name="chkItem" value="${PqInfo.id}" /></td>
				
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${PqInfo.contract.name}</td>
				
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${PqInfo.contract.code}</td>
			
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${PqInfo.contract.supplierDepName}</td>
			
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${PqInfo.type}</td>
				
				<td class="tc opinter" onclick="view('${PqInfo.id}')">${PqInfo.conclusion}</td>
			
				<td class="tc opinter"><button type="button" onclick="showPic('${PqInfo.id}')">质检报告</button><img class="hide" id="${PqInfo.id}" src="${PqInfo.report}"/></td>
   				
			</tr>
		</c:forEach>
        </table>
     </div>
   	<div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
