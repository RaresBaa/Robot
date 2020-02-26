package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Configuration.*;

//This OpMode moves block from one side to another.The robot must be facing towards the wall

//Left-Blocks  Right-Tray

@Autonomous(name="Blocks_GoRight_BLUE", group="Auto")
public class OpMode_Blocks_GoRight extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();
    private int Target[] = new int[4];

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
        GoToPosition();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //go to the other side
        GoTargetRight(Block_Steps_Distance);
        GoToPosition();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        sleep(Servo_Wait);
        hardware.Intake_Power(0);
        //go to the other side + one block distance
        GoTargetLeft(Block_Steps_Distance + Block_Steps_Between);
        GoToPosition();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //go to the other side + one block distance
        GoTargetRight(Block_Steps_Distance + Block_Steps_Between);
        GoToPosition();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        sleep(Servo_Wait);
        hardware.Intake_Power(0);
        //go to the other side + two block distance
        GoTargetLeft(Block_Steps_Distance + 2 * Block_Steps_Between);
        GoToPosition();
        LogTelemetery();
        //activate intake
        hardware.Intake_Power(1);
        //go to the block
        GoTargetBack(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //deactivate intake
        hardware.Intake_Power(0);
        //go back with the block
        GoTargetFront(Block_Steps_Back_Forth);
        GoToPosition();
        LogTelemetery();
        //go to the other side + two block distance
        GoTargetRight(Block_Steps_Distance + 2 * Block_Steps_Between);
        GoToPosition();
        LogTelemetery();
        //drop the block off
        hardware.Intake_Power(-1);
        sleep(Servo_Wait);
        hardware.Intake_Power(0);
        //Park
        GoTargetFront(Block_Park_Distance);
        GoToPosition();
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
    private void GoToPosition(){
        hardware.M_BR.setTargetPosition(Target[0]);
        hardware.M_BL.setTargetPosition(Target[1]);
        hardware.M_FR.setTargetPosition(Target[2]);
        hardware.M_FL.setTargetPosition(Target[3]);

        hardware.M_BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.M_BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.M_FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.M_FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hardware.M_BR.setPower(1);
        hardware.M_BL.setPower(1);
        hardware.M_FR.setPower(1);
        hardware.M_FL.setPower(1);

        while(hardware.M_BR.isBusy() || hardware.M_BL.isBusy() || hardware.M_FR.isBusy() || hardware.M_FL.isBusy()){
            LogTelemetery();
        }
        hardware.M_BR.setPower(0);
        hardware.M_BL.setPower(0);
        hardware.M_FR.setPower(0);
        hardware.M_FL.setPower(0);

    }
    void GoTargetFront(int steps){
        Target[0] += steps;
        Target[1] -= steps;
        Target[2] += steps;
        Target[3] -= steps;
    }
    void GoTargetBack(int steps){
        Target[0] -= steps;
        Target[1] += steps;
        Target[2] -= steps;
        Target[3] += steps;
    }
    void GoTargetRight(int steps){
        Target[0] += steps;
        Target[1] += steps;
        Target[2] -= steps;
        Target[3] -= steps;
    }
    void GoTargetLeft(int steps){
        Target[0] -= steps;
        Target[1] -= steps;
        Target[2] += steps;
        Target[3] += steps;
    }
}
