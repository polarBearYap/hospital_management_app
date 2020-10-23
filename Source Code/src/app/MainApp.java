package app;

import app.controller.AddMenuController;
import app.controller.AppbarController;
import app.controller.MainMenuController;
import app.controller.SubMenuController;
import app.model.Doctor;
import app.model.Facility;
import app.model.Lab;
import app.model.Medical;
import app.model.Patient;
import app.model.Staff;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*JFoenix Library jar file is under lib folder, if program is not running,
 * please right click "JRE System Library" > "Build Path" > "Configure Build Path" > "Add JARs"
 * to add the JFoenix. TQ.
 */
public final class MainApp extends Application {

	private Stage primaryStage;

	private BorderPane rootScene;

	/*
	 * Reason that ObservableList is used:
	 * i) Used in javafx.scene.control.TableView
	 * ii) To provide synchronization when associated with a view component.
	 * iii) Easier to edit, add, or remove objects from the model list and at the same time reflect changes in the view component.
	 *
	 * Reference:
	 * https://www.developer.com/java/data/understanding-java-observable-and-javafx-
	 * observable.html
	 */
	private static final ObservableList<Staff> STAFFS = FXCollections.observableArrayList();
	private static final ObservableList<Doctor> DOCTORS = FXCollections.observableArrayList();
	private static final ObservableList<Patient> PATIENTS = FXCollections.observableArrayList();
	private static final ObservableList<Medical> MEDICALS = FXCollections.observableArrayList();
	private static final ObservableList<Lab> LABS = FXCollections.observableArrayList();
	private static final ObservableList<Facility> FACILITIES = FXCollections.observableArrayList();

	/**
	 * Initialize 5 records for staffs, doctors, patients, medicals, labs and facilities.
	 * Fake name generated under: https://www.random-name-generator.com/malaysia
	 */
	public void initData() {

		// Staff(int id, String name, String sex, int age, int salary, String
		// workStartTime, String workEndTime, int room, String designation)
		STAFFS.add(new Staff(224, "Yap Jheng Khin", "Male", 20, 50000, "09:00AM", "05:00PM", 1,
				"Chief Executive Officer (CEO)"));
		STAFFS.add(new Staff(225, "Muhammad Haji Selamat bin Andalis", "Male", 37, 48000, "09:00AM", "05:00PM", 2,
				"Chief Operating Officer (COO)"));
		STAFFS.add(new Staff(226, "Shanthi Pakiam a/p Param Thuraisingham", "Female", 30, 47000, "09:00AM", "05:00PM",
				3, "Hospital administrator"));
		STAFFS.add(new Staff(227, "Gobalakrishnan Raj", "Male", 26, 46000, "09:00AM", "05:00PM", 4,
				"Director of finance"));
		STAFFS.add(new Staff(228, "Ainul Rohaizan binti Hamidoon", "Female", 22, 45000, "09:00AM", "05:00PM", 5,
				"Director of human resources"));

		// Doctor(int id, String name, String sex, int age, int salary, String
		// workStartTime, String workEndTime, int room, String specialist, String
		// qualification)
		DOCTORS.add(new Doctor(224, "Yap Jheng Khin", "Male", 20, 44000, "09:00AM", "05:00PM", 6, "Allergist", "MS"));
		DOCTORS.add(
				new Doctor(225, "Tiong Chooi Le", "Female", 35, 43000, "09:00AM", "05:00PM", 7, "Cardiologist", "MSc"));
		DOCTORS.add(new Doctor(226, "Loganathan Chengara a/l Rattan Raj", "Male", 50, 42000, "11:00AM", "09:00PM", 8,
				"Gastroenterologist", "DCM"));
		DOCTORS.add(new Doctor(227, "Awatif Bahri binti Darus", "Female", 66, 41000, "08:00AM", "03:00PM", 9,
				"Immunologist", "DClinSurg"));
		DOCTORS.add(
				new Doctor(228, "Koh Dou Jeong", "Male", 40, 40000, "10:00AM", "07:00PM", 10, "DMSc", "Neurologist"));

		// Patient(int id, String name, String sex, int age, String disease, String
		// admitStatus)
		PATIENTS.add(new Patient(224, "Yap Jheng Khin", "Male", 20, "Influenza", "Discharged"));
		PATIENTS.add(
				new Patient(225, "Muhammed Arbani Hamizuddin bin Firdaus", "Female", 35, "Food poisoning", "Admitted"));
		PATIENTS.add(new Patient(226, "Kasthuriraani Prakash a/p Nanthakumar Thuraisingham", "Female", 56,
				"Type 1 diabetes", "Admitted"));
		PATIENTS.add(new Patient(227, "Nur Farahtasha binti Azuan", "Female", 28, "Cancer", "Admitted"));
		PATIENTS.add(new Patient(228, "Ku Wi Dim", "Male", 25, "Influenza", "Discharged"));

		// Medical(String name, String manufacturer, String expiryDate, int cost, int
		// count)
		MEDICALS.add(new Medical("Gabapentin", "Johnson & Johnson", "17/08/2021", 15, 700));
		MEDICALS.add(new Medical("Viagra", "Roche", "18/09/2022", 16, 100));
		MEDICALS.add(new Medical("Eliquis", "Pfizer", "19/10/2023", 17, 400));
		MEDICALS.add(new Medical("Atorvastatin calcium", "Novartis", "20/11/2024", 18, 300));
		MEDICALS.add(new Medical("Sildenafil", "Merck", "21/12/2025", 19, 200));

		// Lab(String facility, int cost)
		LABS.add(new Lab("Ambulatory surgical centers", 20000));
		LABS.add(new Lab("Birth centers", 20000));
		LABS.add(new Lab("Blood banks", 20000));
		LABS.add(new Lab("Dialysis Centers", 20000));
		LABS.add(new Lab("Mental health and addiction treatment centers", 20000));

		// Facility(String facility)
		FACILITIES.add(new Facility("Ambulatory surgical centers"));
		FACILITIES.add(new Facility("Birth centers"));
		FACILITIES.add(new Facility("Dialysis Centers"));
		FACILITIES.add(new Facility("Mental health and addiction treatment centers"));
		FACILITIES.add(new Facility("Imaging and radiology centers"));
	}

