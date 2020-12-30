package view.Contract;

import java.io.IOException;
import java.util.ArrayList;

import routePlanning.Impl.GPSCoordinate;

public interface IHtmlExecutor {
	void write(ArrayList<GPSCoordinate> master) throws IOException;
}
