/**
 * This is free software.
 */
package com.android.PieChart;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.ArcShape;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * This class represent the pie chart.
 * @author Daniel Gomez Sierra
 *
 */
public class PieChart extends View {

	private final static int PIE_CHART_SIZE = 200;
	private final static int CIRCLE_DEGRESS = 360;
	private final static int NUMBER_SECTIONS = 10;
	public ArrayList<Section> sections = new ArrayList<Section>();
	private Context context;
	
	
	public PieChart(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.TRANSPARENT);
		canvas.drawPaint(paint);
		
		float initialDegrees = 0;
		fillPieChart();
		
		// Draw the sections.
		for(Section section: sections){
			
			// Draw a section.
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setARGB(section.getColor()[0],section.getColor()[1],section.getColor()[2],section.getColor()[3]);
			paint.setShadowLayer(10.5f, 1.2f, 1.2f, Color.BLACK);
			ArcShape arco = new ArcShape(initialDegrees, section.getSectionDegrees());
			arco.resize(PIE_CHART_SIZE, PIE_CHART_SIZE);
			arco.draw(canvas, paint);
			
			section.setInitialDegrees(initialDegrees);
			section.setFinalDegree(section.getInitialDegree(), section.getSectionDegrees());
						
			// Draw a stroke section.
			paint.setAntiAlias(true);
			paint.setStrokeWidth(0.5f);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			ArcShape contornoArco = new ArcShape(initialDegrees, section.getSectionDegrees());
			contornoArco.resize(PIE_CHART_SIZE, PIE_CHART_SIZE);
			contornoArco.draw(canvas, paint);
			
			initialDegrees += section.getSectionDegrees();			
		}
	}	
	
	/**
	 * Calculate the section degrees using the rule of three
	 * @param sectionValue	Section's value
	 * @param sectionsTotalValue Sum of sections.
	 * @return Section's degrees.
	 */
	private float calculateDegreesSection(float sectionValue, float sectionsTotalValue){
		
		// Rule of three to identify the length of any arc.
		return	(CIRCLE_DEGRESS * sectionValue) / sectionsTotalValue; 	
	}
	
	/**
	 * Sum the section's values.
	 * @return	The sum of the values.
	 */
	private float sumSectionsValues(){
		
		float total = 0.0f;		
		for(Section section: sections){
			
			total += section.getValue().floatValue();
		}		
		return total;
	}
	
	/**
	 * Fill all the sections in the pie chart.
	 */
	private void fillPieChart(){
		
		// Contain the 	ARGB values for the sections.
		Integer[][] colors = {{255,0,191,255},		
							   {255,50,205,50},		
							   {255,178,34,34},		
							   {255,255,140,0},		
							   {255,255,215,0},		
							   {255,220,220,220},	
							   {255,139,69,19},		
							   {255,106,90,205},	
							   {255,60,179,113},	
							   {255,0,206,209}};	
		
		int colorIndex = 0;
		
		// A random value for the section.
		Random randomGenerator = new Random();
		sections.clear();
		
		Section section;
		for(int i = 0; i < NUMBER_SECTIONS; ++i){
			
			section = new Section(context);
			section.setColor(colors[colorIndex]);
			section.setValue(randomGenerator.nextInt(200));
			section.setName("Section " + i);
			sections.add(section);
			
			// If the last index dosen't exist, reset the index.
			if(colorIndex + 1 < colors.length){
				colorIndex++;
			}
			else{
				colorIndex = 0;
			}
		}
		
		float sectionsTotalValue = sumSectionsValues();
		
		for(Section seccionLlena: sections){
			
			//
			seccionLlena.setSectionDegrees(calculateDegreesSection(seccionLlena.getValue(), sectionsTotalValue));
		}
	}
	
	/**
	 * Validate if a section is touched or return null instead.
	 * @param posX	X position selected.
	 * @param posY	Y position selected.
	 * @return Touched section.
	 */
	private Section touchedSection(float posX, float posY){
		
		// Calculate the degrees of the touch position (read the atan2 documentation).
		float degreesTouch = (float) Math.toDegrees(Math.atan2(posY, posX));
		
		// Calculate if the touch is inside the whole pie chart with the formula x^2 + y^2 <= r^2.
		float distance = (float)(Math.pow((double)posX, 2.0) + Math.pow((double)posY, 2.0));
		
		float radio = PIE_CHART_SIZE / 2.0F;
		
		if( distance <= (radio * radio)){
			if(degreesTouch < 0){
				degreesTouch += CIRCLE_DEGRESS;
			}
			
			for(Section section: sections){
				if(section.getInitialDegree() < degreesTouch && degreesTouch < section.getFinalDegree()){
					
					return section;
				}
			}
			
		}
		return null;
	}
	
	/**
	 * Method that it's launched if the user touch a section.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent evento){
		float posX = evento.getX() - PIE_CHART_SIZE / 2.0F;
		float posY = evento.getY() - PIE_CHART_SIZE / 2.0F;
		Section section = touchedSection(posX, posY);
		
		if(section != null){
			
			// Show a message if the user touch a section.
			String mensaje = "You've selected the " + section.getName();
			Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
			toast.show();	
		}
		return false;
	}
}
