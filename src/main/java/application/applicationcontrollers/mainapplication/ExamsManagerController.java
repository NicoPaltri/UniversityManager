package application.applicationcontrollers.mainapplication;

import application.applicationutils.ExamUtils;
import application.applicationutils.FXMLUtils;
import application.applicationutils.StatisticUtils;
import application.applicationutils.openwindowmanager.OpenWindowUtils;
import application.applicationutils.openwindowmanager.WindowRequest;
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
import examsmanager.examtypes.Exam;
import examsmanager.examtypes.GradedExam;

import java.text.DecimalFormat;
import java.util.List;

public class ExamsManagerController {

    public BorderPane mainPane;
    public VBox leftBox;
    public VBox rightBox;

    public TableView<Exam> examTable;
    public TableColumn<Exam, String> colName;
    public TableColumn<Exam, Number> colWeight;
    public TableColumn<Exam, String> colGrade;
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

        List<GradedExam> gradedExamList = ExamUtils.filterGradedExamsFromExamList(exams);

        for (GradedExam exam : gradedExamList) {
            gradesSeries.getData().add(
                    new XYChart.Data<>(exam.getDate().toString(), exam.getGrade())
            );
        }

    }

    private void updateWeightedAverageSeries() {
        weightedAverageSeries.getData().clear();

        List<GradedExam> gradedExams = ExamUtils.filterGradedExamsFromExamList(exams);

        double numerator = 0;
        double denominator = 0;

        for (GradedExam exam : gradedExams) {
            numerator += exam.getWeight() * exam.getGrade();
            denominator += exam.getWeight();

            weightedAverageSeries.getData().add(
                    new XYChart.Data<>(exam.getDate().toString(), numerator / denominator)
            );
        }
    }

    private void updatePieChartProgress() {
        DBSettingsInterrogation settingsInterrogation = new DBSettingsInterrogation();

        double maxCFU = settingsInterrogation.getTotalAmountCFU();
        double filled = StatisticUtils.getTotalExamsWeight(exams);

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
        List<GradedExam> gradedExams = ExamUtils.filterGradedExamsFromExamList(exams);

        double arithmeticMean = StatisticUtils.getArithmeticMeanFromGradedExamList(gradedExams);
        aritmethicMeanLabel.setText(formatter.format(arithmeticMean));

        double weightedMean = StatisticUtils.getWeightedMeanFromGradedExamList(gradedExams);
        weightedMeanLabel.setText(formatter.format(weightedMean));

        double median = StatisticUtils.getMedianFromGradedExamList(gradedExams);
        medianLabel.setText(formatter.format(median));

        int mode = StatisticUtils.getModeFromGradedExamList(gradedExams);
        modeLabel.setText(String.valueOf(mode));

        double standardDeviation = StatisticUtils.getStandardDeviationFromGradedExamList(gradedExams);
        standardDeviationLabel.setText(formatter.format(standardDeviation));

        double interQuartileRange = StatisticUtils.getInterQuartileRangeFromGradedExamList(gradedExams);
        interQuartileLabel.setText(formatter.format(interQuartileRange));

        double weightedMeanLastFiveExams = StatisticUtils.getWeightedMeanOfLastFiveGradedExamsFromList(gradedExams);
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

        DBSettingsStartTable.ensureCreated();
        DBSettingsInterrogation settingsInterrogator = new DBSettingsInterrogation();
        if (!settingsInterrogator.settingsTableIsFull()) {
            settingsInterrogator.fillSettingsTable();
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
        WindowRequest<Object> windowRequest = new WindowRequest<>(
                "Aggiungi esame",
                "/stages/AddExamStage.fxml"
        );
        windowRequest.withOwnerPan(mainPane);
        windowRequest.withOnClose(this::updateDatas);


        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(windowRequest);
    }

    public void removeButtonOnAction(ActionEvent actionEvent) {
        WindowRequest<Object> windowRequest = new WindowRequest<>(
                "Rimuovi esame",
                "/stages/RemoveExamStage.fxml"
        );
        windowRequest.withOverrideCss("/styles/specificRemoveExam.css");
        windowRequest.withOwnerPan(mainPane);
        windowRequest.withOnClose(this::updateDatas);

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(windowRequest);
    }

    public void settingButtonOnAction(ActionEvent actionEvent) {
        WindowRequest<Object> windowRequest = new WindowRequest<>(
                "Impostazioni",
                "/stages/settingsstages/SettingsStage.fxml"
        );
        windowRequest.withOverrideCss("/styles/specificSettingStage.css");
        windowRequest.withOwnerPan(mainPane);
        windowRequest.withOnClose(this::updateDatas);

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(windowRequest);
    }

    public void modifyButtonOnAction(ActionEvent actionEvent) {
        WindowRequest<Object> windowRequest = new WindowRequest<>(
                "Modifica",
                "/stages/modifystages/ModifyExamStage.fxml"
        );
        windowRequest.withOverrideCss("/styles/specificModifyExam.css");
        windowRequest.withOwnerPan(mainPane);
        windowRequest.withOnClose(this::updateDatas);

        OpenWindowUtils utils = new OpenWindowUtils();
        utils.openNewWindow(windowRequest);
    }
}
