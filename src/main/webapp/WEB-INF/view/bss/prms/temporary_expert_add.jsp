<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<jsp:include page="/static/backend_common.jsp"></jsp:include>   
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
          <div class="drop_window">
<%--    <form action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" id="form" method="post" > --%>
<%--    <input type="hidden" value="${projectId}" name="projectId"/> --%>
     <ul class="list-unstyled">
      <li class="col-md-6">
         <label class="col-md-12 padding-left-5">专家姓名：</label>
	    <span class="col-md-12 p0">
        <input class="col-md-12 p0" maxlength="10" id="appendedInput" name="relName" type="text"/>
        </span>
	 </li>
	    <li class="col-md-6  ">
	      <label class="col-md-12 padding-left-5">证件类型：</label>
         <span class="col-md-12 p0">
         <select class="col-md-12 p0" name="idType">
           <option>-请选择-</option>
           <option value="身份证">身份证</option>
           <option value="士兵证">士兵证</option>
           <option value="驾驶证">驾驶证</option>
           <option value="工作证">工作证</option>
           <option value="护照">护照</option>
           <option value="其他">其他</option>
         </select>
        </span>
     </li>
     <li class="col-md-6 ">
        <label class="col-md-12 padding-left-5">证件号码：</label>
	    <span class="col-md-12 p0">
         <input class="col-md-12 p0" id="appendedInput" name="idNumber" maxlength="18" type="text">
       </span>
	 </li>
	  <li class="col-md-6">
	    <label class="col-md-12 padding-left-5">专家类型：</label>
        <span>
         <select class="col-md-12 p0" name="expertsTypeId">
           <option>-请选择-</option>
           <option value="1">技术</option>
           <option value="2">法律</option>
           <option value="3">商务</option>
         </select>
        </span>
     </li>
     <li class="col-md-6">
        <label class="col-md-12 padding-left-5">现任职务：</label>
        <span class="col-md-12 p0">
        <input class="col-md-12 p0" id="appendedInput" name="atDuty" maxlength="10" type="text">
        </span>
	 </li> 
     <li class="col-md-6 ">
       <label class="col-md-12 padding-left-5">联系地址：</label>
	   <span class="col-md-12 p0">
        <input class="col-md-12 p0" id="appendedInput" name="unitAddress" maxlength="20" type="text">
       </span>
	 </li> 
     <li class="col-md-6">
       <label class="col-md-12 padding-left-5">联系电话：</label>
	   <span class="col-md-12 p0">
        <input class="col-md-12 p0" id="appendedInput" name="mobile" maxlength="11" type="text">
       </span>
	 </li> 
	  <li class="col-md-6">
	    <label class="col-md-12 padding-left-5">分配账户：</label>
        <span>
        <input class="col-md-12 p0" id="appendedInput" name="loginName" maxlength="11" type="text">
        </span>
     </li> 
      <li class="col-md-6">
        <label class="col-md-12 padding-left-5">分配密码：</label>
       <span class="col-md-12 p0">
        <input class="col-md-12 p0" id="appendedInput" name="loginPwd" maxlength="11" type="text">
       </span>
     </li>  
     <li class="col-md-12 p0">
       <label class="col-md-12 padding-left-5">备注：</label>
        <textarea class="col-md-12 h80 p0" maxlength="200"  title="不超过200个字" placeholder="不超过200个字"></textarea>
	 </li> 
   </ul>
  <div  class="col-md-12">
   <div class="col-md-6" align="center">
    <button class="btn btn-windows add" onclick="sumbits();" type="button">添加</button>
	</div>
  </div>
<!--   </form> -->
 </div>
</body>
</html>
