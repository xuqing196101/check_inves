<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%--<jsp:include page="/WEB-INF/view/ses/ems/expert/common/common.jsp"></jsp:include>--%>
<!DOCTYPE HTML>
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<title>评审专家基本信息</title>
<script type="text/javascript">
	//页签点击跳转
	function go1(){
		$("#reg_box_id_3").show();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function go2(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").show();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function go3(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").show();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function go4(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").show();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}	
	function go5(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").show();
		$("#reg_box_id_8").hide();
	}	
	$(function(){
		var parentId ;
		var addressId="${expert.address}";
		//回显已选产品
		var id="${expert.id}";
		var count=0;
		var expertsTypeId = $("#expertsTypeId").val();
		//控制品目树的显示和隐藏
		if(expertsTypeId==1 || expertsTypeId=="1"){
			$.ajax({
				url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do",
				dataType:"json",
				data:{"expertId":id},
				async:false,
				success:function(code){
					var checklist = document.getElementsByName ("chkItem");
					for(var i=0;i<checklist.length;i++){
						var vals=checklist[i].value;
						if(code.length>0){
							$.each(code,function(j,result){
								if(vals==result){
						 			checklist[i].checked=true;
						 		}
								if("GOODS"==result){
									count++;
								}
							});
						} 
					} 
					if(count>0){
						$("#hwType").show(); 
					}else{
						$("#hwType").hide(); 
					}
				}
			}); 
			$("#ztree").show();
		} else {
			$("#ztree").hide();
		}
		//地区回显和数据显示
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_id.do",
			dataType:"json",
			data:{"id":addressId},
			success:function(obj){
				$.each(obj,function(i,result){
					if(addressId == result.id){
						parentId = result.parentId;
						$("#add").append(result.name);
						$("#add2").append(result.name);
					}
				});
			}
		}); 
		//地区
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			dataType:"json",
			success:function(obj){
				$.each( obj,function(i,result){
					if(parentId == result.id){
						$("#addr").append(result.name+",");
						$("#addr2").append(result.name+",");
					}
				});
			}
		});
	}); 	
</script>
</head>
<body>
	<jsp:include page="/index_head.jsp"></jsp:include>
  <form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post">
    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}">
	<input type="hidden" name="id" id="id" value="${expert.id}">
	<input type="hidden" name="expertsTypeId" id="expertsTypeId" value="${expert.expertsTypeId}">
	<input type="hidden" name="sysId" value="${sysId}">
	<input type="hidden" id="categoryId" name="categoryId" value="">
	<input type="hidden"  name="token2" value="<%=tokenValue%>">
	<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
	  <h2 class="step_flow">
	    <span id="" class="new_step current fl" onclick="go1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
		<span  class="new_step current fl"   onclick="go2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
		<span class="new_step current fl" onclick="go3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
		<span class="new_step current fl" onclick="go4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
		<span class="new_step current fl new_step_last" onclick="go5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
	    <div class="clear"></div>
	  </h2>
