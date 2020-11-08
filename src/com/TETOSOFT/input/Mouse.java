package com.TETOSOFT.input;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Mouse {
	
    protected final int MOUSE_MOVE_LEFT = 0;
    protected final int MOUSE_MOVE_RIGHT = 1;
    protected final int MOUSE_MOVE_UP = 2;
    protected final int MOUSE_MOVE_DOWN = 3;
    protected final int MOUSE_WHEEL_UP = 4;
    protected final int MOUSE_WHEEL_DOWN = 5;
    protected final static int MOUSE_BUTTON_1 = 6;
    protected final static int MOUSE_BUTTON_2 = 7;
    protected final static int MOUSE_BUTTON_3 = 8;

    private Point mouseLocation;
    
    public Mouse() {
    	mouseLocation = new Point();
    }
    
    public Point getMouseLocation() {
    	return mouseLocation;
    }
    
    public int getMouseLocationX() {
    	return mouseLocation.x;
    }
    
    public int getMouseLocationY() {
    	return mouseLocation.y;
    }
    
    public void setMouseLocationX(int x) {
    	mouseLocation.x = x;
    }
    
    public void setMouseLocationY(int y) {
    	mouseLocation.y = y;
    }
    
    public String getMouseName(int mouseCode) {
        switch (mouseCode) {
            case MOUSE_MOVE_LEFT: return "Mouse Left";
            case MOUSE_MOVE_RIGHT: return "Mouse Right";
            case MOUSE_MOVE_UP: return "Mouse Up";
            case MOUSE_MOVE_DOWN: return "Mouse Down";
            case MOUSE_WHEEL_UP: return "Mouse Wheel Up";
            case MOUSE_WHEEL_DOWN: return "Mouse Wheel Down";
            case MOUSE_BUTTON_1: return "Mouse Button 1";
            case MOUSE_BUTTON_2: return "Mouse Button 2";
            case MOUSE_BUTTON_3: return "Mouse Button 3";
            default: return "Unknown mouse code " + mouseCode;
        }
    }
    
    public static int getMouseButtonCode(MouseEvent e) {
        switch (e.getButton()) {
           case MouseEvent.BUTTON1:
               return MOUSE_BUTTON_1;
           case MouseEvent.BUTTON2:
               return MOUSE_BUTTON_2;
           case MouseEvent.BUTTON3:
               return MOUSE_BUTTON_3;
           default:
               return -1;
       }
    }
}
