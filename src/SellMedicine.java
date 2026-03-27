/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JOptionPane;
import java.sql.Connection;
import dao.ConnectionProvider;
import java.awt.HeadlessException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author v1p3r
 */
public class SellMedicine extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SellMedicine.class.getName());
    private String username = "";
    private DefaultTableModel korpaModel;
    private java.util.List<Integer> lijekIdsKorpa = new java.util.ArrayList<>();

    /**
     * Creates new form SellMedicine
     */
    public SellMedicine() {
        initComponents();
        setLocationRelativeTo(null);

        // Postavi model za korpu (tblRacun)
        korpaModel = new DefaultTableModel(
            new String[]{"Naziv", "Količina", "Cijena", "Ukupno", " "}, // zadnja za X dugme
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1; // samo količina se može mijenjati
            }
        };
        tblRacun.setModel(korpaModel);

        // Dodaj dugme za brisanje (kao u narudžbama)
        int kolonaBrisanje = 4;
        tblRacun.getColumnModel().getColumn(kolonaBrisanje).setCellRenderer(new ButtonRenderer());
        tblRacun.getColumnModel().getColumn(kolonaBrisanje).setCellEditor(new ButtonEditor(new JCheckBox()));
        tblRacun.getColumnModel().getColumn(kolonaBrisanje).setPreferredWidth(40);
    }
    
    private void azurirajUkupnoStavke() {
        try {
            double cijena = Double.parseDouble(txtCijena.getText().replace(" KM", "").replace(",", "."));
            int kolicina = Integer.parseInt(txtKolicina.getText().trim());
            double ukupno = cijena * kolicina;
            txtUkupnoStavke.setText(String.format("%.2f KM", ukupno));
        } catch (NumberFormatException ex) {
            txtUkupnoStavke.setText("0.00 KM");
        }
    }
    
    private void azurirajUkupnoRacuna() {
        double suma = 0;
        for (int i = 0; i < korpaModel.getRowCount(); i++) {
            String ukupnoStr = korpaModel.getValueAt(i, 3).toString()
                    .replace(" KM", "")
                    .replace(",", ".");
            try {
                suma += Double.parseDouble(ukupnoStr);
            } catch (Exception ignored) {}
        }
        txtRacunCijena.setText(String.format("%.2f KM", suma));
    }
    
    public SellMedicine(String tempName) {
        initComponents();
        username = tempName;
        setLocationRelativeTo(null);

        // 1. Postavi model za korpu (tblRacun)
        postaviKorpaModel();
        ocistiPoljaOdabranogLijeka();

        // 2. Listener za odabir lijeka iz pretrage
        postaviSelektovanjeLijeka();

        // 3. Listener za promjenu količine (ažurira ukupno stavke u realnom vremenu)
        txtKolicina.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { azurirajUkupnoStavke(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e)   { azurirajUkupnoStavke(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e)  { azurirajUkupnoStavke(); }
        });
    }
    private void ocistiPoljaOdabranogLijeka() {
        txtLijek.setText("");
        txtCijena.setText("");
        txtKolicina.setText("");
        txtUkupnoStavke.setText("0.00 KM");
        txtKolicina.setEnabled(false);
        btnDodajKorpa.setEnabled(false);
    }
    // Nova metoda - postavljanje modela za korpu
    private void postaviKorpaModel() {
        korpaModel = new DefaultTableModel(
            new String[]{"Naziv lijeka", "Količina", "Cijena", "Ukupno"},   // uklonjena zadnja kolona " "
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1;        // samo količina se može mijenjati
            }
        };
        tblRacun.setModel(korpaModel);

        // Centriraj kolone
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblRacun.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Količina
        tblRacun.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Ukupno
    }

    // Nova metoda - odabir lijeka iz tabele
    private void postaviSelektovanjeLijeka() {
        tblLijek.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblLijek.getSelectedRow();
                if (row != -1) {
                    // Kolone: 0=ID (skriven), 1=ATC, 2=Naziv, 3=Cijena, 4=Zaliha
                    txtLijek.setText(tblLijek.getValueAt(row, 2).toString());   // Naziv
                    txtCijena.setText(tblLijek.getValueAt(row, 3).toString());   // Cijena

                    txtKolicina.setText("1");
                    txtKolicina.setEnabled(true);
                    btnDodajKorpa.setEnabled(true);

                    azurirajUkupnoStavke();   // izračunaj ukupno za 1 komad
                } else {
                    txtLijek.setText("");
                    txtCijena.setText("");
                    txtKolicina.setText("");
                    txtUkupnoStavke.setText("0.00 KM");
                    txtKolicina.setEnabled(false);
                    btnDodajKorpa.setEnabled(false);
                }
            }
        });
    }

