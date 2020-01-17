package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


public class RobotHardware {

    DcMotor M_BL = null;
    DcMotor M_BR = null;
    DcMotor M_FL = null;
    DcMotor M_FR = null;

    private DcMotor M_Intake_Left = null;
    private DcMotor M_Intake_Right = null;

    DcMotor M_Lift = null;

    private Servo S_Intake_Left = null;
    private Servo S_Intake_Right = null;

    private Servo S_Tray_Back = null;
    private Servo S_Tray_Front = null;

    CRServo S_Ruleta = null;

    VuforiaLocalizer.Parameters VuforiaParams;

    /* Constructor */
    RobotHardware(){

    }
    void init(HardwareMap hwMap){
        //Hardware to software mapping
        M_BL = hwMap.get(DcMotor.class, "M_BL");
        M_BR = hwMap.get(DcMotor.class, "M_BR");
        M_FL = hwMap.get(DcMotor.class, "M_FL");
        M_FR = hwMap.get(DcMotor.class, "M_FR");

        M_Intake_Left = hwMap.get(DcMotor.class, "M_Intake_Left");
        M_Intake_Right = hwMap.get(DcMotor.class, "M_Intake_Right");

        M_Lift = hwMap.get(DcMotor.class, "M_Lift");

        S_Intake_Left = hwMap.get(Servo.class, "S_Intake_Left");
        S_Intake_Right = hwMap.get(Servo.class, "S_Intake_Right");

        S_Tray_Front = hwMap.get(Servo.class, "S_Tray_Front");
        S_Tray_Back = hwMap.get(Servo.class, "S_Tray_Back");

        S_Ruleta = hwMap.get(CRServo.class, "S_Ruleta");

        WebcamName webcamName = hwMap.get(WebcamName.class, "webcam");


        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters VuforiaParams = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        VuforiaParams.vuforiaLicenseKey = VuforiaKey.KEY;
        VuforiaParams.cameraName = webcamName;


        //Reset Motor encoders
        M_Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //Enable Motor Encoders
        M_Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Setting the Motor Direction, If needed
        M_Intake_Left.setDirection(DcMotor.Direction.REVERSE);
        S_Intake_Left.setDirection(Servo.Direction.REVERSE);
        S_Tray_Back.setDirection(Servo.Direction.REVERSE);
    }
    void Intake_Power(double pow){
        M_Intake_Right.setPower(pow);
        M_Intake_Left.setPower(pow);
    }

    void Lift_Intake(){
        S_Intake_Right.setPosition(Configuration.S_Intake_Right_Up);
        S_Intake_Left.setPosition(Configuration.S_Intake_Left_Up);
    }
    void Lower_Intake(){
        S_Intake_Right.setPosition(Configuration.S_Intake_Right_Down);
        S_Intake_Left.setPosition(Configuration.S_Intake_Left_Down);
    }
    void Lift_Tray(){
        S_Tray_Back.setPosition(Configuration.S_Tray_Back_UP);
        S_Tray_Front.setPosition(Configuration.S_Tray_Front_Up);
    }
    void Lower_Tray(){
        S_Tray_Back.setPosition(Configuration.S_Tray_Back_Down);
        S_Tray_Front.setPosition(Configuration.S_Tray_Front_Down);
    }

}
