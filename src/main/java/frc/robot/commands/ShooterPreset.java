/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterSpeeds;

public class ShooterPreset extends CommandBase {
  /**
   * Creates a new ShooterPreset.
   */
  private Shooter shooter;
  private ShooterSpeeds shooterSpeeds;
  private double shooterHoodAngle;

  public ShooterPreset(Shooter shoot, ShooterSpeeds speed, double hoodAngle) {
    shooter = shoot;
    shooterSpeeds = speed;
    shooterHoodAngle = hoodAngle;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.spinPrimaryMotor(shooterSpeeds);
    shooter.moveHood(shooterHoodAngle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (shooter.atSpeed()&& (shooter.hoodInPosition())) {
      return true;
    }
    else 
    return false;
    }
}
