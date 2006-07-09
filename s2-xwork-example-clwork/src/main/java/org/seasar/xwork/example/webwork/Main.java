package org.seasar.xwork.example.webwork;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.xwork.clwork.CommandLineDispatcher;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SingletonS2ContainerFactory.init();
		CommandLineDispatcher dispatcher = new CommandLineDispatcher();
		
		dispatcher.dispatch("add",args);
	}

}
