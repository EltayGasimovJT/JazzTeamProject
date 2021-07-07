import exception.NumberCustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.NumberService;

import java.io.IOException;

public class App {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String PATH = "firstTask\\src\\main\\resources\\data\\data.txt";

    public static void main(String[] args) {
        int i = 0;

        try {
            i = NumberService.countSum(PATH);
        } catch (IOException e) {
            LOGGER.error("Cannot open file ");
        } catch (NumberCustomException e){
            LOGGER.error(e.getMessage());
        }
        LOGGER.info(i);
    }
}
