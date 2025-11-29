package application.mainapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import universitymanager.Exam;
import universitymanager.UniversityManager;

import java.io.IOException;

public class ExamsManagerController {

    public BorderPane mainPane;
    public VBox leftBox;
    public VBox rightBox;

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, Number> colGrade;
    public TableColumn<Exam, String> colDate;

    public LineChart<String, Number> lineChart;
    public CategoryAxis timeAxisInLineChart;
    public NumberAxis gradeAxisInLineChart;

    public Button addButton;
    public Button removeButton;

    private final XYChart.Series<String, Number> gradesSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> weightedAverageSeries = new XYChart.Series<>();
    private final ObservableList<Exam> exams = FXCollections.observableArrayList();

    public void initialize() {
        rightBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        leftBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        VBox.setVgrow(examTable, Priority.ALWAYS);

        colName.prefWidthProperty().bind(examTable.widthProperty().multiply(0.50));
        colWeight.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colGrade.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colDate.prefWidthProperty().bind(examTable.widthProperty().multiply(0.30));

        examTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //--------------------------------------------------------------------------------

        //1. collego la lista e la table
        linkTableViewAndList(exams);

        //2 nomino la serie dei voti e la collego al grafico
        gradesSeries.setName("Voti");
        lineChart.getData().add(gradesSeries);

        //3 nomino la serie della media ponderata e la collego al grafico
        weightedAverageSeries.setName("Media ponderata");
        lineChart.getData().add(weightedAverageSeries);

        //4 aggiorno i dati
        updateDatas();
    }

    private void updateDatas() {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());

        updateGradesSeries();
        updateWeightedAverageSeries();
    }

    private void updateGradesSeries() {
        gradesSeries.getData().clear();

        for (Exam exam : exams) {
            gradesSeries.getData().add(
                    new XYChart.Data<>(exam.getDate(), exam.getGrade())
            );
        }

    }

    private void updateWeightedAverageSeries() {
        weightedAverageSeries.getData().clear();

        double numerator = 0;
        double denominator = 0;

        for (Exam exam : exams) {
            numerator += exam.getWeight() * exam.getGrade();
            denominator += exam.getWeight();

            weightedAverageSeries.getData().add(
                    new XYChart.Data<>(exam.getDate(), numerator / denominator)
            );
        }
    }

    private void linkTableViewAndList(ObservableList<Exam> exams) {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colWeight.setCellValueFactory(c -> c.getValue().weightProperty());
        colGrade.setCellValueFactory(c -> c.getValue().gradeProperty());
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());

        examTable.setItems(exams);
    }

    public void addExamButtonOnAction(ActionEvent actionEvent) {
        openNewWindow("Aggiungi esame", "/universitymanager/AddExamView.fxml", "/styles/add_exam.css",
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    public void removeButtonOnAction(ActionEvent actionEvent) {
        openNewWindow("Rimuovi esame", "/universitymanager/RemoveExamView.fxml", "/styles/remove_exam.css",
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    private void openNewWindow(String windowName, String windowPath, String cssPath, Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource(cssPath).toExternalForm()
            );

            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);

            //Callback: cosa fare quando la finestra viene chiusa
            stage.setOnHidden(e -> {
                if (onClose != null) {
                    onClose.run();
                }
            });

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
