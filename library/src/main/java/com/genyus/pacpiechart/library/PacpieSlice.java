package com.genyus.pacpiechart.library;

public class PacpieSlice {

	public float count;
    public int color = -1;
	public String label;

    public void count(float countValue){
        this.count = countValue;
    }

    public void color(int colorValue){
        this.color = colorValue;
    }

    public void label(String labelValue){
        this.label = labelValue;
    }
}
