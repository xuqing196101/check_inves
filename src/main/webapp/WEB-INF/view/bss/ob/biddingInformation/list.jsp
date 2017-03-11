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
	 window.location.href = "${pageContext.request.contextPath}/ob_project/add.html";
	}
	/**发布竞价项目**/
	function release(){
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
    <div class="search_detail">
       <form action="" method="post" id="form1" class="mb0">
         <input type="hidden" name="page" id="page">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价标题：</label>
	    	  <select class="w178">
	    	    <option></option>
	    	    <option>选项一</option>
	    	    <option>选项二</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">竞价开始时间：</label>
			<input type="text" id="topic" class=""/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows apply" type="submit" onclick="create()">创建竞价项目</button>
		<button class="btn btn-windows apply" type="submit" onclick="release()">发布竞价项目</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">竞价标题</th>
		  <th class="info">竞价开始时间</th>
		  <th class="info">成交供应商</th>
		  <th class="info">合格供应商</th>
		  <th class="info">竞价状态</th>
		  <th class="info">操作</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="list" varStatus="v">
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${list.id}" /></td>
		  <td class="tc w50">${(v.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
		  <td class="tc">${list.name}</td>
		  <td class="tc"><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:ss:mm"/></td>
		  <td class="tc">
		  <c:if test="${list.closingSupplier==null}">
		   0
		  </c:if>
		   <c:if test="${list.closingSupplier!=null}">
		   ${list.closingSupplier}
		  </c:if>
		  </td>
		  <td class="tc">
		   <c:if test="${list.qualifiedSupplier==null}">
		   0
		  </c:if>
		   <c:if test="${list.qualifiedSupplier!=null}">
		   ${list.closingSupplier}
		  </c:if></td>
		  <td class="tc">
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
		              流拍
		    </c:if>
		  
		  </td>
		  <td class="tc"><a href="javascript:void(0)">
		   <c:if test="${list.status==3}">
		              查看结果
		    </c:if>
		     <c:if test="${list.status!=3}">
		              查看供应商
		    </c:if>
		  </a></td>
		</tr>
		</c:forEach>
		
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>