package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universitymanager.Exam;
import universitymanager.UniversityManager;

import java.io.IOException;

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

        //associo ad ogni colonna il valore riferito all'esame
        colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        colWeight.setCellValueFactory(cell -> cell.getValue().weightProperty());
        colGrade.setCellValueFactory(cell -> cell.getValue().gradeProperty());
        colDate.setCellValueFactory(cell -> cell.getValue().dateProperty());

        //associa la lista e la lista ordinata
        SortedList<Exam> sorted = new SortedList<>(exams);
        sorted.comparatorProperty().bind(examTable.comparatorProperty());
        examTable.setItems(sorted);
        /*
         * sorted è un wrapper (osservatore) di exams
         * il metodo per ordinare la table, viene applicato in modo dinamico anche a sorted
         * la table viene scritta seguendo sorted
         */

        //definisce il come la tabella della essere ordinata
        colDate.setSortType(TableColumn.SortType.ASCENDING);
        examTable.getSortOrder().setAll(colDate);
        examTable.sort();
        /*
         * specifico che colDate preferisce come ordinamento ascending
         * dico che table deve seguire l'ordinamento impostato in colDate
         *
         * examTable.sort:
         *   table aggiorna il suo comparator (quindi quello di colDate)
         *   sorted aggiorna (bind) il suo comparator
         *   sorted riordina i dati
         *   table mostra sorted
         * */
    }

    public static void commonUpdateDatas(ObservableList<Exam> exams, TableView<Exam> examTable) {
        updateList(exams);

        clearTableSelection(examTable);
    }

    private static void updateList(ObservableList<Exam> exams) {
        UniversityManager universityManager = new UniversityManager();

        exams.setAll(universityManager.getObservableListFromDB());
    }

    public static <T> void clearTableSelection(TableView<T> tableView) {
        tableView.getSelectionModel().clearSelection();
    }


    public void openNewWindow(String windowName, String windowPath, String cssPath, Pane mainPane, Runnable onClose) {
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

    public <C> void openNewWindow(
            String windowName,
            String windowPath,
            String cssPath,
            Pane mainPane,
            java.util.function.Consumer<C> controllerInitializer,
            Runnable onClose
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowPath));
            Parent root = loader.load();

            // prende controller creato da FXMLLoader
            C controller = loader.getController();
            if (controllerInitializer != null) {
                controllerInitializer.accept(controller);
            }

            //css
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());

            stage.setOnHidden(e -> {
                if (onClose != null) onClose.run();
            });

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
