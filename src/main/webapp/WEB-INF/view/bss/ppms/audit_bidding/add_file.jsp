<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function OpenFile(fileId) {
		setTimeout(open_file(fileId),5000);
	}
	
	function open_file(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != '0'){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId="+fileId, true, false, 'word.document');// 异步加载, 服务器文件路径
		} 
		var filePath = "${filePath}";
		if (filePath != null && filePath != undefined && filePath != ""){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+filePath, true, false, 'word.document');// 异步加载, 服务器文件路径
		}
	}
	
	
	function exportWord() {
		var obj = document.getElementById("TANGER_OCX");
		// 参数说明
		// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
		//obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
	}
	
	function queryVersion(){
	
		var obj = document.getElementById("TANGER_OCX");
		var v = obj.GetProductVerString();
		obj.ShowTipMessage("当前ntko版本",v);
	}
	
	function inputTemplete(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function saveFile(flag){
		var projectId = $("#projectId").val();
		var flowDefineId = $("#flowDefineId").val();
		var projectName = $("#projectName").val();
		var obj = document.getElementById("TANGER_OCX");
		//提交
		if (flag == "1") {
			//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId="+projectId+"&flowDefineId="+flowDefineId+"&flag="+flag, "ntko", "", projectName+"_招标文件.doc", "MyFile");
			alert("招标文件已提交");
			$("#handle").attr("class","dnone");
			$("#audit_file_add").attr("class","dnone");
			$("#audit_file_view").removeAttr("class","dnone");
		} 
		
		//暂存
		if (flag == "0") {
			//参数说明
			//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId="+projectId+"&flowDefineId="+flowDefineId+"&flag="+flag, "ntko", "", projectName+"_招标文件.doc", "MyFile");
			//obj.ShowTipMessage("提示","招标文件已上传至服务器");
			alert("招标文件已暂存");
		}
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
		
	function jump(url){
      	$("#open_bidding_main").load(url);
    }
    
    function confirmOk(obj, id, flowDefineId){
      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId="+id+"&flowDefineId="+flowDefineId,
	 				dataType: 'json',  
	 	       		success:function(result){
	                   	layer.msg(result.msg,{offset: '222px'});
	                   	$("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
	                    $("#queren").remove();
	                },
	                error: function(result){
	                    layer.msg("确认失败",{offset: '222px'});
	                }
	 	       	});
	 		});
      }
</script>

<!-- 打开文档后只读 -->
<script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		/* var obj = document.getElementById("TANGER_OCX");
		var st = $("#ope").val();
		if(st == 'view'){
			obj.SetReadOnly(true);
		} */
</script>
</head>

<body onload="OpenFile('${fileId}')">
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	   	 <c:if test="${ope == 'add' }">
		     <li>
			   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
			   <i></i>
			 </li>
			 
			 <%-- <li >
			   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
			   <i></i>							  
			 </li> --%>
		     <li>
			   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
			   <i></i>
			 </li>
			 <li>
			   <a  href="${pageContext.request.contextPath}/project/packDetail.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、移除明细</a>
			   <i></i>
			 </li>
			 <li class="active">
			   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}" >
			     04、招标文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
			   </a>
			 </li>
	   	 </c:if>
	   	 <c:if test="${ope == 'view' }">
	   	 	<li >
		   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${project.id}&flowDefineId=${flowDefineId }" >01、资格性和符合性审查</a>
		   <i></i>
		 </li>
		 <%-- <li>
		   <a href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">02、符合性关联</a>
		   <i></i>							  
		 </li> --%>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">02、经济和技术评审细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="${pageContext.request.contextPath}/project/packDetail.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、移除明细</a>
		   <i></i>
		 </li>
		 <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${project.id}&flowDefineId=${flowDefineId }" >
		         04、招标文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、招标文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
		   </a>
		   <i></i>
		 </li>
		 <li>
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a></c:if>
		    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
		 </li>
	   	 </c:if>
	   </ul>
	 </div>
	 <!-- 按钮 -->
	 <c:if test="${project.confirmFile != 1 && ope =='add'}">
	     <div class="mt5 mb5 fr" id="handle">
	      	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
	      	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
	     	<!-- <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input> -->
	        <input type="button" class="btn btn-windows save" onclick="saveFile('0')" value="暂存">
	        <input type="button" class="btn btn-windows save" onclick="" value="生成正式采购文件">
	   		<input type="button" class="btn btn-windows git" onclick="saveFile('1')" value="提交至采购管理部门"></input>
	    </div>
	 </c:if>
	<form id="MyFile" method="post" class="h800">
		<c:if test="${project.confirmFile != 1}">
			<div class="" id="audit_file_add">
				<span class="fl">上传审批文件</span>
				<div>
			        <u:upload id="a" buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp" multiple="true"  businessId="${project.id}"  sysKey="${sysKey}" typeId="${typeId}" auto="true" />
			        <u:show  showId="b" groups="b,c,d" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}"/>
				</div>
			</div>
			<div class="dnone" id="audit_file_view">
				<span class="fl">审批文件</span>
		        <u:show  showId="d" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}"/>
			</div>
		</c:if>
		
		<c:if test="${project.confirmFile == 1}">
			<div class="clear" >
				<span class="fl">审批文件</span>
		        <u:show  showId="c" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}"/>
			</div>
		</c:if>
		<input type="hidden" id="ope" value="${ope}">
		<input type="hidden" id="confirmFileId" value="${project.confirmFile}">
		<input type="hidden" id="flowDefineId" value="${flowDefineId }">
    	<input type="hidden" id="projectId" value="${project.id}">
    	<input type="hidden" id="projectName" value="${project.name}">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
		<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">采购管理部门意见</span>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
        	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80"  maxlength="100" name="address" title="不超过100个字">${user.address}</textarea>
        </div>
	</form>
</body>
</html>
