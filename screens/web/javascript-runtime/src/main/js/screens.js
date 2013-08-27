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
		var properties = context.tree.properties(current);
		for (var i in properties) {
			var p = properties[i];
			var e = context.document.createElement("div");
			var v = context.tree.getProperty(current, p);
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
		var type = context.tree.getProperty(current, constants.type);
		if (type == constants.typeField) {
			context.renderer.renderField(current, newNode, context);
		} else if (type == constants.typeAction) {
			context.renderer.renderAction(current, newNode, context);
		}
		renderProperties(current, newNode, context);

		// Render children with this very function
		var children = context.tree.children(current);
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
						context.tree = treelib(response.tree);
					}
					if (response.modifications) {
						treeTrackApply(response.modifications, context.tree);
					}
					update(context.tree.getRoot(), node, context);
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
		r.tree = treelib();
		r.renderer = renderer;
		r.render = render;
		r.document =  document;
		r.node = node;
		r.modifications = [];
		r.onChange = function (screen, newValue, context) {
			var tracked = treeTrack(context.tree, context.modifications);
			tracked.setProperty(screen, constants.value, newValue);
			if (context.tree.getProperty(screen, constants.interaction) == "true") {
				context.onAction(screen, context);
			}
		};
        r.onAction = function (screen, context) {
        	var javascriptAction = context.tree.getProperty(screen, constants.interactionJavascript);
        	if (context.tree.hasProperty(screen, constants.interactionJavascript)) {
    			var tracked = treeTrack(context.tree, context.modifications);
        		// Action handled locally
        		var icontext = {
        				tree: tracked
        		};
        		var iclient = {
        		};
        		javascriptAction = eval("var r = " + javascriptAction + "; r;");
        		javascriptAction(screen, icontext, iclient);
				update(context.tree.getRoot(), node, context);
        	} else {
        		// Server handles the action.
	            var request = new XMLHttpRequest();
	            request.onreadystatechange = handleResponse(request, node, r);
	            request.open("POST", target, true);
	            request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	            var modifications = treeTrackRequest(context.modifications);
	            // Clear modifications
	            context.modifications = [];
	            request.send("modificationsOnly=true&" + modifications + "&action=" + encodeURIComponent(screen));
        	}
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
