package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.annotation.Target;


@Autonomous(name="Tray_GoLeft", group="Auto")
public class OpMode_Tray_GoLeft extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();
    private int Target[];

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap);


        waitForStart();//Until Play is pressed
        runtime.reset();
        LogTelemetery();
        Target[0] = hardware.M_BR.getCurrentPosition();
        Target[1] = hardware.M_BL.getCurrentPosition();
        Target[2] = hardware.M_FR.getCurrentPosition();
        Target[3] = hardware.M_FL.getCurrentPosition();
        //Go Forward to the tray (Go to the Left Side)
        Target[0] += Configuration.Tray_Steps;
        Target[1] -= Configuration.Tray_Steps;
        Target[2] -= Configuration.Tray_Steps;
        Target[3] += Configuration.Tray_Steps;
        WaitForTarget();
        LogTelemetery();
        //Lower Tray Servos
        hardware.Lower_Tray();
        try {
            wait(Configuration.Servo_Wait);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //Go Back With the Tray(Go to the Right Side)
        Target[0] -= Configuration.Tray_Back_Steps;
        Target[1] += Configuration.Tray_Back_Steps;
        Target[2] += Configuration.Tray_Back_Steps;
        Target[3] -= Configuration.Tray_Back_Steps;
        WaitForTarget();
        LogTelemetery();
        //Lift Tray Servos
        hardware.Lift_Tray();
        try {
            wait(Configuration.Servo_Wait);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //TODO: Finish Movements

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
    void WaitForTarget(){
        while(hardware.M_BR.getCurrentPosition() != Target[0] || hardware.M_BL.getCurrentPosition() != Target[1] ||
                hardware.M_FR.getCurrentPosition() != Target[2] || hardware.M_FL.getCurrentPosition() != Target[3]){
            hardware.M_BR.setTargetPosition(Target[0]);
            hardware.M_BL.setTargetPosition(Target[1]);
            hardware.M_FR.setTargetPosition(Target[2]);
            hardware.M_FL.setTargetPosition(Target[3]);
        }
    }
}
