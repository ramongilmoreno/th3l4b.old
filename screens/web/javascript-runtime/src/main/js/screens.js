/*global define */

define("com/th3l4b/screens/web/javascript-runtime",
		[
		 	"com/th3l4b/screens/web/javascript-runtime-tree",
	 		"com/th3l4b/screens/web/javascript-runtime-treeTrack",
	 		"com/th3l4b/screens/web/javascript-runtime-treeTrack-apply",
	 		"com/th3l4b/screens/web/javascript-runtime-treeTrack-request"

		],
		function (treelib, treeTrack, treeTrackApply, treeTrackRequest) {

	var prefix = "com.th3l4b.screens.base";
	var typePrefix = prefix + ".type";
	var interactionPrefix = prefix + ".interaction";
	var constants = {
		type: typePrefix,
		value: prefix + ".value",
		typeField: typePrefix + ".field",
		typeAction: typePrefix + ".action",
		typeHidden: typePrefix + ".hidden",
		interaction: interactionPrefix,
		interactionJavascript: interactionPrefix + ".javascript"
	};

	/**
	* Renders the properties of an screen in a node
	*/
	var renderProperties = function (current, domNode, context) {
		var properties = context.treelib.properties(context.tree, current);
		for (var i in properties) {
			var p = properties[i];
			var e = context.document.createElement("div");
			var v = context.treelib.getProperty(context.tree, current, p);
			var s = "" + p + " = " + v;
			var n = context.document.createTextNode(s);
			e.appendChild(n);
			domNode.appendChild(e);
		}
	};

	/**
	* Renders an screen and its children
	*/
	var render = function (current, domNode, context) {
		// Create a new node to fit this screen
		var newNode = context.document.createElement("div");

		// Render it
		var type = context.treelib.getProperty(context.tree, current, constants.type);
		if (type == constants.typeField) {
			context.renderer.renderField(current, newNode, context);
		} else if (type == constants.typeAction) {
			context.renderer.renderAction(current, newNode, context);
		}
		renderProperties(current, newNode, context);

		// Render children with this very function
		var children = context.treelib.children(context.tree, current);
		for (var i in children) {
			var child = children[i];
			context.render(child, newNode, context);
		}

		// Append result to main node
		domNode.appendChild(newNode);
	};

	/**
	* Refactors a domNode to fit a current
	*/
	var update = function (current, domNode, context) {
		domNode.innerHTML = "";
		context.render(current, domNode, context);
	};
	
	var handleResponse = function (request, node, context) {
		return function () {
			if ((request.readyState == 4) && (request.status == 200)) {
				var response = eval("var r = " + request.responseText + "; r;");
				if (response.ok) {
					if (response.tree) {
						context.tree = response.tree;
					}
					if (response.modifications) {
						treeTrackApply(response.modifications, context.tree, context.treelib);
					}
					update(context.treelib.getRoot(context.tree), node, context);
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
		r.tree = {
			root: undefined,
			tree: {
			}
		};
		r.treelib = treelib;
		r.renderer = renderer;
		r.render = render;
		r.document =  document;
		r.node = node;
		r.modifications = [];
		r.onChange = function (screen, newValue, context) {
			var tracked = treeTrack(context.treelib, context.modifications);
			tracked.setProperty(context.tree, screen, constants.value, newValue);
			if (context.treelib.getProperty(context.tree, screen, constants.interaction) == "true") {
				context.onAction(screen, context);
			}
		};
        r.onAction = function (screen, context) {
            var request = new XMLHttpRequest();
            request.onreadystatechange = handleResponse(request, node, r);
            request.open("POST", target, true);
            request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            var modifications = treeTrackRequest(context.modifications);
            // Clear modifications
            context.modifications = [];
            request.send("modificationsOnly=true&" + modifications + "&action=" + encodeURIComponent(screen)); 
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
