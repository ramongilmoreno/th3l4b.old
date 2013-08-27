/*global define */

define('com/th3l4b/screens/web/javascript-runtime-tree', function () {
	return function (tree) {
		if (tree == undefined) {
			tree = {
				root: undefined,
				nodes: {
				}
			};
		}
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
				var t2 = tree.nodes;
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
				var t2 = tree.nodes;
				var r = [];
				for (var i in t2) {
					if (t2.hasOwnProperty(i) && (t2[i].parent == screen)) {
						i = i.substring(prefixLength);
						r.push(i);
					}
				}
				var f = function (a) {
					var r = 10;
					a = tree.nodes[prefix + a].properties[prefix + "com.th3l4b.screens.base.order.index"]
					if (a) {
						var v = parseInt(a);
						if (!isNaN(v)) {
							r = v; 
						}
					}
					return r;
				}
				r.sort(function (a, b) {
					return f(a) - f(b);
				});
				return r;
			},
			
			parent: function (screen) {
				return tree.nodes[prefix + screen].parent;
			},
			
			addScreen: function (screen, parent) {
				tree.nodes[prefix + screen] = {
					name: screen,
					parent: parent,
					properties: {
					}
				};
			},
			
			removeScreen: function (screen) {
				delete tree.nodes[prefix + screen];
			},
			
			setProperty: function (screen, property, value) {
				tree.nodes[prefix + screen].properties[prefix + property] = value;
			},
			
			getProperty: function (screen, property, value) {
				return tree.nodes[prefix + screen].properties[prefix + property];
			},
			
			removeProperty: function (screen, property) {
				delete tree.nodes[prefix + screen].properties[prefix + property];
			},
			
			hasProperty: function (screen, property) {
				return tree.nodes[prefix + screen].properties.hasOwnProperty(prefix + property);
			},
			
			properties: function (screen) {
				var r = [];
				var p = tree.nodes[prefix + screen].properties;
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
