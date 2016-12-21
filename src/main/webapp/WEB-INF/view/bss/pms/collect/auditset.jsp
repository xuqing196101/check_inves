<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
  /*   	var id1=[];
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
		$("#val2").val(id2); */
		
		
		$("#set_form").submit();
    }
    var nature;
    var turns;
    function experts(){
    	 nature=$("#audit_nature").val();
     	  turns=$("#audit_turn").val();
     	var tp = 0;
     if(nature==null || nature==''){
    	 layer.alert("请填写审核人员性质", {
				offset: ['30%', '40%']
			});
    	 tp = 1;
      }
//      if(turns==null || turns== ''){
//     	 layer.alert("请填写审核轮次", {
// 				offset: ['30%', '40%']
// 			});  
//     	 tp = 1;
//      }
     if(tp != 1){
  	  layer.open({
		  type: 2, //page层
		  area: ['900px', '500px'],
		  title: '专家库',
		  closeBtn: 1,
		  shade:0.01, //遮罩透明度
		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  shift: 1, //0-6的动画形式，-1不开启
		  offset: ['200px', '500px'],
		  content:  "${pageContext.request.contextPath}/set/expert.html?type="+$("#auditRound").val(),
		});
     }
    }
    function users(){
    	 nature=$("#audit_nature").val();
    	 turns=$("#audit_turn").val();
    	    var tp=0;
    	    
        if(nature==null || nature == ''){
       	 layer.alert("请填写审核人员性质", {
   				offset: ['30%', '40%']
   			});  
       	 tp = 1;
         }
//         if(turns==null || turns == '' ){
//        	 layer.alert("请填写审核轮次", {
//    				offset: ['30%', '40%']
//    			});  
//        	 tp = 0;
//         }
        if(tp != 1 ){
    	 layer.open({
   		  type: 2, //page层
   		  area: ['900px', '500px'],
   		  title: '用户库',
   		  closeBtn: 1,
   		  shade:0.01, //遮罩透明度
   		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
   		  shift: 1, //0-6的动画形式，-1不开启
   		  offset: ['200px', '500px'],
   		  content:  "${pageContext.request.contextPath}/set/user.html?type="+$("#auditRound").val(),
   		});
        	
        }
    }
    var index;
    function temp(){
    	 index=layer.open({
      		  type: 1, //page层
      		  area: ['500px', '300px'],
      		  title: '临时添加专家',
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
     	 nature=$("#audit_nature").val();
    	 turns=$("#audit_turn").val();
    	 $("#auditStaff_1").val(nature);
    	 $("#auditRound_1").val(turns);
    	 nature=$("#audit_nature").val();
         turns=$("#audit_turn").val();
            var tp=0;
          if(nature==null || nature == ''){
           layer.alert("请填写审核人员性质", {
            offset: ['30%', '40%']
          });  
           tp = 1;
           }
          if(tp != 1 ){
    	
		 $.ajax({
			 url:"${pageContext.request.contextPath}/set/judgeAddUser.do",
			 type:"POST",
			 dataType:"json",
			 data:$("#collect_form").serialize(),
			 success:function(data){
			  
				 if(data.status != null && data.status != '' && data.status == 1) {
						layer.msg('添加成功', {
							offset: ['40%', '45%']
						});
						layer.close(index);
					    var el = document.createElement("a");
                  document.body.appendChild(el);
                  el.href = "${pageContext.request.contextPath}/set/list.html?staff="+data.staff+"&&id="+$("#collId").val(); //url 是你得到的连接
                  el.target = '_parent'; //指定在新窗口打开
                  el.click();
                  document.body.removeChild(el);
// 						window.location.reload();
					} else {
						var error = eval(data);
						if(error.name) {
							$("#userName").html(error.name);
						} else {
							$("#userName").html("");
						}
						if(error.phone) {
							$("#userPhone").html(error.phone);
						} else {
							$("#userPhone").html("");
						}
						if(error.unitName) {
							$("#userUnitName").html(error.unitName);
						} else {
							$("#userUnitName").html("");
						}
						if(error.auditStaff) {
							$("#userAuditStaff").html(error.auditStaff);
						} else {
							$("#userAuditStaff").html("");
						}
					}
			 	}
		 });
          }
    }
    
    function delet(){
    	
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		$("#del_id").val(id);
		$.ajax({
			 	url:"${pageContext.request.contextPath}/set/delete.html",
			 	type:"post",
			 	data:$("#del_form").serialize(),
			 	success:function(){
					layer.msg('删除成功', {
					offset: ['40%', '45%']
				});
					window.location.reload();
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
 <div class="container container_box">
 
       <div>
          <h2 class="count_flow"><i>1</i>审核设置</h2>
        
       
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>审核人员性质</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="audit_nature" type="text" class="input_group" name="name" value="${staff }" />
                <span class="add-on">i</span>
         <%--        <div class="cue">${ERR_name}</div> --%>
              </div>
            </li>
  <%--           <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>审核轮次</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="audit_turn" type="text" class="input_group" name="projectNumber" value="" />
                <span class="add-on">i</span>
           <div class="cue">${ERR_projectNumber}</div>  
              </div>
            </li> --%>
          </ul>
        </div>
        
        
  <div>
            <h2 class="count_flow"><i>2</i>审核人员列表</h2>
<!--             
  <h2 class="list_title">审核人员列表</h2> -->
  <ul class="ul_list">
      
    <div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" onclick="experts()">专家库添加</button>
		<button class="btn btn-windows add" onclick="users()">用户库添加</button>
		<button class="btn btn-windows add" onclick="temp()">临时添加</button>
		<button class="btn btn-windows delete" onclick="delet()">删除</button>
	  </div>
  <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr class="info">
		  <th class="w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="w50">序号</th>
		<!--   <th>审核轮次</th> -->
		  <th>姓名</th>
		  <th>电话</th>
		  <th>单位名称</th>
		<!--   <th>审核人员性质</th> -->
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr class="tc pointer" id="person_set">
			  <td class="w30"><input type="checkbox" value="${obj.id }" name="chkItem"  alt=""></td>
			  <td class="w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
		<%-- 	  <td>
			  	<c:forEach items="${kind}" var="kind">
						<c:if test="${kind.id == obj.auditRound}">${kind.name}</c:if>
					</c:forEach>
				</td> --%>
			  <td>${obj.name }</td>
			  <td>${obj.mobile }</td>
			  <td>${obj.unitName }</td>
			<%--   <td>${obj.auditStaff }</td> --%>
			</tr>
		 </c:forEach>
      </table>
   </div>
    <div id="pagediv" align="right"></div>
 </ul>
 </div>
 
 
 <div style="margin-top: 50px; margin-bottom: 30px; text-align: center;">
		<button class="btn btn-windows git" onclick="save()">保存</button>
		<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>

 <div id="content" class="dnone layui-layer-wrap">
	 
	<form id="collect_form" action="">
	<input type="hidden" id="audit_turn" value="${type }" name="auditRound">
	   <input type="hidden" name="type" id="type" value="3">
         <input type="hidden" name="id" value="123123123">
	<div class="drop_window">
	   <ul class="list-unstyled">
	           <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 pl20 col-xs-12">姓名</label>
                    <span class="col-md-12 col-xs-12">
                       <input id="citySel" class="title col-md-12" name="name" type="text"/>
                       <div class="cue" id="userName"></div>
                    </span>
                 </li>
                 <li class="col-sm-6 col-md-6 p0 col-lg-6 col-xs-6">
                   <label class="col-md-12 pl20 col-xs-12">电话</label>
                    <span class="col-md-12 col-xs-12">
                       <input id="citySel" class="title col-md-12" name="mobile" type="text"/>
                       <div class="cue" id="userPhone"></div>
                    </span>
                 </li>
	           <li class="mt10 col-md-12 p0 col-xs-12">
                   <label class="col-md-12 pl20 col-xs-12">单位名称</label>
                     <span class="col-md-12 col-xs-12">
                        <input class="title col-md-12" name="unitName"  type="text">
                        <div class="cue" id="userUnitName"></div>
                    </span>
            	</li>
            
        <input type="hidden" name="auditStaff" value="" id="auditStaff_1" >
<!-- 	 	<input type="hidden" name="auditRound" value="" id="auditRound_1" > -->
	 	
	 		<input type="hidden" id="collId" name="collectId" value="${id }">	
		 <div class="clear"></div>
	</ul>
	<div class="tc mt10 col-md-12 col-xs-12">
                <button class="btn btn-windows save" id="save" type="button" onclick="qd()">添加</button>
                <button class="btn btn-windows cancel"  onclick="cancel()" type="button">取消</button>
          </div>
	</div>
	 </form>   
 </div>
</div>
</div>
	<form id="set_form" action="${pageContext.request.contextPath}/set/update.html" method="post" >
<!-- 		 <input type="hidden" name="val1" value="" id="val1" >
	 	<input type="hidden" name="val2" value="" id="val2" >
	 	<input type="hidden" name="fname" value="" id="fname" >
	 	<input type="hidden" name="fname2" value="" id="fname2" > -->
	 	<input type="hidden" name="collectId" value="${id }">	
	 	<input type="hidden" name="type" value="${type }">	
	 	
	 </form>
	 
	 
	 <form id="del_form" action="" method="post" >
 
	 	<input type="hidden" name="id" id="del_id" >	
 
	 	
	 </form>
	 
	 
	 </body>
	 
	
</html>
