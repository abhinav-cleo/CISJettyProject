package com.cleo.cis.server;

import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {

	public App() {
		packages("com.cleo.cis");
	}
}
