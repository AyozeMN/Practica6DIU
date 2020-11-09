package com.mycompany.practica6;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class UtilsPractica6 {
    private static BufferedImage clonarImagen(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public static BufferedImage seleccionarComponentes(BufferedImage img, boolean c_red, boolean c_green, boolean c_blue){
        int mask = 0x000000;
        if (c_red) mask = mask | 0xFF0000;
        if (c_green) mask = mask | 0x00FF00;
        if (c_blue) mask = mask | 0x0000FF;
        
        BufferedImage img_copy = clonarImagen(img);
        for (int j=0;j<img.getHeight();j++){
            for (int i=0;i<img.getWidth();i++){
                img_copy.setRGB(i, j, mask & img.getRGB(i, j));
            }
        }
        return img_copy;
    }
    
    private Mat umbralizar(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris, imagenUmbralizada, umbral, 255, Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }
}
