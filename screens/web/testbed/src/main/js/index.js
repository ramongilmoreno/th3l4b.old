/*global $, require */

// When the document is ready:
$().ready(function() {
	require([ "com.th3l4b.types.javascript-runtime", "com.th3l4b.screens.web.javascript-runtime" ], function(types, screens) {
		$("#a").html("Changed text");
	});
});
