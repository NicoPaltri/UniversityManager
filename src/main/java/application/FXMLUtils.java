package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import universitymanager.Exam;
import universitymanager.UniversityManager;

public class FXMLUtils {
    public static ObservableList<Exam> getEmptyObservableList() {
        return FXCollections.observableArrayList();
    }

    public static void setUpStandardExamTable(TableView<Exam> examTable,
                                              TableColumn<Exam, String> colName,
                                              TableColumn<Exam, Number> colWeight,
                                              TableColumn<Exam, Number> colGrade,
                                              TableColumn<Exam, String> colDate) {

        colName.prefWidthProperty().bind(examTable.widthProperty().multiply(0.50));
        colWeight.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colGrade.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colDate.prefWidthProperty().bind(examTable.widthProperty().multiply(0.30));

        examTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static void linkTableViewAndObservableList(ObservableList<Exam> exams,
                                                      TableView<Exam> examTable,
                                                      TableColumn<Exam, String> colName,
                                                      TableColumn<Exam, Number> colWeight,
                                                      TableColumn<Exam, Number> colGrade,
                                                      TableColumn<Exam, String> colDate) {

        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colWeight.setCellValueFactory(c -> c.getValue().weightProperty());
        colGrade.setCellValueFactory(c -> c.getValue().gradeProperty());
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());

        // 2. CREIAMO IL WRAPPER SortedList
        SortedList<Exam> sortedData = new SortedList<>(exams);

        // 3. Colleghiamo il comparatore
        sortedData.comparatorProperty().bind(examTable.comparatorProperty());

        // 4. Passiamo i dati alla tabella
        examTable.setItems(sortedData);

        // 5. FORZIAMO L'ORDINAMENTO VISIVO
        colDate.setSortType(TableColumn.SortType.ASCENDING);

        // Aggiungiamo la colonna alla lista degli ordinamenti attivi
        examTable.getSortOrder().clear();
        examTable.getSortOrder().add(colDate);

        // Chiamiamo sort() per applicarlo immediatamente
        examTable.sort();
    }

    public static void commonUpdateDatas(ObservableList<Exam> exams, TableView<Exam> examTable) {
        updateList(exams);

        clearTableSelection(examTable);
    }

    private static void updateList(ObservableList<Exam> exams) {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());
    }

    public static void clearTableSelection(TableView<Exam> examTable) {
        Platform.runLater(() -> examTable.getSelectionModel().clearSelection());
    }

    public static void errorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di input");
        alert.setHeaderText("Operazione non valida");
        alert.setContentText(message);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

    }
}
