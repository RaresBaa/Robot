package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Tray_GoRight", group="Auto")
public class OpMode_Tray_GoRight extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {//Main Loop

            telemetry.addData("Motor Distance-BL", hardware.M_BL.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BR.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FL.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FR.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
