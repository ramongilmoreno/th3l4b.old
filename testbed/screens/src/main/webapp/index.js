/*global $, require */

// When the document is ready:
$().ready(function() {
	require([
         "com/th3l4b/types/javascript-runtime",
         "com/th3l4b/screens/web/javascript-runtime",
         "com/th3l4b/screens/web/javascript-runtime-renderer",
         "com/th3l4b/screens/web/javascript-runtime-tree",
         "com/th3l4b/screens/web/javascript-runtime-tree-test",
         "com/th3l4b/screens/web/javascript-runtime-treeTrack-test"
	], function(types, screens, renderer, tree, tree_test, treeTrack_test) {
		tree_test();
		treeTrack_test();
		screens.createContext(document, document.getElementById("root"), "DesktopScreensServlet", renderer);
		//$("#root").html("Changed text");
	});
});

