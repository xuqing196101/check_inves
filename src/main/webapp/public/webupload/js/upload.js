/****
 * 文件上传插件
 * 
 */
(function ($) {
	var  GUID = WebUploader.Base.guid();
	var $wrap = $('#uploaderId');
	$(function(){
		webuploadEnv_check();
		$(".web_uploader_class").each(function(){
			var id = $(this).prev()[0].value;
			init_uploader(eval("var  uploader_" + id),id);
		});
		/*
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
		*/
		
		
	 });
	 
	 //判断是否为空
	 webIsNull = function(value){
		 if (value == null || value == "undefined" || value == "" || value == "null") {
			 return true;
		 }
		 return false;
	 }
	
	  //多个上传按钮
	 /* groupsArray = function(bntArry){
		  $.each(bntArry,function(){
				init_uploader(eval("var  uploader_" + this),this);
			});
	  }*/
	  
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
                            '<param name="allowscriptaccess" value="sameDomain" />' +
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
		   var $list = $('#'+$base+'_thelist'),
	         $btn = $('#'+$base+'_ctlBtn'),
	         $base = $base;
	         state = $base+'_pending',
	         fileCount = 0,
	         fileSize = 0,
	         percentLayer = null;
	         percentages = {},
	         mutiple = transBoolean($("#"+$base+"_multipleId").val()),
	         extension = $("#extensionId").val(),
	         mimeTypes = $("#mimeTypesId").val(),
	         duplicate = transBoolean($("#"+$base+"_duplicateId").val()),
	         auto = transBoolean($("#"+$base+"_autoId").val()),
	         buttonName = $("#" + $base + "_btnNameId").val();
		   if (buttonName == null || buttonName == "" || buttonName == "undefined" || buttonName == "null"){
			   buttonName = "上传彩色扫描件";
		   }
			//实例化
		  uploader = WebUploader.create({
				
				auto: auto,
			    swf:  globalPath + '/public/webupload/css/Uploader.swf',
			    server: globalPath + '/file/upload.html',
			    pick:{
			    	id:'#'+$base+'_picker',
			    	label:buttonName,
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
				if (!checkFileType(file.name,$base)){
					if (percentLayer != null){
						layer.close(percentLayer);
					}
					uploader.removeFile(file);
					var fileType =$("#"+$base+"_extId").val();
					if (fileType != null && fileType !="null" && fileType !=""){
						layer.msg("文件格式错误，只允许" + fileType + "文件格式");
					} else {
						layer.msg("文件格式错误！");
					}
					return;
				}
				var fileSize = file.size / 1024;
				var  singleFileSize = $("#" + $base + "_singFileSize").val();
				if (fileSize > singleFileSize){
					if (percentLayer != null){
						layer.close(percentLayer);
					}
					uploader.removeFile(file);
					layer.msg("文件大小错误，只允许上传" + singleFileSize + "KB文件");
					return;
				}
				var mutiples = transBoolean($("#"+$base+"_multipleId").val());
				if (!mutiples){
					uploader.reset();
				}
				var maxcount = $("#"+$base+"_maxcount").val();
				var currentCount = uploader.getFiles().length;
				if (maxcount != null) {
					$.ajax({
						url: globalPath + "/file/isOverMaxCount.do",
						data: {'businessId': $("#"+$base+"_businessId").val(),'typeId': $("#"+$base+"_typeId").val(), 'sysKey': $("#"+$base+"_sysKeyId").val(), 'maxcount':maxcount, 'currentCount':currentCount},
						success: function(msg){
							if(msg == 'error'){
								uploader.removeFile(file);
								layer.msg("超过最大数量上限，只允许上传" + maxcount + "个文件");
								return;
							}
						}
					});
				}
			});
			
			//待上传的文件
			uploader.on( 'fileQueued', function(file) {
				if (!checkFileType(file.name,$base)){
					$("#"+file.id).remove();
					return;
				}
				var mutiples = transBoolean($("#"+$base+"_multipleId").val());
				if (!mutiples){
					$list.empty();
				}
				fileCount ++;
				fileSize += file.size;
				percentages[ file.id ] = [ file.size, 0 ];
				updateTotalProgress();
			});
			
			//总限制文件大小
			uploader.on('error',function(type){
				 if(type == "F_EXCEED_SIZE"){
					 if (percentLayer != null){
						layer.close(percentLayer);
					 }
					 var  singleFileSize = $("#singlSizeId").val();
					 layer.msg("单个文件大小不能超过" + singleFileSize /1024/1024 + "MB");
				 }
			});
			
			//上传进度条
			uploader.on( 'uploadProgress', function( file, percentage ) {
				var $percent =$('.progress span .percentage');
			    $percent.css( 'width', percentage * 100 + '%' );
			    percentages[ file.id ][ 1 ] = percentage;
	            updateTotalProgress();
			});
			//上传成功后
			uploader.on( 'uploadSuccess', function(file,res) {
				if(file && res){
					$.post(globalPath + '/file/finished.html'
			   			,{fileName: file.name, path: res._raw , businessId: $("#"+$base+"_businessId").val(),
			   			  typeId: $("#"+$base+"_typeId").val(), key: $("#"+$base+"_sysKeyId").val() ,mutiple: $("#"+$base+"_multipleId").val()
			   			},
			   			function(msg){
			   				if (msg == 'ok') {
			   					var sid = $("#" + $base + "_btnNameId").nextAll('#showId').val();
			   					if (sid == undefined){
			   						var pic_show = $("#"+ $base +"_btnNameId").parent().next(".picShow")[0];
			   						var showPicId = $(pic_show).children("#showId")[0].id;
			   						sid = $("#" +showPicId).val();
			   					}
			   					showInitAfterUpload(sid);
			   					// showInit();
			   				}
			   			}
			   		);
				}
			});
			
			//判断文件类型
			checkFileType = function (fileName,id){
				var fileType =$("#"+id+"_extId").val();
				var allType = $("#extensionId").val();
				var fileExt = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
				if (fileType == null || fileType == "" || fileType == "null" ){
					if (allType.indexOf(fileExt) != -1){
						return true;
					}
				} else {
					if (fileType.indexOf(fileExt) != -1){
						return true;
					}
				}
				return false;
			}
			
			/**
			 * 删除文件
			 */
			var fileObj =[];
			delFile = function (obj,fileId){
				$(obj).parent().parent().remove();
				fileObj.push(uploader.getFile(fileId));
			}
			
			/***
			 * 总进度
			 */
			function updateTotalProgress() {
	            var $statusBar = $('#statuId'),
		         $info = $statusBar.find( '.info' ),
		         $progress = $statusBar.find( '.progress' ),
	                loaded = 0,
	                total = 0,
	                spans = $progress.children(),
	                percent;
	            $.each( percentages, function( k, v ) {
	                total += v[ 0 ];
	                loaded += v[ 0 ] * v[ 1 ];
	            } );

	            percent = total ? loaded / total : 0;

	            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
	            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
	            updateStatus();
	        }
			
			/***
			 * 更新状态
			 */
			function updateStatus() {
	            var text = '', stats;
                stats = uploader.getStats();
                var successNum = stats.successNum + 1;
                text = '共' + fileCount + '个（' +
                        WebUploader.formatSize( fileSize )  +
                        '），已上传' +  successNum + '个';

                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '个';
                }
                $("#statuId .info").html(text);
	        }
			
			/**
			 * 打开上传进度
			 */
			openUploadDiv = function(){
				var html= "<div id='statuId' class='statusBar'>" +
						    "<div class='progress fl'>" +
						      "<span class='text'></span>" +
						      "<span class='percentage' style='width:0%'></span>" +
						    "</div>" +
						    "<div class='info pl10 mt5 pr10'></div>" +
						  "</div>";
				percentLayer = layer.open({
					  type: 1,
					  title:false,
					  closeBtn: 0,
					  shadeClose: false,
					  content: html
					});
			  }
			
			/**
			 * 上传出错
			 */
			uploader.on( 'uploadError', function( file ) {
			    $( '#'+file.id ).find('h4').text('上传出错');
			});
			
			/**
			 * 完成所有的上传
			 */
			uploader.on('uploadFinished',function(){
				fileCount = 0;
				if (percentLayer != null){
					layer.close(percentLayer);
				}
				uploader.reset();
			});
			
			/**
			 * 开始上传
			 */
			uploader.on('startUpload',function(){
				openUploadDiv();
			});
			
			/**
			 * 点击上传
			 */
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