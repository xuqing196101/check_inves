<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../../../common.jsp"%>
    <base href="${pageContext.request.contextPath}/">
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>

  <script type="text/javascript">
  
  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/user/show.html?id="+id;
  	}
  	//保存监督人员
    function add(){
        var _phoneVal = $("input[name='phone']").val();
        var _relNameVal = $("input[name='relName']").val();
        var _companyVal = $("input[name='company']").val();
        var _dutiesVal = $("input[name='duties']").val();
        if(_relNameVal!=""&&_companyVal!=""&&_dutiesVal!=""){
            if(!check_mobile(_phoneVal)){
                layer.msg("请输入正确的手机号码");
            }else{
                $.ajax({
                    cache: true,
                    type: "POST",
                    dataType : "json",
                    url:'${pageContext.request.contextPath}/ExpExtract/saveSupervise.do',
                    data:$('#form1').serialize(),// 你的formid
                    async: false,
                    success: function(data) {
                        if(data != 'ERROR'){
                            parent.$('#supervises').val(data.relName);
                            parent.$('#supervises').attr(data.relName);
                            parent.$('#superviseId').val(data.superviseId);
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);
                        }else{
                            layer.msg("请输入必填项");
                        }
                    },
                    error:function(data){
                        layer.msg("请输入必填项");
                    }
                });
            }
        }else{
            layer.msg("请输入必填项");
        }
    }
    
    /**添加*/
    function addBranch(btn){
    	var html = "<tr>"+   
            "<td class='tc'><input type='text'  name='relName' /> </td>"+
            "<td class='tc'><input type='text' name='company' /></td>"+
            "<td class='tc'><input type='text' name='duties' /></td>"+
            "<td class='tc'><input type='text' name='phone' /> </td>"+
            "<td class='tc'>"+
              "<input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>"+
              "<input type='button' onclick='delBranch(this)' class='btn list_btn' value='一'/>"+
            "</td>"+
        "</tr>";
    	
    	$("#tbody").append(html);
    }
    
    /**删除*/
    function delBranch(btn){
    	var le=$("#tbody tr").length;
    	if(le==1){
    		$("#tbody").find(":input").not(":button,:submit,:reset,:hidden").val("");
    	}else{
    		  $(btn).parent().parent().remove();		
    	}
    
    }
    
  </script>
  <body>
<!-- 表格开始-->

   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     <form id="form1" >
     <input name="projectId" value="${projectId}" type="hidden">
     <input name="type" value="${type}" type="hidden" > 
        <table class="table table-bordered table-condensed table_input left_table">
		<thead>
		<tr>
		  <th class="info "><span class="star_red">*</span>姓名</th>
		  <th class="info "><span class="star_red">*</span>单位</th>
		  <th class="info "><span class="star_red">*</span>职务</th>
		  <th class="info "><span class="star_red">*</span>手机号</th>
		  <th class="info w100 ">操作</th>
		</tr>
		</thead>
		<tbody id="tbody">
		<c:forEach items="${list}" var="list">
				<tr>		
					   <td class="tc"><input type="text"  name="relName" value="${list.relName}" /> </td>
					   <td class="tc"><input type="text" name="company" value="${list.company}" /></td>
					   <td class="tc"><input type="text" name="duties" value="${list.duties}" /> </td>
					   <td class="tc"><input type="text"  name="phone" value="${list.phone}" /> </td>
					   <td class="w100 ml10">
					     <input type="button" onclick="addBranch(this)" class="btn list_btn" value="十"/>
	             <input type="button" onclick="delBranch(this)" class="btn list_btn" value="一"/>
					  </td>
				</tr>
		</c:forEach>
		<c:if test="${fn:length(list)==0}">
		  <tr>    
            <td class="tc"><input type="text"  name="relName"  /> </td>
            <td class="tc"><input type="text" name="company" /></td>
            <td class="tc"><input type="text" name="duties"  /> </td>
            <td class="tc"><input type="text" name="phone"  /> </td>
            <td class="w100">
              <input type="button" onclick="addBranch(this)" class="btn list_btn" value="十"/>
              <input type="button" onclick="delBranch(this)" class="btn list_btn" value="一"/>
            </td>
        </tr>
		</c:if>
		</tbody>
        </table>
        </form>
     </div>
   </div>
  </body>
</html>
<script type="text/javascript">
    $("input[name='phone']").on('change',function () {
        var _phoneVal = $(this).val();
        if(!check_mobile(_phoneVal)){
            layer.msg("请输入正确的手机号码");
        }
    })
</script>