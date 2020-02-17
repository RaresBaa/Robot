package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "Servo setting", group = "Base")
public class OpMode_ServoSetting extends LinearOpMode {

    @Override
    public void runOpMode() {
        Servo servo;
        servo = hardwareMap.get(Servo.class, "servo");
        double pos = 0.5;
        servo.setPosition(pos);
        telemetry.addData("Info", "Started");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.dpad_up) {
                pos += 0.1f;
            }else if(gamepad1.dpad_down) {
                pos -= 0.1f;
            }else if(gamepad1.dpad_right) {
                pos += 0.01f;
            }else if(gamepad1.dpad_left) {
                pos -= 0.01f;
            }
            try {
                Thread.sleep(400);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            servo.setPosition(pos);
            telemetry.addData("Servo pos:","%.3f", pos);
            telemetry.update();
        }
    }
}
