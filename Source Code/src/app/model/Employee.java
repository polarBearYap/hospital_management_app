package app.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public abstract class Employee extends Person {

    private IntegerProperty salary;
    private ObjectProperty<WorkTimeUtility> workTime;
    private IntegerProperty room;

    protected static final List<Integer> ROOM_ID_LIST = new ArrayList<Integer>();

    public Employee() {
    	super();
        this.salary = new SimpleIntegerProperty(0);
		this.workTime = new SimpleObjectProperty<WorkTimeUtility>(null);
		this.room = new SimpleIntegerProperty(0);
    }

    public Employee(int id, String name, String sex, int age, int salary, String workStartTime, String workEndTime, int room) {

        super(id, name, sex, age);
        ROOM_ID_LIST.add(room);
        this.salary = new SimpleIntegerProperty(salary);
		this.workTime = new SimpleObjectProperty<WorkTimeUtility>(new WorkTimeUtility(workStartTime, workEndTime));
		this.room = new SimpleIntegerProperty(room);
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param <EmployeeChildClass>
     * @param table
     */
	@SuppressWarnings("unchecked")
	protected static <EmployeeChildClass> void showEmployees(TableView<EmployeeChildClass> table) {

        showPeople(table);

        TableColumn<EmployeeChildClass, WorkTimeUtility> workTimeColumn = new TableColumn<EmployeeChildClass, WorkTimeUtility>("Work Time");
        TableColumn<EmployeeChildClass, Integer> salaryColumn = new TableColumn<EmployeeChildClass, Integer>("Salary");
        TableColumn<EmployeeChildClass, Integer> roomColumn = new TableColumn<EmployeeChildClass, Integer>("Room No.");

        table.getColumns().addAll(workTimeColumn, salaryColumn, roomColumn);

		workTimeColumn.setCellValueFactory(cellData -> ((Employee) cellData.getValue()).workTimeProperty());
        salaryColumn.setCellValueFactory(cellData -> ((Employee) cellData.getValue()).salaryProperty().asObject());
        roomColumn.setCellValueFactory(cellData -> ((Employee) cellData.getValue()).roomNoProperty().asObject());

		workTimeColumn.setStyle("-fx-alignment: CENTER");
        salaryColumn.setStyle("-fx-alignment: CENTER");
        roomColumn.setStyle("-fx-alignment: CENTER");

		workTimeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
		salaryColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
		roomColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
    }

	/**
	 * Private class to handle converting and displaying work time.
	 */
    private static final class WorkTimeUtility {

    	private final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
    	private LocalTime startTime;
    	private LocalTime endTime;

		public WorkTimeUtility(LocalTime startTime, LocalTime endTime) {
    		this.startTime = startTime;
    		this.endTime = endTime;
    	}

    	public WorkTimeUtility(String startTimeString, String endTimeString) {
    		this.startTime = LocalTime.parse(startTimeString, timeFormat);
    		this.endTime = LocalTime.parse(endTimeString, timeFormat);
    	}

    	@Override
    	public String toString() {
    		return startTime.format(timeFormat) + " - " + endTime.format(timeFormat);
    	}
    }

    /*
     * Getter and setter for salary, work time and room number
     * Getter is not used in this application, therefore being removed.
     */

    public void setWorkTime(LocalTime workStartTime, LocalTime workEndTime) {
        this.workTime.set(new WorkTimeUtility(workStartTime, workEndTime));
    }

    private ObjectProperty<WorkTimeUtility> workTimeProperty() {
    	return workTime;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    private IntegerProperty salaryProperty() {
        return salary;
    }

    public void setRoomNo(int room) {
        this.room.set(room);
    }

    private IntegerProperty roomNoProperty() {
        return room;
    }
}
