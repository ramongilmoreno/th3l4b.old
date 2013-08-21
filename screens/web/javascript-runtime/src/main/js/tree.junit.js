/*global define */

define('com/th3l4b/screens/web/javascript-runtime-tree-test',
		[
		 	'com/th3l4b/screens/web/javascript-runtime-tree',
		 	'com/th3l4b/types/javascript-runtime-junit'
		 	],
		function (tree, junitlib) {
	return function () {
		var junit = junitlib();
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
		tree.addScreen(t, "d", "a");
		junit.assertArraysEquals(['a', 'b', 'c', 'd'], tree.screens(t));
		junit.assertArraysEquals(['b', 'c', 'd'], tree.children(t, "a"));
		junit.assertEquals('a', tree.parent(t, "b"));
		tree.removeScreen(t, "c");
		junit.assertArraysEquals(['b', 'd'], tree.children(t, "a"));
		tree.setProperty(t, "b", "kk", "value");
		junit.assertEquals('value', tree.getProperty(t, "b", "kk"));
		junit.assertTrue(tree.hasProperty(t, "b", "kk"));
		junit.assertArraysEquals(['kk'], tree.properties(t, "b"));
		tree.removeProperty(t, "b", "kk")
		junit.assertFalse(tree.hasProperty(t, "b", "kk"));
		junit.assertArraysEquals([], tree.properties(t, "b"));
	};
});