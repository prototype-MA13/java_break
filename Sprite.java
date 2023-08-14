import java.awt.*;

public class Sprite {
    int x, y;
    int anchoImg, altoImg;
    Image img;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAnchoImg() {
        return anchoImg;
    }

    public void setAnchoImg(int anchoImg) {
        this.anchoImg = anchoImg;
    }

    public int getAltoImg() {
        return altoImg;
    }

    public void setAltoImg(int altoImg) {
        this.altoImg = altoImg;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    Rectangle getRec(){
        return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
    }

    void getDimensiones(){
        anchoImg = img.getWidth(null);
        altoImg = img.getHeight(null);
    }
}
