//alert($($(this).find("i")[0]).text())
;(function($, w) {
	if(sessionStorage.locationA){
		$("#locationA").addClass("current")
	}
	if(sessionStorage.locationB){
		$("#locationB").addClass("current")
	}
	if(sessionStorage.locationC){
		$("#locationC").addClass("current")
	}
	if(sessionStorage.locationD){
		$("#locationD").addClass("current")
	}
	if(sessionStorage.locationE){
		$("#locationE").addClass("current")
	}
	if(sessionStorage.locationF){
		$("#locationF").addClass("current")
	}
	if(sessionStorage.locationG){
		$("#locationG").addClass("current")
	}
	if(sessionStorage.locationH){
		$("#locationH").addClass("current")
	}
		var $doms = $(".current").each(function() {
			$(this).on({
				click : function() {
					switch (parseInt($($(this).find("i")[0]).text())) {
					case 1:
						window.location.href=sessionStorage.locationA;
						break;
					case 2:
						$("input[name='flag']").val("1");
						$("#basic_info_form_id").submit();
						break;
					case 3:
						/*$("input[name='flag']").val("1");
						$("#items_info_form_id").submit();*/
						break;
					case 4:
						$("input[name='flag']").val("1");
						$("#items_info_form_id").submit();
						break;
					case 5:
						$("#flag").val("5");
						 $("#items_info_form_id").submit();
						break;
					case 6:
						$("input[name='flag']").val("prev");
						$("#template_download_form_id").submit();
						break;
					case 7:
						$("input[name='jsp']").val("prev");
						$("#template_upload_form_id").submit();
						break;
					case 8:
						$("input[name='flag']").val("next");
						$("#template_download_form_id").submit();
						break;
					default:
						window.location.href = window.location;
						break;

					}

				}
			});
		});
	
})(jQuery, window);
