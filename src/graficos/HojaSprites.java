package graficos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
//dafd5531-6836-4780-b83e-e8f2a23441d1
public class HojaSprites {
    private final int ancho;
    private final int alto;
    public final int[] pixeles;

    //Coleccion de hojas de sprites:
    //No funciona ruta:
    public static HojaSprites desierto = new HojaSprites("/texturas/desierto.png",320,320);
    //fin de la coleccion

    public HojaSprites(final String ruta, final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;
        pixeles = new int[ancho*alto];

        BufferedImage imagen;
        try {
            imagen = ImageIO.read(HojaSprites.class.getResourceAsStream(ruta));
            imagen.getRGB(0,0,ancho,alto,pixeles,0,ancho);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getAncho(){
        return ancho;
    }
}
