/**
 * dom加载...
 */
$(function(){
	showInit();
});

/**
 * 初始化
 */
function showInit(showId){
	if(showId){
		packParam(showId);
	}else{
		$(".uploaded_file_show").each(function(){
			var id = $(this).prev()[0].value;
			packParam(id);
		});
	}
	
	/*
	var singleId = $("#showId").val();
	var groups = $("#show_groupId").val();
	if (!webIsNull(groups)){
		var arr = groups.split(",");
		$.each(arr,function(){
			packParam(this);
		});
	} else {
		packParam(singleId);
	}
	*/
}

/**
 * 文件上传成功后的回调展示上传的图片文件等
 */
function showInitAfterUpload(singleId){
	packParam(singleId);
}


/**
 * 包装参数
 */
function packParam(id){
	var businessId = $('#' + id +'_downBsId').val();
	var typeId = $("#" + id + "_downBstypeId").val();
	var key = $("#" + id + "_downBsKeyId").val();
	var zipFileName = $("#" + id + "_zipFileName").val();
	var fileName = $("#" + id + "_fileName").val();
	var del = $("#"+id+"_showdel").val();
	var param_del = transBoolean(del);
	var params = {businessId: businessId,typeId: typeId,key: key,zipFileName: zipFileName, fileName: fileName};
	fileExist(params,id,param_del);
}

//判断是否为空
webIsNull = function(value){
	 if (value == null || value == "undefined" || value == "" || value == "null") {
		 return true;
	 }
	 return false;
}

//boolean类型转换
transBoolean = function(str){
		if (str === "true"){
			return true;
		}
		if (str === "false"){
			return false;
		}
	}

/***
 * 判断文件是否存在
 * @param params
 * @param id
 * @param del
 * @returns
 */
function fileExist(params,id,del){
	$.ajax({
		url: globalPath + '/file/fileExist.do',
		data: params,
		type:'post',
		async: true,
		dataType: 'json',
		success:function(data){
			displayName(params,data,id,del);
		}
	});
}

	
/**
 * 显示下载,查看
 */
function displayName(params,data,id,del){
	var key = params.key;
	var zipFileName = params.zipFileName;
	var fileName = params.fileName;
	var $ul = $("#"+id+"_disFileId");
	var picture = new Object();
	picture.bid = params.businessId;
	picture.tid = params.typeId;
	picture.key = key;
	picture.id = id;
	picture.zipFileName = zipFileName;
	picture.fileName = fileName;
	picture.ids = data.fileIds;
	window.sessionStorage.setItem(id,JSON.stringify(picture));
	$ul.empty();
	if (data != null){
		if (data.picture){
			//var li = '<li class="file_view"><a href=\'javascript:openViewDIv("'+params.businessId+'","'+params.typeId+'","'+key+'","'+id+'","this");\'></a></li>';
			//var li = '<li class="file_view"><a href=\'javascript:showPhotoToNewWindow("'+params.businessId+'","'+params.typeId+'","'+key+'","'+id+'","this");\'></a></li>';
			var li = '<li class="file_view"><a href=\'javascript:showPhotoToNewWindow("'+id+'");\'></a></li>';
			//var li = '<li class="file_view"><a target=\'_blank\' href=\'javascript:openViewDIv("'+params.businessId+'","'+params.typeId+'","'+key+'","'+id+'","this");\'></a></li>';
			//var li = '<li class="file_view"><a target="__blank" href=\'../openPic.jsp?bid='+params.businessId+'&tid='+params.typeId+'&key='+key+'&id='+id+'\'\></a></li>';
			//var li = '<li class="file_view"><a target="__blank" href=\'../openPic.jsp?id='+id+'\'\></a></li>';
			$ul.append(li);
		}
		if (data.success){
			//var li = '<li class="file_load"><a href=\javascript:download("'+data.fileIds+'",'+key+',"'+zipFileName+'","'+fileName+'");></a></li>';
			var li = '<li class="file_load"><a href=\javascript:downloads("'+id+'");></a></li>';
			$ul.append(li);
		}
		if (del && data.success){
			//var li = '<li class="file_delete"><a href=\javascript:removeFile("'+data.fileIds+'",'+key+',"'+id+'");></a></li>';
			var li = '<li class="file_delete"><a href=\javascript:removeFile("'+id+'");></a></li>';
			$ul.append(li);
		}
		
	}
}
	

