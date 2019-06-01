package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrinterStateController {

    private final PrinterState printerState = new PrinterState();

    @RequestMapping("/temperature/settemp")
    @ResponseBody
    public String setTemp(@RequestParam(value="temp") float temp) {

        printerState.setTemp(temp);
        System.out.println("New Temperature: " + temp);

        return "1";
    }

    @RequestMapping("/processPercentage/setProcessPercentage")
    @ResponseBody
    public String setProcessPercentage(@RequestParam(value="processPercentage") float processPercentage) {

        printerState.setProcessPercentage(processPercentage);
        System.out.println("New Process Percentage: " + processPercentage);

        return "1";
    }


    @RequestMapping("/getPrinterState")
    public PrinterState getPrinterState() {


        System.out.println("Returned printerState: " + printerState);

        return printerState;
    }

}
