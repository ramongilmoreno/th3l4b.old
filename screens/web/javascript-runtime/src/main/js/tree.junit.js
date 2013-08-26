/*global define */

define('com/th3l4b/screens/web/javascript-runtime-tree-test',
		[
		 	'com/th3l4b/screens/web/javascript-runtime-tree',
		 	'com/th3l4b/types/javascript-runtime-junit'
		 	],
		function (treelib, junitlib) {
	return function () {
		var junit = junitlib();
		var t = {
			root: "_tos_a",
			nodes: {
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
		var tree = treelib(t);
		tree.addScreen("d", "a");
		junit.assertArraysEquals(['a', 'b', 'c', 'd'], tree.screens());
		junit.assertArraysEquals(['b', 'c', 'd'], tree.children("a"));
		junit.assertEquals('a', tree.parent("b"));
		tree.removeScreen("c");
		junit.assertArraysEquals(['b', 'd'], tree.children("a"));
		tree.setProperty("b", "kk", "value");
		junit.assertEquals('value', tree.getProperty("b", "kk"));
		junit.assertTrue(tree.hasProperty("b", "kk"));
		junit.assertArraysEquals(['kk'], tree.properties("b"));
		tree.removeProperty("b", "kk")
		junit.assertFalse(tree.hasProperty("b", "kk"));
		junit.assertArraysEquals([], tree.properties("b"));
	};
});