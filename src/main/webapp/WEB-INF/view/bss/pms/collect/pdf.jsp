<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	


<title>版块管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">


<jsp:include page="/WEB-INF/view/common.jsp"/> 

<script type="text/javascript">
 	//跳转到增加页面
    function add(){
    	window.location.href="${pageContext.request.contextPath}/purchaser/add.html";
    }
    
    
	//鼠标移动显示全部内容
	 var index;
		function chakan(){
			index=layer.open({
				  type: 1, //page层
				  area: ['600px', '500px'],
				  title: '编制说明',
				  closeBtn: 1,
				  shade:0.01, //遮罩透明度
				  moveType: 1, //拖拽风格，0是默认，1是传统拖动
				  shift: 1, //0-6的动画形式，-1不开启
				  offset: ['80px', '400px'],
				  content: $('#content'),
				});
	}
	
	function closeLayer(){
		layer.close(index);	
	}
	//上传excel文件
	function upload(){
		  var name=$("#jhmc").val();
	 		 var no=$("#jhbh").val();
	 		 
	 		 
 		var type=$("#goodsType").val();
 		
 		if(name==""){
 			layer.tips("计划名称不允许为空","#jhmc");
 		 } else if(no==""){
 			layer.tips("计划编号不允许为空","#jhbh");
 		 }else{
 			 
 		 
		            $.ajaxFileUpload (
		                {
		                    url: '${pageContext.request.contextPath}/purchaser/upload.do?type='+type+"&&planName="+name+"&&planNo="+no, //用于文件上传的服务器端请求地址
		                    secureuri: false, //一般设置为false
		                    fileElementId: 'fileName', //文件上传空间的id属性  <input type="file" id="file" name="file" />
		                  
		                 	 dataType: "text", //返回值类型 一般设置为json
		                    success: function (data)  //服务器成功响应处理函数
		                    { 
		                    	alert(data);
		                    	if(data=="ERROR"){
		                    		 alert("文件名错误");
		                    	}else if(data=="exception"){
		                    		alert("格式出错");
		                    	}
		                    	
		                    	else{
		                    		alert("上传成功");
		                    	}
		                   
		                    },
		                    error: function (data, status, e)//服务器响应失败处理函数
		                    {
		                    	  alert("上传失败");
		                    }
		                }
		            );
		            
 		 }
 		
		  	}    
	  
	  
	  function adds(){
		  $("#add_div").show();
	  }
	  function hide(){
		  $("#add_div").hide();
	  }
	  
 	 //动态添加
  	  function aadd(){
		  var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var tr = $("input[name=dyadds]").parent().parent();
	        $(tr).before("<tr><td class='tc'><input style='border: 0px;' type='text' name='list["+s+"].seq' /></td>"+
		       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+  
	        +"<tr/>");
	  }
 	 function incr(){
 		 
 		
 	  var name=$("#jhmc").val();
 		 var no=$("#jhbh").val();
 		 
 		 if(name==""){
 			layer.tips("计划名称不允许为空","#jhmc");
 		 } else if(no==""){
 			layer.tips("计划编号不允许为空","#jhbh");
 		 } 
 		 
 		 else{
 			 $("#fjhmc").val(name);
 	 		$("#fjhbh").val(no);
 	 		
 			 $("#add_form").find("*").each(function(){ 
 	  			var elem = $(this); 
 	  			if (elem.prop("name") != null&&elem.prop("name") != "") { 
 	  			if(elem.val()==""){ 
 	  			elem.removeAttr("name"); 
 	  			} 
 	  			} 
 	  			}); 
 			 
 		 
 		 $.ajax({
 			 url:"${pageContext.request.contextPath}/purchaser/adddetail.html",
 			 type:"post",
 			 data:$("#add_form").serialize(),
 			 success:function(){
 				 alert("添加成功");
 				 window.location.href="${pageContext.request.contextPath}/purchaser/list.html";
 			 },error:function(){
 				 
 			 }
 		 });
 		//  $("#add_form").submit();   
 		 
 		 }   
 		
 			 
 		 
	}
 	 
 	 function down(){
 		 window.location.href="${pageContext.request.contextPath}/purchaser/download.html?filename=模板.xlsx";
 	 }
 	 
 	 function delets(){
 		  var tr = $("input[name=delt]").parent().parent();
 	        $(tr).prev().remove();   
 	 }
 	var datas;
	var treeObj;
	$(function() {
		
		   
		   var setting={
					async:{
								autoParam:["id"],
								enable:true,
								url:"${pageContext.request.contextPath}/category/createtree.do",
								dataType:"json",
								type:"post",
							},
							callback:{
						    	onClick:zTreeOnClick,//点击节点触发的事件
						    	 //beforeRemove: zTreeBeforeRemove,
						    	//beforeRename: zTreeBeforeRename, 
								//onRemove: zTreeOnRemove,
			       			    //onRename: zTreeOnRename,
						    }, 
							data:{
								simpleData:{
									enable:true,
									idKey:"id",
									pIdKey:"pId",
									rootPId:0,
								}
						    },
						   check:{
							    chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
				        		chkStyle:"checkbox", 
								enable: true
						   }
			  };
		      //控制树的显示和隐藏
			  var expertsTypeId = $("#expertsTypeId").val();
				 if(expertsTypeId==1 || expertsTypeId=="1"){
				 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").show();
				 }else{
					 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").hide();
				 }
	});
	
	function typeShow(){
	/* 	 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){ */
			 $("#ztree").show();
		 	$("#bt").show();
		/*  }else{
			 $("#ztree").hide();
		 } */
		
	}
	var treeid=null;
    /*树点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		
    }
    function typehide(){
    	 $("#ztree").hide();
    	 $("#bt").hide();
    }
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:void(0);">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>


	<div class="container clear margin-top-30">
	<span> 计划名称:${plan.fileName }</span>
		<span>计划编号:${plan.planNo }</span>
		 
		
		<!-- <div class="padding-10 border1">
		 
			 <label class="fl">采购需求导入（Excel表格）：</label><input  style="float: left;" type="file" id="fileName" name="file" />   
			 
		 
			<button style="float: left;"  class="btn padding-right-10 btn_back" onclick="upload()">导入</button><span style="margin-left: 200px"></span>
				<button class="btn padding-left-10 padding-right-10 btn_back" onclick="down()">下载Excel模板</button>
				<button class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button>
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="chakan()">查看编制说明</button> 
 
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="adds()">录入</button>
			 
			
		</div> -->
	</div>
	<div class="container clear margin-top-30" id="add_div">
		<form id="add_form" action="${pageContext.request.contextPath}/purchaser/adddetail.html" method="post">
			<input type="hidden" name="planName" id="fjhmc">
			<input type="hidden" name="planNo" id="fjhbh">
			<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
						<th class="info w50"><h2> 序号</h2> </th>
						<th class="info"><h2>  评审内容 </h2></th>
		 
					</tr>
				</thead>
                <tbody>
                <c:forEach items="${details }" var="d">
				<tr>
					<td class="tc w50">${d.seq }  </td>
					<td class="tc w50">
				
						<textarea style="width: 650px;height: 100px;border: 0px" readonly="readonly">
							<c:if test="${d.oneAdvice!=null&&d.oneAdvice!='' }">第一轮审核意见是：${d.oneAdvice}  </c:if>
							<c:if test="${d.twoAdvice!=null&&d.twoAdvice!='' }">第二轮审核意见是：${d.twoAdvice}  </c:if>
							<c:if test="${d.threeAdvice!=null&&d.threeAdvice!='' }">第三轮审核意见是：${d.threeAdvice}  </c:if>
						</textarea>
					
					</td>
	 	 
				</tr>
				</c:forEach>
				<!-- <tr>
					<td class="tc w50">2 </td>
					<td class="tc w50"><textarea style="width: 650px;height: 100px;border: 0px"></textarea></td>
		 
				</tr>
				<tr>
					<td class="tc w50"> 3</td>
					<td class="tc w50"><textarea style="width: 650px;height: 100px;border: 0px"></textarea> </td>
				 
				</tr>
				<tr>
					<td class="tc w50"> 4</td>
					<td class="tc w50"><textarea style="width: 650px;height: 100px;border: 0px"></textarea> </td>
		 
				</tr>
				<tr>
					<td class="tc w50">5 </td>
					<td class="tc w50"><textarea style="width: 650px;height: 100px;border: 0px"></textarea> </td>
 
				</tr>
				<tr>
					<td class="tc w50">6</td>
					<td class="tc w50"><textarea style="width: 650px;height: 100px;border: 0px"></textarea>  </td>
				</tr> -->
			<%-- 	<tr>

					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${type }">
					 <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加">
					 <input class="btn btn-windows delete" name="delt" type="button" onclick="delets()" value="删除"></td>
				</tr> --%>
				</tbody>
			</table>
			<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
			  <input class="btn btn-windows save" type="button" onclick="window.print()" value="下载打印"> 
			  <button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
			</div>
		</form>
	</div>

 

 
</body>
</html>
