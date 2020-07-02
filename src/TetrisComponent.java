import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisComponent extends JComponent implements KeyListener, Runnable{
    
    private final int CICLOS_PARA_DROP = 25;//ciclos necesarios antes de bajar una casilla
    
    private TetrisGrid grid;
    private int contadorDelay = 0; //cuenta la cantidad de ciclos que ha hecho desdes del drop

    
    /**
    * Contructor de clase. Hereda de JComponent
    *
    * @param width Cantidad de cuadros a lo ancho que tendrá la tabla del juego
    * @param height Cantidad de cuadros a lo alto que tendrá la tabla del juego
    */
    public TetrisComponent(int width, int height){

            super();
            
            grid = new TetrisGrid(width, height);
            setPreferredSize(new Dimension(grid.getGraphicsWidth(), grid.getGraphicsHeight()));

            addKeyListener(this);
            Thread run = new Thread(this);
            run.start();
    }

    
    //@Override
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

    
    /**
     * Refresca la pantalla y desciende la pieza un bloque cuando contadorDelay == ciclosParaDrop.
     */
    public void update(){

        contadorDelay++;
        
        if(contadorDelay == CICLOS_PARA_DROP){
            
            grid.moveDown();
            
            contadorDelay = 0;
        }
        grid.updateTetrisGrid();
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