 /**初始化 **/
      $(function() {
    	  getList();
      });
    /** 获取数据**/
    function getList(){
    	$.ajax({
    		url: globalPath + "/ob_project/listPage.do",
    		type:"post",
    		data:{'page': page,'searchType':searchType,'startTime' : startTime, 'name' :name},
    		dataType:"json",
    		success:function(obj){
    			alert(obj);
    			if(obj){
    				listPage(obj.pages, obj.total, obj.startRow, obj.endRow, obj.pages, obj.pageNum);
    			}
    		}
    	});
    }
	/**分页**/
    function listPage(pages,total,startRow,endRow,pages,pageNum){
    	 laypage({
             cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
             pages: pages, //总页数
             skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
             skip: true, //是否开启跳页
             total: total,
             startRow: startRow,
             endRow: endRow,
             groups: pages >= 3 ? 3 : pages, //连续显示分页数
             curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
               return pageNum;
             }(),
             jump: function(e, first) { //触发分页后的回调
               if(!first) { //一定要加此判断，否则初始时会无限刷新
                 $("#page").val(e.curr);
                 $("#form1").submit();
               }
             }
           });
    }
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
	
	//创建竞价项目
	function create(orgid){
	 var orgid='${orgId}';
	   alert(orgid);
	   if(orgid=='0'){
	 window.location.href = "${pageContext.request.contextPath}/ob_project/add.html";
	 }else{
	 layer.alert("您不是需求部门不能创建", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
	/**编辑 **/
	function release(){
	 var orgid='${orgId}';
	   if(orgid=='0'){
	 var id = [];
	   $('input[name="chkItem"]:checked').each(function() {
	   		id.push($(this).val());
	   });
	   if(id.length==1){
	  var checkID= $('input[name="chkItem"]:checked').val();
	  var status=$("#"+checkID+"status").html();
	  if(checkID){
	    if($.trim(status)=='暂存'){
	     window.location.href ="${pageContext.request.contextPath}/ob_project/editOBProject.html?obProjectId="+checkID;
	     }else{
	       layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		     });
	       }
	     }else{
	  layer.alert("请先选择", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	    }
	  }else{
	  layer.alert("请选择1项", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	 }else{
	  layer.alert("您不是需求部门不能编辑", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
		/**删除 **/
	function del(){
	 var orgid='${orgId}';
	   if(orgid=='0'){
	  var id = [];
	   $('input[name="chkItem"]:checked').each(function() {
	   		id.push($(this).val());
	   });
	 if(id.length==1){
	  var checkID= $('input[name="chkItem"]:checked').val();
	  var status=$("#"+checkID+"status").html();
	  if(checkID){
	    if($.trim(status)=='暂存'){
	         layer.confirm('您是否要删除?', {
                    shade: 0.01,
                    btn: ['是', '否'],
                  }, function() {
	        window.location.href ="${pageContext.request.contextPath}/ob_project/delOBProject.html?obProjectId="+checkID;
			    });
	     }else{
	       layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		     });
	     }
	  }else{
	  layer.alert("请先选择", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	  }
	   }else{
	  layer.alert("请选择1项", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	 }else{
	 layer.alert("您不是需求部门不能删除", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	 }
	}
	
	/**发布竞价项目**/
	function releaseHref(checkID){
	   var orgid='${orgId}';
	   if(orgid=='0'){
	  if(checkID){
	   window.location.href ="${pageContext.request.contextPath}/ob_project/editOBProject.html?status=1&obProjectId="+checkID;
	  }else{
	  layer.alert("请选择暂存的竞价", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	   }
	  }else{
	   layer.alert("您不是需求部门不能创建", {
			offset: ['222px', '390px'],
			shade: 0.01
		});
	  }
	}
	
	/**查询**/
	function query(){
	if(!$("#name").val().trim()){
	   return ;
	 }
	if(!$("#startTime").val().trim()){
	   return
	 }
	}
	/**重置**/
	function resetAll(){
		$("#name").val("");
		$("#startTime").val("");
	}