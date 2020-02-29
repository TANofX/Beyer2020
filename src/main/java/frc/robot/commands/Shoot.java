/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {
  private Shooter shooter;
  private Revolver revolver;
  private Limelight limelight;
 
  /**
   * Creates a new Shoot.
   */
  public Shoot(Shooter shooterSubsytem, Revolver revolverSubsteym, Limelight limelightSubsystes) {
    // Use addRequirements() here to declare subsystem dependencies.

    shooter = shooterSubsytem;
    revolver = revolverSubsteym;
    limelight = limelightSubsystes;

    addRequirements(shooter, revolver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    shooter.shoot();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    shooter.stopShoot();
    revolver.shootFuelCell();
    revolver.rotateToPosition(revolver.currentPosition() + 1);
  

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    }
}