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

