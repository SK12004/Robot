import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class RobotMotorController {

    private EV3LargeRegulatedMotor driveMotor; // Motor for movement
    private EV3LargeRegulatedMotor armMotor;   // Motor for the arm or ball mechanism
    private EV3MediumRegulatedMotor clawMotor;  // Motor for the claw mechanism

    // Speed settings
    private static final int DEFAULT_DRIVE_SPEED = 200; // Default drive motor speed
    private static final int DEFAULT_ARM_SPEED = 150;   // Default arm motor speed
    private static final int DEFAULT_CLAW_SPEED = 100;  // Default claw motor speed
    private static final int TURN_SPEED = 150;          // Speed for turning

    // Constructor to initialize motors
    public RobotMotorController(String driveMotorPort, String armMotorPort, String clawMotorPort) {
        this.driveMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(driveMotorPort));
        this.armMotor = new EV3LargeRegulatedMotor(getMotorPortFromString(armMotorPort));
        this.clawMotor = new EV3MediumRegulatedMotor(getMotorPortFromString(clawMotorPort));

        // Set default speeds
        this.driveMotor.setSpeed(DEFAULT_DRIVE_SPEED);
        this.armMotor.setSpeed(DEFAULT_ARM_SPEED);
        this.clawMotor.setSpeed(DEFAULT_CLAW_SPEED);
    }

    // Map string port names to EV3 motor ports
    private static Port getMotorPortFromString(String port) {
        switch (port.toUpperCase()) {
            case "A": return MotorPort.A;
            case "B": return MotorPort.B;
            case "C": return MotorPort.C;
            case "D": return MotorPort.D;
            default: throw new IllegalArgumentException("Invalid motor port: " + port);
        }
    }

    // ---- Drive Motor Controls ----

    public void moveForward() {
        driveMotor.forward();
    }

    public void moveBackward() {
        driveMotor.backward();
    }

    public void stopDriveMotor() {
        driveMotor.stop(true);
    }

    public void turnLeft() {
        driveMotor.setSpeed(TURN_SPEED);
        driveMotor.rotate(-90); // Simulate turning left
        resetDriveMotorSpeed();
    }

    public void turnRight() {
        driveMotor.setSpeed(TURN_SPEED);
        driveMotor.rotate(90); // Simulate turning right
        resetDriveMotorSpeed();
    }

    public void controlDriveMotor(float speed) {
        if (speed > 0) {
            driveMotor.setSpeed((int) speed);
            driveMotor.forward();
        } else if (speed < 0) {
            driveMotor.setSpeed((int) Math.abs(speed));
            driveMotor.backward();
        } else {
            stopDriveMotor();
        }
    }
    public void controlWheelMotor(float speed) {
        if (speed > 0) {
            driveMotor.setSpeed((int) speed);
            driveMotor.forward();
        } else if (speed < 0) {
            driveMotor.setSpeed((int) Math.abs(speed));
            driveMotor.backward();
        } else {
            driveMotor.stop();
        }
    }

    private void resetDriveMotorSpeed() {
        driveMotor.setSpeed(DEFAULT_DRIVE_SPEED);
    }

    // ---- Arm Motor Controls ----

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

    public void pickBall() {
        armMotor.rotate(180);   // Adjust angle for picking up a ball
        Delay.msDelay(1500);    // Optional delay to ensure action completes
        // =`   x armMotor.rotate(-250);  // Return to the starting position
    }

    public void depositBall() {
        armMotor.rotate(200);  // Adjust angle for depositing a ball
        Delay.msDelay(500);    // Optional delay to ensure action completes
        armMotor.rotate(-250);   // Return to the starting position
    }

    public void stopArmMotor() {
        armMotor.stop(true);
    }

    private void resetArmMotorSpeed() {
        armMotor.setSpeed(DEFAULT_ARM_SPEED);
    }

    // ---- Claw Motor Controls ----

    public void openClaw() {
        clawMotor.rotateTo(45, true);  // Adjust rotation to open the claw;
        Delay.msDelay(300);    // Delay to ensure action completes
    }

    public void closeClaw() {
        clawMotor.rotateTo(-45, true); // Adjust rotation to close the claw
        Delay.msDelay(300);    // Delay to ensure action completes
    }

    public void operateClawMotor(float speed) {
        if (speed > 0) {
            clawMotor.setSpeed((int) speed);
            clawMotor.forward();
        } else if (speed < 0) {
            clawMotor.setSpeed((int) Math.abs(speed));
            clawMotor.backward();
        } else {
            clawMotor.stop();
        }
    }

    public void stopClawMotor() {
        clawMotor.stop(true);
    }

    private void resetClawMotorSpeed() {
        clawMotor.setSpeed(DEFAULT_CLAW_SPEED);
    }

    // ---- General Controls ----

    public void stopAllMotors() {
        driveMotor.stop(true);
        armMotor.stop(true);
        clawMotor.stop(true);
    }

    public void resetAllMotorSpeeds() {
        resetDriveMotorSpeed();
        resetArmMotorSpeed();
        resetClawMotorSpeed();
    }
}


