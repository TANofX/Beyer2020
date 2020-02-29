/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drives;
import frc.robot.subsystems.Limelight;

public class FollowTarget extends CommandBase {
  private Limelight limelight;
  private Drives drivebase;
  private double tx;
  private double thor;
  private double turnRange;
  private double distanceRange;
  private double thorTarget;

  /**
   * Creates a new FollowTarget.
   */
  public FollowTarget(Limelight eyes, Drives move, double spinKp, double spinKi, double spinMin, double driveKp, double driveKi, double driveMin, int targetWidth, double allowedAngle, double allowedDistance) {
    limelight = eyes;
    drivebase = move;
    Kp = spinKp;
    Ki = spinKi;
    min_command = spinMin;
    dKp = driveKp;
    dKi = driveKi;
    min_forward = driveMin;

    turnRange = allowedAngle;
    distanceRange = allowedDistance;

    thorTarget = targetWidth;

    addRequirements(drivebase);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  double dKp = 0.0;
  double dKi = 0.0;
  double Kp = 0.01;
  double Ki = 0.00001;
  double errorAccumulator = 0;
  double driveAccumulator = 0;
  double min_command = 0.15;
  double min_forward = 0.15;
  double distance_error = 0;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tx = limelight.getTargetingValue("tx");
    thor = limelight.getTargetingValue("thor");
    distance_error = thorTarget - thor;
    double heading_error = Math.abs(tx);
    double steering_adjust = 0.0f;
    errorAccumulator += heading_error;
    driveAccumulator += distance_error;

    steering_adjust = (min_command + Kp * heading_error + Ki * errorAccumulator);//Kp*heading_error - min_command;
    double drive_speed = (dKp * distance_error + dKi * driveAccumulator + (Math.signum(distance_error) * min_forward));

    double left_command = 1.0 * drive_speed;
    double right_command = -1.0 * drive_speed;
    if (Math.signum(tx) < 0) {
      right_command -= steering_adjust;
    } else if (Math.signum(tx) > 0) {
      left_command +=
       steering_adjust;      
    }
     drivebase.tankDrive(left_command, right_command);

     SmartDashboard.putNumber("tx", tx);
     SmartDashboard.putNumber("THOR", thor);
     SmartDashboard.putNumber("left", left_command);
     SmartDashboard.putNumber("right", right_command);
     SmartDashboard.putNumber("adjust", steering_adjust);
     SmartDashboard.putNumber("ErrorAccum", errorAccumulator);
     SmartDashboard.putNumber("DriveErrorAccum", driveAccumulator);
     SmartDashboard.putNumber("TurnIContrib", errorAccumulator * Ki);
     SmartDashboard.putNumber("DriveIContrib", driveAccumulator * dKi);
    }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    drivebase.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((Math.abs(tx) <= turnRange) && (distance_error <= distanceRange) || (limelight.getTargetingValue("tv") == 0));
  }

public static void whileHeld(Object object) {
}
}
