<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<head>


<%@ include file="/WEB-INF/view/common.jsp"%>
    
    <%
    	String packageId = (String)request.getAttribute("packageId");
    	String bidMethodId = (String)request.getAttribute("bidMethodId");
    	String projectId = (String)request.getAttribute("projectId");
     %>
<script type="text/javascript">
	/* layer.config({
		    extend: 'extend/layer.ext.js'
		}); */
    //${pageContext.request.contextPath}/intelligentScore/getMarkTermTree.do?packageId=A1CC4A603B6F466C936BBA2BA7BC2317
     var id = $("#packageId").val();
     
     var setting = {  
        async : {  
            enable : true,//开启异步加载处理  
            url : "${pageContext.request.contextPath}/intelligentScore/getMarkTermTree.do?packageId=<%=packageId%>"+"&bidMethodId=<%=bidMethodId%>"+"&projectId=<%=projectId%>",  
            autoParam : ["id"],  
            dataFilter : filter,  
            //contentType : "application/json",  //提交参数体式格式，这里 JSON 格局，默认form格局  获取参数必须默认form格局
            type : "post"  
        },  
        view : {  
            expandSpeed : "",  
            addHoverDom : addHoverDom,  
            removeHoverDom : removeHoverDom,  
            selectedMulti : false  
        },  
        edit : {  
            enable : true  
        },  
        data : {  
            simpleData : {  
                enable : true  
            }  
        },  
        callback : {  
            beforeRemove : beforeRemove,  
            beforeRename : beforeRename, 
            //beforeEditName: beforeEditName,  
            onClick:zTreeOnClick 
        }  
    };  
    function filter(treeId, parentNode, childNodes) {  
        if (!childNodes)  
            return null;  
        for (var i = 0, l = childNodes.length; i < l; i++) {  
            //childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
        }  
        return childNodes;  
    }  
    function beforeRemove(treeId, treeNode) {  
    	var content = "您确认删除节点" + treeNode.name + "吗?";
    	var packageId = $("#packageId").val();
    	layer.confirm(content, {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				$.post("${pageContext.request.contextPath}/intelligentScore/operatorNode.do",{id:treeNode.id,method:"delnode",packageId:packageId},
				function(){
					window.location.reload();
				}
				); 
       		   // $("#addBtn_" + treeNode.tId).unbind().remove();  
				layer.close(index); 
		});
		
    }  
    function beforeRename(treeId, treeNode, newName) {  
    	var packageId = $("#packageId").val();
    	var projectId = $("#projectId").val();
    	var remainScore = $("#remainScore").val();
    	var id = $("#id").val();
        if (newName.length == 0) {  
            layer.mag("节点名称不能为空.");  
            return false;  
        }  
        var param = "id=" + treeNode.id + "&name=" + newName;
        $.post("${pageContext.request.contextPath}/intelligentScore/operatorNode.do",{id:treeNode.id,name:newName,method:"updatenode",packageId:packageId,projectId:projectId,remainScore:remainScore});  
        return true;  
    }  
  	function  beforeEditName(){
  		layer.open({
					  type: 2,
					  title: '修改评分项',
					  shadeClose: true,
					  shade: 0.4,
					  area: ['500px', '20%'],
					  offset: '100px',
					  content: "${pageContext.request.contextPath}/intelligentScore/addNode.do?pid="+treeNode.id+"&method=addnode"+"&packageId="+packageId+"&projectId="+projectId+"&remainScore="+remainScore //iframe的url
				}); 
  	}
    function addHoverDom(treeId, treeNode) { 
    	//最低评标法  不需要添加打分项了
    	var projectWay = $("#projectWay").val();
    	
    	
    	if(treeId==null || treeId==undefined || treeId==""){
    		layer.msg("请先完善根节点评标办法");
    	}
    	var packageId = $("#packageId").val();
    	var projectId = $("#projectId").val();
    	var remainScore = $("#remainScore").val();
    	var id = $("#id").val();
    	
        var sObj = $("#" + treeNode.tId + "_span");  
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)  
            return;  
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId  
                + "' title='add node' onfocus='this.blur();'></span>";  
        sObj.after(addStr);  
        var btn = $("#addBtn_" + treeNode.tId);  
        if (btn)  
            btn.bind("click", function() {  
                //var Ppname = prompt("请输入新节点名称");  
                //alert(treeNode.id=="");
                if(projectWay !=null && projectWay != undefined && projectWay !='' && projectWay == 'XJCG' ){
                	alert("询价采购不需要添加节点");
    				//layer.msg("询价采购不需要添加节点");
    				return;
    			}
                if(treeNode.id==null || treeNode.id==undefined || treeNode.id==""){
    				alert("请先完善根节点评标办法");
    				return;
    			}
                layer.open({
					  type: 2,
					  title: '添加评分项',
					  shadeClose: true,
					  shade: 0.4,
					  area: ['500px', '20%'],
					  offset: '100px',
					  content: "${pageContext.request.contextPath}/intelligentScore/addNode.do?pid="+treeNode.id+"&method=addnode"+"&packageId="+packageId+"&projectId="+projectId+"&remainScore="+remainScore //iframe的url
				}); 
               /*  var Ppname="";
               layer.prompt({
                    title: '输入节点名称',
                    formType: 2,
                    offset:'100px'
                }, function (str,index) {
                    if (str) {
                        //需要执行的方法
                        if (str == null) {  
                    return;  
                } else if (str == "") {  
                    alert("节点名称不能为空");  
                } else {  
                     
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
                    $.post(  
                           "${pageContext.request.contextPath}/intelligentScore/operatorNode.do",
                           {pid:treeNode.id,name:str,method:"addnode",packageId:packageId,projectId:projectId}, function(data) {  
                                if (data.success) {  
                                    //var treenode = $.trim(data);  
                                    zTree.addNodes(treeNode, { 
                                    	id:data.message, 
                                        pId : treeNode.id,  
                                        name : str  
                                    }, true);  
                                }  
                            });  
                } 
                        layer.close(index);
                    }else{
                    	consolr.dir(2);
                    };
                }); */
               
				 //
				
				 //
                 
  
            });  
    };  
    function removeHoverDom(treeId, treeNode) {  
       $("#addBtn_" + treeNode.tId).unbind().remove();  
    };  
    $(document).ready(function() {  
        $.fn.zTree.init($("#treeDemo"), setting);  
  
    });  
    function zTreeOnClick(event,treeId,treeNode){
    	var id = $("#packageId").val();
    	
		console.dir(treeNode);
		if(treeNode.bidMethodId!=null && treeNode.bidMethodId!=""){
			setBidMothodForm(treeNode.bidMethodId,id);
			$("#show_content_div").hide();
			$("#bid_method_form").show();
		}else if(!treeNode.isParent && treeNode.id!=""){
			$("#treebody").attr("src","${pageContext.request.contextPath}/intelligentScore/gettreebody.do?id="+treeNode.id+"&packageId="+id+"&name="+encodeURI(encodeURI(treeNode.name))+"&projectId=${projectId}");
			$("#show_content_div").show();
			$("#bid_method_form").hide();
		}else if (treeNode.id==""){
			$("#show_content_div").hide();
			$("#bid_method_form").show();
		}else{
			$("#show_content_div").hide();
			$("#bid_method_form").hide();
		}
		/* if(!treeNode.isParent && treeNode.bidMethodId==""){
			$("#treebody").attr("src","${pageContext.request.contextPath}/intelligentScore/gettreebody.do?id="+treeNode.id+"&packageId="+id+"&name="+encodeURI(encodeURI(treeNode.name)));
			$("#show_content_div").show();
			$("#bid_method_form").hide();
		}else{
			setBidMothodForm(treeNode.bidMethodId,id);
			$("#show_content_div").hide();
			$("#bid_method_form").show();
		} */
	}
	function setBidMothodForm(id,packageId){
		$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/intelligentScore/getBidMethodById.do",
			    dataType:'json',
			    data : {id:id,packageId:packageId},
			    success: function(data) {
			    	console.dir(data.obj.id);
			        //layer.msg(data.message,{offset: '222px'});
			    }
			});
	}
	function choseModel(){
		var model = $("#model").val();
		console.dir(model);
		if(model==""){
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="1"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			$("#model7 tbody tr").clone().appendTo("#show_table tbody");
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			$("#model8 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelTwoAddSubstact(){
		var model = $("#model").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function reList(projectId){
		window.location.href = "${pageContext.request.contextPath}/intelligentScore/packageList.do?projectId="+projectId;
	}
	function show(){
		 var typeName = $("#typeName").val();
		 console.dir(typeName);
		 if(typeName!=null && typeName!="" && typeName=="1"){
		 	$("#floating_ratio").show();
		 }else{
		 	$("#floatingRatio").val("");
		 	$("#floating_ratio").hide();
		 }
	}
	function pageOnLoad(){
		var projectName = $("#projectName").val();
		projectName = projectName + "_评分细则";
		var name = $("#name").val();
		var projectWay = $("#projectWay").val();
		$("#typeName").val($("#type").val());
		if(name==null ||name==undefined || name==""){
		
			$("#name").val(projectName);
		}
		if(projectWay !=null && projectWay != undefined && projectWay !='' && projectWay == 'XJCG' ){
			$("#typeName").empty();
			$("#typeName").append("<option value='1'>最低评标法</option>");
		}
	}
	function save(){
		//$("#formID").attr("action","${pageContext.request.contextPath}/intelligentScore/operatorBidMethod");
		//$("#formID").submit();
		$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/intelligentScore/operatorBidMethod.do",
			    dataType:'json',
			    data : $('#formID').serialize(),
			    success: function(data) {
			    	$("#total_score").text(data.message);
			    	$("#remain_score").text(data.message);
			    	window.location.reload();
			        //layer.msg(data.message,{offset: '222px'});
			    }
			});
	}
</script>  
<style type="text/css">  
.ztree li span.button.add {  
    margin-left: 2px;  
    margin-right: -1px;  
    background-position: -144px 0;  
    vertical-align: top;  
    *vertical-align: middle  
}  
</style>  
</head>  
<body onload="pageOnLoad();">
	
	<input id="bidMethodId" type="hidden" value="${bidMethodId }">
	<input id="projectName" type="hidden" value="${project.name }">
	<input id="projectWay" type="hidden" value="${project.dictionary.code }">
	<input id="remainScore" type="hidden" value="${bidMethod.remainScore }">
	<c:if test="${bidMethod.typeName!=null && bidMethod.typeName!='' && bidMethod.typeName!='1' }">
		<div>
		<h2 class="panel-title heading-sm pull-left">
			<i class="fa fa-bars"></i> 总分: <span id="total_score" class="label rounded-2x label-u">${bidMethod.maxScore }</span>
		</h2>
		<h2 class="panel-title heading-sm pull-left">
			<i class="fa fa-bars"></i> 还剩: <span id="remain_score" class="label rounded-2x label-u">${bidMethod.remainScore }</span>
		</h2>
		<input type="button" class="btn  padding-right-20 btn_back margin-5 ml70"
					onclick="reList('${projectId}');" value="返回">
	</div>
	</c:if>
	<div class="container content">
		<div class="row">
				<div class="col-md-2 col-sm-3 col-xs-12" id="show_tree_div">
					<div class="tag-box tag-box-v3">
						<ul id="treeDemo" class="ztree">
						</ul>
					</div>
				</div>
				<div class="tag-box tag-box-v4 col-md-10 col-sm-9 col-xs-12" id="bid_method_form">
					<form action="" method="post" id="formID">
						<input  type="hidden" id="id" name="id" value="${bidMethod.id }">
						<input  type="hidden" id="type" value="${bidMethod.typeName }">
						<input id="packageId" name="packageId" type="hidden" value="${packageId }">
						<input id="projectId" name="projectId" type="hidden" value="${projectId }">
						<ul class="list-unstyled list-flow" style="margin-left: 0px;">
							<li class="p0"><span class=""><i class="red ">*</i>名称:</span>
								<div class="">
									<input class="" name="name" id="name" placeholder=""
										 type="text" value="${bidMethod.name }">
								</div></li>
							<li class="col-md-6 p0"><span class="">评分方法:</span> <select
								class="w180" name="typeName" id="typeName" type="text" onchange="show();">
									<option value="">请选择</option>
									<option value="0">综合评标法</option>
									<option value="1">最低评标法</option>
									<option value="2">基准价评标法</option>
									<option value="3">性价比评标法</option>
							</select></li>
							<li class="col-md-6 p0" style="display: none;" id="floating_ratio"><span class=""><i
									class="red ">*</i>下浮比例:</span>
								<div class="">
									<input class="" name="floatingRatio" id="floatingRatio" type="text" value="${bidMethod.floatingRatio }">
								</div>
							</li>
							<li class="col-md-6 p0"><span class=""><i
									class="red ">*</i>最大分值:</span>
								<div class="">
									<input class="" name="maxScore" type="text" value="${bidMethod.maxScore }">
								</div>
							</li>
							<li class="col-md-6 p0"><span class=""><i
									class="red ">*</i>备注:</span>
								<div class="">
									<textarea class="w180" name="remark" type="text">${bidMethod.remark}</textarea>
								</div>
							</li>
						</ul>
						<div class="col-md-12">
							<div class="mt40 tc mb50">
								<input type="button"
									class="btn  padding-right-20 btn_back margin-5" value="保存" onclick="save();"></input>
							</div>
						</div>
					</form>
				</div>
				<div class="tag-box tag-box-v4 col-md-9 fl " id="show_content_div" style="height: auto;width: 446px; display: none">
				
					 <iframe id="treebody" name="treeframe"
				src=""
				frameborder="0" style="width: 100%;height: 100%;"> </iframe> 
				 <%-- <iframe id="treebody" name="treeframe"
				src="${pageContext.request.contextPath}/intelligentScore/gettreebody.do?packgeId=${packageId}"
				frameborder="0" style="width: 100%;height: 100%;"> </iframe>  --%>
				</div>
			</div>
	</div>
	<!-- 八大模型 -->
	
	<!-- 八大模型 -->
</body>  
</html>  