package univr.ing.carconfigurator;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdministratorAddCarThirdViewController {
    @FXML
    private Button openImage;
    @FXML
    private Button confirmImg;
    @FXML
    private Button ExitButton;

    private int counter = 0;
    private MainController mainController;

    public AdministratorAddCarThirdViewController () {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void ExitButton(ActionEvent actionEvent) {
    }

    public void submitLoadedImg(ActionEvent actionEvent) {
    }

    @FXML
    protected void onOpenNewImage() {
        /*
        Stage stage = new Stage();
        File selectedFile = new FileChooser().showOpenDialog(stage);
        String imgPath = selectedFile.getPath();
        Path inputPath = Paths.get(imgPath);
        Path outputDirectory = Paths.get("img/Usato/"+user.getUserID()+"/");
        Path outputPath = Paths.get(outputDirectory+"/"+user.getUserName()+counter+".jpg");


        try {
            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }

            Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (counter) {
            case 0:
                setCarImg(String.valueOf(outputPath), topLeft);
                break;
            case 1:
                setCarImg(String.valueOf(outputPath), topRight);
                break;
            case 2:
                setCarImg(String.valueOf(outputPath), centerLeft);
                break;
            case 3:
                setCarImg(String.valueOf(outputPath), centerRight);
                break;
            case 4:
                setCarImg(String.valueOf(outputPath), bottomLeft);
                break;
            case 5:
                setCarImg(String.valueOf(outputPath), bottomRight);
                confirmImg.setDisable(false);
                break;
            default:
                System.out.println("Impossibile caricare immagini.");
                break;
        }
        counter++;
    }

       */
    }
}
