/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;



public class RevolverIntake extends CommandBase {
  /**
   * Creates a new RevolverIntake.
   */

   private Revolver revolver;
  public RevolverIntake(Revolver rev) {
    // Use addRequirements() here to declare subsystem dependencies.
    revolver = rev;
    addRequirements(revolver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if ( revolver.intakeFuelCell()){
      int next = revolver.nextEmptyPosition();
   revolver.rotateToPosition(next);
  }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (revolver.positionCheck()) {
     if ( revolver.intakeFuelCell()){
         int next = revolver.nextEmptyPosition();
      revolver.rotateToPosition(next);
     }
    }
    
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    revolver.stopIntake();
    revolver.stopTransit();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((revolver.sumFuelCells() == 5) && (revolver.positionCheck()));
  }
}
