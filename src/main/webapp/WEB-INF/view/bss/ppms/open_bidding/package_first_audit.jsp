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
    <base href="<%=basePath%>">
    
    <title>My JSP 'bid_file.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
    <script type="text/javascript">
	function submit1(){
		
		<%-- $.ajax({
			url:"<%=basePath %>firstAudit/add.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				window.location.reload();
			},
			error:function(){
				layer.msg("添加失败",{offset: ['222px', '390px']});
			}
		}); --%>
		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="kind"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择类型", "#kind");
			return ;
		}
		
		var creater = $("#creater").val();
		if(!creater){
			layer.tips("请填写名称", "#creater");
			return ;
		}
		$("#form1").submit();
		//$("#form1").reset();
	}
	 var index;
	function cancel(){
	   layer.close(index);
	}
	function openWindow(){
		index = layer.open({
	          type: 1, //page层
	          area: ['700px', '200px'],
	          title: '手动添加初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	function remove(id){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"<%=basePath%>firstAudit/remove.html?id="+id,
	 				//data:{"id":id},
	 				//type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 500);
	 	       		},
	 	       		error: function(){
	 					layer.msg("删除失败",{offset: ['222px', '390px']});
	 				}
	 	       	});
	 		});
		
	}
	function edit(id){
		  layer.open({
	          type: 2, //page层
	          area: ['700px', '200px'],
	          title: '修改初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          closeBtn: 1,
	          content:'<%=basePath %>firstAudit/toEdit.html?id='+id
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	//打开模板窗口列表
	function openTemplat(){
		layer.open({
	          type: 2, //page层
	          area: ['700px', '400px'],
	          title: '选择模板',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '90px'],
	          closeBtn: 1,
	          content:'<%=basePath %>firstAudit/toTemplatList.html'
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
		
	}
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
</script>
  </head>
  
  <body>
	                     <div class="col-md-12 p0">
						   <ul class="flow_step">
						     <li >
							   <a  href="<%=basePath%>firstAudit/toAdd.html?projectId=${projectId}" >01、符合性</a>
							   <i></i>
							 </li>
							 
							 <li class="active">
							   <a  href="<%=basePath%>firstAudit/toPackageFirstAudit.html?projectId=${projectId}" >02、符合性关联</a>
							   <i></i>							  
							 </li>
						     <li>
							   <a  href="#tab-3" data-toggle="tab">03、评标细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a  href="#tab-4" data-toggle="tab">04、招标文件</a>
							 </li>
						   </ul>
						 </div>
						 <div class="tab-content clear step_cont">
						 <!--第一个  -->
						 <!--第二个 -->
						 <div class=class="col-md-12 tab-pane active"  id="tab-1">
						 	<h1 class="f16 count_flow"><i>02</i>关联初审项</h1>
						 	   <div class="container clear margin-top-30" id="package">
							   <h5>01、项目分包信息</h5>
								   <c:forEach items="${packageList }" var="pack" varStatus="p">
								   		<span>包名:<span>${pack.name }</span>
								   		<input type="hidden" value="${pack.id }"/>
								   		</span>
								   		
								   		<table class="table table-bordered table-condensed mt5">
							        	<thead>
							        		<tr class="info">
							          			<th class="w50">序号</th>
							         			<th>需求部门</th>
										        <th>物资名称</th>
										        <th>规格型号</th>
										        <th>质量技术标准</th>
										        <th>计量单位</th>
										        <th>采购数量</th>
										        <th>单价（元）</th>
										        <th>预算金额（万元）</th>
										        <th>交货期限</th>
										        <th>采购方式建议</th>
										        <th>供应商名称</th>
										        <th>是否申请办理免税</th>
											    <th>物资用途（进口）</th>
											    <th>使用单位（进口）</th>
							        		</tr>
							        	</thead>
							          <c:forEach items="${pack.projectDetails}" var="obj">
							            <tr class="tc">
								            <td class="w50">${obj.serialNumber }</td>
								            <td>${obj.department}</td>
								            <td>${obj.goodsName}</td>
								            <td>${obj.stand}</td>
								            <td>${obj.qualitStand}</td>
								            <td>${obj.item}</td>
								            <td>${obj.purchaseCount}</td>
								            <td>${obj.price}</td>
								            <td>${obj.budget}</td>
								            <td>${obj.deliverDate}</td>
								            <td>${obj.purchaseType}</td>
								            <td>${obj.supplier}</td>
								            <td>${obj.isFreeTax}</td>
									        <td>${obj.goodsUse}</td>
									        <td>${obj.useUnit}</td>
							            </tr>
							         </c:forEach> 
							      </table>
								   </c:forEach>
							   </div> 
							<div class="container clear margin-top-30" id="package">
						 	 <table class="table table-bordered table-condensed mt5">
						 	  <h5>02、项目初审项信息</h5>
							    <thead>
							      <tr>
							      	<th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
							        <th>初审项名称</th>
							        <th>要求类型</th>
							        <th>创建人</th>
							      </tr>
							     </thead>
							      <c:forEach items="${list }" var="l" varStatus="vs">
								      <thead>
								       <tr>
								        <td class="tc w30"><input type="checkbox" value="${l.id }" name="chkItem" onclick="check()"  alt=""></td>
								        <td align="center">${l.name }</td>
								        <td align="center">${l.kind }</td>
								        <td align="center">${l.creater }</td>
								      </tr>
								      </thead>
						      	  </c:forEach>
						    </table>
						 </div>	
						</div>
                      </div>
  </body>
</html>
