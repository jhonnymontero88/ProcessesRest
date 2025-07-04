package min.gob.ec.tracingservices.dto.common;

public class CommonDTO {
    private Integer year;
    private Integer month;
    private Double sold;

    public CommonDTO(){
    }

    public CommonDTO(Integer _year, Integer _month, Double _sold){
        this.year = _year;
        this.month = _month;
        this.sold = _sold;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getSold() {
        return sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
