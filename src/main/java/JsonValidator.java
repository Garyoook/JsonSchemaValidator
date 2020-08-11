import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class JsonValidator {
	public static void validate(String input_path) {
		boolean isViolated = false;
		//这个就是你设定的标准JSON
		InputStream inputStream = JsonValidator.class.getResourceAsStream(input_path);
		//这个是你打算验证的JSON，这里我也用一份文件来存放，你也可以使用string或者jsonObject
		InputStream inputStream1 = JsonValidator.class.getResourceAsStream("/failure.json");
		JSONObject Schema = new JSONObject(new JSONTokener(inputStream));
		JSONObject data = new JSONObject(new JSONTokener(inputStream1));
		org.everit.json.schema.Schema schema = SchemaLoader.load(Schema);
		try {
      System.out.println("----------------------- starting validation -----------------------");
			schema.validate(data);
		} catch (ValidationException e) {
			isViolated = true;
      System.out.println(e.getViolationCount() + " violation found in the target json file.");
			for (String s : e.getAllMessages()) {
        System.out.println(s);
			}
		}
    System.out.print("----------------------- validation complete");
		if (isViolated) {
      System.out.println(" -----------------------");
		} else {
      System.out.println(", no violation found ---");
		}
	}



  public static void main(String[] args) {
		do {
      System.out.println("please give the correct path to the target Json file！");
		} while (args.length != 1);
	  validate(args[0]);

  }
}
