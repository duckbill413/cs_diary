import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Car;
import dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        // TIP: ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User();
        user.setName("홍길동");
        user.setAge(32);

        Car car1 = new Car("K5",
                "11가 1203",
                "sedan");
        Car car2 = new Car("Q5",
                "23가 7233",
                "suv");

        List<Car> carList = Arrays.asList(car1, car2);
        user.setCars(carList);

        System.out.println(user);

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

        // INFO: json parsing
        // TIP: JsonNode (특정 객체의 값 수정 불가)
        JsonNode jsonNode = objectMapper.readTree(json);
        String _name = jsonNode.get("name").asText();
        int _age = jsonNode.get("age").asInt();
        System.out.println("name : " + _name);
        System.out.println("age : " + _age);
        // INFO: json list parsing
        // TIP: ArrayNode,, (ArrayNode) JsonNode
        JsonNode cars = jsonNode.get("cars");
        ArrayNode arrayNode = (ArrayNode) cars;
        List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {
        });
        System.out.println(_cars);

        // INFO: ObjectNode (특정 객체의 값 수정 가능)
        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "duckbill");
        objectNode.put("age", 27);

        System.out.println(objectNode.toPrettyString()); // 출력
    }
}
