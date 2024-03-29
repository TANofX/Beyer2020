/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


//Gabe made this - We don't know what we are doing and we basically just coppied all of this from Philo2019
//And it is probably all just old code that doesn't work     ----  WE NEED HELP
//test.


package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drives;

public class DriveForward extends CommandBase {
  
  Drives drives;
  double distance;

  public DriveForward(Drives drivetrain, double inches) {

    drives = drivetrain;

    addRequirements(drives);
    distance = inches;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drives.enableSafety(false);
    drives.moveXInches(distance);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    SmartDashboard.putNumber("Remaining Distance", distance - drives.inchesMoved());
    SmartDashboard.putNumber("Inches Moved", drives.inchesMoved());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drives.enableSafety(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drives.isMoveXInchesFinished(distance);
  }
}
