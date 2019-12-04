package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controlled01", group="Base")
public class Controlled01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    private static final float ServoClawClosedPosition = 0.0f;
    private static final float ServoClawOpenedPosition = 1.0f;
    private static final float ServoTrayLeftClosedPosition = 0.0f;
    private static final float ServoTrayLeftOpenedPosition = 1.0f;
    private static final float ServoTrayRightClosedPosition = 0.0f;
    private static final float ServoTrayRightOpenedPosition = 1.0f;
    private static final float ServoClawExtenderClosedPosition = 0.0f;
    private static final float ServoClawExtenderOpenedPosition = 1.0f;

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
              hardware.S_Claw.setPosition(ServoClawClosedPosition);
          }
          if(gamepad1.right_bumper){
              hardware.S_Claw.setPosition(ServoClawOpenedPosition);
          }
          //Controlling the Tray servos
          if(gamepad1.dpad_down){
              hardware.S_Tray1.setPosition(ServoTrayLeftClosedPosition);
              hardware.S_Tray2.setPosition(ServoTrayRightClosedPosition);
          }
          if(gamepad1.dpad_up){
              hardware.S_Tray1.setPosition(ServoTrayLeftOpenedPosition);
              hardware.S_Tray2.setPosition(ServoTrayRightOpenedPosition);
          }
          if(gamepad1.dpad_left){
            hardware.S_ClawExtender.setPosition(ServoClawExtenderClosedPosition);
          }
          if(gamepad1.dpad_right){
            hardware.S_ClawExtender.setPosition(ServoClawExtenderOpenedPosition);
          }

        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}
