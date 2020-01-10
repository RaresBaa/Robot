package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {

    public static int AutonomousFrontDistance = 500;
    public static int AutonomousRotateDistance = 100;
    public static int AutonomousOffTheWall = 100;
    public static int AutonomousTrayDistance = 300;
    public static int AutonomousBackWithTrayDistance = 350;
    public static float AutonomousLightTape = 380f;
    public static long AutonomousWaitBeforeMovesMilis = 1000;
    public static long AutonomousTrayServoDeployTime = 300;

    private static final float mmPerInch        = 25.4f;
    public static float CAMERA_FORWARD_DISPLACEMENT  = 8.85f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    public static float CAMERA_VERTICAL_DISPLACEMENT = 7.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    public static float CAMERA_LEFT_DISPLACEMENT     = -5.31f * mmPerInch;     // eg: Camera is ON the robot's center line

    public static float joystickXsensitivity = 1.0f;
    public static float joystickYsensitivity = 1.0f;
    public static float joystickFINEsensitivity = 5.0f;

    public static double ClawMotorMinSpeed = 0.02;

    public static double pidArmPower_P = 0.1;
    public static double pidArmPower_I = 0;
    public static double pidArmPower_D = 0;
    public static double pidArmPower_T = 5;
    public static double ClawMaxHeight = 40;
    public static double ClawMinHeight = 10;
    public static  double ClawJoystickSensibility = 0.1;
}
