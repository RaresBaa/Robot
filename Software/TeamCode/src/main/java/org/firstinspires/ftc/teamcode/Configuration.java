package org.firstinspires.ftc.teamcode;

class Configuration {
    //For the webcam position
    private static final float mmPerInch        = 25.4f;
    static final float CAMERA_FORWARD_DISPLACEMENT  = 8.85f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    static final float CAMERA_VERTICAL_DISPLACEMENT = 7.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    static final float CAMERA_LEFT_DISPLACEMENT     = -5.31f * mmPerInch;     // eg: Camera is ON the robot's center line

    //All the servo positions
    static final double S_Tray_Front_Up = 0.9;
    static final double S_Tray_Front_Down = 0.2;
    static final double S_Tray_Back_UP = 0.2;
    static final double S_Tray_Back_Down = 0.85;
    static final double S_Claw_Opened = 0.6;
    static final double S_Claw_Closed = 0.4;

    //TeleOp Constants
    static final float FineControl = 4;
    static final long Servo_Wait = 1000;

    static final int Park_Steps = 3000;  //distance to the parking line
    static final int Tray_Steps = 1000; //distance to the tray
    static final int Tray_Back_Steps = 1500; //distance with the tray back

    static final int Block_Steps_Back_Forth = 500; //distance required to go back
    static final int Block_Steps_Initial = 1000; //From the wall to the blocks
    static final int Block_Steps_Distance = 1000; //From one side of the field to another
    static final int Block_Steps_Between = 100; //Distance between blocks
    static final int Block_Park_Distance =  200; //distance from the final position to the parking line
    static final long Block_Drop_Time = 500; //milliseconds for the intake to release the block


}
//fortza steaua fortza steaua hei hei
//a fost odata ca-n povesti
//a fost ca niciodata
//din rude mari imparatesti
//o prea frumoasa fata

//si era una la parinti
// si mandra in toate cele
//ca fecioara intre sfinti
//si luna intre stele
