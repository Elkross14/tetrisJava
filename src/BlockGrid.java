import java.awt.*;

public class BlockGrid {
    
    public static final int BLOCK_SIDE = 25;
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    private Color[][] blocks;
    private Color currentColor = BACKGROUND_COLOR;

    
    public BlockGrid(int width, int height){

        blocks = new Color[width][height];
    }

    
    public void drawSquare(int x, int y) {

        try {

        blocks[x][y] = currentColor;

        } catch(Exception ex) {
            //creamos un chatch vacio para evitar que aparezcan errores innecesarios en el log
        }
    }

    
    public void drawSquare(Point p) {

        drawSquare(p.x, p.y);
    }

    
    public void setColor(Color color) {

        currentColor = color;
    }

    
    /**
     * Nos devuleve la cantidad de cuadros de la tabla a lo ancho
     * 
     * @return Cantidad de cuadros de la tabla a lo ancho
     */
    public int getWidthTable() {

        return blocks.length;
    }

    
    /**
     * Nos devuleve la cantidad de cuadros de la tabla a lo alto
     * 
     * @return Cantidad de cuadros de la tabla a lo alto
     */
    public int getHeightTable() {

        return blocks[0].length;
    }

    
    /**
     * Multiplica la cantidad de cuadros a lo ancho con el tamaño en pixeles de un cuadro
     * 
     * @return Ancho total de la tabla en píxeles
     */
    public int getGraphicsWidth() {

        return getWidthTable()*BLOCK_SIDE;
    }

    /**
     * Multiplica la cantidad de cuadros a lo alto con el tamaño en pixeles de un cuadro
     * 
     * @return Alto total de la tabla en píxeles
     */
    public int getGraphicsHeight() {

        return getHeightTable()*BLOCK_SIDE;
    }

    public void clear() {

        blocks = new Color[blocks.length][blocks[0].length];
    }

    public void draw(Graphics g) {

        int i;
        int j;

        for(i = 0; i < blocks.length; i++) {

            for(j = 0; j < blocks[0].length; j++) {

                if(blocks[i][j] != null) {

                    g.setColor(blocks[i][j]);

                } else {

                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(i * BLOCK_SIDE, j * BLOCK_SIDE, BLOCK_SIDE - 1, BLOCK_SIDE - 1);
                    g.setColor(Color.BLACK);
                    g.drawRect(i * BLOCK_SIDE, j * BLOCK_SIDE, BLOCK_SIDE - 1, BLOCK_SIDE - 1);
                }
            }
        }
    }

    @Override
    public String toString() {

        String ret = "";
        int j;

        for(j = 0; j < blocks[0].length; j++) {

            if(j != 0) {

                ret += "\n";
            }

            for (Color[] block : blocks) {

                if (block[j] != null) {

                    ret += "#" ;
                } else {

                    ret += "-";
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        
        BlockGrid grid = new BlockGrid(10,20);
        TetrisBlock.RIGHT_L.setLocation(0,2).draw(grid);
        System.out.println(grid);
    }
}