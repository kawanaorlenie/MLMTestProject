function handler( event ) {
  var target = $( event.target );
  if ( target.is( "li" ) ) {
    target.children().toggle();
    if(target.hasClass('expanded')) {
    	target.removeClass('expanded');
	} else { 
		target.addClass('expanded');
	} 
  }
}

$(document).ready(function() {
	
	$( "ul" ).click( handler ).find( "ul" ).hide();
}); 
