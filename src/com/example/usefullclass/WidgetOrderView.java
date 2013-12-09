package com.example.usefullclass;

public class WidgetOrderView  implements Comparable<WidgetOrderView>{
	
	private String v;
	private String widgetId;
	private int  widget_order_id = 0;

	@Override
	public int compareTo(WidgetOrderView otherCam) {
        if (otherCam.getWidget_order_id() > this.widget_order_id)
            return -1;
	    else if (otherCam.getWidget_order_id() < this.widget_order_id)
	        return 1;
	    else
	        return 0;
	}
	
	public String getV() {
		return v;
	}
	
	public void setV(String v) {
		this.v = v;
	}
	
	public int getWidget_order_id() {
		return widget_order_id;
	}

	public void setWidget_order_id(int widget_order_id) {
		this.widget_order_id = widget_order_id;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	
	
	
}