<div class="container content height-350">
  <div class="row magazine-page">
    <div class="col-md-12 tab-v2 job-content">
      <div class="padding-top-10">
        <div class="tab-content padding-top-20  h900">
          <div class="tab-pane fade active in height-500"  id="tab-1">
            <div class=" margin-bottom-0"><br/>
			  <h2 class="count_flow">
				<i>01</i>专家基本信息
			  </h2>
	          <ul class="ul_list">
	            <table class="table table-bordered">
	              <tbody>
	                <tr>
				      <td width="25%" class="info">联系电话（固话）：</td>
				      <td width="25%">${expert.telephone}</td>
				      <td width="25%" class="info">单位地址：</td>
				      <td width="25%">${expert.unitAddress}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">姓名：</td>
				      <td width="25%">${expert.relName}</td>
				      <td width="25%" class="info">手机：</td>
				      <td width="25%">${expert.mobile}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">性别：</td>
				      <td width="25%"> 
				        <c:forEach items="${sexList}" var="sex">
				          <c:if test="${expert.gender eq sex.code }">${sex.name}</c:if>
				        </c:forEach>
	     			  </td>
				      <td width="25%" class="info">出生日期：</td>
				      <td width="25%"><fmt:formatDate type='date' value='${expert.birthday}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">政治面貌：</td>
				      <td width="25%">
				         <c:forEach items="${zzList}" var="zz">
				          <c:if test="${expert.politicsStatus eq zz.id }">${zz.name}</c:if>
				         </c:forEach>
				      </td>
				      <td width="25%" class="info">专家来源：</td>
				      <td width="25%">
				         <c:forEach items="${lyTypeList}" var="ly">
				           <c:if test="${expert.expertsFrom eq ly.id }">${ly.name}</c:if>
				         </c:forEach>
				      </td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">证件类型：</td>
				      <td width="25%">
				         <c:forEach items="${idTypeList}" var="id">
				           <c:if test="${expert.idType eq id.id }">${id.name}</c:if>
				         </c:forEach>
				      </td>
				      <td width="25%" class="info">证件号码：</td>
				      <td width="25%">${expert.idNumber}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">所在地区：</td>
				      <td width="25%">
				        <font id="addr"></font><font id="add"></font>
				      </td>
				      <td width="25%" class="info">民族：</td>
				      <td width="25%">${expert.nation}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">毕业院校：</td>
				      <td width="25%">${expert.graduateSchool }</td>
				      <td width="25%" class="info">专家技术职称：</td>
				      <td width="25%">${expert.professTechTitles}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">参加工作时间：</td>
				      <td width="25%"><fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				      <td width="25%" class="info">最高学历：</td>
				      <td width="25%">
				        <c:forEach items="${xlList }" var="xl">
				          <c:if test="${expert.hightEducation eq xl.id }">${xl.name}</c:if>
				        </c:forEach>
				      </td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">专业：</td>
				      <td width="25%">${expert.major}</td>
				      <td width="25%" class="info">从事专业年度：</td>
				      <td width="25%"><fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">工作单位：</td>
				      <td width="25%">${expert.workUnit}</td>
				      <td width="25%" class="info">传真：</td>
				      <td width="25%">${expert.fax}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">邮政编码：</td>
				      <td width="25%">${expert.postCode}</td>
				      <td width="25%" class="info">取得技术时间：</td>
				      <td width="25%"><fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">学位：</td>
				      <td width="25%">${expert.degree}</td>
				      <td width="25%" class="info">健康状态：</td>
				      <td width="25%">${expert.healthState}</td>
				    </tr>
				    <tr>
				      <td width="25%" class="info">现任职务：</td>
				      <td width="25%">${expert.atDuty}</td>
				      <td width="25%" class="info"></td>
				      <td width="25%"></td>
				    </tr>
		        </tbody>
	        </table>
	     </ul>
		   </div>
