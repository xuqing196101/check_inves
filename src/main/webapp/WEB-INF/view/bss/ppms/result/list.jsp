<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>模版管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${list.total}",
		    startRow:"${list.startRow}",
		    endRow:"${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '${pageContext.request.contextPath}/templet/getAll.do?page='+e.curr;
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
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/templet/view.do?id="+id;
  	}
    function save(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			 $.post("${pageContext.request.contextPath}/resultAnnouncement/view.do?id="+id,{email:$('#email').val(),address:$('#address').val()},
					  function(data){
					    var tem=data;
					    var ue = parent.UE.getEditor('editor'); 
					    ue.ready(function(){
					        //需要ready后执行，否则可能报错
					        ue.setContent(tem.content);
					        ue.setHeight(500);
					    });
					    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    parent.layer.close(index);
					  },
					  "json");
					
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择模板",{offset: ['222px', '390px'], shade:0.01});
		}
    }

	function cancle(){
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
	}
   
</script>
<body>
	<!--面包屑导航开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>模版管理</h2>
		</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
		<div class="padding-left-25 padding-right-25"></div>
	</div>

	<div class="container">
		<div class="content padding-left-25 padding-right-25 padding-top-0">
			<div class="col-md-12">
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox"
								onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info">模板类型</th>
							<th class="info">模板名称</th>
							<th class="info">创建日期</th>
							<th class="info">修改日期</th>
						</tr>
					</thead>
					<c:forEach items="${list.list}" var="templet" varStatus="vs">
						<tr>

							<td class="tc opinter"><input onclick="check()"
								type="checkbox" name="chkItem" value="${templet.id}" /></td>

							<td class="tc opinter" onclick="view('${templet.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>

							<td class="tc opinter" onclick="view('${templet.id}')">${templet.temType}</td>

							<td class="tc opinter" onclick="view('${templet.id}')">${templet.name}</td>

							<td class="tc opinter" onclick="view('${templet.id}')"><fmt:formatDate
									value='${templet.createdAt}' pattern="yyyy-MM-dd" /></td>

							<td class="tc opinter" onclick="view('${templet.id}')"><fmt:formatDate
									value='${templet.updatedAt}' pattern="yyyy-MM-dd " /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
		<div class="tc mt20 clear col-md-12">
			<input type="button"
				class="btn padding-left-10 padding-right-10 btn_back"
				onclick="save()" value="引用"></input> <input type="button"
				class="btn padding-left-10 padding-right-10 btn_back"
				onclick="cancle();" value="取消"></input>
		</div>
</body>
</html>
