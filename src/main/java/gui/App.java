package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * <h1>App class</h1>
 * <p>
 *     The main application class that sets up and manages the different panels of the application. <br>
 *     It uses a CardLayout to switch between different panels. <br>
 *     It also configures the application window and handles navigation between panels.
 * </p>
 * @see JFrame - The main window of the application
 * @see CardLayout - Layout manager for switching between panels
 * @see JPanel - Container for the different panels
 * @see PrincipalPanel - Panel for the main menu
 * @see RegisterPanel - Panel for user registration
 * @see CountryPanel - Panel for selecting the country and province
 * @see DisplayData - Panel for displaying the user data
 * @see FinishPanel - Panel for finishing the application
 * @see UsuarioController - Controller for managing user data using SQLite
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static PrincipalPanel principalPanel;
    private static RegisterPanel registerPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;
    private static FinishPanel finishPanel;
    private UsuarioController usuarioController;

    /**
     * <h1>App constructor</h1>
     * <p>
     *     Configures the application window and initializes the different panels. <br>
     *     Adds the panels to the main panel and sets up the CardLayout for navigation.
     * </p>
     * @see #configApp() - Configure the application window
     * @see CardLayout - Layout manager for switching between panels
     * @see JPanel - Container for the different panels
     * @see PrincipalPanel - Panel for the main menu
     * @see RegisterPanel - Panel for user registration
     * @see CountryPanel - Panel for selecting the country and province
     * @see DisplayData - Panel for displaying the user data
     * @see FinishPanel - Panel for finishing the application
     * @since JDK21.0.5
     */
    public App() {
        // Configure the app & app icon
        configApp();

        // Create the main panel and add the panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // App panels
        principalPanel = new PrincipalPanel(this);
        registerPanel = new RegisterPanel(this);
        countryPanel = new CountryPanel(this);
        displayData = new DisplayData(this);
        finishPanel = new FinishPanel(this);

        mainPanel.add(principalPanel, principalPanel.getName());
        mainPanel.add(registerPanel, registerPanel.getName());
        mainPanel.add(countryPanel, countryPanel.getName());
        mainPanel.add(displayData, displayData.getName());
        mainPanel.add(finishPanel, finishPanel.getName());

        // Add the main panel to the principal frame
        add(mainPanel);
    }

    /**
     * <h1>Configure the application</h1>
     * <p>
     *     Sets the title, size, location, and close operation of the application window. <br>
     *     Initializes the UsuarioController and sets the application icon.
     * </p>
     * @see UsuarioController - Controller for managing user data using SQLite
     * @see #addIcon() - Add the application icon
     * @since JDK21.0.5
     */
    private void configApp() {
        setTitle("App");
        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        usuarioController = new UsuarioController();
        addIcon();
    }

    /**
     * <h1>Add the application icon</h1>
     * <p>
     *     Sets the icon image for the application window. <br>
     *     If the icon cannot be loaded, displays an error message and exits the application.
     * </p>
     * @see ImageIcon - Loads the application icon
     * @since JDK21.0.5
     */
    private void addIcon() {
        try {
            setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/icon.png"))).getImage());
        } catch (NullPointerException e) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error loading app icon", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error loading app icon");
            System.exit(0);
        }
    }

    /**
     * <h1>Navigate to the next panel</h1>
     * <p>
     *     Switches to the next panel in the CardLayout. <br>
     *     Updates the data displayed in the DisplayData panel.
     * </p>
     * @see DisplayData - Panel for displaying the user data
     * @since JDK21.0.5
     */
    public void nextPanel() {
        cardLayout.next(mainPanel);
        displayData.updateData(this);
    }

    /**
     * <h1>Navigate to the previous panel</h1>
     * <p>
     *     Switches to the previous panel in the CardLayout.
     * </p>
     * @see CardLayout - Layout manager for switching between panels
     * @since JDK21.0.5
     */
    public void previousPanel() {
        cardLayout.previous(mainPanel);
    }

    /**
     * <h1>Show a specific panel</h1>
     * <p>
     *     Displays the specified panel by name in the CardLayout.
     * </p>
     * @param panel The panel to be displayed
     * @see CardLayout#show(Container, String)  - Show the specified panel
     * @since JDK21.0.5
     */
    public void showPanel(JPanel panel) {
        cardLayout.show(mainPanel, panel.getName());
    }

    /**
     * <h1>Show the statistics panel</h1>
     * <p>
     *     Adds and displays the statistics panel in the CardLayout.
     * </p>
     * @param statisticsPanel The statistics panel to be displayed
     * @since JDK21.0.5
     */
    public void showStatistics(JPanel statisticsPanel) {
        mainPanel.add(statisticsPanel, statisticsPanel.getName());
        cardLayout.show(mainPanel, statisticsPanel.getName());
    }

    /**
     * <h1>Quit the statistics panel</h1>
     * <p>
     *     Removes the statistics panel from the CardLayout and returns to the previous panel.
     * </p>
     * @param statisticsPanel The statistics panel to be removed
     * @param previousPanel The panel to return to
     * @see CardLayout#show(Container, String)  - Show the specified panel
     * @see JPanel#remove(Component) - Remove the specified component
     * @since JDK21.0.5
     */
    public void quitStatistics(JPanel statisticsPanel, JPanel previousPanel) {
        mainPanel.remove(statisticsPanel);
        cardLayout.show(mainPanel, previousPanel.getName());
    }

    /**
     * <h1>Check if a panel is already added</h1>
     * <p>
     *     Checks if the specified panel is already added to the main panel.
     * </p>
     * @param panel The panel to check
     * @see JPanel#getComponents() - Get the components of the panel
     * @see Component#getName() - Get the name of the component
     * @see List#of(Object...) - Create an immutable list
     * @return boolean - true if the panel is already added, false otherwise
     * @since JDK21.0.5
     */
    public boolean containsPanel(JPanel panel) {
        List<Component> panels = List.of(mainPanel.getComponents());

        for (Component p : panels) {
            if (p.getName().equals(panel.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * <h1>Get the user name</h1>
     * <p>
     *     Retrieves the user name from the RegisterPanel.
     * </p>
     * @see RegisterPanel#getUserName() - Get the user name
     * @return String - The user name
     * @since JDK21.0.5
     */
    public String getUserName() {
        return registerPanel.getUserName();
    }

    /**
     * <h1>Get the user email</h1>
     * <p>
     *     Retrieves the user email from the RegisterPanel.
     * </p>
     * @see RegisterPanel#geteMail() - Get the user email
     * @return String - The user email
     * @since JDK21.0.5
     */
    public String geteMail() {
        return registerPanel.geteMail();
    }

    /**
     * <h1>Get the user password</h1>
     * <p>
     *     Retrieves the user password from the RegisterPanel.
     * </p>
     * @see RegisterPanel#getPassword() - Get the user password
     * @return String - The user password
     * @since JDK21.0.5
     */
    public String getPassword() {
        return registerPanel.getPassword();
    }

    /**
     * <h1>Get the user country</h1>
     * <p>
     *     Retrieves the user country from the CountryPanel.
     * </p>
     * @see CountryPanel#getCountry() - Get the user country
     * @return String - The user country
     * @since JDK21.0.5
     */
    public String getCountry() {
        return countryPanel.getCountry();
    }

    /**
     * <h1>Get the user province</h1>
     * <p>
     *     Retrieves the user province from the CountryPanel.
     * </p>
     * @see CountryPanel#getProvince() - Get the user province
     * @return String - The user province
     * @since JDK21.0.5
     */
    public String getProvince() {
        return countryPanel.getProvince();
    }
}