<!-- 附件信息-->
	  <div class="padding-right-20 clear">
	    <h2 class="count_flow"><i>2</i>上传附件</h2>
		<ul class="ul_list">
	    <table class="table table-bordered">
	  	  <tr>
	  	    <td width="25%" class="info"><i class="red">*</i>身份证:</td>
	  	    <td>
	          <up:show  delete="false" showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
	  	    </td>
	  	    <td width="25%" class="info"><i class="red">*</i>学历证书:</td>
	  	    <td>
	          <up:show  delete="false" showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
	  	    </td>
	  	  </tr>
	  	  <tr>
	  	    <td width="25%" class="info"><i class="red">*</i>职称证书:</td>
	  	    <td>
	          <up:show  delete="false"  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
	  	    </td>
	  	    <td width="25%" class="info"><i class="red">*</i>学位证书:</td>
	  	    <td>
	          <up:show  delete="false" showId="show4"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
	  	    </td>
	  	  </tr>
	  	  <tr>
	  	    <td width="25%" class="info"><i class="red">*</i>个人照片:</td>
	  	    <td>
	          <up:show  delete="false" showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
	  	    </td>
	  	    <td></td><td></td>
	  	  </tr>  
	   </table>
       <div class="tc mt20 clear col-md-11">
		 <button class="btn btn-windows git" id="nextBind"  type="button" onclick="go2()" >下一步</button>
       </div>
	 </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
		
  <div id="reg_box_id_4" class="container clear margin-top-30 yinc" style="display: none;">
    <h2 class="step_flow">
      <span class="new_step current fl"  onclick="go1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
      <span  class="new_step current fl"   onclick="go2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
      <span class="new_step current fl" onclick="go3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
      <span class="new_step current fl" onclick="go4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
      <span class="new_step current fl new_step_last" onclick="go5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
	  <div class="clear"></div>
    </h2><br/>
	<h2 class="count_flow">
	  <i>02</i>专家类型
	</h2>
    <ul class="list-unstyled list-flow" >
	  <table>
        <tbody>
          <tr>
		    <td width="90px;" class="info"> <h4>专家类型：</h4></td> 
		    <td width="100px;">
		      <h4>
		      <c:if test="${expert.expertsTypeId eq '1' }">技术</c:if>
		      <c:if test="${expert.expertsTypeId eq '2' }">法律</c:if>
		      <c:if test="${expert.expertsTypeId eq '3' }">经济</c:if>
		      </h4>
		    </td>
		  </tr>
		</tbody>
      </table> 
    </ul>
    <ul class="ul_list dnone" id="ztree" >
      <div>
        <div class="col-md-5 title"><span class="star_red fl">*</span>产品服务/分类：</div>
          <div class="col-md-7 service_list">
            <c:forEach items="${spList}" var="obj" >
              <span><input type="checkbox" name="chkItem" disabled="disabled" value="${obj.code}" />${obj.name} </span>
            </c:forEach>
          </div>
       </div>
       <div id="hwType" class="dnone">
         <div class="col-md-5 title"><span class="star_red fl">*</span>货物分类：</div>
          <div class="col-md-7 service_list">
            <c:forEach items="${hwList}" var="hw" >
            <span><input type="checkbox" name="chkItem" disabled="disabled" value="${hw.code}" />${hw.name} </span>
            </c:forEach>
          </div>
        </div>
    </ul>
	    <div class="tc mt20 clear col-md-11">
			<button class="btn btn-windows back"   type="button" onclick="go1()">上一步</button>
			<button class="btn btn-windows git"   type="button" onclick="go3()">下一步</button>
		</div>
		</div>
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc" style="display: none;">
	  		<h2 class="step_flow">
				<span class="new_step current fl"  onclick="go1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
				<span  class="new_step current fl"   onclick="go2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
				<span class="new_step current fl" onclick="go3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
				<span class="new_step current fl" onclick="go4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
				<span class="new_step current fl new_step_last" onclick="go5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			    <div class="clear"></div>
		    </h2><br/>
		    <h2 class="count_flow">
			  <i>03</i>采购机构
			</h2>
			<table id="tb1"  class="table table-bordered table-condensed">
			  <thead>
			    <tr>
				  <th class="info w30"><input type="radio"   disabled="disabled"  id="purchaseDepId2" ></th>
				  <th class="info w50">序号</th>
				  <th class="info">采购机构</th>
				  <th class="info">联系人</th>
				  <th class="info">联系地址</th>
				  <th class="info">联系电话</th>
				</tr>
			  </thead>
				<c:forEach items="${ purchase}" var="p" varStatus="vs">
				  <tr align="center">
					<td><input type="radio" disabled="disabled" name="purchaseDepId" <c:if test="${expert.purchaseDepId eq p.id }">checked</c:if>  value="${p.id }" /></td>
					<td>${vs.count}</td>
					<td><input border="0" readonly="readonly" value="${p.name}" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
					<td><input border="0" readonly="readonly" value="${p.princinpal}" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
					<td><input border="0" readonly="readonly" value="${p.detailAddr}" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
					<td><input border="0" readonly="readonly" value="${p.mobile}" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
				  </tr>
				</c:forEach> 
			</table>
			  <h6>
		                 友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		      </h6>
		    <div class="tc mt20 clear col-md-11">
			  <button class="btn btn-windows back"   type="button" onclick="go2()">上一步</button>
			  <button class="btn btn-windows git"   type="button" onclick="go4()">下一步</button>
			</div>
    </div>
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc" style="display: none;">
	  <h2 class="step_flow">
		<span class="new_step current fl" onclick="go1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
		<span class="new_step current fl" onclick="go2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
		<span class="new_step current fl" onclick="go3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
		<span class="new_step current fl" onclick="go4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
		<span class="new_step current fl new_step_last" onclick="go5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
	    <div class="clear"></div>
	  </h2><br/>
	  <h2 class="count_flow">
	    <i>04</i>打印专家申请表
	  </h2>
	  <div class="margin-top-30"></div>
