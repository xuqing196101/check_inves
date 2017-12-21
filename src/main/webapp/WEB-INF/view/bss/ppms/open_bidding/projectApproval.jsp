<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
		 $(function(){
			/*  if("${msg}"  == "1") {
			    layer.alert("请先上传编报说明",{offset: '50px'});
			}else if("${msg}" == "2"){
				layer.alert("请先上传审批文件",{offset: '50px'});
			} */
			//获取查看或操作权限
        var isOperate = $('#isOperate', window.parent.document).val();
        if(isOperate == 0) { 
          //只具有查看权限，隐藏操作按钮
          $(":button").each(function() {
            $(this).hide();
          });
         }
         
         var isCharges = "${project.confirmFile}";
	        if(isCharges){
	          if(isCharges == 1  || isCharges == 3 || isCharges == 4){
	            var isCharge = $("input[name='fileName']");
	            isCharge[0].checked=true;
	            $(isCharge).attr("disabled","disabled");
	          } else if (isCharges == 5){
	            var isCharge = $("input[name='fileName']");
	            isCharge[1].checked=true;
	            $(isCharge).attr("disabled","disabled");
	          }
	        }
		 });
		 function bidRegister(id,type) {
       window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
     }
		      
		 function saveFile() {
		 		var flag = $("input[name='fileName']:checked").val();
		 		var type = "${project.confirmFile}";
		 		if(type){
		 			var text = $("#f_disFileId").find("a");
		 			if(text.length<=0){
		 				layer.msg("请上传");
		 			} else {
		 				//提交
						 $.ajax({
		          url: "${pageContext.request.contextPath}/open_bidding/saveBidDocument.html?projectId=${project.id}&flowDefineId=${flowDefineId}&flag=" + flag,
		          type: "post",
		          dataType: "text",
		          async:false,
		          success: function(result) {
		          	if(result == "ok"){
		          		window.location.href = "${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${project.id}&flowDefineId=${flowDefineId}";
		          	}
		          },
		          error: function() {
		            layer.msg("失败");
		          }
		        });
		 			}
		 			
		 		} else {
		 			layer.msg("请提交采购文件");
		 		}
		 }
				
		</script>
	</head>

	<body>
		<div class="col-md-12 p0">
			<h2 class="list_title">编报说明</h2>
			<ul class="flow_step">
				<li>
					<a href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
					<i></i>
				</li>
				<li>
		   <a  href="${pageContext.request.contextPath}/project/packDetail.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、移除明细</a>
		   <i></i>
		 </li>
				<li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、评审项预览</a>
						   <i></i>
						 </li>
				<li>
					<a href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}">
						05、采购文件
					</a>
					<i></i>
				</li>
				<li class="active">
					<a href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${projectId}&flowDefineId=${flowDefineId}">06、编报说明</a>
					<i></i>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">07、审核意见</a>
				</li>
			</ul>
		</div>
		<div class="clear"></div>
		<div class="m_boxS1 mt20">
			<c:if test="${project.confirmFile eq null or project.confirmFile == 0 or project.confirmFile == 2}">
			<div class="mb10"><span class="star_red">*</span>编报说明：</div>
			<button class="btn btn-windows input h28 mr10 mb0 fl w50p" type="button" onclick="bidRegister('${project.id}','16')">模板下载</button>
			<div class="m_uploadFiles fl">
				<u:upload id="c" buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp,pdf" multiple="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" auto="true" />
				<u:show showId="f" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" />
				<div class="clear"></div>
			</div>
			</c:if>
			<c:if test="${project.confirmFile == 1 or project.confirmFile == 3 or project.confirmFile == 4 or project.confirmFile == 5 }">
			<p><span class="star_red">*</span>编报说明：</p>
			<u:show showId="f" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" />
			</c:if>
			<div class="clear"></div>
		</div>
		
		<div class="col-md-12 col-sm-12 col-xs-12 m_boxS1 mt20">
			<div class="clear">
				<input type="radio" name="fileName" value="1" id="fileName_1"><label for="fileName_1" class="m_inline hand ml5">报批</label>
				<input type="radio" name="fileName" value="5" id="fileName_2" class="ml10"><label for="fileName_2" class="m_inline hand ml5">不报批</label>
			</div>
			<c:if test="${project.confirmFile eq null or project.confirmFile == 0 or project.confirmFile == 2}">
			<p>采购文件是否需要提交至管理部门报批</p>
			 <button class="btn btn-windows save" type="button" onclick="saveFile()"">完成</button>
       <!-- <button class="btn btn-windows save" type="button" onclick="saveFile('1')"">报批</button>
       <button class="btn" type="button" onclick="saveFile('5')">不报批</button> -->
       </c:if>
     </div>
	</body>

</html>