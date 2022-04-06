package hci.compare.rates.entity;

public class VehicleDetails
{
    private Trim[] trim;

    private String model;

    private String vin;

    private String modelYear;

    private String make;

    public Trim[] getTrim ()
    {
        return trim;
    }

    public void setTrim (Trim[] trim)
    {
        this.trim = trim;
    }

    public String getModel ()
    {
        return model;
    }

    public void setModel (String model)
    {
        this.model = model;
    }

    public String getVin ()
    {
        return vin;
    }

    public void setVin (String vin)
    {
        this.vin = vin;
    }

    public String getModelYear ()
    {
        return modelYear;
    }

    public void setModelYear (String modelYear)
    {
        this.modelYear = modelYear;
    }

    public String getMake ()
    {
        return make;
    }

    public void setMake (String make)
    {
        this.make = make;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [trim = "+trim+", model = "+model+", vin = "+vin+", modelYear = "+modelYear+", make = "+make+"]";
    }
}
