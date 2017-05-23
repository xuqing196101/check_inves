<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>抽取记录</title>
    <link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    media="screen" rel="stylesheet">
 <script type="text/javascript"
    src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
    laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${listExtractRecord.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${listExtractRecord.total}",
          startRow: "${listExtractRecord.startRow}",
          endRow: "${listExtractRecord.endRow}",
          groups: "${listExtractRecord.pages}">=5?5:"${listExtractRecord.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
          
              return "${listExtractRecord.pageNum}";
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                  $("#page").val(e.curr);
                  $("form:first").submit();
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
	
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/ExpExtract/showRecord.do?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${pageContext.request.contextPath}/user/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/user/delete_soft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
 
    
    function openPreMenu(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '500px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['180px', '550px'],
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/user/openPreMenu.html?id=' + ids,
				success : function(layero, index) {
					iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				},
				btn : [ '保存', '关闭' ],
				yes : function() {
					iframeWin.onCheck(ids);
				},
				btn2 : function() {
					layer.closeAll();
				}
			});
		} else if (ids.length > 1) {
			layer.alert("只能同时选择一个用户", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择一个用户", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
	}
    
    function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    }
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a href="javascript:void(0);">支撑系统</a></li>
				<li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/ExpExtract/projectList.html?typeclassId=typeclassId')">专家抽取</a></li>
				<li class="active"><a href="javascript:void(0);">抽取专家记录</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>抽取专家记录</h2>
		</div>
	</div>

	<!-- 查询 -->

	<div class="container clear margin-top-0">
		 <h2 class="search_detail">
			<form action="${pageContext.request.contextPath}/ExpExtract/resuleRecordlist.do" method="post" id="form1" >
				<input type="hidden" id="page" name="page">
				
				<ul class="demand_list">
					<li class="fl mr15"><label class="fl">项目名称：</label><span><input
							name="projectName" value="${expExtractRecord.projectName }"
							type="text" class="mb0" /></span>
					</li>
					<li class="fl mr15"><label class="fl ">抽取时间：</label><span><input
							onclick='WdatePicker()'
							value="<fmt:formatDate value='${expExtractRecord.extractionTime}'
                                pattern='yyyy-MM-dd' />"
							name="extractionTime" type="text" class="mb0" /></span>
					</li>
				        <input class="btn fl mt1" type="submit" value="查询"/>
                        <button type="button" class="btn fl mt1" onclick="resetQuery();">重置</button> 
				</ul>
			</form>
			<div class="clear"></div>
			</h2>
	</div>
	<!-- 表格开始-->
	<div class="container margin-top-5">
		<div class="content table_box">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info" width="35%">采购项目名称</th>
						<th class="info" width="15%">抽取时间</th>
						<th class="info" width="30%">抽取地点</th>
						<th class="info">抽取方式</th>
					</tr>
				</thead>
				<c:forEach items="${listExtractRecord.list}" var="extract"
					varStatus="vs">
					<tr class="cursor" onclick="show('${extract.id}');">
						<td class="tc">${(vs.index+1)+(listExtractRecord.pageNum-1)*(listExtractRecord.pageSize)}</td>
						<td class="tl pl20">${extract.projectName}</td>
						<td class="tc"><fmt:formatDate
								value="${extract.extractionTime}" pattern="yyyy年MM月dd日   " /></td>
						<td >${extract.extractionSites }</td>
						<td class="tc"><c:if test="${extract.extractTheWay==1}">
				             	             人工抽取
					        </c:if> <c:if test="${extract.extractTheWay==2}">
	                                  		    语音抽取                                          			   
	           		             </c:if></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>
</html>
