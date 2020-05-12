package spring;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import client.FlightPlaner;
import routePlanningService.Impl.OptimizationService;

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

	public static DomainLayerSpringContext GetContext() {
		if (myInstance == null) {
			throw new IllegalAccessError("Not able to lauch spring context, please use constructor with argument");
		}

		return myInstance;
	}

	public static DomainLayerSpringContext GetContext(String directory) {

		DomainLayerSpringContext context = GetInstance(directory);

		return context;
	}

	public FlightPlaner GetFlightPlaner() {
		return (FlightPlaner) myApplicationContext.getBean("FlightPlaner");
	}

	public OptimizationService GetOptimizationService() {
		return (OptimizationService) myApplicationContext.getBean("OptimizationService");
	}
}
