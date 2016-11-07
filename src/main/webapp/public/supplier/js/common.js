function autoSelected(id, v) {
	if (v) {
		$("#" + id).find("option").each(function() {
			var value = $(this).val();
			if(value == v) {
				$(this).prop("selected", true);
			} else {
				$(this).prop("selected", false);
			}
		});
	}
}

function resetForm(id) {
	$("#" + id).find(":text").each(function() {
		$(this).val("");
	});
	$("#" + id).find("select").each(function() {
		$(this).find("option").eq(0).prop("selected", true);
	});
}