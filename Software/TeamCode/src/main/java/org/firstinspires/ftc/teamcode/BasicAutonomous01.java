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

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@Autonomous(name="BasicAutonomous01", group="Base")
public class BasicAutonomous01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    //Vuforia Constants
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


        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {//Main Loop
            boolean targetVisible = false;
            for (VuforiaTrackable trackable : AllObjects) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    targetVisible = true;
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }

                    telemetry.addData("Visible Target", trackable.getName());

                    break;
                }
            }
            if (targetVisible) {
                VectorF translation = lastLocation.getTranslation();
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);


                telemetry.addData("Pos (cm)", "{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0), translation.get(1), translation.get(2));
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();
        }
        targetsSkyStone.deactivate();
        telemetry.addData("Status", "Finished");
        telemetry.update();
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
