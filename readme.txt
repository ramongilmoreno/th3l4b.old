Quick start guide:
------------------

Set these environment variables:

	export MAVEN_HOME=/home/rgil/Ramon/aplicaciones/apache-maven-3.0.4
	export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
	export ANT_HOME=/home/rgil/Ramon/aplicaciones/apache-ant-1.8.4
	export JAVA_HOME=/home/rgil/Ramon/aplicaciones/jdk1.7.0_11
	export PHANTOMJS_HOME=/home/rgil/Ramon/aplicaciones/phantomjs-1.9.0-linux-x86_64
	export ANDROID_HOME=/home/rgil/Ramon/aplicaciones/adt-bundle-linux-x86_64-20130917/sdk
	export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME/bin:$MAVEN_HOME/bin:$ANT_HOME/bin:$PHANTOMJS_HOME/bin:$PATH

Ensure these API levels are found in Android SDK:

	4.3 (API 18)
	2.2 (API 8)

Start the web application with:

	$ cd th3l4b/screens/testbed
	$ ./jetty

Open browser to:

	http://127.0.0.1:8080

To deploy Android application to (running) emulator:

	$ mvn android:deploy -pl com.th3l4b.android:testbed


Setup of development environment:
---------------------------------

On this directory, create the workspace for Maven:

	$ mvn eclipse:configure-workspace -Declipse.workspace=workspace

	http://maven.apache.org/plugins/maven-eclipse-plugin/configure-workspace-mojo.html

Generate the Eclipse projects (.project and .classpath):

	$ mvn eclipse:eclipse

Open Eclipse and import all projects.
