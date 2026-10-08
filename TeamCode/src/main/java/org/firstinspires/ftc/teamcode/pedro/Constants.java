package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
         return new Follower(new PinpointLocalizer(h,localizerConfig),new Mecanum(h,driveConfig),new Foresight(foresightConfig));

    }

    public static MecanumConfig driveConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("frontLeft");
                c.backLeftName.set("backLeft");
                c.frontRightName.set("frontRight");
                c.backRightName.set("backRight");

                c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
                c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-8.606740996593567);
        c.yPodOffset.set(-0.9686305579238051);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        }
    );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.280793369194275);
                Controller secondaryTranslationalForward = Controller.proportional(0.10374564713735866);
                Controller primaryTranslationalLateral = Controller.proportional(0.35715014330520084);
                Controller secondaryTranslationalLateral = Controller.proportional(0.13195743472368246);
                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));
                c.coast.set(Controller.proportionalFeedforward(0.01825745134195873));
                c.brake.set(Controller.proportionalFeedforward(0.015518833640664788));
                c.headingFeedback.set(Controller.proportional(6.304191112112534));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.040862808420967574, 0.01203818227737477));
                c.linearBrakeCoefficients.set(Matrix.diag(0.07791606388794231, 0.05693604440358609));
                c.quadraticBrakeCoefficients.set(Matrix.diag(9.139016939723533E-4, 0.0013880997494686862));
                c.maxAchievableForwardVelocity.set(57.55782498839388);
                c.maxAchievableStrafeVelocity.set(48.2801756928782);
                c.naturalForwardDeceleration.set(34.610455775081626);
                c.naturalStrafeDeceleration.set(56.82077826115135);
            }
    );

}