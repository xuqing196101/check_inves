<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>专家添加考试人员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			$("#relName").val("${relName}");
			$("#idNumber").val("${idNumber}");
			var type_options = document.getElementById("userType").options;
			for(var i=0;i<type_options.length;i++){
				if($(type_options[i]).attr("value")=="${userType}"){
					type_options[i].selected=true;
				}
			}
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${expert.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${expert.total}",
			    startRow: "${expert.startRow}",
			    endRow: "${expert.endRow}",
			    groups: "${expert.pages}">=5?5:"${expert.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var ruleId = "${ruleId}";
			        	var relName = "${relName}";
			        	var idNumber = "${idNumber}";
			        	var userType = "${userType}";
			            location.href = "${pageContext.request.contextPath }/expertExam/userAdd.do?ruleId="+ruleId+"&relName="+relName+"&idNumber="+idNumber+"&userType="+userType+"&page="+e.curr;
			        }
			    }
			});
		})
		
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
		
		//重置结果
		function resetResult(){
			$("#relName").val("");
			$("#idNumber").val("");
			var userType = document.getElementById("userType").options;
			userType[0].selected = true;
		}
	
		//检查全选
		function check(){
			var count = 0;
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == false){
					selectAll.checked = false;
					break;
				}
			}
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == info.length){
				selectAll.checked = true;
			}
		}
		
		//查询
		function query(){
			var ruleId = $("#ruleId").val();
			var relName = $("#relName").val();
			var idNumber = $("#idNumber").val();
			var userType = $("#userType").val();
			if((relName==""||relName==null)&&(idNumber==""||idNumber==null)&&(userType==""||userType==null)){
				window.location.href = "${pageContext.request.contextPath }/expertExam/userAdd.html?ruleId="+ruleId;
				return;
			}else{
				window.location.href = "${pageContext.request.contextPath }/expertExam/userAdd.do?ruleId="+ruleId+"&relName="+relName+"&idNumber="+idNumber+"&userType="+userType;
			}
		}
		
		//添加人员
		function add(){
			var ruleId = $("#ruleId").val();
			var count = 0;
			var id = "";
			var info = document.getElementsByName("info");
			for(var i = 0;i<info.length;i++){
				if(info[i].checked == true){
					count++;
				}
			}
			if(count == 0){
				layer.alert("请选择一项",{offset: ['30%', '40%']});
				$(".layui-layer-shade").remove();
				return;
			}
			for(var i=0;i < info.length;i++){    
		        if(info[i].checked){    
		        	id += info[i].value+',';
		        }
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/expertExam/checkExpertInfo.do?id="+id,
			    success:function(data){
			    	if(data){
			    		var html = "";
			    		for(var i=0;i<data.length;i++){
			            	  html = html + "<tr class='tc'>";
			            	  html = html + "<td>"+(i+1)+"</td>";
			            	  html = html + "<td>"+data[i].relName+"</td>";
			            	  html = html + "<td>"+data[i].idNumber+"</td>";
			            	  if(data[i].expertsTypeId==1){
			            		  html = html + "<td>技术</td>";
			            	  }else if(data[i].expertsTypeId==2){
			            		  html = html + "<td>法律</td>";
			            	  }else if(data[i].expertsTypeId==3){
			            		  html = html + "<td>商务</td>";
			            	  }
			            	  html = html + "</tr>";
			            }
			    		$("#refResult").html(html);
			    		layer.open({
							  type: 1,
							  title: '信息',
							  skin: 'layui-layer-rim',
							  shadeClose: true,
							  area: ['580px','410px'],
							  content: $("#expert")
						});
						$(".layui-layer-shade").remove();
			    	}
			    }
		     })
		}
		
		//审核确定
		function sure(){
			var ruleId = $("#ruleId").val();
			var count = 0;
			var id = "";
			var info = document.getElementsByName("info");
			for(var i=0;i<info.length;i++){    
		        if(info[i].checked){    
		        	id += info[i].value+',';
		        }
			}
			$.ajax({
				type:"POST",
				dataType:"text",
				url:"${pageContext.request.contextPath }/expertExam/addUserById.do?ruleId="+ruleId+"&id="+id,
		       	success:function(data){
		       		if(data.length<=5){
			    		layer.msg('添加成功',{offset: ['40%', '45%']});
			    		window.setTimeout(function(){
				       		window.location.reload();
				       	}, 1000);
			    	}else{
			    		var array = data.split(";");
			    		$("#errorNews").html("以下人员已经被添加到本场考试中");
			    		var html = "";
				    	for(var i=0;i<array.length-1;i++){
				            html = html + "<tr class='tc'>";
				            html = html + "<td>"+(i+1)+"</td>";
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
							type: 1,
							title: '错误信息',
						 	skin: 'layui-layer-rim',
							shadeClose: true,
							area: ['580px','410px'],
							content: $("#errorExpert")
						});
						$(".layui-layer-shade").remove();
		       		}
		       	}
	       	});
		}
		
		//返回
		function back(){
			var ruleId = $("#ruleId").val();
			window.location.href="${pageContext.request.contextPath }/expertExam/viewReference.html?id="+ruleId;
		}
		
		//取消方法
        function cancel(){
        	layer.closeAll();
        }
	</script>

  </head>
  
  <body>
   		<!--面包屑导航开始-->
	   	<div class="margin-top-10 breadcrumbs">
	      	<div class="container">
			   	<ul class="breadcrumb margin-left-0">
			   	<li><a href="javascript:void(0);">首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">专家考试规则管理</a></li>
			   	</ul>
				<div class="clear"></div>
		  	</div>
	   	</div>
	   	
		<div class="container mt20">
		  	<div class="ml20 mt10 tc f18 b">
	  		考试年份：${examRule.year }
	  	  	</div>
  		</div>
  		
  	<div class="container">
  		<h2 class="search_detail">
			<ul class="demand_list">
		    	<li>
			    	<label class="fl">姓名：</label>
			    	<span>
			    		<input type="text" id="relName" name="relName"/>
			    	</span>
			    </li>
			    <li>
			    	<label class="fl">证件号：</label>
			    	<span>
				    	<input type="text" id="idNumber" name="idNumber"/>
			   		</span>
			     </li>
			     <li>
			    	<label class="fl">专家类型：</label>
			    	<span>
				    	<select id="userType" class="w80">
				  			<option value="">请选择</option>
				  			<option value="1">技术</option>
				  			<option value="2">法律</option>
				  			<option value="3">商务</option>
				  		</select>
			   		</span>
			     </li>
			    <button class="btn" type="button" onclick="query()">查询</button>
	    		<button class="btn" type="button" onclick="resetResult()">重置</button>
		    </ul>
		    <div class="clear"></div>
	 	</h2>
	 	
	 	<!-- 按钮开始 -->
		<div class="col-md-12 pl20 mt10">
			<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
	    </div>
	 	
	 	<div class="content table_box">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
						<th class="w50">序号</th>
						<th>姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${expert.list }" var="user" varStatus="vs">
						<tr class="tc">
							<td><input type="checkbox" name="info" value="${user.id }" onclick="check()"/></td>
							<td>${(vs.index+1)+(expert.pageNum-1)*(expert.pageSize)}</td>
							<td>${user.relName }</td>
							<td>${user.idNumber }</td>
							<c:if test="${user.expertsTypeId==1 }">
								<td>技术</td>
							</c:if>
							<c:if test="${user.expertsTypeId==2 }">
								<td>法律</td>
							</c:if>
							<c:if test="${user.expertsTypeId==3 }">
								<td>商务</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right" class="mr10"></div>
	 	
	 </div>
	 
	 	<!-- 返回按钮 -->
  		<div class="mt20 clear tc">
	      	<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
	  	</div>
	  	
	  	<div class="dnone layui-layer-wrap col-md-12" id="expert">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w60">姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
					</tr>
				</thead>
				<tbody id="refResult">
				</tbody>
			</table>
			<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
				<button class="btn btn-windows add" type="button" onclick="sure()">确定</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div>
		</div>
	  	
	  	<div class="dnone layui-layer-wrap col-md-12" id="errorExpert">
	  		<div id="errorNews" class="col-md-12 tc red mb5 f18"></div>
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w60">姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
					</tr>
				</thead>
				<tbody id="errResult">
				</tbody>
			</table>
		</div>
	  	
	  	<input type="hidden" value="${examRule.id }" name="ruleId" id="ruleId"/>
  </body>
</html>
