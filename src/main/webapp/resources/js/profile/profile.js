$(document).ready(function() {

	$("#editProfileDialog").dialog({
		autoOpen : false,
		modal : true,
		buttons : {
			"Update" : function() {
				$('#editProfileForm').ajaxSubmit({
					success : editProfileFormSuccess,
					error : editProfileFormError,
					dataType : 'json'
				});
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
	});

	$("#changePasswordDialog").dialog({
		autoOpen : false,
		modal : true,
		buttons : {
			"Change" : function() {
				$('#changePasswordForm').ajaxSubmit({
					success : changePasswordFormSuccess,
					error : changePasswordFormError,
					dataType : 'json'
				});
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
	});

	$("#uploadPhotoDialog").dialog({
		autoOpen : false,
		modal : true,
		buttons : {
			"Update" : function() {
				$('#upload').click();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
	});

	$("#birthdate").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : "1914:2014",
		dateFormat : "dd-mm-yy"
	});

	$("#edit-profile").click(function() {
		$("#editProfileDialog").dialog("open");
	});

	$("#changePassword").click(function() {
		$("#changePasswordDialog").dialog("open");
	});

	$("#uploadPhoto").click(function() {
		$("#uploadPhotoDialog").dialog("open");
	});

	$("#fileName").click(function(e) {
		e.preventDefault();
		$('#file').click();
	});

	$('#file').change(function(e) {
		var path = $(this).val();
		var filename = path.split('\\')[2];
		$('input[id="fileName"]').val(filename);
	});

	$('button').css('display', 'inline-block');

});

function changePasswordFormSuccess(json) {
	$('#changePasswordDialog .alert').remove();
	if (json.Result == "OK") {
		showSuccessMessage(json.Message);
	} else {
		showValidationMessages(json.Records);
	}
}

function changePasswordFormError(xhr, returnCode) {
	alert("something wrong");
}

function editProfileFormSuccess(json) {
	if (json.Result == "OK") {
		updateProfileValues(json.Records);
	} else {
		alert(json.Message);
	}
}

function editProfileFormError(xhr, returnCode) {
	alert("something wrong");
}

function showSuccessMessage(message) {
	$('#changePasswordForm').before(
			'<div id="successMessage" class="alert alert-success">' + message
					+ '</div>');
}

function showValidationMessages(records) {
	if (records.newPassword != "" && records.newPassword != null)
		$('#newPassword').before(
				'<div id="newPasswordError" class="alert alert-danger">'
						+ records.newPassword + '</div>');
}

function updateProfileValues(records) {
	$('#hFirstName').text(records.firstName);
	$('#hLastName').text(records.lastName);
	$('#hCity').text(records.city);
	var d = new Date(records.birthdate);
	$('#hBirthdate').text(
			d.getDate() + '-' + parseInt(d.getMonth()+1) + '-' + d.getFullYear());
	$("#editProfileDialog").dialog('close');
}
