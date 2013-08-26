/*global define */

define('com/th3l4b/screens/web/javascript-runtime-treeTrack-test',
		[
		 		'com/th3l4b/screens/web/javascript-runtime-tree',
		 		'com/th3l4b/screens/web/javascript-runtime-treeTrack',
		 		'com/th3l4b/screens/web/javascript-runtime-treeTrack-apply',
		 		'com/th3l4b/screens/web/javascript-runtime-treeTrack-request',
		 		'com/th3l4b/types/javascript-runtime-junit'
		],
		function (treelib, treeTrack, treeTrackApply, treeTrackRequest, junitlib) {
	var deepCopy = function (a) {
		var r = {};
		for (var i in a) {
			if (a.hasOwnProperty(i)) {
				var src = a[i];
				if (typeof src == 'object') {
					r[i] = deepCopy(a[i]);
				} else {
					r[i] = a[i];
				}
			}
		}
		return r;
	};
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
		var original = deepCopy(t);
//		original.tree._tos_b.parent = "b";
		junit.assertDeepEquals(t, original, "Copy is not equal");
		var modifications = [];
		var tree = treeTrack(treelib(t), modifications);
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
		treeTrackApply(modifications, treelib(original));
		console.log(treeTrackRequest(modifications));
		junit.assertDeepEquals(t, original, "Modifications applied to copy do not lead to same result");
	};
});
