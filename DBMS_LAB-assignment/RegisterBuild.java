import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterBuild extends JFrame {

    JTextField nameBox, emailBox, phoneBox;
    JPasswordField passBox;
    JComboBox<String> genderBox;

    public RegisterBuild() {
        setTitle("Registration Page");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Register New User");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 20, 250, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        panel.add(nameLabel);
        nameBox = new JTextField();
        nameBox.setBounds(160, 80, 220, 30);
        panel.add(nameBox);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 130, 100, 25);
        panel.add(emailLabel);
        emailBox = new JTextField();
        emailBox.setBounds(160, 130, 220, 30);
        panel.add(emailBox);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 180, 100, 25);
        panel.add(passLabel);
        passBox = new JPasswordField();
        passBox.setBounds(160, 180, 220, 30);
        panel.add(passBox);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 230, 100, 25);
        panel.add(phoneLabel);
        phoneBox = new JTextField();
        phoneBox.setBounds(160, 230, 220, 30);
        panel.add(phoneBox);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 280, 100, 25);
        panel.add(genderLabel);
        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(160, 280, 220, 30);
        panel.add(genderBox);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(90, 350, 120, 35);
        registerBtn.setBackground(new Color(40, 167, 69));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(registerBtn);

        JButton loginBtn = new JButton("Go to Login");
        loginBtn.setBounds(230, 350, 120, 35);
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(loginBtn);

        registerBtn.addActionListener(e -> {
            String name = nameBox.getText().trim();
            String email = emailBox.getText().trim();
            String pass = new String(passBox.getPassword());
            String phone = phoneBox.getText().trim();
            String gender = (String) genderBox.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email!");
                return;
            }

            if (phone.length() < 10) {
                JOptionPane.showMessageDialog(this, "Phone must be 10+ digits!");
                return;
            }

            try {
                Connection conn = DatabaseConnection.getConnection();

                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Cannot connect to database!");
                    return;
                }

                PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE email=?");
                check.setString(1, email);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Email already exists!");
                    return;
                }

                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO users (name, email, password, phone, gender) VALUES (?,?,?,?,?)"
                );
                insert.setString(1, name);
                insert.setString(2, email);
                insert.setString(3, PasswordBuild.encryptPassword(pass));
                insert.setString(4, phone);
                insert.setString(5, gender);
                insert.executeUpdate();

                JOptionPane.showMessageDialog(this, "Registration Successful!");

                nameBox.setText("");
                emailBox.setText("");
                passBox.setText("");
                phoneBox.setText("");

                conn.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        loginBtn.addActionListener(e -> {
            new LoginBuild().setVisible(true);
            dispose();
        });

        add(panel);
    }

    public static void main(String[] args) {
        new RegisterBuild().setVisible(true);
    }
}