
import lejos.hardware.port.Port;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorSensorExample {
    public static Logger LOGGER = LoggerFactory.getLogger(ColorSensorExample.class);

    private EV3ColorSensor colorSensor;
    private SampleProvider colorProvider;
    private float[] sample;

    // Constructor to initialize the color sensor
    public ColorSensorExample(Port sensorPort) {
        colorSensor = new EV3ColorSensor(sensorPort);
        colorProvider = colorSensor.getColorIDMode();
        sample = new float[colorProvider.sampleSize()];
    }

    // Get the current color ID
    public int getColorID() {
        colorProvider.fetchSample(sample, 0);
        LOGGER.info("Raw sample value from color sensor: " + (int)sample[0]); // Log the raw value
        System.out.println("Raw sample value: " + (int)sample[0]);
        return (int) sample[0];
    }

    // Method to interpret color ID as a string
    public String getColorName() {
        int colorID = getColorID();
        switch (colorID) {
            case 0:
                return "Red";
            case 1:
                return "Green";
            case 2:
                return "Blue";
            case 3:
                return "Yellow";
            case 5:
                return "Orange";
            case 6:
                return "White";
            case 7:
                return "Black";
            case 8:
                return "Pink";
            default:
                return "Unknown";
        }
    }
    // Method to check if the detected color matches the target color
    public boolean isTargetColor(String color) {
        String detectedColor = getColorName();
        System.out.println("Detected Color: " + detectedColor + ", Target Color: " + color);
        return detectedColor.equalsIgnoreCase(color);
    }

    // Method to print the detected color
    public void printColor() {
        System.out.println("Detected Color: " + getColorName());
    }

    // No explicit close method needed
    public void releaseResources() {
        System.out.println("Sensor resources will be released automatically upon program exit.");
    }
}
