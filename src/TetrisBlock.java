import java.awt.Color;
import java.util.Vector;

public class TetrisBlock{
    
    //Figura I
    public static final TetrisBlock LONG = new TetrisBlock(
        new boolean[][]{{true, true, true, true}}, Color.RED);

    //Figura J
    public static final TetrisBlock LEFT_L = new TetrisBlock(new boolean[][]{
        {true, true, true}, {true, false, false}}, Color.GREEN);

    //Figura T
    public static final TetrisBlock TEE = new TetrisBlock(
        new boolean[][]{{true, true, true}, {false, true, false}}, Color.BLUE);

    //Figura L
    public static final TetrisBlock RIGHT_L = new TetrisBlock(
        new boolean[][]{{true, true, true}, {false, false, true}}, Color.CYAN);

    //Figuro O o cuadrado
    public static final TetrisBlock SQUARE = new TetrisBlock(
        new boolean[][]{{true, true}, {true, true}}, Color.BLACK);

    private boolean[][] blocks;
    private Color color;

    // ubicación de la esquina inferior izquierda
    private int x, y;

    
    
    private TetrisBlock(boolean[][] blocks, Color color){
        
        this.blocks = blocks;
        this.color = color;
    }

    
    /**
     * Constructor de clase. Guarda en un objeto el tipo de figura, color y posición de este.
     * 
     * @param blocks Tipo de figura
     * @param color Color de la figura
     * @param x Posición en altura del bloque de abajo a la izquierda de la figura
     * @param y Posición en anchura del bloque de abajo a la izquierda de la figura
     */
    private TetrisBlock(boolean[][] blocks, Color color, int x, int y){
        
        this(blocks, color);
        this.x = x;
        this.y = y;
    }

    
    /**
     * Genera una pieza aleatoria entre todas las que tentemos
     * 
     * @return Devuelve el nombre de la pieza a mostrar
     */
    public static TetrisBlock getRandomBlock(){
        
        switch((int)(Math.random()*5)){

            case 0: return LONG;
            case 1: return LEFT_L;
            case 2: return TEE;
            case 3: return RIGHT_L;
            case 4: return SQUARE;
        }
        return null;
    }

    
    public int getX(){
        
        return x;
    }

    
    public int getY(){
            
        return y;
    }

    
    /**
     * Devuelve el ancho de la figura en movimiento
     * 
     * @return Anchura en bloques de la figura en movimiento
     */
    public int getWidthBlock(){
            
        return blocks.length;
    }
    

    /**
     * Devuelve el alto de la figura en movimiento
     * 
     * @return Altura en bloques de la figura en movimiento
     */
    public int getHeightBlock(){
        
        return blocks[0].length;
    }

    
    /**
     * Nos devuelve la posición que tiene la fiugura en el tablero. Se toma
     * como referencia el bloque de abajo a la izquierda del tablero.
     * 
     * @param x Posición de anchura de la tabla empezando desde la izquierda.
     * @param y Posición de altura en la tabla empezando desde arriba.
     * @return La posición del bloque de más abajo y a la izquierda de la figura.
     */
    public TetrisBlock setLocation(int x, int y){
            
        return new TetrisBlock(blocks, color, x, y);
    }

    
    public TetrisBlock moveDown(){
            
        return new TetrisBlock(blocks, color, x, y+1);
    }

    
    public TetrisBlock moveLeft(){
        
        return new TetrisBlock(blocks, color, x-1, y);
    }

    
    public TetrisBlock moveRight(){
        
        return new TetrisBlock(blocks, color, x+1, y);
    }

    
    public boolean overlaps(int x, int y){
        
        for(int i = 0; i < blocks.length; i++){

            for(int j = 0; j < blocks[0].length; j++){

                if(blocks[i][j]){

                    if((this.x+i == x) && (this.y-blocks[0].length+1+j == y)){

                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    public boolean overlaps(TetrisBlock other){
        
        for(int i = 0; i < blocks.length; i++){

            for(int j = 0; j < blocks[0].length; j++){

                if(blocks[i][j]){

                    if(other.overlaps(x+i, y-blocks[0].length+1+j)){

                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    public TetrisBlock turnRight(){
        
        boolean[][] newBlocks = new boolean[blocks[0].length][blocks.length];
        
        for(int i = 0; i < blocks.length; i++){
            
            for(int j = 0; j < blocks[0].length; j++){
                
                newBlocks[blocks[0].length-1-j][i] = blocks[i][j];
            }
        }
        return new TetrisBlock(newBlocks, color, x, y);
    }

    
    public TetrisBlock turnLeft(){
        
        boolean[][] newBlocks = new boolean[blocks[0].length][blocks.length];
        
        for(int i = 0; i < blocks.length; i++){
            
            for(int j = 0; j < blocks[0].length; j++){

                newBlocks[j][blocks.length-1-i] = blocks[i][j];
            }
        }
        return new TetrisBlock(newBlocks, color, x, y);
    }

    
    @Override
    public String toString(){
        
        String ret = "";
        for(int j = 0; j < blocks[0].length; j++){

            ret+=(j!=0)?"\n":"";
            
            for(int i = 0; i < blocks.length; i++){

                if(blocks[i][j]){

                    ret+="#";
                }
                else{

                    ret+=" ";
                }
            }
        }
        return ret;
    }

    public static TetrisBlock getSingleBlock(int x, int y, Color color){
        
            return new TetrisBlock(new boolean[][]{{true}}, color, x, y);
    }

    
    public Vector<TetrisBlock> getSingleBlocks(){
        
        Vector<TetrisBlock> parts = new Vector();

        for(int i = 0; i < blocks.length; i++){

            for(int j = 0; j < blocks[0].length; j++){

                if(blocks[i][j]){

                    parts.add(getSingleBlock(x+i,y-blocks[0].length+1+j, color));
                }
            }
        }
        return parts;
    }
    

    public void draw(BlockGrid grid){
        
        grid.setColor(color);

        for(int i = 0; i < blocks.length; i++){

            for(int j = 0; j < blocks[0].length; j++){

                if(blocks[i][j]){

                    grid.drawSquare(x+i,y-blocks[0].length+1+j);
                }
            }
        }
    }

    
    public static void main(String[] args){
        
        TetrisBlock a = TetrisBlock.SQUARE.setLocation(2,2);
        TetrisBlock b = TetrisBlock.SQUARE.setLocation(5,2);
        System.out.println(a.overlaps(b));
    }
}