/****
 * 文件上传插件
 * 
 */
(function ($) { 
	
	var  GUID = WebUploader.Base.guid();
	$(function(){
		webuploadEnv_check();
		var groups = $("#groupId").val();
		if (!webIsNull(groups)){
			if (groups.indexOf(",")!=-1){
				var groupArray = groups.split(",");
				groupsArray(groupArray);
			} 
		} else {
			var id = $("#id").val();
			init_uploader(eval("var  uploader_" + id),id);
		}
		
		
	 });
	 
	 //判断是否为空
	 webIsNull = function(value){
		 if (value == null || value == "undefined" || value == "" || value == "null") {
			 return true;
		 }
		 return false;
	 }
	
	  //多个上传按钮
	  groupsArray = function(bntArry){
		  $.each(bntArry,function(){
				init_uploader(eval("var  uploader_" + this),this);
			});
	  }
	
	  //webupload环境检查
	  webuploadEnv_check = function(){
		  var isSupportBase64 = (function () {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function () {
                    if (this.width != 1 || this.height != 1) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            })(),

        // 检测是否已经安装flash，检测flash的版本
            flashVersion = (function () {
                var version;

                try {
                    version = navigator.plugins['Shockwave Flash'];
                    version = version.description;
                } catch (ex) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                    } catch (ex2) {
                        version = '0.0';
                    }
                }
                version = version.match(/\d+/g);
                return parseFloat(version[0] + '.' + version[1], 10);
            })(),

            supportTransition = (function () {
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })()
            if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {
                // flash 安装了但是版本过低。
                if (flashVersion) {
                    (function (container) {
                       window['expressinstallcallback'] = function (state) {
                            switch (state) {
                                case 'Download.Cancelled':
                                    alert('您取消了更新！')
                                    break;
                                case 'Download.Failed':
                                    alert('安装失败')
                                    break;
                                default:
                                    alert('安装已成功，请刷新！');
                                    break;
                            }
                            delete window['expressinstallcallback'];
                        };
                        var swf = 'Scripts/expressInstall.swf';
                        var html = '<object type="application/' +
                                   'x-shockwave-flash" data="' + swf + '" ';
                        if (WebUploader.browser.ie) {
                            html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                        }
                        html += 'width="100%" height="100%" style="outline:0">' +
                            '<param name="movie" value="' + swf + '" />' +
                            '<param name="wmode" value="transparent" />' +
                            '<param name="allowscriptaccess" value="always" />' +
                            '</object>';
                        container.html(html);
                    })($wrap);
                } else {
                    $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
                }
                return;
            } else if (!WebUploader.Uploader.support()) {
                alert('不支持您的浏览器！');
                return;
            }
		
	  }
	
	  
	  
	  //实例初始化
	  init_uploader = function(uploader,$base){
		   var $wrap = $('#'+$base+'_uploader'),
		     $list = $('#'+$base+'_thelist'),
	         $btn = $('#'+$base+'_ctlBtn'),
	         state = $base+'_pending',
	         percentages = {},
	         mutiple = transBoolean($("#"+$base+"_multipleId").val()),
	         extension = $("#extensionId").val(),
	         mimeTypes = $("#mimeTypesId").val(),
	         duplicate = transBoolean($("#"+$base+"_duplicateId").val()),
	         auto = transBoolean($("#"+$base+"_autoId").val());
			//实例化
		  uploader = WebUploader.create({
				
				auto: auto,
			    swf:  globalPath + '/public/webupload/css/Uploader.swf',
			    server: globalPath + '/file/upload.html',
			    pick:{
			    	id:'#'+$base+'_picker',
			    	label:'选择文件',
			    	multiple: mutiple
			    }, 
			    disableGlobalDnd: true,
			    chunked: true,
			    chunkSize: $("#chunSizeId").val(),
	            threads : 1,
	            prepareNextFile: true,
			    formData: {
			    	guid:GUID
			    },
			    duplicate:duplicate,
			    resize: false,
			    fileSizeLimit: $("#maxSizeId").val(),
			    fileSingleSizeLimit: $("#singlSizeId").val()
			});
			
			//上传前准备
			uploader.on( 'beforeFileQueued', function(file) {
				if (!checkFileType(file.name)){
					uploader.removeFile(file);
					alert("不支持上传文件类型!");
					return;
				}
				
				if (!mutiple){
					uploader.reset();
				}
			});
			
			//待上传的文件
			uploader.on( 'fileQueued', function(file) {
				if (!checkFileType(file.name)){
					$("#"+file.id).remove();
					return;
				}
				if (!mutiple){
					$list.empty();
				}
				liHtml($list,file);
			});
			//上传进度条
			uploader.on( 'uploadProgress', function( file, percentage ) {
			    var $li = $( '#'+file.id ),
			        $percent = $li.find('.progress .progress-bar');
			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<div class="progress progress-striped active">' +
			          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
			          '</div>' +
			        '</div>').appendTo( $li ).find('.progress-bar');
			    }

			    $li.find('h4').text('上传中');
			    $percent.css( 'width', percentage * 100 + '%' );
			});
			//上传成功后
			uploader.on( 'uploadSuccess', function(file,res) {
			   	$.post(globalPath + '/file/finished.html'
			   			,{fileName: file.name, path: res._raw , businessId: $("#"+$base+"_businessId").val(),
			   			  typeId: $("#"+$base+"_typeId").val(), key: $("#"+$base+"_sysKeyId").val() ,mutiple: mutiple
			   			},
			   			function(msg){
			   				if (msg == 'ok') {
			   					$( '#'+file.id ).find('h4').text("上传成功");
			   					setTimeout(function(){$list.empty();}, 1000);
			   					showInit();
			   				}
			   				
			   				
			   	});
			});
			
			//带上传文件
			liHtml = function (html,file) {
				html.append( '<li id="' + file.id + '" class="item">' +
				        '<h4 class="info">' + file.name + ',文件大小:'  + WebUploader.formatSize(file.size) + ',' +
				        ' 等待上传...' + '<span onclick=\'delFile(this,"'+file.id+'");\' style=\'color:red;cursor:pointer\'>×</span></h4>' + 
				    '</li>' );
			}
			//判断文件类型
			checkFileType = function (fileName){
				var fileType = $("#extensionId").val();
				if (fileName) {
					var fileExt = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
					if (fileType.indexOf(fileExt) != -1){
						return true;
					}
				}
				return false;
			}
			var fileObj =[];
			delFile = function (obj,fileId){
				$(obj).parent().parent().remove();
				fileObj.push(uploader.getFile(fileId));
			}
			
			uploader.on( 'uploadError', function( file ) {
			    $( '#'+file.id ).find('h4').text('上传出错');
			});

			uploader.on( 'uploadComplete', function( file ) {
			    $( '#'+file.id ).find('.progress').fadeOut();
			});
			
			$btn.on( 'click', function() {
				if (fileObj && fileObj.length > 0){
					for (var i = 0;i<fileObj.length;i++){
						uploader.removeFile(fileObj[i]);
					}
				}
				if ( state === $base+'_uploading' ) {
					uploader.stop();
		        } else {
		        	uploader.upload();
		        }
				fileObj = [];
		    });
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
	
	
})(jQuery);