package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Configuration.*;

//This OpMode moves block from one side to another.The robot must be facing towards the wall

//Left-Blocks  Right-Tray

@Autonomous(name="Blocks_GoRight_BLUE", group="Auto")
public class OpMode_Blocks_GoRight extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();
    private int Target[];

    @Override
    public void runOpMode(){
        hardware.init(hardwareMap);

        waitForStart();
        runtime.reset();
        LogTelemetery();
        Target[0] = hardware.M_BR.getCurrentPosition();
        Target[1] = hardware.M_BL.getCurrentPosition();
        Target[2] = hardware.M_FR.getCurrentPosition();
        Target[3] = hardware.M_FL.getCurrentPosition();
        //go forward to the blocks
        GoTargetBack(Block_Steps_Initial);
        WaitForTarget();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //go to the other side
        GoTargetRight(Block_Steps_Distance);
        WaitForTarget();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        try{
            wait(Block_Drop_Time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        hardware.Intake_Power(0);
        //go to the other side + one block distance
        GoTargetLeft(Block_Steps_Distance + Block_Steps_Between);
        WaitForTarget();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //go to the other side + one block distance
        GoTargetRight(Block_Steps_Distance + Block_Steps_Between);
        WaitForTarget();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        try{
            wait(Block_Drop_Time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        hardware.Intake_Power(0);
        //go to the other side + two block distance
        GoTargetLeft(Block_Steps_Distance + 2 * Block_Steps_Between);
        WaitForTarget();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        WaitForTarget();
        LogTelemetery();
        //go to the other side + two block distance
        GoTargetRight(Block_Steps_Distance + 2 * Block_Steps_Between);
        WaitForTarget();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        try{
            wait(Block_Drop_Time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        hardware.Intake_Power(0);
        //Park
        GoTargetFront(Block_Park_Distance);
        WaitForTarget();
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
    void GoTargetFront(int steps){
        Target[0] += steps;
        Target[1] += steps;
        Target[2] += steps;
        Target[3] += steps;
    }
    void GoTargetBack(int steps){
        Target[0] -= steps;
        Target[1] -= steps;
        Target[2] -= steps;
        Target[3] -= steps;
    }
    void GoTargetLeft(int steps){
        Target[0] -= steps;
        Target[1] += steps;
        Target[2] += steps;
        Target[3] -= steps;
    }
    void GoTargetRight(int steps){
        Target[0] += steps;
        Target[1] -= steps;
        Target[2] -= steps;
        Target[3] += steps;
    }
}
