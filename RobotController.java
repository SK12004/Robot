/**import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class RobotController {

    // Motors for the robot
    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;
    private EV3LargeRegulatedMotor armMotor;

    // Movement constants
    private static final int MOVE_SPEED = 200;  // Degrees per second for movement
    private static final int TURN_SPEED = 100; // Degrees per second for turning
    private static final int ARM_SPEED = 150;  // Degrees per second for the arm

    // Constructor
    public RobotController(String leftMotorPort, String rightMotorPort, String armMotorPort) {
        // Initialize motors using the given motor port strings (ev3dev MotorPort)
        leftMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(leftMotorPort));
        rightMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(rightMotorPort));
        armMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(armMotorPort));

        // Set default speeds for all motors
        leftMotor.setSpeed(MOVE_SPEED);
        rightMotor.setSpeed(MOVE_SPEED);
        armMotor.setSpeed(ARM_SPEED);
    }

    // Method to map motor port strings to ev3dev MotorPort constants
    private static Port getMotorPortFromString(String port) {
        switch (port) {
            case "A":
                return MotorPort.A;
            case "B":
                return MotorPort.B;
            case "C":
                return MotorPort.C;
            case "D":
                return MotorPort.D;
            default:
                throw new IllegalArgumentException("Invalid motor port: " + port);
        }
    }

    // Move the robot forward
    public void moveForward() {
        leftMotor.forward();
        rightMotor.forward();
    }

    // Move the robot backward
    public void moveBackward() {
        leftMotor.backward();
        rightMotor.backward();
    }

    // Turn the robot left
    public void turnLeft() {
        leftMotor.setSpeed(TURN_SPEED);
        rightMotor.setSpeed(TURN_SPEED);

        leftMotor.backward();
        rightMotor.forward();

        // Delay for a quarter turn (adjust based on testing)
        Delay.msDelay(500);

        stop(); // Stop after the turn
        resetSpeed();
    }

    // Turn the robot right
    public void turnRight() {
        leftMotor.setSpeed(TURN_SPEED);
        rightMotor.setSpeed(TURN_SPEED);

        leftMotor.forward();
        rightMotor.backward();

        // Delay for a quarter turn (adjust based on testing)
        Delay.msDelay(500);

        stop(); // Stop after the turn
        resetSpeed();
    }

    // Stop the robot
    public void stop() {
        leftMotor.stop(true);
        rightMotor.stop(true);
    }

    // Pick a ball (using the arm motor)
    public void pickBall() {
        armMotor.rotate(90);  // Adjust angle as per arm design
        armMotor.rotate(-90);
    }

    // Deposit a ball (optional, if depositing is required)
    public void depositBall() {
        armMotor.rotate(-90); // Adjust angle for depositing
        armMotor.rotate(90);
    }

    // Reset motors to default speeds
    private void resetSpeed() {
        leftMotor.setSpeed(MOVE_SPEED);
        rightMotor.setSpeed(MOVE_SPEED);
    }
}**/

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class RobotController {

    // Motors for the robot
    private EV3LargeRegulatedMotor driveMotor;  // Single motor for movement
    private EV3LargeRegulatedMotor armMotor;    // Motor for the arm mechanism

    // Movement constants
    private static final int DRIVE_SPEED = 200;  // Speed for moving straight
    private static final int TURN_SPEED = 150;   // Speed for turning
    private static final int ARM_SPEED = 150;    // Speed for arm operations

    // Constructor
    public RobotController(String driveMotorPort, String armMotorPort) {
        // Initialize motors using the provided motor ports
        driveMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(driveMotorPort));
        armMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(armMotorPort));

        // Set default speeds
        driveMotor.setSpeed(DRIVE_SPEED);
        armMotor.setSpeed(ARM_SPEED);
    }

    // Map motor port strings to ev3dev MotorPort constants
    private static Port getMotorPortFromString(String port) {
        switch (port) {
            case "A":
                return MotorPort.A;
            case "B":
                return MotorPort.B;
            case "C":
                return MotorPort.C;
            case "D":
                return MotorPort.D;
            default:
                throw new IllegalArgumentException("Invalid motor port: " + port);
        }
    }
    /**public void controlWheelMotor(float speed) {
        if (speed > 0) {
            driveMotor.setSpeed((int) speed);
            driveMotor.forward();
        } else if (speed < 0) {
            driveMotor.setSpeed((int) Math.abs(speed));
            driveMotor.backward();
        } else {
            driveMotor.stop();
        }
    }**/

    // Move forward
    public void moveForward() {
        driveMotor.forward();
    }

    // Move backward
    public void moveBackward() {
        driveMotor.backward();
    }

    // Turn left
    public void turnLeft() {
        driveMotor.setSpeed(TURN_SPEED);  // Reduce speed for turning
        driveMotor.rotate(-90);          // Rotate motor to simulate left turn
        resetSpeed();
    }

    // Turn right
    public void turnRight() {
        driveMotor.setSpeed(TURN_SPEED);  // Reduce speed for turning
        driveMotor.rotate(90);           // Rotate motor to simulate right turn
        resetSpeed();
    }

    // Stop the robot
    public void stop() {
        driveMotor.stop(true);  // Stop the motor
    }

    // Pick up a ball (using the arm motor)
    public void pickBall() {
        armMotor.rotate(90);  // Adjust angle for picking up a ball
        Delay.msDelay(500);   // Optional delay to ensure action completes
        armMotor.rotate(-90); // Return to the starting position
    }

    // Deposit a ball (optional, if needed)
    public void depositBall() {
        armMotor.rotate(-90); // Adjust angle for depositing a ball
        Delay.msDelay(500);   // Optional delay to ensure action completes
        armMotor.rotate(90);  // Return to the starting position
    }

    // Reset motor to default speed
    private void resetSpeed() {
        driveMotor.setSpeed(DRIVE_SPEED);
    }
}
