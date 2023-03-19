import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 40; row < 100; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 157; col < 197; col++)
      {
        count++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println(count);
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }

  public void keepOnlyBlue() {
    Pixel[][] pixels = getPixels2D();
    Pixel pixel = null;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        pixel = pixels[row][col];
        pixel.setRed(0);
        pixel.setGreen(0);
      }
    }
  }

  public void negate() {
    Pixel[][] pixels = getPixels2D();
    Pixel pixel = null;
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixel = pixels[i][j];
        pixel.setRed(255 - pixel.getRed());
        pixel.setGreen(255 - pixel.getGreen());
        pixel.setBlue(255 - pixel.getBlue());
      }
    }
  }

  public void grayscale() {
    Pixel[][] pixels = getPixels2D();
    Pixel pixel = null;
    int total = 0;
    int avg = 0;
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        total = 0;
        pixel = pixels[i][j];
        total = total + pixel.getRed();
        total = total + pixel.getGreen();
        total = total + pixel.getBlue();
        avg = total / 3;
        pixel.setColor(new Color(avg, avg, avg));
      }
    }
  }

  public void fixUnderwater() {
    Pixel[][] pixels = getPixels2D();
    Pixel pixel = null;
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixel = pixels[i][j];
        pixel.setRed(pixel.getRed() * 3);
      }
    }
  }

  public void mirrorVerticalRightToLeft() {
    Pixel[][] pixels = getPixels2D();
    Pixel left = null;
    Pixel right = null;
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length / 2; j++) {
        left = pixels[i][j];
        right = pixels[i][pixels[0].length - j - 1];
        left.setColor(right.getColor());
      }
    }
  }

  public void mirrorHorizontal() {
    Pixel[][] pixels = getPixels2D();
    Pixel top = null;
    Pixel bot = null;
    for (int i = 0; i < pixels.length / 2; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        top = pixels[i][j];
        bot = pixels[pixels.length - i - 1][j];
        bot.setColor(top.getColor());
      }
    }
  }

  public void mirrorHorizontalBotToTop() {
    Pixel[][] pixels = getPixels2D();
    Pixel top = null;
    Pixel bot = null;
    for (int i = 0; i < pixels.length / 2; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        top = pixels[i][j];
        bot = pixels[pixels.length - i - 1][j];
        top.setColor(bot.getColor());
      }
    }
  }

  public void mirrorDiagonal() {
    Pixel[][] pixels = getPixels2D();
    Pixel left = null;
    Pixel right = null;
    int max = pixels.length;
    if (pixels[0].length < max) {
      max = pixels[0].length;
    }
    for (int i = 1; i < max; i++) {
      for (int j = 0; j < i; j++) {
        left = pixels[i][j];
        right = pixels[j][i];
        right.setColor(left.getColor());
      }
    }
  }

  public void mirrorArms() {
    Pixel top = null;
    Pixel bot = null;
    Pixel[][] pixels = getPixels2D();

    for (int row = 155; row < 191; row++) {
      for (int col = 98; col < 169; col++) {
        top = pixels[row][col];
        bot = pixels[191 - row + 191][col];
        bot.setColor(top.getColor());
      }
    }

    for (int row = 155; row < 191; row++) {
      for (int col = 238; col < 296; col++) {
        top = pixels[row][col];
        bot = pixels[191 - row + 191][col];
        bot.setColor(top.getColor());
      }
    }
  }

  public void mirrorGull() {
    int mirrorPoint = 350;
    Pixel left = null;
    Pixel right = null;
    Pixel[][] pixels = getPixels2D();

    for (int row = 255; row < 332; row++) {
      for (int col = 219; col < mirrorPoint; col++) {
        left = pixels[row][col];
        right = pixels[row][mirrorPoint - col + mirrorPoint];
        right.setColor(left.getColor());
      }
    }
  }
} // this } is the end of class Picture, put all new methods before this
