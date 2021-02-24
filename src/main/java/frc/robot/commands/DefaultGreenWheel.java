/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GreenWheel;
import frc.robot.subsystems.Revolver;

public class DefaultGreenWheel extends CommandBase {
  /**
   * Creates a new DefaultGreenWheel.
   */
  GreenWheel greenWheel;
  Revolver revolver;
  boolean wasRotatating;

  public DefaultGreenWheel(GreenWheel greenWheel, Revolver rev) {
    // Use addRequirements() here to declare subsystem dependencies.
    revolver = rev;
    this.greenWheel = greenWheel;

    addRequirements(greenWheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wasRotatating = revolver.isRotating();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean isRotating;
    isRotating = revolver.isRotating();

  if (wasRotatating != isRotating){
    if (isRotating) {
      greenWheel.runTransit();
    } else {
      greenWheel.stopTransit();
    }
  }
  wasRotatating = isRotating;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    greenWheel.stopTransit();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
