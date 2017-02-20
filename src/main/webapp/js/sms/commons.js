;
(function($, w) {
	if (sessionStorage.locationA) {
		$("#locationA").addClass("current");
	}
	if (sessionStorage.locationB) {
		$("#locationB").addClass("current");
	}
	if (sessionStorage.locationC) {
		$("#locationC").addClass("current");
	}
	if (sessionStorage.locationD) {
		$("#locationD").addClass("current");
	}
	if (sessionStorage.locationE) {
		$("#locationE").addClass("current");
	}
	if (sessionStorage.locationF) {
		$("#locationF").addClass("current");
	}
	if (sessionStorage.locationG) {
		$("#locationG").addClass("current");
	}
	if (sessionStorage.locationH) {
		$("#locationH").addClass("current");
	}
	$(".current").each(
			function() {
				$(this).on(
						{
							click : function() {
								w.history.go(parseInt($($(this).find("i")[0])
										.text())
										- parseInt(sessionStorage.index));
							}
						});
			});
})(jQuery, window);
