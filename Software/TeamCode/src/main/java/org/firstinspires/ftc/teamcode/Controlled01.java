package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controlled01", group="Base")
public class Controlled01 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    PIDController pidJoystick_X, pidJoystick_Y, pidArmPower;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        hardware.init(hardwareMap);

        pidJoystick_X = new PIDController(Configuration.pidJoystick_X_P, Configuration.pidJoystick_X_I, Configuration.pidJoystick_X_D);
        pidJoystick_Y = new PIDController(Configuration.pidJoystick_Y_P, Configuration.pidJoystick_Y_I, Configuration.pidJoystick_Y_D);
        pidArmPower = new PIDController(Configuration.pidArmPower_P, Configuration.pidArmPower_I, Configuration.pidArmPower_D);


        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {//Main Loop
            double drive = -gamepad1.left_stick_x;
            double turn  =  gamepad1.left_stick_y;
            double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
            double armPower = Range.clip(-gamepad1.right_stick_x, -1.0, 1.0) ;

            hardware.M_BackLeft.setPower(leftPower);
            hardware.M_BackRight.setPower(rightPower);
            hardware.M_FrontLeft.setPower(leftPower);
            hardware.M_FrontRight.setPower(rightPower);
            hardware.M_ChainLeft.setPower(armPower);
            hardware.M_ChainRight.setPower(armPower);


            telemetry.addData("Motor Joystick X", -drive);
            telemetry.addData("Motor Joystick Y", turn);
            telemetry.addData("Motor Power", drive);
            telemetry.addData("Motor Turn", turn);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Arm Power", armPower);


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
            //Controlling the Tray servos
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
            //Claw extender
            if(gamepad1.y || gamepad1.b) {
                if (gamepad1.y) {
                    telemetry.addData("Extended", false);
                    hardware.S_ClawExtender.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                if (gamepad1.b) {
                    telemetry.addData("Extended", true);
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
