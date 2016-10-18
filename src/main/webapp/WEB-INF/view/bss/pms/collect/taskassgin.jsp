<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>采购需求管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	


<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
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
		        	
		         location.href = '<%=basePath%>purchaser/list.do?page='+e.curr;
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
 
	var index;
	function audit(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length>=1){  
			
			index=layer.open({
				  type: 1, //page层
				  area: ['500px', '300px'],
				  title: '采购任务下达',
				  closeBtn: 1,
				  shade:0.01, //遮罩透明度
				  moveType: 1, //拖拽风格，0是默认，1是传统拖动
				  shift: 1, //0-6的动画形式，-1不开启
				  offset: ['80px', '600px'],
				  content: $('#content'),
				});
			
			
	  	}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}  
	}
 
	

	function closeLayer(){
	     var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
	  	$("#cid").val(id);
	    $("#collect_form").submit();
		  layer.close(index);	
	}
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">障碍作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
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
  <div class="border1 col-md-12 ml30">
    <form id="add_form" action="<%=basePath%>taskassgin/list.html" method="post" >
  

		 
		  计划名称： <input type="text" class="mt10" name="fileName" value="${inf.fileName }"/> 
		   计划编号： <input type="text" class="mt10"name="" value="${inf.planNo }"/>
		   计划类型： <input type="text" class="mt10" name="planNo" value="${inf.goodsType }"/> 
	   	 <input class="btn padding-left-10 padding-right-10 btn_back"   type="submit" name="" value="查询" />
	 
   </form>
  </div>
   <div class="headline-v2 fl">
      <h2>采购计划列表
	  </h2>
   </div> 
    <span class="fr option_btn margin-top-10">
		 
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="audit()">下达</button>
 
	  </span>
   <div class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">采购计划名称</th>
		  <th class="info">预算总金额</th>
		  <th class="info">汇总时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30">
			  <c:if test="${obj.status=='2' }">
			  <input type="hidden" value="${obj.id }" disabled="disabled" name="chkItem" onclick="check()"  alt="">
			  </c:if>
			  <c:if test="${obj.status=='1' }">
			   <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt="">
			  </c:if>
			  </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  
			  <td class="tc"  >${obj.fileName }</td>
			
			
			  <td class="tc"  ><fmt:formatNumber>${obj.budget }</fmt:formatNumber> </td>
			    <td class="tc"  ><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tc"  >
			  <c:if test="${obj.status=='1' }">
			   初始化
			  </c:if>
			    <c:if test="${obj.status=='2' }">
			   已下达
			  </c:if>
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>

 <div id="content" class="div_show">
	 
	<form id="collect_form" action="http://localhost:6666/zhbj/taskassgin/add.html" method="post" style="margin-top: 20px;">
	
	  	   <div style="text-align: center;"><span>采购任务名称:</span><input  type="text" name="name" value=""></div>
	       <div  style="text-align: center;margin-top: 20px;"><span>采购任务编号:</span><input  type="text" name="documentNumber" value=""></div>
	       
	       
		<!--  文件名称：<input type="text" name="fileName" value=""><br>
		 密码:<input type="password" name="password" value=""><br> -->
		 <div  style="text-align: center;margin-top: 20px;"><span>采购机构:</span>	<select name="purchaseId">
							<c:forEach items="${org }" var="obj">
							<option value="${obj.id }">${obj.name }</option>
							</c:forEach>
							</select></div>
		 
		 <input type="hidden" name="cid" id="cid" value="">
		
	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 180px;" onclick="closeLayer()" >确认</button>
	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 30px" onclick="cancels()" >取消</button>
	   
	 </form>   
 </div>
 

 
 
	 </body>
</html>
