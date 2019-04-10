package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/*
* @author Nino Kelehsashvili
* Applied Computing_20074819
* Year2_Semester4
* (Repeat Learner)
*/

/* This is a WIP. Some issues/bugs found are listed below, as well as parts that are incomplete.
 * BUGS/ISSUES:
 * 1. Slider to change the values of how many pixels make up a bird seems to only work correcly
 *    the first time the slider value is set. Afterwards, the slider seems to be ignored until
 *    scenebuilder slider value is changed, saved and loaded again.
 * 2. The blue boxes draw slightly incorrectly around the bird. They work, but sometimes 
 *    ignore parts of the bird and cut through the middle or edges of the bird.
 * 3. The sets counted are close, but still not exact when the bird sizes are all different. The
 * 	  sample image 10x10 counts correctly and draws boxes correctly
 * 4. Due to the way the images are loaded, the images can be stretched or compressed, causing
 *    blurriness. This is possibly because Writable image is used from the beginning instead of
 *    buffered image. Once I noticed the issue, changing the entire code was too complex, so I 
 *    worked around it by setting the image sizes.
 * 
 * UNFINISHED PARTS:
 * 1. JUnit testing was not done as I ran out of time.
 * 2. Benchmarking was not done.
 * 3. Scanning the image and adding numbers to the boxes was not completed, however, I still added 
 *    the count in the console.
 * 4. Counting number of birds in clusters was not fully completed, however, the way the code is 
 * 	  written allows for a limit of bird size, so it still sometimes counts multiple birds in a 
 * 	  cluster.
 * 5. Noise and outlier management is not fully completed with a slider, but is still managed on 
 *    a smaller scale using color limits in RGB. Some images that have a rapid change in color 
 *    (like an image with a dramatic sunset/sunrise/brightness chage cannot account for the color
 *    change)
 * 6. There was no work done on image analysis when trying for identification of shapes, flock 
 * 	  formation or other details about the birds in the image.
 */

public class Main extends Application {
	private Stage primaryStage;
	// Set the stage and the Title of the app.
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Main Window");
		showMainView();
	}
	// Load the FXML file and create a new scene in the stage. 
	// Also load initial scenebuilder as a new window
	private void showMainView() throws Exception {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Welcome.fxml"));
		BorderPane mainLayout = loader.load();
	    Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
}