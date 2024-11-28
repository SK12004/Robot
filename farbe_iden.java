import lejos.hardware.port.SensorPort;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class farbe_iden {

    public static void main(String[] args) {

        // Initialize the EV3 Color Sensor on port S1
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

        // Get a sample provider for color ID mode
        SampleProvider colorProvider = colorSensor.getColorIDMode();
        float[] sample = new float[colorProvider.sampleSize()];

        try {
            while (true) {
                // Fetch color sample
                colorProvider.fetchSample(sample, 0);
                int colorID = (int) sample[0];

                // Print detected color ID
                switch (colorID) {
                    case 0:
                        System.out.println("Detected Color: Red");
                        break;
                    case 1:
                        System.out.println("Detected Color: Green");
                        break;
                    case 2:
                        System.out.println("Detected Color: Blue");
                        break;
                    case 3:
                        System.out.println("Detected Color: Yellow");
                        break;
                    case 5:
                        System.out.println("Detected Color: Orange");
                        break;
                    case 6:
                        System.out.println("Detected Color: White");
                        break;
                    case 7:
                        System.out.println("Detected Color: Black");
                        break;
                    case 8:
                        System.out.println("Detected Color: Pink");
                        break;
                    default:
                        System.out.println("Unknown Color");
                        break;
                }

                // Sleep for a short time to avoid busy-waiting
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Program terminated. Resources will be released automatically.");
            // No explicit close required for EV3ColorSensor
        }
    }
}
