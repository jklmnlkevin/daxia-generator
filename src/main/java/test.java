

public class test {
    public static void main(String[] args) {
        String templateString = "insert into promocomment(promo_id, content) values({id}, '{content}');";
        for (int i = 1; i <= 10; i++) {
            String copy = templateString;
            copy = copy.replace("{id}", i + "");
            copy = copy.replace("{content}", "试试看，尝尝鲜");
            System.out.println(copy);
        }
    }
}
