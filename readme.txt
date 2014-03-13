Quick start guide:
------------------

This project uses symbolic links in the repository. Windows platforms support is only partial. Linux preferred.

Set these environment variables:

	export MAVEN_HOME=/home/rgil/Ramon/aplicaciones/apache-maven-3.0.4
	export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
	export ANT_HOME=/home/rgil/Ramon/aplicaciones/apache-ant-1.8.4
	export JAVA_HOME=/home/rgil/Ramon/aplicaciones/jdk1.7.0_11
	# 
	# This one is not needed for the moment 
	# export PHANTOMJS_HOME=/home/rgil/Ramon/aplicaciones/phantomjs-1.9.0-linux-x86_64
	#
	export ANDROID_HOME=/home/rgil/Ramon/aplicaciones/adt-bundle-linux-x86_64-20130917/sdk
	# export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME/bin:$MAVEN_HOME/bin:$ANT_HOME/bin:$PHANTOMJS_HOME/bin:$PATH
	export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME/bin:$MAVEN_HOME/bin:$ANT_HOME/bin:$PATH

Ensure these API levels are found in Android SDK:

	4.3 (API 18)
	2.2 (API 8)

Sample 1: build an .srm file from scratch (no ANDROID_HOME needed) to inspect products:

	$ mvn install -pl com.th3l4b.apps.shopping:base -am


Sample 2: start the web application with (symlinks are used in this project):

	$ mvn install -pl com.th3l4b.testbed:screens -am
	$ cd testbed/screens
	$ ./jetty

Open browser to:

	http://127.0.0.1:8080


Sample 3: to deploy Android application to a (running) emulator:

	$ mvn android:deploy -pl com.th3l4b.testbed:android


Setup of development environment:
---------------------------------

On this directory, create the workspace for Maven:

	$ mvn eclipse:configure-workspace -Declipse.workspace=workspace

	http://maven.apache.org/plugins/maven-eclipse-plugin/configure-workspace-mojo.html

Generate the Eclipse projects (.project and .classpath):

	$ mvn eclipse:eclipse

Open Eclipse and import all projects.
