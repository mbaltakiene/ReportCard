import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The class allows a school to store student’s grades for a particular year
 *
 * @author margarita baltakiene
 */
public class ReportCard {
	
	/**
	 *  The variable mStudentId stores the student ID
	 */
    private int mStudentId;
    
    /**
     *  The variable mYear stores the year of reference
     */
    private int mYear;
    
    /**
     *  The variable mGrades stores the map between the subject titles and the scores. 
     *  The scores are stored as the map between the date and an integer grade.
     *  It is assumed that only one grade can be given on a certain date.
     *  Therefore all student's scores are stored in one variable 
     *  and the course titles can be different for different students 
     *  and maintained outside the class.
     */
    private HashMap<String, HashMap<Date, Integer>> mGrades;
    
    /**
     *  The variable to store the date display format to facilitate printing
     */
    private SimpleDateFormat mDateFormat;
    
    /**
     * The class constructor initialises the variables
     * @param studentID
     * @param year
     */
    public ReportCard(int studentID, int year) {
        mStudentId = studentID;
        mYear = year;
        mGrades = new HashMap<String, HashMap<Date, Integer>>();
        mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    }

    /**
     * getter method for the mStudentId
     * @return mStudentId
     */
    public int getStudentId() {
        return mStudentId;
    }

    /**
     * getter method for the mYear
     * @return mYear
     */
    public int getYear() {
        return mYear;
    }
    
    /**
     * getter method for the mDateFormat
     * @return mDateFormat
     */
    public SimpleDateFormat getDateFormat() {
        return mDateFormat;
    }

    /**
     * setter method for the mDateFormat
     * @param dateFormatString
     */
    public void setDateFormat(String dateFormatString) {
        mDateFormat = new SimpleDateFormat(dateFormatString);
    }

    /**
     * setter method for the student grades
     * @param subject
     * @param date
     * @param grade
     */
    public void setGrades(String subject, Date date, int grade) {
    	// No grade is yet stored for this subject
        if (mGrades.get(subject) == null) {
        	// Create a new map between the date and the grade
            HashMap<Date, Integer> dateGradeMap = new HashMap<Date, Integer>();
            // Record date and grade pair in the map
            dateGradeMap.put(date, grade);
            // Record subject title and grades map in the parent map
            mGrades.put(subject, dateGradeMap);
        } 
        // Some grades are stored for this subject, but not for a given date
        else if (mGrades.get(subject).get(date) == null) {
        	// Record date and grade pair in the map
            mGrades.get(subject).put(date, grade);
        } 
        // The grade for this subject and date are already recorded
        else {
            System.out.println("The grade for " + subject + " on " 
                    + mDateFormat.format(date)
                    + " already exists. To change the grade, "
                    + "use correctGrades or deleteGrades method instead.");
        }
    }

    /**
     * getter method for the student grades
     * @return mGrades
     */
    public HashMap<String, HashMap<Date, Integer>> getGrades() {
        return mGrades;
    }

    /**
     * method to correct the grade if it was incorrectly recorded
     * @param subject
     * @param date
     * @param grade
     */
    public void correctGrades(String subject, Date date, int newGrade) {
    	// The grade for this subject and date will be replaced with a new grade
    	if (hasGrades(subject, date)){
    		System.out.println("Changing the grade from " 
                    + mGrades.get(subject).get(date) 
                    + " to " + newGrade + " on "
                    + mDateFormat.format(date) + " for " + subject);
            mGrades.get(subject).replace(date, newGrade);
        }
    }

    /**
     * method to delete the grade if it was incorrectly recorded
     * @param subject
     * @param date
     */
    public void deleteGrades(String subject, Date date) {
        // The grade for this subject and date will be deleted
        if (hasGrades(subject, date)){ 
            System.out.println("Deleting the grade " 
            		+ mGrades.get(subject).get(date) + " on "
                    + mDateFormat.format(date) + " for " + subject);
            mGrades.get(subject).remove(date);
            // If all grades for a given subject are removed, 
            // then the subject with no grades is also removed
            if (mGrades.get(subject).isEmpty()) {
                mGrades.remove(subject);
            }
        }
    }
    
    /**
     * method to check if the grade was registered for a subject on a given date
     * @param subject
     * @param date
     * @return true if a grade exists for a subject on a given date
     * otherwise prints out a message and returns false.
     */
    private boolean hasGrades(String subject, Date date){
    	// No grade is yet stored for this subject
        if (mGrades.get(subject) == null) {
            System.out.println("No grade exists for " + subject); 
            return false;
        } 
        // Some grades are stored for this subject, but not for a given date
        else if (mGrades.get(subject).get(date) == null) {
            System.out.println("No grade was registered on " 
                    + mDateFormat.format(date) 
                    + " for " + subject);
            return false;
        } 
        // The grade for this subject and date exists
        return true;
    }

    /**
     * method to show the recorded student's grades
     */
    public void showGrades() {
        System.out.println(toString());
    }

    /**
     * @return the string representation of the {@link ReportCard} object.
     */
    @Override
    public String toString() {
    	// skipping the first comma when formatting the string
        boolean skipFirstComma = true;
        // first line of the output
        String firstLine = "ReportCard for studentID " + mStudentId + " in " + mYear + ":\n";
        // following line(s) of the output
        String nextLines = "";
        // No grade is recorded for the student
        if (mGrades.isEmpty()) {
            return firstLine + "no grades are recorded.";
        } 
        // Some grades are recorded for the student
        else {	
        	// iterating through the map objects of subjects and grades
            for (Entry<String, HashMap<Date, Integer>> e : mGrades.entrySet()) {
            	// start the string with the subject name
            	nextLines = nextLines + e.getKey() + " | ";
            	// iterating through the map of dates and grades for a subject
                for (Entry<Date, Integer> m : e.getValue().entrySet()) {
                	// appending the string with the the <date>: <grade>, formatted text
                    if (skipFirstComma) {
                    	nextLines = nextLines + mDateFormat.format(m.getKey()) + ": " + m.getValue();
                        skipFirstComma = false;
                    } else {
                    	nextLines = nextLines + ", " + mDateFormat.format(m.getKey()) + ": " + m.getValue();
                    }
                }
                // starting new line
                nextLines = nextLines + "\n";
                // resetting the variable to true for other subjects' formatting
                skipFirstComma = true;
            }
        }
        // the string output of the ReportCard data
        return firstLine + nextLines;
    }
}