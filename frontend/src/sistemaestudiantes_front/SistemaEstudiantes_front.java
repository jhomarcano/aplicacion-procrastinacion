package sistemaestudiantes_front;

//import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Clase principal que implementa la interfaz gráfica del sistema de estudiantes
 */
public class SistemaEstudiantes_front extends Application {
    private final Map<String, Estudiante> estudiantes = new HashMap<>();
    private Estudiante estudianteActual;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Planificación Estudiantil");
        primaryStage.setScene(new Scene(createMainScreen(primaryStage), 600, 400));
        primaryStage.show();
    }
    
    private VBox createMainScreen(Stage stage) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Sistema de Planificación Estudiantil");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Button registerBtn = createMenuButton("Registrar Estudiante", e -> showRegisterScreen(stage));
        Button loginBtn = createMenuButton("Iniciar Sesión", e -> showLoginScreen(stage));
        Button exitBtn = createMenuButton("Salir", e -> stage.close());
        
        vbox.getChildren().addAll(titleLabel, registerBtn, loginBtn, exitBtn);
        return vbox;
    }
    
    private Button createMenuButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setOnAction(handler);
        return button;
    }
    
    private void showRegisterScreen(Stage stage) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Registro de Estudiante");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TextField userField = new TextField();
        userField.setPromptText("Usuario");
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email (@poligran.edu.co)");
        
        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");
        
        Button registerBtn = new Button("Registrar");
        registerBtn.setOnAction(e -> registrarEstudiante(userField, emailField, passField, stage));
        
        Button backBtn = new Button("Volver");
        backBtn.setOnAction(e -> stage.setScene(new Scene(createMainScreen(stage), 600, 400)));        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(userField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Contraseña:"), 0, 2);
        grid.add(passField, 1, 2);
        
        HBox buttons = new HBox(10, registerBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(titleLabel, grid, buttons);
        stage.setScene(new Scene(vbox, 600, 400));
    }
    
    private void registrarEstudiante(TextField userField, TextField emailField, PasswordField passField, Stage stage) {
        String usuario = userField.getText();
        String email = emailField.getText();
        String contraseña = passField.getText();
        
        if (usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
            showAlert("Error", "Todos los campos son obligatorios");
            return;
        }
        
        if (estudiantes.containsKey(usuario)) {
            showAlert("Error", "El usuario ya está registrado");
            return;
        }
        
        try {
            Estudiante estudiante = new Estudiante(usuario, email, contraseña);
            estudiantes.put(usuario, estudiante);
            showAlert("Éxito", "Estudiante registrado correctamente");
            stage.setScene(new Scene(createMainScreen(stage), 600, 400));
        } catch (IllegalArgumentException ex) {
            showAlert("Error", ex.getMessage());
        }
    }
    
    private void showLoginScreen(Stage stage) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Inicio de Sesión");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TextField userField = new TextField();
        userField.setPromptText("Usuario");
        
        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");
        
        Button loginBtn = new Button("Iniciar Sesión");
        loginBtn.setOnAction(e -> iniciarSesion(userField, passField, stage));
        
        Button backBtn = new Button("Volver");
        backBtn.setOnAction(e -> stage.setScene(new Scene(createMainScreen(stage), 600, 400)));
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(userField, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(passField, 1, 1);
        
        HBox buttons = new HBox(10, loginBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(titleLabel, grid, buttons);
        stage.setScene(new Scene(vbox, 600, 400));
    }
    
    private void iniciarSesion(TextField userField, PasswordField passField, Stage stage) {
        String usuario = userField.getText();
        String contraseña = passField.getText();
        
        if (!estudiantes.containsKey(usuario)) {
            showAlert("Error", "Usuario no registrado");
            return;
        }
        
        Estudiante estudiante = estudiantes.get(usuario);
        if (estudiante.getContraseña().equals(contraseña)) {
            estudianteActual = estudiante;
            showStudentMenu(stage);
        } else {
            showAlert("Error", "Contraseña incorrecta");
        }
    }
    
    private void showStudentMenu(Stage stage) {
        BorderPane borderPane = new BorderPane();
        
        Label welcomeLabel = new Label("Bienvenido, " + estudianteActual.getUsuario());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        borderPane.setTop(welcomeLabel);
        
        
        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(20));
        menuBox.setAlignment(Pos.CENTER);
        
        menuBox.getChildren().addAll(
            createMenuButton("Ver Horario", e -> showSchedule()),
            createMenuButton("Agregar Clase", e -> showAddClassDialog()),
            createMenuButton("Ver Tareas Pendientes", e -> showPendingTasks()),
            createMenuButton("Agregar Tarea", e -> showAddTaskDialog()),
            createMenuButton("Completar Tarea", e -> completarTarea()),
            createMenuButton("Cerrar Sesión", e -> {
                estudianteActual = null;
                stage.setScene(new Scene(createMainScreen(stage), 600, 400));
            })
        );
        
        borderPane.setCenter(menuBox);
        stage.setScene(new Scene(borderPane, 600, 500));
    }
    
    private void showSchedule() {
    List<HorarioClase> horario = estudianteActual.getHorario();
    
    Stage scheduleStage = new Stage();
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(15));
    
    Label titleLabel = new Label("Horario de Clases");
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    
    if (horario.isEmpty()) {
        vbox.getChildren().addAll(titleLabel, new Label("No hay clases en el horario"));
    } else {
        TableView<HorarioClase> table = new TableView<>();
        
        // Configuración de columnas (igual que antes)
        TableColumn<HorarioClase, String> diaCol = new TableColumn<>("Día");
        diaCol.setCellValueFactory(cellData -> cellData.getValue().diaProperty());
        
        TableColumn<HorarioClase, String> inicioCol = new TableColumn<>("Hora Inicio");
        inicioCol.setCellValueFactory(cellData -> cellData.getValue().horaInicioProperty());
        
        TableColumn<HorarioClase, String> finCol = new TableColumn<>("Hora Fin");
        finCol.setCellValueFactory(cellData -> cellData.getValue().horaFinProperty());
        
        TableColumn<HorarioClase, String> materiaCol = new TableColumn<>("Materia");
        materiaCol.setCellValueFactory(cellData -> cellData.getValue().materiaProperty());
        
        table.getItems().addAll(horario);
        
        // Botones para editar y eliminar
        TableColumn<HorarioClase, Void> accionesCol = new TableColumn<>("Acciones");
        accionesCol.setCellFactory(param -> new TableCell<>() {
            private final Button editarBtn = new Button("Editar");
            private final Button eliminarBtn = new Button("Eliminar");
            
            {
                editarBtn.setOnAction(event -> {
                    HorarioClase clase = getTableView().getItems().get(getIndex());
                    mostrarDialogoEdicion(clase, getIndex(), table);
                });
                
                eliminarBtn.setOnAction(event -> {
                    if (estudianteActual.eliminarClase(getIndex())) {
                        table.getItems().remove(getIndex());
                        showAlert("Éxito", "Clase eliminada correctamente");
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox botones = new HBox(5, editarBtn, eliminarBtn);
                    setGraphic(botones);
                }
            }
        });
        
        table.getColumns().addAll(diaCol, inicioCol, finCol, materiaCol, accionesCol);
        vbox.getChildren().addAll(titleLabel, table);
    }
    
    Scene scene = new Scene(vbox, 600, 400);
    scheduleStage.setScene(scene);
    scheduleStage.setTitle("Horario");
    scheduleStage.show();
}

private void mostrarDialogoEdicion(HorarioClase clase, int indice, TableView<HorarioClase> table) {
    Dialog<HorarioClase> dialog = new Dialog<>();
    dialog.setTitle("Editar Clase");
    dialog.setHeaderText("Modifique los datos de la clase");
    
    ButtonType guardarBtnType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(guardarBtnType, ButtonType.CANCEL);
    
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    
    TextField diaField = new TextField(clase.getDia());
    TextField inicioField = new TextField(clase.getHoraInicio());
    TextField finField = new TextField(clase.getHoraFin());
    TextField materiaField = new TextField(clase.getMateria());
    
    grid.add(new Label("Día:"), 0, 0);
    grid.add(diaField, 1, 0);
    grid.add(new Label("Hora Inicio:"), 0, 1);
    grid.add(inicioField, 1, 1);
    grid.add(new Label("Hora Fin:"), 0, 2);
    grid.add(finField, 1, 2);
    grid.add(new Label("Materia:"), 0, 3);
    grid.add(materiaField, 1, 3);
    
    dialog.getDialogPane().setContent(grid);
    
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == guardarBtnType) {
            return new HorarioClase(
                diaField.getText(),
                inicioField.getText(),
                finField.getText(),
                materiaField.getText()
            );
        }
        return null;
    });
    
    dialog.showAndWait().ifPresent(claseEditada -> {
        if (estudianteActual.modificarClase(indice, claseEditada)) {
            table.getItems().set(indice, claseEditada);
            showAlert("Éxito", "Clase modificada correctamente");
        }
    });
}
    
  private TableView<HorarioClase> createHorarioTableView(List<HorarioClase> horario) {
    TableView<HorarioClase> table = new TableView<>();
    
    TableColumn<HorarioClase, String> diaCol = new TableColumn<>("Día");
    diaCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getDia()));
    
    TableColumn<HorarioClase, String> inicioCol = new TableColumn<>("Hora Inicio");
    inicioCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getHoraInicio()));
    
    TableColumn<HorarioClase, String> finCol = new TableColumn<>("Hora Fin");
    finCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getHoraFin()));
    
    TableColumn<HorarioClase, String> materiaCol = new TableColumn<>("Materia");
    materiaCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getMateria()));
    
    table.getColumns().addAll(diaCol, inicioCol, finCol, materiaCol);
    table.getItems().addAll(horario);
    
    return table;
}
    
    private void showAddClassDialog() {
        Dialog<HorarioClase> dialog = new Dialog<>();
        dialog.setTitle("Agregar Clase");
        dialog.setHeaderText("Ingrese los detalles de la clase");
        
        ButtonType addButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = createClassInputGrid();
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return createHorarioClaseFromGrid(grid);
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(clase -> {
            estudianteActual.agregarClase(clase);
            showAlert("Éxito", "Clase agregada al horario!");
        });
    }
    
    private GridPane createClassInputGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField diaField = new TextField();
        diaField.setPromptText("Lunes-Viernes");
        
        TextField inicioField = new TextField();
        inicioField.setPromptText("HH:MM");
        
        TextField finField = new TextField();
        finField.setPromptText("HH:MM");
        
        TextField materiaField = new TextField();
        materiaField.setPromptText("Nombre de la materia");
        
        grid.add(new Label("Día:"), 0, 0);
        grid.add(diaField, 1, 0);
        grid.add(new Label("Hora Inicio:"), 0, 1);
        grid.add(inicioField, 1, 1);
        grid.add(new Label("Hora Fin:"), 0, 2);
        grid.add(finField, 1, 2);
        grid.add(new Label("Materia:"), 0, 3);
        grid.add(materiaField, 1, 3);
        
        return grid;
    }
    
    private HorarioClase createHorarioClaseFromGrid(GridPane grid) {
        TextField diaField = (TextField) grid.getChildren().get(1);
        TextField inicioField = (TextField) grid.getChildren().get(3);
        TextField finField = (TextField) grid.getChildren().get(5);
        TextField materiaField = (TextField) grid.getChildren().get(7);
        
        return new HorarioClase(
            diaField.getText(),
            inicioField.getText(),
            finField.getText(),
            materiaField.getText()
        );
    }
    
    private void showPendingTasks() {
        List<Tarea> tareas = estudianteActual.getTareasPendientes();
        
        Stage tasksStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Tareas Pendientes");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        if (tareas.isEmpty()) {
            vbox.getChildren().addAll(titleLabel, new Label("No hay tareas pendientes"));
        } else {
            TableView<Tarea> table = createTareasTableView(tareas);
            vbox.getChildren().addAll(titleLabel, table);
        }
        
        tasksStage.setScene(new Scene(vbox, 500, 400));
        tasksStage.setTitle("Tareas Pendientes");
        tasksStage.show();
    }
    
    private TableView<Tarea> createTareasTableView(List<Tarea> tareas) {
        TableView<Tarea> table = new TableView<>();
        
        TableColumn<Tarea, String> descCol = new TableColumn<>("Descripción");
        descCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        
        TableColumn<Tarea, Integer> prioCol = new TableColumn<>("Prioridad");
        prioCol.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty().asObject());
        
        table.getColumns().addAll(descCol, prioCol);
        table.getItems().addAll(tareas);
        
        return table;
    }
    
    private void showAddTaskDialog() {
        Dialog<Tarea> dialog = new Dialog<>();
        dialog.setTitle("Agregar Tarea");
        dialog.setHeaderText("Ingrese los detalles de la tarea");
        
        ButtonType addButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = createTaskInputGrid();
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return createTareaFromGrid(grid);
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(tarea -> {
            estudianteActual.agregarTarea(tarea);
            showAlert("Éxito", "Tarea agregada!");
        });
    }
    
    private GridPane createTaskInputGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField descField = new TextField();
        descField.setPromptText("Descripción de la tarea");
        
        TextField prioField = new TextField();
        prioField.setPromptText("Prioridad (1-10)");
        
        grid.add(new Label("Descripción:"), 0, 0);
        grid.add(descField, 1, 0);
        grid.add(new Label("Prioridad:"), 0, 1);
        grid.add(prioField, 1, 1);
        
        return grid;
    }
    
    private Tarea createTareaFromGrid(GridPane grid) {
        TextField descField = (TextField) grid.getChildren().get(1);
        TextField prioField = (TextField) grid.getChildren().get(3);
        
        try {
            int prioridad = Integer.parseInt(prioField.getText());
            if (prioridad < 1 || prioridad > 10) {
                showAlert("Error", "La prioridad debe estar entre 1 y 10");
                return null;
            }
            return new Tarea(descField.getText(), prioridad);
        } catch (NumberFormatException e) {
            showAlert("Error", "La prioridad debe ser un número");
            return null;
        }
    }
    
    private void completarTarea() {
        if (estudianteActual.getTareasPendientes().isEmpty()) {
            showAlert("Información", "No hay tareas pendientes para completar");
            return;
        }
        
        estudianteActual.completarTarea();
        showAlert("Tarea Completada", "Se ha marcado la tarea más antigua como completada");
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}