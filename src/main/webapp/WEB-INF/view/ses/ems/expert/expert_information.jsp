<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
    //非空判断
	function notNull(name) {
		var len = document.getElementById(name).value.length;
		if(len == null || len == 0) {
			if(name=="email"){
				layer.msg("个人邮箱不能为空!");
				return;
			}else if(name="telephone"){
				layer.msg("固定电话不能为空!");
				return;
			}
		}
	}
    function checkMobile(){
				var mobile = $("#mobile").val();
				if(!mobile) {
					layer.msg("请填写手机号!");
					return false;
				}
				if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
					layer.msg("手机号格式不正确!");
                    return false;
				}
				$.ajax({
					url: '${pageContext.request.contextPath}/expert/checkPhone.do',
					type: "post",
					async: false,
					data: {
						"phone": mobile,
						"id": expertId
					},
					success: function(obj) {
						if(obj == '1') {
							layer.msg("该手机号码已被使用!");
							return false;
						}
					}
				});
			}
    function submitExpert(){
    	var expertId=$("#expertId").val();
    	var mobile = $("#mobile").val();
		var email=$("#email").val();
		var telephone=$("#telephone").val();
		var fax=$("#fax").val();
		if(!email) {
			layer.msg("请填写个人邮箱!");
			return false;
		}
		var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
		if(email != "" && !emailReg.test(email)) {
			layer.msg("个人邮箱格式有误 !");
			return false;
		}
		if(!mobile) {
			layer.msg("请填写手机号!");
			return false;
		}
		if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
			layer.msg("手机号格式不正确!");
            return false;
		}
		var checkPhone=0;
		$.ajax({
			url: '${pageContext.request.contextPath}/expert/checkPhone.do',
			type: "post",
			async: false,
			data: {
				"phone": mobile,
				"id": expertId
			},
			success: function(obj) {
				if(obj == '1') {
					layer.msg("该手机号码已被使用!");
					checkPhone=1;
				}
			}
		});
		if(checkPhone==1){
			 return false;
		}
		if(!telephone) {
			layer.msg("请填写固定电话!");
			return false;
		}
		if(telephone != "" && telephone.length > 20) {
			layer.msg("固定电话格式有误!");
			return false;
		}
		var mobile = $("#mobile").val();
		var email=$("#email").val();
		var telephone=$("#telephone").val();
		var fax=$("#fax").val();
		 $.ajax({
            url: "${pageContext.request.contextPath}/expert/updateExpertInformation.do",
            data: {
                "id": expertId,
                "mobile":mobile,
                "email":email,
                "telephone":telephone,
                "fax":fax
            },
            type: "POST",
            async: false,
            //dataType: "json",
           success: function(e) {
        	   layer.msg("信息变更成功");
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
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">评审专家信息</a>
					</li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/expert/getExpertInformation.html')" >变更联系方式</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
		
		<div class="container">
		<div class="headline-v2 ml0"><h2>变更联系方式</h2></div>
		<div class="container_box pb70">
			<div class="tab-v2 job-content">
				<div id="upfcTable" class="padding-top-10" >
               		<table style="width: 940px; float: none; margin: 0 auto;">
               			<tr>
               				<td class="w120 tr p10 b"><i class="red">*</i> 个人邮箱：</td>
               				<td class="p10"><input type="text" class="w100p mb0" onblur="notNull('email')" id="email" value="${expert.email}"></td>
               				<td class="w1w0 tr p10 b"><i  class="red">*</i> 手　　机：</td>
               				<td class="p10"><input type="text"  id="mobile" class="w100p mb0" onblur="checkMobile()" value="${expert.mobile}"></td>
               			</tr>
               			<tr>
               				<td class="tr p10 b"><i class="red">*</i> 固定电话：</td>
               				<td class="p10"><input type="text" id="telephone" onblur="notNull('telephone')" class="w100p mb0" value="${expert.telephone}"></td>
               				<td class="tr p10 b">  传真电话：</td>
               				<td class="p10"><input type="text" class="w100p mb0" id="fax" value="${expert.fax}"></td>
               			</tr>
               		</table>
               		
               		<div class="tc mt10">
               			<input type="hidden" id="expertId" value="${expert.id}">
               			<button class="btn mb0 mr0" onclick="submitExpert();">保存</button>
               		</div>
				</div>
			</div>
		</div>
		</div>


</body>

</html>