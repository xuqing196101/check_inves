<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
       
  //导入模板
    function inputTemplete(){
        var iframeWin;
        layer.open({
          type: 2, //page层
          area: ['800px', '500px'],
          title: '导入模板',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['180px', '550px'],
          shadeClose: false,
          content: '${pageContext.request.contextPath }/resultAnnouncement/getAll.html',
          success: function(layero, index){
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          }
        });
    }
        //导出
        function outputAnnouncement(){
            $("#form").attr("action",'${pageContext.request.contextPath }/bidAnnouncement/outputBidAnnouncement.do');   
            $("#form").submit();
        }
        //预览
        function preview(){
             $("#form").attr("action",'${pageContext.request.contextPath }/bidAnnouncement/preViewBidAnnouncement.do');   
             $("#form").submit();
        }
        //发布
        function publish(){
        	var content = UE.getEditor('editor').getContent();
            //alert(content);
            //${pageContext.request.contextPath}.getSession.setAttribute("BidAnnouncement", content);
        	var iframeWin;
            layer.open({
              type: 2, //page层
              area: ['800px', '500px'],
              title: '发布招标公告',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              shift: 1, //0-6的动画形式，-1不开启
              offset: ['180px', '550px'],
              shadeClose: false,
              content: '${pageContext.request.contextPath }/bidAnnouncement/publish.do?content='+content,
              success: function(layero, index){
            
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
              }
            });
        }
        //保存
        function save(){                  
            layer.prompt({title: '请输入招标公告名称', formType: 2}, function(text){
            	$("#form").attr("action",'${pageContext.request.contextPath }/bidAnnouncement/saveBidAnnouncement.do?name='+text);   
                $("#form").submit();               
             });
        }
        
    </script>
</head>

<body>
  
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
           <li><a >首页</a></li><li><a >采购项目管理</a></li><li><a>拟制招标公告</a></li> 
        </ul>
      </div>
   </div>

    <div class="container content">
        <div class="row">
             <div class="col-md-3 col-sm-4 col-xs-12 " id="show_tree_div">
                  <div class="tag-box tag-box-v3">
                    <ul id="ztree_show" class="ztree">
                      <li id="ztree_show_1" class="level0" tabindex="0" hidefocus="true" treenode="">
                      </li>
                    </ul>
                 </div>     
             </div>
             <div class="tag-box tag-box-v4 col-md-9 col-sm-8 col-xs-12 " id="show_content_div">
			 <form  method="post" id="form"> 
				<div class="row">				        
				        <!-- 按钮 -->
				     <div class="col-md-12 col-sm-12 col-xs-12">		
				           		          
				          <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
				          <input type="button" class="btn btn-windows output" onclick="outputAnnouncement()" value="导出"></input>
				          <input type="button" class="btn btn-windows git" onclick="preview()" value="预览"></input>  
				          <input type="button" class="btn btn-windows apply" onclick="publish()" value="发布"></input>  
				      </div>
				        <!-- 文本编辑器 -->
				        <div class="col-md-12 col-sm-12 col-xs-12">
		                     <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
		                </div>
		                
                        <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">     
                                                                                                                                       
                          <input type="button" class="btn btn-windows save" onclick="save()" value="保存"></input>                                              
                          <input type="button" class="btn btn-windows back" onclick="history.go(-1)" value = "返回"></input>
                        </div>
				      </div>
				      </form>
			</div>
        </div>
    </div>
    <script type="text/javascript">
    var ue = UE.getEditor('editor'); 
    ue.ready(function(){
        //需要ready后执行，否则可能报错
        ue.setContent("<h1>欢迎使用UEditor！</h1>");
        ue.setHeight(500);
    })
    </script>
</body>
</html>


