package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="BasicHolomnic", group="Auto")
public class OpMode_BasicHolomnic extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor M_BL = null;
    private DcMotor M_BR = null;
    private DcMotor M_FL = null;
    private DcMotor M_FR = null;

    @Override
    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        //Hardware to software mapping
        M_BL = hardwareMap.get(DcMotor.class, "M_BL");
        M_BR = hardwareMap.get(DcMotor.class, "M_BR");
        M_FL = hardwareMap.get(DcMotor.class, "M_FL");
        M_FR = hardwareMap.get(DcMotor.class, "M_FR");
        //Reset Motor encoders
        M_BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        M_FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Enable Motor Encoders
        M_BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        M_FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Enable Braking When Stationary
        M_BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        M_FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Setting the Motor Direction, If needed
        M_BL.setDirection(DcMotor.Direction.REVERSE);
        M_BR.setDirection(DcMotor.Direction.REVERSE);
        M_FR.setDirection(DcMotor.Direction.REVERSE);
        M_FL.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {//Main Loop
            if(gamepad1.left_bumper){
                SideOne();
            }
            if(gamepad1.right_bumper){
                SideTwo();
            }
            float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightY = -gamepad1.right_stick_y;
            float gamepad1RightX = gamepad1.right_stick_x;
            float gamepad1LeftTrigger = gamepad1.left_trigger;
            float gamepad1RightTrigger = gamepad1.right_trigger;
            float PowY = gamepad1LeftY + gamepad1RightY/Configuration.FineControl;
            float PowX = -gamepad1LeftX - gamepad1RightX/Configuration.FineControl;
            float turn = gamepad1LeftTrigger - gamepad1RightTrigger;
            HolomnicDrive(PowX, PowY, turn);
            telemetry.addData("Motor Distance-BL", M_BL.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", M_BR.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", M_FL.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", M_FR.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

    private void HolomnicDrive(float PowX, float PowY, float turn){
        float FrontLeft = -PowY - PowX - turn;
        float FrontRight = PowY - PowX - turn;
        float BackRight = PowY + PowX - turn;
        float BackLeft = -PowY + PowX - turn;
        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);
        M_BL.setPower(BackLeft);
        M_BR.setPower(BackRight);
        M_FL.setPower(FrontLeft);
        M_FR.setPower(FrontRight);
    }
    private void SideOne(){
        M_BL.setDirection(DcMotor.Direction.REVERSE);
        M_BR.setDirection(DcMotor.Direction.REVERSE);
        M_FR.setDirection(DcMotor.Direction.REVERSE);
        M_FL.setDirection(DcMotor.Direction.REVERSE);
    }
    private void SideTwo(){
        M_BL.setDirection(DcMotor.Direction.FORWARD);
        M_BR.setDirection(DcMotor.Direction.FORWARD);
        M_FR.setDirection(DcMotor.Direction.FORWARD);
        M_FL.setDirection(DcMotor.Direction.FORWARD);
    }
}