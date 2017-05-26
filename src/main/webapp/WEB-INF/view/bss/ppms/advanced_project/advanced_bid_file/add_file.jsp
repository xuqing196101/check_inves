<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript">

      function OpenFile(fileId) {
        setTimeout(open_file(fileId), 5000);
      }

      function open_file(fileId) {
        var obj = document.getElementById("TANGER_OCX");
        obj.Menubar = true;
        obj.Caption = "( 双击可放大 ! )";
        if(fileId != '0') {
          obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/loadFile.html?fileId=" + fileId, true, false, 'word.document'); // 异步加载, 服务器文件路径
        } else {
          var filePath = "${filePath}";
          if(filePath != null && filePath != undefined && filePath != "") {
            obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/downloadFile.html?filePath=" + filePath, true, false, 'word.document'); // 异步加载, 服务器文件路径
          }
        }
      }

      function exportWord() {
        var obj = document.getElementById("TANGER_OCX");
        // 参数说明
        // 1.url  2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
        //obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
      }

      function queryVersion() {

        var obj = document.getElementById("TANGER_OCX");
        var v = obj.GetProductVerString();
        obj.ShowTipMessage("当前ntko版本", v);
      }

      function inputTemplete() {
        var obj = document.getElementById("TANGER_OCX");
      }

      function saveFile(flag) {
        var flowDefineId = $("#flowDefineId").val();
        var projectId = $("#projectId").val();
        var projectName = $("#projectName").val();
        var obj = document.getElementById("TANGER_OCX");

        //提交
        if(flag == "1") {
          //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
          obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
          	alert("采购文件已提交");
			$("#handle").attr("class","dnone");
			$("#audit_file_add").attr("class","dnone");
			$("#audit_file_view").removeAttr("class","dnone");
			$("#cgdiv").addClass("dnone");
        }

        //暂存
        if(flag == "0") {
          //参数说明
          //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
          obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
          //obj.ShowTipMessage("提示","招标文件已上传至服务器");
          alert("采购文件已暂存");
        }

      }

      /**
	通过|退回
	*/
	function updateAudit(status){
	var formData = $("#MyFile").serialize();
      formData = decodeURIComponent(formData, true);
     var pcReason = $("#pcReason").val();
     var causereason = $("#causereason").val();
     var financereason = $("#financereason").val();
     var finalreason = $("#finalreason").val();
	 if(status == 2){
		  if($("#pcReason").val() != null &&  $("#causereason").val() != null && $("#financereason").val() != null &&   $("#finalreason").val() != null ){
			  /* ajax(formData,status); */
			  ajax(pcReason,causereason,financereason,finalreason,status);
		  }else{
			  alert("理由不能为空");
		  }
		}else if(status ==3 || status ==4){
			ajax(pcReason,causereason,financereason,finalreason,status);
		}
	}
	
	function ajax(pcReason,causereason,financereason,finalreason,status){
		 var projectId = $("#projectId").val();
	      var flowDefineId = $("#flowDefineId").val();
	      var process = "${process}";
	      $.ajax({
	      		type: "POST",
	            url:"${pageContext.request.contextPath}/AdAuditbidding/updateAuditStatus.html?projectId="+projectId+"&flowDefineId="+flowDefineId+"&status="+status,
	            dataType: 'json', 
	            data:{"pcReason":pcReason,"causeReason":causereason,"financeReason":financereason,"finalReason":finalreason}, 
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

      function closeFile() {
        var obj = document.getElementById("TANGER_OCX");
        obj.close();
      }

      function jump(url) {
        $("#open_bidding_main").load(url);
      }

      function confirmOk(obj, id, flowDefineId) {
        layer.confirm('您已经确认了吗?', {
          title: '提示',
          offset: ['100px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          $.ajax({
            url: "${pageContext.request.contextPath}/Adopen_bidding/confirmOk.html?projectId=" + id + "&flowDefineId=" + flowDefineId,
            dataType: 'json',
            success: function(result) {
              layer.msg(result.msg, {
                offset: '222px'
              });
              $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
              $("#queren").remove();
            },
            error: function(result) {
              layer.msg("确认失败", {
                offset: '222px'
              });
            }
          });
        });
      }
      
      /**生成正式的采购文件*/
    function oncreate(){
    	var obj = document.getElementById("TANGER_OCX");
    	
    	obj.SaveToLocal("E:\\采购文件.doc",false,true);
    
    }
    </script>
<!-- 打开文档后调用  -->
<script type="text/javascript"  for="TANGER_OCX" event="OnDocumentOpened(a,b)">
        //声明控件
		var obj = document.getElementById("TANGER_OCX");
// 转换日期格式  如果是CST 日期  转换 GMT 日期
function getTaskTime(strDate) { 
    if(null==strDate || ""==strDate){  
        return "";  
    }
    if(strDate.indexOf("GMT")>0){
      return new Date(strDate).Format("yyyy年MMdd日hh时mm分");
    }
    var dateStr=strDate.trim().split(" ");  
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";  
    var date = new Date(Date.parse(strGMT));  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();    
    minute = minute < 10 ? ('0' + minute) : minute;  
    var second = date.getSeconds();  
    second = second < 10 ? ('0' + second) : second;  
    return y+"年"+m+"月"+d+"日"+h+"时"+minute+"分";  
};  
       //通用方法 判断是否存在 存在则行
	function replaceContent(begin,end,date) {
	   if(obj.ActiveDocument.Bookmarks.Exists(begin) && obj.ActiveDocument.Bookmarks.Exists(end)){
		obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End,ActiveDocument.Bookmarks(end).Range.End).Select();
		obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
		obj.ActiveDocument.Application.Selection.TypeText(date);
		obj.ActiveDocument.Bookmarks.Add(end);
	   }
	}
    function loadWord(begin,end,url){
     	obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End,ActiveDocument.Bookmarks(end).Range.Start).Select();
		obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
		obj.AddTemplateFromURL(url, false, true);
			
    }
	/**
	 * ntko 控件加载玩之后调用
	 * **/
	$(function() {
		// 组合word文档
		var marks = obj.ActiveDocument.Bookmarks;//获取所有的书签
		var filePath = "${filePath}";
		if (filePath != null && filePath != "") {
			var pathArray = filePath.split(",");
			if (pathArray.length > 1) {
				//项目名称
				replaceContent("SYS_1", "SYS_1_1", "${project.name}");
				//项目编号
				replaceContent("SYS_2", "SYS_2_2", "${project.projectNumber}");
				//招标人
				replaceContent("SYS_3", "SYS_3_1", "${project.sectorOfDemand}");
				//项目名称
				replaceContent("SYS_20171200", "SYS_20171201", "${project.name}");
				//项目编号
				replaceContent("SYS_20171202", "SYS_20171203", "${project.projectNumber}");
				//投标截止时间
				replaceContent("SYS_20171204", "SYS_20171205", "${project.deadline}");
				// 投标地点
				replaceContent("SYS_20171206", "SYS_20171207", "${project.bidAddress}");
				// 开标时间
				replaceContent("SYS_20171208", "SYS_20171209", "${project.bidDate}");
				//开标地点
				replaceContent("SYS_20171210", "SYS_20171211", "${project.bidAddress}");
				//招标人
				replaceContent("SYS_20171212", "SYS_20171213", "${project.sectorOfDemand}");
				//招标人
				replaceContent("SYS_20171214", "SYS_20171215", "${project.sectorOfDemand}");
				//招标人
				replaceContent("SYS_20171216", "SYS_20171217", "${project.sectorOfDemand}");

				//定位定义标签位置
				loadWord("DW_TWO_TWO", "DW_TWO_THREE", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[1]);
				loadWord("DW_THREE_2", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[0]);
				obj.ActiveDocument.DeleteAllEditableRanges(-1);//取消编辑
			}
		}
		for ( var i = 1; i <= marks.Count; i++) {
			// 判读 标签 可编辑
			if (marks(i).Name.indexOf("EDITOR") == 0) {
				obj.ActiveDocument.Bookmarks(marks(i).Name).Range.Select();//选取书签区域保护
				obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
				//添加 内容标识显示
				obj.ActiveDocument.ActiveWindow.View.ShadeEditableRanges = true;
				obj.ActiveDocument.ActiveWindow.View.ShowBookmarks = true;
			}
		}
		if (obj.ActiveDocument.ProtectionType == -1) {
			obj.ActiveDocument.Protect(3);//实现文档保护
		}
		obj.ActiveDocument.Bookmarks("OLE_LINK_TOP").Select();

	});
