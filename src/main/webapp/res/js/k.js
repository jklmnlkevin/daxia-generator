var k_ctx = getRootPath();
console.log('k_ctx = ' + k_ctx);
k_initAutocomplete();

function k_initAutocomplete() {
	console.log('k_initAutocomplete...');
	$("input[data-autocomplete=username]").autocomplete({
	    source: function(request, response) {
	   	  $.ajax({
	             url: k_ctx + "/admin/user/autocomplete",
	             dataType: "json",
	             data: {
	           	  term: request.term
	             },
	             success: function(data) {
		           	  console.log('data = ' + data);
		           	  // return response({'abc':'abc'});
		           	  var arr = new Array();
		           	  for (var i = 0; i < data.length; i++) {
		           		  arr[i] = {label:data[i].username, value:data[i].username};
		           	  }
		           	  response(arr);
	              }
	         });
	     }, 
	     select: function( event, ui ) {
	     }
	});
	
	// group
	/*$("input[data-autocomplete=friendGroup]").autocomplete({
	    source: function(request, response) {
	   	  $.ajax({
	             url: k_ctx + "/admin/friendGroup/autocomplete",
	             dataType: "json",
	             data: {
	           	  term: request.term,
	           	  username: $('')
	             },
	             success: function(data) {
		           	  console.log('data = ' + data);
		           	  // return response({'abc':'abc'});
		           	  var arr = new Array();
		           	  for (var i = 0; i < data.length; i++) {
		           		  arr[i] = {label:data[i].username, value:data[i].username};
		           	  }
		           	  response(arr);
	              }
	         });
	     }, 
	     select: function( event, ui ) {
	     }
	}); */
};

function getRootPath(){
    var scriptObj = $("script[src$='/res/js/k.js']");
    if(scriptObj==undefined)
    {
        return "";
    }
    var srcBase = scriptObj.eq(0).attr("src").replace("/res/js/k.js","");;
    return srcBase
}

function k_init() {
	$('input[need]').each(function() {
		var $input = $(this);
		$input.on("focus", function() {
			$val = $input.attr('need');
			var $need = $("#" + $val);
			if ($need.val() == '') {
				alert($input.attr('need-message'));
				$need.trigger("focus");
			}
			console.log($val);
		})
	});
}
