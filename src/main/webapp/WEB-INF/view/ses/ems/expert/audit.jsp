<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tld/upload" prefix="up"%>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<script type="text/javascript">
  $(function() {
	//回显已选产品
	   var id="${expert.id}";
	   var count=0;
	   var expertsTypeId = $("#expertsTypeId").val();
	   //控制品目树的显示和隐藏
	   if(expertsTypeId==1 || expertsTypeId=="1"){
		  $("#ztree").show();
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
		}else{
		  $("#ztree").hide();
			 }
  });

  window.onload=function(){
		var parentId ="";
		var addressId="${expert.address}";
		//alert(addressId);
		//地区回显和数据显示
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_id.do",
			data:{"id":addressId},
			success:function(obj){
				//alert(JSON.stringify(obj));
				//var data = eval('(' + obj+ ')');
				$.each(obj,function(i,result){
					if(addressId == result.id){
						parentId = result.parentId;
						//alert(parentId);
					$("#addr").append(result.name);
					}
				});
			}
		});
		
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				//var data = eval('(' + obj + ')');
				//alert(parentId);
				$.each(obj,function(i,result){
					if(parentId == result.id){
						$("#add").append(result.name+",");
					}
				});
			}
		});
  }	
	 //是否通过标示
	function pass(flag){
		$("#isPass").val(flag);
		var remark = $("#remark").val(); 
		if(remark.replace(/(^\s*)|(\s*$)/g, "")=="" || remark==null){
			layer.alert("请填写意见！",{offset: ['750px', '400px'],shade:0.01});
		}else{
			$("#form1").submit();
		}
	}
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li class="active"><a href="javascript:void(0)">评标专家审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 项目戳开始 -->
		<div class="container">
         <div class="tab-content">
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
				<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">基本信息</a></li>
				<li> <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">专家类型</a></li>
			</ul>
