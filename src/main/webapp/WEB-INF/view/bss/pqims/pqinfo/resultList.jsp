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
		        	if((${pqinfo.contract.name!=null} && ${pqinfo.contract.name!=""}) ||
		        		(${pqinfo.contract.code!=null} && ${pqinfo.contract.code!=""}) ||
		        		(${pqinfo.type!=""} && ${pqinfo.type!="-请选择-"} && ${pqinfo.type!=null}) ||
		        		(${pqinfo.conclusion!="-请选择-"} && ${pqinfo.conclusion!=""} && ${pqinfo.conclusion!=null}  )){
			        		location.href = '${pageContext.request.contextPath}/pqinfo/searchReasult.do?page='+e.curr+'&contract.name='+"${pqinfo.contract.name}"+'&contract.code='+ "${pqinfo.contract.code}"+'&type='+ "${pqinfo.type}"+'&conclusion='+ "${pqinfo.conclusion}";
			        	}else{
			            	location.href = '${pageContext.request.contextPath}/pqinfo/getAllReasult.do?page='+e.curr;
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
	
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/pqinfo/view.html?id="+id;
  	}
    

	$(function(){
		if(${pqinfo.type!=null}&&${pqinfo.type!=""}){
			$("#searchType").val('${pqinfo.type}');			
		}else{
			$("#searchType").val('-请选择-');	
		}
		if(${pqinfo.conclusion!=null}&&${pqinfo.conclusion!=""}){
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
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">产品质量管理</a></li><li class="active"><a href="javascript:void(0)">产品质量结果查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>质量结果查询</h2>
	   </div>
   <!-- 查询 -->

     <div class="search_detail"">
   	<form action="${pageContext.request.contextPath}/pqinfo/searchReasult.html" method="post" enctype="multipart/form-data" class="mb0">
	 <ul class="demand_list">
	   <li class="fl"><label class="fl">合同名称：</label><span><input type="text" id="contractName" name="contract.name" class="mb0"/></span></li>
	   <li class="fl"><label class="fl">合同编号：</label><span><input type="text" id="contractCode" name="contract.code" class="mb0"/></span></li>
	   <li class="fl"><label class="fl">验收类型：</label>
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
	   <li class="fl mr15"><label class="fl mt5">质检结论：</label>
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

     <div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info" width="18%">合同名称</th>
		  <th class="info" width="11%">合同编号</th>
		  <th class="info" width="15%">采购机构</th>
		  <th class="info" width="15%">供应商名称</th>
		  <th class="info" width="8%">验收类型</th>
		  <th class="info" width="10%">质检日期</th>
		  <th class="info" width="7%">质检结论</th>
		  <th class="info">查看</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="PqInfo" varStatus="vs">
			<tr>
				
				<td class="tc" onclick="show('${PqInfo.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
				<td class="tl pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.name}</td>
				
				<td class="tl pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.code}</td>
			
				<td class="tl pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.purchaseDepName}</td>
			
				<td class="tl pointer" onclick="show('${PqInfo.id}')">${PqInfo.contract.supplier.supplierName}</td>
			
				<td class="tc pointer" onclick="show('${PqInfo.id}')">${PqInfo.type}</td>
				
				<td class="tc pointer" onclick="show('${PqInfo.id}')"><fmt:formatDate value='${PqInfo.date}' pattern='yyyy-MM-dd'/></td>
				
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