</script>
</head>

<body onload="OpenFile('${fileId}')">
<c:if test="${process == 1 }">
<!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
    <ul class="breadcrumb margin-left-0">
      <li><a href="javascript:void(0)">首页</a></li>
    <li><a href="javascript:void(0)">保障作业系统</a></li>
    <li><a href="javascript:void(0)">采购项目管理</a></li>
    <li class="active"><a href="javascript:void(0)">采购文件审核</a></li>
    </ul>
    <div class="clear"></div>
    </div>
  </div>
  <div class="container">
 </c:if>
      <c:if test="${process != 1 }">
        <div class="col-md-12 p0">
          <ul class="flow_step">
            <c:if test="${ope == 'add' }">
              <li>
                <a href="${pageContext.request.contextPath}/adFirstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
                <i></i>
              </li>

              <li>
                <a href="${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
                <i></i>
              </li>
              <li class="active">
                <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}">
                  03、采购文件
                </a>
                <i></i>
              </li>
              <li>
			         <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
			       </li>
            </c:if>
            <c:if test="${ope == 'view' }">
              <li>
                <a href="${pageContext.request.contextPath}/Adopen_bidding/firstAduitView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
                <i></i>
              </li>
              <li>
                <a href="${pageContext.request.contextPath}/adIntelligentScore/packageListView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
                <i></i>
              </li>
              <li class="active">
                <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFileView.html?id=${project.id}&flowDefineId=${flowDefineId}">
                  03、采购文件
                </a>
                <i></i>
              </li>
              <li>
               <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
             </li>
              <li>
                <c:if test="${project.confirmFile == 0 || project.confirmFile==null}">
                  <a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a>
                </c:if>
                <c:if test="${project.confirmFile == 1 }">
                  <a>05、已确认</a>
                </c:if>
              </li>
            </c:if>
          </ul>
        </div>
      </c:if>
      <!-- 按钮 -->
      <c:if test="${process != 1 && project.confirmFile != 1 && project.confirmFile != 3 && project.confirmFile != 4 && ope =='add'}">
	     <div class="mt5 mb5 fr" id="handle">
          <input type="button" class="btn btn-windows save" onclick="saveFile('0')" value="暂存">
          <input type="button" class="btn btn-windows git" onclick="saveFile('1')" value="提交至采购管理部门"></input>
        </div>
      </c:if>
      <c:if test="${(project.confirmFile == 3 || project.confirmFile == 4) && process != 1}">
       <div class="mt5 mb5 fr" id="handle">
          <input type="button" class="btn btn-windows save" onclick="oncreate();" value="生成正式采购文件">
        </div>
   	</c:if>
      <form id="MyFile" method="post" class="h800">
        <c:if test="${ (project.confirmFile == null || project.confirmFile == 0 || project.confirmFile == 2) && process != 1  }">
			<div class="" id="audit_file_add">
				<span class="fl">上传审批文件：</span>
				<div>
              <u:upload id="a" buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp,pdf" multiple="true" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
              <u:show showId="b" groups="b,c,d" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
            </div>
          </div>
          <div class="dnone" id="audit_file_view">
            <span class="fl">审批文件：</span>
            <u:show showId="d" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
          </div>
        </c:if>

        <c:if test="${project.confirmFile == 1 || project.confirmFile == 3 || project.confirmFile == 4 || process == 1 }">
			<div class="clear" >
				<span class="fl">审批文件：</span>
            <u:show showId="c" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
          </div>
        </c:if>
        <input type="hidden" id="ope" value="${ope}">
        <input type="hidden" id="confirmFileId" value="${project.confirmFile}">
        <input type="hidden" id="flowDefineId" value="${flowDefineId }">
        <input type="hidden" id="projectId" value="${project.id}">
        <input type="hidden" id="projectName" value="${project.name}">
        <script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0" id="cgdiv">
          <c:if test="${process == 1 }">
		 <div class="mt10">
         	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">采购管理部门意见</span>
            <c:if test="${project.confirmFile != 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10" disabled="disabled"  id="pcReason" maxlength="2000" name="pcReason" title="不超过2000个字">${reasons.pcReason}</textarea>
            	<span class="fl">采购管理部门审核意见附件：</span>
            	<u:show delete="false"  showId="e" businessId="${project.id}" sysKey="${sysKey}" typeId="${pcTypeId}"/>
            </c:if>
            <c:if test="${project.confirmFile == 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10"  id="pcReason" maxlength="2000" name="pcReason" title="不超过2000个字">${reasons.pcReason}</textarea>
            	<span class="fl">采购管理部门审核意见附件：</span>
            	<u:upload id="r"  buttonName="上传采购管理部门审核意见"  multiple="true"  businessId="${project.id}"  sysKey="${sysKey}" typeId="${pcTypeId}" auto="true" />
			    <u:show  showId="t" businessId="${project.id}" sysKey="${sysKey}" typeId="${pcTypeId}"/>
            </c:if>
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">事业部门意见</span>
            <c:if test="${project.confirmFile != 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10" disabled="disabled"  id="causereason" maxlength="2000" name="causeReason" title="不超过2000个字">${reasons.causeReason}</textarea>
          		<span class="fl">事业部门审核意见附件：</span>
          		<u:show delete="false"  showId="y" businessId="${project.id}" sysKey="${sysKey}" typeId="${causeTypeId}"/>
          	</c:if>
          	<c:if test="${project.confirmFile == 1}">
          		<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10"  id="causereason" maxlength="2000" name="causeReason" title="不超过2000个字">${reasons.causeReason}</textarea>
          		<span class="fl">事业部门审核意见附件：</span>
          		<u:upload id="u"  buttonName="上传事业部门审核意见"  multiple="true"  businessId="${project.id}"  sysKey="${sysKey}" typeId="${causeTypeId}" auto="true" />
			    <u:show  showId="i" businessId="${project.id}" sysKey="${sysKey}" typeId="${causeTypeId}"/>
          	</c:if>
          	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">财务部门意见</span>
            <c:if test="${project.confirmFile != 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10" disabled="disabled"  id="financereason" maxlength="2000" name="financeReason" title="不超过2000个字">${reasons.financeReason}</textarea>
            	<span class="fl">财务部门审核意见附件：</span>
            	<u:show delete="false"  showId="o" businessId="${project.id}" sysKey="${sysKey}" typeId="${financeTypeId}"/>
            </c:if>
            <c:if test="${project.confirmFile == 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb10"  id="financereason" maxlength="2000" name="financeReason" title="不超过2000个字">${reasons.financeReason}</textarea>
            	<span class="fl">财务部门审核意见附件：</span>
            	<u:upload id="p"  buttonName="上传财务部门审核意见"  multiple="true"  businessId="${project.id}"  sysKey="${sysKey}" typeId="${financeTypeId}" auto="true" />
			    <u:show  showId="s" businessId="${project.id}" sysKey="${sysKey}" typeId="${financeTypeId}"/>
          	</c:if>
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">最终意见</span>
            <c:if test="${project.confirmFile != 1}">
            	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb20" disabled="disabled"  id="finalreason" maxlength="2000" name="finalReason" title="不超过2000个字">${reasons.finalReason}</textarea>
            </c:if>
            <c:if test="${project.confirmFile == 1}">
	            <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80 mb20"  id="finalreason" maxlength="2000" name="finalReason" title="不超过2000个字">${reasons.finalReason}</textarea>
            </c:if>
          </div>
         <div class="clear tc mt50">
         	<c:if test="${exist == true && project.confirmFile == 1}">
	            <input type="button" class="btn btn-windows check_pass " onclick="updateAudit('3')" value="审核通过"></input>
	            <input type="button" class="btn btn-windows check_back " onclick="updateAudit('2')" value="退回重报 "></input>
	            <input type="button" class="btn btn-windows edit " onclick="updateAudit('4')" value="修改报备 "></input> 
         	</c:if>
	        <input type="button" class="btn btn-windows back " onclick="javascript:history.go(-1);" value="返回 "></input>
         </div>
         </div>
         </c:if>
       </div>
      </form>
  </body>
</html>