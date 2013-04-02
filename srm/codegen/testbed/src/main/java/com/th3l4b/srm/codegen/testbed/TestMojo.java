package com.th3l4b.srm.codegen.testbed;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;

import com.th3l4b.srm.codegen.mojo.AllMojo;

public class TestMojo {
	public static void main(String[] args) throws MojoExecutionException {
		int i = 0;
		String input = args[i++];
		String pkg = args[i++];
		String output = args[i++];
		AllMojo mojo = new AllMojo();
		mojo.setInput(new File(input));
		mojo.setPackage(pkg);
		mojo.setOutput(new File(output));
		mojo.setOverwrite(true);
		mojo.execute();
	}
}
