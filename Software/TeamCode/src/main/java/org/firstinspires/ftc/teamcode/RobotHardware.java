package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class RobotHardware {

    //for the light sensor
    static final double     WHITE_THRESHOLD = 0.2;  // spans between 0.1 - 0.5 from dark to light
    static final double     APPROACH_SPEED  = 0.5;

    DcMotor M_BackLeft = null;
    DcMotor M_BackRight = null;
    DcMotor M_FrontLeft = null;
    DcMotor M_FrontRight = null;
    DcMotor M_ChainLeft = null;
    DcMotor M_ChainRight = null;
    LightSensor lightSensor = null;
    WebcamName webcamName = null;
    CRServo S_Claw = null;
    CRServo S_ClawExtender = null;
    CRServo S_Tray1 = null;
    CRServo S_Tray2 = null;
    private static final float mmPerInch        = 25.4f;
    final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

    /* Constructor */
    RobotHardware(){

    }
    void init(HardwareMap hwMap){
        //Hardware to software mapping
        webcamName = hwMap.get(WebcamName.class, "webcam");
        //lightSensor = hwMap.lightSensor.get("lightSensor");
        M_BackLeft = hwMap.get(DcMotor.class, "motorBackLeft");
        M_BackRight = hwMap.get(DcMotor.class, "motorBackRight");
        M_FrontLeft = hwMap.get(DcMotor.class, "motorFrontLeft");
        M_FrontRight = hwMap.get(DcMotor.class, "motorFrontRight");
        M_ChainLeft = hwMap.get(DcMotor.class, "motorChainLeft");
        M_ChainRight = hwMap.get(DcMotor.class, "motorChainRight");
        S_Claw = hwMap.get(CRServo.class, "servoClaw");
        S_Tray1 = hwMap.get(CRServo.class, "servoTrayLeft");
        S_Tray2 = hwMap.get(CRServo.class, "servoTrayRight");
        S_ClawExtender = hwMap.get(CRServo.class, "servoClawExtender");

        //lightSensor.enableLed(true);

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

}
