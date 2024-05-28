package assignment;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    public Keys(){
        action = new Action();
    }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;  //Speed of Ship
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;   //Rotation speed
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE: //Space to shoot
                action.shoot = true;
                break;
            case KeyEvent.VK_ESCAPE:    //Esc to Escape/Quit Game
                System.exit(0);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
