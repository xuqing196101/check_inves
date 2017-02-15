<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
     <!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script> -->
    <%--  <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script> --%>

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
	
  	function view(no){
  		
  		
  		window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if(id.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/park/delete.html?id="+id;
			});
		}else{
			layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    var index;
    function add(){
    	
    	index=layer.open({
			  type: 1, //page层
			  area: ['300px', '200px'],
			  title: '',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '600px'],
			  content: $('#content'),
			});
    	
   
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length>10){
	layer.msg(content, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
	
	function closeLayer(){
		var val=$("input[name='goods']:checked").val();
		
		window.location.href="${pageContext.request.contextPath}/purchaser/add.html?type=" + val;
		layer.close(index);
	}
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box">
				<h2 class="count_flow"><i>1</i>计划主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="true" name="name" id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="${list[0].planNo}" disabled="true" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划文号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  disabled="true"  value="${list[0].referenceNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类别</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="planType" id="wtype" onchange="gtype(this)" disabled="true">
								<c:forEach items="${types }" var="tp" >
									<option value="${tp.id }">${tp.name }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					
				  
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="true" id="mobile" value="${list[0].recorderMobile }"> 
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" style="display:none" id="dnone" >
			            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			                <input type="checkbox" name="" onchange="" value="进口" />进口
			            </div>
			         </li>
          
             <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划附件</span>
                       <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                        <u:show showId="detailshow"  businessId="${fileId}" sysKey="2" typeId="${typeId}" />
             </li>
          
          
           <%--  <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划附件</span>
                   <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                    <u:show showId="detailshow"  businessId="${fileId}" sysKey="2" typeId="${typeId}" />
           </li> --%>
	   </ul>
		
        <h2 class="count_flow"><i>2</i>计划明细</h2>
		<div class="content require_ul_list"  id="content">
				<table id="table" class="table table-bordered table-condensed lockout">
					<thead>
						<tr class="space_nowrap">
							<th class="info seq">序号</th>
							<th class="info department">需求部门</th>
							<th class="info goodsname">物资类别<br>及名称</th>
							<th class="info stand">规格型号</th>
							<th class="info qualitstand">质量技术标准</th>
							<th class="info item">计量</br>单位</th>
							<th class="info purchasecount">采购</br>数量</th>
							<th class="info price">单价</br>（元）</th>
							<th class="info budget">预算金额<br>（万元）</th>
							<th class="info deliverdate">交货期限</th>
							<th class="info purchasetype">采购方式</th>
							<c:if test="${org_advice!=null }">
							 <th class="info organization">采购机构</br>建议</th>
							 </c:if>
							<th class="info purchasename">供应商名称</th>
							<th class="info freetax">是否申请<br>办理免税</th>
							<!-- <th class="goodsuse ">物资用途（仅进口）</th>
							<th class="useunit">使用单位（仅进口）</th> -->
							<th class="info memo">备注</th>
							<th class="info extrafile">附件</th>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td>
							   <div class="seq">${obj.seq }</div> 
							</td>
							<td><div class="department">${obj.department}</div></td >
							<%-- <td class="tl pl20">
							     <c:forEach items="${requires }" var="re" >
									  <c:if test="${obj.department==re.id }"> ${re.name }</c:if>
							  	</c:forEach> 
			  	
							</td> --%>
							<%-- <td>${obj.goodsName }</td> --%>
							<td title="${obj.goodsName}" class="tl">
							 <div class="goodsname">
							   <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
							 </div>
							</td >
							<%-- <td class="tc"> ${obj.stand }</td> --%>
							<td title="${obj.stand}" class="tl">
							 <div class="stand">
							   <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
							 </div>
							</td >
							<%-- <td class="tc"> ${obj.qualitStand }</td> --%>
							<td title="${obj.qualitStand}" class="tl">
						  	 <div class="qualitstand">
							   <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
							 </div>
							</td >
							<%-- <td class="tc"> ${obj.item }</td> --%>
							<td title="${obj.item}">
							 <div class="item">
							  <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
							  <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
							 </div>
							</td >
							<td><div class="purchasecount"> <fmt:formatNumber>${obj.purchaseCount }</fmt:formatNumber></div></td>
							<td><div class="price"> <fmt:formatNumber  type="number"      pattern="#,###.00#" value="${obj.price }"/></div></td>
							<td><div class="budget"> <fmt:formatNumber type="number"      pattern="#,###.00#" value="${obj.budget}"  /></div></td>
							<td><div class="deliverdate">${obj.deliverDate }</div></td>
							<td> 
							  <div class="purchasetype">
							  <c:if test="${obj.purchaseCount!=null }">
							   <c:forEach items="${kind}" var="kind" >
                               <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                               </c:forEach>
                               </c:if>
                              </div>
                            </td>
                            <c:if test="${org_advice!=null }">
						 	 <td>
						 	  <div class="organization">
						 	    <c:if test="${obj.purchaseCount!=null }">
							      <c:forEach items="${requires}" var="ss" >
				                  <c:if test="${ss.orgId==obj.organization}">${ss.name}</c:if>
				                 </c:forEach>
				               </c:if>
				              </div>
							</td >
							</c:if>
							<td title="${obj.supplier}">
							 <div class="purchasename">
							   <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
							 </div>
							</td >
							<td title="${obj.isFreeTax}">
							 <div class="freetax">
							   <c:if test="${fn:length (obj.isFreeTax) > 8}">${fn:substring(obj.isFreeTax,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.isFreeTax) <= 8}">${obj.isFreeTax}</c:if>
							 </div>
							</td >
							<%-- <td class="tc">${obj.goodsUse }</td> --%>
						<%-- 	<td title="${obj.goodsUse}" class="tl pl20">
							<c:if test="${fn:length (obj.goodsUse) > 8}">${fn:substring(obj.goodsUse,0,7)}...</c:if>
							<c:if test="${fn:length(obj.goodsUse) <= 8}">${obj.goodsUse}</c:if>
							</td > 
							<td class="tl pl20">${obj.useUnit }</td> --%>
							 <td title="${obj.memo}">
							  <div class="memo">
							    <c:if test="${fn:length (obj.memo) > 8}">${fn:substring(obj.memo,0,7)}...</c:if>
							    <c:if test="${fn:length(obj.memo) <= 8}">${obj.memo}</c:if>
							  </div>
							</td > 
							
							<td>
						<%-- 	<div class="extrafile">
													<u:upload id="pUp${vs.index}" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
													<u:show showId="pShow${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
											   </div> --%>	
							</td>
							
							
						   <%-- <td class="tc"><div class="memo">${obj.memo }</div></td> --%> 
						</tr>

					</c:forEach>
				</table>
				  </div>
			<!--   </div> -->
			  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
			   <input class="btn btn-windows back" value="返回" type="button"
                    onclick="location.href='javascript:history.go(-1);'">
              </div>
      
      </div>
   
</body>
</html>
