<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>确定中标供应商</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">

  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName("chkItem");
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
		
		layer.open({
			  type: 2,
			  title: '上传',
			  shadeClose: true,
			  shade: 0.01,
			  area: ['367px', '180px'], //宽高
			  content: '<%=basePath%>winningSupplier/upload.html'
			});
		
// 		 var count=0;
// 		 var checklist = document.getElementsByName ("chkItem");
// 		 var checkAll = document.getElementById("checkAll");
// 		 for(var i=0;i<checklist.length;i++){
// 			   if(checklist[i].checked == false){
// 				   checkAll.checked = false;
// 				   break;
// 			   }
// 			   for(var j=0;j<checklist.length;j++){
// 					 if(checklist[j].checked == true){
// 						   checkAll.checked = true;
// 						   count++;
// 					   }
// 				 }
// 		   }
	}
  	function view(id){
  		window.location.href="<%=basePath%>winningSupplier/template.do?id="+id;
  	}
    function save(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			 $.post("<%=basePath%>resultAnnouncement/view.do?id="+id,{email:$('#email').val(),address:$('#address').val()},
					  function(data){
					    var tem=data;
					    var ue = parent.UE.getEditor('editor'); 
					    ue.ready(function(){
					        //需要ready后执行，否则可能报错
					        ue.setContent(tem.content);
					        ue.setHeight(500);
					    });
					    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    parent.layer.close(index);
					  },
					  "json");
					
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择模板",{offset: ['222px', '390px'], shade:0.01});
		}
    }

   
</script>
<body>
	<!--面包屑导航开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>确定中标供应商</h2>
		</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
		<div class="padding-left-25 padding-right-25"></div>
	</div>
	<div class="container">
		<div class="p10_25">
			<h2 class="padding-10 border1">
				<form action="" method="post" class="mb0">
					<ul class="demand_list">
					  <li class="fl">
                              <label class="fl">合同名称：</label><span><input type="text" id="topic" class=""/></span>
                         </li>
					</ul>
					<div class="clear"></div>
				</form>
			</h2>
		</div>
	</div>
	<div class="container">

		<div class="content padding-left-25 padding-right-25 padding-top-0">
			<div class="col-md-12">
				<span>第一包</span>    <button type="button" onclick="view()" class="btn">确定</button>
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w30">选择</th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">参加时间</th>
							<th class="info">标书状态</th>
							<th class="info">总报价（万元）</th>
							<th class="info">总得分</th>
							<th class="info">排名</th>
						</tr>
					</thead>
					<c:forEach items="${list}" var="list" varStatus="vs">
						<tr>
							<td class="tc opinter"><input onclick="check()"
								type="checkbox" name="chkItem" value="${list.address}" /></td>

							<td class="tc opinter" onclick="">${(vs.index+1)}</td>

							<td class="tc opinter" onclick="">${list.address}</td>

							<td class="tc opinter" onclick="">${list.address}</td>

							<td class="tc opinter" onclick="">${list.address}</td>

							<td class="tc opinter" onclick="">${list.address}</td>
							
							<td class="tc opinter" onclick="">${list.address}</td>
							 <td class="tc opinter" onclick="">${list.address}</td>
							<%-- 							<td class="tc opinter" onclick="view('${templet.id}')"><fmt:formatDate --%>
							<%-- 									value='${templet.updatedAt}' pattern="yyyy-MM-dd " /></td> --%>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
</body>
</html>
