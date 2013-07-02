/*global define */

define('com/th3l4b/types/javascript-runtime', function () {
	var r = {
		toString: function (v) {
			if (!v) {
				return "";
			} else {
				return "" + v;
			}
		},
		fromString: function (s) {
			if (!s) {
				return null;
			} else {
				return s;
			}
		}
	};
	return {
		"integer": r,
		"decimal": r,
		"boolean": r,
		"date": r,
		"timestamp": r,
		"label": r,
		"string": r,
		"text": r
	};
});
