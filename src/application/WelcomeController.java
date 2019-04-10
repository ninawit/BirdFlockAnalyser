package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
* @author Nino Kelehsashvili
* Applied Computing_20074819
* Year2_Semester4
* (Repeat Learner)
*/

public class WelcomeController {
	
	@FXML
	private BorderPane borderPane;
	@FXML
	private Button welcomeButton;
	
	/* Sets the actions to be used in the welcome screen */
	public void welcomButton(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("OriginalImg.fxml"));
			BorderPane layout = loader.load();
			Scene scene = new Scene(layout);
			Stage stage = new Stage();
			stage.setTitle("Original Image");
			stage.setScene(scene);
			stage.show();
			}catch (Exception e) {
				System.out.println("Can't load new window.");
			}			
	 }
}

	

