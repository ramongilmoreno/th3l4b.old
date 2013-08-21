/*global define */

define('com/th3l4b/screens/web/javascript-runtime-tree', function () {
	var prefix = "_tos_";
	var prefixLength = prefix.length;
	return {
		getRoot: function (t) {
			return t.root;
		},
		
		setRoot: function (t, root) {
			r.root = root;
		},
		
		screens: function (t) {
			var t2 = t.tree;
			var r = [];
			for (var i in t2) {
				if (t2.hasOwnProperty(i)) {
					i = i.substring(prefixLength);
					r.push(i);
				}
			}
			return r;
		},
		
		children: function (t, screen) {
			var t2 = t.tree;
			var r = [];
			for (var i in t2) {
				if (t2.hasOwnProperty(i) && (t2[i].parent == screen)) {
					i = i.substring(prefixLength);
					r.push(i);
				}
			}
			return r;
		},
		
		parent: function (t, screen) {
			return t.tree[prefix + screen].parent;
		},
		
		addScreen: function (t, screen, parent) {
			t.tree[prefix + screen] = {
				name: screen,
				parent: parent,
				properties: {
				}
			};
		},
		
		removeScreen: function (t, screen) {
			delete t.tree[prefix + screen];
		},
		
		setProperty: function (t, screen, property, value) {
			t.tree[prefix + screen].properties[prefix + property] = value;
		},
		
		getProperty: function (t, screen, property, value) {
			return t.tree[prefix + screen].properties[prefix + property];
		},
		
		removeProperty: function (t, screen, property) {
			delete t.tree[prefix + screen].properties[prefix + property];
		},
		
		hasProperty: function (t, screen, property) {
			return t.tree[prefix + screen].properties.hasOwnProperty(prefix + property);
		},
		
		properties: function (t, screen) {
			var r = [];
			var p = t.tree[prefix + screen].properties;
			for (var i in p) {
				if (p.hasOwnProperty(i)) {
					i = i.substring(prefixLength);
					r.push(i);
				}
			}
			return r;
		}
	};
});

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