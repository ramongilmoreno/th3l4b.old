/*global define */

define('com/th3l4b/screens/web/javascript-runtime-treeTrack', function () {
	return function (original, modifications) {
		return {
			getRoot: function () {
				return original.getRoot();
			},
			
			setRoot: function (root) {
				original.setRoot(root);
				modifications.push({
					type: "SetRoot",
					screen: root
				});
			},
			
			screens: function () {
				return original.screens();
			},
			
			children: function (screen) {
				return original.children(screen);
			},
			
			parent: function (screen) {
				return original.parent(screen);
			},
			
			addScreen: function (screen, parent) {
				original.addScreen(screen, parent);
				modifications.push({
					type: "AddScreen",
					screen: screen,
					parent: parent
				});
			},
			
			removeScreen: function (screen) {
				original.removeScreen(screen);
				modifications.push({
					type: "RemoveScreen",
					screen: screen
				});
			},
			
			setProperty: function (screen, property, value) {
				original.setProperty(screen, property, value);
				modifications.push({
					type: "SetProperty",
					screen: screen,
					property: property,
					value: value
				});
			},
			
			getProperty: function (screen, property, value) {
				return original.getProperty(screen, property, value);
			},
			
			removeProperty: function (screen, property) {
				original.removeProperty(screen, property);
				modifications.push({
					type: "RemoveProperty",
					screen: screen,
					property: property
				});
			},
			
			hasProperty: function (screen, property) {
				return original.hasProperty(screen, property);
			},
			
			properties: function (screen) {
				return original.properties(screen);
			}
		};
	};
});

define('com/th3l4b/screens/web/javascript-runtime-treeTrack-apply', function () {
	return function (modifications, tree) {
		for (var i in modifications) {
			var m = modifications[i];
			if (m.type == 'SetRoot') {
				tree.setRoot(m.screen);
			} else if (m.type == 'AddScreen') {
				tree.addScreen(m.screen, m.parent);
			} else if (m.type == 'RemoveScreen') {
				tree.removeScreen(m.screen);
			} else if (m.type == 'SetProperty') {
				tree.setProperty(m.screen, m.property, m.value);
			} else if (m.type == 'RemoveProperty') {
				tree.removeProperty(m.screen, m.property);
			} else {
				throw "Unknown modification: " + m;
			}
		}
	};
});

define('com/th3l4b/screens/web/javascript-runtime-treeTrack-request', function () {
	return function (modifications) {
		var r = [];
		var parameter = "modifications";
		r.push(parameter + "=");
		r.push(modifications.length);
		for (var i in modifications) {
			var m = modifications[i];
			var prefix = "&" + parameter + "." + i + ".";
			for (var j in m) {
				if (m.hasOwnProperty(j)) {
					var v = m[j];
					r.push(prefix);
					r.push(encodeURIComponent(j));
					r.push("=");
					r.push(encodeURIComponent(v));
				}
			}
		}
		return r.join("");
	};
});