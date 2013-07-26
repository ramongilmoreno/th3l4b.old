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
	var render = function (current, domNode, context) {
		// Create a new node to fit this screen
		var newNode = context.document.createElement("div");

		// Render it
		var properties = current.properties;
		properties = properties ? properties : {};
		if (properties[constants.type] == constants.typeField) {
			context.renderer.renderField(current, newNode, function (name, value) {
				context.onChange(name, value);
			}, context);
		} else if (properties[constants.type] == constants.typeInteraction) {
			context.renderer.renderAction(current, newNode, function (name) {
				context.onAction(name);
			}, context);
                }
		renderProperties(properties, newNode, context);

		// Render children with this very function
		var childrenList = context.children(current.name, context);
		for (var child in childrenList) {
			if (childrenList.hasOwnProperty(child)) {
				context.render(childrenList[child], newNode, context);
			}
		}

		// Append result to main node
		domNode.appendChild(newNode);
	};

	/**
	* Refactors a domNode to fit a current
	*/
	var update = function (tree, currentName, domNode, context) {
		domNode.innerHTML = "";
		context.tree = tree;
		context.render(tree[currentName], domNode, context);
	};
	
	var children = function (parentName, context) {
		var r = [];
		var tree = context.tree;
		for (var child in tree) {
			if (tree.hasOwnProperty(child)) {
				var candidate = tree[child];
				if (candidate["parent"] ==  parentName) {
					r.push(candidate);
				}
			}
		}
		return r;
	};

	var handleResponse = function (request, node, context) {
		return function () {
			if ((request.readyState == 4) && (request.status == 200)) {
				var response = eval("var r = " + request.responseText + "; r;");
				if (response.ok) {
					update(response.tree, response.root, node, context);
					return;
				}
			}

			// If here, an error happened
			// Do not throw here or the error will be thrown
			// throw { id: "KO", message: "Could not find the 'ok'"};
		};
	};

	/**
	* Create context
	*/
	var createContext = function (document, node, target, renderer) {
		var r = {
		};
		r.tree = [];
		r.children = children;
		r.renderer = renderer;
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
