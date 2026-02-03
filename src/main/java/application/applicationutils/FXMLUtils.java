package application.applicationutils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import examsmanager.examtypes.Exam;
import examsmanager.UniversityManager;

import java.util.List;

public class FXMLUtils {
    public static ObservableList<Exam> getEmptyObservableList() {
        return FXCollections.observableArrayList();
    }

    public static void setUpStandardExamTable(TableView<Exam> examTable,
                                              TableColumn<Exam, String> colName,
                                              TableColumn<Exam, Number> colWeight,
                                              TableColumn<Exam, String> colGrade,
                                              TableColumn<Exam, String> colDate) {

        colName.prefWidthProperty().bind(examTable.widthProperty().multiply(0.50));
        colWeight.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colGrade.prefWidthProperty().bind(examTable.widthProperty().multiply(0.10));
        colDate.prefWidthProperty().bind(examTable.widthProperty().multiply(0.30));

        examTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public static void linkTableViewAndObservableList(ObservableList<Exam> exams,
                                                      TableView<Exam> examTable,
                                                      TableColumn<Exam, String> colName,
                                                      TableColumn<Exam, Number> colWeight,
                                                      TableColumn<Exam, String> colGrade,
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
        colDate.setSortType(TableColumn.SortType.DESCENDING);
        examTable.getSortOrder().setAll(List.of(colDate));
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

        exams.setAll(universityManager.getExamOrderedObservableListFromDB());
    }

    public static <T> void clearTableSelection(TableView<T> tableView) {
        tableView.getSelectionModel().clearSelection();
    }
}
