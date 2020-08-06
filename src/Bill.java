public class Bill {
    private final String time;
    private final String phone;

    public Bill(String time, String phone) {
        this.time = time;
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "time='" + time + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
