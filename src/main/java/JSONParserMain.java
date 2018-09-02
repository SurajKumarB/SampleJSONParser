import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;

import static com.fasterxml.jackson.databind.node.JsonNodeType.ARRAY;
import static com.fasterxml.jackson.databind.node.JsonNodeType.OBJECT;

public class JSONParserMain {

    public static void main(String args[]) {
        String jsonString = "{\n" +

                "    \"firstName\":\"John\",\n" +
                "    \"lastName\":\"Smith\",\n" +
                "    \"address\":{\n" +
                "        \"streetAddress\":\"21 2nd Street\",\n" +
                "         \"city\":\"New York\",\n" +
                "         \"state\":\"NY\",\n" +
                "         \"postalCode\":{\n" +
                "\t\t \"postalPlace\" : \"NY 100\",\n" +
                "\t\t \"code\" : \"10021\"\n" +
                "\t\t }\n" +
                "    },\n" +
                "     \"age\":25,\n" +
                "     \"phoneNumbers\":[\n" +
                "            {\n" +
                "            \"type\":\"home\", \"number\":\"212 555-1234\"\n" +
                "            },\n" +
                "         {\n" +
                "            \"type\":\"fax\", \"number\":\"212 555-1234\"\n" +
                "         }\n" +
                "     ]\n" +
                "}";

        try {
            parseJSON(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    public static void parseArrayJSON(Object object, String key){
    public static void parseArrayJSON(Object object) {
        try {
//            ArrayList<Object> arrayList = new ArrayList<Object>();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(object.toString());

            System.out.println("Phone numbers : " + jsonNode);

            Iterator iterator = jsonNode.iterator();
            while (iterator.hasNext()) {
                parseJSON(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void parseJSON(Object object) {
        try {

            JsonFactory factory = new JsonFactory();
            ObjectMapper objectMapper = new ObjectMapper(factory);

            JsonNode jsonNode = objectMapper.readTree(object.toString());
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();

            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> field = fieldsIterator.next();
//                System.out.println("Node type : " + field.getValue().getNodeType());
                if (field.getValue().getNodeType() == OBJECT) {
                    parseJSON(field.getValue());
                } else if (field.getValue().getNodeType() == ARRAY) {
//                    parseArrayJSON(field.getValue(), field.getKey());
                    parseArrayJSON(field.getValue());
                } else
                    System.out.println(field.getKey() + " : " + field.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
