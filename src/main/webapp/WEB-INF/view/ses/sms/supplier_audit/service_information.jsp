<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <html>
    <head>
			<title>服务-专业信息</title>
			<meta http-equiv="pragma" content="no-cache">
			<meta http-equiv="cache-control" content="no-cache">
			<meta http-equiv="expires" content="0">
			<style type="text/css">
				td {
				  cursor:pointer;
				}
				input {
				  cursor:pointer;
				}
			</style>
      <script type="text/javascript">
			  //默认不显示叉
			   $(function() {
			    $("td").each(function() {
			    $(this).parent("tr").find("td").eq(7).find("a").hide();
			    });
			  });
			   $(function() {
			    $(":input").each(function() {
			      $(this).parent("div").find("div").hide();
			        var onmouseover = "this.style.border='solid 1px #FF0000'";
			        var onmouseout = "this.style.border='solid 1px #D3D3D3'";
			        $(this).attr("onmouseover",onmouseover);
			        $(this).attr("onmouseout",onmouseout);
			    });
			  });

				function reason(id){
		 		  /* var offset = "";
				  if (window.event) {
				    e = event || window.event;
				    var x = "";
				    var y = "";
				    x = e.clientX + 20 + "px";
				    y = e.clientY + 20 + "px";
				    offset = [y, x];
				  } else {
				      offset = "200px";
				  } */ 
				  var supplierId=$("#supplierId").val();
				  /* var auditContent="服务资质证书为："+$("#"+id).text()+"的信息"; */ //审批的字段内容
				  var auditContent="服务-资质证书信息";
				  var index = layer.prompt({
					  title: '请填写不通过的理由：', 
					  formType: 2,
					  offset : '100px'
				   },
				    function(text){
				    $.ajax({
				        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				        type:"post",
				        data:"auditType=mat_serve_page"+"&auditFieldName=服务-资质证书"+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
				        dataType:"json",
				        success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				          layer.msg('该条信息已审核过！', {
				            shift: 6, //动画类型
				            offset:'100px'
				            });
				        }
				      }
				      });
				        $("#"+id+"_hidden").hide();
				        $("#"+id+"_show").show();
				        layer.close(index);
				    });
        }

				function reason1(id,auditField){
				  /* var offset = "";
				  if (window.event) {
				    e = event || window.event;
				    var x = "";
				    var y = "";
				    x = e.clientX + 20 + "px";
				    y = e.clientY + 20 + "px";
				    offset = [y, x];
				  } else {
				      offset = "200px";
				  } */
					  var supplierId=$("#supplierId").val();
					  var id2=id+"2";
					  var id3=id+"3";
					  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字
					  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
					  var index = layer.prompt({
						  title: '请填写不通过的理由：', 
						  formType: 2, 
						  offset: '100px'
					  }, 
				  function(text){
				    $.ajax({
				        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				        type:"post",
				        data:"auditType=mat_serve_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
				        dataType:"json",
				        success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				          layer.msg('该条信息已审核过！', {
				            shift: 6, //动画类型
				            offset:'100px'
				            });
				        }
				      }
				      });
					     $("#"+id3+"").show();
					     $("#"+id3+"").parents("li").find("input").css("padding-right","30px");
				       layer.close(index);
				    });
        }

				//只读
				$(function() {
				    $(":input").each(function() {
				      $(this).attr("readonly", "readonly");
				    });
				});

				function nextStep(){
				  var action = "${pageContext.request.contextPath}/supplierAudit/product.html";
				  $("#form_id").attr("action",action);
				  $("#form_id").submit();
				}


				//文件下載
				 /*  function downloadFile(fileName) {
				    $("input[name='fileName']").val(fileName);
				    $("#download_form_id").submit();
				  } */
  
				function download(id,key){
				  var form = $("<form>");   
				      form.attr('style', 'display:none');   
				      form.attr('method', 'post');
				      form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
				      $('body').append(form); 
				      form.submit();
				}
      </script>
      
			<script type="text/javascript">
			/*   function zhancun(){
			    var supplierId=$("#supplierId").val();
			    $.ajax({
			      url:"${pageContext.request.contextPath}/supplierAudit/temporaryAudit.html",
			      type:"post",
			      data:"id="+supplierId,
			      dataType:"json",
			      success:function(result){
			        result = eval("(" + result + ")");
			        if(result.msg == "success"){
			          layer.msg("暂存成功！");
			        }
			      },error:function(){
			        layer.msg("暂存失败！");
			      }
			    });
			  } */
			</script>
  </head>
  
  <body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
	    <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="#"> 首页</a>
            </li><li>
            <a href="#">供应商管理</a>
            </li><li>
            <a href="#">供应商审核</a>
          </li>
        </ul>
	    </div>
		</div> 
    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
	        <ul class="flow_step">
	          <li >
	            <a aria-expanded="false" href="#tab-1" >详细信息</a>
	            <i></i>
	          </li>
	          <li >
              <a aria-expanded="false" href="#tab-2" >财务信息</a>
              <i></i>                            
	          </li>
	          <li >
              <a aria-expanded="false" href="#tab-3" >股东信息</a>
              <i></i>
	          </li>
	          <c:if test="${fn:contains(supplierTypeNames, '生产')}">
	            <li>
	              <a aria-expanded="false" href="#tab-4">生产信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
		          <li >
		            <a aria-expanded="false" href="#tab-4" >销售信息</a>
		            <i></i>
		          </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
		          <li>
		            <a aria-expanded="false" href="#tab-4" >工程信息</a>
		            <i></i>
		          </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li class="active">
	              <a aria-expanded="true" href="#tab-4" data-toggle="tab">服务信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <li>
	            <a aria-expanded="false" href="#tab-4" >产品信息</a>
	            <i></i>
	          </li>
	          <li>
	            <a aria-expanded="false" href="#tab-4" >申请表</a>
	            <i></i>
	          </li>
	          <li>
              <a aria-expanded="false" href="#tab-4" >汇总</a>
	          </li>
	        </ul>
                    
          <form id="form_id" action="" method="post" >
              <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
          </form>

          <h2 class="count_flow"><i>1</i>供应商资质证书</h2>
          <ul class="ul_list count_flow">
            <table class="table table-bordered table-condensed table-hover">
	            <thead>
	              <tr>
	                <th class="info w50">序号</th>
	                <th class="info">资质证书名称</th>
	                <th class="info">资质等级</th>
	                <th class="info">发证机关</th>
	                <th class="info">有效期(起止时间)</th>
	                <th class="info">是否年检</th>
	                <th class="info">附件</th>
	                <th class="info w50">操作</th>
	              </tr>
	            </thead>
	            <c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
	              <tr>
	                <td class="tc">${vs.index + 1}</td>
	                <td class="tc" id="${s.id}" >${s.name }</td>
	                <td class="tc" >${s.levelCert}</td>
	                <td class="tc" >${s.licenceAuthorith }</td>
	                <td class="tc" >
	                  <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                  <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                </td>
	                <td class="tc">
	                 <c:if test="${s.mot==0 }">否</c:if>
	                 <c:if test="${s.mot==1 }">是</c:if>
	                </td>
	                <td class="tc">
	                   <a class="mt3 color7171C6" href="javascript:download('${s.attachId}', '${sysKey}')">${s.attach}</a>
	                <td class="tc w50">
	                  <p onclick="reason('${s.id}');" id="${s.id}_hidden" class="btn">审核</p>
	                  <a  id="${s.id}_show" class="b red">×</a>
	                </td>
	              </tr>
	            </c:forEach>
            </table>
          </ul>

          <h2 class="count_flow"><i>2</i>供应商组织结构和人员</h2>
            <ul class="ul_list">
	            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="orgName2">组织机构：</span>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <input id="orgName" class="span5" type="text" value="${supplierMatSes.orgName }" onclick="reason1(this.id,'supplierMatSe.orgName')"/>
	                <div id="orgName3"  class="abolish">×</div>
	              </div>
	            </li>
	            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalPerson2">人员总数：</span>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <input id="totalPerson" class="span5" type="text" value="${supplierMatSes.totalPerson }" onclick="reason1(this.id,'supplierMatSe.totalPerson')" />
	                <div id="totalPerson3" class="abolish">×</div>
	              </div>
	            </li>
	            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalMange2">管理人员：</span>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <input id="totalMange" class="span5" type="text"  value="${supplierMatSes.totalMange }" onclick="reason1(this.id,'supplierMatSe.totalMange')" />
	                <div id="totalMange3" class="abolish">×</div>
	              </div>
	            </li>
	            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalTech2">技术人员：</span>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <input id="totalTech" class="span5" type="text"  value="${supplierMatSes.totalTech }" onclick="reason1(this.id,'supplierMatSe.totalTech')" />
	              <div id="totalTech3" class="abolish">×</div>
	              </div>
	            </li>
	            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalWorker2">工人(职员)：</span>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                <input id="totalWorker" class="span5" type="text" value="${supplierMatSes.totalWorker }" onclick="reason1(this.id,'supplierMatSe.totalWorker')" />
	                <div id="totalWorker3" class="abolish">×</div>
	              </div>
	            </li>
            </ul>
          </div>
		      <div class="col-sm-12 col-xs-12 col-md-12 add_regist tc">
		        <!--  <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
		        <a class="btn"  type="button" onclick="nextStep();">下一步</a>
		      </div>
        </div>
      </div>   
			<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
			    <input type="hidden" name="fileName" />
			</form>
  </body>
</html>
