<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>  
<%@ include file="/WEB-INF/view/common.jsp" %> 
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
 <title>产品目录管理</title>   
<script type="text/javascript">
	var treeid = null , nodeName=null, level = null, typesObj = null;
	var datas;
	 $(document).ready(function(){  
          $.fn.zTree.init($("#ztree"),setting,datas);
	      var treeObj = $.fn.zTree.getZTreeObj("ztree");
	      var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
	      for(var i=0 ;i<nodes.length;i++){
		     if (nodes[i].status==1) {
				 check==true;
		      }
	       }
	    //初始化类型
		typesObj = initTypes();
	 }); 
	 var setting={
		   async:{
					autoParam:["id"],
					enable:true,
					url:"${pageContext.request.contextPath}/category/createtree.do",
					otherParam:{"otherParam":"zTreeAsyncTest"},  
					dataType:"json",
					datafilter:filter,
					type:"get",
				},
				callback:{
			    	onClick: zTreeOnClick,//点击节点触发的事件
       			    
			    }, 
				data:{
					keep:{
						parent:true
					},
					key:{
						title:"title"
					},
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:"0",
					}
			    },
			   view:{
			        selectedMulti: false,
			        showTitle: false,
			   },
         };
	
	 
	 function filter(treeId,parentNode,childNode){
		 if (!childNodes) return null;
			for(var i = 0; i<childNodes.length;i++){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g,'.');
			}
		return childNodes;
	 }
	 
    /**点击事件*/
    var selectedNode = null;
    function zTreeOnClick(event,treeId,treeNode){
    	treeid = treeNode.id;
    	var node = treeNode.getParentNode();
    	if (node && node != null ) {
    		level = node.level + 2;
    		resetTips();
    		$("#tableDivId").removeClass("dis_none");
    		$("#uploadBtnId").addClass("dis_none");
			$("#btnIds").hide();
			$("#fileId_downBsId").val(treeNode.id);
			$("#fileId_showdel").val("false");
			$("#uploadBtnId").hide();
			if (treeNode.level == 3) {
				if (node.getParentNode().getParentNode().name == "工程") {
					/* $("#engLevelTrId").removeClass("dis_none"); */
					/* $("#levelTrId").addClass("dis_none"); */
				} else {
					/* $("#levelTrId").removeClass("dis_none"); */
					/* $("#engLevelTrId").addClass("dis_none"); */
				}
			} else {
				/* $("#levelTrId").addClass("dis_none"); */
				/* $("#engLevelTrId").addClass("dis_none"); */
			}
	    	nodeName = node.name;
    		update(treeNode);
    		selectedNode = treeNode;
    	} else {
    		$("#tableDivId").addClass("dis_none");
    	}
    }

    
    /** 判断是否为根节点 */
    function isRoot(node){
    	if (node.pId == 0){
    		return true;
    	} 
    	return false;
    }
    
    
    /**新增 */
    function add(){
    	hideQua();
		if (treeid==null) {
			layer.msg("请选择一个节点");
			return;		
		}else{
    	    var zTree = $.fn.zTree.getZTreeObj("ztree");
			nodes = zTree.getSelectedNodes();
			var node = nodes[0];
			var nodes = getCurrentRoot(node);
			$("#operaFlag").val('add');
			if (nodes.classify && nodes.classify == "PROJECT" && level == 3){
				showQua(null, nodes.classify);
			}
			if (nodes.classify && nodes.classify == "GOODS" && level == 2){
				showQua(null, nodes.classify);
			}
			if (nodes.classify && nodes.classify == "SERVICE" && level == 2){
				showQua(null, nodes.classify);
			}
			if (node) {
				$("#typeId").empty();
				$("#openId").empty();
				$.ajax({
					url:"${pageContext.request.contextPath}/category/add.do",
					type:"POST",
					success:function(data){
						reset();
						$("#tableDivId").removeClass("dis_none");
						$("#uploadBtnId").show();
						$("#mainId").val(data);
						$("#uploadId_businessId").val(data);
						$("#fileId_downBsId").val(data);
						$("#fileId_showdel").val("true");
						showInit();
						if (nodes.classify && nodes.classify == "GOODS"){
							$("#typeTrId").show();
							loadcheckbox("");
						} else {
							$("#typeTrId").hide();
						}
						if (level == 3) {
							if (node.getParentNode().getParentNode().name == "工程") {
								/* $("#engLevelTrId").removeClass("dis_none"); */
								/* $("#levelTrId").addClass("dis_none"); */
							} else {
								/* $("#levelTrId").removeClass("dis_none"); */
								/* $("#engLevelTrId").addClass("dis_none"); */
							}
						} else {
							/* $("#levelTrId").addClass("dis_none"); */
							/* $("#engLevelTrId").addClass("dis_none"); */
						}
						loadRadioHtml("");
					}
				});
				$("#pid").val(node.id);
				$("#parentNameId").text(node.name);
				$("#uploadBtnId").removeClass("dis_none");
				$("#btnIds").show();
				$("#operaId").val('add');
				
			} 
		}
	}
    /** 重置 */
    function reset() {
    	$("#cateId").val("");
		$("#posId").val("");
		$("#descId").val("");
		/* $("#levelId").val(""); */
		/* $("#engLevelId").val("");
		$("#engLevelSelect").find("option").each(function(index, element){
			element.selected = false;
		}); */
    }

	/**修改节点信息*/
    function update(nodes){
    	hideQua();
 	    if (treeid==null){
 			layer.msg("请选择一个节点");
		}else{
		$("#typeId").empty();
		$("#openId").empty();
		var node = getCurrentRoot(nodes);
		  $.ajax({
			url:"${pageContext.request.contextPath}/category/update.do?id="+treeid,
			dataType:"json",
			type:"POST",
			success:function(cate){
					$("#uploadId_businessId").val(cate.id);
					$("#fileId_downBsId").val(cate.id);
					$("#pid").val(cate.parentId);
					$("#parentNameId").text(nodeName);
					$("#cateId").val(cate.name);
					/* $("#levelId").val(cate.level); */
					/* if (cate.getEngLevel != null && cate.getEngLevel != "undefined") {
						var engLevel = cate.getEngLevel.split(",");
						for (var i = 0; i < engLevel.length; i++) {
							$("#engLevelSelect").find("option").each(function(index, element){
								if (element.value == engLevel[i]) {
									element.selected = true;
								}
							});
						}
					} */
					$("#posId").val(cate.code);
					$("#descId").val(cate.description);
					showInit();
					if (node.classify && node.classify == "PROJECT" && level == 4){
						showQua(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS" && level == 3){
						showQua(cate, node.classify);
					}
					if (node.classify && node.classify == "SERVICE" && level == 3){
						showQua(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						$("#typeTrId").show();
						loadcheckbox(cate.classify);
					} else {
						$("#typeTrId").hide();
					}
					loadRadioHtml(cate.isPublish);
		      }
            });
        }
    }
    
	/** 保存 */
	function save(){
		var operaValue = $("#operaFlag").val();
		var types = getTypeValue();
		var open = getOpenValue();
		var root = getCurrentRoot(selectedNode);
		if (root.classify =="GOODS" && types.length == 0){
			layer.msg("类型不能为空");
			return ;
		}
		if (types.length == 2){
			$("#classify").val(3);
		}
		if (types.length == 1){
			if (types[0] == 'PRODUCT'){
				$("#classify").val(1);
			}
			if (types[0] == 'SALES'){
				$("#classify").val(2);
			}
		}
		
		/* var engLevel = "";
		$("#engLevelSelect").find("option").each(function(index, element){
			if (element.selected == true) {
				engLevel = engLevel + element.value + ",";
			}
		});
		$("#engLevelId").val(engLevel); */
		
    	$.ajax({
    		dataType:"json",
    		type:"post",
    		data:$("#fm").serialize(),
    		url:"${pageContext.request.contextPath}/category/save.do",
    		success:function(data){
    			result(data,operaValue);
    		}
    	});
    }
    
    /** 清空错误提示 */
    function resetTips(){
    	$("#cateTipsId").text("");
    	$("#posTipsId").text("");
    	$("#descTipsId").text("");
    	/* $("#levelTipsId").text(""); */
    	/* $("#engLevelTipsId").text(""); */
    }
    
    /** 保存后的提示 */
    function result(msg,operaValue){
    	resetTips();
    	if (msg.success) {
    		$("#uploadBtnId").hide();
			$("#btnIds").hide();
			if (operaValue =='add'){
				refreshNode();
			} else {
				refreshParentNode();
			}
			layer.msg('保存成功');
    	} else {
    		if (msg.msg != null && msg.msg != ""){
    			$("#cateTipsId").text(msg.msg);
    		}
    		if (msg.error != null && msg.error !="") {
    			$("#posTipsId").text(msg.error);
    		}
    		if (msg.lenTxt != null && msg.lenTxt !=""){
    			$("#descTipsId").text(msg.lenTxt);
    		}
    	}
   }
   
    /**
     	刷新当前节点
    */
   function refreshNode(){
	   var zTree = $.fn.zTree.getZTreeObj("ztree"),
	   type = "refresh",  
	   silent = false,  
	   nodes = zTree.getSelectedNodes();
	   zTree.reAsyncChildNodes(nodes[0], type, silent); 
	   zTree.expandNode(nodes[0], true, false);
   }
    
    /** 刷新父级节点 */
   function refreshParentNode() {  
	   var zTree = $.fn.zTree.getZTreeObj("ztree"),
	   type = "refresh", 
	   silent = false,  
	   nodes = zTree.getSelectedNodes();  
	   var parentNode = zTree.getNodeByTId(nodes[0].parentTId); 
	   zTree.reAsyncChildNodes(parentNode, type, silent); 
	   zTree.selectNode(nodes[0],true);
	   zTree.expandNode(parentNode, true, false);
   }
    
    /** 刷新根节点 */
   function refreshRootNode(){
	   var treeObj = $.fn.zTree.getZTreeObj("ztree");
	   var type = "refresh", 
	   silent = false;
	   treeObj.reAsyncChildNodes("", type, silent);  
   }
   
	
	  /** 编辑 */
  function 	edit(){
	  var zTree = $.fn.zTree.getZTreeObj("ztree");
	  var nodes = zTree.getSelectedNodes();  
	  $("#operaFlag").val('edit');
	  if (isRoot(nodes[0])){
		  layer.msg(nodes[0].name + '不能被编辑');
		  return;
	  }
	  
	  var msg = determine('edit');
	  if (msg != null){
		  if (msg != "ok"){
			  layer.msg(msg);
			  return false;
		  }
	  }
	  
	  $("#operaId").val('edit');
	  $("#mainId").val(treeid);
	  $("#fileId_showdel").val("true");
	  $("#uploadBtnId").removeClass("dis_none");
      $("#btnIds").show();
      $("#uploadBtnId").show();
      update(nodes[0]);
	}
	
  /**删除*/
  function del(){
	  if (treeid == null){
		 layer.alert("请选择一个子节点,进行删除",{offset: ['150px', '500px'], shade:0.01});
		 return;
	  }
	  var zTree = $.fn.zTree.getZTreeObj("ztree");
	  var nodes = zTree.getSelectedNodes();  
	  if (nodes[0] && nodes[0].isParent){
		  layer.alert("请选择一个子节点,进行删除",{offset: ['150px', '500px'], shade:0.01});
		  return;
	  }
	  
	  var msg = determine('del');
	  if (msg != null){
		  if (msg != "ok"){
			  layer.msg(msg);
			  return false;
		  }
	  }
	  
	  layer.confirm('您确认要删除吗？', {
		  btn: ['确认','取消']
	    },function (){
	    	delNode();
	    }
  	  );
  }
  
  
  /** 获取状态 **/
  
  function  determine(operaType){
	  var res = null;
	  $.ajax({
		  type:"post",
		  data:{'id':treeid,'opera':operaType},
		  async: false,
	  	  url:"${pageContext.request.contextPath}/category/calledStatus.do",
	      success:function(msg){
	    	  res = msg;
	  	  }
	  });
	  return res;
  }
  
  
  /** 删除节点 */
  function delNode(){
	  $.ajax({
	  		type:"post",
	  		url:"${pageContext.request.contextPath}/category/deleted.do?id=" + treeid,
	  		success:function(data){
	  			if (data == "success") {
	  				refreshParentNode();
	  				layer.msg('删除成功');
	  				$("#tableDivId").addClass("dis_none");
	  			} else {
	  				layer.msg('删除失败');
	  			}
	  		}
	  	});
  }

  //显示资质要求
 function showQua(cate, type){
	 $("#generaQuaTr").show();
	 $("#profileQuaTr").show();
	 if (type == "GOODS") {
		 $("#profileQuaTr_sales").show();
		 var tdArr = $("#profileQuaTr").children();
		 tdArr.eq(0).html("物资生产型专业资质要求");
	 } else {
	     var tdArr = $("#profileQuaTr").children();
		 tdArr.eq(0).html("专业资质要求");
	 }
	 if (cate != null && cate !="" && cate !="undefined" && cate !="null"){
		 $("#generalIQuaId").val(cate.generalQuaIds);
		 $("#generalIQuaName").val(cate.generalQuaNames);
		 $("#profileIQuaId").val(cate.profileQuaIds);
		 $("#profileIQuaName").val(cate.profileQuaNames);
		 $("#profileSalesId").val(cate.profileSalesQuaIds);
		 $("#profileSalesName").val(cate.profileSalesQuaNames);
	 }
 }
  
 //隐藏 
 function hideQua(){
	 $("#generaQuaTr").hide();
	 $("#profileQuaTr").hide();
	 $("#profileQuaTr_sales").hide();
 }
 
 //初始化类型
 function openLayer(type){
	 var title ="";
	 var ids ="";
	 if (type == 1){
		 ids = $("#generalIQuaId").val();
		 title ="添加通用资质要求";
	 }
	 if (type == 2){
	 	var root = getCurrentRoot(selectedNode);
		if (root.classify && root.classify == "PROJECT"){
			ids = $("#profileIQuaId").val();
		 	title ="添加工程专业资质要求";
		 	type = 4;
		} else {
			 ids = $("#profileIQuaId").val();
			 title ="添加物资生产型专业资质要求";
		}
	 }
	 if (type == 3){
		 ids = $("#profileSalesId").val();
		 title ="添加物资销售型专业资质要求";
	 }
	 layer.open({
			type : 2, 
			area : [ '800px', '590px' ],
			title : title,
			shadeClose : true,
			content : '${pageContext.request.contextPath}/qualification/initLayer.html?type=' + type +"&ids=" + ids
		 });
 }
 
 //添加通用的
 function addGeneralValue(ids,name){
	 $("#generalIQuaId").val(ids);
	 $("#generalIQuaName").val(name);
 }
 
 //添加物资生产型专用的
 function addProfileValue(ids,name){
	 $("#profileIQuaId").val(ids);
	 $("#profileIQuaName").val(name);
 }
 
 //添加物资销售型专用的
 function addProfileSalesValue(ids,name){
	 $("#profileSalesId").val(ids);
	 $("#profileSalesName").val(name);
 }
 
 //获取数量
 function getTypeValue(){
	 var typeArray = [];
	 $("input[name='smallClass']:checked").each(function(){
		 var value = $(this).val();
		 typeArray.push(value);
	 });
	 return typeArray;
 }
 
 //获取是否公开
 function getOpenValue(){
	 var isPublish = null;
	 $("input[name='isOPen']:checked").each(function(){
		 isPublish = $(this).val();
	 });
	 $("#isPublish").val(isPublish);
	 return isPublish;
 }
 
 /**
  * 加载checkbox
  * @param checkedVal 判断选中的值
  */
 function loadcheckbox(checkedVal){
 	var html = "";
 	for (var i =0;i<typesObj.length;i++){
 		 if (checkedVal == 1 && typesObj[i].code == 'PRODUCT'){
 			 html+="<input name='smallClass' type='checkbox'  checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
 		 } else if (checkedVal == 2 && typesObj[i].code == 'SALES'){
 			 html+="<input name='smallClass' type='checkbox'  checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
 		 }else if (checkedVal == 3){
 			 html+="<input name='smallClass' type='checkbox'  checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
 		 } else {
 			 html+="<input name='smallClass' type='checkbox'  value='"+typesObj[i].code+"' />" +typesObj[i].name;
 		 }
 		
 	}
   $("#typeId").append(html);
 }
 
 //加载radio
 function loadRadioHtml(checked){
		var  yes_checked = false,no_checked = false;
		if (checked == 0){
			yes_checked = true;
		}
		if (checked == 1){
			no_checked = true;
		}
		var html ="";
		if (yes_checked){
			html += "  <input type='radio'  checked='checked'   name='isOPen' value='0' >是    " 
			html += "  <input type='radio'     name='isOPen' value='1' /> 否    "
		}else if (no_checked){
			html += "  <input type='radio'     name='isOPen' value='0' >是    " 
			html += "  <input type='radio'   checked='checked'  name='isOPen' value='1' /> 否    "
		} else {
			html += "  <input type='radio'     name='isOPen' value='0' >是    " 
			html += "  <input type='radio'    name='isOPen'  value='1'/> 否    "
		}
		$("#openId").append(html);
	}
 
 /**
  * 初始化数据类型
  * @returns 返回类型数据
  */
 function initTypes(){
 	var typeData;
 	$.ajax({
 		type:"post",
 		dataType:"json",
 		async: false ,
 		url: globalPath + "/cateParam/initTypes.do" ,
 		success:function(data){
 			typeData = data;
 		}
 	});
 	return typeData;
 }
 
 /**
  * 获取当前节点的根节点
  * @param treeNode treeNode节点
  * @returns 当前节点
  */
 function getCurrentRoot(treeNode){ 
 	if(treeNode.getParentNode()!=null){  
 		var parentNode = treeNode.getParentNode(); 
 		return getCurrentRoot(parentNode);
 	} else {
 		return treeNode;
 	}
 }
 
 function viewIQua(){
 	layer.tips($("#profileIQuaName").val(), "#profileIQuaName", {
	  tips: 3
	});
 }
 function viewSales(){
 	layer.tips($("#profileSalesName").val(), "#profileSalesName", {
	  tips: 3
	});
 }
</script>

</head>

<body>
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">产品管理</a></li><li><a href="javascript:void(0);">产品目录管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <!-- 内容 -->
   <div class="container">
   
     <div class="col-md-3 col-sm-4 col-xs-12">
  	   <div class="tag-box tag-box-v3 mt15">
	 	 <div><ul id="ztree" class="ztree s_ztree"></ul></div>
	   </div>
     </div>
     <div class=" tag-box tag-box-v3 mt15 col-md-9 col-sm-8 col-xs-12">
   	   <button class="btn btn-windows add" type="button" onclick="add();" >新增</button>
   	   <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
   	   <button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
       <div id="tableDivId"   class="content dis_none" >   
         <input id="operaFlag" type="hidden" name="operaName"  />
         <form id="fm">
       		<input type="hidden" id="pid" name="parentId" />
       		<input type="hidden" id="mainId" name="id" />
       		<input type="hidden" id="operaId" name="opera" />
       		<input type="hidden" id="isPublish" name="isPublish" />
       		<input type="hidden" id="classify" name="classify" />
            <table id="result"  class="table table-bordered table-condensedb" >
           	  <tbody>
           	 	<tr>
       			  <td class='info'>上级目录</td>
       			  <td id="parentNameId"></td>
           		</tr>
           		<tr>
           		  <td class='info'>品目名称<span class="red">*</span></td>
           		  <td id="cateTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateNameId" >
       		    	  <input id="cateId" type="text" name='name'/>
       		    	  <span class="add-on">i</span>
       		    	</div>
       		    	  <span id="cateTipsId" class="red clear span_style" />
           		  </td>
           		</tr>
           		<tr>
       			  <td class='info'>编码<span class="red">*</span></td>
       			  <td id="posTdId">
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id ="posNameId">
       				  <input  id="posId" type="text" name='code'/>
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style" />
       		      </td>
           	    </tr>
           	    <tr id="generaQuaTr" class="dnone">
       			  <td class='info'>通用资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" >
       				  <input id="generalIQuaId" type="hidden" name="generalQuaIds" />
       				  <input id="generalIQuaName" readonly="readonly" type="text" name='generalQuaNames' onclick="openLayer(1);"/>
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style" />
       		      </td>
           	    </tr>
           	    <tr id="typeTrId">
       			  <td class='info'>类型<span class="red">*</span></td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7" id="typeId" >
       				</div>
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTr" class="dnone">
       			  <td class='info'>专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" >
       				  <input id="profileIQuaId" type="hidden" name="profileQuaIds" />
       				  <input id="profileIQuaName" onmouseover="viewIQua();" onmouseout="viewIQua();" readonly="readonly" type="text" name='profileQuaNames' onclick="openLayer(2);" />
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style" />
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTr_sales" class="dnone">
       			  <td class='info'>物资销售型专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" >
       				  <input id="profileSalesId" type="hidden" name="profileSalesIds" />
       				  <input id="profileSalesName" onmouseover="viewSales();" onmouseout="viewSales();" readonly="readonly" type="text" name='profileSalesNames' onclick="openLayer(3);" />
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style" />
       		      </td>
           	    </tr>
           	    <tr>
       			  <td class='info'>是否公开<span class="red">*</span></td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7" id="openId" >
       				</div>
       		      </td>
           	    </tr>
           	    <!-- <tr class="dis_none" id="levelTrId">
           		  <td class='info'>供应商注册等级要求<span class="red">*</span></td>
           		  <td id="levelTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="level" >
       		    	  <input id="levelId" type="text" name='level' maxlength="1" onkeyup="value=value.replace(/[^\d]/g,'')"/>
       		    	  <span class="add-on">i</span>
       		    	</div>
       		    	  <span id="levelTipsId" class="red clear span_style" />
           		  </td>
           		</tr> -->
           	    <%-- <tr class="dis_none" id="engLevelTrId">
           		  <td class='info'>供应商注册等级要求<span class="red">*</span></td>
           		  <td id="engLevelTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="engLevel" >
       		    	  <input id="engLevelId" type="hidden" name='engLevel'/>
       		    	  <select multiple="multiple" id="engLevelSelect">
       		    	  	<c:forEach items="${levelList}" var="level">
       		    	  	  <option value="${level.id}">${level.name}</option>
       		    	  	</c:forEach>
       		    	  </select>
       		    	</div>
       		    	<span id="engLevelTipsId" class="red clear span_style" />
           		  </td>
           		</tr> --%>
           	    <tr>
       	    	  <td class='info'>图片</td>
       	    	  <td>
       	    		<div id="uploadBtnId" class="dis_none">
       	    		  <u:upload  id="uploadId"   businessId="${id}" multiple="true" exts="png,jpeg,jpg,bmp,gif"  auto="true" sysKey="2"/>
       	    		</div>
       	    		<div id="showFileId" class="picShow">
       	    		  <u:show showId="fileId" businessId="${id}" sysKey="2"/>
       	    		</div>
       	    	  </td>
           	    </tr>
           	    <tr>
       	          <td class='info'>描述</td>
       	          <td id="descTdId">
       	        	<textarea name='description' class="col-md-10 col-sm-10 col-xs-12 h80 textArea_resizeB"   id="descId"></textarea>
       	        	<span class="red" id="descTipsId"></span>
       	          </td>
           	    </tr>
           	   </tbody>
            </table>
            <div id="btnIds" class="dnone textc">
              <button  type='button' onclick='save()'  class='mr30  btn btn-windows save '>保存</button>
            </div>
           </form> 
         </div>
      </div>
	</div>
</body>
</html>
