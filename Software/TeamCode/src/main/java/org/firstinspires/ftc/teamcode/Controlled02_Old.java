package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@TeleOp(name="Controlled02_Old", group="Base")
public class Controlled02_Old extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    private static void logGamepad(Telemetry telemetry, Gamepad gamepad, String prefix) {
        telemetry.addData(prefix + "Synthetic",
                gamepad.getGamepadId() == Gamepad.ID_UNASSOCIATED);
        for (Field field : gamepad.getClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            try {
                telemetry.addData(prefix + field.getName(), field.get(gamepad));
            } catch (IllegalAccessException e) {
                // ignore for now
            }
        }
    }

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

            logGamepad(telemetry, gamepad1, "gamepad1");
            logGamepad(telemetry, gamepad2, "gamepad2");

            double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);

            hardware.M_BackLeft.setPower(leftPower);
            hardware.M_BackRight.setPower(rightPower);
            hardware.M_FrontLeft.setPower(leftPower);
            hardware.M_FrontRight.setPower(rightPower);
            //Control the arm from the joystick
            double armPower = Range.clip(-gamepad1.right_stick_x, -1.0, 1.0) ;

            telemetry.addData("Arm Power", armPower);

            hardware.M_ChainLeft.setPower(armPower);
            hardware.M_ChainRight.setPower(armPower);
            //Controlling the Claw

            if(gamepad1.left_bumper || (gamepad1.left_trigger > 0.5f)){
                if(gamepad1.left_bumper){
                    hardware.S_Claw.setDirection(DcMotor.Direction.FORWARD);
                    telemetry.addData("Claw", true);
                }
                if(gamepad1.left_trigger > 0.5f){
                    hardware.S_Claw.setDirection(DcMotor.Direction.REVERSE);
                    telemetry.addData("Claw", false);
                }
                hardware.S_Claw.setPower(1.0f);
            } else{
                hardware.S_Claw.setPower(0.0f);
            }
            //Controlling the Tray servos
            if(gamepad1.right_bumper || (gamepad1.right_trigger >0.5f)) {
                if (gamepad1.right_bumper) {
                    telemetry.addData("Tray", false);
                    hardware.S_Tray1.setDirection(DcMotorSimple.Direction.FORWARD);
                    hardware.S_Tray2.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                if (gamepad1.right_trigger > 0.5f) {
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
            if(gamepad1.y || gamepad1.b) {
                if (gamepad1.y) {
                    telemetry.addData("Extended", false);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                if (gamepad1.b) {
                    telemetry.addData("Extended", true);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                hardware.S_ClawExtender.setPower(1.0f);
            }else{
                hardware.S_ClawExtender.setPower(0.0f);

            }
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}