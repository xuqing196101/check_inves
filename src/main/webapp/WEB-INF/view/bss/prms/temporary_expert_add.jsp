<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<script type="text/javascript">
 
function sumbits(){
	
	
// 	$.ajax({
//         cache: true,
//         type: "POST",
<%--         url:"${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do", --%>
//         data:$('#form').serialize(),// 你的formid
//         async: false,
//         dataType:"json",
//         error: function(request) {
//         	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
//         	 parent.layer.close(index);
//         },
//         success: function(data) {
//         	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
//         	var list=data;
//             var html="";
//            for(var i=0;i<list.length;i++){
//             html+="<tr>"+
//                "<td class='tc w30'><input type='checkbox'"+
//                    "value='"+list[i].expert.id+"' name='chkItemExp' onclick='checkExp();'"+
//                    "alt=''></td>"+
//                "<td class='tc w30'>"+(i+1)+"</td>"+
//                "<td align='center'>"+list[i].expert.relName+"</td>";

//                if(list[i].expert.expertsTypeId == '1' ){
//                        html+="<td align='center'>技术</td>";
//                }else if(list[i].expert.expertsTypeId == '2' ){
//                        html+="<td align='center'>法律</td>";
//                }else if(list[i].expert.expertsTypeId == '3' ){
//                            html+="<td align='center'>商务</td>";
//                }
           

//                html+="<td align='center'>"+list[i].expert.idNumber+"</td>"+
//                "<td align='center'>"+list[i].expert.atDuty+"</td>"+
//                "<td align='center'>"+list[i].expert.unitAddress+"</td>"+
//                "<td align='center'>"+list[i].expert.mobile+"</td>"+
//            "</tr>";
//            }
//                  parent.$('#tbody').empty();
//                  parent.$('#tbody').append(html);

        	
//         	  parent.layer.close(index);
           
//         }
//     });
	}
</script>
<body>


   
<!-- 修改订列表开始-->
   <div class="  mt20">
   <form action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" id="form" method="post" >
   <input type="hidden" value="${projectId}" name="projectId"/>
   <ul class="list-unstyled list-flow ">
     <li class="col-md-6 p0">
	   <span class="">专家姓名：</span>
	   <div class="input-append">
        <input class="span2" maxlength="10" id="appendedInput" name="relName" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
	    <li class="col-md-6 p0 ">
       <span class=" ">证件类型：</span>
         <div class="select_common mb10">
         <select class="w250 " name="idType">
           <option>-请选择-</option>
           <option value="身份证">身份证</option>
           <option value="士兵证">士兵证</option>
           <option value="驾驶证">驾驶证</option>
           <option value="工作证">工作证</option>
           <option value="护照">护照</option>
           <option value="其他">其他</option>
         </select>
         </div>
     </li>
     <li class="col-md-6  p0 ">
	   <span class="">证件号码：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" name="idNumber" maxlength="18" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
	  <li class="col-md-6 p0 ">
       <span class=" ">专家类型：</span>
         <div class="select_common mb10">
         <select class="w250 " name="expertsTypeId">
           <option>-请选择-</option>
           <option value="1">技术</option>
           <option value="2">法律</option>
           <option value="3">商务</option>
         </select>
         </div>
     </li>
     <li class="col-md-6  p0 ">
	   <span class="">现任职务：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" name="atDuty" maxlength="10" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">联系地址：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" name="unitAddress" maxlength="20" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">联系电话：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" name="mobile" maxlength="11" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	  <li class="col-md-6  p0 ">
       <span class="">分配账户：</span>
       <div class="input-append">
        <input class="span2" id="appendedInput" name="loginName" maxlength="11" type="text">
        <span class="add-on">i</span>
       </div>
     </li> 
      <li class="col-md-6  p0 ">
       <span class="">分配密码：</span>
       <div class="input-append">
        <input class="span2" id="appendedInput" name="loginPwd" maxlength="11" type="text">
        <span class="add-on">i</span>
       </div>
     </li>  
     <li class="col-md-12 p0">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " maxlength="200"  title="不超过200个字" placeholder="不超过200个字"></textarea>
       </div>
	 </li> 
	 
   </ul>
  <div  class="col-md-12">
   <div class="col-md-6" align="center">
    <button class="btn btn-windows add" onclick="sumbits();" type="button">添加</button>
	</div>
  </div>
  </form>
 </div>
</body>
</html>
