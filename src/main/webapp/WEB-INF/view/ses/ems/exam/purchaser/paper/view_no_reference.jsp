<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看未考人员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#purchaser").hide();
			$("#errorPurchaser").hide();
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${paperUserList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${paperUserList.total}",
			    startRow: "${paperUserList.startRow}",
			    endRow: "${paperUserList.endRow}",
			    groups: "${paperUserList.pages}">=5?5:"${paperUserList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var id = "${id}";
			            location.href = "<%=path%>/purchaserExam/viewReference.do?id="+id+"&page="+e.curr;
			        }
			    }
			});
		})
		
		
		
		//Excel导入
		function poiExcel(){
			var paperId = $("#paperId").val();
			$.ajaxFileUpload({
			    url: "<%=path %>/purchaserExam/importReference.do?paperId="+paperId,  
			    secureuri: false,
			    fileElementId: "excelFile",
			    dataType: "text",
			    success: function(data) {  
			    	if(data.length<=5){
			    		layer.msg('添加成功',{offset: ['222px', '390px']});
			    		window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			    	}else{
			    		var array = data.split(";");
			    		if(array[array.length-1].indexOf("1")>-1){
			    			$("#errorNews").html("Excel表中有以下人员不是采购人");
			    			var html = "";
				    		for(var i=0;i<array.length-1;i++){
				            	  html = html + "<tr class='tc'>";
				            	  if(i==0){
				            		  html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
				            	  }else{
				            		  html = html + "<td>"+array[i].split(",")[0]+"</td>";
				            	  }
				            	  html = html + "<td>"+array[i].split(",")[2]+"</td>";
				            	  html = html + "<td>"+array[i].split(",")[1]+"</td>";
				            	  html = html + "</tr>";
				            }
				    		$("#errResult").html(html);
				    		layer.open({
							 	type: 1, //page层
								area: ['430px', '200px'],
								closeBtn: 1,
								shade:0.01, //遮罩透明度
								moveType: 1, //拖拽风格，0是默认，1是传统拖动
								shift: 1, //0-6的动画形式，-1不开启
								offset: ['120px', '550px'],
								shadeClose: false,
								content : $('#errorPurchaser')
							});
							$(".layui-layer-shade").remove();
			    		}else if(array[array.length-1].indexOf("2")>-1){
			    			$("#errorNews").html("Excel表中以下人员已被添加到本场考试中");
			    			var html = "";
				    		for(var i=0;i<array.length-1;i++){
				            	  html = html + "<tr class='tc'>";
				            	  if(i==0){
				            		  html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
				            	  }else{
				            		  html = html + "<td>"+array[i].split(",")[0]+"</td>";
				            	  }
				            	  html = html + "<td>"+array[i].split(",")[2]+"</td>";
				            	  html = html + "<td>"+array[i].split(",")[1]+"</td>";
				            	  html = html + "</tr>";
				            }
				    		$("#errResult").html(html);
				    		layer.open({
							 	type: 1, //page层
								area: ['430px', '200px'],
								closeBtn: 1,
								shade:0.01, //遮罩透明度
								moveType: 1, //拖拽风格，0是默认，1是传统拖动
								shift: 1, //0-6的动画形式，-1不开启
								offset: ['120px', '550px'],
								shadeClose: false,
								content : $('#errorPurchaser')
							});
							$(".layui-layer-shade").remove();
			    		}else if(array[array.length-1].indexOf("3")>-1){
			    			$("#errorNews").html("Excel表中以下人员考试时间有冲突");
			    			var html = "";
				    		for(var i=0;i<array.length-1;i++){
				            	  html = html + "<tr class='tc'>";
				            	  if(i==0){
				            		  html = html + "<td>"+array[i].split(",")[0].substring(1)+"</td>";
				            	  }else{
				            		  html = html + "<td>"+array[i].split(",")[0]+"</td>";
				            	  }
				            	  html = html + "<td>"+array[i].split(",")[2]+"</td>";
				            	  html = html + "<td>"+array[i].split(",")[1]+"</td>";
				            	  html = html + "</tr>";
				            }
				    		$("#errResult").html(html);
				    		layer.open({
							 	type: 1, //page层
								area: ['430px', '200px'],
								closeBtn: 1,
								shade:0.01, //遮罩透明度
								moveType: 1, //拖拽风格，0是默认，1是传统拖动
								shift: 1, //0-6的动画形式，-1不开启
								offset: ['120px', '550px'],
								shadeClose: false,
								content : $('#errorPurchaser')
							});
							$(".layui-layer-shade").remove();
			    		}
			    	}
			    }  
			}); 
		}
		
		//删除参考人员
		function deleteByPaperUserId(){
			var paperId = $("#paperId").val();
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请选择删除内容",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			for(var i=0;i < info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=path%>/purchaserExam/deleteByPaperUserId.do?ids="+ids,
			       	success:function(data){
			       		layer.msg('删除成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		//window.location.href="<%=path%>/purchaserExam/viewReference.html?id="+paperId;
				       		window.location.reload();
				       	}, 1000);
			       	}
		       	});
			});
		}
		
		//全选方法
		function selectAll(){
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			if(selectAll.checked){
				for(var i = 0;i<info.length;i++){
					info[i].checked = true;
				}
			}else{
				for(var i = 0;i<info.length;i++){
					info[i].checked = false;
				}
			}
		}
		
		//下载模板
		function download(){
			window.location.href = "<%=path%>/purchaserExam/loadReferenceTemplet.html";
		}
		
		//查询
		function query(){
			var userName = $("#userName").val();
			var card = $("#card").val();
			if(userName==""||userName==null){
				layer.alert("请输入采购人姓名",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			if(card==""||card==null){
				layer.alert("请输入身份证号",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/queryReferenceByCondition.do?userName="+userName+"&card="+card,
			    success:function(data){
			       	if(data==0){
			       		layer.alert("没有查到符合条件的采购人",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
						return;
			       	}else if(data==1){
			       		$.ajax({
							type:"POST",
							dataType:"json",
							url:"<%=path%>/purchaserExam/getReference.do?userName="+userName+"&card="+card,
						    success:function(data){
						    	if(data){
						    		var html = "";
						    		for(var i=0;i<data.length;i++){
						            	  html = html + "<tr class='tc'>";
						            	  html = html + "<td><input type='checkbox' name='purchaserInfo' value='\""+data[i].userId+"\"'/></td>";
						            	  html = html + "<td>"+data[i].relName+"</td>";
						            	  html = html + "<td>"+data[i].idCard+"</td>";
						            	  html = html + "<td>"+data[i].purchaseDepName+"</td>";
						            	  html = html + "</tr>";
						            }
						    		$("#refResult").html(html);
						    		layer.open({
									 	type: 1, //page层
										area: ['430px', '200px'],
										closeBtn: 1,
										shade:0.01, //遮罩透明度
										moveType: 1, //拖拽风格，0是默认，1是传统拖动
										shift: 1, //0-6的动画形式，-1不开启
										offset: ['120px', '550px'],
										shadeClose: false,
										content : $('#purchaser')
									});
									$(".layui-layer-shade").remove();
						    	}
						    }
					     })
			       	}	
			    }
		     });
			
		}
		
		//添加参考人员
		function add(){
			var userName = $("#userName").val();
			var card = $("#card").val();
			var paperId = $("#paperId").val();
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("purchaserInfo");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/addReferenceById.do?paperId="+paperId+"&relName="+userName+"&card="+card,
		       	success:function(data){
			       if(data==2){
			       		layer.alert("已经添加该采购人,请重新添加",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			       	}else if(data==1){
			       		layer.msg('添加成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			       	}else if(data==3){
			       		layer.alert("该考生考试时间有冲突,请重新添加",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			       	}
		       	}
	       	});
		}
		
		//取消方法
        function cancel(){
        	layer.closeAll();
        }
		
		function resetResult(){
			$("#userName").val("");
			$("#card").val("");
		}
	</script>

  </head>
  
  <body>
	  	<!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考卷管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	<div class="container">
		当前考卷编号:${examPaper.code }
	</div>   
  
  	<div class="container">
	   <div class="headline-v2">
	   		<h2>添加考试人员</h2>
	   </div>
   	</div>
  	
  	<div class="container">
    	<div class="col-md-12 padding-left-25">
	    	姓名:<input type="text" id="userName" name="userName" class="mt10 w80"/>
	    	身份证号:<input type="text" id="card" name="card" class="mt10 w230"/>
	    	<button class="btn btn-windows pl13" type="button" onclick="query()">查询</button>
	    	<button class="btn btn-windows pl13" type="button" onclick="resetResult()">重置</button>
	    	<button class="btn btn-windows delete" type="button" onclick="deleteByPaperUserId()">删除</button>
    	</div>
    </div>
    
    <div class="container">
	   <div class="headline-v2">
	   		<h2>参考人员列表</h2>
	   </div>
   	</div>
   	
    <!-- 表格开始 -->
    <div class="container">
	    	<div class="fr padding-right-25">
	    		<button class="btn" type="button" onclick="download()">人员模板下载</button>
	    		<span class="">
		    	  	<input type="file" name="file" id="excelFile" style="display:inline;"/>
		    	  	<input type="button" value="导入" class="btn btn-windows input" onclick="poiExcel()"/>
	    	  	</span>
	    	</div>
    
  		<div class="content padding-left-25 padding-right-25">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th class="info w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
						<th class="info w50">序号</th>
						<th class="info">姓名</th>
						<th class="info">身份证号</th>
						<th class="info">所属单位</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paperUserList.list }" var="paper" varStatus="vs">
						<tr>
							<td class="tc"><input type="checkbox" name="info" value="${paper.id }"/></td>
							<td class="tc">${(vs.index+1)+(paperUserList.pageNum-1)*(paperUserList.pageSize)}</td>
							<td class="tc">${paper.userName }</td>
							<td class="tc">${paper.card }</td>
							<td class="tc">${paper.unitName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right" class="mr10"></div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="mt20 clear tc">
	      <input class="btn btn-windows back" value="返回考卷列表" type="button" onclick="location.href='<%=path%>/purchaserExam/paperManage.html'">
	  	</div>
	  	
	  	
	  	<div class="content padding-left-25 padding-right-25" id="purchaser">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th>选择</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>所属单位</th>
					</tr>
				</thead>
				<tbody id="refResult">
				</tbody>
			</table>
			
			<button class="btn btn-windows" type="button" onclick="add()">添加</button>
			<button class="btn btn-windows" type="button" onclick="cancel()">取消</button>
		</div>
	  	
	  	<div class="content padding-left-25 padding-right-25" id="errorPurchaser">
	  		<div id="errorNews"></div>
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th>姓名</th>
						<th>身份证号</th>
						<th>所属单位</th>
					</tr>
				</thead>
				<tbody id="errResult">
				</tbody>
			</table>
		</div>
	  	
	  	<input type="hidden" value="${examPaper.id }" name="paperId" id="paperId"/>
  </body>
</html>
