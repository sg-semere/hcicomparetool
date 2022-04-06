package hci.compare.rates.entity;
public class Trim
{
    private String trimName;

    public String getTrimName ()
    {
        return trimName;
    }

    public void setTrimName (String trimName)
    {
        this.trimName = trimName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [trimName = "+trimName+"]";
    }
}