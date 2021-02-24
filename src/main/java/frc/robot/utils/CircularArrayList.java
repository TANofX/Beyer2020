/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Add your docs here.
 */
public class CircularArrayList<E> extends ArrayList<E> {

    private int fixedLength = 0;
	
	protected CircularArrayList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CircularArrayList(Collection<? extends E> c) {
		super(c);
		fixedLength = c.size();
	}

	public CircularArrayList(int initialCapacity) {
		super(initialCapacity);
		fixedLength = initialCapacity;
	}

	@Override
	public E get(int arg0) {
		int pos = coerceIndex(arg0);

		return super.get(pos);
	}

	@Override
	public E set(int arg0, E arg1) {
		int pos = coerceIndex(arg0);
		
		return super.set(pos, arg1);
	}
	
	@Override
	public boolean add(E o) {
		if (super.size() < fixedLength) {
			return super.add(o);
		}
		
		return false;
	}
	
	@Override
	public void add(int index, E element) {
		int pos = coerceIndex(index);
		if (super.size() < fixedLength) {
			super.add(pos, element);
		}
	}

	private int coerceIndex(int index){
		int pos = index % fixedLength;
		if (index <0){
			pos = pos + fixedLength;
		}
		return pos;
	}
}
