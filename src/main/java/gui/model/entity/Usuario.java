package gui.model.entity;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * <h1>Usuario class</h1>
 * <p>
 *     Entity class representing a user. <br>
 *     Provides methods for getting and setting user attributes, and for serializing the user object.
 * </p>
 * @see Serializable - Interface for serializing the user object
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String email;
    private String password;
    private String registerDate;

    /**
     * <h1>Constructor for creating a new user</h1>
     * <p>
     *     Initializes a new user with the specified name, email, and password. <br>
     *     Sets the registration date to the current date.
     * </p>
     * @param name The user's name
     * @param email The user's email
     * @param password The user's password
     * @see Date - Class for getting the current date
     * @since JDK21.0.5
     */
    public Usuario(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.registerDate = new Date(System.currentTimeMillis()).toString();
        System.out.println(registerDate);
    }

    /**
     * <h1>Constructor for creating a user from the database</h1>
     * <p>
     *     Initializes a user with the specified ID, name, email, password, and registration date.
     * </p>
     * @param id The user's ID
     * @param name The user's name
     * @param email The user's email
     * @param password The user's password
     * @param registerDate The user's registration date
     * @since JDK21.0.5
     */
    public Usuario(int id, String name, String email, String password, String registerDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * <h1>Get the registration year</h1>
     * <p>
     *     Parses the registration date and returns the year.
     * </p>
     * @see SimpleDateFormat - Class for parsing
     * @see Calendar - Class for getting the year
     * @see ParseException - Exception for parsing errors
     * @return int - The registration year
     * @since JDK21.0.5
     */
    public int getRegisterYear() {
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(registerDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("------------------- Usuario -------------------\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Password: ").append(password).append("\n");
        sb.append("Register Date: ").append(registerDate).append("\n");
        sb.append("------------------------------------------------\n");

        return sb.toString();
    }
}