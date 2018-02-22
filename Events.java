
import objectdraw.*;
import java.awt.*;
import java.util.Random;

/**
 * Erika Sklaver 
 * Lab 3 - Sorting Laundry
 * Section 2
 * 02/11/15
 * 
 * The user will see 3 laundry baskets labeled whites, darks and colors, and will sort random colored pieces of laundry 
 * into the correct basket. The user drags the clothing into the basket and if he/she correctly sorts the clothing
 * the number of correctly sorted pieces of laundry will increase by one, and a new piece of laundry appears. If the user
 * incorrectly sorts the laundry,the clothing will return to its original spot and the number of incorrectly sorted pieces
 * of laundry will increase by one. Additionally if the user is incorrect, information about the laundry will appear to 
 * help the user. If the user drags the clothing into empty area, the clothing will simply return to it's original spot. 
 * 
 */
public class Events extends FrameWindowController {

//placement of laundry basket and label display    
private FramedRect whites; 
private FramedRect darks; 
private FramedRect colors; 
private static final int SQUARE_SIDE = 100; 
private static final int BOX_LOC_Y = 100; 
private static final int WHITES_LOC_X = 20;
private static final int DARKS_LOC_X = 140; 
private static final int COLORS_LOC_X = 260; 
private static final int GAP1 = 25; 
private static final int GAP2 = 50;

//clothing placement and size
private FilledRect clothes; 
private FramedRect clothesoutline;
private static int CLOTHES_X_LOC = 100;
private static int CLOTHES_Y_LOC = 20;
private static int CLOTHES_SIDE = 50;

//random clothing color
private Random generator; 
private int colorCode1;
private int colorCode2;
private int colorCode3;
private Color color; 
private static final int MAX_COLOR_VALUE = 255;
private static final int DARK_VALUE = 230; 
private static final int WHITE_VALUE = 600;

//placement of correct and incorrect text
private static final int CORRECTX = 20;
private static final int CORRECTY = 275;
private static final int INCORRECTX = 230;
private static final int INCORRECTY = 275;

//allows for display of the counters and for the counters to increase at the appropriate time
private int correctCounter = 0;
private int incorrectCounter = 0; 
private Text correctDisplay;
private Text incorrectDisplay; 
private static final int CORRECTX_COUNTER = 75;
private static final int CORRECTY_COUNTER = 275;
private static final int INCORRECTX_COUNTER = 285;
private static final int INCORRECTY_COUNTER = 275; 

//Last position of mouse while dragging 
 private Location lastPoint; 
       
 //remembers whether the clothes were touched when the button was pressed
private boolean clothesGrabbed = true; 

//allows for information to be given when the user sorts the clothes in the wrong basket 
private static final int WRONG_X = 75; 
private static final int WRONG_Y = 350; 
private static final int NUMBERS_Y = 425; 
private Text colorInfo;
private Text moreColorInfo; 


public void begin() {
        // The following are executed when the program beings
        
        //creates the three labeled laundry baskets 
        resize (500,500); 
        whites = new FramedRect(WHITES_LOC_X, BOX_LOC_Y, SQUARE_SIDE, SQUARE_SIDE, canvas); 
        darks = new FramedRect(DARKS_LOC_X, BOX_LOC_Y, SQUARE_SIDE, SQUARE_SIDE, canvas); 
        colors = new FramedRect(COLORS_LOC_X, BOX_LOC_Y, SQUARE_SIDE, SQUARE_SIDE, canvas);
        new Text("whites", WHITES_LOC_X+GAP1, BOX_LOC_Y+GAP2, canvas); 
        new Text("darks", DARKS_LOC_X+GAP1, BOX_LOC_Y+GAP2, canvas);
        new Text("colors", COLORS_LOC_X+GAP1, BOX_LOC_Y+GAP2, canvas);
        
        //creates the clothes box with a random color
        clothes = new FilledRect(CLOTHES_X_LOC, CLOTHES_Y_LOC, CLOTHES_SIDE, CLOTHES_SIDE, canvas);
        clothesoutline = new FramedRect(CLOTHES_X_LOC, CLOTHES_Y_LOC, CLOTHES_SIDE, CLOTHES_SIDE, canvas);
        generator = new Random (); 
        colorCode1 = generator.nextInt(MAX_COLOR_VALUE); 
        colorCode2 = generator.nextInt(MAX_COLOR_VALUE);
        colorCode3 = generator.nextInt(MAX_COLOR_VALUE);
        color = new Color (colorCode1, colorCode2, colorCode3); 
        clothes.setColor(color); 
        
        //creates the initial display of text seen in the window 
        correctDisplay = new Text("correct = " + correctCounter, CORRECTX, CORRECTY, canvas);
        incorrectDisplay = new Text("incorrect = " + incorrectCounter,INCORRECTX, INCORRECTY, canvas); 
        
        //the color infomation that will pop up if the user sorts the laundry incorrectly but will not appear initially 
        colorInfo = new Text("",WRONG_X, WRONG_Y, canvas); 
        moreColorInfo = new Text("Dark: 0-229 Colors:230-600 Whites: 601-765",WRONG_X, NUMBERS_Y, canvas);
        moreColorInfo.hide();
        
    }

    public void onMousePress(Location point) {
        
        //allows clothes to be dragged around the screen when the user presses on the clothes
        lastPoint = point; 
        clothesGrabbed = clothes.contains(point); 
      
    }


