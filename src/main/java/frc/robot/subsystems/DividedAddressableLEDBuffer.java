/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/**
 * Add your docs here.
 */
public class DividedAddressableLEDBuffer {
    private int ledLength = 0;
    private int start = 0;
    private AddressableLEDBuffer parentBuffer;
    private boolean reversed = false;

    public DividedAddressableLEDBuffer(AddressableLEDBuffer actualBuffer, int startPos, int length) {
        start = startPos;
        ledLength = length;

        parentBuffer = actualBuffer;
    }

    public DividedAddressableLEDBuffer(AddressableLEDBuffer actualBuffer, int startPos, int length, boolean reverse) {
        this(actualBuffer, startPos, length);

        reversed = reverse;
    }

    public int getLength() {
        return ledLength;
    }

    public void setHSV(int index, int h, int s, int v) {
        int setPos = getPosition(index);

        parentBuffer.setHSV(setPos, h, s, v);
    }

    public void setLED(int index, Color color) {
        int setPos = getPosition(index);

        parentBuffer.setLED(setPos, color);
    }

    public void setLED(int index, Color8Bit color) {
        int setPos = getPosition(index);

        parentBuffer.setLED(setPos, color);
    }

    public void setRGB(int index, int r, int g, int b) {
        int setPos = getPosition(index);

        parentBuffer.setRGB(setPos, r, g, b);
    }

    private void checkLength(int index) {
        if (index > ledLength) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private int getPosition(int index) {
        checkLength(index);
        
        if (reversed) {
            return (start + ledLength - 1) - index;
        } else {
            return start + index;
        }
    }
}
