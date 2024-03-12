class Passenger
{
    static int id = 1;

    String name, preferredBerth, alloted;
    int age, passengerId;

    Passenger(String name, int age, String preferredBerth)
    {
        this.name = name;
        this.age = age;
        this.preferredBerth = preferredBerth;
        this.alloted = "";
        this.passengerId = this.id++;
    }
}