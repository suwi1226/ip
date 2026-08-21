public class todo extends task {
    public todo(String name) {
        super(name);
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }
}
