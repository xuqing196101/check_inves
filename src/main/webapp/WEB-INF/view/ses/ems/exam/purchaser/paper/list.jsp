<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>历史考卷管理</title>
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
		//新增考卷
		function addPaper(){
			window.location.href="<%=path %>/purchaserExam/addPaper.html";
		}
		
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${paperList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${paperList.total}",
			    startRow: "${paperList.startRow}",
			    endRow: "${paperList.endRow}",
			    groups: "${paperList.pages}">=5?5:"${paperList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = "<%=path%>/purchaserExam/paperManage.do?page="+e.curr;
			        }
			    }
			});	
			
		})

		//编辑考卷
		function editPaper() {
			var count = 0;
			var ids = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请先选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			if(count > 1){
				layer.alert("只能修改一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			for(var i=0;i<info.length;i++){    
		        if(info[i].checked){    
		        	ids += info[i].value+',';
		        }
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/editSelectedPaper.html?id="+ids,
		       	success:function(data){
			    	if(data==1){
			    		layer.msg('当前考卷正在考试期间,请勿编辑',{offset: ['222px', '390px']});
			    	}else if(data==2){
			    		window.location.href="<%=path%>/purchaserExam/editPaper.html?id="+ids;
			    	}else if(data==3){
			    		layer.msg('当前考卷已经过了考试有效期,不好编辑',{offset: ['222px', '390px']});
			    	}
		       	}
		    });
		}
		
		//查看考卷
		function view(obj){
			window.location.href = "<%=path%>/purchaserExam/viewPaper.html?id="+obj;
		}
		
		//设置参考人员
		function setReference(){
			var count = 0;
			var info = document.getElementsByName("info");
			var str = "";
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count > 1){
				layer.alert("只能选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else if(count == 0){
				layer.alert("请先选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else{
				for(var i = 0;i<info.length;i++){
					if(info[i].checked == true){
						str = info[i].value;
					}
				}
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=path%>/purchaserExam/setReference.do?id="+str,
			       	success:function(data){
				    	if(data==1){
				    		layer.alert("当前考卷正在考试中,请选择其它考卷",{offset: ['222px', '390px']});
							$(".layui-layer-shade").remove();
				    	}else if(data==2){
				    		window.location.href = "<%=path%>/purchaserExam/viewReference.do?id="+str;
				    	}else if(data==3){
				    		layer.alert("当前考卷考试时间已结束,请选择其它考卷",{offset: ['222px', '390px']});
							$(".layui-layer-shade").remove();
				    	}
			       	}
			    });
				
			}
		}
		
		//查看成绩
		function viewScore(){
			var count = 0;
			var info = document.getElementsByName("info");
			var str = "";
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count > 1){
				layer.alert("只能选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else if(count == 0){
				layer.alert("请先选择一项",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}else{
				for(var i = 0;i<info.length;i++){
					if(info[i].checked == true){
						str = info[i].value;
					}
				}
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=path%>/purchaserExam/setReference.do?id="+str,
			       	success:function(data){
				    	if(data==1){
				    		layer.alert("当前考卷正在考试中,请选择其它考卷",{offset: ['222px', '390px']});
							$(".layui-layer-shade").remove();
				    	}else if(data==2){
				    		layer.alert("当前考卷考试时间未结束,请选择其它考卷",{offset: ['222px', '390px']});
							$(".layui-layer-shade").remove();
				    	}else if(data==3){
				    		window.location.href = "<%=path%>/purchaserExam/viewReference.do?id="+str;
				    	}
			       	}
			    });
			}
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
	   <div class="headline-v2">
	   		<h2>历史考卷列表</h2>
	   </div>
   </div>
   
   
    <div class="container">
   		<div class="col-md-10">
	    	<input type="button" class="btn btn-windows add" value="新建考卷" onclick="addPaper()"/>
	    	<input type="button" class="btn btn-windows edit" value="编辑" onclick="editPaper()"/>
	    	<input type="button" class="btn btn-windows pl13" value="设置参考人员" onclick="setReference()"/>
    		<input type="button" class="btn btn-windows pl13" value="查看成绩" onclick="viewScore()"/>
    	</div>
    </div>
    
   <div class="container margin-top-5" id="div_print">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
	   		<table class="table table-bordered table-condensed table-hover">
		    	<thead>
		    		<th class="info">选择</th>
		    		<th class="info">序号</th>
		    		<th class="info">考卷名称</th>
		    		<th class="info">考卷年度</th>
		    		<th class="info">考试日期</th>
		    		<th class="info">考卷状态</th>
		    	</thead>
		    	<tbody>
		    		<c:forEach items="${paperList.list }" var="paper" varStatus="vs">
		    			<tr>
		    				<td class="tc"><input type="checkbox" name="info" value="${paper.id }"/></td>
		    				<td class="tc pointer" onclick="view('${paper.id }')">${(vs.index+1)+(paperList.pageNum-1)*(paperList.pageSize)}</td>
		    				<td class="tc pointer" onclick="view('${paper.id }')">${paper.name }</td>
		    				<td class="tc pointer" onclick="view('${paper.id }')">${paper.year }</td>
		    				<td class="tc pointer" onclick="view('${paper.id }')">${paper.startTrueDate }</td>
		    				<td class="tc pointer" onclick="view('${paper.id }')">${paper.status }</td>
		    			</tr>
		    		</c:forEach>
		    	</tbody>
		    </table>
   		</div>
   		<div id="pageDiv" align="right"></div>
    </div>
  </body>
</html>
