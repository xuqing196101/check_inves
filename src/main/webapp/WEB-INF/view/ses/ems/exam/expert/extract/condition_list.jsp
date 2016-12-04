<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>抽取列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">
    
<script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
              var page = location.search.match(/page=(\d+)/);
              return page ? page[1] : 1;
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新projectNumber
                  location.href = '${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&page='+e.curr;
              }
          }
       
          
      });
	  var typeclassId = "${typeclassId}";
	  if( typeclassId != null && typeclassId != ""){
		  $("#tenderTimeId").removeAttr("readonly");
	  } else {
		  $("#tenderTimeId").attr("rereadonly","readonly");
	  }
	  
	   
	  
	   
      $('#minute').bind('input propertychange', function() {
          var count=$(this).val();
              if(count>60){
                  $("#minute").val("59");
              }
              if(count==0){
                  $("#minute").val("");
              }
             
            
         });
	  
	  $("#area1").empty();   
      //抽取地址回显    
       var extractAddress="${extractionSites}";
       if(extractAddress != null && extractAddress != ''){
           var extractAddressArray=extractAddress.split(","); 
           city1=extractAddressArray[1];
           <c:forEach items="${listArea}" var="item" varStatus="status" >  
           if("${item.name}" == extractAddressArray[0]){
                  $("#area1").append("<option selected='selected' value='${item.id}'>${item.name}</option>");
           }else{
           $("#area1").append("<option  value='${item.id}'>${item.name}</option>");
           }
           </c:forEach> 
       }else{
              <c:forEach items="${listArea}" var="item" varStatus="status" >  
              $("#area1").append("<option value='${item.id}'>${item.name}</option>");
              </c:forEach>   
       } 
       
       var areas1=$("#area1 option:selected").val();
       $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
            data:{area:areas1},
            dataType:"json",
            success: function(data){
                 var list = data;
                 $("#city1").empty();
                 for(var i=0;i<list.length;i++){
                     if(list[i].name==city1){
                         $("#city1").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
                     }
                      $("#city1").append("<option  value="+list[i].id+">"+list[i].name+"</option>");
                 }
            }
        }); 
       
       //获取包id
       var projectId = "${projectId}";
       if (projectId != null && projectId != ''){
           $("#projectName").attr("readonly",true);
           $("#projectNumber").attr("readonly",true);
           $("#packageName").attr("readonly",true);
       }else{
           $("#projectName").attr("readonly",false);
           $("#projectNumber").attr("readonly",false);
           $("#packageName").attr("readonly",false);
       }
  });
  
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
    
    function add(){

//      var projectName = $("#projectName").val();
//      var projectNumber = $("#projectNumber").val();
          $("#extAddress").val($("#area1 option:selected").text()+","+$("#city1 option:selected").text());
//        var projectId="${projectId}";
//        alert(projectId);
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/ExpExtract/validateAddExtraction.do',
            data:$('#form').serialize(),// 你的formid
            async: false,
            success: function(data) {
                $("#projectNameError").text("");
                $("#projectNumberError").text("");
                $("#packageNameError").text("");
                $("#dSupervise").text("");
                $("#tenderTimeError").text("");
                $("#responseTimeError").text("");
                
                
                var map =data;
                $("#projectNameError").text(map.projectNameError);
                $("#projectNumberError").text(map.projectNumberError);
                $("#packageNameError").text(map.packageNameError);
                $("#dSupervise").text(map.supervise);
                $("#tenderTimeError").text(map.tenderTimeError);
                $("#responseTimeError").text(map.responseTimeError);
                if(map.status != null && map.status!=0){
                     layer.alert("请全部抽取完之后在添加条件",{shade:0.01});
                }
                if(map.sccuess=="SCCUESS"){
                    var projectId=map.projectId;
                      window.location.href = '${pageContext.request.contextPath}/ExpExtract/addExtractions.html?projectId='+projectId+'&&typeclassId=${typeclassId}';
                }
            }
        });

    }
    function extract(id,btn){
    	  layer.open({
              type: 2, //page层
              area: ['90%', '50%'],
              title: '抽取专家 项目名称： ${projectName}',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              shadeClose: true,
              offset: '30px',
              move:false,
              content: '${pageContext.request.contextPath}/ExpExtract/extractCondition.html?cId='+id
            });
    	  $(btn).next().remove();
          $(btn).parent().parent().find("td:eq(2)").html("抽取中");
    	  
    }
    
    //选择监督人员
    function supervise(){
        //  iframe层
        var iframeWin;
            layer.open({
              type: 2,
              title:"选择监督人员",
              shadeClose: true,
              shade: 0.01,
              offset: '20px',
              move: false,
              area: ['90%', '50%'],
              content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
              success: function(layero, index){
                  iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
              btn: ['保存', '关闭'] 
                  ,yes: function(){
                      iframeWin.add();
                  
                  }
                  ,btn2: function(){
                    layer.closeAll();
                  }
            }); 
        }
    
    function update(id){
    	  location.href = '${pageContext.request.contextPath}/ExtCondition/showExtCondition.html?Id='+id;
  }
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !=''  }">
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑环境系统</a></li>
				<li><a href="#">专家管理</a></li>
				<li><a href="#">专家抽取</a></li>
				<li class="active"><a href="#">专家抽取列表</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	</c:if>
	
	
	
    <!-- 项目戳开始 -->
    <div class="container container_box">
           <form id="form">
            <!-- 抽取地区 -->
            <input type="hidden" name="extAddress" id="extAddress"  value="${extractionSites}">
            <!-- 监督人员 -->
            <input type="hidden" name="sids" id="sids" value="${userId}" />
            <!-- 打开类型 -->
            <input type="hidden" value="${typeclassId}" name="typeclassId"/>
            <!-- 项目id  -->
            <input type="hidden" id="projectId" value="${projectId}" name="id">
               <div>
                <h2 class="count_flow"><i>1</i>必填项</h2>
               <ul class="ul_list">
                 <li class="col-md-4 col-sm-6 col-xs-12 pl15">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i>抽取地区:</span>
                   <div class="input-append">
                    <select class="w133" id="area1" onchange="areas1();">
                            </select> <select name="extractionSites" class="w93" id="city1"></select>
                   </div>
                 </li>
                 <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>监督人员:</span>
                   <div class="input-append">
                       <input class="span5" readonly id="supervises"  title="${userName}"
                                value="${userName}" onclick="supervise();" type="text">
                    <span class="add-on">i</span>
                    <div class="cue" id="dSupervise"></div>
                   </div>
                 </li>
                 <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>项目名称:</span>
                   <div class="input-append">
                    <input class="span5"  id="projectName"  name="name" value="${projectName}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                   </div>
                 </li>
                 <li class="col-md-4 col-sm-6 col-xs-12">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>项目编号:</span>
                   <div class="input-append">
                    <input class="span5"  id="projectNumber" name="projectNumber" value="${projectNumber}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="projectNumberError"></div>
                   </div>
                 </li>
                   <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>包名:</span>
                   <div class="input-append">
                    <input class="span5"   id="packageName" name="packageName" value="${packageName}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="packageNameError"></div>
                   </div>
                 </li>
                 <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>采购方式:</span>
                   <div class="input-append">
                   <select class="w230" name="purchaseType">
                        <c:forEach items="${findByMap}" var="map">
                            <option value="${map.id}">${map.name}</option>
                        </c:forEach>
                   </select>
                   </div>
                 </li>
                  <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>开标时间:</span>
                   <div class="input-append">
                    <input class="Wdate w230"  onclick="WdatePicker();"  id="tenderTimeId"  name="tenderTime"
                       value="<fmt:formatDate value='${tenderTime}'
                                pattern='yyyy-MM-dd' />"
                       maxlength="30"   type="text">
                       <div class="cue" id="tenderTimeError"></div>
                       </div>
                 </li>
                   <li class="col-md-4 col-sm-6 col-xs-12 ">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>响应时间:</span>
                   <div class="input-append">
                        <input class="w108" name="hour" value="${hour}" 
	                       maxlength="3" type="text"><span class="f14">时</span><input
	                       class="w108" value="${minute}" id="minute" name="minute"
	                       maxlength="3" type="text"
	                       onkeyup="this.value=this.value.replace(/\D/g,'')"
	                       onafterpaste="this.value=this.value.replace(/\D/g,'')"><span class="f14">分</span>
	                         <div class="cue" id="responseTimeError"></div>
                   </div>
                 </li>
                 
                 
                  <div class="margin-bottom-5" >
                    <button class="btn btn-windows add" type="button" onclick="add();">添加条件</button>
                </div>
               <table class="table table-bordered table-condensed ">
                <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">抽取条件</th>
                        <th class="info">状态</th>
                        <th class="info">操作</th>
                    </tr>
                </thead>
                <c:forEach items="${list.list}" var="obj" varStatus="vs">
                    <tr >
                            <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                        <td class="ww50">第【${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}】次抽取，
                                                                        
                            <c:forEach items="${stemFrom}" var="fm">
                              <c:if test="${fm.id eq obj.expertsFrom }">
                                                                                            专家来源【 ${ fm.name}】，
                              </c:if>
                            </c:forEach>                                                   
                                                                           
                                                                            
                                                                             专家所在地区【${ obj.address}】
                        <c:forEach items="${obj.conTypes }" var="contypes">
                         ，  专家类型
                          <c:choose>
                              <c:when test="${contypes.expertsTypeId==1}">
                                                【技术】
                              </c:when>
                              <c:when test="${contypes.expertsTypeId==2}">
                                                【法律】
                              </c:when>
                              <c:when test="${contypes.expertsTypeId==3}">
                                               【商务】
                              </c:when>
                          </c:choose>
                           <c:if test="${contypes.categoryName!=null}">
                          <c:set var="re" value="${fn:replace(contypes.categoryName,'^',',')}"/>
                                                                               ， 采购类别【 ${re}】
                          </c:if>
                            ,专家数量【${contypes.expertsCount }】
                        </c:forEach>                    
                        </td>
                        <td class="tc w50" id="status"><c:if test="${obj.status==1}">
                                                                           待抽取
                      </c:if> <c:if test="${obj.status==2}">
                                                             抽取中
                        </c:if>
                         <c:if test="${obj.status==3}">
                                                     已抽取
                        </c:if>
                </td>
                        <td class="tc w100" align="center" >
                            <button 
                                class="btn" 
                                id="save" type="button" onclick="extract('${obj.id }',this);">抽取</button>
                                
                                
                                <c:if test="${obj.status==1 }">
                                        <button
                                class="btn margin-top-10"
                                id="save" type="button" onclick="update('${obj.id }');">修改</button>
                                </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div id="pagediv" align="right"></div>
               </ul>
              </div>
          </form>
     </div> 

</body>
</html>
