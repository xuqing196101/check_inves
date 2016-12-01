<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>   
<script type="text/javascript">
	var treeid = null , nodeName=null;
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
			    	onClick:zTreeOnClick,//点击节点触发的事件
       			    
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
    function zTreeOnClick(event,treeId,treeNode){
    	treeid = treeNode.id;
    	var node = treeNode.getParentNode();
    	if (node && node != null ) {
    		resetTips();
    		$("#tableDivId").removeClass("dis_none");
    		$("#uploadBtnId").addClass("dis_none");
			$("#btnIds").hide();
			$("#fileId_downBsId").val(treeNode.id);
			$("#fileId_showdel").val("false");
			$("#uploadBtnId").hide();
	    	nodeName = node.name;
    		update();
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
		if (treeid==null) {
			layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
			return;		
		}else{
    	    var zTree = $.fn.zTree.getZTreeObj("ztree");
			nodes = zTree.getSelectedNodes();
			var node = nodes[0];
			$("#operaFlag").val('add');
			if (node) {
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
    }

	/**修改节点信息*/
    function update(){
 		if (treeid==null){
 			layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
		}else{
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
					$("#posId").val(cate.position);
					$("#descId").val(cate.description);
					showInit();
		      }
            });
        }
    }
    
	/** 保存 */
	function save(){
		var operaValue = $("#operaFlag").val();
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
      update();
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
       		    	  <span id="cateTipsId" class="red clear" />
       		    	</div>
           		  </td>
           		</tr>
           		<tr>
       			  <td class='info'>排序<span class="red">*</span></td>
       			  <td id="posTdId">
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id ="posNameId">
       				  <input  id="posId" type="text" name='position'/>
       				  <span class="add-on">i</span>
       				  <span id="posTipsId" class="red clear" />
       				</div>
       		      </td>
           	    </tr>
           	    <tr>
       	    	  <td class='info'>图片</td>
       	    	  <td>
       	    		<div id="uploadBtnId" class="dis_none">
       	    		  <u:upload  id="uploadId"   businessId="${id}" auto="true" sysKey="2"/>
       	    		</div>
       	    		<div id="showFileId">
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
