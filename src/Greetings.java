import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Greetings extends JDialog {
    private JPanel contentPane;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JTable myTable;

    public Greetings() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnRefresh);

        btnRefresh.addActionListener(e -> refresh());

        btnAdd.addActionListener(e -> add());

        // call dispose() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // call dispose() on ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void refresh() {
        try {
            Facade.allBooks((MyModel) myTable.getModel());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        try {
            Facade.newBook();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JTable getTable() {
        return myTable;
    }

    public static void main(String[] args) {
        Greetings dialog = new Greetings();
        dialog.getTable().setModel(new MyModel());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
