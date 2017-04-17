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
		         	$("#page").val(e.curr);
		          $("#add_form").submit();
		        	
		          
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
	/* 	var no=generateMixed();
	 $("#aduit_No").val(no); */
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
		var td=	 $('input[name="chkItem"]:checked').parent();
		var val=$(td).next().next().text();
		  $("#aduit_Name").val(val);
		  $("#aduit_Name").attr("title",val);
		}
		if(id.length==1){  
			var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
			if($.trim(status)=="未下达"){
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
				 layer.alert("请选择未下达的计划", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
			}
			
	  	}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}  
	}
 
	

	function closeLayer(){
		var no=$("#aduit_No").val(); 
		var name=$("#aduit_Name").val();
	     var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if($.trim(name)==""){
			layer.alert("请填写采购任务名称！", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		} else if($.trim(no)==""){
			layer.alert("请填写采购任务文号！", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}else{
		  	$("#cid").val(id);
		    $("#collect_form").submit();
			  layer.close(index);  	
		}
	  
	}
	function resetQuery(){
		$("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
	function view(id) {
        window.location.href = "${pageContext.request.contextPath }/look/view.html?id="+id;
  }
	
	function generateMixed() {
		var myDate = new Date(); 
		var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	     var res = "";
	     for(var i = 0; i < 5 ; i ++) {
	         var id = Math.ceil(Math.random()*35);
	         res += chars[id];
	     }
	     var year1=myDate.getFullYear(); //获取完整的年份(4位,1970-????) 
	     return "JH-"+year1+"-"+res;
	}

	
	function cancel(){
		/* alert("cs");
 		var index = parent.layer.getFrameIndex(window.name); 
 		parent. */
 		layer.closeAll();
 	}
	
	
	function down() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			window.location.href = "${pageContext.request.contextPath }/look/excel.html?uniqueId=" + id;
		} else if(id.length > 1) {
			layer.alert("只能选择一个", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		} else {
			layer.alert("请选中一条", {
				offset: ['222px', '390px'],
				shade: 0.01
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
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
  <div class="headline-v2 fl">
      <h2>采购计划列表
	  </h2>
   </div> 
   
   <h2 class="search_detail">
    <form id="add_form" action="${pageContext.request.contextPath }/taskassgin/list.html" method="post" >
  
	<input type="hidden" name="page" id="page">
		 
		  <ul class="demand_list">
			    	  <li>
				    	<label class="fl"> 计划名称：</label><span>
				  	 <input type="text"   name="fileName" value="${inf.fileName }"/>  
				    	
				    	</span>
				      </li>
				   <%-- <li>
				    	<label class="fl"> 计划编号：</label><span>
				  	   <input type="text"  name="" value="${inf.planNo }"/>
				    	
				    	</span>
				      </li> --%>
				      <li>
				    	<label class="fl">    计划类型：</label><span>
				    	<select name="status">
				  	      <option value="1" >全部</option>
						  	<option value="12" <c:if test="${inf.status=='12'}"> selected</c:if> >未下达</option>
							<option value="2" <c:if test="${inf.status=='2'}"> selected</c:if> >已下达</option>
					   </select>
				    	
				    	</span>
				      </li>
			    	</ul>
		 
	   	 <input class="btn fl"   type="submit" name="" value="查询" />
	  <input type="button" onclick="resetQuery()" class="btn fl" value="重置"/>
   </form>
  </h2>
 
     <div class="col-md-12 pl20 mt10">
		 
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="audit()">下达</button>
		<button class="btn btn-windows input" onclick="down()">下载打印</button>
	  </div>
	  
	  
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">采购计划名称</th>
		  <th class="info">预算总金额（万元）</th>
		  <th class="info">汇总时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30">
			  <%-- <c:if test="${obj.status=='12' || obj.status=='13'}"> --%>
			  	<input type="checkbox" value="${obj.id }"  name="chkItem" onclick="check()"  alt="">
			<%--   </c:if> --%>
			  <%-- <c:if test="${obj.status=='1' }">
			   <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt="">
			  </c:if> --%>
			  </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  
			  <td class="tl pl20"  onclick="view('${obj.id}')">${obj.fileName }</td>
			
			
			  <td class="tr pr20 w140"  onclick="view('${obj.id}')"><fmt:formatNumber type="number"  pattern="#,##0.00"  value="${obj.budget}"  /></td>
			    <td class="tc w120"  onclick="view('${obj.id}')"><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tc w120"  onclick="view('${obj.id}')">
			  <c:if test="${obj.status=='12' || obj.status=='13' }">
					 未下达
			  </c:if>
			    <c:if test="${obj.status=='2'}">
			 
			 		  已下达
			  </c:if>
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>

 <div id="content" class="dnone col-md-12 col-xs-12 col-sm-12 mt10">
	 
	<form id="collect_form" action="${pageContext.request.contextPath }/taskassgin/add.html" method="post" style="margin-top: 20px;">
	
	  	   <div class="col-md-6 col-sm-6 col-xs-6">
	  	   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购任务名称:</span>
	  	   		<div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	  	   			<input  type="text" id="aduit_Name"    name="name" value="">
	  	   		</div>
	  	   </div>
	       <div class="col-md-6 col-sm-6 col-xs-6">
	       		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购任务文号:</span>
	       		<div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	       			<input id="aduit_No"   type="text" name="documentNumber" value="">
	       		</div>
	       </div>
	       
	       
		<!--  文件名称：<input type="text" name="fileName" value=""><br>
		 密码:<input type="password" name="password" value=""><br> -->
		 
		 
		 <input type="hidden" name="cid" id="cid" value="">
		<div class="col-md-12 col-xs-12 col-sm-12 tc mt10">
	     	<input type="button" class="btn" onclick="closeLayer()" value="确认" />
	     	<input type="button"  class="btn" onclick="cancel()" value="取消"/>
	    </div>
	 </form>   
 </div>
 

 
 
	 </body>
</html>
