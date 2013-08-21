/*global define */

define('com/th3l4b/screens/web/javascript-runtime-renderer', function () {

	var innerRenderField = function (screen, node, onChange, context) {
		var e = context.document.createElement("input");
		e.setAttribute("type", "text");
		var v = context.treelib.getProperty(context.tree, screen, "com.th3l4b.screens.base.value");
		if (!v) {
			v = "";
		}
		e.onchange = function () {
			onChange(screen, e.value);
		};
		node.appendChild(e);
		e.setAttribute("value", v);	
		return e;
	};

	var renderField = function (screen, node, onChange, context) {
		var e = innerRenderField(screen, node, onChange, context);
		var r2 = {
			input: e,
			setValue: function (field, value, context) {
				field.input.setAttribute("value", value);
			},
			getValue: function (field, context) {
				return field.input.value;
			},
			update: function (field, screen, node, onChange, context) {
				node.innerHTML = "";
				field.input = innerRender(screen, node, onChange, context);
			},
			destroy: function (field, node, context) {
				node.innerHTML = "";
			}
		};
		return r2;
	};

	var innerRenderAction = function (screen, node, onAction, context) {
		var e = context.document.createElement("a");
		e.setAttribute("href", "#");
		e.onclick =  function () {
			onAction(screen);
		};
		var text = context.document.createTextNode("Action");
		e.appendChild(text);
		node.appendChild(e);
		return e;
	};

	var renderAction = function (screen, node, onAction, context) {
		var e = innerRenderAction(screen, node, onAction, context);
		var r2 = {
			link: e,
			update: function (field, screen, node, onAction, context) {
				node.innerHTML = "";
				field.link = innerRenderAction(screen, node, onAction, context);
			},
			destroy: function (field, node) {
				node.innerHTML = "";
			}
		};
		return r2;
	};

	return {
		renderField: renderField,
		renderAction: renderAction
	};

});
