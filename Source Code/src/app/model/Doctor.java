package app.model;

import java.util.ArrayList;
import java.util.List;

import app.util.CustomTextField;
import app.util.EmployeeFormLauncher;
import app.util.FormPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public final class Doctor extends Employee {

    private StringProperty specialist;
    private StringProperty qualification;

    private static List<Integer> DOCTOR_ID_LIST = new ArrayList<Integer>();
    //Qualification: https://en.wikipedia.org/wiki/Medical_degree
    private static final String[] QUALIFICATION_SUGGESTION_LIST = {
    		"BMBS","CM","ChM","DCM","DClinSurg","DM","DMSc, DMedSc","DO","DPhil","DS, DSurg",
    		"MBBCh","MBBS","MBChB","MCM","MCh","MChir","MD","MD(Res)","MM","MMSc","MMed",
    		"MMedSc","MPhil","MS","MSc","MSurg","PhD"
    };

    //Specialist: https://www.rantaumeditrip.com/34-jenis-doktor-pakar-peranannya?lang=en
    private static final String[] SPECIALIST_SUGGESTION_LIST = {
    		"Allergist","Anaesthesiologist","Andrologist","Cardiac electrophysiologist",
    		"Cardiologist","Dermatologist","Endocrinologist","Gastroenterologist",
    		"Geriatrician","Hematologist","Hepatologist","Immunologist","Neonatologist",
    		"Nephrologist","Neurologist","Obstetrician & Gynecologist (O & G)",
    		"Oncologist","Ophthalmologist","Orthopedist","Otolaryngologist (or ENT Specialist)",
    		"Pathologist","Pediatrician","Perinatologist","Physiatrist","Plastic surgeon",
    		"Pulmonologist","Radiologist","Rheumatologist","Urologist"
    };

    public Doctor() {
    	super();
		this.specialist = new SimpleStringProperty("");
		this.qualification = new SimpleStringProperty("");
    }

	public Doctor(int id, String name, String sex, int age, int salary, String workStartTime, String workEndTime, int room, String specialist, String qualification) {
		super(id, name, sex, age, salary, workStartTime, workEndTime, room);
		DOCTOR_ID_LIST.add(id);
		this.specialist = new SimpleStringProperty(specialist);
		this.qualification = new SimpleStringProperty(qualification);
	}

	//Form Input Fields
	private static final EmployeeFormLauncher PARENT_FORM = new EmployeeFormLauncher();
    private static final FormPane ADD_FORM = new FormPane(Pos.TOP_LEFT);
    private static final CustomTextField SPECIALIST_INPUT_FIELD = new CustomTextField("Specialist");
    private static final CustomTextField QUALIFICATION_INPUT_FIELD = new CustomTextField("Qualification");

    /**
     * Preload doctor form.
     */
    public static void preloadDoctorForm() {

    	PARENT_FORM.preloadForm(ADD_FORM, "Doctor", DOCTOR_ID_LIST, ROOM_ID_LIST);

    	//Add auto-complete pop-up
    	SPECIALIST_INPUT_FIELD.addPopupAutoComplete(SPECIALIST_SUGGESTION_LIST);
    	QUALIFICATION_INPUT_FIELD.addPopupAutoComplete(QUALIFICATION_SUGGESTION_LIST);

    	//Add all input fields into the scene
        ADD_FORM.addNode(SPECIALIST_INPUT_FIELD, 50, 80, 0, 80);
        ADD_FORM.addNode(QUALIFICATION_INPUT_FIELD, 50, 80, 50, 80);
    }

	/**
     * Launch doctor form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox lauchDoctorForm(BorderPane curScene) {

    	SPECIALIST_INPUT_FIELD.getNode().setText("");
    	QUALIFICATION_INPUT_FIELD.getNode().setText("");

    	curScene.setCenter(ADD_FORM.getVBoxScroll(900));

    	return ADD_FORM.getVBox();
    }

    /**
     * Store the value of the recently added doctor using setter.
     * @return newDoctor
     */
    public static Doctor addNewDoctor() {

    	Doctor newDoctor = new Doctor();

    	PARENT_FORM.addNewEmployee(ADD_FORM, newDoctor);

    	newDoctor.setSpecialist(SPECIALIST_INPUT_FIELD.getNode().getText());
    	newDoctor.setQualification(QUALIFICATION_INPUT_FIELD.getNode().getText());

    	SPECIALIST_INPUT_FIELD.getNode().setText("");
    	QUALIFICATION_INPUT_FIELD.getNode().setText("");
    	return newDoctor;
	}

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
    @SuppressWarnings("unchecked")
	public static void showDoctors(TableView<Doctor> table) {

    	showEmployees(table);

        TableColumn<Doctor, String> specialistColumn = new TableColumn<Doctor, String>("Specialist");
        TableColumn<Doctor, String> qualificationColumn = new TableColumn<Doctor, String>("Qualification");

        table.getColumns().addAll(specialistColumn, qualificationColumn);

		specialistColumn.setCellValueFactory(cellData -> cellData.getValue().specialistProperty());
		qualificationColumn.setCellValueFactory(cellData -> cellData.getValue().qualificationProperty());

		qualificationColumn.setStyle("-fx-alignment: CENTER");

		specialistColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
		qualificationColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
    }

    /*
     * Getter and setter for specialist, qualification
     * Getter is not used in this application, therefore being removed.
     */
    public void setSpecialist(String specialist) {
        this.specialist.set(specialist);
    }

    private StringProperty specialistProperty() {
        return specialist;
    }

    public void setQualification(String qualification) {
        this.qualification.set(qualification);
    }

    private StringProperty qualificationProperty() {
        return qualification;
    }
}

