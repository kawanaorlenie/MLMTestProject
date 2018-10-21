$(document).ready(function() {

	$("#createEventDialog").dialog({
		autoOpen : false,
		modal : true,
	});

	$('#date').datetimepicker({
		mask:'39-19-9999 29:59'
	});


	$("#create-event").click(function() {
		$("#createEventDialog").dialog("open");
	});

	

});


function createEventFormSuccess(json) {

	if (json.Result == "OK") {

		alert(json.Message);
	} else {
		alert(json.Message);
	}
}

function createEventFormError(xhr, returnCode) {
	// do something based on the return code
	alert("something wrong");
}
