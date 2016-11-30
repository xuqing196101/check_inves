<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/WEB-INF/view/common.jsp"/> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>采购需求管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
 
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
 

 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${info.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		        	  $("#add_form").submit();
		        	
		       <%--  location.href = '<%=basePath%>purchaser/list.do?page='+e.curr; --%>
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
	
  	function view(no){
  		
  		window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+no+"&&type=1";
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+id+"&&type=2";;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if(id.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				 $.ajax({
		 			 url:"<%=basePath%>purchaser/delete.html",
		 			 type:"post",
		 			 data:{
		 				 planNo:$('input[name="chkItem"]:checked').val()
		 				 },
		 			 success:function(){
		 				window.location.reload();
		 				 
		 			 },error:function(){
		 				 
		 			 }
		 		 });
			});
		}else{
			layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    var index;
    function add(){
    	
    	/* index=layer.open({
			  type: 1, //page层
			  area: ['300px', '200px'],
			  title: '',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '600px'],
			  content: $('#content'),
			}); */
			
    	window.location.href="<%=basePath%>purchaser/add.html";
    	
   
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length>10){
	layer.msg(content, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
	
	function closeLayer(){
		var val=$("input[name='goods']:checked").val();
		
		window.location.href="<%=basePath%>purchaser/add.html?type="+val;
		layer.close(index);	
	}
	
	function exports(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>purchaser/exports.html?planNo="+id+"&&type=2";
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}
		
	}
	
	function sub(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>purchaser/submit.html?planNo="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}
		
	}
	
	function resetQuery(){
		$("#param_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">

   
		  <div class="headline-v2">
			  <h2>需求计划列表</h2>
		  </div>

		     <h2 class="search_detail">
		       	<form id="param_form" action="${pageContext.request.contextPath }/purchaser/list.html"  method="post" class="mb0">
		       	<input type="hidden" name="page" id="page">
			    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl">需求部门：</label><span>
				  	   <input  type="text" name="department" value="${inf.department }" /> 
				    	
				    	</span>
				      </li>
				   <li>
				    	<label class="fl">需求计划名称：</label><span>
				  	   <input type="text" name="planName" value="${inf.planName }" /> 
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl">需求计划编号：</label><span>
				  	   <input  type="text" name="planNo" value="${inf.planNo }" /> 
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl">需求填报日期：</label><span>
				  	  <input style="width: 120px;" class="span2 Wdate w220"  value='<fmt:formatDate value="${inf.createdAt }"/>' name="createdAt" type="text" onclick='WdatePicker()'> 

				    	
				    	</span>
				      </li>
				    	  
			    	</ul>
			    	<div class="col-md-12 clear tc mt10">
			    	<input class="btn"   type="submit" name="" value="查询" /> 
				      <input type="button" onclick="resetQuery()" class="btn" value="重置"/>	
			    	</div>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
	   	  
   	  <div class="col-md-12 pl20 mt10">
	    <button class="btn btn-windows add" onclick="add()">需求计划录入</button>
	    <button class="btn btn-windows edit"  onclick="edit()">修改</button>
		<button class="btn btn-windows output" onclick="exports()">下载</button>
	    <button class="btn btn-windows delete" onclick="del()">删除</button>
		<button class="btn btn-windows git" onclick="sub()">提交</button>
	  </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">计划名称</th>
		  <th class="info">编制单位名称</th>
		  <th class="info">金额</th>
		  <th class="info">编制时间</th>
		  <th class="info">完成时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""></td>
			  <td class="tc w50" onclick="view('${obj.planNo }')" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.planName }</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.department }</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.budget }</td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.auditDate }"/> </td>
			  <td class="tc" onclick="view('${obj.planNo }')">
				 <c:if test="${obj.status=='1' }">
			 	 已编制为采购计划
			  </c:if>
			  
			     <c:if test="${obj.status=='2' }">
			 	已提交
			  </c:if>
			  <c:if test="${obj.status=='3' }">
			 	受理退回
			  </c:if>
			    <c:if test="${obj.status=='4' }">
			 	已受理
			  </c:if>
			   <c:if test="${obj.status=='5' }">
			 	已汇总
			  </c:if>
			   <c:if test="${obj.status=='6' }">
			 	审核通过
			  </c:if>
			   <c:if test="${obj.status=='7' }">
			 	审核暂存
			  </c:if>
			  
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>


<!--  <div id="content" class="div_show">
	 <p align="center" class="type">
	         请选择类别
	<br>
	
	 <input type="radio" name="goods" value="1">:货物<br>
	 <input type="radio" name="goods" value="2">:工程<br>
	 <input type="radio" name="goods" value="3">:服务<br>
	    </p>
	     <button class="btn padding-left-10 padding-right-10 btn_back goods"  onclick="closeLayer()" >确定</button>
	    
 </div> -->
 
	 </body>
</html>