/***
 * 在新窗口显示图片存储图片信息
 */
/*function showPhotoToNewWindow(bid,tid,key,id){
	window.sessionStorage.setItem("bid",bid);
	window.sessionStorage.setItem("tid",tid);
	window.sessionStorage.setItem("key",key);
	window.sessionStorage.setItem("id",id);
	window.open(globalPath+"/openPic.jsp");
}*/
/***
 * 在新窗口显示图片存储图片信息
 */
function showPhotoToNewWindow(id){
	window.sessionStorage.setItem("id",id);
	window.open(globalPath+"/openPic.jsp");
}


/**
 * 删除文件
 * @param id  附件Id
 * @param key 系统对应的key
 */
/*function removeFile(ids,key,id){
	var $ul = $("#"+id+"_disFileId");
	$.ajax({
		url: globalPath + '/file/deleteFile.html',
		data: {id: ids, key: key},
		async:true,
		type:"post",
		success:function(msg){
			if (msg){
				$("#" + id).text("删除成功.");
				$ul.empty();
			}
		}
	});
}*/
/**
 * 删除文件(修改)
 * @param id  附件Id
 * @param key 系统对应的key
 */
function removeFile(id){
	var $ul = $("#"+id+"_disFileId");
	var picture = JSON.parse(sessionStorage.getItem(id));
	var ids = picture.ids;
	var key = picture.key;
	$.ajax({
		url: globalPath + '/file/deleteFile.html',
		data: {id: ids, key: key},
		async:true,
		type:"post",
		success:function(msg){
			if (msg){
				$("#" + id).text("删除成功.");
				$ul.empty();
			}
		}
	});
}


/**
 * 显示图片
 * @param obj
 * @returns
 */
var view;
function openViewDIv(businessId,typeId,key,id,obj){
	
	//window.open('../openPic.jsp','newwindow');
	//window.location.replace('openPic.jsp');
	var html ="<iframe frameborder= '0' scrolling='no' style='background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;'></iframe><ul id='"+id+"showPicId'></ul>";
	var height = document.documentElement.clientHeight;
	var index = layer.open({
		  type: 1,
		  title: '图片查看',
		  skin: 'layui-layer-pic',
		  shadeClose: true,
		  area: [$(document).width() +'px',height + "px"],
		  offset:['0px','0px'],
		  content: html,
		  end:function(){
			  window.close();
		  }
		});
	display(businessId,typeId,key,id);
	
	view =  $("#"+id+"showPicId").viewer({
		  url:'data-original'
	  }); 
}


/**
 * 显示附件
 * @param params
 */
//TODO
function display(businessId,typeId,key,id){
	var params = {businessId: businessId,typeId: typeId,key: key};
	$.ajax({
		url: globalPath + '/file/displayFile.do',
		data: params,
		async: false,
		dataType: 'json',
		success:function(datas){
			disFiles(datas,key,id);
		}
	});
}

/**
 * 显示每一个li
 * @param data @link Array
 * @param key 系统对应的key
 */
function disFiles(data,key,id){
	var $ul = $("#"+id+"showPicId");
	$ul.empty();
	if (data != null && data.length > 0) {
		for (var i =0 ;i < data.length; i++){
			disFile($ul,data[i],key);
		}
	}
}

/**
 * 生产li标签
 * @param html 父级html
 * @param obj  传入的对象
 * @param key  对应的系统key
 */
