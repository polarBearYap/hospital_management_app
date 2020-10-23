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

public final class Staff extends Employee {

    private StringProperty designation;

    private static final List<Integer> STAFF_ID_LIST = new ArrayList<Integer>();

    //Source: https://www.thebalancecareers.com/healthcare-medical-job-titles-2061494
    private static String[] DESIGNATION_SUGGESTION_LIST = {
    		"Accountant","Associate executive director","Chief Financial Officer (CFO)",
    		"Chief Operating Officer (COO)","Clerk","Director of Nursing","Director of Operations",
    		"Director of Rehabilitation","Director of clinical operations","Director of finance",
    		"Director of human resources","Director of nursing","Executive Director","Health Facilities Surveyor",
    		"Health Services Manager","Healthcare Administrator","Hospital administrator",
    		"Hospital operations administrator","Medical Claims and Billing Specialist","Medical students or interns",
    		"Nursing","Pharmaceutical Sales","Physician Assistant","President/Chief Executive Officer (CEO)",
    		"Recruitment specialist","Safety Surveillance Associate","Sales Associate","Sales Manager",
    		"Sales Representative","Supervisor",};

    public Staff() {
    	super();
    	this.designation = new SimpleStringProperty("");
    }

    public Staff(int id, String name, String sex, int age, int salary, String workStartTime, String workEndTime, int room, String designation) {

        super(id, name, sex, age, salary, workStartTime, workEndTime, room);
        STAFF_ID_LIST.add(id);
        this.designation = new SimpleStringProperty(designation);
    }

    //Form Input Fields
    private static final EmployeeFormLauncher parentForm = new EmployeeFormLauncher();
	private static final FormPane addForm = new FormPane(Pos.TOP_LEFT);
    private static final CustomTextField designationInputField = new CustomTextField("Designation");

    /**
     * Preload staff form.
     */
    public static void preloadStaffForm() {

    	parentForm.preloadForm(addForm, "Staff", STAFF_ID_LIST, ROOM_ID_LIST);

    	//Add auto-complete pop-up
    	designationInputField.addPopupAutoComplete(DESIGNATION_SUGGESTION_LIST);

    	//Add the input field into the scene
        addForm.addNode(designationInputField, 50, 80, 50, 80);
    }

	/**
     * Launch staff form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox launchStaffForm(BorderPane curScene) {

        curScene.setCenter(addForm.getVBoxScroll(900));

    	return addForm.getVBox();
    }

    /**
     * Store the value of the recently added staff using setter.
     * @return newStaff
     */
    public static Staff addNewStaff() {

    	Staff newStaff = new Staff();

    	parentForm.addNewEmployee(addForm, newStaff);

    	newStaff.setDesignation(designationInputField.getNode().getText());

    	designationInputField.getNode().setText("");

    	return newStaff;
	}

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
	public static void showStaffs(TableView<Staff> table) {

		showEmployees(table);

        TableColumn<Staff, String> designationColumn = new TableColumn<Staff, String>("Designation");

        table.getColumns().add(designationColumn);

		designationColumn.setCellValueFactory(cellData -> cellData.getValue().designationProperty());

		designationColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
    }

    /*
     * Getter and setter for designation
     * Getter is not used in this application, therefore being removed.
     */
    public void setDesignation(String designation) {
        this.designation.set(designation);
    }

    private StringProperty designationProperty() {
        return designation;
    }
}
