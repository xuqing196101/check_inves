;(function($, w) {
	var user="${sessionScope.loginUser.relName}";
	console.info(user)
	//locations.push(window.location.href);
	//alert(JSON.stringify(locations))
	w.onload = function() {
		var $doms = $(".current").each(function() {
			$(this).on({
				click : function() {
					switch (parseInt($($(this).find("i")[0]).text())) {
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					case 4:

						break;
					case 5:

						break;
					case 6:

						break;
					case 7:

						break;
					case 8:

						break;
					default:
						//window.location.href = window.location;
						break

					}

				}
			})
		});
	}
	
})(jQuery, window);
