package gui;

import util.Log;
import util.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * App class
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
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class View extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static PrincipalPanel principalPanel;
    private static RegisterPanel registerPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;
    private static FinishPanel finishPanel;
    private UsuarioController usuarioController;

    /**
     * App constructor
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
    public View() {
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
     * Configure the application
     * <p>
     *     Sets the title, size, location, and close operation of the application window. <br>
     *     Initializes the UsuarioController and sets the application icon.
     * </p>
     * @see UsuarioController - Controller for managing user data using SQLite
     * @see #addIcon() - Add the application icon
     * @since JDK21.0.5
     */
    private void configApp() {
        setTitle("registrationForm");
        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        usuarioController = new UsuarioController();
        addIcon();
    }

    /**
     * Add the application icon
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
            Log.error("Error loading app icon" + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Navigate to the next panel
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
     * Navigate to the previous panel
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
     * Show a specific panel
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
     * Show the statistics panel
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
     * Quit the statistics panel
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
     * Check if a panel is already added
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
     * Get the user name
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
     * Get the user email
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
     * Get the user password
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
     * Get the user country
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
     * Get the user province
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