/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Add your docs here.
 */
public class DriveMotor extends TalonFX implements SpeedController {

    @Override
    public void disable() {
       // super.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public double get() {
        // TODO Auto-generated method stub
        return super.getMotorOutputPercent();
    }

    @Override
    public void set(double speed) {
        super.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void stopMotor() {
        super.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void pidWrite(double output) {
        this.set(output);
    }

    public DriveMotor(int deviceNumber) {
        super(deviceNumber);
    }

}
