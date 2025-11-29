package application.mainapplication;

import application.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
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

    public PieChart pieChartTotalExam;

    public Button addButton;
    public Button removeButton;

    private final XYChart.Series<String, Number> gradesSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> weightedAverageSeries = new XYChart.Series<>();
    private final ObservableList<Exam> exams = FXMLUtils.getEmptyObservableList();

    public void initialize() {
        rightBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        leftBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        VBox.setVgrow(examTable, Priority.ALWAYS);

        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);

        //--------------------------------------------------------------------------------

        //1. collego la lista e la table
        FXMLUtils.linkTableViewAndObservableList(exams,
                examTable, colName, colWeight, colGrade, colDate);

        //2 setto le regole del line chart
        timeAxisInLineChart.setGapStartAndEnd(false);
        timeAxisInLineChart.setAnimated(false);
        lineChart.setAnimated(false);


        //3 nomino la serie dei voti e la collego al grafico
        gradesSeries.setName("Voti");
        lineChart.getData().add(gradesSeries);

        //4 nomino la serie della media ponderata e la collego al grafico
        weightedAverageSeries.setName("Media ponderata");
        lineChart.getData().add(weightedAverageSeries);

        //5 aggiorno i dati
        updateDatas();
    }

    private void updateDatas() {
        FXMLUtils.commonUpdateDatas(exams, examTable);

        //CONTROLLER SPECIFIC UPDATES

        //line chart update
        updateLineChart();

        //pie chart update
        updatePieChartProgress();
    }

    private void updateLineChart() {
        updateSeriesInLineChart();
    }

    private void updateSeriesInLineChart() {
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

    private void updatePieChartProgress() {
        final int max = 180;
        int filled = UniversityManager.getTotalExamsWeight(exams);

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Filled", filled),
                new PieChart.Data("Remaining", max - filled)
        );

        pieChartTotalExam.setData(data);
    }

    public void addExamButtonOnAction(ActionEvent actionEvent) {
        openNewWindow("Aggiungi esame", "/stages/AddExamStage.fxml", "/styles/add_exam.css",
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    public void removeButtonOnAction(ActionEvent actionEvent) {
        openNewWindow("Rimuovi esame", "/stages/RemoveExamStage.fxml", "/styles/remove_exam.css",
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
