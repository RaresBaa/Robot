package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

@Autonomous(name="DrivePark", group="Auto")
public class DrivePark extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    //Vuforia Constants
    private static final float mmPerInch = 25.4f;
    private OpenGLMatrix lastLocation = null;

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();

        hardware.M_BL.setPower(-1);
        hardware.M_BR.setPower(1);
        hardware.M_FL.setPower(-1);
        hardware.M_FR.setPower(1);

        try {
            Thread.sleep(Configuration.Autonomous_Park_Sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hardware.M_BL.setPower(0);
        hardware.M_BR.setPower(0);
        hardware.M_FL.setPower(0);
        hardware.M_FR.setPower(0);

        while (opModeIsActive()) {//Main Loop

            telemetry.addData("Motor Distance-BL", hardware.M_BL.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BR.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FL.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FR.getCurrentPosition());
            telemetry.addData("Motor Distance-CL", hardware.M_Lift.getCurrentPosition());

            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
