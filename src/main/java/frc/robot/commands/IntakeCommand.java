/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.RevolverIntakeState;

public class IntakeCommand extends CommandBase {
  frc.robot.subsystems.Intake intake;
  Revolver revolver;
  boolean stateChange;
  RevolverIntakeState lastState;

  public IntakeCommand(frc.robot.subsystems.Intake intakeCommand, Revolver rev) {
    intake = intakeCommand;
    revolver = rev;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    lastState = RevolverIntakeState.UNKOWN;
    intake.moveRollerDown();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    RevolverIntakeState currentState;
    currentState = checkState();

    stateChange = (currentState != lastState);

    SmartDashboard.putString("Current State", currentState.toString());
    SmartDashboard.putString("Last State", lastState.toString());

    if (stateChange) {
      // NOP
      // Unknown -> Revolver Turning
      // Unknown -> Revolver Full
      // Revolver Turning -> Revolver Full
      // Unknown -> Revolver Not Full Slot Full
      // Revolver Not Full Slot Empty -> Revolver Not Full Slot Full
      if (((currentState == RevolverIntakeState.REVOLVER_NF_SLOT_F) && (lastState == RevolverIntakeState.UNKOWN))
          || ((currentState == RevolverIntakeState.REVOLVER_NF_SLOT_F)
              && (lastState == RevolverIntakeState.REVOLVER_NF_SLOT_E))) {
        // Do nothing
      } else {
        // All Intake
        // Unknown -> Revolver Not Full Slot Empty
        // Revolver Turning -> Revolver Not Full Slot Empty

        // Intake No Rollers
        // Unknown -> Revolver Has 4, Intake Full, Slot Empty
        // Revolver Turning -> Revolver Has 4, Intake Full, Slot Empty

        // Extake
        // Unknown -> Revolver Full, Intake Full
        // Revolver Turning -> Revolver Full, Intake Full
        // Revolver has 4, Intake Full, Slot Empty -> Revolver Full, Intake Full
        // Anytime current state is Revolver Full, Intake Full

        // Stop Intake
        // Revolver Not Full, Slot Full -> Revolver Turning
        // Revolver has 4, Intake Full, Slot Empty -> Revolver Turning

        // Stop Extake
        // Revolver Full, Intake Full -> Revolver Full
        switch (currentState) {
        case REVOLVER_NF_SLOT_E:
          intake.activateIntakeRollers();
          intake.activateIntake();
          break;
        case REVOLVER_4_INTAKE_F_SLOT_E:
          intake.stopIntakeRollers();
          intake.activateIntake();
          break;
        case REVOLVER_F_INTAKE_F:
          intake.activateExtake();
          break;
        case REVOLVER_TURNING:
        case REVOLVER_FULL:
          intake.stopIntake();
          intake.stopIntakeRollers();
          break;
        default:
          break;
        }
      }

      lastState = currentState;
    }
    /*
     * if ((revolver.sumFuelCells() == 4) && (intake.checkForFuel())) {
     * 
     * intake.stopIntakeRollers(); }
     * 
     * else if ((revolver.sumFuelCells() == 5) && (intake.checkForFuel())) {
     * 
     * intake.activateExtake();
     * 
     * }
     * 
     * else { intake.activateIntakeRollers(); }
     * 
     * if (revolver.isRotating()) { intake.stopIntake(); }
     * 
     * else { intake.activateIntake();
     * 
     * }
     */
  }

  private RevolverIntakeState checkState() {

    RevolverIntakeState returnState = RevolverIntakeState.UNKOWN;

    if (revolver.isRotating())
      returnState = RevolverIntakeState.REVOLVER_TURNING;
    else {
      if ((revolver.sumFuelCells() == 5) && (intake.checkForFuel() == false))
        returnState = RevolverIntakeState.REVOLVER_FULL;
      else {
        if ((revolver.sumFuelCells() == 5) && (intake.checkForFuel() == true))
          returnState = RevolverIntakeState.REVOLVER_F_INTAKE_F;
        else {
          if ((revolver.sumFuelCells() == 4) && (intake.checkForFuel()) && (revolver.checkForFuel()))
            returnState = RevolverIntakeState.REVOLVER_4_INTAKE_F_SLOT_F;
          else {
            if ((revolver.sumFuelCells() == 4) && (intake.checkForFuel()) && (!revolver.checkForFuel()))
              returnState = RevolverIntakeState.REVOLVER_4_INTAKE_F_SLOT_E;
            if ((revolver.sumFuelCells() <= 4) && (revolver.checkForFuel()))
              returnState = RevolverIntakeState.REVOLVER_NF_SLOT_F;
            else {
              if ((revolver.sumFuelCells() <= 4) && (!revolver.checkForFuel()))
                returnState = RevolverIntakeState.REVOLVER_NF_SLOT_E;
            }
          }
        }
      }
    }

    return returnState;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
    intake.stopIntakeRollers();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (lastState == RevolverIntakeState.REVOLVER_FULL);
  }
}
