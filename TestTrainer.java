public class TestTrainer {
    public static void main(String[] args) {
        Trainer ash = new Trainer ("Ash", 10);

        System.out.println("Trainer Name: " + ash.getName());
        System.out.println("Trainer Age: " + ash.getAge()); 

        System.out.println(ash);
    }
}
