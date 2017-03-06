<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  
  $(function (){
	  var  pStatus = "${pStatus}";
	  if(pStatus == 'ZBWJYTJ'){
	     //采购部门意见
	      $("#pcReason").removeAttr("readonly");
	      //事业部门意见
	      $("#causereason").removeAttr("readonly");
	      //财务部门意见
	      $("#financereason").removeAttr("readonly");
	      //最终意见
	      $("#finalreason").removeAttr("readonly");
	  }
	});
  
	function OpenFile(fileId) {
		setTimeout(open_file(fileId),5000);
	}
	
	function open_file(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != '0'){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/loadFile.html?fileId="+fileId, true, false, 'word.document');// 异步加载, 服务器文件路径
		} else {
		  var filePath = "${filePath}";
      if (filePath != null && filePath != undefined && filePath != ""){
	      obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/downloadFile.html?filePath="+filePath, true, false, 'word.document');// 异步加载, 服务器文件路径
	    }
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
		var projectName = $("#projectName").val();
		var obj = document.getElementById("TANGER_OCX");
		
		//提交
    if (flag == "1") {
      //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
      obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId="+projectId+"&flag="+flag, "ntko", "", projectName+"_招标文件.doc", "MyFile");
      alert("招标文件已提交");
      $("#handle").attr("class","dnone");
      $("#audit_file_add").attr("class","dnone");
      $("#audit_file_view").removeAttr("class","dnone");
      $("#cgdiv").addClass("dnone");
    }
    
    //暂存
    if (flag == "0") {
      //参数说明
      //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
      obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId="+projectId+"&flowDefineId="+flowDefineId+"&flag="+flag, "ntko", "", projectName+"_招标文件.doc", "MyFile");
      //obj.ShowTipMessage("提示","招标文件已上传至服务器");
      alert("招标文件已暂存");
    }
		
	}
	
	function updateAudit(status){
  var formData = $("#MyFile").serialize();
      formData = decodeURIComponent(formData, true);
   if(status == 2){
      if($("#pcReason").val() != null &&  $("#causereason").val() != null && $("#financereason").val() != null &&   $("#finalreason").val() != null ){
        ajax(formData,status);
      }else{
        alert("理由不能为空");
      }
    }else if(status ==3 || status ==4){
      ajax(null,status);
    }
  }
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	
	
	function ajax(reason,status){
     var projectId = $("#projectId").val();
        var flowDefineId = $("#flowDefineId").val();
        var process = "${process}";
        $.ajax({
              url:"${pageContext.request.contextPath}/AdAuditbidding/updateAuditStatus.html?projectId="+projectId+"&flowDefineId="+flowDefineId+"&status="+status+"&"+encodeURI(encodeURI(reason)),
              dataType: 'json',  
              success:function(result){
                if(result == 'SUCCESS'){
                  if(process != null && process == 1){
                    window.location.href = "${pageContext.request.contextPath}/AdAuditbidding/list.html";   
                  }
                     $("#cgspan").addClass("dnone");
                      $("#cgdiv").addClass("dnone");
                }
              },error: function(result){
                          layer.msg("失败",{offset: '222px'});
                      }
                });
  }
	
		
	function jump(url){
      	$("#open_bidding_main").load(url);
    }
    
    function confirmOk(obj, id){
      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/Adopen_bidding/confirmOk.html?projectId="+id,
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

</head>

<body onload="OpenFile('${fileId}')">
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	   	 <c:if test="${ope == 'add' }">
		     <li>
			   <a  href="${pageContext.request.contextPath}/adFirstAudit/toAdd.html?projectId=${project.id}" >01、资格性和符合性审查</a>
			   <i></i>
			 </li>
			 
			 <%-- <li >
			   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
			   <i></i>							  
			 </li> --%>
		     <li>
			   <a  href="${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${project.id}">02、经济和技术评审细则</a>
			   <i></i>
			 </li>
			 <li class="active">
			   <a  href="${pageContext.request.contextPath}/Adopen_bidding/bidFile.html?id=${project.id}" >
			     03、招标文件
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
		   <a  href="${pageContext.request.contextPath}/Adopen_bidding/firstAduitView.html?projectId=${project.id}" >01、资格性和符合性审查</a>
		   <i></i>
		 </li>
		 <%-- <li>
		   <a href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">02、符合性关联</a>
		   <i></i>							  
		 </li> --%>
	     <li>
		   <a  href="${pageContext.request.contextPath}/adIntelligentScore/packageListView.html?projectId=${project.id}">02、经济和技术评审细则</a>
		   <i></i>
		 </li>
		 <li class="active">
		   <a  href="${pageContext.request.contextPath}/Adopen_bidding/bidFileView.html?id=${project.id}" >
		         03、招标文件
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
		    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}');" id="queren">05、确认</a></c:if>
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
	        <input type="button" class="btn btn-windows save" onclick="saveFile('0')" value="暂存"></input>
	   		<input type="button" class="btn btn-windows git" onclick="saveFile('1')" value="提交"></input>
	    </div>
	 </c:if>
	<form id="MyFile" method="post" class="h800">
		<input type="hidden" id="ope" value="${ope }">
		<input type="hidden" id="confirmFileId" value="${project.confirmFile}">
    	<input type="hidden" id="projectId" value="${project.id }">
    	<input type="hidden" id="projectName" value="${project.name }">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
	</form>
</body>
</html>
