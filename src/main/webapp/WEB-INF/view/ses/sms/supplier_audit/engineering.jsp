<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <html>
    <head>
			<title>工程-专业信息</title>
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
				    $(this).parent("tr").find("td").eq(14).find("a").hide();
				    $(this).parent("tr").find("td").eq(3).find("a").hide();
		    });
	    
		    $(":input").each(function() {
		      $(this).parent("div").find("div").hide();
		      var onmouseover = "this.style.border='solid 1px #FF0000'";
		      var onmouseout = "this.style.border='solid 1px #D3D3D3'";
		      $(this).attr("onmouseover",onmouseover);
		      $(this).attr("onmouseout",onmouseout);
		      });
	      });
			
				function reason(id,auditContent){
				  var offset = "";
				  if (window.event) {
				    e = event || window.event;
				    var x = "";
				    var y = "";
				    x = e.clientX + 20 + "px";
				    y = e.clientY + 20 + "px";
				    offset = [y, x];
				  } else {
				      offset = "200px";
				  }
				   var supplierId=$("#supplierId").val();
				   var auditFieldName= auditContent.replace("信息","");
				   var index = layer.prompt({
				   title: '请填写不通过的理由：', 
				   formType: 2, 
				   offset : offset,
				 }, 
				function(text){
				$.ajax({
				  url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				  type:"post",
				  data:"auditType=mat_eng_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
				  dataType:"json",
				  success:function(result){
				    result = eval("(" + result + ")");
				    if(result.msg == "fail"){
				      layer.msg('该条信息已审核过！', {
				        shift: 6, //动画类型
				        offset:'300px'
				        });
				      }
				    }
				  });
				    $("#"+id+"_show").show();
				    $("#"+id+"_show1").show();
				    $("#"+id+"_show2").show();
				    layer.close(index);
				  });
				}
			
				function reason1(id,auditField){
				  var offset = "";
				  if (window.event) {
				    e = event || window.event;
				    var x = "";
				    var y = "";
				    x = e.clientX + 20 + "px";
				    y = e.clientY + 20 + "px";
				    offset = [y, x];
				  } else {
				      offset = "200px";
				  }
				  var supplierId=$("#supplierId").val();
				  var id2=id+"2";
				  var id3=id+"3";
				  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字 
				  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
				  var index = layer.prompt({
				  title: '请填写不通过的理由：', 
				  formType: 2, 
				   offset : offset,
				  }, 
				  function(text){
				    $.ajax({
				        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				        type:"post",
				        data:"auditType=mat_eng_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
				        dataType:"json",
				        success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				          layer.msg('该条信息已审核过！', {
				            shift: 6, //动画类型
				            offset:'300px'
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
			  
			  
				function nextStep(url){
				  $("#form_id").attr("action",url);
				  $("#form_id").submit();
				}
			
				//文件下載
				/*   function downloadFile(fileName) {
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
			          layer.msg("暂存成功！",{offset:'200px'});
			        }
			      },error:function(){
			        layer.msg("暂存失败！",{offset:'200px'});
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
	            <li class="active">
	              <a aria-expanded="true" href="#tab-4" data-toggle="tab">工程信息</a>
	              <i></i>
	            </li>
	          </c:if>
            <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li>
	              <a aria-expanded="false" href="#tab-4" >服务信息</a>
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

          <form id="form_id" action="" method="post">
              <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
          </form>
                  
          <h2 class="count_flow"><i>1</i>供应商资质证书信息</h2>
          <div class="ul_list count_flow">
	          <table class="table table-bordered table-condensed table-hover">
		          <thead>
		            <tr>
		              <th class="info">资质资格类型</th>
		              <th class="info">证书编号</th>
		              <th class="info">资质资格最高等级</th>
		              <th class="info">技术负责人姓名</th>
		              <th class="info">技术负责人职称</th>
		              <th class="info">技术负责人职务</th>
		              <th class="info">单位负责人姓名</th>
		              <th class="info">单位负责人职称</th>
		              <th class="info">单位负责人职务</th>
		              <th class="info">发证机关</th>
		              <th class="info">发证日期</th>
		              <th class="info">有效截止日期</th>
		              <th class="info">证书状态</th>
		              <th class="info">附件</th>
		             <th class="info"></th>
		            </tr>
		          </thead>
              <c:forEach items="${supplierCertEng}" var="s" >
                <tr>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certType }</td>
                  <td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certCode }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certMaxLevel }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techName }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techPt }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techJop }</td>
                  <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.depName }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.depPt }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.depJop }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.licenceAuthorith }</td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >
                    <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
                  </td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >
                     <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
                     <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
                  </td>
                  <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >
                    <c:if test="${s.certStatus==0 }">无效</c:if>
                    <c:if test="${s.certStatus==1 }">有效</c:if>
                  </td>
                  <td class="tc" >
                    <c:if test="${s.attachCert !=null}">
                        <%-- <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a> --%>
                        <a class="mt3 color7171C6" href="javascript:download('${s.attachCertId}', '${sysKey}')">${s.attachCert}</a>
                      </c:if>
                       <c:if test="${s.attachCert ==null}">
                         <a class="red">无附件下载</a>
                       </c:if>
                  </td>
                  <td class="tc">
                    <a id="${s.id }_show" class="b f18 fl ml10 hand red">×</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </div>

          <h2 class="count_flow"><i>2</i>供应商资质资格信息</h2>
          <ul class="ul_list count_flow">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
		            <tr>
		              <th class="info">资质资格类型</th>
		              <th class="info">证书编号</th>
		              <th class="info">资质资格序列</th>
		              <th class="info">专业类别</th>
		              <th class="info">资质资格等级</th>
		              <th class="info">是否主项资质</th>
		              <th class="info">批准资质资格内容</th>
		              <th class="info">首次批准资质资格文号</th>
		              <th class="info">首次批准资质资格日期</th>
		              <th class="info">资质资格取得方式</th>
		              <th class="info">资质资格状态</th>
		              <th class="info">资质资格状态变更时间</th>
		              <th class="info">资质资格状态变更原因</th>
		              <th class="info">附件</th>
		              <th class="info"></th>
		            </tr>
              </thead>
		          <c:forEach items="${supplierAptitutes}" var="s" >
		            <tr>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.certType }</td>
		              <td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.certCode }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteSequence }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.professType }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteLevel }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
		                <c:if test="${s.isMajorFund==0 }">否</c:if>
		                <c:if test="${s.isMajorFund==1 }">是</c:if>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteContent }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteCode }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
		                <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
		              </td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteWay }</td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
		                <c:if test="${s.aptituteStatus==0 }">无效</c:if>
		                <c:if test="${s.aptituteStatus==1 }">有效</c:if>
		              </td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
		                <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
		              </td>
		              <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteChangeReason }</td>
		              <td class="tc" >
		                <c:if test="${s.attachCert !=null}">
		                  <%-- <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a> --%>
		                  <a class="mt3 color7171C6" href="javascript:download('${s.attachCertId}', '${sysKey}')">${s.attachCert}</a>
		                </c:if>
		                <c:if test="${s.attachCert ==null}">
		                    <a class="red">无附件下载</a>
		                </c:if>
		              </td>
		              <td class="tc">
		                <a id="${s.id }_show1" class="b f18 fl ml10 hand red">×</a>
		              </td>
		            </tr>
		          </c:forEach>
            </table>
          </ul>

          <h2 class="count_flow"><i>3</i>供应商注册人员登记</h2>
          <ul class="ul_list count_flow">
            <table class="table table-bordered table-condensed table-hover">
		          <thead>
	              <tr>
	                <th class="info w50">序号</th>
	                <th class="info">注册名称</th>
	                <th class="info">注册人数</th>
	                <th class="info"></th>
	              </tr>
		          </thead>
              <c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
                <tr onclick="reason('${regPrson.id}','工程-注册人员登记信息');">
                  <td class="tc">${vs.index + 1}</td>
                  <td class="tc">${regPrson.regType}</td>
                  <td class="tc">${regPrson.regNumber}</td>
                  <td class="tc">
                    <a id="${regPrson.id }_show2" class="abolish">×</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </ul>
          <h2 class="count_flow"><i>4</i>供应商组织机构</h2>
          <ul class="ul_list count_flow">
	          
			      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="orgName2">组织机构：</span>
			        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
			          <input id="orgName" type="text" value="${supplierMatEngs.orgName }" onclick="reason1(this.id,'supplierMatEng.orgName')"/>
	              <div id="orgName3"  class="abolish">×</div>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalTech2">技术负责人：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalTech" type="text" value="${supplierMatEngs.totalTech }" onclick="reason1(this.id,'supplierMatEng.totalTech')"/>
	              <div id="totalTech3" class="abolish">×</div>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalGlNormal2">中级及以上职称人员：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalGlNormal" type="text"  value="${supplierMatEngs.totalGlNormal }" onclick="reason1(this.id,'supplierMatEng.totalGlNormal')"/>
	              <div id="totalGlNormal3"  class="abolish">×</div>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalMange2">管理人员：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalMange" type="text"  value="${supplierMatEngs.totalMange }" onclick="reason1(this.id,'supplierMatEng.totalMange')"/>
	              <div id="totalMange3"  class="abolish">×</div>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="totalTechWorker2">技术工人：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalTechWorker" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="reason1(this.id,'supplierMatEng.totalTechWorker')"/>
			          <div id="totalTechWorker3"  class="abolish">×</div>
			        </div>
			      </li>
          </ul>
        </div>
        <div class="col-md-12 add_regist tc">
	        <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
	        <a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>
        </div> 
      </div>
    </div>
    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
      <input type="hidden" name="fileName" />
    </form>
  </body>
</html>
