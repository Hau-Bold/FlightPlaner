package com;

import routePlanning.Contract.IOpenStreetMapService;
import spring.DomainLayerSpringContext;

public class JSTransferService {

	private static IOpenStreetMapService myOpenStreetMapService;

	public JSTransferService() {

		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myOpenStreetMapService = springContext.GetOpenStreetMapService();
	}

	public static void request(String location) {
		// GPSCoordinate gps = myOpenStreetMapService.getCoordinates(location);

		System.out.println("fuck u");
	}

}
