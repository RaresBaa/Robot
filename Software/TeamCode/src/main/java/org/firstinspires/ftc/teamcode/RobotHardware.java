package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;


public class RobotHardware {

    DcMotor M_BL = null;
    DcMotor M_BR = null;
    DcMotor M_FL = null;
    DcMotor M_FR = null;

    private int SwapDirection = 1;

    private DcMotor M_Intake_Left = null;
    private DcMotor M_Intake_Right = null;

    DcMotor M_Lift = null;

    private Servo S_Intake_Left = null;
    private Servo S_Intake_Right = null;

    private Servo S_Tray_Back = null;
    private Servo S_Tray_Front = null;

    CRServo S_Ruleta = null;

    private VuforiaLocalizer.Parameters VuforiaParams;
    private List<VuforiaTrackable> AllObjects;
    VuforiaLocalizer vuforia;
    boolean targetVisible;
    OpenGLMatrix lastLocation = null;
    VectorF translation;
    Orientation rotation;

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

        //Enable Braking When Stationary
        M_Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Setting the Motor Direction, If needed
        M_Intake_Right.setDirection(DcMotor.Direction.REVERSE);
        M_BL.setDirection(DcMotor.Direction.REVERSE);
        M_BR.setDirection(DcMotor.Direction.REVERSE);
        M_FR.setDirection(DcMotor.Direction.REVERSE);
        M_FL.setDirection(DcMotor.Direction.REVERSE);

    }
    void SideOne(){
        SwapDirection = -1;
        M_BL.setDirection(DcMotor.Direction.REVERSE);
        M_BR.setDirection(DcMotor.Direction.REVERSE);
        M_FR.setDirection(DcMotor.Direction.REVERSE);
        M_FL.setDirection(DcMotor.Direction.REVERSE);
    }
    void SideTwo(){
        SwapDirection = 1;
        M_BL.setDirection(DcMotor.Direction.FORWARD);
        M_BR.setDirection(DcMotor.Direction.FORWARD);
        M_FR.setDirection(DcMotor.Direction.FORWARD);
        M_FL.setDirection(DcMotor.Direction.FORWARD);
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

    void HolomnicDrive(float PowX, float PowY, float turn){

        turn = turn * SwapDirection;

        float FrontLeft = -PowY - PowX - turn;
        float FrontRight = PowY - PowX - turn;
        float BackRight = PowY + PowX - turn;
        float BackLeft = -PowY + PowX - turn;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        M_BL.setPower(BackLeft);
        M_BR.setPower(BackRight);
        M_FL.setPower(FrontLeft);
        M_FR.setPower(FrontRight);
    }

    void InitVuforia(HardwareMap hwMap){

        WebcamName webcamName = hwMap.get(WebcamName.class, "webcam");

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters VuforiaParams = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        VuforiaParams.vuforiaLicenseKey = VuforiaKey.KEY;
        VuforiaParams.cameraName = webcamName;

        vuforia = ClassFactory.getInstance().createVuforia(VuforiaParams);
        // Load the data sets for the trackable objects.
        VuforiaTrackables targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");
        AllObjects = getTrackables(targetsSkyStone);
    }
    String VuforiaTrack(){
        /*
        A nice way of Getting Telemetry in OpMode:
            telemetry.addData("Pos (cm)", "{X, Y, Z} = %.1f, %.1f, %.1f", hardware.translation.get(0), hardware.translation.get(1), hardware.translation.get(2));
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", hardware.rotation.firstAngle, hardware.rotation.secondAngle, hardware.rotation.thirdAngle);

         */
        targetVisible = false;
        for (VuforiaTrackable trackable : AllObjects) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                targetVisible = true;
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                    translation = lastLocation.getTranslation();
                    rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                }
                return trackable.getName();
            }
        }
        return "";
    }

    private List<VuforiaTrackable> getTrackables(VuforiaTrackables targetsSkyStone){

        final float mmPerInch = 25.4f;
        final float mmTargetHeight   = (6) * mmPerInch;    // the height of the center of the target image above the floor
        final float stoneZ = 2.00f * mmPerInch;
        final float bridgeZ = 6.42f * mmPerInch;
        final float bridgeY = 23 * mmPerInch;
        final float bridgeX = 5.18f * mmPerInch;
        final float bridgeRotY = 59;   // Units are degrees
        final float bridgeRotZ = 180;
        final float halfField = 72 * mmPerInch;
        final float quadField  = 36 * mmPerInch;

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        List<VuforiaTrackable> allTrackables = new ArrayList<>(targetsSkyStone);

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix.translation(0, 0, stoneZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
        blueFrontBridge.setLocation(OpenGLMatrix.translation(-bridgeX, bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));
        blueRearBridge.setLocation(OpenGLMatrix.translation(-bridgeX, bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));
        redFrontBridge.setLocation(OpenGLMatrix.translation(-bridgeX, -bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));
        redRearBridge.setLocation(OpenGLMatrix.translation(bridgeX, -bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));
        red1.setLocation(OpenGLMatrix.translation(quadField, -halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));
        red2.setLocation(OpenGLMatrix.translation(-quadField, -halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));
        front1.setLocation(OpenGLMatrix.translation(-halfField, -quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));
        front2.setLocation(OpenGLMatrix.translation(-halfField, quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));
        blue1.setLocation(OpenGLMatrix.translation(-quadField, halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        blue2.setLocation(OpenGLMatrix.translation(quadField, halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        rear1.setLocation(OpenGLMatrix.translation(halfField, quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));
        rear2.setLocation(OpenGLMatrix.translation(halfField, -quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        OpenGLMatrix robotFromCamera = OpenGLMatrix.translation(Configuration.CAMERA_FORWARD_DISPLACEMENT, Configuration.CAMERA_LEFT_DISPLACEMENT, Configuration.CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, 90, 0, 0));

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, VuforiaParams.cameraDirection);
        }
        targetsSkyStone.activate();

        return allTrackables;
    }

}
