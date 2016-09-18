<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>信息发布</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
    
<script type="text/javascript">
	$(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = '<%=basePath%>article/auditlist.html?status=1&page='+e.curr;
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
    
	function getInfo(){
		window.location.href="<%=basePath%>article/getAll.html";
	}
	
	function sub(){
    	window.location.href="<%=basePath%>article/sublist.html?status=0";
    }
	
	function view(id){
		window.location.href="<%=basePath%>article/auditInfo.html?id="+id;
	}
	
	function audit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>article/auditInfo.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要审核的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
</script>

  </head>
  
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="<%=basePath%>article/getAll.html">信息管理</a></li><li><a href="#">审核信息管理</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
	<div class="container">
	   <div class="headline-v2">
	   		<h2>审核信息列表</h2>
	   </div>
	   <div class="col-md-8">
		   <button class="btn btn-windows git" type="button" onclick="getInfo()">返回信息页面</button>
	  	   <button class="btn btn-windows git" type="button" onclick="sub()">提交信息页面</button>
	   </div>
   </div>
   
	   <input type="hidden" id="depid" name="depid">
	  	
		<div class="container">	
			<div class="col-md-8">
	   			<button class="btn btn-windows add" type="button" onclick="audit()">提交信息</button>
			</div>
			
			<div class="col-md-4 ">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="<%=basePath%>article/serch.html" method="get"><div style="display:none"><input name="utf8" value="✓" type="hidden"></div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <input name="status" value="1" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group">
                        <input class="form-control bgnone h37 p0_10" id="kname" name="kname" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>               
			   </div>
              </div>
            </div>
		</div>
			
	<div class="container margin-top-5">
	<div class="content padding-left-25 padding-right-25 padding-top-5">
		  <table class="table table-bordered table-condensed">
		  	<thead>
	  			<tr>
	  				<th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	  				<th class="info">序号</th>
	  				<th class="info">信息标题</th>
	  				<th class="info">发布范围</th>
	  				<th class="info">录入时间</th>
	  				<th class="info">信息类型</th>
	  			</tr>
	  		</thead>
	  		<c:forEach items="${list.list}" var="article" varStatus="vs">
	  			<tr>
		  			<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
		  			<td class="tc" onclick="view('${article.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
		  			<td class="tc" onclick="view('${article.id }')">${article.name }</td>
		  			<td class="tc" onclick="view('${article.id }')">
		  				<c:if test="${article.range=='0' }">
		  					内网
		  				</c:if>
		  				<c:if test="${article.range=='1' }">
		  					外网
		  				</c:if>
		  				<c:if test="${article.range=='2' }">
		  					内网/外网
		  				</c:if>
		  			</td>
		  			<td class="tc" onclick="view('${article.id }')">
		  				<fmt:formatDate value='${article.createdAt }' pattern="yyyy年MM月dd日   HH:mm:ss" />
		  			</td>
		  			<td class="tc" onclick="view('${article.id }')">${article.articleType.name }</td>
		  		</tr>
		  		</c:forEach>
		  </table>
	  	</div>  
	  	<div id="pagediv" align="right"></div>
  </div>
  
  </body>
</html>
