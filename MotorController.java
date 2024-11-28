import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

public class MotorController {

    private EV3LargeRegulatedMotor driveMotor; // Motor for movement
    private EV3LargeRegulatedMotor armMotor;   // Motor for the arm or ball mechanism

    // Speed settings
    private static final int DEFAULT_DRIVE_SPEED = 200; // Default speed for the drive motor
    private static final int DEFAULT_ARM_SPEED = 150;   // Default speed for the arm motor

    // Constructor to initialize motors
    public MotorController(String driveMotorPort, String armMotorPort) {
        this.driveMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(driveMotorPort));
        this.armMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(armMotorPort));

        // Set default speeds for the motors
        this.driveMotor.setSpeed(DEFAULT_DRIVE_SPEED);
        this.armMotor.setSpeed(DEFAULT_ARM_SPEED);
    }

    // Map string port names to EV3 motor ports
    private static Port getMotorPortFromString(String port) {
        switch (port.toUpperCase()) {
            case "A": return lejos.hardware.port.MotorPort.A;
            case "B": return lejos.hardware.port.MotorPort.B;
            case "C": return lejos.hardware.port.MotorPort.C;
            case "D": return lejos.hardware.port.MotorPort.D;
            default: throw new IllegalArgumentException("Invalid motor port: " + port);
        }
    }

    // Control the drive motor by setting speed and direction
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

    // Control the arm motor by setting speed and direction
    public void operateArmMotor(float speed) {
        if (speed > 0) {
            armMotor.setSpeed((int) speed);
            armMotor.forward();
        } else if (speed < 0) {
            armMotor.setSpeed((int) Math.abs(speed));
            armMotor.backward();
        } else {
            armMotor.stop();
        }
    }

    // Stop all motors
    public void stopAllMotors() {
        driveMotor.stop(true);
        armMotor.stop(true);
    }

    // Reset the motors to their default speeds
    public void resetMotorSpeeds() {
        driveMotor.setSpeed(DEFAULT_DRIVE_SPEED);
        armMotor.setSpeed(DEFAULT_ARM_SPEED);
    }
}
