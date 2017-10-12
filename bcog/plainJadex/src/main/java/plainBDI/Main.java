package plainBDI;

import jadex.base.PlatformConfiguration;
import jadex.base.Starter;

public class Main {
	public static void main(String[] args) {

		//Main class starts BDI agent, go to

		PlatformConfiguration   config  = PlatformConfiguration.getDefaultNoGui();

		config.addComponent("plainBDI.plainBDI.class");
		Starter.createPlatform(config).get();
	}
}
