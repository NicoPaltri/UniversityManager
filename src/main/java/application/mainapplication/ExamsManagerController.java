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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universitymanager.Exam;
import universitymanager.UniversityManager;

import java.io.IOException;
import java.text.DecimalFormat;

public class ExamsManagerController {

    public BorderPane mainPane;
    public VBox leftBox;
    public VBox rightBox;

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, Number> colGrade;
    public TableColumn<Exam, String> colDate;

    public HBox chartsRow1;

    public LineChart<String, Number> lineChart;
    public CategoryAxis timeAxisInLineChart;
    public NumberAxis gradeAxisInLineChart;
    public VBox lineChartBox;

    public PieChart pieChartTotalExam;
    public VBox pieChartBox;
    public Label pieChartLabel;

    public Button addButton;
    public Button removeButton;

    public Label aritmethicMeanLabel;
    public Label weightedMeanLabel;
    public Label medianLabel;

    public Label modeLabel;
    public Label standardDeviationLabel;
    public Label varianceLabel;
    public Label weightedMeanLastFiveExamsLabel;

    private final XYChart.Series<String, Number> gradesSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> weightedAverageSeries = new XYChart.Series<>();
    private final ObservableList<Exam> exams = FXMLUtils.getEmptyObservableList();

    private static final DecimalFormat formatter = new DecimalFormat("0.00");


    public void initialize() {
        // LEFT: 40% fisso, RIGHT: tutto il resto
        leftBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        rightBox.prefWidthProperty().bind(
                mainPane.widthProperty().subtract(leftBox.widthProperty())
        );

        VBox.setVgrow(examTable, Priority.ALWAYS);

        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);
        FXMLUtils.linkTableViewAndObservableList(exams,
                examTable, colName, colWeight, colGrade, colDate);

        // ---- LINE CHART SETTINGS ----
        lineChart.setAnimated(false);
        timeAxisInLineChart.setGapStartAndEnd(false);
        timeAxisInLineChart.setAnimated(false);

        // proporzione 70/30 tra lineChartBox e pieChartBox nella fascia superiore
        chartsRow1.widthProperty().addListener((obs, oldW, newW) -> {
            double w = newW.doubleValue();
            lineChartBox.setPrefWidth(w * 0.70);
            pieChartBox.setPrefWidth(w * 0.30);
        });

        // i grafici riempiono i rispettivi box
        VBox.setVgrow(lineChartBox, Priority.ALWAYS);
        VBox.setVgrow(pieChartBox, Priority.ALWAYS);
        VBox.setVgrow(lineChart, Priority.ALWAYS);
        VBox.setVgrow(pieChartTotalExam, Priority.ALWAYS);

        lineChart.prefWidthProperty().bind(lineChartBox.widthProperty());
        lineChart.prefHeightProperty().bind(lineChartBox.heightProperty());

        pieChartTotalExam.prefWidthProperty().bind(pieChartBox.widthProperty());
        pieChartTotalExam.prefHeightProperty().bind(pieChartBox.heightProperty());

        // ---- LABELS: niente troncamenti ----
        aritmethicMeanLabel.setWrapText(true);
        weightedMeanLabel.setWrapText(true);
        medianLabel.setWrapText(true);
        modeLabel.setWrapText(true);
        standardDeviationLabel.setWrapText(true);
        varianceLabel.setWrapText(true);
        weightedMeanLastFiveExamsLabel.setWrapText(true);

        // ---- SERIE LINE CHART ----
        gradesSeries.setName("Voti");
        weightedAverageSeries.setName("Media ponderata");
        lineChart.getData().addAll(gradesSeries, weightedAverageSeries);

        // ---- DATI INIZIALI ----
        updateDatas();
    }


    private void updateDatas() {
        FXMLUtils.commonUpdateDatas(exams, examTable);

        //CONTROLLER SPECIFIC UPDATES

        //line chart update
        updateLineChart();

        //pie chart update
        updatePieChartProgress();

        //update labels
        updateLabels();
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
        final double max = 180;
        double filled = UniversityManager.getTotalExamsWeight(exams);

        double filledPercentage = filled / max * 100;
        double remainingPercentage = 100 - filledPercentage;

        pieChartLabel.setText(formatter.format(filledPercentage) + "%");

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Filled (" + formatter.format(filledPercentage) + "%)", filled),
                new PieChart.Data("Remaining (" + formatter.format(remainingPercentage) + "%)", max - filled)
        );

        pieChartTotalExam.setData(data);
    }

    private void updateLabels() {
        double arithmeticMean = UniversityManager.getArithmeticMeanFromExamList(exams);
        aritmethicMeanLabel.setText(formatter.format(arithmeticMean));

        double weightedMean = UniversityManager.getWeightedMeanFromExamList(exams);
        weightedMeanLabel.setText(formatter.format(weightedMean));

        double median = UniversityManager.getMedianFromExamList(exams);
        medianLabel.setText(formatter.format(median));

        int mode = UniversityManager.getModeFromExamList(exams);
        modeLabel.setText(String.valueOf(mode));

        double standardDeviation = UniversityManager.getStandardDeviationFromExamList(exams);
        standardDeviationLabel.setText(formatter.format(standardDeviation));

        double variance = UniversityManager.getVarianceFromExamList(exams);
        varianceLabel.setText(formatter.format(variance));

        double weightedMeanLastFiveExams = UniversityManager.getWeightedMeanOfLastFiveExamsFromList(exams);
        weightedMeanLastFiveExamsLabel.setText(formatter.format(weightedMeanLastFiveExams));
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

            //css
            scene.getStylesheets().add(
                    getClass().getResource(cssPath).toExternalForm()
            );

            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);

            //blocco la finestra principale
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());

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
