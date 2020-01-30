package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Drive", group="Controlled")
public class OpMode_Drive extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        //Main INIT code goes here
        hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addData("Status", "Init Done");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {//Main Loop

            /*
                Gamepad 1:
                Left Joystick - Direction
                Right Joystick -Fine Direction
                Triggers control Rotation
                Bumpers control Switching Sides
             */

            if(gamepad1.left_bumper){
                hardware.SideOne();
            }
            if(gamepad1.right_bumper){
                hardware.SideTwo();
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

            hardware.HolomnicDrive(PowX, PowY, turn);

            /*
                Gamepad 2:
                A-Lower Intake
                B-Lift Intake
                Dpad Up- Lift Tray
                Dpad Down - Lower Tray
                Right Bumper - Extend Ruleta
                Left Bumper - Retract Ruleta
                Joysticks-Arm Power
                Right Trigger - Intake Power

             */

            double armPower = Range.clip(-gamepad2.right_stick_x, -1.0, 1.0) ;
            double armPowerFINE = Range.clip((-gamepad2.left_stick_x / Configuration.FineControl), -1.0, 1.0) ;
            double ArmPowerFinal = Range.clip(armPower + armPowerFINE, -1.0, 1.0);

            hardware.M_Lift.setPower(ArmPowerFinal);
            hardware.Intake_Power(gamepad2.right_trigger - gamepad2.left_trigger);

            if(gamepad2.a){
                hardware.Lower_Intake();
            }
            if(gamepad2.b){
                hardware.Lift_Intake();
            }
            if(gamepad2.dpad_up){
                hardware.Lift_Tray();
            }
            if(gamepad2.dpad_down){
                hardware.Lower_Tray();
            }
            if(gamepad2.right_bumper || gamepad2.left_bumper){
                if(gamepad2.left_bumper){
                    hardware.S_Ruleta.setDirection(DcMotorSimple.Direction.FORWARD);
                }else{
                    hardware.S_Ruleta.setDirection(DcMotorSimple.Direction.REVERSE);
                }
                hardware.S_Ruleta.setPower(1);
            }else{
                hardware.S_Ruleta.setPower(0);
            }

            telemetry.addData("Lift Power", armPower);
            telemetry.addData("Lift Power FINE", armPowerFINE);
            telemetry.addData("Lift Power Final", ArmPowerFinal);
            telemetry.addData("Intake Power", gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.addData("Joystick L-X", gamepad1LeftX);
            telemetry.addData("Joystick L-Y", gamepad1LeftY);
            telemetry.addData("Joystick R-X", gamepad1RightX);
            telemetry.addData("Joystick R-Y", gamepad1RightY);
            telemetry.addData("Trigger 1-L", gamepad1LeftTrigger);
            telemetry.addData("Trigger 1-R", gamepad1RightTrigger);
            telemetry.addData("PowX", PowX);
            telemetry.addData("PowY", PowY);
            telemetry.addData("M-Power BL", hardware.M_BL.getPower());
            telemetry.addData("M-Power BR", hardware.M_BL.getPower());
            telemetry.addData("M-Power FL", hardware.M_BL.getPower());
            telemetry.addData("M-Power FR", hardware.M_BL.getPower());
            telemetry.addData("---------", "---------");
            telemetry.addData("M_BL", hardware.M_BL.getCurrentPosition());
            telemetry.addData("M_BR", hardware.M_BR.getCurrentPosition());
            telemetry.addData("M_FL", hardware.M_FL.getCurrentPosition());
            telemetry.addData("M_FR", hardware.M_FR.getCurrentPosition());
            telemetry.addData("M_Lift", hardware.M_Lift.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}