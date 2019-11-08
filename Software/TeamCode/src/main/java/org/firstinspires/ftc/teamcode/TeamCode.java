package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CompassSensor;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
public class TeamCode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    CompassSensor compass;

    //Declaring the hardware
    private DcMotor M_BackLeft = null;
    private DcMotor M_BackRight = null;
    private DcMotor M_FrontLeft = null;
    private DcMotor M_FrontRight = null;
    private DcMotor M_ChainLeft = null;
    private DcMotor M_ChainRight = null;
    private Servo S_Claw = null;
    private Servo S_Tray1 = null;
    private Servo S_Tray2 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Main INIT code goes here

        initMotors();

        //Just Compass Stuff
        compass = hardwareMap.get(CompassSensor.class, "compass");
        compass.setMode(CompassSensor.CompassMode.CALIBRATION_MODE);
        compass.setMode(CompassSensor.CompassMode.MEASUREMENT_MODE);
        if (compass.calibrationFailed()) telemetry.addData("Compass", "Calibration Failed. Try Again!");
        else telemetry.addData("Compass", "Calibration Passed.");
        telemetry.update();


        telemetry.addData("Status", "Init Done");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {//Main Loop


        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

    void initMotors(){
      //Hardware to software mapping
      M_BackLeft = hardwareMap.get(DcMotor.class, "");
      M_BackRight = hardwareMap.get(DcMotor.class, "");
      M_FrontLeft = hardwareMap.get(DcMotor.class, "");
      M_FrontRight = hardwareMap.get(DcMotor.class, "");
      M_ChainLeft = hardwareMap.get(DcMotor.class, "");
      M_ChainRight = hardwareMap.get(DcMotor.class, "");
      S_Claw = hardwareMap.get(Servo.class, "");
      S_Tray1 = hardwareMap.get(Servo.class, "");
      S_Tray2 = = hardwareMap.get(Servo.class, "");
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
    double ServoCheckAngle(double Angle){
      static final double MAX_POS     =  1.0;     // Maximum rotational position
      static final double MIN_POS     =  0.0;     // Minimum rotational position
      if(Angle > MAX_POS){
        Angle = MAX_POS;
      }else if(Angle < MIN_POS){
        Angle = MIN_POS;
      }
      return Angle;
    }
}
