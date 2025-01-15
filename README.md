# Registration Form

This project is a practice exercise for the subject of Interface Development (DI) in Java.

[Download Program](https://github.com/Sacha1083/registrationForm/releases/download/V2.0/registrationFormInstaller.exe) - Requires administrator if installed for all users

## Features

- **Data Display and Save**: Displays received data and asks the user if they want to save it. If the user chooses to save, a pop-up window appears to select the destination folder. [#18](https://github.com/Sacha1083/registrationForm/issues/18)
- **Load Provinces/States from File**: Loads provinces or states from a text file located in the `files` directory. [#16](https://github.com/Sacha1083/registrationForm/issues/16)
- **Maven Integration**: The project has been converted to use Maven for better project management and dependency handling. [#12](https://github.com/Sacha1083/registrationForm/issues/12)

## App images
<p align="center">
  <img src="https://github.com/user-attachments/assets/fcb6bcd7-8183-4e4d-94f4-3fdf0653cf27" alt="Captura de pantalla 2025-01-15 145245"/>
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/da80b231-c5e9-4811-a36c-73d7ce838bdd" alt="Captura de pantalla 2025-01-15 145417" width="400"/>
  <img src="https://github.com/user-attachments/assets/1fff4226-47a0-4abc-8d24-94ea16a4c6b9" alt="Captura de pantalla 2025-01-15 145429" width="400"/>
</p>

## Requirements

- Java 8 or higher (JDK 21.0.5 recommended)
- Maven 3.6.3 or higher
- IntelliJ IDEA (recommended for development)

## Setup

### Clone the Repository

1. Clone the repository:
    ```sh
    git clone https://github.com/Sacha1083/registrationForm.git
    ```
2. Navigate to the project directory:
    ```sh
    cd registrationForm
    ```

### Import the Project into IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select `File` > `New` > `Project from Existing Sources...`.
3. Navigate to the cloned project directory and select the `pom.xml` file.
4. IntelliJ IDEA will automatically recognize the project as a Maven project. Click `OK`.
5. Wait for IntelliJ to download the required dependencies and set up the project.

### Build the Project

To build the project using Maven, run:
```sh
mvn clean install
```

## Usage

To run the project, use the following command from the project directory:
```sh
java -jar target/registrationForm.jar
```

This will start the application and load the registration form.

## Contributions

Contributions are welcome. If you find any issues or have suggestions, please open an `issue` in the repository or submit a `pull request`.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
