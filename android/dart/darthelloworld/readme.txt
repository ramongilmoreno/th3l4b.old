Simple project that opens a WebView.

Opens a local asset html file whose script is written in Dart.

The contents of assets/ were created from the .html, .dart and .yaml files:

	$ dart2js helloworld.dart -o helloworld.dart.js
	$ pub get
