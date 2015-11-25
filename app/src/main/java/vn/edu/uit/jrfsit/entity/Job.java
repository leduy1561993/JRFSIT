package vn.edu.uit.jrfsit.entity;

/**
 * Created by LeDuy on 11/10/2015.
 */
public class Job {
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getBenifit() {
        return benifit;
    }

    public void setBenifit(String benifit) {
        this.benifit = benifit;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String isExpired() {
        return isExpired;
    }

    public void setIsExpired(String isExpired) {
        this.isExpired = isExpired;
    }

    private	String	jobId;
    private	String	categoryId;
    private	String	accountId;
    private	String	jobName;
    private	String	location;
    private	String	salary;
    private	String	description;
    private	String	tags;
    private	String	requirement;
    private	String	benifit;
    private	String	expired;
    private	String	source;
    private	String	company;
    private	String	dateCreate;
    private	String	isExpired;
    private	String	logo;

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }

    private int isSave;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    private	float	rate;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }



}
