package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

@Autonomous(name="Blocks", group="Auto")
public class OpMode_Blocks extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    @Override
    public void runOpMode(){
        hardware.init(hardwareMap);
        hardware.InitVuforia(hardwareMap);

        //We aren't Waiting for play to be pressed
        //Only update Vuforia until play is pressed
        while (opModeIsActive()) {//Main Loop
            telemetry.addData("Visible Object", hardware.VuforiaTrack());
            telemetry.addData("Pos (cm)", "{X, Y, Z} = %.1f, %.1f, %.1f", hardware.translation.get(0), hardware.translation.get(1), hardware.translation.get(2));
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", hardware.rotation.firstAngle, hardware.rotation.secondAngle, hardware.rotation.thirdAngle);
            if(isStarted()){//If Play button has been pressed



            }else{//reset the runtime until playbutton is pressed
                runtime.reset();
            }
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
