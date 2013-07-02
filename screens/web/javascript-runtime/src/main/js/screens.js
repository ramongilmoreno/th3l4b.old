/*global define */

define('com/th3l4b/screens/web/javascript-runtime', function () {

	var prefix = "com.th3l4b.screens.base";
	var typePrefix = prefix + ".type";
	var constants = {
		type: typePrefix,
		value: prefix + ".value",
		typeField: typePrefix + ".field",
		typeInteraction: typePrefix + ".interaction"
	};

	/**
	* Renders the properties of an screen in a node
	*/
	var renderProperties = function (screenProperties, domNode, context) {
		for (var p in screenProperties) {
			if (screenProperties.hasOwnProperty(p)) {
				var e = context.document.createElement("div");
				var v = screenProperties[p];
				var s = "" + p + " = " + v;
				var n = context.document.createTextNode(s);
				e.appendChild(n);
				domNode.appendChild(e);
			}
		}
	};

	/**
	* Renders an screen and its children
	*/
	var render = function (screensTree, domNode, context) {
		// Create a new node to fit this screen
		var newNode = context.document.createElement("div");

		// Render it
		var properties = screensTree.properties;
		properties = properties ? properties : {};
		if (properties[constants.type] == constants.typeField) {
			var e = context.document.createElement("input");
			e.setAttribute("type", "text");
			var v = properties[constants.value];
			if (!v) {
				v = "";
			}
			e.setAttribute("value", v);
			e.onchange = function () {
				context.onChange(screensTree.name, e.value);
			};
			newNode.appendChild(e);
		} else if (properties[constants.type] == constants.typeInteraction) {
                        var e = context.document.createElement("a");
			e.setAttribute("href", "#");
			e.onclick =  function () {
				context.onAction(screensTree.name);
			};
			var text = context.document.createTextNode("Action");
			e.appendChild(text);
                        newNode.appendChild(e);
                }

		renderProperties(properties, newNode, context);

		// Render children with this very function
		var children = screensTree.children;
		for (var child in children) {
			if (children.hasOwnProperty(child)) {
				context.render(children[child], newNode, context);
			}
		}

		// Append result to main node
		domNode.appendChild(newNode);
	};

	/**
	* Refactors a domNode to fit a screensTree
	*/
	var update = function (screensTree, domNode, context) {
		domNode.innerHTML = "";
		context.tree = screensTree;
		context.render(screensTree, domNode, context);
	};

	var handleResponse = function (request, node, context) {
		return function () {
			if ((request.readyState == 4) && (request.status == 200)) {
				var response = eval("var r = " + request.responseText + "; r;");
				if (response.ok) {
					update(response.tree, node, context);
					return;
				}
			}

			// If here, an error happened
		};
	};

	/**
	* Create context
	*/
	var createContext = function (document, node, target) {
		var r = {
		};
		r.render = render;
		r.document =  document;
		r.node = node;
		r.onChange = function (screen, newValue) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = handleResponse(request, node, r);
			request.open("POST", target, true);
			request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			request.send("set=1&set.0.screen=" + encodeURIComponent(screen) + "&set.0.property=com.th3l4b.screens.base.value&set.0.value=" + encodeURIComponent(newValue));
		};
                r.onAction = function (screen) {
                        var request = new XMLHttpRequest();
			request.onreadystatechange = handleResponse(request, node, r);
                        request.open("POST", target, true);
                        request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                        request.send("actions=1&actions.0=" + encodeURIComponent(screen)); 
                };
		var request = new XMLHttpRequest();
		request.onreadystatechange = handleResponse(request, node, r);
		request.open("GET", target, true);
		request.send();
		return r;
	
	};

	return {
		constants: constants,
		renderProperties: renderProperties,
		render: render,
		update: update,
		createContext: createContext
	};
});
