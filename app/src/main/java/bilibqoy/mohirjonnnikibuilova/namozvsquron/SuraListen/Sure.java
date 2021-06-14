package bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen;

public class Sure {

    private int id;
    private String name;
    private String number;
    private String countAye;
    private String place;

    @Override
    public String toString() {
        return "Sure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", countAye='" + countAye + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountAye() {
        return countAye;
    }

    public void setCountAye(String countAye) {
        this.countAye = countAye;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
