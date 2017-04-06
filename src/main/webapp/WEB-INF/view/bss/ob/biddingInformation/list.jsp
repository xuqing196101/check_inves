<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价信息页面</title>
<script type="text/javascript">
	 /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
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
	
	//创建竞价项目
	function create(){
	 var orgid='${orgId}';
	   if(orgid=='0'){
	 window.location.href = "${pageContext.request.contextPath}/ob_project/add.html";
	 }else{
	 layer.alert("您不是需求部门不能创建", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
	/**编辑 **/
	function release(){
	 var orgid='${orgId}';
	   if(orgid=='0'){
	 var id = [];
	   $('input[name="chkItem"]:checked').each(function() {
	   		id.push($(this).val());
	   });
	   if(id.length==1){
	  var checkID= $('input[name="chkItem"]:checked').val();
	  var status=$("#"+checkID+"status").html();
	  if(checkID){
	    
	    if($.trim(status)=='暂存'){
	     window.location.href ="${pageContext.request.contextPath}/ob_project/editOBProject.html?obProjectId="+checkID;
	     }else{
	       layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		     });
	       }
	     }else{
	  layer.alert("请先选择", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	    }
	  }else{
	  layer.alert("请选择1项", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	 }else{
	  layer.alert("您不是需求部门不能编辑", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
		/**删除 **/
	function del(){
	 var orgid='${orgId}';
	   if(orgid=='0'){
	  var id = [];
	   $('input[name="chkItem"]:checked').each(function() {
	   		id.push($(this).val());
	   });
	 if(id.length==1){
	  var checkID= $('input[name="chkItem"]:checked').val();
	  var status=$("#"+checkID+"status").html();
	  if(checkID){
	    if($.trim(status)=='暂存'){
	         layer.confirm('您是否要删除?', {
                    shade: 0.01,
                    btn: ['是', '否'],
                  }, function() {
	        window.location.href ="${pageContext.request.contextPath}/ob_project/delOBProject.html?obProjectId="+checkID;
			    });
	     }else{
	       layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		     });
	     }
	  }else{
	  layer.alert("请先选择", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	  }
	   }else{
	  layer.alert("请选择1项", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	 }else{
	 layer.alert("您不是需求部门不能删除", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
	
	/**发布竞价项目**/
	function releaseHref(checkID){
	   var orgid='${orgId}';
	   if(orgid=='0'){
	  if(checkID){
	   window.location.href ="${pageContext.request.contextPath}/ob_project/editOBProject.html?status=1&obProjectId="+checkID;
	  }else{
	  layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	   }
	  }else{
	   layer.alert("您不是需求部门不能创建", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	  }
	}
	
	/**查询**/
	function query(){
	if(!$("#name").val().trim()){
	   return ;
	 }
	if(!$("#startTime").val().trim()){
	   return
	 }
	}
	/**重置**/
	function reset(){
	$("#name").val("");
	$("#startTime").val("");
	} 
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">网上竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价信息管理</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价信息列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>竞价信息列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/ob_project/list.html" method="post" id="form1" class="mb0">
         <input type="hidden" name="page" id="page">
         <input type="hidden" name="projectId" id="projectId"/>
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价项目名称：</label>
	    	  <input id="name" name="name" value="${name }" type="text" maxlength="180" class="w230 mb0">
	      </li>
    	  <li>
	    	<label class="fl">竞价开始时间：</label>
			<input value=""
			 name="startTime" id="startTime" type="text"  readonly="readonly"   maxlength="19" 
			 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="Wdate" />
	      </li> 
	    	<input type="submit" onclick="return query()" class="btn fl mt1" value="查询">
	    	<input type="reset" class="btn fl mt1 ml5"  value="重置">  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="submit" onclick="create()">创建</button>
		<button class="btn btn-windows edit" type="submit" onclick="release()">编辑</button>
		<button class="btn btn-windows delete" type="submit" onclick="del()">删除</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="30%">竞价项目名称</th>
		  <th class="info">竞价项目编号</th>
		  <th class="info">竞价开始时间</th>
		  <th class="info">中标供应商</th>
		  <th class="info">报价供应商</th>
		  <th class="info">竞价状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="list" varStatus="v">
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${list.id}" /></td>
		  <td class="tc w50">${(v.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
		  <td class="tl"><a onclick="releaseHref('${list.id}')" style="cursor:pointer "/> ${list.name}</a></td>
		  <td class="tl">${list.projectNumber}</td>
		  <td class="tc"><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <td class="tc">
		  <c:if test="${list.closingSupplier==0}">
		   0
		  </c:if>
		  <c:if test="${list.closingSupplier==null}">
		   0
		  </c:if>
		   <c:if test="${list.closingSupplier!=0}">
		   <a href="${pageContext.request.contextPath}/ob_project/supplierList.html?obProjectId=${list.id}&result=1">${list.closingSupplier}</a>
		  </c:if>
		  </td>
		  
		  <td class="tc">
		  <c:if test="${list.status==3 || list.status==4 }">
		  	<c:if test="${list.offerSupplierNumber==0}">0</c:if>
		  	<c:if test="${list.offerSupplierNumber==null}">0</c:if>
		   	<c:if test="${list.offerSupplierNumber != null && list.offerSupplierNumber != 0}">
		    <a href="${pageContext.request.contextPath}/ob_project/offerSupplierList.html?obProjectId=${list.id}">${list.offerSupplierNumber}</a>
		 	</c:if>       
		  </c:if>
		  
		   <c:if test="${list.status != 3 && list.status != 4}">
		  	<c:if test="${list.offerSupplierNumber==0}">0</c:if>
		  	<c:if test="${list.offerSupplierNumber==null}">0</c:if>
		   	<c:if test="${list.offerSupplierNumber != null && list.offerSupplierNumber != 0}">
		   	${list.offerSupplierNumber}
		 	</c:if>       
		  </c:if>
		  
		  </td>
		  <td class="tc" id="${list.id}status">
		    <c:if test="${list.status==0}">
		              暂存
		    </c:if>
		     <c:if test="${list.status==1}">
		                  已发布
		    </c:if>
		     <c:if test="${list.status==2}">
		              竞价中
		    </c:if>
		     <c:if test="${list.status==3}">
		              竞价结束
		    </c:if>
		     <c:if test="${list.status==4}">
		              已流拍
		    </c:if>
		      <c:if test="${list.status==5}">
		              第一轮待确认
		    </c:if>
		     <c:if test="${list.status==6}">
		              第二轮待确认
		    </c:if>
		  
		  </td>
		</tr>
		</c:forEach>
		
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>