$(document).ready(function() {

	$("#tabs").tabs({
		activate : function(event, ui) {
			$.cookie("tabs_selected", $("#tabs").tabs("option", "active"));
		},
		active : $("#tabs").tabs({
			active : $.cookie("tabs_selected")
		}),
	});

	$('#userName').blur(function() {
		validate($(this), 'userName');
	});

});

function validate(input, name) {
	var formId = input.closest('form').attr('id');
	var buttonId = $("#" + formId + " button").attr('id');
	var jsonURL = '/MLMTestProject/validation/' + name;
	var data = name + '=' + input.val();
	$.post(jsonURL, data, function(json) {

		$('div[for=' + input.attr('id') + ']').remove();
		if (json.Result == 'OK') {
			styleAsValid(input);
		} else {
			styleAsInvalid(input);
			showValidationMessages(input, json.Records);
		}
	}, 'json');
}

function styleAsValid(input) {
	input.css('border-color', 'green');
};

function styleAsInvalid(input) {
	input.css('border-color', 'red');
};

function showValidationMessages(input, records) {
	var label = $('label[for=' + input.attr('id') + ']');
	if (records[input.attr('id')] != null)
		label.before('<div for="' + input.attr('id')
				+ '" class="alert alert-danger">' + records[input.attr('id')]
				+ '</div>');
}
