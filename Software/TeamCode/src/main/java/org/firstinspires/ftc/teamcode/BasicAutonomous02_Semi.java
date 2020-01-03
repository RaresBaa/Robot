package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@Autonomous(name="BasicAutonomous02_Semi", group="Base")
public class BasicAutonomous02_Semi extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    // 1-for blue, 0-for red
    private static boolean TeamSide = false;
    // 1-for left side, 0-for right side
    private static boolean TeamQuadrant = false;
    //Locations for the BlueTray, RedTray, BluePark, RedPark;
    private final VectorF BlueTrayLocation = new VectorF(10,10,0);
    private final VectorF RedTrayLocation = new VectorF(20,20,0);
    private final VectorF BlueParkLocation = new VectorF(20,20,0);
    private final VectorF RedParkLocation = new VectorF(20,20,0);
    private final VectorF BlueTraySpotLocation = new VectorF(1,2,0);
    private final VectorF RedTraySpotLocation = new VectorF(3, 4);

    //Vuforia Constants
    private boolean targetVisible;
    private static final float mmPerInch        = 25.4f;
    private OpenGLMatrix lastLocation = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        //Vuforia Configuration
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(hardware.VuforiaParams);
        // Load the data sets for the trackable objects.
        VuforiaTrackables targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");
        List<VuforiaTrackable> AllObjects = getTrackables(targetsSkyStone);
        //Streaming vuforia to /dash
        dashboard.startCameraStream(vuforia,0);

        //In order to not block the while loop, we step through the blocking instructions
        int step = 0;

        VectorF TrayLocation = new VectorF(0,0,0);
        VectorF ParkLocation = new VectorF(0,0,0);
        VectorF TraySpotLocation = new VectorF(0,0,0);
        long ServoTime = 0;
        boolean TraySide = true;

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {//Main Loop
            telemetry.addData("Trackable Name", vuforiaLocation(AllObjects));
            VectorF translation = lastLocation.getTranslation();
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            float xPos = translation.get(0);
            float yPos = translation.get(1);

            switch(step){
                case 0://Figuring in which quadrant we are, and which team
                     /*
                    If you are standing in the Red Alliance Station looking towards the center of the field,
                     - The X axis runs from your left to the right. (positive from the center to the right)
                     - The Y axis runs from the Red Alliance Station towards the other side of the field
                         where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)

                    Team-Side 1-for blue, 0-for red
                    Team_Quadrant 1-for left side, 0-for right side
                    */
                    if(xPos < 0 && yPos < 0){
                        //Red Team, Left
                        TeamSide = false;
                        TeamQuadrant =true;
                    }else if(xPos > 0 && yPos < 0){
                        //Red Team, Right
                        TeamSide = false;
                        TeamQuadrant = false;
                    }else if(xPos > 0 && yPos > 0){
                        //Blue Team, Left
                        TeamSide = true;
                        TeamQuadrant = true;
                    }else if(xPos < 0 && yPos > 0) {
                        //Blue Team. Right
                        TeamSide = true;
                        TeamQuadrant = false;
                    }
                    TraySide = xPos < 0;
                    if(targetVisible){
                        step++;
                    }
                    break;
                case 1://Setting the Location Variables

                    if(TeamSide){//blue
                        TrayLocation = BlueTrayLocation;
                        ParkLocation = BlueParkLocation;
                        TraySpotLocation = BlueTraySpotLocation;
                    }else if(!TeamSide){//red
                        TrayLocation = RedTrayLocation;
                        ParkLocation = RedParkLocation;
                        TraySpotLocation = RedTraySpotLocation;
                    }
                    step++;
                    break;
                case 2://Going to the tray if we are in the tray area
                    if(TraySide){
                        //the distance remaning to the Tray
                        float xDis = xPos - TrayLocation.get(0);
                        float yDis = yPos - TrayLocation.get(1);


                        if(xDis == 0 && yDis == 0){
                            step++;
                        }
                    }
                    break;
                case 3://Deploying tray servos
                    if(TraySide) {
                        hardware.S_Tray1.setPower(1.0f);
                        hardware.S_Tray2.setPower(1.0f);
                        ServoTime = runtime.now(TimeUnit.MILLISECONDS);
                        step++;
                    }
                    break;
                case 4://Stopping the servos from moving
                case 7:
                    if(TraySide) {
                        if (ServoTime + Configuration.AutonomousTrayServoDeployTime >= runtime.now(TimeUnit.MILLISECONDS)) {
                            hardware.S_Tray1.setPower(0);
                            hardware.S_Tray2.setPower(0);
                            step++;
                        }
                    }
                    break;
                case 5://going back with the tray
                    if(TraySide) {
                        //the distance remaning to the tray spot
                        float xDis = xPos - TraySpotLocation.get(0);
                        float yDis = yPos - TraySpotLocation.get(1);


                        if (xDis == 0 && yDis == 0) {
                            step++;
                        }
                    }
                    break;
                case 6://Lifting the Servos
                    if(TraySide) {
                        hardware.S_Tray1.setPower(-1.0f);
                        hardware.S_Tray2.setPower(-1.0f);
                        ServoTime = runtime.now(TimeUnit.MILLISECONDS);
                        step++;
                    }
                    break;
                case 8://Parking
                    float xDis = xPos - ParkLocation.get(0);
                    float yDis = yPos = ParkLocation.get(1);


                    if(xDis == 0 && yDis ==0){
                        step++;
                    }
                    break;
                default:
                    telemetry.addData("Status", "Finished the switch.");
                    break;
            }

            telemetry.addData("Sides", (TeamSide) ? "Blue" : "Red");
            telemetry.addData("Quadrant", (TeamQuadrant) ? "Left Side" : "Right Side");
            telemetry.addData("Target Visible", targetVisible);
            telemetry.addData("Tray Location", "{X, Y, Z} = %.1f, %.1f, %.1f", TrayLocation.get(0), TrayLocation.get(1), TrayLocation.get(2));
            telemetry.addData("Park Location", "{X, Y, Z} = %.1f, %.1f, %.1f", ParkLocation.get(0), ParkLocation.get(1), ParkLocation.get(2));
            telemetry.addData("Pos (cm)", "{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0), translation.get(1), translation.get(2));
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            telemetry.addData("Motor Distance-BL", hardware.M_BackLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BackRight.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FrontLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FrontRight.getCurrentPosition());
            telemetry.addData("Motor Distance-CL", hardware.M_ChainLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-CR", hardware.M_ChainRight.getCurrentPosition());
            telemetry.update();
        }
        targetsSkyStone.deactivate();
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

    private String vuforiaLocation(List<VuforiaTrackable> AllObjects){
        targetVisible = false;
        for (VuforiaTrackable trackable : AllObjects) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                targetVisible = true;
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                return trackable.getName();
            }
        }
        return "";
    }

    private List<VuforiaTrackable> getTrackables(VuforiaTrackables targetsSkyStone){

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
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, hardware.VuforiaParams.cameraDirection);
        }
        targetsSkyStone.activate();

        return allTrackables;
    }

}
