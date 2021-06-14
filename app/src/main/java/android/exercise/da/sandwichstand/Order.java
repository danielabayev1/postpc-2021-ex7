package android.exercise.da.sandwichstand;

public class Order {
    public String id;
    private String customerName;
    private int pickles;
    private boolean hummus;
    private boolean tahini;
    private String comment;
    private String status;

    public Order() {
    }

    public Order(String id, String costumerName, int pickles, boolean hummus,
                 boolean tahini, String comment, String status) {
        this.id = id;
        this.customerName = costumerName;
        this.pickles = pickles;
        this.hummus = hummus;
        this.tahini = tahini;
        this.comment = comment;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPickles() {
        return pickles;
    }

    public void setPickles(int pickles) {
        this.pickles = pickles;
    }

    public boolean isHummus() {
        return hummus;
    }

    public void setHummus(boolean hummus) {
        this.hummus = hummus;
    }

    public boolean isTahini() {
        return tahini;
    }

    public void setTahini(boolean tahini) {
        this.tahini = tahini;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
