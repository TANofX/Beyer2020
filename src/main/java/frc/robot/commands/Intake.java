/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;



public class Intake extends CommandBase {
  frc.robot.subsystems.Intake intake;
  Revolver revolver;


  public Intake(frc.robot.subsystems.Intake intakeCommand, Revolver rev) {
    intake = intakeCommand;
    revolver = rev;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    intake.moveRollerDown();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    revolver.sumFuelCells();

    if ((revolver.sumFuelCells() > 3) && (intake.checkForFuel())) {

        intake.stopIntakeRollers();
    }

    else {

      intake.activateIntakeRollers();

    }

    intake.activateIntake();
    revolver.runTransit();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    intake.stopIntakeRollers();
    intake.stopIntake();
    revolver.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (revolver.sumFuelCells() == 5);
  }
}