function disFile(html,obj,key){
	var fileName = obj.path;
	var fileExt = fileName.substring(fileName.indexOf(".")+1,fileName.length).toLowerCase();
	//var fileExt=obj.path;
	if (/(gif|jpg|jpeg|png|bmp|GIF|JPG|JPEG|PNG|BMP)$/.test(fileExt)) {
		var url = globalPath + '/file/viewFile.html?id=' + obj.id +'&key=' + key;
		var li = '<li><div class="col-md-2 padding-0 fl"><div class="fl suolue"><a href="javascript:upPicture();" class="thumbnail mb0 suolue">'
			+'<img data-original="'+url+'"  src="'+url+'" height="120px"/></a></div></div></li>';
		html.append(li);
	}
}

/**
 * 显示图片
 * @returns
 */
function upPicture(){
	view.show();
}

/**
 * 包装为预览html
 * @param url 请求的url
 */
function packingHtml(url,obj){
	var html = "<div id='uploadView'> "
		   + " <div class='filelist'> "
		   + " <div id='imgDivId'> "
		   + " <div id='imgId' class='imgWrap'><img  src='"+ url +"' /></div>"
		   + "	<div id='btnsid' class='file-panel'> "
           + "       <span class='uploadCancel'>删除</span> "
           + "        <span class='rotateRight'>向右旋转</span> "
           + "         <span class='rotateLeft'>向左旋转</span>"
           + "   </div> "
		   + " </div> "
		   + " </div></div>";
	
	layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 0,
		  shadeClose: true,
		  area: ['600px','530px'],
		  offset: [$(obj).height()],
		  content: html
		});
	preview();
}

/**
 * 旋转
 */
function preview(){
	
	$btns = $("#btnsid");
	$wrap = $("#imgId");
	$("#imgDivId").on('mouseenter',function(){
		$btns.stop().animate({height: 30});
	});
	$("#imgDivId").on('mouseleave',function(){
		$btns.stop().animate({height: 0});
	});
	
	var rotation =0;
	supportTransition = (function(){
	 var s = document.createElement('p').style,
	     r = 'transition' in s ||
	             'WebkitTransition' in s ||
	             'MozTransition' in s ||
	             'msTransition' in s ||
	             'OTransition' in s;
	 s = null;
	 return r;
	})(),
	
	$btns.on( 'click', 'span', function() {
	 var index = $(this).index(),
	     deg;
	
	 switch ( index ) {
	     case 0:
	         layer.closeAll();
	         return;
	     case 1:
	         rotation += 90;
	         break;
	
	     case 2:
	         rotation -= 90;
	         break;
	 }
	
	 if ( supportTransition ) {
	     deg = 'rotate(' + rotation + 'deg)';
	     $wrap.css({
	         '-webkit-transform': deg,
	         '-mos-transform': deg,
	         '-o-transform': deg,
	         'transform': deg
	     });
	 } else {
	     $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((rotation/90)%4 + 4)%4) +')');
	 }
	
	});
}


/**
 * 附件下载
 * @param id 主键
 * @param key 对应系统的key
 */
function download(id,key,zipFileName,fileName){
	var form = $("<form>");   
	    form.attr('style', 'display:none');   
	    form.attr('method', 'post');
	    form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key + '&zipFileName=' + encodeURI(encodeURI(zipFileName)) + '&fileName=' + encodeURI(encodeURI(fileName)));
	    $('body').append(form); 
	    form.submit();
}
/**
 * 附件下载
 * @param id  组件主键
 * @param key 对应系统的key
 */
function downloads(id){
	var picture = JSON.parse(sessionStorage.getItem(id));
	var zipFileName = encodeURI(encodeURI(picture.zipFileName));
	var fileName = encodeURI(encodeURI(picture.fileName));
	var ids = picture.ids;
	var key = picture.key;
	
	var form = $("<form>");   
	form.attr('style', 'display:none');   
	form.attr('method', 'post');
	form.attr('action', globalPath + '/file/download.html');
	form.append("<input type='hiden' name='id' value='"+ids+"'/>");
	form.append("<input type='hiden' name='key' value='"+key+"'/>");
	form.append("<input type='hiden' name='zipFileName' value='"+zipFileName+"'/>");
	form.append("<input type='hiden' name='fileName' value='"+fileName+"'/>");
	$('body').append(form); 
	form.submit();
}


