import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisComponent extends JComponent implements KeyListener, Runnable{
    
    public static final int DROP_DELAY_DECRESE_SPEED = 500;
    public static final int MIN_DROP_DELAY = 3;
    private TetrisGrid grid;
    private int dropDelay = 25;
    private int delay = 0;
    private int delayDelay = 0;

    
    public TetrisComponent(int width, int height){

            super();
            grid = new TetrisGrid(width, height);
            setPreferredSize(new Dimension(grid.getGraphicsWidth(), grid.getGraphicsHeight()));

            addKeyListener(this);
            Thread run = new Thread(this);
            run.start();
    }

    
    @Override
    public void run(){
        
        while(true){

            try{

                Thread.sleep(20);//pausa la ejecucion del programa 20 milisegundos
            }
            catch(Exception ex){
                //Lo dejamos vacio para evitar que nos salten mensajes de error innecesarios
            }
            requestFocus();
            update();
            repaint();
        }
    }

    
    @Override
    public void paint(Graphics g){
        
        synchronized(g){

            grid.draw(g);
        }
    }

    
    public void update(){
        
        delayDelay = (delayDelay+1)%DROP_DELAY_DECRESE_SPEED;
        if(delayDelay == 0){
            
            dropDelay--;
            
            if(dropDelay < MIN_DROP_DELAY){

                dropDelay = MIN_DROP_DELAY;
            }
        }
        delay = (delay+1)%dropDelay;
        
        if(delay == 0){
            
            grid.moveDown();
        }
        grid.update();
    }

    
    @Override
    public void keyPressed(KeyEvent ke){
        
        switch (ke.getKeyCode()) {
            
            case KeyEvent.VK_LEFT:
                
                grid.moveLeft();
                break;
                
            case KeyEvent.VK_RIGHT:
                
                grid.moveRight();
                break;
                
            case KeyEvent.VK_DOWN:
                
                grid.moveDown();
                break;
                
            case KeyEvent.VK_UP:
                
                grid.turnRight();
                break;
                
            case KeyEvent.VK_COMMA:
                
                grid.turnLeft();
                break;
                
            case KeyEvent.VK_PERIOD:
                
                grid.turnRight();
                break;
                
            default:
                
                break;
        }
    }

    
    @Override
    public void keyReleased(KeyEvent ke){
        
    }

    
    @Override
    public void keyTyped(KeyEvent ke){
        
    }
}