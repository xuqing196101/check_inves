<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<jsp:include page="/WEB-INF/view/common.jsp"/> 
 <link href="${pageContext.request.contextPath}/public/codebase/set.css" media="screen" rel="stylesheet" type="text/css" >
  <script type="text/javascript">
  
  var id="${id}";
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
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
		        	
		         location.href = '${pageContext.request.contextPath}/purchaser/list.do?page='+e.curr;
		        }  
		    }
		});
  });
  
    $(function(){	
    	//移到右边
    	$('#add').click(function(){
    		//先判断是否有选中
    		if(!$("#select1 option").is(":selected")){			
    			alert("请选择需要移动的选项")
    		}
    		//获取选中的选项，删除并追加给对方
    		else{
    			$('#select1 option:selected').appendTo('#select2');
    		}	
    	});
    	
    	//移到左边
    	$('#remove').click(function(){
    		//先判断是否有选中
    		if(!$("#select2 option").is(":selected")){			
    			alert("请选择需要移动的选项")
    		}
    		else{
    			$('#select2 option:selected').appendTo('#select1');
    		}
    	});
    	
    	//全部移到右边
    	$('#add_all').click(function(){
    		//获取全部的选项,删除并追加给对方
    		$('#select1 option').appendTo('#select2');
    	});
    	
    	//全部移到左边
    	$('#remove_all').click(function(){
    		$('#select2 option').appendTo('#select1');
    	});
    	
    	//双击选项
    	$('#select1').dblclick(function(){ //绑定双击事件
    		//获取全部的选项,删除并追加给对方
    		$("option:selected",this).appendTo('#select2'); //追加给对方
    	});
    	
    	//双击选项
    	$('#select2').dblclick(function(){
    		$("option:selected",this).appendTo('#select1');
    	});
    	
    });
    
    function save(){
    	var id1=[];
    	var name=[];
		$('#select1 option').each(function(){ 
			var val=$(this).val().split(",");
			id1.push(val[0]);
			name.push(val[1]);
		}); 
	
		var id2=[]; 
		var name2=[];
		$('#select2 option').each(function(){ 
			var val=$(this).val().split(",");
			id2.push(val[0]);
			name2.push(val[1]);
		});
		$("#fname").val(name);
		$("#fname2").val(name2);
		$("#val1").val(id1);
		$("#val2").val(id2);
		$("#set_form").submit();
    }
    function experts(){
     
  	  layer.open({
		  type: 2, //page层
		  area: ['900px', '500px'],
		  title: '专家库',
		  closeBtn: 1,
		  shade:0.01, //遮罩透明度
		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  shift: 1, //0-6的动画形式，-1不开启
		  offset: ['200px', '500px'],
		  content:  '${pageContext.request.contextPath}/set/expert.html',
		});
    }
    function users(){
    	 layer.open({
   		  type: 2, //page层
   		  area: ['900px', '500px'],
   		  title: '用户库',
   		  closeBtn: 1,
   		  shade:0.01, //遮罩透明度
   		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
   		  shift: 1, //0-6的动画形式，-1不开启
   		  offset: ['200px', '500px'],
   		  content:  '${pageContext.request.contextPath}/set/user.html',
   		});
    }
    var index;
    function temp(){
    	 index=layer.open({
      		  type: 1, //page层
      		  area: ['500px', '300px'],
      		  title: '临死添加专家',
      		  closeBtn: 1,
      		  shade:0.01, //遮罩透明度
      		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
      		  shift: 1, //0-6的动画形式，-1不开启
      		  offset: ['80px', '500px'],
      		  content: $("#content"),
      		});
    }
    function cancel(){
    	layer.close(index);
    }
    
    function qd(){
   	 
		 $.ajax({
			 url:"${pageContext.request.contextPath}/set/add.html",
			 type:"post",
			 data:$("#collect_form").serialize(),
			 success:function(){
				 alert("添加成功");
			    	layer.close(index);
		window.location.reload();
			 },error:function(){
				 
			 }
		 });
    }
    
    
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购计划子系统</a></li><li class="active"><a href="#">计划汇总审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
  <%-- <div class="border1 col-md-12 ml30">
    <form id="add_form" >
  		审核人员设置： <input type="text" class="mt10" name="planName" value="${inf.planName }"/>
   </form>
  </div> --%>
  <div class="container container_box">
  <div>
  <h2 class="list_title">审核人员列列表</h2>
  <ul class="ul_list">
      
    <div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" onclick="experts()">专家库添加</button>
		<button class="btn btn-windows add" onclick="users()">用户库添加</button>
		<button class="btn btn-windows add" onclick="temp()">临时添加</button>
		
	  </div>
  <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">姓名</th>
		  <th class="info">电话</th>
		  <th class="info">身份证号</th>
 
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
			  
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  
			    <td class="tc"  >${obj.name }</td>
			    
			  <td class="tc"  >${obj.mobile }</td>
			
			  <td class="tc"  >${obj.idNumber }</td>
			 
			</tr>
	 
		 </c:forEach>
      </table>
   </div>
    <div id="pagediv" align="right"></div>
 </ul>
 </div>
 <div class="padding-top-10 clear">
    
      <h2 class="list_title">审核字段设置
      </h2>
