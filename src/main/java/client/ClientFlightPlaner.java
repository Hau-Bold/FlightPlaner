package client;

import javax.swing.SwingUtilities;

import spring.DomainLayerSpringContext;

public class ClientFlightPlaner {

	public static void main(String[] args) {

		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext(args[0]);

		// HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		// obj.getMessage();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				RoutePlanningService routePlaner = springContext.GetRoutePlanningService();
				routePlaner.setDirectory(args[0]);// Todo how to pass as argument?????
				routePlaner.initComponent();
				routePlaner.setVisible(true);
			}
		});

	}

}