    public void onMouseRelease(Location point) {
        
        //If the clothes color is dark and the user releases the mouse inside the dark basket
        //a new colored clothing will appear in the original clothing spot
       if (darks.contains(point) && 
           clothesGrabbed && 
           (colorCode1+colorCode2+colorCode3)< DARK_VALUE){
           //If the clothing color is dark and the user drops off the clothes in the dark basket... 
           
           //a new random colored clothing will appear 
           generator = new Random (); 
           colorCode1 = generator.nextInt(MAX_COLOR_VALUE); 
           colorCode2 = generator.nextInt(MAX_COLOR_VALUE);
           colorCode3 = generator.nextInt(MAX_COLOR_VALUE);
           color = new Color (colorCode1, colorCode2, colorCode3); 
           clothes.setColor(color);
                 
           //the new clothing will appear in its original spot     
           clothes.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
           clothesoutline.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
                 
           //the number of correctly sorted laundry will increase by 1      
           correctCounter = correctCounter+1;
           correctDisplay.setText("correct = " + correctCounter);
                 
           //If there was previous information regarding the color, it is hidden from the canvas     
           colorInfo.hide(); 
           moreColorInfo.hide(); 
               
       } else if(whites.contains(point) && 
                 clothesGrabbed && 
                 (colorCode1+colorCode2+colorCode3)>WHITE_VALUE){
           //If the clothing color is whites and the user drops off the clothes in the white basket...
                
           //a new random colored clothing will appear
           generator = new Random (); 
           colorCode1 = generator.nextInt(MAX_COLOR_VALUE); 
           colorCode2 = generator.nextInt(MAX_COLOR_VALUE);
           colorCode3 = generator.nextInt(MAX_COLOR_VALUE);
           color = new Color (colorCode1, colorCode2, colorCode3); 
           clothes.setColor(color);
                 
           //the new clothing will appear in its original spot
           clothes.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
           clothesoutline.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
                 
           //the number of correctly sorted laundry will increase by 1 
           correctCounter = correctCounter+1;
           correctDisplay.setText("correct = " + correctCounter);
                 
           //If there was previous information regarding the color, it is hidden from the canvas 
           colorInfo.hide();
           moreColorInfo.hide(); 
                 
       } else if (colors.contains(point) && 
                  clothesGrabbed &&
                  (colorCode1+colorCode2+colorCode3)>DARK_VALUE &&
                  (colorCode1+colorCode2+colorCode3)<WHITE_VALUE){
           //If the clothing is colored and the user drops off the clothes in the colors basket...
                  
           //a new random colored clothing will appear    
           generator = new Random (); 
           colorCode1 = generator.nextInt(MAX_COLOR_VALUE); 
           colorCode2 = generator.nextInt(MAX_COLOR_VALUE);
           colorCode3 = generator.nextInt(MAX_COLOR_VALUE);
           color = new Color (colorCode1, colorCode2, colorCode3); 
           clothes.setColor(color);
                 
           //the new clothing will appear in its original spot
           clothes.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
           clothesoutline.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
                 
           //the number of correctly sorted laundry will increase by 1 
           correctCounter = correctCounter+1;
           correctDisplay.setText("correct = " + correctCounter);
                 
           //If there was previous information regarding the color, it is hidden from the canvas
           colorInfo.hide();
           moreColorInfo.hide(); 
                 
       } else if (!colors.contains(point) &&
                !whites.contains(point) &&
                !darks.contains(point)){
            //If the user drops off the clothing in empty space (not in a basket) it will return to its original place
            clothes.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
            clothesoutline.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
                
                
            //This is next section is for when the user drops the clothing in the wrong basket. 
            //It will increase the number of incorrectly sorted laundry by 1
            //And information on the colors number code and the range for whites, darks and colors will appear
            } else if (((colorCode1+colorCode2+colorCode3)< DARK_VALUE &&
                       clothesGrabbed &&
                       (whites.contains(point)||colors.contains(point)))||
                              ((colorCode1+colorCode2+colorCode3)> WHITE_VALUE &&
                              clothesGrabbed &&
                              (darks.contains(point)||colors.contains(point)))||
                                        ((colorCode1+colorCode2+colorCode3)>DARK_VALUE && 
                                        (colorCode1+colorCode2+colorCode3)<WHITE_VALUE &&
                                        clothesGrabbed &&
                                        ((whites.contains(point)||darks.contains(point))))){
            //When the user drops the clothing in the wrong basket it will increase the number of correctly 
            //sorted laundry by 1 and the information on the colors number code and the range for whites,
            //darks and colors will appear. 
            incorrectCounter = incorrectCounter+1;
            incorrectDisplay.setText("incorrect = "+incorrectCounter); 
            colorInfo.setText("Color = (" +colorCode1+", "+colorCode2+", "+colorCode3+")");
            colorInfo.show();
            moreColorInfo.show();   
            clothes.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);
                 clothesoutline.moveTo(CLOTHES_X_LOC, CLOTHES_Y_LOC);

        }
           
    }

    public void onMouseDrag(Location point) {
        
        //allows the clothing to be moved as the mouse is dragged wherever the user clicks on the clothing 
        if (clothesGrabbed) {
            clothes.move(point.getX()-lastPoint.getX(), point.getY()-lastPoint.getY());
            clothesoutline.move(point.getX()-lastPoint.getX(), point.getY()-lastPoint.getY());
            lastPoint = point; 
        }
        
    }

}
