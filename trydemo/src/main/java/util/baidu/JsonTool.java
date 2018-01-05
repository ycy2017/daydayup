package util.baidu;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonTool {
	
	protected static Logger logger = Logger.getLogger(JsonTool.class); 
	
    public static JsonGenerator jsonGenerator = null;
    
    public static ObjectMapper objectMapper = null;
    
    static {
        objectMapper = new ObjectMapper();        
    	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
    	objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, Boolean.TRUE);        
    }
		
	public static Map<String, Object> json2Map(String jsonStr){
		Map<String, Object> maps;
		try {
			maps = objectMapper.readValue(jsonStr, Map.class);
			return maps;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("error:"+e+" jsonStr:" +jsonStr);
			return null;
		}
		

	}
	
	public static String prettyJson(Object o){
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("error:"+e+" object:" +o);
			return (String) o;
		}
	}

	
	
}
