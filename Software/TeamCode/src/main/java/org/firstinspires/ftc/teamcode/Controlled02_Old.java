package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Controlled02_Old", group="Base")
public class Controlled02_Old extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Main INIT code goes here
        hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {//Main Loop

            //Control the robot from joystick 1
            //Later for mecanum wheels, both joysticks will be used.
            double drive = -gamepad1.left_stick_x;
            double turn  =  gamepad1.left_stick_y;
            telemetry.addData("Motor Power", drive);
            telemetry.addData("Motor Turn", turn);

            double drive_fine = -gamepad2.left_stick_x / Configuration.joystickFINEsensitivity;
            double turn_fine  =  gamepad2.left_stick_y / Configuration.joystickFINEsensitivity;
            telemetry.addData("Motor Power-Fine", drive_fine);
            telemetry.addData("Motor Turn-Fine", turn_fine);

            double leftPower    = Range.clip(drive + turn + drive_fine + turn_fine, -1.0, 1.0) ;
            double rightPower   = Range.clip(drive - turn + drive_fine - turn_fine, -1.0, 1.0) ;

            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);

            hardware.M_BackLeft.setPower(leftPower);
            hardware.M_BackRight.setPower(rightPower);
            hardware.M_FrontLeft.setPower(leftPower);
            hardware.M_FrontRight.setPower(rightPower);
            //Control the arm from the joystick
            double armPower = Range.clip(-gamepad2.right_stick_x, -1.0, 1.0) ;

            telemetry.addData("Arm Power", armPower);

            hardware.M_ChainLeft.setPower(armPower);
            hardware.M_ChainRight.setPower(armPower);

            //Controlling the Claw
            if((gamepad2.right_trigger > 0.5f) || (gamepad2.left_trigger > 0.5f)){
                if((gamepad2.right_trigger > 0.5f)){
                    hardware.S_Claw.setDirection(DcMotor.Direction.FORWARD);
                    telemetry.addData("Claw", true);
                }
                if(gamepad2.left_trigger > 0.5f){
                    hardware.S_Claw.setDirection(DcMotor.Direction.REVERSE);
                    telemetry.addData("Claw", false);
                }
                hardware.S_Claw.setPower(1.0f);
            } else{
                hardware.S_Claw.setPower(0.0f);
            }
            //Controlling the Tray servos
            if(gamepad2.right_bumper || gamepad2.left_bumper) {
                if (gamepad2.left_bumper) {
                    telemetry.addData("Tray", false);
                    hardware.S_Tray1.setDirection(DcMotorSimple.Direction.FORWARD);
                    hardware.S_Tray2.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                if (gamepad2.right_bumper) {
                    telemetry.addData("Tray", true);
                    hardware.S_Tray1.setDirection(DcMotorSimple.Direction.REVERSE);
                    hardware.S_Tray2.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                hardware.S_Tray1.setPower(1.0f);
                hardware.S_Tray2.setPower(1.0f);
            }else{
                hardware.S_Tray1.setPower(0.0f);
                hardware.S_Tray2.setPower(0.0f);
            }
            //Claw extender
            if(gamepad2.a || gamepad2.b) {
                if (gamepad2.a) {
                    telemetry.addData("Extended", false);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                if (gamepad2.b) {
                    telemetry.addData("Extended", true);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                hardware.S_ClawExtender.setPower(1.0f);
            }else{
                hardware.S_ClawExtender.setPower(0.0f);

            }
            telemetry.addData("Distance_Sensor", hardware.HeightSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("Light-alpha", hardware.ColorSensor.alpha());
            telemetry.addData("Light-Red", hardware.ColorSensor.red());
            telemetry.addData("Light-Green", hardware.ColorSensor.green());
            telemetry.addData("Light-Blue", hardware.ColorSensor.blue());
            telemetry.addData("Motor Distance-BL", hardware.M_BackLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BackRight.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FrontLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FrontRight.getCurrentPosition());
            telemetry.addData("Motor Distance-CL", hardware.M_ChainLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-CR", hardware.M_ChainRight.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}