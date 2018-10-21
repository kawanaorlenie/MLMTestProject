$(document).ready(function() {
	$("input[type=submit], button").button().addClass("block");

	$('option[value="' + $.cookie("language_selected") + '"]').attr("selected", "");

	$("#languageSelect").change(function() {
		$.cookie("language_selected", $(this).val());
		window.location = '?language=' + $(this).val();
	});
	
});