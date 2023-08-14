import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Random;

public class Bloque extends Sprite{
    private boolean destruido;
    public Bloque (int x, int y) {
        initBloque(x,y);
    }

    private void initBloque(int x, int y) {
        this.x = x;
        this.y = y;
        destruido = false;
        cargarImagen();
        getDimensiones();
    }
    /* la imagen de los bloques contiene 8 bloques de diferentes colores,
        se utiliza el metodo CropImageFilter() para recortar la imagen en
        8 partes, estos se guardan en un array y con el metodo random()
        recuperamos un bloque al azar desde el array.
     */

    private void cargarImagen() {
        var original = new ImageIcon("src/recursos/imagenes/blocks.png");
        int ancho = 61;
        int alto = 28;
        int numBloques = original.getIconWidth() / ancho;
        ImageIcon[] bloques = new ImageIcon[numBloques];

        for (int i = 0; i < numBloques; i++) {
            Image imagen = original.getImage();
            CropImageFilter crop = new CropImageFilter(i * ancho, 0,
                    ancho, alto);
            Image bloqueCrop = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
                    imagen.getSource(), crop));
            bloques[i] = new ImageIcon(bloqueCrop);
        }
        Random random = new Random();
        int index = random.nextInt(numBloques);
        ImageIcon bloqueFinal = bloques[index];
        img = bloqueFinal.getImage();
    }

    boolean isDestruido(){
        return destruido;
    }
    void setDestruido(boolean valor){
        destruido = valor;
    }
}