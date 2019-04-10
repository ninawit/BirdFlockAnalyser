package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/*
* @author Nino Kelehsashvili
* Applied Computing_20074819
* Year2_Semester4
* Assignment_1
* (Repeat Learner)
*/

public class BlackAndWhiteImgController {
	/* Used to initialize global variables and FXML items used. */
	@FXML
	private BorderPane borderPane;
	@FXML
	private VBox topVBox;
	@FXML
	private VBox bottomVBox;
	@FXML
	private HBox menuHBox;
	@FXML
	private HBox infoHBox;
	@FXML
	private Button bnwButton;
	@FXML
	private ImageView imageView;
	@FXML
	private Slider slider1;
	
	public static int[] dset;
	public static int[] allPixels;
	
	PixelWriter pxWriter;
	WritableImage wrImage;
	public int sliderValue;

	 /* Initializes the pixelreader and a new writable image so the image can be edited 
	 * using the writer. The pixelreader is used to get the height and width of the 
	 * image. */
	public void changeToBlackAndWhite(ActionEvent event) {
		PixelReader pxReader = OriginalImgController.image.getPixelReader();
		 wrImage = new WritableImage(
                        (int) OriginalImgController.image.getWidth(),
                        (int) OriginalImgController.image.getHeight());
		 pxWriter = wrImage.getPixelWriter();

		for (int y = 0; y < OriginalImgController.image.getHeight(); y++) {
            for (int x = 0; x < OriginalImgController.image.getWidth(); x++) {
                Color color = pxReader.getColor( x, y );

                /* Sets the threshold of color and changes everything above that to white. 
                 * Everything else is changed to black */
                if (color.getRed() * 255 > 30 && color.getGreen() * 255 > 70 && color.getBlue() * 255 > 100) {
                    pxWriter.setColor(x, y, Color.WHITE);
                } else {
                    pxWriter.setColor(x, y, Color.BLACK );
                }
            }
        }
        /* Sets the writable image as the new image to be called */
        imageView.setImage(wrImage);
        countBirdsInImage(wrImage);
    }

	/* Uses the black and white image created as the new image and searches through 
	 * it using the pixelreader to find all pixels that are birds and stores them
	 * in an array to make disjoint sets */
	public void countBirdsInImage(Image bwImage) {
		int width = (int) bwImage.getWidth();
		/* Use the b&w image */
		PixelReader pxReader = bwImage.getPixelReader();
		/* Store all the pixels of the image in the array */
		dset = new int [ (int) (bwImage.getHeight() * bwImage.getWidth()) ];
		allPixels = dset;
		/* Index 'i' gets value 'i' */
		for (int i = 0; i < dset.length; i++) {
			dset[i] = i;
			allPixels[i] = i;
		}

		for (int y = 0; y < bwImage.getHeight(); y++) {
            for (int x = 0; x < bwImage.getWidth(); x++) {
                if ((pxReader.getArgb(x, y) & 1) == 1) { 	//white value
                	dset [(int) (bwImage.getWidth() * y + x)] = -111;
                }
            }
		}
		/* Checking itself 'i' and to the right 'i+1' 
		 * if its not equals to '-111' then add to set */
		for (int i = 0; i < dset.length; i++) {
			if (dset[i] != -111) {
				if ((i + 1) % width != 0 && dset[i + 1] != -111) {
					UnionFind.union(dset, i, i + 1);
					allPixels[i] = UnionFind.find(dset,i);
				}
				if ((i + width) < dset.length && dset [i + width] != -111) {
					UnionFind.union(dset, i, i + width);
					allPixels[i] = UnionFind.find(dset,i);
			}
		}
	}
		/* Prints the image in integers (-111 is white pixels and 
		 * all positive numbers are birds found) */
		for (int i = 0; i < dset.length; i++) {
			System.out.print(UnionFind.find(dset,i) + " ");
			if ((i + 1) % (int)(bwImage.getWidth()) == 0) System.out.println();
			/* Find the positive value compared to the slider value, if the value found is 
			 * greater than or equal to the slider value, add the values found to the array */
			if(UnionFind.find(dset,i) >= sliderValue) UnionFind.birds.add(UnionFind.find(dset,i));
		}
        	/* Used to find the pixel values found for each bird and the number of pixels
        	 * in each set. Mostly used to confirm the number of birds found in the image */
        	for(int bird : UnionFind.birds) {
        		int pxCount = 0;
        		for (int i = 0; i < dset.length; i++)
        			if (UnionFind.find(dset,i) == bird) pxCount++;
        				if(pxCount > 5) {
        				System.out.println(bird + " Pixels " + pxCount);
        					continue;
        				}				
        }
            System.out.println("\nThere are " + UnionFind.birds.size() + " birds in this image!!!!!!");

        /* Finds the birds previously found and searches for the top, bottom, left and right most 
         * pixels in the set, confirms they are the edges of the bird and sets a blue box around
         * the bird found. */
		Iterator<Integer> iter = UnionFind.birds.iterator();
		while(iter.hasNext()) {
			int root = iter.next();
			List<Integer> nodes = getListPixels(root, allPixels);

			int minX = -1;
			int maxX = -1;
			int minY = -1;
			int maxY = -1;

			for(int i = 0; i < nodes.size(); i++) {
				int nodeX = (int) (nodes.get(i) % bwImage.getWidth());
				int nodeY = (int)(nodes.get(i) / bwImage.getWidth());

				if(minX == -1 || nodeX < minX) {
					minX = nodeX;
				}
				if(maxX == -1 || nodeX > maxX) {
					maxX = nodeX;
				}
				if(minY == -1 || nodeY < minY) {
					minY = nodeY;
				}
				if(maxY == -1 || nodeY > maxY) {
					maxY = nodeY;
				}
			}
			int col = minX;
			int row = minY;
			for (row = minY; row < maxY; row++) {
				pxWriter.setColor(col, row, Color.BLUE);
			}
			row = minY;
			for (col = minX; col < maxX; col++) {
				pxWriter.setColor(col, row, Color.BLUE);
			}
			
			col = maxX;
			row = maxY;
			for (row = minY; row < maxY; row++) {
				pxWriter.setColor(col, row, Color.BLUE);
			}
			row = maxY;
			for (col = minX; col < maxX; col++) {
				pxWriter.setColor(col, row, Color.BLUE);
			}
		}
		imageView.setImage(wrImage);
	}
	/* Finds the pixels in the set and confirms that the root found is equal 
	 * to the index in the array. If it is, then the nodes are added.*/
	public static List<Integer> getListPixels(int root, int[] px){
		List<Integer> nodes = new ArrayList<>();
		for(int i = 0; i < px.length; i++) {
			if(root == px[i]) {
				nodes.add(i);
			}
		}
		return nodes;
	}
	/* Uses a slider to set the number of pixels that make up a bird.
	 * Anything less than the value is ignored */
	public void onSliderChanged() {
		System.out.print("IN SLIDER - Value is: ");
		sliderValue = (int) slider1.getValue();
		System.out.println(sliderValue + " ");
	}
}

