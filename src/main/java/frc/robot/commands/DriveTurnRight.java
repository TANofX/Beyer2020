/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drives;

public class DriveTurnRight extends CommandBase {
 
 Drives drives;

  /**
   * Creates a new DriveTurn.
   */
  public DriveTurnRight(Drives drivetrain) {

    drives = drivetrain;

    addRequirements(drives);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drives.enableSafety(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drives.tankDrive(1, -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drives.enableSafety(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
