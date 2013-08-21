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
			return false;
		};
		r.assertTrue =  function (expected, msg) {
			if (!msg) {
				msg = "is not true";
			}
			if (expected == true) {
				return true;
			} else {
				return r.log(msg, expected);
			}
		};
		r.assertFalse =  function (expected, msg) {
			if (!msg) {
				msg = "is not false";
			}
			if (expected == false) {
				return true;
			} else {
				return r.log(msg, expected);
			}
		};
		r.assertEquals =  function (expected, actual, msg) {
			if (!msg) {
				msg = "are not equals";
			}
			if (expected == actual) {
				return true;
			} else {
				return r.log(msg, expected, actual);
			}
		};
		r.assertDeepEquals = function (expected, actual, msg) {
			if (!msg) {
				msg = "objects are not equals";
			}
			if ((typeof expected == typeof actual) && (typeof expected == 'object')) {
				for (var i in expected) {
//					console.log("Testing property", i, "of", actual)
					if (expected.hasOwnProperty(i)) {
						if (!r.assertDeepEquals(expected[i], actual[i], msg)) {
							return false;
						}
					}
				}
				return true;
			} else {
				return r.assertEquals(expected, actual, msg);
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
							return r.log(msg, expected, actual);
						}
					}
					return true;
				}
			}
			return r.log(msg, expected, actual);
		};
		return r;
	};
});
