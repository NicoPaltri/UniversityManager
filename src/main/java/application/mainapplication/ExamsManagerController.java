package application.mainapplication;

import application.FXMLUtils;
import dbmanager.examsTable.DBExamsStartTable;
import dbmanager.settingsTable.DBSettingsInterrogation;
import dbmanager.settingsTable.DBSettingsStartTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import universitymanager.examtypes.Exam;
import universitymanager.UniversityManager;

import java.text.DecimalFormat;
import java.util.List;

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
    public Button settingsButton;
    public Button modifyButton;

    public Label aritmethicMeanLabel;
    public Label weightedMeanLabel;
    public Label medianLabel;
    public Label modeLabel;
    public Label standardDeviationLabel;
    public Label interQuartileLabel;
    public Label weightedMeanLastFiveExamsLabel;

    private final XYChart.Series<String, Number> gradesSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> weightedAverageSeries = new XYChart.Series<>();
    private final ObservableList<Exam> exams = FXMLUtils.getEmptyObservableList();

    private static final DecimalFormat formatter = new DecimalFormat("0.00");


    public void initialize() {
        //FXML e collegamento della lista di esami con la tableView
        FXMLUtils.setUpStandardExamTable(examTable, colName, colWeight, colGrade, colDate);
        FXMLUtils.linkTableViewAndObservableList(exams, examTable, colName, colWeight, colGrade, colDate);

        //FXML Vboxes
        arrangeVBoxes();

        //FXML top row (piechart and linechart)
        arrangeTopRow();

        //FXML linechart
        arrangeLineChart();

        //FXML piechart
        arrangePieChart();

        //FXML delle label
        arrangeFXMLLabels();

        //Inizializza grafico a linee
        initGradeChart();

        //Inizializza tabelle del DB
        initDBTables();

        //Refresh dati mostrati eg grafici, scritte, liste...
        updateDatas();
    }

    //updating datas
    private void updateDatas() {
        //common updates
        FXMLUtils.commonUpdateDatas(exams, examTable);

        //controller specific updates

        //line chart updates
        updateLineChart();

        //pie chart updates
        updatePieChartProgress();

        //labels updates
        updateLabels();
    }

    private void updateLineChart() {
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
        DBSettingsInterrogation settingsInterrogation = new DBSettingsInterrogation();

        double maxCFU = settingsInterrogation.getTotalAmountCFU();
        double filled = UniversityManager.getTotalExamsWeight(exams);

        double displayedFilled = Math.min(filled, maxCFU);
        double displayedRemaining = Math.max(0, maxCFU - filled);

        double filledPercentage = (displayedFilled / maxCFU) * 100;

        pieChartLabel.setText(formatter.format(filledPercentage) + "%" + " (" + (int) displayedFilled + "/" + (int) maxCFU + ")");

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data(
                        "Filled (" + formatter.format(filledPercentage) + "%)", displayedFilled
                ),
                new PieChart.Data(
                        "Remaining (" + formatter.format(100 - filledPercentage) + "%)", displayedRemaining
                )
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

        double interQuartileRange = UniversityManager.getInterQuartileRangeFromExamList(exams);
        interQuartileLabel.setText(formatter.format(interQuartileRange));

        double weightedMeanLastFiveExams = UniversityManager.getWeightedMeanOfLastFiveExamsFromList(exams);
        weightedMeanLastFiveExamsLabel.setText(formatter.format(weightedMeanLastFiveExams));
    }

    //FXML arranging
    private void arrangeTopRow() {
        chartsRow1.widthProperty().addListener((obs, oldW, newW) -> {
            double w = newW.doubleValue();
            lineChartBox.setPrefWidth(w * 0.70);
            pieChartBox.setPrefWidth(w * 0.30);
        });
    }

    private void arrangeVBoxes() {
        leftBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.40));
        rightBox.prefWidthProperty().bind(
                mainPane.widthProperty().subtract(leftBox.widthProperty())
        );

        VBox.setVgrow(examTable, Priority.ALWAYS);
        VBox.setVgrow(lineChartBox, Priority.ALWAYS);
        VBox.setVgrow(pieChartBox, Priority.ALWAYS);
        VBox.setVgrow(lineChart, Priority.ALWAYS);
        VBox.setVgrow(pieChartTotalExam, Priority.ALWAYS);
    }

    private void arrangeLineChart() {
        lineChart.setAnimated(false);
        timeAxisInLineChart.setGapStartAndEnd(false);
        timeAxisInLineChart.setAnimated(false);
        lineChart.prefWidthProperty().bind(lineChartBox.widthProperty());
        lineChart.prefHeightProperty().bind(lineChartBox.heightProperty());
    }

    private void arrangePieChart() {
        pieChartTotalExam.prefWidthProperty().bind(pieChartBox.widthProperty());
        pieChartTotalExam.prefHeightProperty().bind(pieChartBox.heightProperty());
    }

    private void arrangeFXMLLabels() {
        aritmethicMeanLabel.setWrapText(true);
        weightedMeanLabel.setWrapText(true);
        medianLabel.setWrapText(true);
        modeLabel.setWrapText(true);
        standardDeviationLabel.setWrapText(true);
        interQuartileLabel.setWrapText(true);
        weightedMeanLastFiveExamsLabel.setWrapText(true);
    }

    //initializing
    private void initDBTables() {
        DBExamsStartTable.ensureCreated();

        DBSettingsInterrogation settingsInterrogator = new DBSettingsInterrogation();
        if (!settingsInterrogator.settingsTableExistsAndIsFull()) {
            DBSettingsStartTable.ensureCreated();
            settingsInterrogator.insertDefaultCFU();
        }
    }

    private void initGradeChart() {
        gradesSeries.setName("Voti");
        weightedAverageSeries.setName("Media ponderata");
        lineChart.getData().addAll(
                List.of(gradesSeries, weightedAverageSeries)
        );
    }

    //buttons
    public void addExamButtonOnAction(ActionEvent actionEvent) {
        FXMLUtils utils = new FXMLUtils();

        utils.openNewWindow("Aggiungi esame", "/stages/AddExamStage.fxml",
                "/styles/add_exam.css", mainPane, () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    public void removeButtonOnAction(ActionEvent actionEvent) {
        FXMLUtils utils = new FXMLUtils();

        utils.openNewWindow("Rimuovi esame", "/stages/RemoveExamStage.fxml",
                "/styles/remove_exam.css", mainPane,
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                });
    }

    public void settingButtonOnAction(ActionEvent actionEvent) {
        FXMLUtils utils = new FXMLUtils();

        utils.openNewWindow("Impostazioni", "/stages/settingsstages/SettingsStage.fxml",
                "/styles/settings_stage.css", mainPane,
                () -> {

                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                }
        );
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        FXMLUtils utils = new FXMLUtils();

        utils.openNewWindow("Modifica",
                "/stages/modifystages/ModifyExamStage.fxml",
                "/styles/modify_exam.css",
                mainPane,
                () -> {
                    //Questo viene eseguito SOLO dopo la chiusura della finestra!
                    updateDatas();

                    System.out.println("La finestra è stata chiusa. Aggiorno i dati…");
                }
        );
    }
}
