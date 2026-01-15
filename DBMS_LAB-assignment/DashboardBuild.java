import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DashboardBuild extends JFrame {

    JTable userTable;
    DefaultTableModel tableModel;
    JLabel userCountLabel;
    String loggedUserName, loggedUserEmail;

    public DashboardBuild(ResultSet rs) {
        try {
            loggedUserName = rs.getString("name");
            loggedUserEmail = rs.getString("email");
        } catch (Exception e) {
            loggedUserName = "User";
            loggedUserEmail = "";
        }

        setTitle("Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setPreferredSize(new Dimension(900, 120));
        headerPanel.setBackground(new Color(70, 130, 180));

        JLabel greetingLabel = new JLabel("Hello, " + loggedUserName + "!");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 26));
        greetingLabel.setForeground(Color.WHITE);
        greetingLabel.setBounds(30, 20, 500, 35);
        headerPanel.add(greetingLabel);

        JLabel emailInfo = new JLabel("Email: " + loggedUserEmail);
        emailInfo.setFont(new Font("Arial", Font.PLAIN, 15));
        emailInfo.setForeground(Color.WHITE);
        emailInfo.setBounds(30, 65, 500, 25);
        headerPanel.add(emailInfo);

        JButton exitBtn = new JButton("Logout");
        exitBtn.setBounds(760, 30, 110, 40);
        exitBtn.setBackground(new Color(220, 53, 69));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 15));
        headerPanel.add(exitBtn);

        exitBtn.addActionListener(e -> {
            new LoginBuild().setVisible(true);
            dispose();
        });

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        JLabel tableHeading = new JLabel("  Registered Users List");
        tableHeading.setFont(new Font("Arial", Font.BOLD, 19));
        tableHeading.setPreferredSize(new Dimension(900, 45));

        String[] columnNames = {"ID", "Name", "Email", "Phone", "Gender", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setRowHeight(32);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        userTable.getTableHeader().setBackground(new Color(52, 152, 219));
        userTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTable = new JScrollPane(userTable);

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollTable, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(900, 55));
        footerPanel.setBackground(new Color(240, 240, 240));

        userCountLabel = new JLabel("Total Users: 0");
        userCountLabel.setFont(new Font("Arial", Font.BOLD, 17));
        footerPanel.add(userCountLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadUsers();
    }

    void loadUsers() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Cannot connect to database!");
                return;
            }

            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM users ORDER BY registration_date DESC");

            int totalUsers = 0;
            while (result.next()) {
                Object[] rowData = {
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("gender"),
                        result.getString("registration_date")
                };
                tableModel.addRow(rowData);
                totalUsers++;
            }

            userCountLabel.setText("Total Users: " + totalUsers);

            stmt.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading users!");
            ex.printStackTrace();
        }
    }
}