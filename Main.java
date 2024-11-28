import lejos.hardware.port.SensorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialize the MotorController for managing motor operations
        LOGGER.info("Initializing MotorController...");
        RobotMotorController robotMotorController = new RobotMotorController("A", "B","C"); // A for drive, B for arm
        // Initialize the ColorSensorExample
        LOGGER.info("Initializing Color Sensor...");
        ColorSensorExample colorSensor = new ColorSensorExample(SensorPort.S1);

        // Target color to detect
        String targetColor = "Orange";
        LOGGER.info("Checking for target color: {}", targetColor);


        // Detect the target color and pick up the ball if detected
        if (colorSensor.isTargetColor(targetColor)) {
            LOGGER.info("Target color detected: {}", targetColor);


            // Step 1: Open the claw
            LOGGER.info("Opening the claw...");
            robotMotorController.openClaw();
            delay(3000); // Optional delay to ensure claw opens fully

            // Step 2: Lower the arm to pick up the ball
            LOGGER.info("Lowering the arm to pick up the ball...");
            robotMotorController.operateArmMotor(-100); // Lower the arm
            delay(3000);

            // Step 3: Close the claw to secure the ball
            LOGGER.info("Closing the claw...");
            robotMotorController.closeClaw();
            delay(3000);

            // Step 4: Raise the arm back to its starting position
            LOGGER.info("Raising the arm...");
            robotMotorController.operateArmMotor(100); // Raise the arm
            delay(3000);

            // Step 5: Move to the drop-off location (left or right)
            LOGGER.info("Moving to the drop-off location...");
            robotMotorController.turnLeft(); // Turn to face the drop-off location
            delay(500);

            // Step 6: Lower the arm and open the claw to release the ball
            LOGGER.info("Lowering the arm to release the ball...");
            robotMotorController.operateArmMotor(-100); // Lower the arm
            delay(3000);
            LOGGER.info("Opening the claw to release the ball...");
            robotMotorController.openClaw();
            delay(500);

            // Step 7: Raise the arm and return to the original position
            LOGGER.info("Raising the arm...");
            robotMotorController.operateArmMotor(100); // Raise the arm
            delay(3000);

            LOGGER.info("Returning to the starting position...");
            robotMotorController.turnRight(); // Face the original direction
            delay(500);
        } else {
            LOGGER.info("Target color not detected.");
        }

        // Stop all motors
        LOGGER.info("Stopping all motors...");
        robotMotorController.stopAllMotors(); // Unified motor stop control

        // Shutdown process
        LOGGER.info("Shutting down...");
    }

    // Utility method for delays
    private static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.error("Delay interrupted: " + e.getMessage());
        }
    }
}
