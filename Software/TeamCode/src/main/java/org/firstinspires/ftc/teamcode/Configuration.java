package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {

    //for the light sensor
    public static double     WHITE_THRESHOLD = 0.2;  // spans between 0.1 - 0.5 from dark to light
    public static double     APPROACH_SPEED  = 0.5;

    private static final float mmPerInch        = 25.4f;
    public static float CAMERA_FORWARD_DISPLACEMENT  = 8.85f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
    public static float CAMERA_VERTICAL_DISPLACEMENT = 7.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    public static float CAMERA_LEFT_DISPLACEMENT     = -5.31f * mmPerInch;     // eg: Camera is ON the robot's center line

    public static double pidJoystick_X_P = 0.5;
    public static double pidJoystick_X_I = 0.0;
    public static double pidJoystick_X_D = 0.0;

    public static double pidJoystick_Y_P = 0.5;
    public static double pidJoystick_Y_I = 0.0;
    public static double pidJoystick_Y_D = 0.0;

    public static double pidArmPower_P = 0.5;
    public static double pidArmPower_I = 0.00003;
    public static double pidArmPower_D = 0;
    public static double pidArmPower_T = 5;
    public static double ClawMaxHeight = 40;
    public static double ClawMinHeight = 10;
    public static  double ClawJoystickSensibility = 0.1;
}
