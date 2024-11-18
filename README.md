# Registration Form

This project is a practice exercise for the subject of Interface Development (DI) in Java.

## Features

- **Data Display and Save**: Displays received data and asks the user if they want to save it. If the user chooses to save, a pop-up window appears to select the destination folder. [#18](https://github.com/Sacha1083/registrationForm/issues/18)
- **Load Provinces/States from File**: Loads provinces or states from a text file located in the `files` directory. [#16](https://github.com/Sacha1083/registrationForm/issues/16)
- **Maven Integration**: The project has been converted to use Maven for better project management and dependency handling. [#12](https://github.com/Sacha1083/registrationForm/issues/12)

## Requirements

- Java 8 or higher
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
