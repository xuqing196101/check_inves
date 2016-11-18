<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>标书管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	function OpenFile(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != 0){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/supplierProject/loadFile.html?fileId="+fileId, true);// 异步加载, 服务器文件路径
		}
	}
	
	function queryVersion(){
		var obj = document.getElementById("TANGER_OCX");
		var v = obj.GetProductVerString();
		obj.ShowTipMessage("当前ntko版本",v);
	}
	
	function inputTemplete(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function saveFile(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	 
	//标记
	function mark(e,target){
		var obj = document.getElementById("TANGER_OCX");
		//obj.ShowTipMessage("提示","绑定指标时请把光标停在选中内容的起始处");
		if(typeof(obj.ActiveDocument) == "undefined"){
			obj.ShowTipMessage("提示","文档加载失败或者未加载文档");
			return;
		}
		//获取当前页码
		var page = obj.ActiveDocument.Application.Selection.information(1);
		if(confirm("确定【"+target+"】指标的绑定内容从第"+page+"页开始吗？")){
			obj.ActiveDocument.BookMarks.Add(target);
			var html = "<div class='shanchu light_icon'><a href='javascript:void(0)' onclick='delMark(this,"+'"'+target+'"'+");'>删除</a></div>";
			html+= "<div class='dinwei light_icon'><a href='javascript:void(0)' onclick='searchMark("+'"'+target+'"'+");'>定位</a></div>";
			$(e).parent().after(html);
			$(e).parent().remove();
			obj.ShowTipMessage("提示","【"+target+"】"+"指标内容绑定成功，请在 刷新 或者 关闭 页面前保存文件");
		}
	}	
	
	//获取标记内容并且定位
	function searchMark(target){
		var obj = document.getElementById("TANGER_OCX");
		//判断标记是否存在
		if(obj.ActiveDocument.Bookmarks.Exists(target)){
			//定位到书签内容
			obj.ActiveDocument.Bookmarks.Item(target).Select();
		}else{
			obj.ShowTipMessage("提示","获取失败，请重新绑定指标！",true);
		}
	}
	
	//删除标记
	function delMark(e,target){
		var obj = document.getElementById("TANGER_OCX");
		//判断标记是否存在
		if(obj.ActiveDocument.Bookmarks.Exists(target)){
			obj.ActiveDocument.BookMarks.Item(target).Delete();
			obj.ShowTipMessage("提示","删除绑定成功！",false);
			var html = "<div class='bdzb light_icon'><a href='javascript:void(0)' onclick='mark(this,"+'"'+target+'"'+");'>定位</a></div>";
			$(e).parent().next().remove();
			$(e).parent().after(html);
			$(e).parent().remove();
		}else{
			obj.ShowTipMessage("提示","删除失败，该指标未被绑定！",true);
		}
	}
	
	function bd(projectId){
		var obj = document.getElementById("TANGER_OCX");
		$.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/supplierProject/isExistFile.html?projectId="+projectId,  
               dataType: 'json',  
               success:function(result){
               		if(result.isExist == 0){
               			obj.ShowTipMessage("提示","请先保存标书到服务器",true);
               			return;
               		}
               		if(result.isExist == 1){
               			window.location.href = "${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId="+projectId;
               		}
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
	}
	
	function tishi(v){
		var obj = document.getElementById("TANGER_OCX");
		obj.ShowTipMessage("提示",v,true);
	}
</script>

</head>

<body onload="OpenFile('${fileId}')">
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">我的项目</a></li><li><a href="#">标书管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
		    <span>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
				  <span class="green_link">→</span>
		    	</c:if>
		    	<c:if test="${std.bidFinish != 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
				  <span class="">→</span>
		    	</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 1}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
				  <span class="">→</span>
				</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 2 }">
				  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}" class="img-v1">填写报价</a>
				  <span class="">→</span>
				</c:if>
			</span>
		    <span>
		    	<c:if test="${std.bidFinish == 3 }">
			  		<a href="javascript:void(0);" class="img-v2  orange_link">完成</a>
		    	</c:if>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 2}">
				  <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 4}">
				  <a href="javascript:void(0);"  class="img-v1">完成</a>
				</c:if>
			</span>
   		</div>
  	</div>
    
    <div class="container content height-350">
       <div class="row">
          <!-- Begin Content -->
          <div class="col-md-3 md-margin-bottom-40">
           	<div class="tag-box tag-box-v3">	
			   <div class="light_main">
			    <div class="light_list">
				 初审项      
				</div>
			    <ul class="light_box"> 
			    	<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
			    		<c:forEach items="${firstAudits}" var="fa">
				    		<li>
							    <span class="light_desc">${fa.name }</span>
						    	<div class='dinwei light_icon'>
						    		<a href='javascript:void(0)' onclick="searchMark('${fa.name }');">定位</a>
						    	</div>
							</li>
				    	</c:forEach>
			    	</c:if>
			    	<c:if test="${std.bidFinish == 1}">
				    	<c:forEach items="${firstAudits}" var="fa">
				    		<li>
							    <span class="light_desc">${fa.name }</span>
							    <div class='bdzb light_icon'>
							    	<a href='javascript:void(0)' onclick="mark(this,'${fa.name }');">绑定指标</a>
							    </div>
							</li>
				    	</c:forEach>
			        </c:if>
				</ul>
		       </div>
			   <div class="light_main">
			    <div class="light_list">
				 评分细则
				</div>
			    <ul class="light_box">
			    	<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
			    		<c:forEach items="${scoreModels}" var="sm">
				    		<li>
							    <span class="light_desc">${sm.markTermName }</span>
						    	<div class='dinwei light_icon'>
						    		<a href='javascript:void(0)' onclick="searchMark('${sm.markTermName }');">定位</a>
						    	</div>
							</li>
				    	</c:forEach>
			    	</c:if>
			    	<c:if test="${std.bidFinish == 1}">
				    	<c:forEach items="${firstAudits}" var="fa">
				    		<li>
							    <span class="light_desc">${sm.markTermName }</span>
							    <div class='bdzb light_icon'>
							    	<a href='javascript:void(0)' onclick="mark(this,'${sm.markTermName }');">绑定指标</a>
							    </div>
							</li>
				    	</c:forEach>
			        </c:if> 
				</ul>
		       </div>
        	</div>   
		  </div>
		  <div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
			<form id="MyFile" method="post"  enctype="multipart/form-data">
				<c:if test="${std.bidFinish == 1}">
					 <!-- 按钮 -->
			        <div class="fr pr15 mt10">
			        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
			        	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
			        	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input> -->
			        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
			        	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
					     <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
				         <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存绑定操作"></input>
				    </div>
				</c:if>
				<input type="hidden" id="status" value="${status }">
				 <input type="hidden" id="fileId" value="${fileId }">
				<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</form>
		  </div>
	   </div>
	</div>
</body>
</html>
