package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Drive", group="Controlled")
public class OpMode_Drive extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode() {
        //Main INIT code goes here
        hardware.init(hardwareMap);


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
                Triggers - Intake Power
                A -Lift Tray Servos
                B -Lower Tray Servos
                Left Bumper- Close Claw
                Right Bumper - Open Claw
                Left Stick X - Lift
                Right Stick X - Claw Extender

             */
            int AddStep = (int)(gamepad2.left_stick_x * Configuration.StepMultiplier);
            hardware.M_LL.setTargetPosition(hardware.M_LL.getCurrentPosition() + AddStep);
            hardware.M_LR.setTargetPosition(hardware.M_LR.getCurrentPosition() + AddStep);

            hardware.Claw_Extender(gamepad2.right_stick_x);

            hardware.Intake_Power(gamepad2.right_trigger - gamepad2.left_trigger);

            if(gamepad2.a){
                hardware.Lift_Tray();
            }else if(gamepad2.b){
                hardware.Lower_Tray();
            }
            if(gamepad2.left_bumper){
                hardware.Close_Claw();
            }else if(gamepad2.right_bumper){
                hardware.Open_Claw();
            }

            telemetry.addData("Intake Power", gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.addData("Joystick L-X", gamepad1LeftX);
            telemetry.addData("Joystick L-Y", gamepad1LeftY);
            telemetry.addData("Joystick R-X", gamepad1RightX);
            telemetry.addData("Joystick R-Y", gamepad1RightY);
            telemetry.addData("Trigger 1-L", gamepad1LeftTrigger);
            telemetry.addData("Trigger 1-R", gamepad1RightTrigger);
            telemetry.addData("PowX", PowX);
            telemetry.addData("PowY", PowY);
            telemetry.addData("Lift Step", AddStep);
            telemetry.addData("Claw Extender Power", gamepad2.right_stick_x);
            telemetry.addLine();
            telemetry.addData("M-Power BL", hardware.M_BL.getPower());
            telemetry.addData("M-Power BR", hardware.M_BL.getPower());
            telemetry.addData("M-Power FL", hardware.M_BL.getPower());
            telemetry.addData("M-Power FR", hardware.M_BL.getPower());
            telemetry.addData("M-Power LL", hardware.M_LL.getPower());
            telemetry.addData("M-Power LR", hardware.M_LR.getPower());
            telemetry.addLine();
            telemetry.addData("M_BL", hardware.M_BL.getCurrentPosition());
            telemetry.addData("M_BR", hardware.M_BR.getCurrentPosition());
            telemetry.addData("M_FL", hardware.M_FL.getCurrentPosition());
            telemetry.addData("M_FR", hardware.M_FR.getCurrentPosition());
            telemetry.addData("M_LL", hardware.M_LL.getCurrentPosition());
            telemetry.addData("M_LR", hardware.M_LR.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }

}