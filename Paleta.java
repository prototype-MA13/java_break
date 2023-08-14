import javax.swing.*;
import java.awt.event.KeyEvent;

public class Paleta extends Sprite{
    private int dx;
    private int velocidad = 2;
    public Paleta () {
        iniciarPaleta();
    }

    private void iniciarPaleta() {
        cargarImagen();
        getDimensiones();
        reiniciarEstado();
    }
    private void cargarImagen() {
        var ii = new ImageIcon("src/recursos/imagenes/paddle.png");
        img = ii.getImage();
    }
    // La paleta solo se mueve de manera horizontal.
    // Por lo que solamente se actualiza X.
    void mover(){
        x += dx * velocidad;

        if (x <= 0) {
            x = 0;
        }

        if (x >= Ajustes.ANCHO - anchoImg) {
            x = Ajustes.ANCHO - anchoImg;
        }
    }
    public void masVelocidad(int incremento) {
        velocidad += incremento;
    }
    void keyPresionada(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT){
            dx = -1;
        }
        if (key == KeyEvent.VK_RIGHT){
            dx = 1;
        }
    }
    void keyLiberada(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT){
            dx = -0;
        }
        if (key == KeyEvent.VK_RIGHT){
            dx = 0;
        }
    }
    private void reiniciarEstado() {
        x = Ajustes.PALETA_X;
        y = Ajustes.PALETA_Y;
    }
}
