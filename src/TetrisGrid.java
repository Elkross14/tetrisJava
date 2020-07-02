import java.util.*;
import java.awt.*;

public class TetrisGrid extends BlockGrid{
    
    private Vector<TetrisBlock> lockedBlocks = new Vector();
    private TetrisBlock workingBlock;
    private int points = 0; //puntos obtenidos por el jugador

    /**
    * Contructor de clase. Hereda de BlockGrid
    *
    * @param width Cantidad de cuadros a lo ancho que tendrá la tabla del juego
    * @param height Cantidad de cuadros a lo alto que tendrá la tabla del juego
    */
    public TetrisGrid(int width, int height){

            super(width, height);
    }


    public void updateTetrisGrid(){

        if(workingBlock == null){

            setWorkingBlock(TetrisBlock.getRandomBlock().setLocation(getWidthTable()/2, 0));
        }
    }


    @Override
    public void draw(Graphics g){

        clear();

        if(workingBlock != null){

            workingBlock.draw(this);
        }
        for(int i = 0; i < lockedBlocks.size(); i++){

            lockedBlocks.get(i).draw(this);
        }
        if(g != null){

            super.draw(g);
        }

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.drawString("Points: " + points, 5, 12+5);

        if(isFilled()){

            g2.drawString("You Lose", getGraphicsWidth()/2-20, getGraphicsHeight()/2-6);
        }
    }


    /**
    * Cambia la posición del bloque
    * 
    * @param block Objeto de tipo TetrisBlock con los datos de tipo de figura, color y posición
    * @return True si el bloque se ha modificado y False en caso contrario.
    */
    public boolean setWorkingBlock(TetrisBlock block){

        if(canFit(block)){

            workingBlock = block;
            return true;
        }
        return false;
    }


    public void lockWorkingBlock(){

        points++;

        if(workingBlock != null){

            lockedBlocks.addAll(workingBlock.getSingleBlocks());
        }

        workingBlock = null;
        rowCheck();
    }


    private boolean canFit(TetrisBlock block){

        if((block.getX() < 0) || (block.getX()+block.getWidthBlock() > getWidthTable())){

            return false;
        }

        if(block.getY() >= getHeightTable()){

            return false;
        }

        for(int i = 0; i < lockedBlocks.size(); i++){

            if(lockedBlocks.get(i).overlaps(block)){

                return false;
            }
        }

        return true;
    }


    public void moveDown(){

        if(workingBlock == null){

            return;
        }

        TetrisBlock newBlock = workingBlock.moveDown();

        if(canFit(newBlock)){

            workingBlock = newBlock;
        }
        else{

            lockWorkingBlock();
        }
    }


    public void moveLeft(){

        if(workingBlock == null){

            return;
        }

        TetrisBlock newBlock = workingBlock.moveLeft();

        if(canFit(newBlock)){

            workingBlock = newBlock;
        }
    }


    public void moveRight(){

        if(workingBlock == null){

            return;
        }

        TetrisBlock newBlock = workingBlock.moveRight();

        if(canFit(newBlock)){

            workingBlock = newBlock;
        }
    }


    public void turnLeft(){

        if(workingBlock == null){

            return;
        }

        TetrisBlock newBlock = workingBlock.turnLeft();

        if(canFit(newBlock)){

            workingBlock = newBlock;
        }
    }


    public void turnRight(){

        if(workingBlock == null){

            return;
        }

        TetrisBlock newBlock = workingBlock.turnRight();

        if(canFit(newBlock)){

            workingBlock = newBlock;
        }
    }


    public boolean isFilled(){

        for(int i = 0; i < lockedBlocks.size(); i++){

            if(lockedBlocks.get(i).getY() == 0){

                return true;
            }
        }
        return false;
    }


    public boolean isRowFilled(int row){

        int blocksInRow = 0;

        for(int i = 0; i < lockedBlocks.size(); i++){

            if(lockedBlocks.get(i).getY() == row){

                blocksInRow++;
            }
        }
        return blocksInRow == getWidthTable();
    }


    public void clearRow(int row){

        for(int i = 0; i < lockedBlocks.size(); i++){

            if(lockedBlocks.get(i).getY() == row){

                lockedBlocks.remove(i);
                i--;
            }
            else if(lockedBlocks.get(i).getY() < row){

                lockedBlocks.add(i,lockedBlocks.get(i).moveDown());
                lockedBlocks.remove(i+1);
            }
        }
    }


    public void rowCheck(){

        for(int row = 1; row < getHeightTable(); row++){

            if(isRowFilled(row)){

                clearRow(row);
            }
        }
    }

    public static void main(String[] args){

        TetrisGrid grid = new TetrisGrid(10,20);
        System.out.println(grid.setWorkingBlock(TetrisBlock.RIGHT_L.setLocation(10,10)));
        Scanner s = new Scanner(System.in);

        while(true){

            grid.updateTetrisGrid();
            grid.draw(null);
            System.out.println(grid);
            String line = s.nextLine();

            switch (line) {
                case "":
                    grid.moveDown();
                    break;

                case "<":
                    grid.moveLeft();
                    break;

                case ">":
                    grid.moveRight();
                    break;

                case "(":
                    grid.turnLeft();
                    break;

                case ")":
                    grid.turnRight();
                    break;

                default:
                    break;
            }
        }
    }
}
