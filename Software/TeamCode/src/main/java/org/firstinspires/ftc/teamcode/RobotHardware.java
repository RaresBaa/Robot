package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


class RobotHardware {

    DcMotor M_BackLeft = null;
    DcMotor M_BackRight = null;
    DcMotor M_FrontLeft = null;
    DcMotor M_FrontRight = null;
    DcMotor M_ChainLeft = null;
    DcMotor M_ChainRight = null;
    CRServo S_Claw = null;
    CRServo S_ClawExtender = null;
    CRServo S_Tray1 = null;
    CRServo S_Tray2 = null;
    DistanceSensor HeightSensor = null;
    ColorSensor ColorSensor = null;

    VuforiaLocalizer.Parameters VuforiaParams;

    /* Constructor */
    RobotHardware(){

    }
    void init(HardwareMap hwMap){
        //Hardware to software mapping
        WebcamName webcamName = hwMap.get(WebcamName.class, "webcam");
        HeightSensor = hwMap.get(DistanceSensor.class, "sensorDistance");
        ColorSensor = hwMap.get(ColorSensor.class, "sensorColor");
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

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters VuforiaParams = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        VuforiaParams.vuforiaLicenseKey = VuforiaKey.KEY;
        VuforiaParams.cameraName = webcamName;


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
        M_ChainLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
