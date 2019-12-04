package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo setting", group = "Base")
public class ServoSetting extends LinearOpMode {

    private static final double INCREMENT = 0.01;
    private static final int CYCLE_MS = 50;
    private static final double MAX_POS = 1.0;
    private static final double MIN_POS = 0.0;
    private double position = (MAX_POS - MIN_POS) / 2;
    private boolean rampUp = true;

    @Override
    public void runOpMode() {
        Servo servo = hardwareMap.get(Servo.class, "");
        telemetry.addData("Info", "Started");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if (rampUp) {
                position += INCREMENT;
                if (position >= MAX_POS) {
                    position = MAX_POS;
                    rampUp = !rampUp;
                } else {
                    position -= INCREMENT;
                    if (position <= MIN_POS) {
                        position = MIN_POS;
                        rampUp = !rampUp;
                    }
                }
                telemetry.addData("Servo Position", "%5.2f", position);
                telemetry.update();
                servo.setPosition(position);
                sleep(CYCLE_MS);
                idle();
            }
            telemetry.addData(">", "Done");
            telemetry.update();
        }
    }
}
