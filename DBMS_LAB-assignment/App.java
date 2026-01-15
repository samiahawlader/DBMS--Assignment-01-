import javax.swing.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String[] options = {"Login", "Register"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to User Management System!",
                    "Welcome",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                new LoginBuild().setVisible(true);
            } else if (choice == 1) {
                new RegisterBuild().setVisible(true);
            }
        });
    }
}