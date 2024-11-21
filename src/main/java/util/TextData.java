package util;

public class TextData {
    public static String readInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>")
                .append("<h2>1.- Welcome Screen:</h2>")
                .append("<p>Read the instructions carefully on how to complete the form. When you are ready, click the 'Next' button to start the registration.</p>")
                .append("<h2>2.- Personal Data Screen:</h2>")
                .append("<p>Enter your name, email, and password in the corresponding fields.</p>")
                .append("<ul>")
                .append("<li>The email is in a valid format.</li>")
                .append("<li>The password is between 8 and 16 characters long and includes at least one number, one uppercase letter, one lowercase letter, and one special character.</li>")
                .append("</ul>")
                .append("<p>Once you have completed these fields, click 'Next'.</p>")
                .append("<h2>3.- Location Screen:</h2>")
                .append("<p>Select your country and then the province or state from the dropdown list.</p>")
                .append("<p>Click 'Next' to continue.</p>")
                .append("<h2>4.- Data Verification Screen:</h2>")
                .append("<p>Verify that the entered information is correct. If everything is fine, you can choose to export the data to a text file by checking the 'Export data' box.</p>")
                .append("<p>If you select the export option, you will be asked to choose a location to save the file using a file selector.</p>")
                .append("<p>Click 'Save' to confirm the export or 'Next' to continue without saving.</p>")
                .append("<h2>5.- Completion Screen:</h2>")
                .append("<p>A message will be displayed confirming whether the file has been saved successfully (if you selected that option).</p>")
                .append("<p>Click 'Finish' to close the program.</p>")
                .append("<h2>6.- Success Notification:</h2>")
                .append("<p>If the registration and, if applicable, the file export were successful, a pop-up window will notify you of the success of the process.</p>");
        sb.append("</html>");
        return sb.toString();
    }

    public static String readTitlePrincipalPanel() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>")
                .append("<center><h1>Instructions</h1></center>");
        sb.append("</html>");
        return sb.toString();
    }

    public static String readTitleFormPanel() {
        StringBuilder sb = new StringBuilder();

            sb.append("<html>")
                    .append("<center><h1>Register User Form</h1></center>");
            sb.append("</html>");

        return sb.toString();
    }
}