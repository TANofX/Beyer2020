/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.JoystickCurvatureDrive;
import frc.robot.commands.JoystickTankDrive;
import frc.robot.commands.MoveHood;
import frc.robot.subsystems.Drives;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterSpeeds;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final Joystick m_stick2 = new Joystick(Constants.STICK_2);
  private final Joystick m_stick = new Joystick(Constants.STICK);
  private final Joystick m_xbox = new Joystick(Constants.XBOX);

  private final JoystickButton m_ReverseTrue = new JoystickButton(m_xbox, Constants.REVERSIT_TRUE);
  private final JoystickButton m_ReverseFalse = new JoystickButton(m_xbox, Constants.REVERSEIT_FALSE);

  private final JoystickButton driveTestButton = new JoystickButton(m_xbox, 8);

  private final JoystickButton spinRevolver = new JoystickButton(m_stick, Constants.SPIN_REVOLVER);
  private final JoystickButton spinHigh = new JoystickButton(m_stick, Constants.HIGH_SHOOTER_SPEED);
  private final JoystickButton spinMedium = new JoystickButton(m_stick, Constants.MEDIUM_SHOOTER_SPEED);
  private final JoystickButton spinLow = new JoystickButton(m_stick, Constants.LOW_SHOOTER_SPEED);
  private final JoystickButton spinStop = new JoystickButton(m_stick, Constants.STOP_SHOOTER);
  private final JoystickButton hoodUp = new JoystickButton(m_stick, Constants.HOOD_UP);
  private final JoystickButton hoodDown = new JoystickButton(m_stick, Constants.HOOD_DOWN);


  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Drives m_Drives = new Drives();

  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final JoystickCurvatureDrive m_CurvatureCommand = new JoystickCurvatureDrive(m_xbox, m_Drives);
  private final JoystickTankDrive m_TankDrive = new JoystickTankDrive(m_stick, m_stick2, m_Drives);

  private final Shooter m_Shooter = new Shooter();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    m_ReverseTrue.whenPressed(() -> m_Drives.reverseIt(true));
    m_ReverseFalse.whenPressed(() -> m_Drives.reverseIt(false));

    driveTestButton.whenPressed(()-> m_Drives.moveXInches(36));

    //spinRevolver.whenPressed(()-> Revolver.SpinRevolver());
    spinHigh.whenPressed(()-> m_Shooter.spinPrimaryMotor(ShooterSpeeds.HIGHSPEED));
    spinMedium.whenPressed(()-> m_Shooter.spinPrimaryMotor(ShooterSpeeds.MEDIUMSPEED));
    spinLow.whenPressed(()-> m_Shooter.spinPrimaryMotor(ShooterSpeeds.LOWSPEED));
    spinStop.whenPressed(()-> m_Shooter.stop());

    hoodUp.whileHeld(new MoveHood(m_Shooter, true));
    hoodDown.whileHeld(new MoveHood(m_Shooter, false));


    CommandScheduler.getInstance().setDefaultCommand(m_Drives, m_CurvatureCommand);

    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
 // public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
  }

