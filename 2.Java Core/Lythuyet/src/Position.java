public class Position {
    // gồm 2 thuộc tính là id và name(enum(DEV, TEST, PM, SCRUM_MASTER))

    private int id;
    private PositionName name;

    public Position(int id, PositionName name) {
        this.id = id;
        this.name = name;
    }

    // 1 phương thức in thông tin của position đó
    public void printInfo() {
        System.out.println("Position ID: " + id);
        System.out.println("Position Name: " + name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PositionName getName() {
        return name;
    }

    public void setName(PositionName name) {
        this.name = name;
    }


}