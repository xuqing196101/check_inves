<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp"%>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<script type="text/javascript">
	function backOld() {
		window.location.href="${pageContext.request.contextPath}/expert/findAllExpert.html";
	}

	function initData(){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/initData.do",
			data:{"expertId":$("#id").val()},
			async:false,
			dataType:"json",
			success:function(response){
				$("#tSex").html(response.gender);
				$("#tFace").html(response.politicsStatus);
				$("#Taddress").html(response.address);
				$("#tHight").html(response.hightEducation);
				$("#idType").html(response.idType);
				$("#expertsFrom").html(response.expertsFrom);
				$("#expertsType").html(response.expertsTypeId);
				$("#degree").html(response.degree);
			}
		});
	}
	
	function resetPaw(){
		layer.open({
				  type: 1,
				  title: '重置密码',
				  area: ['270', '260px'],
				  closeBtn: 1,
				  shade:0.01, //遮罩透明度
				  moveType: 1, //拖拽风格，0是默认，1是传统拖动
				  shift: 1, //0-6的动画形式，-1不开启
				  offset: '150px',
				  shadeClose: false,
				  content: $("#openDiv"),
		});
	}
	
	function ajaxOldPassword(){
		var is_error = 0;
		var userId = $("#userId").val();
		var oldPassord = $("#oldPassword").val();
		$.ajax({
           type: "GET",
           async: false, 
           url: "${pageContext.request.contextPath}/user/ajaxOldPassword.do?id="+userId+"&password="+oldPassord,
           dataType: "json",
           success: function(data){
                 if (!data.success) {
                 	is_error = 1;
					layer.msg(data.msg,{offset: ['150px']});
				 }
             }
       	});
       	return is_error;
	}
	
	function resetPasswSubmit(){
		var is_error = ajaxOldPassword();
		if (is_error == 1) {
			return false;
		} else {
			$.ajax({   
		            type: "POST",  
		            url: "${pageContext.request.contextPath}/user/resetPwd.html",        
		           	data : $('#form2').serializeArray(),
				    dataType:'json',
				    success:function(result){
				    	if(!result.success){
	                    	layer.msg(result.msg,{offset: ['150px']});
				    	}else{
				    		layer.closeAll();
				    		layer.msg(result.msg,{offset: ['222px']});
				    	}
	                },
	                error: function(result){
	                    layer.msg("重置失败",{offset: ['222px']});
	                }
		     });    
		}
	}
	
	function cancel(){
		layer.closeAll();
	}
</script>
</head>
<body onload="initData()">
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
	<input type="hidden" name="id" id="id" value="${expert.id}"/>
  <!-- <div class="">	
	 <button class="btn" type="button" onclick="resetPaw()">重置密码</button>
  </div> -->
  <table class="table table-bordered table-condensed ">
  <div class="margin-top-10"></div>
   	<tr>
 	  <td width="25%" class="bggrey">姓名</td>
 	  <td width="25%" id="tName">${expert.relName}</td>
 	  <td width="25%" class="bggrey">性别</td>
      <td width="25%" id="tSex"></td>
   	</tr>
   <tr>
	 <td width="25%" class="bggrey">出生日期</td>
	 <td width="25%" id="tBirthday"><fmt:formatDate value="${expert.birthday}" pattern="yyyy-MM-dd" /></td>
	 <td width="25%" class="bggrey">政治面貌</td>
	 <td width="25%"  id="tFace"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">所在地区</td>
	 <td width="25%" id="Taddress"></td>
	 <td width="25%" class="bggrey">专业技术职称/执业资格</td>
	 <td width="25%" id="tHey" >${expert.professTechTitles}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">居民身份证号码</td>
	 <td width="25%">${expert.idCardNumber}</td>
	 <td width="25%" class="bggrey">民族</td>
	 <td width="25%">${expert.nation}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">健康状况</td>
	 <td width="25%">${expert.healthState}</td>
	 <td width="25%" class="bggrey">所在单位</td>
	 <td width="25%" id="tTimeStartWork">${expert.workUnit}</td>
   </tr>
   
   
   <tr>
	 <td width="25%" class="bggrey">缴纳社会保险证明</td>
	 <td width="25%">${expert.coverNote}</td>
	 <td width="25%" class="bggrey">单位邮编</td>
	 <td width="25%">${expert.postCode}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位地址</td>
	 <td colspan="3">${expert.unitAddress}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">军队人员身份证件类型</td>
	 <td width="25%" id="idType"></td>
	 <td width="25%" class="bggrey">证件号码</td>
	 <td width="25%" >${expert.idNumber}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">现任职务</td>
	 <td width="25%" >${expert.atDuty}</td>
	 <td width="25%" class="bggrey">从事专业</td>
	 <td width="25%">${expert.major}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">从事专业起始年度</td>
	 <td width="25%"><fmt:formatDate value="${expert.timeStartWork}" pattern="yyyy-MM" /></td>
	 <td width="25%" class="bggrey">专家来源</td>
	 <td width="25%" id="expertsFrom"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">专业技术职称/执业资格</td>
	 <td width="25%">${expert.professTechTitles}</td>
	 <td width="25%" class="bggrey">取得技术职称时间</td>
	 <td width="25%"><fmt:formatDate value="${expert.makeTechDate}" pattern="yyyy-MM" /></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">毕业院校及专业</td>
	 <td colspan="3">${expert.graduateSchool}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">专家类别</td>
	 <td width="25%" id="expertsType"></td>
	 <td width="25%" class="bggrey">最高学历</td>
	 <td width="25%" id="tHight"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">最高学位</td>
	 <td width="25%" id="degree"></td>
	 <td width="25%" class="bggrey">个人邮箱</td>
	 <td width="25%">${expert.email}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">移动电话</td>
	 <td width="25%">${expert.mobile}</td>
	 <td width="25%" class="bggrey">固定电话</td>
	 <td width="25%">${expert.telephone}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">传真电话</td>
	 <td colspan="3">${expert.fax}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">参评的产品类别 </td>
	 <td colspan="3">${expert.productCategories}</td>
   </tr>
   <tr>
	 <td width="25%"  class="bggrey">主要工作经历</td>
	 <td colspan="3">${expert.productCategories}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">专业学术成果</td>
	 <td colspan="3">${expert.academicAchievement}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">参加军队地方采购评审情况 </td>
	 <td colspan="3">${expert.reviewSituation}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">需要申请回避的情况 </td>
	 <td colspan="3">${expert.avoidanceSituation}</td>
   </tr>
 </table>
    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
	</div>
	  </div>
	    </div>
	      </div>
	<div id="openDiv" class="dnone layui-layer-wrap" >
	  <form id="form2" method="post" >
	  	<div class="drop_window">
	  		  <input type="hidden" name="id" id="userId" value="${user.id}">
			  <ul class="list-unstyled">
			  	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入原密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                 	<input type="password" id="oldPassword" name="oldPassword" type="text" onblur="ajaxOldPassword()">
	                </div>
	              </div>
	              <div class="clear">
	              </div>
	          	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                 	<input type="password" id="password" name="password" type="text">
	                </div>
	              </div>
	              <div class="col-md-6  col-sm-6 col-xs-12 ">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>确认新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                  <input type="password" id="password2" name="password2"  class="">
	                </div>
	              </div>
			  </ul>
              <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
                <input class="btn" id="inputb" name="addr" onclick="resetPasswSubmit();" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		 </form>
	  </div>
</body>
</html>
