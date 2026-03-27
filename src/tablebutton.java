/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author v1p3r
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        setText((value == null) ? "Obriši" : value.toString());
        return this;
    }
}

// ────────────────────────────────────────────────
// Editor – hvata klik i izvršava brisanje
// ────────────────────────────────────────────────
class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private int row;

    public ButtonEditor(JCheckBox checkBox) {
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();   // važno – zatvara editor
                performDeleteAction();
            }
        });
    }

    private void performDeleteAction() {
        if (table != null && row >= 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(row);

            // Opcionalno: ažuriraj ukupnu sumu ako imaš
            // azurirajUkupno();
        }
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        this.table = table;
        this.row = row;
        this.label = (value == null) ? "Obriši" : value.toString();
        button.setText(label);
        
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // ovdje možeš vratiti nešto ako treba
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}