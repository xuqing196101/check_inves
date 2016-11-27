<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tld/upload" prefix="up"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>专家查看</title>
<script type="text/javascript">
   $(function(){
	   var expertsTypeId = $("#expertsTypeId").val();
	 //回显已选产品
	   var id="${expert.id}";
	   var count=0;
	 //控制品目树的显示和隐藏
	   if(expertsTypeId==1 || expertsTypeId=="1"){
		  $.ajax({
			  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
			  success:function(code){
				  var checklist = document.getElementsByName ("chkItem");
				  for(var i=0;i<checklist.length;i++){
						var vals=checklist[i].value;
						 if(code.length>0){
								$.each(code,function(i,result){
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
		}else{
			 $("#ztree").hide();
		}
}); 
    var parentId ;
	var addressId="${expert.address}";
	window.onload=function(){
		//地区回显和数据显示
		 $.ajax({
		url : "${pageContext.request.contextPath}/area/find_by_id.do",
		data:{"id":addressId},
		success:function(obj){
			$.each(obj,function(i,result){
				if(addressId == result.id){
					parentId = result.parentId;
				$("#add").append(result.name);
				}
			});
		}
	}); 
		//地区
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				$.each(obj,function(i,result){
					 if(parentId == result.id){
						$("#addr").append(result.name+",");
					}
				});
			}
		});
		validateBase();
		showJiGou();
	}
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li><a href="javascript:void(0)">评标专家信息查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">基本信息</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">专家类型</a></li>
						</ul>
<!-- 修改订列表开始-->
   <div class="container">
   <form action=""  method="post" id="form1" class="registerform"> 
   <input type="hidden" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
  <div class="tab-content padding-top-20" style="height: 900px;">
	<div class="tab-pane fade active in height-450" id="tab-1">
   <ul class="list-unstyled list-flow p0_20">
     <table class="table table-bordered">
	  <tbody>
				  <tr>
				    <td width="25%" class="info">姓名：</td>
				    <td width="25%">${expert.relName }</td>
				    <td width="25%" class="info">手机：</td>
				    <td width="25%">${expert.mobile }</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">联系电话（固话）：</td>
				    <td width="25%">${expert.telephone }</td>
				    <td width="25%" class="info">单位地址：</td>
				    <td width="25%">${expert.unitAddress }</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">性别：</td>
				    <td width="25%"> 
				      <c:forEach items="${sexList}" var="sex">
				        <c:if test="${expert.gender eq sex.id }">${sex.name}</c:if>
				      </c:forEach>
	     			</td>
				    <td width="25%" class="info">出生日期：：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
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
				    <td width="25%"><fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    <td width="25%" class="info">最高学历：</td>
				    <td width="25%">
				      <c:forEach items="${xlList }" var="xl">
				          <c:if test="${expert.hightEducation eq xl.id }">${xl.name}</c:if>
				      </c:forEach>
				    </td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">专业：</td>
				    <td width="25%">${expert.major }</td>
				    <td width="25%" class="info">从事专业年度：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">工作单位：</td>
				    <td width="25%">${expert.workUnit }</td>
				    <td width="25%" class="info">传真：</td>
				    <td width="25%">${expert.fax }</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">邮政编码：</td>
				    <td width="25%">${expert.postCode }</td>
				    <td width="25%" class="info">取得技术时间：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">学位：</td>
				    <td width="25%">${expert.degree }</td>
				    <td width="25%" class="info">健康状态：</td>
				    <td width="25%">${expert.healthState }</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">现任职务：</td>
				    <td width="25%">${expert.atDuty }</td>
				    <td width="25%" class="info"></td>
				    <td width="25%"></td>
				  </tr>
		</tbody>
	 </table>
   </ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>附件信息</h2>
   </div>
    <table class="table table-bordered">
										  	<tr>
										  	   <td width="25%" class="info">身份证:</td>
										  	   <td >
										          <up:show  delete="false" showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"/>
										  	   </td>
										  	   <td width="25%" class="info">学历证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td width="25%" class="info">职称证书:</td>
										  	   <td>
										          <up:show  delete="false"  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_TITLE_TYPEID }"/>
										  	   </td>
										  	   <td width="25%" class="info">学位证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show4"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_DEGREE_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td width="25%" class="info">个人照片:</td>
										  	   <td>
										          <up:show  delete="false" showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_PHOTO_TYPEID }"/>
										  	   </td>
										  	    <td width="25%" class="info">专家申请表：</td>
										   	    <td width="25%">
												   <up:show showId="show6" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
										   	    </td>
										  	</tr>  
										  	 <tr>
										   	    <td width="25%" class="info">专家合同书：</td>
										   	    <td>
												   <up:show showId="show7" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
										   	    </td>
										   	    <td></td><td></td>
										   	  </tr>
										  </table>
    <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>采购机构</h2>
   </div>
   <table class="table table-condensed" >
  	<tr>
		<th>采购机构名称：</th><td>${purchase.name }</td>
		<th>采购机构联系人：</th><td>${purchase.princinpal }</td>
		<th>采购机构地址：</th><td>${purchase.detailAddr }</td>
		<th>联系电话：</th><td>${purchase.mobile }</td>
	</tr>
	</table>
  </div>
  </div>
  </div> 
   <div class="tab-pane fade height-450" id="tab-2">
		<div class="margin-bottom-0  categories">
		  <ul class="list-unstyled list-flow" style="margin-left: 250px;">
			  <table>
			    <input type="hidden" id="expertsTypeId" value="${expert.expertsTypeId }" >
		        <tbody>
		          <tr>
				    <td width="90px;" class="info"> <h4>专家类型：</h4></td> 
				    <td width="100px;">
				      <h4>
				      <c:if test="${expert.expertsTypeId eq '1' }">技术</c:if>
				      <c:if test="${expert.expertsTypeId eq '2' }">法律</c:if>
				      <c:if test="${expert.expertsTypeId eq '3' }">商务</c:if>
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
		              <span><input type="checkbox" name="chkItem" disabled="disabled"  value="${obj.code}" />${obj.name} </span>
		            </c:forEach>
		          </div>
		       </div>
		       <div id="hwType" class="dnone">
		         <div class="col-md-5 title"><span class="star_red fl">*</span>货物分类：</div>
		          <div class="col-md-7 service_list">
		            <c:forEach items="${hwList}" var="hw" >
		            <span><input type="checkbox" name="chkItem" disabled="disabled"   value="${hw.code}" />${hw.name} </span>
		            </c:forEach>
		          </div>
		        </div>
    		 </ul>
		</div>
	</div>
	
	<!-- <div class="tab-pane fade height-450" id="tab-3">
			<div class="margin-bottom-0  categories">
			</div>
	</div> -->
  </div>
   <br/><br/>
  <div  class="col-md-12">
   <div class="fl padding-10">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </form>
                </div>
 	          </div>
		   </div>
		 </div>
	  </div>
	</div>
</div>
</body>
</html>