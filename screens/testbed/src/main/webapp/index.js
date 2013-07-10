/*global $, require */

// When the document is ready:
$().ready(function() {
	require([ "com/th3l4b/types/javascript-runtime", "com/th3l4b/screens/web/javascript-runtime", "com/th3l4b/screens/web/javascript-runtime-renderer" ], function(types, screens, renderer) {
		screens.createContext(document, document.getElementById("root"), "DesktopScreensServlet", renderer);
		//$("#root").html("Changed text");
	});
});

