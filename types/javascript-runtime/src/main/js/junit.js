/*global define */

define('com/th3l4b/types/javascript-runtime-junit', function () {
	return function () {
		var r = {};
		r.log = function (msg, expected, actual, c) {
			msg = "junit: " + msg;
			if (expected != undefined) {
				msg += " - expected: " + expected;
			}
			if (actual != undefined) {
				msg += " - actual " + actual;
			}
			if (c != undefined) {
				msg += " - " + c;
			}
			console.error(msg);
		};
		r.assertTrue =  function (expected, msg) {
			if (!msg) {
				msg = "is not true";
			}
			if (expected == true) {
				return;
			} else {
				r.log(msg, expected);
			}
		};
		r.assertFalse =  function (expected, msg) {
			if (!msg) {
				msg = "is not false";
			}
			if (expected == false) {
				return;
			} else {
				r.log(msg, expected);
			}
		};
		r.assertEquals =  function (expected, actual, msg) {
			if (!msg) {
				msg = "are not equals";
			}
			if (expected == actual) {
				return;
			} else {
				r.log(msg, expected, actual);
			}
		};
		r.assertArraysEquals = function (expected, actual, msg) {
			if (!msg) {
				msg = "arrays are not equals";
			}
			if (Array.isArray(expected) && Array.isArray(actual)) {
				if (expected.length == actual.length) {
					for (var i = 0; i < expected.length; i++) {
						if (expected[i] != actual[i]) {
							r.log(msg, expected, actual);
							return;
						}
					}
					return;
				}
			}
			r.log(msg, expected, actual);
		};
		return r;
	};
});
