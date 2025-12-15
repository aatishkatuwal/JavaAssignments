import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class ConvertFrame extends JFrame {
    private final JTextField inputField, outputField;
    private final JButton convertBtn, clearBtn, exitBtn;
    private final JRadioButton usdRadio, mxnRadio, eurRadio;
    private final JRadioButton usdRadioTo, mxnRadioTo, eurRadioTo;

    public ConvertFrame() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Initialize components
        inputField = new JTextField(10);
        outputField = new JTextField(10);
        outputField.setEditable(false);
        outputField.setBackground(Color.GREEN);
        convertBtn = new JButton("Convert");
        clearBtn = new JButton("Clear");
        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this::confirmExit);

        // Initialize radio buttons
        usdRadio = new JRadioButton("US Dollar", true);
        mxnRadio = new JRadioButton("Mexican Peso");
        eurRadio = new JRadioButton("Euro");
        usdRadioTo = new JRadioButton("US Dollar");
        mxnRadioTo = new JRadioButton("Mexican Peso", true);
        eurRadioTo = new JRadioButton("Euro");

        // Load and resize icons
        ImageIcon usdIcon = createIcon("/img/dollar.jpg");
        ImageIcon mxnIcon = createIcon("/img/peso.jpg");
        ImageIcon eurIcon = createIcon("/img/euro.jpg");

        // Group radio buttons
        ButtonGroup fromGroup = new ButtonGroup();
        ButtonGroup toGroup = new ButtonGroup();
        fromGroup.add(usdRadio);
        fromGroup.add(mxnRadio);
        fromGroup.add(eurRadio);
        toGroup.add(usdRadioTo);
        toGroup.add(mxnRadioTo);
        toGroup.add(eurRadioTo);

        // Add components to the frame
        add(createCurrencyPanel("Convert from:", usdRadio, mxnRadio, eurRadio, usdIcon, mxnIcon, eurIcon));
        add(createInputPanel("Enter Currency:", inputField));
        add(createCurrencyPanel("Convert to:", usdRadioTo, mxnRadioTo, eurRadioTo, usdIcon, mxnIcon, eurIcon));
        add(createOutputPanel("Comparable Currency is:", outputField));
        add(createButtonPanel(convertBtn, clearBtn, exitBtn));

        // Event listeners
        convertBtn.addActionListener(this::convertCurrency);
        clearBtn.addActionListener(this::clearFields);

        // Menu bar
        createMenuBar();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createCurrencyPanel(String title, JRadioButton rBtn1, JRadioButton rBtn2, JRadioButton rBtn3,
                                       ImageIcon icon1, ImageIcon icon2, ImageIcon icon3) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(title));
        panel.add(createLabeledRadioButton(rBtn1, icon1));
        panel.add(createLabeledRadioButton(rBtn2, icon2));
        panel.add(createLabeledRadioButton(rBtn3, icon3));
        return panel;
    }

    private JPanel createLabeledRadioButton(JRadioButton rBtn, ImageIcon icon) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(iconLabel, BorderLayout.NORTH);

        panel.add(rBtn, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInputPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private JPanel createOutputPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        textField.setPreferredSize(new Dimension(100, 20));
        panel.add(textField);
        return panel;
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this::showAboutDialog);

        JMenuItem convertItem = new JMenuItem("Convert");
        convertItem.addActionListener(this::convertCurrency);

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(this::clearFields);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this::confirmExit);

        fileMenu.add(aboutItem);
        fileMenu.addSeparator();
        fileMenu.add(convertItem);
        fileMenu.add(clearItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private ImageIcon createIcon(String path) {
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(imgUrl);
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    private void convertCurrency(ActionEvent event) {
        try {
            double amountToConvert = Double.parseDouble(inputField.getText());
            double convertedAmount = 0.0;
            String fromCurrency = "";
            String toCurrency = "";

            if (usdRadio.isSelected() && mxnRadioTo.isSelected()) {
                convertedAmount = amountToConvert * (1 / 0.057687);
                fromCurrency = "US Dollar";
                toCurrency = "Mexican Peso";
            } else if (mxnRadio.isSelected() && usdRadioTo.isSelected()) {
                convertedAmount = amountToConvert * 0.057687;
                fromCurrency = "Mexican Peso";
                toCurrency = "US Dollar";
            } else if (usdRadio.isSelected() && eurRadioTo.isSelected()) {
                convertedAmount = amountToConvert * 0.920693;
                fromCurrency = "US Dollar";
                toCurrency = "Euro";
            } else if (eurRadio.isSelected() && usdRadioTo.isSelected()) {
                convertedAmount = amountToConvert * (1 / 0.920693);
                fromCurrency = "Euro";
                toCurrency = "US Dollar";
            } else if (mxnRadio.isSelected() && eurRadioTo.isSelected()) {
                convertedAmount = amountToConvert * 0.053094;
                fromCurrency = "Mexican Peso";
                toCurrency = "Euro";
            } else if (eurRadio.isSelected() && mxnRadioTo.isSelected()) {
                convertedAmount = amountToConvert * (1 / 0.053094);
                fromCurrency = "Euro";
                toCurrency = "Mexican Peso";
            } else {
                // If from and to are the same currency, no conversion is needed
                convertedAmount = amountToConvert;
                fromCurrency = toCurrency = usdRadio.isSelected() ? "US Dollar" : (mxnRadio.isSelected() ? "Mexican Peso" : "Euro");
            }

            outputField.setText(String.format("%.2f", convertedAmount));

            // Show the result in a dialog
            JOptionPane.showMessageDialog(this,
                    String.format("%s to %s\n%.2f is equivalent to %.2f",
                            fromCurrency, toCurrency, amountToConvert, convertedAmount),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the currency amount.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields(ActionEvent event) {
        inputField.setText("");
        outputField.setText("");
    }

    private void confirmExit(ActionEvent event) {
        int confirmed = JOptionPane.showConfirmDialog(
                this,
                "Are you sure?",
                "Confirmation window",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void showAboutDialog(ActionEvent event) {
        JOptionPane.showMessageDialog(this,
                "Currency Converter Program\nusing menus and buttons\nsource: https://www.oanda.com/currency-converter/",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConvertFrame());
    }
}




