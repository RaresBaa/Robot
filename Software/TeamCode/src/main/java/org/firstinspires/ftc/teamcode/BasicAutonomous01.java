package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="BasicAutonomous01", group="Base")
public class BasicAutonomous01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    private static final float ServoClawClosedPosition = 0.0f;
    private static final float ServoClawOpenedPosition = 1.0f;
    private static final float ServoTrayLeftClosedPosition = 0.0f;
    private static final float ServoTrayLeftOpenedPosition = 1.0f;
    private static final float ServoTrayRightClosedPosition = 0.0f;
    private static final float ServoTrayRightOpenedPosition = 1.0f;
    private static final float ServoClawExtenderClosedPosition = 0.0f;
    private static final float ServoClawExtenderOpenedPosition = 1.0f;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Main INIT code goes here
        hardware.init(hardwareMap);


        telemetry.addData("Status", "Init Done");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {//Main Loop


        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}
