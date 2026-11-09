package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.pedro.Constants;


@Autonomous
public class autoMovement extends LinearOpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(58.6833, 7.1056, -90);
    private final Pose path1 = poseFactory.of(83.012, 30.9912, -90);
    private final Pose point2 = poseFactory.of(28.049, 43.5338, 167.1451);
    private final Pose point3Start = poseFactory.of(28.049, 43.5338, 0);
    private final Pose point3 = poseFactory.of(8.0357, 115.9812, 0);

    private DcMotorEx shooter = null;
    private DcMotorEx shooterTwo = null;
    private DcMotor shooterIntake = null;
    Command shootBalls = Command.build();

    public int convertRpm(int rpm) {
        int conversion;
        conversion = (rpm * 28) / 60;
        return conversion;
    }


    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> {
                    shooter.setVelocity(convertRpm(3100));
                    shooterTwo.setVelocity(convertRpm(3100));
                }),
                waitMs(1000),
                follow(follower, path1()),
                instant(() -> shooterIntake.setPower(1)),
                waitMs(5000),
                follow(follower, path2()),
                follow(follower, path3())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooter2");
        shooterIntake = hardwareMap.get(DcMotor.class, "shooterIntake");

        shooter.setDirection(DcMotor.Direction.FORWARD);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterTwo.setDirection(DcMotor.Direction.REVERSE);
        shooterTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).tangent();
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }
}
