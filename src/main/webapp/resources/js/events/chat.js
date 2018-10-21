$(document).ready(function() {

	$("#postMessageDialog").dialog({
		autoOpen : false,
		modal : true,
	});

	$("#post-message").click(function() {
		$("#postMessageDialog").dialog("open");
	});

	

});


function postMessageFormSuccess(json) {

	if (json.Result == "OK") {

		alert(json.Message);
	} else {
		alert(json.Message);
	}
}

function postMessageFormError(xhr, returnCode) {
	// do something based on the return code
	alert("something wrong");
}