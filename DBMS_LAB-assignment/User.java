public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String registrationDate;

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        this.gender = gender; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