<ul class="ul_list">
<div class="content table_box">
	
	<div class="selectbox" style="float: left;">
		<div class="select-bar">
		    <select multiple="multiple" id="select1">
		    <c:if test="${listf!=null}">
		      <c:forEach items="${listf }" var="obj">
		      		 <option value="${obj.code},${obj.desc}"> ${obj.desc }</option>
		       </c:forEach>
		      </c:if>
		      
		      <c:if test="${listy!=null}">
		      <c:forEach items="${listy }" var="obj">
		      		 <option value="${obj.filed},${obj.filedName}"> ${obj.filedName }</option>
		       </c:forEach>
		      </c:if>
		    </select>
		</div>
		<div class="btn-bar">
		    <p><span id="add"><input type="button" class="btn" value=">" title="移动选择项到右侧"/></span></p>
		    <p><span id="add_all"><input type="button" class="btn" value=">>" title="全部移到右侧"/></span></p>
		    <p><span id="remove"><input type="button" class="btn" value="<" title="移动选择项到左侧"/></span></p>
		    <p><span id="remove_all"><input type="button" class="btn" value="<<" title="全部移到左侧"/></span></p>
		</div>
		<div class="select-bar">
		    <select multiple="multiple" id="select2">
		   
		     <c:forEach items="${listn }" var="obj">
		      		 <option value="${obj.filed},${obj.filedName}"> ${obj.filedName }</option>
		       </c:forEach>
		    </select>
		</div>	
	</div>
	
<br><br>
	<span style="padding-top: 30px;">
	 把左边字段移动到右边，代表该字段能修改，如果不移动，则改字段在审核过程中不能修改。
	</span>
 
	
</div>
</ul>
</div>
	<div style="margin-top: 50px; margin-bottom: 30px; text-align: center;">
		<button class="btn btn-windows git" onclick="save()">保存</button>
		<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>

 <div id="content" class="dnone">
	 
	<form id="collect_form" action="" style="margin-top: 20px;">
	
	  <div style="text-align: center;"><span>姓名:</span><input  type="text" name="name" value=""></div>
	   <div  style="text-align: center;margin-top: 20px;"><span>电话:</span><input  type="text" name="mobile" value=""></div>
	  	<div  style="text-align: center;margin-top: 20px;"><span>身份证号:</span><input  type="text" name="idNumber" value=""></div>
		<!--  文件名称：<input type="text" name="fileName" value=""><br>
		 密码:<input type="password" name="password" value=""><br> -->
		 <input type="hidden" name="type" id="type" value="3">
		 
		 <input type="hidden" name="id" value="123123123">
<!-- 	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 180px;" onclick="qd()" >确认添加</button>
	   	<button class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 30px" onclick="cancel()" >取消</button>
 -->	   
	   <input class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;margin-left: 180px;" type="button"  onclick="qd()" value="确认添加">
	  <input class="btn padding-left-10 padding-right-10 btn_back"  style="margin-top: 20px;" type="button"  onclick="cancel()" value="取消">
	
	 </form>   
 </div>
</div>
</div>
	<form id="set_form" action="${pageContext.request.contextPath}/set/update.html" method="post" style="display: none;">
		 <input type="hidden" name="val1" value="" id="val1" >
	 	<input type="hidden" name="val2" value="" id="val2" >
	 	<input type="hidden" name="fname" value="" id="fname" >
	 	<input type="hidden" name="fname2" value="" id="fname2" >
	 	<input type="hidden" name="collectId" value="${id }">	
	 </form>
	 </body>
	 
	
</html>
