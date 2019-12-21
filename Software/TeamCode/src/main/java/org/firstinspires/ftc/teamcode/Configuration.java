package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {

    public static int AutonomousFrontDistance = 50;
    public static int AutonomousRotateDistance = 10;
    public static int AutonomousOffTheWall = 10;
    public static int AutonomousTrayDistance = 30;
    public static int AutonomousBackWithTrayDistance = 35;
    public static float AutonomousLightTapeMin = 0.3f;
    public static float AutonomousLightTapeMax = 0.5f;
    public static long AutonomousWaitBeforeMovesMilis = 2000;
    public static long AutonomousTrayServoDeployTime = 1000;

    private static final float mmPerInch        = 25.4f;
    public static float CAMERA_FORWARD_DISPLACEMENT  = 8.85f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    public static float CAMERA_VERTICAL_DISPLACEMENT = 7.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    public static float CAMERA_LEFT_DISPLACEMENT     = -5.31f * mmPerInch;     // eg: Camera is ON the robot's center line

    public static float joystickXsensitivity = 1.0f;
    public static float joystickYsensitivity = 1.0f;

    public static double pidArmPower_P = 0.5;
    public static double pidArmPower_I = 5;
    public static double pidArmPower_D = 0;
    public static double pidArmPower_T = 5;
    public static double ClawMaxHeight = 40;
    public static double ClawMinHeight = 10;
    public static  double ClawJoystickSensibility = 0.1;
}
