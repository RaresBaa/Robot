package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@TeleOp(name="Controlled01", group="Base")
public class Controlled01 extends LinearOpMode {

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
          double drive = -gamepad1.left_stick_y;
          double turn  =  gamepad1.left_stick_x;

          logGamepad(telemetry, gamepad1, "gamepad1");
          logGamepad(telemetry, gamepad2, "gamepad2");

          double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
          double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

          telemetry.addData("Left Power", leftPower);
          telemetry.addData("Right Power", rightPower);
          telemetry.addData("Compass", hardware.compass.getDirection());
          telemetry.addData("Light", hardware.lightSensor.getLightDetected() );

          hardware.M_BackLeft.setPower(leftPower);
          hardware.M_BackRight.setPower(rightPower);
          hardware.M_FrontLeft.setPower(leftPower);
          hardware.M_FrontRight.setPower(rightPower);
          //Control the arm from the joystick
          double armPower = Range.clip(-gamepad1.right_stick_y, -1.0, 1.0) ;

          telemetry.addData("Arm Power", armPower);

          hardware.M_ChainLeft.setPower(armPower);
          hardware.M_ChainRight.setPower(armPower);
          //Controlling the Claw
          if(gamepad1.left_bumper){
              hardware.CloseClaw();
              telemetry.addData("Claw", true);
          }
          if(gamepad1.right_bumper){
              hardware.OpenClaw();
              telemetry.addData("Claw", false);
          }
          //Controlling the Tray servos
          if(gamepad1.dpad_down){
              hardware.UnhookTray();
              telemetry.addData("Tray", false);
          }
          if(gamepad1.dpad_up){
              hardware.HookTray();
              telemetry.addData("Claw", true);
          }
          if(gamepad1.dpad_left){
            hardware.RetractClaw();
            telemetry.addData("Extended", false);
          }
          if(gamepad1.dpad_right){
            hardware.ExtendClaw();
            telemetry.addData("Extended", true);
          }
          telemetry.update();

        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}
