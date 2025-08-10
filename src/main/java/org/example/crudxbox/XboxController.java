package org.example.crudxbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class XboxController {

    // Table
    @FXML private TableView<Tarea> tblTareas;
    @FXML private TableColumn<Tarea, Integer> colId;
    @FXML private TableColumn<Tarea, String>  colTitulo;
    @FXML private TableColumn<Tarea, String>  colPrioridad;
    @FXML private TableColumn<Tarea, LocalDate> colFecha;
    @FXML private TableColumn<Tarea, String>  colDescripcion;

    // Form
    @FXML private TextField txtTitulo;
    @FXML private TextArea  txtDescripcion;
    @FXML private ComboBox<String> cbPrioridad;
    @FXML private DatePicker dpFechaLimite;

    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    // Datos en memoria
    private final ObservableList<Tarea> tareas = FXCollections.observableArrayList();
    private final AtomicInteger secuenciaId = new AtomicInteger(1);

    @FXML
    private void initialize() {
        // Enlazar columnas
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colTitulo.setCellValueFactory(c -> c.getValue().tituloProperty());
        colPrioridad.setCellValueFactory(c -> c.getValue().prioridadProperty());
        colFecha.setCellValueFactory(c -> c.getValue().fechaLimiteProperty());
        colDescripcion.setCellValueFactory(c -> c.getValue().descripcionProperty());

        // Tabla
        tblTareas.setItems(tareas);
        tblTareas.setPlaceholder(new Label("Sin tareas aun"));

        // Combo prioridades
        cbPrioridad.setItems(FXCollections.observableArrayList("Baja", "Media", "Alta", "Crítica"));

        // Selección form y botones
        tblTareas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            boolean haySel = sel != null;
            btnEditar.setDisable(!haySel);
            btnEliminar.setDisable(!haySel);
            if (haySel) {
                txtTitulo.setText(sel.getTitulo());
                txtDescripcion.setText(sel.getDescripcion());
                cbPrioridad.setValue(sel.getPrioridad());
                dpFechaLimite.setValue(sel.getFechaLimite());
            }
        });

        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        // juegos
        juegos();
    }

    // === Handlers FXML ===
    @FXML private void onAgregar() {
        if (!validar()) return;
        Tarea t = new Tarea(
                secuenciaId.getAndIncrement(),
                txtTitulo.getText().trim(),
                txtDescripcion.getText().trim(),
                cbPrioridad.getValue(),
                dpFechaLimite.getValue()
        );
        tareas.add(t);
        limpiar();
        tblTareas.getSelectionModel().select(t);
    }

    @FXML private void onEditar() {
        Tarea sel = tblTareas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alert("Selecciona una fila para editar.", Alert.AlertType.WARNING);
            return;
        }
        if (!validar()) return;

        sel.setTitulo(txtTitulo.getText().trim());
        sel.setDescripcion(txtDescripcion.getText().trim());
        sel.setPrioridad(cbPrioridad.getValue());
        sel.setFechaLimite(dpFechaLimite.getValue());
        tblTareas.refresh();
        limpiar();
    }

    @FXML private void onEliminar() {
        Tarea sel = tblTareas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alert("Selecciona una fila para eliminar.", Alert.AlertType.WARNING);
            return;
        }
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar la tarea seleccionada?", ButtonType.OK, ButtonType.CANCEL);
        conf.setHeaderText("Confirmación");
        conf.showAndWait().ifPresent(b -> {
            if (b == ButtonType.OK) {
                tareas.remove(sel);
                limpiar();
            }
        });
    }

    @FXML private void onLimpiar() { limpiar(); }

    // === Utilidades ===
    private void limpiar() {
        txtTitulo.clear();
        txtDescripcion.clear();
        cbPrioridad.getSelectionModel().clearSelection();
        dpFechaLimite.setValue(null);
        tblTareas.getSelectionModel().clearSelection();
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
// === valida que se cumpla con todos los campos ====
    private boolean validar() {
        StringBuilder sb = new StringBuilder();
        if (txtTitulo.getText() == null || txtTitulo.getText().trim().isEmpty())
            sb.append("- El título es requerido.\n");
        if (cbPrioridad.getValue() == null)
            sb.append("- Selecciona una prioridad.\n");
        if (dpFechaLimite.getValue() == null)
            sb.append("- Selecciona una fecha límite.\n");

        if (sb.length() > 0) {
            alert(sb.toString(), Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void alert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.show();
    }

    private void juegos() {
        tareas.addAll(
                new Tarea(secuenciaId.getAndIncrement(), "Halo Infinite", "Campaña y multijugador", "Alta", LocalDate.now().plusDays(7)),
                new Tarea(secuenciaId.getAndIncrement(), "Forza Horizon 5", "Temporada actual", "Media", LocalDate.now().plusDays(14)),
                new Tarea(secuenciaId.getAndIncrement(), "Gears 5", "Co-op acto II", "Baja", LocalDate.now().plusDays(21))
        );
    }
}
