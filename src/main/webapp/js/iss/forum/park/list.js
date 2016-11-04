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

/**
 * 查看
 * @param id
 */
function view(id){
	titleMouseOut();
	loadHtml(globalPath + "/park/view.html?id="+id);
}
/**
 * 编辑
 */ 	
function edit(){
	titleMouseOut();
	var id=[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==1){
		
		loadHtml(globalPath + "/park/edit.html?id="+id);
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
	}
}
/**
 * 删除
 */   
function del(){
	var ids =[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		ids.push($(this).val()); 
	}); 
	if(ids.length>0){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
			layer.close(index);
			loadHtml(globalPath + "/park/delete.html?ids="+ids);
		});
	}else{
		layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
	}
}
/**
 * 添加
 */	    
function add(){
	titleMouseOut();
	loadHtml(globalPath + '/park/add.html');
}