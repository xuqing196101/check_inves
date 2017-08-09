<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>  
<%@ include file="/WEB-INF/view/common.jsp" %> 
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
 <title>产品目录管理</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<script type="text/javascript">
	var treeid = null , nodeName=null, level = null, typesObj = null;
	var datas;
	/**把这个注释的文档就绪函数放到本页面的最下边，因为本页面中有图片上传webuploader组件，影响IE8加载页面
	 **即在IE8下页面是不算加载成功，则导致文档就绪函数没有加载---MaMingwei
	 $(document).ready(function(){
		 ztreeInit();
	      var treeObj = $.fn.zTree.getZTreeObj("ztree");
	      var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
	      for(var i=0 ;i<nodes.length;i++){
		     if (nodes[i].status==1) {
				 check==true;
		      }
	       }
	    //初始化类型
		typesObj = initTypes();
	 }); **/
	 function ztreeInit(){
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
		 $.fn.zTree.init($("#ztree"),setting,datas);
	 }
	
	 
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
    		$("#generalIQuaName").attr("disabled", "true");
    		$("#profileIQuaName").attr("disabled", "true");
    		$("#profileSalesName").attr("disabled", "true");
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
	    	onClickTree(treeNode);
    		selectedNode = treeNode;
    	} else {
    		level = 1;
    		selectedNode = treeNode;
    		$("#tableDivId").addClass("dis_none");
    	}
    }

    function onClickTree(nodes){
    	$("#results").css("display","");
    	$("#result").css("display","none");
    	hideQuas();
 	    if (treeid==null){
 			layer.msg("请选择一个节点");
		}else{
		var node = getCurrentRoot(nodes);
		  $.ajax({
			url:"${pageContext.request.contextPath}/category/update.do?id="+treeid,
			dataType:"json",
			type:"POST",
			success:function(cate){
					$("#parentNameIds").html(nodeName);
					$("#cateNameIds").html(cate.name);
					$("#descriptions").html(cate.description);
					$("#codes").html(cate.code);
					$("#fileIds_downBsId").val(cate.id);
					showInit();
					if (node.classify && node.classify == "PROJECT" ){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "SERVICE"){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						$("#typeTrIds").show();
						if(cate.classify=="3"){
							$("#typeIds").html("物资生产，物资销售");
						}else if(cate.classify=="2"){
							$("#typeIds").html("物资销售");
						}else if(cate.classify=="1"){
							$("#typeIds").html("物资生产");
						}
					} else {
						$("#typeTrIds").hide();
					}
					if(cate.isPublish=="1"){
						$("#openIds").html("不公开");
					}else if(cate.isPublish=="0"){
						$("#openIds").html("公开");
					}
		      }
            });
        }
    }
    function showQuas(cate, type){
   	 $("#generaQuaTrs").hide();
   	 $("#profileQuaTrs").show();
   	 if (type == "GOODS") {
   		 $("#profileQuaTr_saless").show();
   		 var tdArr = $("#profileQuaTrs").children();
   		 tdArr.eq(0).html("物资生产型专业资质要求");
   	 } else {
   		 $("#profileQuaTr_saless").hide();
   	     var tdArr = $("#profileQuaTrs").children();
   		 tdArr.eq(0).html("专业资质要求");
   	 }
   	 if (cate != null && cate !="" && cate !="undefined" && cate !="null"){
   		 $("#generalIQuaNames").html(cate.generalQuaNames);
   		 $("#profileIQuaNames").html(cate.profileQuaNames);
   		 $("#profileSalesNames").html(cate.profileSalesQuaNames);
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
    	$("#posId").val("");
		  $("#cateId").val("");
    	$("#results").css("display","none");
    	$("#result").css("display","");
    	hideQua();
    	$("#parentNameId").attr("readonly","readonly");
		if (treeid==null) {
			layer.msg("请选择一个节点");
			return;
		}else{
    	    var zTree = $.fn.zTree.getZTreeObj("ztree");
			nodes = zTree.getSelectedNodes();
			var node = nodes[0];
			var nodes = getCurrentRoot(node);
			$("#operaFlag").val('add');
			if (nodes.classify && nodes.classify == "PROJECT"){
				showQua(null, nodes.classify);
			}
			if (nodes.classify && nodes.classify == "GOODS"){
				showQua(null, nodes.classify);
			}
			if (nodes.classify && nodes.classify == "SERVICE"){
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
						$("#generalIQuaName").removeAttr("disabled");
			    		$("#profileIQuaName").removeAttr("disabled");
			    		$("#profileSalesName").removeAttr("disabled");
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
				$("#parentNameId").val(node.name);
				$("#uploadBtnId").removeClass("dis_none");
				$("#btnIds").show();
				$("#posIdTr").show();
				$("#cateIdTr").show();
				$("#parentNIdTr").show();
				$("#uploadBtnIdTr").show();
				$("#descIdTr").show();
				$("#operaId").val('add');
				
			} 
		}
	}
    /** 重置 */
    function reset() {
    	$("#param").val("");
		$("#code").val("");
		$("#isCreate").val("");
		/* $("#levelId").val(""); */
		/* $("#engLevelId").val("");
		$("#engLevelSelect").find("option").each(function(index, element){
			element.selected = false;
		}); */
    }

	/**修改节点信息*/
    function update(nodes){
    	$("#results").css("display","none");
    	$("#result").css("display","");
    	hideQua();
    	$("#parentNameId").attr("readonly",false);
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
					$("#parentNameId").val(nodeName);
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
					if (node.classify && node.classify == "PROJECT"){
						showQua(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						showQua(cate, node.classify);
					}
					if (node.classify && node.classify == "SERVICE"){
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
		//如果是工程
		if (root.classify == "PROJECT"){
			$("#isProject").val(1);
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
    	$("#parentNIds").text("");
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
    		if(msg.filePath != null && msg.filePath !=""){
    			$("#parentNIds").text(msg.filePath);
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

	   zTree.reAsyncChildNodes(parentNode, type,silent); 
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
	  $("#generalIQuaName").removeAttr("disabled");
      $("#profileIQuaName").removeAttr("disabled");
	  $("#profileSalesName").removeAttr("disabled");
	  $("#operaId").val('edit');
	  $("#mainId").val(treeid);
	  $("#fileId_showdel").val("true");
	  $("#uploadBtnId").removeClass("dis_none");
      $("#btnIds").show();
      $("#posIdTr").show();
      $("#cateIdTr").show();
      $("#parentNIdTr").show();
      $("#uploadBtnIdTr").show();
      $("#descIdTr").show();
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
	 $("#generaQuaTr").hide();
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
 	 $("#profileSalesId").val("");
 	 $("#profileIQuaId").val("");
 	 $("#generalIQuaId").val("");   
 	 $("#profileSalesName").val("");
 	 $("#profileIQuaName").val("");
 	 $("#generalIQuaName").val("");
	 $("#generaQuaTr").hide();
	 $("#profileQuaTr").hide();
	 $("#profileQuaTr_sales").hide();
 }
 function hideQuas(){
 	 $("#profileSalesIds").val("");
 	 $("#profileIQuaIds").val("");
 	 $("#generalIQuaIds").val("");   
 	 $("#profileSalesNames").val("");
 	 $("#profileIQuaNames").val("");
 	 $("#generalIQuaNames").val("");
	 $("#generaQuaTrs").hide();
	 $("#profileQuaTrs").hide();
	 $("#profileQuaTr_saless").hide();
 }
 
 //初始化类型
 function openLayer(type){
	 var title ="";
	 var ids ="";
	 var names="";
	 if (type == 1){
		 ids = $("#generalIQuaId").val();
		 title ="添加通用资质要求";
	 }
	 if (type == 2){
	 	var root = getCurrentRoot(selectedNode);
		if (root.classify && root.classify == "PROJECT"){
			ids = $("#profileIQuaId").val();
			names=$("#profileIQuaName").val();
		 	title ="添加工程专业资质要求";
		 	type = 4;
		} else {
			 ids = $("#profileIQuaId").val();
			 names=$("#profileIQuaName").val();
			 title ="添加物资生产型专业资质要求";
		}
	 }
	 if (type == 3){
		 ids = $("#profileSalesId").val();
		 names=$("#profileSalesName").val();
		 title ="添加物资销售型专业资质要求";
	 }
	 layer.open({
			type : 2, 
			area : [ '800px', '590px' ],
			title : title,
			shadeClose : true,
			content : '${pageContext.request.contextPath}/qualification/initLayer.html?type=' + type +"&ids=" + ids+"&names="+encodeURI(names)
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
 function searchM() {
	 var param = $("#param").val();
	 var isCreate=$("#isCreate").val();
	 var code=$("#code").val();
	 if((param!=null&&param!="")||(isCreate!=null&&isCreate!="")||(code!=null&&code!="")){
		 var zNodes;
			var zTreeObj;
			var setting = {
					async:{
						autoParam:["id"],
						enable:true,
						url: "${pageContext.request.contextPath}/category/createtree.do"
					},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "parentId",
					}
				},
				callback: {
					onClick: zTreeOnClick
				},view: {
					showLine: true
				}
			};
			// 加载中的菊花图标
			 var loading = layer.load(1);
			
				$.ajax({
					url: "${pageContext.request.contextPath}/category/createtree.do",
					data: { "param" : encodeURI(param),"code":code,"isCreate":isCreate},
					async: false,
					dataType: "json",
					success: function(data){
						if (data.length == 1) {
							layer.msg("没有符合查询条件的产品类别信息！");
						} else {
							zNodes = data;
							zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
							zTreeObj.expandAll(true);//全部展开
						}
						// 关闭加载中的菊花图标
						
						layer.close(loading);
						
					}
				});
	 }else{
		 ztreeInit();
		
	 }
	 
		

	 
	 
	}

 /* function zTreeOnNodeCreated(event, treeId, treeNode) {
	   var param = $.trim($("input[name='param']").val());
	   var treeObj = $.fn.zTree.getZTreeObj("ztree");
	   var node = treeNode.getSelectedNodes();
	   //只有搜索参数不为空且该节点为父节点时才进行异步加载
	   if(param != "" && treeNode.isParent){
	     treeObj.reAsyncChildNodes(node, "refresh");
	   } 
	 }; */
  
</script>

</head>

<body>
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
			  <li><a href="javascript:void(0);">支撑系统</a></li>
			  <li><a href="javascript:void(0);">产品管理</a></li>
			  <li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/category/get.html')">产品目录管理</a></li>
		  </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
	<c:if test="${authType == 4}">
   <!-- 内容 -->
   <div class="container">
   
   <div class="search_detail">
    	<ul class="demand_list">
          <li class="fl pl5"><label class="fl">目录名称：</label><span><input type="text" id="param" name="param"/></span></li>
	      <li class="fl pl5"><label class="fl">目录编号：</label><span><input type="text" id="code" name="code"/></span></li>
	      <li class="fl pl5"><label class="fl">是否公开：</label><span>
	      	<select id="isCreate" name="isCreate" class="mb0 mt5 w170">
	      		<option value="">--请选择--</option>
	      		<option value="0">公开</option>
	      		<option value="1">不公开</option>
	      	</select>
	      </span></li>	
    	</ul>
	      
	    	<button type="button" onclick="searchM();" class="btn">查询</button>
	    	<button type="reset" onclick="reset()" class="btn">重置</button>
	        
    	  <div class="clear"></div>
     </div>
   
     

     <div class="col-md-3 col-sm-4 col-xs-12">
  	   <div class="tag-box tag-box-v3 mt15">
	 	 <ul id="ztree" class="ztree s_ztree"></ul>
	   </div>
     </div>
     <div class=" tag-box tag-box-v3 mt15 col-md-9 col-sm-8 col-xs-12">
      <c:if test="${buttonHidden==false}">
	   	   <button class="btn btn-windows add" type="button" onclick="add();" >新增</button>
	   	   <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
	   	   <%--<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>--%>
   	   </c:if>
       <div id="tableDivId"   class="content dis_none" >   
         <input id="operaFlag" type="hidden" name="operaName"  />
         <form id="fm">
       		<input type="hidden" id="pid" name="parentId" />
       		<input type="hidden" id="mainId" name="id" />
       		<input type="hidden" id="operaId" name="opera" />
       		<input type="hidden" id="isPublish" name="isPublish" />
       		<input type="hidden" id="classify" name="classify" />
       		<input type="hidden" id="isProject" name="isProjectCate" />
            <table id="result"  class="table table-bordered table-condensedb" >
           	  <tbody>
           	 	<tr id="parentNameIdTr" class="dnone">
       			  <td class='info'>上级目录<span class="red">*</span></td>
       			  <td id="parentNId">
       			      <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateNameId" >
       		    	  <input  id="parentNameId" type="text" name="parentNameId"/>
       		    	  <span class="add-on">i</span>
       		    	  </div>
       		    	  <span id="parentNIds" class="red clear span_style"></span>
       			  </td>
           		</tr>
           		<tr id="cateIdTr" class="dnone">
           		  <td class='info'>品目名称<span class="red">*</span></td>
           		  <td id="cateTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateNameId" >
       		    	  <input id="cateId" type="text" name='name'/>
       		    	  <span class="add-on">i</span>
       		    	</div>
       		    	  <span id="cateTipsId" class="red clear span_style"></span>
           		  </td>
           		</tr>
           		<tr id="posIdTr" class="dnone">
       			  <td class='info'>编码<span class="red">*</span></td>
       			  <td id="posTdId">
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id ="posNameId">
       				  <input  id="posId" type="text" name='code'/>
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style"></span>
       		      </td>
           	    </tr>
           	    <tr id="generaQuaTr" class="dnone">
       			  <td class='info'>通用资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0"  id="cateNameId">
       				  <input id="generalIQuaId" type="hidden" name="generalQuaIds" />
       				  <input id="generalIQuaName" readonly="readonly" type="text" name='generalQuaNames' onclick="openLayer(1);"/>
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style"></span>
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
       				  <span id="posTipsId" class="red clear span_style"></span>
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
       				  <span id="posTipsId" class="red clear span_style"></span>
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
           	    <tr id="uploadBtnIdTr">
       	    	  <td class='info'>图片</td>
       	    	  <td><!-- cnjewfn start -->
       	    		<div id="uploadBtnId" class="dis_none">
       	    		  <u:upload  id="uploadId"   businessId="${id}" multiple="true" exts="png,jpeg,jpg,bmp,gif"  auto="true" sysKey="2"/>
       	    		</div><!-- cnjewfn end -->
       	    		<div id="showFileId" class="picShow">
       	    		  <u:show showId="fileId" businessId="${id}" sysKey="2"/>
       	    		</div>
       	    	  </td>
           	    </tr>
           	    <tr id="descIdTr" class="dnone">
       	          <td class='info'>描述</td>
       	          <td id="descTdId">
       	        	<textarea name='description' class="col-md-10 col-sm-10 col-xs-12 h80 textArea_resizeB"   id="descId"></textarea>
       	        	<span class="red" id="descTipsId"></span>
       	          </td>
           	    </tr>
           	   </tbody>
            </table>
            <table id="results"  class="table table-bordered table-condensedb" >
           	  <tbody>
           	 	<tr>
       			  <td class='info w200'>上级目录</td>
       			  <td id="parentNId">
       			      <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="parentNameIds" >
       		    	  
       		    	  
       		    	</div>
       		    	  
       			  </td>
           		</tr>
           		<tr>
           		  <td class='info'>品目名称</td>
           		  <td id="cateTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateNameIds" >
       		    	 </div>
           		  </td>
           		</tr>
           		<tr>
       			  <td class='info'>编码</td>
       			  <td id="posTdId">
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id ="codes">
       				 </div>
       		      </td>
           	    </tr>
           	    <tr id="generaQuaTrs" class="dnone">
       			  <td class='info'>通用资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0"  id="generalIQuaNames">
       				 
       				</div>
       				 
       		      </td>
           	    </tr>
           	    <tr id="typeTrIds">
       			  <td class='info'>类型</td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7 p0" id="typeIds" >
       				</div>
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTrs" class="dnone">
       			  <td class='info'>专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="profileIQuaNames">
       				 
       				</div>
       				 
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTr_saless" class="dnone"> 
       			  <td class='info'>物资销售型专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="profileSalesNames">
       				 
       				</div>
       				  
       		      </td>
           	    </tr>
           	    <tr>
       			  <td class='info'>是否公开</td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7 p0" id="openIds" >
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
       	    		
       	    		<div id="showFileIds" class="picShow">
       	    		  <u:show showId="fileIds" businessId="${id}" delete="false" sysKey="2"/>
       	    		</div>
       	    	  </td>
           	    </tr>
           	    <tr>
       	          <td class='info'>描述</td>
       	          <td  id='descriptions'>
       	        	
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
	</c:if>
</body>
<script type="text/javascript">
	 ztreeInit();
     var treeObj = $.fn.zTree.getZTreeObj("ztree");
     var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
     for(var i=0 ;i<nodes.length;i++){
	     if (nodes[i].status==1) {
			 check==true;
	      }
      }
   //初始化类型
	typesObj = initTypes();
</script>
</html>
