<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
  <html>
    <head>
        <%@ include file="/WEB-INF/view/common.jsp" %>
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
				    $(this).parent("tr").find("td").eq(13).find("a").hide();
				    $(this).parent("tr").find("td").eq(3).find("a").hide();
		    });
	    
		    $(":input").each(function() {
		      var onMouseMove = "this.style.background='#E8E8E8'";
		      var onmouseout = "this.style.background='#FFFFFF'";
		      $(this).attr("onMouseMove",onMouseMove);
		      $(this).attr("onmouseout",onmouseout);
		      });
	      });
			
				function reason(id,auditContent){
				   var supplierId=$("#supplierId").val();
				   var auditFieldName= auditContent.replace("信息","");
				   var index = layer.prompt({
				   title: '请填写不通过的理由：', 
				   formType: 2, 
				   offset : '100px'
				 }, 
				function(text){
				$.ajax({
				  url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				  type:"post",
				  data: {"auditType":"mat_eng_page","auditFieldName":auditFieldName,"auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":id},
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
				    $("#"+id+"_hidden1").hide();
				    $("#"+id+"_hidden2").hide();
				    $("#"+id+"_show").show();
				    $("#"+id+"_show1").show();
				    $("#"+id+"_show2").show();
				    layer.close(index);
				  });
				}
			
				function reason1(obj){
				  var supplierId=$("#supplierId").val();
				  var auditField = obj.id;;
				  var auditContent;
				  var auditFieldName ;
				  var html = "<a class='abolish'><img src='/zhbj/public/backend/images/sc.png'></a>";
			    $("#"+obj.id+"").each(function() {
			      auditFieldName = $(this).parents("li").find("span").text().replace("：","").trim();
	          auditContent = $(this).parents("li").find("input").val();
	    		});
				  
				  var index = layer.prompt({
				  title: '请填写不通过的理由：', 
				  formType: 2, 
				   offset : '100px',
				  }, 
				  function(text){
				    $.ajax({
				        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
				        type:"post",
				        data: {"auditType":"mat_eng_page","auditFieldName":auditFieldName,"auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":auditField},
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
				     /* $("#"+id3+"").show();
				     $("#"+id3+"").parents("li").find("input").css("padding-right","30px"); */
				     $(obj).after(html);
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
			
				//上一步
				function lastStep(lastUrl){
				  $("#form_id").attr("action",lastUrl);
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
			  
			  // 提示之前的信息
				function isCompare(field) {
					var supplierId=$("#supplierId").val();
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
						data: {"supplierId":supplierId, "beforeField":field, "modifyType":"engineering"},
						async: false,
						success: function(result) {
							layer.tips("修改前:" + result, "#" + field, { 
								tips: 1
							});
						}
					});
				}
			  
      </script>

		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="essential"){
			     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
			  }
			  if(str=="financial"){
			    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
			  }
			  if(str=="shareholder"){
			    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
			  }
			  if(str=="materialProduction"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
			  }
			  if(str=="materialSales"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
			  }
			  if(str=="engineering"){
			    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
			  }
			  if(str=="serviceInformation"){
			    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
			  }
			  if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
			  if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				}
			  if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
			  if(str=="applicationForm"){
			    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
  </head>
  
  <body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
	    <div class="container">
	      <ul class="breadcrumb margin-left-0">
	        <li>
	          <a href="javascript:void(0);"> 首页</a>
	          </li><li>
	          <a href="javascript:void(0);">供应商管理</a>
	          </li><li>
	          <a href="javascript:void(0);">供应商审核</a>
	        </li>
	      </ul>
	    </div>
		</div> 
    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
	        <ul class="nav nav-tabs bgdd">
	          <li onclick = "jump('essential')">
	            <a aria-expanded="false" href="#tab-1">详细信息</a>
	            <i></i>
	          </li>
	          <li onclick = "jump('financial')">
	            <a aria-expanded="true" href="#tab-2">财务信息</a>
	            <i></i>                            
	          </li>
	          <li onclick = "jump('shareholder')" >
	            <a aria-expanded="false" href="#tab-3">股东信息</a>
	            <i></i>
	          </li>
	          <c:if test="${fn:contains(supplierTypeNames, '生产')}">
	            <li onclick = "jump('materialProduction')">
	              <a aria-expanded="false" href="#tab-4">生产信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
	            <li onclick = "jump('materialSales')" >
	              <a aria-expanded="false" href="#tab-4" >销售信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li onclick = "jump('engineering')"  class="active">
	              <a aria-expanded="false" href="#tab-4" data-toggle="tab">工程信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li onclick = "jump('serviceInformation')">
	              <a aria-expanded="false" href="#tab-4" >服务信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <li onclick = "jump('items')">
	            <a aria-expanded="false" href="#tab-4" >品目信息</a>
	            <i></i>
	          </li>
	          <li onclick="jump('aptitude')">
							<a aria-expanded="false" href="#tab-4">资质文件</a>
							<i></i>
						</li>
	         	<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
						</li>
	          <li onclick = "jump('applicationForm')">
	            <a aria-expanded="false" href="#tab-4" >申请表</a>
	            <i></i>
	          </li>
	          <li onclick = "jump('reasonsList')">
	            <a aria-expanded="false" href="#tab-4" >审核汇总</a>
	          </li>
	        </ul>

          <form id="form_id" action="" method="post">
              <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
          </form>
                  
          <h2 class="count_flow"><i>1</i>供应商工程证书信息</h2>
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
		              <!-- <th class="info">附件</th> -->
		             <th class="info w50">操作</th>
		            </tr>
		          </thead>
              <c:forEach items="${supplierCertEng}" var="s" >
                <tr>
                  <td class="tc" >${s.certType }</td>
                  <td class="tc" id="${s.id }" >${s.certCode }</td>
                  <td class="tc" >${s.certMaxLevel }</td>
                  <td class="tc" >${s.techName }</td>
                  <td class="tc" >${s.techPt }</td>
                  <td class="tc" >${s.techJop }</td>
                  <td class="tc" >${s.depName }</td>
                  <td class="tc" >${s.depPt }</td>
                  <td class="tc" >${s.depJop }</td>
                  <td class="tc" >${s.licenceAuthorith }</td>
                  <td class="tc " >
                    <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
                  </td>
                  <td class="tc" >
                     <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
                     <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
                  </td>
                  <td class="tc" >
                    <c:if test="${s.certStatus==0 }">无效</c:if>
                    <c:if test="${s.certStatus==1 }">有效</c:if>
                  </td>
                  <%-- <td class="tc" >
                    <c:if test="${s.attachCert !=null}">
                        <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
                        <a class="mt3 color7171C6" href="javascript:download('${s.attachCertId}', '${sysKey}')">${s.attachCert}</a>
                      </c:if>
                       <c:if test="${s.attachCert ==null}">
                         <a class="red">无附件下载</a>
                       </c:if>
                  </td> --%>
                  <td class="tc w50">
                    <p onclick="reason('${s.id}','工程-资质证书信息');" id="${s.id}_hidden" class="btn">审核</p>
                    <a id="${s.id }_show"><img src='/zhbj/public/backend/images/sc.png'></a>
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
		              <!-- <th class="info">附件</th> -->
		              <th class="info w50">操作</th>
		            </tr>
              </thead>
		          <c:forEach items="${supplierAptitutes}" var="s" >
		            <tr>
		              <td class="tc" >${s.certType }</td>
		              <td class="tc" id="${s.id }" >${s.certCode }</td>
		              <td class="tc" >${s.aptituteSequence }</td>
		              <td class="tc" >${s.professType }</td>
		              <td class="tc" >${s.aptituteLevel }</td>
		              <td class="tc" >
		                <c:if test="${s.isMajorFund==0 }">否</c:if>
		                <c:if test="${s.isMajorFund==1 }">是</c:if>
		              <td class="tc" >${s.aptituteContent }</td>
		              <td class="tc" >${s.aptituteCode }</td>
		              <td class="tc" >
		                <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
		              </td>
		              <td class="tc" >${s.aptituteWay }</td>
		              <td class="tc" >
		                <c:if test="${s.aptituteStatus==0 }">无效</c:if>
		                <c:if test="${s.aptituteStatus==1 }">有效</c:if>
		              </td>
		              <td class="tc">
		                <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
		              </td>
		              <td class="tc">${s.aptituteChangeReason }</td>
		              <%-- <td class="tc" >
		                <c:if test="${s.attachCert !=null}">
		                  <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
		                  <a class="mt3 color7171C6" href="javascript:download('${s.attachCertId}', '${sysKey}')">${s.attachCert}</a>
		                </c:if>
		                <c:if test="${s.attachCert ==null}">
		                    <a class="red">无附件下载</a>
		                </c:if>
		              </td> --%>
		              <td class="tc w50">
		                <p onclick="reason('${s.id}','工程-资质资格证书信息');" id="${s.id}_hidden1" class="btn">审核</p>
		                <a id="${s.id }_show1"><img src='/zhbj/public/backend/images/sc.png'></a>
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
	                <th class="info w50">操作</th>
	              </tr>
		          </thead>
              <c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
                <tr>
                  <td class="tc">${vs.index + 1}</td>
                  <td class="tc">${regPrson.regType}</td>
                  <td class="tc">${regPrson.regNumber}</td>
                  <td class="tc w50">
                    <p onclick="reason('${regPrson.id}','工程-注册人员登记信息');" id="${regPrson.id}_hidden2" class="btn">审核</p>
                    <a id="${regPrson.id }_show2"><img src='/zhbj/public/backend/images/sc.png'></a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </ul>
          <h2 class="count_flow"><i>4</i>法人代表信息</h2>
          <ul class="ul_list count_flow">
	          
			      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
			        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
			          <input id="orgName" type="text" value="${supplierMatEngs.orgName }" onclick="reason1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName');"</c:if>/>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术负责人：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalTech" type="text" value="${supplierMatEngs.totalTech }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech');"</c:if>/>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">中级及以上职称人员：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalGlNormal" type="text"  value="${supplierMatEngs.totalGlNormal }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalGlNormal')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalGlNormal');"</c:if>/>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalMange" type="text"  value="${supplierMatEngs.totalMange }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange');"</c:if>/>
	            </div>
	          </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术工人：</span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input id="totalTechWorker" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalTechWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTechWorker');"</c:if>/>
			        </div>
			      </li>
          </ul>
        </div>
        <div class="col-md-12 add_regist tc">
	        <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
	        <a class="btn"  type="button" onclick="lastStep('${lastUrl}');">上一步</a>
	        <a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>
        </div> 
      </div>
    </div>
    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
      <input type="hidden" name="fileName" />
    </form>
  </body>
</html>
