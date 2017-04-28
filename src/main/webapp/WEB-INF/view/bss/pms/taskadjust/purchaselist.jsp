<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
		        //	$("#page").val(e.curr);
		        	// $("#form1").submit();
		        	
		         location.href = '${pageContext.request.contextPath}/purchaser/list.do?page='+e.curr;
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
  		var cid=$("#collectId").val();
  		window.location.href="${pageContext.request.contextPath}/adjust/detail.html?planNo="+no+"&&id="+cid;
  	}
  	
    function edit(){
    	var cid=$("#collectId").val();
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${pageContext.request.contextPath}/adjust/detail.html?planNo="+id+"&&id="+cid;
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
		 			 url:"${pageContext.request.contextPath}/purchaser/delete.html",
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
    	
    	index=layer.open({
			  type: 1, //page层
			  area: ['300px', '200px'],
			  title: '',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '600px'],
			  content: $('#content'),
			});
    	
   
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
		
		window.location.href="${pageContext.request.contextPath}/purchaser/add.html?type="+val;
		layer.close(index);	
	}
	
	function exports(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/purchaser/exports.html?planNo="+id+"&&type=2";
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
			
			window.location.href="${pageContext.request.contextPath}/purchaser/submit.html?planNo="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}
		
	}
	function goback(){
		window.location.href="${pageContext.request.contextPath}/adjust/list.html";
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
  <!--  <div class="headline-v2">
      <h2>查询条件</h2>
   </div> -->
<!-- 项目戳开始 -->
	<%--   <div class="border1 col-md-12 ml30">
	    <form id="add_form" action="${pageContext.request.contextPath}/purchaser/list.html" method="post" >
	   
	
		  
		   需求部门： <input class="mt10" type="text" name="department" value="${inf.department }" /> 
		   采购需求名称： <input class="mt10" type="text" name="planName" value="${inf.planName }"/> 
		   采购需求编号： <input class="mt10" type="text" name="planNo" value="${inf.planNo }" /> 
		    需求填报日期：  <input class="mt10" class="span2 Wdate w220"  value='<fmt:formatDate value="${inf.createdAt }"/>' name="createdAt" type="text" onclick='WdatePicker()'> 
		   	 <input class="btn-u"   type="submit" name="" value="查询" /> 
		 
	
		
	 
	   </form>
	  </div> --%>
   <div class="headline-v2 fl">
      <h2>采购需求列表
	  </h2>
   </div> 
   	  <div class="col-md-12 pl20 mt10">
<!-- 	    <button class="btn padding-left-10 padding-right-10 btn_back" onclick="add()">采购需求录入</button> -->
	    <button class="btn btn-windows edit"  onclick="edit()">修改</button>
	    <div class="clear"></div>
	    <input type="button"  class="btn btn-windows back" value="返回"  onclick="goback()"> 
	<!-- 	<button class="btn padding-left-10 padding-right-10 btn_back" onclick="exports()">下载</button>
	    <button class="btn padding-left-10 padding-right-10 btn_back" onclick="del()">删除</button>
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sub()">提交</button> -->
	  </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">采购需求名称</th>
		  <th class="info">金额</th>
		  <th class="info">编制时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30"><input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""></td>
			  <td class="tc w50" onclick="view('${obj.planNo }')" >${vs.index}</td>
			  <td class="tl pl20" onclick="view('${obj.planNo }')">${obj.planName }</td>
			  <td class="tr pr20" onclick="view('${obj.planNo }')">${obj.budget }</td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tl pl20" onclick="view('${obj.planNo }')">
			  <c:if test="${obj.status=='1' }">
			 	 已编制为采购计划
			  </c:if>
			   <c:if test="${obj.status=='2' }">
			 	已提交
			  </c:if>
			  <c:if test="${obj.status=='3' }">
			 	提交受理
			  </c:if>
			    <c:if test="${obj.status=='4' }">
			 	已受理
			  </c:if>
			  
			  <c:if test="${obj.status=='5' }">
			 	已汇总
			  </c:if>
			  
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      

   </div>
 </div>
	<input id="collectId" value="${id}" type="hidden">

 
	 </body>
</html>
