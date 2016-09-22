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
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		//添加参考人员
		function add(){
			var userName = $("#userName").val();
			var card = $("#card").val();
			var paperId = $("#paperId").val();
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
				url:"<%=path%>/purchaserExam/addReferenceById.do?userName="+userName+"&paperId="+paperId+"&card="+card,
		       	success:function(data){
			       	if(data==0){
			       		layer.alert("没有该采购人",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			       	}else if(data==2){
			       		layer.alert("已经添加该采购人,请重新添加",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			       	}else if(data==1){
			       		layer.msg('添加成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		//window.location.href="<%=path%>/purchaserExam/viewReference.html?id="+paperId;
				       		window.location.reload();
				       	}, 1000);
			       	}else if(data==3){
			       		layer.alert("该考生考试时间有冲突,请重新添加",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			       	}
		       	}
	       	});
		}
		
		//Excel导入
		function poiExcel(){
			var paperId = $("#paperId").val();
			$.ajaxFileUpload({
			    url: "<%=path %>/purchaserExam/importReference.do?paperId="+paperId,  
			    secureuri: false,
			    fileElementId: "excelFile",
			    dataType: "json",
			    success: function(data) {  
			    	if(data==0){
			    		layer.alert("Excel表中有人员不是采购人,请仔细检查",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==1){
			    		layer.msg('导入成功',{offset: ['222px', '390px']});
				       	window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			    	}else if(data==2){
			    		layer.alert("Excel表中有人员已经在该考卷中,请仔细检查",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==3){
			    		layer.alert("Excel表中有人员考试时间冲突,请仔细检查",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
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
	</script>

  </head>
  
  <body>
  	<div class="container mt10">
  		<div class="col-md-12 mb10 border1 bggrey">
  			<div class="fl f18 gary b">参考人员信息</div>
  		</div>
  	</div>
  	
  	<div class="container">
    	<div class="border1 col-md-12">
	    	姓名:<input type="text" id="userName" name="userName" class="mt10 w80"/>
	    	身份证号:<input type="text" id="card" name="card" class="mt10 w230"/>
	    	<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
	    	<input type="file" name="file" id="excelFile" style="display:inline;"/>
	    	<button class="btn btn-windows pl13" type="button" onclick="poiExcel()">Excel导入</button>
	  		<button class="btn btn-windows delete" type="button" onclick="deleteByPaperUserId()">删除</button>
    	</div>
    </div>
    
    <!-- 表格开始 -->
    <div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w100">选择</th>
						<th class="info w100">序号</th>
						<th class="info w100">姓名</th>
					    <th class="info w100">试卷编号</th>
						<th class="info w100">所属单位</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paperUserList }" var="paper" varStatus="p">
						<tr>
							<td class="tc pointer"><input type="checkbox" name="info" value="${paper.id }"/></td>
							<td class="tc pointer">${p.index+1 }</td>
							<td class="tc pointer">${paper.userName }</td>
							<td class="tc pointer">${paper.code }</td>
							<td class="tc pointer">${paper.unitName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
	    			<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
	  	
	  	<input type="hidden" value="${examPaper.id }" name="paperId" id="paperId"/>
  </body>
</html>
