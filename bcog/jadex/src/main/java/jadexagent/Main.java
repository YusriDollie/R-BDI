package jadexagent;

import jadex.base.PlatformConfiguration;
import jadex.base.Starter;

public class Main {
	public static void main(String[] args) {

		//Main Class starts BDI agent go to HelloAgentBDI.java for agent code

		PlatformConfiguration   config  = PlatformConfiguration.getDefaultNoGui();

		config.addComponent("jadexagent.HelloAgentBDI.class");
		Starter.createPlatform(config).get();
	}
}
