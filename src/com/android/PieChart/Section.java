/**
 * This is free software.
 */
package com.android.PieChart;

import android.content.Context;
import android.view.View;

/**
 * Define a section within the pie chart.
 * @author Daniel Gomez Sierra
 *
 */
public class Section extends View{
	
	private Integer[] color;		
	private Integer value;
	private String name;
	// Where the arc start.
	private Float initialDegree;
	// Where the arc finish.
	private Float finalDegree;
	// How long the is arc.
	private Float sectionDegrees;
	
	public Section(Context context) {
		super(context);
	}

	public Integer[] getColor() {
		return color;
	}

	public void setColor(Integer[] color) {
		this.color = color;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getInitialDegree() {
		return initialDegree;
	}
	
	public void setInitialDegrees(Float initialDegrees){
		this.initialDegree = initialDegrees;
	}

	public Float getFinalDegree() {
		return finalDegree;
	}

	// To find the final degree, we add the initialdegree to the sectionDegrees.
	public void setFinalDegree(Float initialDegree, Float sectionDegrees) {
		this.finalDegree = initialDegree + sectionDegrees;
	}
	
	public Float getSectionDegrees(){
		return sectionDegrees;
	}
	public void setSectionDegrees(Float sectionDegrees){
		this.sectionDegrees = sectionDegrees;
	}
}
