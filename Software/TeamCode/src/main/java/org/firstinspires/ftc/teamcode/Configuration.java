package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {
    //For the webcam position
    private static final float mmPerInch        = 25.4f;
    public static float CAMERA_FORWARD_DISPLACEMENT  = 8.85f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    public static float CAMERA_VERTICAL_DISPLACEMENT = 7.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    public static float CAMERA_LEFT_DISPLACEMENT     = -5.31f * mmPerInch;     // eg: Camera is ON the robot's center line

    public static double S_Intake_Left_Up = 0.2;
    public static double S_Intake_Left_Down = 0.5;
    public static double S_Intake_Right_Up = 0.2;
    public static double S_Intake_Right_Down = 0.5;

    public static double S_Tray_Front_Up = 0.2;
    public static double S_Tray_Front_Down = 0.5;
    public static double S_Tray_Back_UP = 0.2;
    public static double S_Tray_Back_Down = 0.5;

    public static long Autonomous_Park_Sleep = 300;
    public static long Autonomous_Ruleta_Sleep = 300;

    public static float FineControl = 5;

}
