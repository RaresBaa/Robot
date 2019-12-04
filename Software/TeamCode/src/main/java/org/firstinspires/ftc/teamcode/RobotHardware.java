package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class RobotHardware {

    private static final float ServoClawClosedPosition = 0.0f;
    private static final float ServoClawOpenedPosition = 1.0f;
    private static final float ServoTrayLeftClosedPosition = 0.0f;
    private static final float ServoTrayLeftOpenedPosition = 1.0f;
    private static final float ServoTrayRightClosedPosition = 0.0f;
    private static final float ServoTrayRightOpenedPosition = 1.0f;
    private static final float ServoClawExtenderClosedPosition = 0.0f;
    private static final float ServoClawExtenderOpenedPosition = 1.0f;

    //for the light sensor
    static final double     WHITE_THRESHOLD = 0.2;  // spans between 0.1 - 0.5 from dark to light
    static final double     APPROACH_SPEED  = 0.5;



    DcMotor M_BackLeft = null;
    DcMotor M_BackRight = null;
    DcMotor M_FrontLeft = null;
    DcMotor M_FrontRight = null;
    DcMotor M_ChainLeft = null;
    DcMotor M_ChainRight = null;
    CompassSensor compass = null;
    LightSensor lightSensor = null;
    WebcamName webcamName = null;
    private Servo S_Claw = null;
    private Servo S_ClawExtender = null;
    private Servo S_Tray1 = null;
    private Servo S_Tray2 = null;
    private static final float mmPerInch        = 25.4f;
    final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

    /* Constructor */
    RobotHardware(){

    }
    void init(HardwareMap hwMap){
        //Hardware to software mapping
        webcamName = hwMap.get(WebcamName.class, "");
        lightSensor = hwMap.lightSensor.get("");
        compass = hwMap.get(CompassSensor.class, "");
        M_BackLeft = hwMap.get(DcMotor.class, "");
        M_BackRight = hwMap.get(DcMotor.class, "");
        M_FrontLeft = hwMap.get(DcMotor.class, "");
        M_FrontRight = hwMap.get(DcMotor.class, "");
        M_ChainLeft = hwMap.get(DcMotor.class, "");
        M_ChainRight = hwMap.get(DcMotor.class, "");
        S_Claw = hwMap.get(Servo.class, "");
        S_Tray1 = hwMap.get(Servo.class, "");
        S_Tray2 = hwMap.get(Servo.class, "");
        S_ClawExtender = hwMap.get(Servo.class, "");

        compass.setMode(CompassSensor.CompassMode.CALIBRATION_MODE);
        lightSensor.enableLed(true);

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
        //Example: .setDirection(DcMotor.Direction.FORWARD /REVERSE);
    }
    void OpenClaw(){
        S_Claw.setPosition(ServoClawOpenedPosition);
    }
    void CloseClaw(){
        S_Claw.setPosition((ServoClawClosedPosition));
    }
    void ExtendClaw(){
        S_ClawExtender.setPosition(ServoClawExtenderOpenedPosition);
    }
    void RetractClaw(){
        S_ClawExtender.setPosition(ServoClawExtenderClosedPosition);
    }
    void HookTray(){
        S_Tray1.setPosition(ServoTrayLeftOpenedPosition);
        S_Tray2.setPosition(ServoTrayRightOpenedPosition);
    }
    void UnhookTray(){
        S_Tray1.setPosition(ServoTrayLeftClosedPosition);
        S_Tray2.setPosition(ServoTrayRightClosedPosition);
    }
}