<!-- 修改订列表开始-->
  <form action="${pageContext.request.contextPath}/expert/shenhe.html"  method="post" id="form1"  class="registerform"> 
    <input type="hidden"  name="token2" value="<%=tokenValue%>">
    <input type="hidden" name="id" value="${expert.id}">
    <input type="hidden" name="isPass" id="isPass"/>
    <div class="tab-content">
	<div class="tab-pane fade in active" id="tab-1">
	<h2 class="count_flow"><i>1</i>个人信息</h2>
  <ul class="ul_list">
    <table class="table table-bordered">
	  <tbody>
		  <tr>
		    <td  class="info">姓名：</td>
		    <td >${expert.relName}</td>
		    <td  class="info">手机：</td>
		    <td >${expert.mobile}</td>
		  </tr>
		   <tr>
		    <td  class="info">联系电话（固话）：</td>
		    <td >${expert.telephone}</td>
		    <td  class="info">单位地址：</td>
		    <td >${expert.unitAddress}</td>
		  </tr>
		  <tr>
		    <td  class="info">性别：</td>
		    <td > 
		      <c:forEach items="${sexList}" var="sex">
		          <c:if test="${expert.gender eq sex.id }">${sex.name}</c:if>
		      </c:forEach>
    			</td>
		    <td  class="info">出生日期：：</td>
		    <td ><fmt:formatDate type='date' value='${expert.birthday}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		  </tr>
		  <tr>
		    <td  class="info">政治面貌：</td>
		    <td>
		      <c:forEach items="${zzList}" var="zz">
		        <c:if test="${expert.politicsStatus eq zz.id }">${zz.name}</c:if>
		      </c:forEach>
		    </td>
		    <td  class="info">专家来源：</td>
		    <td >
		      <c:forEach items="${lyTypeList}" var="ly">
		         <c:if test="${expert.expertsFrom eq ly.id }">${ly.name}</c:if>
		      </c:forEach>
		    </td>
		    
		  </tr>
		  <tr>
		    <td  class="info">证件类型：</td>
		    <td>
		      <c:forEach items="${idTypeList}" var="id">
		         <c:if test="${expert.idType eq id.id }">${id.name}</c:if>
		      </c:forEach>
		    </td>
		    <td  class="info">证件号码：</td>
		    <td >${expert.idNumber}</td>
		  </tr>
		  <tr>
		    <td  class="info">所在地区：</td>
		    <td >
		      <font id="add"></font><font id="addr"></font>
		    </td>
		    <td  class="info">民族：</td>
		    <td >${expert.nation}</td>
		  </tr>
		  <tr>
		    <td  class="info">毕业院校：</td>
		    <td >${expert.graduateSchool}</td>
		    <td  class="info">专家技术职称：</td>
		    <td >${expert.professTechTitles}</td>
		  </tr>
		  <tr>
		    <td  class="info">参加工作时间：</td>
		    <td ><fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		    <td  class="info">最高学历：</td>
		    <td>
		      <c:forEach items="${xlList}" var="xl">
		        <c:if test="${expert.hightEducation eq xl.id }">${xl.name}</c:if>
		      </c:forEach>
		    </td>
		  </tr>
		   <tr>
		    <td  class="info">专业：</td>
		    <td >${expert.major}</td>
		    <td  class="info">从事专业年度：</td>
		    <td ><fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		  </tr>
		   <tr>
		    <td  class="info">工作单位：</td>
		    <td >${expert.workUnit}</td>
		    <td  class="info">传真：</td>
		    <td >${expert.fax}</td>
		  </tr>
		   <tr>
		    <td  class="info">邮政编码：</td>
		    <td >${expert.postCode}</td>
		    <td  class="info">取得技术时间：</td>
		    <td ><fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		  </tr>
		   <tr>
		    <td  class="info">学位：</td>
		    <td >${expert.degree}</td>
		    <td  class="info">健康状态：</td>
		    <td >${expert.healthState}</td>
		  </tr>
		   <tr>
		    <td  class="info">现任职务：</td>
		    <td >${expert.atDuty}</td>
		    <td  class="info"></td>
		    <td ></td>
		  </tr>
	  </tbody>
	</table>
  </ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>2</i>附件信息</h2>
    <ul class="ul_list">
	  <table class="table table-bordered">
	  	<tr>
	  	   <td  class="info">身份证:</td>
	  	   <td>
	          <up:show  delete="false" showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
	  	   </td>
	  	   <td  class="info">学历证书:</td>
	  	   <td>
	          <up:show  delete="false" showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
	  	   </td>
	  	</tr>
	  	<tr>
	  	   <td  class="info">职称证书:</td>
	  	   <td>
	          <up:show  delete="false"  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
	  	   </td>
	  	   <td  class="info">学位证书:</td>
	  	   <td>
	          <up:show  delete="false" showId="show4"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
	  	   </td>
	  	</tr>
	  	<tr>
	  	   <td  class="info">个人照片:</td>
	  	   <td>
	          <up:show  delete="false" showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
	  	   </td>
	  	    <td  class="info">专家申请表：</td>
	   	    <td>
			   <up:show showId="show6" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}"/>
	   	    </td>
	  	</tr>  
	  	 <tr>
	   	    <td  class="info">专家合同书：</td>
	   	    <td>
			   <up:show showId="show7" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}"/>
	   	    </td>
	   	    <td></td><td></td>
	   	  </tr>
	  </table>
	 </ul>
  </div>		 
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>3</i>采购机构</h2>
    <ul class="ul_list">
      <table class="table table-bordered">
  	   <tr>
         <td class="bggrey">采购机构名称：</td><td>${purchase.name }</td>
         <td class="bggrey">采购机构联系人：</td><td>${purchase.princinpal }</td>
       </tr>
       <tr>
         <td class="bggrey">采购机构地址：</td><td>${purchase.detailAddr }</td>
         <td class="bggrey">联系电话：</td><td>${purchase.mobile }</td>
       </tr>
	  </table>
	</ul>
  </div>
  <div class="padding-top-10 clear">
    <ul class="ul_list">
	  <li class="col-md-12 col-sm-12 col-xs-12">
	    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">审核意见</span>
	    <div class="col-md-12 col-sm-12 col-xs-12 p0">
          <textarea class="col-md-12 col-sm-12 col-xs-12 " style="height:130px" maxlength="200" id="remark" name="remark"  placeholder="不超过200个字"></textarea>
        </div>
	  </li> 
    </ul>
  </div>
  <div class="col-md-12 tc">
    <input class="btn btn-windows git" type="button" onclick="pass('1');" value="通过">
	<input class="btn btn-windows cancel" type="button" onclick="pass('2');" value="不通过">
	<input class="btn btn-windows withdraw" type="button" onclick="pass('3');" value="退回修改">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
  </div>
  </div>
   <div class="tab-pane fade height-450"  id="tab-2">
     <div class="margin-bottom-0  categories" >
	   <ul class="ul_list">
	  <div class="margin-bottom-0  categories">
		<ul class="list-unstyled list-flow" style="margin-left: 250px;">
     	  <li class="p0">
		    <span class="">专家类型：</span>
			  <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId}">
			  <select name="expertsTypeId" id="expertsTypeId"  disabled="disabled" class="w178">
		   		 <option value="">-请选择-</option>
		   		 <option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
		   		 <option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
		   		 <option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			  </select>
		   </li>
         </ul>
         <ul class="" id="ztree" >
  			<div>
		      <div class="col-md-5 title"><span class="star_red fl">*</span>产品服务/分类：</div>
			  <div class="col-md-7 service_list">
				  <c:forEach items="${spList }" var="obj" >
					 <span><input type="checkbox" name="chkItem" disabled="disabled" value="${obj.code}" />${obj.name} </span>
			      </c:forEach>
			  </div>
			</div>
			<div id="hwType">
			  <div class="col-md-5 title"><span class="star_red fl">*</span>货物分类：</div>
			  <div class="col-md-7 service_list">
				  <c:forEach items="${hwList }" var="hw" >
					 <span><input type="checkbox" name="chkItem" disabled="disabled"  value="${hw.code}" />${hw.name} </span>
			      </c:forEach>
			  </div>
			</div>
 		  </ul>
        </div>
       </ul>
	  </div>
	</div>
  </div>
  </form>
    </div>
  </div>
</div>
</body>
</html>