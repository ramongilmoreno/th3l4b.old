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
					type: "SetRoot",
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
					type: "AddScreen",
					screen: screen,
					parent: parent
				});
			},
			
			removeScreen: function (t, screen) {
				original.removeScreen(t, screen);
				modifications.push({
					type: "RemoveScreen",
					screen: screen
				});
			},
			
			setProperty: function (t, screen, property, value) {
				original.setProperty(t, screen, property, value);
				modifications.push({
					type: "SetProperty",
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
					type: "RemoveProperty",
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
			if (m.type == 'SetRoot') {
				tree.setRoot(t, m.screen);
			} else if (m.type == 'AddScreen') {
				tree.addScreen(t, m.screen, m.parent);
			} else if (m.type == 'RemoveScreen') {
				tree.removeScreen(t, m.screen);
			} else if (m.type == 'SetProperty') {
				tree.setProperty(t, m.screen, m.property, m.value);
			} else if (m.type == 'RemoveProperty') {
				tree.removeProperty(t, m.screen, m.property);
			} else {
				throw "Unknown modification: " + m;
			}
		}
	};
});

