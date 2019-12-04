package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controlled01", group="Base")
public class Controlled01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Main INIT code goes here
        hardware.init(hardwareMap);


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
          double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
          double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
          hardware.M_BackLeft.setPower(leftPower);
          hardware.M_BackRight.setPower(rightPower);
          hardware.M_FrontLeft.setPower(leftPower);
          hardware.M_FrontRight.setPower(rightPower);
          //Control the arm from the joystick
          double armPower = Range.clip(-gamepad1.right_stick_y, -1.0, 1.0) ;
          hardware.M_ChainLeft.setPower(armPower);
          hardware.M_ChainRight.setPower(armPower);
          //Controlling the Claw
          if(gamepad1.left_bumper){
              hardware.CloseClaw();
          }
          if(gamepad1.right_bumper){
              hardware.OpenClaw();
          }
          //Controlling the Tray servos
          if(gamepad1.dpad_down){
              hardware.UnhookTray();
          }
          if(gamepad1.dpad_up){
              hardware.HookTray();
          }
          if(gamepad1.dpad_left){
            hardware.RetractClaw();
          }
          if(gamepad1.dpad_right){
            hardware.ExtendClaw();
          }

        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}
