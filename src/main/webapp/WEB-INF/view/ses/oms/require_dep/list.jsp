<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/oms/layer-extend.js"></script>
<script type="text/javascript">
	$(function(){
		/*这是要初始化成树的后台数据*/
		var datas;
		/*页面初始化时加载数据转换成json数据*/
		
		
		/*树的设置*/
		var setting={
			async:{
				autoParam:["id"],
				enable:true,
				url:"<%=basePath%>purchaseManage/gettree.do",
				dataType:"json",
				type:"post",
			},
			data:{
				simpleData:{
					enable:true,
					idKey:"id",
					pId:"pId",
					rootPId:-1,
				}
			},
			callback:{
				onClick:zTreeOnClick
			}
		};
		var treeObj=$.fn.zTree.init($("#ztree"),setting);
		treeObj.expandAll(false);
		//var id="${id}";
		/* if(id!=''&&id!=null){
			getList(id);
		}else{
			id="1";
			getList(id);
		} */
	});
	
	/*这个方法的treeNode.id就是对应的id。treeNode.name就是对应的name*/
	function zTreeOnClick(event,treeId,treeNode){
		console.dir(treeNode);
		$("#parentid").val(treeNode.id);
		if(treeNode.isParent==true){
			//console.dir("true");
		}else{
			getDetail(treeNode.id);
		}
		//getDetail(treeNode.id);
	};
	function getDetail(id){
		$.ajax({   
            type: "POST",  
            dataType: "json",
            async:false,
            url: "${pageContext.request.contextPath}/purchaseManage/getDetail.do?id="+id,      //提交到一般处理程序请求数据   
            success: function(data) {
            	console.dir(data.orgnization.name);
            	$("#org_name").html(data.orgnization.name);
            	$("#org_address").html(data.orgnization.address);
            	$("#org_mobile").html(data.orgnization.mobile);
            	$("#org_postCode").html(data.orgnization.postCode);
            	//var html ="";
                //$("#Result tr:gt(0)").remove();        //移除Id为Result的表格里的行，从第二行开始（这里根据页面布局不同页变）
               //$("#Result").append(html); 
            }  
        });
	}
	
	function add(){
		showiframe("需求部门新增",1000,600,"${pageContext.request.contextPath}/purchaseManage/add.do?","-4");
	}
	function edit(){
		var id = $("#parentid").val();
		showiframe("需求部门修改",1000,600,"${pageContext.request.contextPath}/purchaseManage/edit.do?id="+id,"-4");
    }
    function del(){
		var id = $("#parentid").val();
		layer.confirm('您确定要删除吗？',{icon:3,offset:295}, function(index){
			$.post("${pageContext.request.contextPath}/purchaseManage/updateOrg.do",
			{is_deleted:1,id:id},		
			function(data){
				showalert(data.message,1,"295");
				location.reload();
			},"json"); 
    	});
		showiframe("需求部门修改",1000,600,"${pageContext.request.contextPath}/purchaseManage/updateOrg.do?is_deleted=1&id="+id,"-4");
    }
   function showiframe(titles,width,height,url,top){
		 if(top == null || top == "underfined"){
		  top = 120;
		 }
		layer.open({
	        type: 2,
	        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
	        maxmin: true,
	        shade: [0.3, '#000'],
	       	offset: top+"px",
	        shadeClose: false, //点击遮罩关闭层 
	        area : [width+"px" , height+"px"],
	        content: url
	    });
	}
</script>
</head>

