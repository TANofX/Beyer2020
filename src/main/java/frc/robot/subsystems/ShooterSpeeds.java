/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * Add your docs here.
 */
public enum ShooterSpeeds {
LOWSPEED(2000),
MEDIUMSPEED(5000),
HIGHSPEED(7900), 
OFF(0);

private double actualSpeed;

    private ShooterSpeeds(double Speed) {
        
        actualSpeed = Speed;

    }

    public double getMotorSpeed() {
        return actualSpeed;
    }
}