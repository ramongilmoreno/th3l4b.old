/*global $, require */

// When the document is ready:
$().ready(function() {
	require([ "com/th3l4b/types/javascript-runtime", "com/th3l4b/screens/web/javascript-runtime" ], function(types, screens) {

		screens.createContext(document, document.getElementById("root"), "DesktopScreensServlet");

		//$("#root").html("Changed text");
	});
});

