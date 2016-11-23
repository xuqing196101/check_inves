<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<script type="text/javascript">
	function save(){
		var index = parent.layer.getFrameIndex(window.name);
		if(check()){
			$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/intelligentScore/operatorNode.do",
			    dataType:'json',
			    data : $('#formID').serialize(),
			    success: function(data) {
			    	//$("#total_score").text(data.message);
			    	//$("#remain_score").text(data.message);
			    	parent.window.location.reload();
			    	/* var zTree = $.fn.parent.zTree.getZTreeObj("treeDemo");  
			    	var treeNode = [{"id":data.pTreeNode.id,"pid":data.pTreeNode.id,"name",data.pTreeNode.name}]
			    	 zTree.addNodes(treeNode, { 
                                    	id:data.treeNode.id, 
                                        pId : data.treeNode.pid,  
                                        name : data.treeNode.name 
                                    }, true);  */
			    	parent.layer.close(index); //执行关闭
			    }
			});
		}
	}
	function check(){
		var name = $("#name").val();
		var maxScore = $("#maxScore").val();
		var remain_score = $("#remain_score").val();
		if(name==null ||name==undefined || name==""){
			layer.msg("打分项名称不可以为空");
			return false;
		}
		if(maxScore==null ||maxScore==undefined || maxScore==""){
			layer.msg("最高分不可以为空");
			return false;
		}
		if(maxScore!=null && maxScore!=undefined && maxScore!=""){
			if(Number(remain_score)-Number(maxScore)<0){
				layer.msg("最高分不能超过"+remain_score+"分");
				return false;
			}
			
		}else{
			layer.msg("最高分不可以为空");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container">
		<form action="" method="post"  id="formID">
			<input type="hidden" name="method" value="${method }">
			<input type="hidden" name="pid" value="${pid }">
			<%-- <input type="hidden" name="bidMethodId" value="${bidMethodId }"> --%>
			<input type="hidden" name="packageId" value="${packageId }">
			<input type="hidden" name="projectId" value="${projectId }">
			<input type="hidden" id="remain_score" value="${remainScore }">
			<input type="hidden" id="id" value="${markTerm.id }">
			<div>
				<ul class="list-unstyled list-flow p10_20 mt10">
					<li class="col-md-6 p0"><span class="">打分项名称：</span>
						<div class="input-append">
							<input class="span2 w180" name="name" type="text" id="name" value="${markTerm.name }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6 p0"><span class="">分值：</span>
						<div class="input-append">
							<input class="span2 w180" name="maxScore" type="text" id="maxScore"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类型:</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="typeName">
								<option value="">-请选择-</option>
								<option value="0">商务</option>
								<option value="1">技术</option>
							</select>
						</div></li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="mt40 tc mb50">
					<input type="button" class="btn  padding-right-20 btn_back margin-5" value="保存" onclick="save();"></input>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
