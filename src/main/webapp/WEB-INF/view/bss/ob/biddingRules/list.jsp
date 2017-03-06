<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价规则列表页面</title>
<script type="text/javascript">
	
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
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">竞价规则列表</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价规格列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">间隔工作日：</label>
			<input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">报价时间（分钟）：</label>
	    	  <select class="w178">
	    	    <option></option>
	    	    <option>选项一</option>
	    	    <option>选项二</option>
	    	    <option>选项三</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">确认时间（分钟）：</label>
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
		<button class="btn btn-windows add" type="submit">创建竞价规则</button>
		<button class="btn" type="submit">设为默认</button>
		<button class="btn btn-windows delete" type="submit">删除</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">间隔工作日（天）</th>
		  <th class="info">具体时间点</th>
		  <th class="info">报价时间（分钟）</th>
		  <th class="info">二次报价时间（分钟）</th>
		  <th class="info">确认时间（分钟）</th>
		  <th class="info">项目数量</th>
		  <th class="info">是否为默认</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">1</td>
		  <td class="tc">1</td>
		  <td class="tc">09:00</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">22</td>
		  <td class="tc">默认</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">1</td>
		  <td class="tc">1</td>
		  <td class="tc">09:00</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">22</td>
		  <td class="tc">默认</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">1</td>
		  <td class="tc">1</td>
		  <td class="tc">09:00</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">22</td>
		  <td class="tc">默认</td>
		</tr>
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">1</td>
		  <td class="tc">1</td>
		  <td class="tc">09:00</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">20</td>
		  <td class="tc">22</td>
		  <td class="tc">默认</td>
		</tr>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>