	@Override
	public void start(Stage primaryStage) {

		this.initData();
		// Initialize form input fields
		Staff.preloadStaffForm();
		Doctor.preloadDoctorForm();
		Patient.preloadPatientForm();
		Medical.preloadMedicalForm();
		Lab.preloadLabForm();
		Facility.preloadFacilityForm();

		this.primaryStage = primaryStage;

		// Icon from https://icons8.com/icons/set/hospital
		this.primaryStage.getIcons().add(new Image("app/resources/icons/hmsIcon.png"));

		// Remove the default application bar and implement the custom one
		this.primaryStage.initStyle(StageStyle.UNDECORATED);

		this.launchAppbar();
		this.launchMainMenu();

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Launch application bar to display minimize and close button.
	 */
	public void launchAppbar() {

		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Appbar.fxml"));

		BorderPane curScene = null;
		try {
			curScene = (BorderPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		curScene.getStylesheets().add(MainApp.class.getResource("css/Appbar.css").toExternalForm());
		this.rootScene = curScene;

		primaryStage.setScene(new Scene(curScene));

		// Controller needs access to primaryStage to minimize and drag window
		AppbarController controller = loader.getController();
		controller.makeStageMinimizableAndDraggable(primaryStage);
	}

	/**
	 * Launch main menu to display time and menu options.
	 */
	public void launchMainMenu() {

		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainMenu.fxml"));

		AnchorPane curScene = null;
		try {
			curScene = (AnchorPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Controller needs access to the current instance of MainApp to switch menus
		MainMenuController controller = loader.getController();
		controller.setCurApp(this);

		curScene.getStylesheets().add(MainApp.class.getResource("css/MainMenu.css").toExternalForm());

		rootScene.setCenter(curScene);
	}

	/**
	 * Launch sub-menu to view records based on the selected menuOption.
	 * @param menuOption
	 */
	@SuppressWarnings("unchecked")
	public void launchSubMenu(int menuOption) {
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/SubMenu.fxml"));

		BorderPane curScene = null;
		try {
			curScene = (BorderPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		curScene.getStylesheets().add(MainApp.class.getResource("css/SubMenu.css").toExternalForm());

		// Controller needs access to the current instance of MainApp to switch menus
		SubMenuController controller = loader.getController();
		controller.setCurApp(this, menuOption);

		@SuppressWarnings("rawtypes")
		TableView table = null;

		//Initialize TableView and fetch data before displaying
		switch (menuOption) {
		case 1:
			table = new TableView<Staff>();
			table.setItems(STAFFS);
			Staff.showStaffs(table);
			break;
		case 2:
			table = new TableView<Doctor>();
			table.setItems(DOCTORS);
			Doctor.showDoctors(table);
			break;
		case 3:
			table = new TableView<Patient>();
			table.setItems(PATIENTS);
			Patient.showPatients(table);
			break;
		case 4:
			table = new TableView<Medical>();
			table.setItems(MEDICALS);
			Medical.showMedicals(table);
			break;
		case 5:
			table = new TableView<Lab>();
			table.setItems(LABS);
			Lab.showLabs(table);
			break;
		case 6:
			table = new TableView<Facility>();
			table.setItems(FACILITIES);
			Facility.showFacilities(table);
			break;
		}

		curScene.setCenter(table);
		rootScene.setCenter(curScene);
	}

	/**
	 * Launch menu to add record based on the selected menuOption.
	 * @param menuOption
	 */
	public void launchAddMenu(int menuOption) {

		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/AddMenu.fxml"));

		BorderPane curScene = null;
		try {
			curScene = (BorderPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Controller needs access to the current instance of MainApp to switch menus
		AddMenuController controller = loader.getController();
		VBox formPane = null;

		//Initialize a form based on on the chosen menu option
		switch (menuOption) {
		case 1:
			formPane = Staff.launchStaffForm(curScene);
			break;
		case 2:
			formPane = Doctor.lauchDoctorForm(curScene);
			break;
		case 3:
			formPane = Patient.launchPatientForm(curScene);
			break;
		case 4:
			formPane = Medical.launchMedicalForm(curScene);
			break;
		case 5:
			formPane = Lab.launchLabForm(curScene);
			break;
		case 6:
			formPane = Facility.launchFacilityForm(curScene);
			break;
		}

		controller.setCurApp(this, menuOption, formPane);
		curScene.getStylesheets().add(MainApp.class.getResource("css/AddMenu.css").toExternalForm());
		rootScene.setCenter(curScene);
	}

	/**
	 * Add user inputed value into the existing array based on the menuOption.
	 * @param menuOption
	 */
	public void addInputRecords(int menuOption) {

		switch (menuOption) {
		case 1:
			STAFFS.add(Staff.addNewStaff());
			break;
		case 2:
			DOCTORS.add(Doctor.addNewDoctor());
			break;
		case 3:
			PATIENTS.add(Patient.addNewPatient());
			break;
		case 4:
			MEDICALS.add(Medical.addNewMedical());
			break;
		case 5:
			LABS.add(Lab.addNewLab());
			break;
		case 6:
			FACILITIES.add(Facility.addNewFacility());
			break;
		}
	}

	/**
	 * Getter for primaryStage
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}