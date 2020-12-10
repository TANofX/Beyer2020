/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;

public class BallCount extends CommandBase  {
  /**
   * Creates a new BallCount.
   */

  private Revolver revolver;
  private int timesRun;
  public BallCount(Revolver rev) {
    // Use addRequirements() here to declare subsystem dependencies.
    revolver = rev;

    addRequirements(revolver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timesRun = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if ((timesRun <= 4) && revolver.positionCheck()) {

    revolver.intakeFuelCell();
    revolver.rotateToPosition(revolver.currentPosition() + 1);
    timesRun++;

    }

    if (timesRun < 4) {


    }
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    revolver.stopRevolver();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (timesRun > 4) && (revolver.positionCheck());
  }
}
