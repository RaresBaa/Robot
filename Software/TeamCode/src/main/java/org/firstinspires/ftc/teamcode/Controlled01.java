package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Controlled01", group="Base")
public class Controlled01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    private PIDController pidArmPower;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        hardware.init(hardwareMap);

        pidArmPower = new PIDController(Configuration.pidArmPower_P, Configuration.pidArmPower_I, Configuration.pidArmPower_D);
        pidArmPower.setOutputRange(-1.0,1.0);
        pidArmPower.setInputRange(-1.0,1.0);
        pidArmPower.enable();

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        double drive, turn, leftPower, rightPower, joystickArmStick, armPower, clawHeight;
        double SetArmHeight = 10;

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {//Main Loop
            drive = -gamepad1.left_stick_x * Configuration.joystickXsensitivity;
            turn  = gamepad1.left_stick_y * Configuration.joystickYsensitivity;
            clawHeight = hardware.HeightSensor.getDistance(DistanceUnit.CM);

            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            //setting the SetClawHeight by joystick
            joystickArmStick = gamepad1.right_stick_x;
            if(joystickArmStick <= -0.1 || joystickArmStick >= 0.1){
                SetArmHeight += joystickArmStick*Configuration.ClawJoystickSensibility;
            }

            pidArmPower.reset();
            pidArmPower.setSetpoint(SetArmHeight);
            pidArmPower.setInputRange(Configuration.ClawMinHeight, Configuration.ClawMaxHeight);
            pidArmPower.setOutputRange(-1.0,1.0);
            pidArmPower.setTolerance(Configuration.pidArmPower_T);
            pidArmPower.enable();
            armPower =pidArmPower.performPID(clawHeight);

            hardware.M_BackLeft.setPower(leftPower);
            hardware.M_BackRight.setPower(rightPower);
            hardware.M_FrontLeft.setPower(leftPower);
            hardware.M_FrontRight.setPower(rightPower);
            hardware.M_ChainLeft.setPower(armPower);
            hardware.M_ChainRight.setPower(armPower);

            telemetry.addData("Motor Joystick X", gamepad1.left_stick_x);
            telemetry.addData("Motor Joystick Y", gamepad1.left_stick_y);
            telemetry.addData("Tuned Motor Joystick X", drive);
            telemetry.addData("Tuned Motor Joystick Y", turn);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Arm Height", clawHeight);
            telemetry.addData("SET Arm Height", SetArmHeight);
            telemetry.addData("Arm Stick", joystickArmStick);
            telemetry.addData("Arm Power", armPower);
            telemetry.addData("Motor Distance-BL", hardware.M_BackLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-BR", hardware.M_BackRight.getCurrentPosition());
            telemetry.addData("Motor Distance-FL", hardware.M_FrontLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-FR", hardware.M_FrontRight.getCurrentPosition());
            telemetry.addData("Motor Distance-CL", hardware.M_ChainLeft.getCurrentPosition());
            telemetry.addData("Motor Distance-CR", hardware.M_ChainRight.getCurrentPosition());

            //Claw controls
            if(gamepad1.left_bumper || (gamepad1.left_trigger > 0.5f)){
                if(gamepad1.left_bumper){
                    hardware.S_Claw.setDirection(DcMotor.Direction.FORWARD);
                    telemetry.addData("Claw", true);
                }
                if(gamepad1.left_trigger > 0.5f){
                    hardware.S_Claw.setDirection(DcMotor.Direction.REVERSE);
                    telemetry.addData("Claw", false);
                }
                hardware.S_Claw.setPower(1.0f);
            } else{
              hardware.S_Claw.setPower(0.0f);
            }
            //Tray Servo controls
            if(gamepad1.right_bumper || (gamepad1.right_trigger >0.5f)) {
                if (gamepad1.right_bumper) {
                    telemetry.addData("Tray", false);
                    hardware.S_Tray1.setDirection(DcMotorSimple.Direction.FORWARD);
                    hardware.S_Tray2.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                if (gamepad1.right_trigger > 0.5f) {
                    telemetry.addData("Tray", true);
                    hardware.S_Tray1.setDirection(DcMotorSimple.Direction.REVERSE);
                    hardware.S_Tray2.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                hardware.S_Tray1.setPower(1.0f);
                hardware.S_Tray2.setPower(1.0f);
            }else{
                hardware.S_Tray1.setPower(0.0f);
                hardware.S_Tray2.setPower(0.0f);
            }
            //Claw extender controls
            if(gamepad1.y || gamepad1.b) {
                if (gamepad1.y) {
                    telemetry.addData("Extender", false);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                if (gamepad1.b) {
                    telemetry.addData("Extender", true);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                hardware.S_ClawExtender.setPower(1.0f);
            }else{
                hardware.S_ClawExtender.setPower(0.0f);

            }
          telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}
