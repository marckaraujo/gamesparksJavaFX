package com.gamespark.test;


import com.gamespark.test.controller.DashboardController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.kairos.core.ActivityFactory;

public class main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private static final String PERSISTENCE = "persistence";
    private static EntityManager manager;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    
    @Override
    public void init() throws Exception {
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start(Stage stage) throws Exception {
                
              StackPane rootNode = new StackPane(); // Create root pane
         rootNode.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    rootNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            stage.setX(event.getScreenX() - xOffset);
                            stage.setY(event.getScreenY() - yOffset);
                        }
                    });
        Scene scene = new Scene(rootNode);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Pro AK");
        stage.centerOnScreen();
        stage.setScene(scene);
         // this object represent the stack  of activities  in your application
        ActivityFactory factory = new ActivityFactory(stage);
        factory.startActivity(DashboardController.class); // start the activity
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

