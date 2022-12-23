package switch_case;

public class SwitchCase {
    public static void main(String[] args) {
        String sector = "A구역";
        switch (sector) {
            case "A구역", "B구역", "C구역" -> System.out.println("지역 1");
            case "D구역", "E구역", "F구역" -> System.out.println("지역 2");
            case "G구역", "H구역", "I구역" -> System.out.println("지역 3");
            default -> System.out.println("지역 설정 오류");
        }

        String animal = "squirrel";
        String group = switch (animal){
            case "Tiger", "Lion" -> "mammal";
            case "mice", "squirrel" -> "rodents";
            case "chicken", "duck" -> "birds";
            case "butterfly", "mantis" -> "insects";
            default -> {
                int len = animal.length();
                yield String.valueOf(len);
            }
        };
        System.out.println(group);
    }
}
