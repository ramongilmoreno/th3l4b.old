/*global define */

define('com/th3l4b/screens/web/javascript-runtime-treeTrack', function () {
	return function (original, modifications) {
		return {
			getRoot: function (t) {
				return original.getRoot(t);
			},
			
			setRoot: function (t, root) {
				original.setRoot(t, root);
				modifications.push({
					type: "setRoot",
					screen: root
				});
			},
			
			screens: function (t) {
				return original.screens(t);
			},
			
			children: function (t, screen) {
				return original.children(t, screen);
			},
			
			parent: function (t, screen) {
				return original.parent(t, screen);
			},
			
			addScreen: function (t, screen, parent) {
				original.addScreen(t, screen, parent);
				modifications.push({
					type: "addScreen",
					screen: screen,
					parent: parent
				});
			},
			
			removeScreen: function (t, screen) {
				original.removeScreen(t, screen);
				modifications.push({
					type: "removeScreen",
					screen: screen
				});
			},
			
			setProperty: function (t, screen, property, value) {
				original.setProperty(t, screen, property, value);
				modifications.push({
					type: "setProperty",
					screen: screen,
					property: property,
					value: value
				});
			},
			
			getProperty: function (t, screen, property, value) {
				return original.getProperty(t, screen, property, value);
			},
			
			removeProperty: function (t, screen, property) {
				original.removeProperty(t, screen, property);
				modifications.push({
					type: "removeProperty",
					screen: screen,
					property: property
				});
			},
			
			hasProperty: function (t, screen, property) {
				return original.hasProperty(t, screen, property);
			},
			
			properties: function (t, screen) {
				return original.properties(t, screen);
			}
		};
	};
});

define('com/th3l4b/screens/web/javascript-runtime-treeTrack-apply', function () {
	return function (modifications, t, tree) {
		for (var i in modifications) {
			var m = modifications[i];
			if (m.type == 'setRoot') {
				tree.setRoot(t, m.screen);
			} else if (m.type == 'addScreen') {
				tree.addScreen(t, m.screen, m.parent);
			} else if (m.type == 'removeScreen') {
				tree.removeScreen(t, m.screen);
			} else if (m.type == 'setProperty') {
				tree.setProperty(t, m.screen, m.property, m.value);
			} else if (m.type == 'removeProperty') {
				tree.removeProperty(t, m.screen, m.property);
			} else {
				throw "Unknown modification: " + m;
			}
		}
	};
});

define('com/th3l4b/screens/web/javascript-runtime-treeTrack-test',
		[
		 		'com/th3l4b/screens/web/javascript-runtime-tree',
		 		'com/th3l4b/screens/web/javascript-runtime-treeTrack',
		 		'com/th3l4b/screens/web/javascript-runtime-treeTrack-apply',
		 		'com/th3l4b/types/javascript-runtime-junit'
		],
		function (treelib, treeTrack, treeTrackApply, junitlib) {
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
		junit.assertDeepEquals(t, original, "Modifications applied to copy does not lead to same result");
	};
});