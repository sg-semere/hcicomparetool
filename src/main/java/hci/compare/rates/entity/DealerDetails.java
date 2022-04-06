package hci.compare.rates.entity;
public class DealerDetails
{
    private String dealerCountry;

    private String dealerName;

    private String dealerCode;

    private String programName;

    private String dealerState;

    public String getDealerCountry ()
    {
        return dealerCountry;
    }

    public void setDealerCountry (String dealerCountry)
    {
        this.dealerCountry = dealerCountry;
    }

    public String getDealerName ()
    {
        return dealerName;
    }

    public void setDealerName (String dealerName)
    {
        this.dealerName = dealerName;
    }

    public String getDealerCode ()
    {
        return dealerCode;
    }

    public void setDealerCode (String dealerCode)
    {
        this.dealerCode = dealerCode;
    }

    public String getProgramName ()
    {
        return programName;
    }

    public void setProgramName (String programName)
    {
        this.programName = programName;
    }

    public String getDealerState ()
    {
        return dealerState;
    }

    public void setDealerState (String dealerState)
    {
        this.dealerState = dealerState;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dealerCountry = "+dealerCountry+", dealerName = "+dealerName+", dealerCode = "+dealerCode+", programName = "+programName+", dealerState = "+dealerState+"]";
    }
}
