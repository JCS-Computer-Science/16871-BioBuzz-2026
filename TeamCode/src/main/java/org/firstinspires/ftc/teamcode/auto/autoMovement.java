package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;


@Autonomous
public class autoMovement extends OpMode{

    private Follower followerOne;

    @Override
    public void init() {
        followerOne = Constants.create(hardwareMap);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }
}