<div>
  <table class="table table-bordered table-condensed">
   	<tr>
   	  <td align="center" width="100px">姓名</td>
   	  <td align="center" width="150px" id="tName">${expert.relName}</td>
   	  <td align="center">性别</td>
	  <td class="tc" id="tSex" colspan="2">
	  	<c:forEach items="${sexList}" var="sex">
         <c:if test="${expert.gender eq sex.id }">${sex.name}</c:if>
        </c:forEach>
	  </td>
   	</tr>
    <tr>
   	  <td align="center">出生日期</td>
   	  <td align="center" width="150px" id="tBirthday"><fmt:formatDate type='date' value='${expert.birthday}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   	  <td align="center">政治面貌</td>
   	  <td align="center" width="150px" id="tFace" colspan="2">
		 <c:forEach items="${zzList}" var="zz">
	       <c:if test="${expert.politicsStatus eq zz.id }">${zz.name}</c:if>
	     </c:forEach>
	  </td>
    </tr>
    <tr>
 	  <td align="center">所在地区</td>
 	  <td align="center" width="150px" >
 		<font id="addr2"></font><font id="add2"></font>
 	  </td>
 	  <td align="center">职称</td>
 	  <td align="center" width="150px" id="tHey" colspan="2">${expert.professTechTitles}</td>
   </tr>
   <tr>
   	  <td align="center">证件号码</td>
   	  <td align="center" id="tNumber" colspan="4">${expert.idNumber}</td>
   </tr>
   <tr>
   	 <td align="center">从事专业类别</td>
   	 <td align="center" id="tExpertsTypeId" width="150px">
   	  <c:if test="${expert.expertsTypeId eq '1' }">技术</c:if>
	  <c:if test="${expert.expertsTypeId eq '2' }">法律</c:if>
	  <c:if test="${expert.expertsTypeId eq '3' }">经济</c:if>
   	 </td>
   	 <td align="center">从事年限</td>
   	 <td align="center" id="tTimeStartWork" colspan="2"><fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   </tr>
   <tr>
   	  <td align="center">最高学历</td>
   	  <td align="center" id="tHight" width="150px">
   		<c:forEach items="${xlList}" var="xl">
	       <c:if test="${expert.hightEducation eq xl.id}">${xl.name}</c:if>
	    </c:forEach>
   	  </td>
   	  <td align="center">最高学位</td>
   	  <td align="center" id="tWei" colspan="2">${expert.degree}</td>
   </tr>
   <tr>
   	 <td align="center">执业资格1</td>
   	 <td align="center" width="150px"> </td>
   	 <td align="center">注册证书编号1</td>
   	 <td align="center" colspan="2"> </td>
   </tr>
   <tr>
   	 <td align="center">执业资格2</td>
   	 <td align="center" width="150px"></td>
   	 <td align="center">注册证书编号2</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">执业资格3</td>
   	 <td align="center" width="150px"></td>
   	 <td align="center">注册证书编号3</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">近两年是否接受过评标业务培训</td>
   	 <td align="center" width="150px"></td>
   	 <td align="center">是否愿意成为应急专家</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">所属行业</td>
   	 <td align="center" width="150px"></td>
   	 <td align="center">报送部门</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">手机号码</td>
   	 <td align="center" id="tMobile" width="150px">${expert.mobile}</td>
   	 <td align="center">单位电话</td>
   	 <td align="center" id="tTelephone" colspan="2">${expert.telephone}</td>
   </tr>
   <tr>
   	 <td align="center">住宅电话</td>
   	 <td align="center" width="150px"></td>
   	 <td align="center">电子邮箱</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">毕业院校及专业</td>
   	 <td align="center" id="tGraduateSchool" colspan="4">${expert.graduateSchool}</td>
   </tr>
   <tr>
   	 <td align="center">单位名称</td>
   	 <td align="center" id="tWorkUnit" colspan="4">${expert.workUnit}</td>
   </tr>
   <tr>
   	 <td align="center">单位地址 </td>
   	 <td align="center" id="tUnitAddress" width="150px">${expert.unitAddress}</td>
   	 <td align="center">单位邮编</td>
   	 <td align="center" id="tPostCode" colspan="2">${expert.postCode}</td>
   </tr>
   <tr>
   	 <td align="center">家庭地址 </td>
     <td align="center" width="150px" ></td>
   	 <td align="center">家庭邮编</td>
   	 <td align="center" colspan="2"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业一</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业二</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业三</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业四</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业五</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center">评标专业六</td>
   	 <td align="center" colspan="4"></td>
   </tr>
   <tr>
   	 <td align="center" colspan="5">工作经历</td>
   </tr>
   <tr>
   	 <td align="center">起止年月</td>
   	 <td align="center" colspan="3">单位及职务</td>
   	 <td align="center">证明人</td>
   </tr>
   <tr>
   	 <td align="center">至 </td>
   	 <td align="center" colspan="3"> </td>
   	 <td align="center" width="150px"> </td>
   </tr>
   <tr>
   	 <td align="center"> 至</td>
   	 <td align="center" colspan="3"> </td>
   	 <td align="center" width="150px"> </td>
   </tr>
   <tr>
   	 <td align="center">至 </td>
   	 <td align="center" colspan="3"> </td>
   	 <td align="center" width="150px"> </td>
   </tr>
   <tr>
   	 <td align="center">至 </td>
   	 <td align="center" colspan="3"> </td>
   	 <td align="center" width="150px"> </td>
   </tr>
   <tr>
   	 <td align="center">至 </td>
   	 <td align="center" colspan="3"> </td>
   	 <td align="center" width="150px"> </td>
   </tr>
   </table>
  <div class="tc mt20 clear col-md-11">
    <button class="btn btn-windows back"   type="button" onclick="go3()">上一步</button>
    <button class="btn btn-windows git"   type="button" onclick="go5()">下一步</button>
  </div>
    </div>
       </div>
	   <div id="reg_box_id_7" class="container clear margin-top-30 yinc" style="display: none;">
		 <h2 class="step_flow">
			<span class="new_step current fl" onclick="go1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
			<span class="new_step current fl" onclick="go2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
			<span class="new_step current fl" onclick="go3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
			<span class="new_step current fl" onclick="go4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
			<span class="new_step current fl new_step_last" onclick="go5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			<div class="clear"></div>
		 </h2><br/>
	     <h2 class="count_flow">
		   <i>05</i>专家申请表、合同书
		 </h2>
		 <div class="">
		   <table class="table table-bordered">
		     <tr>
		       <td width="25%" class="info"><i class="red">*</i>专家申请表：</td>
		   	   <td>
				  <up:show showId="show6" delete="false" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}"/>
		   	   </td>
			   <td width="25%" class="info"><i class="red">*</i>专家合同书：</td>
		   	   <td>
				  <up:show showId="show7" delete="false" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}"/>
		   	   </td>
			 </tr>
		   </table>
		     </div>
		       <div class="tc mt20 clear col-md-11">
		   	     <button class="btn btn-windows back"   type="button" onclick="go4()">上一步</button>
			   </div>
		</div>
      </form>
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
