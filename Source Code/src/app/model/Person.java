package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public abstract class Person {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty sex;
    private IntegerProperty age;

    public Person() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.sex = new SimpleStringProperty("");
        this.age = new SimpleIntegerProperty(0);
    }

    public Person(int id, String name, String sex, int age) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleIntegerProperty(age);
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param <PersonChildClass>
     * @param table
     */
    @SuppressWarnings("unchecked")
	protected static <PersonChildClass> void showPeople(TableView<PersonChildClass> table) {

        TableColumn<PersonChildClass, Integer> idColumn = new TableColumn<PersonChildClass, Integer>("Id");
        TableColumn<PersonChildClass, String> nameColumn = new TableColumn<PersonChildClass, String>("Name");
        TableColumn<PersonChildClass, String> sexColumn = new TableColumn<PersonChildClass, String>("Sex");
        TableColumn<PersonChildClass, Integer> ageColumn = new TableColumn<PersonChildClass, Integer>("Age");

        table.getColumns().addAll(idColumn, nameColumn, sexColumn, ageColumn);

		idColumn.setCellValueFactory(cellData -> ((Person) cellData.getValue()).idProperty().asObject());
		nameColumn.setCellValueFactory(cellData -> ((Person) cellData.getValue()).nameProperty());
		sexColumn.setCellValueFactory(cellData -> ((Person) cellData.getValue()).sexProperty());
		ageColumn.setCellValueFactory(cellData -> ((Person) cellData.getValue()).ageProperty().asObject());

		idColumn.setStyle("-fx-alignment: CENTER");
		sexColumn.setStyle("-fx-alignment: CENTER");
		ageColumn.setStyle("-fx-alignment: CENTER");
    }

    /*
     * Getter and setter for Id, Name, Sex and Age
     * Getter is not used in this application, therefore being removed.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    private IntegerProperty idProperty() {
        return id;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    private StringProperty nameProperty() {
        return name;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    private StringProperty sexProperty() {
        return sex;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    private IntegerProperty ageProperty() {
        return age;
    }
}
