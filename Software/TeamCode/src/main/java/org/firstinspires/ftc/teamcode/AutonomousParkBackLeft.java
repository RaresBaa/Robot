package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="ParkBackLeft", group="Base")
public class AutonomousParkBackLeft extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();

        //Getting some distance from the wall
        hardware.M_BackRight.setTargetPosition(hardware.M_BackRight.getCurrentPosition() + Configuration.AutonomousOffTheWall);
        hardware.M_BackLeft.setTargetPosition(hardware.M_BackLeft.getCurrentPosition() + Configuration.AutonomousOffTheWall);
        hardware.M_FrontRight.setTargetPosition(hardware.M_FrontRight.getCurrentPosition() + Configuration.AutonomousOffTheWall);
        hardware.M_FrontLeft.setTargetPosition(hardware.M_FrontLeft.getCurrentPosition() + Configuration.AutonomousOffTheWall);
        wait(Configuration.AutonomousWaitBeforeMovesMilis);
        //turning to 90' degrees
        hardware.M_FrontRight.setTargetPosition(hardware.M_FrontRight.getCurrentPosition() + Configuration.AutonomousRotateDistance);
        hardware.M_BackRight.setTargetPosition(hardware.M_BackRight.getCurrentPosition() + Configuration.AutonomousRotateDistance);
        wait(Configuration.AutonomousWaitBeforeMovesMilis);
        //going full speed
        hardware.M_FrontLeft.setPower(1);
        hardware.M_FrontRight.setPower(1);
        hardware.M_BackLeft.setPower(1);
        hardware.M_BackRight.setPower(1);

        while (opModeIsActive()) {//Main Loop
            //Going until we see the tape below
            double Light = hardware.ColorSensor.red() + hardware.ColorSensor.blue();
            if(Light < Configuration.AutonomousLightTapeMax && Light > Configuration.AutonomousLightTapeMin){
                hardware.M_FrontLeft.setPower(0);
                hardware.M_FrontRight.setPower(0);
                hardware.M_BackLeft.setPower(0);
                hardware.M_BackRight.setPower(0);
            }

            telemetry.addData("Light Detected", Light);
            telemetry.addData("Motor Distance-BL", hardware.M_BackLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BackRight.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FrontLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FrontRight.getCurrentPosition());
            telemetry.addData("Motor Distance-CL", hardware.M_ChainLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-CR", hardware.M_ChainRight.getCurrentPosition());

            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
