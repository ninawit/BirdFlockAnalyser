package application;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/*
* @author Nino Kelehsashvili
* Applied Computing_20074819
* Year2_Semester4
* Assignment_1
* (Repeat Learner)
*/

public class OriginalImgController {
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
	private Button uploadButton;
	@FXML
	private Button editButton;
	@FXML
	private Button closeButton;
	@FXML
	private ImageView imageView;
	
	public static Image image;
	
	/* Used to load an image into the imageview and sets the size of the image
	 * then returns it as the main image to be used */
	public Image uploadImage(ActionEvent event) {
		FileChooser fc = new FileChooser();
		Window ownerWindow = null;
		File selectedFile = fc.showOpenDialog(ownerWindow);
				
		if(selectedFile !=null) {
			image = new Image(selectedFile.toURI().toString(), 250,250,true,true);
			imageView.setImage(image);
		}else {
			System.out.println("Not a valid file.");
		}
		return image;
	}
	/* Adds the action to edit the image and use the methods found in BlackAndWhiteImgController*/
	public void editImage(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("BlackAndWhiteImg.fxml"));
			BorderPane layout = loader.load();
			Scene scene = new Scene(layout);
			Stage stage = new Stage();
			stage.setTitle("Black and White Image");
			stage.setScene(scene);
			stage.show();
			}catch (Exception e) {
				System.out.println("Can't load new window.");
			}			
	}
	/* Closes the Image from the imageview and by setting it to null*/
	public void closeImage(ActionEvent event) {
		if(imageView != null) {
			imageView.setImage(null);
		}else {
			System.out.println("No image imported");
		}	
	}	
}