<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">机构管理</a></li><li class="active"><a href="#">需求部门管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>需求部门管理</h2>
	   </div>
   </div>
   <div class="container">
   		<!-- 机构树-->
		<div id="ztree" class="ztree fl"></div>
		<div class="fr">
			    <div class="col-md-88">
			    	<button class="btn btn-windows git" type="button" onclick="show()">需求部门信息</button>
				    <button class="btn btn-windows add" type="button" onclick="add()">新增需求部门信息</button>
					<button class="btn btn-windows edit" type="button" onclick="edit()">修改部门信息</button>
					<button class="btn btn-windows delete" type="button" onclick="del();">删除需求部门信息</button>
					<input id="parentid" type="hidden"/>
			    </div>
			    <table id="fResult" class="table table-bordered table-condensed">
			    	<caption>需求部门信息</caption>
			    	<tr>
			    		<td class="tc w110">名称</td>
			    		<td class="tc w140" id="org_name"></td>
			    		<td class="tc w110">地址</td>
			    		<td class="tc w140" id="org_address"></td>
			    	</tr>
			    	<tr>
			    		<td class="tc w110">手机号</td>
			    		<td class="tc w140" id="org_mobile"></td>
			    		<td class="tc w110">邮编</td>
			    		<td class="tc w140" id="org_postCode"></td>
			    	</tr>
		        </table>
				<div>
					<span>需求部门人员信息</span>
					<button class="btn btn-windows git fr" type="button" onclick="show()">人员授权</button>
					<button class="btn btn-windows delete fr" type="button" onclick="show()">删除人员</button>
					<button class="btn btn-windows edit fr" type="button" onclick="show()">修改人员</button>
					<button class="btn btn-windows add fr" type="button" onclick="addUser()">添加人员</button>
				</div>	        
		    	 <table id="mResult" class="table table-bordered table-condensed">
					<thead>
						<tr>
						  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						  <th class="info w50">序号</th>
						  <th class="info">详细地址</th>
						  <th class="info">手机</th>
						  <th class="info">电话</th>
						  <th class="info">传真</th>
						  <th class="info">军网邮箱</th>
						</tr>
						<tr>
							<td><input id="checkAll" type="checkbox" onclick="" /></td>
							<td>1</td>
							<td>深圳福田区</td>
							<td>13112345678</td>
							<td>01028987898</td>
							<td>4455</td>
							<td>1222@ssd.com</td>
						</tr>
						<tr>
							<td><input id="checkAll" type="checkbox" onclick="" /></td>
							<td>2</td>
							<td>深圳福田区fkdhfkhfkhdfkf</td>
							<td>13112345678</td>
							<td>01028987898</td>
							<td>4455</td>
							<td>1222@ssd.com</td>
						</tr>
					</thead>
		        </table>
		        <div>
				    <span>采购管理部门信息</span>
					<button class="btn btn-windows delete fr" type="button" onclick="show()">删除管理部门</button>
					<button class="btn btn-windows add fr" type="button" onclick="show()">添加管理部门</button>
				</div>	
		         <table id="lResult" class="table table-bordered table-condensed">
					<thead>
						<tr>
						  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						  <th class="info w50">序号</th>
						  <th class="info">名称</th>
						  <th class="info">简称</th>
						  <th class="info">组织机构代码</th>
						  <th class="info">电话</th>
						  <!-- <th class="info">所在地市</th> -->
						  <!-- <th class="info">详细地址</th> -->
						  <!-- <th class="info">邮编</th>
						  <th class="info">传真</th> -->
						  <th class="info">网址地址</th>
						  <th class="info">负责人</th>
						  <!-- <th class="info">监管负责人身份证号码</th> -->
						  <th class="info">监管机构性质</th>
						</tr>
					</thead>
		        </table>	
	   </div>
    </div>
   
   <%-- <div id="ztree" class="ztree w35 fl"></div>
   <div class="fr" style="margin-left: 100px;">
	<table id="Result" class="common mt10 change ww100" cellpadding="0" cellspacing="0" border="0">
		<caption>需求部门信息</caption>
		<thead>
			<tr>
				<th class="w30"><input id="checkedAll" type="checkbox"
					name="checkedAll" onclick="selectAll()">
				</th>
				<th class="w50">序号</th>
				<th>品目名称</th>
				<th class="w100">参数</th>
				<th class="w100">排序</th>
			</tr>
		</thead>
	</table> --%>
	</div>
</body>
</html>
