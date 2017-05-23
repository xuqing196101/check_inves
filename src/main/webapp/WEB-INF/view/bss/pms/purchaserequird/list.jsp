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
		        	$("#page").val(e.curr);
		        	  $("#param_form").submit();
		        	
		       <%--  location.href = '${pageContext.request.contextPath}/purchaser/list.do?page='+e.curr; --%>
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
  		
  		window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no+"&&type=2";
  	}
  	
    function edit(){
    	var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();
    	
    	
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(status.trim()=="已提交"){
			layer.alert("已提交，不允许修改",{offset: ['222px', '390px'], shade:0.01});
		}
		else if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+id+"&&type=3";;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    //删除
    function del(){
    	var id =[];
    	var flag=true;
    	var count=0
    	var n=[];
			$('input[name="chkItem"]:checked').each(function(i){ 
				id.push($(this).val()); 
				var status = $(this).parents("tr").find("td").eq(6).text();
				if(status.trim()=="已提交"){
					count=i+1;
					n.push(count);
					flag=false; 
				}
			});
			
			if(flag==false){
				layer.alert("选中的第"+n+"个已提交，不允许删除",{offset: ['222px', '390px'], shade:0.01});
			}
			else  if(id.length>0){
				layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				 	$.ajax({
		 			 	url:"${pageContext.request.contextPath}/purchaser/delete.html?planNo="+id,
		 			 	type:"post",
		 			 	success:function(){
		 					 layer.msg('删除成功', { offset: ['40%', '45%'] });
		 					$("#param_form").submit();
						/* 	window.setTimeout(function() { */
								//window.location.reload();
						/* 	}, 500); */
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
			
    	window.location.href="${pageContext.request.contextPath}/purchaser/add.html";
    	
   
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
		var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();
		var manages="${manages}";
		status = $.trim(status);
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(manages<1){
			layer.alert("没有关联管理部门",{offset: ['222px', '390px'], shade:0.01});
		}
		else if(id.length==1&&manages>0){
			if(status == "未提交"||status == "受理退回"){
		    	layer.open({
					type : 2, //page层
					area : [ '550px', '500px' ],
					title : "管理部门",
					shade : 0.01, //遮罩透明度
					moveType : 1, //拖拽风格，0是默认，1是传统拖动
					shift : 1, //0-6的动画形式，-1不开启
					shadeClose : true,
					content : "${pageContext.request.contextPath}/purchaser/submit.html?planNo="+id
				 });
			   //  window.location.href="${pageContext.request.contextPath}/purchaser/submit.html?planNo="+id;
			}else{
			     layer.alert("已提交",{offset: ['322px', '790px'], shade:0.01});
			}
			
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
		   <li><a href="javascript:void(0);">首页</a></li><li><a href="javascript:void(0);">保障作业系统</a></li><li><a href="javascript:void(0);">采购计划管理</a></li><li class="active"><a href="javascript:void(0);">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
		<!-- 录入采购计划开始-->
 		<div class="container">
		  <div class="headline-v2">
			  <h2>采购需求列表</h2>
		  </div>

		     <h2 class="search_detail">
		       	<form id="param_form" action="${pageContext.request.contextPath }/purchaser/list.html"  method="post" class="mb0">
		       	<input type="hidden" name="page" id="page">
			    	<ul class="demand_list">
			    	  <%-- <li>
				    		<label class="fl">需求部门：</label><span>
				  	  	<input type="text" name="department" value="${inf.department }" />
				    		</span>
				      </li> --%>
				   		<li>
				    		<label class="fl">采购需求名称：</label><span>
				  	   	<input type="text" name="planName" value="${inf.planName }" />
				    	</span>
				      </li>
				      <li>
				    		<label class="fl">采购需求文号：</label><span>
				  	   	<input  type="text" name="referenceNo" value="${inf.referenceNo }" /> 
				    	</span>
				      </li>
				      <li>
				    		<label class="fl">需求填报日期：</label><span>
				  	  	<input style="width: 120px;" class="span2 Wdate w220"  value='<fmt:formatDate value="${inf.createdAt }"/>' name="createdAt" type="text" onclick='WdatePicker()'> 
				    		</span>
				      </li>
				    	
				    <li>
					 <label class="fl">状态：</label>
			              <select  name="status" id="status">
			                <option selected="selected" value="total">全部</option>
			                <option value="1" <c:if test="${'1'==inf.status}">selected="selected"</c:if>>未提交</option>
			                <option value="4" <c:if test="${'4'==inf.status}">selected="selected"</c:if>>受理退回</option>
			                <option value="5" <c:if test="${'5'==inf.status}">selected="selected"</c:if>>已提交 </option>
			              </select>
			          </li>
          
          
            
			    	</ul>
			    	<div class="col-md-12 clear tc mt10">
			    	  <input class="btn" type="submit" value="查询" /> 
				      <input class="btn" type="button" value="重置" onclick="resetQuery()" />	
			    	</div>
		    	  <div class="clear"></div>
		        </form>
		     </h2>
	   	  
   	  <div class="col-md-12 pl20 mt10">
	    	<button class="btn btn-windows add" onclick="add()">需求录入</button>
	    	<button class="btn btn-windows edit"  onclick="edit()">修改</button>
				<button class="btn btn-windows input" onclick="exports()">下载</button>
	   		<button class="btn btn-windows delete" onclick="del()">删除</button>
				<button class="btn btn-windows git" onclick="sub()">提交采购管理部门</button>
	  	</div>
	  	<div class="clear"></div>
   	<div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped" >
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info" width="40%">需求名称</th>
		  <th class="info" width="18%" >采购需求文号</th>
		  <th class="info" width="10%">金额（万元）</th>
		  <th class="info" width="15%">编制时间</th>
	<!-- 	  <th class="info">完成时间</th> -->
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30">
			<%--   <c:if test="${obj.status=='1' || obj.status=='4'  }"> --%>
                 <input type="checkbox" value="${obj.uniqueId }" name="chkItem" onclick="check()">
            <%--   </c:if> --%>
			  </td>
			  <td class="tc w50" onclick="view('${obj.uniqueId }')" >${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td class="tl" onclick="view('${obj.uniqueId }')">
			  	${obj.planName }
				</td>
		      <td class="tl" onclick="view('${obj.uniqueId }')">
		      ${obj.referenceNo }
			  <%-- <td class="pl20">
			     <div onclick="view('${obj.planNo }')">${obj.planName }</div>
			  </td> --%>
			 <%--    <c:forEach items="${requires }" var="re" >
					  <c:if test="${obj.department==re.id }"> ${re.name }</c:if>
			  	</c:forEach> --%>
			  </td>  
			  <td class="tr"><div onclick="view('${obj.uniqueId }')"> <fmt:formatNumber type="number"  pattern="#,##0.00"  value="${obj.budget}" /></div></td>
			  <td class="tc" onclick="view('${obj.uniqueId }')"><fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd"/></td>
			 <%--  <td class="tc" onclick="view('${obj.uniqueId }')">${obj.deliverDate } </td> --%>
			  <td class="tc" onclick="view('${obj.uniqueId }')">
				 <c:if test="${obj.status=='1' }">
			 		未提交
			  	</c:if>
			  	
  				<c:if test="${obj.status=='4' }">
			 		受理退回
			  	</c:if> 
			  	
		<%-- 	    <c:if test="${obj.status =='2' }">
			 		已提交
			  	</c:if> --%>
		 
			   <c:if test="${obj.status =='2' || obj.status =='3' || obj.status=='5' }">
			 		已提交
			  	</c:if>
			  

			</tr>
		 	</c:forEach>
      </table>
      <div id="pagediv" align="right"></div>
   </div>
 </div>

 
	 </body>
</html>
