<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>经济技术审查项</title>
    <jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
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
    //新增一个评审项
    function addItem(obj,kindId){
    	//得到点击坐标。
        var y;  
        oRect = obj.getBoundingClientRect();  
        y=oRect.top-150;  
    	$("#faKind").val(kindId);
    	layer.open({
            type: 1,
            title: '添加评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: y,
            shadeClose: false,
            content: $("#openDiv"),
          });
    }
    
    //修改评审项
    function editItem(obj,id){
    	//得到点击坐标。
        var y;  
        oRect = obj.getBoundingClientRect();  
        y=oRect.top-150; 
        layer.open({
            type: 2,
            title: '修改评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: y,
            shadeClose: false,
            content: '${pageContext.request.contextPath}/firstAudit/editItem.html?id='+id+'&isConfirm=1'
          });
    }
    
    //删除评审项 
    function delItem(id){
    	layer.confirm('您确定要删除吗?', {title:'提示',offset: '50px',shade:0.01}, function(index){
	    	$.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/firstAudit/delItem.html?id="+id,        
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                	 var packageId = $("#packageId").val();
	                	 var projectId = $("#projectId").val();
	                     window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
	                    layer.msg(result.msg,{offset: ['150px']});
	                    layer.close(index);
	                }
	            },
	            error: function(result){
	                layer.msg("删除失败",{offset: ['150px']});
	            }
	       });       	
        });
    }
    
    //关闭弹窗
    function cancel(){
        layer.closeAll();
    }
    
    //返回模板列表
    function goBack(){
    	
    }
    
    //保存评审项
    function saveItem(){
    	$.ajax({   
            type: "POST",  
            url: "${pageContext.request.contextPath}/firstAudit/savePackageFirstAudit.html",        
            data : $('#form2').serializeArray(),
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                    var packageId = $("#packageId").val();
                    var projectId = $("#projectId").val();
                    window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
                    layer.closeAll();
                    layer.msg(result.msg,{offset: ['150px']});
                }
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
            }
       });    
    }
    
    //引入模板内容
    function loadTemplat(projectId, packageId){
    	var fatId = $("#fatId").val();
    	if (fatId != null && fatId != '') {
	    	$.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/firstAudit/loadTemplat.html?isConfirm=1",   
	            data:{"id":fatId,"projectId":projectId,"packageId":packageId},
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                    var packageId = $("#packageId").val();
	                    var projectId = $("#projectId").val();
	                    window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
	                    layer.closeAll();
	                    layer.msg(result.msg,{offset: ['150px']});
	                }
	            },
	            error: function(result){
	                layer.msg("添加失败",{offset: ['150px']});
	            }
	       }); 
	    }else {
			layer.msg("请选择模板",{offset: ['150px']});
		}
    }
    
  </script>
<body>  
    <h2 class="list_title">${packages.name}经济技术评审项编辑</h2>
    <c:if test="${flag != '1' }">
	    <div class="search_detail ml0">
	        <ul class="demand_list">
	          <li>
	            <label class="fl">选择模板：</label>
	              <select id="fatId">
	                <option value="">请选择</option>
	                <c:forEach items="${firstAuditTemplats}" var="fat">
	                    <option value="${fat.id}">${fat.name}</option>
	                </c:forEach>
	              </select>
	           </li>
	           <button type="button" onclick="loadTemplat('${projectId}','${packageId}')" class="btn">确定</button>
	           <%-- <div class="pull-right">
	              <button type="button" onclick="loadOtherPackage('${packageId}','${projectId}')" class="btn">引入模板</button>
	           </div> --%>
	        </ul>
	        <div class="clear"></div>
	     </div>
    </c:if>
    <div class="content">
        <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                  <th class="info" colspan="2">评审名称</th>
                  <th class="info">评审内容</th>
               </tr>
            </thead>
            <c:forEach items="${dds}" var="d" varStatus="vs">
               <!-- 如果没有评审项 ，显示空td-->
               <c:if test="${d.code == 'ECONOMY' && items1.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <input type="hidden" value="2">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <c:if test="${d.code == 'TECHNOLOGY' && items2.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <input type="hidden" value="2">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <!-- 如果有评审项 ，加载符合性评审项-->
               <c:if test="${d.code == 'ECONOMY' && items1.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items1.size() + 1}" class="w150">
                        <input type="hidden" value="${items1.size() + 1}">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <c:forEach items="${items1}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                           <c:if test="${flag != '1' }">
                              <div class="fr">
	                           <a href="javascript:void(0);" title="编辑" onclick="editItem(this,'${i.id}');" class="item_size editItem"></a>
	                           <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem" ></a>
                              </div>
                           </c:if>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                         ${i.content}
                      </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
                <!-- 如果有评审项 ，加载资格性评审项-->
                <c:if test="${d.code == 'TECHNOLOGY' && items2.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items2.size() + 1}" class="w150">
                        <input type="hidden" value="${items2.size() + 1}">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <c:forEach items="${items2}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                             <c:if test="${flag != '1' }">
                             <div class="fr">
                              <a href="javascript:void(0);" title="编辑" onclick="editItem(this,'${i.id}');" class="item_size editItem"></a>
                              <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem" ></a>
                              </div>
                             </c:if>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                            ${i.content}
                         </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
             </c:forEach>
        </table>
    </div>
    <c:if test="${flag != '1' }">
	    <div class="mt40 tc mb50">
	    	<button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}'">返回</button>
	    </div>
    </c:if>
    <div id="openDiv" class="dnone layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="projectId" id="projectId" value="${projectId}">
              <input type="hidden" name="packageId" id="packageId" value="${packageId}">
              <input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}">
              <input type="hidden" name="kind" id="faKind" > 
              <input type="hidden" name="isConfirm" value="1">
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审名称</label>
	                <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input name="name" id="itemName" maxlength="30" type="text">
	                </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input  name="position" id="itemPosition" maxlength="10" type="text">
	                </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5"><div class="star_red">*</div>评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="w100p h80" id="itemContent" name="content" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                 </li>
              </ul>
              <div class="mt40 tc mb50">
                <input class="btn btn-windows save"  onclick="saveItem();" value="保存" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>