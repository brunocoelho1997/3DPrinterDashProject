package hello;

public class PrinterState {

    private float temp;
    private float processPercentage;


    public PrinterState() {
        temp = 0;
        processPercentage = 0;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getProcessPercentage() {
        return processPercentage;
    }

    public void setProcessPercentage(float processPercentage) {
        this.processPercentage = processPercentage;
    }

    @Override
    public String toString() {
        return "PrinterState{" +
                "temp=" + temp +
                ", processPercentage=" + processPercentage +
                '}';
    }
}
