import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

public class FieldNavigator {

    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;

    private static final int MOVE_SPEED = 200; // Speed for moving straight
    private static final int TURN_SPEED = 100; // Speed for turning
    private static final int TILE_LENGTH = 500; // Adjust based on field size and motor calibration

    // Constructor
    public FieldNavigator(Port leftMotorPort, Port rightMotorPort) {
        leftMotor = new EV3LargeRegulatedMotor(leftMotorPort);
        rightMotor = new EV3LargeRegulatedMotor(rightMotorPort);

        // Set default motor speeds
        leftMotor.setSpeed(MOVE_SPEED);
        rightMotor.setSpeed(MOVE_SPEED);
    }

    // Move forward by one tile
    public void moveForward() {
        leftMotor.rotate(convertDistance(TILE_LENGTH), true);
        rightMotor.rotate(convertDistance(TILE_LENGTH), false);
    }

    // Turn left (90 degrees)
    public void turnLeft() {
        leftMotor.setSpeed(TURN_SPEED);
        rightMotor.setSpeed(TURN_SPEED);
        leftMotor.rotate(-convertAngle(90), true);
        rightMotor.rotate(convertAngle(90), false);
        resetSpeed();
    }

    // Turn right (90 degrees)
    public void turnRight() {
        leftMotor.setSpeed(TURN_SPEED);
        rightMotor.setSpeed(TURN_SPEED);
        leftMotor.rotate(convertAngle(90), false);
        rightMotor.rotate(-convertAngle(90), true);
        resetSpeed();
    }

    // Zigzag navigation for full field coverage
    public void zigzagTraverse(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 1; col++) {
                moveForward();
            }

            if (row < rows - 1) {
                // At the end of each row, turn appropriately
                if (row % 2 == 0) {
                    turnRight();
                    moveForward();
                    turnRight();
                } else {
                    turnLeft();
                    moveForward();
                    turnLeft();
                }
            }
        }

        // At the end, return to the original orientation
        if (rows % 2 == 0) {
            turnRight();
        }
    }

    // Stop the motors
    public void stop() {
        leftMotor.stop(true);
        rightMotor.stop(true);
    }

    // Reset motor speeds to default
    private void resetSpeed() {
        leftMotor.setSpeed(MOVE_SPEED);
        rightMotor.setSpeed(MOVE_SPEED);
    }

    // Convert distance (cm) to motor rotation degrees
    private int convertDistance(double distance) {
        final double WHEEL_DIAMETER = 5.6; // Diameter in cm, adjust based on your robot
        return (int) ((180.0 * distance) / (Math.PI * WHEEL_DIAMETER));
    }

    // Convert angle to motor rotation degrees
    private int convertAngle(double angle) {
        final double ROBOT_TRACK = 12.0; // Distance between the wheels in cm, adjust based on your robot
        return convertDistance(Math.PI * ROBOT_TRACK * angle / 360.0);
    }
}
