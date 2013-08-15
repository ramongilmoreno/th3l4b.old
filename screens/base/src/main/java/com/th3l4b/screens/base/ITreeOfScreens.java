package com.th3l4b.screens.base;


public interface ITreeOfScreens {
	String getRoot() throws Exception;

	void setRoot(String root) throws Exception;

	Iterable<String> screens() throws Exception;

	Iterable<String> children(String screen) throws Exception;

	String parent(String screen) throws Exception;

	void addScreen(String screen, String parent) throws Exception;

	void removeScreen(String screen) throws Exception;

	void setProperty(String screen, String property, String value)
			throws Exception;

	String getProperty(String screen, String property) throws Exception;

	void removeProperty(String screen, String property) throws Exception;

	boolean hasProperty(String screen, String property) throws Exception;

	Iterable<String> properties(String screen) throws Exception;
}
