/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class JoystickAxisButton extends Trigger {

    private Joystick stick;
    private int axis;
    private boolean isInverted = false;

    public JoystickAxisButton(Joystick dave, int trigger, boolean isInverted){

        this(dave, trigger);
        this.isInverted = isInverted;

    }

    public JoystickAxisButton(Joystick dave, int trigger){

        stick = dave;
        axis = trigger;
    }

    public boolean get(){

        if (isInverted == false) {
            return (stick.getRawAxis(axis) > 0.5);
        }
        else return (stick.getRawAxis(axis) < 0.5);

    }
}