/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class GreenWheel extends SubsystemBase {
  /**
   * Creates a new GreenWheel.
   */
  private CANSparkMax collectorTransit;
  private CANPIDController collectorController;
  private int startCount = 0;
  
  public GreenWheel() {
 collectorTransit = new CANSparkMax(Constants.COLLECTOR_TRANSIT_MOTOR, MotorType.kBrushless);
 collectorController = collectorTransit.getPIDController();
    collectorController.setP(0.00000125);
    collectorController.setI(0.00000025);
    collectorController.setD(0.0);
    collectorController.setFF(0.0);
    collectorController.setSmartMotionMaxVelocity(4200.0, 0);
    collectorController.setSmartMotionMinOutputVelocity(0.0, 0);
    collectorController.setSmartMotionMaxAccel(15000.0, 0);
    collectorController.setSmartMotionAllowedClosedLoopError(0.0, 0);
    collectorController.setOutputRange(-1.0, 1.0);
  }

  public void runTransit(){
    collectorController.setReference(Constants.COLLECTOR_TRANSIT_MOTOR_SPEED, ControlType.kVelocity);
    startCount++;
  }

  public void stopTransit() {
      startCount--;

      if (startCount == 0) {
       // collectorTransit.stopMotor();
       collectorController.setReference(0, ControlType.kVelocity);
      } else if (startCount < 0) {
        startCount = 0;
      }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Green Wheel", startCount);
  }
}