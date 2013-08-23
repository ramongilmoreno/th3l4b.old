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
		var tree = treeTrack(treelib, modifications);
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
		treeTrackApply(modifications, original, treelib);
		console.log(treeTrackRequest(modifications));
		junit.assertDeepEquals(t, original, "Modifications applied to copy do not lead to same result");
	};
});
