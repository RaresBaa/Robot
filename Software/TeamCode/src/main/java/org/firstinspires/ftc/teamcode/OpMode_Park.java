package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Park_Forward", group="Auto")
public class OpMode_Park extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap);

        waitForStart();//Until Play is pressed
        LogTelemetery();
        //Go Forward to the tray
        hardware.M_FL.setTargetPosition(hardware.M_FL.getCurrentPosition() + Configuration.Park_Steps);
        hardware.M_FR.setTargetPosition(hardware.M_FR.getCurrentPosition() + Configuration.Park_Steps);
        hardware.M_BL.setTargetPosition(hardware.M_BL.getCurrentPosition() + Configuration.Park_Steps);
        hardware.M_BR.setTargetPosition(hardware.M_BR.getCurrentPosition() + Configuration.Park_Steps);
        LogTelemetery();

        while (opModeIsActive()) {//Main Loop
            LogTelemetery();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

    private void LogTelemetery(){
        telemetry.addData("Motor Distance-BL", hardware.M_BL.getCurrentPosition());
        telemetry.addData("Motor Distance-BR", hardware.M_BR.getCurrentPosition());
        telemetry.addData("Motor Distance-FL", hardware.M_FL.getCurrentPosition());
        telemetry.addData("Motor Distance-FR", hardware.M_FR.getCurrentPosition());
        telemetry.update();
    }
}