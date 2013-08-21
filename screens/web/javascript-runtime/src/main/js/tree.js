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
