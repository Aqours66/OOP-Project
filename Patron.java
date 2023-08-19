public class Patron {
    private int id; // Add this field
    private String name;

    public Patron(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Add this method to format the patron's information
    public String getInfo() {
        return "Patron ID: " + id + "\nName: " + name;
    }
}
