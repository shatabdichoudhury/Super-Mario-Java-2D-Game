package com.TETOSOFT.input;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class InputManager implements KeyListener, MouseListener,
    MouseMotionListener, MouseWheelListener
{
    
    public static final Cursor INVISIBLE_CURSOR =
        Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(""),
            new Point(0,0),
            "invisible");

    private static final int NUM_MOUSE_CODES = 9;

    private static final int NUM_KEY_CODES = 600;

    private GameAction[] keyActions =
        new GameAction[NUM_KEY_CODES];
    private GameAction[] mouseActions =
        new GameAction[NUM_MOUSE_CODES];

    private static Mouse mouse;
    //private Point mouseLocation;
    private Point centerLocation;
    private Component comp;
    private Robot robot;
    private boolean isRecentering;

  
    public InputManager(Component comp) {
        this.comp = comp;
        mouse = new Mouse();
        centerLocation = new Point();

        comp.addKeyListener(this);
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
        comp.addMouseWheelListener(this);

        comp.setFocusTraversalKeysEnabled(false);
    }


    public void setCursor(Cursor cursor) 
    {
        comp.setCursor(cursor);
    }


    public void setRelativeMouseMode(boolean mode) 
    {
        if (mode == isRelativeMouseMode()) {
            return;
        }

        if (mode) {
            try {
                robot = new Robot();
                recenterMouse();
            }
            catch (AWTException ex) {
                
                robot = null;
            }
        }
        else {
            robot = null;
        }
    }


    public boolean isRelativeMouseMode() {
        return (robot != null);
    }


    public void mapToKey(GameAction gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }


    public void mapToMouse(GameAction gameAction,
        int mouseCode)
    {
        mouseActions[mouseCode] = gameAction;
    }
    
    private synchronized void recenterMouse() {
        if (robot != null && comp.isShowing()) {
            centerLocation.x = comp.getWidth() / 2;
            centerLocation.y = comp.getHeight() / 2;
            SwingUtilities.convertPointToScreen(centerLocation,
                comp);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }

    private GameAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    private GameAction getMouseButtonAction(MouseEvent e) {
        int mouseCode = mouse.getMouseButtonCode(e);
        if (mouseCode != -1) {
             return mouseActions[mouseCode];
        }
        else {
             return null;
        }
    }


    // from the KeyListener interface
    public void keyPressed(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


    // from the KeyListener interface
    public void keyReleased(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


    // from the KeyListener interface
    public void keyTyped(KeyEvent e) {
        // make sure the key isn't processed for anything else
        e.consume();
    }


    // from the MouseListener interface
    public void mousePressed(MouseEvent e) {
        GameAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
    }


    // from the MouseListener interface
    public void mouseReleased(MouseEvent e) {
        GameAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
    }


    // from the MouseListener interface
    public void mouseClicked(MouseEvent e) {
        // do nothing
    }


    // from the MouseListener interface
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseListener interface
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseMotionListener interface
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseMotionListener interface
    public synchronized void mouseMoved(MouseEvent e) {
        // this event is from re-centering the mouse - ignore it
        if (isRecentering &&
            centerLocation.x == e.getX() &&
            centerLocation.y == e.getY())
        {
            isRecentering = false;
        }
        else {
            int dx = e.getX() - mouse.getMouseLocationX();
            int dy = e.getY() - mouse.getMouseLocationY();
            mouseHelper(mouse.MOUSE_MOVE_LEFT, mouse.MOUSE_MOVE_RIGHT, dx);
            mouseHelper(mouse.MOUSE_MOVE_UP, mouse.MOUSE_MOVE_DOWN, dy);

            if (isRelativeMouseMode()) {
                recenterMouse();
            }
        }

        mouse.setMouseLocationX(e.getX());
        mouse.setMouseLocationY(e.getY());
    }


    // from the MouseWheelListener interface
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseHelper(mouse.MOUSE_WHEEL_UP, mouse.MOUSE_WHEEL_DOWN,
            e.getWheelRotation());
    }

    private void mouseHelper(int codeNeg, int codePos,
        int amount)
    {
        GameAction gameAction;
        if (amount < 0) {
            gameAction = mouseActions[codeNeg];
        }
        else {
            gameAction = mouseActions[codePos];
        }
        if (gameAction != null) {
            gameAction.press(Math.abs(amount));
            gameAction.release();
        }
    }

}
