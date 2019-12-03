package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Team Code 1", group="Base")
public class TeamCode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private static final float ServoClawClosedPosition = 0.0f;
    private static final float ServoClawOpenedPosition = 1.0f;
    private static final float ServoTrayLeftClosedPosition = 0.0f;
    private static final float ServoTrayLeftOpenedPosition = 1.0f;
    private static final float ServoTrayRightClosedPosition = 0.0f;
    private static final float ServoTrayRightOpenedPosition = 1.0f;
    private static final float ServoClawExtenderClosedPosition = 0.0f;
    private static final float ServoClawExtenderOpenedPosition = 1.0f;

    //Declaring the hardware
    private DcMotor M_BackLeft = null;
    private DcMotor M_BackRight = null;
    private DcMotor M_FrontLeft = null;
    private DcMotor M_FrontRight = null;
    private DcMotor M_ChainLeft = null;
    private DcMotor M_ChainRight = null;
    private Servo S_Claw = null;
    private Servo S_ClawExtender = null;
    private Servo S_Tray1 = null;
    private Servo S_Tray2 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Main INIT code goes here

        initMotors();


        telemetry.addData("Status", "Init Done");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {//Main Loop

          //Control the robot from joystick 1
          //Later for meccano wheel, both joystick will be used.
          double drive = -gamepad1.left_stick_y;
          double turn  =  gamepad1.left_stick_x;
          double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
          double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
          M_BackLeft.setPower(leftPower);
          M_BackRight.setPower(rightPower);
          M_FrontLeft.setPower(leftPower);
          M_FrontRight.setPower(rightPower);
          //Control the arm from the joystick
          double armPower = Range.clip(-gamepad1.right_stick_y, -1.0, 1.0) ;
          M_ChainLeft.setPower(armPower);
          M_ChainRight.setPower(armPower);
          //Controlling the Claw
          if(gamepad1.left_bumper){
              S_Claw.setPosition(ServoClawClosedPosition);
          }
          if(gamepad1.right_bumper){
              S_Claw.setPosition(ServoClawOpenedPosition);
          }
          //Controlling the Tray servos
          if(gamepad1.dpad_down){
              S_Tray1.setPosition(ServoTrayLeftClosedPosition);
              S_Tray2.setPosition(ServoTrayRightClosedPosition);
          }
          if(gamepad1.dpad_up){
              S_Tray1.setPosition(ServoTrayLeftOpenedPosition);
              S_Tray2.setPosition(ServoTrayRightOpenedPosition);
          }
          if(gamepad1.dpad_left){
            S_ClawExtender.setPosition(ServoClawExtenderClosedPosition);
          }
          if(gamepad1.dpad_right){
            S_ClawExtender.setPosition(ServoClawExtenderOpenedPosition);
          }

        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

    private void initMotors(){
      //Hardware to software mapping
      M_BackLeft = hardwareMap.get(DcMotor.class, "");
      M_BackRight = hardwareMap.get(DcMotor.class, "");
      M_FrontLeft = hardwareMap.get(DcMotor.class, "");
      M_FrontRight = hardwareMap.get(DcMotor.class, "");
      M_ChainLeft = hardwareMap.get(DcMotor.class, "");
      M_ChainRight = hardwareMap.get(DcMotor.class, "");
      S_Claw = hardwareMap.get(Servo.class, "");
      S_Tray1 = hardwareMap.get(Servo.class, "");
      S_Tray2 = hardwareMap.get(Servo.class, "");
      S_ClawExtender = hardwareMap.get(Servo.class, "");
      //Reset Motor encoders
      M_BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      M_BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      M_FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      M_FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      M_ChainLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      M_ChainRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      //Enable Motor Encoders
      M_BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      M_BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      M_FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      M_FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      M_ChainLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      M_ChainRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      //Setting the Motor Direction, If needed
      //Example: .setDirection(DcMotor.Direction.FORWARD);
    }

}
