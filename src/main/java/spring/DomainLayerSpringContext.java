package spring;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import routePlanning.Constants.Constants;
import routePlanning.Contract.IOpenStreetMapService;
import routePlanning.Contract.IOptimizationService;
import widgets.flightsOverview.FlightsOverview;

@Configuration
public class DomainLayerSpringContext {

	String myDirectory;
	private static DomainLayerSpringContext myInstance;

	private static ApplicationContext myApplicationContext;

	private DomainLayerSpringContext(String directory) {

		String configLocation = directory + File.separator + Constants.CONFIG + File.separator + "Beans.xml";
		myApplicationContext = new FileSystemXmlApplicationContext(configLocation);
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

	public IOptimizationService GetOptimizationService() {
		return (IOptimizationService) myApplicationContext.getBean("OptimizationService");
	}

	public FlightsOverview GetFlightsOverview() {
		return (FlightsOverview) myApplicationContext.getBean("FlightsOverview");
	}

	public IOpenStreetMapService GetOpenStreetMapService() {
		return (IOpenStreetMapService) myApplicationContext.getBean("OpenStreetMapService");
	}
}
