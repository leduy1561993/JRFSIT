package vn.edu.uit.jrfsit.entity;
/**
 * Created by LeDuy on 11/5/2015.
 */
public class JobSearch {
    public JobSearch() {
        dateTime =0;
    }

    public JobSearch(int id, String keyword, String location, String specialy, String logoURL) {
        this.id = id;
        this.jobName = keyword;
        this.logoURL = logoURL;
        this.location = location;
        this.specialy = specialy;
    }

    public JobSearch(String keyword, String location, String sortmode, String specialy, long dateTime) {
        this.jobName = keyword;
        this.location = location;
        this.sortmode = sortmode;
        this.specialy = specialy;
        this.dateTime = dateTime;
    }
    public JobSearch(int id, String keyword, String location,  String specialy,String sortmode, long dateTime) {
        this.id = id;
        this.jobName = keyword;
        this.location = location;
        this.sortmode = sortmode;
        this.specialy = specialy;
        this.dateTime = dateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String keyword) {
        this.jobName = keyword;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    private String jobName;
    private String location;
    private String salary;
    private String specialy;

    public String getSortmode() {
        return sortmode;
    }

    public void setSortmode(String sortmode) {
        this.sortmode = sortmode;
    }

    private String sortmode;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    private String information;

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    private String logoURL;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private String company;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    private long dateTime;
}
