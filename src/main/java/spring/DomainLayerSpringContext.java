package spring;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import client.RoutePlanningService;

public class DomainLayerSpringContext {

	String myDirectory;
	private static DomainLayerSpringContext myInstance;

	private static ApplicationContext myApplicationContext;

	private DomainLayerSpringContext(String directory) {

		String pathToRessource = directory + File.separator + "Config" + File.separator + "Beans.xml";
		myApplicationContext = new FileSystemXmlApplicationContext(pathToRessource);
	}

	private static DomainLayerSpringContext GetInstance(String directory) {
		if (myInstance == null) {
			myInstance = new DomainLayerSpringContext(directory);
		}

		return myInstance;
	}

	public static DomainLayerSpringContext GetContext(String directory) {

		DomainLayerSpringContext context = GetInstance(directory);

		return context;
	}

	public RoutePlanningService GetRoutePlanningService() {
		return (RoutePlanningService) myApplicationContext.getBean("RoutePlanningService");
	}

}