// Ažuriranje ukupno za trenutnu stavku
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnZatvori = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnPretrazi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLijek = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtLijek = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUkupnoStavke = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRacun = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtRacunCijena = new javax.swing.JLabel();
        btnDodajKorpa = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        btnZavrsi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prodaj lijekove");
        setUndecorated(true);

        btnZatvori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        btnZatvori.addActionListener(this::btnZatvoriActionPerformed);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 48)); // NOI18N
        jLabel1.setText("Prodaja lijekova");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel2.setText("Pretraga");

        btnPretrazi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnPretrazi.setText("Pretraži");
        btnPretrazi.addActionListener(this::btnPretraziActionPerformed);

        tblLijek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "ATC", "Naziv", "Cijena", "Zaliha"
            }
        ));
        jScrollPane1.setViewportView(tblLijek);

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel3.setText("Odabrani lijek:");

        txtLijek.setText("Paracetamol");

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel4.setText("Cijena:");

        txtCijena.setText("1.25 KM");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel5.setText("Količina:");

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel6.setText("Ukupno stavke:");

        txtUkupnoStavke.setText("20.36 KM");

        tblRacun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Naziv", "Količina", "Cijena", "Ukupno"
            }
        ));
        jScrollPane2.setViewportView(tblRacun);

        jLabel8.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel8.setText("Račun:");

        txtRacunCijena.setText("14.50 KM");

        btnDodajKorpa.setText("Dodaj u korpu");
        btnDodajKorpa.addActionListener(this::btnDodajKorpaActionPerformed);

        btnObrisi.setText("Obriši stavku");
        btnObrisi.addActionListener(this::btnObrisiActionPerformed);

        btnZavrsi.setText("Završi prodaju");
        btnZavrsi.addActionListener(this::btnZavrsiActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(430, 430, 430)
                .addComponent(btnZatvori)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLijek)
                            .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCijena)
                            .addComponent(txtUkupnoStavke)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(471, 471, 471)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPretrazi))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnDodajKorpa)
                                    .addGap(63, 63, 63)
                                    .addComponent(btnObrisi)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnZavrsi))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(520, 520, 520)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtRacunCijena)))
                .addContainerGap(437, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnZatvori))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPretrazi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLijek)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCijena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUkupnoStavke))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtRacunCijena))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDodajKorpa)
                    .addComponent(btnObrisi)
                    .addComponent(btnZavrsi))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnZatvoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZatvoriActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        new PharmacistDashboard(username).setVisible(true);
    }//GEN-LAST:event_btnZatvoriActionPerformed

    private void btnPretraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPretraziActionPerformed
        // TODO add your handling code here:
        String search = txtSearch.getText().trim();
        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Unesite naziv ili ATC!");
            return;
        }

        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, atc_sifra, naziv, cijena, na_stanju " +
                "FROM lijek WHERE naziv LIKE ? OR atc_sifra LIKE ? " +
                "ORDER BY naziv LIMIT 50"
            );
            String like = "%" + search + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tblLijek.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),                        // skrivena kolona
                    rs.getString("atc_sifra"),
                    rs.getString("naziv"),
                    String.format("%.2f KM", rs.getDouble("cijena")),
                    rs.getInt("na_stanju")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnPretraziActionPerformed

    private void btnDodajKorpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajKorpaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblLijek.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Prvo odaberite lijek iz tabele!");
            return;
        }

        try {
            int kolicina = Integer.parseInt(txtKolicina.getText().trim());
            if (kolicina <= 0) {
                JOptionPane.showMessageDialog(this, "Količina mora biti veća od 0!");
                return;
            }

            // Provjera zalihe
            int zaliha = (Integer) tblLijek.getValueAt(selectedRow, 4);
            if (kolicina > zaliha) {
                JOptionPane.showMessageDialog(this, 
                    "Nema dovoljno na stanju!\nDostupno: " + zaliha + " komada");
                return;
            }

            String naziv = txtLijek.getText();
            String cijenaStr = txtCijena.getText().replace(" KM", "").replace(",", ".");
            double cijena = Double.parseDouble(cijenaStr);
            double ukupno = kolicina * cijena;

            int lijekId = (Integer) tblLijek.getValueAt(selectedRow, 0);

            // Dodaj u korpu
            korpaModel.addRow(new Object[]{
                naziv,
                kolicina,
                txtCijena.getText(),
                String.format("%.2f KM", ukupno),
                "X"
            });
            lijekIdsKorpa.add(lijekId);

            azurirajUkupnoRacuna();

            // Očisti polja
            ocistiPoljaOdabranogLijeka();
            tblLijek.clearSelection();
            txtSearch.requestFocus();   // fokus nazad na pretragu

        } 
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nevažeća količina!");
        } 
        catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Greška pri dodavanju u korpu!");
        }
    }//GEN-LAST:event_btnDodajKorpaActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblRacun.getSelectedRow();
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Odaberite stavku koju želite obrisati iz korpe!", 
                "Upozorenje", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        lijekIdsKorpa.remove(selectedRow);

        korpaModel.removeRow(selectedRow);
        azurirajUkupnoRacuna();
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void btnZavrsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZavrsiActionPerformed
        // TODO add your handling code here:
        if (korpaModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Korpa je prazna! Dodajte barem jedan lijek.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection con = null;
        try {
            con = ConnectionProvider.getCon();
            con.setAutoCommit(false);        // početak transakcije

            // 1. Spremi zaglavlje prodaje
            PreparedStatement psProdaja = con.prepareStatement(
                "INSERT INTO prodaje (datum, ukupno, placeno, nacin_placanja, korisnik) " +
                "VALUES (NOW(), ?, ?, 'Gotovina', ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            double ukupno = Double.parseDouble(txtRacunCijena.getText()
                    .replace(" KM", "")
                    .replace(",", "."));

            psProdaja.setDouble(1, ukupno);
            psProdaja.setDouble(2, ukupno);        // za sada plaćeno = ukupno
            psProdaja.setString(3, username);
            psProdaja.executeUpdate();

            ResultSet rs = psProdaja.getGeneratedKeys();
            int prodajaId = -1;
            if (rs.next()) {
                prodajaId = rs.getInt(1);
            }

            // 2. Spremi sve stavke i smanji zalihe
            PreparedStatement psStavka = con.prepareStatement(
                "INSERT INTO prodaja_stavke (prodaja_id, lijek_id, kolicina, prodajna_cijena) " +
                "VALUES (?, ?, ?, ?)"
            );

            PreparedStatement psSmanjiZalihu = con.prepareStatement(
                "UPDATE lijek SET na_stanju = na_stanju - ? WHERE id = ?"
            );

            for (int i = 0; i < korpaModel.getRowCount(); i++) {
                int lijekId = lijekIdsKorpa.get(i);
                int kolicina = Integer.parseInt(korpaModel.getValueAt(i, 1).toString());
                double cijena = Double.parseDouble(
                    korpaModel.getValueAt(i, 2).toString()
                        .replace(" KM", "")
                        .replace(",", ".")
                );

                // Spremi stavku
                psStavka.setInt(1, prodajaId);
                psStavka.setInt(2, lijekId);
                psStavka.setInt(3, kolicina);
                psStavka.setDouble(4, cijena);
                psStavka.executeUpdate();

                // Smanji zalihu
                psSmanjiZalihu.setInt(1, kolicina);
                psSmanjiZalihu.setInt(2, lijekId);
                psSmanjiZalihu.executeUpdate();
            }

            con.commit();   // sve je prošlo OK → potvrdi transakciju

            JOptionPane.showMessageDialog(this, 
                "Prodaja uspješno završena!\nRačun broj: " + prodajaId + 
                "\nUkupno: " + txtRacunCijena.getText(), 
                "Uspjeh", JOptionPane.INFORMATION_MESSAGE);

            // Očisti korpu nakon uspješne prodaje
            korpaModel.setRowCount(0);
            lijekIdsKorpa.clear();
            txtRacunCijena.setText("0.00 KM");

        } catch (SQLException ex) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ignored) {}
            }
            JOptionPane.showMessageDialog(this, 
                "Greška pri spremanju prodaje:\n" + ex.getMessage(), 
                "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Neočekivana greška: " + ex.getMessage());
        } finally {
            if (con != null) {
                try { 
                    con.setAutoCommit(true); 
                    con.close(); 
                } catch (Exception ignored) {}
            }
        }
    }//GEN-LAST:event_btnZavrsiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SellMedicine().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodajKorpa;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnPretrazi;
    private javax.swing.JButton btnZatvori;
    private javax.swing.JButton btnZavrsi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLijek;
    private javax.swing.JTable tblRacun;
    private javax.swing.JLabel txtCijena;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JLabel txtLijek;
    private javax.swing.JLabel txtRacunCijena;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JLabel txtUkupnoStavke;
    // End of variables declaration//GEN-END:variables
}
