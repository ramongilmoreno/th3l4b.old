/*global $, require */

// When the document is ready:
$().ready(function() {
	require([ "com/th3l4b/types/javascript-runtime", "com/th3l4b/screens/web/javascript-runtime", "com/th3l4b/screens/web/javascript-runtime-renderer", "com/th3l4b/screens/web/javascript-runtime-tree" ], function(types, screens, renderer, tree) {
		var t = {
			root: "_tos_a",
			tree: {
				"_tos_a": {
					properties: {
					}
				},
				"_tos_b": {
					parent: "a",
					properties: {
					}
				},
				"_tos_c": {
					parent: "a",
					properties: {
					}
				}
			}
		};
		console.log(tree.addScreen(t, "d", "a"));
		console.log(tree.screens(t));
		console.log(tree.children(t, "a"));
		console.log(tree.parent(t, "b"));
		tree.removeScreen(t, "c");
		console.log(tree.children(t, "a"));
		tree.setProperty(t, "b", "kk", "value");
		console.log(tree.getProperty(t, "b", "kk"));
		console.log(tree.hasProperty(t, "b", "kk"));
		console.log(tree.properties(t, "b"));
		tree.removeProperty(t, "b", "kk")
		console.log(tree.hasProperty(t, "b", "kk"));
		console.log(tree.properties(t, "b"));
		
		screens.createContext(document, document.getElementById("root"), "DesktopScreensServlet", renderer);
		//$("#root").html("Changed text");
	});
});

