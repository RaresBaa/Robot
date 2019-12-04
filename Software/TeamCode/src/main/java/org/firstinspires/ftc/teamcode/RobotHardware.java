package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class RobotHardware {

    public DcMotor M_BackLeft = null;
    public DcMotor M_BackRight = null;
    public DcMotor M_FrontLeft = null;
    public DcMotor M_FrontRight = null;
    public DcMotor M_ChainLeft = null;
    public DcMotor M_ChainRight = null;
    public Servo S_Claw = null;
    public Servo S_ClawExtender = null;
    public Servo S_Tray1 = null;
    public Servo S_Tray2 = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        //Hardware to software mapping
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
