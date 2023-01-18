package ex01;

public class User {
    private final Integer id;
    private String name;
    private Integer remainingFunds;

    public User() {
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRemainingFunds() {
        return remainingFunds;
    }

    public void setRemainingFunds(Integer remainingFunds) {
        if (remainingFunds >= 0)
            this.remainingFunds = remainingFunds;
    }
}
