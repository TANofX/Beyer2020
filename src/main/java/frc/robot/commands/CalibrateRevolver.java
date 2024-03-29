/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Revolver;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class CalibrateRevolver extends CommandBase {
  /**
   * Creates a new CalibrateRevolver.
   */
  private Revolver revolver;

  public CalibrateRevolver(Revolver revolver) {
    // Use addequirements() here to declare subsystem dependencies.
    this.revolver = revolver;

    addRequirements(revolver);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    revolver.spinRevolver();
    revolver.runTransit();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    revolver.stopRevolver();
    revolver.calibrateRevolver();
    revolver.stopTransit();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return revolver.inCalibratePosition();
    
  }
  
}
