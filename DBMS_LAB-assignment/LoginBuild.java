import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginBuild extends JFrame {

    JTextField emailBox;
    JPasswordField passBox;

    public LoginBuild() {
        setTitle("Login Page");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("User Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(180, 20, 200, 40);
        panel.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(40, 90, 100, 30);
        panel.add(emailLabel);

        emailBox = new JTextField();
        emailBox.setBounds(250, 90, 200, 35);
        panel.add(emailBox);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setBounds(40, 145, 100, 30);
        panel.add(passLabel);

        passBox = new JPasswordField();
        passBox.setBounds(250, 145, 200, 35);
        panel.add(passBox);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 210, 195, 35);
        loginBtn.setBackground(new Color(100, 149, 237));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(255, 210, 195, 35);
        registerBtn.setBackground(new Color(100, 149, 237));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(registerBtn);

        loginBtn.addActionListener(e -> {
            String email = emailBox.getText().trim();
            String pass = new String(passBox.getPassword());

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            try {
                Connection conn = DatabaseConnection.getConnection();

                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email=?");
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String savedPass = rs.getString("password");

                    if (PasswordBuild.verifyPassword(pass, savedPass)) {
                        String userName = rs.getString("name");
                        JOptionPane.showMessageDialog(this, "Welcome " + userName);
                        new DashboardBuild(rs).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Wrong password!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User not found!");
                }

                conn.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        registerBtn.addActionListener(e -> {
            new RegisterBuild().setVisible(true);
            dispose();
        });

        add(panel);
    }

    public static void main(String[] args) {
        new LoginBuild().setVisible(true);
    }
}