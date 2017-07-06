<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a>
                </li>
                <li><a href="javascript:void(0)">支撑系统</a>
                </li>
                <li><a href="javascript:void(0)">后台管理</a>
                </li>
                </li>
                <li><a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/templet/getAll.html')">模板管理</a>
                </li>
                <li class="active"><a href="javascript:void(0)">新增模板</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>

    <!-- 新增模板开始-->
    <div class="container container_box">
        <form id="editContentForm" action="${pageContext.request.contextPath}/templet/save.do" method="post">
            <div>
			   <h2 class="list_title">新增模板</h2>
                <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板名称</span>
                        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input id="name" name="name" type="text"  value="${templet.name}">
                            <span class="add-on">i</span>
                            <div id="contractCodeErr" class="cue">${ERR_name}</div>
                        </div>
                    </li>
                     <li class="col-md-3 col-sm-6 col-xs-12">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板类型</span>
                       <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
                            <select id="temType" name="temType" >
                                <option value="">-请选择-</option>
								<option value="0">采购公告-公开招标</option>
								<option value="1">采购公告-邀请招标</option>
								<option value="2">采购公告-询价采购</option>
								<option value="3">采购公告-竞争性谈判</option>
								<option value="4">单一来源公示</option>
								<option value="5">中标公示-公开招标</option>
								<option value="6">中标公示-邀请招标</option>
								<option value="7">中标公示-询价采购</option>
								<option value="8">中标公示-竞争性谈判</option>
                            </select>
                            <div id="contractCodeErr" class="cue">${ERR_temType}</div>
                       </div>
                    </li>

                     <li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>模板内容</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                            <script id="editor" name="content" type="text/plain"></script>
                            <!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
                        <div id="contractCodeErr" class="red clear">${ERR_content}</div>
                        </div>
                    </li>

                    <%-- <li class="col-md-6 p0">
               <span class="">角色：</span>
               <div class="input-append">
                 <input class="span2" id="roleId" name="roleId" type="hidden">
                 <input class="span2" id="roleName" name="roleName" type="text">
                 <div class="btn-group ">
                  <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
                  <img src="${pageContext.request.contextPath}/public/ZHH/images/down.png" class="margin-bottom-5"/>
                  </button>
                  <ul class="dropdown-menu list-unstyled">
                    <c:forEach items="${roles}" var="role" varStatus="vs">
                        <li class="select_opt">
                            <input type="checkbox" name="chkItem" value="${role.id };${role.name }" onclick="cheClick();" class="select_input">${role.name }
                        </li>
                    </c:forEach>
                  </ul>
               </div>
              </div>
             </li> --%>

                </ul>

                <div class="col-md-12 col-sm-12 col-xs-12 tc">
                    <input type="button" id="btnSave" value="保 存" class="btn btn-windows save" onclick="formSubmit();"/>
                    <input type="button" id="btnBack" value="返 回" class="btn btn-windows back"  onclick="goback();" />
                </div>
            </div>
        </form>
    </div>

    <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content='${templet.content}';
    ue.ready(function(){
        ue.setContent(content);
    });

    /** 全选全不选 */
    $(function(){
        if(${templet.temType!=null}&&${templet.temType!=""} && ${templet.temType!="-请选择-"}){
            $("#temType").val("${templet.temType}");
        }else{
            $("#temType").val('-请选择-');
        }
    });

    /*校验内容是否为空*/
    function checkForm(){
        if($("#name").val()=="") {
            layer.msg("请输入模板名称.");
            return false;
        }
        /*if(!UE.getEditor('editor').hasContent()){
            layer.msg("请输入模板内容.");
            return false;
        }*/
        if($("#temType").val()==null || $("#temType").val()=="") {
            layer.msg("请输入模板类型.");
            return false;
        }
        return true;
    }

    function formSubmit(){
        if(checkForm()) {
            $("#editContentForm").submit();
        }
    }

    function goback(){
        window.location.href="${pageContext.request.contextPath}/templet/getAll.html";
    }
</script>
</body>
</html>
