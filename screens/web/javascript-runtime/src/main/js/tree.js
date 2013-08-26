/*global define */

define('com/th3l4b/screens/web/javascript-runtime-tree', function () {
	return function (tree) {
		var prefix = "_tos_";
		var prefixLength = prefix.length;
		return {
			getRoot: function () {
				return tree.root;
			},
			
			setRoot: function (root) {
				r.root = root;
			},
			
			screens: function () {
				var t2 = tree.tree;
				var r = [];
				for (var i in t2) {
					if (t2.hasOwnProperty(i)) {
						i = i.substring(prefixLength);
						r.push(i);
					}
				}
				return r;
			},
			
			children: function (screen) {
				var t2 = tree.tree;
				var r = [];
				for (var i in t2) {
					if (t2.hasOwnProperty(i) && (t2[i].parent == screen)) {
						i = i.substring(prefixLength);
						r.push(i);
					}
				}
				return r;
			},
			
			parent: function (screen) {
				return tree.tree[prefix + screen].parent;
			},
			
			addScreen: function (screen, parent) {
				tree.tree[prefix + screen] = {
					name: screen,
					parent: parent,
					properties: {
					}
				};
			},
			
			removeScreen: function (screen) {
				delete tree.tree[prefix + screen];
			},
			
			setProperty: function (screen, property, value) {
				tree.tree[prefix + screen].properties[prefix + property] = value;
			},
			
			getProperty: function (screen, property, value) {
				return tree.tree[prefix + screen].properties[prefix + property];
			},
			
			removeProperty: function (screen, property) {
				delete tree.tree[prefix + screen].properties[prefix + property];
			},
			
			hasProperty: function (screen, property) {
				return tree.tree[prefix + screen].properties.hasOwnProperty(prefix + property);
			},
			
			properties: function (screen) {
				var r = [];
				var p = tree.tree[prefix + screen].properties;
				for (var i in p) {
					if (p.hasOwnProperty(i)) {
						i = i.substring(prefixLength);
						r.push(i);
					}
				}
				return r;
			}
		};
	};
});
